package com.ez.tablebase.rest.model;

public class CategoryModelBuilder
{
    public static CategoryModel buildModel(int tableId, int categoryId, String attributeName, Integer parentId, DataType type)
    {
        CategoryModel model = new CategoryModel();

        model.setTableId(tableId);
        model.setCategoryId(categoryId);
        model.setAttributeName(attributeName);
        model.setParentId(parentId);
        model.setType(type);
        return model;
    }
}
