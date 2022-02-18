package com.trang.QuanLyNhanVien.model;

public class Employees {

	
	private Integer id;
	
	private String name;
	
	private String phone;
	
	private String address;
	
	private Integer age;
	
	private String img;
	
	private String password;
	
	private String username;
	
	private Integer roleid;

	
	public Integer getId() {
		return id;
	}

	
	public void setId(Integer id) {
		this.id = id;
	}

	public Employees( String name, String phone, String address, Integer age, String password,
			String username, Integer roleid) {
		super();
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.age = age;
		this.password = password;
		this.username = username;
		this.roleid = roleid;
	}


	public Employees(Integer id, String name, String phone, String address, Integer age, String img, String password,
			String username, Integer roleid) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.age = age;
		this.img = img;
		this.password = password;
		this.username = username;
		this.roleid = roleid;
	}


	public Employees() {
		super();
	}


	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	
	public void setPhone(String phone) {
		this.phone = phone;
	}

	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	
	public String getImg() {
		return img;
	}

	
	public void setImg(String img) {
		this.img = img;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
}