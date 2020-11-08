package pl.szymanski.sharelibrary.security;

public class Constants {

    public static final String RESPONSE_WITH_UNAUTH_ERROR = "Responding with unauthorized error. Message - {}";
    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER_TOKEN_BEGIN = "Bearer ";
    public static final String HOME_URL = "http://localhost:8081";

    public static String[] getUnprotectedEndpoints() {
        return new String[]{
                "/api/v1/users/register",
                "/api/v1/login",
                "/console/**",
                "/swagger-ui.html"
        };
    }

}
