package nl.felipenanes.geoloc.auth.internal.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import nl.felipenanes.geoloc.auth.internal.service.AuthService;
import nl.felipenanes.geoloc.auth.web.dto.AuthenticationRequest;
import nl.felipenanes.geoloc.auth.web.dto.AuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtServiceImpl jwtServiceImpl;

    @Override
    @SneakyThrows
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        
        UserDetails user = (UserDetails) auth.getPrincipal();
        String token = jwtServiceImpl.generateToken(user);
        
        return new AuthenticationResponse(token);
    }

}
