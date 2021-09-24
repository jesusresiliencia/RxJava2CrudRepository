package com.rxjava.springboot.apirest.app.services;

import com.rxjava.springboot.apirest.app.models.entity.Producto;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface ProductoService {
	
	public Flowable<Producto> findAll();
	
	public Maybe<Producto> findById(String id);
	
	public Single<Producto> save(Producto producto);
	
	public Completable delete(Producto producto);
	
}
