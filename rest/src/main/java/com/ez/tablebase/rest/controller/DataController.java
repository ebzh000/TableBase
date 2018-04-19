package com.ez.tablebase.rest.controller;
/*

 * Created by ErikZ on 19/09/2017.
 */

import com.ez.tablebase.rest.model.Entry;
import com.ez.tablebase.rest.model.requests.DataRequest;
import com.ez.tablebase.rest.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tablebase/table/{tableId}")
public class DataController
{
    private DataService dataService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public DataController(DataService dataService)
    {
        this.dataService = dataService;
    }

    @PostMapping(value = "/entry/create")
    public Entry createTableEntry(@PathVariable int tableId, DataRequest request)
    {
        request.setTableId(tableId);
        return dataService.createTableEntry(request);
    }

    @GetMapping(value = "/entries")
    public List<Entry> getTableEntries(@PathVariable int tableId)
    {
        return dataService.getTableEntries(tableId);
    }

    @GetMapping(value = "/entry/{entryId}")
    public Entry getTableEntry(@PathVariable int tableId, @PathVariable int entryId)
    {
        return dataService.getTableEntry(tableId, entryId);
    }

    @PostMapping(value = "/entry/{entryId}")
    public Entry updateTableEntry(@PathVariable int tableId, @PathVariable int entryId, @RequestBody DataRequest request)
    {
        request.setTableId(tableId);
        request.setEntryId(entryId);
        return dataService.updateTableEntry(request);
    }
}
