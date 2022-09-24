
package com.egg.biblioteca.repositorios;

import com.egg.biblioteca.entidades.editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<editorial, String> {

}
