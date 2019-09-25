package com.example.demo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Person implements Serializable {

	private static final long serialVersionUID = 8614282066880303854L;

	private int id;
	private String firstName;
	private String lastName;
	private String streetName;
	private String city;
	private String state;
	private String country;

	public Person() {}

	public Person(String firstName, String lastName, String street, String city, String state, String country) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.streetName = street;
		this.city = city;
		this.state = state;
		this.country = country;
	}
}
