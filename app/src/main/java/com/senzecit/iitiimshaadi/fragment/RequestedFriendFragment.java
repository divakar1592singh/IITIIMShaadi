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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.RequestFriendAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.chat.SocketSingleChatActivity;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.requested_friend.AllRequestFriend;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.requested_friend.RequestedFriendResponse;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTPREF;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.RecyclerItemClickListener;
import com.senzecit.iitiimshaadi.utils.UserDefinedKeyword;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;
import com.senzecit.iitiimshaadi.viewController.OtherProfileActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ravi on 15/11/17.
 */

public class RequestedFriendFragment extends Fragment {
    RecyclerView mRecyclerView;
    View view;
    AppPrefs prefs;

    OnRequestedFriendListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (OnRequestedFriendListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_requested_friend,container,false);
        prefs = AppController.getInstance().getPrefs();
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        handleView();
/*
        RequestFriendAdapter adapter = new RequestFriendAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);*/
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        callWebServiceForRequestedFriend();


    }

    private void init(){
        mRecyclerView = view.findViewById(R.id.requestFriendListRV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }


    public void handleView(){

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), mRecyclerView,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever

                        TextView tvUserID = view.findViewById(R.id.idUserIDTV);
                        TextView tvUserName = view.findViewById(R.id.idUserNameTV);

                        String userID = tvUserID.getText().toString();
                        String username = tvUserName.getText().toString();
//                        Toast.makeText(getContext(), "Short : "+userID, Toast.LENGTH_SHORT).show();

                        LinearLayout layout = view.findViewById(R.id.idProfileLayout);
                        layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Toast.makeText(getContext(), "Add as Friend : "+userID, Toast.LENGTH_SHORT).show();
//                                listener.onFragmentAddFriend(UserDefinedKeyword.ADD.toString(), userID);

                                if(userID.length()> 0){
                                    prefs.putString(CONSTANTS.OTHER_USERID, userID);
                                    Navigator.getClassInstance().navigateToActivity(getActivity(), OtherProfileActivity.class);
                                }
                            }
                        });


                        Button btnAddFriend = view.findViewById(R.id.idAddFriendBtn);
                        btnAddFriend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Toast.makeText(getContext(), "Add as Friend : "+userID, Toast.LENGTH_SHORT).show();
                                listener.onFragmentAddFriend(UserDefinedKeyword.ADD.toString(), userID);

                            }
                        });


                        Button btnCancelReq = view.findViewById(R.id.idCancelReqBtn);
                        btnCancelReq.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Toast.makeText(getContext(), "Cancel : "+userID, Toast.LENGTH_SHORT).show();
                                listener.onFragmentCancelReq(UserDefinedKeyword.CANCEL.toString(), userID);
                            }
                        });


                        Button btnUnShortList = view.findViewById(R.id.idShortlistBtn);
                        btnUnShortList.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Toast.makeText(getContext(), "Shortlist Friend : "+userID, Toast.LENGTH_SHORT).show();
                                listener.onFragmentShortListFriend(UserDefinedKeyword.SHORTLIST.toString(), userID);
                            }
                        });


                        Button mSubsProfileBtn = view.findViewById(R.id.idSubsProfileBtn);
                        mSubsProfileBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Toast.makeText(getContext(), "View Profile : " + userID, Toast.LENGTH_SHORT).show();
                                if (userID.length() > 0) {
                                    prefs.putString(CONSTANTS.OTHER_USERID, userID);
                                    Navigator.getClassInstance().navigateToActivity(getActivity(), OtherProfileActivity.class);
                                }
                            }
                        });

                        Button mChat = view.findViewById(R.id.idChatBtn);
                        mChat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Toast.makeText(getContext(), "View Profile : " + userID, Toast.LENGTH_SHORT).show();
                                if (userID.length() > 0) {
                                    prefs.putString(CONSTANTS.OTHER_USERID, userID);
                                    try {
                                        prefs.putString(CONSTANTS.OTHER_USERNAME, username.split("\\s")[0]);
                                    }catch (IndexOutOfBoundsException iobe){

                                    }catch (NullPointerException npe){

                                    }
                                    Navigator.getClassInstance().navigateToActivity(getActivity(), SocketSingleChatActivity.class);
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
    public void callWebServiceForRequestedFriend(){

        AppPrefs prefs = AppController.getInstance().getPrefs();
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(NetworkClass.getInstance().checkInternet(getActivity()) == true){

        if(prefs.getInt(CONSTANTPREF.PROGRESS_STATUS_FOR_TAB) != 1){
            ProgressClass.getProgressInstance().showDialog(getActivity());
        }else {
            prefs.putInt(CONSTANTPREF.PROGRESS_STATUS_FOR_TAB, 2);
        }

        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        Call<RequestedFriendResponse> call = apiInterface.requestedFriends(token);
        call.enqueue(new Callback<RequestedFriendResponse>() {
            @Override
            public void onResponse(Call<RequestedFriendResponse> call, Response<RequestedFriendResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    RequestedFriendResponse serverResponse = response.body();
                    if(serverResponse.getMessage().getSuccess() != null) {
                        if (serverResponse.getMessage().getSuccess().equalsIgnoreCase("success")) {
//                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
//                            AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Rename Folder", "Folder rename succesfull.");

                            List<AllRequestFriend> allFriendList = serverResponse.getAllRequestFriend();

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
            public void onFailure(Call<RequestedFriendResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(getActivity());
        }
    }


    public void setDataToAdapter(List<AllRequestFriend> allFriendList){

            listener.onFragmentSetRequestedFriend(allFriendList.size());
            RequestFriendAdapter adapter = new RequestFriendAdapter(getActivity(), allFriendList);
            mRecyclerView.setAdapter(adapter);



    }


    public interface OnRequestedFriendListener {
        void onFragmentSetRequestedFriend(int size);
        void onFragmentAddFriend(String typeOf, String otherUserId);
        void onFragmentCancelReq(String typeOf, String otherUserId);
        void onFragmentShortListFriend(String typeOf, String otherUserId);
    }


}