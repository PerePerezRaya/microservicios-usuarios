package com.formacionbdi.microservicios.app.usuarios.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.microservicios.app.usuarios.models.entity.Alumno;
import com.formacionbdi.microservicios.app.usuarios.models.services.AlumnoService;

@RestController
public class AlumnoController {
	
	@Autowired
	private AlumnoService alumnoService;
	
	/*
	 * Este metodo, devuelve un ResponeEntity por que es una lista de Alumnos, que son entidades.
	 * 
	 * Al no poner ninguna ruta, cogerá la raiz.
	 * 
	 * ok() devuelve un 200
	 * */
	@GetMapping
	public ResponseEntity<?> listar(){
		return ResponseEntity.ok().body(alumnoService.findAll());
	}
	
	/*
	 * Este metodo requiere el parametro id
	 * 
	 * @PathVariable => Sirve para que coja el parametro automaticamente de la url
	 * 
	 * ResponseEntity.notFound().build() => Construye una respuesta de not found
	 * */
	@GetMapping("/{id}")
	public ResponseEntity<?> ver(@PathVariable Long id){
		Optional<Alumno> o = alumnoService.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}		
		return ResponseEntity.ok(o.get());
	}

	/*
	 * Este metodo va a recibir por el request body un Alumno (Json) que utilizará para hacer el insert
	 * */
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Alumno alumno){
		Alumno alumnoDb = alumnoService.save(alumno);	
		return ResponseEntity.status(HttpStatus.CREATED).body(alumnoDb);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@RequestBody Alumno alumno, @PathVariable Long id){
		Optional<Alumno> o = alumnoService.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Alumno alumnoDb = o.get();
		alumnoDb.setNombre(alumno.getNombre());
		alumnoDb.setApellido(alumno.getApellido());
		alumnoDb.setEmail(alumno.getEmail());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(alumnoService.save(alumnoDb));
	}
	
	/*
	 * En este caso, el metodo deleteById() devuelve void, así que en el return devolvemos un noContent() 204
	 * */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		Optional<Alumno> o = alumnoService.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		alumnoService.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
}