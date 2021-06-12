package com.bqh_2021.Cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;

import com.bqh_2021.Entidades.Clases.BQH;
import com.bqh_2021.Entidades.Clases.Order;
import com.bqh_2021.Entidades.Interfaces.IProduct;

import java.lang.Character;


/**
 * CLIMenus
 * 
 * @author Javier Martin Lloret
 * @version 1.1
 * @since 2
 * 
 */
public class CLIMenus {

    Scanner IO;
    BQH BqhSystem;

    public CLIMenus(Scanner IO, BQH BqhSystem)
    {
        this.IO = IO;
        this.BqhSystem = BqhSystem;
    }

    public void startCLI()
    {
        
        char optionSelected = 'x';

        while(optionSelected != 'Q'){
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Bienvenido al terminal BQH");
            System.out.println("--------------------------");
            System.out.println("1. Crear Pedido");
            System.out.println("2. Consultar Caja del día");
            System.out.println("Q. Salir");
            System.out.println("--------------------------");
            System.out.print("Introduzca su elección: ");
            
            do {
                optionSelected = evaluateInput(IO.next().charAt(0) , 2, true, false);
            } while (optionSelected == 'x');

            switch(Character.getNumericValue(optionSelected)){
                case 1:
                    orderMenu(BqhSystem.createOrder());
                    break;
                case 2:
                    dayIncomeMenu();
                    break;
            }

        }

        BqhSystem.save();
    }

    public void orderMenu(int orderID) {
        
        char optionSelected = 'x';
        boolean vengoDePagar = false;

        while(optionSelected != 'R' && !vengoDePagar){

            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Pedido en curso ("+orderID+")");
            System.out.println("-----------------------------");
            Order order = BqhSystem.getOrderFromId(orderID);
            int i = 1;

            HashMap<IProduct, Integer> productos = order.getItems();
            for (IProduct product : productos.keySet()) {
                System.out.println(i+ ". "+product.getName()+" ("+order.getItems().get(product)+") * "+product.getPrice()+"€");
                i++;
            }
            System.out.println("-----------------------------");
            System.out.println("1. Anadir Producto");
            System.out.println("2. Quitar Producto");
            System.out.println("3. Terminar y pagar");
            System.out.println("-----------------------------");
            System.out.println("R. Volver atras. (Finaliza Pedido)");
            
            System.out.print("Introduzca su elección: ");

            do {
                optionSelected = evaluateInput(IO.next().charAt(0) , 3, false, true);
            } while (optionSelected == 'x');
        
            switch(Character.getNumericValue(optionSelected)){
                case 1:
                    itemCategoryChoosingMenu(orderID);
                    break;
                case 2:
                    removeItemMenu(orderID);
                    break;
                case 3:
                    vengoDePagar = payMenu(orderID);
                    break;
                default:
                    if(BqhSystem.getOrderFromId(orderID) != null)
                        BqhSystem.endOrder(orderID);
            }
        }
    }

    public void itemCategoryChoosingMenu(int orderID) {
        char optionSelected = 'x';
        SortedSet<String> categories = BqhSystem.getProductCategories();

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Seleccione la categoría de producto");
        System.out.println("-----------------------------------");
        int numCategories = categories.size();
        int i = 1;
        for (String currentCategory : categories) {
            System.out.println(i+". "+currentCategory);
            i++;
        }
        System.out.println("-----------------------------------");
        System.out.println("R. Volver atras.");
        System.out.print("Introduzca su elección: ");

        do {
            optionSelected = evaluateInput(IO.next().charAt(0) , numCategories, false, true);
        } while (optionSelected == 'x');
        
        if(optionSelected != 'R')
            itemChoosingMenu(categories.toArray()[Character.getNumericValue(optionSelected -1)].toString(), orderID);
    }

    public void itemChoosingMenu(String prodCategory, int orderID) {
        char optionSelected = 'x';

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Seleccione el producto");
        System.out.println("-----------------------------------");
        
        HashMap<String, List<IProduct>> productos = BqhSystem.getProducts();
        List<IProduct> productosAMostrar = productos.get(prodCategory);
        int numCategories = productosAMostrar.size();

        if(prodCategory.equals("Menu")){
            int i = 1;
            for (IProduct currentProduct : productosAMostrar) {
                System.out.println(i+". "+currentProduct.toString());
                i++;
            }
        }
        else{
            int i = 1;
            for (IProduct currentProduct : productosAMostrar) {
                System.out.println(i+". "+currentProduct.getName());
                i++;
            }
        }
        

        /* Si hay más de 9 productos por categoría, habrá que implementar otra forma de control o paginación*/
        System.out.println("-----------------------------------");
        System.out.println("R. Volver atras.");
        System.out.print("Introduzca su elección: ");

        do {
            optionSelected = evaluateInput(IO.next().charAt(0) , numCategories, false, true);
        } while (optionSelected == 'x');
        
        if(optionSelected != 'R')
        {
            IProduct productoSeleccionado = productosAMostrar.get(Character.getNumericValue(optionSelected -1));
            int qty = choosingItemQuantityMenu(orderID, productoSeleccionado);
            BqhSystem.addToOrder(orderID, productoSeleccionado, qty);
        }
    }

