package com.bqh_2021.Entidades.Clases;

import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IFactoryDAO;
import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IUserDAO;
import com.bqh_2021.Utils.PersistenceConfiguration;

/**
 * User
 * @author Javier Martín-Lloret && Fco Arce Iniesta
 * @version 1.0
 * @since 3.0
 */
public class User {
    protected int userID;
    protected String nickname;
    protected boolean isAdult;
    protected String email;
    protected String password;
    protected UserSecurityManager secManager;

    protected static IFactoryDAO factoryDAO = PersistenceConfiguration.LoadPersistenceType();

    // Constructores

    public User(){};

    /**
     * @author Javier Martín-Lloret
     * @version 1.0
     * @since 3.0
     * 
     * @param userID identificador inequívoco del usuario
     * @param nickname apodo del usuario. Puede no ser único
     * @param email email del usuario. Inequívoco.
     * @param password contraseña cifrada del usuario
     * @param secManager manejador para el cifrado de la contraseña
     * 
     * @see UserSecurityManager
     */

    public User(String nickname, String email, String password, boolean isAdult, boolean isNew) throws RuntimeException
    {
        IUserDAO userDAO = factoryDAO.createUserDAO();
        this.userID = userDAO.getNextUserID();
        this.nickname = nickname;
        this.isAdult = isAdult;
        if(EmailValidator.emailFormatIsValid(email))
            this.email = email;
        else
            throw new RuntimeException("Error, el email del usuario no cumple el formato *@*.com");
        this.secManager = UserSecurityManager.getInstance();
        this.setPassword(password, !isNew);
    }

    public User(int userID, String nickname, String email, String password, boolean isAdult) throws RuntimeException
    {
        this(nickname, email, password, isAdult, false);
        this.userID = userID;
    }

    public User(String email){
        this.email = email;
    }


    public int getUserID()
    {
        return userID;
    }

    public boolean getIsAdult(){
        return isAdult;
    }

    public void setIsAdult(boolean isAdult){
        this.isAdult = isAdult;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password, boolean encrypted) throws RuntimeException
    {
        this.password = password;
        if(!encrypted){
            try {
                secManager.encryptPasswordAES(this);
            } catch (Exception e) {throw new RuntimeException("Error al cifrar la contraseña del usuario");}
        } 
    }

    public String toString(){
        return email + " " + password;
    }
}
