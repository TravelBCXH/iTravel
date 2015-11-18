
package com.example.travelguide;

import java.util.ArrayList;
import java.util.List;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.amap.api.maps.model.LatLng;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class ThirdActivity extends Activity {
	private Spinner spinner1,spinner2;
	private List<String> data_list;
	private ArrayAdapter<String> arr_adapter;
	private double mm,nn;
	private AMap aMap;
	private OnLocationChangedListener mListener;
    private LocationManagerProxy mLocationManagerProxy;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_third);
		Intent intent_accept = getIntent();           //����һ��������ͼ
        Bundle bundle = intent_accept.getExtras();    //����Bundle�������ڽ���Intent����
		mm = bundle.getDouble("dangqian1");               //��ȡIntent������age
        nn = bundle.getDouble("dangqian2");  
        spinner1 = (Spinner) findViewById(R.id.spinnerthird1);
	    
        //����
        data_list = new ArrayList<String>();
        data_list.add("�ҵ�λ��");
        data_list.add("�岷��԰");
        data_list.add("����");
        data_list.add("�����У");
        data_list.add("����֮��");
        data_list.add("ǧ������");
        data_list.add("�����껪");
        data_list.add("֪����");
        data_list.add("С������");
        data_list.add("�������ɲ�");
        data_list.add("���");
        data_list.add("�������");
        data_list.add("Բͨ��ݵ�");
        
        
        //������
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //������ʽ
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //����������
        spinner1.setAdapter(arr_adapter);
        spinner2 = (Spinner) findViewById(R.id.spinnerthird2);
	    
        //����
        data_list = new ArrayList<String>();
        
        data_list.add("�岷��԰");
        data_list.add("����");
        data_list.add("�����У");
        data_list.add("����֮��");
        data_list.add("ǧ������");
        data_list.add("�����껪");
        data_list.add("֪����");
        data_list.add("С������");
        data_list.add("�������ɲ�");
        data_list.add("���");
        data_list.add("�������");
        data_list.add("Բͨ��ݵ�");
        
       
        
        
        
        //������
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //������ʽ
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //����������
        spinner2.setAdapter(arr_adapter);
        
        
        
      
				
        
        
        
		Button btn = (Button)findViewById(R.id.queren);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				
				
				String text1 = (String) spinner1.getSelectedItem();
				
				
				String text2  = (String) spinner2.getSelectedItem();
				
//				����     26.05502  119.18683
//				Բͨ    26.05500   119.186838
//				�����У    26.05502    119.18705
//				�岷��԰    26.05383    119.18704	
//				����  26.051906��119.191682/
//				��� 26.051067��119.192433/
//				������� 26.051241��119.191629

//				addMarker(26.051246,119.19283,"����֮��","1.png");//
//				addMarker(26.050908,119.19187,"ǧ������","1.png");//
//				addMarker(26.050932,119.191784,"��߲��԰","1.png");//
//				addMarker(26.051824,119.191677,"�����껪","1.png");//
//				addMarker(26.052002,119.191704,"Բͨ��ݵ�","1.png");//
//				addMarker(26.052103,119.191725,"�����У","1.png");//
//				addMarker(26.052171,119.191816,"֪����","1.png");//
//				addMarker(26.052171,119.191913,"С������","1.png");//
//				addMarker(26.052195,119.192122,"�������ɲ�","1.png");//
//				
//				��� 26.051067��119.192433
//				������� 26.051241��119.191629

				String s0 = "�ҵ�λ��";
 				String s1 = "����";
				String s2 = "Բͨ��ݵ�";
				String s3 = "�����У";
				String s4 = "�岷��԰";
				String s5 = "����֮��";
				String s6 = "ǧ������";
				String s7 = "�����껪";
				String s8 = "֪����";
				String s9 = "С������";
				String s10 = "�������ɲ�";
				String s11= "���";
				String s12= "�������";

				
				
				int a=0,b=0;
				
				if(text1.equals(s1))
				{
					a=1;
				}
				else if(text1.equals(s2))
				{
					a=2;
				}
				else if(text1.equals(s3))
				{
					a=3;
				}
				else if(text1.equals(s4))
				{
					a=4;
				}
				else if(text1.equals(s5))
				{
					a=5;
				}
				else if(text1.equals(s6))
				{
					a=6;
				}
				else if(text1.equals(s7))
				{
					a=7;
				}
				else if(text1.equals(s8))
				{
					a=8;
				}
				else if(text1.equals(s9))
				{
					a=9;
				}
				else if(text1.equals(s10))
				{
					a=10;
				}
				else if(text1.equals(s11))
				{
					a=11;
				}
				else if(text1.equals(s12))
				{
					a=12;
				}
				else if(text1.equals(s0))
				{
					a=100;
				}
				
				
				if(text2.equals(s1))
				{
					b=1;
				}
				else if(text2.equals(s2))
				{
					b=2;
				}
				else if(text2.equals(s3))
				{
					b=3;
				}
				else if(text2.equals(s4))
				{
					b=4;
				}
				else if(text2.equals(s5))
				{
					b=5;
				}
				else if(text2.equals(s6))
				{
					b=6;
				}
				else if(text2.equals(s7))
				{
					b=7;
				}
				else if(text2.equals(s8))
				{
					b=8;
				}
				else if(text2.equals(s9))
				{
					b=9;
				}
				else if(text2.equals(s10))
				{
					b=10;
				}
				else if(text2.equals(s11))
				{
					b=11;
				}
				else if(text2.equals(s12))
				{
					b=12;
				}

				
				
				/* �½�һ��Intent���� */
		        Intent intent = new Intent();
		        intent.putExtra("number1",a);    
		        intent.putExtra("number2",b);
		        intent.putExtra("number3",mm);
		        intent.putExtra("number4",nn);
		        /* ָ��intentҪ�������� */
		        intent.setClass(ThirdActivity.this, SecondActivity.class);
		        /* ����һ���µ�Activity */
		        ThirdActivity.this.startActivity(intent);
		        finish();
		        
				
			}
		});
	}
	


}
