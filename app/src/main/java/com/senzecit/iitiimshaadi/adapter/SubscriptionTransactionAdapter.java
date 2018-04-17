package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.model.api_response_model.subscription_retrieve.AllSubscription;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ravi on 24/11/17.
 */

public class SubscriptionTransactionAdapter extends RecyclerView.Adapter<SubscriptionTransactionAdapter.MyViewHolder> {

    Context mContext;
    List<AllSubscription> allSubsList;
    public SubscriptionTransactionAdapter(Context mContext, List<AllSubscription> allSubsList){
        this.mContext = mContext;
        this.allSubsList = allSubsList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mSerialNoTV, mPaymentModeTV, mTransactionIdTV, mAmountTV, mPaymentDateTV, mExpiryDateTV, mDurationTV;
        public MyViewHolder(View itemView) {
            super(itemView);

            mSerialNoTV = itemView.findViewById(R.id.idSerialNoTV);
            mPaymentModeTV = itemView.findViewById(R.id.idPaymentModeTV);
            mTransactionIdTV = itemView.findViewById(R.id.idTransactionIdTV);
            mAmountTV = itemView.findViewById(R.id.idAmountTV);
            mPaymentDateTV = itemView.findViewById(R.id.idPaymentDateTV);
            mExpiryDateTV = itemView.findViewById(R.id.idExpiryDateTV);
            mDurationTV = itemView.findViewById(R.id.idDurationTV);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscription_transaction_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.mSerialNoTV.setText(String.valueOf(allSubsList.get(position).getID()));
        holder.mPaymentModeTV.setText(allSubsList.get(position).getPaymentMode());
        holder.mTransactionIdTV.setText(String.valueOf(allSubsList.get(position).getTransactionId()));
        holder.mAmountTV.setText(allSubsList.get(position).getAmount());
        holder.mPaymentDateTV.setText(getDate(allSubsList.get(position).getPaymentDate()));
        holder.mExpiryDateTV.setText(getDate(allSubsList.get(position).getExpDate()));
        holder.mDurationTV.setText("Test");

    }

    @Override
    public int getItemCount() {
        return allSubsList.size();
    }

    public static String getDate(String _Date){

//        String _Date = "2010-09-29 08:45:22";
//        String _Date = "2018-05-02T00:00:00+0000";

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fmt2 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = fmt.parse(_Date);
            return fmt2.format(date);
        }
        catch(ParseException pe) {

            return "Date";
        }

    }
}
