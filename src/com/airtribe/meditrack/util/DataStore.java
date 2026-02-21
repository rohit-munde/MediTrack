package com.airtribe.meditrack.util;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generic DataStore class for storing and retrieving entities by ID
 * @param <T> Type of entity to store
 */
public class DataStore<T> {
    private HashMap<Integer, T> data;

    public DataStore() {
        this.data = new HashMap<>();
    }

    public void create(int id, T item) {
        this.data.put(id, item);
    }

    public T read(int id) {
        return this.data.get(id);
    }

    public void update(int id, T item) {
        this.data.put(id, item);
    }

    public void delete(int id) {
        this.data.remove(id);
    }

    public HashMap<Integer, T> getAll() {
        return this.data;
    }

    /**
     * Print all entities in the datastore
     */
    public void printAll() {
        if (data.isEmpty()) {
            System.out.println("DataStore is empty");
            return;
        }
        for (Integer id : data.keySet()) {
            System.out.println("[ID: " + id + "] " + data.get(id));
        }
    }

    /**
     * Get total count of entities
     */
    public int size() {
        return data.size();
    }

    /**
     * Check if datastore is empty
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * Check if entity exists by ID
     */
    public boolean exists(int id) {
        return data.containsKey(id);
    }

    /**
     * Clear all data
     */
    public void clear() {
        data.clear();
    }

    /**
     * Get list of all values
     */
    public List<T> getValues() {
        return new ArrayList<>(data.values());
    }

    /**
     * Get list of all keys (IDs)
     */
    public List<Integer> getKeys() {
        return new ArrayList<>(data.keySet());
    }
}
