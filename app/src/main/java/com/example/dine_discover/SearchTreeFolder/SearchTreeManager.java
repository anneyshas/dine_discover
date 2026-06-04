/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.SearchTreeFolder;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * The class SearchTree manager creates and stores SearchTrees for quick recall of instantiated
 * SearchTrees.
 *
 * @author  Sarah Palmer
 * @since   2024-10-13
 */
public class SearchTreeManager {
    private static SearchTree searchTree;
    private ScheduledExecutorService scheduler;

    public SearchTreeManager(Context context) {
        setSearchTree(context);
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public static void setSearchTree(Context context) {
        searchTree = SearchTree.getInstance(context);
    }

    public static SearchTree getSearchTree() {
        return searchTree;
    }

    public static void refreshSearchTree(Context context) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                SearchTree.refreshInstance(context);
            }
        });
        executor.shutdown();
    }

}
