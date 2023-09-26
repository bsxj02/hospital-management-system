package com.hospital.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hospital.entity.Doctor;

public interface DoctorDao {

	List<Doctor> selectDoctorList(@Param("map") Map<String, Object> maps);

	void addDoctor(@Param("cc")  Doctor ss);

	void deleteDoctor(@Param("id")  int parseInt);

	Doctor selectDoctorById(@Param("id")  int parseInt);

	void updateDoctor(@Param("cc")  Doctor ss);

	Doctor selectDoctorByPhone(@Param("phone") String phone);


}
