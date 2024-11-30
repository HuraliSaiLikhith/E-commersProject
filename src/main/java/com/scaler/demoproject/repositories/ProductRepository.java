package com.scaler.demoproject.repositories;

import com.scaler.demoproject.model.Product;
import com.scaler.demoproject.repositories.projections.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product product);
    Product findByTitle(String title);
    Product findByDescription(String description);

    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p from Product p where p.category.id = :categoryId")
    List<Product> getProductsByCategoryId(@Param("categoryId")Long categoryId);

    @Query(value = "SELECT *  from Product p where p.category_id = :categoryId", nativeQuery = true)
    List<Product> getProductsByCategoryIdWithNativeQueries(@Param("categoryId")Long categoryId);

    @Query("SELECT p.title as title, p.id as id from Product p where p.category.id = :categoryId")
    List<ProductProjection> getProductsByCategoryIdProjection(@Param("categoryId")Long categoryId);





}
