package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.date_to_age.DateToAgeResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.date_to_age.Message;
import com.senzecit.iitiimshaadi.model.api_response_model.my_profile.MyProfileResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.other_profile.OtherProfileResponse;
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OtherExpListAdapter extends BaseExpandableListAdapter {


    private Context _context;
    LayoutInflater layoutInflater;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    OtherProfileResponse myProfileResponse;

    public OtherExpListAdapter(Context context, List<String> listDataHeader,
                               HashMap<String, List<String>> listChildData, OtherProfileResponse myProfileResponse) {
        this._context = context;
        layoutInflater = LayoutInflater.from(_context);
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.myProfileResponse = myProfileResponse;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        //****lchild data items start0
        switch (groupPosition) {
            case 0:
                switch (childPosition) {
                    case 0:
                        LayoutInflater infalInflater = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild.setText(childText);

                        TextView txtListChildHeader = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader.setText(childText);
                        //SetData - Name
                        txtListChild.setText(myProfileResponse.getBasicData().getName());

                        break;
                    case 1:
                        LayoutInflater infalInflater1 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater1.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild1 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild1.setText(childText);

                        TextView txtListChildHeader1 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader1.setText(childText);
                        txtListChild1.setText(myProfileResponse.getBasicData().getProfileCreatedFor());

                        break;
                    case 2:
                        LayoutInflater infalInflater2 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater2.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild2 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild2.setText(childText);

                        TextView txtListChildHeader2 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader2.setText(childText);

                        //SetData - Age
//                        txtListChildHeader.setText(myProfileResponse.getBasicData().getDiet());
//                        txtListChild2.setText("Test");jtyj
                        formattedDate(txtListChild2, myProfileResponse.getBasicData().getBirthDate());

                        break;
                    case 3:
                        LayoutInflater infalInflater3 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater3.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild3 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild3.setText(childText);

                        TextView txtListChildHeader3 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader3.setText(childText);

                        //SetData - Diet
                        txtListChild3.setText(myProfileResponse.getBasicData().getDiet());

                        break;
                    case 4:
                        LayoutInflater infalInflater4 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater4.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild4 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild4.setText(childText);

                        TextView txtListChildHeader4 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader4.setText(childText);

                        //SetData - DOB
                        if(myProfileResponse.getBasicData().getBirthDate() != null){

                            String dateOfBirth = myProfileResponse.getBasicData().getBirthDate();
                            txtListChild4.setText(getDate(dateOfBirth));
                        }

                        break;
                    case 5:
                        LayoutInflater infalInflater5 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater5.inflate(R.layout.list_item_non_editable, null);

                        /*maritalStatusTV= (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        maritalStatusTV.setText(childText);*/
                        final TextView txtListChild5 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild5.setText(childText);

                        final TextView txtListChildHeader5 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader5.setText(childText);

                        //SetData - MaritalStatus
                        txtListChild5.setText(myProfileResponse.getBasicData().getMaritalStatus());

                        break;
                    case 6:
                        LayoutInflater infalInflater6 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater6.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild6 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild6.setText(childText);

                        //SetData - Drink
                        txtListChild6.setText(myProfileResponse.getBasicData().getDrink());

                        TextView txtListChildHeader6 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader6.setText(childText);

                        //SetData - MaritalStatus
                        txtListChild6.setText(myProfileResponse.getBasicData().getMaritalStatus());

                        break;
                    case 7:
                        LayoutInflater infalInflater7 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater7.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild7 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild7.setText(childText);

                        //SetData - Drink
                        txtListChild7.setText(myProfileResponse.getBasicData().getSmoke());

                        TextView txtListChildHeader7 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader7.setText(childText);

                        break;
                    case 8:
                        LayoutInflater infalInflater8 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater8.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild8 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild8.setText(childText);

                        //SetData - Height
                        txtListChild8.setText(myProfileResponse.getBasicData().getHeight());

                        TextView txtListChildHeader8 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader8.setText(childText);

                        break;
                    case 9:
                        LayoutInflater infalInflater9 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater9.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild9 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild9.setText(childText);

                        //SetData - Interest
                        if(myProfileResponse.getBasicData().getInterest() != null) {
                            String interest1 = myProfileResponse.getBasicData().getInterest().toString().replace("[", "");
                            String interestNet = interest1.replace("]", "");
                            txtListChild9.setText(interestNet);

                            TextView txtListChildHeader9 = (TextView) convertView
                                    .findViewById(R.id.childItemTVheader);
                            txtListChildHeader9.setText(childText);
                        }

                        break;

                }
                break;
            case 1:
                switch (childPosition) {

                    case 0:
                        LayoutInflater infalInflater1 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater1.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild1 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild1.setText(childText);

                        //SetData - Religion
                        txtListChild1.setText(myProfileResponse.getBasicData().getReligion());

                        TextView txtListChildHeader1 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader1.setText(childText);

                        break;
                    case 1:
                        LayoutInflater infalInflater2 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater2.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild2 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild2.setText(childText);

                        //SetData - Caste
                        txtListChild2.setText(myProfileResponse.getBasicData().getCaste());

                        TextView txtListChildHeader2 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader2.setText(childText);

                        break;
                    case 2:
                        LayoutInflater infalInflater3 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater3.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild3 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild3.setText(childText);

                        //SetData - Mother Tounge
                        txtListChild3.setText(myProfileResponse.getBasicData().getMotherTounge());

                        TextView txtListChildHeader3 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader3.setText(childText);

                        break;

                }
                break;
//CONTACT DETAIL
            case 2:
                switch (childPosition) {

                    case 0:
                        LayoutInflater infalInflater = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild.setText(childText);

                        //SetData - MobileNo
                        txtListChild.setText(myProfileResponse.getBasicData().getMobileNo());

                        TextView txtListChildHeader = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader.setText(childText);


                        break;
                    case 1:
                        LayoutInflater infalInflater1 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater1.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild1 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild1.setText(childText);

                        TextView txtListChildHeader1 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader1.setText(childText);
                        //SetData - Name

                        txtListChild1.setText(myProfileResponse.getBasicData().getAlternateNo());

                        break;
                    case 2:
                        LayoutInflater infalInflater2 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater2.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild2 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild2.setText(childText);

                        TextView txtListChildHeader2 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader2.setText(childText);

                        //SetData - PermanentAddress
                        txtListChild2.setText(myProfileResponse.getBasicData().getPermanentAddress());

                        break;
                    case 3:
                        LayoutInflater infalInflater3 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater3.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild3 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild3.setText(childText);

                        //SetData - PermanentCountry
                        txtListChild3.setText(myProfileResponse.getBasicData().getPermanentCountry());

                        TextView txtListChildHeader3 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader3.setText(childText);

                        break;
                    case 4:
                        LayoutInflater infalInflater4 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater4.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild4 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild4.setText(childText);

                        //SetData - PermanentState
                        txtListChild4.setText(myProfileResponse.getBasicData().getPermanentState());

                        TextView txtListChildHeader4 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader4.setText(childText);
                        break;
                    case 5:
                        LayoutInflater infalInflater5 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater5.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild5 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild5.setText(childText);

                        TextView txtListChildHeader5 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader5.setText(childText);

                        //SetData - PermanentCity
                        txtListChild5.setText(myProfileResponse.getBasicData().getPermanentCity());

                        break;
                    case 6:
                        LayoutInflater infalInflater6 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater6.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild6 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild6.setText(childText);

                        TextView txtListChildHeader6 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader6.setText(childText);

                        //SetData - PermanentZipcode
                        txtListChild6.setText(myProfileResponse.getBasicData().getPermanentZipcode());

                        break;
                    case 7:
                        LayoutInflater infalInflater7 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater7.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild7 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild7.setText(childText);

                        TextView txtListChildHeader7 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader7.setText(childText);
                        //SetData - CurrentAddress
                        txtListChild7.setText(myProfileResponse.getBasicData().getCurrentAddress());

                        break;
                    case 8:
                        LayoutInflater infalInflater8 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater8.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild8 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild8.setText(childText);

                        //SetData - CurrentCountry
                        txtListChild8.setText(myProfileResponse.getBasicData().getCurrentCountry());

                        TextView txtListChildHeader8 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader8.setText(childText);

                        break;
                    case 9:
                        LayoutInflater infalInflater9 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater9.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild9 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild9.setText(childText);

                        //SetData - CurrentState
                        txtListChild9.setText(myProfileResponse.getBasicData().getCurrentState());

                        TextView txtListChildHeader9 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader9.setText(childText);

                        break;
                    case 10:
                        LayoutInflater infalInflater10 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater10.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild10 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild10.setText(childText);

                        TextView txtListChildHeader10 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader10.setText(childText);
                        //SetData - CurrentCity
                        txtListChild10.setText(myProfileResponse.getBasicData().getCurrentCity());

                        break;
                    case 11:
                        LayoutInflater infalInflater11 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater11.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild11 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild11.setText(childText);

                        TextView txtListChildHeader11 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader11.setText(childText);
                        //SetData - CurrentZipcode
                        txtListChild11.setText(myProfileResponse.getBasicData().getCurrentZipcode());

                        break;
                }
                break;
            case 3:
                switch (childPosition) {

                    case 0:
                        LayoutInflater infalInflater = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild.setText(childText);

                        TextView txtListChildHeader = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader.setText(childText);

                        //SetData - FatherName
                        txtListChild.setText(myProfileResponse.getBasicData().getFatherName());

                        break;
                    case 1:
                        LayoutInflater infalInflater1 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater1.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild1 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild1.setText(childText);

                        TextView txtListChildHeader1 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader1.setText(childText);

                        //SetData - FatherOccupation
                        txtListChild1.setText(myProfileResponse.getBasicData().getFatherOccupation());

                        break;
                    case 2:
                        LayoutInflater infalInflater2 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater2.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild2 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild2.setText(childText);

                        TextView txtListChildHeader2 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader2.setText(childText);

                        //SetData - MotherName
                        txtListChild2.setText(myProfileResponse.getBasicData().getMotherName());

                        break;
                    case 3:
                        LayoutInflater infalInflater3 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater3.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild3 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild3.setText(childText);

                        TextView txtListChildHeader3 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader3.setText(childText);

                        //SetData - MotherOccupation
                        txtListChild3.setText(myProfileResponse.getBasicData().getMotherOccupation());

                        break;
                    case 4:
                        LayoutInflater infalInflater4 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater4.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild4 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild4.setText(childText);

                        TextView txtListChildHeader4 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader4.setText(childText);

                        //SetData - Sister
                        txtListChild4.setText(myProfileResponse.getBasicData().getSister());

                        break;
                    case 5:
                        LayoutInflater infalInflater5 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater5.inflate(R.layout.list_item_non_editable, null);


                        final TextView txtListChild5 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild5.setText(childText);

                        TextView txtListChildHeader5 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader5.setText(childText);

                        //SetData - Brother
                        txtListChild5.setText(myProfileResponse.getBasicData().getBrother());

                        break;

                }
                break;
            case 4:
                switch (childPosition) {

                    case 0:
                        LayoutInflater infalInflater = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater.inflate(R.layout.list_item_non_editable, null);


                        final TextView txtListChild = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild.setText(childText);

                        TextView txtListChildHeader = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader.setText(childText);

                        //SetData - Schooling
                        txtListChild.setText(myProfileResponse.getBasicData().getSchooling());

                        break;
                    case 1:
                        LayoutInflater infalInflater1 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater1.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild1 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild1.setText(childText);

                        TextView txtListChildHeader1 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader1.setText(childText);

                        //SetData - SchoolingYear
                        txtListChild1.setText(String.valueOf(myProfileResponse.getBasicData().getSchoolingYear()));

                    case 2:
                        LayoutInflater infalInflater2 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater2.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild2 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild2.setText(childText);

                        TextView txtListChildHeader2 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader2.setText(childText);

                        //SetData - Graduation
                        txtListChild2.setText(myProfileResponse.getBasicData().getGraduation());

                        break;
                    case 3:
                        LayoutInflater infalInflater3 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater3.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild3 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild3.setText(childText);

                        TextView txtListChildHeader3 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader3.setText(childText);

                        //SetData - GraduationCollege
                        txtListChild3.setText(myProfileResponse.getBasicData().getGraduationCollege());

                        break;
                    case 4:
                        LayoutInflater infalInflater4 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater4.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild4 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild4.setText(childText);

                        TextView txtListChildHeader4 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader4.setText(childText);

                        //SetData - GraduationYear
                        txtListChild4.setText(String.valueOf(myProfileResponse.getBasicData().getGraduationYear()));

                        break;
                    case 5:
                        LayoutInflater infalInflater5 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater5.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild5 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild5.setText(childText);

                        TextView txtListChildHeader5 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader5.setText(childText);

                        //SetData - PostGraduation
                        txtListChild5.setText(myProfileResponse.getBasicData().getPostGraduation());

                        break;
                    case 6:
                        LayoutInflater infalInflater6 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater6.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild6 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild6.setText(childText);

                        TextView txtListChildHeader6 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader6.setText(childText);

                        //SetData - PostGraduationCollege
                        txtListChild6.setText(myProfileResponse.getBasicData().getPostGraduationCollege());

                        break;
                    case 7:
                        LayoutInflater infalInflater7 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater7.inflate(R.layout.list_item_non_editable, null);


                        final TextView txtListChild7 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild7.setText(childText);

                        TextView txtListChildHeader7 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader7.setText(childText);

                        //SetData - PostGraduationYear
                        txtListChild7.setText(String.valueOf(myProfileResponse.getBasicData().getPostGraduationYear()));

                        break;
                    case 8:
                        LayoutInflater infalInflater8 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater8.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild8 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild8.setText(childText);

                        TextView txtListChildHeader8 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader8.setText(childText);

                        //SetData - HighestEducation
                        txtListChild8.setText(myProfileResponse.getBasicData().getHighestEducation());

                        break;
                    case 9:
                        LayoutInflater infalInflater9 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater9.inflate(R.layout.list_item_non_editable, null);


                        final TextView txtListChild9 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild9.setText(childText);

                        TextView txtListChildHeader9 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader9.setText(childText);

                        //SetData - WorkingWith
                        txtListChild9.setText(myProfileResponse.getBasicData().getNameOfCompany());

                        break;
                    case 10:
                        LayoutInflater infalInflater10 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater10.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild10 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild10.setText(childText);

                        TextView txtListChildHeader10 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader10.setText(childText);

                        //SetData - WorkingAs
                        txtListChild10.setText(myProfileResponse.getBasicData().getWorkingAs());
                        break;
                    case 11:
                        LayoutInflater infalInflater11 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater11.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild11 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild11.setText(childText);

                        TextView txtListChildHeader11 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader11.setText(childText);

                        //SetData - WorkingLocation
                        txtListChild11.setText(myProfileResponse.getBasicData().getJobLocation());

                        break;
                    case 12:
                        LayoutInflater infalInflater12 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater12.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild12 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild12.setText(childText);

                        //SetData - AnnualIncome
                        txtListChild12.setText(myProfileResponse.getBasicData().getAnnualIncome());

                        TextView txtListChildHeader12 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader12.setText(childText);

                        break;
                    case 13:
                        LayoutInflater infalInflater13 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater13.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild13 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild13.setText(childText);

                        TextView txtListChildHeader13 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader13.setText(childText);

                        //SetData - LinkedIn
                        txtListChild13.setText(myProfileResponse.getBasicData().getLinkedIn());

                        break;
                }
                break;
            case 5:
                switch (childPosition) {

                    case 0:
                        LayoutInflater infalInflater = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild.setText(childText);

                        TextView txtListChildHeader = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader.setText(childText);
                        //SetData - AboutMe
                        txtListChild.setText(myProfileResponse.getBasicData().getAboutMe());

                        break;
                }
                break;
            case 6:
                switch (childPosition) {

                    case 0:
                        LayoutInflater infalInflater = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater.inflate(R.layout.list_item_non_editable, null);

                        TextInputLayout textInputLayout = (TextInputLayout) convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout.setHint(childText);

                        EditText editText = convertView.findViewById(R.id.idlistitemET);
                        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        break;
                    case 1:
                        LayoutInflater infalInflater1 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater1.inflate(R.layout.list_item_non_editable, null);

                        TextInputLayout textInputLayout1 = (TextInputLayout) convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout1.setHint(childText);

                        EditText editText1 = convertView.findViewById(R.id.idlistitemET);
                        editText1.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        break;
                    case 2:
                        LayoutInflater infalInflater2 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater2.inflate(R.layout.list_item_non_editable, null);

                        TextInputLayout textInputLayout2 = (TextInputLayout) convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout2.setHint(childText);

                        EditText editText2 = convertView.findViewById(R.id.idlistitemET);
                        editText2.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        break;
                    case 3:
                        LayoutInflater infalInflater3 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater3.inflate(R.layout.list_item_non_editable, null);

                        TextInputLayout textInputLayout3 = (TextInputLayout) convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout3.setHint(childText);

                        EditText editText3 = convertView.findViewById(R.id.idlistitemET);
                        editText3.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        break;
                    case 4:
                        LayoutInflater infalInflater4 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater4.inflate(R.layout.list_item_non_editable, null);

                        TextInputLayout textInputLayout4 = (TextInputLayout) convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout4.setHint(childText);

                        EditText editText4 = convertView.findViewById(R.id.idlistitemET);
                        editText4.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        break;
                    case 5:
                        LayoutInflater infalInflater5 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater5.inflate(R.layout.list_item_non_editable, null);

                        TextInputLayout textInputLayout5 = (TextInputLayout) convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout5.setHint(childText);

                        EditText editText5 = convertView.findViewById(R.id.idlistitemET);
                        editText5.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        break;
                    case 6:
                        LayoutInflater infalInflater6 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater6.inflate(R.layout.list_item_non_editable, null);

                        TextInputLayout textInputLayout6 = (TextInputLayout) convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout6.setHint(childText);

                        EditText editText6 = convertView.findViewById(R.id.idlistitemET);
                        editText6.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        break;
                    case 7:
                        LayoutInflater infalInflater7 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater7.inflate(R.layout.list_item_non_editable, null);

                        TextInputLayout textInputLayout7 = (TextInputLayout) convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout7.setHint(childText);

                        EditText editText7 = convertView.findViewById(R.id.idlistitemET);
                        editText7.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        break;
                    case 8:
                        LayoutInflater infalInflater8 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater8.inflate(R.layout.list_item_non_editable, null);

                        TextInputLayout textInputLayout8 = (TextInputLayout) convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout8.setHint(childText);

                        EditText editText8 = convertView.findViewById(R.id.idlistitemET);
                        editText8.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        break;
                    case 9:
                        LayoutInflater infalInflater9 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater9.inflate(R.layout.list_item_non_editable, null);

                        TextInputLayout textInputLayout9 = (TextInputLayout) convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout9.setHint(childText);

                        EditText editText9 = convertView.findViewById(R.id.idlistitemET);
                        editText9.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        break;

                }
                break;
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.profileGroupTV);
        lblListHeader.setTypeface(null, Typeface.NORMAL);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void formattedDate(TextView tv, String _date) {

//        String _date = "1988-08-28";

        try {

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
            Call<DateToAgeResponse> call = apiInterface.dateToAge(_date);
//            ProgressClass.getProgressInstance().showDialog(mContext);
            call.enqueue(new Callback<DateToAgeResponse>() {
                @Override
                public void onResponse(Call<DateToAgeResponse> call, Response<DateToAgeResponse> response) {
                    if (response.isSuccessful()) {
//                        ProgressClass.getProgressInstance().stopProgress();
                        Message message = response.body().getMessage();
                        try {
                            if (message != null) {
                                tv.setText(response.body().getAge());
                            } else {
                                AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", " Check Religion selected!");
                            }
                        } catch (NullPointerException npe) {
                            Log.e("TAG", "#Error : " + npe, npe);
                            AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", " Date Format not correct");
                        }
                    }
                }

                @Override
                public void onFailure(Call<DateToAgeResponse> call, Throwable t) {
                    call.cancel();
                    ProgressClass.getProgressInstance().stopProgress();
                    Toast.makeText(_context, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (NullPointerException npe) {
            Log.e("TAG", "#Error : " + npe, npe);
            AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", "Religion not seletced");
        }
    }

    public static String getDate(String _Date){

//        String _Date = "2010-09-29 08:45:22";
//        String _Date = "2018-05-02T00:00:00+0000";

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

