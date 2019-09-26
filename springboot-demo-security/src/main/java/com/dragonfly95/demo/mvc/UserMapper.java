package com.dragonfly95.demo.mvc;

import com.dragonfly95.demo.dto.UserDto;

public interface UserMapper {
  public UserDto selectUser(UserDto userDto);
}
