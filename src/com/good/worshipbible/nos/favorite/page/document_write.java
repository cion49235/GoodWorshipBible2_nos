//This is source code of favorite. Copyrightⓒ. Tarks. All Rights Reserved.
package com.good.worshipbible.nos.favorite.page;

import java.io.File;
import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.actionbarsherlock.view.Window;
import com.admixer.AdAdapter;
import com.admixer.AdInfo;
import com.admixer.AdMixerManager;
import com.admixer.AdView;
import com.admixer.AdViewListener;
import com.good.worshipbible.nos.R;
import com.good.worshipbible.nos.data.Const;
import com.good.worshipbible.nos.favorite.GalleryView;
import com.good.worshipbible.nos.favorite.connect.AsyncHttpTask;
import com.good.worshipbible.nos.favorite.global.Global;
import com.good.worshipbible.nos.favorite.global.Globalvariable;
import com.good.worshipbible.nos.util.PreferenceUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class document_write extends SherlockActivity implements AdViewListener {

	Button bt;

	String content;
	String page_srl;
	String page_name;
	String status = "0";
	String doc_contents;
	
	EditText content_edittext;
	
	int REQ_CODE_PICK_PICTURE = 2;
	int IMAGE_EDIT = 3;
	int CAMERA_PIC_REQUEST = 4;
	int FILE_CODE = 5;
	boolean attach_exist =false;
	int file_kind = 0;
	Uri file_path;
	// 1 image 2 file
	// IMG
	Uri mImageUri;
	String externel_path;
	ArrayList<String> files = new ArrayList<String>();
	public static RelativeLayout ad_layout;
	boolean show_alert = false;
	private NativeExpressAdView admobNative;
	Context context;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Can use progress
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.favorite_document_write);
		show_alert = true;
		context = this;
		// 액션바백버튼가져오기
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setSupportProgressBarIndeterminateVisibility(false);
		AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD, "AX00056EB");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_TAD_FULL, "AX00056EC");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMIXER, "u6dbtyd1");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB, "ca-app-pub-4637651494513698/9745545364");
    	AdMixerManager.getInstance().setAdapterDefaultAppCode(AdAdapter.ADAPTER_ADMOB_FULL, "ca-app-pub-4637651494513698/2222278564");
    	if(!PreferenceUtil.getStringSharedData(context, PreferenceUtil.PREF_ISSUBSCRIBED, Const.isSubscribed).equals("true")){
        	addBannerView();    		
    	}
