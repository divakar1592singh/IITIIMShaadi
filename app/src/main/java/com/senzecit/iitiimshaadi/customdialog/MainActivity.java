package com.senzecit.iitiimshaadi.customdialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;

import java.util.ArrayList;
import java.util.Vector;

public class MainActivity extends Activity {

	TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		textView= (TextView) findViewById(R.id.text);
		((Button)findViewById(R.id.btn_normal)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(100,50);
			}
		});

		((Button)findViewById(R.id.btn_cal)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}

	public Vector<Dialog> dialogs = new Vector<Dialog>();


	private void showDialog(int d_width,int d_height){

		final ArrayList<Model>models=new ArrayList<Model>();
		for(int i=0;i<20;i++){
			Model model=new Model();
			model.setName("Faiyaz "+i);
			models.add(model);
		}

		final Dialog dialog = new Dialog(this, R.style.CustomDialog);//,R.style.CustomDialog
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

		View view = getLayoutInflater().inflate(R.layout.toast_layout, null);

		final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
		final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.custom_list);
//		final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
		recyclerView.setLayoutManager(mLayoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());


		((Button)view.findViewById(R.id.button_done)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		final CustomListAdapterDialog clad1 = new CustomListAdapterDialog(MainActivity.this, models);
		recyclerView.setAdapter(clad1);

		dialog.setContentView(view);
		dialog.setCanceledOnTouchOutside(true);
		dialogs.add(dialog);
		dialog.show();

		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;

		Window window = dialog.getWindow();
		window.setGravity(Gravity.RIGHT);
		window.setLayout(width-d_width, height-d_height);

		clad1.setOnItemClickListener(new CustomListAdapterDialog.OnItemClickListener() {
			@Override
			public void onItemClick(View view, int position, String id) {
				int positionInList = position % models.size();
                textView.setText(models.get(positionInList).getName());
				dialog.dismiss();

//				showDialog(130,50);
			}
		});

		/*final TextView mCenterIndicator = (TextView) view.findViewById(R.id.centerIndicator);

		final int itemWidth =240;// (int) getResources().getDimension(R.dimen.flexible_space_image_height) ;


		mCenterIndicator.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {

				int center = ( mCenterIndicator.getTop()+ mCenterIndicator.getBottom() ) / 2 ;

				int padding =  center - itemWidth / 2; //Assuming both left and right padding needed are the same

//				recyclerView.setPadding(padding,0,padding,0);
				recyclerView.setOnScrollListener(new CenterLockListener(center));
			}
		});
		recyclerView.getLayoutManager().scrollToPosition(Integer.MAX_VALUE / 2);


		new Thread(new Runnable() {
			@Override
			public void run() {
				int firstPos = mLayoutManager.findFirstVisibleItemPosition();
				int lastPos = mLayoutManager.findLastVisibleItemPosition();
				int middle = Math.abs(lastPos - firstPos) / 2 + firstPos;

				int selectedPos = -1;
				for (int i = 0; i < clad1.getItemCount(); i++) {
					if (i == middle) {

						selectedPos = i;
					} else {

					}
				}
				System.out.println("Middle Pos "+selectedPos);
			}
		}).start();
*/
//		clad1.notifyDataSetChanged();
	}

}
