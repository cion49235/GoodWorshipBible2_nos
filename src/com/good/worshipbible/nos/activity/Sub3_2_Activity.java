package com.good.worshipbible.nos.activity;

import java.io.InputStream;

import com.admixer.AdAdapter;
import com.admixer.AdInfo;
import com.admixer.AdMixerManager;
import com.admixer.AdView;
import com.admixer.AdViewListener;
import com.admixer.InterstitialAd;
import com.admixer.InterstitialAdListener;
import com.good.worshipbible.nos.R;
import com.good.worshipbible.nos.data.Const;
import com.good.worshipbible.nos.util.PreferenceUtil;
import com.good.worshipbible.nos.util.SimpleCrypto;
import com.good.worshipbible.nos.util.TimeUtil;
import com.good.worshipbible.nos.util.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import uk.co.senab.photoview.PhotoViewAttacher;
public class Sub3_2_Activity extends Activity implements OnTouchListener, AdViewListener, OnClickListener, InterstitialAdListener{
	public static ImageView img_hymn;
	public static Context context;
	public DownloadImageAsync downloadImageAsync = null;
	public static LinearLayout layout_img_biblesong, layout_progress, layout_nodata;
	public static int id;
	public static String title;
	public static int description;
	public static RelativeLayout ad_layout;
	public static Handler navigator_handler = new Handler();
	public static MediaPlayer mediaPlayer;
	public int seekBackwardTime = 5000; // 5000 milliseconds
	public int seekForwardtime = 5000; // 5000 milliseconds
	public int duration_check = 0;
	public static boolean CALL_STATE_OFFHOOK = false;
	public static boolean CALL_STATE_RINGING = false;
	public static TextView txt_hymn_title, current_time;
	public static ImageButton bt_pause;
	public static RelativeLayout hymn_control_panel_layout;
	public MediaPlayAsync mediaPlayAsync = null;
	public Handler handler = new Handler();
	private NativeExpressAdView admobNative;
	private com.admixer.InterstitialAd interstialAd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub3_2);
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
		mediaPlayer = new MediaPlayer();
		hymn_control_panel_layout = (RelativeLayout)findViewById(R.id.hymn_control_panel_layout);
		layout_img_biblesong = (LinearLayout)findViewById(R.id.layout_img_biblesong);
		layout_nodata = (LinearLayout)findViewById(R.id.layout_nodata);
		layout_progress = (LinearLayout)findViewById(R.id.layout_progress);
		img_hymn = (ImageView)findViewById(R.id.img_hymn);
		bt_pause = (ImageButton)findViewById(R.id.bt_pause);
		bt_pause.setOnClickListener(this);
		img_hymn.setOnTouchListener(this);
		txt_hymn_title = (TextView)findViewById(R.id.txt_hymn_title);
		current_time = (TextView)findViewById(R.id.current_time);
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

		id = getIntent().getIntExtra("id", id);
		title = getIntent().getStringExtra("title");
		description = getIntent().getIntExtra("description", description);
				
		if(description == 1){
		try{
			String hymnsong_old = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.txt_hymnsong_url_old));
			String hymnlyrics_old = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.txt_hymnlyrics_url_old));
			hymn_control_panel_layout.setVisibility(View.VISIBLE);
			downloadImageAsync = new DownloadImageAsync(img_hymn, hymnlyrics_old + Integer.toString(id) + ".gif");
			downloadImageAsync.execute();
			mediaPlayAsync = new MediaPlayAsync();
			mediaPlayAsync.execute(hymnsong_old + Integer.toString(id) + context.getString(R.string.txt_hymn_type));
		}catch(Exception e){
		}
		}else if(description == 2){
			try{
				String hymnsong_new = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.txt_hymnsong_url_new));
				String hymnlyrics_new = SimpleCrypto.decrypt(Utils.get_data, context.getString(R.string.txt_hymnlyrics_url_new));
				hymn_control_panel_layout.setVisibility(View.VISIBLE);
				downloadImageAsync = new DownloadImageAsync(img_hymn, hymnlyrics_new + Integer.toString(id) + ".JPG");
				downloadImageAsync.execute();
				mediaPlayAsync = new MediaPlayAsync();
				mediaPlayAsync.execute(hymnsong_new + Integer.toString(id) + context.getString(R.string.txt_hymn_type));
			}catch(Exception e){
			}
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
	protected void onDestroy() {
		super.onDestroy();
//		admobNative.destroy();
		if(downloadImageAsync != null){
			downloadImageAsync.cancel(true);
		}
		if(mediaPlayAsync != null){
			mediaPlayAsync.cancel(true);
		}
		navigator_handler.removeCallbacks(UpdateTimetask);
		if(mediaPlayer.isPlaying()){
         	mediaPlayer.stop();
		}
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
	
	public class DownloadImageAsync extends AsyncTask<String, Void, Bitmap> {
	    public ImageView img_hymn;
	    public String url;
	    private PhotoViewAttacher mAttacher;
	    public DownloadImageAsync(ImageView img_hymn, String url) {
	        this.img_hymn = img_hymn;
	        this.url = url;
	    }
	    
	    @Override
	    protected void onPreExecute() {
	    	super.onPreExecute();
	    	layout_progress.setVisibility(View.VISIBLE);
	    }
	    @Override
	    protected Bitmap doInBackground(String... params) {
	    	Bitmap bimap = null;
	    	try {
	    		InputStream in = new java.net.URL(url).openStream();
	    		BitmapFactory.Options options = new BitmapFactory.Options();
	    		options.inPreferredConfig = Config.RGB_565;
	    		bimap = BitmapFactory.decodeStream(in, null, options);
	    	} catch (Exception e) {
	    	}
	    	return bimap;
	    }
	    
	   @Override
	   protected void onPostExecute(Bitmap Response) {
		   super.onPostExecute(Response);
//		   Log.i("dsu"	,"url : " + Response);
		   if(downloadImageAsync != null){
				downloadImageAsync.cancel(true);
			}
		   layout_progress.setVisibility(View.GONE);
		   try{
	    		if(Response != null){
	    			Toast.makeText(context, context.getString(R.string.txt_hymn_toast), Toast.LENGTH_SHORT).show();
	    			img_hymn.setImageBitmap(Response);
	    			mAttacher = new PhotoViewAttacher(img_hymn);
//	    			img_hymn.setScaleType(ScaleType.MATRIX);	
	    			layout_nodata.setVisibility(View.GONE);
	    		}else{
	    			layout_nodata.setVisibility(View.VISIBLE);
	    		}
	    	}catch(NullPointerException e){
	    	}
	   }
	}
	
	public class MediaPlayAsync extends AsyncTask<String, Long, Integer> implements OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener,android.widget.SeekBar.OnSeekBarChangeListener, OnErrorListener {
		public int result = -1;
		public MediaPlayAsync() {
		}
		@Override
        protected void onPreExecute() {
			try{
				txt_hymn_title.setText(context.getString(R.string.txt_hymn_ready));
	            navigator_handler.removeCallbacks(UpdateTimetask);
	            if(mediaPlayer.isPlaying()){
	            	mediaPlayer.stop();
	            }
			}catch(Exception e) {
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
				
				mediaPlayer.reset();
	            mediaPlayer.setDataSource(params[0]);
	            mediaPlayer.prepare();
	            
				mediaPlayer.seekTo(0);
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
				if(mediaPlayAsync != null){
					mediaPlayAsync.cancel(true);
				}
				mediaPlayer.start();
				handler.postDelayed(new Runnable() {
					 @Override
					 public void run() {
						 if(mediaPlayer.isPlaying() == true){
							 mediaPlayer.pause();
							 Toast.makeText(context, context.getString(R.string.txt_hymn_ready2), Toast.LENGTH_LONG).show();
						 }
					 }
				 },500);
			}else{
				mediaPlayAsync = new MediaPlayAsync();
				mediaPlayAsync.execute();
			}
		}

		@Override
		public void onPrepared(MediaPlayer mp) {
        	txt_hymn_title.setText(title);
		}
        
		@Override
		public void onBufferingUpdate(MediaPlayer mediaPlayer, int buffering) {
		}
		@Override
		public void onCompletion(MediaPlayer mp) {
			mediaPlayer.seekTo(0);
			if(mediaPlayer.isPlaying()){
				mediaPlayer.stop();
			}
		}
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			navigator_handler.removeCallbacks(UpdateTimetask);
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			navigator_handler.removeCallbacks(UpdateTimetask);
			int totalDuration = mediaPlayer.getDuration();
			int currentPosition = TimeUtil.progressToTimer(seekBar.getProgress(), totalDuration);
			// forward or backward to certain seconds
			mediaPlayer.seekTo(currentPosition);
			if (mediaPlayer.isPlaying()){
		    // update timer progress again
		      updateProgressBar();
		      return;
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

	public static void updateProgressBar(){
		navigator_handler.postDelayed(UpdateTimetask, 100);
	}
	
	public static Runnable UpdateTimetask = new Runnable() {
		@Override
		public void run() {
			if(mediaPlayer != null){
				if(mediaPlayer.isPlaying()){
					txt_hymn_title.setText(title);
					bt_pause.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_media_pause_small));
				}else{
					txt_hymn_title.setText(context.getString(R.string.txt_hymn_ready));
					bt_pause.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_media_play_small));
				}
				long currentDuration = mediaPlayer.getCurrentPosition();
				// Displaying Total Duration time
				current_time.setText(""+TimeUtil.milliSecondsToTimer(currentDuration));
				// Updating progress bar
				navigator_handler.postDelayed(this, 100);	
			}
		}
	};
	
	@Override
	public void onClick(View view) {
		if(view == bt_pause){
			if(mediaPlayer.isPlaying()){
				mediaPlayer.pause();
				bt_pause.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_media_play_small));
			}else{
				mediaPlayer.start();
				bt_pause.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_media_pause_small));
			}
		}
	}
	
	
	public void setImagePit(){
        float[] value = new float[9];
        matrix.getValues(value);
        int width = img_hymn.getWidth();
        int height = img_hymn.getHeight();
        Drawable d = img_hymn.getDrawable();
        if (d == null)  return;
        int imageWidth = d.getIntrinsicWidth();
        int imageHeight = d.getIntrinsicHeight();
        int scaleWidth = (int) (imageWidth * value[0]);
        int scaleHeight = (int) (imageHeight * value[4]);
        value[2] = 0;
        value[5] = 0;
        if (imageWidth > width || imageHeight > height){
            int target = WIDTH;
            if (imageWidth < imageHeight) target = HEIGHT;
            
            if (target == WIDTH) value[0] = value[4] = (float)width / imageWidth;
            if (target == HEIGHT) value[0] = value[4] = (float)height / imageHeight;
            
            scaleWidth = (int) (imageWidth * value[0]);
            scaleHeight = (int) (imageHeight * value[4]);
            
            if (scaleWidth > width) value[0] = value[4] = (float)width / imageWidth;
            if (scaleHeight > height) value[0] = value[4] = (float)height / imageHeight;
        }
        
        scaleWidth = (int) (imageWidth * value[0]);
        scaleHeight = (int) (imageHeight * value[4]);
        if (scaleWidth < width){
            value[2] = (float) width / 2 - (float)scaleWidth / 2;
        }
        if (scaleHeight < height){
            value[5] = (float) height / 2 - (float)scaleHeight / 2;
        }
        matrix.setValues(value);
        img_hymn.setImageMatrix(matrix);
    }
	
	private float spacing(MotionEvent event) {
	       float x = event.getX(0) - event.getX(1);
	       float y = event.getY(0) - event.getY(1);
	       return (float)Math.sin(x * x + y * y);
	}
	
	private void midPoint(PointF point, MotionEvent event) {
	       float x = event.getX(0) + event.getX(1);
	       float y = event.getY(0) + event.getY(1);
	       point.set(x / 2, y / 2);
	}
	
	private void matrixTurning(Matrix matrix, ImageView view){
        float[] value = new float[9];
        matrix.getValues(value);
        float[] savedValue = new float[9];
        savedMatrix2.getValues(savedValue);

        int width = view.getWidth();
        int height = view.getHeight();
        
        Drawable d = view.getDrawable();
        if (d == null)  return;
        int imageWidth = d.getIntrinsicWidth();
        int imageHeight = d.getIntrinsicHeight();
        int scaleWidth = (int) (imageWidth * value[0]);
        int scaleHeight = (int) (imageHeight * value[4]);

        if (value[2] < width - scaleWidth)   value[2] = width - scaleWidth;
        if (value[5] < height - scaleHeight)   value[5] = height - scaleHeight;
        if (value[2] > 0)   value[2] = 0;
        if (value[5] > 0)   value[5] = 0;
        
        if (value[0] > 10 || value[4] > 10){
            value[0] = savedValue[0];
            value[4] = savedValue[4];
            value[2] = savedValue[2];
            value[5] = savedValue[5];
        }
        
        if (imageWidth > width || imageHeight > height){
            if (scaleWidth < width && scaleHeight < height){
                int target = WIDTH;
                if (imageWidth < imageHeight) target = HEIGHT;
                
                if (target == WIDTH) value[0] = value[4] = (float)width / imageWidth;
                if (target == HEIGHT) value[0] = value[4] = (float)height / imageHeight;
                
                scaleWidth = (int) (imageWidth * value[0]);
                scaleHeight = (int) (imageHeight * value[4]);
                
                if (scaleWidth > width) value[0] = value[4] = (float)width / imageWidth;
                if (scaleHeight > height) value[0] = value[4] = (float)height / imageHeight;
            }
        }
        
        else{
            if (value[0] < 1)   value[0] = 1;
            if (value[4] < 1)   value[4] = 1;
        }
        
        scaleWidth = (int) (imageWidth * value[0]);
        scaleHeight = (int) (imageHeight * value[4]);
        if (scaleWidth < width){
            value[2] = (float) width / 2 - (float)scaleWidth / 2;
        }
        if (scaleHeight < height){
            value[5] = (float) height / 2 - (float)scaleHeight / 2;
        }
        
        matrix.setValues(value);
        savedMatrix2.set(matrix);
    }
	
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView view = (ImageView) v;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN:
           savedMatrix.set(matrix);
           start.set(event.getX(), event.getY());
           mode = DRAG;
           break;
        case MotionEvent.ACTION_POINTER_DOWN:
           oldDist = spacing(event);
           if (oldDist > 10f) {
              savedMatrix.set(matrix);
              midPoint(mid, event);
              mode = ZOOM;
           }
           break;
       case MotionEvent.ACTION_UP:
       case MotionEvent.ACTION_POINTER_UP:
           mode = NONE;
           break;
       case MotionEvent.ACTION_MOVE:
           if (mode == DRAG) {
             matrix.set(savedMatrix);
             matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
           }
           else if (mode == ZOOM) {
              float newDist = spacing(event);
              if (newDist > 10f) {
                 matrix.set(savedMatrix);
                 float scale = newDist / oldDist;
                 matrix.postScale(scale, scale, mid.x, mid.y);
              }
           }
           break;
        }
        matrixTurning(matrix, view);
        view.setImageMatrix(matrix);
        return true;
    }
    
    public void addInterstitialView() {
    	if(interstialAd == null) {
        	AdInfo adInfo = new AdInfo("u6dbtyd1");
//        	adInfo.setTestMode(false);
        	interstialAd = new com.admixer.InterstitialAd(this);
        	interstialAd.setAdInfo(adInfo, this);
        	interstialAd.setInterstitialAdListener(this);
        	interstialAd.startInterstitial();
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
			if(!PreferenceUtil.getStringSharedData(context, PreferenceUtil.PREF_ISSUBSCRIBED, Const.isSubscribed).equals("true")){
				Toast.makeText(context, context.getString(R.string.txt_after_ad), Toast.LENGTH_LONG).show();
				addInterstitialView();				
			}
			 /*handler.postDelayed(new Runnable() {
				 @Override
				 public void run() {
					 onDestroy();
					 finish();
				 }
			 },2000);*/
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
    
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();
    private Matrix savedMatrix2 = new Matrix();
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    private PointF start = new PointF();
    private PointF mid = new PointF();
    private float oldDist = 1f;
    private static final int WIDTH = 0;
    private static final int HEIGHT = 1;
    
    private boolean isInit = false;
	@Override
	public void onInterstitialAdClosed(InterstitialAd arg0) {
		interstialAd = null;
		 onDestroy();
		 finish();
	}

	@Override
	public void onInterstitialAdFailedToReceive(int arg0, String arg1, InterstitialAd arg2) {
		interstialAd = null;	
		 onDestroy();
		 finish();
	}

	@Override
	public void onInterstitialAdReceived(String arg0, InterstitialAd arg1) {
		interstialAd = null;		
	}

	@Override
	public void onInterstitialAdShown(String arg0, InterstitialAd arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLeftClicked(String arg0, InterstitialAd arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRightClicked(String arg0, InterstitialAd arg1) {
		// TODO Auto-generated method stub
		
	}
    
    
}
		
