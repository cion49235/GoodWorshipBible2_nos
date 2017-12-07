package com.good.worshipbible.nos.ccm;

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
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.admixer.AdAdapter;
import com.admixer.AdInfo;
import com.admixer.AdMixerManager;
import com.admixer.AdView;
import com.admixer.AdViewListener;
import com.good.worshipbible.nos.R;
import com.good.worshipbible.nos.ccm.data.Favorite_Data;
import com.good.worshipbible.nos.ccm.db.helper.Sub6_2_DBopenHelper;
import com.good.worshipbible.nos.util.ImageLoader;
import com.good.worshipbible.nos.util.RoundedTransform;
import com.good.worshipbible.nos.videoplayer.CustomVideoPlayer;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.squareup.picasso.Picasso;

public class Sub6_2_Activity extends Activity implements OnItemClickListener, AdViewListener, OnClickListener, OnScrollListener{
	public Sub6_2_DBopenHelper favorite_mydb;
	public SQLiteDatabase mdb;
	public Cursor cursor;
	public Context context;
	public ConnectivityManager connectivityManger;
	public NetworkInfo mobile;
	public NetworkInfo wifi;
	public static ListView listview_favorite;
	public FavoriteAdapter<Favorite_Data> adapter;
	public static LinearLayout layout_listview_favorite, layout_nodata;
	public int SDK_INT = android.os.Build.VERSION.SDK_INT;
	public static RelativeLayout ad_layout;
	public static TextView txt_favorite_title;
	public static LinearLayout action_layout;
	public static Button bt_all_select,bt_play_media, bt_play_video, bt_favorite_delete;
	private NativeExpressAdView admobNative;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub6_2);
//		Logger.setLogLevel(com.admixer.Logger$LogLevel.Verbose);
		AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD, "AX00056EB");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD_FULL, "AX00056EC");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMIXER, "u6dbtyd1");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB, "ca-app-pub-4637651494513698/9745545364");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB_FULL, "ca-app-pub-4637651494513698/2222278564");
		context = this;
