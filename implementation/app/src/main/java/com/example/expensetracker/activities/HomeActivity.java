package com.example.expensetracker.activities;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.database.IDataBase;
import com.example.expensetracker.R;
import com.example.expensetracker.database.AccountDBHelper;
import com.example.expensetracker.model.Account;
import com.example.expensetracker.model.AccountFactory;
import com.example.expensetracker.model.IAccount;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity implements IDataBase {

    private AccountDBHelper mydb;
    private ArrayList<Account> allAccounts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mydb = new AccountDBHelper(HomeActivity.this);
        readDB();

        setContentView(R.layout.activity_home);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<String> accountTypes = new ArrayList<String>();
        for(Account a : allAccounts) {
            accountTypes.add(a.getAccountType());
        }

        CustomAdapterAccount customAdapterAccount = new CustomAdapterAccount(accountTypes);
        recyclerView.setAdapter(customAdapterAccount);

    }

    private void saveData(String accountType, double value) {
        mydb.addAccount(accountType, value);
        readDB();
    }

    private void updateData(String accountType, String newAccountType, double value) {
        mydb.updateAccount(accountType, newAccountType, value);
        readDB();
    }

    private void deleteData(String accountType) {
        mydb.deleteAccount(accountType);
        readDB();
    }

    @Override
    public void addToDB(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Add Account");
        alertDialogBuilder.setMessage("Enter Account Name & Value:");
        alertDialogBuilder.setCancelable(false);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        List<String> accountTypes = new ArrayList<String>();
        accountTypes.add("BANK");
        accountTypes.add("CASH");
        accountTypes.add("CARD");
        final ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, accountTypes);

        final Spinner accounts = new Spinner(this);
        accounts.setAdapter(adp);
        layout.addView(accounts);
        alertDialogBuilder.setView(layout);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String name = accounts.getSelectedItem().toString();
                String value = "0";

                //implementing Factory Pattern
                AccountFactory accountFactory = new AccountFactory();
                IAccount acc = accountFactory.getAccount(name);
                Account accountToSave = acc.create();

                saveData(accountToSave.getAccountType(), Double.parseDouble(value));
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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

        for(Account a : allAccounts) {
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
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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

        for(Account a : allAccounts) {
            s.add(a.getAccountType());
        }

        final ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, s);

        final Spinner input = new Spinner(this);
        input.setAdapter(adp);
        layout.addView(input);

        final EditText nameChanged = new EditText(this);
        nameChanged.setHint("Change Account Name");
        layout.addView(nameChanged);

        alertDialogBuilder.setView(layout);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String accountType = input.getSelectedItem().toString();
                String newAccountType = nameChanged.getText().toString();
                System.out.println(newAccountType);
                String inputValue = "";
                for(Account a : allAccounts) {
                    inputValue = a.getValue().toString();
                }
                updateData(accountType, newAccountType, Double.parseDouble(inputValue));
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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
        allAccounts = mydb.getAllAccounts();
    }

    public void showAccount(View view) {
        TextView textView;

        textView = (TextView) view.findViewById(R.id.textView);

        Intent intent = new Intent(HomeActivity.this, AccountActivity.class);
        intent.putExtra("accountName", textView.getText().toString());
        startActivity(intent);
    }

    public void editCategories(View view) {
        Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
        startActivity(intent);
    }
}
