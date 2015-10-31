package com.example.travelguide;

import android.os.Bundle;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;


import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_first);
		
		Button btn = (Button)findViewById(R.id.searchText);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				
				/* �½�һ��Intent���� */
		        Intent intent = new Intent();
		        intent.putExtra("name","LeiPei");    
		        /* ָ��intentҪ�������� */
		        intent.setClass(MainActivity.this, SecondActivity.class);
		        /* ����һ���µ�Activity */
		        MainActivity.this.startActivity(intent);
		        /* �رյ�ǰ��Activity */
		        MainActivity.this.finish();
				
			}
		});
		
		
	   
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
