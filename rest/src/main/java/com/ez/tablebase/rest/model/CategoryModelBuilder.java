package com.ez.tablebase.rest.model;

import com.ez.tablebase.rest.database.CategoryEntity;

public class CategoryModelBuilder
{
    public static CategoryModel buildModel(CategoryEntity entity)
    {
        CategoryModel model = new CategoryModel();

        model.setTableId(entity.getTableId());
        model.setCategoryId(entity.getCategoryId());
        model.setAttributeName(entity.getAttributeName());
        model.setParentId(entity.getParentId());
        model.setType(DataType.values()[entity.getType()]);
        return model;
    }
}
