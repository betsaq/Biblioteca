package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.autor;
import com.egg.biblioteca.entidades.editorial;
import com.egg.biblioteca.entidades.libro;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import com.egg.biblioteca.repositorios.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio LibroRepositorio;

    @Autowired
    private AutorRepositorio AutorRepositorio;

    @Autowired
    private EditorialRepositorio EditorialRepositorio;

    @Transactional
    public void crearLibro(Long ISBN, String titulo, String idAutor, Integer ejemplares, String idEditorial) throws MiException {

        validar(ISBN, titulo, idAutor, ejemplares, idEditorial);

        Optional<libro> respuesta = LibroRepositorio.findById(ISBN);
        Optional<autor> respuestaAutor = AutorRepositorio.findById(idAutor);
        Optional<editorial> respuestaEditorial = EditorialRepositorio.findById(idEditorial);

//        autor autor = AutorRepositorio.findById(idAutor).get();
//        editorial editorial = EditorialRepositorio.findById(idAutor).get();
        autor autor = new autor();
        editorial editorial = new editorial();

        if (respuestaAutor.isPresent()) {

            autor = respuestaAutor.get();
        }

        if (respuestaEditorial.isPresent()) {

            editorial = respuestaEditorial.get();
        }

        libro libro = new libro();

        libro.setISBN(ISBN);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());

        libro.setAutor(autor);
        libro.setEditorial(editorial);

        LibroRepositorio.save(libro);

    }

    public List<libro> listarLibros() {
        List<libro> libros = new ArrayList();
        libros = LibroRepositorio.findAll();

        return libros;
    }

    public void modificarLibro(Long ISBN, String titulo, String IdAutor, String IdEditorial, Integer ejemplares) throws MiException {
        validar(ISBN, titulo, IdAutor, ejemplares, IdEditorial);

        Optional<libro> respuesta = LibroRepositorio.findById(ISBN);
        Optional<autor> respuestaAutor = AutorRepositorio.findById(IdAutor);
        Optional<editorial> respuestaEditorial = EditorialRepositorio.findById(IdEditorial);

        autor autor = new autor();
        editorial editorial = new editorial();

        if (respuestaAutor.isPresent()) {
            autor = respuestaAutor.get();
        }

        if (respuestaEditorial.isPresent()) {
            editorial = respuestaEditorial.get();
        }

        if (respuesta.isPresent()) {
            libro libro = respuesta.get();

            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setEjemplares(ejemplares);
            LibroRepositorio.save(libro);

        }

    }

    private void validar(Long ISBN, String titulo, String IdAutor, Integer ejemplares, String IdEditorial) throws MiException {

        if (ISBN == null) {
            throw new MiException("El ISBN no puede ser nulo");
        }

        if (titulo == null || titulo.isEmpty()) {
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }

        if (ejemplares == null) {
            throw new MiException("Ejemplares no puede ser nulo");
        }

        if (IdAutor == null || IdAutor.isEmpty()) {
            throw new MiException("El Id del Autor no puede ser nulo o estar vacio");
        }

        if (IdEditorial == null || IdEditorial.isEmpty()) {
            throw new MiException("El Id de la editorial no puede ser nulo o estar vacio");
        }

    }
}
