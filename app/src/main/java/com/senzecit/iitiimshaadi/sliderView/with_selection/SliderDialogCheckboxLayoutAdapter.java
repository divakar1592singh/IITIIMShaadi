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
import android.widget.LinearLayout;
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
    List<SliderCheckModel> sliderCheckList = new ArrayList<>();
    SliderCheckModel checkModel = null;

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
        LinearLayout layout = (LinearLayout)view.findViewById(R.id.idSelectableLayout) ;
        CheckBox mSliderCheck = (CheckBox)view.findViewById(R.id.idCheckbox);
        TextView mmSlidertext = (TextView)view.findViewById(R.id.idText);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("****************Pos : "+position);

                if(mSliderCheck.isChecked() == true){
                    mSliderCheck.setChecked(false);
                    checkModel = new SliderCheckModel(position, false);
                    sliderCheckList.add(checkModel);
                }else {
                    mSliderCheck.setChecked(true);
                    checkModel = new SliderCheckModel(position, true);
                    sliderCheckList.add(checkModel);
                }
            }
        });
        mmSlidertext.setText(models.get(position).getName());
try {
    if(sliderCheckList.get(position).isChecked() == true){

        mSliderCheck.setChecked(sliderCheckList.get(position).isChecked());
    }
}catch (IndexOutOfBoundsException iobe){

}
        return view;
    }
}