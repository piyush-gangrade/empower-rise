// package com.empower.config;

// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.io.Encoders;
// import io.jsonwebtoken.security.Keys;

// import javax.crypto.SecretKey;

// public class GenerateSecretKey {
//     public static void main(String[] args) {
//         // Generate a secure key for the HS256 algorithm (32 bytes for 256-bit encryption)
//         SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//         String encodedKey = Encoders.BASE64.encode(secretKey.getEncoded());
//         System.out.println("Your Base64-encoded secret key: " + encodedKey);
//     }
// }