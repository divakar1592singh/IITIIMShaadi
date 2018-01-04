package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;

import java.util.List;

/**
 * Created by ravi on 24/11/17.
 */

public class SubscriptionTransactionAdapter extends RecyclerView.Adapter<SubscriptionTransactionAdapter.MyViewHolder> {

    Context mContext;
    List<String> list;
    public SubscriptionTransactionAdapter(Context mContext, List<String> list){
        this.mContext = mContext;
        this.list = list;
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

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
