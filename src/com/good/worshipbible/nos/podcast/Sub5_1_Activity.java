package com.good.worshipbible.nos.podcast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.admixer.AdAdapter;
import com.admixer.AdInfo;
import com.admixer.AdMixerManager;
import com.admixer.AdView;
import com.admixer.AdViewListener;
import com.admixer.CustomPopup;
import com.admixer.InterstitialAd;
import com.good.worshipbible.nos.R;
import com.good.worshipbible.nos.data.Const;
import com.good.worshipbible.nos.mediaplayer.ContinueMediaPlayer;
import com.good.worshipbible.nos.mediaplayer.CustomMediaPlayer;
import com.good.worshipbible.nos.podcast.adapter.Sub5_1_Adapter;
import com.good.worshipbible.nos.podcast.data.Sub5_1_ColumData;
import com.good.worshipbible.nos.podcast.db.helper.Pause_DBOpenHelper;
import com.good.worshipbible.nos.podcast.db.helper.Sub5_3_DBopenHelper;
import com.good.worshipbible.nos.util.Crypto;
import com.good.worshipbible.nos.util.KoreanTextMatch;
import com.good.worshipbible.nos.util.KoreanTextMatcher;
import com.good.worshipbible.nos.util.PreferenceUtil;
import com.good.worshipbible.nos.util.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

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
public class Sub5_1_Activity extends Activity implements OnItemClickListener, OnClickListener,OnScrollListener, AdViewListener{
	public static Context context;
	public ConnectivityManager connectivityManger;
	public NetworkInfo mobile;
	public NetworkInfo wifi;
	public static Sub5_1_Adapter main_adapter;
	public static ListView listview_main;
	public static int SDK_INT = android.os.Build.VERSION.SDK_INT;
	public static LinearLayout layout_nodata;
	public static RelativeLayout ad_layout;
	public static boolean loadingMore = true;
	public static boolean exeFlag;
	public Handler handler;
	public static int current_page = 1;
	public static int row = 50;
	public static int current_position = 0;
	public boolean flag;
	public Main_ParseAsync main_parseAsync = null;
	public ProgressDialog progressDialog = null;
	public static Sub5_3_DBopenHelper favorite_mydb;
	public static Pause_DBOpenHelper pause_mydb;
	public static NotificationManager notificationManager;
	public static Notification notification;
	public static int noti_state = 1;
	public static Button Bottom_01, Bottom_02, Bottom_05;
	public static TextView txt_main_title;
	public static long TotalRow;
	public static ArrayList<Sub5_1_ColumData> list;
	public static LinearLayout layout_progress;
	public static InterstitialAd interstialAd;
	public boolean retry_alert = false;
	public static TextView textview_write;
	public String view_num;
	public static EditText edit_searcher;
	public static ImageButton bt_home, bt_search_result; 
	public String searchKeyword = "";
	private NativeExpressAdView admobNative;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_sub5_1);
//	Logger.setLogLevel(com.admixer.Logger$LogLevel.Verbose);
	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD, "AX00056EB");
	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD_FULL, "AX00056EC");
	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMIXER, "u6dbtyd1");
	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB, "ca-app-pub-4637651494513698/9745545364");
	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB_FULL, "ca-app-pub-4637651494513698/2222278564");
	context = this;
	view_num = "460";
	if(!PreferenceUtil.getStringSharedData(context, PreferenceUtil.PREF_ISSUBSCRIBED, Const.isSubscribed).equals("true")){
    	addBannerView();    		
	}
