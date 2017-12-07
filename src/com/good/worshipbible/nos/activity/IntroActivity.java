package com.good.worshipbible.nos.activity;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.good.worshipbible.nos.R;


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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        context = this;
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
        handler = new Handler();
        handler.postDelayed(runnable, 2000);
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
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
			Intent intent = new Intent(context, Sub1_Activity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
			//fade_animation
			overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		}
	};
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if(handler != null) handler.removeCallbacks(runnable);
		finish();
	}
}
