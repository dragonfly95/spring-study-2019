package com.dragonfly95.demo.security;

import com.dragonfly95.demo.util.Constant;
import com.dragonfly95.demo.dto.EnumLoginType;
import com.dragonfly95.demo.util.HashUtil;
import com.dragonfly95.demo.mvc.UserMapper;
import com.dragonfly95.demo.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class AuthProvider implements AuthenticationProvider {

  @Autowired
  UserMapper userMapper;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    String id = authentication.getName();
    String password = HashUtil.sha256(authentication.getCredentials().toString());
    UserDto user;

    if(authentication instanceof MyAuthentication) {
      return authentication;
    } else {
      user = userMapper.selectUser(UserDto.builder().email(id).build());

      // email에 맞는 user가 없거나 비밀번호가 맞지 않는 경우.
      if (null == user || !user.getPassword().equals(password)) {
        return null;
      }
    }

    List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

    if (user.isAdmin()) {
      grantedAuthorityList.add(new SimpleGrantedAuthority(Constant.ROLE_TYPE.ROLE_ADMIN.toString()));
    } else {
      grantedAuthorityList.add(new SimpleGrantedAuthority(Constant.ROLE_TYPE.ROLE_USER.toString()));
    }

    return new MyAuthentication(id, password, grantedAuthorityList, user, EnumLoginType.NORMAL, user.getUserId());
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return false;
  }
}
