/**
 * 
 */
package no.hvl.dat159.crypto.maths;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tdoy
 *
 */
public class FrequencyAnalysis {
	
	/**
	 * Probabilities of occurrence of the 26 letters in English Language
	 * where index = 0 is the probability value for letter A, index = 1, is the prob value of B
	 * and so on....index = 25 is the prob value of Z
	 */
	private static double[] probOfOccurence = {0.082, 0.015, 0.028, 0.043, 0.127, 0.022, 0.020,
			0.061, 0.070, 0.002, 0.008, 0.040, 0.024, 0.067, 0.075, 0.019, 0.001, 0.060, 0.063,
			0.091, 0.028, 0.010, 0.023, 0.001, 0.020, 0.001};
	
	/**
	 * Gives the Ic of English Letters
	 * Ic = 0.0656
	 * @return
	 */
	public static double indexOfCoincidenceOfEnglishLetters() {
		
		double ic = 0;
		
		for(int i=0; i<probOfOccurence.length; i++) {

			ic += Math.pow(probOfOccurence[i], 2.0);	// compute Pi^2
		}
		
		ic = getDecimalFormattedValue(ic, 4);
		
		return ic;
	}
	
	/**
	 * 
	 * @param y
	 * @return the frequency of occurrence of each letter in y
	 */
	public static Map<Character, Integer> frequencyOfLetters(String y) {
		
		Map<Character, Integer> freq = new HashMap<Character, Integer>();
		
		// find the frequency of occurrence of each letter in y
		for(int i=0; i<y.length(); i++) {
			char x = y.charAt(i);
			if(freq.containsKey(x)){
				int v = freq.get(x) + 1;
				freq.put(x, v);
			} else {
				freq.put(x, 1);
			}
		}
		
		return freq;
	}
	
	/**
	 * Compute the Friedman's index of coincidence Ic(y)
	 * @param y
	 */
	public static double indexOfCoincidence(String y) {
		
		// remove all spaces
		y = y.replaceAll(" ", "");
		
		int len = y.length();
		
		Map<Character, Integer> freqY = frequencyOfLetters(y);
		
		// get the frequency for each char 
		// compute sum(fi*fi-1) for i=A to Z
		double ic = 0.0;
		
		for(char c : freqY.keySet()) {
			int freqc = freqY.get(c);
			ic += freqc * (freqc - 1);
		}
		
		// compute sum(fi*fi-1)/N*N-1
		ic = ic/((double)len*((double)len - 1));
		
		ic = getDecimalFormattedValue(ic, 4);
		
		return ic;
	}
	
	private static Double getDecimalFormattedValue(Double n, int decimal) {
		
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(decimal);
		n = Double.valueOf(df.format(n));
		
		return n;
	}
	
	public static String reduceToMonoAlphabetic(int n, int start, String y) {
		
		// remove all spaces
		y = y.replaceAll(" ", "");
		
		int size = y.length()/n;

		char[] x = new char[size+1];

		for(int i=0; i<=size; i++) {
			if(start < y.length())
				x[i] = y.charAt(start);
			start += n;
		}
		 
		return new String(x);
	}
	
	/**
	 * This implementation is based on pg.32-34 of Cryptography Theory and Practice 3rd ed. (Douglas R. Stinson)
	 * for each monoAlphabetic y substring, we compute the Ic for each letter in y. We remember that
	 * y is shifted each time by g and the shift(i.e. g) that gives us an Ic close to 0.0656 is our guess
	 * Mg(y) where A<=g<=Z (i.e. 0<=g<=25) 
	 * for each shift (g) compute sum(pi*fi+g)/len(y) where i=A to Z 
	 * Assumption is that the correct key (letter) for each monoalphabetic substring
	 * will give us an indexOfOccurence close to 0.0656
	 * @param y
	 * @return
	 */
	public static List<Double> keyFromMonoAlphabeticRow(String y){
		
		Map<Character, Integer> freqY = frequencyOfLetters(y.trim());
		
		double[] mg = new double[26];
		
		for(int g=0; g<26; g++) {
			double pix = 0;
			for(char x : freqY.keySet()) {
				int ix = x - 'A';
				char xg = (char) ('A' + (ix + g)  % 26);
				 
				try{
					int freqxplusg = freqY.get(xg);
					double pi = probOfOccurence[ix];
					
					pix += pi*freqxplusg;
				}catch(NullPointerException e) {
					//
				}			

			}

			mg[g] = getDecimalFormattedValue(pix/(double)y.trim().length(), 3);
		}
		
		List<Double> mga = new ArrayList<Double>();
		for(int i=0; i<mg.length; i++)
			mga.add(mg[i]);
		
		return mga;
	}
	
	/**
	 * 
	 * @param Ics
	 * @return the char with an Ic that is closest to the standard Ic value of 0.0656
	 */
	public static String closestToStandardIc(List<Double> Ics) {
		
		double standardic = 0.0656;
		int index = 0;
		double min = Math.abs(Ics.get(0) - standardic);		// we need the absolute value here
		for(int i=1; i<Ics.size(); i++) {
			double diff = Math.abs(Ics.get(i) - standardic);
			min = Math.min(min, diff);
			if(min == diff)
				index = i;
		}
		
		char l = (char) ('A' + index);
		
		return String.valueOf(l);
	}
	
	/**
	 * find the pattern of n length in the string y
	 * return the distance between the occurrence of the search pattern
	 * @param n
	 * @param y
	 * @return the distance between the occurrence of the search pattern
	 */
	public static List<Integer> kasiskiRepeat(int n, String y) {
		
		String pattern = y.substring(0, n);

		return search(pattern, y);
	}

	/**
	 * 
	 * @param pattern
	 * @param y
	 * @return
	 */
	public static List<Integer> kasiskiRepeat(String pattern, String y) {
		
		return search(pattern, y);
	}
	
	private static List<Integer> search(String pattern, String y) {
		
		char[] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S',
				'T','U','V','W','X','Y','Z'};
		// remove all spaces
		y = y.replaceAll(" ", "");
		
		System.out.println("Pattern = "+pattern);
		
		List<Integer> indices = new ArrayList<Integer>();
		
		String dy = new String(y);
		int i = 0;
		while(i != -1) {
			i = dy.indexOf(pattern);
			String let = new String(letters);
			int nindex = (let.indexOf(pattern.charAt(0))+1)%26;	// take a new letter to destroy the pattern
			char nl = letters[nindex];
			char[] parr = pattern.toCharArray();
			parr[0] = nl;
			
			dy = dy.replaceFirst(pattern, new String(parr));
			if(i > 0)
				indices.add(i);
		}
		
		return indices;
	}
}
