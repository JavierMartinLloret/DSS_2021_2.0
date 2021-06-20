package com.bqh_2021.Entidades.Clases;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bqh_2021.Abstract_Factory_DAO.Interfaces.ICafeteriaDAO;
import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IFactoryDAO;
import com.bqh_2021.Entidades.Interfaces.IProduct;
import com.bqh_2021.Utils.PersistenceConfiguration;

public class Cafeteria {

    protected BQH bqhSystem;
    protected String kitchenEmail; //Único
    
    protected Map<String, List<OrderWithUserAndDate>> currentOpenedOrders; // Histórico de pedidos para la persistencia
    protected Map<String, List<OrderWithUserAndDate>> orderArchive; // Histórico de pedidos para la persistencia

    protected static IFactoryDAO factoryDAO = PersistenceConfiguration.LoadPersistenceType();

    // Constructores
    /**
     * Cafeteria
     * @author Javier Martín-Lloret
     * @version 1.0
     * @since 3.0
     * 
     * 
     * @param kitchenEmail email de la cocina que además la identifica como única.
     * 
     * @throws RuntimeException error de formato en el email
     */
    public Cafeteria(String kitchenEmail) throws RuntimeException
    {
        bqhSystem = new BQH(kitchenEmail);
        if(EmailValidator.emailFormatIsValid(kitchenEmail))
            this.kitchenEmail = kitchenEmail;
        else
            throw new RuntimeException("Error, el email del usuario no cumple el formato *@*.com");
        currentOpenedOrders = new HashMap<String, List<OrderWithUserAndDate>>();

        ICafeteriaDAO cafeteriaDAO = factoryDAO.createCafeteriaDAO(kitchenEmail);
        orderArchive = cafeteriaDAO.getOrders();
    }

    //TODO: Eliminar la dependencia modificando el sistema
    /**
     * Cafeteria
     * @author Javier Martín-Lloret
     * @version 1.0
     * @since 3.0
     * 
     * 
     * @param kitchenEmail email de la cocina que además la identifica como única
     * @param override flag para avisar de que No instancia servicio ni histórico
     * 
     * @throws RuntimeException error de formato en el email
     * 
     * Este constructor existe por una dependencia asociada a la persistencia de ficheros. Su eliminación es cuestión
     * de tiempo.
     */
    @Deprecated
    public Cafeteria(String kitchenEmail, boolean override) throws RuntimeException
    {
        bqhSystem = new BQH(kitchenEmail);
        if(EmailValidator.emailFormatIsValid(kitchenEmail))
            this.kitchenEmail = kitchenEmail;
        else
            throw new RuntimeException("Error, el email del usuario no cumple el formato *@*.com");
        currentOpenedOrders = new HashMap<String, List<OrderWithUserAndDate>>();
    }

    /**
     * @author Juan Carlos Lucena Monje
     * @version 1.0
     * @since 3.0
     * 
     * @param clientEmail
     * @return Conjunto de ordenes ya archivadas del cliente en esta cafetería concreta. Puede ser vacío.
     */
    public Set<Order> getArchivedOrdersFromUser(String clientEmail){
        Set<Order> s = new HashSet<Order>();
        if(orderArchive.containsKey(clientEmail)){
            for(OrderWithUserAndDate o: orderArchive.get(clientEmail)){
                Order u = bqhSystem.getClosedOrderFromId(o.orderID);
                if(u != null){
                    s.add(u);
                }
            }
        }
        return s;
    }

    /*  METODOS DERIVADOS DE BQH ADAPTADOS A LAS NUEVAS NECESIDADES DE CAFETERÍA    */

    public Map<String, List<IProduct>> getProductsFromCafeteria()
    {
        return bqhSystem.getProducts();
    }

    public int createNewOrder(User client, Date orderDate)
    {
        int orderID = bqhSystem.createOrder();
        OrderWithUserAndDate newOrder = new OrderWithUserAndDate(client, this, orderDate, orderID);

        if(!currentOpenedOrders.containsKey(client.getEmail())) // Cliente no figura en pedidos abiertos desde inicio.
            currentOpenedOrders.put(client.getEmail(), new ArrayList<OrderWithUserAndDate>());

        currentOpenedOrders.get(client.getEmail()).add(newOrder);

        return orderID;
    }

    public void addToOrder(User client, int orderID, IProduct item, int quantity) throws RuntimeException
    {
        getOpenedOrderFormID(client, orderID).addItem(item, quantity);
    }

    public void removeFromOrder(User client, int orderID, IProduct item) throws RuntimeException
    {
        getOpenedOrderFormID(client, orderID).removeItem(item);
    }

    public void removeFromOrder(User client, int orderID, IProduct item, int quantity) throws RuntimeException
    {
        getOpenedOrderFormID(client, orderID).removeItem(item, quantity);
    }

    public void endOrder(User client, int orderID) throws RuntimeException
    {
        // Actualizamos caja
        Order orderToClose = getOpenedOrderFormID(client, orderID); // Comprueba la existencia de la orden en currentOppenedOrders
        if(orderToClose != null)
        {
            bqhSystem.endOrder(orderID);

            /* Pasamos la orden de las abiertas al histórico */
            // Eliminamos de las abiertas
            OrderWithUserAndDate orderToArchieve = getOWUADFromId(client, orderID);
            currentOpenedOrders.get(client.getEmail()).remove(orderToArchieve);

            // Volcamos al histórico
            if(!orderArchive.containsKey(client.getEmail()))
                orderArchive.put(client.getEmail(), new ArrayList<OrderWithUserAndDate>());
            
            orderArchive.get(client.getEmail()).add(orderToArchieve);
            save();
        }
        else
            throw new RuntimeException("Error al cerrar la orden");
    }

