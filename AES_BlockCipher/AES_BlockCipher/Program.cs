using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Security.Cryptography;


/**
 * Default key size is 256bits
 */
namespace AES_BlockCipher
{
    public class AES_Rijndael
    {
        private RijndaelManaged myRijndael = new RijndaelManaged();
        
        // Create an RijndaelManaged object 
        // with the specified key and IV. 
        public AES_Rijndael(int keySize, string mode)
        {
            myRijndael.KeySize = keySize;
            myRijndael.GenerateKey();
            myRijndael.GenerateIV();

            // TODO: option for other mode
            myRijndael.Mode = CipherMode.ECB;
            // TODO: study other options for padding 
            myRijndael.Padding = PaddingMode.Zeros;
        }

        public string Encryption(string data)
        {
            StringBuilder output = new StringBuilder("");
            
            // Create a encrytor to perform the stream transform.
            ICryptoTransform encryptor = myRijndael.CreateEncryptor(myRijndael.Key, myRijndael.IV);
            // Encrypt the string to an array of bytes.
            byte[] encrypted;
            // Create the streams used for encryption. 
            using (MemoryStream msEncrypt = new MemoryStream())
            {
                using (CryptoStream csEncrypt = new CryptoStream(msEncrypt, encryptor, CryptoStreamMode.Write))
                {
                    using (StreamWriter swEncrypt = new StreamWriter(csEncrypt))
                    {
                        //Write all data to the stream.
                        swEncrypt.Write(data);
                    }
                    encrypted = msEncrypt.ToArray();
                }
            }
            foreach(var b in encrypted)
            {
                output.Append(b.ToString("x")); // in hex format
            }

            return output.ToString();
        }

        public string getKey()
        {
            StringBuilder key = new StringBuilder("");
            byte[] rawKey = myRijndael.Key;
            foreach (var k in rawKey)
            {
                key.Append(k.ToString("x")); // in hex format
            }
            return key.ToString();
        }

    }


    public class AES
    {
        public static void Main(string[] args)
        {
            string plaintext = @"D:\Cryptography\AES_BlockCipher\plaintext.txt";

            var input = new StreamReader(plaintext);
            string data = input.ReadToEnd();
            input.Close();

            int keySize = 256;
            string mode = "ECB";
            AES_Rijndael ciphertext = new AES_Rijndael(keySize, mode);
            string output = ciphertext.Encryption(data);
            string key = ciphertext.getKey();

            Console.WriteLine("Cipher:\n" + output);
            Console.WriteLine("Key:\n" + key);

            
        }
    }
}
