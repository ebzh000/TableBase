package com.ez.tablebase.rest.repository;
/*

 * Created by erikz on 15/09/2017.
 */

import com.ez.tablebase.rest.database.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TableRepository extends JpaRepository<TableEntity, String>
{

    @Query(value = "SELECT * FROM table_list WHERE table_id = :tableId", nativeQuery = true)
    TableEntity findTable(@Param("tableId") int tableId);

    @Query(value = "SELECT * FROM table_list t WHERE t.table_name LIKE %:keyword% OR t.tags LIKE %:keyword%", nativeQuery = true)
    List<TableEntity> searchTable(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM table_list t where t.user_id = :userId", nativeQuery = true)
    List<TableEntity> getUserTables(@Param("userId") int userId);
}
