package com.bqh_2021;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.bqh_2021.Entidades.Clases.User;
import com.bqh_2021.Entidades.Clases.UserSecurityManager;

import org.junit.Test;

public class UserTest {
    
    @Test
    public void passwordIsEncryptedCorrectly()
    {
        final String pass_sin_cifrar = "pass_super_segura";

        User user = new User(0, "apodo", "correo@correo.com", "pass_super_segura", true);

        assertTrue(pass_sin_cifrar == user.getPassword());

        UserSecurityManager securityManager = new UserSecurityManager();

        securityManager.encryptPasswordAES(user);

        assertTrue(pass_sin_cifrar != user.getPassword());
    }

    @Test(expected = RuntimeException.class)
    public void nonValidEmailIsDetected()
    {
        final String badEmail = "correoMalHecho.com";

        try {
            User user = new User(0, "apodo", badEmail, "pass_super_segura", true);
            user.getIsAdult(); //Para quitar el Warning
        } catch (RuntimeException rE) {throw rE;}
    }

    @Test
    public void toStringFormatIsCorrect()
    {
        User user = new User(0, "apodo", "correo@correo.com", "pass_super_segura", true);

        final String correctFormat = user.getEmail()+" "+user.getPassword();
        assertEquals(correctFormat, user.toString());
    }

    @Test
    public void userIDIncreasesCorrectly()
    {
        User firstUser = new User("paco", "paco@tabaco.com", "1234", true, true);
        User secondUser = new User("jhon", "jhon@salchichon.com", "abcd", true, true);

        assertTrue((firstUser.getUserID()+1) == secondUser.getUserID());
    }
}
