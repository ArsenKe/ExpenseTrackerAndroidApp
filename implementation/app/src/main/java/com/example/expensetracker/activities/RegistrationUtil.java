package com.example.expensetracker.activities;

 class   RegistrationUtil {

  private  String name;
  private String password;
//  validate the email and password in it main class

     public RegistrationUtil(String name, String password) {
         this.name = name;
         this.password = password;
     }

     public boolean validation(){

       if(name.equals("admin")&&password.equals("admin")){
           return true;
         }
         else {
           return false;
         }

     }

}
