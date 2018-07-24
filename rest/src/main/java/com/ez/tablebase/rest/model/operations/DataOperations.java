package com.ez.tablebase.rest.model.operations;
/*
 * Created by ErikZ on 3/07/2018.
 */

import com.ez.tablebase.rest.common.Utils;
import com.ez.tablebase.rest.database.EntryEntity;
import com.ez.tablebase.rest.model.DataType;
import com.ez.tablebase.rest.model.OperationType;

import java.text.ParseException;
import java.util.List;

public class DataOperations extends Operation
{
    private List<EntryEntity> entries;
    private OperationType operationType;
    
    public DataOperations(List<EntryEntity> entries, OperationType operationType)
    {
        this.entries = entries;
        this.operationType = operationType;
    }
    
    @Override
    public Object exec() throws ParseException
    {
        EntryEntity retEntry = null;

        DataType type = DataType.values()[entries.get(0).getType()];
        switch (operationType)
        {
            case MAX:
                retEntry = Utils.max(entries, type);
                break;
            case MIN:
                retEntry = Utils.min(entries, type);
                break;
            case MEAN:
                retEntry = Utils.mean(entries, type);
                break;
            case SUM:
                retEntry = Utils.sum(entries, type);
                break;
            case DIFFERENCE:
                retEntry = Utils.difference(entries, type);
                break;
            case CONCATENATE_STRING:
                retEntry = Utils.concatenateString(entries);
                break;

            // Note these Operations only accept 2 entries
            case LEFT:
                retEntry = Utils.left(entries, type);
                break;
            case RIGHT:
                retEntry = Utils.right(entries, type);
                break;
            case COPY:
                retEntry = Utils.copy(entries, type);
                break;
            case THRESHOLD:
                retEntry = Utils.threshold(entries, type);
                break;

            case NO_OPERATION:
                break;
            default:
                break;
        }
        
        return retEntry;
    }
}
