package com.senzecit.iitiimshaadi.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.MyFriendsAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.my_friends.AllFriend;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.my_friends.MyFriendsResponse;
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ravi on 15/11/17.
 */

public class MyFriendsFragment extends Fragment {
    RecyclerView mRecyclerView;
    View view;
    ArrayList<String> myFriendList;
    OnMyFriendListener  listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
         listener = (OnMyFriendListener) activity;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_my_friends,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

/*
        myFriendList = new ArrayList<>();
        myFriendList.add("nsp");
        myFriendList.add("cp");
        myFriendList.add("mango");
        myFriendList.add("nsp");
        myFriendList.add("cp");
        myFriendList.add("mango");
*/

        callWebServiceForMyFriend();

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

    }

    private void init(){
        mRecyclerView = view.findViewById(R.id.myFriendListRV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }


    /** API */
    public void callWebServiceForMyFriend(){

//        String token = Constants.Token_Paid;
        AppPrefs prefs = new AppPrefs(getActivity());
        String token = prefs.getString(Constants.LOGGED_TOKEN);

        ProgressClass.getProgressInstance().showDialog(getActivity());
        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
        Call<MyFriendsResponse> call = apiInterface.myFriends(token);
        call.enqueue(new Callback<MyFriendsResponse>() {
            @Override
            public void onResponse(Call<MyFriendsResponse> call, Response<MyFriendsResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    MyFriendsResponse serverResponse = response.body();
                    if(serverResponse.getMessage().getSuccess() != null) {
                        if (serverResponse.getMessage().getSuccess().toString().equalsIgnoreCase("success")) {
                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
//                            AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Rename Folder", "Folder rename succesfull.");

                            List<AllFriend> allFriendList = serverResponse.getAllFriend();

                            setDataToAdapter(allFriendList);
                        } else {
                            Toast.makeText(getActivity(), "Confuse", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyFriendsResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });
    }

    public void setDataToAdapter(List<AllFriend> allFriendList){

        listener.onFragmentSetSetMyFriend(allFriendList.size());
        MyFriendsAdapter adapter = new MyFriendsAdapter(getActivity(), allFriendList);
        mRecyclerView.setAdapter(adapter);

    }


    public interface OnMyFriendListener {
        void onFragmentSetSetMyFriend(int size);
    }

}
