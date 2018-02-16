package com.senzecit.iitiimshaadi.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.about_me.AboutMeResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.groom.ChoiceOfGroomResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.pt_basic_profile.ParnerBasicProfileResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.pt_education.PtrEduCareerResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.ptr_religious_country.PtrReligionCountryResponse;
import com.senzecit.iitiimshaadi.model.api_rquest_model.about_me.AboutMeRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.groom.ChoiceOfGroomRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.pt_education.PtrEduCareerRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.ptr_basic_profile.ParnerBasicProfileRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.ptr_religious_country.PtrReligionCountryRequest;
import com.senzecit.iitiimshaadi.model.common.country.AllCountry;
import com.senzecit.iitiimshaadi.model.common.country.CountryListResponse;
import com.senzecit.iitiimshaadi.model.exp_listview.ExpPartnerProfileModel;
import com.senzecit.iitiimshaadi.sliderView.with_list.SliderDialogListLayoutAdapter;
import com.senzecit.iitiimshaadi.sliderView.with_list.SliderDialogListLayoutModel;
import com.senzecit.iitiimshaadi.sliderView.with_selection.SliderDialogCheckboxLayoutAdapter;
import com.senzecit.iitiimshaadi.sliderView.with_selection.SliderDialogCheckboxLayoutModel;
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ravi on 28/11/17.
 */

public class ExpListViewSubscriberPartnerAdapter extends BaseExpandableListAdapter {

