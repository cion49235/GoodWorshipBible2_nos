package com.good.worshipbible.nos.activity;
import java.util.ArrayList;
import java.util.List;

import com.admixer.AdAdapter;
import com.admixer.AdInfo;
import com.admixer.AdMixerManager;
import com.admixer.AdView;
import com.admixer.AdViewListener;
import com.good.worshipbible.nos.R;
import com.good.worshipbible.nos.data.Const;
import com.good.worshipbible.nos.data.Sub3_ColumData;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_Sub3_1;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_Sub3_2;
import com.good.worshipbible.nos.util.KoreanTextMatch;
import com.good.worshipbible.nos.util.KoreanTextMatcher;
import com.good.worshipbible.nos.util.PreferenceUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Sub3_1_Activity extends Activity implements OnClickListener, OnItemClickListener, AdViewListener{
	public static ListView list_bible_song;
	public static BibleSong_Adaper<Sub3_ColumData> bibleSong_adapter;
	public DBOpenHelper_Sub3_1 tongilHymn_mydb;
	public DBOpenHelper_Sub3_2 twentyoneHymn_mydb;
	public SQLiteDatabase mdb;
	private Cursor cursor;
	public List<Sub3_ColumData>contactsList;
	int SDK_INT = android.os.Build.VERSION.SDK_INT;
	public static EditText edit_seacher;
	public String searchKeyword = "";
	public static Button bt_hymn_category;
	public static SharedPreferences settings,pref;
	public static Editor edit;
	public Context context;
	public static AlertDialog alertDialog;
	public static int hymn_category = 1;
	public static Button top_01, top_02, top_03, top_04, top_05, top_06, top_07, top_08;
	public static RelativeLayout ad_layout;
	KoreanTextMatch match1;
	private NativeExpressAdView admobNative;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub3_1);
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
		edit_seacher = (EditText)findViewById(R.id.edit_seacher);
		bt_hymn_category = (Button)findViewById(R.id.bt_hymn_category);
		bt_hymn_category.setOnClickListener(this);
		settings = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), MODE_PRIVATE);
		hymn_category = settings.getInt("hymn_category", hymn_category);
		top_01 = (Button)findViewById(R.id.top_01);
    	top_02 = (Button)findViewById(R.id.top_02);
    	top_03 = (Button)findViewById(R.id.top_03);
    	top_04 = (Button)findViewById(R.id.top_04);
    	top_05 = (Button)findViewById(R.id.top_05);
    	top_06 = (Button)findViewById(R.id.top_06);
    	top_07 = (Button)findViewById(R.id.top_07);
    	top_08 = (Button)findViewById(R.id.top_08);
		
    	top_01.setOnClickListener(this);
    	top_02.setOnClickListener(this);
    	top_03.setOnClickListener(this);
    	top_04.setOnClickListener(this);
    	top_05.setOnClickListener(this);
    	top_06.setOnClickListener(this);
    	top_07.setOnClickListener(this);
    	top_08.setOnClickListener(this);
		SeaCherKeyword();
		displayList(); 
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
//		 admobNative.destroy();
		 if(tongilHymn_mydb != null){
			 tongilHymn_mydb.close();
		 }
		 if(twentyoneHymn_mydb != null){
			 twentyoneHymn_mydb.close();
		 }
	 }
	 
	 @Override
	protected void onRestart() {
		super.onRestart();
//		displayList();
//		if(bibleSong_adapter != null){
//			bibleSong_adapter.notifyDataSetChanged();
//		}
	}

	public void SeaCherKeyword(){
		edit_seacher.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable arg0) {}
	 		   
			public void beforeTextChanged(CharSequence s, int start, int count, int after){
			}
			public void onTextChanged(CharSequence s, int start, int before, int count){
				try {
					searchKeyword = s.toString().toLowerCase();
					displayList(); 
					if(bibleSong_adapter != null){
						bibleSong_adapter.notifyDataSetChanged();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void displayList() {
    	contactsList = getContactsList();
    	bibleSong_adapter = new BibleSong_Adaper<Sub3_ColumData>(
 			   this,R.layout.activity_sub3_listlow, contactsList);
    	list_bible_song = (ListView)findViewById(R.id.list_bible_song);
    	list_bible_song.setAdapter(bibleSong_adapter);
    	list_bible_song.setOnItemClickListener(this);
    	if (SDK_INT >= Build.VERSION_CODES.HONEYCOMB){ //허니콤 버전에서만 실행 가능한 API 사용}
    		list_bible_song.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    	}
    	list_bible_song.setItemsCanFocus(false);
    	list_bible_song.setFastScrollEnabled(true);
    	if(hymn_category == 0){
    		bt_hymn_category.setText(context.getString(R.string.txt_hymn_category1));
    	}else if(hymn_category == 1){
    		bt_hymn_category.setText(context.getString(R.string.txt_hymn_category2));
    	}
    }

	private List<Sub3_ColumData> getContactsList() {
			try{
				contactsList = new ArrayList<Sub3_ColumData>();
				tongilHymn_mydb = new DBOpenHelper_Sub3_1(this);
				twentyoneHymn_mydb = new DBOpenHelper_Sub3_2(this);
				if(hymn_category == 0){
					mdb = tongilHymn_mydb.getReadableDatabase();
					cursor = mdb.rawQuery("select * from tongil_hymn", null);
				}else if(hymn_category == 1){
					mdb = twentyoneHymn_mydb.getReadableDatabase();
					cursor = mdb.rawQuery("select * from twentyone_hymn", null);
				}
//				startManagingCursor(cursor);
				while(cursor.moveToNext()){
					addContact(contactsList, cursor.getInt(cursor.getColumnIndex("_id")), cursor.getString(cursor.getColumnIndex("title")), cursor.getInt(cursor.getColumnIndex("description")));
				}
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(tongilHymn_mydb != null){
					tongilHymn_mydb.close();				
				}
				if(twentyoneHymn_mydb != null){
					twentyoneHymn_mydb.close();
				}
			}
			return contactsList;
		}

	public void addContact(List<Sub3_ColumData> contactsList,int id, String bibleSong_title, int bibleSong_description) {
		if(searchKeyword != null && "".equals(searchKeyword.trim()) == false){
			KoreanTextMatcher matcher = new KoreanTextMatcher(searchKeyword);
			match1 = matcher.match(bibleSong_title);
			if (match1.success()) {
				contactsList.add(new Sub3_ColumData(id, bibleSong_title, bibleSong_description));
			}
		}else{
			contactsList.add(new Sub3_ColumData(id, bibleSong_title, bibleSong_description));
		}
	 }
	
	@Override
	public void onClick(View view) {
		if(view == bt_hymn_category){
			settings = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), MODE_PRIVATE);
    		edit = settings.edit();
    		String[] hymn_alert ={context.getString(R.string.txt_hymn_category1),
    								context.getString(R.string.txt_hymn_category2)};
    		new AlertDialog.Builder(this)
        	.setTitle(context.getString(R.string.txt_hymn_category0))
        	.setSingleChoiceItems(hymn_alert, hymn_category, new DialogInterface.OnClickListener(){
        		@Override
        		public void onClick(DialogInterface dialog, int which) {
        			if(which == 0){
        				hymn_category = 0;
        				edit.putInt("hymn_category", hymn_category);
        			}else if(which == 1){
        				hymn_category = 1;
        				edit.putInt("hymn_category", hymn_category);
        			}
        			dialog.dismiss();
        			edit.commit();
        			displayList();
        		}
        	}).show();
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		contactsList = getContactsList();
		int id = contactsList.get(position).get_id();
		String title = contactsList.get(position).getTitle();
		int description = contactsList.get(position).getDescription();
		Intent intent = new Intent(this, Sub3_2_Activity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("id", id);
		intent.putExtra("title", title);
		intent.putExtra("description", description);
		startActivity(intent);
	}
	
	public class BibleSong_Adaper<T extends Sub3_ColumData> extends ArrayAdapter<T>{
		   public List<T> contactsList;
		   public BibleSong_Adaper(Context context, int textViewResourceId, List<T> items) {
			super(context, textViewResourceId, items);
			contactsList = items;
		}
		
		@Override
		public View getView(int position, View view, ViewGroup parent) {
			if(view == null){
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = vi.inflate(R.layout.activity_sub3_listlow, null);
			}
			
			final T contacts = contactsList.get(position);
			if(contacts != null){
				TextView bibleSong_title = (TextView)view.findViewById(R.id.bibleSong_title);
//				bibleSong_title.setText(contacts.getTitle());
				setTextViewColorPartial(bibleSong_title, contacts.getTitle(), searchKeyword, Color.RED);
			}
			return view;
		}
		public void setTextViewColorPartial(TextView view, String fulltext, String subtext, int color) {
			try{
				view.setText(fulltext, TextView.BufferType.SPANNABLE);
				Spannable str = (Spannable) view.getText();
				int i = fulltext.indexOf(subtext);
				str.setSpan(new ForegroundColorSpan(color), i, i + subtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}catch (IndexOutOfBoundsException e) {
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
	
	@Override
	 public void onBackPressed() {
		 super.onBackPressed();
	 }
}