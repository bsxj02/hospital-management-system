package com.hospital.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.hospital.entity.Records;
import com.hospital.entity.Statics;
import com.hospital.entity.User;
import com.hospital.service.DepartmentService;
import com.hospital.service.DoctorService;
import com.hospital.service.RecordsService;
import com.hospital.service.UserService;

@RestController
public class RecordsController {
	
	@Autowired
	RecordsService service;
	
	@Autowired
	UserService userService;
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	DoctorService doctorService;
	

	@RequestMapping("/RecordsList")
    public ModelAndView RecordsList(
			@RequestParam(defaultValue="1") Integer currentPage,HttpServletRequest request,
			Map<String,Object> map){
		User user = (User)request.getSession().getAttribute("user");
		Doctor doctor = (Doctor)request.getSession().getAttribute("doctor");
		String RecordsName = request.getParameter("key");
		List<Records> list = new ArrayList<>();
		Map<String,Object> maps = new HashMap<>();
		maps.put("key", RecordsName);
		maps.put("uid", user!=null?user.getId():null);
		maps.put("did", doctor!=null?doctor.getId():null);
		PageHelper.startPage(currentPage,10);
		list=service.selectRecordsList(maps);
		for(Records record : list) {
			record.setUser(userService.selectUserById(record.getUid()));
			record.setDoctor(doctorService.selectDoctorById(record.getDid()));
		}
		PageInfo<Records> pageInfo=new PageInfo<Records>(list,8);
		map.put("pageInfo", pageInfo);
		map.put("key", RecordsName);
		return new ModelAndView("view/records/list");
	} 
	
	

	@RequestMapping("/toAddRecords")
    public ModelAndView toAddRecords(HttpServletRequest request,ModelAndView mv){
		Map<String,Object> maps = new HashMap<>();
		List<Department> departmentList = departmentService.selectDepartmentList(maps);
		mv.addObject("departmentList", departmentList);
		mv.setViewName("view/records/add");
		return mv;
	}
	
	@RequestMapping("/selectDoctorsByDname")
	@ResponseBody
	public List<Doctor> addRecords(String dname){
		Map<String,Object> maps = new HashMap<>();
		maps.put("dname", dname);
		List<Doctor> doctorList = doctorService.selectDoctorList(maps);
		return doctorList;
	}

	@RequestMapping("/addRecordsSearch")
	@ResponseBody
	protected Integer addRecordsSearch(Records ss,HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Records> list = new ArrayList<>();
		Map<String,Object> maps = new HashMap<>();
String starttime=ss.getStarttime();
		maps.put("did", ss.getDid());
		maps.put("starttime",starttime.substring(0,10) );

		list=service.selectRecordsList(maps);
	return  list.size();


	}

	@RequestMapping("/addRecords")
	@ResponseBody
	public boolean addRecords(HttpServletRequest request,Records ss){
		boolean re = true;
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		User user = (User)request.getSession().getAttribute("user");
		Map<String,Object> maps = new HashMap<>();
		maps.put("did", ss.getDid());
		if(user != null) {
			List<Records> records = service.selectRecordsList(maps);
			try {
				Date nowDate = sim.parse(ss.getStarttime());
				Date endDate = sim.parse(ss.getEndtime());
				for(Records record : records) {
					Date renowDate = sim.parse(record.getStarttime()); 
					Date reendDate = sim.parse(record.getEndtime());
					 if(isEffectiveDate(nowDate,renowDate,reendDate)){
						 re= false;
	                     break;
	                 }else if(isEffectiveDate(endDate,renowDate,reendDate)){
					     re= false;
	                     break;
	                 }else if(isEffectiveDate(renowDate,nowDate,endDate)){
					     re= false;
	                     break;
	                 }else if(isEffectiveDate(reendDate,nowDate,endDate)){
					     re= false;
	                     break;
	                 }
				}
				if(re) {
					ss.setUid(user.getId());
					ss.setUname(user.getRealName());
					ss.setCreatetime(sim.format(new Date()));
					service.addRecords(ss);
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			re = false;
		}
		return re;
	}
	

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @author jqlin
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }
	
	@RequestMapping("/deleteRecords")
    public boolean deleteRecords(HttpServletRequest request,
			HttpServletResponse response){
		boolean re = false;
		String id = request.getParameter("id");
		service.deleteRecords(Integer.parseInt(id));
		re = true;
	    return re;
	}
	
	
	@RequestMapping("/toUpdateRecords")
    public ModelAndView toUpdateRecords(HttpServletRequest request,ModelAndView mv){
	    String id=request.getParameter("id");
	    Records ff = service.selectRecordsById(Integer.parseInt(id));
		mv.addObject("RecordsDate", ff);
		mv.setViewName("view/records/update");
		return mv;
	}
	
	
	@RequestMapping("/payMoney")
	@ResponseBody
    public boolean payMoney(HttpServletRequest request){
		boolean re = false;
		User user =(User)request.getSession().getAttribute("user");
	    String id=request.getParameter("id");
	    Records ff = service.selectRecordsById(Integer.parseInt(id));
	    Doctor doctor = doctorService.selectDoctorById(ff.getDid());
	    if(user.getMoney() > doctor.getPrice()) {
	    	user.setMoney(user.getMoney()-doctor.getPrice());
	        userService.updateUser(user);
	        request.getSession().setAttribute("user",user);
	        service.updateZfStatus(id);
	        re = true;
	    }
	    return re;
	}
	
	

	@RequestMapping("/updateRecords")
	@ResponseBody
	public boolean updateRecords(Records ss){
		boolean re = false;
		service.updateRecords(ss);
		re = true;
		return re;
	}
	
	@RequestMapping("/updateRecordsStatus")
	@ResponseBody
	public boolean updateRecordsStatus(Records ss){
		boolean re = false;
		service.updateRecordsStatus(ss);
		re = true;
		return re;
	}
	
	@RequestMapping("/selectRecordsStatics")
	@ResponseBody
	public List<Statics> selectRecordsStatics(){
		return service.selectRecordsStatics();
	}
	
}
