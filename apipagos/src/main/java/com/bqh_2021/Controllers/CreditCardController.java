package com.bqh_2021.Controllers;

import java.math.BigDecimal;
import java.util.Optional;

import com.bqh_2021.accessingdatamysql.CreditCard;
import com.bqh_2021.accessingdatamysql.CreditCardRepository;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreditCardController {
    
    @Autowired
    private CreditCardRepository creditCardRepository;

    @PostMapping("/creditCard")
    public String addNewCreditCard(@RequestBody String payload) throws ParseException{
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(payload);
        JSONObject j = (JSONObject)obj;
        CreditCard creditCard = new CreditCard(j.get("ownerEmail").toString());
        creditCardRepository.save(creditCard);
        return "{\"status\": \"success\"}";
    }

    @GetMapping("/creditCard")
    public Iterable<CreditCard> getAllCreditCard(){
        return creditCardRepository.findAll();
    }

    @GetMapping("/creditCardBalance")
    public BigDecimal getCreditCard(@RequestParam String ownerEmail){
        return creditCardRepository.findById(ownerEmail).get().getBalance();
    }

    @PutMapping("/creditCard")
    public String addBalanceToCreditCard(@RequestBody String payload) throws ParseException{
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(payload);
        JSONObject j = (JSONObject)obj;
        Optional<CreditCard> creditCard = creditCardRepository.findById(j.get("ownerEmail").toString());
        if(creditCard.isPresent()){
                creditCard.get().addToBalance(new BigDecimal(j.get("balanceToAdd").toString()));
                creditCardRepository.save(creditCard.get());
                return "{\"status\": \"success\"}";    
        }
        return "{\"status\": \"fail\"}"; 
    }
}
