package com.bqh_2021.accessingdatamysql;

import org.springframework.data.repository.CrudRepository;

public interface CreditCardRepository extends CrudRepository<CreditCard, String> {
    
}

