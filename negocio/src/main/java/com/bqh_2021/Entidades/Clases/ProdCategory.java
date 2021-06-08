package com.bqh_2021.Entidades.Clases;

import java.util.Set;

/**
 * ProdCategory
 * 
 * @author Javier Martín-Lloret
 * @version 1.1
 * @since 1.0
 */

public class ProdCategory {

    private static final ProdCategory SINGLE_INSTANCE = new ProdCategory();
    Set<String> categories;

    /**
     * 
     */
    private ProdCategory(){}

    public ProdCategory getInstance()
    {
        return SINGLE_INSTANCE;
    }

    public void setCategories(Set<String> s)
    {
        categories = s;
    }

    public Set<String> getCurrentCategories()
    {
        return categories;
    }
}
