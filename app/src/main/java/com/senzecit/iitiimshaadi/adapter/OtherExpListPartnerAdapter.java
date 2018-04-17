package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.graphics.Typeface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.model.api_response_model.other_profile.OtherProfileResponse;

import java.util.HashMap;
import java.util.List;
/**
 * Created by ravi on 28/11/17.
 */

public class OtherExpListPartnerAdapter extends BaseExpandableListAdapter {


    private Context _context;
    LayoutInflater layoutInflater;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    OtherProfileResponse myProfileResponse;

    public OtherExpListPartnerAdapter(Context context, List<String> listDataHeader,
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
        //****lchild data items start
        switch (groupPosition){
            case 0:
                //Toast.makeText(_context, "0", //Toast.LENGTH_LONG).show();
                switch (childPosition){
                    case 0:
                        LayoutInflater infalInflater = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild.setText(childText);

                        TextView txtListChildHeader = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader.setText(childText);

                        //SetData - PreferedPartnerMinAge
                        txtListChild.setText(String.valueOf(myProfileResponse.getPartnerBasicData().getPreferedPartnerMinAge()));

                        break;
                    case 1:
                        LayoutInflater infalInflater1 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater1.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild1 = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild1.setText(childText);

                        TextView txtListChildHeader1 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader1.setText(childText);

                        //SetData - PreferedPartnerMaxAge
                        txtListChild1.setText(String.valueOf(myProfileResponse.getPartnerBasicData().getPreferedPartnerMaxAge()));
                        break;
                    case 2:
                        LayoutInflater infalInflater2 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater2.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild2 = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild2.setText(childText);

                        //SetData - PreferedPartnerHeightMin
                        txtListChild2.setText(myProfileResponse.getPartnerBasicData().getPreferedPartnerHeightMin());

                        TextView txtListChildHeader2 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader2.setText(childText);
                        break;
                    case 3:
                        LayoutInflater infalInflater3 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater3.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild3 = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild3.setText(childText);

                        //SetData - PreferedPartnerHeightMax
                        txtListChild3.setText(myProfileResponse.getPartnerBasicData().getPreferedPartnerHeightMax());

                        TextView txtListChildHeader3 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader3.setText(childText);

                        break;
                    case 4:
                        LayoutInflater infalInflater4 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater4.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild4 = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild4.setText(childText);

                        //SetData - PreferedPartnerMaritalStatus
                        String marital1 = myProfileResponse.getPartnerBasicData().getPreferedPartnerMaritalStatus().toString().replace("[", "");
                        String maritalNet = marital1.replace("]", "");
                        txtListChild4.setText(maritalNet);

                        TextView txtListChildHeader4 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader4.setText(childText);
                        break;
                }

                break;
            case 1:
                switch (childPosition){
                    case 0:
                        LayoutInflater infalInflater = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild.setText(childText);

                        //SetData - PreferedPartnerReligion
                        txtListChild.setText(myProfileResponse.getPartnerBasicData().getPreferedPartnerReligion());

                        TextView txtListChildHeader = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader.setText(childText);

                        break;
                    case 1:
                        LayoutInflater infalInflater1 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater1.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild1 = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild1.setText(childText);

                        //SetData - PreferedPartnerCaste
                        String caste1 = myProfileResponse.getPartnerBasicData().getPreferedPartnerCaste().toString().replace("[", "");
                        String caste = caste1.replace("]","");
                        txtListChild1.setText(caste);

                        TextView txtListChildHeader1 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader1.setText(childText);

                        break;
                    case 2:
                        LayoutInflater infalInflater2 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater2.inflate(R.layout.list_item_non_editable, null);
                        final TextView txtListChild2 = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild2.setText(childText);

                        //SetData - PreferedPartnerCountry
                        String country1 = myProfileResponse.getPartnerBasicData().getPreferedPartnerCountry().toString().replace("[", "");
                        String country = country1.replace("]","");
                        txtListChild2.setText(country);
                        TextView txtListChildHeader2 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader2.setText(childText);
                        break;

                }
                break;
            case 2:
                switch (childPosition){
                    case 0:
                        LayoutInflater infalInflater = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild.setText(childText);
                        //SetData - PreferedPartnerEducation
                        String education1 = myProfileResponse.getPartnerBasicData().getPreferedPartnerEducation().toString().replace("[", "");
                        String education = education1.replace("]","");
                        txtListChild.setText(education);
                        TextView txtListChildHeader = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader.setText(childText);
                        break;
                }
                break;
            case 3:

                switch (childPosition){
                    case 0:
                        LayoutInflater infalInflater = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater.inflate(R.layout.list_item_non_editable, null);
                        final TextView txtListChild = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild.setText(childText);
                        TextView txtListChildHeader = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader.setText(childText);
                        //SetData - ChoiceOfGroom
                        txtListChild.setText(myProfileResponse.getPartnerBasicData().getChoiceOfPartner());
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

        TextView lblListHeader = convertView
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

}
