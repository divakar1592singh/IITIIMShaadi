package com.senzecit.iitiimshaadi.viewController;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.utils.custom_view_pager.ViewPagerCustomDuration;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class IntroSliderActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPagerCustomDuration vp_slider;
    private LinearLayout ll_dots;
    SliderPagerAdapter sliderPagerAdapter;
    ArrayList<String> intro_slider_list;
    private TextView[] dots;
    int page_position = 0;
    Button mLogin,mRegistration;

    Timer swipeTime;
    int current_page=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_intro_slider);
        init();
        addBottomDots(0);

    }

    private void init(){
        vp_slider = (ViewPagerCustomDuration) findViewById(R.id.vp_slider);
        ll_dots = (LinearLayout) findViewById(R.id.ll_dots);
        mLogin = (Button) findViewById(R.id.idLoginBtn);
        mRegistration = (Button) findViewById(R.id.idRegisterBtn);
        mLogin.setOnClickListener(this);
        mRegistration.setOnClickListener(this);

        intro_slider_list = new ArrayList<>();

//Add few items to slider_image_list ,this should contain url of images which should be displayed in slider
// here i am adding few sample image links, you can add your own

        intro_slider_list.add(getApplicationContext().getResources().getString(R.string.intro_1));
        intro_slider_list.add(getApplicationContext().getResources().getString(R.string.intro_2));
        intro_slider_list.add(getApplicationContext().getResources().getString(R.string.intro_3));


        sliderPagerAdapter = new SliderPagerAdapter(IntroSliderActivity.this, intro_slider_list);
        vp_slider.setAdapter(sliderPagerAdapter);

//        vp_slider.setPageTransformer(true, new AccordionTransformer());
        vp_slider.beginFakeDrag();

        vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == intro_slider_list.size()) {
                    page_position = 0;
                    vp_slider.setScrollDurationFactor(2);
                } else {
                    page_position = page_position + 1;
                    vp_slider.setScrollDurationFactor(2);
                }
                vp_slider.setCurrentItem(page_position, true);

                vp_slider.setScrollDurationFactor(2);

            }
        };

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 7000, 7000);


       /* try {
            Interpolator sInterpolator = new AccelerateInterpolator();
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(vp_slider.getContext(), sInterpolator);
            // scroller.setFixedDuration(5000);
            mScroller.set(vp_slider, scroller);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }

        vp_slider.setPageTransformer(true, new DepthPageTransformer());



        current_page = vp_slider.getCurrentItem();
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(current_page == intro_slider_list.size())
                {   current_page = 0;
                    vp_slider.setScrollDurationFactor(200);
                }
                vp_slider.setScrollDurationFactor(1500);
                vp_slider.setCurrentItem(current_page++,true);

            }
        };
        swipeTime = new Timer();
        swipeTime.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        },1000,3000);*/

    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[intro_slider_list.size()];

        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#FFFFFF"));
            ll_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#ffab38"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.idLoginBtn:
                startActivity(new Intent(IntroSliderActivity.this,LoginActivity.class));
                break;
            case R.id.idRegisterBtn:
                startActivity(new Intent(IntroSliderActivity.this,RegistrationQuickActivity.class));
                break;
        }
    }

    class SliderPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        Activity activity;
        ArrayList<String> image_arraylist;

        public SliderPagerAdapter(Activity activity, ArrayList<String> image_arraylist) {
            this.activity = activity;
            this.image_arraylist = image_arraylist;
        }
        @Override
        public int getCount() {
            return image_arraylist.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.intro_slider_item, container, false);
            TextView textView = (TextView) view.findViewById(R.id.intro_slider_text);
            textView.setText(image_arraylist.get(position).toString());
            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);

        }
    }
}
