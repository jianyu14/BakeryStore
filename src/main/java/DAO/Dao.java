package DAO;

import java.util.List;

public interface Dao<T> {
    List<T> getAll(); // Retrieve all records
    T getById(String id); // Retrieve a record by its ID
    boolean update(T o); // Update a record
    boolean deleteById(String id); // Delete a record by its ID
    boolean insert(T o); // Insert a new record
}
