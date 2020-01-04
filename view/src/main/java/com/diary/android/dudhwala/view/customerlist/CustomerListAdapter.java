package com.diary.android.dudhwala.view.customerlist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.view.R;

import java.util.List;
import java.util.Optional;

import static com.diary.android.dudhwala.common.Constants.Log._TAG;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ViewHolder> {
    private final String TAG = _TAG + "CustomerListAdapter";
    @Nullable
    private List<CustomerInfo> mCustomerInfoList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.customers_list_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder position : " + position);

        CustomerInfo customerInfo = mCustomerInfoList.get(position);
        if (customerInfo != null) {
            String name = "Date : " + customerInfo.getCustomerName();
            String pendAmt = "Milk Type : " + customerInfo.getTotalAmountDue();

            holder.name.setText(name);
            holder.pendingAmount.setText(pendAmt);
        }
    }

    @Override
    public int getItemCount() {
        return Optional.ofNullable(mCustomerInfoList)
                .map(List::size)
                .orElse(0);
    }

    void updateCustomerList(List<CustomerInfo> customerInfoList) {

        Log.d(TAG, "customer list update. size : " + customerInfoList.size());
        mCustomerInfoList = customerInfoList;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView pendingAmount;
        ImageView quickAdd;
        ImageView add;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.customer_name);
            this.pendingAmount = itemView.findViewById(R.id.pending_amount);
            this.quickAdd = itemView.findViewById(R.id.quick_add_milk);
            this.add = itemView.findViewById(R.id.add_milk);
        }
    }
}
