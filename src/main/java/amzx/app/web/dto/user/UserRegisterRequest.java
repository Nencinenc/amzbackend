package amzx.app.web.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterRequest
{
  @Email
  @NotBlank
  private String email;

  @NotBlank
  private String password;
}
