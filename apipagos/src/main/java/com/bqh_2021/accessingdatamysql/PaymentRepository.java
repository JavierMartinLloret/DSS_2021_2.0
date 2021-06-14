package com.bqh_2021.accessingdatamysql;

import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {
    
}
