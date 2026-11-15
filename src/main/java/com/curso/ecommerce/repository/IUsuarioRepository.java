package com.curso.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.curso.ecommerce.model.Usuario;

// @Repository es innecesario porque JpaRepository ya tiene la anotación @Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer>{

}
