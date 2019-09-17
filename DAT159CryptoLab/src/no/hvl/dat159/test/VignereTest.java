/**
 * 
 */
package no.hvl.dat159.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import no.hvl.dat159.crypto.Vignere;

/**
 * @author tdoy
 *
 */
class VignereTest {

	@Test
	void test() {

		String message = "These are english letters";
		char[] keys = "JOY".toCharArray();
		
		Vignere vg = new Vignere(keys);
		String ecipher = vg.encrypt(message);
		String dmessage = vg.decrypt(ecipher);

		
		System.out.println(ecipher);
		
		assertArrayEquals(message.toCharArray(), dmessage.toCharArray());
				
		
	}

}
