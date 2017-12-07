package com.good.worshipbible.nos.podcast.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.good.worshipbible.nos.R;
import com.good.worshipbible.nos.ccm.data.Main_Data;
import com.good.worshipbible.nos.podcast.Sub5_1_Activity;
import com.good.worshipbible.nos.podcast.data.Sub5_1_ColumData;
import com.good.worshipbible.nos.util.ImageLoader;
import com.good.worshipbible.nos.util.RoundedTransform;
import com.squareup.picasso.Picasso;


public class Sub5_1_Adapter extends BaseAdapter{
	public Context context;
	public ImageLoader imgLoader;
	public String num = "empty";
	public Cursor cursor;
	public ImageButton bt_favorite;
	public ArrayList<Sub5_1_ColumData> list;
	public ListView listview_main;
	public String searchKeyword;
	public Sub5_1_Adapter(Context context, ArrayList<Sub5_1_ColumData> list, ListView listview_main, String searchKeyword) {
		this.imgLoader = new ImageLoader(context.getApplicationContext());
		this.context = context;
		this.list = list;
		this.listview_main = listview_main;
		this.searchKeyword = searchKeyword;
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
			if(view == null){	
				LayoutInflater layoutInflater = LayoutInflater.from(context);
				view = layoutInflater.inflate(R.layout.activity_sub5_1_listrow, parent, false); 
			}
			ImageView img_imageurl = (ImageView)view.findViewById(R.id.img_imageurl);
			img_imageurl.setFocusable(false);
			String image_url = list.get(position).imageurl;
			
			BitmapFactory.Options dimensions = new BitmapFactory.Options(); 
			dimensions.inJustDecodeBounds = true;
			Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.no_image, dimensions);
			        int height = dimensions.outHeight;
			        int width =  dimensions.outWidth;
			Picasso.with(context)
		    .load(image_url)
		    .transform(new RoundedTransform())
		    .resize(width, height )
		    .placeholder(R.drawable.no_image)
		    .error(R.drawable.no_image)
		    .into(img_imageurl);
			
//			Collections.sort(list, new Sub5_1_ColumData());

			TextView txt_title = (TextView)view.findViewById(R.id.txt_title);
			txt_title.setTextColor(Color.BLACK);
			if(searchKeyword.length() == 0){
				txt_title.setText(list.get(position).title);
					
			}else{
				setTextViewColorPartial(txt_title, list.get(position).title, searchKeyword, Color.RED);	
			}
			
			TextView txt_provider = (TextView)view.findViewById(R.id.txt_provider);
			txt_provider.setTextColor(Color.GRAY);
			if(searchKeyword.length() == 0){
				txt_provider.setText(list.get(position).provider);	
			}else{
				setTextViewColorPartial(txt_provider, list.get(position).provider, searchKeyword, Color.RED);
								
			}
			
			TextView txt_udate = (TextView)view.findViewById(R.id.txt_udate);
			txt_udate.setText(setDateTrim(list.get(position).udate));
			
			bt_favorite = (ImageButton)view.findViewById(R.id.bt_favorite);
			bt_favorite.setFocusable(false);
			bt_favorite.setSelected(false);
			cursor = Sub5_1_Activity.favorite_mydb.getReadableDatabase().rawQuery(
					"select * from favorite_list where num = '"+list.get(position).num+"'", null);
			if(null != cursor && cursor.moveToFirst()){
				num = cursor.getString(cursor.getColumnIndex("num"));
			}else{
				num = "empty";
			}
			if(num.equals("empty")){
				bt_favorite.setImageResource(R.drawable.bt_favorite_normal);
			}else{
				bt_favorite.setImageResource(R.drawable.bt_favorite_pressed);	
			}
			
			bt_favorite.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					cursor = Sub5_1_Activity.favorite_mydb.getReadableDatabase().rawQuery(
							"select * from favorite_list where num = '"+list.get(position).num+"'", null);
					if(null != cursor && cursor.moveToFirst()){
						num = cursor.getString(cursor.getColumnIndex("num"));
					}else{
						num = "empty";
					}
					if(num.equals("empty")){
						bt_favorite.setImageResource(R.drawable.bt_favorite_pressed);
						ContentValues cv = new ContentValues();
						cv.put("id",list.get(position).id);
						cv.put("num",list.get(position).num);
						cv.put("title",list.get(position).title);
						cv.put("provider",list.get(position).provider);
						cv.put("imageurl",list.get(position).imageurl);
						cv.put("rssurl",list.get(position).rssurl);
						cv.put("udate",list.get(position).udate);
						Sub5_1_Activity.favorite_mydb.getWritableDatabase().insert("favorite_list", null, cv);
						Sub5_1_Activity.main_adapter.notifyDataSetChanged();
						Toast.makeText(context, context.getString(R.string.sub5_txt15), Toast.LENGTH_SHORT).show();
					}else{
						bt_favorite.setImageResource(R.drawable.bt_favorite_normal);
						Sub5_1_Activity.favorite_mydb.getWritableDatabase().delete("favorite_list", "num" + "=" +num, null);
						Sub5_1_Activity.main_adapter.notifyDataSetChanged();
						Toast.makeText(context, context.getString(R.string.sub5_txt16), Toast.LENGTH_SHORT).show();
					}
				}
			});
		}catch (Exception e) {
		}finally{
			Sub5_1_Activity.favorite_mydb.close();
			if(cursor != null){
				cursor.close();	
			}
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
	
	public String setDateTrim(String paramString){
		return paramString.substring(2, 11).replace("-", ".");
	}
}