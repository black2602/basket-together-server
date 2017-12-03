package user;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.blackangel.baskettogether.user.dao.UserDao;
import com.blackangel.baskettogether.user.dao.UserLoginSessionDao;
import com.blackangel.baskettogether.user.domain.UserLoginSession;
import com.blackangel.baskettogether.user.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@ActiveProfiles("dev")
public class UserLoginSessionTest {

	@Autowired UserDao userDao;
	@Autowired UserLoginSessionDao userLoginSessionDao;
	@Autowired UserService userService;
	
	@Test
	public void testIssueSession() {
		userLoginSessionDao.deleteAll();
		
		UserLoginSession session = userService.issueSession();
		assertThat(userLoginSessionDao.count(), is(1));
		assertNotNull(session.getSessionId());
		assertTrue(session.getSessionId() > 0);
		
		assertNotNull(session.getPbk());
		assertNotNull(session.getPvk());
		assertNotNull(session.getRegDts());
	}
	
	

}
