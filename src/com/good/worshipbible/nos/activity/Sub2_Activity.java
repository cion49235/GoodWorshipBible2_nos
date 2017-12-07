package com.good.worshipbible.nos.activity;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.admixer.AdAdapter;
import com.admixer.AdInfo;
import com.admixer.AdMixerManager;
import com.admixer.AdView;
import com.admixer.AdViewListener;
import com.admixer.InterstitialAd;
import com.admixer.InterstitialAdListener;
import com.good.worshipbible.nos.R;
import com.good.worshipbible.nos.ccm.db.helper.VoicePause_DBOpenHelper;
import com.good.worshipbible.nos.data.Sub1_2_ColumData;
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
import com.good.worshipbible.nos.mediaplayer.ContinueMediaPlayer;
import com.good.worshipbible.nos.util.SimpleCrypto;
import com.good.worshipbible.nos.util.TimeUtil;
import com.good.worshipbible.nos.util.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;


public class Sub2_Activity extends Activity implements OnClickListener,OnItemClickListener, OnScrollListener, OnInitListener, AdViewListener, InterstitialAdListener{
	public SQLiteDatabase mdb, mdb2;
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
	private Cursor cursor, favorite_cursor, cursor2;
	public static  LinearLayout action_layout;
	public List<Sub1_ColumData>contactsList;
	public ArrayList<Sub1_2_ColumData>contactsList2;
	public Bible_ListAdapter<Sub1_ColumData> bible_Adapter;
	public ListView listview_sublist;
	public AlertDialog alert;
	int SDK_INT = android.os.Build.VERSION.SDK_INT;
	public static Button bt_kwon, bt_jang;
	public static Button Bottom_01, Bottom_02, Bottom_03, Bottom_04, Bottom_05, Bottom_06, Bottom_07, Bottom_08;
	public static AlertDialog alertDialog;
	public static int bible_type = 0;
	public static int bible2_type = 0;
	public static int text_color = 0;
	public static int audio_speed = 1;
	public String kwon = "40";
	public String jang = "1";
	public int kwon_which = 0;
	public int jang_which = 0;
	public static SharedPreferences settings,pref;
	public static Editor edit;
	public static Context context;
	public static Button bt_action1, bt_action2, bt_action3, bt_action4;
	public static Button top_01, top_02, top_03, top_04, top_05, top_06, top_07, top_08;
	public static RelativeLayout ad_layout;
	public ConnectivityManager connectivityManger;
	public NetworkInfo mobile;
	public NetworkInfo wifi;
	TextToSpeech tts;
	public VoicePlayAsync voicePlayAsync = null;
	public static Handler navigator_handler = new Handler();
	public static TextView max_time, current_time, txt_voice_title ;
	public static ImageButton bt_duration_rew, bt_pause, bt_duration_ffwd;
	public int seekBackwardTime = 5000; // 5000 milliseconds
	public int seekForwardtime = 5000; // 5000 milliseconds
	public int duration_check = 0;
	public static SeekBar mediacontroller_progress;
	public static MediaPlayer mediaPlayer;
	public static int current_position;
	public static VoicePause_DBOpenHelper voicepause_mydb;
	public static LinearLayout layout_progress;
	public static RelativeLayout voice_control_panel_layout;
	public static InterstitialAd interstialAd;
	public static boolean check_db = false;
	private NativeExpressAdView admobNative;
	//40.마태복음
	String[] jang_kbb1 = {"1","2","3","4","5","6","7","8","9","10","11","12","13",
			"14","15","16","17","18","19","20","21","22","23","24","25","26","27",
			"28"};
	//41.마가복음
	String[] jang_kbb2 = {"1","2","3","4","5","6","7","8","9","10","11","12","13",
			"14","15","16"};
	
	//42.누가복음
	String[] jang_kbb3 = {"1","2","3","4","5","6","7","8","9","10","11","12","13",
			"14","15","16","17","18","19","20","21","22","23","24"};
	//43.요한복음
	String[] jang_kbb4 = {"1","2","3","4","5","6","7","8","9","10","11","12","13",
			"14","15","16","17","18","19","20","21"};
	//44.사도행전
	String[] jang_kbb5 = {"1","2","3","4","5","6","7","8","9","10","11","12","13",
			"14","15","16","17","18","19","20","21","22","23","24","25","26","27",
			"28"};
	//45.로마서
	String[] jang_kbb6 = {"1","2","3","4","5","6","7","8","9","10","11","12","13",
			"14","15","16"};
	
	//46.고린도전서
	String[] jang_kbb7 = {"1","2","3","4","5","6","7","8","9","10","11","12","13",
			"14","15","16"};
	//47.고린도후서
	String[] jang_kbb8 = {"1","2","3","4","5","6","7","8","9","10","11","12","13"};
	
	//48.갈라디아서
	String[] jang_kbb9 = {"1","2","3","4","5","6"};
	
	//49.에베소서
	String[] jang_kbb10 = {"1","2","3","4","5","6"};
	
	//50.빌립보서
	String[] jang_kbb11 = {"1","2","3","4"};
	
	//51.골로새서
	String[] jang_kbb12 = {"1","2","3","4"};
	
	//52.데살로니가전서
	String[] jang_kbb13 = {"1","2","3","4","5"};
	
	//53.데살로니가후서
	String[] jang_kbb14 = {"1","2","3"};
	
	//54.디모데전서
	String[] jang_kbb15 = {"1","2","3","4","5","6"};
	
	//55.디모데후서
	String[] jang_kbb16 = {"1","2","3","4"};
	
	//56.디도서
	String[] jang_kbb17 = {"1","2","3"};
	
	//57.빌레몬서
	String[] jang_kbb18 = {"1"};
	
	//58.히브리서
	String[] jang_kbb19 = {"1","2","3","4","5","6","7","8","9","10","11","12","13"};
	
	//59.야고보서
	String[] jang_kbb20 = {"1","2","3","4","5"};
	
	//60.베드로전서
	String[] jang_kbb21 = {"1","2","3","4","5"};
	
	//61.베드로후서
	String[] jang_kbb22 = {"1","2","3"};
	
	//62.요한일서
	String[] jang_kbb23 = {"1","2","3","4","5"};
	
	//63.요한이서
	String[] jang_kbb24 = {"1"};
	
	//64.요한삼서
	String[] jang_kbb25 = {"1"};
	
	//65.유다서
	String[] jang_kbb26 = {"1"};
	
	//66.요한계시록
	String[] jang_kbb27 = {"1","2","3","4","5","6","7","8","9","10","11","12","13",
			"14","15","16","17","18","19","20","21","22"};
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub2);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD, "AX00056EB");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD_FULL, "AX00056EC");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMIXER, "u6dbtyd1");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB, "ca-app-pub-4637651494513698/9745545364");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB_FULL, "ca-app-pub-4637651494513698/2222278564");
        context = this;
        voice_control_panel_layout = (RelativeLayout)findViewById(R.id.voice_control_panel_layout);
        try{
        	String language = context.getResources().getConfiguration().locale.toString();
        	if(language.equals("ko_KR")){
        		voice_control_panel_layout.setVisibility(View.VISIBLE);
        	}else{
        		voice_control_panel_layout.setVisibility(View.GONE);
        	}
        }catch(Exception e){
        }
//        mediaPlayer = new MediaPlayer();
        voicepause_mydb = new VoicePause_DBOpenHelper(this);
        addBannerView();
//        init_admob_naive();
        String[] kwon_kbb = {context.getString(R.string.txt_kwon_kbb40),
        		context.getString(R.string.txt_kwon_kbb41),
        		context.getString(R.string.txt_kwon_kbb42),
        		context.getString(R.string.txt_kwon_kbb43),
        		context.getString(R.string.txt_kwon_kbb44),
        		context.getString(R.string.txt_kwon_kbb45),
        		context.getString(R.string.txt_kwon_kbb46),
        		context.getString(R.string.txt_kwon_kbb47),
        		context.getString(R.string.txt_kwon_kbb48),
        		context.getString(R.string.txt_kwon_kbb49),
        		context.getString(R.string.txt_kwon_kbb50),
        		context.getString(R.string.txt_kwon_kbb51),
        		context.getString(R.string.txt_kwon_kbb52),
        		context.getString(R.string.txt_kwon_kbb53),
        		context.getString(R.string.txt_kwon_kbb54),
        		context.getString(R.string.txt_kwon_kbb55),
        		context.getString(R.string.txt_kwon_kbb56),
        		context.getString(R.string.txt_kwon_kbb57),
        		context.getString(R.string.txt_kwon_kbb58),
        		context.getString(R.string.txt_kwon_kbb59),
        		context.getString(R.string.txt_kwon_kbb60),
        		context.getString(R.string.txt_kwon_kbb61),
        		context.getString(R.string.txt_kwon_kbb62),
        		context.getString(R.string.txt_kwon_kbb63),
        		context.getString(R.string.txt_kwon_kbb64),
        		context.getString(R.string.txt_kwon_kbb65),
        		context.getString(R.string.txt_kwon_kbb66)};

        action_layout = (LinearLayout)findViewById(R.id.action_layout);
    	bt_kwon = (Button)findViewById(R.id.bt_kwon);
    	bt_jang = (Button)findViewById(R.id.bt_jang);
    	Bottom_01 = (Button)findViewById(R.id.Bottom_01);
    	Bottom_02 = (Button)findViewById(R.id.Bottom_02);
    	Bottom_03 = (Button)findViewById(R.id.Bottom_03);
    	Bottom_04 = (Button)findViewById(R.id.Bottom_04);
    	Bottom_05 = (Button)findViewById(R.id.Bottom_05);
    	Bottom_06 = (Button)findViewById(R.id.Bottom_06);
    	Bottom_07 = (Button)findViewById(R.id.Bottom_07);
    	Bottom_08 = (Button)findViewById(R.id.Bottom_08);
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
    	
    	layout_progress = (LinearLayout)findViewById(R.id.layout_progress);
    	mediacontroller_progress = (SeekBar)findViewById(R.id.mediacontroller_progress);
    	max_time = (TextView)findViewById(R.id.max_time);
    	current_time = (TextView)findViewById(R.id.current_time);
    	txt_voice_title = (TextView)findViewById(R.id.txt_voice_title);
    	
    	bt_duration_rew = (ImageButton)findViewById(R.id.bt_duration_rew); 
    	bt_pause = (ImageButton)findViewById(R.id.bt_pause); 
    	bt_duration_ffwd = (ImageButton)findViewById(R.id.bt_duration_ffwd); 
    	bt_duration_rew.setOnClickListener(this);
    	bt_pause.setOnClickListener(this);
    	bt_duration_ffwd.setOnClickListener(this);
    	
    	bt_kwon.setOnClickListener(this);
    	bt_jang.setOnClickListener(this);
    	Bottom_03.setOnClickListener(this);
    	Bottom_04.setOnClickListener(this);
    	Bottom_05.setOnClickListener(this);
    	Bottom_06.setOnClickListener(this);
    	Bottom_07.setOnClickListener(this);
    	Bottom_08.setOnClickListener(this);
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
    	
    	pref = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), Activity.MODE_PRIVATE);
    	kwon = pref.getString("kwon2", "40");
    	jang = pref.getString("jang2", "1");
    	kwon_which = pref.getInt("kwon_which2", 0);
    	jang_which = pref.getInt("jang_which2", 0);
    	
    	//language_set
    	bible_type = pref.getInt("bible_type", bible_type);
    	bible2_type = pref.getInt("bible2_type", bible2_type);
    	//txtcolor_set
    	text_color = pref.getInt("text_color", text_color);
    	audio_speed = pref.getInt("audio_speed", audio_speed);
    	
    	bt_kwon.setText(kwon_kbb[kwon_which]);
    	bt_jang.setText(jang_kbb1[jang_which] + context.getString(R.string.txt_jang));
