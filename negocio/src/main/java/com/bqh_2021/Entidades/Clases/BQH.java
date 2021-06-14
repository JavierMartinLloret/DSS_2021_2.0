package com.bqh_2021.Entidades.Clases;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IDayBoxDAO;
import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IFactoryDAO;
import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IOrderDAO;
import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IProdCategoryDAO;
import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IProductDAO;
import com.bqh_2021.Entidades.Interfaces.IProduct;
import com.bqh_2021.Utils.PersistenceConfiguration;


/**
 * BQH
 * 
 * @author Javier Martín-Lloret
 * @version 1.1
 * @since 1.0
 */
public class BQH {
    protected HashMap<String, List<IProduct>> products; // BD?
    protected String kitchenEmail;
    protected SortedSet<String> productCategories;
    protected HashMap<Integer, Order> currentOpenedOrders; // Ordenes que se han realizado en el día.
    protected Set<Order> closedOrders;
    protected Map<String, BigDecimal> dailyBox;
    protected SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY");

    protected static IFactoryDAO factoryDAO = PersistenceConfiguration.LoadPersistenceType();

    private int idOrderCounter = 0; // Este parámetro debería ser un autoincrementado de la BDD

    // Constructores
    /**
     * @author Javier Martín-Lloret
     * @version 1.5
     * @since 1.0
     */
    public BQH(String kitchenEmail)
    {
        IProductDAO productDAO = factoryDAO.createProductDAO(kitchenEmail);
        IProdCategoryDAO prodCategoryDAO = factoryDAO.createProdCategoryDAO();
        IOrderDAO orderDAO = factoryDAO.createOrderDAO(kitchenEmail);
        IDayBoxDAO dayBoxDAO = factoryDAO.createDayBoxDAO(kitchenEmail);

        this.kitchenEmail = kitchenEmail;
        currentOpenedOrders = new HashMap<Integer, Order>();
        dailyBox = dayBoxDAO.getDayBox();
        products = productDAO.getProducts();
        productCategories = prodCategoryDAO.getCategories();
        closedOrders = orderDAO.getOrders();    
        for(Order o: closedOrders){
            if(o.getId() > idOrderCounter){
                idOrderCounter = o.getId();
            }
        }
    }


    /**
     * getProducts
     * 
     * @author Javier Martín-Lloret
     * @version 1.0
     * @since 1.0
     * 
     * @return products o productos disponibles en el sistema.
     * 
     * Este método realmente no lo veo útil, al menos de forma pública. Quizás lo usemos para el tema de volcado en la BD.
     */
    public HashMap<String, List<IProduct>> getProducts()
    {
        return products;
    }

    public Set<Order> getOrders(){
        return closedOrders;
    }

    /**
     * createOrder
     * 
     * @author Javier Martin Lloret
     * @version 1.1
     * @since 1.0
     * 
     * @return Identificador de la comanda
     * 
     * Abrimos una nueva comanda en el sistema
     */
    public int createOrder()
    {
        idOrderCounter++;
        Order newOrder = new Order(idOrderCounter);
        currentOpenedOrders.put(idOrderCounter, newOrder);
        

        return idOrderCounter;
    }

    /**
     * addToOrder
     * 
     * @author Javier Martin Lloret
     * @version 1.1
     * @since 1.0
     * 
     * @param id identificador de la comanda
     * @param item producto o menu a añadir a la orden
     * @param quantity número de productos que se van a añadir
     * 
     * @throws RuntimeException En caso de querer añadir productos a una orden no registrada.
     * 
     * Añade a la orden actual del sistema un nuevo producto o menú
     */
    public void addToOrder(int id, IProduct item, int quantity) throws RuntimeException
    {
        Order currentOrder = currentOpenedOrders.get(id);
        if(currentOrder != null) // Existe la comanda en el sistema
        {
            currentOrder.addItem(item, quantity);
        }
        else
        {
            throw new RuntimeException("Error, se intenta acceder a una orden no inicializada en BQH");
        }
    }

