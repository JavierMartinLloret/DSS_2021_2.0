package com.bqh_2021;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import com.bqh_2021.Entidades.Clases.Cafeteria;
import com.bqh_2021.Entidades.Clases.OrderWithUserAndDate;
import com.bqh_2021.Entidades.Clases.User;

import org.junit.Test;

public class OWADTest {
    // @Test(expected = RuntimeException.class)
    // public void OWADsDateControlWorksProperly()
    // {
    //     OrderWithUserAndDate owad = new OrderWithUserAndDate();
    //     Calendar variableDate;

    //     // In the future
    //     variableDate = Calendar.getInstance();
    //     variableDate.add(Calendar.MONTH, 1);
    //     try {
    //         owad = new OrderWithUserAndDate(new User(), new Cafeteria("cafeteria@email.com"), variableDate.getTime(), 0);
    //     } catch (RuntimeException rEFuture) {/*No debería lanzar excepción*/}
        
    //     // At the moment
    //     variableDate = Calendar.getInstance();
    //     try {
    //         owad = new OrderWithUserAndDate(new User(), new Cafeteria("cafeteria@email.com"), variableDate.getTime(), 1);
    //     } catch (RuntimeException rECurrentTime) {/*No debería lanzar excepción*/}

    //     // In the past
    //     variableDate = Calendar.getInstance();
    //     variableDate.set(Calendar.YEAR, variableDate.get(Calendar.YEAR)-1);
    //     try {
    //         owad = new OrderWithUserAndDate(new User(), new Cafeteria("cafeteria@email.com"), variableDate.getTime(), 2);
    //     } catch (RuntimeException rECurrentTime) {/*No debería lanzar excepción*/}

    //     owad.getClass(); // Para quitar el Warning
    // }
    // @Test
    // public void toStringFormatIsCorrect()
    // {
    //     User client = new User("user@email.com");
    //     Cafeteria cafeteria = new Cafeteria("cafeteria@email.com");
    //     Date date = new Date();
    //     OrderWithUserAndDate owad = new OrderWithUserAndDate(client, cafeteria, date, 0);

    //     assertEquals(owad.toString(), client.getEmail()+" "+cafeteria.getKitchenEmail()+" "+date.toString()+" "+0);
    // }
}
