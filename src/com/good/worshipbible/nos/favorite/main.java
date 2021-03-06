//This is source code of favorite. Copyrightⓒ. Tarks. All Rights Reserved.
package com.good.worshipbible.nos.favorite;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.admixer.AdAdapter;
import com.admixer.AdInfo;
import com.admixer.AdMixerManager;
import com.admixer.AdView;
import com.admixer.AdViewListener;
import com.good.worshipbible.nos.R;
import com.good.worshipbible.nos.data.Const;
import com.good.worshipbible.nos.favorite.global.Global;
import com.good.worshipbible.nos.favorite.page.ProfileActivity;
import com.good.worshipbible.nos.util.PreferenceUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class main extends SherlockActivity implements AdViewListener {

	ListView listView;
	ArrayList<List> m_orders = new ArrayList<List>();
	ListAdapter m_adapter;
	Context context;
	public static RelativeLayout ad_layout;
	private NativeExpressAdView admobNative;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_listview);
        context = this;
        AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD, "AX00056EB");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD_FULL, "AX00056EC");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMIXER, "u6dbtyd1");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB, "ca-app-pub-4637651494513698/9745545364");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB_FULL, "ca-app-pub-4637651494513698/2222278564");
    	if(!PreferenceUtil.getStringSharedData(context, PreferenceUtil.PREF_ISSUBSCRIBED, Const.isSubscribed).equals("true")){
        	addBannerView();    		
    	}
//    	init_admob_naive();
        setList(context.getString(R.string.community), context.getString(R.string.community_subtitle), 309);
        setListAdapter();
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
    
    public void setList(String title, String des, int Tag){
    	List p1 = new List( title, des,  Tag);
		m_orders.add(p1);
		
    }
    
	public void setListAdapter() {
		listView = (ListView) findViewById(R.id.listView1);
		m_adapter = new ListAdapter(this, R.layout.favorite_list2, m_orders);
		listView.setAdapter(m_adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				ListAdapter ca = (ListAdapter)arg0.getAdapter();
				List ls = (List) ca.getItem(arg2);
				Intent intent = new Intent(main.this, ProfileActivity.class);
				intent.putExtra("member_srl", String.valueOf(ls.getTag()));
				startActivity(intent);
			}
		});
	}
    
	private class ListAdapter extends ArrayAdapter<List> {

		private ArrayList<List> items;

		public ListAdapter(Context context, int textViewResourceId,
				ArrayList<List> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			final List p = items.get(position);
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.favorite_list2, null);
				
			
			}
			
	
			if (p != null) {
				
			//	LinearLayout layout = (LinearLayout) v.findViewById(R.id.layoutback);
				TextView tt = (TextView) v.findViewById(R.id.title);
				TextView bt = (TextView) v.findViewById(R.id.description);
				ImageView image = (ImageView) v.findViewById(R.id.img);

			
				if (tt != null) {
					tt.setText(p.getTitle());
				}
				if (bt != null) {
					bt.setText(Global.getValue(p.getDes()));
				}
			
			}
			return v;
		}
	}

	class List {

		private String Title;
		private String Description;
		private int Tag;

		public List(String _Title, String _Description,
				int _Tag) {
			
			this.Title = _Title;
			this.Description = _Description;
			this.Tag = _Tag;
	}

	

		public String getTitle() {
			return Title;
		}

		public String getDes() {
			return Description;
		}

		
		public int getTag() {
			return Tag;
		}

		
	

	}

	/**
	 * 임의의 방법으로 더미 아이템을 추가합니다.
	 * 
	 * @param size
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
	//	this.optionsMenu = menu;
		MenuItem item;
		menu.add(0, 0, 0, getString(R.string.app_info)).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		return true;
	}

	// 빽백키 상단액션바
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			// Alert
			Intent intent1 = new Intent(main.this, info.class);
			startActivity(intent1);
			return true;
		default:
			return super.onOptionsItemSelected(item);
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
    

