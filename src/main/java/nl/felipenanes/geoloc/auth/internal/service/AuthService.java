package nl.felipenanes.geoloc.auth.internal.service;

import nl.felipenanes.geoloc.auth.web.dto.AuthenticationRequest;
import nl.felipenanes.geoloc.auth.web.dto.AuthenticationResponse;

public interface AuthService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
