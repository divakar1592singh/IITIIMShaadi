package com.senzecit.iitiimshaadi.sliderView.with_selection;

/**
 * Created by senzec on 4/12/17.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;

import java.util.ArrayList;

/**
 * Created by senzec on 30/11/17.
 */


public class SliderDialogCheckboxLayoutAdapter extends BaseAdapter {

    private static final String TAG = "SliderDialogCheckboxLayoutAdapter";
    Context mContext;
    LayoutInflater inflater;
    ArrayList<SliderDialogCheckboxLayoutModel> models = new ArrayList<SliderDialogCheckboxLayoutModel>();

    public SliderDialogCheckboxLayoutAdapter(Context mContext, ArrayList<SliderDialogCheckboxLayoutModel> models){
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.models = models;

    }


    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.slider_dialog_checkbox_row, null);
        CheckBox mSliderCheck = (CheckBox)view.findViewById(R.id.idCheckbox);
        TextView mmSlidertext = (TextView)view.findViewById(R.id.idText);

        mmSlidertext.setText(models.get(position).getName());

        return view;
    }
}