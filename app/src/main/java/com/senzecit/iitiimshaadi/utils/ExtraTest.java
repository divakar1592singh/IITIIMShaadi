package com.senzecit.iitiimshaadi.utils;

/**
 * Created by senzec on 20/2/18.
 */

public class ExtraTest {

/*

    private void showCountry(final TextView textView){

        countryListWithId = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        countryList.clear();

//        AppPrefs prefs = AppController.getInstance().getPrefs();
//        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        String token = CONSTANTS.Token_Paid;

        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        Call<CountryListResponse> call = apiInterface.countryList(token);
        ProgressClass.getProgressInstance().showDialog(getActivity());
        call.enqueue(new Callback<CountryListResponse>() {
            @Override
            public void onResponse(Call<CountryListResponse> call, Response<CountryListResponse> response) {
                if (response.isSuccessful()) {
                    ProgressClass.getProgressInstance().stopProgress();
                    List<AllCountry> rawCountryList = response.body().getAllCountries();
                    for(int i = 0; i<rawCountryList.size(); i++){
                        if(rawCountryList.get(i).getName() != null){
                            countryList.add(rawCountryList.get(i).getName());
                            countryResponse = new CountryModel(String.valueOf(rawCountryList.get(i).getOldValue()), rawCountryList.get(i).getName());
                            countryListWithId.add(countryResponse);
                        }
                    }
                    showDialog(countryList, textView);
                }
            }

            @Override
            public void onFailure(Call<CountryListResponse> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showCity(final TextView textView){

        final List<String> cityList = new ArrayList<>();
        cityList.clear();
        String countryId = null;

        String country = mPartnerCurrentCountryTV.getText().toString() ;
        for(int i = 0; i < countryListWithId.size(); i++){
            if(countryListWithId.get(i).getCountryName().equalsIgnoreCase(country)){
                countryId = countryListWithId.get(i).getCountryId();
            }
        }
        System.out.println(countryId);

//        String countryId = "1151";

        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        Call<CitiesAccCountryResponse> call = apiInterface.cityList(countryId);
        ProgressClass.getProgressInstance().showDialog(getActivity());
        call.enqueue(new Callback<CitiesAccCountryResponse>() {
            @Override
            public void onResponse(Call<CitiesAccCountryResponse> call, Response<CitiesAccCountryResponse> response) {
                if (response.isSuccessful()) {
                    ProgressClass.getProgressInstance().stopProgress();
                    List<AllCity> rawCityList = response.body().getAllCities();
                    for(int i = 0; i<rawCityList.size(); i++){
                        if(rawCityList.get(i).getName() != null){
                            cityList.add(rawCityList.get(i).getName());
                        }
                    }

                    showDialog(cityList, textView);
                }
            }

            @Override
            public void onFailure(Call<CitiesAccCountryResponse> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showCaste(final TextView textView){

        String token = CONSTANTS.Token_Paid;
        String caste = mSelectReligionTV.getText().toString() ;

        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        Call<CasteAccReligionResponse> call = apiInterface.casteList(token, caste);
        ProgressClass.getProgressInstance().showDialog(getActivity());
        call.enqueue(new Callback<CasteAccReligionResponse>() {
            @Override
            public void onResponse(Call<CasteAccReligionResponse> call, Response<CasteAccReligionResponse> response) {
                if (response.isSuccessful()) {
                    ProgressClass.getProgressInstance().stopProgress();
                    List<String> casteList = response.body().getAllCastes();

                    showDialog(casteList, textView);
                }
            }

            @Override
            public void onFailure(Call<CasteAccReligionResponse> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
*/

 /*   public void showCurrentState(final TextView textView){

     *//*   final List<String> stateList = new ArrayList<>();
        stateList.clear();*//*
//        AppPrefs prefs = new AppPrefs(_context);
//        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        String token = CONSTANTS.Token_Paid;
        String Country = ExpOwnProfileModel.getInstance().getCurrent_Country();

        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        Call<StateListResponse> call = apiInterface.stateList(token, Country);
        call.enqueue(new Callback<StateListResponse>() {
            @Override
            public void onResponse(Call<StateListResponse> call, Response<StateListResponse> response) {
                if (response.isSuccessful()) {

                    List<String> stateList = response.body().getAllStates();

                    showDialog(stateList, textView);

                }
            }

            @Override
            public void onFailure(Call<StateListResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(_context, "Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }*/

//Date
/*
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

    }*/

/*
    public static String removeLastChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length()-1);
    }
    */



}
