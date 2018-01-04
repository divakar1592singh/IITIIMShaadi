package com.senzecit.iitiimshaadi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.ChatUserAdapter;

/**
 * Created by ravi on 23/11/17.
 */

public class ChatUserFragment extends Fragment {
    View view;
    RecyclerView mRecyclerViewChatUser;
    ChatUserFragmentCommunicator communicator;

    public void setChatUserFragmentCommunicator(ChatUserFragmentCommunicator communicator){
        this.communicator = communicator;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chatuser,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
//        ChatUserAdapter adapter = new ChatUserAdapter(communicator);
//        mRecyclerViewChatUser.setAdapter(adapter);
    }

    private void init(){
        mRecyclerViewChatUser = view.findViewById(R.id.idChatUserRV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewChatUser.setLayoutManager(layoutManager);
    }

    public interface ChatUserFragmentCommunicator{
        void gotoChat();
    }
}
