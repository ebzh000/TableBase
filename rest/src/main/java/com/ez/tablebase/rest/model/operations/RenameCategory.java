package com.ez.tablebase.rest.model.operations;
/*
 * Created by ErikZ on 1/06/2018.
 */

import com.ez.tablebase.rest.database.CategoryEntity;

public class RenameCategory extends Operation<CategoryEntity>
{
    private CategoryEntity category;
    private String newName;

    public RenameCategory(CategoryEntity category, String newName)
    {
        this.category = category;
        this.newName = newName;
    }

    @Override
    public CategoryEntity exec()
    {
        //TODO: Implement Rename Category
        return null;
    }
}
