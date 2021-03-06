package com.ez.tablebase.rest.service;
/*

 * Created by ErikZ on 27/11/2017.
 */

import com.ez.tablebase.rest.model.Entry;
import com.ez.tablebase.rest.model.requests.DataRequest;

import java.util.List;

public interface DataService
{
    List<Entry> getTableEntries(int tableId);

    Entry getTableEntry(int tableId, int entryId);

    Entry updateTableEntry(DataRequest request);

    String toHtml(int tableId);
}
