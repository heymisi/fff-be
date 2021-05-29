package hu.fitforfun.util;

import hu.fitforfun.SpringApplicationContext;
import hu.fitforfun.configuration.AppProperties;

public class EmailConstants {

    private static AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("appProperties");

    public static String getEmailSender() {
        return appProperties.getEmailSender();
    }

    public static String getEmailVerifyEmailSubject() {
        return appProperties.getEmailVerifyEmailSubject();
    }

    public static String getEmailVerifyEmailHtmlBody() {
        return appProperties.getEmailVerifyEmailHtmlBody();
    }

    public static String getEmailVerifyEmailTextBody() {
        return appProperties.getEmailVerifyEmailTextBody();
    }

    public static String getEmailPasswordResetHtmlBody() {
        return appProperties.getEmailPasswordResetHtmlBody();
    }

    public static String getEmailPasswordResetTextBody() {
        return appProperties.getEmailPasswordResetTextBody();
    }

    public static String getEmailPasswordResetSubject() {
        return appProperties.getEmailPasswordResetSubject();
    }


}
