package com.trang.QuanLyNhanVien.ServiceImpl;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.trang.QuanLyNhanVien.mapper.RoleMapper;
import com.trang.QuanLyNhanVien.model.Employees;
import com.trang.QuanLyNhanVien.model.Role;

public class EmployeeDeail implements UserDetails {
	 Employees employee;
	 @Autowired 
	 RoleMapper roleMapper;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Role rolee= roleMapper.SelectById(employee.getRoleid());
		Set<Role> roles = (Set<Role>) rolee;
		return roles.stream().map(role->new SimpleGrantedAuthority("ROLE_"+role.getRole())).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return employee.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return employee.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
