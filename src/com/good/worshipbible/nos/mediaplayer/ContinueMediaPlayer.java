package com.good.worshipbible.nos.mediaplayer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.good.worshipbible.nos.podcast.Sub5_1_Activity;
import com.good.worshipbible.nos.podcast.Sub5_5_Activity;
import com.good.worshipbible.nos.podcast.db.helper.Pause_DBOpenHelper;
import com.good.worshipbible.nos.util.TimeUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.squareup.picasso.Picasso;

public class ContinueMediaPlayer extends Activity implements OnClickListener, AdViewListener, InterstitialAdListener{
	public static LinearLayout layout_progress;
	public static Context context;
	public static SeekBar mediacontroller_progress;
	public static Handler navigator_handler = new Handler();
	public static TextView max_time, current_time, txt_description_title,txt_title, txt_pubDate ;
	public static ImageButton bt_duration_rew, bt_pause, bt_duration_ffwd, bt_rew, bt_ffwd;
	public int seekBackwardTime = 5000; // 5000 milliseconds
	public int seekForwardtime = 5000; // 5000 milliseconds
	public int duration_check = 0;
	public static boolean CALL_STATE_OFFHOOK = false;
	public static boolean CALL_STATE_RINGING = false;
	public static MediaPlayer mediaPlayer = new MediaPlayer();
	public static ArrayList<String> array_title,array_enclosure,array_pubDate,array_image,array_description_title;
	public static ImageView img_image, btn_media_continue;
	public MediaPlayAsync mediaPlayAsync = null;
	public static Cursor cursor;
	public static int current_position;
	public static Pause_DBOpenHelper pause_mydb;
	public int video_num = 0;
	SharedPreferences settings,pref;
	Editor edit;
	public static LinearLayout control_panel_layout;
	public static RelativeLayout ad_layout;
	public static InterstitialAd interstialAd;
	public Handler handler = new Handler();
	private NativeExpressAdView admobNative;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.continue_mediaplayer);
		context = this;
//		init_admob_naive();
		addBannerView();
//		Toast toast = Toast.makeText(context, context.getString(R.string.sub6_txt26), Toast.LENGTH_LONG);
//		toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
//		toast.show();
		AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD, "AX00056EB");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD_FULL, "AX00056EC");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMIXER, "u6dbtyd1");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB, "ca-app-pub-4637651494513698/9745545364");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB_FULL, "ca-app-pub-4637651494513698/2222278564");
		Sub5_1_Activity.onPause_DB_Continue(video_num);
		pause_mydb = new Pause_DBOpenHelper(context);
		layout_progress = (LinearLayout)findViewById(R.id.layout_progress);
		control_panel_layout = (LinearLayout)findViewById(R.id.control_panel_layout);
		mediacontroller_progress = (SeekBar)findViewById(R.id.mediacontroller_progress);
		max_time = (TextView)findViewById(R.id.max_time);
		current_time = (TextView)findViewById(R.id.current_time);
		txt_description_title = (TextView)findViewById(R.id.txt_description_title);
		txt_title = (TextView)findViewById(R.id.txt_title);
		txt_title.setSelected(true);
		txt_pubDate = (TextView)findViewById(R.id.txt_pubDate);
		img_image = (ImageView)findViewById(R.id.img_image);
		btn_media_continue = (ImageView)findViewById(R.id.btn_media_continue);
		btn_media_continue.setOnClickListener(this);
		bt_duration_rew = (ImageButton)findViewById(R.id.bt_duration_rew);
		bt_pause = (ImageButton)findViewById(R.id.bt_pause);
		bt_duration_ffwd = (ImageButton)findViewById(R.id.bt_duration_ffwd);
		bt_rew = (ImageButton)findViewById(R.id.bt_rew);
		bt_ffwd = (ImageButton)findViewById(R.id.bt_ffwd);
		bt_duration_rew.setOnClickListener(this);
		bt_pause.setOnClickListener(this);
		bt_duration_ffwd.setOnClickListener(this);
		bt_rew.setOnClickListener(this);
		bt_ffwd.setOnClickListener(this);
		
		TelephonyManager telephonymanager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		telephonymanager.listen(new PhoneStateListener() {
			public void onCallStateChanged(int state, String incomingNumber) {
				switch (state) {
				case TelephonyManager.CALL_STATE_IDLE: 
//					if ((duration_check > 0) && (mediaPlayer != null) && (!mediaPlayer.isPlaying())){
//						if(duration_check > 0){
//							mediaPlayer.seekTo(duration_check);						
//							mediaPlayer.start();
//						}
//					}
				case TelephonyManager.CALL_STATE_OFFHOOK:
					if ((mediaPlayer != null) && (mediaPlayer.isPlaying())){
						mediaPlayer.pause();
						duration_check = mediaPlayer.getCurrentPosition();
					}
				case TelephonyManager.CALL_STATE_RINGING:
					if ((mediaPlayer != null) && (mediaPlayer.isPlaying())){
						mediaPlayer.pause();
						duration_check = mediaPlayer.getCurrentPosition();
					}
				default: break;
				} 
			}
		}, PhoneStateListener.LISTEN_CALL_STATE); 
		
		array_title = getIntent().getStringArrayListExtra("array_title");
		array_enclosure = getIntent().getStringArrayListExtra("array_enclosure");
		array_pubDate = getIntent().getStringArrayListExtra("array_pubDate");
		array_image = getIntent().getStringArrayListExtra("array_image");
		array_description_title = getIntent().getStringArrayListExtra("array_description_title");
		
		video_num = getIntent().getIntExtra("video_num",array_title.size()-1);
		
