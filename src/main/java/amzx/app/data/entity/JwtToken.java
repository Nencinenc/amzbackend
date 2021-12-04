package amzx.app.data.entity;

import amzx.app.web.dto.user.UserLoginResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtToken
{

  private static final String TOKEN_TYPE = "Bearer ";

  private String            token;
  private String            type;
  private UserLoginResponse userLoginResponse;

  public JwtToken(String token, UserLoginResponse userLoginResponse)
  {
    this.token = token;
    this.type = TOKEN_TYPE;
    this.userLoginResponse = userLoginResponse;
  }
}
