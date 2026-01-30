package nl.felipenanes.geoloc.auth.internal.security.providers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
class AuthenticationProviderBundleConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    @Primary
    AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    // TODO: Future improvement - OAuth2 Authentication Provider
    // Uncomment to enable social login (Google, GitHub, etc.)
    /*
    @Bean
    AuthenticationProvider oauth2AuthenticationProvider() {
        // OAuth2AuthenticationProvider for social login integration
        // Requires dependencies: spring-boot-starter-oauth2-client
        // Configuration in application.properties:
        // spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID
        // spring.security.oauth2.client.registration.google.client-secret=YOUR_SECRET
        // spring.security.oauth2.client.registration.google.scope=profile,email
        
        // Custom implementation example:
        return new OAuth2AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) {
                // Validate OAuth2 token and create authenticated user
                // Extract user info from OAuth2 provider (Google, GitHub, etc.)
                return authentication;
            }
            
            @Override
            public boolean supports(Class<?> authentication) {
                return OAuth2AuthenticationToken.class.isAssignableFrom(authentication);
            }
        };
    }
    */

    // TODO: Future improvement - LDAP Authentication Provider
    // Uncomment to enable Active Directory/LDAP authentication
    /*
    @Bean
    AuthenticationProvider ldapAuthenticationProvider() {
        // LdapAuthenticationProvider for corporate AD integration
        // Requires dependencies: spring-ldap-core, spring-security-ldap
        // Configuration in application.properties:
        // spring.ldap.urls=ldap://localhost:389
        // spring.ldap.base=dc=example,dc=com
        // spring.ldap.username=cn=admin,dc=example,dc=com
        // spring.ldap.password=admin
        
        LdapAuthenticationProvider provider = new LdapAuthenticationProvider(
            ldapBindAuthenticator(),
            ldapAuthoritiesPopulator()
        );
        provider.setUserDetailsContextMapper(new InetOrgPersonContextMapper());
        return provider;
    }
    
    private LdapAuthenticator ldapBindAuthenticator() {
        BindAuthenticator authenticator = new BindAuthenticator(contextSource());
        authenticator.setUserDnPatterns(new String[]{"uid={0},ou=people"});
        return authenticator;
    }
    
    private LdapAuthoritiesPopulator ldapAuthoritiesPopulator() {
        DefaultLdapAuthoritiesPopulator populator = new DefaultLdapAuthoritiesPopulator(
            contextSource(), 
            "ou=groups"
        );
        populator.setGroupRoleAttribute("cn");
        return populator;
    }
    
    private ContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("ldap://localhost:389");
        contextSource.setBase("dc=example,dc=com");
        contextSource.setUserDn("cn=admin,dc=example,dc=com");
        contextSource.setPassword("admin");
        return contextSource;
    }
    */

}
