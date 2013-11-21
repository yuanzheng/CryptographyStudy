
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import javax.crypto.*;

public class AES {
	protected BufferedReader is = null;
	
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
	
	public void Encryption(String fileName)
	{
		String content = getContent(fileName);
		
		KeyGenerator keygen = null;
		try {
			keygen = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    SecretKey aesKey = keygen.generateKey();
		Cipher aesCipher = null;
		try {
			aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Initialize the cipher for encryption
	    try {
			aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    try {
			byte[] ciphertext = aesCipher.doFinal(content.getBytes());
			for(byte b : ciphertext)
			{
				System.out.print(String.format("%x", b));
			}
			System.out.println();
			for(byte b : ciphertext)
			{
				System.out.print(String.format("%02x", b&0xff));
			}
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
	}
        
	public static void main(String[] args)
	{
		AES encryption = new AES();
		String content = encryption.getContent("plaintext.txt");
		System.out.println(content.toString());
		
		encryption.Encryption("plaintext.txt");
		
		
	}

}