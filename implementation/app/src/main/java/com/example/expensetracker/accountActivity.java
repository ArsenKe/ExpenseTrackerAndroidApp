package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class accountActivity extends AppCompatActivity implements IDataBase {

private String accountName;
private TextView accountOutput;
private TextView balanceOutput;
private DBHelper mydb;
private ArrayList<Account> accountData;
private ArrayList<Transaction> transactionData;
private Integer deleteID;
private Integer updateID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Intent intent = getIntent();
        accountName = intent.getStringExtra("accountName");

        mydb = new DBHelper(accountActivity.this);
        //readDB();

        accountOutput = (TextView) findViewById(R.id.accountNameView);
        balanceOutput = (TextView) findViewById(R.id.totalBudgetTextView);
        //getAccountData();
        readDB();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewTransactions);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        CustomAdapterTransaction customAdapter = new CustomAdapterTransaction(transactionData);
        recyclerView.setAdapter(customAdapter);

    }

    public void saveData(String accountName, String transType, String category, Double value) {
        mydb.addTransaction(accountName, transType, category, value);
        //getAccountData();
    }

    public void updateData(Integer transID, String accountType, String transType, String category, String transValue, Double oldValue) {
        mydb.updateTransaction(transID, accountType, transType, category, transValue, oldValue);
        //getAccountData();
    }

    public void deleteData(Integer transID, String accountType, String transType, Double transValue) {
        mydb.deleteTransaction(transID, accountType, transType, transValue);
        //getAccountData();
    }

    @Override
    public void addToDB(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Add Transaction");
        alertDialogBuilder.setMessage("Enter transaction:");
        alertDialogBuilder.setCancelable(false);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        List<String> s = new ArrayList<String>();
        for(String str : mydb.getAllCategories()) {
            s.add(str);
        }
        final ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, s);

        List<String> t = new ArrayList<String>();
        t.add("Income");
        t.add("Expense");
        final ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, t);

        final Spinner transTypes = new Spinner(this);
        transTypes.setAdapter(adp2);
        layout.addView(transTypes);

        final Spinner categories = new Spinner(this);
        categories.setAdapter(adp);
        layout.addView(categories);

        final EditText inputValue = new EditText(this);
        inputValue.setHint("Value");
        layout.addView(inputValue);

        alertDialogBuilder.setView(layout);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String transType = transTypes.getSelectedItem().toString();
                String category = categories.getSelectedItem().toString();
                String value = inputValue.getText().toString();
                saveData(accountName, transType, category, Double.parseDouble(value));
                readDB();
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

        if(deleteID != null) {
            alertDialogBuilder.setTitle("Delete Transaction");
            alertDialogBuilder.setMessage("Are you sure?");
            alertDialogBuilder.setCancelable(false);

            alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    String transType = "";
                    Double transValue = 0.0;
                    for(Transaction t : transactionData) {
                        if(t.getTransID() == deleteID) {
                            transType = t.getTransType();
                            transValue = t.getValue();
                        }
                    }
                    deleteData(deleteID, accountName, transType, transValue);
                    readDB();
                }
            });

            alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),"You clicked on Cancel",Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            alertDialogBuilder.setTitle("No Transaction Selected");
            alertDialogBuilder.setMessage("Select Transaction");
            alertDialogBuilder.setCancelable(false);

            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                }
            });
        }

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void updateInDB(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        if(updateID != null) {
            alertDialogBuilder.setTitle("Update Transaction");
            alertDialogBuilder.setMessage("Enter values:");
            alertDialogBuilder.setCancelable(false);

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);

            List<String> s = new ArrayList<String>();
            for(String str : mydb.getAllCategories()) {
                s.add(str);
            }
            final ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, s);

            List<String> t = new ArrayList<String>();
            t.add("Income");
            t.add("Expense");
            final ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, t);

            final Spinner transTypes = new Spinner(this);
            transTypes.setAdapter(adp2);
            layout.addView(transTypes);

            final Spinner categories = new Spinner(this);
            categories.setAdapter(adp);
            layout.addView(categories);

            final EditText inputValue = new EditText(this);
            inputValue.setHint("Value");
            layout.addView(inputValue);

            alertDialogBuilder.setView(layout);

            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    String transType = transTypes.getSelectedItem().toString();
                    String category = categories.getSelectedItem().toString();
                    String value = inputValue.getText().toString();
                    Double oldValue = 0.0;

                    for(Transaction t : transactionData) {
                        if(t.getTransID() == updateID) {
                            oldValue = t.getValue();
                        }
                    }

                    updateData(updateID, accountName, transType, category, value, oldValue);
                    readDB();
                }
            });

            alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),"You clicked on Cancel",Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            alertDialogBuilder.setTitle("No Transaction Selected");
            alertDialogBuilder.setMessage("Select Transaction");
            alertDialogBuilder.setCancelable(false);

            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                }
            });
        }

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void readDB() {
        accountData = mydb.getAllAccounts();
        transactionData = mydb.getTransactions(accountName);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewTransactions);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        CustomAdapterTransaction customAdapter = new CustomAdapterTransaction(transactionData);
        recyclerView.setAdapter(customAdapter);

        ArrayList<Account> accountData = mydb.getAllAccounts();
        double balance = 0;
        Account account = new Account("test", 0.0);

        for(Transaction t : transactionData) {
            if(t.getTransType().equals("Income")) {
                balance += t.getValue();
            } else {
                balance -= t.getValue();
            }
        }

        balanceOutput.setText("Account balance: " + balance);
        balanceOutput.setFocusable(false);
        balanceOutput.setClickable(false);

        accountOutput.setText(accountName);
        accountOutput.setFocusable(false);
        accountOutput.setClickable(false);
    }

    public ArrayList<Account> getAccountData() {
        ArrayList<Account> accountData = mydb.getAllAccounts();

        return accountData;
    }

    public void backHome(View view) {
        Intent intent = new Intent(accountActivity.this, HomeFragment.class);
        startActivity(intent);
    }

    public void getItemForDelete(View view) {
        TextView transID;

        transID = (TextView) view.findViewById(R.id.transID);

        deleteID = Integer.parseInt(transID.getText().toString());
        updateID = deleteID;

    }

}