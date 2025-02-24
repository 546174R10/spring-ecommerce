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
	LOGGER.info("Objeto de la vista {}",producto);
	productoService.save(producto); 
	Usuario u= new Usuario (1,"","","","","","","");
	producto.setUsuario(u);
	
	//imagen
	if (producto.getId()==null) { //validacion cuando se crea un producto
	String nombreImagen= upload.saveImage(file);	
	producto.setImagen(nombreImagen);
	}else {
		if (file.isEmpty()) { //Es cuando editamos el producto pero no cambiamos la imagen
			Producto p= new Producto();
			p=productoService.get(producto.getId()).get();
			producto.setImagen(p.getImagen());
		}else {
	String nombreImagen= upload.saveImage(file);	
	producto.setImagen(nombreImagen);
		}
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
	public  String update(Producto productro) {
		productoService.update(productro);
		return "redirect:/productos";
	}
	//Agregamos mapeo para el direccionamiento
	@GetMapping( "/delete/{id}")
	//creamos un nuevo metodo publico con mapeo 
   public String delete(@PathVariable Integer id) {
	   productoService.delete(id);
	   return"redirect:/productos";
   }   

}
