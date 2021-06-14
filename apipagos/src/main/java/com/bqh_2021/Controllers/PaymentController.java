package com.bqh_2021.Controllers;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.bqh_2021.Servicios.SendEmailService;
import com.bqh_2021.accessingdatamysql.CreditCard;
import com.bqh_2021.accessingdatamysql.CreditCardRepository;
import com.bqh_2021.accessingdatamysql.Payment;
import com.bqh_2021.clases.PaymentCodeGenerator;
import com.bqh_2021.clases.PaymentObserver;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    private CreditCardRepository creditCardRepository;

    public static Map<String, PaymentObserver> codes = new HashMap<String, PaymentObserver>();

    @Autowired
    public SendEmailService sendEmailService;

    @Value("${spring.mail.ipConfirm}")
    private String ip;
    
    @PostMapping("/pagos")
    public String pay(@RequestBody String payload) throws ParseException, java.text.ParseException{
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(payload);
        JSONObject j = (JSONObject)obj;
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        String code =  PaymentCodeGenerator.generateCode();
        Payment payment = new Payment(j.get("concept").toString(),
                                j.get("payerEmail").toString(),
                                dateFormat.parse(j.get("date").toString()),
                                new BigDecimal(j.get("cost").toString()),
                                code);
        PaymentObserver observer = new PaymentObserver(payment);
        codes.put(code,observer);
        sendEmailService.sendEmail("javier.martinlloret@alum.uca.es", ip + payment.getCodeForPayment(), "Confirmacion de pago en " + j.get("concept").toString());
        while(!payment.isPaied()){
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }    
        }
        Optional<CreditCard> creditCard = creditCardRepository.findById(j.get("payerEmail").toString());
        if(creditCard.isPresent()){
            creditCard.get().addPaymentToArchive(payment);
            try{
                creditCard.get().charge(payment.getCost());
                creditCardRepository.save(creditCard.get());
                return "{\"status\": \"success\"}"; 
            }catch(RuntimeException e){
                return "{\"status\": \"fail\", \"error\": \"" + e.getMessage() + "\"}";
            }
        }
        return "{\"status\": \"fail\"}"; 
    }

    @GetMapping("/pagosConfirm")
    public void confirm(@RequestParam String code){
        codes.get(code).update();
    }
}
