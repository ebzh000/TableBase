package com.ez.tablebase.rest.common;
/*
 * Created by ErikZ on 6/07/2018.
 */

import com.ez.tablebase.rest.database.EntryEntity;
import com.ez.tablebase.rest.model.DataType;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

public class Utils
{
    private static DecimalFormat percentageFormat = new DecimalFormat("0.0#%");

    public static EntryEntity max(List<EntryEntity> entries, DataType type) throws ParseException
    {
        EntryEntity retEntry;

        if (type.equals(DataType.INTEGER))
            retEntry = Collections.max(entries, new IntegerComparator());
        else if (type.equals(DataType.PERCENT))
            retEntry = Collections.max(entries, new PercentageComparator());
        else if (type.equals(DataType.DECIMAL))
            retEntry = Collections.max(entries, new DoubleComparator());
        else
            throw new InvalidOperationException("Operation does not support data type: " + type);

        return retEntry;
    }

    public static EntryEntity min(List<EntryEntity> entries, DataType type) throws ParseException
    {
        EntryEntity retEntry;

        if (type.equals(DataType.INTEGER))
            retEntry = Collections.min(entries, new IntegerComparator());
        else if (type.equals(DataType.PERCENT))
            retEntry = Collections.min(entries, new PercentageComparator());
        else if (type.equals(DataType.DECIMAL))
            retEntry = Collections.min(entries, new DoubleComparator());
        else
            throw new InvalidOperationException("Operation does not support data type: " + type);

        return retEntry;
    }


    public static EntryEntity mean(List<EntryEntity> entries, DataType type) throws ParseException
    {
        EntryEntity retEntry;

        if (type.equals(DataType.INTEGER))
            retEntry = Collections.min(entries, new IntegerComparator());
        else if (type.equals(DataType.PERCENT))
            retEntry = Collections.min(entries, new PercentageComparator());
        else if (type.equals(DataType.DECIMAL))
            retEntry = Collections.min(entries, new DoubleComparator());
        else
            throw new InvalidOperationException("Operation does not support data type: " + type);

        return retEntry;
    }


    public static EntryEntity sum(List<EntryEntity> entries, DataType type) throws ParseException
    {
        EntryEntity retEntry;

        if (type.equals(DataType.INTEGER))
            retEntry = Collections.min(entries, new IntegerComparator());
        else if (type.equals(DataType.PERCENT))
            retEntry = Collections.min(entries, new PercentageComparator());
        else if (type.equals(DataType.DECIMAL))
            retEntry = Collections.min(entries, new DoubleComparator());
        else
            throw new InvalidOperationException("Operation does not support data type: " + type);

        return retEntry;
    }


    public static EntryEntity difference(List<EntryEntity> entries, DataType type) throws ParseException
    {
        EntryEntity retEntry;

        if (type.equals(DataType.INTEGER))
            retEntry = Collections.min(entries, new IntegerComparator());
        else if (type.equals(DataType.PERCENT))
            retEntry = Collections.min(entries, new PercentageComparator());
        else if (type.equals(DataType.DECIMAL))
            retEntry = Collections.min(entries, new DoubleComparator());
        else
            throw new InvalidOperationException("Operation does not support data type: " + type);

        return retEntry;
    }
    
    public static EntryEntity concatenateString(List<EntryEntity> entries) throws ParseException
    {
        EntryEntity retEntry = entries.get(0);

        StringBuilder newData = new StringBuilder();
        for(EntryEntity entry : entries)
            newData.append(entry.getData()).append("; ");

        retEntry.setData(newData.toString());
        return retEntry;
    }

    public static EntryEntity left(List<EntryEntity> entries, DataType type) throws ParseException
    {
        EntryEntity retEntry;

        if (type.equals(DataType.INTEGER))
            retEntry = Collections.min(entries, new IntegerComparator());
        else if (type.equals(DataType.PERCENT))
            retEntry = Collections.min(entries, new PercentageComparator());
        else if (type.equals(DataType.DECIMAL))
            retEntry = Collections.min(entries, new DoubleComparator());
        else
            throw new InvalidOperationException("Operation does not support data type: " + type);

        return retEntry;
    }

    public static EntryEntity right(List<EntryEntity> entries, DataType type) throws ParseException
    {
        EntryEntity maxEntry;

        if (type.equals(DataType.INTEGER))
            maxEntry = Collections.min(entries, new IntegerComparator());
        else if (type.equals(DataType.PERCENT))
            maxEntry = Collections.min(entries, new PercentageComparator());
        else if (type.equals(DataType.DECIMAL))
            maxEntry = Collections.min(entries, new DoubleComparator());
        else
            throw new InvalidOperationException("Operation does not support data type: " + type);

        return maxEntry;
    }

    public static EntryEntity copy(List<EntryEntity> entries, DataType type) throws ParseException
    {
        EntryEntity maxEntry;

        if (type.equals(DataType.INTEGER))
            maxEntry = Collections.min(entries, new IntegerComparator());
        else if (type.equals(DataType.PERCENT))
            maxEntry = Collections.min(entries, new PercentageComparator());
        else if (type.equals(DataType.DECIMAL))
            maxEntry = Collections.min(entries, new DoubleComparator());
        else
            throw new InvalidOperationException("Operation does not support data type: " + type);

        return maxEntry;
    }

    public static EntryEntity threshold(List<EntryEntity> entries, DataType type) throws ParseException
    {
        EntryEntity maxEntry;

        if (type.equals(DataType.INTEGER))
            maxEntry = Collections.min(entries, new IntegerComparator());
        else if (type.equals(DataType.PERCENT))
            maxEntry = Collections.min(entries, new PercentageComparator());
        else if (type.equals(DataType.DECIMAL))
            maxEntry = Collections.min(entries, new DoubleComparator());
        else
            throw new InvalidOperationException("Operation does not support data type: " + type);

        return maxEntry;
    }

    public static class IntegerComparator implements Comparator<EntryEntity>
    {
        @Override
        public int compare(EntryEntity o1, EntryEntity o2)
        {
            Integer data1 = Integer.parseInt(o1.getData());
            Integer data2 = Integer.parseInt(o2.getData());
            return data1.compareTo(data2);
        }
    }

    public static class PercentageComparator implements Comparator<EntryEntity>
    {
        @Override
        public int compare(EntryEntity o1, EntryEntity o2)
        {
            Double data1 = 0D;
            Double data2 = 0D;

            try
            {
                data1 = percentageFormat.parse(o1.getData()).doubleValue();
                data2 = percentageFormat.parse(o2.getData()).doubleValue();
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }

            return data1.compareTo(data2);
        }
    }

    public static class DoubleComparator implements Comparator<EntryEntity>
    {
        @Override
        public int compare(EntryEntity o1, EntryEntity o2)
        {
            Double data1 = Double.parseDouble(o1.getData());
            Double data2 = Double.parseDouble(o2.getData());
            return data1.compareTo(data2);
        }
    }

}
