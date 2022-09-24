package com.egg.biblioteca.repositorios;

import com.egg.biblioteca.entidades.autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<autor, String> {

 //public Object findBy(String IdAutor);

}
