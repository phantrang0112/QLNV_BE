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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.trang.QuanLyNhanVien.Util.jwtUtil;
import com.trang.QuanLyNhanVien.email.emailSender;
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
	@Autowired
	EmailValidator emailValidator;
	@Autowired
	emailSender emailSender;

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
		 Employees newEmployees= new Employees(record.getName(),record.getPhone(),record.getAddress(),record.getAge(),record.getPassword(),
				record.getUsername(),record.getRoleid(),record.getEmail());
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

	@Override
	public String register(Employees employee) {
		// TODO Auto-generated method stub
		boolean isValidEmail = emailValidator.test(employee.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        String token = jwtUtil.generateToken(employee.getUsername());

        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
        emailSender.send(
                employee.getEmail(),
                buildEmail(employee.getUsername(), link));
		return token;
	}
	
	private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}
