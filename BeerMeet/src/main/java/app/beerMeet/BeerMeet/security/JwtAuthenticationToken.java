package app.beerMeet.BeerMeet.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final UserDetails principal;

    public JwtAuthenticationToken(UserDetails principal) {
        super(principal.getAuthorities());
        this.principal = principal;
        setAuthenticated(true);  // Marcar como autenticado
    }

    @Override
    public Object getCredentials() {
        return null;  // No necesitamos almacenar credenciales aquí
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}