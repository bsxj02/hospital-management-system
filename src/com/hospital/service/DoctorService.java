package com.hospital.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.dao.DoctorDao;
import com.hospital.entity.Doctor;

@Service
public class DoctorService {
	
	@Autowired 
	DoctorDao dao;

	public List<Doctor> selectDoctorList(Map<String, Object> maps) {
		// TODO Auto-generated method stub
		return dao.selectDoctorList(maps);
	}

	public void addDoctor(Doctor ss) {
		// TODO Auto-generated method stub
		dao.addDoctor(ss);
	}

	public void deleteDoctor(int parseInt) {
		// TODO Auto-generated method stub
		dao.deleteDoctor(parseInt);
	}

	public Doctor selectDoctorById(int parseInt) {
		// TODO Auto-generated method stub
		return dao.selectDoctorById(parseInt);
	}

	public void updateDoctor(Doctor ss) {
		// TODO Auto-generated method stub
		dao.updateDoctor(ss);
	}

	public Doctor selectDoctorByPhone(String phone) {
		// TODO Auto-generated method stub
		return dao.selectDoctorByPhone(phone);
	}

}
