package com.hospital.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hospital.entity.Department;

public interface DepartmentDao {

	List<Department> selectDepartmentList(@Param("map") Map<String, Object> maps);

	void addDepartment(@Param("cc")  Department ss);

	void deleteDepartment(@Param("id")  int parseInt);

	Department selectDepartmentById(@Param("id")  int parseInt);

	void updateDepartment(@Param("cc")  Department ss);


}
