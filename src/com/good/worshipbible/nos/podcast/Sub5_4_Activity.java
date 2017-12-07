package com.good.worshipbible.nos.podcast;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.admixer.AdAdapter;
import com.admixer.AdInfo;
import com.admixer.AdMixerManager;
import com.admixer.AdView;
import com.admixer.AdViewListener;
import com.good.worshipbible.nos.R;
import com.good.worshipbible.nos.podcast.data.Sub5_4_ColumData;
import com.good.worshipbible.nos.podcast.db.helper.Sub5_4_DBopenHelper;
import com.good.worshipbible.nos.util.ImageLoader;
import com.good.worshipbible.nos.util.RoundedTransform;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.squareup.picasso.Picasso;

public class Sub5_4_Activity extends Activity implements OnItemClickListener, AdViewListener, OnClickListener{
	public Sub5_4_DBopenHelper download_mydb;
	public SQLiteDatabase mdb;
	public Cursor cursor;
	public Context context;
	public ConnectivityManager connectivityManger;
	public NetworkInfo mobile;
	public NetworkInfo wifi;
	public static ListView listview_maindownload;
	public static DownloadAdapter<Sub5_4_ColumData> adapter;
	public static LinearLayout layout_listview_maindownload, layout_nodata;
	public int SDK_INT = android.os.Build.VERSION.SDK_INT;
	public static RelativeLayout ad_layout;
	public static Button Bottom_01, Bottom_02, Bottom_05;
	public static TextView txt_maindownload_title;
	private NativeExpressAdView admobNative;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub5_4_activity);
//		Logger.setLogLevel(com.admixer.Logger$LogLevel.Verbose);
		AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD, "AX00056EB");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD_FULL, "AX00056EC");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMIXER, "u6dbtyd1");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB, "ca-app-pub-4637651494513698/9745545364");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB_FULL, "ca-app-pub-4637651494513698/2222278564");
		context = this;
		addBannerView();
