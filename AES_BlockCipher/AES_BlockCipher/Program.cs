using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Security.Cryptography;

namespace AES_BlockCipher
{
    public class AES_Rijndael
    {
        public StringBuilder Encryption(string data)
        {
            StringBuilder output = new StringBuilder("");

            using (RijndaelManaged myRijndael = new RijndaelManaged())
            {
                myRijndael.GenerateKey();
                myRijndael.GenerateIV();
                // Encrypt the string to an array of bytes. 
                byte[] encrypted = EncryptStringToBytes(data, myRijndael.Key, myRijndael.IV);

                foreach(var b in encrypted)
                {
                    output.Append(b.ToString("x"));
                }
            }

            return output;
        }

        private byte[] EncryptStringToBytes(string plainText, byte[] Key, byte[] IV)
        {
            byte[] encrypted;
            // Create an RijndaelManaged object 
            // with the specified key and IV. 
            using (RijndaelManaged rijAlg = new RijndaelManaged())
            {
                rijAlg.Key = Key;
                rijAlg.IV = IV;
                rijAlg.Mode = CipherMode.ECB;
                rijAlg.Padding = PaddingMode.Zeros;

                // Create a decrytor to perform the stream transform.
                ICryptoTransform encryptor = rijAlg.CreateEncryptor(rijAlg.Key, rijAlg.IV);

                // Create the streams used for encryption. 
                using (MemoryStream msEncrypt = new MemoryStream())
                {
                    using (CryptoStream csEncrypt = new CryptoStream(msEncrypt, encryptor, CryptoStreamMode.Write))
                    {
                        using (StreamWriter swEncrypt = new StreamWriter(csEncrypt))
                        {

                            //Write all data to the stream.
                            swEncrypt.Write(plainText);
                        }
                        encrypted = msEncrypt.ToArray();
                    }
                }
            }

            return encrypted;
        }
    }


    public class AES
    {
        public static void Main(string[] args)
        {
            string plaintext = @"D:\Cryptography\AES_BlockCipher\plaintext.txt";

            var input = new StreamReader(plaintext);
            string data = input.ReadToEnd();

            AES_Rijndael ciphertext = new AES_Rijndael();
            StringBuilder output = ciphertext.Encryption(data);


            Console.WriteLine(output.ToString());
            input.Close();
        }
    }
}
