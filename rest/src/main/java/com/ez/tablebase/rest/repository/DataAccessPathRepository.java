package com.ez.tablebase.rest.repository;
/*

 * Created by ErikZ on 8/10/2017.
 */

import com.ez.tablebase.rest.database.DataAccessPathEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DataAccessPathRepository extends PagingAndSortingRepository<DataAccessPathEntity, String>
{
    @Query(value = "SELECT * FROM data_access_path WHERE table_id = :tableId AND entry_id = :entryId ORDER BY id ASC", nativeQuery = true)
    List<DataAccessPathEntity> getEntryAccessPath(@Param("tableId") int tableId, @Param("entryId") int entryId);

    @Query(value = "SELECT dap.entry_id FROM (SELECT b.id, b.table_id, b.entry_id, b.category_id FROM data_access_path a JOIN data_access_path b ON b.entry_id = a.entry_id AND a.table_id = :tableId AND b.table_id = :tableId AND a.category_id = :categoryId) AS dap GROUP BY dap.entry_id", nativeQuery = true)
    List<Integer> getEntriesForCategory(@Param("tableId") int tableId, @Param("categoryId") int categoryId);
}
