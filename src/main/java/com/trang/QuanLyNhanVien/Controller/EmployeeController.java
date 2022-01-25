package com.trang.QuanLyNhanVien.Controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trang.QuanLyNhanVien.Service.EmployeeService;
import com.trang.QuanLyNhanVien.mapper.EmployeesMapper;
import com.trang.QuanLyNhanVien.model.Employees;
import com.trang.QuanLyNhanVien.model.EmployeesExample;

@RestController
@RequestMapping("/Employee")
@CrossOrigin(origins = "*")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@GetMapping("")
	public List<Employees> helloTrang() {
		EmployeesExample employeesExample= new EmployeesExample();
		List<Employees> listEmployees= employeeService.selectByExample(employeesExample);
		return listEmployees;
	}
	
	@PostMapping("")
	public Employees postEmployee(@RequestBody Employees employees){
		employeeService.insert(employees);
		
		return employees;
	}
	@PutMapping("/{id}")
	public Employees editEmployee(@RequestBody Employees employees, @PathVariable("id")int id){
		employees.setId(id);
		employeeService.updateByPrimaryKey(employees);
		return employees;
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") int id) {
		employeeService.deleteByPrimaryKey(id);
	}
}