    private Context _context;
    LayoutInflater layoutInflater;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public ExpListViewSubscriberPartnerAdapter(Context context, List<String> listDataHeader,
                                               HashMap<String, List<String>> listChildData) {
        this._context = context;
        layoutInflater = LayoutInflater.from(_context);
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
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
                        convertView = infalInflater.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout = (TextInputLayout) convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout.setHint(childText);

                        EditText editText = convertView.findViewById(R.id.idlistitemET);
                        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpPartnerProfileModel.getInstance().setMinimum_Age(editable.toString());

                            }
                        });

                        break;
                    case 1:
                        LayoutInflater infalInflater1 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater1.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout1 = (TextInputLayout) convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout1.setHint(childText);

                        EditText editText1 = convertView.findViewById(R.id.idlistitemET);
                        editText1.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        editText1.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpPartnerProfileModel.getInstance().setMaximum_Age(editable.toString());

                            }
                        });

                        break;
                    case 2:
                        LayoutInflater infalInflater2 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater2.inflate(R.layout.list_item, null);

                        final TextView txtListChild2 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild2.setText(childText);

                        TextView txtListChildHeader2 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader2.setText(childText);
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showDialog(txtListChild2,100,50);
                                showHeight(txtListChild2);
                            }
                        });

                        txtListChild2.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpPartnerProfileModel.getInstance().setMin_Height(editable.toString());
                            }
                        });

                        break;
                    case 3:
                        LayoutInflater infalInflater3 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater3.inflate(R.layout.list_item, null);

                        final TextView txtListChild3 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild3.setText(childText);

                        TextView txtListChildHeader3 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader3.setText(childText);
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showDialog(txtListChild3,100,50);
                                showHeight(txtListChild3);
                            }
                        });

                        txtListChild3.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpPartnerProfileModel.getInstance().setMax_Height(editable.toString());
                            }
                        });

                        break;
                    case 4:
                        LayoutInflater infalInflater4 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater4.inflate(R.layout.list_item, null);

                        final TextView txtListChild4 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild4.setText(childText);

                        TextView txtListChildHeader4 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader4.setText(childText);
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showDialog(txtListChild4,100,50);
                                showMaritalStatus(txtListChild4);
                            }
                        });

                        txtListChild4.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpPartnerProfileModel.getInstance().setMarital_Status(editable.toString());
                            }
                        });

                        break;
                    case 5:
                        LayoutInflater infalInflater5 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater5.inflate(R.layout.submit_data_button, null);
                        Button saveChanges = convertView.findViewById(R.id.save_changes_button);
                        saveChanges.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //do somethings.
                                saveChangesOfCase_0();
                            }
                        });
                        break;

                }

                break;
            case 1:
                //Toast.makeText(_context, "1", //Toast.LENGTH_LONG).show();
                switch (childPosition){
                    case 0:
                        LayoutInflater infalInflater = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater.inflate(R.layout.list_item, null);

                        final TextView txtListChild = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild.setText(childText);

                        TextView txtListChildHeader = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader.setText(childText);
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showDialog(txtListChild,100,50);
                                showReligion(txtListChild);
                            }
                        });

                        txtListChild.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpPartnerProfileModel.getInstance().setPreferred_Religion(editable.toString());
                            }
                        });

                        break;
                    case 1:
                        LayoutInflater infalInflater1 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater1.inflate(R.layout.list_item, null);

                        final TextView txtListChild1 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild1.setText(childText);

                        TextView txtListChildHeader1 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader1.setText(childText);
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showDialog(txtListChild1,100,50);
                                showCaste(txtListChild1);
                            }
                        });

                        txtListChild1.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpPartnerProfileModel.getInstance().setPreferred_Caste(editable.toString());
                            }
                        });
                        break;
                    case 2:
                        LayoutInflater infalInflater2 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater2.inflate(R.layout.list_item, null);

                        final TextView txtListChild2 = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild2.setText(childText);

                        TextView txtListChildHeader2 = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader2.setText(childText);
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showDialog(txtListChild2,100,50);
                                showCountry(txtListChild2);
                            }
                        });

                        txtListChild2.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpPartnerProfileModel.getInstance().setPreferred_Country(editable.toString());
                            }
                        });

                        break;
                    case 3:
                        LayoutInflater infalInflater3 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater3.inflate(R.layout.submit_data_button, null);
                        Button saveChanges = convertView.findViewById(R.id.save_changes_button);
                        saveChanges.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //do somethings.
                                saveChangesOfCase_1();
                            }
                        });
                        break;
                }
                break;
            case 2:
                //Toast.makeText(_context, "2", //Toast.LENGTH_LONG).show();
                switch (childPosition){
                    case 0:
                        LayoutInflater infalInflater = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater.inflate(R.layout.list_item, null);

                        final TextView txtListChild = (TextView) convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild.setText(childText);

                        TextView txtListChildHeader = (TextView) convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader.setText(childText);
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showSelectableDialog(txtListChild,100,50);
                                showEducation(txtListChild);
                            }
                        });

                        txtListChild.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpPartnerProfileModel.getInstance().setPreferred_Education(editable.toString());
                            }
                        });

                        break;
                    case 1:
                        LayoutInflater infalInflater1 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater1.inflate(R.layout.submit_data_button, null);
                        Button saveChanges = convertView.findViewById(R.id.save_changes_button);
                        saveChanges.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //do somethings.
                                saveChangesOfCase_2();
                            }
                        });
                        break;
                }
                break;
            case 3:

                switch (childPosition){
                    case 0:
                        LayoutInflater infalInflater = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater.inflate(R.layout.list_item_thirdtype, null);
                        EditText editText = convertView.findViewById(R.id.idlistitemET);
                        editText.setHint("Say something...");
                        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            }
                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            }
                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpPartnerProfileModel.getInstance().setChoice_of_Groom(editable.toString());
                            }
                        });

                        break;
                    case 1:
                        LayoutInflater infalInflater1 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater1.inflate(R.layout.submit_data_button, null);
                        Button saveChanges = convertView.findViewById(R.id.save_changes_button);
                        saveChanges.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //do somethings.
                                saveChangesOfCase_3();
                            }
                        });
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


    public Vector<Dialog> dialogs = new Vector<Dialog>();
    private void showDialog(List<String> dataList, final TextView txtListChild) {

        int d_width = 100;
        int d_height = 50;
        final ArrayList<SliderDialogListLayoutModel> models = new ArrayList<SliderDialogListLayoutModel>();
        if(dataList.size()>0){
            for (int i = 0; i < dataList.size(); i++) {
                SliderDialogListLayoutModel model = new SliderDialogListLayoutModel();
                model.setName(dataList.get(i));
                models.add(model);
            }
        }else {
            for (int i = 0; i < 20; i++) {
                SliderDialogListLayoutModel model = new SliderDialogListLayoutModel();
                model.setName("senzecit " + i);
                models.add(model);
            }
        }

        final Dialog dialog = new Dialog(_context, R.style.CustomDialog);//,R.style.CustomDialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

//        View view = getLayoutInflater().inflate(R.layout.slider_dialog_list_layout, null);
        View view = layoutInflater.inflate(R.layout.slider_dialog_list_layout, null);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(_context);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.custom_list);
