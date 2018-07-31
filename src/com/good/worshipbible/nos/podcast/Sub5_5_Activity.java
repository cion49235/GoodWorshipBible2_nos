package com.good.worshipbible.nos.podcast;

import java.util.ArrayList;
import java.util.List;

import com.admixer.AdAdapter;
import com.admixer.AdInfo;
import com.admixer.AdMixerManager;
import com.admixer.AdView;
import com.admixer.AdViewListener;
import com.good.worshipbible.nos.R;
import com.good.worshipbible.nos.data.Const;
import com.good.worshipbible.nos.mediaplayer.ContinueMediaPlayer;
import com.good.worshipbible.nos.podcast.data.Sub5_5_ColumData;
import com.good.worshipbible.nos.podcast.db.helper.Sub5_5_DBopenHelper;
import com.good.worshipbible.nos.util.ImageLoader;
import com.good.worshipbible.nos.util.PreferenceUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Sub5_5_Activity extends Activity implements OnItemClickListener, AdViewListener, OnClickListener{
	public Sub5_5_DBopenHelper continue_mydb;
	public SQLiteDatabase mdb;
	public Cursor cursor;
	public Context context;
	public ConnectivityManager connectivityManger;
	public NetworkInfo mobile;
	public NetworkInfo wifi;
	public static ListView listview_continue;
	public static ContinueAdapter<Sub5_5_ColumData> adapter;
	public static LinearLayout layout_listview_continue, layout_nodata;
	public int SDK_INT = android.os.Build.VERSION.SDK_INT;
	public static RelativeLayout ad_layout;
	public static LinearLayout action_layout;
	public static Button bt_all_select, bt_play_intent, bt_delete;
	public static Button Bottom_01, Bottom_02, Bottom_05;
	public static TextView txt_continue_title;
	private NativeExpressAdView admobNative;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub5_5);
//		Logger.setLogLevel(com.admixer.Logger$LogLevel.Verbose);
		AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD, "AX00056EB");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD_FULL, "AX00056EC");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMIXER, "u6dbtyd1");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB, "ca-app-pub-4637651494513698/9745545364");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB_FULL, "ca-app-pub-4637651494513698/2222278564");
		context = this;
		if(!PreferenceUtil.getStringSharedData(context, PreferenceUtil.PREF_ISSUBSCRIBED, Const.isSubscribed).equals("true")){
        	addBannerView();    		
    	}
