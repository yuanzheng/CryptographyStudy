using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;


/**
 * Default key size is 256bits
 */
namespace AES_BlockCipher
{
 
    class AES
    {
        public static void Main(string[] args)
        {
            string plaintext = @"D:\Cryptography\AES_BlockCipher\plaintext.txt";

            var input = new StreamReader(plaintext);
            string data = input.ReadToEnd();
            input.Close();

            int keySize = 256;
            string mode = "ECB";
            Rijndael ciphertext = new Rijndael(keySize, mode);
            string output = ciphertext.Encryption(data);
            string key = ciphertext.getKey();

            Console.WriteLine("Cipher:\n" + output);
            Console.WriteLine("Key:\n" + key);

            
        }
    }
}
