package amzx.app.data.service;

import org.springframework.security.core.Authentication;

public interface JwtService
{
  String generateJwtToken(Authentication authentication);

  boolean validateJwtToken(String token);

  String getEmailFromJwtToken(String token);
}
