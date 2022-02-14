package com.trang.QuanLyNhanVien.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.trang.QuanLyNhanVien.mapper.EmployeesMapper;
import com.trang.QuanLyNhanVien.mapper.RoleMapper;
import com.trang.QuanLyNhanVien.model.Employees;
import com.trang.QuanLyNhanVien.model.EmployeesExample;
import com.trang.QuanLyNhanVien.model.Role;

@Service
public class EmployeeDetailService implements UserDetailsService {

	@Autowired
	EmployeesMapper employeeMapper;
	@Autowired
	RoleMapper roleMapper;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		EmployeesExample taikhoanExample = new EmployeesExample();
		taikhoanExample.createCriteria().andUsernameEqualTo(username);
		List<Employees> listUser= employeeMapper.selectByExample(taikhoanExample);
		List<GrantedAuthority> grantedAuthorities= new ArrayList<GrantedAuthority>();
		if(listUser.size()>0) {
			Employees user = listUser.get(0);
			Role role= roleMapper.SelectById(listUser.get(0).getRoleid());
			GrantedAuthority authority= new SimpleGrantedAuthority("ROLE_"+role.getRole());//muốn có nhiều quyền thì add thêm nhiều authority
			grantedAuthorities.add(authority);
			
			UserDetails userDetails= new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),grantedAuthorities);
			System.out.println(userDetails.getAuthorities());
			return userDetails;
		}
		else {
			new UsernameNotFoundException("Đăng nhập không thành công");
		}
		return null;
	}

}
