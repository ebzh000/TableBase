package com.ez.tablebase.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ez.tablebase.rest")
public class TableBaseApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(TableBaseApplication.class, args);
    }
}
