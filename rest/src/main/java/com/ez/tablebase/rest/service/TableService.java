package com.ez.tablebase.rest.service;

import com.ez.tablebase.rest.model.TableModel;
import com.ez.tablebase.rest.model.TableRequest;
import com.ez.tablebase.rest.model.TableResponse;

import java.util.List;

/**
 * Created by Erik Zhong on 9/6/2017.
 */

public interface TableService
{
    TableResponse createTable(TableRequest request);
    List<TableModel> getTables();
}
