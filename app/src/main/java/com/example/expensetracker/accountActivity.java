package com.example.expensetracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// refrences
//https://stackoverflow.com/questions/15762905/how-can-i-display-a-list-view-in-an-android-alert-dialog
public class accountActivity extends AppCompatActivity {
private EditText name;
private EditText account;
private Button create_account;
private  String id_of_account;
private String name_person;
private String account_type;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        name=findViewById(R.id.account_holder_name);
        account=findViewById(R.id.account_type);
        create_account=findViewById(R.id.get_transaction);
        create_account.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                if(!name.getText().toString().isEmpty()&&!account.getText().toString().isEmpty())
                id_of_account=now.toString()+name.getText().toString()+account.getText().toString();

                else{
                    if(name.getText().toString().isEmpty())
                    {
                        name.setError("Enter the name please");
                    }
                    else{
                        account.setError("Kindly select the acccount");
                    }
                }
            }
        });

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    // add a list
    String[] account_type = {"BANK_ACCOUNT",
            "CARD",
            "CASH", 
            "STOCK"};
    builder.setItems(account_type, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            account.setText(account_type[which]);
        }
    });
   account.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           AlertDialog dialog = builder.create();
           dialog.show();
       }
   });


    // create and show the alert dialog


    }

    public String getName_person() {
        return name_person;
    }

    public String getAccount_type() {
        return account_type;
    }

    public String getId_of_account() {
        return id_of_account;
    }
}