//		Log.i("dsu", "notification_restart");
		mediaPlayAsync = new MediaPlayAsync();
		mediaPlayAsync.execute();
		
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		invokeFragmentManagerNoteStateNotSaved();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void invokeFragmentManagerNoteStateNotSaved() {
	    /**
	     * For post-Honeycomb devices
	     */
	    if (Build.VERSION.SDK_INT < 11) {
	        return;
	    }
	    try {
	        Class cls = getClass();
	        do {
	            cls = cls.getSuperclass();
	        } while (!"Activity".equals(cls.getSimpleName()));
	        Field fragmentMgrField = cls.getDeclaredField("mFragments");
	        fragmentMgrField.setAccessible(true);

	        Object fragmentMgr = fragmentMgrField.get(this);
	        cls = fragmentMgr.getClass();

	        Method noteStateNotSavedMethod = cls.getDeclaredMethod("noteStateNotSaved", new Class[] {});
	        noteStateNotSavedMethod.invoke(fragmentMgr, new Object[] {});
	        Log.d("dsu", "Successful call for noteStateNotSaved!!!");
	    } catch (Exception ex) {
	        Log.e("dsu", "Exception on worka FM.noteStateNotSaved", ex);
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
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
//		admobNative.destroy();
//		Sub5_1_Activity.setNotification_Cancel();
		navigator_handler.removeCallbacks(UpdateTimetask);
		Sub5_1_Activity.onPause_DB_Continue(video_num);
		if(mediaPlayAsync != null){
			mediaPlayAsync.cancel(true);
		}
		super.onDestroy();
	}
	@Override
	protected void onRestart() {
		super.onRestart();
		try{
			updateProgressBar();
	    	if(!mediaPlayer.isPlaying()){
	    		if(duration_check > 0){
	    			mediaPlayer.seekTo(duration_check);
	    			mediaPlayer.start();
	    		}
	    		return;
	    	}
	    }catch (IllegalStateException localIllegalStateException){
	    }
	    catch (IllegalArgumentException localIllegalArgumentException){
	    }
	    catch (NullPointerException localNullPointerException){
	    }
	}
	@Override
	protected void onUserLeaveHint() {
		Sub5_1_Activity.setNotification_continue(context, array_title, array_enclosure, array_pubDate, array_image, array_description_title, video_num );
		super.onUserLeaveHint();
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
    
    public static void updateProgressBar(){
		navigator_handler.postDelayed(UpdateTimetask, 100);
	}
    
    public static Runnable UpdateTimetask = new Runnable() {
		@Override
		public void run() {
			if(mediaPlayer != null){
				if(mediaPlayer.isPlaying()){
					layout_progress.setVisibility(View.GONE);
					bt_pause.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_media_pause));
				}else{
					bt_pause.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_media_play));
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
	
	public class MediaPlayAsync extends AsyncTask<String, Long, Integer> implements OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener,android.widget.SeekBar.OnSeekBarChangeListener, OnErrorListener {
		public int result = -1;
		public MediaPlayAsync() {
		}
		
		@Override
        protected void onPreExecute() {
			try{
				layout_progress.setVisibility(View.VISIBLE);
//				control_panel_layout.setVisibility(View.INVISIBLE);
				
				cursor = pause_mydb.getReadableDatabase().rawQuery("select * from video_pause WHERE video_title = '"+array_title.get(video_num)+"' AND video_pubDate = '"+array_pubDate.get(video_num)+"'", null);
	            if(cursor != null && cursor.moveToFirst()) {
//	            	 Log.i("dsu", "이어서재생========> : " + cursor.getString(1) + "\n날짜 : " + cursor.getString(3));
	            	current_position = cursor.getInt(cursor.getColumnIndex("video_currentPosition"));
	            }else{
	            	current_position = 0;
//	            	 Log.i("dsu", "처음부터재생=======>");
	            }
	            navigator_handler.removeCallbacks(UpdateTimetask);
	            if(mediaPlayer.isPlaying()){
	            	mediaPlayer.stop();
	            }
	            if(CustomMediaPlayer.mediaPlayer.isPlaying()){
	            	CustomMediaPlayer.mediaPlayer.stop();
	            }
			}catch(Exception e){
			}finally{
				if(pause_mydb != null){
					pause_mydb.close();
				}
			}
            super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(String... params) {
			try{
				mediaPlayer.setOnBufferingUpdateListener(this);
				mediaPlayer.setOnCompletionListener(this);
				mediaPlayer.setOnErrorListener(this);
				mediaPlayer.setOnPreparedListener(this);
				mediacontroller_progress.setOnSeekBarChangeListener(this);
				
				mediaPlayer.reset();
	            mediaPlayer.setDataSource(array_enclosure.get(video_num));
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
			if(mediaPlayer != null){
				if(mediaPlayer.isPlaying()){
					layout_progress.setVisibility(View.GONE);
					bt_pause.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_media_pause));
				}else{
					layout_progress.setVisibility(View.VISIBLE);
					bt_pause.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_media_play));
				}
			}
			if(result == 1){
				if(mediaPlayAsync != null){
					mediaPlayAsync.cancel(true);
				}
				Sub5_1_Activity.setNotification_continue(context, array_title, array_enclosure, array_pubDate, array_image, array_description_title, video_num );
				mediaPlayer.start();
				layout_progress.setVisibility(View.GONE);
//				Toast.makeText(context, context.getString(R.string.txt_custom_mediaplayer2), Toast.LENGTH_SHORT).show();
//				control_panel_layout.setVisibility(View.VISIBLE);
			}else{
				mediaPlayAsync = new MediaPlayAsync();
				mediaPlayAsync.execute();
			}
		}
        
        @Override
		public void onPrepared(MediaPlayer mp) {
        	txt_description_title.setText(array_description_title.get(video_num));
    		txt_title.setText(array_title.get(video_num));
    		txt_pubDate.setText(array_pubDate.get(video_num));

    		Picasso.with(context)
		    .load(array_image.get(video_num))
		    .placeholder(R.drawable.no_image)
		    .error(R.drawable.no_image)
		    .into(img_image);
    		
    		settings = getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), MODE_PRIVATE);
    		edit = settings.edit();
    		edit.putString("title", array_title.get(video_num));
    		edit.putString("pubDate", array_pubDate.get(video_num));
    		edit.commit();
    		if(Sub5_5_Activity.adapter != null){
    			Sub5_5_Activity.adapter.notifyDataSetChanged();	
    		}
		}
        
		@Override
		public void onBufferingUpdate(MediaPlayer mediaPlayer, int buffering) {
			mediacontroller_progress.setSecondaryProgress(buffering);
		}
		@Override
		public void onCompletion(MediaPlayer mp) {
			mediaPlayer.seekTo(0);
			onDestroy();
			if(mediaPlayer.isPlaying()){
				mediaPlayer.stop();
//				Log.i("dsu", "재생완료");
			}
			if(video_num > 0){
				video_num = video_num - 1;
				mediaPlayAsync = new MediaPlayAsync();
				mediaPlayAsync.execute();
			}else{
				video_num = array_title.size()-1;
				mediaPlayAsync = new MediaPlayAsync();
				mediaPlayAsync.execute();
			}
		}
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			navigator_handler.removeCallbacks(UpdateTimetask);
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
			layout_progress.setVisibility(View.VISIBLE);
			navigator_handler.removeCallbacks(UpdateTimetask);
