package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.model.commons.CountryCodeModel;
import java.util.List;

/**
 * Created by senzec on 1/1/18.
 */

public class CustomArrayAdapter extends ArrayAdapter<String>{

        private static final String TAG = CustomArrayAdapter.class.getSimpleName();
        private final LayoutInflater mInflater;
        private final Context mContext;
        private final List<CountryCodeModel> countryCodeModelList;
        private final int mResource;

        public CustomArrayAdapter(@NonNull Context context, @LayoutRes int resource,
                                  @NonNull List objects) {
                super(context, resource, 0, objects);

                mContext = context;
                mInflater = LayoutInflater.from(context);
                mResource = resource;
                countryCodeModelList = objects;
        }
        @Override
        public View getDropDownView(int position, @Nullable View convertView,
                                    @NonNull ViewGroup parent) {
                return createItemView(position, convertView, parent);
        }

        @Override
        public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                return createItemView(position, convertView, parent);
        }

        private View createItemView(int position, View convertView, ViewGroup parent){
                final View view = mInflater.inflate(mResource, parent, false);

//                countryCodeList = new ArrayList<>();
                ImageView mCountryFlagIV = view.findViewById(R.id.idCountryFlagIV);
                TextView mCountryCodeTV = view.findViewById(R.id.idCountryCodeTV);

//                mCountryCodeTV.setText(countryCodeModelList.get(position).getDial_code());
                try {
                        mCountryCodeTV.setText(countryCodeModelList.get(position).getDial_code());
            loadProfileImage(mCountryFlagIV, countryCodeModelList.get(position).getCode());
                }catch (IndexOutOfBoundsException iobe){
                        Log.e(TAG, "#Error : "+iobe, iobe);
                }

                return view;
        }

        private void loadProfileImage(ImageView imageView, String countryCode){

                if(countryCode != null) {

                        Glide.with(mContext)
                                .load("http://www.geognos.com/api/en/countries/flag/"+countryCode+".png")
                                .into(imageView);
                }
        }
}