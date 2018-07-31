/*
 * com.ziofront.android.contacts
 * Contact.java
 * Jiho Park    2009. 11. 27.
 *
 * Copyright (c) 2009 ziofront.com. All Rights Reserved.
 */
package com.good.worshipbible.nos.data;

 public class Sub3_ColumData {
	 public int _id;
	 public String title;
	 public int description;
	 
	public Sub3_ColumData(int _id, String title, int description) {
		this._id = _id;
		this.title = title;
		this.description = description;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDescription() {
		return description;
	}

	public void setDescription(int description) {
		this.description = description;
	}
 }
