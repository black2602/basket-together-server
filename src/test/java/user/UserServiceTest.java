package user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.blackangel.baskettogether.common.exception.UserException;
import com.blackangel.baskettogether.user.dao.UserDao;
import com.blackangel.baskettogether.user.dao.UserLoginSessionDao;
import com.blackangel.baskettogether.user.domain.User;
import com.blackangel.baskettogether.user.domain.User.UserRegType;
import com.blackangel.baskettogether.user.domain.UserLoginSession;
import com.blackangel.baskettogether.user.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class UserServiceTest {

	@Autowired UserDao userDao;
	@Autowired UserLoginSessionDao userLoginSessionDao;
	@Autowired UserService userService;
	User user1;
	
	@Before
	public void setup() {
		user1 = new User("test1234", "qwer1234", "테스트1234", "1234", UserRegType.TYPE_APP);
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void testNoExistUserLogin() {
		User user = new User("qwer1234", "1234");
		User loginUser = userService.login(user);
		assertNull(loginUser);
	}
	
	@Test(expected=UserException.class)
	public void testLoginInvalidPassword() {
		userDao.deleteAll();
		
		long _id = userService.signUp(user1);
		user1.set_id(_id);
		
		assertTrue(_id >= 0);
		
		User loginUser = userService.login(user1);
		checkSameUser(user1, loginUser);
		
		user1.setPassword(user1.getPassword() + "*");
		User loginUser2 = userService.login(user1);
	}
	
	@Test
	public void testLoginUserUpdate() {
		userDao.deleteAll();
		
		long _id = userService.signUp(user1);
		user1.set_id(_id);
		
		assertTrue(_id >= 0);
		
		user1.setDeviceId("testDeviceId");
		user1.setDeviceType("a");
		
		User loginUser = userService.login(user1);
		User getUser = userDao.get(user1.getUserId());
		
		checkSameUser(getUser, loginUser);
		
		assertEquals(loginUser.getDeviceId(), getUser.getDeviceId());
		assertNotNull(getUser.getLastLoginAt());
		assertEquals(loginUser.getDeviceType(), getUser.getDeviceType());
		
	}
	
	@Test
	public void testSignup() {
		//세션 발급
		UserLoginSession session = userService.issueSession();
		
		
		
		
	}
	
	private void checkSameUser(User user1, User user2) {
		assertEquals(user1.get_id(), user2.get_id());
		assertEquals(user1.getNickname(), user2.getNickname());
		assertEquals(user1.getUserId(), user2.getUserId());
		assertEquals(user1.getRegType(), user2.getRegType());		
	}
	

}
