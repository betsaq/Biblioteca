package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.autor;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.AutorServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor") //localhost:8080/autor
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/registrar") //localhost:8080/autor/registrar
    public String registrar() {
        return "autor_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) throws MiException {

        try {
            autorServicio.crearAutor(nombre);
            modelo.put("exito", "El autor no puede ser nulo");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "autor_form.html";
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<autor> autores = autorServicio.listarAutores();
        modelo.addAttribute("autores", autores);

        return "autor_list";
    }

    //GetMapping para renderizar los métodos que enviamos por path
    @GetMapping("/modificar/{id}") //con el recurso path enviamos por la url el recurso id del autor a modificar
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("autor", autorServicio.getOne(id));
        return "autor_modificar.html";
    }

    //   PostMapping para recibir los datos del formulario 
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo) {
        try {

            autorServicio.modificarAutor(nombre, id); //si algo no sale bien retornamos nuevamente el formulario modificar
            return "redirect:../lista"; //redireccionamos a autor/lista luego de haber modificado el autor

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "autor_modificar.html";
            //si algo no sale bien retornamos nuevamente el formulario modificar
        }

    }

}
