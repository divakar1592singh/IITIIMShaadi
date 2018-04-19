package com.senzecit.iitiimshaadi.viewController;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.RxNetworkingForObjectClass;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;

import org.json.JSONException;
import org.json.JSONObject;

public class TestActivity extends AppCompatActivity implements RxNetworkingForObjectClass.CompletionHandler{

    RxNetworkingForObjectClass networkingClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        networkingClass = RxNetworkingForObjectClass.getInstance();
        networkingClass.setCompletionHandler(this);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", "97981");

            RxNetworkingForObjectClass.getInstance().callWebServiceForJSONParsing(TestActivity.this, "http://35.154.217.225:1110/getRecentSearch", jsonObject, CONSTANTS.METHOD_1);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void handle(JSONObject object, String methodName) {

        System.out.println(object);
    }
}
