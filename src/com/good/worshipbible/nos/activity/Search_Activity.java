package com.good.worshipbible.nos.activity;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.admixer.AdAdapter;
import com.admixer.AdInfo;
import com.admixer.AdMixerManager;
import com.admixer.AdView;
import com.admixer.AdViewListener;
import com.admixer.InterstitialAd;
import com.admixer.InterstitialAdListener;
import com.good.worshipbible.nos.R;
import com.good.worshipbible.nos.data.Const;
import com.good.worshipbible.nos.data.Sub1_ColumData;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_Sub4;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_alb;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_asv;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_avs;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_barun;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_chb;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_chg;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_cjb;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_ckb;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_ckc;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_ckg;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_cks;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_frenchdarby;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_germanluther;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_gst;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_hebbhs;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_hebmod;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_hebwlc;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_indianhindi;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_indiantamil;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_indonesianbaru;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_jpnnew;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_jpnold;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_kbb;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_kjv;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_kkk;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_portugal;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_reina;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_russiansynodal;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_tagalog;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_tkh;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_web;
import com.good.worshipbible.nos.util.PreferenceUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Search_Activity extends Activity implements OnClickListener,OnItemClickListener, OnScrollListener, OnInitListener, AdViewListener, InterstitialAdListener {
	public SQLiteDatabase mdb;
	private DBOpenHelper_Sub4 favorite_mydb;
	private DBOpenHelper_kkk kkk_db;
	private DBOpenHelper_kbb kbb_db;
	private DBOpenHelper_kjv kjv_db;
	
	private DBOpenHelper_jpnnew jpnnew_db;
	private DBOpenHelper_ckb ckb_db;
	private DBOpenHelper_frenchdarby frenchdarby_db;
	private DBOpenHelper_germanluther germanluther_db;
	private DBOpenHelper_gst gst_db;
	private DBOpenHelper_indonesianbaru indonesianbaru_db;
	private DBOpenHelper_portugal portugal_db;
	private DBOpenHelper_russiansynodal russiansynodal_db;
	private DBOpenHelper_alb alb_db;
	private DBOpenHelper_asv asv_db;
	private DBOpenHelper_avs avs_db;
	private DBOpenHelper_barun barun_db;
	private DBOpenHelper_chb chb_db;
	private DBOpenHelper_chg chg_db;
	private DBOpenHelper_cjb cjb_db;
	private DBOpenHelper_ckc ckc_db;
	private DBOpenHelper_ckg ckg_db;
	private DBOpenHelper_cks cks_db;
	private DBOpenHelper_hebbhs hebbhs_db;
	private DBOpenHelper_hebmod hebmod_db;
	private DBOpenHelper_hebwlc hebwlc_db;
	private DBOpenHelper_indianhindi indianhindi_db;
	private DBOpenHelper_indiantamil indiantamil_db;
	private DBOpenHelper_jpnold jpnold_db;
	private DBOpenHelper_reina reina_db;
	private DBOpenHelper_tagalog tagalog_db;
	private DBOpenHelper_tkh tkh_db;
	private DBOpenHelper_web web_db;
	private Cursor cursor, favorite_cursor;
	public static LinearLayout layout_listview_search , layout_nodata;
	public static  LinearLayout action_layout, layout_bottom;
	public List<Sub1_ColumData>contactsList;
	public Bible_ListAdapter<Sub1_ColumData> bible_Adapter;
	public ListView listview_search;
	public AlertDialog alert;
	int SDK_INT = android.os.Build.VERSION.SDK_INT;
	public static AlertDialog alertDialog;
	public static int bible_type = 0;
	public static int text_color = 0;
	public Context context;
	public static EditText edit_seacher;
	public static String kwon;
	public SharedPreferences settings,pref;
	public static ImageButton bt_search_result, bt_home;
	public Editor edit;
	public static Button Bottom_01, Bottom_02, Bottom_03, Bottom_07;
	public static Button bt_action1, bt_action2, bt_action3, bt_action4;
	public static Button top_01, top_02, top_03, top_04, top_05, top_06, top_07, top_08;
	public static RelativeLayout ad_layout;
	TextToSpeech tts;
	public static InterstitialAd interstialAd;
	private NativeExpressAdView admobNative;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD, "AX00056EB");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD_FULL, "AX00056EC");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMIXER, "u6dbtyd1");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB, "ca-app-pub-4637651494513698/9745545364");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB_FULL, "ca-app-pub-4637651494513698/2222278564");
        context = this;
        if(!PreferenceUtil.getStringSharedData(context, PreferenceUtil.PREF_ISSUBSCRIBED, Const.isSubscribed).equals("true")){
        	addBannerView();    		
    	}
