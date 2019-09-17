/**
 * 
 */
package no.hvl.dat159.crypto;

/**
 * @author tdoy
 *
 */
public class Vignere {
	
	private char[][] encoder;			// encryption array
	private char[][] decoder;			// decryption array
	/**
	 * 
	 */
	public Vignere(char[] key) {
		
		// implement me
		
	}
	
	/**
	 * 
	 * @param plaintext
	 * @return
	 */
	public String encrypt(String plaintext) {
		
		return operation(plaintext, encoder);
	}
	
	/**
	 * 
	 * @param ciphertext
	 * @return
	 */
	public String decrypt(String ciphertext) {
		
		return operation(ciphertext, decoder);
	}
	
	/**
	 * 
	 * @param input
	 * @param code
	 * @return
	 */
	private String operation(String input, char[][] code) {
		
		// implement me
				
		return null;
	}

	/**
	 * @return the encoder
	 */
	public char[][] getEncoder() {
		return encoder;
	}

	/**
	 * @return the decoder
	 */
	public char[][] getDecoder() {
		return decoder;
	}

}
