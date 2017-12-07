package com.good.worshipbible.nos.activity;

import java.util.ArrayList;
import java.util.List;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.admixer.AdAdapter;
import com.admixer.AdInfo;
import com.admixer.AdMixerManager;
import com.admixer.AdView;
import com.admixer.AdViewListener;
import com.good.worshipbible.nos.R;
import com.good.worshipbible.nos.data.Sub7_ColumData;
import com.good.worshipbible.nos.db.helper.DBOpenHelper_Sub7;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

public class Sub7_Activity extends Activity implements OnClickListener, OnItemClickListener, AdViewListener {
	public static Button bt_main_description, bt_sub_description;
	public static Button Bottom_01, Bottom_02;
	private DBOpenHelper_Sub7 mydb;
	public SQLiteDatabase mdb;
	public List<Sub7_ColumData>contactsList;
	public Bible_GidomunAdapter<Sub7_ColumData> adapter;
	public SharedPreferences settings,pref;
	public Editor edit;
	public Cursor cursor;
	public static Button top_01, top_02, top_03, top_04, top_05, top_06, top_07, top_08;
	public Context context;
	public static ListView listview_gidomunlist;
	int SDK_INT = android.os.Build.VERSION.SDK_INT;
	public static AlertDialog alertDialog;
	int main_gidomun_which = 0;
	int kyodok_gidomun_which = 0;
	int simbang_gidomun_which = 0;
	int old_kyodok_gidomun_which = 0;
	public static RelativeLayout ad_layout;
	private NativeExpressAdView admobNative;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub7_1);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD, "AX00056EB");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD_FULL, "AX00056EC");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMIXER, "u6dbtyd1");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB, "ca-app-pub-4637651494513698/9745545364");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB_FULL, "ca-app-pub-4637651494513698/2222278564");
		context = this;
		addBannerView();