    public IProduct getProductCafeteria(String itemName) throws RuntimeException
    {
        IProduct productToReturn = null;

        for (String category : bqhSystem.getProducts().keySet()) {
            List<IProduct> productsFromTheCategory = bqhSystem.getProducts().get(category);
            for (IProduct item : productsFromTheCategory) {
                if(item.getName().equals(itemName))
                    productToReturn = item;
            }
        }

        if(productToReturn == null)
            throw new RuntimeException("Error, no existe el producto buscado");
        
        return productToReturn;
    }

    public BQH getSystem()
    {
        return bqhSystem;
    }

    public void setBqhSystem(BQH bqhSystem)
    {
        this.bqhSystem = bqhSystem;
    }

    public String getKitchenEmail()
    {
        return kitchenEmail;
    }
    
    public void setKitchenEmail(String kitchenEmail)
    {
        this.kitchenEmail = kitchenEmail;
    }

    public Map<String, List<OrderWithUserAndDate>> getOrderArchive()
    {
        return orderArchive;
    }

    public void setOrderArchive(Map<String, List<OrderWithUserAndDate>> orderArchive)
    {
        this.orderArchive = orderArchive;
    }

    public void save()
    {
        bqhSystem.save();

        ICafeteriaDAO cafeteriaDAO = factoryDAO.createCafeteriaDAO(kitchenEmail);
        cafeteriaDAO.postOrders(orderArchive);
    }

    /**
     * @author Javier Martín-Lloret
     * @version 1.0
     * @since 3.0
     * 
     * @param client
     * @param orderID
     * 
     * @return OrderWithUserAndDate con identificador orderID del cliente referido por client
     * 
     * @throws RuntimeException En caso de intentarse acceder a una orden que no figura como abierta en el bqhsystem.
     */
    public OrderWithUserAndDate getOWUADFromId(User client, int orderID) throws RuntimeException
    {
        if(! clientHasThisOrderCurrentlyOppened(client, orderID))
            throw new RuntimeException("Error, el usuario no posee una orden abierta en el sistema con el identificador "+orderID);

        List<OrderWithUserAndDate> clientCurrentOrders = currentOpenedOrders.get(client.getEmail());
        OrderWithUserAndDate orderSought = null;

        for (OrderWithUserAndDate i : clientCurrentOrders)
            if(i.getOrderID() == orderID)
                orderSought = i;

        return orderSought;    
    }

    /**
     * @author Javier Martín-Lloret
     * @version 1.0
     * @since 3.0
     * 
     * @param client
     * @param orderID
     * 
     * @return Order de bqhsystem con identificador orderID
     * 
     * @throws RuntimeException En caso de intentarse acceder a una orden que no figura como abierta en el bqhsystem.
     */
    public Order getOpenedOrderFormID(User client, int orderID) throws RuntimeException
    {
        if(! clientHasThisOrderCurrentlyOppened(client, orderID))
            throw new RuntimeException("Error, el usuario no posee una orden abierta en el sistema con el identificador "+orderID);
        
        return bqhSystem.getOrderFromId(orderID);
    }

    //TODO: Refactor
    public Order getClosedOrderFromId(User client, int orderID) throws RuntimeException
    {
        if(orderArchive.containsKey(client.getEmail()))
        {
            List<OrderWithUserAndDate> personalArchive = orderArchive.get(client.getEmail());
            OrderWithUserAndDate orderSought = null;
            for (OrderWithUserAndDate i : personalArchive) {
                if(i.getOrderID() == orderID)
                    orderSought = i;
            }
            if(orderSought == null)
                throw new RuntimeException("No se encontró una orden con ID correspondiente a nombre del cliente con email "+client.getEmail());
            else
                return bqhSystem.getClosedOrderFromId(orderID);
        }
        else
            throw new RuntimeException("Error, no hay ordenes archivadas a nombre del cliente con email "+client.getEmail());
    }

    /* REFACTORING */
    /**
     * @author Javier Martín-Lloret
     * @version 1.0
     * @since 3.0
     * 
     * @param client
     * @param orderID
     * 
     * @return true en caso de que exista una orden abierta referida a client con identificador orderID
     * @return false en caso contrario
     */
    private boolean clientHasThisOrderCurrentlyOppened(User client, int orderID)
    {
        boolean itDoes = true;

        if(currentOpenedOrders.containsKey(client.getEmail()))
        {
            List<OrderWithUserAndDate> clientCurrentOrders = currentOpenedOrders.get(client.getEmail());
            OrderWithUserAndDate orderSought = null;
            for (OrderWithUserAndDate i : clientCurrentOrders) {
                if(i.getOrderID() == orderID)
                    orderSought = i;
            }
            itDoes = (orderSought != null) ? true : false;
        }
        else
            itDoes = false;

        return itDoes;
    }

    public Map<String, List<OrderWithUserAndDate>> getOpen(){
        return currentOpenedOrders;
    }


}
