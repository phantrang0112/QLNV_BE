package com.trang.QuanLyNhanVien.ServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.trang.QuanLyNhanVien.Util.jwtUtil;
import com.trang.QuanLyNhanVien.mapper.EmployeesMapper;
import com.trang.QuanLyNhanVien.model.AuthRequest;
import com.trang.QuanLyNhanVien.model.EmployeeForm;
import com.trang.QuanLyNhanVien.model.Employees;
import com.trang.QuanLyNhanVien.model.EmployeesExample;

@Service("EmployeeService")
public class EmployeeServiceImpl implements com.trang.QuanLyNhanVien.Service.EmployeeService {
	@Autowired
	EmployeesMapper employeesMapper;
	@Value("${upload.path}")
    private String fileUpload;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private com.trang.QuanLyNhanVien.Util.jwtUtil jwtUtil;

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
	public Employees insert(EmployeeForm record) {
		// TODO Auto-generated method stub
		 Employees newEmployees= new Employees(  record.getName(),record.getPhone(),record.getAddress(),record.getAge(),record.getPassword(),
				record.getUsername(), record.getRoleid());
		MultipartFile multipartFile = record.getImg();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(record.getImg().getBytes(), new File(this.fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        newEmployees.setImg(fileName);;
		record.setPassword(new BCryptPasswordEncoder().encode(record.getPassword()));
		Employees employees = employeesMapper.selectByPrimaryKey(record.getId());
		if (employees == null) {
			employeesMapper.insert(newEmployees);
			return newEmployees;
		}
		return null;
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
	public Map<String, Object> login(AuthRequest employee) {
		// TODO Auto-generated method stub
		EmployeesExample employeesExample = new EmployeesExample();
		Map<String, Object> paren = new HashMap<String, Object>();

		System.out.println(employee.getPassword() + employee.getUsername());
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(employee.getUsername(), employee.getPassword()));
			employeesExample.createCriteria().andUsernameEqualTo(employee.getUsername());
			List<Employees> listEmployee = employeesMapper.selectByExample(employeesExample);
			paren.put("username", listEmployee.get(0).getUsername());
			paren.put("id", listEmployee.get(0).getId());
			paren.put("message", "Đăng nhập thành công");
			paren.put("token", jwtUtil.generateToken(employee.getUsername()));
			return paren;
		} catch (Exception ex) {
			paren.put("username", null);
			paren.put("message", "Đăng nhập thất bại");
			paren.put("token", null);
			return paren;
		}

	}

	@Override
	public List<Employees> listEmployeesSearch(int page, int pageSize, String name) {
		List<Employees> result = null;
		try {
			PageHelper.startPage(page, pageSize);
//            PageHelper.orderBy("id ASC");
			EmployeesExample employeesExample = new EmployeesExample();
			employeesExample.createCriteria().andNameLike("%" + name + "%");
			result = employeesMapper.selectByExample(employeesExample);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public UserDetails employDetails(Employees employees) {
		// TODO Auto-generated method stub
		return null;
	}

}
