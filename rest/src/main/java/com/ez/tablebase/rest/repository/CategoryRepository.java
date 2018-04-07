package com.ez.tablebase.rest.repository;

import com.ez.tablebase.rest.database.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer>
{
    @Query(value = "SELECT * FROM categories WHERE table_id = :tableId ORDER BY table_id, category_id ASC", nativeQuery = true)
    List<CategoryEntity> findAllTableCategories(@Param("tableId") int tableId);

    @Query(value = "SELECT * FROM categories WHERE table_id = :tableId AND category_id = :categoryId", nativeQuery = true)
    CategoryEntity findCategory(@Param("tableId") int tableId, @Param("categoryId") int categoryId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE categories SET attribute_name = :attributeName, parent_id = :parentId, type = :typeOrd WHERE table_id = :tableId AND category_id = :categoryId", nativeQuery = true)
    void updateTableCateogry(@Param("tableId") int tableId, @Param("categoryId") int categoryId, @Param("attributeName") String attributeName, @Param("parentId") Integer parentId, @Param("typeOrd") byte type);

    @Query(value = "SELECT c.table_id, c.category_id, c.attribute_name, c.parent_id, c.type FROM categories p LEFT JOIN categories c ON c.parent_id = p.category_id WHERE p.table_id = :tableId AND p.category_id = :categoryId", nativeQuery = true)
    List<CategoryEntity> findChildren(@Param("tableId") int tableId, @Param("categoryId") int categoryId);

    @Query(value = "select category_id from (select * from categories where table_id = :tableId order by parent_id, category_id) categories_sorted, (select @pv \\:= :categoryId) initialisation where find_in_set(parent_id, @pv) and length(@pv \\:= concat(@pv, ',', category_id))", nativeQuery = true)
    List<Integer> getAllChildren(@Param("tableId") int tableId, @Param("categoryId") int categoryId);
}
