package amzx.app.web.controller;

import amzx.app.data.entity.JwtToken;
import amzx.app.data.entity.User;
import amzx.app.data.service.JwtService;
import amzx.app.data.service.UserService;
import amzx.app.web.dto.user.UserLoginRequest;
import amzx.app.web.dto.user.UserLoginResponse;
import amzx.app.web.dto.user.UserRegisterRequest;
import amzx.app.web.dto.user.UserRegisterResponse;
import lombok.SneakyThrows;
import org.apache.http.auth.InvalidCredentialsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController
{

  private final UserService           userService;
  private final ModelMapper           modelMapper;
  private final AuthenticationManager authenticationManager;
  private final JwtService            jwtService;

  @Autowired
  public AuthenticationController(UserService userService, ModelMapper modelMapper, AuthenticationManager authenticationManager, JwtService jwtService)
  {
    this.userService = userService;
    this.modelMapper = modelMapper;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
  public UserRegisterResponse userRegister(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
    return this.userService.register(userRegisterRequest);
  }

  @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
  @SneakyThrows
  public ResponseEntity<?> userLogin(@Valid @RequestBody UserLoginRequest userLoginRequest) {
    Authentication authentication =
        this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                userLoginRequest.getEmail(),
                userLoginRequest.getPassword()
            )
        );

    if (!authentication.isAuthenticated()) {
      throw new InvalidCredentialsException();
    }

//        this.userService.isEmailConfirmed(userLoginServiceModel); FIXME: sending emails does not work

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwtToken = this.jwtService.generateJwtToken(authentication);
    User user = (User) authentication.getPrincipal();

    UserLoginResponse userLoginResponse = new UserLoginResponse();
    if(user != null) {
      userLoginResponse = this.modelMapper.map(user, UserLoginResponse.class);
    }

    return ResponseEntity.ok(new JwtToken(jwtToken, userLoginResponse));
  }
}
