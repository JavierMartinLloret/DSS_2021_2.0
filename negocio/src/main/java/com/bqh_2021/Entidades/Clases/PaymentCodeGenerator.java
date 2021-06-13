package com.bqh_2021.Entidades.Clases;

import java.util.Random;

public class PaymentCodeGenerator {
    private static final int size = 5;
    private static final Random ranGen = new Random((long)Math.random());
    private static final String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String generateCode()
    {
        String validationCode = new String();
        for (int i = 0; i < size; i++) {
            if(ranGen.nextInt(2)%2 == 0) //50%
                validationCode += characters.charAt(ranGen.nextInt(10)); //Numero
            else
                validationCode += characters.charAt(10 + ranGen.nextInt(26)); //Caracter
        }

        return validationCode;
    }
}
