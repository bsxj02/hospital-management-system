package com.hospital.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hospital.entity.Department;
import com.hospital.entity.Doctor;
import com.hospital.entity.User;
import com.hospital.service.DepartmentService;
import com.hospital.service.DoctorService;

@RestController
public class DoctorController {
	
	@Autowired
	DoctorService service;
	
	@Autowired
	DepartmentService departmentService;
	

	@RequestMapping("/DoctorList")
    public ModelAndView DoctorList(
			@RequestParam(defaultValue="1") Integer currentPage,HttpServletRequest request,
			Map<String,Object> map){
		Doctor doctor = (Doctor)request.getSession().getAttribute("doctor");
		String DoctorName = request.getParameter("key");
		List<Doctor> list = new ArrayList<>();
		Map<String,Object> maps = new HashMap<>();
		maps.put("key", DoctorName);
		maps.put("uid", doctor!=null?doctor.getId():null);
		PageHelper.startPage(currentPage,10);
		list=service.selectDoctorList(maps);
		PageInfo<Doctor> pageInfo=new PageInfo<Doctor>(list,8);
		map.put("pageInfo", pageInfo);
		map.put("key", DoctorName);
		return new ModelAndView("view/doctor/list");
	} 
	
	

	@RequestMapping("/toAddDoctor")
    public ModelAndView toAddDoctor(HttpServletRequest request,ModelAndView mv){
		Map<String,Object> maps = new HashMap<>();
		List<Department> departmentList = departmentService.selectDepartmentList(maps);
		mv.addObject("departmentList", departmentList);
		mv.setViewName("view/doctor/add");
		return mv;
	}
	
	@RequestMapping("/addDoctor")
	@ResponseBody
	public boolean addDoctor(Doctor ss){
		boolean re = false;
		Doctor doctor = service.selectDoctorByPhone(ss.getPhone());
		if(doctor == null) {
			ss.setTimes(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
			service.addDoctor(ss);
			re = true;
		}
		return re;
	}
	
	@RequestMapping("/deleteDoctor")
    public boolean deleteDoctor(HttpServletRequest request,
			HttpServletResponse response){
		boolean re = false;
		String id = request.getParameter("id");
		service.deleteDoctor(Integer.parseInt(id));
		re = true;
	    return re;
	}
	
	
	@RequestMapping("/toUpdateDoctor")
    public ModelAndView toUpdateDoctor(HttpServletRequest request,ModelAndView mv){
		Map<String,Object> maps = new HashMap<>();
		String id=request.getParameter("id");
	    Doctor ff = service.selectDoctorById(Integer.parseInt(id));
	    List<Department> departmentList = departmentService.selectDepartmentList(maps);
		mv.addObject("departmentList", departmentList);
		mv.addObject("DoctorDate", ff);
		mv.setViewName("view/doctor/update");
		return mv;
	}
	

	@RequestMapping("/updateDoctor")
	@ResponseBody
	public boolean updateDoctor(Doctor ss){
		boolean re = false;
		service.updateDoctor(ss);
		re = true;
		return re;
	}

}
