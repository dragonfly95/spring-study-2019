package com.dragonfly95.clickme;

public class Enumtest {

  enum City {Seoul, Busan, Daegu, Daejun}


  public static void main(String[] args) {
    City city = City.Busan;
    System.out.println(City.valueOf("Seoul"));
  }
}
