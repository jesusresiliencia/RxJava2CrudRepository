package com.rxjava.springboot.apirest.app.repository;

import org.springframework.data.repository.reactive.RxJava2CrudRepository;
import com.rxjava.springboot.apirest.app.models.entity.Producto;

public interface ProductoRepository extends RxJava2CrudRepository<Producto, String>  {


}
