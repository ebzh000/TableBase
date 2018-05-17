package com.ez.tablebase.rest.service;
/*

 * Created by ErikZ on 27/11/2017.
 */

import com.ez.tablebase.rest.model.dao.EntryDaoImpl;
import com.ez.tablebase.rest.model.requests.DataRequest;

import java.util.List;

public interface DataService
{
    List<EntryDaoImpl> getTableEntries(int tableId);

    EntryDaoImpl getTableEntry(int tableId, int entryId);

    EntryDaoImpl updateTableEntry(DataRequest request);

    String toHtml(int tableId);
}
