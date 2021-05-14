package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
public class PagedData<T> {
    @JsonProperty(value = "items")
    private List<T> items;

    @JsonProperty(value = "total_items")
    private long totalItems;

    @JsonProperty(value = "total_pages")
    private int totalPages;

    public PagedData(List<T> items, long totalItems, int totalPages){
        this.items = items;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
 public boolean isEmpty(){
        return items == null || items.isEmpty();
 }

 public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
