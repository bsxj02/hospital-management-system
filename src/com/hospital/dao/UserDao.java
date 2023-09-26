package com.hospital.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hospital.entity.Admin;
import com.hospital.entity.Doctor;
import com.hospital.entity.User;

public interface UserDao {

	Admin selectAdmin(@Param("admin") Admin admin);

	User selectUser(@Param("user") User user);

	List<User> selectUserList(@Param("map") Map<String, Object> maps);

	void addUser(@Param("cc")  User ss);

	void deleteUser(@Param("id") int parseInt);

	User selectUserById(@Param("id")  Integer id);

	void updateUser(@Param("cc") User ss);

	User selectUserByUserName(@Param("username") String username);

	void updateAdminPassword(@Param("newpass") String newpass,@Param("id") Integer id);

	Doctor selectDoctor(@Param("doctor") Doctor doctor);


}
