package org.planning.SpringBootProject.repository;

import org.springframework.data.domain.Page;
import org.planning.SpringBootProject.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByName(String name);

    @Query(value = "Select e from Product e Where e.code =:code")
    Product findProductByCode(@Param("code") String code);

    @Query(value = "Select e from Product e where e.isDelete = true")
    List<Product> getAllProducts();

    @Modifying
    @Query("UPDATE Product p SET p.isDelete = false, p.deleteDate = CURRENT_TIMESTAMP WHERE p.code = :code")
    void softDeleteProduct(@Param("code") String code);

    @Query(value = "SELECT p FROM Product p WHERE p.name LIKE %:likeName% AND p.isDelete = true")
    Page<Product> findByLikeNameAndIsDeletedTrue(@Param("likeName") String likeName, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM Product p WHERE p.name LIKE %:likeName%")
    int getTotalRecords(String likeName);

    @Query(value = "Select e from Product e Where e.code =:code")
    Product findProduct(String code);

    boolean existsByCode(String code);

    List<Product> findByNameContainingIgnoreCase(String keyword);

    @Modifying
    @Query("UPDATE Product p SET p.isDelete = true, p.updateDate = CURRENT_TIMESTAMP WHERE p.code = :code")
    void unlockProduct(@Param("code") String code);
}