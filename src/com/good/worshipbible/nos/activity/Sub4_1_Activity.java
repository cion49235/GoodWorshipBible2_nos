package com.good.worshipbible.nos.activity;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.good.worshipbible.nos.data.Sub4_ColumData;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_Sub4;
import com.good.worshipbible.nos.util.ImageLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;


public class Sub4_1_Activity extends Activity implements OnClickListener,OnItemClickListener, AdViewListener{
	public static Button bt_write;
	public SQLiteDatabase mdb;
	private DBOpenHelper_Sub4 favorite_mydb;
	private Cursor cursor;
	public List<Sub4_ColumData>contactsList;
	public MyListAdapter<Sub4_ColumData> adapter;
	public ListView listview_mylist;
	public static LinearLayout layout_listview_mylist, layout_nodata;
	public Context context;
	int SDK_INT = android.os.Build.VERSION.SDK_INT;
	public static Button top_01, top_02, top_03, top_04, top_05, top_06, top_07, top_08;
	public static RelativeLayout ad_layout;
	private NativeExpressAdView admobNative;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub4_1);
        AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD, "AX00056EB");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD_FULL, "AX00056EC");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMIXER, "u6dbtyd1");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB, "ca-app-pub-4637651494513698/9745545364");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB_FULL, "ca-app-pub-4637651494513698/2222278564");
        context = this;
        addBannerView();
//        init_admob_naive();
    	layout_nodata = (LinearLayout)findViewById(R.id.layout_nodata);
        bt_write = (Button)findViewById(R.id.bt_write);
        top_01 = (Button)findViewById(R.id.top_01);
        top_02 = (Button)findViewById(R.id.top_02);
    	top_03 = (Button)findViewById(R.id.top_03);
    	top_04 = (Button)findViewById(R.id.top_04);
    	top_05 = (Button)findViewById(R.id.top_05);
    	top_06 = (Button)findViewById(R.id.top_06);
    	top_07 = (Button)findViewById(R.id.top_07);
    	top_08 = (Button)findViewById(R.id.top_08);
    	
        bt_write.setOnClickListener(this);
        top_01.setOnClickListener(this);
    	top_02.setOnClickListener(this);
    	top_03.setOnClickListener(this);
    	top_04.setOnClickListener(this);
    	top_05.setOnClickListener(this);
    	top_06.setOnClickListener(this);
    	top_07.setOnClickListener(this);
    	top_08.setOnClickListener(this);
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
//    	admobNative.destroy();
    	if(favorite_mydb != null){
    		favorite_mydb.close();
    	}
