package com.ez.tablebase.rest.service.utils;
/*
 * Created by ErikZ on 19/04/2018.
 */

import com.ez.tablebase.rest.common.IncompatibleCategoryTypeException;
import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.database.DataAccessPathEntity;
import com.ez.tablebase.rest.database.EntryEntity;
import com.ez.tablebase.rest.model.OperationType;
import com.ez.tablebase.rest.repository.CategoryRepository;
import com.ez.tablebase.rest.repository.DataAccessPathRepository;
import com.ez.tablebase.rest.repository.TableEntryRepository;
import com.ez.tablebase.rest.repository.TableRepository;

import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TableEntryUtils extends BaseUtils
{
    public TableEntryUtils(CategoryRepository categoryRepository, TableRepository tableRepository, DataAccessPathRepository dataAccessPathRepository, TableEntryRepository tableEntryRepository)
    {
        super(categoryRepository, tableRepository, dataAccessPathRepository, tableEntryRepository);
    }

    public void combineEntries(CategoryEntity categoryA, CategoryEntity categoryB, OperationType operationType) throws ParseException
    {
        List<Integer> categoryAEntries = findEntriesForCategory(categoryA.getTableId(), categoryA.getCategoryId());
        List<Integer> categoryBEntries = findEntriesForCategory(categoryB.getTableId(), categoryB.getCategoryId());
        Map<String, List<Integer>> entryMapByDap = new HashMap<>();
        // Map category A entries
        for (Integer entryId : categoryAEntries)
        {
            List<DataAccessPathEntity> daps = dataAccessPathRepository.getEntryAccessPathExcludingTree(categoryA.getTableId(), entryId, categoryA.getTreeId());
            StringBuilder dapString = new StringBuilder();
            for(DataAccessPathEntity dapEntity : daps)
                dapString.append(dapEntity.getCategoryId()).append("-");

            dapString.deleteCharAt(dapString.length() - 1);
            if(entryMapByDap.containsKey(dapString.toString()))
                entryMapByDap.get(dapString.toString()).add(entryId);
            else
            {
                List<Integer> entries = new LinkedList<>();
                entries.add(entryId);
                entryMapByDap.put(dapString.toString(), entries);
            }
        }

        // Map Category B Entries
        for (Integer entryId : categoryBEntries)
        {
            List<DataAccessPathEntity> daps = dataAccessPathRepository.getEntryAccessPathExcludingTree(categoryB.getTableId(), entryId, categoryB.getTreeId());
            StringBuilder dapString = new StringBuilder();
            for(DataAccessPathEntity dapEntity : daps)
                dapString.append(dapEntity.getCategoryId()).append("-");

            dapString.deleteCharAt(dapString.length() - 1);
            if(entryMapByDap.containsKey(dapString.toString()))
                entryMapByDap.get(dapString.toString()).add(entryId);
            else
            {
                List<Integer> entries = new LinkedList<>();
                entries.add(entryId);
                entryMapByDap.put(dapString.toString(), entries);
            }
        }

        for (String dap : entryMapByDap.keySet())
        {
            EntryEntity entry1 = tableEntryRepository.findTableEntry(categoryA.getTableId(), entryMapByDap.get(dap).get(0));
            EntryEntity entry2 = tableEntryRepository.findTableEntry(categoryB.getTableId(), entryMapByDap.get(dap).get(1));

            if(entry1.getType() != entry2.getType())
                throw new IncompatibleCategoryTypeException("Specified categories have entries of different types");

            String data1 = entry1.getData();
            String data2 = entry2.getData();

            byte type = entry1.getType();
            switch (operationType)
            {
                case MAX:
                    data1 = OperationUtils.max(data1, data2, type);
                    break;
                case MIN:
                    data1 = OperationUtils.min(data1, data2, type);
                    break;
                case MEAN:
                    data1 = OperationUtils.mean(data1, data2, type);
                    break;
                case SUM:
                    data1 = OperationUtils.sum(data1, data2, type);
                    break;
                case DIFFERENCE:
                    data1 = OperationUtils.difference(data1, data2, type);
                    break;
                case CONCATENATE_STRING:
                    data1 = OperationUtils.concatenateString(data1, data2);
                    break;
                case LEFT:
                    break;
                case RIGHT:
                    data1 = data2;
                    break;
                case NO_OPERATION:
                    break;
                default:
                    break;
            }

            tableEntryRepository.updateTableEntry(entry1.getTableId(), entry1.getEntryId(), data1);
            deleteTableEntry(entry2.getTableId(), entry2.getEntryId());
        }
    }

    public List<EntryEntity> findAllTableEntries(Integer tableId)
    {
        return tableEntryRepository.findAllTableEntries(tableId);
    }

    public EntryEntity findTableEntry(Integer tableId, Integer entryId)
    {
        return tableEntryRepository.findTableEntry(tableId, entryId);
    }
}
