package com.bqh_2021.Entidades.Clases;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.bqh_2021.File_DAO.UserSecurityService;




public class UserSecurityManager {

    // Acceso concurrente???
    private static Map<String, SecretKey> user_key;
    private static UserSecurityService userSecurityService;

    private static final UserSecurityManager SINGLE_INSTANCE = new UserSecurityManager();

    public static UserSecurityManager getInstance(){
        return SINGLE_INSTANCE;
    }

    public UserSecurityManager()
    {
        userSecurityService = new UserSecurityService();
        user_key = userSecurityService.GetUsersKeys();
    }

    public void encryptPasswordAES(User user) throws RuntimeException
    {
        SecretKey key = createSecutiryKeyAES();
        user_key.put(user.getEmail(), key);
        try{
            encryptPasswordAESPrivate(user, key);
        } catch(Exception e){throw new RuntimeException("Error al encriptar la contraseña");}
        closeUserSecurityManger();
    }

    private SecretKey createSecutiryKeyAES() throws RuntimeException
    {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128); // De momento es suficiente
            return keyGenerator.generateKey();
        } catch(NoSuchAlgorithmException e){throw new RuntimeException("Error, no se reconoce AES como algoritmo");}
    }

    private void encryptPasswordAESPrivate(User user, SecretKey key) throws RuntimeException{
        final String input = user.getPassword();

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cipherText = cipher.doFinal(input.getBytes());
            user.setPassword(Base64.getEncoder().encodeToString(cipherText), true);
        } catch (Exception e){throw new RuntimeException("Error al encriptar la contraseña");}
    }

    public void closeUserSecurityManger(){
        userSecurityService.PostUsersKeys(user_key);
    }
}
