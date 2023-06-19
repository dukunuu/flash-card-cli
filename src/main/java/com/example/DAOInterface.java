package com.example;

import java.sql.*;
import java.util.*;

public interface DAOInterface {
    public List<Object> getAllElements(String tableName) throws SQLException;

    public boolean add(String tableName, Object element) throws SQLException;

    public boolean remove(String tableName, int id) throws SQLException;

    public void createTable(String tableName) throws SQLException;

    public void removeTable(String tableName) throws SQLException;

    public List<FlashCard> getRandomizedFlashcards(String tablename) throws SQLException;
}
