package dbryzz.services.auth.event.listener;

import dbryzz.services.auth.event.OnRegenerateEmailVerificationEvent;
import dbryzz.services.auth.exception.MailSendException;
import dbryzz.services.auth.model.EmailVerificationToken;
import dbryzz.services.auth.model.User;
import dbryzz.services.auth.service.MailService;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
public class OnRegenerateEmailVerificationEventListener implements ApplicationListener<OnRegenerateEmailVerificationEvent> {

    private static final Logger logger = Logger.getLogger(OnRegenerateEmailVerificationEventListener.class);
    private final MailService mailService;

    @Autowired
    public OnRegenerateEmailVerificationEventListener(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * As soon as a registration event is complete, invoke the email verification
     */
    @Override
    @Async
    public void onApplicationEvent(OnRegenerateEmailVerificationEvent onRegenerateEmailVerificationEvent) {
        resendEmailVerification(onRegenerateEmailVerificationEvent);
    }

    /**
     * Send email verification to the user and persist the token in the database.
     */
    private void resendEmailVerification(OnRegenerateEmailVerificationEvent event) {
        User user = event.getUser();
        EmailVerificationToken emailVerificationToken = event.getToken();
        String recipientAddress = user.getEmail();

        String emailConfirmationUrl =
                event.getRedirectUrl().queryParam("token", emailVerificationToken.getToken()).toUriString();
        try {
            mailService.sendEmailVerification(emailConfirmationUrl, recipientAddress);
        } catch (IOException | TemplateException | MessagingException e) {
            logger.error(e);
            throw new MailSendException(recipientAddress, "Email Verification");
        }
    }

}
