package amzx.app.web.security;

import amzx.app.data.service.JwtService;
import amzx.app.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationJwtTokenFilter extends OncePerRequestFilter
{

  private final JwtService  jwtService;
  private final UserService userService;

  @Autowired
  public AuthenticationJwtTokenFilter(JwtService jwtService, UserService userService)
  {
    this.jwtService = jwtService;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      FilterChain filterChain

  ) throws ServletException, IOException
  {

    String jwtToken = parseJwt(httpServletRequest);

    if (null != jwtToken && this.jwtService.validateJwtToken(jwtToken)) {

      String email = this.jwtService.getEmailFromJwtToken(jwtToken);
      UserDetails userDetails = this.userService.loadUserByUsername(email);

      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());

      authentication.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

      SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }

  private String parseJwt(HttpServletRequest httpServletRequest) {
    String authorizationHeader = httpServletRequest.getHeader("Authorization");

    if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
      return authorizationHeader.substring(7);
    }
    return null;
  }
}
