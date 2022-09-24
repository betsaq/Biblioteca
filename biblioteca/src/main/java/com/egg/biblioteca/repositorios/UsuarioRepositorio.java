package com.egg.biblioteca.repositorios;

import com.egg.biblioteca.entidades.usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<usuario, String> {

    @Query("SELECT u FROM usuario u WHERE u.email = :email")
    public usuario buscarPorEmail(@Param("email")String email);
}