//	init_admob_naive();
	textview_write = (TextView) findViewById(R.id.textview_write);
	layout_nodata = (LinearLayout)findViewById(R.id.layout_nodata);
	layout_progress = (LinearLayout)findViewById(R.id.layout_progress);
	listview_main = (ListView)findViewById(R.id.listview_main);
	txt_main_title = (TextView)findViewById(R.id.txt_main_title);
	txt_main_title.setText(context.getString(R.string.app_name));
	txt_main_title.setSelected(false);
	Bottom_01 = (Button)findViewById(R.id.Bottom_01);
	Bottom_02 = (Button)findViewById(R.id.Bottom_02);
	Bottom_05 = (Button)findViewById(R.id.Bottom_05);
	Bottom_01.setOnClickListener(this);
	Bottom_02.setOnClickListener(this);
	Bottom_05.setOnClickListener(this);
	edit_searcher = (EditText)findViewById(R.id.edit_searcher);
	bt_home = (ImageButton)findViewById(R.id.bt_home);
	bt_search_result = (ImageButton)findViewById(R.id.bt_search_result);
	bt_home.setOnClickListener(this);
	bt_search_result.setOnClickListener(this);
	pause_mydb = new Pause_DBOpenHelper(this);
	favorite_mydb = new Sub5_3_DBopenHelper(this);
	list = new ArrayList<Sub5_1_ColumData>();
	list.clear();
	retry_alert = true;
	displaylist();
	seacher_start();
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
		// Custom Popup 종료
    	CustomPopup.stopCustomPopup();
		current_position = 0;
		current_page = 1;
		loadingMore = true;
		exeFlag = false;
		retry_alert = false;
		view_num = "460";
		
		if(main_parseAsync != null){
			main_parseAsync.cancel(true);
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
		if(main_adapter != null){
			main_adapter.notifyDataSetChanged();	
		}
//		Log.i("dsu", "onRestart");
	}
	
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
	public void displaylist(){
		main_parseAsync = new Main_ParseAsync();
		main_parseAsync.execute();
		if (SDK_INT >= Build.VERSION_CODES.HONEYCOMB){ //허니콤 버전에서만 실행 가능한 API 사용}
			listview_main.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		listview_main.setOnScrollListener(this);
		listview_main.setOnItemClickListener(this);
		listview_main.setItemsCanFocus(false);
		listview_main.setFastScrollEnabled(false);
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

	public void parsingRssServer(ArrayList<HashMap<String, String>> list){
		 String id = "";
		 String num = "";
		 String title = "";
		 String provider = "";
		 String imageurl = "";
		 String rssurl = "";
		 String udate = "";
		 String Categenre = "BEST";
		 String search_string;
		 HashMap hash_map;
		 try{
			 String sTag;
			 search_string = URLEncoder.encode(context.getString(R.string.sub5_txt36).trim(), "euc-kr");
			 String urlparam = ("&Search_String=" + search_string);
			 String str = context.getString(R.string.sub5_txt34)+ "?&row=" + row + "&page=" + current_page + urlparam;
//	         Log.i("dsu", "current_page => " + current_page);
	         HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(str).openConnection();
	         HttpURLConnection.setFollowRedirects(true);
	         localHttpURLConnection.setConnectTimeout(15000);
	         localHttpURLConnection.setReadTimeout(15000);
	         localHttpURLConnection.setRequestMethod("GET");
	         localHttpURLConnection.connect();
	         InputStream inputStream = new URL(str).openStream(); //open Stream을 사용하여 InputStream을 생성합니다.
	         XmlPullParserFactory factory = XmlPullParserFactory.newInstance(); 
	         XmlPullParser xpp = factory.newPullParser();
	         xpp.setInput(inputStream, "EUC-KR"); //euc-kr로 언어를 설정합니다. utf-8로 하니깐 깨지더군요
	         int eventType = xpp.getEventType();
	         textview_write.setText("");
	         while (eventType != XmlPullParser.END_DOCUMENT) {
		        	if (eventType == XmlPullParser.START_DOCUMENT) {
		        	}else if (eventType == XmlPullParser.END_DOCUMENT) {
		        	}else if (eventType == XmlPullParser.START_TAG){
		        		sTag = xpp.getName();
		        		if(sTag.equals("End_Page")){
		        			String end_page = xpp.nextText()+"";
		        			if(current_page >= Integer.parseInt(end_page)){
//	        					Log.i("dsu", "end_page : " + end_page);
	        					loadingMore = false;
	        				}
		        		}else if(sTag.equals("Total_Row")){
		        			String total_row = xpp.nextText()+"";
		        			TotalRow = Long.parseLong(total_row);
//		        			Log.i("dsu", "TotalRow : " + TotalRow + "\n<= current_page * row : " + current_page * row);
//		        			if ((current_page * row >= TotalRow) || (row >= TotalRow))
//		        				loadingMore = false;
		        			if (TotalRow == 0L)
		        				loadingMore = false;
		        			if ((current_page == 1) && (TotalRow > 0L)){
		        				search_string.trim().length();
		        			}
		        		}else if(sTag.equals("Content")){
		        			id = xpp.getAttributeValue(null, "id") + "";
		            	}else if(sTag.equals("num")){
		            		num = xpp.nextText()+"";
		            	}else if(sTag.equals("title")){
		            		title = xpp.nextText()+"";
		            	}else if(sTag.equals("provider")){
		            		provider = xpp.nextText()+"";
		            	}else if(sTag.equals("imageurl")){
		            		imageurl = xpp.nextText()+"";
		            	}else if(sTag.equals("rssurl")){
		            		rssurl = xpp.nextText()+"";
		            	}else if(sTag.equals("udate")){
		            		udate = xpp.nextText()+"";
		            	}
		        	} else if (eventType == XmlPullParser.END_TAG){
		            	sTag = xpp.getName();
		            	if(sTag.equals("Content")){
//		            		Log.i("dsu", "파싱종료======>");
		            		hash_map = new HashMap();
		            		hash_map.put("id", id);
		            		hash_map.put("num", num);
		            		hash_map.put("title", title);
		            		hash_map.put("provider", provider);
		            		hash_map.put("imageurl", imageurl);
		            		hash_map.put("rssurl", rssurl);
		            		hash_map.put("udate", udate);
		            		list.add(hash_map);
		            		
//		            		String txt_write = 
//	            					context.getString(R.string.line1) + "\n" + 
//	            					"	" + context.getString(R.string.line2) + " "+id + ");" + "\n" +  
//	            					"	" + context.getString(R.string.line3) +	"\""+num+"\"" + ");" + "\n" + 
//	            					"	" + context.getString(R.string.line4) +	"\""+title+"\"" + ");" + "\n" +
//	            					"	" + context.getString(R.string.line5) +	"\""+provider+"\"" + ");" + "\n" +
//	            					"	" + context.getString(R.string.line6) +	"\""+imageurl+"\"" + ");" + "\n" +
//	            					"	" + context.getString(R.string.line7) +	"\""+rssurl+"\"" + ");" + "\n" +
//	            					"	" + context.getString(R.string.line8) +	"\""+udate+"\"" + ");" + "\n" +
//	            					context.getString(R.string.line9) + "\n" + 
//	            					"  "; 
//		            		textview_write.append(txt_write + "\n");
//		            		File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
//	            					+ "/xml_podcast/");
//	            	        file.mkdirs();
//	            	        
//	            	        try{
//	            		    	  FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()
//	  	            					+ "/xml_podcast/" + current_page + ".php");
//	            		    	  BufferedWriter buw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
//	            		    	  buw.write(textview_write.getText().toString());
//	            		    	  buw.close();
//	            		    	  fos.close();
//	            		      }catch(IOException e){
//	            		    	  Toast.makeText(this, "저장실패", Toast.LENGTH_SHORT).show();
//	            		}finally{
//	            			File fileCacheItem = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
//	            					+ "/xml_podcast/" + current_page + ".php");
//	            			sendBroadcast(new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fileCacheItem)) );
//	            		}
		            	}
		            } else if (eventType == XmlPullParser.TEXT) {
		            }
		            eventType = xpp.next();
		        }
		 }catch (SocketTimeoutException localSocketTimeoutException)
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
         catch (XmlPullParserException localXmlPullParserException)
         {
         }
         catch (NullPointerException NullPointerException)
         {
         }
	}
	
//	public class Main_ParseAsync extends AsyncTask<String, Integer, ArrayList<HashMap<String, String>>>{
//		public ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String,String>>();
//		public Main_ParseAsync(){
//		}
//			@Override
//			protected ArrayList<HashMap<String, String>> doInBackground(String... params) {
//				return xmlReader();
//			}
//			
//			@Override
//	        protected void onPreExecute() {
//	            super.onPreExecute();
//	            layout_progress.setVisibility(View.VISIBLE);
//	            
//	        }
//			@Override
//			protected void onPostExecute(ArrayList<HashMap<String, String>> list) {
//				super.onPostExecute(list);
//				 layout_progress.setVisibility(View.GONE);
//				 try{
//					 Log.i("dsu", "Response : " + list.size());
//					 if(list.size() == 0){
//							layout_nodata.setVisibility(View.VISIBLE);
//							Retry_AlertShow(context.getString(R.string.sub5_txt18));
//						}else{
//							for(int i=0;; i++){
//								if(i >= list.size());
//								while (i > list.size()-1){
//									main_adapter = new Sub5_1_Adapter(context, menuItems);
//									listview_main.setAdapter(main_adapter);
//									layout_nodata.setVisibility(View.GONE);
//									listview_main.setFocusable(true);
//									listview_main.setSelected(true);
//									listview_main.setSelection(current_position);
//									return;
//								}
//								menuItems.add(list.get(i));
//							}
//						}
//				 }catch(NullPointerException e){
//				 }
//			}
//			@Override
//			protected void onProgressUpdate(Integer... values) {
//				super.onProgressUpdate(values);
//			}
//		}
	
	
	public class Main_ParseAsync extends AsyncTask<String, Integer, String>{
		Sub5_1_ColumData sub5_1_data;
		ArrayList<Sub5_1_ColumData> menuItems = new ArrayList<Sub5_1_ColumData>();
		String i;
		String id;
		String num;
		String title;
		String provider;
		String imageurl;
		String rssurl;
		String udate;
		String sprit_title[];
		KoreanTextMatch match1;
		public Main_ParseAsync(){
		}
			@Override
			protected String doInBackground(String... params) {
				String sTag;
				try{
				   String data = context.getString(R.string.url_detail_podcast);
		           String str = data+i+".php?view="+view_num;
//		           Log.i("dsu", "str : " + str);
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
			        			sub5_1_data = new Sub5_1_ColumData();
			            	}else if(sTag.equals("id")){
			            		id = xpp.nextText()+"";
			            	}else if(sTag.equals("num")){
			            		num = xpp.nextText()+"";
			            	}else if(sTag.equals("title")){
			            		title = xpp.nextText()+"";
			            	}else if(sTag.equals("provider")){
			            		provider = xpp.nextText()+"";
			            	}else if(sTag.equals("imageurl")){
			            		imageurl = xpp.nextText()+"";
			            	}else if(sTag.equals("rssurl")){
			            		rssurl = xpp.nextText()+"";
			            	}else if(sTag.equals("udate")){
			            		udate = xpp.nextText()+"";
			            	}
			        	} else if (eventType == XmlPullParser.END_TAG){
			            	sTag = xpp.getName();
			            	if(sTag.equals("Content")){
			            		sub5_1_data.id = id;
			            		sub5_1_data.num = num;
			            		sub5_1_data.title = title;
			            		sub5_1_data.provider = provider;
			            		sub5_1_data.imageurl = imageurl;
			            		sub5_1_data.rssurl = rssurl;
			            		sub5_1_data.udate = udate;
			            		
			            		if(searchKeyword != null && "".equals(searchKeyword.trim()) == false){
			            			KoreanTextMatcher matcher = new KoreanTextMatcher(searchKeyword);
			            			match1 = matcher.match(sub5_1_data.title);
			            			if (match1.success()) {
			            				list.add(sub5_1_data);
			            			}
			            		}else{
			            			list.add(sub5_1_data);
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
				 return rssurl;
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
								main_adapter = new Sub5_1_Adapter(context, menuItems, listview_main, searchKeyword);
								listview_main.setAdapter(main_adapter);
								listview_main.setFocusable(true);
								listview_main.setSelected(true);
								listview_main.setSelection(current_position);
								if(listview_main.getCount() == 0){
									layout_nodata.setVisibility(View.VISIBLE);
								}else{
									layout_nodata.setVisibility(View.GONE);
								}
								layout_nodata.setVisibility(View.GONE);
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
		}else if(view == bt_search_result){
			String search_text = edit_searcher.getText().toString();
			if ((search_text != null) && (search_text.length() > 0)){
				InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);  
	    		inputMethodManager.hideSoftInputFromWindow(edit_searcher.getWindowToken(), 0);
				
	    		list = new ArrayList<Sub5_1_ColumData>();
				list.clear();
				displaylist();
			}else{
				Toast.makeText(context, context.getString(R.string.txt_search_empty), Toast.LENGTH_SHORT).show();
			}
		}else if(view == bt_home){
			current_position = 0;
			exeFlag = false;
			
			list = new ArrayList<Sub5_1_ColumData>();
			list.clear();
			edit_searcher.setText("");
			view_num = "460";	
			displaylist();
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		String id = list.get(position).id;
		String num = list.get(position).num;
		String title = list.get(position).title;
		String provider = list.get(position).provider;
		String imageurl = list.get(position).imageurl;
		String rssurl = list.get(position).rssurl;
		String udate = list.get(position).udate;
		
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
//		Log.i("dsu", "title : " + title + "\n rssurl : " + rssurl);
		
	}

	public void Retry_AlertShow(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(Sub5_1_Activity.this);
		builder.setCancelable(false);
		builder.setMessage(msg);
		builder.setInverseBackgroundForced(true);
		builder.setNeutralButton(context.getString(R.string.sub5_txt19), new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int whichButton){
           	 loadingMore = true;
           	 exeFlag = false;
           	 
           	 main_parseAsync = new Main_ParseAsync();
           	 main_parseAsync.execute();
           	 dialog.dismiss();
			}
		});
		builder.setNegativeButton(context.getString(R.string.sub5_txt20), new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int whichButton){
				dialog.dismiss();
			}
		});
		AlertDialog myAlertDialog = builder.create();
		if(retry_alert) myAlertDialog.show();
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
	
	public static void setNotification(Context context, String title, String enclosure, String pubDate, String image, String description_title) {
		if (SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){ 
			try{
				notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		        Intent intent = new Intent(context, com.good.worshipbible.nos.mediaplayer.CustomMediaPlayer.class);
		        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
		        intent.putExtra("title", title);
				intent.putExtra("enclosure", enclosure);
				intent.putExtra("pubDate", pubDate);
				intent.putExtra("image", image);
				intent.putExtra("description_title", description_title);
				PendingIntent content = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		        Notification.Builder builder = new Notification.Builder(context)
		                .setContentIntent(content)
		                .setSmallIcon(R.drawable.icon64)
		                .setContentTitle(title)
//		                .setContentText("")
		                .setDefaults(Notification.FLAG_AUTO_CANCEL)
		                .setTicker(context.getString(R.string.app_name));
		        notification = builder.build();
		        notificationManager.notify(noti_state, notification);
			}catch(NullPointerException e){
			}
		}
    }
	
	public static void setNotification_continue(Context context, ArrayList<String> array_title, ArrayList<String> array_enclosure, ArrayList<String> array_pubDate, ArrayList<String> array_image, ArrayList<String> array_description_title, int video_num) {
		if (SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){ 
			try{
				notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		        Intent intent = new Intent(context, com.good.worshipbible.nos.mediaplayer.ContinueMediaPlayer.class);
		        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
		        intent.putExtra("array_title", array_title);
				intent.putExtra("array_enclosure", array_enclosure);
				intent.putExtra("array_pubDate", array_pubDate);
				intent.putExtra("array_image", array_image);
				intent.putExtra("array_description_title", array_description_title);
				intent.putExtra("video_num", video_num);
				PendingIntent content = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		        Notification.Builder builder = new Notification.Builder(context)
		                .setContentIntent(content)
		                .setSmallIcon(R.drawable.icon64)
		                .setContentTitle(array_title.get(video_num))
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
	
	public static void onPause_DB_Custom(){
    	Cursor cursor;
    	ContentValues cv = new ContentValues();
		try{
    		cursor = pause_mydb.getReadableDatabase().rawQuery("select * from video_pause WHERE video_title = '"+CustomMediaPlayer.title+"' AND video_pubDate = '"+CustomMediaPlayer.pubDate+"'", null);
            if(cursor != null && cursor.moveToFirst()) {
            	int video_currentPosition = cursor.getInt(cursor.getColumnIndex("video_currentPosition"));
            	cv.put("video_title", CustomMediaPlayer.title);
    			cv.put("video_currentPosition", CustomMediaPlayer.mediaPlayer.getCurrentPosition());
    			cv.put("video_pubDate", CustomMediaPlayer.pubDate);
    			pause_mydb.getWritableDatabase().update("video_pause", cv, "video_currentPosition" + "=" + video_currentPosition, null);
//    			Log.i("dsu", "DB_Update=======>" + CustomMediaPlayer.mediaPlayer.getCurrentPosition());
            }else{
            	cv.put("video_title", CustomMediaPlayer.title);
            	cv.put("video_currentPosition", CustomMediaPlayer.mediaPlayer.getCurrentPosition());
            	cv.put("video_pubDate", CustomMediaPlayer.pubDate);
            	pause_mydb.getWritableDatabase().insert("video_pause", null, cv);
//            	Log.i("dsu", "DB_Insert=======>" + CustomMediaPlayer.mediaPlayer.getCurrentPosition());
            }
    	}catch (Exception e) {
		}finally{
			if(pause_mydb != null){
				pause_mydb.close();	
			}
		}
    }
	
	public static void onPause_DB_Continue(int video_num){
    	Cursor cursor;
    	ContentValues cv = new ContentValues();
		try{
    		cursor = pause_mydb.getReadableDatabase().rawQuery("select * from video_pause WHERE video_title = '"+ContinueMediaPlayer.array_title.get(video_num)+"' AND video_pubDate = '"+ContinueMediaPlayer.array_pubDate.get(video_num)+"'", null);
            if(cursor != null && cursor.moveToFirst()) {
            	int video_currentPosition = cursor.getInt(cursor.getColumnIndex("video_currentPosition"));
            	cv.put("video_title", ContinueMediaPlayer.array_title.get(video_num));
    			cv.put("video_currentPosition", ContinueMediaPlayer.mediaPlayer.getCurrentPosition());
    			cv.put("video_pubDate", ContinueMediaPlayer.array_pubDate.get(video_num));
    			pause_mydb.getWritableDatabase().update("video_pause", cv, "video_currentPosition" + "=" + video_currentPosition, null);
//    			Log.i("dsu", "DB_Update=======>" + ContinueMediaPlayer.mediaPlayer.getCurrentPosition());
            }else{
            	cv.put("video_title", ContinueMediaPlayer.array_title.get(video_num));
            	cv.put("video_currentPosition", ContinueMediaPlayer.mediaPlayer.getCurrentPosition());
            	cv.put("video_pubDate", ContinueMediaPlayer.array_pubDate.get(video_num));
            	pause_mydb.getWritableDatabase().insert("video_pause", null, cv);
//            	Log.i("dsu", "DB_Insert=======>" + ContinueMediaPlayer.mediaPlayer.getCurrentPosition());
            }
    	}catch (Exception e) {
		}finally{
			if(pause_mydb != null){
				pause_mydb.close();	
			}
		}
    }
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if(keyCode == KeyEvent.KEYCODE_BACK){
			 if(!flag){
				 Toast.makeText(context, context.getString(R.string.sub5_txt21) , Toast.LENGTH_SHORT).show();
			 flag = true;
			 handler.sendEmptyMessageDelayed(0, 2000);
			 return false;
			 }else{
				 Sub5_1_Activity.setNotification_Cancel();
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
		}
	}
