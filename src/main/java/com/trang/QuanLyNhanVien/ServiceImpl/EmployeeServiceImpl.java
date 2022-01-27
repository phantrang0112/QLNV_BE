package com.trang.QuanLyNhanVien.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
		Employees employees= employeesMapper.selectByPrimaryKey(id);
		if(employees!=null){
			employeesMapper.deleteByPrimaryKey(id);
			return 1;
		}
		
		return 0;
	}

	@Override
	public int insert(Employees record) {
		// TODO Auto-generated method stub
		Employees employees= employeesMapper.selectByPrimaryKey(record.getId());
		if(employees==null) {
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
		List<Employees> listEmployees= employeesMapper.selectByExample(example);
		return listEmployees ;
	}

	@Override
	public Employees selectByPrimaryKey(Integer id) {
		Employees Employee= employeesMapper.selectByPrimaryKey(id);
		return Employee ;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Employees record) {
		Employees employees= employeesMapper.selectByPrimaryKey(record.getId());
		if(employees!=null) {
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

}