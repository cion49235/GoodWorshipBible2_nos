package com.good.worshipbible.nos.podcast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.admixer.AdAdapter;
import com.admixer.AdInfo;
import com.admixer.AdMixerManager;
import com.admixer.AdView;
import com.admixer.AdViewListener;
import com.good.worshipbible.nos.R;
import com.good.worshipbible.nos.data.Const;
import com.good.worshipbible.nos.mediaplayer.CustomMediaPlayer;
import com.good.worshipbible.nos.podcast.adapter.Sub5_2_Adapter;
import com.good.worshipbible.nos.podcast.data.Sub5_2_ColumData;
import com.good.worshipbible.nos.podcast.db.helper.Sub5_4_DBopenHelper;
import com.good.worshipbible.nos.podcast.db.helper.Sub5_5_DBopenHelper;
import com.good.worshipbible.nos.util.PreferenceUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
public class Sub5_2_Activity extends Activity implements OnItemClickListener, OnClickListener, AdViewListener, OnScrollListener{
	public static Context context;
	public ConnectivityManager connectivityManger;
	public NetworkInfo mobile;
	public NetworkInfo wifi;
	public static Sub5_2_Adapter sub_adapter;
	public static ListView listview_sub;
	public int SDK_INT = android.os.Build.VERSION.SDK_INT;
	public static LinearLayout layout_nodata;
	public static RelativeLayout ad_layout;
	public static ArrayList<Sub5_2_ColumData> list;
	public Sub_ParseAsync sub_parseAsync = null;
	public ProgressDialog mProgressDialog;
	public static TextView txt_sub_title;
	public static Sub5_5_DBopenHelper continue_mydb;
	public static Sub5_4_DBopenHelper down_mydb;
	public static DownloadAsync downloadAsync = null;
	public static String title;
	String provider;
	static String rssurl;
	public static ProgressBar progressBar;
	public static Button Bottom_01, Bottom_02, Bottom_05;
	public boolean retry_alert = false;
//	SharedPreferences settings,pref;
//	Editor edit;
	public Handler handler = new Handler();
	private NativeExpressAdView admobNative;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_sub5_2);
//	Logger.setLogLevel(com.admixer.Logger$LogLevel.Verbose);
	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD, "AX00056EB");
	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD_FULL, "AX00056EC");
	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMIXER, "u6dbtyd1");
	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB, "ca-app-pub-4637651494513698/9745545364");
	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB_FULL, "ca-app-pub-4637651494513698/2222278564");
	context = this;
	if(!PreferenceUtil.getStringSharedData(context, PreferenceUtil.PREF_ISSUBSCRIBED, Const.isSubscribed).equals("true")){
    	addBannerView();
	}
//	init_admob_naive();
	continue_mydb = new Sub5_5_DBopenHelper(this);
	down_mydb = new Sub5_4_DBopenHelper(this);
	layout_nodata = (LinearLayout)findViewById(R.id.layout_nodata);
	listview_sub = (ListView)findViewById(R.id.listview_sub);
	list = new ArrayList<Sub5_2_ColumData>();
	list.clear();
	Bottom_01 = (Button)findViewById(R.id.Bottom_01);
	Bottom_02 = (Button)findViewById(R.id.Bottom_02);
	Bottom_05 = (Button)findViewById(R.id.Bottom_05);
	Bottom_01.setOnClickListener(this);
	Bottom_02.setOnClickListener(this);
	Bottom_05.setOnClickListener(this);
	title = getIntent().getStringExtra("title");
	provider = getIntent().getStringExtra("provider");
	rssurl = getIntent().getStringExtra("rssurl");
