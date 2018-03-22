package com.senzecit.iitiimshaadi.model.api_response_model.common;

/**
 * Created by senzec on 22/3/18.
 */

public class SliderCheckModel {

    public SliderCheckModel(int position, boolean isChecked) {
        this.position = position;
        this.isChecked = isChecked;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    int position;
    boolean isChecked;
}
