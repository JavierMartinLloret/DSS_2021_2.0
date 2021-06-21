package com.bqh_2021.Entidades.Clases;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IFactoryDAO;
import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IUserSecurityDAO;
import com.bqh_2021.Utils.PersistenceConfiguration;





public class UserSecurityManager {

    private static Map<String, SecretKey> user_key;

    /*
    private static final UserSecurityManager SINGLE_INSTANCE = new UserSecurityManager();

    public static UserSecurityManager getInstance(){
        return SINGLE_INSTANCE;
    }
    */
    protected static IFactoryDAO factoryDAO = PersistenceConfiguration.LoadPersistenceType();

    public UserSecurityManager()
    {
        IUserSecurityDAO userSecurityDAO = factoryDAO.createUserSecurityDAO();
        user_key = userSecurityDAO.getUserSecurityKeys();
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
        IUserSecurityDAO userSecurityDAO = factoryDAO.createUserSecurityDAO();
        userSecurityDAO.postUserSecurityKey(user_key);
    }
}