//		init_admob_naive();
		bt_main_description = (Button)findViewById(R.id.bt_main_description);
		bt_sub_description = (Button)findViewById(R.id.bt_sub_description);
		Bottom_01 = (Button)findViewById(R.id.Bottom_01);
		Bottom_02 = (Button)findViewById(R.id.Bottom_02);
		top_01 = (Button)findViewById(R.id.top_01);
    	top_02 = (Button)findViewById(R.id.top_02);
    	top_03 = (Button)findViewById(R.id.top_03);
    	top_04 = (Button)findViewById(R.id.top_04);
    	top_05 = (Button)findViewById(R.id.top_05);
    	top_06 = (Button)findViewById(R.id.top_06);
    	top_07 = (Button)findViewById(R.id.top_07);
    	top_08 = (Button)findViewById(R.id.top_08);
    	
		bt_main_description.setOnClickListener(this);
		bt_sub_description.setOnClickListener(this);
		Bottom_01.setOnClickListener(this);
		Bottom_02.setOnClickListener(this);
		top_01.setOnClickListener(this);
    	top_02.setOnClickListener(this);
    	top_03.setOnClickListener(this);
    	top_04.setOnClickListener(this);
    	top_05.setOnClickListener(this);
    	top_06.setOnClickListener(this);
    	top_07.setOnClickListener(this);
    	top_08.setOnClickListener(this);
    	
    	String[] gidomun_main_description = {
    		context.getString(R.string.txt_gidomun_description00),
    		context.getString(R.string.txt_gidomun_description01),
    		context.getString(R.string.txt_gidomun_description02),
    		context.getString(R.string.txt_gidomun_description03),
    		context.getString(R.string.txt_gidomun_description04),
    		context.getString(R.string.txt_gidomun_description05),
    		context.getString(R.string.txt_gidomun_description06),
    		context.getString(R.string.txt_gidomun_description07),
    		context.getString(R.string.txt_gidomun_description08),
    		context.getString(R.string.txt_gidomun_description09)
    	};
    	
    	String[] gidomun_kyodok_description = {
				context.getString(R.string.txt_kyodok_description00),
				context.getString(R.string.txt_kyodok_description01),
				context.getString(R.string.txt_kyodok_description02),
				context.getString(R.string.txt_kyodok_description03),
				context.getString(R.string.txt_kyodok_description04),
				context.getString(R.string.txt_kyodok_description05),
				context.getString(R.string.txt_kyodok_description06),
				context.getString(R.string.txt_kyodok_description07),
				context.getString(R.string.txt_kyodok_description08),
				context.getString(R.string.txt_kyodok_description09),
				context.getString(R.string.txt_kyodok_description10),
				
				context.getString(R.string.txt_kyodok_description11),
				context.getString(R.string.txt_kyodok_description12),
				context.getString(R.string.txt_kyodok_description13),
				context.getString(R.string.txt_kyodok_description14),
				context.getString(R.string.txt_kyodok_description15),
				context.getString(R.string.txt_kyodok_description16),
				context.getString(R.string.txt_kyodok_description17),
				context.getString(R.string.txt_kyodok_description18),
				context.getString(R.string.txt_kyodok_description19),
				context.getString(R.string.txt_kyodok_description20),
				
				context.getString(R.string.txt_kyodok_description21),
				context.getString(R.string.txt_kyodok_description22),
				context.getString(R.string.txt_kyodok_description23),
				context.getString(R.string.txt_kyodok_description24),
				context.getString(R.string.txt_kyodok_description25),
				context.getString(R.string.txt_kyodok_description26),
				context.getString(R.string.txt_kyodok_description27),
				context.getString(R.string.txt_kyodok_description28),
				context.getString(R.string.txt_kyodok_description29),
				context.getString(R.string.txt_kyodok_description30),
				
				context.getString(R.string.txt_kyodok_description31),
				context.getString(R.string.txt_kyodok_description32),
				context.getString(R.string.txt_kyodok_description33),
				context.getString(R.string.txt_kyodok_description34),
				context.getString(R.string.txt_kyodok_description35),
				context.getString(R.string.txt_kyodok_description36),
				context.getString(R.string.txt_kyodok_description37),
				context.getString(R.string.txt_kyodok_description38),
				context.getString(R.string.txt_kyodok_description39),
				context.getString(R.string.txt_kyodok_description40),
				
				context.getString(R.string.txt_kyodok_description41),
				context.getString(R.string.txt_kyodok_description42),
				context.getString(R.string.txt_kyodok_description43),
				context.getString(R.string.txt_kyodok_description44),
				context.getString(R.string.txt_kyodok_description45),
				context.getString(R.string.txt_kyodok_description46),
				context.getString(R.string.txt_kyodok_description47),
				context.getString(R.string.txt_kyodok_description48),
				context.getString(R.string.txt_kyodok_description49),
				context.getString(R.string.txt_kyodok_description50),
				
				context.getString(R.string.txt_kyodok_description51),
				context.getString(R.string.txt_kyodok_description52),
				context.getString(R.string.txt_kyodok_description53),
				context.getString(R.string.txt_kyodok_description54),
				context.getString(R.string.txt_kyodok_description55),
				context.getString(R.string.txt_kyodok_description56),
				context.getString(R.string.txt_kyodok_description57),
				context.getString(R.string.txt_kyodok_description58),
				context.getString(R.string.txt_kyodok_description59),
				context.getString(R.string.txt_kyodok_description60),
				
				context.getString(R.string.txt_kyodok_description61),
				context.getString(R.string.txt_kyodok_description62),
				context.getString(R.string.txt_kyodok_description63),
				context.getString(R.string.txt_kyodok_description64),
				context.getString(R.string.txt_kyodok_description65),
				context.getString(R.string.txt_kyodok_description66),
				context.getString(R.string.txt_kyodok_description67),
				context.getString(R.string.txt_kyodok_description68),
				context.getString(R.string.txt_kyodok_description69),
				context.getString(R.string.txt_kyodok_description70),
				
				context.getString(R.string.txt_kyodok_description71),
				context.getString(R.string.txt_kyodok_description72),
				context.getString(R.string.txt_kyodok_description73),
				context.getString(R.string.txt_kyodok_description74),
				context.getString(R.string.txt_kyodok_description75),
				context.getString(R.string.txt_kyodok_description76),
				context.getString(R.string.txt_kyodok_description77),
				context.getString(R.string.txt_kyodok_description78),
				context.getString(R.string.txt_kyodok_description79),
				context.getString(R.string.txt_kyodok_description80),
				
				context.getString(R.string.txt_kyodok_description81),
				context.getString(R.string.txt_kyodok_description82),
				context.getString(R.string.txt_kyodok_description83),
				context.getString(R.string.txt_kyodok_description84),
				context.getString(R.string.txt_kyodok_description85),
				context.getString(R.string.txt_kyodok_description86),
				context.getString(R.string.txt_kyodok_description87),
				context.getString(R.string.txt_kyodok_description88),
				context.getString(R.string.txt_kyodok_description89),
				context.getString(R.string.txt_kyodok_description90),
				
				context.getString(R.string.txt_kyodok_description91),
				context.getString(R.string.txt_kyodok_description92),
				context.getString(R.string.txt_kyodok_description93),
				context.getString(R.string.txt_kyodok_description94),
				context.getString(R.string.txt_kyodok_description95),
				context.getString(R.string.txt_kyodok_description96),
				context.getString(R.string.txt_kyodok_description97),
				context.getString(R.string.txt_kyodok_description98),
				context.getString(R.string.txt_kyodok_description99),
				context.getString(R.string.txt_kyodok_description100),
				
				context.getString(R.string.txt_kyodok_description101),
				context.getString(R.string.txt_kyodok_description102),
				context.getString(R.string.txt_kyodok_description103),
				context.getString(R.string.txt_kyodok_description104),
				context.getString(R.string.txt_kyodok_description105),
				context.getString(R.string.txt_kyodok_description106),
				context.getString(R.string.txt_kyodok_description107),
				context.getString(R.string.txt_kyodok_description108),
				context.getString(R.string.txt_kyodok_description109),
				context.getString(R.string.txt_kyodok_description110),
				
				context.getString(R.string.txt_kyodok_description111),
				context.getString(R.string.txt_kyodok_description112),
				context.getString(R.string.txt_kyodok_description113),
				context.getString(R.string.txt_kyodok_description114),
				context.getString(R.string.txt_kyodok_description115),
				context.getString(R.string.txt_kyodok_description116),
				context.getString(R.string.txt_kyodok_description117),
				context.getString(R.string.txt_kyodok_description118),
				context.getString(R.string.txt_kyodok_description119),
				context.getString(R.string.txt_kyodok_description120),
				
				context.getString(R.string.txt_kyodok_description121),
				context.getString(R.string.txt_kyodok_description122),
				context.getString(R.string.txt_kyodok_description123),
				context.getString(R.string.txt_kyodok_description124),
				context.getString(R.string.txt_kyodok_description125),
				context.getString(R.string.txt_kyodok_description126),
				context.getString(R.string.txt_kyodok_description127),
				context.getString(R.string.txt_kyodok_description128),
				context.getString(R.string.txt_kyodok_description129),
				context.getString(R.string.txt_kyodok_description130),
				
				context.getString(R.string.txt_kyodok_description131),
				context.getString(R.string.txt_kyodok_description132),
				context.getString(R.string.txt_kyodok_description133),
				context.getString(R.string.txt_kyodok_description134),
				context.getString(R.string.txt_kyodok_description135),
				context.getString(R.string.txt_kyodok_description136),
				context.getString(R.string.txt_kyodok_description137)
			};
		
		String[] gidomun_simbang_description = {
				context.getString(R.string.txt_simbang_description00),
				context.getString(R.string.txt_simbang_description01),
				context.getString(R.string.txt_simbang_description02),
				context.getString(R.string.txt_simbang_description03),
				context.getString(R.string.txt_simbang_description04),
				context.getString(R.string.txt_simbang_description05),
				context.getString(R.string.txt_simbang_description07),
				context.getString(R.string.txt_simbang_description08),
				context.getString(R.string.txt_simbang_description09),
				context.getString(R.string.txt_simbang_description10),
				
				context.getString(R.string.txt_simbang_description11),
				context.getString(R.string.txt_simbang_description12),
				context.getString(R.string.txt_simbang_description13),
				context.getString(R.string.txt_simbang_description14),
				context.getString(R.string.txt_simbang_description15),
				context.getString(R.string.txt_simbang_description16),
				context.getString(R.string.txt_simbang_description17),
				context.getString(R.string.txt_simbang_description18),
				context.getString(R.string.txt_simbang_description19),
				context.getString(R.string.txt_simbang_description19_5),
				context.getString(R.string.txt_simbang_description20),
				
				context.getString(R.string.txt_simbang_description21),
				context.getString(R.string.txt_simbang_description22),
				context.getString(R.string.txt_simbang_description23),
				context.getString(R.string.txt_simbang_description24),
				context.getString(R.string.txt_simbang_description25),
				context.getString(R.string.txt_simbang_description26),
				context.getString(R.string.txt_simbang_description27),
				context.getString(R.string.txt_simbang_description28),
				context.getString(R.string.txt_simbang_description29),
				context.getString(R.string.txt_simbang_description30),
				
				context.getString(R.string.txt_simbang_description31),
				context.getString(R.string.txt_simbang_description32),
				context.getString(R.string.txt_simbang_description33),
				context.getString(R.string.txt_simbang_description34),
				context.getString(R.string.txt_simbang_description35),
				context.getString(R.string.txt_simbang_description36),
				context.getString(R.string.txt_simbang_description37),
				context.getString(R.string.txt_simbang_description38),
				context.getString(R.string.txt_simbang_description39),
				context.getString(R.string.txt_simbang_description40),
				
				context.getString(R.string.txt_simbang_description41),
				context.getString(R.string.txt_simbang_description42),
				context.getString(R.string.txt_simbang_description43),
				context.getString(R.string.txt_simbang_description44),
				context.getString(R.string.txt_simbang_description45),
				context.getString(R.string.txt_simbang_description46),
				context.getString(R.string.txt_simbang_description47),
				context.getString(R.string.txt_simbang_description48),
				context.getString(R.string.txt_simbang_description49),
			};
		
		String[] gidomun_old_kyodok_description = {
				context.getString(R.string.txt_old_kyodok_description00),
				context.getString(R.string.txt_old_kyodok_description01),
				context.getString(R.string.txt_old_kyodok_description02),
				context.getString(R.string.txt_old_kyodok_description03),
				context.getString(R.string.txt_old_kyodok_description04),
				context.getString(R.string.txt_old_kyodok_description05),
				context.getString(R.string.txt_old_kyodok_description06),
				context.getString(R.string.txt_old_kyodok_description07),
				context.getString(R.string.txt_old_kyodok_description08),
				context.getString(R.string.txt_old_kyodok_description09),
				context.getString(R.string.txt_old_kyodok_description10),
				context.getString(R.string.txt_old_kyodok_description11),
				context.getString(R.string.txt_old_kyodok_description12),
				context.getString(R.string.txt_old_kyodok_description13),
				context.getString(R.string.txt_old_kyodok_description14),
				context.getString(R.string.txt_old_kyodok_description15),
				context.getString(R.string.txt_old_kyodok_description16),
				context.getString(R.string.txt_old_kyodok_description17),
				context.getString(R.string.txt_old_kyodok_description18),
				context.getString(R.string.txt_old_kyodok_description19),
				context.getString(R.string.txt_old_kyodok_description20),
				context.getString(R.string.txt_old_kyodok_description21),
				context.getString(R.string.txt_old_kyodok_description22),
				context.getString(R.string.txt_old_kyodok_description23),
				context.getString(R.string.txt_old_kyodok_description24),
				context.getString(R.string.txt_old_kyodok_description25),
				context.getString(R.string.txt_old_kyodok_description26),
				context.getString(R.string.txt_old_kyodok_description27),
				context.getString(R.string.txt_old_kyodok_description28),
				context.getString(R.string.txt_old_kyodok_description29),
				context.getString(R.string.txt_old_kyodok_description30),
				context.getString(R.string.txt_old_kyodok_description31),
				context.getString(R.string.txt_old_kyodok_description32),
				context.getString(R.string.txt_old_kyodok_description33),
				context.getString(R.string.txt_old_kyodok_description34),
				context.getString(R.string.txt_old_kyodok_description35),
				context.getString(R.string.txt_old_kyodok_description36),
				context.getString(R.string.txt_old_kyodok_description37),
				context.getString(R.string.txt_old_kyodok_description38),
				context.getString(R.string.txt_old_kyodok_description39),
				context.getString(R.string.txt_old_kyodok_description40),
				context.getString(R.string.txt_old_kyodok_description41),
				context.getString(R.string.txt_old_kyodok_description42),
				context.getString(R.string.txt_old_kyodok_description43),
				context.getString(R.string.txt_old_kyodok_description44),
				context.getString(R.string.txt_old_kyodok_description45),
				context.getString(R.string.txt_old_kyodok_description46),
				context.getString(R.string.txt_old_kyodok_description47),
				context.getString(R.string.txt_old_kyodok_description48),
				context.getString(R.string.txt_old_kyodok_description49),
				context.getString(R.string.txt_old_kyodok_description50),
				context.getString(R.string.txt_old_kyodok_description51),
				context.getString(R.string.txt_old_kyodok_description52),
				context.getString(R.string.txt_old_kyodok_description53),
				context.getString(R.string.txt_old_kyodok_description54),
				context.getString(R.string.txt_old_kyodok_description55),
				context.getString(R.string.txt_old_kyodok_description56),
				context.getString(R.string.txt_old_kyodok_description57),
				context.getString(R.string.txt_old_kyodok_description58),
				context.getString(R.string.txt_old_kyodok_description59),
				context.getString(R.string.txt_old_kyodok_description60),
				context.getString(R.string.txt_old_kyodok_description61),
				context.getString(R.string.txt_old_kyodok_description62),
				context.getString(R.string.txt_old_kyodok_description63),
				context.getString(R.string.txt_old_kyodok_description64),
				context.getString(R.string.txt_old_kyodok_description65),
				context.getString(R.string.txt_old_kyodok_description66),
				context.getString(R.string.txt_old_kyodok_description67),
				context.getString(R.string.txt_old_kyodok_description68),
				context.getString(R.string.txt_old_kyodok_description69),
				context.getString(R.string.txt_old_kyodok_description70),
				context.getString(R.string.txt_old_kyodok_description71),
				context.getString(R.string.txt_old_kyodok_description72),
				context.getString(R.string.txt_old_kyodok_description73),
				context.getString(R.string.txt_old_kyodok_description74),
				context.getString(R.string.txt_old_kyodok_description75),
				context.getString(R.string.txt_old_kyodok_description76),
				context.getString(R.string.txt_old_kyodok_description77),
				context.getString(R.string.txt_old_kyodok_description78)
			};
    	
		pref = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_gidomun_string), Activity.MODE_PRIVATE);
		main_gidomun_which = pref.getInt("main_gidomun_which", main_gidomun_which);
		kyodok_gidomun_which = pref.getInt("kyodok_gidomun_which", kyodok_gidomun_which);
		simbang_gidomun_which = pref.getInt("simbang_gidomun_which", simbang_gidomun_which);
		old_kyodok_gidomun_which = pref.getInt("old_kyodok_gidomun_which", old_kyodok_gidomun_which);
		bt_main_description.setText(gidomun_main_description[main_gidomun_which]);
		
		if(main_gidomun_which == 7){
			bt_sub_description.setText(gidomun_kyodok_description[kyodok_gidomun_which]);
			bt_sub_description.setVisibility(View.VISIBLE);
		}else if(main_gidomun_which == 8){
			bt_sub_description.setText(gidomun_simbang_description[simbang_gidomun_which]);
			bt_sub_description.setVisibility(View.VISIBLE);
		}else if(main_gidomun_which == 9){
			bt_sub_description.setText(gidomun_old_kyodok_description[old_kyodok_gidomun_which]);
			bt_sub_description.setVisibility(View.VISIBLE);
		}else{
			bt_sub_description.setVisibility(View.INVISIBLE);
		}
		displayList();
	}
	
	 public void displayList() {
		 contactsList = getContactsList();
		 adapter = new Bible_GidomunAdapter<Sub7_ColumData>(
	 			   this,R.layout.activity_sub7_listrow,contactsList, Bottom_01, Bottom_02);
		 listview_gidomunlist = (ListView)findViewById(R.id.listview_gidomunlist);
		 listview_gidomunlist.setAdapter(adapter);
		 listview_gidomunlist.setOnItemClickListener(this);
		 if (SDK_INT >= Build.VERSION_CODES.HONEYCOMB){ //허니콤 버전에서만 실행 가능한 API 사용}
			 listview_gidomunlist.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	    	}
		 listview_gidomunlist.setItemsCanFocus(false);
		 listview_gidomunlist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	 }
	 
	 public List<Sub7_ColumData> getContactsList() {
		 String[] gidomun_main_description = {
		    		context.getString(R.string.txt_gidomun_description00),
		    		context.getString(R.string.txt_gidomun_description01),
		    		context.getString(R.string.txt_gidomun_description02),
		    		context.getString(R.string.txt_gidomun_description03),
		    		context.getString(R.string.txt_gidomun_description04),
		    		context.getString(R.string.txt_gidomun_description05),
		    		context.getString(R.string.txt_gidomun_description06),
		    		context.getString(R.string.txt_gidomun_description07),
		    		context.getString(R.string.txt_gidomun_description08),
		    		context.getString(R.string.txt_gidomun_description09)
		    	};
		 
		 String[] gidomun_kyodok_description = {
					context.getString(R.string.txt_kyodok_description00),
					context.getString(R.string.txt_kyodok_description01),
					context.getString(R.string.txt_kyodok_description02),
					context.getString(R.string.txt_kyodok_description03),
					context.getString(R.string.txt_kyodok_description04),
					context.getString(R.string.txt_kyodok_description05),
					context.getString(R.string.txt_kyodok_description06),
					context.getString(R.string.txt_kyodok_description07),
					context.getString(R.string.txt_kyodok_description08),
					context.getString(R.string.txt_kyodok_description09),
					context.getString(R.string.txt_kyodok_description10),
					
					context.getString(R.string.txt_kyodok_description11),
					context.getString(R.string.txt_kyodok_description12),
					context.getString(R.string.txt_kyodok_description13),
					context.getString(R.string.txt_kyodok_description14),
					context.getString(R.string.txt_kyodok_description15),
					context.getString(R.string.txt_kyodok_description16),
					context.getString(R.string.txt_kyodok_description17),
					context.getString(R.string.txt_kyodok_description18),
					context.getString(R.string.txt_kyodok_description19),
					context.getString(R.string.txt_kyodok_description20),
					
					context.getString(R.string.txt_kyodok_description21),
					context.getString(R.string.txt_kyodok_description22),
					context.getString(R.string.txt_kyodok_description23),
					context.getString(R.string.txt_kyodok_description24),
					context.getString(R.string.txt_kyodok_description25),
					context.getString(R.string.txt_kyodok_description26),
					context.getString(R.string.txt_kyodok_description27),
					context.getString(R.string.txt_kyodok_description28),
					context.getString(R.string.txt_kyodok_description29),
					context.getString(R.string.txt_kyodok_description30),
					
					context.getString(R.string.txt_kyodok_description31),
					context.getString(R.string.txt_kyodok_description32),
					context.getString(R.string.txt_kyodok_description33),
					context.getString(R.string.txt_kyodok_description34),
					context.getString(R.string.txt_kyodok_description35),
					context.getString(R.string.txt_kyodok_description36),
					context.getString(R.string.txt_kyodok_description37),
					context.getString(R.string.txt_kyodok_description38),
					context.getString(R.string.txt_kyodok_description39),
					context.getString(R.string.txt_kyodok_description40),
					
					context.getString(R.string.txt_kyodok_description41),
					context.getString(R.string.txt_kyodok_description42),
					context.getString(R.string.txt_kyodok_description43),
					context.getString(R.string.txt_kyodok_description44),
					context.getString(R.string.txt_kyodok_description45),
					context.getString(R.string.txt_kyodok_description46),
					context.getString(R.string.txt_kyodok_description47),
					context.getString(R.string.txt_kyodok_description48),
					context.getString(R.string.txt_kyodok_description49),
					context.getString(R.string.txt_kyodok_description50),
					
					context.getString(R.string.txt_kyodok_description51),
					context.getString(R.string.txt_kyodok_description52),
					context.getString(R.string.txt_kyodok_description53),
					context.getString(R.string.txt_kyodok_description54),
					context.getString(R.string.txt_kyodok_description55),
					context.getString(R.string.txt_kyodok_description56),
					context.getString(R.string.txt_kyodok_description57),
					context.getString(R.string.txt_kyodok_description58),
					context.getString(R.string.txt_kyodok_description59),
					context.getString(R.string.txt_kyodok_description60),
					
					context.getString(R.string.txt_kyodok_description61),
					context.getString(R.string.txt_kyodok_description62),
					context.getString(R.string.txt_kyodok_description63),
					context.getString(R.string.txt_kyodok_description64),
					context.getString(R.string.txt_kyodok_description65),
					context.getString(R.string.txt_kyodok_description66),
					context.getString(R.string.txt_kyodok_description67),
					context.getString(R.string.txt_kyodok_description68),
					context.getString(R.string.txt_kyodok_description69),
					context.getString(R.string.txt_kyodok_description70),
					
					context.getString(R.string.txt_kyodok_description71),
					context.getString(R.string.txt_kyodok_description72),
					context.getString(R.string.txt_kyodok_description73),
					context.getString(R.string.txt_kyodok_description74),
					context.getString(R.string.txt_kyodok_description75),
					context.getString(R.string.txt_kyodok_description76),
					context.getString(R.string.txt_kyodok_description77),
					context.getString(R.string.txt_kyodok_description78),
					context.getString(R.string.txt_kyodok_description79),
					context.getString(R.string.txt_kyodok_description80),
					
					context.getString(R.string.txt_kyodok_description81),
					context.getString(R.string.txt_kyodok_description82),
					context.getString(R.string.txt_kyodok_description83),
					context.getString(R.string.txt_kyodok_description84),
					context.getString(R.string.txt_kyodok_description85),
					context.getString(R.string.txt_kyodok_description86),
					context.getString(R.string.txt_kyodok_description87),
					context.getString(R.string.txt_kyodok_description88),
					context.getString(R.string.txt_kyodok_description89),
					context.getString(R.string.txt_kyodok_description90),
					
					context.getString(R.string.txt_kyodok_description91),
					context.getString(R.string.txt_kyodok_description92),
					context.getString(R.string.txt_kyodok_description93),
					context.getString(R.string.txt_kyodok_description94),
					context.getString(R.string.txt_kyodok_description95),
					context.getString(R.string.txt_kyodok_description96),
					context.getString(R.string.txt_kyodok_description97),
					context.getString(R.string.txt_kyodok_description98),
					context.getString(R.string.txt_kyodok_description99),
					context.getString(R.string.txt_kyodok_description100),
					
					context.getString(R.string.txt_kyodok_description101),
					context.getString(R.string.txt_kyodok_description102),
					context.getString(R.string.txt_kyodok_description103),
					context.getString(R.string.txt_kyodok_description104),
					context.getString(R.string.txt_kyodok_description105),
					context.getString(R.string.txt_kyodok_description106),
					context.getString(R.string.txt_kyodok_description107),
					context.getString(R.string.txt_kyodok_description108),
					context.getString(R.string.txt_kyodok_description109),
					context.getString(R.string.txt_kyodok_description110),
					
					context.getString(R.string.txt_kyodok_description111),
					context.getString(R.string.txt_kyodok_description112),
					context.getString(R.string.txt_kyodok_description113),
					context.getString(R.string.txt_kyodok_description114),
					context.getString(R.string.txt_kyodok_description115),
					context.getString(R.string.txt_kyodok_description116),
					context.getString(R.string.txt_kyodok_description117),
					context.getString(R.string.txt_kyodok_description118),
					context.getString(R.string.txt_kyodok_description119),
					context.getString(R.string.txt_kyodok_description120),
					
					context.getString(R.string.txt_kyodok_description121),
					context.getString(R.string.txt_kyodok_description122),
					context.getString(R.string.txt_kyodok_description123),
					context.getString(R.string.txt_kyodok_description124),
					context.getString(R.string.txt_kyodok_description125),
					context.getString(R.string.txt_kyodok_description126),
					context.getString(R.string.txt_kyodok_description127),
					context.getString(R.string.txt_kyodok_description128),
					context.getString(R.string.txt_kyodok_description129),
					context.getString(R.string.txt_kyodok_description130),
					
					context.getString(R.string.txt_kyodok_description131),
					context.getString(R.string.txt_kyodok_description132),
					context.getString(R.string.txt_kyodok_description133),
					context.getString(R.string.txt_kyodok_description134),
					context.getString(R.string.txt_kyodok_description135),
					context.getString(R.string.txt_kyodok_description136),
					context.getString(R.string.txt_kyodok_description137)
				};
		 
		 String[] gidomun_simbang_description = {
					context.getString(R.string.txt_simbang_description00),
					context.getString(R.string.txt_simbang_description01),
					context.getString(R.string.txt_simbang_description02),
					context.getString(R.string.txt_simbang_description03),
					context.getString(R.string.txt_simbang_description04),
					context.getString(R.string.txt_simbang_description05),
					context.getString(R.string.txt_simbang_description07),
					context.getString(R.string.txt_simbang_description08),
					context.getString(R.string.txt_simbang_description09),
					context.getString(R.string.txt_simbang_description10),
					
					context.getString(R.string.txt_simbang_description11),
					context.getString(R.string.txt_simbang_description12),
					context.getString(R.string.txt_simbang_description13),
					context.getString(R.string.txt_simbang_description14),
					context.getString(R.string.txt_simbang_description15),
					context.getString(R.string.txt_simbang_description16),
					context.getString(R.string.txt_simbang_description17),
					context.getString(R.string.txt_simbang_description18),
					context.getString(R.string.txt_simbang_description19),
					context.getString(R.string.txt_simbang_description19_5),
					context.getString(R.string.txt_simbang_description20),
					
					context.getString(R.string.txt_simbang_description21),
					context.getString(R.string.txt_simbang_description22),
					context.getString(R.string.txt_simbang_description23),
					context.getString(R.string.txt_simbang_description24),
					context.getString(R.string.txt_simbang_description25),
					context.getString(R.string.txt_simbang_description26),
					context.getString(R.string.txt_simbang_description27),
					context.getString(R.string.txt_simbang_description28),
					context.getString(R.string.txt_simbang_description29),
					context.getString(R.string.txt_simbang_description30),
					
					context.getString(R.string.txt_simbang_description31),
					context.getString(R.string.txt_simbang_description32),
					context.getString(R.string.txt_simbang_description33),
					context.getString(R.string.txt_simbang_description34),
					context.getString(R.string.txt_simbang_description35),
					context.getString(R.string.txt_simbang_description36),
					context.getString(R.string.txt_simbang_description37),
					context.getString(R.string.txt_simbang_description38),
					context.getString(R.string.txt_simbang_description39),
					context.getString(R.string.txt_simbang_description40),
					
					context.getString(R.string.txt_simbang_description41),
					context.getString(R.string.txt_simbang_description42),
					context.getString(R.string.txt_simbang_description43),
					context.getString(R.string.txt_simbang_description44),
					context.getString(R.string.txt_simbang_description45),
					context.getString(R.string.txt_simbang_description46),
					context.getString(R.string.txt_simbang_description47),
					context.getString(R.string.txt_simbang_description48),
					context.getString(R.string.txt_simbang_description49)
				};
		 
		 String[] gidomun_old_kyodok_description = {
					context.getString(R.string.txt_old_kyodok_description00),
					context.getString(R.string.txt_old_kyodok_description01),
					context.getString(R.string.txt_old_kyodok_description02),
					context.getString(R.string.txt_old_kyodok_description03),
					context.getString(R.string.txt_old_kyodok_description04),
					context.getString(R.string.txt_old_kyodok_description05),
					context.getString(R.string.txt_old_kyodok_description06),
					context.getString(R.string.txt_old_kyodok_description07),
					context.getString(R.string.txt_old_kyodok_description08),
					context.getString(R.string.txt_old_kyodok_description09),
					context.getString(R.string.txt_old_kyodok_description10),
					context.getString(R.string.txt_old_kyodok_description11),
					context.getString(R.string.txt_old_kyodok_description12),
					context.getString(R.string.txt_old_kyodok_description13),
					context.getString(R.string.txt_old_kyodok_description14),
					context.getString(R.string.txt_old_kyodok_description15),
					context.getString(R.string.txt_old_kyodok_description16),
					context.getString(R.string.txt_old_kyodok_description17),
					context.getString(R.string.txt_old_kyodok_description18),
					context.getString(R.string.txt_old_kyodok_description19),
					context.getString(R.string.txt_old_kyodok_description20),
					context.getString(R.string.txt_old_kyodok_description21),
					context.getString(R.string.txt_old_kyodok_description22),
					context.getString(R.string.txt_old_kyodok_description23),
					context.getString(R.string.txt_old_kyodok_description24),
					context.getString(R.string.txt_old_kyodok_description25),
					context.getString(R.string.txt_old_kyodok_description26),
					context.getString(R.string.txt_old_kyodok_description27),
					context.getString(R.string.txt_old_kyodok_description28),
					context.getString(R.string.txt_old_kyodok_description29),
					context.getString(R.string.txt_old_kyodok_description30),
					context.getString(R.string.txt_old_kyodok_description31),
					context.getString(R.string.txt_old_kyodok_description32),
					context.getString(R.string.txt_old_kyodok_description33),
					context.getString(R.string.txt_old_kyodok_description34),
					context.getString(R.string.txt_old_kyodok_description35),
					context.getString(R.string.txt_old_kyodok_description36),
					context.getString(R.string.txt_old_kyodok_description37),
					context.getString(R.string.txt_old_kyodok_description38),
					context.getString(R.string.txt_old_kyodok_description39),
					context.getString(R.string.txt_old_kyodok_description40),
					context.getString(R.string.txt_old_kyodok_description41),
					context.getString(R.string.txt_old_kyodok_description42),
					context.getString(R.string.txt_old_kyodok_description43),
					context.getString(R.string.txt_old_kyodok_description44),
					context.getString(R.string.txt_old_kyodok_description45),
					context.getString(R.string.txt_old_kyodok_description46),
					context.getString(R.string.txt_old_kyodok_description47),
					context.getString(R.string.txt_old_kyodok_description48),
					context.getString(R.string.txt_old_kyodok_description49),
					context.getString(R.string.txt_old_kyodok_description50),
					context.getString(R.string.txt_old_kyodok_description51),
					context.getString(R.string.txt_old_kyodok_description52),
					context.getString(R.string.txt_old_kyodok_description53),
					context.getString(R.string.txt_old_kyodok_description54),
					context.getString(R.string.txt_old_kyodok_description55),
					context.getString(R.string.txt_old_kyodok_description56),
					context.getString(R.string.txt_old_kyodok_description57),
					context.getString(R.string.txt_old_kyodok_description58),
					context.getString(R.string.txt_old_kyodok_description59),
					context.getString(R.string.txt_old_kyodok_description60),
					context.getString(R.string.txt_old_kyodok_description61),
					context.getString(R.string.txt_old_kyodok_description62),
					context.getString(R.string.txt_old_kyodok_description63),
					context.getString(R.string.txt_old_kyodok_description64),
					context.getString(R.string.txt_old_kyodok_description65),
					context.getString(R.string.txt_old_kyodok_description66),
					context.getString(R.string.txt_old_kyodok_description67),
					context.getString(R.string.txt_old_kyodok_description68),
					context.getString(R.string.txt_old_kyodok_description69),
					context.getString(R.string.txt_old_kyodok_description70),
					context.getString(R.string.txt_old_kyodok_description71),
					context.getString(R.string.txt_old_kyodok_description72),
					context.getString(R.string.txt_old_kyodok_description73),
					context.getString(R.string.txt_old_kyodok_description74),
					context.getString(R.string.txt_old_kyodok_description75),
					context.getString(R.string.txt_old_kyodok_description76),
					context.getString(R.string.txt_old_kyodok_description77),
					context.getString(R.string.txt_old_kyodok_description78)
				};
			try{
				contactsList = new ArrayList<Sub7_ColumData>();
				mydb = new DBOpenHelper_Sub7(this);
				mdb = mydb.getReadableDatabase();
				if(main_gidomun_which == 7){
					if(kyodok_gidomun_which == 0){
						cursor = mdb.rawQuery("select * from gidomun_list  WHERE gidomun_main_description = '"+gidomun_main_description[main_gidomun_which]+"'", null);
					}else{
						cursor = mdb.rawQuery("select * from gidomun_list  WHERE gidomun_main_description = '"+gidomun_main_description[main_gidomun_which]+"' and gidomun_sub_description = '"+gidomun_kyodok_description[kyodok_gidomun_which]+"'", null);	
					}
					
				}else if(main_gidomun_which == 8){
					if(simbang_gidomun_which == 0){
						cursor = mdb.rawQuery("select * from gidomun_list  WHERE gidomun_main_description = '"+gidomun_main_description[main_gidomun_which]+"'", null);
					}else{
						cursor = mdb.rawQuery("select * from gidomun_list  WHERE gidomun_main_description = '"+gidomun_main_description[main_gidomun_which]+"' and gidomun_sub_description = '"+gidomun_simbang_description[simbang_gidomun_which]+"'", null);	
					}
				}else if(main_gidomun_which == 9){
					if(old_kyodok_gidomun_which == 0){
						cursor = mdb.rawQuery("select * from gidomun_list  WHERE gidomun_main_description = '"+gidomun_main_description[main_gidomun_which]+"'", null);
					}else{
						cursor = mdb.rawQuery("select * from gidomun_list  WHERE gidomun_main_description = '"+gidomun_main_description[main_gidomun_which]+"' and gidomun_sub_description = '"+gidomun_old_kyodok_description[old_kyodok_gidomun_which]+"'", null);	
					}
				}else{
					cursor = mdb.rawQuery("select * from gidomun_list  WHERE gidomun_main_description = '"+gidomun_main_description[main_gidomun_which]+"'", null);
				}
				while(cursor.moveToNext()){
					addContact(contactsList,cursor.getInt(cursor.getColumnIndex("_id")), cursor.getString(cursor.getColumnIndex("gidomun_main_description")),cursor.getString(cursor.getColumnIndex("gidomun_sub_description")), cursor.getString(cursor.getColumnIndex("gidomun_content")));
				}
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(mydb != null) mydb.close();
			}
			return contactsList;
		}
	 
	 private void addContact(List<Sub7_ColumData> contactsList,
				int id, String gidomun_main_description, String gidomun_sub_description, String gidomun_content) {
			contactsList.add(new Sub7_ColumData(id, gidomun_main_description, gidomun_sub_description, gidomun_content));
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
		if(mydb != null){
			mydb.close();
		}
	}
	@Override
	public void onClick(View view) {
		final String[] gidomun_main_description = {
	    		context.getString(R.string.txt_gidomun_description00),
	    		context.getString(R.string.txt_gidomun_description01),
	    		context.getString(R.string.txt_gidomun_description02),
	    		context.getString(R.string.txt_gidomun_description03),
	    		context.getString(R.string.txt_gidomun_description04),
	    		context.getString(R.string.txt_gidomun_description05),
	    		context.getString(R.string.txt_gidomun_description06),
	    		context.getString(R.string.txt_gidomun_description07),
	    		context.getString(R.string.txt_gidomun_description08),
	    		context.getString(R.string.txt_gidomun_description09)
	    	};
		
		final String[] gidomun_kyodok_description = {
				context.getString(R.string.txt_kyodok_description00),
				context.getString(R.string.txt_kyodok_description01),
				context.getString(R.string.txt_kyodok_description02),
				context.getString(R.string.txt_kyodok_description03),
				context.getString(R.string.txt_kyodok_description04),
				context.getString(R.string.txt_kyodok_description05),
				context.getString(R.string.txt_kyodok_description06),
				context.getString(R.string.txt_kyodok_description07),
				context.getString(R.string.txt_kyodok_description08),
				context.getString(R.string.txt_kyodok_description09),
				context.getString(R.string.txt_kyodok_description10),
				
				context.getString(R.string.txt_kyodok_description11),
				context.getString(R.string.txt_kyodok_description12),
				context.getString(R.string.txt_kyodok_description13),
				context.getString(R.string.txt_kyodok_description14),
				context.getString(R.string.txt_kyodok_description15),
				context.getString(R.string.txt_kyodok_description16),
				context.getString(R.string.txt_kyodok_description17),
				context.getString(R.string.txt_kyodok_description18),
				context.getString(R.string.txt_kyodok_description19),
				context.getString(R.string.txt_kyodok_description20),
				
				context.getString(R.string.txt_kyodok_description21),
				context.getString(R.string.txt_kyodok_description22),
				context.getString(R.string.txt_kyodok_description23),
				context.getString(R.string.txt_kyodok_description24),
				context.getString(R.string.txt_kyodok_description25),
				context.getString(R.string.txt_kyodok_description26),
				context.getString(R.string.txt_kyodok_description27),
				context.getString(R.string.txt_kyodok_description28),
				context.getString(R.string.txt_kyodok_description29),
				context.getString(R.string.txt_kyodok_description30),
				
				context.getString(R.string.txt_kyodok_description31),
				context.getString(R.string.txt_kyodok_description32),
				context.getString(R.string.txt_kyodok_description33),
				context.getString(R.string.txt_kyodok_description34),
				context.getString(R.string.txt_kyodok_description35),
				context.getString(R.string.txt_kyodok_description36),
				context.getString(R.string.txt_kyodok_description37),
				context.getString(R.string.txt_kyodok_description38),
				context.getString(R.string.txt_kyodok_description39),
				context.getString(R.string.txt_kyodok_description40),
				
				context.getString(R.string.txt_kyodok_description41),
				context.getString(R.string.txt_kyodok_description42),
				context.getString(R.string.txt_kyodok_description43),
				context.getString(R.string.txt_kyodok_description44),
				context.getString(R.string.txt_kyodok_description45),
				context.getString(R.string.txt_kyodok_description46),
				context.getString(R.string.txt_kyodok_description47),
				context.getString(R.string.txt_kyodok_description48),
				context.getString(R.string.txt_kyodok_description49),
				context.getString(R.string.txt_kyodok_description50),
				
				context.getString(R.string.txt_kyodok_description51),
				context.getString(R.string.txt_kyodok_description52),
				context.getString(R.string.txt_kyodok_description53),
				context.getString(R.string.txt_kyodok_description54),
				context.getString(R.string.txt_kyodok_description55),
				context.getString(R.string.txt_kyodok_description56),
				context.getString(R.string.txt_kyodok_description57),
				context.getString(R.string.txt_kyodok_description58),
				context.getString(R.string.txt_kyodok_description59),
				context.getString(R.string.txt_kyodok_description60),
				
				context.getString(R.string.txt_kyodok_description61),
				context.getString(R.string.txt_kyodok_description62),
				context.getString(R.string.txt_kyodok_description63),
				context.getString(R.string.txt_kyodok_description64),
				context.getString(R.string.txt_kyodok_description65),
				context.getString(R.string.txt_kyodok_description66),
				context.getString(R.string.txt_kyodok_description67),
				context.getString(R.string.txt_kyodok_description68),
				context.getString(R.string.txt_kyodok_description69),
				context.getString(R.string.txt_kyodok_description70),
				
				context.getString(R.string.txt_kyodok_description71),
				context.getString(R.string.txt_kyodok_description72),
				context.getString(R.string.txt_kyodok_description73),
				context.getString(R.string.txt_kyodok_description74),
				context.getString(R.string.txt_kyodok_description75),
				context.getString(R.string.txt_kyodok_description76),
				context.getString(R.string.txt_kyodok_description77),
				context.getString(R.string.txt_kyodok_description78),
				context.getString(R.string.txt_kyodok_description79),
				context.getString(R.string.txt_kyodok_description80),
				
				context.getString(R.string.txt_kyodok_description81),
				context.getString(R.string.txt_kyodok_description82),
				context.getString(R.string.txt_kyodok_description83),
				context.getString(R.string.txt_kyodok_description84),
				context.getString(R.string.txt_kyodok_description85),
				context.getString(R.string.txt_kyodok_description86),
				context.getString(R.string.txt_kyodok_description87),
				context.getString(R.string.txt_kyodok_description88),
				context.getString(R.string.txt_kyodok_description89),
				context.getString(R.string.txt_kyodok_description90),
				
				context.getString(R.string.txt_kyodok_description91),
				context.getString(R.string.txt_kyodok_description92),
				context.getString(R.string.txt_kyodok_description93),
				context.getString(R.string.txt_kyodok_description94),
				context.getString(R.string.txt_kyodok_description95),
				context.getString(R.string.txt_kyodok_description96),
				context.getString(R.string.txt_kyodok_description97),
				context.getString(R.string.txt_kyodok_description98),
				context.getString(R.string.txt_kyodok_description99),
				context.getString(R.string.txt_kyodok_description100),
				
				context.getString(R.string.txt_kyodok_description101),
				context.getString(R.string.txt_kyodok_description102),
				context.getString(R.string.txt_kyodok_description103),
				context.getString(R.string.txt_kyodok_description104),
				context.getString(R.string.txt_kyodok_description105),
				context.getString(R.string.txt_kyodok_description106),
				context.getString(R.string.txt_kyodok_description107),
				context.getString(R.string.txt_kyodok_description108),
				context.getString(R.string.txt_kyodok_description109),
				context.getString(R.string.txt_kyodok_description110),
				
				context.getString(R.string.txt_kyodok_description111),
				context.getString(R.string.txt_kyodok_description112),
				context.getString(R.string.txt_kyodok_description113),
				context.getString(R.string.txt_kyodok_description114),
				context.getString(R.string.txt_kyodok_description115),
				context.getString(R.string.txt_kyodok_description116),
				context.getString(R.string.txt_kyodok_description117),
				context.getString(R.string.txt_kyodok_description118),
				context.getString(R.string.txt_kyodok_description119),
				context.getString(R.string.txt_kyodok_description120),
				
				context.getString(R.string.txt_kyodok_description121),
				context.getString(R.string.txt_kyodok_description122),
				context.getString(R.string.txt_kyodok_description123),
				context.getString(R.string.txt_kyodok_description124),
				context.getString(R.string.txt_kyodok_description125),
				context.getString(R.string.txt_kyodok_description126),
				context.getString(R.string.txt_kyodok_description127),
				context.getString(R.string.txt_kyodok_description128),
				context.getString(R.string.txt_kyodok_description129),
				context.getString(R.string.txt_kyodok_description130),
				
				context.getString(R.string.txt_kyodok_description131),
				context.getString(R.string.txt_kyodok_description132),
				context.getString(R.string.txt_kyodok_description133),
				context.getString(R.string.txt_kyodok_description134),
				context.getString(R.string.txt_kyodok_description135),
				context.getString(R.string.txt_kyodok_description136),
				context.getString(R.string.txt_kyodok_description137)
			};
		final String[] gidomun_simbang_description = {
				context.getString(R.string.txt_simbang_description00),
				context.getString(R.string.txt_simbang_description01),
				context.getString(R.string.txt_simbang_description02),
				context.getString(R.string.txt_simbang_description03),
				context.getString(R.string.txt_simbang_description04),
				context.getString(R.string.txt_simbang_description05),
				context.getString(R.string.txt_simbang_description07),
				context.getString(R.string.txt_simbang_description08),
				context.getString(R.string.txt_simbang_description09),
				context.getString(R.string.txt_simbang_description10),
				
				context.getString(R.string.txt_simbang_description11),
				context.getString(R.string.txt_simbang_description12),
				context.getString(R.string.txt_simbang_description13),
				context.getString(R.string.txt_simbang_description14),
				context.getString(R.string.txt_simbang_description15),
				context.getString(R.string.txt_simbang_description16),
				context.getString(R.string.txt_simbang_description17),
				context.getString(R.string.txt_simbang_description18),
				context.getString(R.string.txt_simbang_description19),
				context.getString(R.string.txt_simbang_description19_5),
				context.getString(R.string.txt_simbang_description20),
				
				context.getString(R.string.txt_simbang_description21),
				context.getString(R.string.txt_simbang_description22),
				context.getString(R.string.txt_simbang_description23),
				context.getString(R.string.txt_simbang_description24),
				context.getString(R.string.txt_simbang_description25),
				context.getString(R.string.txt_simbang_description26),
				context.getString(R.string.txt_simbang_description27),
				context.getString(R.string.txt_simbang_description28),
				context.getString(R.string.txt_simbang_description29),
				context.getString(R.string.txt_simbang_description30),
				
				context.getString(R.string.txt_simbang_description31),
				context.getString(R.string.txt_simbang_description32),
				context.getString(R.string.txt_simbang_description33),
				context.getString(R.string.txt_simbang_description34),
				context.getString(R.string.txt_simbang_description35),
				context.getString(R.string.txt_simbang_description36),
				context.getString(R.string.txt_simbang_description37),
				context.getString(R.string.txt_simbang_description38),
				context.getString(R.string.txt_simbang_description39),
				context.getString(R.string.txt_simbang_description40),
				
				context.getString(R.string.txt_simbang_description41),
				context.getString(R.string.txt_simbang_description42),
				context.getString(R.string.txt_simbang_description43),
				context.getString(R.string.txt_simbang_description44),
				context.getString(R.string.txt_simbang_description45),
				context.getString(R.string.txt_simbang_description46),
				context.getString(R.string.txt_simbang_description47),
				context.getString(R.string.txt_simbang_description48),
				context.getString(R.string.txt_simbang_description49)
			};
		
		final String[] gidomun_old_kyodok_description = {
				context.getString(R.string.txt_old_kyodok_description00),
				context.getString(R.string.txt_old_kyodok_description01),
				context.getString(R.string.txt_old_kyodok_description02),
				context.getString(R.string.txt_old_kyodok_description03),
				context.getString(R.string.txt_old_kyodok_description04),
				context.getString(R.string.txt_old_kyodok_description05),
				context.getString(R.string.txt_old_kyodok_description06),
				context.getString(R.string.txt_old_kyodok_description07),
				context.getString(R.string.txt_old_kyodok_description08),
				context.getString(R.string.txt_old_kyodok_description09),
				context.getString(R.string.txt_old_kyodok_description10),
				context.getString(R.string.txt_old_kyodok_description11),
				context.getString(R.string.txt_old_kyodok_description12),
				context.getString(R.string.txt_old_kyodok_description13),
				context.getString(R.string.txt_old_kyodok_description14),
				context.getString(R.string.txt_old_kyodok_description15),
				context.getString(R.string.txt_old_kyodok_description16),
				context.getString(R.string.txt_old_kyodok_description17),
				context.getString(R.string.txt_old_kyodok_description18),
				context.getString(R.string.txt_old_kyodok_description19),
				context.getString(R.string.txt_old_kyodok_description20),
				context.getString(R.string.txt_old_kyodok_description21),
				context.getString(R.string.txt_old_kyodok_description22),
				context.getString(R.string.txt_old_kyodok_description23),
				context.getString(R.string.txt_old_kyodok_description24),
				context.getString(R.string.txt_old_kyodok_description25),
				context.getString(R.string.txt_old_kyodok_description26),
				context.getString(R.string.txt_old_kyodok_description27),
				context.getString(R.string.txt_old_kyodok_description28),
				context.getString(R.string.txt_old_kyodok_description29),
				context.getString(R.string.txt_old_kyodok_description30),
				context.getString(R.string.txt_old_kyodok_description31),
				context.getString(R.string.txt_old_kyodok_description32),
				context.getString(R.string.txt_old_kyodok_description33),
				context.getString(R.string.txt_old_kyodok_description34),
				context.getString(R.string.txt_old_kyodok_description35),
				context.getString(R.string.txt_old_kyodok_description36),
				context.getString(R.string.txt_old_kyodok_description37),
				context.getString(R.string.txt_old_kyodok_description38),
				context.getString(R.string.txt_old_kyodok_description39),
				context.getString(R.string.txt_old_kyodok_description40),
				context.getString(R.string.txt_old_kyodok_description41),
				context.getString(R.string.txt_old_kyodok_description42),
				context.getString(R.string.txt_old_kyodok_description43),
				context.getString(R.string.txt_old_kyodok_description44),
				context.getString(R.string.txt_old_kyodok_description45),
				context.getString(R.string.txt_old_kyodok_description46),
				context.getString(R.string.txt_old_kyodok_description47),
				context.getString(R.string.txt_old_kyodok_description48),
				context.getString(R.string.txt_old_kyodok_description49),
				context.getString(R.string.txt_old_kyodok_description50),
				context.getString(R.string.txt_old_kyodok_description51),
				context.getString(R.string.txt_old_kyodok_description52),
				context.getString(R.string.txt_old_kyodok_description53),
				context.getString(R.string.txt_old_kyodok_description54),
				context.getString(R.string.txt_old_kyodok_description55),
				context.getString(R.string.txt_old_kyodok_description56),
				context.getString(R.string.txt_old_kyodok_description57),
				context.getString(R.string.txt_old_kyodok_description58),
				context.getString(R.string.txt_old_kyodok_description59),
				context.getString(R.string.txt_old_kyodok_description60),
				context.getString(R.string.txt_old_kyodok_description61),
				context.getString(R.string.txt_old_kyodok_description62),
				context.getString(R.string.txt_old_kyodok_description63),
				context.getString(R.string.txt_old_kyodok_description64),
				context.getString(R.string.txt_old_kyodok_description65),
				context.getString(R.string.txt_old_kyodok_description66),
				context.getString(R.string.txt_old_kyodok_description67),
				context.getString(R.string.txt_old_kyodok_description68),
				context.getString(R.string.txt_old_kyodok_description69),
				context.getString(R.string.txt_old_kyodok_description70),
				context.getString(R.string.txt_old_kyodok_description71),
				context.getString(R.string.txt_old_kyodok_description72),
				context.getString(R.string.txt_old_kyodok_description73),
				context.getString(R.string.txt_old_kyodok_description74),
				context.getString(R.string.txt_old_kyodok_description75),
				context.getString(R.string.txt_old_kyodok_description76),
				context.getString(R.string.txt_old_kyodok_description77),
				context.getString(R.string.txt_old_kyodok_description78)
			};
		
		if(view == bt_main_description){
			settings = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_gidomun_string), MODE_PRIVATE);
			edit = settings.edit();
			
			pref = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_gidomun_string), Activity.MODE_PRIVATE);
			main_gidomun_which = pref.getInt("main_gidomun_which", 0);
			
			alertDialog = new AlertDialog.Builder(this)
			.setIcon( R.drawable.icon64)
			.setTitle(context.getString(R.string.txt_gidomun_activity1))
			.setSingleChoiceItems(gidomun_main_description, main_gidomun_which, new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which){
					dialog.dismiss();
					if(which == 0){
						bt_sub_description.setVisibility(View.INVISIBLE);
						main_gidomun_which = 0;
						edit.putInt("main_gidomun_which", 0);
					}else if(which == 1){
						bt_sub_description.setVisibility(View.INVISIBLE);
						main_gidomun_which = 1;
						edit.putInt("main_gidomun_which", 1);
					}else if(which == 2){
						bt_sub_description.setVisibility(View.INVISIBLE);
						main_gidomun_which = 2;
						edit.putInt("main_gidomun_which", 2);
					}else if(which == 3){
						bt_sub_description.setVisibility(View.INVISIBLE);
						main_gidomun_which = 3;
						edit.putInt("main_gidomun_which", 3);
					}else if(which == 4){
						bt_sub_description.setVisibility(View.INVISIBLE);
						main_gidomun_which = 4;
						edit.putInt("main_gidomun_which", 4);
					}else if(which == 5){
						bt_sub_description.setVisibility(View.INVISIBLE);
						main_gidomun_which = 5;
						edit.putInt("main_gidomun_which", 5);
					}else if(which == 6){
						bt_sub_description.setVisibility(View.INVISIBLE);
						main_gidomun_which = 6;
						edit.putInt("main_gidomun_which", 6);
					}else if(which == 7){
						bt_sub_description.setText(gidomun_kyodok_description[kyodok_gidomun_which]);
						bt_sub_description.setVisibility(View.VISIBLE);
						main_gidomun_which = 7;
						edit.putInt("main_gidomun_which", 7);
					}else if(which == 8){
						bt_sub_description.setText(gidomun_simbang_description[simbang_gidomun_which]);
						bt_sub_description.setVisibility(View.VISIBLE);
						main_gidomun_which = 8;
						edit.putInt("main_gidomun_which", 8);
					}else if(which == 9){
						bt_sub_description.setText(gidomun_old_kyodok_description[old_kyodok_gidomun_which]);
						bt_sub_description.setVisibility(View.VISIBLE);
						main_gidomun_which = 9;
						edit.putInt("main_gidomun_which", 9);
					}
					bt_main_description.setText(gidomun_main_description[main_gidomun_which]);
					edit.commit();
					
					displayList();
				}
			}).show();
		}else if(view == bt_sub_description){
			settings = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_gidomun_string), MODE_PRIVATE);
			edit = settings.edit();
			
			pref = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_gidomun_string), Activity.MODE_PRIVATE);
			kyodok_gidomun_which = pref.getInt("kyodok_gidomun_which", 0);
			simbang_gidomun_which = pref.getInt("simbang_gidomun_which", 0);
			
			if(main_gidomun_which == 7){
				alertDialog = new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon64)
				.setSingleChoiceItems(gidomun_kyodok_description, kyodok_gidomun_which, new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which){
						dialog.dismiss();
						if(which == 0){
							kyodok_gidomun_which = 0;
							edit.putInt("kyodok_gidomun_which", 0);
						}else if(which == 1){
							kyodok_gidomun_which = 1;
							edit.putInt("kyodok_gidomun_which", 1);
						}else if(which == 2){
							kyodok_gidomun_which = 2;
							edit.putInt("kyodok_gidomun_which", 2);
						}else if(which == 3){
							kyodok_gidomun_which = 3;
							edit.putInt("kyodok_gidomun_which", 3);
						}else if(which == 4){
							kyodok_gidomun_which = 4;
							edit.putInt("kyodok_gidomun_which", 4);
						}else if(which == 5){
							kyodok_gidomun_which = 5;
							edit.putInt("kyodok_gidomun_which", 5);
						}else if(which == 6){
							kyodok_gidomun_which = 6;
							edit.putInt("kyodok_gidomun_which", 6);
						}else if(which == 7){
							kyodok_gidomun_which = 7;
							edit.putInt("kyodok_gidomun_which", 7);
						}else if(which == 8){
							kyodok_gidomun_which = 8;
							edit.putInt("kyodok_gidomun_which", 8);
						}else if(which == 9){
							kyodok_gidomun_which = 9;
							edit.putInt("kyodok_gidomun_which", 9);
						}else if(which == 10){
							kyodok_gidomun_which = 10;
							edit.putInt("kyodok_gidomun_which", 10);
						}else if(which == 11){
							kyodok_gidomun_which = 11;
							edit.putInt("kyodok_gidomun_which", 11);
						}else if(which == 12){
							kyodok_gidomun_which = 12;
							edit.putInt("kyodok_gidomun_which", 12);
						}else if(which == 13){
							kyodok_gidomun_which = 13;
							edit.putInt("kyodok_gidomun_which", 13);
						}else if(which == 14){
							kyodok_gidomun_which = 14;
							edit.putInt("kyodok_gidomun_which", 14);
						}else if(which == 15){
							kyodok_gidomun_which = 15;
							edit.putInt("kyodok_gidomun_which", 15);
						}else if(which == 16){
							kyodok_gidomun_which = 16;
							edit.putInt("kyodok_gidomun_which", 16);
						}else if(which == 17){
							kyodok_gidomun_which = 17;
							edit.putInt("kyodok_gidomun_which", 17);
						}else if(which == 18){
							kyodok_gidomun_which = 18;
							edit.putInt("kyodok_gidomun_which", 18);
						}else if(which == 19){
							kyodok_gidomun_which = 19;
							edit.putInt("kyodok_gidomun_which", 19);
						}else if(which == 20){
							kyodok_gidomun_which = 20;
							edit.putInt("kyodok_gidomun_which", 20);
						}else if(which == 21){
							kyodok_gidomun_which = 21;
							edit.putInt("kyodok_gidomun_which", 21);
						}else if(which == 22){
							kyodok_gidomun_which = 22;
							edit.putInt("kyodok_gidomun_which", 22);
						}else if(which == 23){
							kyodok_gidomun_which = 23;
							edit.putInt("kyodok_gidomun_which", 23);
						}else if(which == 24){
							kyodok_gidomun_which = 24;
							edit.putInt("kyodok_gidomun_which", 24);
						}else if(which == 25){
							kyodok_gidomun_which = 25;
							edit.putInt("kyodok_gidomun_which", 25);
						}else if(which == 26){
							kyodok_gidomun_which = 26;
							edit.putInt("kyodok_gidomun_which", 26);
						}else if(which == 27){
							kyodok_gidomun_which = 27;
							edit.putInt("kyodok_gidomun_which", 27);
						}else if(which == 28){
							kyodok_gidomun_which = 28;
							edit.putInt("kyodok_gidomun_which", 28);
						}else if(which == 29){
							kyodok_gidomun_which = 29;
							edit.putInt("kyodok_gidomun_which", 29);
						}else if(which == 30){
							kyodok_gidomun_which = 30;
							edit.putInt("kyodok_gidomun_which", 30);
						}else if(which == 31){
							kyodok_gidomun_which = 31;
							edit.putInt("kyodok_gidomun_which", 31);
						}else if(which == 32){
							kyodok_gidomun_which = 32;
							edit.putInt("kyodok_gidomun_which", 32);
						}else if(which == 33){
							kyodok_gidomun_which = 33;
							edit.putInt("kyodok_gidomun_which", 33);
						}else if(which == 34){
							kyodok_gidomun_which = 34;
							edit.putInt("kyodok_gidomun_which", 34);
						}else if(which == 35){
							kyodok_gidomun_which = 35;
							edit.putInt("kyodok_gidomun_which", 35);
						}else if(which == 36){
							kyodok_gidomun_which = 36;
							edit.putInt("kyodok_gidomun_which", 36);
						}else if(which == 37){
							kyodok_gidomun_which = 37;
							edit.putInt("kyodok_gidomun_which", 37);
						}else if(which == 38){
							kyodok_gidomun_which = 38;
							edit.putInt("kyodok_gidomun_which", 38);
						}else if(which == 39){
							kyodok_gidomun_which = 39;
							edit.putInt("kyodok_gidomun_which", 39);
						}else if(which == 40){
							kyodok_gidomun_which = 40;
							edit.putInt("kyodok_gidomun_which", 40);
						}else if(which == 41){
							kyodok_gidomun_which = 41;
							edit.putInt("kyodok_gidomun_which", 41);
						}else if(which == 42){
							kyodok_gidomun_which = 42;
							edit.putInt("kyodok_gidomun_which", 42);
						}else if(which == 43){
							kyodok_gidomun_which = 43;
							edit.putInt("kyodok_gidomun_which", 43);
						}else if(which == 44){
							kyodok_gidomun_which = 44;
							edit.putInt("kyodok_gidomun_which", 44);
						}else if(which == 45){
							kyodok_gidomun_which = 45;
							edit.putInt("kyodok_gidomun_which", 45);
						}else if(which == 46){
							kyodok_gidomun_which = 46;
							edit.putInt("kyodok_gidomun_which", 46);
						}else if(which == 47){
							kyodok_gidomun_which = 47;
							edit.putInt("kyodok_gidomun_which", 47);
						}else if(which == 48){
							kyodok_gidomun_which = 48;
							edit.putInt("kyodok_gidomun_which", 48);
						}else if(which == 49){
							kyodok_gidomun_which = 49;
							edit.putInt("kyodok_gidomun_which", 49);
						}else if(which == 50){
							kyodok_gidomun_which = 50;
							edit.putInt("kyodok_gidomun_which", 50);
						}else if(which == 51){
							kyodok_gidomun_which = 51;
							edit.putInt("kyodok_gidomun_which", 51);
						}else if(which == 52){
							kyodok_gidomun_which = 52;
							edit.putInt("kyodok_gidomun_which", 52);
						}else if(which == 53){
							kyodok_gidomun_which = 53;
							edit.putInt("kyodok_gidomun_which", 53);
						}else if(which == 54){
							kyodok_gidomun_which = 54;
							edit.putInt("kyodok_gidomun_which", 54);
						}else if(which == 55){
							kyodok_gidomun_which = 55;
							edit.putInt("kyodok_gidomun_which", 55);
						}else if(which == 56){
							kyodok_gidomun_which = 56;
							edit.putInt("kyodok_gidomun_which", 56);
						}else if(which == 57){
							kyodok_gidomun_which = 57;
							edit.putInt("kyodok_gidomun_which", 57);
						}else if(which == 58){
							kyodok_gidomun_which = 58;
							edit.putInt("kyodok_gidomun_which", 58);
						}else if(which == 59){
							kyodok_gidomun_which = 59;
							edit.putInt("kyodok_gidomun_which", 59);
						}else if(which == 60){
							kyodok_gidomun_which = 60;
							edit.putInt("kyodok_gidomun_which", 60);
						}else if(which == 61){
							kyodok_gidomun_which = 61;
							edit.putInt("kyodok_gidomun_which", 61);
						}else if(which == 62){
							kyodok_gidomun_which = 62;
							edit.putInt("kyodok_gidomun_which", 62);
						}else if(which == 63){
							kyodok_gidomun_which = 63;
							edit.putInt("kyodok_gidomun_which", 63);
						}else if(which == 64){
							kyodok_gidomun_which = 64;
							edit.putInt("kyodok_gidomun_which", 64);
						}else if(which == 65){
							kyodok_gidomun_which = 65;
							edit.putInt("kyodok_gidomun_which", 65);
						}else if(which == 66){
							kyodok_gidomun_which = 66;
							edit.putInt("kyodok_gidomun_which", 66);
						}else if(which == 67){
							kyodok_gidomun_which = 67;
							edit.putInt("kyodok_gidomun_which", 67);
						}else if(which == 68){
							kyodok_gidomun_which = 68;
							edit.putInt("kyodok_gidomun_which", 68);
						}else if(which == 69){
							kyodok_gidomun_which = 69;
							edit.putInt("kyodok_gidomun_which", 69);
						}else if(which == 70){
							kyodok_gidomun_which = 70;
							edit.putInt("kyodok_gidomun_which", 70);
						}else if(which == 71){
							kyodok_gidomun_which = 71;
							edit.putInt("kyodok_gidomun_which", 71);
						}else if(which == 72){
							kyodok_gidomun_which = 72;
							edit.putInt("kyodok_gidomun_which", 72);
						}else if(which == 73){
							kyodok_gidomun_which = 73;
							edit.putInt("kyodok_gidomun_which", 73);
						}else if(which == 74){
							kyodok_gidomun_which = 74;
							edit.putInt("kyodok_gidomun_which", 74);
						}else if(which == 75){
							kyodok_gidomun_which = 75;
							edit.putInt("kyodok_gidomun_which", 75);
						}else if(which == 76){
							kyodok_gidomun_which = 76;
							edit.putInt("kyodok_gidomun_which", 76);
						}else if(which == 77){
							kyodok_gidomun_which = 77;
							edit.putInt("kyodok_gidomun_which", 77);
						}else if(which == 78){
							kyodok_gidomun_which = 78;
							edit.putInt("kyodok_gidomun_which", 78);
						}else if(which == 79){
							kyodok_gidomun_which = 79;
							edit.putInt("kyodok_gidomun_which", 79);
						}else if(which == 80){
							kyodok_gidomun_which = 80;
							edit.putInt("kyodok_gidomun_which", 80);
						}else if(which == 81){
							kyodok_gidomun_which = 81;
							edit.putInt("kyodok_gidomun_which", 81);
						}else if(which == 82){
							kyodok_gidomun_which = 82;
							edit.putInt("kyodok_gidomun_which", 82);
						}else if(which == 83){
							kyodok_gidomun_which = 83;
							edit.putInt("kyodok_gidomun_which", 83);
						}else if(which == 84){
							kyodok_gidomun_which = 84;
							edit.putInt("kyodok_gidomun_which", 84);
						}else if(which == 85){
							kyodok_gidomun_which = 85;
							edit.putInt("kyodok_gidomun_which", 85);
						}else if(which == 86){
							kyodok_gidomun_which = 86;
							edit.putInt("kyodok_gidomun_which", 86);
						}else if(which == 87){
							kyodok_gidomun_which = 87;
							edit.putInt("kyodok_gidomun_which", 87);
						}else if(which == 88){
							kyodok_gidomun_which = 88;
							edit.putInt("kyodok_gidomun_which", 88);
						}else if(which == 89){
							kyodok_gidomun_which = 89;
							edit.putInt("kyodok_gidomun_which", 89);
						}else if(which == 90){
							kyodok_gidomun_which = 90;
							edit.putInt("kyodok_gidomun_which", 90);
						}else if(which == 91){
							kyodok_gidomun_which = 91;
							edit.putInt("kyodok_gidomun_which", 91);
						}else if(which == 92){
							kyodok_gidomun_which = 92;
							edit.putInt("kyodok_gidomun_which", 92);
						}else if(which == 93){
							kyodok_gidomun_which = 93;
							edit.putInt("kyodok_gidomun_which", 93);
						}else if(which == 94){
							kyodok_gidomun_which = 94;
							edit.putInt("kyodok_gidomun_which", 94);
						}else if(which == 95){
							kyodok_gidomun_which = 95;
							edit.putInt("kyodok_gidomun_which", 95);
						}else if(which == 96){
							kyodok_gidomun_which = 96;
							edit.putInt("kyodok_gidomun_which", 96);
						}else if(which == 97){
							kyodok_gidomun_which = 97;
							edit.putInt("kyodok_gidomun_which", 97);
						}else if(which == 98){
							kyodok_gidomun_which = 98;
							edit.putInt("kyodok_gidomun_which", 98);
						}else if(which == 99){
							kyodok_gidomun_which = 99;
							edit.putInt("kyodok_gidomun_which", 99);
						}else if(which == 100){
							kyodok_gidomun_which = 100;
							edit.putInt("kyodok_gidomun_which", 100);
						}else if(which == 101){
							kyodok_gidomun_which = 101;
							edit.putInt("kyodok_gidomun_which", 101);
						}else if(which == 102){
							kyodok_gidomun_which = 102;
							edit.putInt("kyodok_gidomun_which", 102);
						}else if(which == 103){
							kyodok_gidomun_which = 103;
							edit.putInt("kyodok_gidomun_which", 103);
						}else if(which == 104){
							kyodok_gidomun_which = 104;
							edit.putInt("kyodok_gidomun_which", 104);
						}else if(which == 105){
							kyodok_gidomun_which = 105;
							edit.putInt("kyodok_gidomun_which", 105);
						}else if(which == 106){
							kyodok_gidomun_which = 106;
							edit.putInt("kyodok_gidomun_which", 106);
						}else if(which == 107){
							kyodok_gidomun_which = 107;
							edit.putInt("kyodok_gidomun_which", 107);
						}else if(which == 108){
							kyodok_gidomun_which = 108;
							edit.putInt("kyodok_gidomun_which", 108);
						}else if(which == 109){
							kyodok_gidomun_which = 109;
							edit.putInt("kyodok_gidomun_which", 109);
						}else if(which == 110){
							kyodok_gidomun_which = 110;
							edit.putInt("kyodok_gidomun_which", 110);
						}else if(which == 111){
							kyodok_gidomun_which = 111;
							edit.putInt("kyodok_gidomun_which", 111);
						}else if(which == 112){
							kyodok_gidomun_which = 112;
							edit.putInt("kyodok_gidomun_which", 112);
						}else if(which == 113){
							kyodok_gidomun_which = 113;
							edit.putInt("kyodok_gidomun_which", 113);
						}else if(which == 114){
							kyodok_gidomun_which = 114;
							edit.putInt("kyodok_gidomun_which", 114);
						}else if(which == 115){
							kyodok_gidomun_which = 115;
							edit.putInt("kyodok_gidomun_which", 115);
						}else if(which == 116){
							kyodok_gidomun_which = 116;
							edit.putInt("kyodok_gidomun_which", 116);
						}else if(which == 117){
							kyodok_gidomun_which = 117;
							edit.putInt("kyodok_gidomun_which", 117);
						}else if(which == 118){
							kyodok_gidomun_which = 118;
							edit.putInt("kyodok_gidomun_which", 118);
						}else if(which == 119){
							kyodok_gidomun_which = 119;
							edit.putInt("kyodok_gidomun_which", 119);
						}else if(which == 120){
							kyodok_gidomun_which = 120;
							edit.putInt("kyodok_gidomun_which", 120);
						}else if(which == 121){
							kyodok_gidomun_which = 121;
							edit.putInt("kyodok_gidomun_which", 121);
						}else if(which == 122){
							kyodok_gidomun_which = 122;
							edit.putInt("kyodok_gidomun_which", 122);
						}else if(which == 123){
							kyodok_gidomun_which = 123;
							edit.putInt("kyodok_gidomun_which", 123);
						}else if(which == 124){
							kyodok_gidomun_which = 124;
							edit.putInt("kyodok_gidomun_which", 124);
						}else if(which == 125){
							kyodok_gidomun_which = 125;
							edit.putInt("kyodok_gidomun_which", 125);
						}else if(which == 126){
							kyodok_gidomun_which = 126;
							edit.putInt("kyodok_gidomun_which", 126);
						}else if(which == 127){
							kyodok_gidomun_which = 127;
							edit.putInt("kyodok_gidomun_which", 127);
						}else if(which == 128){
							kyodok_gidomun_which = 128;
							edit.putInt("kyodok_gidomun_which", 128);
						}else if(which == 129){
							kyodok_gidomun_which = 129;
							edit.putInt("kyodok_gidomun_which", 129);
						}else if(which == 130){
							kyodok_gidomun_which = 130;
							edit.putInt("kyodok_gidomun_which", 130);
						}else if(which == 131){
							kyodok_gidomun_which = 131;
							edit.putInt("kyodok_gidomun_which", 131);
						}else if(which == 132){
							kyodok_gidomun_which = 132;
							edit.putInt("kyodok_gidomun_which", 132);
						}else if(which == 133){
							kyodok_gidomun_which = 133;
							edit.putInt("kyodok_gidomun_which", 133);
						}else if(which == 134){
							kyodok_gidomun_which = 134;
							edit.putInt("kyodok_gidomun_which", 134);
						}else if(which == 135){
							kyodok_gidomun_which = 135;
							edit.putInt("kyodok_gidomun_which", 135);
						}else if(which == 136){
							kyodok_gidomun_which = 136;
							edit.putInt("kyodok_gidomun_which", 136);
						}else if(which == 137){
							kyodok_gidomun_which = 137;
							edit.putInt("kyodok_gidomun_which", 137);
						}			
						bt_sub_description.setText(gidomun_kyodok_description[kyodok_gidomun_which]);
						edit.commit();
						displayList();
					}
				}).show();
			}else if(main_gidomun_which == 8){
				alertDialog = new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon64)
				.setSingleChoiceItems(gidomun_simbang_description, simbang_gidomun_which, new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which){
						dialog.dismiss();
						if(which == 0){
							simbang_gidomun_which = 0;
							edit.putInt("simbang_gidomun_which", 0);
						}else if(which == 1){
							simbang_gidomun_which = 1;
							edit.putInt("simbang_gidomun_which", 1);
						}else if(which == 2){
							simbang_gidomun_which = 2;
							edit.putInt("simbang_gidomun_which", 2);
						}else if(which == 3){
							simbang_gidomun_which = 3;
							edit.putInt("simbang_gidomun_which", 3);
						}else if(which == 4){
							simbang_gidomun_which = 4;
							edit.putInt("simbang_gidomun_which", 4);
						}else if(which == 5){
							simbang_gidomun_which = 5;
							edit.putInt("simbang_gidomun_which", 5);
						}else if(which == 6){
							simbang_gidomun_which = 6;
							edit.putInt("simbang_gidomun_which", 6);
						}else if(which == 7){
							simbang_gidomun_which = 7;
							edit.putInt("simbang_gidomun_which", 7);
						}else if(which == 8){
							simbang_gidomun_which = 8;
							edit.putInt("simbang_gidomun_which", 8);
						}else if(which == 9){
							simbang_gidomun_which = 9;
							edit.putInt("simbang_gidomun_which", 9);
						}else if(which == 10){
							simbang_gidomun_which = 10;
							edit.putInt("simbang_gidomun_which", 10);
						}else if(which == 11){
							simbang_gidomun_which = 11;
							edit.putInt("simbang_gidomun_which", 11);
						}else if(which == 12){
							simbang_gidomun_which = 12;
							edit.putInt("simbang_gidomun_which", 12);
						}else if(which == 13){
							simbang_gidomun_which = 13;
							edit.putInt("simbang_gidomun_which", 13);
						}else if(which == 14){
							simbang_gidomun_which = 14;
							edit.putInt("simbang_gidomun_which", 14);
						}else if(which == 15){
							simbang_gidomun_which = 15;
							edit.putInt("simbang_gidomun_which", 15);
						}else if(which == 16){
							simbang_gidomun_which = 16;
							edit.putInt("simbang_gidomun_which", 16);
						}else if(which == 17){
							simbang_gidomun_which = 17;
							edit.putInt("simbang_gidomun_which", 17);
						}else if(which == 18){
							simbang_gidomun_which = 18;
							edit.putInt("simbang_gidomun_which", 18);
						}else if(which == 19){
							simbang_gidomun_which = 19;
							edit.putInt("simbang_gidomun_which", 19);
						}else if(which == 20){
							simbang_gidomun_which = 20;
							edit.putInt("simbang_gidomun_which", 20);
						}else if(which == 21){
							simbang_gidomun_which = 21;
							edit.putInt("simbang_gidomun_which", 21);
						}else if(which == 22){
							simbang_gidomun_which = 22;
							edit.putInt("simbang_gidomun_which", 22);
						}else if(which == 23){
							simbang_gidomun_which = 23;
							edit.putInt("simbang_gidomun_which", 23);
						}else if(which == 24){
							simbang_gidomun_which = 24;
							edit.putInt("simbang_gidomun_which", 24);
						}else if(which == 25){
							simbang_gidomun_which = 25;
							edit.putInt("simbang_gidomun_which", 25);
						}else if(which == 26){
							simbang_gidomun_which = 26;
							edit.putInt("simbang_gidomun_which", 26);
						}else if(which == 27){
							simbang_gidomun_which = 27;
							edit.putInt("simbang_gidomun_which", 27);
						}else if(which == 28){
							simbang_gidomun_which = 28;
							edit.putInt("simbang_gidomun_which", 28);
						}else if(which == 29){
							simbang_gidomun_which = 29;
							edit.putInt("simbang_gidomun_which", 29);
						}else if(which == 30){
							simbang_gidomun_which = 30;
							edit.putInt("simbang_gidomun_which", 30);
						}else if(which == 31){
							simbang_gidomun_which = 31;
							edit.putInt("simbang_gidomun_which", 31);
						}else if(which == 32){
							simbang_gidomun_which = 32;
							edit.putInt("simbang_gidomun_which", 32);
						}else if(which == 33){
							simbang_gidomun_which = 33;
							edit.putInt("simbang_gidomun_which", 33);
						}else if(which == 34){
							simbang_gidomun_which = 34;
							edit.putInt("simbang_gidomun_which", 34);
						}else if(which == 35){
							simbang_gidomun_which = 35;
							edit.putInt("simbang_gidomun_which", 35);
						}else if(which == 36){
							simbang_gidomun_which = 36;
							edit.putInt("simbang_gidomun_which", 36);
						}else if(which == 37){
							simbang_gidomun_which = 37;
							edit.putInt("simbang_gidomun_which", 37);
						}else if(which == 38){
							simbang_gidomun_which = 38;
							edit.putInt("simbang_gidomun_which", 38);
						}else if(which == 39){
							simbang_gidomun_which = 39;
							edit.putInt("simbang_gidomun_which", 39);
						}else if(which == 40){
							simbang_gidomun_which = 40;
							edit.putInt("simbang_gidomun_which", 40);
						}else if(which == 41){
							simbang_gidomun_which = 41;
							edit.putInt("simbang_gidomun_which", 41);
						}else if(which == 42){
							simbang_gidomun_which = 42;
							edit.putInt("simbang_gidomun_which", 42);
						}else if(which == 43){
							simbang_gidomun_which = 43;
							edit.putInt("simbang_gidomun_which", 43);
						}else if(which == 44){
							simbang_gidomun_which = 44;
							edit.putInt("simbang_gidomun_which", 44);
						}else if(which == 45){
							simbang_gidomun_which = 45;
							edit.putInt("simbang_gidomun_which", 45);
						}else if(which == 46){
							simbang_gidomun_which = 46;
							edit.putInt("simbang_gidomun_which", 46);
						}else if(which == 47){
							simbang_gidomun_which = 47;
							edit.putInt("simbang_gidomun_which", 47);
						}else if(which == 48){
							simbang_gidomun_which = 48;
							edit.putInt("simbang_gidomun_which", 48);
						}else if(which == 49){
							simbang_gidomun_which = 49;
							edit.putInt("simbang_gidomun_which", 49);
						}else if(which == 50){
							simbang_gidomun_which = 50;
							edit.putInt("simbang_gidomun_which", 50);
						}else if(which == 51){
							simbang_gidomun_which = 51;
							edit.putInt("simbang_gidomun_which", 51);
						}
						bt_sub_description.setText(gidomun_simbang_description[simbang_gidomun_which]);
						edit.commit();
						displayList();
					}
				}).show();
			}else if(main_gidomun_which == 9){
				alertDialog = new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon64)
				.setSingleChoiceItems(gidomun_old_kyodok_description, old_kyodok_gidomun_which, new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which){
						dialog.dismiss();
						if(which == 0){
							old_kyodok_gidomun_which = 0;
							edit.putInt("old_kyodok_gidomun_which", 0);
						}else if(which == 1){
							old_kyodok_gidomun_which = 1;
							edit.putInt("old_kyodok_gidomun_which", 1);
						}else if(which == 2){
							old_kyodok_gidomun_which = 2;
							edit.putInt("old_kyodok_gidomun_which", 2);
						}else if(which == 3){
							old_kyodok_gidomun_which = 3;
							edit.putInt("old_kyodok_gidomun_which", 3);
						}else if(which == 4){
							old_kyodok_gidomun_which = 4;
							edit.putInt("old_kyodok_gidomun_which", 4);
						}else if(which == 5){
							old_kyodok_gidomun_which = 5;
							edit.putInt("old_kyodok_gidomun_which", 5);
						}else if(which == 6){
							old_kyodok_gidomun_which = 6;
							edit.putInt("old_kyodok_gidomun_which", 6);
						}else if(which == 7){
							old_kyodok_gidomun_which = 7;
							edit.putInt("old_kyodok_gidomun_which", 7);
						}else if(which == 8){
							old_kyodok_gidomun_which = 8;
							edit.putInt("old_kyodok_gidomun_which", 8);
						}else if(which == 9){
							old_kyodok_gidomun_which = 9;
							edit.putInt("old_kyodok_gidomun_which", 9);
						}else if(which == 10){
							old_kyodok_gidomun_which = 10;
							edit.putInt("old_kyodok_gidomun_which", 10);
						}else if(which == 11){
							old_kyodok_gidomun_which = 11;
							edit.putInt("old_kyodok_gidomun_which", 11);
						}else if(which == 12){
							old_kyodok_gidomun_which = 12;
							edit.putInt("old_kyodok_gidomun_which", 12);
						}else if(which == 13){
							kyodok_gidomun_which = 13;
							edit.putInt("old_kyodok_gidomun_which", 13);
						}else if(which == 14){
							old_kyodok_gidomun_which = 14;
							edit.putInt("old_kyodok_gidomun_which", 14);
						}else if(which == 15){
							old_kyodok_gidomun_which = 15;
							edit.putInt("old_kyodok_gidomun_which", 15);
						}else if(which == 16){
							old_kyodok_gidomun_which = 16;
							edit.putInt("old_kyodok_gidomun_which", 16);
						}else if(which == 17){
							old_kyodok_gidomun_which = 17;
							edit.putInt("old_kyodok_gidomun_which", 17);
						}else if(which == 18){
							old_kyodok_gidomun_which = 18;
							edit.putInt("old_kyodok_gidomun_which", 18);
						}else if(which == 19){
							old_kyodok_gidomun_which = 19;
							edit.putInt("old_kyodok_gidomun_which", 19);
						}else if(which == 20){
							old_kyodok_gidomun_which = 20;
							edit.putInt("old_kyodok_gidomun_which", 20);
						}else if(which == 21){
							old_kyodok_gidomun_which = 21;
							edit.putInt("old_kyodok_gidomun_which", 21);
						}else if(which == 22){
							old_kyodok_gidomun_which = 22;
							edit.putInt("old_kyodok_gidomun_which", 22);
						}else if(which == 23){
							old_kyodok_gidomun_which = 23;
							edit.putInt("old_kyodok_gidomun_which", 23);
						}else if(which == 24){
							old_kyodok_gidomun_which = 24;
							edit.putInt("old_kyodok_gidomun_which", 24);
						}else if(which == 25){
							old_kyodok_gidomun_which = 25;
							edit.putInt("old_kyodok_gidomun_which", 25);
						}else if(which == 26){
							old_kyodok_gidomun_which = 26;
							edit.putInt("old_kyodok_gidomun_which", 26);
						}else if(which == 27){
							old_kyodok_gidomun_which = 27;
							edit.putInt("old_kyodok_gidomun_which", 27);
						}else if(which == 28){
							old_kyodok_gidomun_which = 28;
							edit.putInt("old_kyodok_gidomun_which", 28);
						}else if(which == 29){
							old_kyodok_gidomun_which = 29;
							edit.putInt("old_kyodok_gidomun_which", 29);
						}else if(which == 30){
							old_kyodok_gidomun_which = 30;
							edit.putInt("old_kyodok_gidomun_which", 30);
						}else if(which == 31){
							old_kyodok_gidomun_which = 31;
							edit.putInt("old_kyodok_gidomun_which", 31);
						}else if(which == 32){
							old_kyodok_gidomun_which = 32;
							edit.putInt("old_kyodok_gidomun_which", 32);
						}else if(which == 33){
							old_kyodok_gidomun_which = 33;
							edit.putInt("old_kyodok_gidomun_which", 33);
						}else if(which == 34){
							old_kyodok_gidomun_which = 34;
							edit.putInt("old_kyodok_gidomun_which", 34);
						}else if(which == 35){
							old_kyodok_gidomun_which = 35;
							edit.putInt("old_kyodok_gidomun_which", 35);
						}else if(which == 36){
							old_kyodok_gidomun_which = 36;
							edit.putInt("old_kyodok_gidomun_which", 36);
						}else if(which == 37){
							old_kyodok_gidomun_which = 37;
							edit.putInt("old_kyodok_gidomun_which", 37);
						}else if(which == 38){
							old_kyodok_gidomun_which = 38;
							edit.putInt("old_kyodok_gidomun_which", 38);
						}else if(which == 39){
							old_kyodok_gidomun_which = 39;
							edit.putInt("old_kyodok_gidomun_which", 39);
						}else if(which == 40){
							old_kyodok_gidomun_which = 40;
							edit.putInt("old_kyodok_gidomun_which", 40);
						}else if(which == 41){
							old_kyodok_gidomun_which = 41;
							edit.putInt("old_kyodok_gidomun_which", 41);
						}else if(which == 42){
							old_kyodok_gidomun_which = 42;
							edit.putInt("old_kyodok_gidomun_which", 42);
						}else if(which == 43){
							old_kyodok_gidomun_which = 43;
							edit.putInt("old_kyodok_gidomun_which", 43);
						}else if(which == 44){
							old_kyodok_gidomun_which = 44;
							edit.putInt("old_kyodok_gidomun_which", 44);
						}else if(which == 45){
							old_kyodok_gidomun_which = 45;
							edit.putInt("old_kyodok_gidomun_which", 45);
						}else if(which == 46){
							old_kyodok_gidomun_which = 46;
							edit.putInt("old_kyodok_gidomun_which", 46);
						}else if(which == 47){
							old_kyodok_gidomun_which = 47;
							edit.putInt("old_kyodok_gidomun_which", 47);
						}else if(which == 48){
							old_kyodok_gidomun_which = 48;
							edit.putInt("old_kyodok_gidomun_which", 48);
						}else if(which == 49){
							old_kyodok_gidomun_which = 49;
							edit.putInt("old_kyodok_gidomun_which", 49);
						}else if(which == 50){
							old_kyodok_gidomun_which = 50;
							edit.putInt("old_kyodok_gidomun_which", 50);
						}else if(which == 51){
							old_kyodok_gidomun_which = 51;
							edit.putInt("old_kyodok_gidomun_which", 51);
						}else if(which == 52){
							old_kyodok_gidomun_which = 52;
							edit.putInt("old_kyodok_gidomun_which", 52);
						}else if(which == 53){
							old_kyodok_gidomun_which = 53;
							edit.putInt("old_kyodok_gidomun_which", 53);
						}else if(which == 54){
							old_kyodok_gidomun_which = 54;
							edit.putInt("old_kyodok_gidomun_which", 54);
						}else if(which == 55){
							old_kyodok_gidomun_which = 55;
							edit.putInt("old_kyodok_gidomun_which", 55);
						}else if(which == 56){
							old_kyodok_gidomun_which = 56;
							edit.putInt("old_kyodok_gidomun_which", 56);
						}else if(which == 57){
							old_kyodok_gidomun_which = 57;
							edit.putInt("old_kyodok_gidomun_which", 57);
						}else if(which == 58){
							old_kyodok_gidomun_which = 58;
							edit.putInt("old_kyodok_gidomun_which", 58);
						}else if(which == 59){
							old_kyodok_gidomun_which = 59;
							edit.putInt("old_kyodok_gidomun_which", 59);
						}else if(which == 60){
							old_kyodok_gidomun_which = 60;
							edit.putInt("old_kyodok_gidomun_which", 60);
						}else if(which == 61){
							old_kyodok_gidomun_which = 61;
							edit.putInt("old_kyodok_gidomun_which", 61);
						}else if(which == 62){
							old_kyodok_gidomun_which = 62;
							edit.putInt("old_kyodok_gidomun_which", 62);
						}else if(which == 63){
							old_kyodok_gidomun_which = 63;
							edit.putInt("old_kyodok_gidomun_which", 63);
						}else if(which == 64){
							old_kyodok_gidomun_which = 64;
							edit.putInt("old_kyodok_gidomun_which", 64);
						}else if(which == 65){
							old_kyodok_gidomun_which = 65;
							edit.putInt("old_kyodok_gidomun_which", 65);
						}else if(which == 66){
							old_kyodok_gidomun_which = 66;
							edit.putInt("old_kyodok_gidomun_which", 66);
						}else if(which == 67){
							old_kyodok_gidomun_which = 67;
							edit.putInt("old_kyodok_gidomun_which", 67);
						}else if(which == 68){
							old_kyodok_gidomun_which = 68;
							edit.putInt("old_kyodok_gidomun_which", 68);
						}else if(which == 69){
							old_kyodok_gidomun_which = 69;
							edit.putInt("old_kyodok_gidomun_which", 69);
						}else if(which == 70){
							old_kyodok_gidomun_which = 70;
							edit.putInt("old_kyodok_gidomun_which", 70);
						}else if(which == 71){
							old_kyodok_gidomun_which = 71;
							edit.putInt("old_kyodok_gidomun_which", 71);
						}else if(which == 72){
							old_kyodok_gidomun_which = 72;
							edit.putInt("old_kyodok_gidomun_which", 72);
						}else if(which == 73){
							old_kyodok_gidomun_which = 73;
							edit.putInt("old_kyodok_gidomun_which", 73);
						}else if(which == 74){
							old_kyodok_gidomun_which = 74;
							edit.putInt("old_kyodok_gidomun_which", 74);
						}else if(which == 75){
							old_kyodok_gidomun_which = 75;
							edit.putInt("old_kyodok_gidomun_which", 75);
						}else if(which == 76){
							old_kyodok_gidomun_which = 76;
							edit.putInt("old_kyodok_gidomun_which", 76);
						}else if(which == 77){
							old_kyodok_gidomun_which = 77;
							edit.putInt("old_kyodok_gidomun_which", 77);
						}else if(which == 78){
							old_kyodok_gidomun_which = 78;
							edit.putInt("old_kyodok_gidomun_which", 78);
						}
						bt_sub_description.setText(gidomun_old_kyodok_description[old_kyodok_gidomun_which]);
						edit.commit();
						displayList();
					}
				}).show();
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

	public class Bible_GidomunAdapter<T extends Sub7_ColumData> extends ArrayAdapter<T> implements OnClickListener{
		   public List<T> contactsList;
		   Button bt_zoom_out, bt_zoom_in;
		   int normal_textSize = 15;
		   
		   public Bible_GidomunAdapter(Context context, int textViewResourceId, List<T> items, Button bt_zoom_out, Button bt_zoom_in) {
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
				view = vi.inflate(R.layout.activity_sub7_listrow, null);
			}
			
			final T contacts = contactsList.get(position);
			if(contacts != null){
				pref = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_gidomun_string), Activity.MODE_PRIVATE);
				normal_textSize = pref.getInt("normal_textSize", 15);
				
				TextView txt_gidomun_content = (TextView)view.findViewById(R.id.txt_gidomun_content);
				txt_gidomun_content.setText(contacts.getGidomun_content());
				txt_gidomun_content.setTextColor(Color.BLACK);
				txt_gidomun_content.setTextSize(normal_textSize);
				
			}
			return view;
		}

		@Override
		public void onClick(View view) {
			settings = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_gidomun_string), MODE_PRIVATE);
			edit = settings.edit();
			if(view == bt_zoom_out){
				normal_textSize--;
				edit.putInt("normal_textSize", normal_textSize);
				if(adapter != null){
					adapter.notifyDataSetChanged();
				}
			}else if(view == bt_zoom_in){
				normal_textSize++;
				edit.putInt("normal_textSize", normal_textSize);
				if(adapter != null){
					adapter.notifyDataSetChanged();
				}
			}
			edit.commit();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		String[] list_alert ={context.getString(R.string.txt_share)};
		contactsList = getContactsList();
		final String gidomun_main_description = bt_main_description.getText().toString();
		final String gidomun_content = contactsList.get(position).getGidomun_content();
		new AlertDialog.Builder(this)
    	.setTitle(gidomun_main_description)
    	.setItems(list_alert, new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			if(which == 0){
    				Intent intent = new Intent(Intent.ACTION_SEND);
    				intent.setType("text/plain");    
    				intent.addCategory(Intent.CATEGORY_DEFAULT);
//    				intent.putExtra(Intent.EXTRA_SUBJECT, title);
    				intent.putExtra(Intent.EXTRA_TEXT, gidomun_main_description + "\n" +gidomun_content);
//    				intent.putExtra(Intent.EXTRA_TITLE, title);
    				startActivity(Intent.createChooser(intent, context.getString(R.string.txt_share)));
    			}
    		}
    	}).show();
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
