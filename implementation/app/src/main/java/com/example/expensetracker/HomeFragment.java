package com.example.expensetracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends AppCompatActivity implements IDataBase {

    private DBHelper mydb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mydb = new DBHelper(HomeFragment.this);
        getAccountData();

        setContentView(R.layout.activity_home_fragment);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<String> accountTypes = new ArrayList<String>();
        for(Account a : getAccountData()) {
            accountTypes.add(a.getAccountType());
        }

        CustomAdapter customAdapter = new CustomAdapter(accountTypes);
        recyclerView.setAdapter(customAdapter);

    }

    public void saveData(String accountType, double value) {
        mydb.addAccount(accountType, value);
        getAccountData();
    }

    public void updateData(String accountType, String newAccountType, double value) {
        mydb.updateAccount(accountType, newAccountType, value);
        getAccountData();
    }

    public void deleteData(String accountType) {
        mydb.deleteAccount(accountType);
        getAccountData();
    }

    @Override
    public void addToDB(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Add Account");
        alertDialogBuilder.setMessage("Enter Account Name & Value:");
        alertDialogBuilder.setCancelable(false);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        List<String> s = new ArrayList<String>();

        ArrayList<Account> accountData = getAccountData();
        for(Account a : accountData) {
            s.add(a.getAccountType());
        }

        final ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, s);

        final EditText inputName = new EditText(this);
        inputName.setHint("Account Name");
        layout.addView(inputName);

        final EditText inputValue = new EditText(this);
        inputValue.setHint("Value");
        layout.addView(inputValue);

        if(inputValue.getText().toString().isEmpty()) {
            alertDialogBuilder.setTitle("No Value Entered!");
            alertDialogBuilder.setMessage("Please Enter Value");
            alertDialogBuilder.setCancelable(false);

            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                }
            });
        }

        alertDialogBuilder.setView(layout);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String name = inputName.getText().toString();
                String value = inputValue.getText().toString();
                if(value.isEmpty()){
                    Toast.makeText(HomeFragment.this, "Empty value not allowed!", Toast.LENGTH_SHORT).show();
                }
                saveData(name, Double.parseDouble(value));
                Intent intent = new Intent(getApplicationContext(), HomeFragment.class);
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

    @Override
    public void deleteFromDB(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Account Name");
        alertDialogBuilder.setMessage("Enter value:");
        alertDialogBuilder.setCancelable(false);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        List<String> s = new ArrayList<String>();

        ArrayList<Account> accountData = getAccountData();
        for(Account a : accountData) {
            s.add(a.getAccountType());
        }

        final ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, s);

        final Spinner input = new Spinner(this);
        input.setAdapter(adp);
        layout.addView(input);

        alertDialogBuilder.setView(layout);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String inputValue = input.getSelectedItem().toString();
                deleteData(inputValue);
                Intent intent = new Intent(getApplicationContext(), HomeFragment.class);
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

    @Override
    public void updateInDB(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Account Name");
        alertDialogBuilder.setMessage("Enter value:");
        alertDialogBuilder.setCancelable(false);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        List<String> s = new ArrayList<String>();

        ArrayList<Account> accountData = getAccountData();
        for(Account a : accountData) {
            s.add(a.getAccountType());
        }

        final ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, s);

        final Spinner input = new Spinner(this);
        input.setAdapter(adp);
        layout.addView(input);

        final EditText nameChanged = new EditText(this);
        nameChanged.setHint("Change Account Name");
        layout.addView(nameChanged);
/*
        final EditText valueInput = new EditText(this);
        valueInput.setHint("Change Value");
        layout.addView(valueInput);
*/
        alertDialogBuilder.setView(layout);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String accountType = input.getSelectedItem().toString();
                String newAccountType = nameChanged.getText().toString();
                System.out.println(newAccountType);
                String inputValue = "";
                for(Account a : accountData) {
                    inputValue = a.getValue().toString();
                }
                /*String inputValue = valueInput.getText().toString();
                if(inputValue.isEmpty()) {
                    for(Account a : getAccountData()) {
                        if(a.getAccountType().equals(accountType)) {
                            inputValue = a.getValue().toString();
                        }
                    }
                }*/
                updateData(accountType, newAccountType, Double.parseDouble(inputValue));
                Intent intent = new Intent(getApplicationContext(), HomeFragment.class);
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

    @Override
    public void readDB() {

    }

    public ArrayList<Account> getAccountData() {
        ArrayList<Account> accountData = mydb.getAllAccounts();
        double balance = 0;

        for(Account a : accountData) {
            balance += a.getValue();
        }

        return accountData;
    }

    public void showAccount(View view) {
        TextView textView;

        textView = (TextView) view.findViewById(R.id.textView);

        Intent intent = new Intent(HomeFragment.this, accountActivity.class);
        intent.putExtra("accountName", textView.getText().toString());
        startActivity(intent);
    }

    public void editCategories(View view) {
        Intent intent = new Intent(HomeFragment.this, Category.class);
        startActivity(intent);
    }
}
