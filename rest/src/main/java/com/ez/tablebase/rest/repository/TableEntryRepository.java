package com.ez.tablebase.rest.repository;
/*

 * Created by ErikZ on 19/09/2017.
 */

import com.ez.tablebase.rest.database.EntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableEntryRepository extends JpaRepository<EntryEntity, String>
{
    @Query(value = "SELECT * FROM table_data WHERE table_id = :tableId ORDER BY table_id, entry_id ASC", nativeQuery = true)
    List<EntryEntity> findAllTableEntries(@Param("tableId") int tableId);

    @Query(value = "SELECT * FROM table_data WHERE table_id = :tableId AND entry_id = :entryId", nativeQuery = true)
    EntryEntity findTableEntry(@Param("tableId") int tableId, @Param("entryId") int entryId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE EntryEntity SET data = :label WHERE table_id = :tableId AND entry_id = :entryId")
    void updateTableEntry(@Param("tableId") int tableId, @Param("entryId") int entryId, @Param("label") String data);

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM table_data WHERE table_id = :tableId and entry_id = :entryId", nativeQuery = true)
    void deleteTableEntry(@Param("tableId") int tableId, @Param("entryId") int entryId);
}
