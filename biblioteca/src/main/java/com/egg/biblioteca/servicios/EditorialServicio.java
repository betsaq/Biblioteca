package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.editorial;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio EditorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) throws MiException {
        validar(nombre);
        editorial editorial = new editorial();
        editorial.setNombre(nombre);
        EditorialRepositorio.save(editorial);

    }

    public List<editorial> listarEditoriales() {

        List<editorial> editoriales = new ArrayList();

        editoriales = EditorialRepositorio.findAll();

        return editoriales;
    }

    public void modificarEditorial(String id, String nombre) {

        Optional<editorial> respuesta = EditorialRepositorio.findById(id);

        if (respuesta.isPresent()) {
            editorial editorial = respuesta.get();

            editorial.setNombre(nombre);

            EditorialRepositorio.save(editorial);
        }

    }

    private void validar(String nombre) throws MiException {

        if (nombre == null || nombre.isEmpty() ) {
            throw new MiException("El nombre no puede estar vacio, ni puede ser nulo");
        }
    }
}
