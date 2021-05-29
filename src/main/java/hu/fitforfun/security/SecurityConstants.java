package hu.fitforfun.security;

public class SecurityConstants {
    public static final Long EXPIRATION_TIME = 86400000L; // 1 day
    public static final Long PASSWORD_RESET_EXPIRATION_TIME = 3600000L; // 1 hour
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/login";
    public static final String TOKEN_SECRET = "jf9i4jgu83nfa2";
    public static final String VERIFICATION_EMAIL_URL = "/users/email-verification";
    public static final String PASSWORD_RESET_REQUEST_URL ="/users/password-reset-request";
    public static final String PASSWORD_RESET_URL ="/users/password-reset";
}
