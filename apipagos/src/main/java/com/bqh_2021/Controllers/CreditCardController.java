package com.bqh_2021.Controllers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.bqh_2021.accessingdatamysql.CreditCard;
import com.bqh_2021.accessingdatamysql.CreditCardRepository;
import com.bqh_2021.clases.PaymentCodeGenerator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CreditCardController {
    
    @Autowired
    private CreditCardRepository creditCardRepository;

    @Value("${spring.mail.ipBalanceConfirm}")
    private String ip;

    public static Map<String, Optional<CreditCard>> codes = new HashMap<String, Optional<CreditCard>>();

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
        String code = PaymentCodeGenerator.generateCode();
        Optional<CreditCard> card = creditCardRepository.findById(j.get("ownerEmail").toString());
        if(card.isPresent()){
            codes.put(code, creditCardRepository.findById(j.get("ownerEmail").toString()));
            return "Simulando pasarela de pago, para continuar pulse aqui " + ip + code + "&quantity=" + j.get("balanceToAdd").toString();
        }
        return "{\"status\": \"fail\"}";
    }

    @PostMapping("/refundBalance")
    public String refundBalance(@RequestBody String payload) throws ParseException{
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(payload);
        JSONObject j = (JSONObject)obj;
        Optional<CreditCard> card = creditCardRepository.findById(j.get("ownerEmail").toString());
        if(card.isPresent()){
            card.get().addToBalance(new BigDecimal(j.get("refund").toString()));
            card.get().deletePaymentFromArchive(Integer.valueOf(j.get("orderID").toString()));
            creditCardRepository.save(card.get());
            return "{\"status\": \"success\"}";
        }
        return "{\"status\": \"fail\"}";
    }

    @GetMapping("/balanceConfirm")
    public String confirm(@RequestParam int quantity, @RequestParam String code){
        CreditCard creditCard = codes.get(code).get();
        creditCard.addToBalance(new BigDecimal(quantity));
        creditCardRepository.save(creditCard);
        return "{\"status\": \"success\"}";    
    }
}
