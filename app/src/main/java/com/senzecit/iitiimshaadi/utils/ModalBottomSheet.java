package com.senzecit.iitiimshaadi.utils;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.senzecit.iitiimshaadi.R;

public class ModalBottomSheet extends BottomSheetDialogFragment {

    static BottomSheetDialogFragment newInstance() {
        return new BottomSheetDialogFragment();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bottom_sheet_tnc_modal, container, false);

        return v;
    }
}