package com.blackangel.baskettogether.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.blackangel.baskettogether.common.domain.MyError;
import com.blackangel.baskettogether.common.domain.RSA;
import com.blackangel.baskettogether.common.exception.UserException;
import com.blackangel.baskettogether.common.util.EncryptUtil;
import com.blackangel.baskettogether.user.domain.User;
import com.blackangel.baskettogether.user.domain.User.UserRegType;
import com.blackangel.baskettogether.user.service.UserService;

@Controller
public class UserController {

	Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired UserService userService;

	@RequestMapping(value="/user/login", method=RequestMethod.GET)
	public ResponseEntity<String> issueSession(HttpServletRequest req, HttpSession session, HttpServletResponse res) {

		//		UserLoginSession session = userService.issueSession();

		try {
			RSA rsa = EncryptUtil.createRSA();

			ObjectMapper mapper = new ObjectMapper();
			String json;

			//			json = mapper.writerWithType(UserLoginSession.class).writeValueAsString(session);

			logger.debug("rsa privateKey=" + rsa.getPrivateKey());
			System.out.println("rsa privateKey=" + rsa.getPrivateKey().toString());
			session.setAttribute("RSAPrivateKey", rsa.getPrivateKey());

			//			return new ResponseEntity<String>(json, HttpStatus.OK);

			res.addCookie(new Cookie("RSAPublicKeyModulus", rsa.getModulus()));
			res.addCookie(new Cookie("RSAPublicKeyExponent", rsa.getExponent()));

			return new ResponseEntity<String>(HttpStatus.OK);

		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value="/user/signup", method=RequestMethod.POST)
	public ResponseEntity<String> signup(HttpServletRequest req, HttpSession session) {

		String id = req.getParameter("userId");
		String pw = req.getParameter("password");
		String name = req.getParameter("nickname");
		String snsId = req.getParameter("snsId");
		UserRegType userType = UserRegType.valueOf(Integer.parseInt(req.getParameter("regType")));

		PrivateKey rsaPvk = (PrivateKey) session.getAttribute("RSAPrivateKey");
		System.out.println("session rsaPvk = " + rsaPvk.toString() + ", pw=" + pw);

		try {
			String plainPw = EncryptUtil.decryptRSA(rsaPvk, pw);
			System.out.println("plain pw = " + plainPw);

			User user = new User(id, plainPw, name, snsId, userType);

			ObjectMapper mapper = new ObjectMapper();

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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	public ResponseEntity<String> login(HttpServletRequest req, HttpSession session) {

		try {
			String userId = req.getParameter("userId");
			String pw = req.getParameter("password");

			PrivateKey rsaPvk = (PrivateKey) session.getAttribute("RSAPrivateKey");
			System.out.println("session rsaPvk = " + rsaPvk.toString() + ", pw=" + pw);

			String plainPw = EncryptUtil.decryptRSA(rsaPvk, pw);
			System.out.println("plain pw = " + plainPw);

			User loginTryUser = new User(userId, plainPw);

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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value="/user/photo", method=RequestMethod.POST)
	public ResponseEntity<String> uploadPhoto(@RequestParam("uploadfile") MultipartFile uploadfile) {
		OutputStream out = null;
        PrintWriter printWriter = null;
 
        try {
            // 파일명 얻기
            String fileName = uploadfile.getOriginalFilename();
            // 파일의 바이트 정보 얻기
            byte[] bytes = uploadfile.getBytes();
            // 파일의 저장 경로 얻기
            String uploadPath = "C:\\Develop\\storage\\basket-together\\" + fileName;
             
            // 파일 객체 생성
            File file = new File(uploadPath);
            // 상위 폴더 존재 여부 확인
            if (!file.getParentFile().exists()) {
                // 상위 폴더가 존재 하지 않는 경우 상위 폴더 생성
                file.getParentFile().mkdirs();
            }
             
            // 파일 아웃풋 스트림 생성
            out = new FileOutputStream(file);
            // 파일 아웃풋 스트림에 파일의 바이트 쓰기
            out.write(bytes);
             
            return new ResponseEntity<String>(HttpStatus.OK);
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
         
        return null;
	}

	@ExceptionHandler(UserException.class)
	@ResponseBody
	public void handleException(HttpServletRequest req, HttpServletResponse res, Writer writer, DataAccessException ex) {
		ex.printStackTrace();

		Map<String, Object> params = new HashMap<String, Object>();

		//		params.put("name", "tracy Mcgrady");
		//		params.put("id", 1);

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
