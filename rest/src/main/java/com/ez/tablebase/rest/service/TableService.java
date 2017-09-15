package com.ez.tablebase.rest.service;

import com.ez.tablebase.rest.Model.TableModel;
import com.ez.tablebase.rest.Model.TableRequest;
import com.ez.tablebase.rest.Model.TableResponse;

import java.util.List;

/**
 * Created by Erik Zhong on 9/6/2017.
 */

public interface TableService
{
    TableResponse createTable(TableRequest request);
    List<TableModel> getTables();
}
