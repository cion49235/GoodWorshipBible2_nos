package com.good.worshipbible.nos.podcast.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.good.worshipbible.nos.R;
import com.good.worshipbible.nos.mediaplayer.CustomMediaPlayer;
import com.good.worshipbible.nos.podcast.DownloadAsync;
import com.good.worshipbible.nos.podcast.Sub5_2_Activity;
import com.good.worshipbible.nos.podcast.data.Sub5_2_ColumData;
import com.good.worshipbible.nos.podcast.db.helper.Sub5_4_DBopenHelper;
import com.good.worshipbible.nos.util.ImageLoader;


public class Sub5_2_Adapter extends BaseAdapter{
	public Context context;
	public ImageLoader imgLoader;
	public String continue_enclosure = "empty";
	public String down_enclosure = "empty";
	public Cursor cursor, cursor2;
	public ImageButton bt_sub_down, bt_sub_continue;
	public ProgressBar progress_down;
	public SharedPreferences settings,pref;
	public Editor edit;
	public ConnectivityManager connectivityManger;
	public NetworkInfo mobile;
	public NetworkInfo wifi;
	public Sub5_4_DBopenHelper down_mydb;
	public String sub_file_name;
	public ArrayList<Sub5_2_ColumData> list;
	public Sub5_2_Adapter(Context context, Sub5_4_DBopenHelper down_mydb, ArrayList<Sub5_2_ColumData> list) {
		this.imgLoader = new ImageLoader(context.getApplicationContext());
		this.context = context;
		this.down_mydb = down_mydb;
		this.list = list;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		try{
			pref = context.getSharedPreferences(context.getString(R.string.txt_sharedpreferences_string), Activity.MODE_PRIVATE);
			String pre_title= pref.getString("title", "");
			String pubDate = pref.getString("pubDate", "");
			
			if(view == null){	
				LayoutInflater layoutInflater = LayoutInflater.from(context);
				view = layoutInflater.inflate(R.layout.activity_sub5_2_listrow, parent, false); 
			}
			TextView txt_sub_title = (TextView)view.findViewById(R.id.txt_sub_title);
			txt_sub_title.setText(list.get(position).title);
			txt_sub_title.setTextColor(Color.BLACK);
			if(list.get(position).title.equals(pre_title) && setDateTrim(list.get(position).pubDate).equals(pubDate)){
				txt_sub_title.setTextColor(Color.RED);
			}else{
				txt_sub_title.setTextColor(Color.BLACK);
			}
			TextView txt_sub_pubdate = (TextView)view.findViewById(R.id.txt_sub_pubdate);
			String setDateTrim = setDateTrim(list.get(position).pubDate);
			txt_sub_pubdate.setText(setDateTrim);
			
			ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progress_down);
			progressBar.setVisibility(View.INVISIBLE);
			
			bt_sub_down = (ImageButton)view.findViewById(R.id.bt_sub_down);
			bt_sub_down.setFocusable(false);
			bt_sub_down.setSelected(false);
			
			String title = list.get(position).title.replaceAll("\\p{Punct}", "") + "";
			String file_type = getExtension(list.get(position).enclosure);
			String file_name = Environment.getExternalStorageDirectory().getAbsolutePath()
					+ context.getString(R.string.sub5_txt9) +title+"."+file_type;
			if(file_name.lastIndexOf("?") != -1){
	        	sub_file_name = file_name.replace(file_name.substring(file_name.lastIndexOf("?")), "");
	        }else{
	        	sub_file_name = file_name;
	        }
//			Log.i("dsu", "sub_file_name : " + sub_file_name);
			cursor2 = down_mydb.getReadableDatabase().rawQuery(
					"select * from download_list where enclosure = '"+sub_file_name+"'", null);
			if(null != cursor2 && cursor2.moveToFirst()){
				down_enclosure = cursor2.getString(cursor2.getColumnIndex("enclosure"));
			}else{
				down_enclosure = "empty";
			}
			
//			Log.i("dsu", "down_enclosure : " + down_enclosure);
			if(down_enclosure.equals("empty")){
				bt_sub_down.setImageResource(R.drawable.bt_download_normal);
			}else{
				bt_sub_down.setImageResource(R.drawable.bt_play_normal);	
			}
			bt_sub_down.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					String old_title = list.get(position).title;
					String title = list.get(position).title.replaceAll("\\p{Punct}", "") + "";
					String enclosure = list.get(position).enclosure + "";
					String pubDate = setDateTrim(list.get(position).pubDate + "");
					String description_title = list.get(position).description_title + "";
					String provider = list.get(position).provider + "";
					String image = list.get(position).image + "";
					
					String file_type = getExtension(list.get(position).enclosure);
					String file_name = Environment.getExternalStorageDirectory().getAbsolutePath()
							+ context.getString(R.string.sub5_txt9) +title+"."+file_type;
					if(file_name.lastIndexOf("?") != -1){
			        	sub_file_name = file_name.replace(file_name.substring(file_name.lastIndexOf("?")), "");
			        }else{
			        	sub_file_name = file_name;
			        }
//					Log.i("dsu", "sub_file_name : " + sub_file_name);
					cursor2 = down_mydb.getReadableDatabase().rawQuery(
							"select * from download_list where enclosure = '"+sub_file_name+"'", null);
					if(null != cursor2 && cursor2.moveToFirst()){
						down_enclosure = cursor2.getString(cursor2.getColumnIndex("enclosure"));
					}else{
						down_enclosure = "empty";
					}
					
					if(down_enclosure.equals("empty")){
						try{
							connectivityManger = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
							mobile = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
							wifi = connectivityManger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
							if(mobile.isConnected()){
								AlertShow(context.getString(R.string.sub5_txt25), context, title, enclosure,position, description_title, down_mydb, provider, image, pubDate, old_title);
							}else{
								Sub5_2_Activity.downloadAsync = new DownloadAsync(context, title, enclosure,position, description_title, down_mydb, provider, image, pubDate, old_title);
								Sub5_2_Activity.downloadAsync.execute();
							}
						}catch(Exception e){
						}
					}else{
						Intent intent = new Intent(context, CustomMediaPlayer.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
						intent.putExtra("title", old_title);
						intent.putExtra("enclosure", down_enclosure);
						intent.putExtra("pubDate", pubDate);
						intent.putExtra("image", image);
						intent.putExtra("description_title", description_title);
						intent.putExtra("down_buffer", true);
						context.startActivity(intent);
					}
				}
			});
			
			bt_sub_continue = (ImageButton)view.findViewById(R.id.bt_sub_continue);
			bt_sub_continue.setFocusable(false);
			bt_sub_continue.setSelected(false);
			cursor = Sub5_2_Activity.continue_mydb.getReadableDatabase().rawQuery(
					"select * from continue_list where enclosure = '"+list.get(position).enclosure+"'", null);
			if(null != cursor && cursor.moveToFirst()){
				continue_enclosure = cursor.getString(cursor.getColumnIndex("enclosure"));
			}else{
				continue_enclosure = "empty";
			}
			if(continue_enclosure.equals("empty")){
				bt_sub_continue.setImageResource(R.drawable.bt_continue_normal);
			}else{
				bt_sub_continue.setImageResource(R.drawable.bt_continue_pressed);	
			}
			
			bt_sub_continue.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					cursor = Sub5_2_Activity.continue_mydb.getReadableDatabase().rawQuery(
							"select * from continue_list where enclosure = '"+list.get(position).enclosure+"'", null);
					if(null != cursor && cursor.moveToFirst()){
						continue_enclosure = cursor.getString(cursor.getColumnIndex("enclosure"));
//						Log.i("dsu", "enclosure : " + continue_enclosure);
					}else{
						continue_enclosure = "empty";
					}
					if(continue_enclosure.equals("empty")){
						bt_sub_continue.setImageResource(R.drawable.bt_favorite_pressed);
						ContentValues cv = new ContentValues();
						cv.put("title", list.get(position).title);
						cv.put("enclosure", list.get(position).enclosure);
						cv.put("pubDate", list.get(position).pubDate);
						cv.put("image", list.get(position).image);
						cv.put("description_title", list.get(position).description_title);
						Sub5_2_Activity.continue_mydb.getWritableDatabase().insert("continue_list", null, cv);
						if(Sub5_2_Activity.sub_adapter != null){
							Sub5_2_Activity.sub_adapter.notifyDataSetChanged();	
						}
						Toast.makeText(context, context.getString(R.string.sub5_txt26), Toast.LENGTH_SHORT).show();
					}else{
						bt_sub_continue.setImageResource(R.drawable.bt_favorite_normal);
						Sub5_2_Activity.continue_mydb.getWritableDatabase().delete("continue_list", "enclosure" + "='" +continue_enclosure+"'", null);
						if(Sub5_2_Activity.sub_adapter != null){
							Sub5_2_Activity.sub_adapter.notifyDataSetChanged();	
						}
						Toast.makeText(context, context.getString(R.string.sub5_txt27), Toast.LENGTH_SHORT).show();
					}
				}
			});
		}catch (Exception e) {
		}finally{
			Sub5_2_Activity.continue_mydb.close();
			Sub5_2_Activity.down_mydb.close();
			if(cursor != null){
				cursor.close();	
			}
			if(cursor2 != null){
				cursor2.close();	
			}
		}
		return view;
	}
	public String setDateTrim(String paramString){
		return paramString.substring(0, 8);
	}
	
	public String getExtension(String fileStr) {
		return fileStr.substring(fileStr.lastIndexOf(".") + 1, fileStr.length());
	}
	
	public void AlertShow(String msg, final Context context, final String title, final String enclosure, final int position, final String description_title, final Sub5_4_DBopenHelper down_mydb, final String provider, final String image, final String pubDate, final String old_title) {
        AlertDialog.Builder alert_internet_status = new AlertDialog.Builder(context);
         alert_internet_status.setCancelable(false);
         alert_internet_status.setMessage(msg);
         alert_internet_status.setPositiveButton(context.getString(R.string.sub5_txt28),
                 new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                    	 Sub5_2_Activity.downloadAsync = new DownloadAsync(context, title, enclosure,position, description_title, down_mydb, provider, image, pubDate, old_title);
                    	 Sub5_2_Activity.downloadAsync.execute();
                    	 dialog.dismiss();
                     }
                 });
         alert_internet_status.setNegativeButton(context.getString(R.string.sub5_txt29), 
        		 new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
         alert_internet_status.show();
	}
}