//    	Log.i("dsu", "adDestroy");
    }
    @Override
    protected void onStart() {
    	super.onStart();
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    }

    public void displayList() {
    	List<Sub4_ColumData>contactsList = getContactsList();
    	adapter = new MyListAdapter<Sub4_ColumData>(
 			   this,R.layout.activity_sub4_listrow,contactsList);
    	listview_mylist = (ListView)findViewById(R.id.listview_mylist);
    	listview_mylist.setAdapter(adapter);
    	listview_mylist.setOnItemClickListener(this);
    	if (SDK_INT >= Build.VERSION_CODES.HONEYCOMB){ //허니콤 버전에서만 실행 가능한 API 사용}
    		listview_mylist.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    	}
    	listview_mylist.setItemsCanFocus(false);
    	listview_mylist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    	if(listview_mylist.getCount() == 0){
    		layout_nodata.setVisibility(View.VISIBLE);
    	}else{
    		layout_nodata.setVisibility(View.GONE);
    	}
    }
    
    @Override
	public void onClick(View view) {
		if(view ==  bt_write){
			Intent intent = new Intent(this, Sub4_2_Activity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
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
	public void onItemClick(AdapterView<?> arg0, View view, final int position, long arg3) {
    	contactsList = getContactsList();
    	String[] mylist_alert ={context.getString(R.string.bible_mylist_txt3),
    			context.getString(R.string.bible_mylist_txt4),
    			context.getString(R.string.bible_mylist_txt5),
    			context.getString(R.string.bible_mylist_txt6)};
    	final int id = contactsList.get(position).get_id();
    	final String kwon = contactsList.get(position).getKwon();
    	final String jang = contactsList.get(position).getJang();
    	final String jul = contactsList.get(position).getJul();
    	final String content = contactsList.get(position).getContent();
    	new AlertDialog.Builder(this)
    	.setTitle(kwon +" "+ jang)
    	.setItems(mylist_alert, new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			if(which == 0){
    				Intent intent = new Intent(Intent.ACTION_SEND);
    				intent.setType("text/plain");    
    				intent.addCategory(Intent.CATEGORY_DEFAULT);
    				intent.putExtra(Intent.EXTRA_TEXT, kwon +" "+ jang + "\n"+content);
    				startActivity(Intent.createChooser(intent, context.getString(R.string.txt_share)));
    			}else if(which == 1){
    				new AlertDialog.Builder(Sub4_1_Activity.this)
    		    	.setTitle(kwon +" "+ jang)
    		  		.setMessage(contactsList.get(position).getContent())
    		  		.setCancelable(true)
    		  		.setPositiveButton(R.string.bible_mylist_txt7, new DialogInterface.OnClickListener() {
    		  			@Override
    		  			public void onClick(DialogInterface dialog, int which) {
    		  			  dialog.dismiss();
    		  			}
    		  		}).show();
    			}else if(which == 2){
    				Intent intent = new Intent(Sub4_1_Activity.this, Sub4_2_Activity.class);
    				intent.putExtra("edit", true);
    				intent.putExtra("_id", contactsList.get(position).get_id());
    				intent.putExtra("kwon", contactsList.get(position).getKwon());
    				intent.putExtra("jang", contactsList.get(position).getJang());
    				intent.putExtra("jul", contactsList.get(position).getJul());
    				intent.putExtra("content", contactsList.get(position).getContent());
    				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    				startActivity(intent);
    			}else if(which == 3){
    				favorite_mydb.getWritableDatabase().delete("my_list", "_id" + "=" + id, null);
    				if(favorite_mydb != null) favorite_mydb.close();
    				Toast.makeText(Sub4_1_Activity.this, R.string.bible_mylist_txt2, Toast.LENGTH_SHORT).show();
    				displayList();
    			}
    		}
    	}).show();
    }
    private List<Sub4_ColumData> getContactsList() {
		try{
			contactsList = new ArrayList<Sub4_ColumData>();
			favorite_mydb = new DBOpenHelper_Sub4(this);
			mdb = favorite_mydb.getReadableDatabase();
			cursor = mdb.rawQuery("select * from my_list order by _id desc", null);
//			startManagingCursor(cursor);
			while(cursor.moveToNext()){
				int idx = cursor.getColumnIndex("_id");
				int id = cursor.getInt(idx);
				addContact(contactsList,id,cursor.getString(cursor.getColumnIndex("kwon")),cursor.getString(cursor.getColumnIndex("jang")), cursor.getString(cursor.getColumnIndex("jul")), cursor.getString(cursor.getColumnIndex("content")));
			}
			return contactsList;
		}finally{
			if(favorite_mydb != null){
				favorite_mydb.close();	
			}
		}
	}
    private void addContact(List<Sub4_ColumData> contactsList, int id,
			String kwon, String jang, String jul, String content) {
		contactsList.add(new Sub4_ColumData(id, kwon,jang, jul, content));
	}
	public class MyListAdapter<T extends Sub4_ColumData> extends ArrayAdapter<T>{
		   public List<T> contactsList;
		   ImageLoader imgLoader = new ImageLoader(getApplicationContext());
		   
		   public MyListAdapter(Context context, int textViewResourceId, List<T> items) {
			super(context, textViewResourceId, items);
			contactsList = items;
		}
		
		@Override
		public View getView(int position, View view, ViewGroup parent) {
			if(view == null){
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = vi.inflate(R.layout.activity_sub4_listrow, null);
			}
			final T contacts = contactsList.get(position);
			if(contacts != null){
				String kwon = contacts.getKwon();
				TextView txt_kwon = (TextView)view.findViewById(R.id.txt_kwon);
				txt_kwon.setText(kwon +" "+ contacts.getJang());
				
				TextView txt_jul = (TextView)view.findViewById(R.id.txt_jul);
				txt_jul.setText(contacts.getJul());
				
				TextView txt_content = (TextView)view.findViewById(R.id.txt_content);
				txt_content.setText(contacts.getContent());
			}
			return view;
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
