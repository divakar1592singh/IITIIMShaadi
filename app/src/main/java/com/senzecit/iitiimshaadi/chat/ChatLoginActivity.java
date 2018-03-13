package com.senzecit.iitiimshaadi.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatLoginActivity extends AppCompatActivity {

    private Socket mSocket;
    AppPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_login);

        AppController app = (AppController) getApplication();
        prefs = AppController.getInstance().getPrefs();

        mSocket = app.getSocket();

        attemptLogin();
        Intent intent = new Intent();
//        intent.putExtra("username", new SharedPrefClass(ChatLoginActivity.this).getLoginInfo());
        intent.putExtra("username", prefs.getString(CONSTANTS.LOGGED_USERNAME));
        intent.putExtra("numUsers", 1);
        setResult(RESULT_OK, intent);
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        //     mSocket.off("login", onLogin);
    }
    private void attemptLogin() {

        JSONObject obj = new JSONObject();
        try {
//            obj.put("userId", new SharedPrefClass(ChatLoginActivity.this).getLoginInfo());
            obj.put("userId", prefs.getString(CONSTANTS.LOGGED_USERID));

        }catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.emit("initChat", obj);

    }

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];

            int numUsers;
            try {
                numUsers = data.getInt("numUsers");
            } catch (JSONException e) {
                return;
            }
            Intent intent = new Intent();
//            intent.putExtra("username", new SharedPrefClass(ChatLoginActivity.this).getLoginInfo());
            intent.putExtra("username", prefs.getString(CONSTANTS.LOGGED_USERNAME));
            intent.putExtra("numUsers", numUsers);
            setResult(RESULT_OK, intent);
            finish();
        }
    };
}
