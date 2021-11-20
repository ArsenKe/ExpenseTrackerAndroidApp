package com.example.expensetracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// refrences
//https://stackoverflow.com/questions/15762905/how-can-i-display-a-list-view-in-an-android-alert-dialog
public class accountActivity extends AppCompatActivity {
    private EditText name;
    private EditText account;
    private Button create_account;
    private  String id_of_account;
    private String name_person;
    private String account_type;
    private TextView accountOutput;
    private TextView balanceOutput;
    private AccountRepository accountRep;
    private DBHelper mydb;

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



        mydb = new DBHelper(accountActivity.this);
        //accountRep = new AccountRepository(mydb);

        accountOutput = (TextView) findViewById(R.id.amount);
        balanceOutput = (TextView) findViewById(R.id.totalBudgetTextView);
        readData();
    }

    public void saveData(String accountType, double value) {
        //Bundle extras = getIntent().getExtras();
        //if (extras != null) {
        //int Value = extras.getInt("accountID");
        //if(Value > 0) {
        //upgrade data
        //}
        //accountRep.writeInDB(accountType, value);
        mydb.addAccount(accountType, value);
        readData();

        Intent intent = new Intent(getApplicationContext(), accountActivity.class);
        startActivity(intent);
        //}else {
        //System.out.println("not added");
        //}
    }


    public void readData() {
        //should be the number of account saved in account object when creating
        Cursor data = mydb.getData(2);
        double balance = 0;

        if (data.moveToFirst()) {
            do {
                balance = data.getDouble(data.getColumnIndex("accountBalance"));
            } while (data.moveToNext());
        }

        balanceOutput.setText("Account balance: " + balance);
        balanceOutput.setFocusable(false);
        balanceOutput.setClickable(false);

    }

    public void accountInput(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Account Name");
        alertDialogBuilder.setMessage("Enter value:");
        alertDialogBuilder.setCancelable(false);

        EditText input = new EditText(this);
        alertDialogBuilder.setView(input);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String inputValue = input.getText().toString();
                saveData("BANK_ACCOUNT", Double.parseDouble(inputValue));
                Intent intent = new Intent(getApplicationContext(), accountActivity.class);
                startActivity(intent);
            }
        });

        alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"You clicked on Cancel",Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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