package com.diary.android.dudhwala.view.customerlist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    private final CustomerListItemClickListener mCustomerListItemClickListener;

    private int mmExpandedForPosition = -1;

    CustomerListAdapter(CustomerListItemClickListener customerListItemClickListener) {
        mCustomerListItemClickListener = customerListItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.customers_list_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mCustomerInfoList == null) {
            return;
        }

        Optional.ofNullable(mCustomerInfoList.get(position)).ifPresent(customerInfo -> {
            String name = "Name : " + customerInfo.getCustomerName();
            String pendAmt = "Pending Amount : " + customerInfo.getTotalAmountDue();

            holder.name.setText(name);
            holder.pendingAmount.setText(pendAmt);
            holder.bindCustomerInfo(position);
        });

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
        private TextView name;
        private TextView pendingAmount;
        private ImageView quickAdd;
        private LinearLayout action_button_container;
        private ImageView action_customer, action_milk_transaction, action_payment;
        private int position;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.customer_name);
            this.pendingAmount = itemView.findViewById(R.id.pending_amount);
            this.quickAdd = itemView.findViewById(R.id.quick_add_milk);
            this.action_button_container = itemView.findViewById(R.id.action_button_container);
            this.action_customer = itemView.findViewById(R.id.action_customer);
            this.action_milk_transaction = itemView.findViewById(R.id.action_milk);
            this.action_payment = itemView.findViewById(R.id.action_payment);

            setClickListeners();

        }

        private void setClickListeners() {
            quickAdd.setOnClickListener(v -> mCustomerListItemClickListener.onClickQuickAdd(position));
            action_customer.setOnClickListener(v -> mCustomerListItemClickListener.onClickActionCustomer(position));
            action_milk_transaction.setOnClickListener(v -> mCustomerListItemClickListener.onClickActionMilk(position));
            action_payment.setOnClickListener(v -> mCustomerListItemClickListener.onClickActionPayment(position));

            itemView.setOnClickListener(v -> {

                int visibility;
                if (action_button_container.getVisibility() == View.VISIBLE) {
                    visibility = View.GONE;
                    mmExpandedForPosition = -1;
                } else {
                    visibility = View.VISIBLE;
                    int oldExpandedPosition = mmExpandedForPosition;
                    mmExpandedForPosition = position;
                    //notifyItemChanged(oldExpandedPosition); animation is not feel good
                    // So kan dusre tarike se pakdte h
                    // or can think about ExpandableListView
                    boolean _done = mCustomerListItemClickListener.removeActionContainerForPositionIfPossible(oldExpandedPosition);
                    if (!_done) {
                        notifyItemChanged(oldExpandedPosition);
                        Log.d("nainf","notified");
                    }
                }
                setActionContainerVisibility(visibility);
            });
        }

        void bindCustomerInfo(int pos) {
            position = pos;
            if (position == mmExpandedForPosition) {
                setActionContainerVisibility(View.VISIBLE);
            } else {
                setActionContainerVisibility(View.GONE);
            }
        }

        private void setActionContainerVisibility(int visibility) {
            action_button_container.setVisibility(visibility);
        }

        void hideActionContainer() {
            action_button_container.setVisibility(View.GONE);
        }
    }
    //test

    interface CustomerListItemClickListener {

        boolean removeActionContainerForPositionIfPossible(int position);

        //void onClickListItem(int position);

        void onClickQuickAdd(int position);

        void onClickActionCustomer(int position);

        void onClickActionMilk(int position);

        void onClickActionPayment(int position);
    }
}
