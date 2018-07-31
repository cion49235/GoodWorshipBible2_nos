package com.good.worshipbible.nos.podcast.data;

import java.util.Comparator;


public class Sub5_1_ColumData implements Comparator<Sub5_1_ColumData>{
	 public String id;
	 public String num;
	 public String title;
	 public String provider;
	 public String imageurl;
	 public String rssurl;
	 public String udate;
	 
	 @Override
		public int compare(Sub5_1_ColumData arg0, Sub5_1_ColumData arg1) {
			// TODO Auto-generated method stub
			return arg0.title.compareTo(arg1.title);
		}
 }
