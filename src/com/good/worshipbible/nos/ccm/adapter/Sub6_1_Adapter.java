package com.good.worshipbible.nos.ccm.adapter;

import java.util.ArrayList;
import java.util.Collections;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
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
import com.good.worshipbible.nos.ccm.Sub6_1_Activity;
import com.good.worshipbible.nos.ccm.data.Main_Data;
import com.good.worshipbible.nos.util.ImageLoader;
import com.good.worshipbible.nos.util.RoundedTransform;
import com.squareup.picasso.Picasso;


public class Sub6_1_Adapter extends BaseAdapter{
	public Context context;
	public ImageLoader imgLoader;
	public int _id = -1;
	public String id = "empty";
	public Cursor cursor;
	public ImageButton bt_favorite;
	public ArrayList<Main_Data> list;
	public ListView listview_main;
	public String searchKeyword;
	public Sub6_1_Adapter(Context context, ArrayList<Main_Data> list, ListView listview_main, String searchKeyword) {
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
				view = layoutInflater.inflate(R.layout.activity_sub6_1_listrow, parent, false); 
			}
			ImageView img_imageurl = (ImageView)view.findViewById(R.id.img_imageurl);
			img_imageurl.setFocusable(false);
			String image_url = list.get(position).thumbnail_hq;
			
			BitmapFactory.Options dimensions = new BitmapFactory.Options(); 
			dimensions.inJustDecodeBounds = true;
			
			Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.no_image, dimensions);
			        int height = dimensions.outHeight;
			        int width =  dimensions.outWidth;
			Picasso.with(context)
		    .load(image_url)
		    .transform(new RoundedTransform())
		    .resize(width, height )
		    .placeholder(R.drawable.loader)
		    .error(R.drawable.loader)
		    .into(img_imageurl);
			
			Collections.sort(list, new Main_Data());

			TextView txt_music = (TextView)view.findViewById(R.id.txt_music);
			if(searchKeyword.length() == 0){
				txt_music.setText(list.get(position).title);	
			}else{
				setTextViewColorPartial(txt_music, list.get(position).title, searchKeyword, Color.RED);	
			}
			
			TextView txt_artist = (TextView)view.findViewById(R.id.txt_artist);
			txt_artist.setText(list.get(position).category);
			
			bt_favorite = (ImageButton)view.findViewById(R.id.bt_favorite);
			bt_favorite.setFocusable(false);
			bt_favorite.setSelected(false);
			cursor = Sub6_1_Activity.favorite_mydb.getReadableDatabase().rawQuery(
					"select * from favorite_list where id = '"+list.get(position).id+"'", null);
			if(null != cursor && cursor.moveToFirst()){
				id = cursor.getString(cursor.getColumnIndex("id"));
				_id = cursor.getInt(cursor.getColumnIndex("_id"));
			}else{
				id = "empty";
				_id = -1;
			}
			if(id.equals("empty")){
				bt_favorite.setImageResource(R.drawable.bt_favorite_normal);
			}else{
				bt_favorite.setImageResource(R.drawable.bt_favorite_pressed);	
			}
			
			bt_favorite.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					cursor = Sub6_1_Activity.favorite_mydb.getReadableDatabase().rawQuery(
							"select * from favorite_list where id = '"+list.get(position).id+"'", null);
					if(null != cursor && cursor.moveToFirst()){
						id = cursor.getString(cursor.getColumnIndex("id"));
						_id = cursor.getInt(cursor.getColumnIndex("_id"));
					}else{
						id = "empty";
						_id = -1;
					}
					if(id.equals("empty")){
						bt_favorite.setImageResource(R.drawable.bt_favorite_pressed);
						ContentValues cv = new ContentValues();
						cv.put("id", list.get(position).id);
						cv.put("title", list.get(position).title);
						cv.put("category", list.get(position).category);
						cv.put("thumbnail_hq", list.get(position).thumbnail_hq);
						cv.put("duration", list.get(position).duration);
						Sub6_1_Activity.favorite_mydb.getWritableDatabase().insert("favorite_list", null, cv);
						Sub6_1_Activity.main_adapter.notifyDataSetChanged();
						Toast.makeText(context, context.getString(R.string.sub6_txt10), Toast.LENGTH_SHORT).show();
					}else{
						bt_favorite.setImageResource(R.drawable.bt_favorite_normal);
						Sub6_1_Activity.favorite_mydb.getWritableDatabase().delete("favorite_list", "_id" + "=" +_id, null);
						Sub6_1_Activity.main_adapter.notifyDataSetChanged();
						Toast.makeText(context, context.getString(R.string.sub6_txt15), Toast.LENGTH_SHORT).show();
					}
					
				}
			});
			if(listview_main.isItemChecked(position)){
				view.setBackgroundColor(Color.parseColor("#00a8ec"));
				txt_music.setTextColor(Color.parseColor("#ffffff"));
				txt_artist.setTextColor(Color.parseColor("#ffffff"));
			}else{
				view.setBackgroundColor(Color.parseColor("#00000000"));
				txt_music.setTextColor(Color.parseColor("#000000"));
				txt_artist.setTextColor(Color.parseColor("#000000"));
			}
		}catch (Exception e) {
		}finally{
			Sub6_1_Activity.favorite_mydb.close();
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
}