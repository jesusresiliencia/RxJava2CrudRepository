package com.rxjava.springboot.apirest.app.controllers;

import java.net.URI;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rxjava.springboot.apirest.app.models.entity.Producto;
import com.rxjava.springboot.apirest.app.services.ProductoService;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@RestController
@RequestMapping("/api")
public class ProductoController {

	private ProductoService service;
	
	public ProductoController(ProductoService injectedService) {
		this.service=injectedService;
	}
	
	@GetMapping("/listar")
	public ResponseEntity<Flowable<Producto>> listar(){
		return ResponseEntity
		.ok()
		.contentType(MediaType.APPLICATION_JSON_UTF8)
		.body(service.findAll());
	}
	
	@GetMapping("/buscar/{id}")
	public Maybe<ResponseEntity<Producto>>findById(@PathVariable String id){
		
		 return service.findById(id).map(db->ResponseEntity
				 					.ok()
				 					.contentType(MediaType.APPLICATION_JSON_UTF8)
				 					.body(db)).defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/guardar")
	public Single<ResponseEntity<Producto>>save(@RequestBody Producto body){
		return service.save(body).map(db->ResponseEntity
									.created(URI.create("/api/buscar/".concat(db.getId())))
									.contentType(MediaType.APPLICATION_JSON_UTF8)
									.body(db)).doOnError(e->ResponseEntity.badRequest().build());
	}
	
	@PutMapping("/editar/{id}")
	public Single<ResponseEntity<Producto>>modify(@PathVariable String id,@RequestBody Producto body){
		
		/*Maybe<Producto>productoDB=service.findById(id).map(db->{
			db.setNombre(body.getNombre());
			db.setPrecio(body.getPrecio());
			return db; });*/
		
		
		//return productoDB.flatMapSingle(pdb->service.save(pdb));

		return service.findById(id).flatMapSingle (db->{
			db.setNombre(body.getNombre());
			db.setPrecio(body.getPrecio());
			return service.save(db); }).map(p->ResponseEntity.created(URI.create("/api/productos/".concat(p.getId())))
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.body(p));
	}
	
	@DeleteMapping("/eliminar/{id}")
	public Completable delete(@PathVariable String id) {
		return service.findById(id).flatMapCompletable(db->service.delete(db));
	}
	
}
