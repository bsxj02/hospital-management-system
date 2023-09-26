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
import com.hospital.entity.User;
import com.hospital.service.DepartmentService;
import com.hospital.service.UserService;

@RestController
public class DepartmentController {
	
	@Autowired
	DepartmentService service;
	
	@Autowired
	UserService userService;
	

	@RequestMapping("/DepartmentList")
    public ModelAndView DepartmentList(
			@RequestParam(defaultValue="1") Integer currentPage,HttpServletRequest request,
			Map<String,Object> map){
		User user = (User)request.getSession().getAttribute("user");
		String DepartmentName = request.getParameter("key");
		List<Department> list = new ArrayList<>();
		Map<String,Object> maps = new HashMap<>();
		maps.put("key", DepartmentName);
		maps.put("uid", user!=null?user.getId():null);
		PageHelper.startPage(currentPage,10);
		list=service.selectDepartmentList(maps);
		PageInfo<Department> pageInfo=new PageInfo<Department>(list,8);
		map.put("pageInfo", pageInfo);
		map.put("key", DepartmentName);
		return new ModelAndView("view/department/list");
	} 
	
	

	@RequestMapping("/toAddDepartment")
    public ModelAndView toAddDepartment(HttpServletRequest request,ModelAndView mv){
		mv.setViewName("view/department/add");
		return mv;
	}
	
	@RequestMapping("/addDepartment")
	@ResponseBody
	public boolean addDepartment(Department ss){
		boolean re = false;
		service.addDepartment(ss);
		re = true;
		return re;
	}
	
	@RequestMapping("/deleteDepartment")
    public boolean deleteDepartment(HttpServletRequest request,
			HttpServletResponse response){
		boolean re = false;
		String id = request.getParameter("id");
		service.deleteDepartment(Integer.parseInt(id));
		re = true;
	    return re;
	}
	
	
	@RequestMapping("/toUpdateDepartment")
    public ModelAndView toUpdateDepartment(HttpServletRequest request,ModelAndView mv){
	    String id=request.getParameter("id");
	    Department ff = service.selectDepartmentById(Integer.parseInt(id));
		mv.addObject("DepartmentDate", ff);
		mv.setViewName("view/department/update");
		return mv;
	}
	

	@RequestMapping("/updateDepartment")
	@ResponseBody
	public boolean updateDepartment(Department ss){
		boolean re = false;
		service.updateDepartment(ss);
		re = true;
		return re;
	}

}
