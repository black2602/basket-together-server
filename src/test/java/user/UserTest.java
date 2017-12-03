package user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.blackangel.baskettogether.common.exception.UserException;
import com.blackangel.baskettogether.user.dao.UserDao;
import com.blackangel.baskettogether.user.domain.User;
import com.blackangel.baskettogether.user.domain.User.UserRegType;
import com.blackangel.baskettogether.user.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class UserTest {

	@Autowired UserDao userDao;
	@Autowired UserService userService;
	User user1;
	
	@Before
	public void setup() {
		user1 = new User("test1234", "qwer1234", "테스트1234", "1234", UserRegType.TYPE_APP);
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testUserAdd() {
		userDao.deleteAll();
		
		long _id = userService.signUp(user1);
		user1.set_id(_id);
		
		assertTrue(_id >= 0);

		User getUser = userDao.get(user1.getUserId());
		
		assertEquals(user1.get_id(), _id);
		assertEquals(user1.getNickname(), getUser.getNickname());
		assertEquals(user1.getUserId(), getUser.getUserId());
		assertEquals(user1.getRegType(), getUser.getRegType());
	}
	
	private void checkSameUser(User user1, User user2) {
		assertEquals(user1.get_id(), user2.get_id());
		assertEquals(user1.getNickname(), user2.getNickname());
		assertEquals(user1.getUserId(), user2.getUserId());
		assertEquals(user1.getRegType(), user2.getRegType());		
	}
	

}
