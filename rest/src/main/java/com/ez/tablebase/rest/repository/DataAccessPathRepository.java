package com.ez.tablebase.rest.repository;
/*

 * Created by ErikZ on 8/10/2017.
 */

import com.ez.tablebase.rest.database.DataAccessPathEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataAccessPathRepository extends JpaRepository<DataAccessPathEntity, String>
{
    @Query(value = "SELECT * FROM data_access_path WHERE table_id = :tableId AND entry_id = :entryId ORDER BY entry_id, tree_id, category_id ASC", nativeQuery = true)
    List<DataAccessPathEntity> getEntryAccessPath(@Param("tableId") int tableId, @Param("entryId") int entryId);

    @Query(value = "SELECT * FROM data_access_path WHERE table_id = :tableId AND entry_id = :entryId AND tree_id = :treeId ORDER BY entry_id, tree_id ASC", nativeQuery = true)
    List<DataAccessPathEntity> getEntryAccessPathByTree(@Param("tableId") int tableId, @Param("entryId") int entryId, @Param("treeId") int treeId);

    @Query(value = "select * from data_access_path where table_id = :tableId and entry_id = :entryId and tree_id != :treeId ORDER BY entry_id, tree_id, category_id", nativeQuery = true)
    List<DataAccessPathEntity> getEntryAccessPathExcludingTree(@Param("tableId") int tableId, @Param("entryId") int entryId, @Param("treeId") int treeId);

    @Query(value = "select category_id from data_access_path where entry_id = :entryId and table_id = :tableId ORDER BY entry_id, tree_id, category_id", nativeQuery = true)
    List<Integer> getDapCategoriesByEntry(@Param("tableId") int tableId, @Param("entryId") int entryId);

    @Query(value = "SELECT dap.entry_id FROM (SELECT b.id, b.table_id, b.entry_id, b.category_id FROM data_access_path a JOIN data_access_path b ON b.entry_id = a.entry_id AND a.table_id = :tableId AND b.table_id = :tableId AND a.category_id = :categoryId) AS dap GROUP BY dap.entry_id", nativeQuery = true)
    List<Integer> getEntriesForCategory(@Param("tableId") int tableId, @Param("categoryId") int categoryId);

    @Query(value = "SELECT entry_id FROM data_access_path WHERE table_id = :tableId AND category_id = :categoryId GROUP BY entry_id", nativeQuery = true)
    List<Integer> getEntryByPathContainingCategory(@Param("tableId") int tableId, @Param("categoryId") int cateogryId);

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM data_access_path WHERE table_id = :tableId AND entry_id = :entryId", nativeQuery = true)
    void deleteDAPByEntryId(@Param("tableId") int tableID, @Param("entryId") int entryId);
}
