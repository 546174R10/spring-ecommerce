package com.curso.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.curso.ecommerce.model.Producto;

public interface ProductoService {
 
//metodos de productos
	public Producto save(Producto producto);
    public Optional<Producto> get(Integer id);
public void update(Producto producto);
public void delete (Integer id);
//creacion de lista para productos
public List<Producto>findAll();

}
