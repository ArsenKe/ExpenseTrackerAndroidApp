package com.example.expensetracker.activities;

//import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;



 class MainActivityTest {

     private MainActivity act = new MainActivity();

/*
@Test
@DisplayName ("Assert Empty Login")
 public void assert_empty_Username ( ) {
    String result = MainActivity.validateRegistrationInput(
            username: "",
            password: "123" ,
            confirmed_Password: "123"
    )
        assertThat()
    }

 */

    @Test
    @DisplayName ("Assert Empty Login")
     void assert_Empty_Login ( ) {

       Assertions.assertEquals(act)

    }





}