package com.senzecit.iitiimshaadi.viewController;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.utils.custom_view_pager.ViewPagerCustomDuration;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SuccessStoriesActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    private ViewPagerCustomDuration vp_slider;
    private LinearLayout ll_dots;
    SliderPagerAdapter sliderPagerAdapter;
    ArrayList<String> slider_story_list;
    private TextView[] dots;
    int page_position = 0;

    Timer swipeTime;
    int current_page=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_success_stories);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        init();
        mBack.setOnClickListener(this);

        addBottomDots(0);

    }

    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("Success Stories");

        vp_slider = (ViewPagerCustomDuration) findViewById(R.id.vp_slider);
        ll_dots = (LinearLayout) findViewById(R.id.ll_dots);

        slider_story_list = new ArrayList<>();
        slider_story_list.clear();

//Add few items to slider_image_list ,this should contain url of images which should be displayed in slider
// here i am adding few sample image links, you can add your own

        slider_story_list.add(getApplicationContext().getResources().getString(R.string.story_1));
        slider_story_list.add(getApplicationContext().getResources().getString(R.string.story_2));
        slider_story_list.add(getApplicationContext().getResources().getString(R.string.story_3));
        slider_story_list.add(getApplicationContext().getResources().getString(R.string.story_4));
        slider_story_list.add(getApplicationContext().getResources().getString(R.string.story_5));
        slider_story_list.add(getApplicationContext().getResources().getString(R.string.story_6));
        slider_story_list.add(getApplicationContext().getResources().getString(R.string.story_7));

        slider_story_list.add(getApplicationContext().getResources().getString(R.string.story_8));
        slider_story_list.add(getApplicationContext().getResources().getString(R.string.story_9));
        slider_story_list.add(getApplicationContext().getResources().getString(R.string.story_10));

        slider_story_list.add("+100\nMore");


        sliderPagerAdapter = new SliderPagerAdapter(SuccessStoriesActivity.this, slider_story_list);
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
                if (page_position == slider_story_list.size()) {
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

        /*try {
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

        current_page = vp_slider.getCurrentItem();
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(current_page == slider_story_list.size())
                {   current_page = 0;
//                    vp_slider.setScrollDurationFactor(200);
                }
//                vp_slider.setScrollDurationFactor(1500);
                vp_slider.setCurrentItem(current_page++,true);

            }
        };
        swipeTime = new Timer();
        swipeTime.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        },2000,3000);*/

    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[slider_story_list.size()];

        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#1a1a1a"));
            ll_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                SuccessStoriesActivity.this.finish();
                break;
        }
    }

    class SliderPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        Activity activity;
        ArrayList<String> slider_story_list;

        public SliderPagerAdapter(Activity activity, ArrayList<String> slider_story_list) {
            this.activity = activity;
            this.slider_story_list = slider_story_list;
        }
        @Override
        public int getCount() {
            return slider_story_list.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.success_story_slider_item, container, false);
            TextView textView = (TextView) view.findViewById(R.id.welcome_slider_text);
            FrameLayout frameLayoutText = view.findViewById(R.id.idstoryTextFL);
            FrameLayout frameLayoutImage = view.findViewById(R.id.idstoryImageFL);
            if(position==10){
                frameLayoutText.setVisibility(View.GONE);
                frameLayoutImage.setVisibility(View.VISIBLE);
            }else {
                frameLayoutText.setVisibility(View.VISIBLE);
                frameLayoutImage.setVisibility(View.GONE);
                textView.setText(slider_story_list.get(position).toString());
            }
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }


}
