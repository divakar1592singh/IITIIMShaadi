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
import com.senzecit.iitiimshaadi.model.api_response_model.common.SliderCheckModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by senzec on 30/11/17.
 */


public class SliderDialogCheckboxLayoutAdapter extends BaseAdapter {

    private static final String TAG = "SliderDialogCheckboxLayoutAdapter";
    Context mContext;
    LayoutInflater inflater;
    ArrayList<SliderDialogCheckboxLayoutModel> models = new ArrayList<SliderDialogCheckboxLayoutModel>();
    List<SliderCheckModel> sliderCheckList;


    public SliderDialogCheckboxLayoutAdapter(Context mContext, ArrayList<SliderDialogCheckboxLayoutModel> models, int i ){
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        sliderCheckList = new ArrayList<>();
        // TODO Auto-generated method stub
        View row = null;
        row = View.inflate(mContext, R.layout.slider_dialog_checkbox_row, null);
        TextView tvContent= row.findViewById(R.id.idText);
        //tvContent.setText(data[position]);
        tvContent.setText(models.get(position).getName());
        //System.out.println("The Text is here like.. == "+tvContent.getText().toString());

        final CheckBox cb = row
                .findViewById(R.id.idCheckbox);
        cb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (sliderCheckList.get(position).isChecked()) {
                    sliderCheckList.get(position).setChecked(false);
                } else {
                    sliderCheckList.get(position).setChecked(true);
                }

                for(int i=0;i<sliderCheckList.size();i++)
                {
                    if (sliderCheckList.get(i).isChecked())
                    {
                        System.out.println("Selectes Are == "+ models.get(position).getName());
                    }
                }
            }
        });

        if (sliderCheckList.get(position).isChecked()) {

            cb.setChecked(true);
        }
        else {
            cb.setChecked(false);
        }
        return row;
    }

}



