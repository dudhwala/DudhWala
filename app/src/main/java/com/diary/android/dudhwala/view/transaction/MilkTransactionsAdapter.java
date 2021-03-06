package com.diary.android.dudhwala.view.transaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diary.android.dudhwala.R;
import com.diary.android.dudhwala.common.TimeUtils;
import com.diary.android.dudhwala.common.entity.MilkTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MilkTransactionsAdapter extends RecyclerView.Adapter<MilkTransactionsAdapter.ViewHolder> {

    private static final String TAG = "DudhWala/MilkTransactionsAdapter";
    private List<MilkTransaction> milkTransactionsArrayList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.milk_transaction_list_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Log.d(TAG, "onBindViewHolder() position : " + position);

        MilkTransaction milkTransaction = milkTransactionsArrayList.get(position);

        String transactionDate = "Date : " + TimeUtils.convertTimestampToDateString(
                milkTransaction.getTransactionDate());
        String milkType = "Milk Type : " + milkTransaction.getMilkType();
        String milkQuantity = "Milk Quantity : " + milkTransaction.getMilkQuantityLiters();
        String milkPricePerLiter = "Price/Liter : " + milkTransaction.getPricePerLiter();
        String totalAmount = "Amount : " + milkTransaction.getTransactionAmount();

        //Log.d(TAG, milkTransaction.toString());

        holder.dateTextView.setText(transactionDate);
        holder.milkType.setText(milkType);
        holder.milkQuantityTextView.setText(milkQuantity);
        holder.milkPricePerLiterTextView.setText(milkPricePerLiter);
        holder.totalAmount.setText(totalAmount);
    }

    @Override
    public int getItemCount() {
        return Optional.ofNullable(milkTransactionsArrayList)
                .map(List::size)
                .orElse(0);
    }

    public void updateMilkTransactionsData(List<MilkTransaction> milkTransactions) {
        Log.d(TAG, "updateMilkTransactionsData() size : " + milkTransactions.size());
        milkTransactionsArrayList = milkTransactions;
        notifyDataSetChanged();
    }

    public MilkTransaction getItem(int position) {
        return Optional.ofNullable(milkTransactionsArrayList)
                .map(milkTransactions -> milkTransactions.get(position))
                .orElse(null);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView milkQuantityTextView;
        TextView milkPricePerLiterTextView;
        TextView totalAmount;
        TextView milkType;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.dateTextView = itemView.findViewById(R.id.dateTextView);
            this.milkQuantityTextView = itemView.findViewById(R.id.milkQuantityTextView);
            this.milkPricePerLiterTextView = itemView.findViewById(R.id.milkRateTextView);
            this.totalAmount = itemView.findViewById(R.id.totalAmount);
            this.milkType = itemView.findViewById(R.id.milkType);

        }
    }
}
