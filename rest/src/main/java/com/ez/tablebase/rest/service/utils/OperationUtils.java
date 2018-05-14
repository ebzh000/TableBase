package com.ez.tablebase.rest.service.utils;
/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by Erik on 08-Apr-18.
 */

import com.ez.tablebase.rest.database.EntryEntity;
import com.ez.tablebase.rest.model.DataType;
import com.ez.tablebase.rest.repository.CategoryRepository;
import com.ez.tablebase.rest.repository.DataAccessPathRepository;
import com.ez.tablebase.rest.repository.TableEntryRepository;
import com.ez.tablebase.rest.repository.TableRepository;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperationUtils extends BaseUtils
{
    private static DecimalFormat percentageFormat = new DecimalFormat("0.0#%");
    private static final Pattern currencyRegExp = Pattern.compile("[^0-9\\.,\\s]*");

    OperationUtils(CategoryRepository categoryRepository, TableRepository tableRepository, DataAccessPathRepository dataAccessPathRepository, TableEntryRepository tableEntryRepository)
    {
        super(categoryRepository, tableRepository, dataAccessPathRepository, tableEntryRepository);
    }

    public static String max(String data1, String data2, byte type) throws ParseException
    {
        DataType dataType = DataType.values()[type];

        if (dataType.equals(DataType.INTEGER))
        {
            Integer int1 = Integer.parseInt(data1);
            Integer int2 = Integer.parseInt(data2);

            if (int1 > int2)
                return int1.toString();
            else if (int1 < int2)
                return int2.toString();
            else
                return int1.toString();
        }

        else if (dataType.equals(DataType.DATE))
        {
            Date date1 = convertToDate(data1);
            Date date2 = convertToDate(data2);

            String dateFormat = TimestampUtils.determineDateFormat(data1);
            if (dateFormat == null)
                throw new ParseException("Failed to parse date string", 0);

            DateFormat df = new SimpleDateFormat(dateFormat);
            if (date1.after(date2))
                return df.format(date1);
            else if (date1.before(date2))
                return df.format(date2);
            else
                return df.format(date1);
        }

        else if (dataType.equals(DataType.PERCENT))
        {
            Double percentage1 = percentageFormat.parse(data1).doubleValue();
            Double percentage2 = percentageFormat.parse(data2).doubleValue();

            if (percentage1 > percentage2)
                return percentageFormat.format(percentage1);
            else if (percentage1 < percentage2)
                return percentageFormat.format(percentage2);
            else
                return percentageFormat.format(percentage1);
        }

        else if (dataType.equals(DataType.DECIMAL))
        {
            Double decimal1 = Double.parseDouble(data1);
            Double decimal2 = Double.parseDouble(data2);

            if (decimal1 > decimal2)
                return decimal1.toString();
            else if (decimal1 < decimal2)
                return decimal2.toString();
            else
                return decimal1.toString();
        }

        else if (dataType.equals(DataType.CURRENCY))
        {
            BigDecimal curr1 = extractCurrencyValue(data1);
            BigDecimal curr2 = extractCurrencyValue(data2);

            int result = curr1.compareTo(curr2);

            if (result < 0)
                return data2;
            else if (result > 0)
                return data1;
            else
                return data1;
        }

        return data1;
    }

    public static String min(String data1, String data2, byte type) throws ParseException
    {
        DataType dataType = DataType.values()[type];

        if (dataType.equals(DataType.INTEGER))
        {
            Integer int1 = Integer.parseInt(data1);
            Integer int2 = Integer.parseInt(data2);

            if (int1 < int2)
                return int1.toString();
            else if (int1 > int2)
                return int2.toString();
            else
                return int1.toString();
        }

        else if (dataType.equals(DataType.DATE))
        {
            Date date1 = convertToDate(data1);
            Date date2 = convertToDate(data2);

            String dateFormat = TimestampUtils.determineDateFormat(data1);
            if (dateFormat == null)
                throw new ParseException("Failed to parse date string", 0);

            DateFormat df = new SimpleDateFormat(dateFormat);
            if (date1.before(date2))
                return df.format(date1);
            else if (date1.after(date2))
                return df.format(date2);
            else
                return df.format(date1);
        }

        else if (dataType.equals(DataType.PERCENT))
        {
            Double percentage1 = percentageFormat.parse(data1).doubleValue();
            Double percentage2 = percentageFormat.parse(data2).doubleValue();

            if (percentage1 < percentage2)
                return percentageFormat.format(percentage1);
            else if (percentage1 > percentage2)
                return percentageFormat.format(percentage2);
            else
                return percentageFormat.format(percentage1);
        }

        else if (dataType.equals(DataType.DECIMAL))
        {
            Double decimal1 = Double.parseDouble(data1);
            Double decimal2 = Double.parseDouble(data2);

            if (decimal1 < decimal2)
                return decimal1.toString();
            else if (decimal1 > decimal2)
                return decimal2.toString();
            else
                return decimal1.toString();
        }

        else if (dataType.equals(DataType.CURRENCY))
        {
            BigDecimal curr1 = extractCurrencyValue(data1);
            BigDecimal curr2 = extractCurrencyValue(data2);

            int result = curr1.compareTo(curr2);

            if (result < 0)
                return data1;
            else if (result > 0)
                return data2;
            else
                return data1;
        }

        return data1;
    }

    public static String mean(String data1, String data2, byte type) throws ParseException
    {
        DataType dataType = DataType.values()[type];

        if (dataType.equals(DataType.INTEGER))
        {
            Integer int1 = Integer.parseInt(data1);
            Integer int2 = Integer.parseInt(data2);

            Double mean = (double) ((int1 + int2) / 2);

            return mean.toString();
        }

        else if (dataType.equals(DataType.PERCENT))
        {
            Double percentage1 = percentageFormat.parse(data1).doubleValue();
            Double percentage2 = percentageFormat.parse(data2).doubleValue();

            Double mean = (percentage1 + percentage2) / 2;

            return percentageFormat.format(mean);
        }

        else if (dataType.equals(DataType.DECIMAL))
        {
            Double decimal1 = Double.parseDouble(data1);
            Double decimal2 = Double.parseDouble(data2);

            Double mean = (decimal1 + decimal2) / 2;

            return mean.toString();
        }

        else if (dataType.equals(DataType.CURRENCY))
        {
            BigDecimal curr1 = extractCurrencyValue(data1);
            BigDecimal curr2 = extractCurrencyValue(data2);

            BigDecimal mean = curr1.add(curr2).divide(BigDecimal.valueOf(2));

            return formatCurrencyValue(mean, data1);
        }

        return data1;
    }

    public static String sum(String data1, String data2, byte type) throws ParseException
    {
        DataType dataType = DataType.values()[type];

        if (dataType.equals(DataType.INTEGER))
        {
            Integer int1 = Integer.parseInt(data1);
            Integer int2 = Integer.parseInt(data2);

            Integer sum = int1 + int2;

            return sum.toString();
        }

        else if (dataType.equals(DataType.PERCENT))
        {
            Double percentage1 = percentageFormat.parse(data1).doubleValue();
            Double percentage2 = percentageFormat.parse(data2).doubleValue();

            Double sum = percentage1 + percentage2;

            return percentageFormat.format(sum);
        }

        else if (dataType.equals(DataType.DECIMAL))
        {
            Double decimal1 = Double.parseDouble(data1);
            Double decimal2 = Double.parseDouble(data2);

            Double sum = decimal1 + decimal2;

            return sum.toString();
        }

        else if (dataType.equals(DataType.CURRENCY))
        {
            BigDecimal curr1 = extractCurrencyValue(data1);
            BigDecimal curr2 = extractCurrencyValue(data2);

            BigDecimal sum = curr1.add(curr2);

            return formatCurrencyValue(sum, data1);
        }

        return data1;
    }

    public static String difference(String data1, String data2, byte type) throws ParseException
    {
        DataType dataType = DataType.values()[type];

        if (dataType.equals(DataType.INTEGER))
        {
            Integer int1 = Integer.parseInt(data1);
            Integer int2 = Integer.parseInt(data2);

            Integer diff = Math.abs(int1 - int2);

            return diff.toString();
        }

        else if (dataType.equals(DataType.PERCENT))
        {
            Double percentage1 = percentageFormat.parse(data1).doubleValue();
            Double percentage2 = percentageFormat.parse(data2).doubleValue();

            Double diff = Math.abs(percentage1 - percentage2);

            return percentageFormat.format(diff);
        }

        else if (dataType.equals(DataType.DECIMAL))
        {
            Double decimal1 = Double.parseDouble(data1);
            Double decimal2 = Double.parseDouble(data2);

            Double diff = Math.abs(decimal1 - decimal2);

            return diff.toString();
        }

        else if (dataType.equals(DataType.CURRENCY))
        {
            BigDecimal curr1 = extractCurrencyValue(data1);
            BigDecimal curr2 = extractCurrencyValue(data2);

            BigDecimal diff = curr1.subtract(curr2).abs();

            return formatCurrencyValue(diff, data1);
        }

        return data1;
    }

    public static String concatenateString(String data1, String data2)
    {
        return data1 + "; " + data2;
    }

    private static Date convertToDate(String data) throws ParseException
    {
        Date date = TimestampUtils.parse(data);

        if (date == null)
            throw new ParseException("Failed to parse date", 0);

        return date;
    }

    private static BigDecimal extractCurrencyValue(String data) throws ParseException
    {
        String value = null;
        Matcher currMatcher = currencyRegExp.matcher(data);
        if (currMatcher.find())
            value = currMatcher.group();

        if (value == null)
            throw new ParseException("Failed to parse currency string", 0);

        return new BigDecimal(value);
    }

    private static String formatCurrencyValue(BigDecimal data, String originalData)
    {
        Matcher currMatcher = currencyRegExp.matcher(originalData);
        if (currMatcher.find())
            return currMatcher.replaceFirst(data.toString());
        else
            return originalData;
    }

    public static class IntegerComparator implements Comparator<EntryEntity>
    {
        @Override
        public int compare(EntryEntity o1, EntryEntity o2)
        {
            Integer data1 = Integer.parseInt(o1.getData());
            Integer data2 = Integer.parseInt(o2.getData());
            if (data1 > data2)
                return 1;
            else if (Objects.equals(data1, data2))
                return 0;
            else
                return -1;
        }
    }

    public static class PercentageComparator implements Comparator<EntryEntity>
    {
        @Override
        public int compare(EntryEntity o1, EntryEntity o2)
        {
            Double data1 = null;
            Double data2 = null;
            try
            {
                data1 = percentageFormat.parse(o1.getData()).doubleValue();
                data2 = percentageFormat.parse(o2.getData()).doubleValue();
            }
            catch (ParseException e)
            {
                System.out.println("Failed to parse percentage");
                e.printStackTrace();
            }

            if(data1 == null || data2 == null)
                System.out.println("Failed to parse percentage");

            if (data1 > data2)
                return 1;
            else if (Objects.equals(data1, data2))
                return 0;
            else
                return -1;
        }
    }

    public static class DoubleComparator implements Comparator<EntryEntity>
    {
        @Override
        public int compare(EntryEntity o1, EntryEntity o2)
        {
            Double data1 = Double.parseDouble(o1.getData());
            Double data2 = Double.parseDouble(o2.getData());

            if (data1 > data2)
                return 1;
            else if (Objects.equals(data1, data2))
                return 0;
            else
                return -1;
        }
    }

}
