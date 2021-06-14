package com.bqh_2021.clases;

import java.util.Random;

public class PaymentCodeGenerator {
    private static final int size = 5;
    private static Random ranGen;
    private static final String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
   
    public static String generateCode()
    {
        ranGen = new Random();
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
