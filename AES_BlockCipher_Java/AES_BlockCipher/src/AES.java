/**
 * AES
 * Block Cipher Modes: ECB, 
 */
import java.io.*;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import javax.crypto.*;

public class AES {
	protected BufferedReader is = null;

	private SecretKey aesKey = null;
	
	public String getContent(String fileName)
	{
		FileInputStream input = null;
		try{
			File file = new File(fileName);
			input = new FileInputStream(file);
		} catch (Exception e)
		{
			
		}
		BufferedInputStream bis = new BufferedInputStream(input);
		Scanner scanner = new Scanner(bis);

		StringBuilder content = new StringBuilder();
		while (scanner.hasNextLine()) {
			content.append(scanner.nextLine());
		}
		scanner.close();
		return content.toString();
	}
	
	public void printFile(String fileName)
	{
		try {
			BufferedReader input = new BufferedReader(new FileReader(fileName));
			
			String currLine;
			while((currLine=input.readLine())!=null)
			{
				System.out.println(currLine);
			}
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// initialize AES key
	public AES(int keySize)
	{
		try {
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			keygen.init(keySize);
		    aesKey = keygen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * TODO: 
	 * CTR mode: http://www.java2s.com/Code/JavaAPI/javax.crypto/CipherENCRYPTMODE.htm
	 * 
	 * AES/CBC/NoPadding (128)
	 * AES/CBC/PKCS5Padding (128)
	 * AES/ECB/NoPadding (128)
	 * AES/ECB/PKCS5Padding (128)
	 */	
	public void Encryption(String fileName, String mode)
	{
		String content = getContent(fileName);
		
	    try {
	    	Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			// Initialize the cipher for encryption
			aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
			byte[] ciphertext = aesCipher.doFinal(content.getBytes());
			
			printCipherText(ciphertext);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	// TODO
	public void Decryption()
	{
		
	}
	
	private void printCipherText(byte[] ciphertext)
	{
		for(byte b : ciphertext)
		{
			System.out.print(String.format("%x", b));
		}
		System.out.println();
		
		// The difference between solution above and down !!!!!
		for(byte b : ciphertext)
		{
			System.out.print(String.format("%02x", b));
		}
		System.out.println();
		
		// Better way ...
		BigInteger bi = new BigInteger(1, ciphertext);
		String result = String.format("%0" + (ciphertext.length<<1) + "X", bi);
		System.out.print(result);
		
	}
        
	public static void main(String[] args)
	{
		int size = 128;
		AES encryption = new AES(size);
		// TEST read file correctly
		String content = encryption.getContent("plaintext.txt");
		System.out.println(content.toString());
		encryption.printFile("plaintext.txt");
		
		// Encrypt Message
		encryption.Encryption("plaintext.txt", "ECB");
		
	}

}