//    	init_admob_naive();
		// Get Intent
		Intent intent = getIntent();// 인텐트 받아오고
		page_srl = intent.getStringExtra("page_srl");
		page_name = intent.getStringExtra("page_name");
		doc_contents = intent.getStringExtra("doc_contents");
		content_edittext = (EditText) findViewById(R.id.editText1);
		mImageUri = intent.getParcelableExtra("image_uri");
		externel_path= getExternalCacheDir().getAbsolutePath() + "/";
		if(page_name != null) getSupportActionBar().setSubtitle(page_name);
		if(doc_contents != null) content_edittext.setText(doc_contents);
		if(mImageUri != null) confirmImage();
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
		show_alert = false;
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

	public void PostAct() {
		// IF Sucessfull no timeout
		setSupportProgressBarIndeterminateVisibility(true);
		ArrayList<String> Paramname = new ArrayList<String>();
		Paramname.add("authcode");
		Paramname.add("kind");
		Paramname.add("page_srl");
		Paramname.add("user_srl");
		Paramname.add("user_srl_auth");
		Paramname.add("title");
		Paramname.add("content");
		Paramname.add("permission");
		Paramname.add("status");
		Paramname.add("privacy");

		ArrayList<String> Paramvalue = new ArrayList<String>();
		Paramvalue.add("642979");
		Paramvalue.add("1");
		Paramvalue.add(page_srl);
		Paramvalue.add(Global.getSetting("user_srl",
				Global.getSetting("user_srl", "0")));
		Paramvalue.add(Global.getSetting("user_srl_auth",
				Global.getSetting("user_srl_auth", "null")));
		Paramvalue.add("null");
		Paramvalue.add(Global.setValue(content));
		Paramvalue.add("3");
		Paramvalue.add(status);
		Paramvalue.add("0");
		
	
//Check attach exist
	if(attach_exist){
		String path = null;
		if(file_kind == 1){
		 path = getCacheDir().toString() + "/attach_image.jpg";
		//check image already exist
		}else{
			path = Global.getPath(this, file_path);
		}
			files.add(path);
		
	}else{
		files = null;
	}

		new AsyncHttpTask(this, getString(R.string.server_path)
				+ "board/documents_app_write.php", mHandler, Paramname,
				Paramvalue, files, 1, 0);
	}

	public void FinishAct() {
		Intent intent = new Intent();
		this.setResult(RESULT_OK, intent);
		finish();
	}
	
	public void CancelWritingAlert(){
		// Alert
		AlertDialog.Builder builder = new AlertDialog.Builder(document_write.this);
		builder.setMessage(getString(R.string.cancel_writing_des)).setTitle(
				getString(R.string.warning));
		builder.setPositiveButton(getString(R.string.yes),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						finish();
						
					}
				});
		builder.setNegativeButton(getString(R.string.no),null);
		builder.show();
	}

	protected Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			// IF Sucessfull no timeout
			setSupportProgressBarIndeterminateVisibility(false);
			if (msg.what == -1) {
				if(show_alert) Global.ConnectionError(document_write.this);
			}

			if (msg.what == 1) {
				String result = msg.obj.toString();
				if (result.matches("document_write_succeed")) {
					FinishAct();
				} else {
				//	Log.i("Error", "Error has been");
					if(show_alert) Global.ConnectionError(document_write.this);
				}
				// Log.i("Result","로그 정상 작동");
				Log.i("Result", msg.obj.toString());

			}

		}
	};
	
	public void confirmImage(){
		Intent intent = new Intent(document_write.this, GalleryView.class);
		intent.putExtra("uri", mImageUri);
		intent.putExtra("edit", true);
		startActivityForResult(intent, IMAGE_EDIT);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
			// setListAdapter();
			String result_status = data.getStringExtra("status");
			status = result_status;
		}
		
		if (requestCode == REQ_CODE_PICK_PICTURE && resultCode == Activity.RESULT_OK) {
				Intent intent = new Intent(document_write.this, GalleryView.class);
				intent.putExtra("uri", data.getData());
				intent.putExtra("edit", true);
				startActivityForResult(intent, IMAGE_EDIT);

			
		}

		if (requestCode == IMAGE_EDIT && resultCode == Activity.RESULT_OK) {
			// Log.i("Imageresult", "itsok");
			
				byte[] b = Globalvariable.image;
//				profile_bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
//				// Log.i("datasetdata", data.getData().toString() + "ssdsd");
//				profile.setImageBitmap(profile_bitmap); // 사진 선택한 사진URI로 연결하기
//				// Profile changed
file_kind = 1;
				attach_exist = true;
				invalidateOptionsMenu();
//				// Set global image null
//				Globalvariable.image = null;
				
			
				
			
		}

		if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
			// ImageView imageView;
			// ... some code to inflate/create/find appropriate ImageView to
			// place grabbed image
			// profile_bitmap = Global.grabImage(mImageUri);
			confirmImage();
		}
		
		if (requestCode == FILE_CODE && resultCode == RESULT_OK) {
			// ImageView imageView;
			// ... some code to inflate/create/find appropriate ImageView to
			// place grabbed image
			// profile_bitmap = Global.grabImage(mImageUri)
file_kind = 2;
			file_path = data.getData();
			 
		//	Log.i("test", data.getDataString());
		//	Log.i("file", file_path);
			attach_exist = true;
			invalidateOptionsMenu();


		}
		
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuItem item;
		
	    SubMenu subMenu1 = menu.addSubMenu(getString(R.string.attach));
        subMenu1.add(0, 1001, 0, getString(R.string.camera));
        subMenu1.add(0, 1002, 0, getString(R.string.choose_picture));
        subMenu1.add(0, 1003, 0, getString(R.string.attach_file));
       // subMenu1.add(0, 1004, 0, "목록");
     if(attach_exist) subMenu1.add(0, 1005, 0, getString(R.string.delete));

        MenuItem subMenu1Item = subMenu1.getItem();
      

		menu.add(0, 2, 0, getString(R.string.privacy_content)).setShowAsAction(
				MenuItem.SHOW_AS_ACTION_NEVER);
		  subMenu1Item.setIcon(R.drawable.ic_attach);
	        subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menu.add(0, 1, 0, getString(R.string.write)).setIcon(R.drawable.accept)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		return true;

	}
	


	// 빽백키 상단액션바
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			if (Globalvariable.okbutton == true) {
				// Set ok button disable
				Globalvariable.okbutton = false;
				Global.ButtonEnable(1);
				setSupportProgressBarIndeterminateVisibility(true);
			
				content = content_edittext.getText().toString();

				if (!content.matches("")) {
					PostAct();
				} else {
					setSupportProgressBarIndeterminateVisibility(false);
					Global.Infoalert(this, getString(R.string.warning),
							getString(R.string.no_content),
							getString(R.string.yes));
				}
			}

			return true;

		case 2:
			Intent intent1 = new Intent(document_write.this,
					privacy_category.class);
			intent1.putExtra("status", status);
			startActivityForResult(intent1, 1);

			return true;
		
		case 1001:
			int w,
			h;
			// Intent cameraIntent = new
			// Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// startActivityForResult(cameraIntent , CAMERA_PIC_REQUEST);

			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			File photo;
			try {
				// place where to store camera taken picture
				photo = Global.createTemporaryFile("picture", ".jpg");
				photo.delete();
			} catch (Exception e) {
				Global.toast(getString(R.string.no_storage_error));
				return false;
			}
			mImageUri = Uri.fromFile(photo);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
			// start camera intent
			this.startActivityForResult(intent, CAMERA_PIC_REQUEST);
			return true;

			
		case 1002:
			Intent i = new Intent(Intent.ACTION_PICK);
			i.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
//			i.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // images
			// 결과를 리턴하는 Activity 호출
			startActivityForResult(i, REQ_CODE_PICK_PICTURE);

			return true;
			
		case 1003:
			Intent i1 = new Intent(Intent.ACTION_GET_CONTENT);
			 i1.setType("*/*");
			// 결과를 리턴하는 Activity 호출
			startActivityForResult(i1, FILE_CODE);

			return true;
			
		case 1005:
		attach_exist = false;
		invalidateOptionsMenu();
			return true;

		case android.R.id.home:
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}
	
	@Override
	public void onBackPressed() {
		if(content_edittext.getText().toString().matches("")){
			finish();
		}else{
			CancelWritingAlert();
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
