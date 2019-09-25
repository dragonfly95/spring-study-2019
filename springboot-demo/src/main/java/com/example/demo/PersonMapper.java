package com.example.demo;

import java.util.List;

public interface PersonMapper {
	public int insertUser(Person person);
//	public int updateUser(Person person);
//	public void deletePerson(int personID);
	public List<Person> selectAllPerson();
}
