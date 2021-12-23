package com.example.expensetracker;

import android.content.Context;
import android.widget.Toast;

public class Exception_handling {
    private String exception;
    private Context context;

    public void setException(String exception) { this.exception = exception; }

    public void setContext(Context contex) { this.context = context; }

    public void show_exception_to_user()
    {
        Toast.makeText(context, exception , Toast.LENGTH_LONG).show();
    }



}