//		final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        ((Button) view.findViewById(R.id.button_done)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final SliderDialogListLayoutAdapter clad1 = new SliderDialogListLayoutAdapter(_context, models);
        recyclerView.setAdapter(clad1);

        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialogs.add(dialog);
        dialog.show();

        WindowManager wm = (WindowManager) _context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        Window window = dialog.getWindow();
        window.setGravity(Gravity.RIGHT);
        window.setLayout(width - d_width, height - d_height);

        clad1.setOnItemClickListener(new SliderDialogListLayoutAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String id) {
                int positionInList = position % models.size();

                txtListChild.setText(models.get(positionInList).getName());
                dialog.dismiss();
            }
        });

    }

    public Vector<Dialog> selectableDialogs = new Vector<Dialog>();
    private void showSelectableDialog(List<String> dataList, final TextView txtListChild)
    {
        int d_width = 100;
        int d_height = 50;
        final StringBuilder selectedQualification = new StringBuilder();
        final View[] parentListView = {null};
        final ArrayList<SliderDialogCheckboxLayoutModel> models = new ArrayList<SliderDialogCheckboxLayoutModel>();
        if(dataList.size()>0){
            for (int i = 0; i < dataList.size(); i++) {
                SliderDialogCheckboxLayoutModel model = new SliderDialogCheckboxLayoutModel();
                model.setName(dataList.get(i));
                models.add(model);
            }
        }else {
            for (int i = 0; i < 20; i++) {
                SliderDialogCheckboxLayoutModel model = new SliderDialogCheckboxLayoutModel();
                model.setName("senzecit " + i);
                models.add(model);
            }
        }

        final Dialog dialog = new Dialog(_context, R.style.CustomDialog);//,R.style.CustomDialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        View view = layoutInflater.inflate(R.layout.slider_dialog_checkbox_layout, null);

        final ListView listView = (ListView) view.findViewById(R.id.custom_list);
        Button doneBtn = (Button)view.findViewById(R.id.button_done);


        SliderDialogCheckboxLayoutAdapter clad1 = new SliderDialogCheckboxLayoutAdapter(_context, models);
        listView.setAdapter(clad1);


        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        selectableDialogs.add(dialog);
        dialog.show();

        WindowManager wm = (WindowManager) _context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        Window window = dialog.getWindow();
        window.setGravity(Gravity.RIGHT);
        window.setLayout(width - d_width, height - d_height);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < models.size(); i++) {

                    parentListView[0] = getViewByPosition(i, listView);
                    CheckBox checkBox = (CheckBox)parentListView[0].findViewById(R.id.idCheckbox);
                    TextView textView = (TextView)parentListView[0].findViewById(R.id.idText);

                    if (checkBox.isChecked()){

                        selectedQualification.append(textView.getText().toString()+", ");
                    }

                }
                txtListChild.setText(selectedQualification.toString());
                dialog.dismiss();
