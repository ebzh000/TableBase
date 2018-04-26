package com.ez.tablebase.rest.service.utils;
/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by ErikZ on 19/04/2018.
 */

import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.database.DataAccessPathEntity;
import com.ez.tablebase.rest.database.EntryEntity;
import com.ez.tablebase.rest.model.DataType;
import com.ez.tablebase.rest.model.OperationType;
import com.ez.tablebase.rest.repository.CategoryRepository;
import com.ez.tablebase.rest.repository.DataAccessPathRepository;
import com.ez.tablebase.rest.repository.TableEntryRepository;
import com.ez.tablebase.rest.repository.TableRepository;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CategoryUtils extends BaseUtils
{
    private static DecimalFormat decimalFormat = new DecimalFormat("0.0#%");
    private static final Pattern currencyRegExp = Pattern.compile("[^0-9\\.,\\s]*");

    public CategoryUtils(CategoryRepository categoryRepository, TableRepository tableRepository, DataAccessPathRepository dataAccessPathRepository, TableEntryRepository tableEntryRepository)
    {
        super(categoryRepository, tableRepository, dataAccessPathRepository, tableEntryRepository);
    }

    public void createCategoryDAPsAndEntries(CategoryEntity category, CategoryEntity parentCategory, boolean linkChildren)
    {
        // Need to check if the parent category has no children. This implies that the parentId currently has DAPs and Entries initialised.
        // Which then implies that we need to update the DAPs to include the new category
        List<CategoryEntity> children = findChildren(parentCategory.getTableId(), parentCategory.getCategoryId());
        // Remove the new category that we just created
        children.remove(category);

        // There are other children for the parent, we must create new DAPs and Entries with relation to the correct tree
        if (children.size() != 0)
        {
            // If we want to link the current children of the parent category to the new category,
            // we need to get all the children to point to the new category and update the current data access paths containing the child category
            if (linkChildren)
            {
                for (CategoryEntity child : children)
                {
                    updateTableCategory(child.getTableId(), child.getCategoryId(), child.getAttributeName(), category.getCategoryId(), child.getType());

                    List<Integer> affectedEntries = dataAccessPathRepository.getEntryByPathContainingCategory(category.getTableId(), category.getCategoryId());
                    List<List<DataAccessPathEntity>> affectedPaths = new LinkedList<>();

                    for (Integer entryId : affectedEntries)
                        affectedPaths.add(dataAccessPathRepository.getEntryAccessPathByTree(category.getTableId(), entryId, category.getTreeId()));

                    for (List<DataAccessPathEntity> dap : affectedPaths)
                        createDataAccessPath(category.getTableId(), dap.get(0).getEntryId(), category.getCategoryId(), category.getTreeId());
                }
            }

            // If we don't want to link the current children of the parent category to the new category,
            // we will then leave the new category as a new child and then create all DAPs and Entries
            else
                initialiseEntries(category);
        }

        // Since the parent category has no children we must then update the data access paths that end with the parent category
        // to now end with the new category
        else
            updateDataAccessPaths(category, parentCategory, category.getTreeId());
    }

    public void duplicateCategories(CategoryEntity entity, Integer newParentId)
    {
        CategoryEntity newCategory = createCategory(entity.getTableId(), entity.getAttributeName(), newParentId, entity.getType(), entity.getTreeId());

        List<CategoryEntity> children = findChildren(entity.getTableId(), entity.getCategoryId());

        // If the first child is not null then we must recursively duplicate the children of this node
        if (children.size() != 0)
            for (CategoryEntity child : children)
                duplicateCategories(child, newCategory.getCategoryId());

            // If the first child is null then we must now duplicate all entries for each DataAccessPaths (DAP) that ends with this child's category_id
            // 1. Get all DAPs that end with this category
            // 2. Loop through
            //    a. Get entry for DAP
            //    b. Clone the entry and then the new DAP
        else
        {
            List<Integer> entries = dataAccessPathRepository.getEntriesForCategory(entity.getTableId(), entity.getCategoryId());
            if (!entries.isEmpty())
            {
                for (Integer entry : entries)
                {
                    List<List<CategoryEntity>> pathList = buildPaths(categoryRepository.getRootCategoryByTreeId(newCategory.getTableId(), newCategory.getTreeId()));
                    EntryEntity newEntry = duplicateEntry(entity.getTableId(), entry);

                    // Step 1 & 2 - Get a the path to the new and old parent category
                    List<Integer> pathToNewLeafNode = getPathToCategory(newCategory, pathList);
                    for (Integer categoryId : pathToNewLeafNode)
                        createDataAccessPath(newEntry.getTableId(), newEntry.getEntryId(), categoryId, newCategory.getTreeId());

                    // Get the relative access paths to the selected category's tree and duplicate DAPs
                    List<Integer> treeIds = getTreeIds(newCategory.getTableId());
                    treeIds.remove(newCategory.getTreeId());
                    for (Integer treeId : treeIds)
                    {
                        List<DataAccessPathEntity> accessPath = dataAccessPathRepository.getEntryAccessPathByTree(entity.getTableId(), entry, treeId);
                        for (DataAccessPathEntity pathEntity : accessPath)
                            createDataAccessPath(newEntry.getTableId(), newEntry.getEntryId(), pathEntity.getCategoryId(), pathEntity.getTreeId());
                    }
                }
            }
        }
    }

    /*
     * 1. Get a list of entries for Category A
     * 2. Get a list of entries for Category B
     *
     * Since the entries are created in order from the first access leaf node down,
     * we will start applying the operations from the entry with the smallest entry_id first
     *
     * 3. Apply Operation
     *    a. NO_OPERATION - We do not apply any operations and skip this step
     *    b. COPY - We copy the entry values from Category A to Category B
     *    c. THRESHOLD - We will be doing a check, for each entry value, against the provided threshold.
     *                   Category A will be designated to have the values under the threshold and
     *                   Category B will hold everything greater and equal to the threshold.
     */
    public void splitCategory(CategoryEntity categoryA, CategoryEntity categoryB, OperationType operationType, String threshold) throws ParseException
    {
        List<Integer> categoryAEntries = dataAccessPathRepository.getEntriesForCategory(categoryA.getTableId(), categoryA.getCategoryId());
        List<Integer> categoryBEntries = dataAccessPathRepository.getEntriesForCategory(categoryB.getTableId(), categoryB.getCategoryId());

        for (int index = 0; index < categoryAEntries.size(); index++)
        {
            EntryEntity entry1 = tableEntryRepository.findTableEntry(categoryA.getTableId(), categoryAEntries.get(index));
            EntryEntity entry2 = tableEntryRepository.findTableEntry(categoryB.getTableId(), categoryBEntries.get(index));

            String data1 = entry1.getData();
            DataType dataType = DataType.values()[categoryA.getType()];

            switch (operationType)
            {
                case NO_OPERATION:
                    break;
                case COPY:
                    tableEntryRepository.updateTableEntry(entry2.getTableId(), entry2.getEntryId(), data1);
                    break;
                case THRESHOLD:
                    applyThresholdOperation(entry1, entry2, data1, threshold, dataType);
                    break;
                default:
                    break;
            }
        }
    }

    private void applyThresholdOperation(EntryEntity entry1, EntryEntity entry2, String data1, String threshold, DataType dataType) throws ParseException
    {
        if (dataType.equals(DataType.NUMERIC))
        {
            Integer integer1 = Integer.parseInt(data1);
            Integer thresholdInteger = Integer.parseInt(threshold);

            if (integer1 >= thresholdInteger)
            {
                tableEntryRepository.updateTableEntry(entry1.getTableId(), entry1.getEntryId(), "");
                tableEntryRepository.updateTableEntry(entry2.getTableId(), entry2.getEntryId(), integer1.toString());
            }
        }

        else if (dataType.equals(DataType.PERCENT))
        {
            Double percentage1 = decimalFormat.parse(data1).doubleValue();
            Double thresholdPercentage = decimalFormat.parse(threshold).doubleValue();

            if (percentage1 >= thresholdPercentage)
            {
                tableEntryRepository.updateTableEntry(entry1.getTableId(), entry1.getEntryId(), "");
                tableEntryRepository.updateTableEntry(entry2.getTableId(), entry2.getEntryId(), decimalFormat.format(percentage1));
            }
        }

        else if (dataType.equals(DataType.CURRENCY))
        {
            BigDecimal curr1 = extractCurrencyValue(data1);
            BigDecimal thresholdCurr = extractCurrencyValue(threshold);
            Integer result = curr1.compareTo(thresholdCurr);

            if (result >= 0)
            {
                tableEntryRepository.updateTableEntry(entry1.getTableId(), entry1.getEntryId(), "");
                tableEntryRepository.updateTableEntry(entry2.getTableId(), entry2.getEntryId(), data1);
            }
        }
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

    /*
     * There are two cases to handle when deleting a category.
     * When we delete a category we will also delete all of the data access paths related to the selected category, as well as all the related entries.
     *
     * 1. If we want to delete the children, we need to check if the parent has any children left after the deletion.
     * If there are children left then we can call it quits here. However, if there are no children left then we must create data access paths to the parent node and initialise all appropriate entries.
     *
     * 2. Otherwise, we shall only delete the selected category and have all the children (if any) of the selected category to now point to the parent category.
     * We do not need to worry about modifying the data access paths as only the entry representing the deleted category is removed.
     */
    public void deleteCategory(CategoryEntity category, boolean deleteChildren)
    {
        CategoryEntity parentCategory = validateCategory(category.getTableId(), category.getParentId());
        if (!deleteChildren)
        {
            updateTableCategoriesForNewParent(category, parentCategory);
            deleteCategory(category);
        }
        else
        {
            // Delete all entries that belong to the category's leaf nodes
            deleteCategoryEntities(category);

            // Delete selected category and all its children, along with related DAPs
            List<Integer> children = getAllCategoryChildren(category.getTableId(), category.getCategoryId());
            for (Integer child : children)
            {
                CategoryEntity childCategory = validateCategory(category.getTableId(), child);
                deleteCategory(childCategory);
            }

            deleteCategory(category);

            List<CategoryEntity> parentChildren = findChildren(parentCategory.getTableId(), parentCategory.getCategoryId());
            if (parentChildren.size() == 0)
                initialiseEntries(parentCategory);
        }
    }

    private void updateTableCategoriesForNewParent(CategoryEntity category, CategoryEntity parentCategory)
    {
        List<CategoryEntity> children = findChildren(category.getTableId(), category.getCategoryId());
        if (children.size() != 0)
        {
            for (CategoryEntity child : children)
                updateTableCategory(child.getTableId(), child.getCategoryId(), child.getAttributeName(), parentCategory.getCategoryId(), child.getType());
        }
    }

    private void deleteCategoryEntities(CategoryEntity category)
    {
        List<Integer> entries = dataAccessPathRepository.getEntriesForCategory(category.getTableId(), category.getCategoryId());
        for (Integer entry : entries)
            tableEntryRepository.delete(validateEntry(category.getTableId(), entry));
    }

    public List<CategoryEntity> findAllTableCategories(Integer tableId)
    {
        return categoryRepository.findAllTableCategories(tableId);
    }

    public void updateTableCategory(Integer tableId, Integer categoryId, String attributeName, Integer parentId, byte type)
    {
        categoryRepository.updateTableCategory(tableId, categoryId, attributeName, parentId, type);
    }

    public void deleteCategory(CategoryEntity category)
    {
        categoryRepository.delete(category);
    }

    public CategoryEntity findCategory(Integer tableId, Integer categoryId)
    {
        return categoryRepository.findCategory(tableId, categoryId);
    }
}
