/**
 * AES
 * Block Cipher Modes: ECB, 
 */
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	public byte[] Encryption(String fileName, String mode)
	{
		//String content = getContent(fileName);
		byte[] ciphertext = null;
	    try {
	    	Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			// Initialize the cipher for encryption
			aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
			// get file location
			Path path = Paths.get(fileName);
			byte[] data = Files.readAllBytes(path);
			ciphertext = aesCipher.doFinal(data);//(content.getBytes());
			
			
			
			
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return ciphertext;
	}
	
	
	// TODO
	public String Decryption(byte[] ciphertext, String mode)
	{
		String plaintext = "";
		try {
			Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			// Initialize the cipher for encryption
			aesCipher.init(Cipher.DECRYPT_MODE, aesKey);
			byte[] plainData = aesCipher.doFinal(ciphertext);
			// convert bytes into string
			plaintext= new String(plainData);
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return plaintext;
		
	}
	
	private void printCipherText(byte[] ciphertext)
	{
		//System.out.println();
		/*
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
		*/
		// Better way ...
		BigInteger bi = new BigInteger(1, ciphertext);
		String result = String.format("%0" + (ciphertext.length<<1) + "X", bi);
		System.out.println(result);
		
	}
	
	
	public static void main(String[] args)
	{
		int size = 128;
		AES encryption = new AES(size);
		// TEST read file correctly
		String content = encryption.getContent("plaintext.txt");
		//System.out.println(content.toString());
		encryption.printFile("plaintext.txt");
		
		// Encrypt Message
		byte[] ciphertext = encryption.Encryption("plaintext.txt", "ECB");
		System.out.println("Cipher Text:");
		encryption.printCipherText(ciphertext);
		
		String plaintext = encryption.Decryption(ciphertext, "ECB");
		System.out.println("Plain Text:");
		System.out.println(plaintext);
	}

}
