package com.curso.ecommerce.service;

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
	if(!file.isEmpty()) {
	byte[] bytes=file.getBytes();	
	Path path =Paths.get(folder+file.getOriginalFilename());
	Files.write(path, bytes)
	return
	}
	return " ";
}
}
