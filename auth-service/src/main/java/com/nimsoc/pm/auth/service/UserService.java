package com.nimsoc.pm.auth.service;


import java.util.Optional;

import com.nimsoc.pm.auth.model.User;
import com.nimsoc.pm.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
