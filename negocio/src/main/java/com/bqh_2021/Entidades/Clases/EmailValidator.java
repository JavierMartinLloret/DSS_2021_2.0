package com.bqh_2021.Entidades.Clases;

public class EmailValidator {
    private static final String validEmailRegExp = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

    public static boolean emailFormatIsValid(String email)
    {
        return email.matches(validEmailRegExp);
    }
}
