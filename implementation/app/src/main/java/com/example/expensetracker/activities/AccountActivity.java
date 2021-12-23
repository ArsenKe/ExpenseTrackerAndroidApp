package com.example.expensetracker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensetracker.Exception_handling;
import com.example.expensetracker.database.DBHelper;
import com.example.expensetracker.database.IDataBase;
import com.example.expensetracker.R;
import com.example.expensetracker.model.Transaction;
import com.example.expensetracker.database.AccountDBHelper;
import com.example.expensetracker.database.CategoryDBHelper;
import com.example.expensetracker.database.TransactionDBHelper;
import com.example.expensetracker.model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends AppCompatActivity implements IDataBase {

private String accountName;
private TextView accountOutput;
private TextView balanceOutput;
private DBHelper myTransDB;
private DBHelper myCatDB;
private DBHelper myAccDB;
private ArrayList<Account> accountData;
private ArrayList<Transaction> transactionData;
private Integer deleteID;
private Integer updateID;
private Subject subject;
private Exception_handling myhandler;

    //use try and catch clause in the code when we are contacting to db
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // ??????????
       // Context context = null;
   /* public AccountActivity(Context context) {
          super(context, "accounts.sqlite", null, 3);
          this.context = context;}

    */

    
        Exception_handling myhandler = new Exception_handling();
        //it gets the account name from previous screeen
        Intent intent = getIntent();
        accountName = intent.getStringExtra("accountName");

        //stores runtime info
        subject = new Subject();
        try{
        myTransDB = new TransactionDBHelper(AccountActivity.this, subject);
        myCatDB = new CategoryDBHelper(AccountActivity.this, subject);
        myAccDB = new AccountDBHelper(AccountActivity.this, subject);

        //binding xml
        accountOutput = (TextView) findViewById(R.id.accountNameView);
        balanceOutput = (TextView) findViewById(R.id.totalBudgetTextView);


        readDB();
        }
        catch (Exception e)
        {
            //if the first one will on storage error
            //the second one will be connection error
            myhandler.setContext(getApplicationContext());
            myhandler.setException(e.toString());
        }

            // recycle view resad the data from DB and shows it in recyclerview in linear layout
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewTransactions);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //connect the data with dataview, data (list) which can be used in recycleview
        CustomAdapterTransaction customAdapter = new CustomAdapterTransaction(transactionData);
        recyclerView.setAdapter(customAdapter);

    }

    private void saveData(String accountName, String transType, String category, Double value) {

        try{
        myTransDB.addTransaction(accountName, transType, category, value);
        subject.setState(accountName, transType, value);
        readDB();
        }
        catch (Exception e)
        {       Log.d("AccountActivity_db", "Save Data error");
                myhandler.setContext(getApplicationContext());
                myhandler.setException(e.toString());
        }

    }

    private void updateData(Integer transID, String accountType, String transType, String category, String transValue, Double oldValue) {

        try {
            myTransDB.updateTransaction(transID, accountType, transType, category, transValue, oldValue);
            subject.setState(accountName, transType, Double.parseDouble(transValue));
            readDB();
        } catch (Exception e) {
            Log.d("AccountActivity_db", "Update Data error");
            myhandler.setContext(getApplicationContext());
            myhandler.setException(e.toString());

        }
    }

    private void deleteData(Integer transID, String accountType, String transType, Double transValue) {
           try {
               myTransDB.deleteTransaction(transID, accountType, transType, transValue);
               subject.setState(accountName, transType, transValue);
               readDB();
           } catch (Exception e)
           {
               Log.d("AccountActivity_db", "Delete Data error");
               myhandler.setContext(getApplicationContext());
               myhandler.setException(e.toString());
           }


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
        for(String str : myCatDB.getAllCategories()) {
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
               try {
                   readDB();
               } catch (Exception e) {
                 // ???????????? e.printStackTrace();
                   Log.d("AccountActivity_db", "Add to DB error");
                   myhandler.setContext(getApplicationContext());
                   myhandler.setException(e.toString());
               }
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
                   try {
                       readDB();
                   } catch (Exception e) {
                       //  e.printStackTrace();
                       Log.d("AccountActivity_db", "deleteFromDB error");
                       myhandler.setContext(getApplicationContext());
                       myhandler.setException(e.toString());
                   }
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
            for(String str : myCatDB.getAllCategories()) {
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

                    try {
                        updateData(updateID, accountName, transType, category, value, oldValue);
                        readDB();
                    } catch (Exception e) {
                       // e.printStackTrace();
                        Log.d("AccountActivity_db", "updateInDB error");
                        myhandler.setContext(getApplicationContext());
                        myhandler.setException(e.toString());
                    }
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
        accountData = myAccDB.getAllAccounts();
        transactionData = myTransDB.getTransactions(accountName);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewTransactions);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        CustomAdapterTransaction customAdapter = new CustomAdapterTransaction(transactionData);
        recyclerView.setAdapter(customAdapter);

        ArrayList<Account> accountData = myAccDB.getAllAccounts();
        double balance = 0.0;
        double accBalance = 0.0;
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

    public void backHome(View view) {
        Intent intent = new Intent(AccountActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    public void getItemForDelete(View view) {
        TextView transID;

        transID = (TextView) view.findViewById(R.id.transID);

        deleteID = Integer.parseInt(transID.getText().toString());
        updateID = deleteID;

    }

}