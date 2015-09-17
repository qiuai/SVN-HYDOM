package com.carinsurance.scaleview;

import java.util.ArrayList;
import java.util.List;



import com.carinsurancer.car.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.widget.TextView;
/**
 * Intent intent = new Intent(getApplicationContext(), ImageShowActivity.class);
			intent.putExtra(ImageShowActivity.DATA_URI_LIST, (Serializable) imageUrls);
			intent.putExtra(ImageShowActivity.DATA_SELECTED_INDEX, index);
			context.startActivity(intent);
 * @author Administrator
 *
 */
public class ImageShowActivity extends FragmentActivity {
	private ImageShowFragmentAdapter adapter = null;
	private ViewPager viewpager = null;
	public static String DATA_URI_ARRAY = "uriArray";
	public static String DATA_URI_LIST = "uriList";
	public static String DATA_SELECTED_INDEX = "selectedIndex";
	private TextView countInfo;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_image_show);
		initial(arg0);
	}

	@SuppressWarnings("unchecked")
	protected void initial(Bundle savedInstanceState) {
		countInfo = (TextView) findViewById(R.id.image_count_info);
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		adapter = new ImageShowFragmentAdapter(getSupportFragmentManager());
		viewpager.setAdapter(adapter);
		viewpager.setOffscreenPageLimit(5);
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				countInfo.setText("第" + (arg0 + 1) + "/" + adapter.getCount() + "张");
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		String[] uriArray = getIntent().getStringArrayExtra(DATA_URI_ARRAY);
		List<String> uriList = (List<String>) getIntent().getSerializableExtra(DATA_URI_LIST);
		if (uriArray != null) {
			uriList = new ArrayList<String>();
			for (int i = 0; i < uriArray.length; i++) {
				uriList.add(uriArray[i]);
			}

		}
		loadData(uriList);

	}

	private void loadData(List<String> uris) {
		if (uris == null)
			return;
		for (int i = 0; i < uris.size(); i++) {
			Log.i("", "" + uris.get(i));
		}
		adapter.getUris().addAll(uris);
		adapter.notifyDataSetChanged();
		int index = getIntent().getIntExtra(DATA_SELECTED_INDEX, 0);
		viewpager.setCurrentItem(index);
		countInfo.setText("第" + (index + 1) + "/" + uris.size() + "张");
	}
}