//    	Log.i("dsu", "kwon==>" + kwon);
    	
    	//TTS Init
    	tts = new TextToSpeech(this, this);
    	displayList();
    	
    	TelephonyManager telephonymanager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		telephonymanager.listen(new PhoneStateListener() {
			public void onCallStateChanged(int state, String incomingNumber) {
				switch (state) {
				case TelephonyManager.CALL_STATE_IDLE: 
				
				case TelephonyManager.CALL_STATE_OFFHOOK:
					if ((mediaPlayer != null) && (mediaPlayer.isPlaying())){
						mediaPlayer.pause();
						bt_pause.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_media_play_small));
					}
				case TelephonyManager.CALL_STATE_RINGING:
					if ((mediaPlayer != null) && (mediaPlayer.isPlaying())){
						onPause_DB_Voice();
						mediaPlayer.pause();
						bt_pause.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_media_play_small));
					}
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
		if(voicepause_mydb != null){
			voicepause_mydb.close();	
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
//    	voice_play_stop();
    }
	
	@Override
	protected void onRestart() {
		super.onRestart();
		pref = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), Activity.MODE_PRIVATE);
    	//language_set
    	bible_type = pref.getInt("bible_type", bible_type);
    	//txtcolor_set
    	text_color = pref.getInt("text_color", text_color);
    	audio_speed = pref.getInt("audio_speed", audio_speed);
		displayList();
		action_layout.setVisibility(View.GONE);
	}
    
	public void displayList() {
		pref = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), Activity.MODE_PRIVATE);
    	//language_set
    	bible_type = pref.getInt("bible_type", bible_type);
    	bible2_type = pref.getInt("bible2_type", bible2_type);
    	
    	contactsList = getContactsList();
    	if(bible2_type != 0){
    		contactsList2 = getContactsList2();
    	}
    	bible_Adapter = new Bible_ListAdapter<Sub1_ColumData>(
 			   this,R.layout.activity_sub2_listrow,contactsList,contactsList2, Bottom_01, Bottom_02);
    	listview_sublist = (ListView)findViewById(R.id.listview_sublist);
    	listview_sublist.setOnScrollListener(this);
    	listview_sublist.setAdapter(bible_Adapter);
    	listview_sublist.setOnItemClickListener(this);
    	if (SDK_INT >= Build.VERSION_CODES.HONEYCOMB){ //허니콤 버전에서만 실행 가능한 API 사용}
    		listview_sublist.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    	}
    	listview_sublist.setItemsCanFocus(false);
    	listview_sublist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    	action_layout.setVisibility(View.GONE);
    }
    
    @Override
	public void onClick(View view) {
    	final String[] kwon_kbb = {context.getString(R.string.txt_kwon_kbb40),
        		context.getString(R.string.txt_kwon_kbb41),
        		context.getString(R.string.txt_kwon_kbb42),
        		context.getString(R.string.txt_kwon_kbb43),
        		context.getString(R.string.txt_kwon_kbb44),
        		context.getString(R.string.txt_kwon_kbb45),
        		context.getString(R.string.txt_kwon_kbb46),
        		context.getString(R.string.txt_kwon_kbb47),
        		context.getString(R.string.txt_kwon_kbb48),
        		context.getString(R.string.txt_kwon_kbb49),
        		context.getString(R.string.txt_kwon_kbb50),
        		context.getString(R.string.txt_kwon_kbb51),
        		context.getString(R.string.txt_kwon_kbb52),
        		context.getString(R.string.txt_kwon_kbb53),
        		context.getString(R.string.txt_kwon_kbb54),
        		context.getString(R.string.txt_kwon_kbb55),
        		context.getString(R.string.txt_kwon_kbb56),
        		context.getString(R.string.txt_kwon_kbb57),
        		context.getString(R.string.txt_kwon_kbb58),
        		context.getString(R.string.txt_kwon_kbb59),
        		context.getString(R.string.txt_kwon_kbb60),
        		context.getString(R.string.txt_kwon_kbb61),
        		context.getString(R.string.txt_kwon_kbb62),
        		context.getString(R.string.txt_kwon_kbb63),
        		context.getString(R.string.txt_kwon_kbb64),
        		context.getString(R.string.txt_kwon_kbb65),
        		context.getString(R.string.txt_kwon_kbb66)};
    	if(view == bt_kwon){
    		settings = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), MODE_PRIVATE);
			edit = settings.edit();
			
			pref = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), Activity.MODE_PRIVATE);
	    	kwon_which = pref.getInt("kwon_which2", 0);
    		alertDialog = new AlertDialog.Builder(this)
    			.setIcon( R.drawable.icon64)
    			.setTitle(context.getString(R.string.txt_bt_kwon2))
    			.setSingleChoiceItems(kwon_kbb, kwon_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					dialog.dismiss();
    					if(which == 0){
    						kwon = "40";
    						edit.putInt("kwon_which2", 0);
    						edit.putString("kwon2", kwon);
    					}else if(which == 1){
    						kwon = "41";
    						edit.putInt("kwon_which2", 1);
    						edit.putString("kwon2", kwon);
    					}else if(which == 2){
    						kwon = "42";
    						edit.putInt("kwon_which2", 2);
    						edit.putString("kwon2", kwon);
    					}else if(which == 3){
    						kwon = "43";
    						edit.putInt("kwon_which2", 3);
    						edit.putString("kwon2", kwon);
    					}else if(which == 4){
    						kwon = "44";
    						edit.putInt("kwon_which2", 4);
    						edit.putString("kwon2", kwon);
    					}else if(which == 5){
    						kwon = "45";
    						edit.putInt("kwon_which2", 5);
    						edit.putString("kwon2", kwon);
    					}else if(which == 6){
    						kwon = "46";
    						edit.putInt("kwon_which2", 6);
    						edit.putString("kwon2", kwon);
    					}else if(which == 7){
    						kwon = "47";
    						edit.putInt("kwon_which2", 7);
    						edit.putString("kwon2", kwon);
    					}else if(which == 8){
    						kwon = "48";
    						edit.putInt("kwon_which2", 8);
    						edit.putString("kwon2", kwon);
    					}else if(which == 9){
    						kwon = "49";
    						edit.putInt("kwon_which2", 9);
    						edit.putString("kwon2", kwon);
    					}else if(which == 10){
    						kwon = "50";
    						edit.putInt("kwon_which2", 10);
    						edit.putString("kwon2", kwon);
    					}else if(which == 11){
    						kwon = "51";
    						edit.putInt("kwon_which2", 11);
    						edit.putString("kwon2", kwon);
    					}else if(which == 12){
    						kwon = "52";
    						edit.putInt("kwon_which2", 12);
    						edit.putString("kwon2", kwon);
    					}else if(which == 13){
    						kwon = "53";
    						edit.putInt("kwon_which2", 13);
    						edit.putString("kwon2", kwon);
    					}else if(which == 14){
    						kwon = "54";
    						edit.putInt("kwon_which2", 14);
    						edit.putString("kwon2", kwon);
    					}else if(which == 15){
    						kwon = "55";
    						edit.putInt("kwon_which2", 15);
    						edit.putString("kwon2", kwon);
    					}else if(which == 16){
    						kwon = "56";
    						edit.putInt("kwon_which2", 16);
    						edit.putString("kwon2", kwon);
    					}else if(which == 17){
    						kwon = "57";
    						edit.putInt("kwon_which2", 17);
    						edit.putString("kwon2", kwon);
    					}else if(which == 18){
    						kwon = "58";
    						edit.putInt("kwon_which2", 18);
    						edit.putString("kwon2", kwon);
    					}else if(which == 19){
    						kwon = "59";
    						edit.putInt("kwon_which2", 19);
    						edit.putString("kwon2", kwon);
    					}else if(which == 20){
    						kwon = "60";
    						edit.putInt("kwon_which2", 20);
    						edit.putString("kwon2", kwon);
    					}else if(which == 21){
    						kwon = "61";
    						edit.putInt("kwon_which2", 21);
    						edit.putString("kwon2", kwon);
    					}else if(which == 22){
    						kwon = "62";
    						edit.putInt("kwon_which2", 22);
    						edit.putString("kwon2", kwon);
    					}else if(which == 23){
    						kwon = "63";
    						edit.putInt("kwon_which2", 23);
    						edit.putString("kwon2", kwon);
    					}else if(which == 24){
    						kwon = "64";
    						edit.putInt("kwon_which2", 24);
    						edit.putString("kwon2", kwon);
    					}else if(which == 25){
    						kwon = "65";
    						edit.putInt("kwon_which2", 25);
    						edit.putString("kwon2", kwon);
    					}else if(which == 26){
    						kwon = "66";
    						edit.putInt("kwon_which2", 26);
    						edit.putString("kwon2", kwon);
    					}
    					bt_kwon.setText(kwon_kbb[which]);
    					bt_jang.setText(jang_kbb20[0] + context.getString(R.string.txt_jang));
    					jang = "1";
    					edit.putInt("jang_which2", 0);
						edit.putString("jang2", jang);
    					edit.commit();
    					
    					displayList();
    				}
    			}).show();
    	}else if(view == bt_jang){
    		settings = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), MODE_PRIVATE);
			edit = settings.edit();
			pref = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), Activity.MODE_PRIVATE);
			jang_which = pref.getInt("jang_which2", 0);
			if(kwon.equals("40")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb1, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}else if(which == 3){
    						jang = "4";
    						edit.putInt("jang_which2", 3);
    						edit.putString("jang2", jang);
    					}else if(which == 4){
    						jang = "5";
    						edit.putInt("jang_which2", 4);
    						edit.putString("jang2", jang);
    					}else if(which == 5){
    						jang = "6";
    						edit.putInt("jang_which2", 5);
    						edit.putString("jang2", jang);
    					}else if(which == 6){
    						jang = "7";
    						edit.putInt("jang_which2", 6);
    						edit.putString("jang2", jang);
    					}else if(which == 7){
    						jang = "8";
    						edit.putInt("jang_which2", 7);
    						edit.putString("jang2", jang);
    					}else if(which == 8){
    						jang = "9";
    						edit.putInt("jang_which2", 8);
    						edit.putString("jang2", jang);
    					}else if(which == 9){
    						jang = "10";
    						edit.putInt("jang_which2", 9);
    						edit.putString("jang2", jang);
    					}else if(which == 10){
    						jang = "11";
    						edit.putInt("jang_which2", 10);
    						edit.putString("jang2", jang);
    					}else if(which == 11){
    						jang = "12";
    						edit.putInt("jang_which2", 11);
    						edit.putString("jang2", jang);
    					}else if(which == 12){
    						jang = "13";
    						edit.putInt("jang_which2", 12);
    						edit.putString("jang2", jang);
    					}else if(which == 13){
    						jang = "14";
    						edit.putInt("jang_which2", 13);
    						edit.putString("jang2", jang);
    					}else if(which == 14){
    						jang = "15";
    						edit.putInt("jang_which2", 14);
    						edit.putString("jang2", jang);
    					}else if(which == 15){
    						jang = "16";
    						edit.putInt("jang_which2", 15);
    						edit.putString("jang2", jang);
    					}else if(which == 16){
    						jang = "17";
    						edit.putInt("jang_which2", 16);
    						edit.putString("jang2", jang);
    					}else if(which == 17){
    						jang = "18";
    						edit.putInt("jang_which2", 17);
    						edit.putString("jang2", jang);
    					}else if(which == 18){
    						jang = "19";
    						edit.putInt("jang_which2", 18);
    						edit.putString("jang2", jang);
    					}else if(which == 19){
    						jang = "20";
    						edit.putInt("jang_which2", 19);
    						edit.putString("jang2", jang);
    					}else if(which == 20){
    						jang = "21";
    						edit.putInt("jang_which2", 20);
    						edit.putString("jang2", jang);
    					}else if(which == 21){
    						jang = "22";
    						edit.putInt("jang_which2", 21);
    						edit.putString("jang2", jang);
    					}else if(which == 22){
    						jang = "23";
    						edit.putInt("jang_which2", 22);
    						edit.putString("jang2", jang);
    					}else if(which == 23){
    						jang = "24";
    						edit.putInt("jang_which2", 23);
    						edit.putString("jang2", jang);
    					}else if(which == 24){
    						jang = "25";
    						edit.putInt("jang_which2", 24);
    						edit.putString("jang2", jang);
    					}else if(which == 25){
    						jang = "26";
    						edit.putInt("jang_which2", 25);
    						edit.putString("jang2", jang);
    					}else if(which == 26){
    						jang = "27";
    						edit.putInt("jang_which2", 26);
    						edit.putString("jang2", jang);
    					}else if(which == 27){
    						jang = "28";
    						edit.putInt("jang_which2", 27);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb1[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
			}else if(kwon.equals("41")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb2, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}else if(which == 3){
    						jang = "4";
    						edit.putInt("jang_which2", 3);
    						edit.putString("jang2", jang);
    					}else if(which == 4){
    						jang = "5";
    						edit.putInt("jang_which2", 4);
    						edit.putString("jang2", jang);
    					}else if(which == 5){
    						jang = "6";
    						edit.putInt("jang_which2", 5);
    						edit.putString("jang2", jang);
    					}else if(which == 6){
    						jang = "7";
    						edit.putInt("jang_which2", 6);
    						edit.putString("jang2", jang);
    					}else if(which == 7){
    						jang = "8";
    						edit.putInt("jang_which2", 7);
    						edit.putString("jang2", jang);
    					}else if(which == 8){
    						jang = "9";
    						edit.putInt("jang_which2", 8);
    						edit.putString("jang2", jang);
    					}else if(which == 9){
    						jang = "10";
    						edit.putInt("jang_which2", 9);
    						edit.putString("jang2", jang);
    					}else if(which == 10){
    						jang = "11";
    						edit.putInt("jang_which2", 10);
    						edit.putString("jang2", jang);
    					}else if(which == 11){
    						jang = "12";
    						edit.putInt("jang_which2", 11);
    						edit.putString("jang2", jang);
    					}else if(which == 12){
    						jang = "13";
    						edit.putInt("jang_which2", 12);
    						edit.putString("jang2", jang);
    					}else if(which == 13){
    						jang = "14";
    						edit.putInt("jang_which2", 13);
    						edit.putString("jang2", jang);
    					}else if(which == 14){
    						jang = "15";
    						edit.putInt("jang_which2", 14);
    						edit.putString("jang2", jang);
    					}else if(which == 15){
    						jang = "16";
    						edit.putInt("jang_which2", 15);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb2[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
			}else if(kwon.equals("42")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb3, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}else if(which == 3){
    						jang = "4";
    						edit.putInt("jang_which2", 3);
    						edit.putString("jang2", jang);
    					}else if(which == 4){
    						jang = "5";
    						edit.putInt("jang_which2", 4);
    						edit.putString("jang2", jang);
    					}else if(which == 5){
    						jang = "6";
    						edit.putInt("jang_which2", 5);
    						edit.putString("jang2", jang);
    					}else if(which == 6){
    						jang = "7";
    						edit.putInt("jang_which2", 6);
    						edit.putString("jang2", jang);
    					}else if(which == 7){
    						jang = "8";
    						edit.putInt("jang_which2", 7);
    						edit.putString("jang2", jang);
    					}else if(which == 8){
    						jang = "9";
    						edit.putInt("jang_which2", 8);
    						edit.putString("jang2", jang);
    					}else if(which == 9){
    						jang = "10";
    						edit.putInt("jang_which2", 9);
    						edit.putString("jang2", jang);
    					}else if(which == 10){
    						jang = "11";
    						edit.putInt("jang_which2", 10);
    						edit.putString("jang2", jang);
    					}else if(which == 11){
    						jang = "12";
    						edit.putInt("jang_which2", 11);
    						edit.putString("jang2", jang);
    					}else if(which == 12){
    						jang = "13";
    						edit.putInt("jang_which2", 12);
    						edit.putString("jang2", jang);
    					}else if(which == 13){
    						jang = "14";
    						edit.putInt("jang_which2", 13);
    						edit.putString("jang2", jang);
    					}else if(which == 14){
    						jang = "15";
    						edit.putInt("jang_which2", 14);
    						edit.putString("jang2", jang);
    					}else if(which == 15){
    						jang = "16";
    						edit.putInt("jang_which2", 15);
    						edit.putString("jang2", jang);
    					}else if(which == 16){
    						jang = "17";
    						edit.putInt("jang_which2", 16);
    						edit.putString("jang2", jang);
    					}else if(which == 17){
    						jang = "18";
    						edit.putInt("jang_which2", 17);
    						edit.putString("jang2", jang);
    					}else if(which == 18){
    						jang = "19";
    						edit.putInt("jang_which2", 18);
    						edit.putString("jang2", jang);
    					}else if(which == 19){
    						jang = "20";
    						edit.putInt("jang_which2", 19);
    						edit.putString("jang2", jang);
    					}else if(which == 20){
    						jang = "21";
    						edit.putInt("jang_which2", 20);
    						edit.putString("jang2", jang);
    					}else if(which == 21){
    						jang = "22";
    						edit.putInt("jang_which2", 21);
    						edit.putString("jang2", jang);
    					}else if(which == 22){
    						jang = "23";
    						edit.putInt("jang_which2", 22);
    						edit.putString("jang2", jang);
    					}else if(which == 23){
    						jang = "24";
    						edit.putInt("jang_which2", 23);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb3[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
			}else if(kwon.equals("43")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb4, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}else if(which == 3){
    						jang = "4";
    						edit.putInt("jang_which2", 3);
    						edit.putString("jang2", jang);
    					}else if(which == 4){
    						jang = "5";
    						edit.putInt("jang_which2", 4);
    						edit.putString("jang2", jang);
    					}else if(which == 5){
    						jang = "6";
    						edit.putInt("jang_which2", 5);
    						edit.putString("jang2", jang);
    					}else if(which == 6){
    						jang = "7";
    						edit.putInt("jang_which2", 6);
    						edit.putString("jang2", jang);
    					}else if(which == 7){
    						jang = "8";
    						edit.putInt("jang_which2", 7);
    						edit.putString("jang2", jang);
    					}else if(which == 8){
    						jang = "9";
    						edit.putInt("jang_which2", 8);
    						edit.putString("jang2", jang);
    					}else if(which == 9){
    						jang = "10";
    						edit.putInt("jang_which2", 9);
    						edit.putString("jang2", jang);
    					}else if(which == 10){
    						jang = "11";
    						edit.putInt("jang_which2", 10);
    						edit.putString("jang2", jang);
    					}else if(which == 11){
    						jang = "12";
    						edit.putInt("jang_which2", 11);
    						edit.putString("jang2", jang);
    					}else if(which == 12){
    						jang = "13";
    						edit.putInt("jang_which2", 12);
    						edit.putString("jang2", jang);
    					}else if(which == 13){
    						jang = "14";
    						edit.putInt("jang_which2", 13);
    						edit.putString("jang2", jang);
    					}else if(which == 14){
    						jang = "15";
    						edit.putInt("jang_which2", 14);
    						edit.putString("jang2", jang);
    					}else if(which == 15){
    						jang = "16";
    						edit.putInt("jang_which2", 15);
    						edit.putString("jang2", jang);
    					}else if(which == 16){
    						jang = "17";
    						edit.putInt("jang_which2", 16);
    						edit.putString("jang2", jang);
    					}else if(which == 17){
    						jang = "18";
    						edit.putInt("jang_which2", 17);
    						edit.putString("jang2", jang);
    					}else if(which == 18){
    						jang = "19";
    						edit.putInt("jang_which2", 18);
    						edit.putString("jang2", jang);
    					}else if(which == 19){
    						jang = "20";
    						edit.putInt("jang_which2", 19);
    						edit.putString("jang2", jang);
    					}else if(which == 20){
    						jang = "21";
    						edit.putInt("jang_which2", 20);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb4[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
			}else if(kwon.equals("44")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb5, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}else if(which == 3){
    						jang = "4";
    						edit.putInt("jang_which2", 3);
    						edit.putString("jang2", jang);
    					}else if(which == 4){
    						jang = "5";
    						edit.putInt("jang_which2", 4);
    						edit.putString("jang2", jang);
    					}else if(which == 5){
    						jang = "6";
    						edit.putInt("jang_which2", 5);
    						edit.putString("jang2", jang);
    					}else if(which == 6){
    						jang = "7";
    						edit.putInt("jang_which2", 6);
    						edit.putString("jang2", jang);
    					}else if(which == 7){
    						jang = "8";
    						edit.putInt("jang_which2", 7);
    						edit.putString("jang2", jang);
    					}else if(which == 8){
    						jang = "9";
    						edit.putInt("jang_which2", 8);
    						edit.putString("jang2", jang);
    					}else if(which == 9){
    						jang = "10";
    						edit.putInt("jang_which2", 9);
    						edit.putString("jang2", jang);
    					}else if(which == 10){
    						jang = "11";
    						edit.putInt("jang_which2", 10);
    						edit.putString("jang2", jang);
    					}else if(which == 11){
    						jang = "12";
    						edit.putInt("jang_which2", 11);
    						edit.putString("jang2", jang);
    					}else if(which == 12){
    						jang = "13";
    						edit.putInt("jang_which2", 12);
    						edit.putString("jang2", jang);
    					}else if(which == 13){
    						jang = "14";
    						edit.putInt("jang_which2", 13);
    						edit.putString("jang2", jang);
    					}else if(which == 14){
    						jang = "15";
    						edit.putInt("jang_which2", 14);
    						edit.putString("jang2", jang);
    					}else if(which == 15){
    						jang = "16";
    						edit.putInt("jang_which2", 15);
    						edit.putString("jang2", jang);
    					}else if(which == 16){
    						jang = "17";
    						edit.putInt("jang_which2", 16);
    						edit.putString("jang2", jang);
    					}else if(which == 17){
    						jang = "18";
    						edit.putInt("jang_which2", 17);
    						edit.putString("jang2", jang);
    					}else if(which == 18){
    						jang = "19";
    						edit.putInt("jang_which2", 18);
    						edit.putString("jang2", jang);
    					}else if(which == 19){
    						jang = "20";
    						edit.putInt("jang_which2", 19);
    						edit.putString("jang2", jang);
    					}else if(which == 20){
    						jang = "21";
    						edit.putInt("jang_which2", 20);
    						edit.putString("jang2", jang);
    					}else if(which == 21){
    						jang = "22";
    						edit.putInt("jang_which2", 21);
    						edit.putString("jang2", jang);
    					}else if(which == 22){
    						jang = "23";
    						edit.putInt("jang_which2", 22);
    						edit.putString("jang2", jang);
    					}else if(which == 23){
    						jang = "24";
    						edit.putInt("jang_which2", 23);
    						edit.putString("jang2", jang);
    					}else if(which == 24){
    						jang = "25";
    						edit.putInt("jang_which2", 24);
    						edit.putString("jang2", jang);
    					}else if(which == 25){
    						jang = "26";
    						edit.putInt("jang_which2", 25);
    						edit.putString("jang2", jang);
    					}else if(which == 26){
    						jang = "27";
    						edit.putInt("jang_which2", 26);
    						edit.putString("jang2", jang);
    					}else if(which == 27){
    						jang = "28";
    						edit.putInt("jang_which2", 27);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb5[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
			}else if(kwon.equals("45")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb6, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}else if(which == 3){
    						jang = "4";
    						edit.putInt("jang_which2", 3);
    						edit.putString("jang2", jang);
    					}else if(which == 4){
    						jang = "5";
    						edit.putInt("jang_which2", 4);
    						edit.putString("jang2", jang);
    					}else if(which == 5){
    						jang = "6";
    						edit.putInt("jang_which2", 5);
    						edit.putString("jang2", jang);
    					}else if(which == 6){
    						jang = "7";
    						edit.putInt("jang_which2", 6);
    						edit.putString("jang2", jang);
    					}else if(which == 7){
    						jang = "8";
    						edit.putInt("jang_which2", 7);
    						edit.putString("jang2", jang);
    					}else if(which == 8){
    						jang = "9";
    						edit.putInt("jang_which2", 8);
    						edit.putString("jang2", jang);
    					}else if(which == 9){
    						jang = "10";
    						edit.putInt("jang_which2", 9);
    						edit.putString("jang2", jang);
    					}else if(which == 10){
    						jang = "11";
    						edit.putInt("jang_which2", 10);
    						edit.putString("jang2", jang);
    					}else if(which == 11){
    						jang = "12";
    						edit.putInt("jang_which2", 11);
    						edit.putString("jang2", jang);
    					}else if(which == 12){
    						jang = "13";
    						edit.putInt("jang_which2", 12);
    						edit.putString("jang2", jang);
    					}else if(which == 13){
    						jang = "14";
    						edit.putInt("jang_which2", 13);
    						edit.putString("jang2", jang);
    					}else if(which == 14){
    						jang = "15";
    						edit.putInt("jang_which2", 14);
    						edit.putString("jang2", jang);
    					}else if(which == 15){
    						jang = "16";
    						edit.putInt("jang_which2", 15);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb6[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
				
			}else if(kwon.equals("46")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb7, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}else if(which == 3){
    						jang = "4";
    						edit.putInt("jang_which2", 3);
    						edit.putString("jang2", jang);
    					}else if(which == 4){
    						jang = "5";
    						edit.putInt("jang_which2", 4);
    						edit.putString("jang2", jang);
    					}else if(which == 5){
    						jang = "6";
    						edit.putInt("jang_which2", 5);
    						edit.putString("jang2", jang);
    					}else if(which == 6){
    						jang = "7";
    						edit.putInt("jang_which2", 6);
    						edit.putString("jang2", jang);
    					}else if(which == 7){
    						jang = "8";
    						edit.putInt("jang_which2", 7);
    						edit.putString("jang2", jang);
    					}else if(which == 8){
    						jang = "9";
    						edit.putInt("jang_which2", 8);
    						edit.putString("jang2", jang);
    					}else if(which == 9){
    						jang = "10";
    						edit.putInt("jang_which2", 9);
    						edit.putString("jang2", jang);
    					}else if(which == 10){
    						jang = "11";
    						edit.putInt("jang_which2", 10);
    						edit.putString("jang2", jang);
    					}else if(which == 11){
    						jang = "12";
    						edit.putInt("jang_which2", 11);
    						edit.putString("jang2", jang);
    					}else if(which == 12){
    						jang = "13";
    						edit.putInt("jang_which2", 12);
    						edit.putString("jang2", jang);
    					}else if(which == 13){
    						jang = "14";
    						edit.putInt("jang_which2", 13);
    						edit.putString("jang2", jang);
    					}else if(which == 14){
    						jang = "15";
    						edit.putInt("jang_which2", 14);
    						edit.putString("jang2", jang);
    					}else if(which == 15){
    						jang = "16";
    						edit.putInt("jang_which2", 15);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb7[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
				
			}else if(kwon.equals("47")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb8, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}else if(which == 3){
    						jang = "4";
    						edit.putInt("jang_which2", 3);
    						edit.putString("jang2", jang);
    					}else if(which == 4){
    						jang = "5";
    						edit.putInt("jang_which2", 4);
    						edit.putString("jang2", jang);
    					}else if(which == 5){
    						jang = "6";
    						edit.putInt("jang_which2", 5);
    						edit.putString("jang2", jang);
    					}else if(which == 6){
    						jang = "7";
    						edit.putInt("jang_which2", 6);
    						edit.putString("jang2", jang);
    					}else if(which == 7){
    						jang = "8";
    						edit.putInt("jang_which2", 7);
    						edit.putString("jang2", jang);
    					}else if(which == 8){
    						jang = "9";
    						edit.putInt("jang_which2", 8);
    						edit.putString("jang2", jang);
    					}else if(which == 9){
    						jang = "10";
    						edit.putInt("jang_which2", 9);
    						edit.putString("jang2", jang);
    					}else if(which == 10){
    						jang = "11";
    						edit.putInt("jang_which2", 10);
    						edit.putString("jang2", jang);
    					}else if(which == 11){
    						jang = "12";
    						edit.putInt("jang_which2", 11);
    						edit.putString("jang2", jang);
    					}else if(which == 12){
    						jang = "13";
    						edit.putInt("jang_which2", 12);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb8[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
				
			}else if(kwon.equals("48")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb9, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}else if(which == 3){
    						jang = "4";
    						edit.putInt("jang_which2", 3);
    						edit.putString("jang2", jang);
    					}else if(which == 4){
    						jang = "5";
    						edit.putInt("jang_which2", 4);
    						edit.putString("jang2", jang);
    					}else if(which == 5){
    						jang = "6";
    						edit.putInt("jang_which2", 5);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb9[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
				
			}else if(kwon.equals("49")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb10, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}else if(which == 3){
    						jang = "4";
    						edit.putInt("jang_which2", 3);
    						edit.putString("jang2", jang);
    					}else if(which == 4){
    						jang = "5";
    						edit.putInt("jang_which2", 4);
    						edit.putString("jang2", jang);
    					}else if(which == 5){
    						jang = "6";
    						edit.putInt("jang_which2", 5);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb10[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
				
			}else if(kwon.equals("50")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb11, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}else if(which == 3){
    						jang = "4";
    						edit.putInt("jang_which2", 3);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb11[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
				
			}else if(kwon.equals("51")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb12, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}else if(which == 3){
    						jang = "4";
    						edit.putInt("jang_which2", 3);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb12[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
				
			}else if(kwon.equals("52")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb13, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}else if(which == 3){
    						jang = "4";
    						edit.putInt("jang_which2", 3);
    						edit.putString("jang2", jang);
    					}else if(which == 4){
    						jang = "5";
    						edit.putInt("jang_which2", 4);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb13[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
				
			}else if(kwon.equals("53")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb14, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb14[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
				
			}else if(kwon.equals("54")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb15, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}else if(which == 3){
    						jang = "4";
    						edit.putInt("jang_which2", 3);
    						edit.putString("jang2", jang);
    					}else if(which == 4){
    						jang = "5";
    						edit.putInt("jang_which2", 4);
    						edit.putString("jang2", jang);
    					}else if(which == 5){
    						jang = "6";
    						edit.putInt("jang_which2", 5);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb15[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
				
			}else if(kwon.equals("55")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb16, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}else if(which == 3){
    						jang = "4";
    						edit.putInt("jang_which2", 3);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb16[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
				
			}else if(kwon.equals("56")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb17, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb17[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
				
			}else if(kwon.equals("57")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb18, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb18[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
				
			}else if(kwon.equals("58")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb19, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}else if(which == 3){
    						jang = "4";
    						edit.putInt("jang_which2", 3);
    						edit.putString("jang2", jang);
    					}else if(which == 4){
    						jang = "5";
    						edit.putInt("jang_which2", 4);
    						edit.putString("jang2", jang);
    					}else if(which == 5){
    						jang = "6";
    						edit.putInt("jang_which2", 5);
    						edit.putString("jang2", jang);
    					}else if(which == 6){
    						jang = "7";
    						edit.putInt("jang_which2", 6);
    						edit.putString("jang2", jang);
    					}else if(which == 7){
    						jang = "8";
    						edit.putInt("jang_which2", 7);
    						edit.putString("jang2", jang);
    					}else if(which == 8){
    						jang = "9";
    						edit.putInt("jang_which2", 8);
    						edit.putString("jang2", jang);
    					}else if(which == 9){
    						jang = "10";
    						edit.putInt("jang_which2", 9);
    						edit.putString("jang2", jang);
    					}else if(which == 10){
    						jang = "11";
    						edit.putInt("jang_which2", 10);
    						edit.putString("jang2", jang);
    					}else if(which == 11){
    						jang = "12";
    						edit.putInt("jang_which2", 11);
    						edit.putString("jang2", jang);
    					}else if(which == 12){
    						jang = "13";
    						edit.putInt("jang_which2", 12);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb19[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
				
			}else if(kwon.equals("59")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb20, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}else if(which == 3){
    						jang = "4";
    						edit.putInt("jang_which2", 3);
    						edit.putString("jang2", jang);
    					}else if(which == 4){
    						jang = "5";
    						edit.putInt("jang_which2", 4);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb20[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
				
			}else if(kwon.equals("60")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb21, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}else if(which == 3){
    						jang = "4";
    						edit.putInt("jang_which2", 3);
    						edit.putString("jang2", jang);
    					}else if(which == 4){
    						jang = "5";
    						edit.putInt("jang_which2", 4);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb21[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
				
			}else if(kwon.equals("61")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb22, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb22[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
				
			}else if(kwon.equals("62")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb23, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}else if(which == 3){
    						jang = "4";
    						edit.putInt("jang_which2", 3);
    						edit.putString("jang2", jang);
    					}else if(which == 4){
    						jang = "5";
    						edit.putInt("jang_which2", 4);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb23[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
				
			}else if(kwon.equals("63")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb24, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb24[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
				
			}else if(kwon.equals("64")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb25, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb25[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
				
			}else if(kwon.equals("65")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb26, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb26[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
				
			}else if(kwon.equals("66")){
				alertDialog = new AlertDialog.Builder(this)
    			.setSingleChoiceItems(jang_kbb27, jang_which, new DialogInterface.OnClickListener(){
    				@Override
    				public void onClick(DialogInterface dialog, int which){
    					dialog.dismiss();
    					if(which == 0){
    						jang = "1";
    						edit.putInt("jang_which2", 0);
    						edit.putString("jang2", jang);
    					}else if(which == 1){
    						jang = "2";
    						edit.putInt("jang_which2", 1);
    						edit.putString("jang2", jang);
    					}else if(which == 2){
    						jang = "3";
    						edit.putInt("jang_which2", 2);
    						edit.putString("jang2", jang);
    					}else if(which == 3){
    						jang = "4";
    						edit.putInt("jang_which2", 3);
    						edit.putString("jang2", jang);
    					}else if(which == 4){
    						jang = "5";
    						edit.putInt("jang_which2", 4);
    						edit.putString("jang2", jang);
    					}else if(which == 5){
    						jang = "6";
    						edit.putInt("jang_which2", 5);
    						edit.putString("jang2", jang);
    					}else if(which == 6){
    						jang = "7";
    						edit.putInt("jang_which2", 6);
    						edit.putString("jang2", jang);
    					}else if(which == 7){
    						jang = "8";
    						edit.putInt("jang_which2", 7);
    						edit.putString("jang2", jang);
    					}else if(which == 8){
    						jang = "9";
    						edit.putInt("jang_which2", 8);
    						edit.putString("jang2", jang);
    					}else if(which == 9){
    						jang = "10";
    						edit.putInt("jang_which2", 9);
    						edit.putString("jang2", jang);
    					}else if(which == 10){
    						jang = "11";
    						edit.putInt("jang_which2", 10);
    						edit.putString("jang2", jang);
    					}else if(which == 11){
    						jang = "12";
    						edit.putInt("jang_which2", 11);
    						edit.putString("jang2", jang);
    					}else if(which == 12){
    						jang = "13";
    						edit.putInt("jang_which2", 12);
    						edit.putString("jang2", jang);
    					}else if(which == 13){
    						jang = "14";
    						edit.putInt("jang_which2", 13);
    						edit.putString("jang2", jang);
    					}else if(which == 14){
    						jang = "15";
    						edit.putInt("jang_which2", 14);
    						edit.putString("jang2", jang);
    					}else if(which == 15){
    						jang = "16";
    						edit.putInt("jang_which2", 15);
    						edit.putString("jang2", jang);
    					}else if(which == 16){
    						jang = "17";
    						edit.putInt("jang_which2", 16);
    						edit.putString("jang2", jang);
    					}else if(which == 17){
    						jang = "18";
    						edit.putInt("jang_which2", 17);
    						edit.putString("jang2", jang);
    					}else if(which == 18){
    						jang = "19";
    						edit.putInt("jang_which2", 18);
    						edit.putString("jang2", jang);
    					}else if(which == 19){
    						jang = "20";
    						edit.putInt("jang_which2", 19);
    						edit.putString("jang2", jang);
    					}else if(which == 20){
    						jang = "21";
    						edit.putInt("jang_which2", 20);
    						edit.putString("jang2", jang);
    					}else if(which == 21){
    						jang = "22";
    						edit.putInt("jang_which2", 21);
    						edit.putString("jang2", jang);
    					}
    					try{
    						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
    							voice_play_stop();
    						}
    					}catch(Exception e){
    					}
    					bt_jang.setText(jang_kbb27[which] + context.getString(R.string.txt_jang));
    					displayList();
    					edit.commit();
    				}
    			}).show();
			}
    	}else if(view == Bottom_04){
    		settings = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), MODE_PRIVATE);
			edit = settings.edit();
    		
    		int IntJang = Integer.parseInt(jang) -1;
    		String stringJang = Integer.toString(IntJang);
    		if(jang.equals("1")){
    			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
    		}else{
    			try{
					if(mediaPlayer != null && mediaPlayer.isPlaying() ){
						voice_play_stop();
					}
				}catch(Exception e){
				}
    			jang = stringJang;
    			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
				edit.putString("jang2", jang);
				bt_jang.setText(jang_kbb5[Integer.parseInt(jang)-1] + context.getString(R.string.txt_jang));
    			displayList();
    			edit.commit();
    		}
    	}else if(view == Bottom_05){
    		settings = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), MODE_PRIVATE);
			edit = settings.edit();
    		int IntJang = Integer.parseInt(jang) +1;
    		String stringJang = Integer.toString(IntJang);
    		if(kwon.equals("40")){
    			if(jang.equals("28")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("41")){
    			if(jang.equals("16")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("42")){
    			if(jang.equals("24")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("43")){
    			if(jang.equals("21")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("44")){
    			if(jang.equals("28")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("45")){
    			if(jang.equals("16")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("46")){
    			if(jang.equals("16")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("47")){
    			if(jang.equals("13")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("48")){
    			if(jang.equals("6")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("49")){
    			if(jang.equals("6")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("50")){
    			if(jang.equals("4")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("51")){
    			if(jang.equals("4")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("52")){
    			if(jang.equals("5")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("53")){
    			if(jang.equals("3")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("54")){
    			if(jang.equals("6")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("55")){
    			if(jang.equals("4")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("56")){
    			if(jang.equals("3")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("57")){
    			if(jang.equals("1")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("58")){
    			if(jang.equals("13")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("59")){
    			if(jang.equals("5")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("60")){
    			if(jang.equals("5")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("61")){
    			if(jang.equals("3")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("62")){
    			if(jang.equals("5")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("63")){
    			if(jang.equals("1")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("64")){
    			if(jang.equals("1")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("65")){
    			if(jang.equals("1")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}else if(kwon.equals("66")){
    			if(jang.equals("22")){
        			Toast.makeText(this, R.string.sub1_txt1, Toast.LENGTH_SHORT).show();
        		}else{
        			try{
						if(mediaPlayer != null && mediaPlayer.isPlaying() ){
							voice_play_stop();
						}
					}catch(Exception e){
					}
        			jang = stringJang;
        			edit.putInt("jang_which2", Integer.parseInt(jang)-1);
        			edit.putString("jang2", jang);
        		}
    		}
    		bt_jang.setText(jang + context.getString(R.string.txt_jang));
			displayList();
			edit.commit();
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
    	}else if(view == Bottom_06){
    		try{
				if(mediaPlayer != null && mediaPlayer.isPlaying() ){
					voice_play_stop();
				}
			}catch(Exception e){
			}
    		Intent intent = new Intent(this, Search_Activity.class);
    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(intent);
    	}else if(view == Bottom_07){
    		addInterstitialView();
    	}else if(view == Bottom_08){
    		settings = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), MODE_PRIVATE);
    		edit = settings.edit();
    		String[] setting_alert ={context.getString(R.string.txt_setting_alert0),
    								context.getString(R.string.txt_setting_alert1),
    								context.getString(R.string.txt_setting_alert2),
    								context.getString(R.string.txt_setting_alert3),
    								context.getString(R.string.txt_setting_alert4),
    								context.getString(R.string.txt_setting_alert5)
    								};
    		new AlertDialog.Builder(this)
        	.setTitle(context.getString(R.string.txt_setting_alert_title))
        	.setItems(setting_alert, new DialogInterface.OnClickListener() {
        		@Override
        		public void onClick(DialogInterface dialog, int which) {
        			if(which == 0){
        				String[] bible_list = {context.getString(R.string.txt_bible_list1),
        									   context.getString(R.string.txt_bible_list2),
        									   context.getString(R.string.txt_bible_list3),
        									   context.getString(R.string.txt_bible_list4),
        									   context.getString(R.string.txt_bible_list5),
        									   context.getString(R.string.txt_bible_list6),
        									   context.getString(R.string.txt_bible_list7),
        									   context.getString(R.string.txt_bible_list8),
        									   context.getString(R.string.txt_bible_list9),
        									   context.getString(R.string.txt_bible_list10),
        									   context.getString(R.string.txt_bible_list11),
        									   
        									   context.getString(R.string.txt_bible_list12),
        									   context.getString(R.string.txt_bible_list13),
        									   context.getString(R.string.txt_bible_list14),
        									   context.getString(R.string.txt_bible_list15),
        									   context.getString(R.string.txt_bible_list16),
        									   context.getString(R.string.txt_bible_list17),
        									   context.getString(R.string.txt_bible_list18),
        									   context.getString(R.string.txt_bible_list19),
        									   context.getString(R.string.txt_bible_list20),
        									   context.getString(R.string.txt_bible_list21),
        									   context.getString(R.string.txt_bible_list22),
        									   context.getString(R.string.txt_bible_list23),
        									   context.getString(R.string.txt_bible_list24),
        									   context.getString(R.string.txt_bible_list25),
        									   context.getString(R.string.txt_bible_list26),
        									   context.getString(R.string.txt_bible_list27),
        									   context.getString(R.string.txt_bible_list28),
        									   context.getString(R.string.txt_bible_list29),
        									   context.getString(R.string.txt_bible_list30),
        									   context.getString(R.string.txt_bible_list31)
        				};
        				alertDialog = new AlertDialog.Builder(context)
        				.setIcon(R.drawable.img_setting1)
        				.setTitle(R.string.txt_setting_alert0)
        				.setSingleChoiceItems(bible_list, bible_type, new DialogInterface.OnClickListener(){
        					@Override
        					public void onClick(DialogInterface dialog, int which){
        						dialog.dismiss();
        						if(which == 0){//성경선택 kkk.db
        							bible_type = 0;
        							edit.putInt("bible_type", bible_type);
        						}else if(which == 1){//kbb.db
        							bible_type = 1;
        							edit.putInt("bible_type", bible_type);
        						}else if(which == 2){//kjv.db
        							bible_type = 2;
        							edit.putInt("bible_type", bible_type);
        						}else if(which == 3){//jpnnew.db
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_jpnnew);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_jpnnew);
    										String input_db_path = context.getString(R.string.txt_jpnnew_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 4){//ckb.db
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_ckb);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_ckb);
    										String input_db_path = context.getString(R.string.txt_ckb_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 5){//frenchdarby.db
        							Check_DB(context.getString(R.string.txt_frenchdarby_path));
//        							if(check_db == false){
        								try{
        									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
        									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//        										String subtype_num = network_info.getSubtype() + "";
//        										String subtype_name = network_info.getSubtypeName();
        										String file_name = context.getString(R.string.txt_input_frenchdarby);
        										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
        												+ context.getString(R.string.sub5_txt9);
        										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
        										String url_path = get_data + context.getString(R.string.txt_input_frenchdarby);
        										String input_db_path = context.getString(R.string.txt_frenchdarby_path);
        										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
        										downloadDBAsync.execute();
        									}else{
        										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
        									}
        								}catch(Exception e){
        								}
//        							}else{
//        								bible_type = 5;
//            							edit.putInt("bible_type", bible_type);
//        							}
        						}else if(which == 6){//germanluther.db
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_germanluther);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_germanluther);
    										String input_db_path = context.getString(R.string.txt_germanluther_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 7){//gst.db
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_gst);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_gst);
    										String input_db_path = context.getString(R.string.txt_gst_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 8){//indonesianbaru.db
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_indonesianbaru);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_indonesianbaru);
    										String input_db_path = context.getString(R.string.txt_indonesianbaru_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 9){//portugal.db
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_portugal);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_portugal);
    										String input_db_path = context.getString(R.string.txt_portugal_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 10){//russiansynodal.db
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_russiansynodal);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_russiansynodal);
    										String input_db_path = context.getString(R.string.txt_russiansynodal_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 11){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_alb);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_alb);
    										String input_db_path = context.getString(R.string.txt_alb_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 12){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_asv);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_asv);
    										String input_db_path = context.getString(R.string.txt_asv_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 13){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_avs);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_avs);
    										String input_db_path = context.getString(R.string.txt_avs_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 14){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_barun);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_barun);
    										String input_db_path = context.getString(R.string.txt_barun_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 15){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_chb);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_chb);
    										String input_db_path = context.getString(R.string.txt_chb_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 16){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_chg);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_chg);
    										String input_db_path = context.getString(R.string.txt_chg_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 17){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_cjb);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_cjb);
    										String input_db_path = context.getString(R.string.txt_cjb_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 18){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_ckc);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_ckc);
    										String input_db_path = context.getString(R.string.txt_ckc_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 19){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_ckg);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_ckg);
    										String input_db_path = context.getString(R.string.txt_ckg_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 20){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_cks);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_cks);
    										String input_db_path = context.getString(R.string.txt_cks_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 21){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_hebbhs);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_hebbhs);
    										String input_db_path = context.getString(R.string.txt_hebbhs_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 22){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_hebmod);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_hebmod);
    										String input_db_path = context.getString(R.string.txt_hebmod_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 23){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_hebwlc);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_hebwlc);
    										String input_db_path = context.getString(R.string.txt_hebwlc_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 24){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_indianhindi);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_indianhindi);
    										String input_db_path = context.getString(R.string.txt_indianhindi_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 25){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_indiantamil);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_indiantamil);
    										String input_db_path = context.getString(R.string.txt_indiantamil_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 26){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_jpnold);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_jpnold);
    										String input_db_path = context.getString(R.string.txt_jpnold_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 27){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_reina);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_reina);
    										String input_db_path = context.getString(R.string.txt_reina_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 28){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_tagalog);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_tagalog);
    										String input_db_path = context.getString(R.string.txt_tagalog_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 29){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_tkh);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_tkh);
    										String input_db_path = context.getString(R.string.txt_tkh_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 30){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_web);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_web);
    										String input_db_path = context.getString(R.string.txt_web_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}
        						edit.commit();
        						displayList();
        					}
        				}).show();
        			}else if(which == 1){
        				String[] bible2_list = {
        						   context.getString(R.string.txt_bible_list_none),
        						   context.getString(R.string.txt_bible_list3),
        						   context.getString(R.string.txt_bible_list1),
        						   context.getString(R.string.txt_bible_list2),
								   context.getString(R.string.txt_bible_list4),
								   context.getString(R.string.txt_bible_list5),
								   context.getString(R.string.txt_bible_list6),
								   context.getString(R.string.txt_bible_list7),
								   context.getString(R.string.txt_bible_list8),
								   context.getString(R.string.txt_bible_list9),
								   context.getString(R.string.txt_bible_list10),
								   context.getString(R.string.txt_bible_list11),
								   
								   context.getString(R.string.txt_bible_list12),
								   context.getString(R.string.txt_bible_list13),
								   context.getString(R.string.txt_bible_list14),
								   context.getString(R.string.txt_bible_list15),
								   context.getString(R.string.txt_bible_list16),
								   context.getString(R.string.txt_bible_list17),
								   context.getString(R.string.txt_bible_list18),
								   context.getString(R.string.txt_bible_list19),
								   context.getString(R.string.txt_bible_list20),
								   context.getString(R.string.txt_bible_list21),
								   context.getString(R.string.txt_bible_list22),
								   context.getString(R.string.txt_bible_list23),
								   context.getString(R.string.txt_bible_list24),
								   context.getString(R.string.txt_bible_list25),
								   context.getString(R.string.txt_bible_list26),
								   context.getString(R.string.txt_bible_list27),
								   context.getString(R.string.txt_bible_list28),
								   context.getString(R.string.txt_bible_list29),
								   context.getString(R.string.txt_bible_list30),
								   context.getString(R.string.txt_bible_list31)
			
        				};
        				
        				alertDialog = new AlertDialog.Builder(context)
						.setIcon(R.drawable.img_setting1)
						.setTitle(R.string.txt_setting_alert1)
						.setSingleChoiceItems(bible2_list, bible2_type, new DialogInterface.OnClickListener(){
							@Override
							public void onClick(DialogInterface dialog, int which){
								dialog.dismiss();
								if(which == 0){//성경선택
									bible2_type = 0;
									edit.putInt("bible2_type", bible2_type);
								}else if(which == 1){
									bible2_type = 1;
									edit.putInt("bible2_type", bible2_type);
								}else if(which == 2){
									bible2_type = 2;
									edit.putInt("bible2_type", bible2_type);
								}else if(which == 3){
									bible2_type = 3;
									edit.putInt("bible2_type", bible2_type);
								}else if(which == 4){//jpnnew.db
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_jpnnew);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_jpnnew);
    										String input_db_path = context.getString(R.string.txt_jpnnew_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 5){//ckb.db
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_ckb);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_ckb);
    										String input_db_path = context.getString(R.string.txt_ckb_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 6){//frenchdarby.db
        							Check_DB(context.getString(R.string.txt_frenchdarby_path));
//        							if(check_db == false){
        								try{
        									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
        									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//        										String subtype_num = network_info.getSubtype() + "";
//        										String subtype_name = network_info.getSubtypeName();
        										String file_name = context.getString(R.string.txt_input_frenchdarby);
        										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
        												+ context.getString(R.string.sub5_txt9);
        										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
        										String url_path = get_data + context.getString(R.string.txt_input_frenchdarby);
        										String input_db_path = context.getString(R.string.txt_frenchdarby_path);
        										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
        										downloadDBAsync.execute();
        									}else{
        										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
        									}
        								}catch(Exception e){
        								}
//        							}else{
//        								bible_type = 5;
//            							edit.putInt("bible2_type", bible_type);
//        							}
        						}else if(which == 7){//germanluther.db
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_germanluther);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_germanluther);
    										String input_db_path = context.getString(R.string.txt_germanluther_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 8){//gst.db
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_gst);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_gst);
    										String input_db_path = context.getString(R.string.txt_gst_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 9){//indonesianbaru.db
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_indonesianbaru);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_indonesianbaru);
    										String input_db_path = context.getString(R.string.txt_indonesianbaru_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 10){//portugal.db
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_portugal);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_portugal);
    										String input_db_path = context.getString(R.string.txt_portugal_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 11){//russiansynodal.db
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_russiansynodal);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_russiansynodal);
    										String input_db_path = context.getString(R.string.txt_russiansynodal_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 12){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_alb);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_alb);
    										String input_db_path = context.getString(R.string.txt_alb_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 13){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_asv);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_asv);
    										String input_db_path = context.getString(R.string.txt_asv_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 14){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_avs);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_avs);
    										String input_db_path = context.getString(R.string.txt_avs_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 15){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_barun);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_barun);
    										String input_db_path = context.getString(R.string.txt_barun_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 16){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_chb);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_chb);
    										String input_db_path = context.getString(R.string.txt_chb_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 17){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_chg);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_chg);
    										String input_db_path = context.getString(R.string.txt_chg_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 18){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_cjb);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_cjb);
    										String input_db_path = context.getString(R.string.txt_cjb_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 19){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_ckc);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_ckc);
    										String input_db_path = context.getString(R.string.txt_ckc_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 20){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_ckg);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_ckg);
    										String input_db_path = context.getString(R.string.txt_ckg_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 21){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_cks);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_cks);
    										String input_db_path = context.getString(R.string.txt_cks_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 22){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_hebbhs);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_hebbhs);
    										String input_db_path = context.getString(R.string.txt_hebbhs_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 23){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_hebmod);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_hebmod);
    										String input_db_path = context.getString(R.string.txt_hebmod_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 24){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_hebwlc);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_hebwlc);
    										String input_db_path = context.getString(R.string.txt_hebwlc_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 25){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_indianhindi);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_indianhindi);
    										String input_db_path = context.getString(R.string.txt_indianhindi_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 26){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_indiantamil);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_indiantamil);
    										String input_db_path = context.getString(R.string.txt_indiantamil_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 27){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_jpnold);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_jpnold);
    										String input_db_path = context.getString(R.string.txt_jpnold_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 28){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_reina);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_reina);
    										String input_db_path = context.getString(R.string.txt_reina_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 29){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_tagalog);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_tagalog);
    										String input_db_path = context.getString(R.string.txt_tagalog_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 30){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_tkh);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_tkh);
    										String input_db_path = context.getString(R.string.txt_tkh_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}else if(which == 31){
        							try{
    									connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    									mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    									wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    									NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
    									if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//    										String subtype_num = network_info.getSubtype() + "";
//    										String subtype_name = network_info.getSubtypeName();
    										String file_name = context.getString(R.string.txt_input_web);
    										String dir_name = Environment.getExternalStorageDirectory().getAbsolutePath()
    												+ context.getString(R.string.sub5_txt9);
    										String get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.download_db_url));
    										String url_path = get_data + context.getString(R.string.txt_input_web);
    										String input_db_path = context.getString(R.string.txt_web_path);
    										downloadDBAsync = new DownloadDBAsync(context, file_name, url_path, dir_name, "bible2_type", which, input_db_path);
    										downloadDBAsync.execute();
    									}else{
    										Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
    									}
    								}catch(Exception e){
    								}
        						}
								edit.commit();
								displayList();
							}
						}).show();
						}else if(which == 2){//오디오속도
							if(mediaPlayer != null && mediaPlayer.isPlaying() ){
								Toast.makeText(context, context.getString(R.string.txt_audiospeed_after), Toast.LENGTH_LONG).show();
							}else{
								String[] audiospeed_list = {context.getString(R.string.txt_audiospeed_list1),
		        						context.getString(R.string.txt_audiospeed_list2)};
								alertDialog = new AlertDialog.Builder(context)
		        				.setTitle(R.string.txt_setting_alert2)
		        				.setSingleChoiceItems(audiospeed_list, audio_speed, new DialogInterface.OnClickListener(){
		        					@Override
		        					public void onClick(DialogInterface dialog, int which){
		        						dialog.dismiss();
		        						if(which == 0){
		        							audio_speed = 0;
		        							edit.putInt("audio_speed", audio_speed);
		        						}else if(which == 1){
		        							audio_speed = 1;
		        							edit.putInt("audio_speed", audio_speed);
		        						}
		        						edit.commit();
		        					}
		        				}).show();	
							}
						}else if(which == 3){//글자색상선택
        				String[] textcolor_list = {context.getString(R.string.txt_textcolor_list1),
        						context.getString(R.string.txt_textcolor_list2),
        						context.getString(R.string.txt_textcolor_list3),
        						context.getString(R.string.txt_textcolor_list4),
        						context.getString(R.string.txt_textcolor_list5),
        						context.getString(R.string.txt_textcolor_list6),
        						context.getString(R.string.txt_textcolor_list7),
        						context.getString(R.string.txt_textcolor_list8),
        						context.getString(R.string.txt_textcolor_list9),
        						context.getString(R.string.txt_textcolor_list10),
        						context.getString(R.string.txt_textcolor_list11),
        						context.getString(R.string.txt_textcolor_list12)};
        				alertDialog = new AlertDialog.Builder(context)
        				.setIcon(R.drawable.img_setting2)
        				.setTitle(R.string.txt_setting_alert1)
        				.setSingleChoiceItems(textcolor_list, text_color, new DialogInterface.OnClickListener(){
        					@Override
        					public void onClick(DialogInterface dialog, int which){
        						dialog.dismiss();
        						if(which == 0){
        							text_color = 0;
        							edit.putInt("text_color", text_color);
        						}else if(which == 1){
        							text_color = 1;
        							edit.putInt("text_color", text_color);
        						}else if(which == 2){
        							text_color = 2;
        							edit.putInt("text_color", text_color);
        						}else if(which == 3){
        							text_color = 3;
        							edit.putInt("text_color", text_color);
        						}else if(which == 4){
        							text_color = 4;
        							edit.putInt("text_color", text_color);
        						}else if(which == 5){
        							text_color = 5;
        							edit.putInt("text_color", text_color);
        						}else if(which == 6){
        							text_color = 6;
        							edit.putInt("text_color", text_color);
        						}else if(which == 7){
        							text_color = 7;
        							edit.putInt("text_color", text_color);
        						}else if(which == 8){
        							text_color = 8;
        							edit.putInt("text_color", text_color);
        						}else if(which == 9){
        							text_color = 9;
        							edit.putInt("text_color", text_color);
        						}else if(which == 10){
        							text_color = 10;
        							edit.putInt("text_color", text_color);
        						}else if(which == 11){
        							text_color = 11;
        							edit.putInt("text_color", text_color);
        						}
        						edit.commit();
        						if(bible_Adapter != null){
        							bible_Adapter.notifyDataSetChanged();
        						}
        					}
        				}).show();	
        			}else if(which == 4){//문의하기
        				Intent intent = new Intent(context, ContactUs_Activity.class);
        				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        				startActivity(intent);
        			}else if(which == 5){//이앱전도
        				Intent intent = new Intent(Intent.ACTION_SEND);
        				intent.setType("text/plain");    
        				intent.addCategory(Intent.CATEGORY_DEFAULT);
//        				intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
        				intent.putExtra(Intent.EXTRA_TEXT,  context.getString(R.string.app_name) + "\n" + context.getString(R.string.app_share_link));
//        				intent.putExtra(Intent.EXTRA_TITLE, context.getString(R.string.app_name));
        				startActivity(Intent.createChooser(intent, context.getString(R.string.txt_share)));
        			}
        		}
        	}).show();
    	}else if(view == bt_action1){
			if(bt_action1.isSelected()){
				bt_action1.setSelected(false);
				bt_action1.setText(context.getString(R.string.txt_sub_action1_1));
				for(int i=0; i < listview_sublist.getCount(); i++){
					listview_sublist.setItemChecked(i, false);
				}
				action_layout.setVisibility(View.GONE);
			}else{
				bt_action1.setSelected(true);
				bt_action1.setText(context.getString(R.string.txt_sub_action1_2));
				for(int i=0; i < listview_sublist.getCount(); i++){
					listview_sublist.setItemChecked(i, true);
				}
			}
			
		}else if(view == bt_action2){
			pref = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), Activity.MODE_PRIVATE);
	    	bible_type = pref.getInt("bible_type", bible_type);
	    	bible2_type = pref.getInt("bible2_type", bible2_type);
			
			SparseBooleanArray sba = listview_sublist.getCheckedItemPositions();
			StringBuffer strBuf = new StringBuffer();
			if(sba.size() != 0){
				for(int i=0; i < listview_sublist.getCount(); i++){
					if(sba.get(i)){
						Sub1_ColumData bible_data = (Sub1_ColumData)bible_Adapter.getItem(i);
						if(bible2_type != 0){
							String bible_data2 = contactsList2.get(i).getContent();
							String content = bible_data.getContent() + "\n" + bible_data2;
							String jul = bible_data.getJul();
							strBuf.append(jul + " " + content);
							sba = listview_sublist.getCheckedItemPositions();
						}else{
							String content = bible_data.getContent();
							String jul = bible_data.getJul();
							strBuf.append(jul + " " + content);
							sba = listview_sublist.getCheckedItemPositions();
						}
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
					cv.put("kwon", bt_kwon.getText().toString() + "" + " " + bt_jang.getText().toString());
					cv.put("jang", "\n"+dateFormat.format(date));
					cv.put("content", strBuf.toString() + "");
					favorite_mydb.getWritableDatabase().insert("my_list", null, cv);
					if(favorite_mydb != null) favorite_mydb.close();
					Toast.makeText(Sub2_Activity.this, R.string.sub1_txt5, Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(Sub2_Activity.this, R.string.sub1_txt6, Toast.LENGTH_SHORT).show();
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
					SparseBooleanArray sba = listview_sublist.getCheckedItemPositions();
					StringBuffer strBuf = new StringBuffer();
					if(sba.size() != 0){
						for(int i=0; i < listview_sublist.getCount(); i++){
							if(sba.get(i)){
								Sub1_ColumData bible_data = (Sub1_ColumData)bible_Adapter.getItem(i);
								String content = bible_data.getContent();
								strBuf.append(content);
								sba = listview_sublist.getCheckedItemPositions();
							}
						}
						speakOut(strBuf.toString() + "");
					}
				}
			}catch(NullPointerException e){
				
			}
		}else if(view == bt_action4){
			pref = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), Activity.MODE_PRIVATE);
	    	bible_type = pref.getInt("bible_type", bible_type);
	    	bible2_type = pref.getInt("bible2_type", bible2_type);
			
			SparseBooleanArray sba = listview_sublist.getCheckedItemPositions();
			StringBuffer strBuf = new StringBuffer();
			if(sba.size() != 0){
				for(int i=0; i < listview_sublist.getCount(); i++){
					if(sba.get(i)){
						Sub1_ColumData bible_data = (Sub1_ColumData)bible_Adapter.getItem(i);
						if(bible2_type != 0){
							String bible_data2 = contactsList2.get(i).getContent();
							String content = bible_data.getContent() + "\n" + bible_data2;
							String jul = bible_data.getJul();
							strBuf.append(jul + " " + content);
							sba = listview_sublist.getCheckedItemPositions();
						}else{
							String content = bible_data.getContent();
							String jul = bible_data.getJul();
							strBuf.append(jul + " " + content);
							sba = listview_sublist.getCheckedItemPositions();
						}
					}
				}
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");    
				intent.addCategory(Intent.CATEGORY_DEFAULT);
//				intent.putExtra(Intent.EXTRA_SUBJECT, title);
				intent.putExtra(Intent.EXTRA_TEXT, bt_kwon.getText().toString() + "" + " " + bt_jang.getText().toString() + "" + "\n" +strBuf.toString() + "");
//				intent.putExtra(Intent.EXTRA_TITLE, title);
				startActivity(Intent.createChooser(intent, context.getString(R.string.txt_share)));
			}
		}else if(view == top_01){
			try{
				if(mediaPlayer != null && mediaPlayer.isPlaying() ){
					voice_play_stop();
				}
			}catch(Exception e){
			}
			Intent intent = new Intent(this, Sub1_Activity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}else if(view == top_02){
			try{
				if(mediaPlayer != null && mediaPlayer.isPlaying() ){
					voice_play_stop();
				}
			}catch(Exception e){
			}
			Intent intent = new Intent(this, Sub2_Activity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}else if(view == top_03){
			try{
				if(mediaPlayer != null && mediaPlayer.isPlaying() ){
					voice_play_stop();
				}
			}catch(Exception e){
			}
			Intent intent = new Intent(this, Sub3_1_Activity.class);
    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(intent);
		}else if(view == top_04){
			try{
				if(mediaPlayer != null && mediaPlayer.isPlaying() ){
					voice_play_stop();
				}
			}catch(Exception e){
			}
			Intent intent = new Intent(this, Sub4_1_Activity.class);
    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(intent);
		}else if(view == top_05){
			try{
				if(mediaPlayer != null && mediaPlayer.isPlaying() ){
					voice_play_stop();
				}
			}catch(Exception e){
			}
			Intent intent = new Intent(this, com.good.worshipbible.nos.podcast.Sub5_1_Activity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}else if(view == top_06){
			try{
				if(mediaPlayer != null && mediaPlayer.isPlaying() ){
					voice_play_stop();
				}
			}catch(Exception e){
			}
			Intent intent = new Intent(this, com.good.worshipbible.nos.ccm.Sub6_1_Activity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}else if(view == top_07){
			try{
				if(mediaPlayer != null && mediaPlayer.isPlaying() ){
					voice_play_stop();
				}
			}catch(Exception e){
			}
			Intent intent = new Intent(this, Sub7_Activity.class);
    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(intent);
		}else if(view == top_08){
			try{
				if(mediaPlayer != null && mediaPlayer.isPlaying() ){
					voice_play_stop();
				}
			}catch(Exception e){
			}
			Intent intent = new Intent(this, com.good.worshipbible.nos.favorite.MainActivity.class);
    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(intent);
		}else if(view == bt_duration_rew){
			if(mediaPlayer != null){
				int currentPosition = mediaPlayer.getCurrentPosition();
				// check if seekBackward time is greater than 0 sec
				if(currentPosition - seekBackwardTime >= 0){
					// forward song
					mediaPlayer.seekTo(currentPosition - seekBackwardTime); 
				}else{
					// backward to starting position
					mediaPlayer.seekTo(0);
				}
			}else{
				return;
			}
		}else if(view == bt_duration_ffwd){
			if(mediaPlayer != null){
				int currentPosition = mediaPlayer.getCurrentPosition();
				if(currentPosition + seekForwardtime <= mediaPlayer.getDuration()){
					// forward song
					mediaPlayer.seekTo(currentPosition + seekForwardtime);
				}else{
					// forward to end position
					mediaPlayer.seekTo(mediaPlayer.getDuration());
				}
			}else{
				return;
			}
		}else if(view == bt_pause){
		try{
			if(mediaPlayer != null && mediaPlayer.isPlaying() ){
				onPause_DB_Voice();
				mediaPlayer.pause();
				bt_pause.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_media_play_small));
			}else{
				connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
				mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
				if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
//					Log.i("dsu", "position : " + current_position);
					voicePlayAsync = new VoicePlayAsync(bt_kwon.getText().toString().replace(kwon_kbb[0], "Matthew")
							.replace(kwon_kbb[1], "Mark")
							.replace(kwon_kbb[2], "Luke")
							.replace(kwon_kbb[3], "John")
							.replace(kwon_kbb[4], "Acts")
							.replace(kwon_kbb[5], "Romans")
							.replace(kwon_kbb[6], "Corinthians_up")
							.replace(kwon_kbb[7], "Corinthians_down")
							.replace(kwon_kbb[8], "Galatians")
							.replace(kwon_kbb[9], "Ephesians")
							.replace(kwon_kbb[10], "Philippians")
							.replace(kwon_kbb[11], "Colossians")
							.replace(kwon_kbb[12], "Thessalonians_up")
							.replace(kwon_kbb[13], "Thessalonians_down")
							.replace(kwon_kbb[14], "Timothy_up")
							.replace(kwon_kbb[15], "Timothy_down")
							.replace(kwon_kbb[16], "Titus")
							.replace(kwon_kbb[17], "Philemon")
							.replace(kwon_kbb[18], "Hebrews")
							.replace(kwon_kbb[19], "James")
							.replace(kwon_kbb[20], "Peter_up")
							.replace(kwon_kbb[21], "Peter_down")
							.replace(kwon_kbb[22], "John_one")
							.replace(kwon_kbb[23], "John_two")
							.replace(kwon_kbb[24], "John_three")
							.replace(kwon_kbb[25], "Jude")
							.replace(kwon_kbb[26], "Revelation")
							, Integer.parseInt(bt_jang.getText().toString().replace("장", "")));
					voicePlayAsync.execute();
//					mediaPlayer.start();
//					bt_pause.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_media_pause_small));
				}else{
					Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
				}
				}
				}catch(Exception e){
			}
		}
   }
    
    public static Thread autoscroll_thread = null;
	void autoScrollTask() {
		(autoscroll_thread = new Thread() {			
			public void run() {
				try {
					for (;;) {
						Thread.sleep(100);
						listview_sublist.smoothScrollBy(3, 100);
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
				Toast.makeText(Sub2_Activity.this, R.string.sub1_txt4, Toast.LENGTH_SHORT).show();
			}else{
				tts.setLanguage(Locale.KOREA);
			}
		}else{
			int result = tts.setLanguage(Locale.US);
			if (result == TextToSpeech.LANG_MISSING_DATA ||
					result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Toast.makeText(Sub2_Activity.this, R.string.sub1_txt4, Toast.LENGTH_SHORT).show();
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
    	int selectd_count = 0;
    	SparseBooleanArray sba = listview_sublist.getCheckedItemPositions();
		if(sba.size() != 0){
			for(int i = listview_sublist.getCount() -1; i>=0; i--){
				if(sba.get(i)){
					sba = listview_sublist.getCheckedItemPositions();
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
			cursor = mdb.rawQuery("select * from bible  WHERE kwon = '"+kwon+"' and jang = '"+jang+"'", null);
//			startManagingCursor(cursor);
			while(cursor.moveToNext()){
				addContact(contactsList,cursor.getString(cursor.getColumnIndex("kwon")),cursor.getString(cursor.getColumnIndex("jang")), cursor.getString(cursor.getColumnIndex("jul")), cursor.getString(cursor.getColumnIndex("content")));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
		return contactsList;
	}
    
    public ArrayList<Sub1_2_ColumData> getContactsList2() {
		try{
			contactsList2 = new ArrayList<Sub1_2_ColumData>();
			if(bible2_type == 1){
				mdb2 = kjv_db.getReadableDatabase(); 
			}else if(bible2_type == 2){
				mdb2 = kkk_db.getReadableDatabase(); 
			}else if(bible2_type == 3){
				mdb2 = kbb_db.getReadableDatabase(); 
			}else if(bible2_type == 4){
				mdb2 = jpnnew_db.getReadableDatabase(); 
			}else if(bible2_type == 5){
				mdb2 = ckb_db.getReadableDatabase(); 
			}else if(bible2_type == 6){
				mdb2 = frenchdarby_db.getReadableDatabase(); 
			}else if(bible2_type == 7){
				mdb2 = germanluther_db.getReadableDatabase(); 
			}else if(bible2_type == 8){
				mdb2 = gst_db.getReadableDatabase(); 
			}else if(bible2_type == 9){
				mdb2 = indonesianbaru_db.getReadableDatabase(); 
			}else if(bible2_type == 10){
				mdb2 = portugal_db.getReadableDatabase(); 
			}else if(bible2_type == 11){
				mdb2 = russiansynodal_db.getReadableDatabase(); 
			}else if(bible2_type == 12){
				mdb2 = alb_db.getReadableDatabase(); 
			}else if(bible2_type == 13){
				mdb2 = asv_db.getReadableDatabase(); 
			}else if(bible2_type == 14){
				mdb2 = avs_db.getReadableDatabase(); 
			}else if(bible2_type == 15){
				mdb2 = barun_db.getReadableDatabase(); 
			}else if(bible2_type == 16){
				mdb2 = chb_db.getReadableDatabase(); 
			}else if(bible2_type == 17){
				mdb2 = chg_db.getReadableDatabase(); 
			}else if(bible2_type == 18){
				mdb2 = cjb_db.getReadableDatabase(); 
			}else if(bible2_type == 19){
				mdb2 = ckc_db.getReadableDatabase(); 
			}else if(bible2_type == 20){
				mdb2 = ckg_db.getReadableDatabase(); 
			}else if(bible2_type == 21){
				mdb2 = cks_db.getReadableDatabase(); 
			}else if(bible2_type == 22){
				mdb2 = hebbhs_db.getReadableDatabase(); 
			}else if(bible2_type == 23){
				mdb2 = hebmod_db.getReadableDatabase(); 
			}else if(bible2_type == 24){
				mdb2 = hebwlc_db.getReadableDatabase(); 
			}else if(bible2_type == 25){
				mdb2 = indianhindi_db.getReadableDatabase(); 
			}else if(bible2_type == 26){
				mdb2 = indiantamil_db.getReadableDatabase(); 
			}else if(bible2_type == 27){
				mdb2 = jpnold_db.getReadableDatabase(); 
			}else if(bible2_type == 28){
				mdb2 = reina_db.getReadableDatabase(); 
			}else if(bible2_type == 29){
				mdb2 = tagalog_db.getReadableDatabase(); 
			}else if(bible2_type == 30){
				mdb2 = tkh_db.getReadableDatabase(); 
			}else if(bible2_type == 31){
				mdb2 = web_db.getReadableDatabase(); 
			}
			cursor2 = mdb2.rawQuery("select * from bible  WHERE kwon = '"+kwon+"' and jang = '"+jang+"'", null);
			while(cursor2.moveToNext()){
				addContact2(contactsList2,cursor2.getString(cursor2.getColumnIndex("kwon")),cursor2.getString(cursor2.getColumnIndex("jang")), cursor2.getString(cursor2.getColumnIndex("jul")), cursor2.getString(cursor2.getColumnIndex("content")));
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
		return contactsList2;
	}
    
    private void addContact(List<Sub1_ColumData> contactsList,
			String kwon, String jang, String jul, String content) {
		contactsList.add(new Sub1_ColumData(kwon, jang, jul, content));
	}
    
    private void addContact2(List<Sub1_2_ColumData> contactsList2,
			String kwon, String jang, String jul, String content) {
		contactsList2.add(new Sub1_2_ColumData(kwon, jang, jul, content));
	}
    
	public class Bible_ListAdapter<T extends Sub1_ColumData> extends ArrayAdapter<T> implements OnClickListener{
		   public List<T> contactsList;
		   Button bt_zoom_out, bt_zoom_in;
		   int normal_textSize = 15;
		   public ArrayList<Sub1_2_ColumData> list;
		   public Bible_ListAdapter(Context context, int textViewResourceId, List<T> items,ArrayList<Sub1_2_ColumData> list, Button bt_zoom_out, Button bt_zoom_in) {
			super(context, textViewResourceId, items);
			contactsList = items;
			this.list = list;
			this.bt_zoom_out = bt_zoom_out;
			this.bt_zoom_in = bt_zoom_in;
			
			bt_zoom_out.setOnClickListener(this);
			bt_zoom_in.setOnClickListener(this);
		}
		
		@Override
		public View getView(int position, View view, ViewGroup parent) {
			if(view == null){
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = vi.inflate(R.layout.activity_sub2_listrow, null);
			}
			
			final T contacts = contactsList.get(position);
			if(contacts != null){
				pref = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), Activity.MODE_PRIVATE);
				normal_textSize = pref.getInt("normal_textSize", 15);
				
				TextView txt_jul = (TextView)view.findViewById(R.id.txt_jul);
				txt_jul.setText(contacts.getJul());
				txt_jul.setTextSize(normal_textSize);
				
				TextView txt_content = (TextView)view.findViewById(R.id.txt_content);
				txt_content.setTextSize(normal_textSize);
			
				
				if(listview_sublist.isItemChecked(position)){
					view.setBackgroundColor(Color.parseColor("#00a8ec"));
					txt_jul.setTextColor(Color.parseColor("#ffffff"));
					txt_content.setTextColor(Color.parseColor("#ffffff"));
					if(bible2_type != 0){
						setTextViewColorPartial(txt_content, contacts.getContent() + "\n" + list.get(position).getContent(),
								list.get(position).getContent(), Color.parseColor("#ffffff"));
					}else{
						txt_content.setText(contacts.getContent());
					}
				}else{
					view.setBackgroundColor(Color.parseColor("#00000000"));
					if(bible2_type != 0){
						setTextViewColorPartial(txt_content, contacts.getContent() + "\n" + list.get(position).getContent(),
								list.get(position).getContent(), Color.parseColor("#00a8ff"));
					}else{
						txt_content.setText(contacts.getContent());
					}
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
	
	public class VoicePlayAsync extends AsyncTask<String, Long, Integer> implements OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener,android.widget.SeekBar.OnSeekBarChangeListener, OnErrorListener {
		public int result = -1;
		public String kwon;
		public int jang;
		public VoicePlayAsync(String kwon, int jang) {
			this.kwon = kwon;
			this.jang = jang;
		}
		
		@Override
        protected void onPreExecute() {
			try{
				mediaPlayer = new MediaPlayer();
				layout_progress.setVisibility(View.VISIBLE);
				txt_voice_title.setText(context.getString(R.string.txt_hymn_ready));
				cursor = voicepause_mydb.getReadableDatabase().rawQuery("select * from voice_pause WHERE kwon = '"+bt_kwon.getText().toString()+"' AND jang = '"+bt_jang.getText().toString()+"'", null);
	            if(cursor != null && cursor.moveToFirst()) {
//	            	 Log.i("dsu", "이어서재생========> : " + cursor.getString(1) + "\n날짜 : " + cursor.getString(3));
	            	current_position = cursor.getInt(cursor.getColumnIndex("voice_currentPosition"));
	            }else{
	            	current_position = 0;
//	            	 Log.i("dsu", "처음부터재생=======>");
	            }
	            navigator_handler.removeCallbacks(UpdateTimetask);
	            if(mediaPlayer != null && mediaPlayer.isPlaying() ){
	            	mediaPlayer.stop();
	            }
			}catch(Exception e) {
			}finally{
				if(voicepause_mydb != null){
					voicepause_mydb.close();
				}
			}
            super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(String... params) {
			try{
				String get_data;
				pref = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), Activity.MODE_PRIVATE);
				audio_speed = pref.getInt("audio_speed", audio_speed);
				if(audio_speed == 0){
					get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.txt_voicedata_url2));	
				}else{
					get_data = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.txt_voicedata_url));
				}
				mediaPlayer.setOnBufferingUpdateListener(this);
				mediaPlayer.setOnCompletionListener(this);
				mediaPlayer.setOnErrorListener(this);
				mediaPlayer.setOnPreparedListener(this);
				mediacontroller_progress.setOnSeekBarChangeListener(this);
				
				mediaPlayer.reset();
				String url = get_data+kwon+jang+""+context.getString(R.string.txt_voicedata_type);
	            mediaPlayer.setDataSource(url);
	            mediaPlayer.prepare();
	            
	            mediacontroller_progress.setProgress(0);
				mediacontroller_progress.setMax(100);
				mediaPlayer.seekTo(current_position);
				updateProgressBar();
				return result = 1;
			}catch (Exception e) {
			}
			return result;
		}
		
		@Override
        protected void onProgressUpdate(Long... values) {
        	super.onProgressUpdate(values);
        }
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if(result == 1){
				layout_progress.setVisibility(View.INVISIBLE);
				mediaPlayer.start();
			}else{
				String[] kwon_kbb = {context.getString(R.string.txt_kwon_kbb40),
		        		context.getString(R.string.txt_kwon_kbb41),
		        		context.getString(R.string.txt_kwon_kbb42),
		        		context.getString(R.string.txt_kwon_kbb43),
		        		context.getString(R.string.txt_kwon_kbb44),
		        		context.getString(R.string.txt_kwon_kbb45),
		        		context.getString(R.string.txt_kwon_kbb46),
		        		context.getString(R.string.txt_kwon_kbb47),
		        		context.getString(R.string.txt_kwon_kbb48),
		        		context.getString(R.string.txt_kwon_kbb49),
		        		context.getString(R.string.txt_kwon_kbb50),
		        		context.getString(R.string.txt_kwon_kbb51),
		        		context.getString(R.string.txt_kwon_kbb52),
		        		context.getString(R.string.txt_kwon_kbb53),
		        		context.getString(R.string.txt_kwon_kbb54),
		        		context.getString(R.string.txt_kwon_kbb55),
		        		context.getString(R.string.txt_kwon_kbb56),
		        		context.getString(R.string.txt_kwon_kbb57),
		        		context.getString(R.string.txt_kwon_kbb58),
		        		context.getString(R.string.txt_kwon_kbb59),
		        		context.getString(R.string.txt_kwon_kbb60),
		        		context.getString(R.string.txt_kwon_kbb61),
		        		context.getString(R.string.txt_kwon_kbb62),
		        		context.getString(R.string.txt_kwon_kbb63),
		        		context.getString(R.string.txt_kwon_kbb64),
		        		context.getString(R.string.txt_kwon_kbb65),
		        		context.getString(R.string.txt_kwon_kbb66)};
				
				voicePlayAsync = new VoicePlayAsync(bt_kwon.getText().toString().replace(kwon_kbb[0], "Matthew")
						.replace(kwon_kbb[1], "Mark")
						.replace(kwon_kbb[2], "Luke")
						.replace(kwon_kbb[3], "John")
						.replace(kwon_kbb[4], "Acts")
						.replace(kwon_kbb[5], "Romans")
						.replace(kwon_kbb[6], "Corinthians_up")
						.replace(kwon_kbb[7], "Corinthians_down")
						.replace(kwon_kbb[8], "Galatians")
						.replace(kwon_kbb[9], "Ephesians")
						.replace(kwon_kbb[10], "Philippians")
						.replace(kwon_kbb[11], "Colossians")
						.replace(kwon_kbb[12], "Thessalonians_up")
						.replace(kwon_kbb[13], "Thessalonians_down")
						.replace(kwon_kbb[14], "Timothy_up")
						.replace(kwon_kbb[15], "Timothy_down")
						.replace(kwon_kbb[16], "Titus")
						.replace(kwon_kbb[17], "Philemon")
						.replace(kwon_kbb[18], "Hebrews")
						.replace(kwon_kbb[19], "James")
						.replace(kwon_kbb[20], "Peter_up")
						.replace(kwon_kbb[21], "Peter_down")
						.replace(kwon_kbb[22], "John_one")
						.replace(kwon_kbb[23], "John_two")
						.replace(kwon_kbb[24], "John_three")
						.replace(kwon_kbb[25], "Jude")
						.replace(kwon_kbb[26], "Revelation")
						, Integer.parseInt(bt_jang.getText().toString().replace("장", "")));
				voicePlayAsync.execute();
			}
		}
        
        @Override
		public void onPrepared(MediaPlayer mp) {
    		txt_voice_title.setText(bt_kwon.getText().toString() + jang);
		}
        
		@Override
		public void onBufferingUpdate(MediaPlayer mediaPlayer, int buffering) {
			mediacontroller_progress.setSecondaryProgress(buffering);	
		}
		@Override
		public void onCompletion(MediaPlayer mp) {
			try{
				if(mediaPlayer != null && mediaPlayer.isPlaying() ){
					mediaPlayer.seekTo(0);
					mediaPlayer.stop();
				}else{
					Cursor cursor;
			    	ContentValues cv = new ContentValues();
			    	
			    	cursor = voicepause_mydb.getReadableDatabase().rawQuery("select * from voice_pause WHERE kwon = '"+bt_kwon.getText().toString()+"' AND jang = '"+bt_jang.getText().toString()+"'", null);
		            if(cursor != null && cursor.moveToFirst()) {
		            	int voice_currentPosition = cursor.getInt(cursor.getColumnIndex("voice_currentPosition"));
		            	cv.put("kwon", bt_kwon.getText().toString());
		    			cv.put("voice_currentPosition", 0);
		    			cv.put("jang", bt_jang.getText().toString());
		    			voicepause_mydb.getWritableDatabase().update("voice_pause", cv, "voice_currentPosition" + "=" + voice_currentPosition, null);
//		    			Log.i("dsu", "DB_Update=======>" + ContinueMediaPlayer.mediaPlayer.getCurrentPosition());
		            }else{
		            	cv.put("kwon", bt_kwon.getText().toString());
		            	cv.put("voice_currentPosition", 0);
		            	cv.put("jang", bt_jang.getText().toString());
		            	voicepause_mydb.getWritableDatabase().insert("voice_pause", null, cv);
//		            	Log.i("dsu", "DB_Insert=======>" + ContinueMediaPlayer.mediaPlayer.getCurrentPosition());
		            }
				}
			}catch(Exception e){
			}finally{
				if(voicepause_mydb != null){
					voicepause_mydb.close();	
				}
			}
		}
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			navigator_handler.removeCallbacks(UpdateTimetask);
			if(mediaPlayer != null && mediaPlayer.isPlaying() ){
				onPause_DB_Voice();
			}
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			if(mediaPlayer != null){
				int totalDuration = mediaPlayer.getDuration();
				int currentPosition = TimeUtil.progressToTimer(seekBar.getProgress(), totalDuration);
				// forward or backward to certain seconds
				mediaPlayer.seekTo(currentPosition);
				if (mediaPlayer.isPlaying()){
					mediaPlayer.start();
					bt_pause.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_media_pause));
					updateProgressBar();
			    }else{
			    	mediaPlayer.start();
					bt_pause.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_media_pause));
					updateProgressBar();
			    }
			}
		}

		@Override
		public boolean onError(MediaPlayer mp, int what, int extra) {
			try{
				if(mediaPlayer != null && mediaPlayer.isPlaying() ){
					voice_play_stop();
				}
			}catch(Exception e){
			}
			return false;
		}
	}
	
	public void voice_play_stop(){
    	onPause_DB_Voice();
    	txt_voice_title.setText(context.getString(R.string.txt_hymn_ready));
    	current_time.setText("00:00");
    	max_time.setText("00:00");
    	navigator_handler.removeCallbacks(UpdateTimetask);
    	mediaPlayer.seekTo(0);
    	mediacontroller_progress.setProgress(0);
		if(mediaPlayer != null && mediaPlayer.isPlaying() ){
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
			bt_pause.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_media_play_small));
		}else{
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
			bt_pause.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_media_play_small));
		}
    	if(voicePlayAsync != null){
    		voicePlayAsync.cancel(true);
    	}
    }
	
	public static void updateProgressBar(){
		navigator_handler.postDelayed(UpdateTimetask, 100);
	}
	
	public static Runnable UpdateTimetask = new Runnable() {
		@Override
		public void run() {
			if(mediaPlayer != null){
				if(mediaPlayer != null && mediaPlayer.isPlaying() ){
					bt_pause.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_media_pause_small));
				}else{
					if(mediaPlayer != null && mediaPlayer.isPlaying() ){
						onPause_DB_Voice();
					}
					bt_pause.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_media_play_small));
				}
				long totalDuration = mediaPlayer.getDuration();
				long currentDuration = mediaPlayer.getCurrentPosition();
				// Displaying Total Duration time
				max_time.setText(""+TimeUtil.milliSecondsToTimer(totalDuration));
				// Displaying time completed playing
				current_time.setText(""+TimeUtil.milliSecondsToTimer(currentDuration));
				// Updating progress bar
				int progress = (int)(TimeUtil.getProgressPercentage(currentDuration, totalDuration));
				mediacontroller_progress.setProgress(progress);
				navigator_handler.postDelayed(this, 100);	
			}
		}
	};
	
	public boolean Check_DB(String path){ 
    	File file = new File(path);
        if(file != null){
        	check_db = file.exists();
        }
        return check_db;
    }
	
	public static void onPause_DB_Voice(){
		Cursor cursor;
    	ContentValues cv = new ContentValues();
		try{
    		cursor = voicepause_mydb.getReadableDatabase().rawQuery("select * from voice_pause WHERE kwon = '"+bt_kwon.getText().toString()+"' AND jang = '"+bt_jang.getText().toString()+"'", null);
            if(cursor != null && cursor.moveToFirst()) {
            	int voice_currentPosition = cursor.getInt(cursor.getColumnIndex("voice_currentPosition"));
            	cv.put("kwon", bt_kwon.getText().toString());
    			cv.put("voice_currentPosition", mediaPlayer.getCurrentPosition());
    			cv.put("jang", bt_jang.getText().toString());
    			voicepause_mydb.getWritableDatabase().update("voice_pause", cv, "voice_currentPosition" + "=" + voice_currentPosition, null);
//    			Log.i("dsu", "DB_Update=======>" + ContinueMediaPlayer.mediaPlayer.getCurrentPosition());
            }else{
            	cv.put("kwon", bt_kwon.getText().toString());
            	cv.put("voice_currentPosition", mediaPlayer.getCurrentPosition());
            	cv.put("jang", bt_jang.getText().toString());
            	voicepause_mydb.getWritableDatabase().insert("voice_pause", null, cv);
//            	Log.i("dsu", "DB_Insert=======>" + ContinueMediaPlayer.mediaPlayer.getCurrentPosition());
            }
    	}catch (Exception e) {
		}finally{
			if(voicepause_mydb != null){
				voicepause_mydb.close();	
			}
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
		 try{
//			 if(mediaPlayer != null && mediaPlayer.isPlaying() ){
				 voice_play_stop();
//			 }else{
//				 if(mediaPlayer != null){
//					 mediaPlayer.stop();
//					 mediaPlayer.release();
//					 mediaPlayer = null;
//				 }
//			 }
			 if(voicePlayAsync != null){
				 voicePlayAsync.cancel(true);
			 }
		 }catch(Exception e){
		 }
	 }
	
	public class DownloadDBAsync extends AsyncTask<String, Integer, String>{
		String file_name, url_path, dir_name, bible_type, input_db_path;
		int select_bible;
		Context context;
		HttpURLConnection urlConnection;
		String Response = "fail";
		public DownloadDBAsync(Context context,String file_name, String url_path, String dir_name, String bible_type, int select_bible, String input_db_path){
			this.context = context;
			this.dir_name = dir_name;
			this.file_name = file_name;
			this.url_path = url_path;
			this.bible_type = bible_type;
			this.select_bible = select_bible;
			this.input_db_path = input_db_path;
		}
		
		@Override
		protected String doInBackground(String...params) {
			File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
					+ context.getString(R.string.sub5_txt9));
	        file.mkdirs();
	        try{
	        	long total = 0;
	        	int max = 100;
//	        	Log.i("dsu", "url_path : " + url_path + "\n" + "dir_filename : " + dir_name + file_name);
	        	URL url = new URL(url_path);
	        	HttpURLConnection localHttpURLConnection = (HttpURLConnection)url.openConnection();
	        	localHttpURLConnection.setConnectTimeout(10000);
	        	localHttpURLConnection.setReadTimeout(10000);
	        	localHttpURLConnection.setUseCaches(false);
	        	localHttpURLConnection.connect();
	        	int lenghtOfFile = localHttpURLConnection.getContentLength();
	        	BufferedInputStream localBufferedInputStream = new BufferedInputStream(url.openStream());
	        	FileOutputStream output = new FileOutputStream(dir_name + file_name);
	        	byte data[] = new byte[1024];
	        	while (true){
	        		int count = localBufferedInputStream.read(data);
	        		if (count == -1){
	        			output.flush();
	        			output.close();
	        			localBufferedInputStream.close();
	        			total += count;
		        		publishProgress(max, (int)((total*100)/lenghtOfFile));
	        			return Response = "success";
	        		}
//	        		total += count;
//	        		int percent = (int)(100 * total / lenghtOfFile);
//	        		if (max < percent){
//	        			Integer[] arrayOfInteger = new Integer[1];
//	        			arrayOfInteger[0] = Integer.valueOf(percent);
//	        			publishProgress(arrayOfInteger);
//	        		}
//	        		max = percent;
	        		output.write(data, 0, count);
	        	}
	        }catch (FileNotFoundException localFileNotFoundException){
	        	Log.e("dsu", "FileNotFoundException : " + localFileNotFoundException.getMessage());
	        	Response = "fail";
	        	File delete_file = new File(dir_name + file_name); //경로를 SD카드로 잡은거고 그 안에 있는 A폴더 입니다. 입맛에 따라 바꾸세요.
//				Log.i("dsu", "file_check : " + file.exists());
				if(file.exists() == true){
				delete_file.delete();	
				}
//	        	while (true)
//	        		localFileNotFoundException.printStackTrace();
	        }catch (IOException localIOException){
	        	Log.e("dsu", "localIOException : " + localIOException.getMessage());
	        	Response = "fail";
	        	File delete_file = new File(dir_name + file_name); //경로를 SD카드로 잡은거고 그 안에 있는 A폴더 입니다. 입맛에 따라 바꾸세요.
//				Log.i("dsu", "file_check : " + file.exists());
				if(file.exists() == true){
				delete_file.delete();	
				}
//	        	while (true)
//	                localIOException.printStackTrace();
	        } finally{
            	File fileCacheItem = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
      					+ dir_name + file_name);
    			sendBroadcast(new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fileCacheItem)) );
            }
			return Response;
		}
		
		@SuppressWarnings("deprecation")
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setProgressDrawable(context.getResources().getDrawable(R.drawable.custom_progressbar));
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.setButton(context.getString(R.string.download_cancel_ment), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					if(urlConnection != null){
						urlConnection.disconnect();
					}
					if(downloadDBAsync != null &&(downloadDBAsync.getStatus() == AsyncTask.Status.PENDING ||
							downloadDBAsync.getStatus() == AsyncTask.Status.RUNNING)){
						downloadDBAsync.cancel(true);
						downloadDBAsync = null;
					}
				}
			});
            mProgressDialog.setMessage(context.getString(R.string.downloading_ment));
            mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.show();
        }
		@Override
		protected void onPostExecute(String Response) {
			super.onPostExecute(Response);
			 if(mProgressDialog != null){
				 mProgressDialog.dismiss();
			 }
			 if(Response.equals("success")){
				 String file_check = dir_name + file_name;
				 File file = new File(file_check);
				 if(file.exists() == true){
					 Toast.makeText(context, context.getString(R.string.downloading_complete), Toast.LENGTH_SHORT).show();
					 AlertDialog.Builder alert_internet_status = new AlertDialog.Builder(context);
					 alert_internet_status.setCancelable(true);
					 alert_internet_status.setMessage(context.getString(R.string.downloading_db_apply_ment));
					 alert_internet_status.setPositiveButton(context.getString(R.string.downloading_db_apply), new DialogInterface.OnClickListener() {
						 @Override
						 public void onClick(DialogInterface dialog, int which) {
							 File file = new File(dir_name + file_name);
							 boolean result;
						        if(file!=null&&file.exists()){
						            try {
						                FileInputStream fis = new FileInputStream(file);
						                FileOutputStream newfos = new FileOutputStream(input_db_path);
						                int readcount=0;
						                byte[] buffer = new byte[1024];
						                while((readcount = fis.read(buffer,0,1024))!= -1){
						                    newfos.write(buffer,0,readcount);
						                }
						                newfos.close();
						                fis.close();
						            } catch (Exception e) {
						                e.printStackTrace();
						            } finally{
						            	File fileCacheItem = new File(input_db_path);
						    			sendBroadcast(new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fileCacheItem)) );
						            }
						            result = true;
						        }else{
						            result = false;
						        }
						        if(result == true){
						        	Toast.makeText(context, context.getString(R.string.download_db_apply_complete), Toast.LENGTH_SHORT).show();
						        	settings = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), MODE_PRIVATE);
						        	edit = settings.edit();
						        	edit.putInt(bible_type, select_bible);
						        	edit.commit();
						        	displayList();
						        }else{
						        	Toast.makeText(context, context.getString(R.string.download_db_write_fail), Toast.LENGTH_SHORT).show();
						        }
						 }
					 });
					 alert_internet_status.setNegativeButton(context.getString(R.string.downloading_db_cancle), new DialogInterface.OnClickListener() {
						 @Override
						 public void onClick(DialogInterface dialog, int which) {
							 dialog.dismiss();
						 }
					 });
				         alert_internet_status.show();				 
				 }else{
				 }
			 }
		}
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			 mProgressDialog.setProgress(values[0]);
		}
	}
	
	public static boolean copyFile(File file , String save_file){
        boolean result;
        if(file!=null&&file.exists()){
            try {
                FileInputStream fis = new FileInputStream(file);
                FileOutputStream newfos = new FileOutputStream(save_file);
                int readcount=0;
                byte[] buffer = new byte[1024];
                while((readcount = fis.read(buffer,0,1024))!= -1){
                    newfos.write(buffer,0,readcount);
                }
                newfos.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            result = true;
        }else{
            result = false;
        }
        return result;
    }
	
	public static DownloadDBAsync downloadDBAsync = null;
	public static ProgressDialog mProgressDialog;
}
