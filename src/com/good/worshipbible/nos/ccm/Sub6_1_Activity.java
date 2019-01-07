package com.good.worshipbible.nos.ccm;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.admixer.AdAdapter;
import com.admixer.AdInfo;
import com.admixer.AdMixerManager;
import com.admixer.AdView;
import com.admixer.AdViewListener;
import com.admixer.InterstitialAd;
import com.good.worshipbible.nos.R;
import com.good.worshipbible.nos.ccm.adapter.Sub6_1_Adapter;
import com.good.worshipbible.nos.ccm.data.Main_Data;
import com.good.worshipbible.nos.ccm.db.helper.Sub6_2_DBopenHelper;
import com.good.worshipbible.nos.data.Const;
import com.good.worshipbible.nos.podcast.db.helper.Pause_DBOpenHelper;
import com.good.worshipbible.nos.util.Crypto;
import com.good.worshipbible.nos.util.KoreanTextMatch;
import com.good.worshipbible.nos.util.KoreanTextMatcher;
import com.good.worshipbible.nos.util.PreferenceUtil;
import com.good.worshipbible.nos.util.Utils;
import com.good.worshipbible.nos.videoplayer.CustomVideoPlayer;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.skplanet.tad.AdFloating;
import com.skplanet.tad.AdFloatingListener;
import com.skplanet.tad.AdRequest.ErrorCode;
import com.skplanet.tad.AdSlot;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
public class Sub6_1_Activity extends Activity implements OnItemClickListener, OnClickListener, AdViewListener, AdFloatingListener, OnScrollListener{
	public static Context context;
	public ConnectivityManager connectivityManger;
	public NetworkInfo mobile;
	public NetworkInfo wifi;
	public static Sub6_1_Adapter main_adapter;
	public static ListView listview_main;
	public static int SDK_INT = android.os.Build.VERSION.SDK_INT;
	public static LinearLayout layout_nodata;
	public static RelativeLayout ad_layout;
	public static boolean loadingMore = true;
	public static boolean exeFlag;
	public Handler handler = new Handler();
	public static int start_index = 1;
	public static int itemsPerPage = 50;
	public static int current_position = 0;
	public boolean flag;
	public Main_ParseAsync main_parseAsync = null;
	public ProgressDialog progressDialog = null;
	public static Sub6_2_DBopenHelper favorite_mydb;
	public static Pause_DBOpenHelper pause_mydb;
	public static NotificationManager notificationManager;
	public static Notification notification;
	public static int noti_state = 1;
	public static TextView txt_main_title;
	public static int TotalRow;;
	public static ArrayList<Main_Data> list;
	public static LinearLayout layout_progress;
	public static InterstitialAd interstialAd;
	public static LinearLayout action_layout;
	public static Button bt_all_select,bt_play_media, bt_play_video, bt_favorite;
	public Cursor cursor;
	public static AlertDialog alertDialog;
	public static int category_which = 0;
	public SharedPreferences settings,pref;
	public Editor edit;
	public boolean retry_alert = false;
	public static Button bt_ccm_favorite;
	int ccm_channel_which = 1;
	public static String uploader;
	public static AdFloating mAdFloating;
	public String num;
	public static EditText edit_searcher;
	public static ImageButton bt_home, bt_search_result; 
	public String searchKeyword = "";
	private NativeExpressAdView admobNative;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_sub6_1);
//	Logger.setLogLevel(com.admixer.Logger$LogLevel.Verbose);
	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD, "AX00056EB");
	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD_FULL, "AX00056EC");
	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMIXER, "u6dbtyd1");
	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB, "ca-app-pub-4637651494513698/9745545364");
	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB_FULL, "ca-app-pub-4637651494513698/2222278564");
	context = this;
	num = "455";
	if(!PreferenceUtil.getStringSharedData(context, PreferenceUtil.PREF_ISSUBSCRIBED, Const.isSubscribed).equals("true")){
    	addBannerView();
    	create_mAdFloating();
	}
