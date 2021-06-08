package com.bqh_2021;

/**
 * Esta rutina define el comportamiento del CLI del hito 2
 *
 */
import com.BQH_2021.AppCli.Frontend.CLIMenus;
import com.BQH_2021.Negocio.Entidades.Clases.BQH;

import java.util.Scanner;

public class AppCLI 
{
    public static void main( String[] args )
    {
        Scanner IO = new Scanner(System.in);
        BQH BqhSystem = new BQH("esi@gmail.com"); // Se crea un sistema de 0.
        CLIMenus ClienteCLI = new CLIMenus(IO, BqhSystem);
        ClienteCLI.startCLI();
    }
}