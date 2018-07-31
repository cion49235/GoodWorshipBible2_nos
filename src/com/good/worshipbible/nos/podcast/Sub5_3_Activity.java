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
import com.good.worshipbible.nos.podcast.data.Sub5_3_ColumData;
import com.good.worshipbible.nos.podcast.db.helper.Sub5_3_DBopenHelper;
import com.good.worshipbible.nos.util.ImageLoader;
import com.good.worshipbible.nos.util.PreferenceUtil;
import com.good.worshipbible.nos.util.RoundedTransform;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.squareup.picasso.Picasso;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Sub5_3_Activity extends Activity implements OnItemClickListener, AdViewListener, OnClickListener{
	public Sub5_3_DBopenHelper favorite_mydb;
	public SQLiteDatabase mdb;
	public Cursor cursor;
	public Context context;
	public ConnectivityManager connectivityManger;
	public NetworkInfo mobile;
	public NetworkInfo wifi;
	public static ListView listview_favorite;
	public FavoriteAdapter<Sub5_3_ColumData> adapter;
	public static LinearLayout layout_listview_favorite, layout_nodata;
	public int SDK_INT = android.os.Build.VERSION.SDK_INT;
	public static RelativeLayout ad_layout;
	public static Button Bottom_01, Bottom_02, Bottom_05;
	public static TextView txt_favorite_title;
	private NativeExpressAdView admobNative;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub5_3);
//		Logger.setLogLevel(com.admixer.Logger$LogLevel.Verbose);
		AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD, "AX00056EB");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD_FULL, "AX00056EC");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMIXER, "u6dbtyd1");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB, "ca-app-pub-4637651494513698/9745545364");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB_FULL, "ca-app-pub-4637651494513698/2222278564");
		context = this;
//		init_admob_naive();
		if(!PreferenceUtil.getStringSharedData(context, PreferenceUtil.PREF_ISSUBSCRIBED, Const.isSubscribed).equals("true")){
        	addBannerView();    		
    	}
		layout_listview_favorite = (LinearLayout)findViewById(R.id.layout_listview_favorite);
		layout_nodata = (LinearLayout)findViewById(R.id.layout_nodata);
		txt_favorite_title = (TextView)findViewById(R.id.txt_favorite_title);
		txt_favorite_title.setText(context.getString(R.string.sub5_txt12));
		txt_favorite_title.setSelected(false);
		favorite_mydb = new Sub5_3_DBopenHelper(context);
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
	protected void onStart() {
		super.onStart();
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
		List<Sub5_3_ColumData>contactsList = getContactsList();
		adapter = new FavoriteAdapter<Sub5_3_ColumData>(
    			context, R.layout.activity_sub5_3_listrow, contactsList);
		listview_favorite = (ListView)findViewById(R.id.listview_favorite);
		listview_favorite.setAdapter(adapter);
		if (SDK_INT >= Build.VERSION_CODES.HONEYCOMB){ //허니콤 버전에서만 실행 가능한 API 사용}
			listview_favorite.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    	}
		listview_favorite.setOnItemClickListener(this);
		listview_favorite.setFastScrollEnabled(false);
		
		if(listview_favorite.getCount() == 0){
			layout_nodata.setVisibility(View.VISIBLE);
			layout_listview_favorite.setVisibility(View.GONE);
		}else{
			layout_nodata.setVisibility(View.GONE);
			layout_listview_favorite.setVisibility(View.VISIBLE);
		}
	}
	
	public List<Sub5_3_ColumData> getContactsList() {
		List<Sub5_3_ColumData>contactsList = new ArrayList<Sub5_3_ColumData>();
		try{
			favorite_mydb = new Sub5_3_DBopenHelper(context);
			mdb = favorite_mydb.getWritableDatabase();
	        cursor = mdb.rawQuery("select * from favorite_list order by _id desc", null);
	        while (cursor.moveToNext()){
				addContact(contactsList,cursor.getInt(0), cursor.getString(1), cursor.getString(2), 
						cursor.getString(3),cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
	        }
		}catch (Exception e) {
		}finally{
			cursor.close();
			favorite_mydb.close();
			mdb.close();
		}
		return contactsList;
	}
	
	public void addContact(List<Sub5_3_ColumData> contactsList, int _id, String id, String num, 
							String title, String provider, String imageurl, String rssurl, String udate){
		contactsList.add(new Sub5_3_ColumData(_id, id, num, title, provider, imageurl, rssurl, udate));
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
		List<Sub5_3_ColumData>contactsList = getContactsList();
		String id = contactsList.get(position).getId();
		String num = contactsList.get(position).getNum();
		String title = contactsList.get(position).getTitle();
		String provider = contactsList.get(position).getProvider();
		String imageurl = contactsList.get(position).getImageurl();
		String rssurl = contactsList.get(position).getRssurl();
		String udate = contactsList.get(position).getUdate();

		Intent intent = new Intent(context, Sub5_2_Activity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("id", id);
		intent.putExtra("num", num);
		intent.putExtra("title", title);
		intent.putExtra("provider", provider);
		intent.putExtra("imageurl", imageurl);
		intent.putExtra("rssurl", rssurl);
		intent.putExtra("udate", udate);
		startActivity(intent);
	}
	
	public class FavoriteAdapter<T extends Sub5_3_ColumData>extends ArrayAdapter<T>{
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
					view = vi.inflate(R.layout.activity_sub5_3_listrow, null);
				}
				final T contacts = contactsList.get(position);
				if (contacts != null) {
					ImageView img_favorite_imageurl = (ImageView)view.findViewById(R.id.img_favorite_imageurl);
					img_favorite_imageurl.setFocusable(false);
					String image_url = contacts.getImageurl();
					
					BitmapFactory.Options dimensions = new BitmapFactory.Options(); 
					dimensions.inJustDecodeBounds = true;
					Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.no_image, dimensions);
					        int height = dimensions.outHeight;
					        int width =  dimensions.outWidth;
					Picasso.with(context)
				    .load(image_url)
				    .transform(new RoundedTransform())
				    .resize(width, height )
				    .placeholder(R.drawable.no_image)
				    .error(R.drawable.no_image)
				    .into(img_favorite_imageurl);
					
					TextView txt_favorite_title = (TextView)view.findViewById(R.id.txt_favorite_title);
					txt_favorite_title.setText(contacts.getTitle());
					txt_favorite_title.setTextColor(Color.BLACK);
					
					TextView txt_favorite_provider = (TextView)view.findViewById(R.id.txt_favorite_provider);
					txt_favorite_provider.setText(contacts.getProvider());
					txt_favorite_provider.setTextColor(Color.BLACK);
					
					TextView txt_favorite_udate = (TextView)view.findViewById(R.id.txt_favorite_udate);
					txt_favorite_udate.setText(setDateTrim(contacts.getUdate()));
				}
				
				bt_favorite_delete = (ImageButton)view.findViewById(R.id.bt_favorite_delete);
				bt_favorite_delete.setFocusable(false);
				bt_favorite_delete.setSelected(false);
				bt_favorite_delete.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						favorite_mydb.getWritableDatabase().delete("favorite_list", "_id" + "=" +contacts.get_id(), null);
						displayList();
						Toast.makeText(context, context.getString(R.string.sub5_txt8), Toast.LENGTH_SHORT).show();
					}
				});
			}catch (Exception e) {
			}finally{
				cursor.close();
				favorite_mydb.close();
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