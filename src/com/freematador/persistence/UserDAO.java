package com.freematador.persistence;

import java.util.List;

import com.freematador.domain.User;


public interface UserDAO {

	public void insert(User Usuario);
	public void update(User Usuario);
	public void delete(Integer id);
	public User findById(Integer id);
	
	public List<User> findAll();
	public List<User> findByName(String name);
	
}
