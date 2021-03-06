package com.trang.QuanLyNhanVien.Service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.event.PublicInvocationEvent;
import org.springframework.security.core.userdetails.UserDetails;

import com.trang.QuanLyNhanVien.DTO.AuthRequest;
import com.trang.QuanLyNhanVien.DTO.EmployeeChangePassword;
import com.trang.QuanLyNhanVien.DTO.EmployeeForm;
import com.trang.QuanLyNhanVien.model.Employees;
import com.trang.QuanLyNhanVien.model.EmployeesExample;

public interface EmployeeService {
	long countByExample(EmployeesExample example);

	
	int deleteByExample(EmployeesExample example);

	
	int deleteByPrimaryKey(Integer id);

	
	Employees insert(Employees record);


	int insertSelective(Employees record);

	
	List<Employees> selectByExample(EmployeesExample example);

	
	Employees selectByPrimaryKey(Integer id);

	
	int updateByExampleSelective(@Param("record") Employees record, @Param("example") EmployeesExample example);

	
	int updateByExample(@Param("record") Employees record, @Param("example") EmployeesExample example);
	int updateByPrimaryKeySelective(Employees record);

	int updateByPrimaryKey(Employees record);
	public List<Employees> listEmployees(int page, int pageSize);
	Map<String, Object>login(AuthRequest employee);
	public List<Employees> listEmployeesSearch(int page,int pageSize, String name);
	UserDetails employDetails(Employees employees);
	public Employees register(Employees employee);
	Employees uploadImg(EmployeeForm record);
	Map<String, Object>  changePassword(EmployeeChangePassword employee);
	List<Employees> sort();
}
