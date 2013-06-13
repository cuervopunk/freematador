package com.freematador.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuapMemoryDB {

	private static Map<String, Map<String, Object>> instances;
	
	static {
		instances = new HashMap<String, Map<String, Object>>();
	}
	
	public static void insert(String className, String pk, Object instance) {
		Map<String,Object> classData = getClassData(className);
		classData.put(pk, instance);
	}

	public static void update(String className, String pk, Object instance) {
		Map<String,Object> classData = getClassData(className);
		classData.put(pk, instance);
	}
	
	public static void delete(String className, String pk) {
		Map<String,Object> classData = getClassData(className);
		classData.remove(pk);
	}
	
	public static List<Object> findAll(String className) {
		Map<String,Object> classData = getClassData(className);
		List<Object> items = new ArrayList<Object>(classData.values());
		return items;
	}
	
	public static Object findByPk(String className, String pk) {
		Map<String,Object> classData = getClassData(className);
		return classData.get(pk);
	}
	
	private static Map<String, Object> getClassData(String className) {
		Map<String,Object> classData = instances.get(className);
		if (classData == null) {
			classData = new HashMap<String,Object>();
			instances.put(className, classData);
		}
		return classData;
	}

}
