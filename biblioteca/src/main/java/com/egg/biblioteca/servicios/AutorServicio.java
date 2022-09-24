package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.autor;
import com.egg.biblioteca.excepciones.MiException;

import com.egg.biblioteca.repositorios.AutorRepositorio;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio AutorRepositorio;

    @Transactional
    public void crearAutor(String nombre) throws MiException {
        validar(nombre);
        //     validarAutor(idAutor);
        autor autor = new autor();
        autor.setNombre(nombre);
        AutorRepositorio.save(autor);

    }

    @Transactional
    public List<autor> listarAutores() {
        List<autor> autores = new ArrayList();

        autores = AutorRepositorio.findAll();
        return autores;

    }

    @Transactional
    public void modificarAutor(String nombre, String id) throws MiException {
        validar(nombre);
        Optional<autor> respuesta = AutorRepositorio.findById(id);

        if (respuesta.isPresent()) {
            autor autor = respuesta.get();

            autor.setNombre(nombre);

            AutorRepositorio.save(autor);
        }
    }

    public autor getOne(String id) {
        return AutorRepositorio.getOne(id);
    }

    private void validar(String nombre) throws MiException {

        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("El nombre no puede estar vacio, ni puede ser nulo");
        }
    }
}