//			finish();
			return false;
		}
	}
	@Override
	public void onClick(View view) {
		if(view == bt_pause){
			if(mediaPlayer.isPlaying()){
				mediaPlayer.pause();
				bt_pause.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_media_play));
			}else{
				mediaPlayer.start();
				bt_pause.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_media_pause));
			}
		}else if(view == bt_duration_rew){
			int currentPosition = mediaPlayer.getCurrentPosition();
			// check if seekBackward time is greater than 0 sec
			if(currentPosition - seekBackwardTime >= 0){
				// forward song
				mediaPlayer.seekTo(currentPosition - seekBackwardTime); 
			}else{
				// backward to starting position
				mediaPlayer.seekTo(0);
			}
		}else if(view == bt_duration_ffwd){
			int currentPosition = mediaPlayer.getCurrentPosition();
			if(currentPosition + seekForwardtime <= mediaPlayer.getDuration()){
				// forward song
				mediaPlayer.seekTo(currentPosition + seekForwardtime);
			}else{
				// forward to end position
				mediaPlayer.seekTo(mediaPlayer.getDuration());
			}
		}else if(view == bt_rew){
			if(video_num < array_title.size() -1){
				onDestroy();
				mediaPlayer.pause();
				navigator_handler.removeCallbacks(UpdateTimetask);
				video_num = video_num + 1;
				mediaPlayAsync = new MediaPlayAsync();
				mediaPlayAsync.execute();
//				Log.i("dsu", "video_num : " + video_num);
			}else{
				Toast.makeText(context, context.getString(R.string.sub5_txt22), Toast.LENGTH_SHORT).show();
			}
		}else if(view == bt_ffwd){
			onDestroy();
			if(video_num > 0){
				mediaPlayer.pause();
				navigator_handler.removeCallbacks(UpdateTimetask);
				video_num = video_num - 1;
				mediaPlayAsync = new MediaPlayAsync();
				mediaPlayAsync.execute();
			}else{
				mediaPlayer.pause();
				navigator_handler.removeCallbacks(UpdateTimetask);
				video_num = array_title.size()-1;
				mediaPlayAsync = new MediaPlayAsync();
				mediaPlayAsync.execute();
			}
		}else if(view == btn_media_continue){
			Toast.makeText(context, context.getString(R.string.media_continue_ment), Toast.LENGTH_LONG).show();
			addInterstitialView();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
			if(ContinueMediaPlayer.mediaPlayer.isPlaying()){
				ContinueMediaPlayer.mediaPlayer.pause();
			}
			Toast.makeText(context, context.getString(R.string.txt_loding_ad), Toast.LENGTH_SHORT).show();
			addInterstitialView();
			 handler.postDelayed(new Runnable() {
				 @Override
				 public void run() {
					 if(ContinueMediaPlayer.mediaPlayer.isPlaying()){
						 ContinueMediaPlayer.mediaPlayer.pause();
					 }
					 onDestroy();
					 Sub5_1_Activity.setNotification_Cancel();
//					 Sub5_1_Activity.setNotification_continue(context, array_title, array_enclosure, array_pubDate, array_image, array_description_title, video_num );
					 finish();
				 }
			 },1500);
			 return false;
		}
		return super.onKeyDown(keyCode, event);
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
	
	//** InterstitialAd 이벤트들 *************
	@Override
	public void onInterstitialAdClosed(InterstitialAd arg0) {
//		Log.i("dsu", "전면광고 닫음 : arg0 : " + arg0) ;
		interstialAd = null;
		if(ContinueMediaPlayer.mediaPlayer.isPlaying()){
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
		}
	}

	@Override
	public void onInterstitialAdFailedToReceive(int arg0, String arg1,
			InterstitialAd arg2) {
//		Log.i("dsu", "전면광고 실패 : arg0 : " + arg0+"\n" + arg1) ;
		interstialAd = null;
		if(ContinueMediaPlayer.mediaPlayer.isPlaying()){
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
		}
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
}
