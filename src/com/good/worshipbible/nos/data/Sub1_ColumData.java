/*
 * com.ziofront.android.contacts
 * Contact.java
 * Jiho Park    2009. 11. 27.
 *
 * Copyright (c) 2009 ziofront.com. All Rights Reserved.
 */
package com.good.worshipbible.nos.data;


public class Sub1_ColumData {
	 String kwon;
	 String jang;
	 String jul;
	 String content;
	 public Sub1_ColumData(String kwon,String jang, String jul, String content) {
		this.kwon = kwon;
		this.jang = jang;
		this.jul = jul;
		this.content = content;
	}
	public String getKwon() {
		return kwon;
	}
	public void setKwon(String kwon) {
		this.kwon = kwon;
	}
	public String getJang() {
		return jang;
	}
	public void setJang(String jang) {
		this.jang = jang;
	}
	public String getJul() {
		return jul;
	}
	public void setJul(String jul) {
		this.jul = jul;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
 }
