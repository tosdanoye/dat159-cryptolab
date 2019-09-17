/**
 * 
 */
package no.hvl.dat159.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import no.hvl.dat159.crypto.Vignere;
import no.hvl.dat159.crypto.maths.FrequencyAnalysis;

/**
 * @author tdoy
 *
 */
class FrequencyAnalysisTest {

	/**
	 * Test method for {@link no.hvl.dat159.crypto.maths.FrequencyAnalysis#indexOfConcidence(java.lang.String)}.
	 */
	@Test
	void testIndexOfConcidence() {
		String ciphertext1 = "KKGHM VGJRG TBIVQ IVWRY CGBSX VPTGQ QLLIX FGUQP BROII "
				+ "TXBVY CHMFC EETLH KVTTK VGRPS HTKYY KXGGV LWNBF ICSLC"
				+ "HTGEA STJFJ GRTVB HLSEI CIVWR YCGLC HKFTL HCKCS XDCIR"
				+ "BXBVJ CRKSV DGABH CIWRB DJVPH MVGVJ PUCTR RTHFK VLITZ"
				+ "EZNWX FUJWB UCNJS HXRKE ADFAG IAXTZ VIYCL OEKGD GGEZN"
				+ "WXFUL QTWPA TPXFW PRJHT BFVTT KMUGC RBSUF DBTZG WYRMC"
				+ "TRLSX JGIWD GSQWR WXAER LQXGQ CTTWK KKFIB AGRLS IOVZC"
				+ "CVSCE BPEWV KJTDB QNJTW UGFDI ASULZ YXQVS SIMVK JMCXV"
				+ "GJYIE CQBGC ZOVZR LBHJV WTLVC CDREC UVBIA WUFLT BGVFM"
				+ "HBARC C";
		
		String ciphertext2 = "CHREEVOAHMAERATBIAXXWTNXBEEOPHBSBQMQEQERBW"
				+ "RVXUOAKXAOSXXWEAHBWGJMMQMNKGRFVGXWTRZXWIAK"
				+ "LXFPSKAUTEMNDCMGTSXMXBTUIADNGMGPSRELXNJELX"
				+ "VRVPRTULHDNQWTWDTYGBPHXTFALJHASVBFXNGLLCHR"
				+ "ZBWELEKMSJIKNBHWRJGNMGJSGLXFEYPHAGNRBIEQJT"
				+ "AMRVLCRREMNDGLXRRIMGNSNRWCHRQHAEYEVTAQEBBI"
				+ "PEEWEVKAKOEWADREMXMTBHHCHRTKDNVRZCHRCLQOHP"
				+ "WQAIIWXNRMGWOIIFKEE";
		
		String ciphertext3 = "TTGFFPWEUVGUSKEHGKIETDALEBJWZJTGUIEKWWLORHZM"
				+ "SHFUMBYHZMPNFJMAMWFBBTJWZLWSWXETJAVRPVWZRAS"
				+ "DILYCJIYHBYBVFSNMERAMKUUFMQFXRKQPDOFLHGOTTR"
				+ "MCEWIXOKENKAGNUNBYZLUZGWQLIUSVGUXTVXGKMGMZW"
				+ "LHICFPVFOZMQZSZWTIOKAVGUTGFTKZQFTBYCVLVSVQB"
				+ "BICVKSVQSASKPBNZVLEBJWIJTMLPRYZAMFMVSBJXFWB"
				+ "BKAWVGBBYPVF";
		
		String ciphertext4 = "EIYAZSDXDDRDOSPPGVHPZJCFDIOAACWWZXRGWDSQZVRGT"
				+ "EGVLCDMVRWRPTRRJOSATRRIZGACWGLZGHRLPDVRRPETRAQYCQGIUXPPG"
				+ "VHPZJSCUDSQFSIVTQFKKSNTLCXCPQNFHPFXYCILWABRDXOEGWQQXQOIW"
				+ "VTFGZHKYPVTBYFDVRPOZRGVHCPIUWFRLDROOBPMQMVKEUNHHNZFUSUCX"
				+ "AESKEYSEMZSWXPCPOTZGVHSCBYOFOLZQKLVWPEWQUFBNZODSQOZRYOUU"
				+ "OYOWQSH";

		// 
		int maxkey = 8;
		for(int key=4; key<=maxkey; key++) {
			StringBuffer keysb = new StringBuffer();
			for(int i=0; i<key; i++) {
				String yi = FrequencyAnalysis.reduceToMonoAlphabetic(key, i, ciphertext1);
				List<Double> freqyi = FrequencyAnalysis.keyFromMonoAlphabeticRow(yi);
				keysb.append(FrequencyAnalysis.closestToStandardIc(freqyi));
			}
			System.out.println("Guessed key = "+keysb.toString().trim());
			
			// try to decrypt using the guessed key and the your Vigenere cipher
			char[] keys = keysb.toString().trim().toCharArray();
			
			Vignere vg = new Vignere(keys);
			String plaintext = vg.decrypt(ciphertext1);
			System.out.println(plaintext);
		}
		
		
		// assertEquals();
		
	}

}
