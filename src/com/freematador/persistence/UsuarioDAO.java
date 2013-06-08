package com.freematador.persistence;

import java.util.List;

import com.freematador.domain.Usuario;


public interface UsuarioDAO {

	public void insert(Usuario Usuario);
	public void update(Usuario Usuario);
	public void delete(Integer id);
	public Usuario findById(Integer id);
	
	public List<Usuario> findAll();
	public List<Usuario> findByName(String name);
	
}
