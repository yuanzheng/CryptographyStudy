
Saving some sample codes did in my spare time. They're all about cryptographic algorithms.
I want to implement the standard and secure cryptographic algorithms in my own version using different programming languages.
My goal is to implement all cryptographic algorithms by using best practices and patterns. I wish my version is fast.


AES (Advanced Encryption Standard)
1. AES_BlockCipher (in C#)

It is a sample how to use AES encryption messages. It is a C# edition.
Sevearl ways to do it, however I use two ways to implement it.
    a) RijndaelManaged: using the Rijndael algorithm
    b) AesManaged: Provide a managed implementation of the AES symmetric algorithm
Default, it uses CBC mode.
Both ciphertext and key are in Hexdecimal.

First sample in is ECB mode, ECB is not considered secure, because it doesn't use an initialization vector to initicalize the first plaintext block. For a given secret key k, a simple block cipher that does not use an initialization vector will encrypt the same input block of plaintext into the same output block of ciphertext. Therefore, if you have duplicate blocks in your input plaintext stream, you will have duplicate blocks in your output ciphertext stream. These duplicate output blocks alert unauthorized users to the weak encryption used the algorithms that might have been employed, and the possible modes of attack. The ECB cipher mode is therefore quite vulnerable to analysis, and ultimately, key discovery.


2. AES_BlockCipher (in Java)





3. AES_BlockCipher (in Javascript)






4. AES_BlockCipher (in Python)