package com.senzecit.iitiimshaadi.chat;

/**
 * Created by senzec on 22/2/18.
 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * A chat fragment containing messages view and input form.
 */
public class MainFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "MainFragment";
    private static final int REQUEST_LOGIN = 0;
    private static final int TYPING_TIMER_LENGTH = 600;
    private RecyclerView mMessagesView;
    private EditText mInputMessageView;
    private List<Message> mMessages = new ArrayList<Message>();
    private RecyclerView.Adapter mAdapter;
    private boolean mTyping = false;
    private Handler mTypingHandler = new Handler();
    private String mUsername;
    private Socket mSocket;
    private Boolean isConnected = true;
    ImageView mBackIV;
    View view;
    AppPrefs prefs;

    public MainFragment() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAdapter = new MessageAdapter(context, mMessages);
        if (context instanceof Activity){
            //this.listener = (MainActivity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        prefs = new AppPrefs(getActivity());
        AppController app = (AppController) getActivity().getApplication();
        mSocket = app.getSocket();
        mSocket.on(Socket.EVENT_CONNECT,onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT,onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on("receiveMessage", onNewMessage);
        //mSocket.on("new message", onNewMessage);
        mSocket.on("user joined", onUserJoined);
        mSocket.on("user left", onUserLeft);
        mSocket.on("typing", onTyping);
        mSocket.on("stop typing", onStopTyping);
        mSocket.connect();

        startSignIn();
//        previousChatWebApi();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_socket_chat_single, container, false);
        //      addMessage("User", "This is test message");
        TextView tv_title = (TextView)view.findViewById(R.id.idUserNameTV) ;
//        tv_title.setText(new SharedPrefClass(getActivity()).getChatReceiverName());
        tv_title.setText(String.valueOf(prefs.getString(CONSTANTS.LOGGED_USERID)));

        ImageView mProfileIV =(ImageView)view.findViewById(R.id.idProfileIV);
        mProfileIV.setVisibility(View.GONE);

        ImageView iv_back=(ImageView)view.findViewById(R.id.idBackIV);
        iv_back.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //      mSocket.disconnect();

        mSocket.off(Socket.EVENT_CONNECT, onConnect);
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.off("receiveMessage", onNewMessage);
        //mSocket.off("new message", onNewMessage);
        mSocket.off("user joined", onUserJoined);
        mSocket.off("user left", onUserLeft);
        mSocket.off("typing", onTyping);
        mSocket.off("stop typing", onStopTyping);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMessagesView = (RecyclerView) view.findViewById(R.id.messages);
        mMessagesView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMessagesView.setAdapter(mAdapter);

        mInputMessageView = (EditText) view.findViewById(R.id.message_input);
 /*       mInputMessageView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == R.id.send || id == EditorInfo.IME_NULL) {
                    attemptSend();
                    return true;
                }
                return false;
            }
        });*/
        mInputMessageView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (null == mUsername) return;
                if (!mSocket.connected()) return;

                if (!mTyping) {
                    mTyping = true;
                    mSocket.emit("typing");
                }

                mTypingHandler.removeCallbacks(onTypingTimeout);
                mTypingHandler.postDelayed(onTypingTimeout, TYPING_TIMER_LENGTH);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ImageButton sendButton = (ImageButton) view.findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSend();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK != resultCode) {
            getActivity().finish();
            return;
        }

        mUsername = data.getStringExtra("username");
        int numUsers = data.getIntExtra("numUsers", 1);

//        addLog(getResources().getString(R.string.message_welcome));
//        addParticipantsLog(numUsers);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_leave) {
            leave();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

  /*  private void previousChatWebApi(){
        ApiInterface apiInterface;
        SingleChatPostRequest chatPostRequest = new SingleChatPostRequest();
        chatPostRequest.senderId = new SharedPrefClass(getActivity()).getLoginInfo();
        chatPostRequest.receiverId = new SharedPrefClass(getActivity()).getReceiverID();

        apiInterface = ApiClient.getClient(CONSTANTS.CHAT_HISTORY_URL).create(ApiInterface.class);
        Call<SingleChatHistoryModel> call1 = apiInterface.singleChatPreviousHistory(chatPostRequest);
        call1.enqueue(new Callback<SingleChatHistoryModel>() {
            @Override
            public void onResponse(Call<SingleChatHistoryModel> call, Response<SingleChatHistoryModel> response) {
                if(response.isSuccessful()&&response.code()==200) {
                    try{
                        if(response.body().getResponseCode() == 200) {
//                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                            List<Result> chatList = response.body().getResult();
                            String profileUrl = "";
                            String userId = "";
                            String userName = "";
                            String message = "";

                            for(int i = 0; i<chatList.size(); i++) {

                                profileUrl = chatList.get(i).getSenderImage();
                                userId = chatList.get(i).getSenderId();
                                userName = chatList.get(i).getSenderName();
                                message = chatList.get(i).getMessage();

                                addMessage( profileUrl, userId, userName, message);
                            }
                        }else {
//                            Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }catch (NullPointerException npe){
                        Log.e(TAG, "#Error : "+npe, npe);
                    }
                } else {
//                    Toast.makeText(getActivity(), "Confused", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SingleChatHistoryModel> call, Throwable t) {
                call.cancel();
//                Toast.makeText(getActivity(), "Something Wrong from server", Toast.LENGTH_SHORT).show();
                //    ProgressClass.getProgressInstance().stopProgress();
            }
        });

    }
*/
    private void addLog(String message) {
        mMessages.add(new Message.Builder(Message.TYPE_LOG)
                .message(message).build());
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        scrollToBottom();
    }

    private void addParticipantsLog(int numUsers) {
//        addLog(getResources().getQuantityString(R.plurals.message_participants, numUsers, numUsers));
    }

    private void addMessage(String profileUrl, String userId, String username, String message) {
        mMessages.add(new Message.Builder(Message.TYPE_MESSAGE)
                .profileUrl(profileUrl)
                .userId(userId)
                .username(username).message(message).build());
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        scrollToBottom();
    }

    private void addTyping(String username) {
        mMessages.add(new Message.Builder(Message.TYPE_ACTION)
                .username(username).build());
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        scrollToBottom();
    }

    private void removeTyping(String username) {
        for (int i = mMessages.size() - 1; i >= 0; i--) {
            Message message = mMessages.get(i);
            if (message.getType() == Message.TYPE_ACTION && message.getUsername().equals(username)) {
                mMessages.remove(i);
                mAdapter.notifyItemRemoved(i);
            }
        }
    }

    private void attemptSend() {
        if (null == mUsername) return;
        if (!mSocket.connected()) return;

        mTyping = false;

        String message = mInputMessageView.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            mInputMessageView.requestFocus();
            return;
        }

//        String url = "https://cdn-images-1.medium.com/max/1600/1*U5N_ryt8h94nyO9oaPW9nw.png";
        String senderId = prefs.getString(CONSTANTS.LOGGED_USERID);
        mInputMessageView.setText("");
        addMessage("", senderId, mUsername, message);


        JSONObject obj = new JSONObject();
        String receiverId = prefs.getString(CONSTANTS.OTHER_USERID);
        //   String senderId = prefs.getString(App.Key.ID_LOGGED);
        String senderName = prefs.getString(CONSTANTS.LOGGED_USERNAME);
        String senderImageUrl = prefs.getString(CONSTANTS.LOGGED_USER_PIC);

//        String currentReceiver = "";

//        System.out.println("ReciverId: "+receiverId+" ,SenderId: "+senderId);
        try {
            obj.put("from_user", senderId);
       /*     if(senderImageUrl != null)
            {obj.put("senderImage", senderImageUrl);}else {
                obj.put("senderImage", "http://www.vermeer.com.au/wp-content/uploads/2016/12/attachment-no-image-available-300x300.png");
            }
            obj.put("senderName", senderName);
*/
            obj.put("to_user", receiverId);
            obj.put("message", message);
            obj.put("sent", new Date());


        }catch (JSONException e) {
            e.printStackTrace();
        }
        // perform the sending message attempt.
        //mSocket.emit("initChat", new SharedPrefClass(LoginActivity.this).getLoginInfo());
        //      mSocket.emit("initChat", obj);

        mSocket.emit("sendMessage", obj);
        //mSocket.emit("sendmessage", convertToJSON(message));

        //      mSocket.on("receiveMessage", onNewMessage);

    }

    private void startSignIn() {
        mUsername = null;
        Intent intent = new Intent(getActivity(), ChatLoginActivity.class);
        startActivityForResult(intent, REQUEST_LOGIN);
    }

    private void leave() {
        mUsername = null;
//        mSocket.disconnect();
        mSocket.connect();
        startSignIn();
    }

    private void scrollToBottom() {
        try {
            mMessagesView.scrollToPosition(mAdapter.getItemCount() - 1);
        }catch (NullPointerException npe){
            Log.e(TAG, "#Error : "+npe, npe);
        }
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!isConnected) {
                        if(null!=mUsername) {
                            JSONObject obj = new JSONObject();
                            try {
                                obj.put("userId", prefs.getString(CONSTANTS.LOGGED_USERID));

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                            mSocket.emit("initChat", obj);
                            //    mSocket.emit("add user", mUsername);

                            Toast.makeText(getActivity().getApplicationContext(),
                                    R.string.connect, Toast.LENGTH_LONG).show();
                            isConnected = true;
                        }
                    }
                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "diconnected");
                    isConnected = false;
