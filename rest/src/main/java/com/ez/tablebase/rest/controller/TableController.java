package com.ez.tablebase.rest.controller;

import com.ez.tablebase.rest.model.requests.TableRequest;
import com.ez.tablebase.rest.service.TableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Erik Zhong on 9/6/2017.
 */

@RestController
@RequestMapping(value = "/tablebase")
public class TableController
{
    private TableService tableService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public TableController(TableService tableService)
    {
        this.tableService = tableService;
    }

    @PostMapping(value = "/create")
    Object createTable(@RequestBody TableRequest request)
    {
        return tableService.createTable(request);
    }

    @GetMapping(value = "/tables")
    Object getTables()
    {
        return tableService.getTables();
    }

    @GetMapping(value = "/table/{tableId}")
    Object getTable(@PathVariable int tableId)
    {
        return tableService.getTable(tableId);
    }

    @GetMapping(value = "/search")
    Object searchTable(@RequestParam("keyword") String keyword)
    {
        return tableService.searchTable(keyword);
    }

    @DeleteMapping(value = "/table/{tableId}")
    void deleteTable(@PathVariable int tableId)
    {
        tableService.deleteTable(tableId);
    }

}