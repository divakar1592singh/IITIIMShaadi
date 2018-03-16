package com.senzecit.iitiimshaadi.viewController;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;

public class PrivacyActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    LinearLayout mPrivacyLayout;
    TextView[] titleText, descText;

    String[] titleArray = {"What information does IITIIMshaadi.com collect from you?",
            "With whom does the site share the collected information",
            "How will IITIIMshaadi.com contact you?",
            "Why do we collect UG/PG certificates?",
            "What are the various categories of members visiting our website ?",
            "What is the information that guest visitors to our website can view/access, our members whose profiles have been activated ?",
            "What are the details of an active member, which can be accessed by other active members ?",
            "What are the details of an active member, which cannot be accessed by other active (unpaid) members ?",
            "What are the details that a paid member can see of other activated members (paid and unpaid both) ?"
            };

    String[] descArray = {"We gather all the essential matrimonial related information from the users. This includes information related to self, family, contact information, education, employment, interests and also views on Partner Preferences.",
            "The information collected from our users is only shared with the activated users who have completed their respective profile. IITIIMshaadi.com does not sell or rent any identifiable information regarding its members to any third party. We are bound to share information with third party and cooperate when it is required by law or legal process to disclose required information for any investigation.",
            "The IITIIMshaadi.com team will contact you from time to time to update you on various features and functionalities of the website. They will contact you on the information provided by you on telephone number(s), Email and Social networking sites.",
            "To attempt that only genuine members participate. The certificates are only visible to administrator of the website. Whereas this precaution is being taken, yet we do not accept any legal responsibility on the veracity of these documents.",
            "Visitors to our website can be categorized as:\n" +
                    "•\tGuest Visitors\n" +
                    "•\tRegistered members (who have filled up less than 60% profile)\n" +
                    "•\tActivated members (who have filled up 60% plus profile and also submitted supporting educational certificates)\n" +
                    "• Paid members (who have paid the fee to initiate communication with other activated members) • Premier Members (who have awarded us an offline assignment to hunt just the right match)",
            "A guest visitor has no access to any information of activated members.",
            "As a matter of fact all important details and photograph(s), such as:\n" +
                    "•\tBasic Details like Marital Status, Height, Age etc\n" +
                    "•\tInformation about Religion, Mother Tongue, and Caste\n" +
                    "•\tDetails about Family, Educational Background and Job\n" +
                    "•\tOther Miscellanous information pertaining to Health Issues etc.",
            "We respect your privacy. Hence all the personal information is hidden even from activated members. This contains name of the potential Bride/Groom, UG/PG Certificate, Mail ID, Phone Number, Linked in URL, Residential Address.",
            "Paid members can see every important profile detail which includes Name,Linkedin-ID etc. However, documents will not be visible to any member. Your contact phone number and Email ID are also not visible even to paid members unless you accept friend request sent by them. Paid members are advised to connect with each other by sending friend request first to indicate interest. Moment your friend request is accepted, you shall be able to view the contact number and E-mail ID of such a member. Alternatively, you could send a private message and share your contact details. Responding or not responding to the received private message is the prerogative of the recipient. There is a provision available for you to check if your message has been read or not (double tick, black and blue like you see in whatsapp)"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        init();
        mBack.setOnClickListener(PrivacyActivity.this);

    }

    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mTitle.setText("Privacy Policy");
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);

        mPrivacyLayout = (LinearLayout)findViewById(R.id.idPrivacyLayout);
        parseData();
    }

    public void parseData(){

        titleText = new TextView[titleArray.length];
        descText = new TextView[titleArray.length];

        for(int i=0; i<titleArray.length; i++){

            titleText[i] = new TextView(PrivacyActivity.this);
            descText[i] = new TextView(PrivacyActivity.this);

//            SETTEXT
            titleText[i].setText(i+1+". "+titleArray[i]);
            descText[i].setText(" "+descArray[i]);
//            COLOR
            titleText[i].setTextColor(getResources().getColor(R.color.colorBlack));
            descText[i].setTextColor(getResources().getColor(R.color.colorBlack));
//            SIZE
            titleText[i].setTextSize(16);
            descText[i].setTextSize(16);

//            BOLD
            titleText[i].setTypeface(null, Typeface.BOLD);
//            PADDING
            titleText[i].setPadding(0, 16, 0, 4);

            mPrivacyLayout.addView(titleText[i]);
            mPrivacyLayout.addView(descText[i]);

        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.backIV:
                PrivacyActivity.this.finish();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }
}
