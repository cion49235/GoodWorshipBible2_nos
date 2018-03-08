package com.good.worshipbible.nos.activity;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.admixer.AdAdapter;
import com.admixer.AdInfo;
import com.admixer.AdMixerManager;
import com.admixer.AdView;
import com.admixer.AdViewListener;
import com.good.worshipbible.nos.R;
import com.good.worshipbible.nos.data.Const;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_Sub4;
import com.good.worshipbible.nos.util.PreferenceUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class Sub4_2_Activity extends Activity implements AdViewListener {
	public static EditText edit_title_txt, edit_content_txt;
	public static Integer _id;
	public static String kwon, jang, jul, content;
	public static boolean edit = false;
	private DBOpenHelper_Sub4 favorite_mydb;
	public Context context;
	public static RelativeLayout ad_layout;
	private NativeExpressAdView admobNative;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub4_2);
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
		edit_title_txt = (EditText)findViewById(R.id.edit_title_txt);
		edit_content_txt = (EditText)findViewById(R.id.edit_content_txt);
		edit = getIntent().getBooleanExtra("edit", false);
		
		_id = getIntent().getIntExtra("_id", 0);
		kwon = getIntent().getStringExtra("kwon");
		jang = getIntent().getStringExtra("jang");
		jul = getIntent().getStringExtra("jul");
		edit_title_txt.setText(kwon);
		content = getIntent().getStringExtra("content");
		if(content != null){
			edit_content_txt.setText(content);
		}
		favorite_mydb = new DBOpenHelper_Sub4(this);
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
		if(favorite_mydb != null){
    		favorite_mydb.close();
    	}
//    	Log.i("dsu", "adDestroy");
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			try{
				if(edit_title_txt.getText().length() == 0 && edit_content_txt.getText().length() == 0){
					onBackPressed();
				}else if(edit_content_txt.getText().length() == 0){
					onBackPressed();
				}
				else{
					SimpleDateFormat dateFormat = new SimpleDateFormat("y.MM.dd a h:mm:ss");  
					Date date = new Date();
					ContentValues cv = new ContentValues();
					if(edit_title_txt.getText().length() == 0){
						cv.put("kwon", context.getString(R.string.bible_noteedit_txt1));	
					}else{
						cv.put("kwon", edit_title_txt.getText().toString());
					}
					if(jang != null){
						if(jang.length() < 2){
							cv.put("jang", jang);
						}	
					}else{
						cv.put("jang", "\n"+dateFormat.format(date));
					}
					if(jul != null){
						if(jul.length() == 0){
							cv.put("jul", "");
						}else{
							cv.put("jul", jul);
						}
					}
					cv.put("content", edit_content_txt.getText().toString());
					if(edit == true){
						favorite_mydb.getWritableDatabase().update("my_list", cv, "_id" + "=" + _id, null);
						Toast.makeText(Sub4_2_Activity.this, R.string.bible_noteedit_txt4, Toast.LENGTH_SHORT).show();
							
					}else{
						favorite_mydb.getWritableDatabase().insert("my_list", null, cv);
						Toast.makeText(Sub4_2_Activity.this, R.string.bible_noteedit_txt3, Toast.LENGTH_SHORT).show();
					}
					Intent intent = new Intent(this, Sub4_1_Activity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			}catch(Exception e){
			}finally{
				if(favorite_mydb != null){
					favorite_mydb.close();
				}
			}
		}
		return super.onKeyDown(keyCode, event);
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
	
	//** BannerAd 이벤트들 *************
	@Override
	public void onClickedAd(String arg0, AdView arg1) {
	}

	@Override
	public void onFailedToReceiveAd(int arg0, String arg1, AdView arg2) {
		
	}
	@Override
	public void onReceivedAd(String arg0, AdView arg1) {
//		Log.i("dsu", "배너광고 : arg0 : " + arg0+"\n" + arg1) ;
	}	
}
