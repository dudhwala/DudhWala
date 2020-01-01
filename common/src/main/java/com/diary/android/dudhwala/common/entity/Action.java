package com.diary.android.dudhwala.common.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//saves all the actions done by seller or buyer.
@Entity(tableName = "actions_table")
public class Action {

    public Action(int actionPerformer, int customerId, int transactionId,
                  int action, int milkType, int milkQuantity,
                  int pricePerLiter, long date) {
        this.actionPerformer = actionPerformer;
        this.customerId = customerId;
        this.transactionId = transactionId;
        this.action = action;
        this.milkType = milkType;
        this.milkQuantity = milkQuantity;
        this.pricePerLiter = pricePerLiter;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActionPerformer() {
        return actionPerformer;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getAction() {
        return action;
    }

    public int getMilkType() {
        return milkType;
    }

    public int getMilkQuantity() {
        return milkQuantity;
    }

    public int getPricePerLiter() {
        return pricePerLiter;
    }

    public long getDate() {
        return date;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    //TODO finalize it, need to keep id also or something else
    //seller, customer
    @ColumnInfo(name = "action_performer")
    private int actionPerformer;

    //same as customer_table => _id column
    @ColumnInfo(name = "customer_id")
    private int customerId;

    //same as transaction_table => _id column
    @ColumnInfo(name = "transaction_id")
    private int transactionId;

    //add = 1
    //update = 2
    //delete = 3
    //undo-delete = 4
    @ColumnInfo(name = "action")
    private int action;

    //Cow = 1
    //Buffalo = 2
    @ColumnInfo(name = "milk_type")
    private int milkType;

    @ColumnInfo(name = "milk_quantity")
    private int milkQuantity;

    @ColumnInfo(name = "price_per_liter")
    private int pricePerLiter;

    @ColumnInfo(name = "date")
    private long date;

}