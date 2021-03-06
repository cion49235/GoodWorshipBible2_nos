package com.good.worshipbible.nos.activity;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.good.worshipbible.nos.R;
import com.good.worshipbible.nos.util.PreferenceUtil;
import com.good.worshipbible.nos.util.StringUtil;
import com.good.worshipbible.nos.widget.DialogServicePopup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;


public class IntroActivity extends Activity{
	public Handler handler;
	public Context context;
	public KBB_Async kbb_Async = null;
	public KJV_Async kjv_Async = null;
	public KKK_Async kkk_Async = null;
	
	public JPNNEW_Async jpnnew_Async = null;
	public CKB_Async ckb_Async = null;
	public FRENCHDARBY_Async frenchdarby_Async = null;
	public GERMANLUTHER_Async germanluther_Async = null;
	public GST_Async gst_Async = null;
	public INDONESIANBARU_Async indonesianbaru_Async = null;
	public PORTUGAL_Async portugal_Async = null;
	public RUSSIANSYNODAL_Async russiansynodal_Async = null;
	public boolean retry_alert = false;
	public static Activity activity;
	public static LinearLayout bg_intro;
    public static int background_type = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        context = this;
        activity = this;
        alert_view = true;
        retry_alert = true;
        
        String kkk_path = context.getString(R.string.txt_kkk_path);
        String kbb_path = context.getString(R.string.txt_kbb_path);
    	String kjv_path = context.getString(R.string.txt_kjv_path);
    	
    	String jpnnew_path = context.getString(R.string.txt_jpnnew_path);
    	String ckb_path = context.getString(R.string.txt_ckb_path);
    	String frenchdarby_path = context.getString(R.string.txt_frenchdarby_path);
    	String germanluther_path = context.getString(R.string.txt_germanluther_path);
    	String gst_path = context.getString(R.string.txt_gst_path);
    	String indonesianbaru_path = context.getString(R.string.txt_indonesianbaru_path);
    	String portugal_path = context.getString(R.string.txt_portugal_path);
    	String russiansynodal_path = context.getString(R.string.txt_russiansynodal_path);
    	 
    	kkk_Async = new KKK_Async(kkk_path);
    	kkk_Async.execute();
    	kbb_Async = new KBB_Async(kbb_path);
        kbb_Async.execute();
        kjv_Async = new KJV_Async(kjv_path);
        kjv_Async.execute();
        
//        jpnnew_Async = new JPNNEW_Async(jpnnew_path);
//        jpnnew_Async.execute();
//        ckb_Async = new CKB_Async(ckb_path);
//        ckb_Async.execute();
//        frenchdarby_Async = new FRENCHDARBY_Async(frenchdarby_path);
//        frenchdarby_Async.execute();
//        germanluther_Async = new GERMANLUTHER_Async(germanluther_path);
//        germanluther_Async.execute();
//        gst_Async = new GST_Async(gst_path);
//        gst_Async.execute();
//        indonesianbaru_Async = new INDONESIANBARU_Async(indonesianbaru_path);
//        indonesianbaru_Async.execute();
//        portugal_Async = new PORTUGAL_Async(portugal_path);
//        portugal_Async.execute();
//        russiansynodal_Async = new RUSSIANSYNODAL_Async(russiansynodal_path);
//        russiansynodal_Async.execute();
        
        billing_process();//인앱정기결제체크
        
