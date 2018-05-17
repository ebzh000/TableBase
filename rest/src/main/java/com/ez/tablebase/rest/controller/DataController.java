package com.ez.tablebase.rest.controller;
/*

 * Created by ErikZ on 19/09/2017.
 */

import com.ez.tablebase.rest.model.dao.EntryDaoImpl;
import com.ez.tablebase.rest.model.requests.DataRequest;
import com.ez.tablebase.rest.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
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

    @GetMapping(value = "/entries")
    public Object getTableEntries(@PathVariable int tableId)
    {
        return dataService.getTableEntries(tableId);
    }

    @GetMapping(value = "/entry/{entryId}")
    public Object getTableEntry(@PathVariable int tableId, @PathVariable int entryId)
    {
        return dataService.getTableEntry(tableId, entryId);
    }

    @PostMapping(value = "/entry/{entryId}")
    public Object updateTableEntry(@PathVariable int tableId, @PathVariable int entryId, @RequestBody DataRequest request, @RequestParam("toHtml") boolean toHtml)
    {
        request.setTableId(tableId);
        request.setEntryId(entryId);
        EntryDaoImpl entry =  dataService.updateTableEntry(request);
        if(toHtml)
            return dataService.toHtml(tableId);
        else
            return entry;
    }
}
