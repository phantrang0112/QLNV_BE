package com.trang.QuanLyNhanVien.Controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trang.QuanLyNhanVien.DTO.AuthRequest;
import com.trang.QuanLyNhanVien.DTO.EmployeeChangePassword;
import com.trang.QuanLyNhanVien.DTO.EmployeeForm;
import com.trang.QuanLyNhanVien.Service.EmployeeService;
import com.trang.QuanLyNhanVien.model.Employees;
import com.trang.QuanLyNhanVien.model.EmployeesExample;

import org.springframework.security.core.userdetails.UserDetails;

import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/Employee")
//@CrossOrigin(origins = "*")
@CrossOrigin
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	@GetMapping("/all")
	public List<Employees> GetEmployees() {
		EmployeesExample employeesExample= new EmployeesExample();
		List<Employees> listEmployees= employeeService.selectByExample(employeesExample);
		return listEmployees; 
	}
	
	@GetMapping("/test")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public String test(Principal principal) {
		System.out.println(principal.getName());
		return "test";
	}
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN') or hasAnyRole('EMPLOYEE')")
	public Employees GetEmployeeId(@PathVariable("id") int id) {
		Employees Employee= employeeService.selectByPrimaryKey(id);
		return Employee;
	}
	@PostMapping("" )
	@PreAuthorize("hasAnyRole('ADMIN')")
	public Employees PostEmployee(@RequestBody Employees employees) throws IOException{
		if(employees!=null) {
			employees.setRoleid(1);
			Employees newEmployees = employeeService.insert(employees);
			if (newEmployees != null) {
				return newEmployees;
			}
		}
		return null;
	}
	@PostMapping("/upload/{id}" )
	@PreAuthorize("hasAnyRole('ADMIN') or hasAnyRole('EMPLOYEE')")
	public Employees UploadImg(@RequestParam("file") MultipartFile file, @PathVariable("id") int id) throws IOException{
		System.out.println("hello");
		EmployeeForm  employees= new EmployeeForm();
		employees.setImg(file);
		employees.setId(id);
		Employees newEmployees=employeeService.uploadImg(employees);
		if(newEmployees!=null) {
			return newEmployees;
		}
		return null;
	}
	@PutMapping("/edit/{id}")
	@PreAuthorize("hasAnyRole('ADMIN') ")
	public Employees EditEmployee(@RequestBody Employees employees, @PathVariable("id")int id){
		employees.setId(id);
		int succes =employeeService.updateByPrimaryKeySelective(employees);
		if(succes>0) {
			return employees;
		}
		return null;
	}
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public void DeleteEmployee(@PathVariable("id") int id) {
		employeeService.deleteByPrimaryKey(id);
//		System.out.println(userDetails.getUsername());
	}
//	 @RequestMapping(value="/getPage", method = RequestMethod.GET)
	@GetMapping("/getPage")
	 public PageInfo<Employees> listEmployees(
	            @RequestParam(value="page", required=false, defaultValue="1") int page,
	            @RequestParam(value="page-size", required=false, defaultValue="5") int pageSize){
	        List<Employees> result = employeeService.listEmployees(page,pageSize);
	        PageInfo<Employees> pi = new PageInfo<Employees>(result);
	        System.out.println(pi.getEndRow()+"hhh"+pi.getSize());
	        return pi;
	    }
	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody AuthRequest employee) {
		System.out.println("pass:"+employee.getPassword()+"name:"+employee.getUsername());
//		employee.setPassword(bcrypt.encode(employee.getPassword()));
		Map<String, Object> usernameString= employeeService.login(employee);
		
		return usernameString ;
	}
	@GetMapping("/search/{name}")
	@PreAuthorize("hasAnyRole('ADMIN') or hasAnyRole('EMPLOYEE')")
	 public PageInfo<Employees> search(@PathVariable("name") String name,
	            @RequestParam(value="page", required=false, defaultValue="1") int page,
	            @RequestParam(value="page-size", required=false, defaultValue="5") int pageSize){
		System.out.println("tên nè:"+name);
	        List<Employees> result = employeeService.listEmployeesSearch(page, pageSize, name);
	        System.out.println(result.get(0).getName()+"ten nha"+result.size()+ "tên nè:"+ name);
	        PageInfo<Employees> pi = new PageInfo<Employees>(result);
	        System.out.println(pi.getEndRow()+"hhh"+pi.getSize());
	        return pi;
	    }
	  @PostMapping("/authenticate")
	  public Map<String,Object> generateToken(@RequestBody AuthRequest authRequest) throws Exception {
		System.out.println(authRequest.getPassword()+authRequest.getUsername());
		Map<String, Object> usernameString= employeeService.login(authRequest);
		return usernameString;
    }
	  @PostMapping("/register")
	  public Employees register(@RequestBody Employees employee) {
		  System.out.println("employee"+employee.getEmail());
		  Employees employees= employeeService.register(employee);
		  if(employees!=null) {
			  return employees;
		  }
		  return null;
	  }
	  @PutMapping("/changepass")
	  @PreAuthorize("hasAnyRole('ADMIN') or hasAnyRole('EMPLOYEE')")
	  public  Map<String,Object> changePassword(@RequestBody EmployeeChangePassword employeeChangePassword) {
		  Map<String, Object> success= employeeService.changePassword(employeeChangePassword);
			  return success;
	  }

}
