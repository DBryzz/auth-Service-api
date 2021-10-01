package dbryzz.services.auth.event;

import dbryzz.services.auth.dto.payload.request.LogoutRequest;
import org.springframework.context.ApplicationEvent;

import java.time.Instant;
import java.util.Date;

public class OnUserLogoutSuccessEvent extends ApplicationEvent {

    private final String userEmail;
    private final String token;
    private final transient LogoutRequest logoutRequest;
    private final Date eventTime;

    public OnUserLogoutSuccessEvent(String userEmail, String token, LogoutRequest logoutRequest) {
        super(userEmail);
        this.userEmail = userEmail;
        this.token = token;
        this.logoutRequest = OnUserLogoutSuccessEvent.this.logoutRequest;
        this.eventTime = Date.from(Instant.now());
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getToken() {
        return token;
    }

    public LogoutRequest getLogoutRequest() {
        return OnUserLogoutSuccessEvent.this.logoutRequest;
    }

    public Date getEventTime() {
        return eventTime;
    }
}