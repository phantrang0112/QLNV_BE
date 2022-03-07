package com.trang.QuanLyNhanVien.ServiceImpl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.trang.QuanLyNhanVien.mapper.RoleMapper;
import com.trang.QuanLyNhanVien.model.Role;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.trang.QuanLyNhanVien.DTO.AuthRequest;
import com.trang.QuanLyNhanVien.DTO.EmployeeChangePassword;
import com.trang.QuanLyNhanVien.DTO.EmployeeForm;
import com.trang.QuanLyNhanVien.Util.jwtUtil;
import com.trang.QuanLyNhanVien.email.emailSender;
import com.trang.QuanLyNhanVien.mapper.EmployeesMapper;
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
	@Autowired
	EmailValidator emailValidator;
	@Autowired
	emailSender emailSender;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	RoleMapper roleMapper;

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
	public Employees insert(Employees record) {
		// TODO Auto-generated method stub
		Employees newEmployees = new Employees();

		record.setPassword(new BCryptPasswordEncoder().encode(record.getPassword()));
		newEmployees = record;
		Employees employees = employeesMapper.selectByPrimaryKey(record.getId());
		if (employees == null) {
			employeesMapper.insert(newEmployees);
			return newEmployees;
		}
		return null;
	}

	@Override
	public Employees uploadImg(EmployeeForm record) {
		Employees updateEmployees = new Employees();
		MultipartFile multipartFile = record.getImg();
		String fileName = multipartFile.getOriginalFilename();
		try {
			FileCopyUtils.copy(record.getImg().getBytes(), new File(this.fileUpload + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		updateEmployees.setImg(fileName);
		updateEmployees.setId(record.getId());
		Employees employees = employeesMapper.selectByPrimaryKey(record.getId());
		if (employees != null) {
			employeesMapper.updateByPrimaryKeySelective(updateEmployees);
			return updateEmployees;
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
		Role role;

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
			role= roleMapper.SelectById(listEmployee.get(0).getRoleid());
			paren.put("role",role.getRole());
			return paren;
		} catch (Exception ex) {
			System.out.println(ex);
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

	@Override
	public Employees register(Employees employee) {
		// TODO Auto-generated method stub
		boolean isValidEmail = emailValidator.test(employee.getEmail());

		if (!isValidEmail) {
			throw new IllegalStateException("email not valid");
		}
//		String token = jwtUtil.generateToken(employee.getUsername());
//		System.out.println("token: " + token);
		
		// link đã đăng nhập sau khi đăng kí xong
//		String link = "http://localhost:8080/Employee/registration/confirm?token=" + token;
		String password=RandomStringUtils.randomAlphanumeric(8);
		employee.setPassword(passwordEncoder.encode(password));
		employee.setRoleid(2);
		int success=employeesMapper.insert(employee);
		if(success==1) {
			emailSender.send(employee.getEmail(), buildEmail(employee.getUsername(), password));
			return employee;
		}
		
		return null;
	}
	
	private String buildEmail(String name, String password) {
		return "<body marginheight=\"0\" topmargin=\"0\" marginwidth=\"0\" style=\"margin: 0px; background-color: #f2f3f8;\" leftmargin=\"0\">"+
				"    <table cellspacing=\"0\" border=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#f2f3f8\""+
				 "       style=\"@import url(https://fonts.googleapis.com/css?family=Rubik:300,400,500,700|Open+Sans:300,400,600,700); font-family: 'Open Sans', sans-serif;\">"+
				  "      <tr> <td><table style=\"background-color: #f2f3f8; max-width:670px;  margin:0 auto;\" width=\"100%\" border=\"0\"align=\"center\" cellpadding=\"0\" cellspacing=\"0\">"+
				   "                 <tr><td style=\"height:80px;\">&nbsp;</td></tr>"+
				    "                <tr><td style=\"text-align:center;\"><a href=\"#\" title=\"logo\" target=\"_blank\">"+
				     "                       <img width=\"60\" src=\"https://i.ibb.co/hL4XZp2/android-chrome-192x192.png\" title=\"logo\" alt=\"logo\"></a></td></tr>"+
				      "              <tr><td style=\"height:20px;\">&nbsp;</td></tr><tr><td>"+
				"                            <table width=\"95%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"style=\"max-width:670px;background:#fff; border-radius:3px; text-align:center;-webkit-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);-moz-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);box-shadow:0 6px 18px 0 rgba(0,0,0,.06);\">"+
				 "                               <tr><td style=\"height:40px;\">&nbsp;</td></tr><tr><td style=\"padding:0 35px;\">"+
				  "                                      <h1 style=\"color:#1e1e2d; font-weight:500; margin:0;font-size:32px;font-family:'Rubik',sans-serif;\">Hi " +name+"</h1>"+
				 "                                       <span style=\"display:inline-block; vertical-align:middle; margin:29px 0 26px; border-bottom:1px solid #cecece; width:100px;\"></span>"+
				 "                                       <p style=\"color:#455056; font-size:15px;line-height:24px; margin:0;\"> Thank you for registering an account. <br>Please login with password: "+"<b>"+password+"</b>"+
				 "                                        </p><p style=\"background:#20e277;text-decoration:none !important; font-weight:500; margin-top:35px; color:#fff;text-transform:uppercase; font-size:14px;padding:10px 24px;display:inline-block;border-radius:50px;\">Thank You</p>"+
				 "                                   </td></tr><tr><td style=\"height:40px;\">&nbsp;</td></tr>"+
				 "                           </table></td><tr><td style=\"height:20px;\">&nbsp;</td></tr>"+
				  "                  <tr><td style=\"text-align:center;\"><p style=\"font-size:14px; color:rgba(69, 80, 86, 0.7411764705882353); line-height:18px; margin:0 0 0;\">&copy; <strong>phantrang151220@gmail.com</strong></p>"+
				  "                      </td></tr><tr><td style=\"height:80px;\">&nbsp;</td>"+
				  "                  </tr></table></td></tr></table></body>";
	}

	@Override
	public Map<String, Object>  changePassword(EmployeeChangePassword employee) {
		String token=null;
		Map<String, Object> paren = new HashMap<String, Object>();
		if (employee != null) {
			EmployeesExample employeesExample = new EmployeesExample();
			employeesExample.createCriteria().andUsernameEqualTo(employee.getUsername());
			
			List<Employees> employees = employeesMapper.selectByExample(employeesExample);
			boolean check=passwordEncoder.matches(employee.getOldPassword(), employees.get(0).getPassword());
			System.out.println("trang1"+employees.size()+employees.get(0).getPassword()+"   "+employees.get(0).getAddress()+passwordEncoder.encode(employee.getOldPassword())+check);
			if (employees.get(0) != null) {
				System.out.println(employees.get(0).getUsername());
				if(passwordEncoder.matches(employee.getOldPassword(), employees.get(0).getPassword())){
					System.out.println("trang1");
					emailSender.send(employees.get(0).getEmail(), emailChangePassword(employees.get(0).getUsername()));
					employees.get(0).setPassword(passwordEncoder.encode(employee.getNewPassword()));
					employeesMapper.updateByPrimaryKeySelective(employees.get(0));
					paren.put("username", employees.get(0).getUsername());
					paren.put("id", employees.get(0).getId());
					paren.put("message", "Đổi mật khẩu thành công!");
					paren.put("token", jwtUtil.generateToken(employee.getUsername()));
					return paren;
				
					
				}
			}
		}
		paren.put("username", employee.getUsername());
		paren.put("message", "Đổi mật khẩu thất bại");
		return paren;
	}
	private String emailChangePassword(String username)
	{
		LocalDate date= LocalDate.now();
		return "<body marginheight=\"0\" topmargin=\"0\" marginwidth=\"0\" style=\"margin: 0px; background-color: #f2f3f8;\" leftmargin=\"0\">"+
				"    <table cellspacing=\"0\" border=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#f2f3f8\""+
				 "       style=\"@import url(https://fonts.googleapis.com/css?family=Rubik:300,400,500,700|Open+Sans:300,400,600,700); font-family: 'Open Sans', sans-serif;\">"+
				  "      <tr> <td><table style=\"background-color: #f2f3f8; max-width:670px;  margin:0 auto;\" width=\"100%\" border=\"0\"align=\"center\" cellpadding=\"0\" cellspacing=\"0\">"+
				   "                 <tr><td style=\"height:80px;\">&nbsp;</td></tr>"+
				    "                <tr><td style=\"text-align:center;\"><a href=\"#\" title=\"logo\" target=\"_blank\">"+
				     "                       <img width=\"60\" src=\"https://i.ibb.co/hL4XZp2/android-chrome-192x192.png\" title=\"logo\" alt=\"logo\"></a></td></tr>"+
				      "              <tr><td style=\"height:20px;\">&nbsp;</td></tr><tr><td>"+
				"                            <table width=\"95%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"style=\"max-width:670px;background:#fff; border-radius:3px; text-align:center;-webkit-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);-moz-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);box-shadow:0 6px 18px 0 rgba(0,0,0,.06);\">"+
				 "                               <tr><td style=\"height:40px;\">&nbsp;</td></tr><tr><td style=\"padding:0 35px;\">"+
				  "                                      <h1 style=\"color:#1e1e2d; font-weight:500; margin:0;font-size:32px;font-family:'Rubik',sans-serif;\">You have requested to reset your password</h1>"+
				 "                                       <span style=\"display:inline-block; vertical-align:middle; margin:29px 0 26px; border-bottom:1px solid #cecece; width:100px;\"></span>"+
				 "                                       <p style=\"color:#455056; font-size:15px;line-height:24px; margin:0;\"> We have received a request to change password associated with your account at Website on "+date+
				 "                                        </p><p style=\"background:#20e277;text-decoration:none !important; font-weight:500; margin-top:35px; color:#fff;text-transform:uppercase; font-size:14px;padding:10px 24px;display:inline-block;border-radius:50px;\">Thank You</p>"+
				 "                                   </td></tr><tr><td style=\"height:40px;\">&nbsp;</td></tr>"+
				 "                           </table></td><tr><td style=\"height:20px;\">&nbsp;</td></tr>"+
				  "                  <tr><td style=\"text-align:center;\"><p style=\"font-size:14px; color:rgba(69, 80, 86, 0.7411764705882353); line-height:18px; margin:0 0 0;\">&copy; <strong>phantrang151220@gmail.com</strong></p>"+
				  "                      </td></tr><tr><td style=\"height:80px;\">&nbsp;</td>"+
				  "                  </tr></table></td></tr></table></body>";
	}
}
