package com.ez.tablebase.rest.common;
/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by Erik on 08-Apr-18.
 */

import com.ez.tablebase.rest.model.DataType;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public class OperationUtils
{
    private static DecimalFormat decimalFormat = new DecimalFormat("0.0#%");

    public static String max(String data1, String data2, byte type) throws ParseException
    {
        DataType dataType = DataType.values()[type];

        if (dataType.equals(DataType.UNKNOWN))
            return data1;

        else if (dataType.equals(DataType.NUMERIC)) {
            Integer int1 = Integer.parseInt(data1);
            Integer int2 = Integer.parseInt(data2);

            if (int1 > int2)
                return int1.toString();
            else if (int1 < int2)
                return int2.toString();
            else
                return int1.toString();

        }

        else if (dataType.equals(DataType.DATE)) {

        }

        else if (dataType.equals(DataType.PERCENT)) {
           Double percentage1 = decimalFormat.parse(data1).doubleValue();
           Double percentage2 = decimalFormat.parse(data2).doubleValue();

           if (percentage1 > percentage2)
               return decimalFormat.format(percentage1);
           else if (percentage1 < percentage2)
               return decimalFormat.format(percentage2);
           else
               return decimalFormat.format(percentage1);
        }

        else if (dataType.equals(DataType.CURRENCY)) {
            NumberFormat format = NumberFormat.getCurrencyInstance();
            Object curr1 = format.parse(data1);
            Object curr2 = format.parse(data2);

        }

        return null;
    }

    public static String min(String data1, String data2, byte type) throws ParseException
    {
        DataType dataType = DataType.values()[type];

        if (dataType.equals(DataType.UNKNOWN))
            return data1;

        else if (dataType.equals(DataType.NUMERIC)) {
            Integer int1 = Integer.parseInt(data1);
            Integer int2 = Integer.parseInt(data2);

            if (int1 < int2)
                return int1.toString();
            else if (int1 > int2)
                return int2.toString();
            else
                return int1.toString();

        }

        else if (dataType.equals(DataType.DATE)) {

        }

        else if (dataType.equals(DataType.PERCENT)) {
            Double percentage1 = decimalFormat.parse(data1).doubleValue();
            Double percentage2 = decimalFormat.parse(data2).doubleValue();

            if (percentage1 < percentage2)
                return decimalFormat.format(percentage1);
            else if (percentage1 > percentage2)
                return decimalFormat.format(percentage2);
            else
                return decimalFormat.format(percentage1);
        }

        else if (dataType.equals(DataType.CURRENCY)) {
            NumberFormat format = NumberFormat.getCurrencyInstance();
            Object curr1 = format.parse(data1);
            Object curr2 = format.parse(data2);
        }

        return null;
    }

    public static String mean(String data1, String data2, byte type)
    {
        DataType dataType = DataType.values()[type];

        if (dataType.equals(DataType.UNKNOWN))
            return data1;

        return null;
    }

//    public static String median(String data1, String data2, byte type)
//    {
//        DataType dataType = DataType.values()[type];
//
//        if (dataType.equals(DataType.UNKNOWN))
//            return data1;
//    }

    public static String concatenateString(String data1, String data2, byte type)
    {
       return data1 + " " + data2;
    }
}
