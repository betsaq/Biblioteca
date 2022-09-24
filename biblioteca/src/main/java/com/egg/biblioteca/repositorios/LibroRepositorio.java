package com.egg.biblioteca.repositorios;

import com.egg.biblioteca.entidades.libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<libro, Long> {

    @Query("SELECT l FROM libro l WHERE l.titulo = :titulo")
    public libro buscarPorTitulo(@Param("titulo") String titulo);

    @Query("SELECT l FROM libro l WHERE l.autor.nombre= :nombre")
    public List<libro> buscarPorAutor(@Param("nombre") String nombre);

}
