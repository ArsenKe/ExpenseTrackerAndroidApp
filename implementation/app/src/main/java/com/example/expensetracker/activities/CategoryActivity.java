package com.example.expensetracker.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.Exception_handling;
import com.example.expensetracker.database.IDataBase;
import com.example.expensetracker.R;
import com.example.expensetracker.database.CategoryDBHelper;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements IDataBase {

    CategoryDBHelper mydb;
    ArrayList<String> saveDataInCategory = new ArrayList<String>();
    private Exception_handling myhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        mydb = new CategoryDBHelper(CategoryActivity.this);
        try {
        readDB();
        } catch (Exception e) {
            Log.d("CategoryActivity_db", e.toString());
            myhandler.setContext(getApplicationContext());
            myhandler.setException(e.toString());
        }


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCategories);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        CustomAdapterCategory customAdapter = new CustomAdapterCategory(categories);
        recyclerView.setAdapter(customAdapter);
    }

    @Override
    public void readDB() {
        try{saveDataInCategory = mydb.getAllCategories();} catch (Exception e) {
            Log.d("CategoryActivity_db", e.toString());
            myhandler.setContext(getApplicationContext());
            myhandler.setException(e.toString());
        }

    }

    private void saveData(String categoryName) {
       try {mydb.addCategory(categoryName);
           readDB();
       } catch (Exception e) {
           Log.d("CategoryActivity_db", e.toString());
           myhandler.setContext(getApplicationContext());
           myhandler.setException(e.toString());
       }

    }

    private void updateData(String categoryName, String newCategoryName) {
        try {
            mydb.updateCategory(categoryName, newCategoryName);
            readDB();
        } catch (Exception e) {
            Log.d("CategoryActivity_db", e.toString());
            myhandler.setContext(getApplicationContext());
            myhandler.setException(e.toString());
        }
    }

    private void deleteData(String categoryName) {
        try {
            mydb.deleteCategory(categoryName);
            readDB();
        } catch (Exception e) {
            Log.d("CategoryActivity_db", e.toString());
            myhandler.setContext(getApplicationContext());
            myhandler.setException(e.toString());
        }
    }

    @Override
    public void addToDB(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Add Category");
        alertDialogBuilder.setMessage("Enter Category Name:");
        alertDialogBuilder.setCancelable(false);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText inputName = new EditText(this);
        inputName.setHint("Category Name");
        layout.addView(inputName);

        alertDialogBuilder.setView(layout);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String name = inputName.getText().toString();
                saveData(name);
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
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
        alertDialogBuilder.setTitle("Category Name");
        alertDialogBuilder.setMessage("Enter Changed Name:");
        alertDialogBuilder.setCancelable(false);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        List<String> s = new ArrayList<String>();
        for(String str : categories) {
            s.add(str);
        }

        final ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, s);

        final Spinner input = new Spinner(this);
        input.setAdapter(adp);
        layout.addView(input);

        final EditText nameChanged = new EditText(this);
        nameChanged.setHint("Change Category Name");
        layout.addView(nameChanged);

        alertDialogBuilder.setView(layout);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String categoryToChange = input.getSelectedItem().toString();
                String newCategory = nameChanged.getText().toString();
                updateData(categoryToChange, newCategory);
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
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
        alertDialogBuilder.setTitle("Category Name");
        alertDialogBuilder.setMessage("Enter Category:");
        alertDialogBuilder.setCancelable(false);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        List<String> s = new ArrayList<String>();
        for(String str : categories) {
            s.add(str);
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
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
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

    public void backHome(View view) {
        Intent intent = new Intent(CategoryActivity.this, HomeActivity.class);
        startActivity(intent);
    }


}