//        init_admob_naive();
        layout_listview_search = (LinearLayout)findViewById(R.id.layout_listview_search);
        layout_nodata = (LinearLayout)findViewById(R.id.layout_nodata);
    	action_layout = (LinearLayout)findViewById(R.id.action_layout);
    	layout_bottom = (LinearLayout)findViewById(R.id.layout_bottom);
    	
    	Bottom_01 = (Button)findViewById(R.id.Bottom_01);
    	Bottom_02 = (Button)findViewById(R.id.Bottom_02);
    	Bottom_03 = (Button)findViewById(R.id.Bottom_03);
    	Bottom_07 = (Button)findViewById(R.id.Bottom_07);
    	bt_action1 = (Button)findViewById(R.id.bt_action1);
    	bt_action2 = (Button)findViewById(R.id.bt_action2);
    	bt_action3 = (Button)findViewById(R.id.bt_action3);
    	bt_action4 = (Button)findViewById(R.id.bt_action4);
    	top_01 = (Button)findViewById(R.id.top_01);
    	top_02 = (Button)findViewById(R.id.top_02);
    	top_03 = (Button)findViewById(R.id.top_03);
    	top_04 = (Button)findViewById(R.id.top_04);
    	top_05 = (Button)findViewById(R.id.top_05);
    	top_06 = (Button)findViewById(R.id.top_06);
    	top_07 = (Button)findViewById(R.id.top_07);
    	top_08 = (Button)findViewById(R.id.top_08);
    	edit_seacher = (EditText)findViewById(R.id.edit_seacher);
    	bt_search_result = (ImageButton)findViewById(R.id.bt_search_result);
    	bt_home = (ImageButton)findViewById(R.id.bt_home);
    	
    	Bottom_03.setOnClickListener(this);
    	Bottom_07.setOnClickListener(this);
    	bt_action1.setOnClickListener(this);
    	bt_action2.setOnClickListener(this);
    	bt_action3.setOnClickListener(this);
    	bt_action4.setOnClickListener(this);
    	top_01.setOnClickListener(this);
    	top_02.setOnClickListener(this);
    	top_03.setOnClickListener(this);
    	top_04.setOnClickListener(this);
    	top_05.setOnClickListener(this);
    	top_06.setOnClickListener(this);
    	top_07.setOnClickListener(this);
    	top_08.setOnClickListener(this);
    	bt_search_result.setOnClickListener(this);
    	bt_home.setOnClickListener(this);
    	
    	pref = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), Activity.MODE_PRIVATE);
    	//language_set
    	bible_type = pref.getInt("bible_type", bible_type);
    	//txtcolor_set
    	text_color = pref.getInt("text_color", text_color);
    	//tts
    	tts = new TextToSpeech(this, this);
    	
    	TelephonyManager telephonymanager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		telephonymanager.listen(new PhoneStateListener() {
			public void onCallStateChanged(int state, String incomingNumber) {
				switch (state) {
				case TelephonyManager.CALL_STATE_IDLE: 
				
				case TelephonyManager.CALL_STATE_OFFHOOK:

				case TelephonyManager.CALL_STATE_RINGING:
					if(tts.isSpeaking()){
        				tts.stop();
        			}
				default: break;
				} 
			}
		}, PhoneStateListener.LISTEN_CALL_STATE);
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
//    	admobNative.destroy();
    	if(kkk_db != null){
    		kkk_db.close();
    	}
    	if(kbb_db != null){
    		kbb_db.close();
    	}
    	if(kjv_db != null){
    		kjv_db.close();
    	}
    	if(jpnnew_db != null){
    		jpnnew_db.close();
    	}
		if(ckb_db != null){
			ckb_db.close();
		}
		if(frenchdarby_db != null){
			frenchdarby_db.close();
		}
		if(germanluther_db != null){
			germanluther_db.close();
		}
		if(gst_db != null){
			gst_db.close();
		}
		if(indonesianbaru_db != null){
			indonesianbaru_db.close();
		}
		if(portugal_db != null){
			portugal_db.close();
		}
		if(russiansynodal_db != null){
			russiansynodal_db.close();
		}
		if(alb_db != null){
			alb_db.close();
		}
		if(asv_db != null){
			asv_db.close();
		}
		if(avs_db != null){
			avs_db.close();
		}
		if(barun_db != null){
			barun_db.close();
		}
		if(chb_db != null){
			chb_db.close();
		}
		if(chg_db != null){
			chg_db.close();
		}
		if(cjb_db != null){
			cjb_db.close();
		}
		if(ckc_db != null){
			ckc_db.close();
		}
		if(ckg_db != null){
			ckg_db.close();
		}
		if(cks_db != null){
			cks_db.close();
		}
		if(hebbhs_db != null){
			hebbhs_db.close();
		}
		if(hebmod_db != null){
			hebmod_db.close();
		}
		if(hebwlc_db != null){
			hebwlc_db.close();
		}
		if(indianhindi_db != null){
			indianhindi_db.close();
		}
		if(indiantamil_db != null){
			indiantamil_db.close();
		}
		if(jpnold_db != null){
			jpnold_db.close();
		}
		if(reina_db != null){
			reina_db.close();
		}
		if(tagalog_db != null){
			tagalog_db.close();
		}
		if(tkh_db != null){
			tkh_db.close();
		}
		if(web_db != null){
			web_db.close();
		}
    	if(favorite_mydb != null){
    		favorite_mydb.close();
    	}
    	if(autoscroll_thread != null){
    		autoscroll_thread.interrupt();
    		autoscroll_thread = null;
    	}
    	if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
    
    @Override
    protected void onRestart() {
    	super.onRestart();
//    	pref = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), Activity.MODE_PRIVATE);
//    	//language_set
//    	bible_type = pref.getInt("bible_type", bible_type);
//    	//txtcolor_set
//    	text_color = pref.getInt("text_color", text_color);
//    	displayList();
    }
    
	public void displayList() {
		pref = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), Activity.MODE_PRIVATE);
    	//language_set
    	bible_type = pref.getInt("bible_type", bible_type);
		
    	contactsList = getContactsList();

    	bible_Adapter = new Bible_ListAdapter<Sub1_ColumData>(
 			   this,R.layout.activity_search_listrow,contactsList, Bottom_01, Bottom_02);
    	listview_search = (ListView)findViewById(R.id.listview_search);
    	listview_search.setOnScrollListener(this);
    	listview_search.setAdapter(bible_Adapter);
    	listview_search.setOnItemClickListener(this);
    	if (SDK_INT >= Build.VERSION_CODES.HONEYCOMB){ //허니콤 버전에서만 실행 가능한 API 사용}
    		listview_search.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    	}
    	listview_search.setItemsCanFocus(false);
    	listview_search.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    	
    	if(listview_search.getCount() == 0){
    		layout_bottom.setVisibility(View.GONE);
    		layout_listview_search.setVisibility(View.GONE);
    		layout_nodata.setVisibility(View.VISIBLE);
    	}else{
    		layout_bottom.setVisibility(View.VISIBLE);
    		layout_nodata.setVisibility(View.GONE);
    		layout_listview_search.setVisibility(View.VISIBLE);
    	}
    }
    
    @Override
	public void onClick(View view) {
    	if(view == bt_search_result){
    		if(edit_seacher.getText().toString().length() < 1){
        		Toast.makeText(this, R.string.search_activity_txt1, Toast.LENGTH_SHORT).show();
        	}else{
        		displayList();
        	}
//    		Log.i("dsu", "검색어 : " + edit_seacher.getText() + "");
    		
    		
    		InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);  
    		inputMethodManager.hideSoftInputFromWindow(edit_seacher.getWindowToken(), 0); 
    	}else if(view == bt_home){
    		onBackPressed();	
    	}else if(view == Bottom_03){
    		if(Bottom_03.isSelected()){
    			Bottom_03.setSelected(false);
    			Bottom_03.setBackgroundResource(R.drawable.btn_03_off);
    			if(autoscroll_thread != null){
    	    		autoscroll_thread.interrupt();
    	    		autoscroll_thread = null;
    	    	}
    		}else{
    			Bottom_03.setSelected(true);
    			Bottom_03.setBackgroundResource(R.drawable.btn_03_on);
    			if(autoscroll_thread == null) autoScrollTask();
    		}
    	}else if(view == Bottom_07){
    		if(!PreferenceUtil.getStringSharedData(context, PreferenceUtil.PREF_ISSUBSCRIBED, Const.isSubscribed).equals("true")){
    			addInterstitialView();    			
    		}
    	}else if(view == bt_action1){
			if(bt_action1.isSelected()){
				bt_action1.setSelected(false);
				bt_action1.setText(context.getString(R.string.txt_sub_action1_1));
				for(int i=0; i < listview_search.getCount(); i++){
					listview_search.setItemChecked(i, false);
				}
				action_layout.setVisibility(View.GONE);
			}else{
				bt_action1.setSelected(true);
				bt_action1.setText(context.getString(R.string.txt_sub_action1_2));
				for(int i=0; i < listview_search.getCount(); i++){
					listview_search.setItemChecked(i, true);
				}
			}
			
		}else if(view == bt_action2){
			String kwon = "";
			String jang = "";
			String jul = "";
			SparseBooleanArray sba = listview_search.getCheckedItemPositions();
			StringBuffer strBuf = new StringBuffer();
			if(sba.size() != 0){
				for(int i=0; i < listview_search.getCount(); i++){
					if(sba.get(i)){
						Sub1_ColumData bible_data = (Sub1_ColumData)bible_Adapter.getItem(i);
						kwon = bible_data.getKwon();
						jang = bible_data.getJang() + context.getString(R.string.txt_jang);
						jul = bible_data.getJul();
						String content = bible_data.getContent();
						if(kwon.equals("1")){
							kwon = context.getString(R.string.txt_kwon_kbb1);
						}else if(kwon.equals("2")){
							kwon = context.getString(R.string.txt_kwon_kbb2);
						}else if(kwon.equals("3")){
							kwon = context.getString(R.string.txt_kwon_kbb3);
						}else if(kwon.equals("4")){
							kwon = context.getString(R.string.txt_kwon_kbb4);
						}else if(kwon.equals("5")){
							kwon = context.getString(R.string.txt_kwon_kbb5);
						}else if(kwon.equals("6")){
							kwon = context.getString(R.string.txt_kwon_kbb6);
						}else if(kwon.equals("7")){
							kwon = context.getString(R.string.txt_kwon_kbb7);
						}else if(kwon.equals("8")){
							kwon = context.getString(R.string.txt_kwon_kbb8);
						}else if(kwon.equals("9")){
							kwon = context.getString(R.string.txt_kwon_kbb9);
						}else if(kwon.equals("10")){
							kwon = context.getString(R.string.txt_kwon_kbb10);
						}else if(kwon.equals("11")){
							kwon = context.getString(R.string.txt_kwon_kbb11);
						}else if(kwon.equals("12")){
							kwon = context.getString(R.string.txt_kwon_kbb12);
						}else if(kwon.equals("13")){
							kwon = context.getString(R.string.txt_kwon_kbb13);
						}else if(kwon.equals("14")){
							kwon = context.getString(R.string.txt_kwon_kbb14);
						}else if(kwon.equals("15")){
							kwon = context.getString(R.string.txt_kwon_kbb15);
						}else if(kwon.equals("16")){
							kwon = context.getString(R.string.txt_kwon_kbb16);
						}else if(kwon.equals("17")){
							kwon = context.getString(R.string.txt_kwon_kbb17);
						}else if(kwon.equals("18")){
							kwon = context.getString(R.string.txt_kwon_kbb18);
						}else if(kwon.equals("19")){
							kwon = context.getString(R.string.txt_kwon_kbb19);
						}else if(kwon.equals("20")){
							kwon = context.getString(R.string.txt_kwon_kbb20);
						}else if(kwon.equals("21")){
							kwon = context.getString(R.string.txt_kwon_kbb21);
						}else if(kwon.equals("22")){
							kwon = context.getString(R.string.txt_kwon_kbb22);
						}else if(kwon.equals("23")){
							kwon = context.getString(R.string.txt_kwon_kbb23);
						}else if(kwon.equals("24")){
							kwon = context.getString(R.string.txt_kwon_kbb24);
						}else if(kwon.equals("25")){
							kwon = context.getString(R.string.txt_kwon_kbb25);
						}else if(kwon.equals("26")){
							kwon = context.getString(R.string.txt_kwon_kbb26);
						}else if(kwon.equals("27")){
							kwon = context.getString(R.string.txt_kwon_kbb27);
						}else if(kwon.equals("28")){
							kwon = context.getString(R.string.txt_kwon_kbb28);
						}else if(kwon.equals("29")){
							kwon = context.getString(R.string.txt_kwon_kbb29);
						}else if(kwon.equals("30")){
							kwon = context.getString(R.string.txt_kwon_kbb30);
						}else if(kwon.equals("31")){
							kwon = context.getString(R.string.txt_kwon_kbb31);
						}else if(kwon.equals("32")){
							kwon = context.getString(R.string.txt_kwon_kbb32);
						}else if(kwon.equals("33")){
							kwon = context.getString(R.string.txt_kwon_kbb33);
						}else if(kwon.equals("34")){
							kwon = context.getString(R.string.txt_kwon_kbb34);
						}else if(kwon.equals("35")){
							kwon = context.getString(R.string.txt_kwon_kbb35);
						}else if(kwon.equals("36")){
							kwon = context.getString(R.string.txt_kwon_kbb36);
						}else if(kwon.equals("37")){
							kwon = context.getString(R.string.txt_kwon_kbb37);
						}else if(kwon.equals("38")){
							kwon = context.getString(R.string.txt_kwon_kbb38);
						}else if(kwon.equals("39")){
							kwon = context.getString(R.string.txt_kwon_kbb39);
						}//구약
						
						//신약
						else if(kwon.equals("40")){
							kwon = context.getString(R.string.txt_kwon_kbb40);
						}else if(kwon.equals("41")){
							kwon = context.getString(R.string.txt_kwon_kbb41);
						}else if(kwon.equals("42")){
							kwon = context.getString(R.string.txt_kwon_kbb42);
						}else if(kwon.equals("43")){
							kwon = context.getString(R.string.txt_kwon_kbb43);
						}else if(kwon.equals("44")){
							kwon = context.getString(R.string.txt_kwon_kbb44);
						}else if(kwon.equals("45")){
							kwon = context.getString(R.string.txt_kwon_kbb45);
						}else if(kwon.equals("46")){
							kwon = context.getString(R.string.txt_kwon_kbb46);
						}else if(kwon.equals("47")){
							kwon = context.getString(R.string.txt_kwon_kbb47);
						}else if(kwon.equals("48")){
							kwon = context.getString(R.string.txt_kwon_kbb48);
						}else if(kwon.equals("49")){
							kwon = context.getString(R.string.txt_kwon_kbb49);
						}else if(kwon.equals("50")){
							kwon = context.getString(R.string.txt_kwon_kbb50);
						}else if(kwon.equals("51")){
							kwon = context.getString(R.string.txt_kwon_kbb51);
						}else if(kwon.equals("52")){
							kwon = context.getString(R.string.txt_kwon_kbb52);
						}else if(kwon.equals("53")){
							kwon = context.getString(R.string.txt_kwon_kbb53);
						}else if(kwon.equals("54")){
							kwon = context.getString(R.string.txt_kwon_kbb54);
						}else if(kwon.equals("55")){
							kwon = context.getString(R.string.txt_kwon_kbb55);
						}else if(kwon.equals("56")){
							kwon = context.getString(R.string.txt_kwon_kbb56);
						}else if(kwon.equals("57")){
							kwon = context.getString(R.string.txt_kwon_kbb57);
						}else if(kwon.equals("58")){
							kwon = context.getString(R.string.txt_kwon_kbb58);
						}else if(kwon.equals("59")){
							kwon = context.getString(R.string.txt_kwon_kbb59);
						}else if(kwon.equals("60")){
							kwon = context.getString(R.string.txt_kwon_kbb60);
						}else if(kwon.equals("61")){
							kwon = context.getString(R.string.txt_kwon_kbb61);
						}else if(kwon.equals("62")){
							kwon = context.getString(R.string.txt_kwon_kbb62);
						}else if(kwon.equals("63")){
							kwon = context.getString(R.string.txt_kwon_kbb63);
						}else if(kwon.equals("64")){
							kwon = context.getString(R.string.txt_kwon_kbb64);
						}else if(kwon.equals("65")){
							kwon = context.getString(R.string.txt_kwon_kbb65);
						}else if(kwon.equals("66")){
							kwon = context.getString(R.string.txt_kwon_kbb66);
						}
						strBuf.append(kwon + jang + jul + " " + content + " ");
						sba = listview_search.getCheckedItemPositions();
					}
				}
				boolean check_DB = false;
				favorite_cursor = favorite_mydb.getReadableDatabase().rawQuery("select * from my_list", null);
				while(favorite_cursor.moveToNext()){
					if((favorite_cursor.getString(favorite_cursor.getColumnIndex("content")).equals(strBuf.toString() + ""))){
						check_DB = true;
						break;
					}
				}
				if(check_DB == false){
					SimpleDateFormat dateFormat = new SimpleDateFormat("y.MM.dd a h:mm:ss");  
					Date date = new Date();
					ContentValues cv = new ContentValues();
					cv.put("kwon", context.getString(R.string.search_activity_txt2));
					cv.put("jang", "\n"+dateFormat.format(date));
					cv.put("content", strBuf.toString() + "");
					favorite_mydb.getWritableDatabase().insert("my_list", null, cv);
					if(favorite_mydb != null) favorite_mydb.close();
					Toast.makeText(Search_Activity.this, R.string.sub1_txt5, Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(Search_Activity.this, R.string.sub1_txt6, Toast.LENGTH_SHORT).show();
				}
			}
		}else if(view == bt_action3){
			try{
				if(bt_action3.isSelected()){
					bt_action3.setSelected(false);
					bt_action3.setText(context.getString(R.string.txt_sub_action3_1));
					if(tts.isSpeaking()){
	    				tts.stop();
	    			}
				}else{
					bt_action3.setSelected(true);
					bt_action3.setText(context.getString(R.string.txt_sub_action3_2));
					SparseBooleanArray sba = listview_search.getCheckedItemPositions();
					StringBuffer strBuf = new StringBuffer();
					if(sba.size() != 0){
						for(int i=0; i < listview_search.getCount(); i++){
							if(sba.get(i)){
								Sub1_ColumData bible_data = (Sub1_ColumData)bible_Adapter.getItem(i);
								String content = bible_data.getContent();
								strBuf.append(content);
								sba = listview_search.getCheckedItemPositions();
							}
						}
						speakOut(strBuf.toString() + "");
					}
				}
			}catch(NullPointerException e){
				
			}
		}else if(view == bt_action4){
			String kwon = "";
			String jang = "";
			String jul = "";
			SparseBooleanArray sba = listview_search.getCheckedItemPositions();
			StringBuffer strBuf = new StringBuffer();
			if(sba.size() != 0){
				for(int i=0; i < listview_search.getCount(); i++){
					if(sba.get(i)){
						Sub1_ColumData bible_data = (Sub1_ColumData)bible_Adapter.getItem(i);
						kwon = bible_data.getKwon();
						jang = bible_data.getJang() + context.getString(R.string.txt_jang);
						jul = bible_data.getJul();
						String content = bible_data.getContent();
						
						if(kwon.equals("1")){
							kwon = context.getString(R.string.txt_kwon_kbb1);
						}else if(kwon.equals("2")){
							kwon = context.getString(R.string.txt_kwon_kbb2);
						}else if(kwon.equals("3")){
							kwon = context.getString(R.string.txt_kwon_kbb3);
						}else if(kwon.equals("4")){
							kwon = context.getString(R.string.txt_kwon_kbb4);
						}else if(kwon.equals("5")){
							kwon = context.getString(R.string.txt_kwon_kbb5);
						}else if(kwon.equals("6")){
							kwon = context.getString(R.string.txt_kwon_kbb6);
						}else if(kwon.equals("7")){
							kwon = context.getString(R.string.txt_kwon_kbb7);
						}else if(kwon.equals("8")){
							kwon = context.getString(R.string.txt_kwon_kbb8);
						}else if(kwon.equals("9")){
							kwon = context.getString(R.string.txt_kwon_kbb9);
						}else if(kwon.equals("10")){
							kwon = context.getString(R.string.txt_kwon_kbb10);
						}else if(kwon.equals("11")){
							kwon = context.getString(R.string.txt_kwon_kbb11);
						}else if(kwon.equals("12")){
							kwon = context.getString(R.string.txt_kwon_kbb12);
						}else if(kwon.equals("13")){
							kwon = context.getString(R.string.txt_kwon_kbb13);
						}else if(kwon.equals("14")){
							kwon = context.getString(R.string.txt_kwon_kbb14);
						}else if(kwon.equals("15")){
							kwon = context.getString(R.string.txt_kwon_kbb15);
						}else if(kwon.equals("16")){
							kwon = context.getString(R.string.txt_kwon_kbb16);
						}else if(kwon.equals("17")){
							kwon = context.getString(R.string.txt_kwon_kbb17);
						}else if(kwon.equals("18")){
							kwon = context.getString(R.string.txt_kwon_kbb18);
						}else if(kwon.equals("19")){
							kwon = context.getString(R.string.txt_kwon_kbb19);
						}else if(kwon.equals("20")){
							kwon = context.getString(R.string.txt_kwon_kbb20);
						}else if(kwon.equals("21")){
							kwon = context.getString(R.string.txt_kwon_kbb21);
						}else if(kwon.equals("22")){
							kwon = context.getString(R.string.txt_kwon_kbb22);
						}else if(kwon.equals("23")){
							kwon = context.getString(R.string.txt_kwon_kbb23);
						}else if(kwon.equals("24")){
							kwon = context.getString(R.string.txt_kwon_kbb24);
						}else if(kwon.equals("25")){
							kwon = context.getString(R.string.txt_kwon_kbb25);
						}else if(kwon.equals("26")){
							kwon = context.getString(R.string.txt_kwon_kbb26);
						}else if(kwon.equals("27")){
							kwon = context.getString(R.string.txt_kwon_kbb27);
						}else if(kwon.equals("28")){
							kwon = context.getString(R.string.txt_kwon_kbb28);
						}else if(kwon.equals("29")){
							kwon = context.getString(R.string.txt_kwon_kbb29);
						}else if(kwon.equals("30")){
							kwon = context.getString(R.string.txt_kwon_kbb30);
						}else if(kwon.equals("31")){
							kwon = context.getString(R.string.txt_kwon_kbb31);
						}else if(kwon.equals("32")){
							kwon = context.getString(R.string.txt_kwon_kbb32);
						}else if(kwon.equals("33")){
							kwon = context.getString(R.string.txt_kwon_kbb33);
						}else if(kwon.equals("34")){
							kwon = context.getString(R.string.txt_kwon_kbb34);
						}else if(kwon.equals("35")){
							kwon = context.getString(R.string.txt_kwon_kbb35);
						}else if(kwon.equals("36")){
							kwon = context.getString(R.string.txt_kwon_kbb36);
						}else if(kwon.equals("37")){
							kwon = context.getString(R.string.txt_kwon_kbb37);
						}else if(kwon.equals("38")){
							kwon = context.getString(R.string.txt_kwon_kbb38);
						}else if(kwon.equals("39")){
							kwon = context.getString(R.string.txt_kwon_kbb39);
						}//구약
						
						//신약
						else if(kwon.equals("40")){
							kwon = context.getString(R.string.txt_kwon_kbb40);
						}else if(kwon.equals("41")){
							kwon = context.getString(R.string.txt_kwon_kbb41);
						}else if(kwon.equals("42")){
							kwon = context.getString(R.string.txt_kwon_kbb42);
						}else if(kwon.equals("43")){
							kwon = context.getString(R.string.txt_kwon_kbb43);
						}else if(kwon.equals("44")){
							kwon = context.getString(R.string.txt_kwon_kbb44);
						}else if(kwon.equals("45")){
							kwon = context.getString(R.string.txt_kwon_kbb45);
						}else if(kwon.equals("46")){
							kwon = context.getString(R.string.txt_kwon_kbb46);
						}else if(kwon.equals("47")){
							kwon = context.getString(R.string.txt_kwon_kbb47);
						}else if(kwon.equals("48")){
							kwon = context.getString(R.string.txt_kwon_kbb48);
						}else if(kwon.equals("49")){
							kwon = context.getString(R.string.txt_kwon_kbb49);
						}else if(kwon.equals("50")){
							kwon = context.getString(R.string.txt_kwon_kbb50);
						}else if(kwon.equals("51")){
							kwon = context.getString(R.string.txt_kwon_kbb51);
						}else if(kwon.equals("52")){
							kwon = context.getString(R.string.txt_kwon_kbb52);
						}else if(kwon.equals("53")){
							kwon = context.getString(R.string.txt_kwon_kbb53);
						}else if(kwon.equals("54")){
							kwon = context.getString(R.string.txt_kwon_kbb54);
						}else if(kwon.equals("55")){
							kwon = context.getString(R.string.txt_kwon_kbb55);
						}else if(kwon.equals("56")){
							kwon = context.getString(R.string.txt_kwon_kbb56);
						}else if(kwon.equals("57")){
							kwon = context.getString(R.string.txt_kwon_kbb57);
						}else if(kwon.equals("58")){
							kwon = context.getString(R.string.txt_kwon_kbb58);
						}else if(kwon.equals("59")){
							kwon = context.getString(R.string.txt_kwon_kbb59);
						}else if(kwon.equals("60")){
							kwon = context.getString(R.string.txt_kwon_kbb60);
						}else if(kwon.equals("61")){
							kwon = context.getString(R.string.txt_kwon_kbb61);
						}else if(kwon.equals("62")){
							kwon = context.getString(R.string.txt_kwon_kbb62);
						}else if(kwon.equals("63")){
							kwon = context.getString(R.string.txt_kwon_kbb63);
						}else if(kwon.equals("64")){
							kwon = context.getString(R.string.txt_kwon_kbb64);
						}else if(kwon.equals("65")){
							kwon = context.getString(R.string.txt_kwon_kbb65);
						}else if(kwon.equals("66")){
							kwon = context.getString(R.string.txt_kwon_kbb66);
						}
						strBuf.append(kwon+ jang + jul + " " +content + " ");
						sba = listview_search.getCheckedItemPositions();
					}
				}
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");    
				intent.addCategory(Intent.CATEGORY_DEFAULT);
//				intent.putExtra(Intent.EXTRA_SUBJECT, title);
				intent.putExtra(Intent.EXTRA_TEXT, strBuf.toString() + "");
//				intent.putExtra(Intent.EXTRA_TITLE, title);
				startActivity(Intent.createChooser(intent, context.getString(R.string.txt_share)));
			}
		}else if(view == top_01){
			Intent intent = new Intent(this, Sub1_Activity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}else if(view == top_02){
			Intent intent = new Intent(this, Sub2_Activity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}else if(view == top_03){
			Intent intent = new Intent(this, Sub3_1_Activity.class);
    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(intent);
		}else if(view == top_04){
			Intent intent = new Intent(this, Sub4_1_Activity.class);
    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(intent);
		}else if(view == top_05){
			Intent intent = new Intent(this, com.good.worshipbible.nos.podcast.Sub5_1_Activity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}else if(view == top_06){
			Intent intent = new Intent(this, com.good.worshipbible.nos.ccm.Sub6_1_Activity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}else if(view == top_07){
			Intent intent = new Intent(this, Sub7_Activity.class);
    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(intent);
		}else if(view == top_08){
			Intent intent = new Intent(this, com.good.worshipbible.nos.favorite.MainActivity.class);
    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(intent);
		}
    }
    
    public static Thread autoscroll_thread = null;
	void autoScrollTask() {
		(autoscroll_thread = new Thread() {			
			public void run() {
				try {
					for (;;) {
						Thread.sleep(100);
						listview_search.smoothScrollBy(3, 100);
						autocsroll_handler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public Handler autocsroll_handler = new Handler() { 
		public void handleMessage(Message msg) {
			if(msg.what == 0){
				
			}
		}
	};
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		int count = totalItemCount - visibleItemCount;
		if(firstVisibleItem >= count && totalItemCount != 0){
			if(autoscroll_thread != null){
	    		autoscroll_thread.interrupt();
	    		autoscroll_thread = null;
	    		Bottom_03.setSelected(false);
	    	}
		}	
	}
	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		
	}
	
	//tts inInit
	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			int result = tts.setLanguage(Locale.US);
			if (result == TextToSpeech.LANG_MISSING_DATA ||
					result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Toast.makeText(this, R.string.sub1_txt4, Toast.LENGTH_SHORT).show();
			}else{
			}
		}else{ 
			Toast.makeText(this, R.string.sub1_txt3, Toast.LENGTH_SHORT).show();
		}
	}
	
	public void speakOut(String content) {
		Toast.makeText(context, context.getString(R.string.txt_tts_ment), Toast.LENGTH_LONG).show();
		char[] temp_content = content.toCharArray();
		int check_language = Character.getType(temp_content[0]);
//		Log.i("dsu", "check_temp" + check_language);
		if(check_language == 5){
			int result = tts.setLanguage(Locale.KOREA);
			if (result == TextToSpeech.LANG_MISSING_DATA ||
					result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Toast.makeText(Search_Activity.this, R.string.sub1_txt4, Toast.LENGTH_SHORT).show();
			}else{
				tts.setLanguage(Locale.KOREA);
			}
		}else{
			int result = tts.setLanguage(Locale.US);
			if (result == TextToSpeech.LANG_MISSING_DATA ||
					result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Toast.makeText(Search_Activity.this, R.string.sub1_txt4, Toast.LENGTH_SHORT).show();
			}else{
				tts.setLanguage(Locale.US);
			}
		}
		tts.setPitch(0.9f);
		tts.setSpeechRate(0.9f);
		tts.speak(content, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
    	contactsList = getContactsList();
    	kwon = contactsList.get(position).getKwon();
    	if(kwon.equals("1")){
			kwon = context.getString(R.string.txt_kwon_kbb1);
		}else if(kwon.equals("2")){
			kwon = context.getString(R.string.txt_kwon_kbb2);
		}else if(kwon.equals("3")){
			kwon = context.getString(R.string.txt_kwon_kbb3);
		}else if(kwon.equals("4")){
			kwon = context.getString(R.string.txt_kwon_kbb4);
		}else if(kwon.equals("5")){
			kwon = context.getString(R.string.txt_kwon_kbb5);
		}else if(kwon.equals("6")){
			kwon = context.getString(R.string.txt_kwon_kbb6);
		}else if(kwon.equals("7")){
			kwon = context.getString(R.string.txt_kwon_kbb7);
		}else if(kwon.equals("8")){
			kwon = context.getString(R.string.txt_kwon_kbb8);
		}else if(kwon.equals("9")){
			kwon = context.getString(R.string.txt_kwon_kbb9);
		}else if(kwon.equals("10")){
			kwon = context.getString(R.string.txt_kwon_kbb10);
		}else if(kwon.equals("11")){
			kwon = context.getString(R.string.txt_kwon_kbb11);
		}else if(kwon.equals("12")){
			kwon = context.getString(R.string.txt_kwon_kbb12);
		}else if(kwon.equals("13")){
			kwon = context.getString(R.string.txt_kwon_kbb13);
		}else if(kwon.equals("14")){
			kwon = context.getString(R.string.txt_kwon_kbb14);
		}else if(kwon.equals("15")){
			kwon = context.getString(R.string.txt_kwon_kbb15);
		}else if(kwon.equals("16")){
			kwon = context.getString(R.string.txt_kwon_kbb16);
		}else if(kwon.equals("17")){
			kwon = context.getString(R.string.txt_kwon_kbb17);
		}else if(kwon.equals("18")){
			kwon = context.getString(R.string.txt_kwon_kbb18);
		}else if(kwon.equals("19")){
			kwon = context.getString(R.string.txt_kwon_kbb19);
		}else if(kwon.equals("20")){
			kwon = context.getString(R.string.txt_kwon_kbb20);
		}else if(kwon.equals("21")){
			kwon = context.getString(R.string.txt_kwon_kbb21);
		}else if(kwon.equals("22")){
			kwon = context.getString(R.string.txt_kwon_kbb22);
		}else if(kwon.equals("23")){
			kwon = context.getString(R.string.txt_kwon_kbb23);
		}else if(kwon.equals("24")){
			kwon = context.getString(R.string.txt_kwon_kbb24);
		}else if(kwon.equals("25")){
			kwon = context.getString(R.string.txt_kwon_kbb25);
		}else if(kwon.equals("26")){
			kwon = context.getString(R.string.txt_kwon_kbb26);
		}else if(kwon.equals("27")){
			kwon = context.getString(R.string.txt_kwon_kbb27);
		}else if(kwon.equals("28")){
			kwon = context.getString(R.string.txt_kwon_kbb28);
		}else if(kwon.equals("29")){
			kwon = context.getString(R.string.txt_kwon_kbb29);
		}else if(kwon.equals("30")){
			kwon = context.getString(R.string.txt_kwon_kbb30);
		}else if(kwon.equals("31")){
			kwon = context.getString(R.string.txt_kwon_kbb31);
		}else if(kwon.equals("32")){
			kwon = context.getString(R.string.txt_kwon_kbb32);
		}else if(kwon.equals("33")){
			kwon = context.getString(R.string.txt_kwon_kbb33);
		}else if(kwon.equals("34")){
			kwon = context.getString(R.string.txt_kwon_kbb34);
		}else if(kwon.equals("35")){
			kwon = context.getString(R.string.txt_kwon_kbb35);
		}else if(kwon.equals("36")){
			kwon = context.getString(R.string.txt_kwon_kbb36);
		}else if(kwon.equals("37")){
			kwon = context.getString(R.string.txt_kwon_kbb37);
		}else if(kwon.equals("38")){
			kwon = context.getString(R.string.txt_kwon_kbb38);
		}else if(kwon.equals("39")){
			kwon = context.getString(R.string.txt_kwon_kbb39);
		}//구약
		
		//신약
		else if(kwon.equals("40")){
			kwon = context.getString(R.string.txt_kwon_kbb40);
		}else if(kwon.equals("41")){
			kwon = context.getString(R.string.txt_kwon_kbb41);
		}else if(kwon.equals("42")){
			kwon = context.getString(R.string.txt_kwon_kbb42);
		}else if(kwon.equals("43")){
			kwon = context.getString(R.string.txt_kwon_kbb43);
		}else if(kwon.equals("44")){
			kwon = context.getString(R.string.txt_kwon_kbb44);
		}else if(kwon.equals("45")){
			kwon = context.getString(R.string.txt_kwon_kbb45);
		}else if(kwon.equals("46")){
			kwon = context.getString(R.string.txt_kwon_kbb46);
		}else if(kwon.equals("47")){
			kwon = context.getString(R.string.txt_kwon_kbb47);
		}else if(kwon.equals("48")){
			kwon = context.getString(R.string.txt_kwon_kbb48);
		}else if(kwon.equals("49")){
			kwon = context.getString(R.string.txt_kwon_kbb49);
		}else if(kwon.equals("50")){
			kwon = context.getString(R.string.txt_kwon_kbb50);
		}else if(kwon.equals("51")){
			kwon = context.getString(R.string.txt_kwon_kbb51);
		}else if(kwon.equals("52")){
			kwon = context.getString(R.string.txt_kwon_kbb52);
		}else if(kwon.equals("53")){
			kwon = context.getString(R.string.txt_kwon_kbb53);
		}else if(kwon.equals("54")){
			kwon = context.getString(R.string.txt_kwon_kbb54);
		}else if(kwon.equals("55")){
			kwon = context.getString(R.string.txt_kwon_kbb55);
		}else if(kwon.equals("56")){
			kwon = context.getString(R.string.txt_kwon_kbb56);
		}else if(kwon.equals("57")){
			kwon = context.getString(R.string.txt_kwon_kbb57);
		}else if(kwon.equals("58")){
			kwon = context.getString(R.string.txt_kwon_kbb58);
		}else if(kwon.equals("59")){
			kwon = context.getString(R.string.txt_kwon_kbb59);
		}else if(kwon.equals("60")){
			kwon = context.getString(R.string.txt_kwon_kbb60);
		}else if(kwon.equals("61")){
			kwon = context.getString(R.string.txt_kwon_kbb61);
		}else if(kwon.equals("62")){
			kwon = context.getString(R.string.txt_kwon_kbb62);
		}else if(kwon.equals("63")){
			kwon = context.getString(R.string.txt_kwon_kbb63);
		}else if(kwon.equals("64")){
			kwon = context.getString(R.string.txt_kwon_kbb64);
		}else if(kwon.equals("65")){
			kwon = context.getString(R.string.txt_kwon_kbb65);
		}else if(kwon.equals("66")){
			kwon = context.getString(R.string.txt_kwon_kbb66);
		}
    	final String jang = contactsList.get(position).getJang();
    	final String jul = contactsList.get(position).getJul();
    	final String content = contactsList.get(position).getContent();

    	int selectd_count = 0;
    	SparseBooleanArray sba = listview_search.getCheckedItemPositions();
		if(sba.size() != 0){
			for(int i = listview_search.getCount() -1; i>=0; i--){
				if(sba.get(i)){
					sba = listview_search.getCheckedItemPositions();
					selectd_count++;
				}
			}
		}
		if(selectd_count == 0){
			action_layout.setVisibility(View.GONE);
		}else{
			action_layout.setVisibility(View.VISIBLE);
		}
		if(bible_Adapter != null){
			bible_Adapter.notifyDataSetChanged();
		}
    }
    
    public List<Sub1_ColumData> getContactsList() {
		try{
			contactsList = new ArrayList<Sub1_ColumData>();
			kkk_db = new DBOpenHelper_kkk(this);
			kbb_db = new DBOpenHelper_kbb(this);
			kjv_db = new DBOpenHelper_kjv(this);
			jpnnew_db = new DBOpenHelper_jpnnew(this);
			ckb_db = new DBOpenHelper_ckb(this);
			frenchdarby_db = new DBOpenHelper_frenchdarby(this);
			germanluther_db = new DBOpenHelper_germanluther(this);
			gst_db = new DBOpenHelper_gst(this);
			indonesianbaru_db = new DBOpenHelper_indonesianbaru(this);
			portugal_db = new DBOpenHelper_portugal(this);
			russiansynodal_db = new DBOpenHelper_russiansynodal(this);
			alb_db = new DBOpenHelper_alb(this);
			asv_db = new DBOpenHelper_asv(this);
			avs_db = new DBOpenHelper_avs(this);
			barun_db = new DBOpenHelper_barun(this);
			chb_db = new DBOpenHelper_chb(this);
			chg_db = new DBOpenHelper_chg(this);
			cjb_db = new DBOpenHelper_cjb(this);
			ckc_db = new DBOpenHelper_ckc(this);
			ckg_db = new DBOpenHelper_ckg(this);
			cks_db = new DBOpenHelper_cks(this);
			hebbhs_db = new DBOpenHelper_hebbhs(this);
			hebmod_db = new DBOpenHelper_hebmod(this);
			hebwlc_db = new DBOpenHelper_hebwlc(this);
			indianhindi_db = new DBOpenHelper_indianhindi(this);
			indiantamil_db = new DBOpenHelper_indiantamil(this);
			jpnold_db = new DBOpenHelper_jpnold(this);
			reina_db = new DBOpenHelper_reina(this);
			tagalog_db = new DBOpenHelper_tagalog(this);
			tkh_db = new DBOpenHelper_tkh(this);
			web_db = new DBOpenHelper_web(this);
			favorite_mydb = new DBOpenHelper_Sub4(this);
			if(bible_type == 0){
				mdb = kkk_db.getReadableDatabase();
			}else if(bible_type == 1){
				mdb = kbb_db.getReadableDatabase();
			}else if(bible_type == 2){
				mdb = kjv_db.getReadableDatabase(); 
			}else if(bible_type == 3){
				mdb = jpnnew_db.getReadableDatabase(); 
			}else if(bible_type == 4){
				mdb = ckb_db.getReadableDatabase(); 
			}else if(bible_type == 5){
				mdb = frenchdarby_db.getReadableDatabase(); 
			}else if(bible_type == 6){
				mdb = germanluther_db.getReadableDatabase(); 
			}else if(bible_type == 7){
				mdb = gst_db.getReadableDatabase(); 
			}else if(bible_type == 8){
				mdb = indonesianbaru_db.getReadableDatabase(); 
			}else if(bible_type == 9){
				mdb = portugal_db.getReadableDatabase(); 
			}else if(bible_type == 10){
				mdb = russiansynodal_db.getReadableDatabase(); 
			}else if(bible_type == 11){
				mdb = alb_db.getReadableDatabase(); 
			}else if(bible_type == 12){
				mdb = asv_db.getReadableDatabase(); 
			}else if(bible_type == 13){
				mdb = avs_db.getReadableDatabase(); 
			}else if(bible_type == 14){
				mdb = barun_db.getReadableDatabase(); 
			}else if(bible_type == 15){
				mdb = chb_db.getReadableDatabase(); 
			}else if(bible_type == 16){
				mdb = chg_db.getReadableDatabase(); 
			}else if(bible_type == 17){
				mdb = cjb_db.getReadableDatabase(); 
			}else if(bible_type == 18){
				mdb = ckc_db.getReadableDatabase(); 
			}else if(bible_type == 19){
				mdb = ckg_db.getReadableDatabase(); 
			}else if(bible_type == 20){
				mdb = cks_db.getReadableDatabase(); 
			}else if(bible_type == 21){
				mdb = hebbhs_db.getReadableDatabase(); 
			}else if(bible_type == 22){
				mdb = hebmod_db.getReadableDatabase(); 
			}else if(bible_type == 23){
				mdb = hebwlc_db.getReadableDatabase(); 
			}else if(bible_type == 24){
				mdb = indianhindi_db.getReadableDatabase(); 
			}else if(bible_type == 25){
				mdb = indiantamil_db.getReadableDatabase(); 
			}else if(bible_type == 26){
				mdb = jpnold_db.getReadableDatabase(); 
			}else if(bible_type == 27){
				mdb = reina_db.getReadableDatabase(); 
			}else if(bible_type == 28){
				mdb = tagalog_db.getReadableDatabase(); 
			}else if(bible_type == 29){
				mdb = tkh_db.getReadableDatabase(); 
			}else if(bible_type == 30){
				mdb = web_db.getReadableDatabase(); 
			}
			cursor = mdb.rawQuery("select * from bible  WHERE content like '%' || '"+edit_seacher.getText().toString() + ""+"' || '%' Order by kwon asc, jang asc, jul asc", null);
//			startManagingCursor(cursor);
			while(cursor.moveToNext()){
				addContact(contactsList,cursor.getString(cursor.getColumnIndex("kwon")),cursor.getString(cursor.getColumnIndex("jang")), cursor.getString(cursor.getColumnIndex("jul")), cursor.getString(cursor.getColumnIndex("content")));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(kkk_db != null) kkk_db.close();
			if(kbb_db != null) kbb_db.close();
			if(kjv_db != null) kjv_db.close();
			if(jpnnew_db != null) jpnnew_db.close();
			if(ckb_db != null) ckb_db.close();
			if(frenchdarby_db != null) frenchdarby_db.close();
			if(germanluther_db != null) germanluther_db.close();
			if(gst_db != null) gst_db.close();
			if(indonesianbaru_db != null) indonesianbaru_db.close();
			if(portugal_db != null) portugal_db.close();
			if(russiansynodal_db != null) russiansynodal_db.close();
			if(alb_db != null) alb_db.close();
			if(asv_db != null) asv_db.close();
			if(avs_db != null) avs_db.close();
			if(barun_db != null) barun_db.close();
			if(chb_db != null) chb_db.close();
			if(chg_db != null) chg_db.close();
			if(cjb_db != null) cjb_db.close();
			if(ckc_db != null) ckc_db.close();
			if(ckg_db != null) ckg_db.close();
			if(cks_db != null) cks_db.close();
			if(hebbhs_db != null) hebbhs_db.close();
			if(hebmod_db != null) hebmod_db.close();
			if(hebwlc_db != null) hebwlc_db.close();
			if(indianhindi_db != null) indianhindi_db.close();
			if(indiantamil_db != null) indiantamil_db.close();
			if(jpnold_db != null) jpnold_db.close();
			if(reina_db != null) reina_db.close();
			if(tagalog_db != null) tagalog_db.close();
			if(tkh_db != null) tkh_db.close();
			if(web_db != null) web_db.close();
		}
		return contactsList;
	}
    
    private void addContact(List<Sub1_ColumData> contactsList,
			String kwon, String jang, String jul, String content) {
//    	Log.i("dsu", "content" + content);
    	contactsList.add(new Sub1_ColumData(kwon, jang, jul, content));
	}
    
	public class Bible_ListAdapter<T extends Sub1_ColumData> extends ArrayAdapter<T> implements OnClickListener{
		   public List<T> contactsList;
		   Button bt_zoom_out, bt_zoom_in;
		   int normal_textSize = 15;
		   public Bible_ListAdapter(Context context, int textViewResourceId, List<T> items, Button bt_zoom_out, Button bt_zoom_in) {
			super(context, textViewResourceId, items);
			contactsList = items;
			this.bt_zoom_out = bt_zoom_out;
			this.bt_zoom_in = bt_zoom_in;
			bt_zoom_out.setOnClickListener(this);
			bt_zoom_in.setOnClickListener(this);
		}
		
		@Override
		public View getView(int position, View view, ViewGroup parent) {
			if(view == null){
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = vi.inflate(R.layout.activity_search_listrow, null);
			}
			
			final T contacts = contactsList.get(position);
			if(contacts != null){
				pref = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), Activity.MODE_PRIVATE);
				normal_textSize = pref.getInt("normal_textSize", 15);
				
				String kwon = contacts.getKwon();
				if(kwon.equals("1")){
					kwon = context.getString(R.string.txt_kwon_kbb1);
				}else if(kwon.equals("2")){
					kwon = context.getString(R.string.txt_kwon_kbb2);
				}else if(kwon.equals("3")){
					kwon = context.getString(R.string.txt_kwon_kbb3);
				}else if(kwon.equals("4")){
					kwon = context.getString(R.string.txt_kwon_kbb4);
				}else if(kwon.equals("5")){
					kwon = context.getString(R.string.txt_kwon_kbb5);
				}else if(kwon.equals("6")){
					kwon = context.getString(R.string.txt_kwon_kbb6);
				}else if(kwon.equals("7")){
					kwon = context.getString(R.string.txt_kwon_kbb7);
				}else if(kwon.equals("8")){
					kwon = context.getString(R.string.txt_kwon_kbb8);
				}else if(kwon.equals("9")){
					kwon = context.getString(R.string.txt_kwon_kbb9);
				}else if(kwon.equals("10")){
					kwon = context.getString(R.string.txt_kwon_kbb10);
				}else if(kwon.equals("11")){
					kwon = context.getString(R.string.txt_kwon_kbb11);
				}else if(kwon.equals("12")){
					kwon = context.getString(R.string.txt_kwon_kbb12);
				}else if(kwon.equals("13")){
					kwon = context.getString(R.string.txt_kwon_kbb13);
				}else if(kwon.equals("14")){
					kwon = context.getString(R.string.txt_kwon_kbb14);
				}else if(kwon.equals("15")){
					kwon = context.getString(R.string.txt_kwon_kbb15);
				}else if(kwon.equals("16")){
					kwon = context.getString(R.string.txt_kwon_kbb16);
				}else if(kwon.equals("17")){
					kwon = context.getString(R.string.txt_kwon_kbb17);
				}else if(kwon.equals("18")){
					kwon = context.getString(R.string.txt_kwon_kbb18);
				}else if(kwon.equals("19")){
					kwon = context.getString(R.string.txt_kwon_kbb19);
				}else if(kwon.equals("20")){
					kwon = context.getString(R.string.txt_kwon_kbb20);
				}else if(kwon.equals("21")){
					kwon = context.getString(R.string.txt_kwon_kbb21);
				}else if(kwon.equals("22")){
					kwon = context.getString(R.string.txt_kwon_kbb22);
				}else if(kwon.equals("23")){
					kwon = context.getString(R.string.txt_kwon_kbb23);
				}else if(kwon.equals("24")){
					kwon = context.getString(R.string.txt_kwon_kbb24);
				}else if(kwon.equals("25")){
					kwon = context.getString(R.string.txt_kwon_kbb25);
				}else if(kwon.equals("26")){
					kwon = context.getString(R.string.txt_kwon_kbb26);
				}else if(kwon.equals("27")){
					kwon = context.getString(R.string.txt_kwon_kbb27);
				}else if(kwon.equals("28")){
					kwon = context.getString(R.string.txt_kwon_kbb28);
				}else if(kwon.equals("29")){
					kwon = context.getString(R.string.txt_kwon_kbb29);
				}else if(kwon.equals("30")){
					kwon = context.getString(R.string.txt_kwon_kbb30);
				}else if(kwon.equals("31")){
					kwon = context.getString(R.string.txt_kwon_kbb31);
				}else if(kwon.equals("32")){
					kwon = context.getString(R.string.txt_kwon_kbb32);
				}else if(kwon.equals("33")){
					kwon = context.getString(R.string.txt_kwon_kbb33);
				}else if(kwon.equals("34")){
					kwon = context.getString(R.string.txt_kwon_kbb34);
				}else if(kwon.equals("35")){
					kwon = context.getString(R.string.txt_kwon_kbb35);
				}else if(kwon.equals("36")){
					kwon = context.getString(R.string.txt_kwon_kbb36);
				}else if(kwon.equals("37")){
					kwon = context.getString(R.string.txt_kwon_kbb37);
				}else if(kwon.equals("38")){
					kwon = context.getString(R.string.txt_kwon_kbb38);
				}else if(kwon.equals("39")){
					kwon = context.getString(R.string.txt_kwon_kbb39);
				}//구약
				
				//신약
				else if(kwon.equals("40")){
					kwon = context.getString(R.string.txt_kwon_kbb40);
				}else if(kwon.equals("41")){
					kwon = context.getString(R.string.txt_kwon_kbb41);
				}else if(kwon.equals("42")){
					kwon = context.getString(R.string.txt_kwon_kbb42);
				}else if(kwon.equals("43")){
					kwon = context.getString(R.string.txt_kwon_kbb43);
				}else if(kwon.equals("44")){
					kwon = context.getString(R.string.txt_kwon_kbb44);
				}else if(kwon.equals("45")){
					kwon = context.getString(R.string.txt_kwon_kbb45);
				}else if(kwon.equals("46")){
					kwon = context.getString(R.string.txt_kwon_kbb46);
				}else if(kwon.equals("47")){
					kwon = context.getString(R.string.txt_kwon_kbb47);
				}else if(kwon.equals("48")){
					kwon = context.getString(R.string.txt_kwon_kbb48);
				}else if(kwon.equals("49")){
					kwon = context.getString(R.string.txt_kwon_kbb49);
				}else if(kwon.equals("50")){
					kwon = context.getString(R.string.txt_kwon_kbb50);
				}else if(kwon.equals("51")){
					kwon = context.getString(R.string.txt_kwon_kbb51);
				}else if(kwon.equals("52")){
					kwon = context.getString(R.string.txt_kwon_kbb52);
				}else if(kwon.equals("53")){
					kwon = context.getString(R.string.txt_kwon_kbb53);
				}else if(kwon.equals("54")){
					kwon = context.getString(R.string.txt_kwon_kbb54);
				}else if(kwon.equals("55")){
					kwon = context.getString(R.string.txt_kwon_kbb55);
				}else if(kwon.equals("56")){
					kwon = context.getString(R.string.txt_kwon_kbb56);
				}else if(kwon.equals("57")){
					kwon = context.getString(R.string.txt_kwon_kbb57);
				}else if(kwon.equals("58")){
					kwon = context.getString(R.string.txt_kwon_kbb58);
				}else if(kwon.equals("59")){
					kwon = context.getString(R.string.txt_kwon_kbb59);
				}else if(kwon.equals("60")){
					kwon = context.getString(R.string.txt_kwon_kbb60);
				}else if(kwon.equals("61")){
					kwon = context.getString(R.string.txt_kwon_kbb61);
				}else if(kwon.equals("62")){
					kwon = context.getString(R.string.txt_kwon_kbb62);
				}else if(kwon.equals("63")){
					kwon = context.getString(R.string.txt_kwon_kbb63);
				}else if(kwon.equals("64")){
					kwon = context.getString(R.string.txt_kwon_kbb64);
				}else if(kwon.equals("65")){
					kwon = context.getString(R.string.txt_kwon_kbb65);
				}else if(kwon.equals("66")){
					kwon = context.getString(R.string.txt_kwon_kbb66);
				}
				
				TextView txt_kwon = (TextView)view.findViewById(R.id.txt_kwon);
				txt_kwon.setText(kwon +" "+ contacts.getJang()+ context.getString(R.string.txt_jang));
				txt_kwon.setTextSize(normal_textSize);
				txt_kwon.setTextColor(Color.DKGRAY);
				
				TextView txt_jul = (TextView)view.findViewById(R.id.txt_jul);
				txt_jul.setText(contacts.getJul());
				txt_jul.setTextSize(normal_textSize);
				
				TextView txt_content = (TextView)view.findViewById(R.id.txt_content);
				txt_content.setTextSize(normal_textSize);
				
				setTextViewColorPartial(txt_content, contacts.getContent(), edit_seacher.getText().toString(), Color.RED);
				
				if(listview_search.isItemChecked(position)){
					view.setBackgroundColor(Color.parseColor("#00a8ec"));
					txt_jul.setTextColor(Color.parseColor("#ffffff"));
					txt_content.setTextColor(Color.parseColor("#ffffff"));
				}else{
					view.setBackgroundColor(Color.parseColor("#00000000"));
					//txtcolorset
					if(text_color == 0){
						txt_jul.setTextColor(Color.parseColor("#000000"));
						txt_content.setTextColor(Color.parseColor("#000000"));
					}else if(text_color == 1){
						txt_jul.setTextColor(Color.parseColor("#a020f0"));
						txt_content.setTextColor(Color.parseColor("#a020f0"));
					}else if(text_color == 2){
						txt_jul.setTextColor(Color.BLUE);
						txt_content.setTextColor(Color.BLUE);
					}else if(text_color == 3){
						txt_jul.setTextColor(Color.CYAN);
						txt_content.setTextColor(Color.CYAN);
					}else if(text_color == 4){
						txt_jul.setTextColor(Color.DKGRAY);
						txt_content.setTextColor(Color.DKGRAY);
					}else if(text_color == 5){
						txt_jul.setTextColor(Color.GRAY);
						txt_content.setTextColor(Color.GRAY);
					}else if(text_color == 6){
						txt_jul.setTextColor(Color.GREEN);
						txt_content.setTextColor(Color.GREEN);
					}else if(text_color == 7){
						txt_jul.setTextColor(Color.LTGRAY);
						txt_content.setTextColor(Color.LTGRAY);
					}else if(text_color == 8){
						txt_jul.setTextColor(Color.MAGENTA);
						txt_content.setTextColor(Color.MAGENTA);
					}else if(text_color == 9){
						txt_jul.setTextColor(Color.RED);
						txt_content.setTextColor(Color.RED);
					}else if(text_color == 10){
						txt_jul.setTextColor(Color.YELLOW);
						txt_content.setTextColor(Color.YELLOW);
					}else if(text_color == 11){
						txt_jul.setTextColor(Color.parseColor("#000080"));
						txt_content.setTextColor(Color.parseColor("#000080"));
					}
				}
			}
			return view;
		}
		
		//Textview 특정색만 바꾸기
		public void setTextViewColorPartial(TextView view, String fulltext, String subtext, int color) {
			try{
				view.setText(fulltext, TextView.BufferType.SPANNABLE);
				Spannable str = (Spannable) view.getText();
				int i = fulltext.indexOf(subtext);
				str.setSpan(new ForegroundColorSpan(color), i, i + subtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}catch (IndexOutOfBoundsException e) {
			}
		}

		@Override
		public void onClick(View view) {
			settings = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), MODE_PRIVATE);
			edit = settings.edit();
			if(view == bt_zoom_out){
				normal_textSize--;
				edit.putInt("normal_textSize", normal_textSize);
				if(bible_Adapter != null) bible_Adapter.notifyDataSetChanged();
			}else if(view == bt_zoom_in){
				normal_textSize++;
				edit.putInt("normal_textSize", normal_textSize);
				if(bible_Adapter != null) bible_Adapter.notifyDataSetChanged();
			}
			edit.commit();
		}
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
	
	public void addInterstitialView() {
    	if(interstialAd == null) {
        	AdInfo adInfo = new AdInfo("u6dbtyd1");
//        	adInfo.setTestMode(false);
        	interstialAd = new InterstitialAd(this);
        	interstialAd.setAdInfo(adInfo, this);
        	interstialAd.setInterstitialAdListener(this);
        	interstialAd.startInterstitial();
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
	
	//** InterstitialAd 이벤트들 *************
	@Override
	public void onInterstitialAdClosed(InterstitialAd arg0) {
//		Log.i("dsu", "전면광고 닫음 : arg0 : " + arg0) ;
		interstialAd = null;
	}

	@Override
	public void onInterstitialAdFailedToReceive(int arg0, String arg1,
			InterstitialAd arg2) {
//		Log.i("dsu", "전면광고 실패 : arg0 : " + arg0+"\n" + arg1) ;
		interstialAd = null;
	}

	@Override
	public void onInterstitialAdReceived(String arg0, InterstitialAd arg1) {
//		Log.i("dsu", "전면광고 성공 : arg0 : " + arg0+"\n" + arg1) ;
		interstialAd = null;
	}	
	
	@Override
	public void onInterstitialAdShown(String arg0, InterstitialAd arg1) {
	}		
	
	@Override
	public void onLeftClicked(String arg0, InterstitialAd arg1) {
	}

	@Override
	public void onRightClicked(String arg0, InterstitialAd arg1) {
	}
	@Override
	 public void onBackPressed() {
		 super.onBackPressed();
	 }
}
