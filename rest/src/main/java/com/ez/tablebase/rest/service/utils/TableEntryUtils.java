package com.ez.tablebase.rest.service.utils;
/*
 * Created by ErikZ on 19/04/2018.
 */

import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.database.EntryEntity;
import com.ez.tablebase.rest.model.OperationType;
import com.ez.tablebase.rest.repository.CategoryRepository;
import com.ez.tablebase.rest.repository.DataAccessPathRepository;
import com.ez.tablebase.rest.repository.TableEntryRepository;
import com.ez.tablebase.rest.repository.TableRepository;

import java.text.ParseException;
import java.util.List;

public class TableEntryUtils extends BaseUtils
{
    public TableEntryUtils(CategoryRepository categoryRepository, TableRepository tableRepository, DataAccessPathRepository dataAccessPathRepository, TableEntryRepository tableEntryRepository)
    {
        super(categoryRepository, tableRepository, dataAccessPathRepository, tableEntryRepository);
    }

    public void combineEntries(CategoryEntity category1, CategoryEntity category2, OperationType operationType) throws ParseException
    {
        List<Integer> category1Entries = dataAccessPathRepository.getEntriesForCategory(category1.getTableId(), category1.getCategoryId());
        List<Integer> category2Entries = dataAccessPathRepository.getEntriesForCategory(category2.getTableId(), category2.getCategoryId());

        for (int index = 0; index < category2Entries.size(); index++)
        {
            EntryEntity entry1 = tableEntryRepository.findTableEntry(category1.getTableId(), category1Entries.get(index));
            EntryEntity entry2 = tableEntryRepository.findTableEntry(category2.getTableId(), category2Entries.get(index));

            String data1 = entry1.getData();
            String data2 = entry2.getData();

            switch (operationType)
            {
                case MAX:
                    data1 = OperationUtils.max(data1, data2, category1.getType());
                    break;
                case MIN:
                    data1 = OperationUtils.min(data1, data2, category1.getType());
                    break;
                case MEAN:
                    data1 = OperationUtils.mean(data1, data2, category1.getType());
                    break;
                case SUM:
                    data1 = OperationUtils.sum(data1, data2, category1.getType());
                    break;
                case DIFFERENCE:
                    data1 = OperationUtils.difference(data1, data2, category1.getType());
                    break;
                case CONCATENATE_STRING:
                    data1 = OperationUtils.concatenateString(data1, data2, category1.getType());
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
            tableEntryRepository.delete(entry2);
        }
    }

    public void updateTableEntry(Integer tableId, Integer entryId, String data)
    {
        tableEntryRepository.updateTableEntry(tableId, entryId, data);
    }

    public List<EntryEntity> findAllTableEntries(Integer tableId)
    {
        return tableEntryRepository.findAllTableEntries(tableId);
    }
}
