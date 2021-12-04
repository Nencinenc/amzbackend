package amzx.app.data.service.impl;

import amzx.app.data.entity.User;
import amzx.app.data.repository.UserRepository;
import amzx.app.data.service.UserService;
import amzx.app.exception.exceptions.UserExistException;
import amzx.app.web.dto.user.UserRegisterRequest;
import amzx.app.web.dto.user.UserRegisterResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService
{

  private final UserRepository        userRepository;
  private final ModelMapper           modelMapper;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      ModelMapper modelMapper,
      BCryptPasswordEncoder bCryptPasswordEncoder
  )
  {

    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
  {
    return this.userRepository.findByEmail(username).orElse(null);
  }

  @Override
  public UserRegisterResponse register(UserRegisterRequest userRegisterRequest)
  {
    Optional<User> userToFind = userRepository.findByEmail(userRegisterRequest.getEmail());
    if (userToFind.isPresent()) {
      throw new UserExistException("user exists with such username");
    }
    User user = this.modelMapper.map(userRegisterRequest, User.class);
    user.setPassword(this.bCryptPasswordEncoder.encode(userRegisterRequest.getPassword()));
    this.userRepository.save(user);
    return this.modelMapper.map(user, UserRegisterResponse.class);
  }
}
