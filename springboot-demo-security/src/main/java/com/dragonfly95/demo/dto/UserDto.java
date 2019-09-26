package com.dragonfly95.demo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = {"password"})
public class UserDto {

  private long userId;

  private String email;
  private String password;

  private boolean isAdmin = false;

  @Builder
  public UserDto(String email) {
    this.email = email;
  }
}
