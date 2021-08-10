package fr.delpharm.esacp.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String REDACTEUR = "ROLE_REDACTEUR";

    public static final String PILOTE = "ROLE_PILOTE";

    public static final String PORTEUR = "ROLE_PORTEUR";

    public static final String HSE = "ROLE_HSE";

    public static final String V_HSE = "ROLE_V_HSE";

    private AuthoritiesConstants() {}
}