        adstatus_async = new Adstatus_Async();
        adstatus_async.execute();	 
    }
    
    private BillingProcessor bp;
    private static final String SUBSCRIPTION_ID = "good.worshipbible.inapp.month";
    private static final String LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmriBNo95wtn/tfq5izqI2jbgUz4dm1M3R3v239zJ/Zzq4a3dk9KXv/yknNUMtBvC/KbL+kj7k2Rs+TnHUgLzfa7oPHNQ0qJNVUpTl+1RUVLY64YZGw8O91ykn6g5CRsG70V9156NWOAlrDSlgzqqJafeRWc3ggrS2TfvLjeC3ffCu6om2yBFGPmpag2rfjVBCr+QtU+xmF+v3F/K0+AsPN7wOTBURlj+upnBD0uGG5GDqF/mD6Thv2kyUvO75DJXRhQDCaKkNHb+rvIUzizWLpST6j4UczADj+YzaaqkPw3+/ssxb9Ypc3yU/QNVrfchc7Q7Y4W1shBRtfq0EiyI2wIDAQAB";
    private void billing_process(){
        if(!BillingProcessor.isIabServiceAvailable(this)) {
        }
        bp = new BillingProcessor(this, LICENSE_KEY, new BillingProcessor.IBillingHandler() {
            @Override
            public void onBillingInitialized() {
                try{
                    bp.loadOwnedPurchasesFromGoogle();
                    Log.i("dsu", "isSubscriptionUpdateSupported : " + bp.isSubscriptionUpdateSupported());
                    Log.i("dsu", "getSubscriptionTransactionDetails : " + bp.getSubscriptionTransactionDetails(SUBSCRIPTION_ID));
                    Log.i("dsu", "isSubscribed : " + bp.isSubscribed(SUBSCRIPTION_ID));
                    Log.i("dsu", "autoRenewing : " + bp.getSubscriptionTransactionDetails(SUBSCRIPTION_ID).purchaseInfo.purchaseData.autoRenewing);
                    Log.i("dsu", "purchaseTime : " + bp.getSubscriptionTransactionDetails(SUBSCRIPTION_ID).purchaseInfo.purchaseData.purchaseTime);
                    Log.i("dsu", "purchaseState : " + bp.getSubscriptionTransactionDetails(SUBSCRIPTION_ID).purchaseInfo.purchaseData.purchaseState);
                    PreferenceUtil.setStringSharedData(context, PreferenceUtil.PREF_ISSUBSCRIBED, Boolean.toString(bp.getSubscriptionTransactionDetails(SUBSCRIPTION_ID).purchaseInfo.purchaseData.autoRenewing));
                }catch (NullPointerException e){
                }
            }
            
            @Override
            public void onPurchaseHistoryRestored() {
//            	showToast("onPurchaseHistoryRestored");
                for(String sku : bp.listOwnedProducts()){
                    Log.i("dsu", "Owned Managed Product: " + sku);
//                    showToast("Owned Managed Product: " + sku);
                }
                for(String sku : bp.listOwnedSubscriptions()){
                    Log.i("dsu", "Owned Subscription: " + sku);
//                    showToast("Owned Subscription : " + sku);
                }
            }

			@Override
			public void onProductPurchased(String arg0, TransactionDetails arg1) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onBillingError(int arg0, Throwable arg1) {

			}
        });
    }
    
    private Adstatus_Async adstatus_async = null;
    public class Adstatus_Async extends AsyncTask<String, Integer, String> {
    	String version;
        String service_status;
        String recommend_status;
        String tv_service;
        String tv_recommend;
        String pk_recommend_name;
        HttpURLConnection localHttpURLConnection;
        public Adstatus_Async(){
        }
        @Override
        protected String doInBackground(String... params) {
            String sTag;
            try{
            	String str = "http://cion49235.cafe24.com/cion49235/goodworshipbible2_nos/ad_status2.php";
                localHttpURLConnection = (HttpURLConnection)new URL(str).openConnection();
                localHttpURLConnection.setFollowRedirects(true);
                localHttpURLConnection.setConnectTimeout(15000);
                localHttpURLConnection.setReadTimeout(15000);
                localHttpURLConnection.setRequestMethod("GET");
                localHttpURLConnection.connect();
                InputStream inputStream = new URL(str).openStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(inputStream, "EUC-KR");
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_DOCUMENT) {
                    }else if (eventType == XmlPullParser.END_DOCUMENT) {
                    }else if (eventType == XmlPullParser.START_TAG){
                        sTag = xpp.getName();
                        if(sTag.equals("version")){
                            version = xpp.nextText()+"";
                            PreferenceUtil.setStringSharedData(context, PreferenceUtil.PREF_VERSION, version);
                            Log.i("dsu", "version : " + version);
                        }else if(sTag.equals("service_status")){
                            service_status = xpp.nextText()+"";
                            PreferenceUtil.setStringSharedData(context, PreferenceUtil.PREF_SERVICE_STATUS, service_status);
                            Log.i("dsu", "service_status : " + service_status);
                        }else if(sTag.equals("recommend_status")){
                            recommend_status = xpp.nextText()+"";
                            PreferenceUtil.setStringSharedData(context, PreferenceUtil.PREF_RECOMMEND_STATUS, recommend_status);
                            Log.i("dsu", "recommend_status : " + recommend_status);
                        }else if(sTag.equals("tv_service")){
                            tv_service = xpp.nextText()+"";
                            PreferenceUtil.setStringSharedData(context, PreferenceUtil.PREF_TV_SERVICE, tv_service);
                            Log.i("dsu", "tv_service : " + tv_service);
                        }else if(sTag.equals("tv_recommend")){
                            tv_recommend = xpp.nextText()+"";
                            PreferenceUtil.setStringSharedData(context, PreferenceUtil.PREF_TV_RECOMMEND, tv_recommend);
                            Log.i("dsu", "tv_recommend : " + tv_recommend);
                        }else if(sTag.equals("pk_recommend_name")){
                            pk_recommend_name = xpp.nextText()+"";
                            PreferenceUtil.setStringSharedData(context, PreferenceUtil.PREF_PK_RECOMMEND_NAME, pk_recommend_name);
                            Log.i("dsu", "pk_recommend_name : " + pk_recommend_name);
                        }
                    } else if (eventType == XmlPullParser.END_TAG){
                        sTag = xpp.getName();
                        if(sTag.equals("Finish")){
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
            catch (java.lang.NullPointerException NullPointerException)
            {
            }
            catch (Exception e)
            {
            }
            return service_status;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            version_check();
        }
        @Override
        protected void onPostExecute(String service_status) {
            super.onPostExecute(service_status);
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
    
    int versionCode;
    @SuppressWarnings("deprecation")
	private void version_check(){
        PackageInfo pi=null;
        try {
            pi = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NullPointerException e){
        } catch (Exception e){
        }
        if ( (versionCode < Integer.parseInt(PreferenceUtil.getStringSharedData(context, PreferenceUtil.PREF_VERSION, "1"))) && (versionCode > 0) ) {
           android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
//            builder.setIcon(R.drawable.icon128);
            builder.setTitle(context.getString(R.string.alert_update_01));
            builder.setMessage(context.getString(R.string.alert_update_02));
            builder.setCancelable(false);
            builder.setPositiveButton(Html.fromHtml("<font color='#ff6d00'>'"+context.getString(R.string.alert_update_03)+"'</font>"), new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int whichButton){
                    String packageName = "";
                    try {
                        @SuppressWarnings("unused")
						PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                        packageName = getPackageName();
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                    } catch (PackageManager.NameNotFoundException e) {
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
                    }
                }
            });
            builder.setNegativeButton(Html.fromHtml("<font color='#ff6d00'>'"+context.getString(R.string.alert_update_05)+"'</font>"), new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int whichButton){
                    finish();
                }
            });
            android.app.AlertDialog myAlertDialog = builder.create();
            myAlertDialog.show();
        }
        else {
            handler = new Handler();
            handler.postDelayed(runnable, 2000);
        }
    }
    
    
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("dsu", "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (!bp.handleActivityResult(requestCode, resultCode, data))
            Log.i("dsu", "인앱결제 requestCode : " + requestCode);
            if (requestCode == 32459) {
                int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
                String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
                String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");//this is the signature which you want
                Log.i("dsu", "purchaseData : " +  purchaseData);
                if(TextUtils.isEmpty(purchaseData)) {
                	show_inapp_alert();
                	return;
                }
                if (resultCode == RESULT_OK) {
                    try {
                        JSONObject jo = new JSONObject(purchaseData);//this is the JSONObject which you have included in Your Question right now
                        String orderId = jo.getString("orderId");
                        String packageName = jo.getString("packageName");
                        String productId = jo.getString("productId");
                        String purchaseTime = jo.getString("purchaseTime");
                        String purchaseState = jo.getString("purchaseState");
                        String purchaseToken = jo.getString("purchaseToken");
                        String autoRenewing = jo.getString("autoRenewing");
                        String format_purchaseTime = MillToDate(Long.parseLong(purchaseTime));
                        Log.i("dsu", "구글주문아이디 " +  orderId + "\n어플리케이션 패키지이름 : " + packageName + "\n아이템 상품 식별자 : " + productId + "\n상품 구매가 이루어진 시간 : " + format_purchaseTime + "\n주문의 구매 상태 : " + purchaseState + "\n구매를 고유하게 식별하는 토큰값 : " + purchaseToken + "\n자동갱신여부 : " + autoRenewing);
                        if(!StringUtil.isEmpty(purchaseState) && autoRenewing.equals("true")) {
                        	PreferenceUtil.setStringSharedData(context, PreferenceUtil.PREF_ISSUBSCRIBED, autoRenewing);	
                        	Intent intent = new Intent(context, Sub1_Activity.class);
            				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            				startActivity(intent);
            				finish();
                        }
                    }
                    catch (JSONException e) {
                        alert("Failed to parse purchase data.");
                        e.printStackTrace();
                    }
                }
            }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    private void show_inapp_alert() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setTitle(context.getString(R.string.txt_inapp_alert_title));
		builder.setMessage(context.getString(R.string.txt_inapp_alert_ment));
		builder.setInverseBackgroundForced(true);
		builder.setNeutralButton(context.getString(R.string.txt_inapp_alert_yes), new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int whichButton){
				bp.subscribe(IntroActivity.this,SUBSCRIPTION_ID);
			}
		});
		builder.setNegativeButton(context.getString(R.string.txt_inapp_alert_no), new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int whichButton){
				Intent intent = new Intent(context, Sub1_Activity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
             	dialog.dismiss();
			}
		});
		AlertDialog myAlertDialog = builder.create();
		if(retry_alert) myAlertDialog.show();
    }
    
    public String MillToDate(long mills) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String date = (String) formatter.format(new Timestamp(mills));
        return date;
    }
    
    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d("dsu", "Showing alert dialog: " + message);
        bld.create().show();
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	alert_view = false;
    	retry_alert = false;
    	if(handler != null){
    		handler.removeCallbacks(runnable);
    	}
    	if(kbb_Async != null){
    		kbb_Async.cancel(true);
    	}
    	if(kjv_Async != null){
    		kjv_Async.cancel(true);
    	}
    	if(kkk_Async != null){
    		kkk_Async.cancel(true);
    	}
    	if(jpnnew_Async != null){
    		jpnnew_Async.cancel(true);
    	}
    	if(ckb_Async != null){
    		ckb_Async.cancel(true);
    	}
    	if (bp != null)
            bp.release();
    	