//		init_admob_naive();
		txt_maindownload_title = (TextView)findViewById(R.id.txt_maindownload_title);
		txt_maindownload_title.setText(context.getString(R.string.sub5_txt33));
		txt_maindownload_title.setSelected(false);
		layout_listview_maindownload = (LinearLayout)findViewById(R.id.layout_listview_maindownload);
		layout_nodata = (LinearLayout)findViewById(R.id.layout_nodata);
		download_mydb = new Sub5_4_DBopenHelper(context);
		Bottom_01 = (Button)findViewById(R.id.Bottom_01);
		Bottom_02 = (Button)findViewById(R.id.Bottom_02);
		Bottom_05 = (Button)findViewById(R.id.Bottom_05);
		Bottom_01.setOnClickListener(this);
		Bottom_02.setOnClickListener(this);
		Bottom_05.setOnClickListener(this);
		displayList();
	}	
	
	private void init_admob_naive(){
		RelativeLayout nativeContainer = (RelativeLayout) findViewById(R.id.admob_native);
		AdRequest adRequest = new AdRequest.Builder().build();	    
		admobNative = new NativeExpressAdView(this);
		admobNative.setAdSize(new AdSize(360, 100));
		admobNative.setAdUnitId("ca-app-pub-4637651494513698/4255049767");
		nativeContainer.addView(admobNative);
		admobNative.loadAd(adRequest);
	}
	
	public void addBannerView() {
    	AdInfo adInfo = new AdInfo("u6dbtyd1");
    	adInfo.setTestMode(false);
        AdView adView = new AdView(this);
        adView.setAdInfo(adInfo, this);
        adView.setAdViewListener(this);
        
        ad_layout = (RelativeLayout)findViewById(R.id.ad_layout);
        if(ad_layout != null){
        	RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            ad_layout.addView(adView, params);	
        }
    }
	
	@Override
	protected void onPause() {
		super.onPause();
//		admobNative.pause();

	}
	@Override
	protected void onResume() {
		super.onResume();
//		admobNative.resume();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
//		admobNative.destroy();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		if(adapter != null){
			adapter.notifyDataSetChanged();
		}
		displayList();
	}
	
	public  void displayList(){
		List<Sub5_4_ColumData>contactsList = getContactsList();
		adapter = new DownloadAdapter<Sub5_4_ColumData>(
    			context, R.layout.activity_sub5_4_listrow, contactsList);
		listview_maindownload = (ListView)findViewById(R.id.listview_maindownload);
		listview_maindownload.setAdapter(adapter);
		if (SDK_INT >= Build.VERSION_CODES.HONEYCOMB){ //허니콤 버전에서만 실행 가능한 API 사용}
			listview_maindownload.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    	}
		listview_maindownload.setOnItemClickListener(this);
		listview_maindownload.setFastScrollEnabled(false);
		
		if(listview_maindownload.getCount() == 0){
			layout_nodata.setVisibility(View.VISIBLE);
			layout_listview_maindownload.setVisibility(View.GONE);
		}else{
			layout_nodata.setVisibility(View.GONE);
			layout_listview_maindownload.setVisibility(View.VISIBLE);
		}
	}
	
	public List<Sub5_4_ColumData> getContactsList() {
		List<Sub5_4_ColumData>contactsList = new ArrayList<Sub5_4_ColumData>();
		try{
			download_mydb = new Sub5_4_DBopenHelper(context);
			mdb = download_mydb.getWritableDatabase();
	        cursor = mdb.rawQuery("select * from download_list group by description_title order by _id desc", null);
	        while (cursor.moveToNext()){
				addContact(contactsList,cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
	        }
		}catch (Exception e) {
		}finally{
			cursor.close();
			download_mydb.close();
			mdb.close();
		}
		return contactsList;
	}
	
	public void addContact(List<Sub5_4_ColumData> contactsList, int _id, String title, String enclosure, 
							String pubDate, String image, String description_title, String provider){
		contactsList.add(new Sub5_4_ColumData(_id, title, enclosure, pubDate, image, description_title, provider));
	}
	
	@Override
	public void onClick(View view) {
		if(view == Bottom_01){
			Intent intent = new Intent(this, Sub5_3_Activity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}else if(view == Bottom_02){
			Intent intent = new Intent(this, Sub5_4_Activity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}else if(view == Bottom_05){
			Intent intent = new Intent(this, Sub5_5_Activity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		List<Sub5_4_ColumData>contactsList = getContactsList();
		int _id = contactsList.get(position).get_id();
		String title = contactsList.get(position).getTitle();
		String enclosure = contactsList.get(position).getEnclosure();
		String pubDate = contactsList.get(position).getPubDate();
		String image = contactsList.get(position).getImage();
		String description_title = contactsList.get(position).getDescription_title();
		String provider = contactsList.get(position).getProvider();

		Intent intent = new Intent(context, Sub5_4_1_Activity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("title", title);
		intent.putExtra("enclosure", enclosure);
		intent.putExtra("pubDate", pubDate);
		intent.putExtra("image", image);
		intent.putExtra("description_title", description_title);
		intent.putExtra("provider", provider);
		startActivity(intent);
	}
	
	public class DownloadAdapter<T extends Sub5_4_ColumData>extends ArrayAdapter<T>{
		public List<T> contactsList;
		ImageLoader imgLoader = new ImageLoader(getApplicationContext());
		public String num = "empty";
		public DownloadAdapter(Context context, int textViewResourceId, List<T> items) {
			super(context, textViewResourceId, items);
			contactsList = items;
		}
		@Override
		public View getView(final int position, View view, ViewGroup parent) {
			try{
				if(view == null){
					LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					view = vi.inflate(R.layout.activity_sub5_4_listrow, null);
				}
				final T contacts = contactsList.get(position);
				if (contacts != null) {
					TextView txt_main_downtitle = (TextView)view.findViewById(R.id.txt_main_downtitle);
					txt_main_downtitle.setText(contacts.getDescription_title());
					txt_main_downtitle.setTextColor(Color.BLACK);
					
					TextView txt_main_provider = (TextView)view.findViewById(R.id.txt_main_provider);
					txt_main_provider.setText(contacts.getProvider());
					txt_main_provider.setTextColor(Color.BLACK);

					ImageView img_main_downimageurl = (ImageView)view.findViewById(R.id.img_main_downimageurl);
					String image_url = contacts.getImage();
					
					BitmapFactory.Options dimensions = new BitmapFactory.Options(); 
					dimensions.inJustDecodeBounds = true;
					
					Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.no_image, dimensions);
					        int height = dimensions.outHeight;
					        int width =  dimensions.outWidth;
					
					Picasso.with(context)
				    .load(image_url)
				    .transform(new RoundedTransform())
				    .resize(width, height )
				    .placeholder(R.drawable.loader)
				    .error(R.drawable.loader)
				    .into(img_main_downimageurl);
				}
			}catch (Exception e) {
			}finally{
				cursor.close();
				download_mydb.close();
				
			}
			return view;
		}
	}
	
	public String setDateTrim(String paramString){
		return paramString.substring(2, 11).replace("-", ".");
	}
	
	public void AlertShow(String msg) {
        AlertDialog.Builder alert_internet_status = new AlertDialog.Builder(
                 this);
         alert_internet_status.setTitle(context.getString(R.string.sub5_txt13));
         alert_internet_status.setCancelable(false);
         alert_internet_status.setMessage(msg);
         alert_internet_status.setPositiveButton(context.getString(R.string.sub5_txt14),
                 new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         dialog.dismiss(); // 닫기
                         finish();
                     }
                 });
         alert_internet_status.show();
     }
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public void onClickedAd(String arg0, AdView arg1) {
	}

	@Override
	public void onFailedToReceiveAd(int arg0, String arg1, AdView arg2) {
	}

	@Override
	public void onReceivedAd(String arg0, AdView arg1) {
	}
}