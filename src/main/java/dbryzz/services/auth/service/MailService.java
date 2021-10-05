package dbryzz.services.auth.service;

import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;

public interface MailService {
    void sendEmailVerification(String emailVerificationUrl, String to)
            throws IOException, TemplateException, MessagingException;

    void sendResetLink(String resetPasswordLink, String to)
                    throws IOException, TemplateException, MessagingException;

    void sendAccountChangeEmail(String action, String actionStatus, String to)
                            throws IOException, TemplateException, MessagingException;
}
