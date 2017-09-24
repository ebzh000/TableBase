package com.ez.tablebase.rest.repository;

import com.ez.tablebase.rest.database.CategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, String>
{
    @Query(value = "SELECT * FROM categories WHERE table_id = :tableId", nativeQuery = true)
    List<CategoryEntity> findAllTableCategories(@Param("tableId") int tableId);

    @Query(value = "SELECT * FROM categories WHERE table_id = :tableId AND category_id = :categoryId", nativeQuery = true)
    CategoryEntity findCategory(@Param("tableId") int tableId, @Param("categoryId") int categoryId);
}
