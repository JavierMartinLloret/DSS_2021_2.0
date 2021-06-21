package com.bqh_2021.Entidades.Clases;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;

import com.bqh_2021.Entidades.Interfaces.IProduct;

/**
 * Menu
 * @author Javier Martín-Lloret
 * @version 1.2
 * @since 1.0
 */
public class Menu implements IProduct{

    protected HashMap<IProduct, Integer> mProducts; //Los elementos del menú podrían ser una lista, el uso del mapa nos da versatilidad
    protected float discount; //Discount applicable to the sum of all prices ex: 0.15
    protected String name;
    protected BigDecimal price;

    // Constructores
    /**
     * @author Javier Martín-Lloret
     * @version 1.2
     * @since 1.0
     * 
     * @param name name que recibe el producto registrado.
     * @param discount descuento aplicado al precio final. Vg 0.5f = 50%
     * @param mProducts productos que contendrá el menú
     */
    public Menu(String name, float discount, HashMap<IProduct, Integer> mProducts) {
        this.name = name;
        this.discount = discount;
        this.mProducts = mProducts;
        price = BigDecimal.ZERO;
        for (IProduct item : this.mProducts.keySet()) {
            price = price.add(item.getPrice().multiply(new BigDecimal(this.mProducts.get(item))));
            /* Precio = Precio + preciodelitem x cantidad del item */
        }
        price = price.multiply(new BigDecimal(discount));
    }

    // Implementación de la interfaz IProduct
    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @author Javier Martín-Lloret
     * @version 1.1
     * @since 1.0
     * 
     * V1.1 El método muestra lo solicitado por el CLI del Hito 2
     */
    @Override
    public String toString()
    {
        String sentence = name+": [";

        int numItems = mProducts.size();
        Iterator<IProduct> it = mProducts.keySet().iterator();
        IProduct aux;
        int i = 0;
        //Recorro todos los items excepto el último
        while (i < numItems-1) {
            aux = it.next();
            sentence += aux.getName()+" + ";
            i++;
        }
        //Ultimo item
        aux = it.next();
        sentence += aux.getName()+"] ("+price+"€)";
        
        return sentence;
    }

    @Override
    public BigDecimal getPrice()
    {
        return price;
    }

    @Override
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    @Override
    public int getStock() {
        int min = Integer.MAX_VALUE;
        for (IProduct product : mProducts.keySet()) {
            if(product.getStock() < min)
                min = product.getStock();
        }
        return min;
    }

    @Override
    public void removeStock(int quantity) {
        for (IProduct product : mProducts.keySet()) {
            product.removeStock(quantity);
        }
    }

    @Override
    public void addStock(int quantity) {
        for (IProduct product : mProducts.keySet()) {
            product.addStock(quantity);
        }
    }

    public float getDiscount(){
        return this.discount;
    }

    public HashMap<IProduct, Integer> getProducts(){
        return this.mProducts;
    }
}
