package com.trang.QuanLyNhanVien.model;


public class AuthRequest {

    private String username;
    private String password;
    public AuthRequest() {
		super();
	}
	public AuthRequest(String userName, String password) {
		super();
		this.username = userName;
		this.password = password;
	}
	public String getUserName() {
		return username;
	}
	public void setUserName(String userName) {
		this.username = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}