//	init_admob_naive();
	layout_nodata = (LinearLayout)findViewById(R.id.layout_nodata);
	layout_progress = (LinearLayout)findViewById(R.id.layout_progress);
	action_layout = (LinearLayout)findViewById(R.id.action_layout);
	listview_main = (ListView)findViewById(R.id.listview_main);
	txt_main_title = (TextView)findViewById(R.id.txt_main_title);
	txt_main_title.setText(context.getString(R.string.sub6_txt7));
	txt_main_title.setSelected(false);
	bt_all_select = (Button)findViewById(R.id.bt_all_select);
	bt_play_media = (Button)findViewById(R.id.bt_play_media);
	bt_play_video = (Button)findViewById(R.id.bt_play_video);
	bt_favorite = (Button)findViewById(R.id.bt_favorite);
	bt_ccm_favorite = (Button)findViewById(R.id.bt_ccm_favorite);
	edit_searcher = (EditText)findViewById(R.id.edit_searcher);
	bt_home = (ImageButton)findViewById(R.id.bt_home);
	bt_search_result = (ImageButton)findViewById(R.id.bt_search_result);
	bt_home.setOnClickListener(this);
	bt_search_result.setOnClickListener(this);
	bt_all_select.setOnClickListener(this);
	bt_play_media.setOnClickListener(this);
	bt_play_video.setOnClickListener(this);
	bt_favorite.setOnClickListener(this);
	bt_ccm_favorite.setOnClickListener(this);
	pause_mydb = new Pause_DBOpenHelper(this);
	favorite_mydb = new Sub6_2_DBopenHelper(this);
	list = new ArrayList<Main_Data>();
	list.clear();
	retry_alert = true;
	
	seacher_start();
	displaylist();
	exit_handler();
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
	
	public void create_mAdFloating(){
		// AdFloating 객체를 생성합니다. 
		mAdFloating = new AdFloating(this); 
		// AdFloating 상태를 모니터링 할 listner 를 등록합니다. listener 에 대한 내용은 아래 참조 mAdFloating.setListener(mListener);   
		// 할당받은 ClientID 를 설정합니다. 
		mAdFloating.setClientId("AX00056ED");   
		// 할당받은 Slot 번호를 설정합니다.
		mAdFloating.setSlotNo(AdSlot.FLOATING);  
		// TestMode 여부를 설정합니다. 
		mAdFloating.setTestMode(false);  
		// 광고를 삽입할 parentView 를 설정합니다.
		mAdFloating.setParentWindow(getWindow()); 
		// 광고를 요청 합니다. 로드시 설정한 값들이 유효한지 판단한 후 광고를 수신합니다.
		// 광고 요청에 대한 결과는 설정한 listener 를 통해 알 수 있습니다.
		mAdFloating.setListener(this);
		// 일일 광고 노출 제한을 설정합니다.
//		mAdFloating.setDailyFrequency(5);
		if (mAdFloating != null) {
			try{
				mAdFloating.loadAd(null);
			}catch(Exception e){ 
				e.printStackTrace(); 
			} 
		}
		handler.postDelayed(new Runnable() {
			 @Override
			 public void run() {
				 if (mAdFloating.isReady()) {
						try {
							mAdFloating.showAd(180, 200);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
			 }
		 },3000);
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
		retry_alert = false;
//		current_position = 0;
//    	start_index = 1;
//		loadingMore = true;
//		exeFlag = false;
		num = "455";
		
		settings = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_ccm_channel_string), MODE_PRIVATE);
		edit = settings.edit();
		ccm_channel_which = 0;
		edit.putInt("ccm_channel_which", ccm_channel_which);
		edit.commit();
		
		if (mAdFloating != null) {
			mAdFloating.destroyAd();
		}
		
		edit_searcher.setText("");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		layout_progress.setVisibility(View.GONE);
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		if(com.good.worshipbible.nos.videoplayer.ContinueMediaPlayer.mediaPlayer.isPlaying()){
			 com.good.worshipbible.nos.videoplayer.ContinueMediaPlayer.mediaPlayer.stop();
		 }
		if(main_adapter != null){
			main_adapter.notifyDataSetChanged();	
		}
//		Log.i("dsu", "onRestart");
	}
	
	public void onConfigurationChanged(android.content.res.Configuration newConfig) {
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	    	if (mAdFloating != null) {
				try {
					mAdFloating.moveAd(320, 50);
				} catch (Exception e) {
				}
			}
	    }else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	    	if (mAdFloating != null) {
				try {
					mAdFloating.moveAd(180, 200);
				} catch (Exception e) {
				}
			}
	    }
	    super.onConfigurationChanged(newConfig);
	};

	public void exit_handler(){
    	handler = new Handler(){
    		@Override
    		public void handleMessage(Message msg) {
    			if(msg.what == 0){
    				flag = false;
    			}
    		}
    	};
    }
	
	public void seacher_start(){
		edit_searcher.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable arg0) {
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try {
					searchKeyword = s.toString().toLowerCase();
					Log.e("dsu", "검색어 : " + searchKeyword);
				} catch (Exception e) {
				}
			}
		});
	}
	
	public void displaylist(){
		main_parseAsync = new Main_ParseAsync();
		main_parseAsync.execute();
		if (SDK_INT >= Build.VERSION_CODES.HONEYCOMB){ //허니콤 버전에서만 실행 가능한 API 사용}
			listview_main.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		listview_main.setOnItemClickListener(this);
		listview_main.setOnScrollListener(this);
		listview_main.setItemsCanFocus(false);
		listview_main.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}
	
	public class Main_ParseAsync extends AsyncTask<String, Integer, String>{
		String Response;
		Main_Data main_data;
		ArrayList<Main_Data> menuItems = new ArrayList<Main_Data>();
		String i;
		int _id;
		String id;
		String title;
		String thumbnail_hq;
		String sprit_title[];
		KoreanTextMatch match1;
		public Main_ParseAsync(){
		}
			@Override
			protected String doInBackground(String... params) {
				String sTag;
				try{
				   String data = context.getString(R.string.url_detail_ccm);
		           String str = data+i+".php?view="+num; 
		           HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(str).openConnection();
		           HttpURLConnection.setFollowRedirects(false);
		           localHttpURLConnection.setConnectTimeout(15000);
		           localHttpURLConnection.setReadTimeout(15000); 
		           localHttpURLConnection.setRequestMethod("GET");
		           localHttpURLConnection.connect();
		           InputStream inputStream = new URL(str).openStream(); //open Stream을 사용하여 InputStream을 생성합니다.
		           XmlPullParserFactory factory = XmlPullParserFactory.newInstance(); 
		           XmlPullParser xpp = factory.newPullParser();
		           xpp.setInput(inputStream, "EUC-KR"); //euc-kr로 언어를 설정합니다. utf-8로 하니깐 깨지더군요
		           int eventType = xpp.getEventType();
		           while (eventType != XmlPullParser.END_DOCUMENT) {
			        	if (eventType == XmlPullParser.START_DOCUMENT) {
			        	}else if (eventType == XmlPullParser.END_DOCUMENT) {
			        	}else if (eventType == XmlPullParser.START_TAG){
			        		sTag = xpp.getName();
			        		if(sTag.equals("Content")){
			        			main_data = new Main_Data();
			        			_id = Integer.parseInt(xpp.getAttributeValue(null, "id") + "");
			            	}else if(sTag.equals("videoid")){
			        			Response = xpp.nextText()+"";
			            	}else if(sTag.equals("subject")){
			            		title = xpp.nextText()+"";
			            		sprit_title = title.split("-");
			            	}else if(sTag.equals("thumb")){
			            		thumbnail_hq = xpp.nextText()+"";
			            	}
			        	} else if (eventType == XmlPullParser.END_TAG){
			            	sTag = xpp.getName();
			            	if(sTag.equals("Content")){
			            		main_data._id = _id;
			            		main_data.id = Response;
			            		main_data.title = title;
			            		main_data.category = context.getString(R.string.app_name);
			            		main_data.thumbnail_hq = thumbnail_hq;
			            		if(searchKeyword != null && "".equals(searchKeyword.trim()) == false){
			            			KoreanTextMatcher matcher = new KoreanTextMatcher(searchKeyword);
			            			match1 = matcher.match(main_data.title);
			            			if (match1.success()) {
			            				list.add(main_data);
			            			}
			            		}else{
			            			list.add(main_data);
			            		}
			            	}
			            } else if (eventType == XmlPullParser.TEXT) {
			            }
			            eventType = xpp.next();
			        }
		         }
				 catch (SocketTimeoutException localSocketTimeoutException)
		         {
		         }
		         catch (ClientProtocolException localClientProtocolException)
		         {
		         }
		         catch (IOException localIOException)
		         {
		         }
		         catch (Resources.NotFoundException localNotFoundException)
		         {
		         }
		         catch (NullPointerException NullPointerException)
		         {
		         }
				 catch (Exception e) 
				 {
				 }
				 return Response;
			}
			
			@Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            i="2";
	            layout_progress.setVisibility(View.VISIBLE);
	        }
			@Override
			protected void onPostExecute(String Response) {
				super.onPostExecute(Response);
				layout_progress.setVisibility(View.GONE);
				try{
					if(Response != null){
						for(int i=0;; i++){
							if(i >= list.size()){
//							while (i > list.size()-1){
								main_adapter = new Sub6_1_Adapter(context, menuItems, listview_main, searchKeyword);
								listview_main.setAdapter(main_adapter);
								listview_main.setFocusable(true);
								listview_main.setSelected(true);
								listview_main.setSelection(current_position);
								layout_nodata.setVisibility(View.GONE);
								action_layout.setVisibility(View.GONE);
								return;
							}
							menuItems.add(list.get(i));
						}
					}else{
						layout_nodata.setVisibility(View.VISIBLE);
						Retry_AlertShow(context.getString(R.string.sub6_txt8));
					}
				}catch(NullPointerException e){
				}
			}
			@Override
			protected void onProgressUpdate(Integer... values) {
				super.onProgressUpdate(values);
			}
		}
	@Override
	public void onClick(View view) {
		if(view == bt_all_select){
			if(bt_all_select.isSelected()){
				bt_all_select.setSelected(false);
				bt_all_select.setText(context.getString(R.string.sub6_txt2));
				for(int i=0; i < listview_main.getCount(); i++){
					listview_main.setItemChecked(i, false);
				}
				action_layout.setVisibility(View.GONE);
			}else{
				bt_all_select.setSelected(true);
				bt_all_select.setText(context.getString(R.string.sub6_txt9));
				for(int i=0; i < listview_main.getCount(); i++){
					listview_main.setItemChecked(i, true);
				}
			}
		}else if(view == bt_play_media){
			SparseBooleanArray sba = listview_main.getCheckedItemPositions();
			ArrayList<String> array_id = new ArrayList<String>();
			ArrayList<String> array_title = new ArrayList<String>();
			ArrayList<String> array_category = new ArrayList<String>();
			ArrayList<String> array_thumbnail_hq = new ArrayList<String>();
			ArrayList<String> array_duration = new ArrayList<String>();
			if(sba.size() != 0){
				for(int i = listview_main.getCount() -1; i>=0; i--){
					if(sba.get(i)){
						Main_Data main_data = (Main_Data)main_adapter.getItem(i);
						String id = main_data.id;
						String title = main_data.title;
						String category = main_data.category;
						String thumbnail_hq = main_data.thumbnail_hq;
						String duration = main_data.duration;
//						Log.i("dsu", "videoid :" + id);
						array_id.add(id);
						array_title.add(title);
						array_category.add(category);
						array_thumbnail_hq.add(thumbnail_hq);
						array_duration.add(duration);
						sba = listview_main.getCheckedItemPositions();
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
			SparseBooleanArray sba = listview_main.getCheckedItemPositions();
			ArrayList<String> array_videoid = new ArrayList<String>();
			ArrayList<String> array_subject = new ArrayList<String>();
			if(sba.size() != 0){
				for(int i = listview_main.getCount() -1; i>=0; i--){
					if(sba.get(i)){
						Main_Data main_data = (Main_Data)main_adapter.getItem(i);
						String videoid = main_data.id;
						String subject = main_data.title;
						array_videoid.add(videoid);
						array_subject.add(subject);
						sba = listview_main.getCheckedItemPositions();
					}
				}
				Intent intent = new Intent(context, CustomVideoPlayer.class);
				intent.putExtra("array_videoid", array_videoid);
				intent.putExtra("array_subject", array_subject);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}
		}else if(view == bt_favorite){
			try{
				SparseBooleanArray sba = listview_main.getCheckedItemPositions();
				boolean check_DB = false;
				if(sba.size() != 0){
					for(int i = listview_main.getCount() -1; i>=0; i--){
						if(sba.get(i)){
							Main_Data main_data = (Main_Data)main_adapter.getItem(i);
							String id = main_data.id;
							String title = main_data.title;
							String category = main_data.category;
							String thumbnail_hq = main_data.thumbnail_hq;
							String duration = main_data.duration;
							cursor = favorite_mydb.getReadableDatabase().rawQuery("select * from favorite_list", null);
							while(cursor.moveToNext()){
								if((cursor.getString(cursor.getColumnIndex("id")).equals(id) && cursor.getString(cursor.getColumnIndex("title")).equals(title))){
									check_DB = true;
									break;
								}
							}
							if(check_DB == false){
								ContentValues cv = new ContentValues();
			    				cv.put("id", id);
			    				cv.put("title", title);
			    				cv.put("category", category);
			    				cv.put("thumbnail_hq", thumbnail_hq);
			    				cv.put("duration", duration);
			    				favorite_mydb.getWritableDatabase().insert("favorite_list", null, cv);
								sba = listview_main.getCheckedItemPositions();
								Toast.makeText(context, title + "\n"+context.getString(R.string.sub6_txt10), Toast.LENGTH_SHORT).show();
							}else{
								Toast.makeText(context, title + "\n"+context.getString(R.string.sub6_txt11), Toast.LENGTH_SHORT).show();
							}
						}
					}
				}
			}catch(Exception e){
			}finally{
				if(cursor != null){
					cursor.close();	
				}
				if(main_adapter != null){
					main_adapter.notifyDataSetChanged();
				}
			}			
		}else if(view == bt_ccm_favorite){
			Intent intent = new Intent(this, Sub6_2_Activity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}else if(view == bt_search_result){
			String search_text = edit_searcher.getText().toString();
			if ((search_text != null) && (search_text.length() > 0)){
				InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);  
	    		inputMethodManager.hideSoftInputFromWindow(edit_searcher.getWindowToken(), 0);
				
	    		list = new ArrayList<Main_Data>();
				list.clear();
				displaylist();
			}else{
				Toast.makeText(context, context.getString(R.string.txt_search_empty), Toast.LENGTH_SHORT).show();
			}
		}else if(view == bt_home){
			current_position = 0;
			start_index = 1;
			loadingMore = true;
			exeFlag = false;
			
			list = new ArrayList<Main_Data>();
			list.clear();
			edit_searcher.setText("");
			num = "455";	
			displaylist();
			
			settings = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_ccm_channel_string), MODE_PRIVATE);
			edit = settings.edit();
			pref = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_ccm_channel_string), Activity.MODE_PRIVATE);
			category_which = pref.getInt("category_which", 0);
			edit.putInt("category_which", 0);
			edit.commit();
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		int selectd_count = 0;
    	SparseBooleanArray sba = listview_main.getCheckedItemPositions();
		if(sba.size() != 0){
			for(int i = listview_main.getCount() -1; i>=0; i--){
				if(sba.get(i)){
					sba = listview_main.getCheckedItemPositions();
					selectd_count++;
				}
			}
		}
		if(selectd_count == 0){
			action_layout.setVisibility(View.GONE);
		}else{
			action_layout.setVisibility(View.VISIBLE);
//			if(mAdFloating.isShown()){
//				mAdFloating.closeAd();
//			}
		}
		if(main_adapter != null){
			main_adapter.notifyDataSetChanged();
		}
	}
	
	public void Retry_AlertShow(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(Sub6_1_Activity.this);
		builder.setCancelable(false);
		builder.setMessage(msg);
		builder.setInverseBackgroundForced(true);
		builder.setNeutralButton(context.getString(R.string.sub6_txt12), new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int whichButton){
				 current_position = 0;
            	 loadingMore = true;
            	 exeFlag = false;
            	 main_parseAsync = new Main_ParseAsync();
            	 main_parseAsync.execute();
            	 dialog.dismiss();
			}
		});
		builder.setNegativeButton(context.getString(R.string.sub6_txt13), new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int whichButton){
             	 dialog.dismiss();
			}
		});
		AlertDialog myAlertDialog = builder.create();
		if(retry_alert) myAlertDialog.show();
	}
	
	public static void setNotification_continue(Context context, ArrayList<String> array_music, ArrayList<String> array_videoid, ArrayList<String> array_playtime, ArrayList<String> array_imageurl, ArrayList<String> array_artist, int video_num) {
    	if (SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){ 
			try{
				notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		        Intent intent = new Intent(context, com.good.worshipbible.nos.videoplayer.ContinueMediaPlayer.class);
		        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
		        intent.putExtra("array_music", array_music);
				intent.putExtra("array_videoid", array_videoid);
				intent.putExtra("array_playtime", array_playtime);
				intent.putExtra("array_imageurl", array_imageurl);
				intent.putExtra("array_artist", array_artist);
				intent.putExtra("video_num", video_num);
				PendingIntent content = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		        Notification.Builder builder = new Notification.Builder(context)
		                .setContentIntent(content)
		                .setSmallIcon(R.drawable.icon64)
		                .setContentTitle(array_music.get(video_num)+ " - " + array_artist.get(video_num))
//		                .setContentText("")
		                .setDefaults(Notification.FLAG_AUTO_CANCEL)
		                .setTicker(context.getString(R.string.app_name));
		        notification = builder.build();
		        notificationManager.notify(noti_state, notification);
			}catch(NullPointerException e){
			}
		}
    }
	
	public static void setNotification_Cancel(){
    	if(notificationManager != null) notificationManager.cancel(noti_state);
    }
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if(keyCode == KeyEvent.KEYCODE_BACK){
			 if(!flag){
				 Toast.makeText(context, context.getString(R.string.sub6_txt14) , Toast.LENGTH_SHORT).show();
			 flag = true;
			 handler.sendEmptyMessageDelayed(0, 2000);
			 return false;
			 }else{
				 if(com.good.worshipbible.nos.videoplayer.ContinueMediaPlayer.mediaPlayer.isPlaying()){
					 com.good.worshipbible.nos.videoplayer.ContinueMediaPlayer.mediaPlayer.stop();
				 }
				 Sub6_1_Activity.setNotification_Cancel();
				 finish();
			 }
            return false;	 
		 }
		return super.onKeyDown(keyCode, event);
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
	
	//** AdFloatingListener 이벤트들 *************
	@Override
	public void onAdWillLoad() {

	}

	@Override
	public void onAdResized() {
	
	}
	
	@Override
	public void onAdResizeClosed() {
	
	}
	
	@Override
	public void onAdPresentScreen() {
//		Log.i("dsu", "플로팅배너onAdPresentScreen");
	}
	
	@Override
	public void onAdLoaded() {
//		Log.i("dsu", "플로팅배너onAdLoaded");
	}
	
	@Override
	public void onAdLeaveApplication() {
//		Log.i("dsu", "플로팅배너onAdLeaveApplication");
	}
	
	@Override
	public void onAdExpanded() {
		
	}
	
	@Override
	public void onAdExpandClosed() {
		
	}
	
	@Override
	public void onAdDismissScreen() {
//		Log.i("dsu", "플로팅배너onAdDismissScreen");
	}

	@Override
	public void onAdFailed(ErrorCode arg0) {
//		Log.i("dsu", "플로팅배너onAdFailed : " + arg0);
	}

	@Override
	public void onAdClicked() {
	}

	@Override
	public void onAdClosed(boolean arg0) {
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if(view == listview_main){
			if(totalItemCount != 0 && firstVisibleItem  > 2 ){
				listview_main.setFastScrollEnabled(true);
			}else{
				listview_main.setFastScrollEnabled(false);
			}
		}
	}
}
