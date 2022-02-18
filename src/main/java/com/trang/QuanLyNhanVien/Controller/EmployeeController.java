package com.trang.QuanLyNhanVien.Controller;

import java.io.Console;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.portable.OutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trang.QuanLyNhanVien.Service.EmployeeService;
import com.trang.QuanLyNhanVien.model.AuthRequest;
import com.trang.QuanLyNhanVien.model.EmployeeForm;
import com.trang.QuanLyNhanVien.model.Employees;
import com.trang.QuanLyNhanVien.model.EmployeesExample;

import org.springframework.security.core.userdetails.UserDetails;

import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/Employee")
@CrossOrigin(origins = "*")
//@CrossOrigin
public class EmployeeController {

	
	@Autowired
	EmployeeService employeeService;
	 private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
	
	@GetMapping("")
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
	@PostMapping("add")
	@PreAuthorize("hasAnyRole('ADMIN') or hasAnyRole('EMPLOYEE')")
	public Employees PostEmployee(@RequestBody EmployeeForm employees) throws IOException{

		Employees newEmployees=employeeService.insert(employees);
		if(newEmployees!=null) {
			return newEmployees;
		}
		return null;
	}
	@PutMapping("/edit/{id}")
	@PreAuthorize("hashRole('ADMIN')")
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
	public void DeleteEmployee(@PathVariable("id") int id,UserDetails userDetails) {
		employeeService.deleteByPrimaryKey(id);
		System.out.println(userDetails.getUsername());
	}
//	 @RequestMapping(value="/getPage", method = RequestMethod.GET)
	@GetMapping("/getPage")
	 public PageInfo<Employees> listEmployees(
	            @RequestParam(value="page", required=false, defaultValue="1") int page,
	            @RequestParam(value="page-size", required=false, defaultValue="5") int pageSize){
	        List<Employees> result = employeeService.listEmployees(page,pageSize);
	        System.out.println(result.get(0).getName()+"ten nha"+result.size());
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
}