//                    Toast.makeText(getActivity().getApplicationContext(),
//                            R.string.disconnect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "Error connecting");
                    Toast.makeText(getActivity().getApplicationContext(),
                            R.string.error_connect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String userId, userName, userImageUrl, message;

                    try {
//                        Toast.makeText(getActivity(), "RECEIVING Data", Toast.LENGTH_LONG).show();
                        /*userId = data.getString("senderId");
                        message = data.getString("message");

                        *//*userImageUrl = "sender_image";
                        userName = "sender_name";*//*
                        userImageUrl = data.getString("senderImage");
                        userName = data.getString("senderName");*/
                        /*if(userId.length()>0){
                            new SharedPrefClass(getActivity()).setChatSenderId(username);
                        }
*/
                        //       Toast.makeText(getActivity(), "Received block", Toast.LENGTH_LONG).show();
                        //message = data.getString("message");
                        //         addMessage(mUsername, message);

                        userId = data.getString("from_user");
                        message = data.getString("message");

                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                        new AlertDialog.Builder(getActivity())
                                .setMessage("Error - Received")
                                .show();
                        //  Toast.makeText(getActivity(), "Exception - Received block", Toast.LENGTH_LONG).show();
                        return;
                    }

//                    removeTyping(userName);
//                    addMessage(userImageUrl, userId, userName, message);

;                    addMessage("", userId, null, message);

                }
            });
        }
    };

    private Emitter.Listener onUserJoined = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    int numUsers;
                    try {
                        username = data.getString("username");
                        numUsers = data.getInt("numUsers");
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                        return;
                    }

                    addLog(getResources().getString(R.string.message_user_joined, username));
                    addParticipantsLog(numUsers);
                }
            });
        }
    };

    private Emitter.Listener onUserLeft = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    int numUsers;
                    try {
                        username = data.getString("username");
                        numUsers = data.getInt("numUsers");
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                        return;
                    }

                    addLog(getResources().getString(R.string.message_user_left, username));
                    addParticipantsLog(numUsers);
                    removeTyping(username);
                }
            });
        }
    };

    private Emitter.Listener onTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    try {
                        username = data.getString("username");
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                        return;
                    }
                    addTyping(username);
                }
            });
        }
    };

    private Emitter.Listener onStopTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    try {
                        username = data.getString("username");
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                        return;
                    }
                    removeTyping(username);
                }
            });
        }
    };

    private Runnable onTypingTimeout = new Runnable() {
        @Override
        public void run() {
            if (!mTyping) return;

            mTyping = false;
            mSocket.emit("stop typing");
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.idBackIV:
                getActivity().finish();
                break;
        }
    }
}