    public int choosingItemQuantityMenu(int orderID, IProduct product)
    {
        char optionSelected = 'x';
        int stock_producto = product.getStock();
        stock_producto = (stock_producto < 10) ? stock_producto: 9;

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Pedido en curso ("+orderID+")");
        System.out.println("-----------------------------");
        System.out.println("Selecciona el número de unidades del producto '"+product.getName()+"'");
        System.out.println("Máximo "+stock_producto+" unidades. 10 == 1");        
        System.out.println("-----------------------------");
        System.out.print("Introduzca su elección: ");        

        do {
            System.out.flush();
            optionSelected = evaluateInput(IO.next().charAt(0) , stock_producto, false, false);
        } while (optionSelected == 'x');
        return Character.getNumericValue(optionSelected);
    }

    public int choosingItemQuantityMenu(int orderID, IProduct product, int MaxUnitsToEliminate)
    {
        char optionSelected = 'x';

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Pedido en curso ("+orderID+")");
        System.out.println("-----------------------------");
        System.out.println("Selecciona el número de unidades del producto '"+product.getName()+"'");            
        System.out.println("-----------------------------");
        System.out.print("Introduzca su elección: ");

        do {
            optionSelected = evaluateInput(IO.next().charAt(0) , MaxUnitsToEliminate, false, false);
        } while (optionSelected == 'x');

        return Character.getNumericValue(optionSelected);
    }

    public void removeItemMenu(int orderID)
    {
        char optionSelected = 'x';

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Eliminar producto del pedido");
        System.out.println("-----------------------------------");

        Order order = BqhSystem.getOrderFromId(orderID);
        int numberOfItemsInTheOrder = order.getItems().size();
        int i = 1;

        HashMap<IProduct, Integer> productos = order.getItems();
        ArrayList<IProduct> estructuraAuxiliarWenaGente = new ArrayList<>();
        for (IProduct product : productos.keySet()) {
            System.out.println(i+ ". "+product.getName()+" ("+order.getItems().get(product)+") * "+product.getPrice()+"€");
            i++;
            estructuraAuxiliarWenaGente.add(product);
        }
        System.out.println("-----------------------------------");
        System.out.println("R. Volver atras.");
        System.out.print("Introduzca su elección: ");

        do {
            optionSelected = evaluateInput(IO.next().charAt(0) , numberOfItemsInTheOrder, false, true);
        } while (optionSelected == 'x');
        
        if(optionSelected != 'R')
        {
            IProduct productoAEliminar = estructuraAuxiliarWenaGente.get(Character.getNumericValue(optionSelected -1));
            BqhSystem.removeFromOrder(orderID, productoAEliminar, choosingItemQuantityMenu(orderID, productoAEliminar, productos.get(productoAEliminar)));
        }
            
            
    }

    public boolean payMenu(int orderID)
    {
        char optionSelected = 'x';

        Order orden = BqhSystem.getOrderFromId(orderID);

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Pedido en curso ("+orderID+")");
        System.out.println("-----------------------------");
        Order order = BqhSystem.getOrderFromId(orderID);
            int i = 1;

            HashMap<IProduct, Integer> productos = order.getItems();
            for (IProduct product : productos.keySet()) {
                System.out.println(i+ ". "+product.getName()+" ("+order.getItems().get(product)+") * "+product.getPrice()+"€");
                i++;
            }
        System.out.println("Total a pagar: "+ orden.getPrice()+"€");
        System.out.println("-----------------------------");
        System.out.println("1. Pagar y finalizar pedido");
        System.out.println("R. Volver a la pantalla anterior");
        System.out.print("Introduzca su elección: ");

        do {
            optionSelected = evaluateInput(IO.next().charAt(0) , 1, false, true);
        } while (optionSelected == 'x');
        
        if(optionSelected != 'R')
        {
            BqhSystem.endOrder(orderID);
            return true;
        }
        else
            return false;
    }

    public void dayIncomeMenu()
    {
        char optionSelected = 'x';
        
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Consulta la caja de hoy");
        System.out.println("-----------------------");
        System.out.println("Pedidos registrados: ");
        System.out.println("Caja: "+BqhSystem.getDayIncome()+" euros");
        System.out.println("-----------------------");
        System.out.println("R. Volver a la pantalla anterior");
        System.out.print("Introduzca su elección: ");

        do {
            optionSelected = IO.next().charAt(0);
        } while (optionSelected != 'R');
    }
    /**
     * @author Javier Martin Lloret && Juan Carlos Lucena
     * @version 1.1
     * @since 2
     * 
     * @param optionSelected Entrada del usuario
     * @param basicOptionNumbers Numero de opciones disponibles sin contar el retorno y la salida
     * @param hasQuitButton Indica si existe botón de salida o 'Q'
     * @param hasReturnButton Indica si existe botón de retorono o 'R'
     * 
     * @return Opcion válida seleccionada por el usuario. El caracter 'x' se utiliza como entrada no válida
     */
    private char evaluateInput(char optionSelected, int basicOptionNumbers, boolean hasQuitButton, boolean hasReturnButton)
    {
        if(hasQuitButton && (optionSelected == 'Q'))
            return 'Q';
        if(hasReturnButton && (optionSelected == 'R'))
            return 'R';

        return ((Character.getNumericValue(optionSelected) >= 1) &&
            (Character.getNumericValue(optionSelected) <= basicOptionNumbers)) 
            ? optionSelected : 'x';
    }
}