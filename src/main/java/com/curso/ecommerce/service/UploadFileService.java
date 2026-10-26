package com.curso.ecommerce.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
//creamos nuestra clase
public class UploadFileService {

	//creamos nuestro metodo para el guardado de imagenes 
	private String folder="images//";

	//nuevo metodo recibe un string
public String saveImage(MultipartFile file) throws IOException {
	File directory =new File (folder); 
	  if (!directory.exists()) {
	        directory.mkdirs(); // Crea la carpeta si en dado caso no existiera
	    }
	    if (!file.isEmpty()) {
	        byte[] bytes = file.getBytes(); //conversion de imagen a bytes    
	        Path path = Paths.get(folder + file.getOriginalFilename());
	        Files.write(path, bytes); //ruta path
	        return file.getOriginalFilename();
	    }
	return "default.jpg";
} 
public void deleteImage(String nombre) {
	String ruta="images//";
	File file= new File(ruta+nombre);
	file.delete();
}
}
