package com.bqh_2021;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.bqh_2021.Entidades.Clases.EmailValidator;

import org.junit.Test;

public class EmailValidatorTest {
    @Test
    public void emailValidatorTest()
    {
        final String email1 = "pacotabaco@gmail.com";
        final String email2 = "pepelu@peluqueria.com";
        final String email3 = "manolitoSilverFoot@gmail.com";

        final String email4 = "pacotabaco.com";
        final String email5 = "pepelu/correo.com";
        final String email6 = "@manolitoSilverFoot.com";

        assertTrue(EmailValidator.emailFormatIsValid(email1));
        assertTrue(EmailValidator.emailFormatIsValid(email2));
        assertTrue(EmailValidator.emailFormatIsValid(email3));
        
        assertFalse(EmailValidator.emailFormatIsValid(email4));
        assertFalse(EmailValidator.emailFormatIsValid(email5));
        assertFalse(EmailValidator.emailFormatIsValid(email6));
    }
}
