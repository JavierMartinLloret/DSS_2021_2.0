package com.bqh_2021;

/**
 * Esta rutina define el comportamiento del CLI del hito 2
 *
 */


import java.util.Scanner;

import com.bqh_2021.Cli.CLIMenus;
import com.bqh_2021.Entidades.Clases.BQH;

public class AppCLI 
{

    public static BQH BqhSystem;
    public static void main( String[] args )
    {
        Scanner IO = new Scanner(System.in);
        BqhSystem = new BQH("esi@gmail.com"); // Se crea un sistema de 0.
        CLIMenus ClienteCLI = new CLIMenus(IO, BqhSystem);
        ClienteCLI.startCLI();
    }
}