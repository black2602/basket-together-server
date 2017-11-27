package com.blackangel.baskettogether.controller;

import java.io.IOException;
import java.io.Writer;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blackangel.baskettogether.common.domain.MyError;
import com.blackangel.baskettogether.user.domain.User;
import com.blackangel.baskettogether.user.domain.User.UserRegType;
import com.blackangel.baskettogether.user.service.UserService;

@Controller
public class UserController {

	@Autowired UserService userService;
	
	@RequestMapping(value="/user/signup", method=RequestMethod.POST)
	public ResponseEntity<String> signup(HttpServletRequest req) {

		String id = req.getParameter("userId");
		String pw = req.getParameter("password");
		String name = req.getParameter("nickname");
		UserRegType userType = UserRegType.valueOf(Integer.parseInt(req.getParameter("regType")));

		User user = new User(id, pw, name, userType);

		ObjectMapper mapper = new ObjectMapper();
		
		try {
			long _id = userService.signUp(user);
			user.set_id(_id);
			
			String json = "";

			json = mapper.writerWithType(User.class).writeValueAsString(user);
			
			return new ResponseEntity<String>(json, HttpStatus.OK);
			
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	public ResponseEntity<String> login(HttpServletRequest req) {
		
		try {
			String userId = req.getParameter("userId");
			String pw = req.getParameter("password");
			
			User loginTryUser = new User(userId, pw);
			
			User loginUser = userService.login(loginTryUser);
			loginUser.setPassword(null);
			
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writerWithType(User.class).writeValueAsString(loginUser);
			
			return new ResponseEntity<String>(json, HttpStatus.OK);
			
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@ExceptionHandler(DataAccessException.class)
	@ResponseBody
	public void handleException(HttpServletRequest req, HttpServletResponse res, Writer writer, DataAccessException ex) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("name", "tracy Mcgrady");
		params.put("id", 1);
		
		MyError err = new MyError(req.getRequestURI(), ex.getMessage(), params);
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			String json = mapper.writerWithType(MyError.class).writeValueAsString(err);
			
			res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			writer.write(json);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
