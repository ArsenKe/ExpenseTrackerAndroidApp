package com.example.expensetracker;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterTransaction extends RecyclerView.Adapter<CustomAdapterTransaction.ViewHolder> {

    private ArrayList<Transaction> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView transID;
        private final TextView transType;
        private final TextView transValue;
        private final TextView category;


        public ViewHolder(View view) {
            super(view);

            transID = (TextView) view.findViewById(R.id.transID);
            transType = (TextView) view.findViewById(R.id.transType);
            transValue = (TextView) view.findViewById(R.id.transValue);
            category = (TextView) view.findViewById(R.id.category);

        }

        public TextView getTransID() {
            return transID;
        }
        public TextView getTransType() {
            return transType;
        }
        public TextView getTransValue() {
            return transValue;
        }
        public TextView getCategory() {
            return category;
        }

    }

    public CustomAdapterTransaction(ArrayList<Transaction> dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rowlayout_transaction, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        String euro = "€";

        viewHolder.getTransID().setText(localDataSet.get(position).getTransID().toString());
        viewHolder.getTransType().setText(localDataSet.get(position).getTransType());
        if(localDataSet.get(position).getTransType().equals("Income")) {
            euro = "+ € ";
        } else {
            euro = "- €";
        }
        viewHolder.getTransValue().setText(euro + localDataSet.get(position).getValue().toString());
        viewHolder.getCategory().setText(localDataSet.get(position).getCategory());

    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

