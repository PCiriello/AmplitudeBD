package com.amplitude.dao;

import java.sql.SQLException;
import java.util.Collection;

public interface DAO<T> {
	
	public void insert(T a) throws SQLException;
	public void update(T a) throws SQLException;
	public void delete(T a) throws SQLException;
	public Collection<T> read() throws SQLException;
	public T read(String id) throws SQLException;
}
