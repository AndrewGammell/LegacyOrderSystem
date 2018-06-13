package com.sogeti.repository;

import java.util.List;

public interface RepositoryInterface<T> {
	
	List<T> getAllObjects();
	T getObjectById(int id);
	
}