//		init_admob_naive();
		addBannerView();
		layout_listview_favorite = (LinearLayout)findViewById(R.id.layout_listview_favorite);
		layout_nodata = (LinearLayout)findViewById(R.id.layout_nodata);
		action_layout = (LinearLayout)findViewById(R.id.action_layout);
		txt_favorite_title = (TextView)findViewById(R.id.txt_favorite_title);
		txt_favorite_title.setText(context.getString(R.string.sub6_txt19));
		txt_favorite_title.setSelected(false);
		favorite_mydb = new Sub6_2_DBopenHelper(context);
		bt_all_select = (Button)findViewById(R.id.bt_all_select);
		bt_play_media = (Button)findViewById(R.id.bt_play_media);
		bt_play_video = (Button)findViewById(R.id.bt_play_video);
		bt_favorite_delete = (Button)findViewById(R.id.bt_favorite_delete);
		bt_all_select.setOnClickListener(this);
		bt_play_media.setOnClickListener(this);
		bt_play_video.setOnClickListener(this);
		bt_favorite_delete.setOnClickListener(this);
			
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
		List<Favorite_Data>contactsList = getContactsList();
		adapter = new FavoriteAdapter<Favorite_Data>(
    			context, R.layout.activity_sub6_2_listrow, contactsList);
		listview_favorite = (ListView)findViewById(R.id.listview_favorite);
		listview_favorite.setAdapter(adapter);
		if (SDK_INT >= Build.VERSION_CODES.HONEYCOMB){ //허니콤 버전에서만 실행 가능한 API 사용}
			listview_favorite.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    	}
		listview_favorite.setOnItemClickListener(this);
		listview_favorite.setOnScrollListener(this);
		listview_favorite.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		if(listview_favorite.getCount() == 0){
			layout_nodata.setVisibility(View.VISIBLE);
			layout_listview_favorite.setVisibility(View.GONE);
		}else{
			layout_nodata.setVisibility(View.GONE);
			layout_listview_favorite.setVisibility(View.VISIBLE);
		}
	}
	
	public List<Favorite_Data> getContactsList() {
		List<Favorite_Data>contactsList = new ArrayList<Favorite_Data>();
		try{
			favorite_mydb = new Sub6_2_DBopenHelper(context);
			mdb = favorite_mydb.getWritableDatabase();
	        cursor = mdb.rawQuery("select * from favorite_list order by _id desc", null);
	        while (cursor.moveToNext()){
				addContact(contactsList,cursor.getInt(0), cursor.getString(1), cursor.getString(2), 
						cursor.getString(3),cursor.getString(4), cursor.getString(5));
	        }
		}catch (Exception e) {
		}finally{
			cursor.close();
			favorite_mydb.close();
			mdb.close();
		}
		return contactsList;
	}
	
	public void addContact(List<Favorite_Data> contactsList, int _id, String id, String title,String category, String thumbnail_hq,String duration){
		contactsList.add(new Favorite_Data(_id, id, title, category, thumbnail_hq, duration));
	}
	
	@Override
	public void onClick(View view) {
		if(view == bt_all_select){
			if(bt_all_select.isSelected()){
				bt_all_select.setSelected(false);
				bt_all_select.setText(context.getString(R.string.sub6_txt2));
				for(int i=0; i < listview_favorite.getCount(); i++){
					listview_favorite.setItemChecked(i, false);
				}
				action_layout.setVisibility(View.GONE);
			}else{
				bt_all_select.setSelected(true);
				bt_all_select.setText(context.getString(R.string.sub6_txt9));
				for(int i=0; i < listview_favorite.getCount(); i++){
					listview_favorite.setItemChecked(i, true);
				}
			}
		}else if(view == bt_play_media){
			SparseBooleanArray sba = listview_favorite.getCheckedItemPositions();
			ArrayList<String> array_id = new ArrayList<String>();
			ArrayList<String> array_title = new ArrayList<String>();
			ArrayList<String> array_category = new ArrayList<String>();
			ArrayList<String> array_thumbnail_hq = new ArrayList<String>();
			ArrayList<String> array_duration = new ArrayList<String>();
			if(sba.size() != 0){
				for(int i = listview_favorite.getCount() -1; i>=0; i--){
					if(sba.get(i)){
						Favorite_Data favorite_data = (Favorite_Data)adapter.getItem(i);
						String id = favorite_data.getId();
						String title = favorite_data.getTitle();
						String category = favorite_data.getCategory();
						String thumbnail_hq =favorite_data.getThumbnail_hq();
						String duration = favorite_data.getDuration();
						
						array_id.add(id);
						array_title.add(title);
						array_category.add(category);
						array_thumbnail_hq.add(thumbnail_hq);
						array_duration.add(duration);
						sba = listview_favorite.getCheckedItemPositions();
					}
				}
				Intent intent = new Intent(context, com.good.worshipbible.nos.videoplayer.ContinueMediaPlayer.class);
				intent.putExtra("array_videoid", array_id);
				intent.putExtra("array_music", array_title);
				intent.putExtra("array_artist", array_category);
				intent.putExtra("array_imageurl", array_thumbnail_hq);
				intent.putExtra("array_playtime", array_duration);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		}else if(view == bt_play_video){
			SparseBooleanArray sba = listview_favorite.getCheckedItemPositions();
			ArrayList<String> array_id = new ArrayList<String>();
			ArrayList<String> array_title = new ArrayList<String>();
			if(sba.size() != 0){
				for(int i = listview_favorite.getCount() -1; i>=0; i--){
					if(sba.get(i)){
						Favorite_Data favorite_data = (Favorite_Data)adapter.getItem(i);
						String id = favorite_data.getId();
						String title = favorite_data.getTitle();
						array_id.add(id);
						array_title.add(title);
						sba = listview_favorite.getCheckedItemPositions();
					}
				}
				Intent intent = new Intent(context, CustomVideoPlayer.class);
				intent.putExtra("array_videoid", array_id);
				intent.putExtra("array_subject", array_title);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}
		}else if(view == bt_favorite_delete){
			SparseBooleanArray sba = listview_favorite.getCheckedItemPositions();
			if(sba.size() != 0){
				for(int i = listview_favorite.getCount() -1; i>=0; i--){
					if(sba.get(i)){
						Favorite_Data favorite_data = (Favorite_Data)adapter.getItem(i);
						int _id = favorite_data.get_id();
						favorite_mydb.getWritableDatabase().delete("favorite_list", "_id" + "=" +_id, null);
						sba = listview_favorite.getCheckedItemPositions();
					}
				}
				displayList();
				if(adapter != null){
					adapter.notifyDataSetChanged();	
				}
				action_layout.setVisibility(View.GONE);
				Toast.makeText(this, context.getString(R.string.sub6_txt15), Toast.LENGTH_SHORT).show();
			}	
			action_layout.setVisibility(View.GONE);
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		int selectd_count = 0;
    	SparseBooleanArray sba = listview_favorite.getCheckedItemPositions();
		if(sba.size() != 0){
			for(int i = listview_favorite.getCount() -1; i>=0; i--){
				if(sba.get(i)){
					sba = listview_favorite.getCheckedItemPositions();
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
	
	public class FavoriteAdapter<T extends Favorite_Data>extends ArrayAdapter<T>{
		public List<T> contactsList;
		ImageLoader imgLoader = new ImageLoader(getApplicationContext());
		public ImageButton bt_favorite_delete;
		public String num = "empty";
		public FavoriteAdapter(Context context, int textViewResourceId, List<T> items) {
			super(context, textViewResourceId, items);
			contactsList = items;
		}
		@Override
		public View getView(final int position, View view, ViewGroup parent) {
			try{
				if(view == null){
					LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					view = vi.inflate(R.layout.activity_sub6_2_listrow, null);
				}
				final T contacts = contactsList.get(position);
				TextView txt_favorite_music = (TextView)view.findViewById(R.id.txt_favorite_music);
				txt_favorite_music.setText(contacts.getTitle());
				
				TextView txt_favorite_artist = (TextView)view.findViewById(R.id.txt_favorite_artist);
				txt_favorite_artist.setText(contacts.getCategory());
				
				ImageView img_favorite_imageurl = (ImageView)view.findViewById(R.id.img_favorite_imageurl);
				img_favorite_imageurl.setFocusable(false);
				String image_url = contacts.getThumbnail_hq();
				
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
			    .into(img_favorite_imageurl);
				
				bt_favorite_delete = (ImageButton)view.findViewById(R.id.bt_favorite_delete);
				bt_favorite_delete.setFocusable(false);
				bt_favorite_delete.setSelected(false);
				bt_favorite_delete.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						favorite_mydb.getWritableDatabase().delete("favorite_list", "_id" + "=" +contacts.get_id(), null);
						displayList();
						Toast.makeText(context, context.getString(R.string.sub6_txt15), Toast.LENGTH_SHORT).show();
					}
				});
				if(listview_favorite.isItemChecked(position)){
					view.setBackgroundColor(Color.parseColor("#00a8ec"));
					txt_favorite_music.setTextColor(Color.parseColor("#ffffff"));
					txt_favorite_artist.setTextColor(Color.parseColor("#ffffff"));
				}else{
					view.setBackgroundColor(Color.parseColor("#00000000"));
					txt_favorite_music.setTextColor(Color.parseColor("#000000"));
					txt_favorite_artist.setTextColor(Color.parseColor("#000000"));
				}
			}catch (Exception e) {
			}finally{
				cursor.close();
				favorite_mydb.close();
			}
			return view;
		}
	}

	public void AlertShow(String msg) {
        AlertDialog.Builder alert_internet_status = new AlertDialog.Builder(
                 this);
         alert_internet_status.setTitle(context.getString(R.string.sub6_txt20));
         alert_internet_status.setCancelable(false);
         alert_internet_status.setMessage(msg);
         alert_internet_status.setPositiveButton(context.getString(R.string.sub6_txt21),
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
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if(view == listview_favorite){
			if(totalItemCount != 0 && firstVisibleItem  > 1 ){
				listview_favorite.setFastScrollEnabled(true);
			}else{
				listview_favorite.setFastScrollEnabled(false);
			}
		}
	}
	
}