//	Log.i("dsu", "rssurl : " + rssurl);
	txt_sub_title = (TextView)findViewById(R.id.txt_sub_title);
	txt_sub_title.setText(title);
	txt_sub_title.setSelected(false);
	retry_alert = true;	
	displaylist();
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
		retry_alert = false;
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		if(sub_adapter != null){
			sub_adapter.notifyDataSetChanged();	
		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		if(mProgressDialog != null){
			mProgressDialog.dismiss();
		}
	}
	
	public void onConfigurationChanged(android.content.res.Configuration newConfig) {
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	    }else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	    }
	    super.onConfigurationChanged(newConfig);
	};
	
	public void displaylist(){
//		sub_adapter = new SubAdapter(context, down_mydb);
		sub_parseAsync = new Sub_ParseAsync();
		sub_parseAsync.execute();
		if (SDK_INT >= Build.VERSION_CODES.HONEYCOMB){ //허니콤 버전에서만 실행 가능한 API 사용}
			listview_sub.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		listview_sub.setOnItemClickListener(this);
		listview_sub.setItemsCanFocus(false);
		listview_sub.setOnScrollListener(this);
	}		
	public class Sub_ParseAsync extends AsyncTask<String, Integer, String>{
		String Response;;
		Sub5_2_ColumData sub_data;
		String enclosure;
		String pubDate;
		String image;
		int dataPercent;
		ArrayList<Sub5_2_ColumData> menuItems = new ArrayList<Sub5_2_ColumData>();
		public Sub_ParseAsync(){
		}
			@Override
			protected String doInBackground(String... params) {
				String sTag;
		         try{
		           String str = rssurl;
//		           Log.i("dsu", "current_page => " + current_page);
		           HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(str).openConnection();
		           HttpURLConnection.setFollowRedirects(true);
		           localHttpURLConnection.setConnectTimeout(3000);
		           localHttpURLConnection.setReadTimeout(3000);
		           localHttpURLConnection.setRequestMethod("GET");
		           localHttpURLConnection.connect();
		           InputStream inputStream = new URL(str).openStream(); //open Stream을 사용하여 InputStream을 생성합니다.
		           XmlPullParserFactory factory = XmlPullParserFactory.newInstance(); 
		           XmlPullParser xpp = factory.newPullParser();
		           xpp.setInput(inputStream, "UTF-8"); //euc-kr로 언어를 설정합니다. utf-8로 하니깐 깨지더군요
		           int eventType = xpp.getEventType();
		           while (eventType != XmlPullParser.END_DOCUMENT) {
			        	if (eventType == XmlPullParser.START_DOCUMENT) {
			        	}else if (eventType == XmlPullParser.END_DOCUMENT) {
			        	}else if (eventType == XmlPullParser.START_TAG){
			        		sTag = xpp.getName();
			        		if(sTag.equals("title")){
			        			sub_data = new Sub5_2_ColumData();
			        			Response = xpp.nextText();
			        			Log.i("dsu", "xpp : " +  Response);
			        			dataPercent++;
			        			publishProgress(dataPercent);
			        		}else if(sTag.equals("enclosure")){
			        			enclosure = xpp.getAttributeValue(null, "url");
			            	}else if(sTag.equals("pubDate")){
			            		pubDate = xpp.nextText();
			            	}else if(sTag.equals("itunes:image")) {
			            		image = xpp.getAttributeValue(null, "href");
        	        		}
			        	} else if (eventType == XmlPullParser.END_TAG){
			            	sTag = xpp.getName();
			            	if(sTag.equals("item")){
//			            		Log.i("dsu", "파싱종료======>");
			            		sub_data.title = Response;
			            		sub_data.provider = provider;
			            		sub_data.enclosure = enclosure;
			            		String format_pubdate = setDate(pubDate);
			            		sub_data.pubDate = format_pubdate;
			            		sub_data.image = image;
			            		sub_data.description_title = title;
			            		list.add(sub_data);
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
		         catch (XmlPullParserException localXmlPullParserException)
		         {
		         }
		         catch (NullPointerException NullPointerException)
		         {
		         }
		         return Response;
			}
			
			@Override
	        protected void onPreExecute() {
				dataPercent = 0;
	            mProgressDialog = new ProgressDialog(context);
	            mProgressDialog.setProgressDrawable(context.getResources().getDrawable(R.drawable.custom_progressbar));
	            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));	
	            mProgressDialog.setMessage(context.getString(R.string.sub5_txt23));
	            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	            mProgressDialog.setCancelable(true);
	            mProgressDialog.setProgress(dataPercent);
	            mProgressDialog.setMax(100);
	            mProgressDialog.show();
	            super.onPreExecute();
	            
	        }
			@Override
			protected void onPostExecute(String Response) {
				super.onPostExecute(Response);
//				Log.i("dsu", "Response : " + Response);
				if(mProgressDialog != null){
					mProgressDialog.dismiss();
				}
				try{
					if(Response != null){
						for(int i=0;; i++){
							if(i >= list.size());
							while(i > list.size()-1){
								sub_adapter = new Sub5_2_Adapter(context, down_mydb, menuItems);
								listview_sub.setAdapter(sub_adapter);
								listview_sub.setFocusable(true);
								listview_sub.setSelected(true);
								layout_nodata.setVisibility(View.GONE);
								return;
							}
							menuItems.add(list.get(i));
						}
					}else{
						layout_nodata.setVisibility(View.VISIBLE);
						Retry_AlertShow(context.getString(R.string.sub5_txt18));
					}
				}catch(NullPointerException e){
				}
			}
			@Override
			protected void onProgressUpdate(Integer... values) {
				super.onProgressUpdate(values);
				mProgressDialog.setProgress(values[0]);
			}
	}
	
	public String setDate(String paramString){
		String str1 = paramString;
		try{
	      paramString = paramString.replace("  ", " ");
	      String str2 = new SimpleDateFormat("yy.MM.dd.HH.mm.ss").format(new Date(Date.parse(paramString)));
	      str1 = str2;
	      return str1;
	    }catch (NullPointerException localNullPointerException){
	    	while (true)
	        str1 = "00.00.00 00.00.00";
	    }catch (IllegalArgumentException localIllegalArgumentException){
	    	while (true)
	        str1 = setDate2(paramString);
	    }
	}
	
	public String setDate2(String paramString){
		String str1 = paramString;
	    try{
	    	String str2 = paramString.replace("KST", "+0900");
	    	String str3 = new SimpleDateFormat("yy.MM.dd.HH.mm.ss").format(new Date(Date.parse(str2)));
	    	str1 = str3;
	    	return str1;
	    }catch (NullPointerException localNullPointerException){
	    	while (true)
	        str1 = "00.00.00 00.00.00";
	    }catch (IllegalArgumentException localIllegalArgumentException){
	    	while (true)
	        str1 = "00.00.00 00.00.00";
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
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		Sub5_2_ColumData sub_data = (Sub5_2_ColumData)sub_adapter.getItem(position);
		String title = sub_data.title;
		String enclosure = sub_data.enclosure;
		String pubDate = sub_data.pubDate;
		String image = sub_data.image;
		String description_title = sub_data.description_title;
		
		connectivityManger = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo network_info = connectivityManger.getActiveNetworkInfo();
		if(network_info != null && network_info.isConnected() == true){ //LTE 13 (NETWORK_TYPE_LTE), eHRPD 14 (NETWORK_TYPE_EHRPD), CDMA 6 (NETWORK_TYPE_EVDO_A)
			Intent intent = new Intent(context, CustomMediaPlayer.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("title", title);
			intent.putExtra("enclosure", enclosure);
			intent.putExtra("pubDate", setDateTrim(pubDate));
			intent.putExtra("image", image);
			intent.putExtra("description_title", description_title);
			startActivity(intent);
		}else{
			Toast.makeText(context, context.getString(R.string.download_data_connection_ment), Toast.LENGTH_LONG).show();
		}
		
//		if(mAdFloating.isShown()){
//			mAdFloating.closeAd();
//		}
		
//		settings = getSharedPreferences(context.getString(R.string.txt_sub_activity4), MODE_PRIVATE);
//		edit = settings.edit();
//		edit.putString("title", title);
//		edit.putString("pubDate", pubDate);
//		edit.commit();
//		sub_adapter.notifyDataSetChanged();
	}
	
	public String setDateTrim(String paramString){
		return paramString.substring(0, 8);
	}

	public void AlertShow(String msg) {
        AlertDialog.Builder alert_internet_status = new AlertDialog.Builder(context);
         alert_internet_status.setCancelable(true);
         alert_internet_status.setMessage(msg);
         alert_internet_status.setPositiveButton(context.getString(R.string.sub5_txt24),
                 new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         dialog.dismiss(); // 닫기
                         finish();
                     }
                 });
         alert_internet_status.show();
	}
	
	public void Retry_AlertShow(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(Sub5_2_Activity.this);
		builder.setCancelable(false);
		builder.setMessage(msg);
		builder.setInverseBackgroundForced(true);
		builder.setNeutralButton(context.getString(R.string.sub5_txt19), new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int whichButton){
				sub_parseAsync = new Sub_ParseAsync();
				sub_parseAsync.execute();
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
	public void onClickedAd(String arg0, AdView arg1) {
	}

	@Override
	public void onFailedToReceiveAd(int arg0, String arg1, AdView arg2) {
	}

	@Override
	public void onReceivedAd(String arg0, AdView arg1) {
	}

	public static void updateStatus(int position, int max, String enclosure, String description_title){
		try{
			View view = listview_sub.getChildAt(position - listview_sub.getFirstVisiblePosition());
		    if (view != null){
		    	progressBar = (ProgressBar)view.findViewById(R.id.progress_down);
		    	progressBar.setFocusable(false);
		    	progressBar.setProgress(max);
		    	if(description_title.equals(title)){
			    	progressBar.setVisibility(View.VISIBLE);
		    	}else{
			    	progressBar.setVisibility(View.INVISIBLE);
		    	}
		    	if (max == 100){
		    		progressBar.setVisibility(View.INVISIBLE);
		    	}
		    }
		}catch(Exception e){
		}
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if(view == listview_sub){
			if(totalItemCount != 0 && firstVisibleItem  > 2 ){
				listview_sub.setFastScrollEnabled(true);
			}else{
				listview_sub.setFastScrollEnabled(false);
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
}