    /**
     * removeFromOrder
     * 
     * @author Javier Martin Lloret
     * @version 1.1
     * @since 1.0
     * 
     * @param id id de la comanda a manipular
     * @param item producto o menu a eliminar de la orden
     * 
     * @throws RuntimeException Se intenta manipular una orden no registrada
     * 
     * Elimina a la orden actual del sistema un nuevo producto o menú
     */
    public void removeFromOrder(int id, IProduct item) throws RuntimeException
    {
        Order currentOrder = currentOpenedOrders.get(id);
        if(currentOrder != null)
        {
            currentOrder.removeItem(item);
        }
        else
        {
            throw new RuntimeException("Error, se intenta eliminar un item de una comanda no registrada en el sistema");
        }
    }

    /**
     * removeFromOrder
     * 
     * @author Javier Martin Lloret
     * @version 1.0
     * @since 1.1
     * 
     * @param id id de la comanda a manipular
     * @param item producto o menu a eliminar de la orden
     * @param quantity
     * 
     * @throws RuntimeException Se intenta manipular una orden no registrada o se intentan eliminar más productos de la comanda de los registrados en ella.
     * 
     * Elimina un numero concreto de ocurrencias del item en la comanda
     */
    public void removeFromOrder(int id, IProduct item, int quantity) throws RuntimeException
    {
        Order currentOrder = currentOpenedOrders.get(id);
        if(currentOrder != null)
        {
            currentOrder.removeItem(item,quantity);
        }
        else
        {
            throw new RuntimeException("Error, se intenta eliminar un item de una comanda no registrada en el sistema");
        }
    }

    /**
     * endOrder
     * 
     * @author Javier Martin Lloret
     * @version 1.0
     * @since 1.0
     * 
     * @param id identificador de la comanda que se desea terminar
     * 
     * @throws RuntimeException Error al acceder a una comanda no registrada
     * 
     * Finaliza la comanda entregada por parámetros.
     */
    public void endOrder(int id) throws RuntimeException
    {
        Order currentOrder = currentOpenedOrders.get(id);
        if(currentOrder != null)
        {
            //Obtenemos el precio de la comanda y lo registramos en la caja diaria
            if(dailyBox.containsKey(simpleDateFormat.format(new Date()).toString())){
                dailyBox.put(simpleDateFormat.format(new Date()).toString(), dailyBox.get(simpleDateFormat.format(new Date()).toString()).add(currentOrder.getPrice()));
            }
            else{
                dailyBox.put(simpleDateFormat.format(new Date()).toString(), currentOrder.getPrice());
            }
            //Eliminamos la posibilidad de modificar la orden.
            closedOrders.add(currentOrder);
            currentOpenedOrders.remove(id);
            save();
        }
        else
        {
            throw new RuntimeException("Error, se intenta eliminar una comanda no registrada en el sistema");
        }
    }

    /**
     * getDayIncome
     * 
     * @author Javier Martin Lloret
     * @version 1.0
     * @since 1.0
     * 
     * @return dailyBox o caja del día 
     * 
     * Devuelve la caja actual del día.
     */
    public BigDecimal getDayIncome()
    {
        if(dailyBox.containsKey(simpleDateFormat.format(new Date()))){
            return dailyBox.get(simpleDateFormat.format(new Date()));
        }
        return new BigDecimal("0");
    }

    public int getLenOrders(){
        return currentOpenedOrders.size();
    }

    public Order getOrderFromId(int orderID) throws RuntimeException
    {
        Order o = currentOpenedOrders.get(orderID);

        if(o == null)
            throw new RuntimeException("Intentando obtener orden abierta inexistente en el sistema");

        return o;

    }

    public Order getClosedOrderFromId(int orderID) throws RuntimeException
    {
        for(Order o: closedOrders){
            if(o.getId() == orderID){
                return o;
            }
        }
        throw new RuntimeException("Intentando obtener orden cerrada inexistente en el sistema");
    }

    public SortedSet<String> getProductCategories()
    {
        return productCategories;
    }

    public void save(){
        IOrderDAO orderDAO = factoryDAO.createOrderDAO(kitchenEmail);
        IProductDAO productDAO = factoryDAO.createProductDAO(kitchenEmail);
        IDayBoxDAO dayBoxDAO = factoryDAO.createDayBoxDAO(kitchenEmail);

        orderDAO.postOrders(closedOrders);
        productDAO.postProducts(products);
        dayBoxDAO.postDayBox(dailyBox);
    }
}
