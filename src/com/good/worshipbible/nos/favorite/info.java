//This is source code of favorite. Copyrightⓒ. Tarks. All Rights Reserved.
package com.good.worshipbible.nos.favorite;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.good.worshipbible.nos.R;

public class info extends SherlockActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_info);
      //액션바백버튼가져오기
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); 
       
    }
      //빽백키 상단액션바
 	   @Override
 	    public boolean onOptionsItemSelected(MenuItem item) {
 	    switch (item.getItemId()) {
 	        case android.R.id.home:
 	            onBackPressed();
 	            return true;
 	        default:
 	            return super.onOptionsItemSelected(item);
 	    }
 	    
 	    
 	   }
    
}
