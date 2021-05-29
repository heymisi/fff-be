package hu.fitforfun.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:emailtext.properties")
public class AppProperties {
    @Autowired
    private Environment env;

    public String getEmailSender() {
        return env.getProperty("email.sender");
    }

    public String getEmailVerifyEmailSubject() {
        return env.getProperty("email.verify-email.subject");
    }

    public String getEmailVerifyEmailHtmlBody() {
        return env.getProperty("email.verify-email.html-body");
    }

    public String getEmailVerifyEmailTextBody() {
        return env.getProperty("email.verify-email.text-body");
    }

    public String getEmailPasswordResetHtmlBody() {
        return env.getProperty("email.password.reset.html-body");
    }

    public String getEmailPasswordResetTextBody() {
        return env.getProperty("email.password.reset.text-body");
    }

    public String getEmailPasswordResetSubject() {
        return env.getProperty("email.password-reset.subject");
    }
}