//		init_admob_naive();
		bt_all_select = (Button)findViewById(R.id.bt_all_select);
		bt_play_intent = (Button)findViewById(R.id.bt_play_intent);
		bt_delete = (Button)findViewById(R.id.bt_delete);
		bt_all_select.setOnClickListener(this);
		bt_play_intent.setOnClickListener(this);
		bt_delete.setOnClickListener(this);
		txt_continue_title = (TextView)findViewById(R.id.txt_continue_title);
		txt_continue_title.setText(context.getString(R.string.sub5_txt6));
		txt_continue_title.setSelected(false);
		layout_listview_continue = (LinearLayout)findViewById(R.id.layout_listview_continue);
		layout_nodata = (LinearLayout)findViewById(R.id.layout_nodata);
		action_layout = (LinearLayout)findViewById(R.id.action_layout);
		continue_mydb = new Sub5_5_DBopenHelper(context);
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
	protected void onStart() {
		super.onStart();
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
	protected void onRestart() {
		super.onRestart();
		if(adapter != null){
			adapter.notifyDataSetChanged();
		}
	}
	
	public void displayList(){
		List<Sub5_5_ColumData>contactsList = getContactsList();
		adapter = new ContinueAdapter<Sub5_5_ColumData>(
    			context, R.layout.activity_sub5_5_listrow, contactsList);
		listview_continue = (ListView)findViewById(R.id.listview_continue);
		listview_continue.setAdapter(adapter);
		if (SDK_INT >= Build.VERSION_CODES.HONEYCOMB){ //허니콤 버전에서만 실행 가능한 API 사용}
			listview_continue.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    	}
		listview_continue.setOnItemClickListener(this);
		listview_continue.setItemsCanFocus(false);
		listview_continue.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listview_continue.setFastScrollEnabled(false);
		
		if(listview_continue.getCount() == 0){
			layout_nodata.setVisibility(View.VISIBLE);
			layout_listview_continue.setVisibility(View.GONE);
		}else{
			layout_nodata.setVisibility(View.GONE);
			layout_listview_continue.setVisibility(View.VISIBLE);
		}
	}
	
	public List<Sub5_5_ColumData> getContactsList() {
		List<Sub5_5_ColumData>contactsList = new ArrayList<Sub5_5_ColumData>();
		try{
			continue_mydb = new Sub5_5_DBopenHelper(context);
			mdb = continue_mydb.getWritableDatabase();
	        cursor = mdb.rawQuery("select * from continue_list order by _id desc", null);
	        while (cursor.moveToNext()){
				addContact(contactsList,cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4), cursor.getString(5));
	        }
		}catch (Exception e) {
		}finally{
			cursor.close();
			continue_mydb.close();
			mdb.close();
		}
		return contactsList;
	}
	
	public void addContact(List<Sub5_5_ColumData> contactsList, int _id, String title, String enclosure, 
							String pubDate, String image, String description_title){
		contactsList.add(new Sub5_5_ColumData(_id,title, enclosure, pubDate, image, description_title));
	}

	@Override
	public void onClick(View view) {
		if(view == bt_all_select){
			if(bt_all_select.isSelected()){
				bt_all_select.setSelected(false);
				bt_all_select.setText(context.getString(R.string.sub5_txt2));
				for(int i=0; i < listview_continue.getCount(); i++){
					listview_continue.setItemChecked(i, false);
				}
				action_layout.setVisibility(View.GONE);
			}else{
				bt_all_select.setSelected(true);
				bt_all_select.setText(context.getString(R.string.sub5_txt7));
				for(int i=0; i < listview_continue.getCount(); i++){
					listview_continue.setItemChecked(i, true);
				}
			}
		}else if(view == bt_play_intent){
			SparseBooleanArray sba = listview_continue.getCheckedItemPositions();
			ArrayList<String> array_title = new ArrayList<String>();
			ArrayList<String> array_enclosure = new ArrayList<String>();
			ArrayList<String> array_pubDate = new ArrayList<String>();
			ArrayList<String> array_image = new ArrayList<String>();
			ArrayList<String> array_description_title = new ArrayList<String>();
			if(sba.size() != 0){
				for(int i = listview_continue.getCount() -1; i>=0; i--){
					if(sba.get(i)){
						Sub5_5_ColumData continue_data = (Sub5_5_ColumData)adapter.getItem(i);
						String title = continue_data.getTitle();
						String enclosure = continue_data.getEnclosure();
						String pubDate = setDateTrim(continue_data.getPubDate());
						String image = continue_data.getImage();
						String description_title = continue_data.getDescription_title();
						
						array_title.add(title);
						array_enclosure.add(enclosure);
						array_pubDate.add(pubDate);
						array_image.add(image);
						array_description_title.add(description_title);
						sba = listview_continue.getCheckedItemPositions();
					}
				}
				connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
				mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
				if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
					Intent intent = new Intent(context, ContinueMediaPlayer.class);
					intent.putExtra("array_title", array_title);
					intent.putExtra("array_enclosure", array_enclosure);
					intent.putExtra("array_pubDate", array_pubDate);
					intent.putExtra("array_image", array_image);
					intent.putExtra("array_description_title", array_description_title);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
				}else{
					Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
				}
			}			
		}else if(view == bt_delete){
			SparseBooleanArray sba = listview_continue.getCheckedItemPositions();
			if(sba.size() != 0){
				for(int i = listview_continue.getCount() -1; i>=0; i--){
					if(sba.get(i)){
						Sub5_5_ColumData continue_data = (Sub5_5_ColumData)adapter.getItem(i);
						int _id = continue_data.get_id();
						continue_mydb.getWritableDatabase().delete("continue_list", "_id" + "=" +_id, null);
						sba = listview_continue.getCheckedItemPositions();
					}
				}
				displayList();
				if(Sub5_2_Activity.sub_adapter != null){
					Sub5_2_Activity.sub_adapter.notifyDataSetChanged();	
				}
				action_layout.setVisibility(View.GONE);
				Toast.makeText(this, context.getString(R.string.sub5_txt8), Toast.LENGTH_SHORT).show();
			}
		}else if(view == Bottom_01){
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
		int selectd_count = 0;
    	SparseBooleanArray sba = listview_continue.getCheckedItemPositions();
		if(sba.size() != 0){
			for(int i = listview_continue.getCount() -1; i>=0; i--){
				if(sba.get(i)){
					sba = listview_continue.getCheckedItemPositions();
					selectd_count++;
				}
			}
		}
		if(selectd_count == 0){
			action_layout.setVisibility(View.GONE);
		}else{
			action_layout.setVisibility(View.VISIBLE);
		}
		if(adapter != null){
			adapter.notifyDataSetChanged();
		}
	}
	
	public class ContinueAdapter<T extends Sub5_5_ColumData>extends ArrayAdapter<T>{
		public List<T> contactsList;
		ImageLoader imgLoader = new ImageLoader(getApplicationContext());
		public ImageButton bt_continue_delete;
		public SharedPreferences settings,pref;
		public ContinueAdapter(Context context, int textViewResourceId, List<T> items) {
			super(context, textViewResourceId, items);
			contactsList = items;
		}
		@Override
		public View getView(final int position, View view, ViewGroup parent) {
			try{
				pref = context.getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), Activity.MODE_PRIVATE);
				String title= pref.getString("title", "");
				String pubDate = pref.getString("pubDate", "");
				
				if(view == null){
					LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					view = vi.inflate(R.layout.activity_sub5_5_listrow, null);
				}
				final T contacts = contactsList.get(position);
				TextView txt_continue_title = (TextView)view.findViewById(R.id.txt_continue_title);
				txt_continue_title.setText(contacts.getTitle());
				
				TextView txt_continue_udate = (TextView)view.findViewById(R.id.txt_continue_udate);
				txt_continue_udate.setText(setDateTrim(contacts.getPubDate()));
				
				bt_continue_delete = (ImageButton)view.findViewById(R.id.bt_continue_delete);
				bt_continue_delete.setFocusable(false);
				bt_continue_delete.setSelected(false);	
				bt_continue_delete.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						continue_mydb.getWritableDatabase().delete("continue_list", "_id" + "=" +contacts.get_id(), null);
						displayList();
						if(Sub5_2_Activity.sub_adapter != null){
							Sub5_2_Activity.sub_adapter.notifyDataSetChanged();
						}
						action_layout.setVisibility(View.GONE);
						Toast.makeText(context, context.getString(R.string.sub5_txt8), Toast.LENGTH_SHORT).show();
					}
				});
				if(listview_continue.isItemChecked(position)){
					view.setBackgroundColor(Color.parseColor("#00a8ec"));
					txt_continue_title.setTextColor(Color.parseColor("#ffffff"));
				}else{
					view.setBackgroundColor(Color.parseColor("#00000000"));
					if(contacts.getTitle().equals(title) && setDateTrim(contacts.getPubDate()).equals(pubDate)){
						txt_continue_title.setTextColor(Color.RED);
					}else{
						txt_continue_title.setTextColor(Color.parseColor("#000000"));
					}
				}
			}catch (Exception e) {
			}finally{
				cursor.close();
				continue_mydb.close();
			}
			return view;
		}
	}
	
	public String setDateTrim(String paramString){
		return paramString.substring(0, 8);
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