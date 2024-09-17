package org.planning.SpringBootProject.repository;

import org.planning.SpringBootProject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}