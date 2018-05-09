package com.ez.tablebase.rest.common.html;
/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by erikz on 9/05/2018.
 */

import com.ez.tablebase.rest.database.CategoryEntity;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TreeNode implements Iterable<TreeNode> {

    public CategoryEntity data;
    private TreeNode parent;
    List<TreeNode> children;

    private boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return children.size() == 0;
    }

    private List<TreeNode> elementsIndex;

    public TreeNode(CategoryEntity data) {
        this.data = data;
        this.children = new LinkedList<>();
        this.elementsIndex = new LinkedList<>();
        this.elementsIndex.add(this);
    }

    public TreeNode addChild(CategoryEntity child) {
        TreeNode childNode = new TreeNode(child);
        childNode.parent = this;
        this.children.add(childNode);
        this.registerChildForSearch(childNode);
        return childNode;
    }

    public List<TreeNode> getChildren()
    {
        return this.children;
    }

    private int getLevel() {
        if (this.isRoot())
            return 0;
        else
            return parent.getLevel() + 1;
    }

    private void registerChildForSearch(TreeNode node) {
        elementsIndex.add(node);
        if (parent != null)
            parent.registerChildForSearch(node);
    }

    public TreeNode findTreeNode(Comparable<CategoryEntity> cmp) {
        for (TreeNode element : this.elementsIndex) {
            CategoryEntity elData = element.data;
            if (cmp.compareTo(elData) == 0)
                return element;
        }

        return null;
    }

    @Override
    public String toString() {
        return data != null ? data.toString() : "[data null]";
    }

    @Override
    public Iterator<TreeNode> iterator() {
        return new TreeNodeIter(this);
    }

}
