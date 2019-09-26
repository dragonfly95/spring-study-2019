package com.dragonfly95.demo.dto;

public enum EnumLoginType {
  NORMAL(0),
  FACE_BOOK(1);

  int flag;

  EnumLoginType(int flag) {
    this.flag = flag;
  }

  public int getFlag() {
    return flag;
  }
}
