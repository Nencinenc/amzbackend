package amzx.app.data.service;

import amzx.app.web.dto.user.UserRegisterRequest;
import amzx.app.web.dto.user.UserRegisterResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService
{

  UserRegisterResponse register(UserRegisterRequest userRegisterRequest);
}
