package com.trang.QuanLyNhanVien.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.trang.QuanLyNhanVien.mapper.EmployeesMapper;
import com.trang.QuanLyNhanVien.model.Employees;
import com.trang.QuanLyNhanVien.model.EmployeesExample;

@Service("EmployeeService")
public class EmployeeServiceImpl implements com.trang.QuanLyNhanVien.Service.EmployeeService {
	@Autowired
	EmployeesMapper employeesMapper;
	@Override
	public long countByExample(EmployeesExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByExample(EmployeesExample example) {

		return 0;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		Employees employees = employeesMapper.selectByPrimaryKey(id);
		if (employees != null) {
			employeesMapper.deleteByPrimaryKey(id);
			return 1;
		}

		return 0;
	}

	@Override
	public int insert(Employees record) {
		// TODO Auto-generated method stub
	record.setPassword(new BCryptPasswordEncoder().encode(record.getPassword()));
		Employees employees = employeesMapper.selectByPrimaryKey(record.getId());
		if (employees == null) {
			employeesMapper.insert(record);
			return 1;
		}
		return 0;
	}

	@Override
	public int insertSelective(Employees record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Employees> selectByExample(EmployeesExample example) {
		// TODO Auto-generated method stub
		List<Employees> listEmployees = employeesMapper.selectByExample(example);
		return listEmployees;
	}

	@Override
	public Employees selectByPrimaryKey(Integer id) {
		Employees Employee = employeesMapper.selectByPrimaryKey(id);
		return Employee;
	}

	@Override
	public int updateByExampleSelective(Employees record, EmployeesExample example) {

		return 0;
	}

	@Override
	public int updateByExample(Employees record, EmployeesExample example) {
		// TODO Auto-generated method stub

		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(Employees record) {
		Employees employees = employeesMapper.selectByPrimaryKey(record.getId());
		if (employees != null) {
			employeesMapper.updateByPrimaryKeySelective(record);
			return 1;
		}
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Employees record) {
		Employees employees = employeesMapper.selectByPrimaryKey(record.getId());
		if (employees != null) {
			employeesMapper.updateByPrimaryKey(record);
			return 1;
		}
		return 0;
	}

	@Override
	public List<Employees> listEmployees(int page, int pageSize) {
		List<Employees> result = null;
		try {
			PageHelper.startPage(page, pageSize);
//            PageHelper.orderBy("id ASC"); 
			result = employeesMapper.selectEmployees();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public Map<String, Object> login(Employees employee) {
		// TODO Auto-generated method stub
		EmployeesExample employeesExample = new EmployeesExample();
		Map<String, Object> paren = new HashMap<String, Object>();
		employeesExample.createCriteria().andUsernameEqualTo(employee.getUsername());
		List<Employees> listEmployee = employeesMapper.selectByExample(employeesExample);
		if (listEmployee.size() > 0) {
			if (listEmployee.get(0).getPassword().equals(new BCryptPasswordEncoder().encode(employee.getPassword()))) {
				paren.put("username", listEmployee.get(0).getUsername());
				paren.put("id", listEmployee.get(0).getId());
				paren.put("message", "Đăng nhập thành công");
				paren.put("statusCode", 1);
				return paren;
			} else {
				paren.put("username", listEmployee.get(0).getUsername());
				paren.put("message", "Sai mật khẩu");
				paren.put("statusCode", 0);
				return paren;
			}
		} else {
			paren.put("username", null);
			paren.put("message", "Đăng nhập thất bại");
			paren.put("statusCode", 0);
			return paren;
		}

	}

	@Override
	public List<Employees> listEmployeesSearch(int page, int pageSize,String name) {
		List<Employees> result = null;
		try {
			PageHelper.startPage(page, pageSize);
//            PageHelper.orderBy("id ASC");
			EmployeesExample employeesExample= new EmployeesExample();
			employeesExample.createCriteria().andNameLike("%"+name+"%")  ;
			result = employeesMapper.selectByExample(employeesExample);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
