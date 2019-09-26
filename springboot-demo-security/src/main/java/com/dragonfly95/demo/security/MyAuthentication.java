package com.dragonfly95.demo.security;

import com.dragonfly95.demo.dto.EnumLoginType;
import com.dragonfly95.demo.dto.UserDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class MyAuthentication extends UsernamePasswordAuthenticationToken {


  long userId;
  UserDto user;
  EnumLoginType loginType;

  public MyAuthentication(String id, String password, List<GrantedAuthority> grantedAuthorityList, UserDto user, EnumLoginType loginType, long userId) {
    super(id, password, grantedAuthorityList);
    this.user = user;
    this.loginType = loginType;
    this.userId = userId;
  }
}
