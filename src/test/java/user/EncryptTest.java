package user;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.blackangel.baskettogether.common.domain.RSA;
import com.blackangel.baskettogether.common.util.EncryptUtil;

public class EncryptTest {

	@Test
	public void testRSA() {
		try {
			RSA rsa = EncryptUtil.createRSA();
			
			String encrypted = EncryptUtil.encryptRSA(rsa.buildPublicKey(), "1234");
			String decrypted = EncryptUtil.decryptRSA(rsa.getPrivateKey(), encrypted);
			System.out.println("encrypted=" + encrypted);
			System.out.println("decrypted=" + decrypted);
			assertEquals(decrypted, "1234");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testDecryptRSA() {
		String encrypted = "ZQsRyg5ZSlloSceDbJ+GQkMLhzfqp9ekW1k03SzfDgWe6RzBmXGKsFB1KKL8CtIUqd5v6nii2N2Y5E9OMq+7GMwhKmA6LQG6ssqDFJDNSgh1JagYzYbkEy33XeGyba/If+Nnf1NhJSkzdgMLmlzzAKsv5JMdfYefu0SCqgKfhGc=";
		
		try {
			String decrypted = EncryptUtil.decryptRSA(EncryptUtil.makeRSAPrivateKey(
					"831bc57728ceef92bba62f770b67362251e159af165762c4c86b20c35e0fc804ce844fce7d06cb29ec66f990410078a891437314b822185040c6f504a51c2602d6460a290d580dbcf3d26c2e43ec090a0ca043798b13541c5a73f6897f616543c0e6ce2778ecfd4163ea85684cd58997a5df044e15246bd5829f92b864e91cbb",
					"44dc30316ea08959c2eefcdd048337fe7c8ce462be54354eef88bc937ff2bff62e5575e918e7998b325bbf55d19e22c13e5273bdfd1c03331b62a3cbc5c06c01a4991eb68394a51fa26d1da2253dffe6f6f0e6c47575c94863d0c521ba68434a7837d61d4abdbecdb1e2bdf4486afd2f0e953b88ae420eb20a94db6687d98029"), encrypted);
			
			assertThat(decrypted, is("1234"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
