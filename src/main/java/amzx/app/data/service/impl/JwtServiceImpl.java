package amzx.app.data.service.impl;

import amzx.app.data.entity.User;
import amzx.app.data.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtServiceImpl implements JwtService
{
  public static final String jwtSecret = "secretkey"; // TODO: izkarai gi v application.properties
  public static final long jwtExpiration = 180000000;

  public String generateJwtToken(Authentication authentication) {
    User userPrincipal = (User) authentication.getPrincipal();

    return Jwts.builder()
        .setSubject(userPrincipal.getEmail())
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public boolean validateJwtToken(String token) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return false;
  }

  public String getEmailFromJwtToken(String token) {
    return Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }
}