//             Toast.makeText(MainActivity.this, "Data is : "+slideText.getText(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition
                + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    //SET & GET DATA
    // Basics & LIFESTYLE
    public void showHeight(TextView textView){
        List<String> list = new ArrayList<>();

        list.add("4 ft 5in (134 cm)");
        list.add("4 ft 6in (137 cm)");
        list.add("4 ft 7in (139 cm)");
        list.add("4 ft 8in (142 cm)");
        list.add("4 ft 9in (144 cm)");
        list.add("4 ft 10in (147 cm)");
        list.add("4 ft 11in (149 cm)");
        list.add("5 ft 0in (152 cm)");
        list.add("5 ft 1in (154 cm)");
        list.add("5 ft 2in (157 cm)");
        list.add("5 ft 3in (160 cm)");
        list.add("5 ft 4in (162 cm)");
        list.add("5 ft 5in (165 cm)");
        list.add("5 ft 6in (167 cm)");
        list.add("5 ft 7in (170 cm)");
        list.add("5 ft 8in (172 cm)");
        list.add("5 ft 9in (175 cm)");
        list.add("5 ft 10in (177 cm)");
        list.add("5 ft 11in (180 cm)");
        list.add("6 ft 0in (182 cm)");
        list.add("6 ft 1in (185 cm)");
        list.add("6 ft 2in (187 cm)");
        list.add("6 ft 3in (190 cm)");
        list.add("6 ft 4in (193 cm)");
        list.add("6 ft 5in (195 cm)");
        list.add("6 ft 6in (198 cm)");
        list.add("6 ft 7in (200 cm)");
        list.add("6 ft 8in (203 cm)");


        showDialog(list, textView);
    }
    public void showMaritalStatus(TextView textView){
        List<String> list = new ArrayList<>();
        list.add("Never Married");
        list.add("Divorced");
        list.add("Divorced Awaited");
        list.add("Widow/Widower");
        list.add("Any");

        showDialog(list, textView);
    }
    //RELIGIOUS AND COUNTRY PREFERENCE
    public void showReligion(TextView textView){
        List<String> list = new ArrayList<>();
        list.add("Hindu");
        list.add("Muslim");
        list.add("Christian");
        list.add("Sikh");
        list.add("Parsi");
        list.add("Jain");
        list.add("Buddhist");
        list.add("Jewish");
        list.add("Other");

        showDialog(list, textView);
    }
    public void showCaste(TextView textView){
        List<String> list = new ArrayList<>();
        list.add("Caste1");
        list.add("Caste2");
        list.add("Caste3");
        list.add("Caste4");

        showDialog(list, textView);
    }

    //EDUCATION
    public void showEducation(TextView textView){
        List<String> list = new ArrayList<>();
        list.add("Any");
        list.add("Actuary");
        list.add("Hotel Management");
        list.add("Management");
        list.add("Engineering/Architecture");
        list.add("Medical");
        list.add("CA/CS/ICWA/CFA");
        list.add("Law");
        list.add("Design/Fashion Design");
        list.add("Government Officer");
        list.add("Social Work (Masters)");
        list.add("Media Communication (Masters)");
        list.add("Performing and Fine Arts");
        list.add("Masters");
        list.add("Research (Ph.D/FPM)");
        list.add("Others");

        showSelectableDialog(list, textView);
    }

    /** Network Operation */

    public void saveChangesOfCase_0(){

        AppPrefs prefs = new AppPrefs(_context);
        String token = prefs.getString(Constants.LOGGED_TOKEN);

        String Minimum_Age = ExpPartnerProfileModel.getInstance().getMinimum_Age();
        String Maximum_Age = ExpPartnerProfileModel.getInstance().getMaximum_Age();
        String Min_Height = ExpPartnerProfileModel.getInstance().getMin_Height();
        String Max_Height = ExpPartnerProfileModel.getInstance().getMax_Height();
        String Marital_Status = ExpPartnerProfileModel.getInstance().getMarital_Status();

        ParnerBasicProfileRequest request = new ParnerBasicProfileRequest();
        request.token = token;
        request.prefered_partner_min_age = Minimum_Age;
        request.prefered_partner_max_age = Maximum_Age;
        request.prefered_partner_height = Min_Height;
        request.prefered_partner_height_max = Max_Height;
        request.prefered_partner_marital_status = Marital_Status;

        ProgressClass.getProgressInstance().showDialog(_context);
        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
        Call<ParnerBasicProfileResponse> call = apiInterface.sendPartnerBasicProfile(request);
        call.enqueue(new Callback<ParnerBasicProfileResponse>() {
            @Override
            public void onResponse(Call<ParnerBasicProfileResponse> call, Response<ParnerBasicProfileResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    ParnerBasicProfileResponse serverResponse = response.body();
                    if(serverResponse.getMessage().getSuccess() != null) {
                        if (serverResponse.getMessage().getSuccess().toString().equalsIgnoreCase("success")) {

//                            AlertDialogSingleClick.getInstance().showDialog(LoginActivity.this, "Forgot Password", "An email with new password is sent to your registered email.");
                            Toast.makeText(_context, "Success", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(_context, "Confuse", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(_context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ParnerBasicProfileResponse> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
                Toast.makeText(_context, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void saveChangesOfCase_1(){

        AppPrefs prefs = new AppPrefs(_context);
        String token = Constants.Temp_Token;

        String Preferred_Religion = ExpPartnerProfileModel.getInstance().getPreferred_Religion();
        String Preferred_Caste = ExpPartnerProfileModel.getInstance().getPreferred_Caste();
        String Preferred_Country = ExpPartnerProfileModel.getInstance().getPreferred_Country();

        PtrReligionCountryRequest request = new PtrReligionCountryRequest();
        request.token = token;
        request.prefered_partner_religion = Preferred_Religion;
        request.prefered_partner_caste = Preferred_Caste;
        request.prefered_partner_country = Preferred_Country;


        ProgressClass.getProgressInstance().showDialog(_context);
        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
        Call<PtrReligionCountryResponse> call = apiInterface.sendPartnerReligionCountry(request);
        call.enqueue(new Callback<PtrReligionCountryResponse>() {
            @Override
            public void onResponse(Call<PtrReligionCountryResponse> call, Response<PtrReligionCountryResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    PtrReligionCountryResponse ptReligionResponse = response.body();
                    if(ptReligionResponse.getMessage().getSuccess() != null) {
                        if (ptReligionResponse.getMessage().getSuccess().toString().equalsIgnoreCase("success")) {

//                            AlertDialogSingleClick.getInstance().showDialog(LoginActivity.this, "Forgot Password", "An email with new password is sent to your registered email.");
                            Toast.makeText(_context, "Success", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(_context, "Confuse", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(_context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PtrReligionCountryResponse> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
                Toast.makeText(_context, "Failed", Toast.LENGTH_SHORT).show();
            }
        });



    }
    public void saveChangesOfCase_2(){

        AppPrefs prefs = new AppPrefs(_context);
        String token = prefs.getString(Constants.LOGGED_TOKEN);

        String Preferred_Education = ExpPartnerProfileModel.getInstance().getPreferred_Education();

        PtrEduCareerRequest request = new PtrEduCareerRequest();
        request.token = token;
        request.prefered_partner_education = Preferred_Education;


        ProgressClass.getProgressInstance().showDialog(_context);
        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
        Call<PtrEduCareerResponse> call = apiInterface.sendPartnerEduCareer(request);
        call.enqueue(new Callback<PtrEduCareerResponse>() {
            @Override
            public void onResponse(Call<PtrEduCareerResponse> call, Response<PtrEduCareerResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    PtrEduCareerResponse ptrEduResponse = response.body();
                    if(ptrEduResponse.getMessage().getSuccess() != null) {
                        if (ptrEduResponse.getMessage().getSuccess().toString().equalsIgnoreCase("success")) {

//                            AlertDialogSingleClick.getInstance().showDialog(LoginActivity.this, "Forgot Password", "An email with new password is sent to your registered email.");
                            Toast.makeText(_context, "Success", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(_context, "Confuse", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(_context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PtrEduCareerResponse> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
                Toast.makeText(_context, "Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void saveChangesOfCase_3(){

        AppPrefs prefs = new AppPrefs(_context);
        String token = prefs.getString(Constants.LOGGED_TOKEN);
//        String token = "362a95acb22b4e842880ef5e78ee223e";

        String Choice_of_Groom = ExpPartnerProfileModel.getInstance().getChoice_of_Groom();

        ChoiceOfGroomRequest request = new ChoiceOfGroomRequest();
        request.token = token;
        request.choice_of_partner = Choice_of_Groom;


        ProgressClass.getProgressInstance().showDialog(_context);
        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
        Call<ChoiceOfGroomResponse> call = apiInterface.sendPartnerGroom(request);
        call.enqueue(new Callback<ChoiceOfGroomResponse>() {
            @Override
            public void onResponse(Call<ChoiceOfGroomResponse> call, Response<ChoiceOfGroomResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    ChoiceOfGroomResponse aboutMeResponse = response.body();
                    if(aboutMeResponse.getMessage().getSuccess() != null) {
                        if (aboutMeResponse.getMessage().getSuccess().toString().equalsIgnoreCase("success")) {

//                            AlertDialogSingleClick.getInstance().showDialog(LoginActivity.this, "Forgot Password", "An email with new password is sent to your registered email.");
                            Toast.makeText(_context, "Success", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(_context, "Confuse", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(_context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ChoiceOfGroomResponse> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
                Toast.makeText(_context, "Failed", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void showCountry(final TextView textView){

        AppPrefs prefs = new AppPrefs(_context);
        String token = prefs.getString(Constants.LOGGED_TOKEN);

        final List<String> countryList = new ArrayList<>();
        countryList.clear();

        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
        Call<CountryListResponse> call = apiInterface.countryList(token);
        call.enqueue(new Callback<CountryListResponse>() {
            @Override
            public void onResponse(Call<CountryListResponse> call, Response<CountryListResponse> response) {
                if (response.isSuccessful()) {

                    List<AllCountry> rawCountryList = response.body().getAllCountries();
                    for(int i = 0; i<rawCountryList.size(); i++){
                        if(rawCountryList.get(i).getName() != null){
                            countryList.add(rawCountryList.get(i).getName());
                        }
                    }

                    showDialog(countryList, textView);

                }
            }

            @Override
            public void onFailure(Call<CountryListResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(_context, "Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
