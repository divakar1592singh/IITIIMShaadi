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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.MyFriendsAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.my_friends.AllFriend;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.my_friends.MyFriendsResponse;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.RecyclerItemClickListener;
import com.senzecit.iitiimshaadi.utils.UserDefinedKeyword;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;
import com.senzecit.iitiimshaadi.viewController.OtherProfileActivity;

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
    AppPrefs prefs;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
         listener = (OnMyFriendListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_my_friends,container,false);
        prefs = AppController.getInstance().getPrefs();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        callWebServiceForMyFriend();

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        handleView();

    }

    private void init(){
        mRecyclerView = view.findViewById(R.id.myFriendListRV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }



    public void handleView(){

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), mRecyclerView,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever

                        TextView tvUserID = (TextView)view.findViewById(R.id.idUserIDTV);
                        String userID = tvUserID.getText().toString();
//                        Toast.makeText(getContext(), "Short : "+userID, Toast.LENGTH_SHORT).show();

                        Button btnRemoveFriend = (Button) view.findViewById(R.id.idRemoveFriendBtn);
                        btnRemoveFriend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Toast.makeText(getContext(), "Add as Friend : "+userID, Toast.LENGTH_SHORT).show();
                                listener.onFragmentRemoveFriend(UserDefinedKeyword.REMOVE.toString(), userID);

                            }
                        });

                        Button btnUnShortList = (Button) view.findViewById(R.id.idShortlistBtn);
                        btnUnShortList.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Toast.makeText(getContext(), "Add as Friend : "+userID, Toast.LENGTH_SHORT).show();
                                listener.onFragmentShortListFriend(UserDefinedKeyword.SHORTLIST.toString(), userID);
                            }
                        });

                        Button btnViewProfile = (Button) view.findViewById(R.id.idViewProfileBtn);
                        btnViewProfile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Toast.makeText(getContext(), "View Profile : "+userID, Toast.LENGTH_SHORT).show();
                                if(userID.length()> 0){
                                    prefs.putString(CONSTANTS.OTHER_USERID, userID);
                                    Navigator.getClassInstance().navigateToActivity(getActivity(), OtherProfileActivity.class);
                                }
                            }
                        });

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                        //Initialize

//                        ImageView mSelectedIV = (ImageView)view.findViewById(R.id.idSelectionIV) ;

//                        resetData(mSelectedIV);
//                        Glide.with(getActivity()).load(R.drawable.ic_done).error(R.drawable.ic_transparent).into(mSelectedIV);

//                        Toast.makeText(getContext(), "Long", Toast.LENGTH_SHORT).show();
                    }
                })
        );

    }

    /** API */
    public void callWebServiceForMyFriend(){

//        String token = CONSTANTS.Token_Paid;
        AppPrefs prefs = AppController.getInstance().getPrefs();
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        ProgressClass.getProgressInstance().showDialog(getActivity());
        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        Call<MyFriendsResponse> call = apiInterface.myFriends(token);
        call.enqueue(new Callback<MyFriendsResponse>() {
            @Override
            public void onResponse(Call<MyFriendsResponse> call, Response<MyFriendsResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    MyFriendsResponse serverResponse = response.body();
                    if(serverResponse.getMessage().getSuccess() != null) {
                        if (serverResponse.getMessage().getSuccess().toString().equalsIgnoreCase("success")) {
//                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
//                            AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Rename Folder", "Folder rename succesfull.");

                            List<AllFriend> allFriendList = serverResponse.getAllFriend();

                            setDataToAdapter(allFriendList);
                        } else {
//                            Toast.makeText(getActivity(), "Confuse", Toast.LENGTH_SHORT).show();
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
        void onFragmentRemoveFriend(String typeOf, String otherUserId);
        void onFragmentShortListFriend(String typeOf, String otherUserId);

    }

}
