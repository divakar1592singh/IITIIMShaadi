package com.senzecit.iitiimshaadi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.SubscriptionTransactionAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.subscription_retrieve.AllSubscription;
import com.senzecit.iitiimshaadi.model.api_response_model.subscription_retrieve.SubsRetrieveResponse;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.LinearLayoutManagerWithSmoothScroller;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ravi on 22/11/17.
 */

public class SubscriptionFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "SubscriptionFragment";
    View view;
    Button mUpgradeSubscription;
    SubscriptionFragmentCommunicator communicator;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    TextView mLastRenewTV, mNextRenewTV;
    TextView mUserTypeTV;
    FrameLayout mSubscriptionLayout;
    AppPrefs prefs;

    public void setSubscriptionFragmentCommunicator(SubscriptionFragmentCommunicator communicator){
        this.communicator = communicator;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        prefs = new AppPrefs(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_subscription_new,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        callWebServiceForSubscription();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        handleView();
    }

    private void init(){

        mSubscriptionLayout = view.findViewById(R.id.idSubscriptionLayout);
        mUserTypeTV = view.findViewById(R.id.idUserType);
       mUpgradeSubscription = view.findViewById(R.id.idSubscriptionBtn);

        mLastRenewTV = view.findViewById(R.id.idLastRenewTV);
        mNextRenewTV = view.findViewById(R.id.idNextRenewTV);

        mRecyclerView = view.findViewById(R.id.idSubsTranDetail_RV);
        mRecyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(getActivity()));
        mRecyclerView.smoothScrollToPosition(0);
    }

    public void handleView(){

        try {
            String sUserType = prefs.getString(CONSTANTS.LOGGED_USER_TYPE);
            mUserTypeTV.setText(sUserType);

            String userType = prefs.getString(CONSTANTS.LOGGED_USER_TYPE);
            if (userType.equalsIgnoreCase("paid_subscriber_viewer")) {
                mUserTypeTV.setText("Paid Subscriber Viewer");
                mSubscriptionLayout.setVisibility(View.GONE);
            } else if (userType.equalsIgnoreCase("subscriber_viewer")) {
                mUserTypeTV.setText("Subscriber Viewer");
                mSubscriptionLayout.setVisibility(View.VISIBLE);
            } else if (userType.equalsIgnoreCase("subscriber")) {
                mUserTypeTV.setText("Subscriber");
                mSubscriptionLayout.setVisibility(View.VISIBLE);
            }



        }catch (NullPointerException npe){
            Log.e(TAG, " #Error : "+npe, npe);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.idSubscriptionBtn:
                communicator.upgradeSubscription();
                break;
        }
    }

    public interface SubscriptionFragmentCommunicator{
        void upgradeSubscription();
    }

    /** Check API Section */
    public void callWebServiceForSubscription(){

//        String token = CONSTANTS.Token_Paid;
        String token =  prefs.getString(CONSTANTS.LOGGED_TOKEN);


        if(NetworkClass.getInstance().checkInternet(getActivity()) == true){

        ProgressClass.getProgressInstance().showDialog(getActivity());
        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);

        Call<SubsRetrieveResponse> call = apiInterface.retrieveSubscription(token);
        call.enqueue(new Callback<SubsRetrieveResponse>() {
            @Override
            public void onResponse(Call<SubsRetrieveResponse> call, Response<SubsRetrieveResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    SubsRetrieveResponse subsResponse = response.body();
                    if(subsResponse.getMessage().getSuccess() != null) {
                        if (subsResponse.getMessage().getSuccess().equalsIgnoreCase("success")) {

                            List<AllSubscription> allSubsList = subsResponse.getAllSubscriptions();
                            setSubsToRecyclerView(allSubsList);
//                            AlertDialogSingleClick.getInstance().showDialog(LoginActivity.this, "Forgot Password", "An email with new password is sent to your registered email.");
//                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

                        } else {
//                            Toast.makeText(getActivity(), "Confuse", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SubsRetrieveResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(getActivity());
        }
    }

    public void setSubsToRecyclerView(List<AllSubscription> allSubsList){

        setData(allSubsList);
        mUpgradeSubscription.setOnClickListener(this);
        SubscriptionTransactionAdapter adapter = new SubscriptionTransactionAdapter(getActivity(), allSubsList);
        mRecyclerView.setAdapter(adapter);

    }

    public void setData(List<AllSubscription> allSubsList){
        try {
            mLastRenewTV.setText(getDate(allSubsList.get(0).getPaymentDate()));
            mNextRenewTV.setText(getDate(allSubsList.get(0).getExpDate()));
        }catch (IndexOutOfBoundsException ioe){
            Log.e("TAG", "#Error : "+ioe, ioe);
        }
    }


    public static String getDate(String _Date){

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