//    	if(frenchdarby_Async != null){
//    		frenchdarby_Async.cancel(true);
//    	}
//    	if(germanluther_Async != null){
//    		germanluther_Async.cancel(true);
//    	}
//    	if(gst_Async != null){
//    		gst_Async.cancel(true);
//    	}
//    	if(indonesianbaru_Async != null){
//    		indonesianbaru_Async.cancel(true);
//    	}
//    	if(portugal_Async != null){
//    		portugal_Async.cancel(true);
//    	}
//    	if(russiansynodal_Async != null){
//    		russiansynodal_Async.cancel(true);
//    	}
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    }
    @Override
    protected void onPause() {
    	super.onPause();
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    }
    
    public class KBB_Async extends AsyncTask<Boolean, Void, Boolean> {
    	String path;
    	File file;
		public KBB_Async(String path) {
			this.path = path;
		}
		@Override
		protected void onPreExecute() {
		}
		@Override
		protected Boolean doInBackground(Boolean... arg0) {
			try {
				file = new File(path);
			} catch (IllegalArgumentException e){
			
			} catch (Resources.NotFoundException e){
			
			} catch (StringIndexOutOfBoundsException e){
			
			} catch (RuntimeException e){
				
			} 
			return file.exists();
		}
 
		@Override
		protected void onPostExecute(Boolean Response) {
//			Log.i("dsu", "KBB_Response : " + Response);
			MAKE_KBB_DB();
		}
	}
    
    public class KJV_Async extends AsyncTask<Boolean, Void, Boolean> {
    	String path;
    	File file;
		public KJV_Async(String path) {
			this.path = path;
		}
		@Override
		protected void onPreExecute() {
		}
		@Override
		protected Boolean doInBackground(Boolean... arg0) {
			try {
				file = new File(path);
			} catch (IllegalArgumentException e){
			
			} catch (Resources.NotFoundException e){
			
			} catch (StringIndexOutOfBoundsException e){
			
			} catch (RuntimeException e){
				
			} 
			return file.exists();
		}
 
		@Override
		protected void onPostExecute(Boolean Response) {
//			Log.i("dsu", "KJV_Response : " + Response);
			MAKE_KJV_DB();
		}
	}
    
    public class KKK_Async extends AsyncTask<Boolean, Void, Boolean> {
    	String path;
    	File file;
		public KKK_Async(String path) {
			this.path = path;
		}
		@Override
		protected void onPreExecute() {
		}
		@Override
		protected Boolean doInBackground(Boolean... arg0) {
			try {
				file = new File(path);
			} catch (IllegalArgumentException e){
			
			} catch (Resources.NotFoundException e){
			
			} catch (StringIndexOutOfBoundsException e){
			
			} catch (RuntimeException e){
				
			} 
			return file.exists();
		}
 
		@Override
		protected void onPostExecute(Boolean Response) {
//			Log.i("dsu", "KKK_Response : " + Response);
			MAKE_KKK_DB();
		}
	}
    
    public class JPNNEW_Async extends AsyncTask<Boolean, Void, Boolean> {
    	String path;
    	File file;
		public JPNNEW_Async(String path) {
			this.path = path;
		}
		@Override
		protected void onPreExecute() {
		}
		@Override
		protected Boolean doInBackground(Boolean... arg0) {
			try {
				file = new File(path);
			} catch (IllegalArgumentException e){
			
			} catch (Resources.NotFoundException e){
			
			} catch (StringIndexOutOfBoundsException e){
			
			} catch (RuntimeException e){
				
			} 
			return file.exists();
		}
 
		@Override
		protected void onPostExecute(Boolean Response) {
//			Log.i("dsu", "JPNNEW_Response : " + Response);
			MAKE_JPNNEW_DB();
		}
	}
    
    public class CKB_Async extends AsyncTask<Boolean, Void, Boolean> {
    	String path;
    	File file;
		public CKB_Async(String path) {
			this.path = path;
		}
		@Override
		protected void onPreExecute() {
		}
		@Override
		protected Boolean doInBackground(Boolean... arg0) {
			try {
				file = new File(path);
			} catch (IllegalArgumentException e){
			
			} catch (Resources.NotFoundException e){
			
			} catch (StringIndexOutOfBoundsException e){
			
			} catch (RuntimeException e){
				
			} 
			return file.exists();
		}
 
		@Override
		protected void onPostExecute(Boolean Response) {
//			Log.i("dsu", "CKB_Response : " + Response);
			MAKE_CKB_DB();
		}
	}
    
    public class FRENCHDARBY_Async extends AsyncTask<Boolean, Void, Boolean> {
    	String path;
    	File file;
		public FRENCHDARBY_Async(String path) {
			this.path = path;
		}
		@Override
		protected void onPreExecute() {
		}
		@Override
		protected Boolean doInBackground(Boolean... arg0) {
			try {
				file = new File(path);
			} catch (IllegalArgumentException e){
			
			} catch (Resources.NotFoundException e){
			
			} catch (StringIndexOutOfBoundsException e){
			
			} catch (RuntimeException e){
				
			} 
			return file.exists();
		}
 
		@Override
		protected void onPostExecute(Boolean Response) {
//			Log.i("dsu", "FRENCHDARBY_Response : " + Response);
			MAKE_FRENCHDARBY_DB();
		}
	}
    
    public class GERMANLUTHER_Async extends AsyncTask<Boolean, Void, Boolean> {
    	String path;
    	File file;
		public GERMANLUTHER_Async(String path) {
			this.path = path;
		}
		@Override
		protected void onPreExecute() {
		}
		@Override
		protected Boolean doInBackground(Boolean... arg0) {
			try {
				file = new File(path);
			} catch (IllegalArgumentException e){
			
			} catch (Resources.NotFoundException e){
			
			} catch (StringIndexOutOfBoundsException e){
			
			} catch (RuntimeException e){
				
			} 
			return file.exists();
		}
 
		@Override
		protected void onPostExecute(Boolean Response) {
//			Log.i("dsu", "GERMANLUTHER_Response : " + Response);
			MAKE_GERMANLUTHER_DB();
		}
	}
    
    public class GST_Async extends AsyncTask<Boolean, Void, Boolean> {
    	String path;
    	File file;
		public GST_Async(String path) {
			this.path = path;
		}
		@Override
		protected void onPreExecute() {
		}
		@Override
		protected Boolean doInBackground(Boolean... arg0) {
			try {
				file = new File(path);
			} catch (IllegalArgumentException e){
			
			} catch (Resources.NotFoundException e){
			
			} catch (StringIndexOutOfBoundsException e){
			
			} catch (RuntimeException e){
				
			} 
			return file.exists();
		}
 
		@Override
		protected void onPostExecute(Boolean Response) {
//			Log.i("dsu", "GST_Response : " + Response);
			MAKE_GST_DB();
		}
	}
    
    public class INDONESIANBARU_Async extends AsyncTask<Boolean, Void, Boolean> {
    	String path;
    	File file;
		public INDONESIANBARU_Async(String path) {
			this.path = path;
		}
		@Override
		protected void onPreExecute() {
		}
		@Override
		protected Boolean doInBackground(Boolean... arg0) {
			try {
				file = new File(path);
			} catch (IllegalArgumentException e){
			
			} catch (Resources.NotFoundException e){
			
			} catch (StringIndexOutOfBoundsException e){
			
			} catch (RuntimeException e){
				
			} 
			return file.exists();
		}
 
		@Override
		protected void onPostExecute(Boolean Response) {
//			Log.i("dsu", "INDONESIANBARU_Response : " + Response);
			MAKE_INDONESIANBARU_DB();
		}
	}
    
    public class PORTUGAL_Async extends AsyncTask<Boolean, Void, Boolean> {
    	String path;
    	File file;
		public PORTUGAL_Async(String path) {
			this.path = path;
		}
		@Override
		protected void onPreExecute() {
		}
		@Override
		protected Boolean doInBackground(Boolean... arg0) {
			try {
				file = new File(path);
			} catch (IllegalArgumentException e){
			
			} catch (Resources.NotFoundException e){
			
			} catch (StringIndexOutOfBoundsException e){
			
			} catch (RuntimeException e){
				
			} 
			return file.exists();
		}
 
		@Override
		protected void onPostExecute(Boolean Response) {
//			Log.i("dsu", "PORTUGAL_Response : " + Response);
			MAKE_PORTUGAL_DB();
		}
	}
    
    public class RUSSIANSYNODAL_Async extends AsyncTask<Boolean, Void, Boolean> {
    	String path;
    	File file;
		public RUSSIANSYNODAL_Async(String path) {
			this.path = path;
		}
		@Override
		protected void onPreExecute() {
		}
		@Override
		protected Boolean doInBackground(Boolean... arg0) {
			try {
				file = new File(path);
			} catch (IllegalArgumentException e){
			
			} catch (Resources.NotFoundException e){
			
			} catch (StringIndexOutOfBoundsException e){
			
			} catch (RuntimeException e){
				
			} 
			return file.exists();
		}
 
		@Override
		protected void onPostExecute(Boolean Response) {
//			Log.i("dsu", "RUSSIANSYNODAL_Response : " + Response);
			MAKE_RUSSIANSYNODAL_DB();
		}
	}
    
    public void MAKE_KBB_DB(){
//    	Log.i("dsu", "만든다KBB");
    	AssetManager manager = context.getAssets();
        String folderPath = context.getString(R.string.txt_folder_path);
        String filePath = context.getString(R.string.txt_kbb_path);
        File folder = new File(folderPath);
        File file = new File(filePath);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
                InputStream is = manager.open(context.getString(R.string.txt_input_kbb));
                BufferedInputStream bis = new BufferedInputStream(is);
                if (folder.exists()) {
                }else{
                        folder.mkdirs();
                }
                if (file.exists()) {
                        file.delete();
                        file.createNewFile();
                }
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos);
                int read = -1;
                byte[] buffer = new byte[1024];
                while ((read = bis.read(buffer, 0, 1024)) != -1) {
                        bos.write(buffer, 0, read);
                }
                bos.flush();
                bos.close();
                fos.close();
                bis.close();
                is.close();
        } catch (IOException e) {
        } 
    }
    
    public void MAKE_KJV_DB(){
//    	Log.i("dsu", "만든다KJV");
    	AssetManager manager = context.getAssets();
        String folderPath = context.getString(R.string.txt_folder_path);
        String filePath = context.getString(R.string.txt_kjv_path);
        File folder = new File(folderPath);
        File file = new File(filePath);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
                InputStream is = manager.open(context.getString(R.string.txt_input_kjv));
                BufferedInputStream bis = new BufferedInputStream(is);
                if (folder.exists()) {
                }else{
                        folder.mkdirs();
                }
                if (file.exists()) {
                        file.delete();
                        file.createNewFile();
                }
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos);
                int read = -1;
                byte[] buffer = new byte[1024];
                while ((read = bis.read(buffer, 0, 1024)) != -1) {
                        bos.write(buffer, 0, read);
                }
                bos.flush();
                bos.close();
                fos.close();
                bis.close();
                is.close();
        } catch (IOException e) {
        } 
    }
    
    public void MAKE_KKK_DB(){
//    	Log.i("dsu", "만든다KKK");
    	AssetManager manager = context.getAssets();
        String folderPath = context.getString(R.string.txt_folder_path);
        String filePath = context.getString(R.string.txt_kkk_path);
        File folder = new File(folderPath);
        File file = new File(filePath);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
                InputStream is = manager.open(context.getString(R.string.txt_input_kkk));
                BufferedInputStream bis = new BufferedInputStream(is);
                if (folder.exists()) {
                }else{
                        folder.mkdirs();
                }
                if (file.exists()) {
                        file.delete();
                        file.createNewFile();
                }
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos);
                int read = -1;
                byte[] buffer = new byte[1024];
                while ((read = bis.read(buffer, 0, 1024)) != -1) {
                        bos.write(buffer, 0, read);
                }
                bos.flush();
                bos.close();
                fos.close();
                bis.close();
                is.close();
        } catch (IOException e) {
        } 
    }
    
    public void MAKE_JPNNEW_DB(){
//    	Log.i("dsu", "만든다JPNNEW");
    	AssetManager manager = context.getAssets();
        String folderPath = context.getString(R.string.txt_folder_path);
        String filePath = context.getString(R.string.txt_jpnnew_path);
        File folder = new File(folderPath);
        File file = new File(filePath);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
                InputStream is = manager.open(context.getString(R.string.txt_input_jpnnew));
                BufferedInputStream bis = new BufferedInputStream(is);
                if (folder.exists()) {
                }else{
                        folder.mkdirs();
                }
                if (file.exists()) {
                        file.delete();
                        file.createNewFile();
                }
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos);
                int read = -1;
                byte[] buffer = new byte[1024];
                while ((read = bis.read(buffer, 0, 1024)) != -1) {
                        bos.write(buffer, 0, read);
                }
                bos.flush();
                bos.close();
                fos.close();
                bis.close();
                is.close();
        } catch (IOException e) {
        } 
    }
    
    public void MAKE_CKB_DB(){
//    	Log.i("dsu", "만든다CKB");
    	AssetManager manager = context.getAssets();
        String folderPath = context.getString(R.string.txt_folder_path);
        String filePath = context.getString(R.string.txt_ckb_path);
        File folder = new File(folderPath);
        File file = new File(filePath);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
                InputStream is = manager.open(context.getString(R.string.txt_input_ckb));
                BufferedInputStream bis = new BufferedInputStream(is);
                if (folder.exists()) {
                }else{
                        folder.mkdirs();
                }
                if (file.exists()) {
                        file.delete();
                        file.createNewFile();
                }
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos);
                int read = -1;
                byte[] buffer = new byte[1024];
                while ((read = bis.read(buffer, 0, 1024)) != -1) {
                        bos.write(buffer, 0, read);
                }
                bos.flush();
                bos.close();
                fos.close();
                bis.close();
                is.close();
        } catch (IOException e) {
        } 
    }
    
    public void MAKE_FRENCHDARBY_DB(){
//    	Log.i("dsu", "만든다FRENCHDARBY");
    	AssetManager manager = context.getAssets();
        String folderPath = context.getString(R.string.txt_folder_path);
        String filePath = context.getString(R.string.txt_frenchdarby_path);
        File folder = new File(folderPath);
        File file = new File(filePath);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
                InputStream is = manager.open(context.getString(R.string.txt_input_frenchdarby));
                BufferedInputStream bis = new BufferedInputStream(is);
                if (folder.exists()) {
                }else{
                        folder.mkdirs();
                }
                if (file.exists()) {
                        file.delete();
                        file.createNewFile();
                }
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos);
                int read = -1;
                byte[] buffer = new byte[1024];
                while ((read = bis.read(buffer, 0, 1024)) != -1) {
                        bos.write(buffer, 0, read);
                }
                bos.flush();
                bos.close();
                fos.close();
                bis.close();
                is.close();
        } catch (IOException e) {
        } 
    }
    
    public void MAKE_GERMANLUTHER_DB(){
//    	Log.i("dsu", "만든다GERMANLUTHER");
    	AssetManager manager = context.getAssets();
        String folderPath = context.getString(R.string.txt_folder_path);
        String filePath = context.getString(R.string.txt_germanluther_path);
        File folder = new File(folderPath);
        File file = new File(filePath);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
                InputStream is = manager.open(context.getString(R.string.txt_input_germanluther));
                BufferedInputStream bis = new BufferedInputStream(is);
                if (folder.exists()) {
                }else{
                        folder.mkdirs();
                }
                if (file.exists()) {
                        file.delete();
                        file.createNewFile();
                }
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos);
                int read = -1;
                byte[] buffer = new byte[1024];
                while ((read = bis.read(buffer, 0, 1024)) != -1) {
                        bos.write(buffer, 0, read);
                }
                bos.flush();
                bos.close();
                fos.close();
                bis.close();
                is.close();
        } catch (IOException e) {
        } 
    }
    
    public void MAKE_GST_DB(){
//    	Log.i("dsu", "만든다GST");
    	AssetManager manager = context.getAssets();
        String folderPath = context.getString(R.string.txt_folder_path);
        String filePath = context.getString(R.string.txt_gst_path);
        File folder = new File(folderPath);
        File file = new File(filePath);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
                InputStream is = manager.open(context.getString(R.string.txt_input_gst));
                BufferedInputStream bis = new BufferedInputStream(is);
                if (folder.exists()) {
                }else{
                        folder.mkdirs();
                }
                if (file.exists()) {
                        file.delete();
                        file.createNewFile();
                }
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos);
                int read = -1;
                byte[] buffer = new byte[1024];
                while ((read = bis.read(buffer, 0, 1024)) != -1) {
                        bos.write(buffer, 0, read);
                }
                bos.flush();
                bos.close();
                fos.close();
                bis.close();
                is.close();
        } catch (IOException e) {
        } 
    }
    
    public void MAKE_INDONESIANBARU_DB(){
//    	Log.i("dsu", "만든다INDONESIANBARU");
    	AssetManager manager = context.getAssets();
        String folderPath = context.getString(R.string.txt_folder_path);
        String filePath = context.getString(R.string.txt_indonesianbaru_path);
        File folder = new File(folderPath);
        File file = new File(filePath);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
                InputStream is = manager.open(context.getString(R.string.txt_input_indonesianbaru));
                BufferedInputStream bis = new BufferedInputStream(is);
                if (folder.exists()) {
                }else{
                        folder.mkdirs();
                }
                if (file.exists()) {
                        file.delete();
                        file.createNewFile();
                }
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos);
                int read = -1;
                byte[] buffer = new byte[1024];
                while ((read = bis.read(buffer, 0, 1024)) != -1) {
                        bos.write(buffer, 0, read);
                }
                bos.flush();
                bos.close();
                fos.close();
                bis.close();
                is.close();
        } catch (IOException e) {
        } 
    }
    
    public void MAKE_PORTUGAL_DB(){
//    	Log.i("dsu", "만든다PORTUGAL");
    	AssetManager manager = context.getAssets();
        String folderPath = context.getString(R.string.txt_folder_path);
        String filePath = context.getString(R.string.txt_portugal_path);
        File folder = new File(folderPath);
        File file = new File(filePath);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
                InputStream is = manager.open(context.getString(R.string.txt_input_portugal));
                BufferedInputStream bis = new BufferedInputStream(is);
                if (folder.exists()) {
                }else{
                        folder.mkdirs();
                }
                if (file.exists()) {
                        file.delete();
                        file.createNewFile();
                }
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos);
                int read = -1;
                byte[] buffer = new byte[1024];
                while ((read = bis.read(buffer, 0, 1024)) != -1) {
                        bos.write(buffer, 0, read);
                }
                bos.flush();
                bos.close();
                fos.close();
                bis.close();
                is.close();
        } catch (IOException e) {
        } 
    }
    
    public void MAKE_RUSSIANSYNODAL_DB(){
//    	Log.i("dsu", "만든다RUSSIANSYNODAL");
    	AssetManager manager = context.getAssets();
        String folderPath = context.getString(R.string.txt_folder_path);
        String filePath = context.getString(R.string.txt_russiansynodal_path);
        File folder = new File(folderPath);
        File file = new File(filePath);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
                InputStream is = manager.open(context.getString(R.string.txt_input_russiansynodal));
                BufferedInputStream bis = new BufferedInputStream(is);
                if (folder.exists()) {
                }else{
                        folder.mkdirs();
                }
                if (file.exists()) {
                        file.delete();
                        file.createNewFile();
                }
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos);
                int read = -1;
                byte[] buffer = new byte[1024];
                while ((read = bis.read(buffer, 0, 1024)) != -1) {
                        bos.write(buffer, 0, read);
                }
                bos.flush();
                bos.close();
                fos.close();
                bis.close();
                is.close();
        } catch (IOException e) {
        } 
    }

    Runnable runnable = new Runnable() {
		@Override
		public void run() {
			/*if(PreferenceUtil.getStringSharedData(context, PreferenceUtil.PREF_ISSUBSCRIBED, Const.isSubscribed).equals("true")) {
				Intent intent = new Intent(context, Sub1_Activity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}else {
				show_inapp_alert();	
			}*/
			if(PreferenceUtil.getStringSharedData(context, PreferenceUtil.PREF_SERVICE_STATUS, "Y").equals("Y")){
				Intent intent = new Intent(context, Sub1_Activity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
				//fade_animation
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);				
			}else {
				service_popup();
			}
		}
	};
	
	public void go_main(){
		Intent intent = new Intent(context, Sub1_Activity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
		//fade_animation
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
	
	private boolean alert_view = false;
	private void service_popup(){
        DialogServicePopup dialog =  new DialogServicePopup(context, activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        if(alert_view) dialog.show();
    }
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if(handler != null) handler.removeCallbacks(runnable);
		finish();
	}
}
