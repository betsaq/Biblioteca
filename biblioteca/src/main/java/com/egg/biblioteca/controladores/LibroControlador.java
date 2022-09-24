package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.autor;
import com.egg.biblioteca.entidades.editorial;
import com.egg.biblioteca.entidades.libro;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.AutorServicio;
import com.egg.biblioteca.servicios.EditorialServicio;
import com.egg.biblioteca.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar") //localhost:8080/libro/registrar
    public String registrar(ModelMap modelo) {
        List<autor> autores = autorServicio.listarAutores();
        List<editorial> editoriales = editorialServicio.listarEditoriales();

        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long ISBN, @RequestParam String titulo,
            @RequestParam(required = false) Integer ejemplares, @RequestParam String idAutor,
            @RequestParam String idEditorial, ModelMap modelo) {
        try {
            libroServicio.crearLibro(ISBN, titulo, idAutor, ejemplares, idEditorial);

            modelo.put("exito", "El Libro fue cargado correctamente!");

        } catch (MiException ex) {
            List<autor> autores = autorServicio.listarAutores();
            List<editorial> editoriales = editorialServicio.listarEditoriales();

            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            modelo.put("error", ex.getMessage());

            return "libro_form.html";  // volvemos a cargar el formulario.
        }
        return "index.html";
    }
//    @PostMapping("/registro")
//    public String registro(@RequestParam(required = false) Long ISBN, @RequestParam String titulo,//el false es porque isbn y ejemplares puede estar nulo nulo
//            @RequestParam(required = false) Integer ejemplares, @RequestParam String idAutor,
//            @RequestParam String idEditorial, ModelMap modelo) throws MiException {
//        try {
//            libroServicio.crearLibro(ISBN, titulo, idAutor, ejemplares, idEditorial); // si todo sale bien retornamos al index.html                   
//            modelo.put("exito", "El Libro fue cargado exitosamente");
//        } catch (MiException ex) {
//            modelo.put("error", ex.getMessage());
//
//            List<autor> autores = autorServicio.listarAutores();
//            List<editorial> editoriales = editorialServicio.listarEditoriales();
//
//            modelo.addAttribute("autores", autores);
//            modelo.addAttribute("editoriales", editoriales);
//
//            return "libro_form.html"; // volvemos a cargar el formulario. 
//        }
//        return "index.html";
//    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<libro> libros = libroServicio.listarLibros();
        modelo.addAttribute("libros", libros);

        return "libro_list";
    }

}
