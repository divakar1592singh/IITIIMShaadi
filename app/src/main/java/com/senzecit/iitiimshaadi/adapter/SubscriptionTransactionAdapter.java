package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.model.api_response_model.subscription_retrieve.AllSubscription;

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

            mSerialNoTV = (TextView)itemView.findViewById(R.id.idSerialNoTV) ;
            mPaymentModeTV = (TextView)itemView.findViewById(R.id.idPaymentModeTV) ;
            mTransactionIdTV = (TextView)itemView.findViewById(R.id.idTransactionIdTV) ;
            mAmountTV = (TextView)itemView.findViewById(R.id.idAmountTV) ;
            mPaymentDateTV = (TextView)itemView.findViewById(R.id.idPaymentDateTV) ;
            mExpiryDateTV = (TextView)itemView.findViewById(R.id.idExpiryDateTV) ;
            mDurationTV = (TextView)itemView.findViewById(R.id.idDurationTV) ;

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
        holder.mPaymentDateTV.setText(allSubsList.get(position).getPaymentDate());
        holder.mExpiryDateTV.setText(allSubsList.get(position).getExpDate());
        holder.mDurationTV.setText("Test");

    }

    @Override
    public int getItemCount() {
        return allSubsList.size();
    }

}
