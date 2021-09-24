package com.rxjava.springboot.apirest.app.services.impl;

import org.springframework.stereotype.Service;

import com.rxjava.springboot.apirest.app.models.entity.Producto;
import com.rxjava.springboot.apirest.app.repository.ProductoRepository;
import com.rxjava.springboot.apirest.app.services.ProductoService;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Service
public class ProductoServiceImpl implements ProductoService {

	ProductoRepository repository;
	
	public ProductoServiceImpl(ProductoRepository injectedRepository) {
		this.repository=injectedRepository;
	}
	
	
	@Override
	public Flowable<Producto> findAll() {
		return repository.findAll();
	}

	@Override
	public Maybe<Producto> findById(String id) {
		return repository.findById(id);
	}

	@Override
	public Single<Producto> save(Producto producto) {
		return repository.save(producto);
	}

	@Override
	public Completable delete(Producto producto) {
		return repository.delete(producto);
	}

}
