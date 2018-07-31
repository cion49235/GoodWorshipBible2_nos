package com.good.worshipbible.nos.ccm.data;

import java.util.Comparator;


public class Main_Data implements Comparator<Main_Data> {
	public int _id;
	public String id; 
	public String title;
	public String category;
	public String thumbnail_hq;
	public String duration;
	
	@Override
	public int compare(Main_Data arg0, Main_Data arg1) {
		// TODO Auto-generated method stub
		return arg0.title.compareTo(arg1.title);
	}
 }
