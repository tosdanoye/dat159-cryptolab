/**
 * 
 */
package no.hvl.dat159.crypto;

/**
 * @author tdoy
 * Data Structure & Algorithm Goodrich et al. 6th ed. pg. 106
 */
public class CaesarCipher {
	
	private char[] encoder = new char[26];			// encryption array
	private char[] decoder = new char[26];			// decryption array
	
	/**
	 * key denotes the alphabet/position
	 * @param key
	 */
	public CaesarCipher(int key) {
		for(int i=0; i<26; i++) {
			encoder[i] = (char) ('A' + (i + key) % 26);
			decoder[i] = (char) ('A' + (i - key + 26) % 26);
		}
	}
	
	public String encrypt(String plaintext) {
		
		return operation(plaintext, encoder);
		
	}
	
	public String decrypt(String ciphertext) {
		
		return operation(ciphertext, decoder);
		
	}
	
	private String operation(String input, char[] code) {
		char[] msg = input.toCharArray();
		for(int j=0; j<msg.length; j++) {
			if(Character.isUpperCase(msg[j])) {
				int i = msg[j] - 'A';
				msg[j] = code[i];
			}
		}
		
		return new String(msg);
	}

	/**
	 * @return the encoder
	 */
	public char[] getEncoder() {
		return encoder;
	}

	/**
	 * @return the decoder
	 */
	public char[] getDecoder() {
		return decoder;
	}

}
