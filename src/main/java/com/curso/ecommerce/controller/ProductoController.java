package com.curso.ecommerce.controller;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.ProductoService;
import com.curso.ecommerce.service.UploadFileService;

import org.springframework.ui.Model;


@Controller
@RequestMapping("/productos")
public class ProductoController {

	private final Logger LOGGER =LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoService productoService;

	//creacion de objeto upload para guardar imagen y llamar la clase anterior
	@Autowired
	private UploadFileService upload;
	
	@GetMapping("")
	public String show(Model model) {
	  model.addAttribute("productos",productoService.findAll());
		return "productos/show";
}
	@GetMapping("/create")
public String create() {
	return "productos/create";
}
	@PostMapping("/save")  
	public String save (Producto producto, @RequestParam("img") MultipartFile file ) throws IOException {
	LOGGER.info("Este es el Objeto producto {}",producto);
//	productoService.save(producto); 
	Usuario u= new Usuario (1,"","","","","","","");
	producto.setUsuario(u);
	
	// logica guardado de imagen
	if (producto.getId()==null) { //validacxion cuando se crea una imagen
	String nombreImagen= !file.isEmpty() ? upload.saveImage(file):"default.jpg";	
	producto.setImagen(nombreImagen);
	}else { 
  
}		
	productoService.save(producto);		
	return"redirect:/productos";

	}
    
	//redireccionamiento para editar un producto// 
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
	   Producto producto= new Producto();
		Optional<Producto> optionalProducto=productoService.get(id);
		producto= optionalProducto.get();
		
		LOGGER.info("producto buscado:{}",producto );
		model.addAttribute("producto", producto);
		
		return "productos/edit";
	}
	@PostMapping("/update")
	public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
		Producto p=new Producto();
        p = productoService.get(producto.getId()).get();
		//
		
		if (file.isEmpty()) { //Editamos el producto pero no cambiamos la imagen 

	        String imagenExistente = p.getImagen();
	        producto.setImagen(imagenExistente != null ? imagenExistente : "default.jpg");
	    } else {  //cuando se edita tambien la imagen
	        
	        
	        if (p.getImagen() != null && !p.getImagen().equals("default.jpg")) {
	            upload.deleteImage(p.getImagen());
	        }
	        String nombreImagen = upload.saveImage(file);    
	        producto.setImagen(nombreImagen);
	    }
	    producto.setUsuario(p.getUsuario());
	    productoService.update(producto);
	    return "redirect:/productos";
	}
	
	

	//Agregamos mapeo para el direccionamiento	
	@GetMapping( "/delete/{id}")
	//creamos un nuevo metodo publico con mapeo  
	public String delete(@PathVariable Integer id) {
	    
		//creacion de variable tipo producto
		Producto p = new Producto();
	    p=productoService.get(id).get();
	    
	    // Eliminar cuando  no sea la imagen por defecto
	    if (p.getImagen() != null && !p.getImagen().equals("default.jpg")) {
	        upload.deleteImage(p.getImagen());
	    }
	    
	    productoService.delete(id) ;
	    return "redirect:/productos";
	}
}
