package com.example.travelguide;

import java.util.ArrayList;
import java.util.List;

import com.amap.api.location.LocationManagerProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class RoadActivity extends Activity{
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
		setContentView(R.layout.activity_road);
		Intent intent_accept = getIntent();           //����һ��������ͼ
        Bundle bundle = intent_accept.getExtras();    //����Bundle�������ڽ���Intent����
		mm = bundle.getDouble("dangqian1");               //��ȡIntent������age
        nn = bundle.getDouble("dangqian2");  
        spinner1 = (Spinner) findViewById(R.id.spinnerroad1);
	    
        //����
        data_list = new ArrayList<String>();
        data_list.add("�ҵ�λ��");
        data_list.add("��ҳ���");
        data_list.add("˳����");
        data_list.add("����");
        data_list.add("�������");
        data_list.add("������װ");
        data_list.add("����������");
        data_list.add("�ϴ���");
        data_list.add("Բͨ���");
        data_list.add("ŵ��������װ");
        data_list.add("������");
        
        
        
        //������
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //������ʽ
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //����������
        spinner1.setAdapter(arr_adapter);
        spinner2 = (Spinner) findViewById(R.id.spinnerroad2);
	    
        //����
        data_list = new ArrayList<String>();
        
        data_list.add("��ҳ���");
        data_list.add("˳����");
        data_list.add("����");
        data_list.add("�������");
        data_list.add("������װ");
        data_list.add("����������");
        data_list.add("�ϴ���");
        data_list.add("Բͨ���");
        data_list.add("ŵ��������װ");
        data_list.add("������");
        
       
        
        
        
        
        //������
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //������ʽ
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //����������
        spinner2.setAdapter(arr_adapter);
        
        
        
      
				
        
        
        
		Button btn = (Button)findViewById(R.id.queren1);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				
				
				String text1 = (String) spinner1.getSelectedItem();
				
				
				String text2  = (String) spinner2.getSelectedItem();
				
//				��γ���   119.190722,26.051058
//              ˳����   119.190829,26.050947
//				����     119.190862,26.05085
//				�������     119.190878,26.050754
//				������װ    119.190894,26.050677
//				����������   119.190915,26.050571
//				�ϴ���    119.190937,26.050474
//				Բͨ�ٵ�    119.190926,26.050392
//				ŵ��������װ�����µ�ͼ�����У�   119.191028,26.050296
//				������     119.191028,26.050132



				String s0 = "�ҵ�λ��";
 				String s1 = "��ҳ���";
				String s2 = "˳����";
				String s3 = "����";
				String s4 = "�������";
				String s5 = "������װ";
				String s6 = "����������";
				String s7 = "�ϴ���";
				String s8 = "Բͨ�ٵ�";
				String s9 = "ŵ��������װ";
				String s10 = "������";
				

				
				
				int a=0,b=0;
				
				if(text1.equals(s1))
				{
					a=41;
				}
				else if(text1.equals(s2))
				{
					a=42;
				}
				else if(text1.equals(s3))
				{
					a=43;
				}
				else if(text1.equals(s4))
				{
					a=44;
				}
				else if(text1.equals(s5))
				{
					a=45;
				}
				else if(text1.equals(s6))
				{
					a=46;
				}
				else if(text1.equals(s7))
				{
					a=47;
				}
				else if(text1.equals(s8))
				{
					a=48;
				}
				else if(text1.equals(s9))
				{
					a=49;
				}
				else if(text1.equals(s10))
				{
					a=410;
				}
				
				else if(text1.equals(s0))
				{
					a=100;
				}
				
				
				if(text2.equals(s1))
				{
					b=41;
				}
				else if(text2.equals(s2))
				{
					b=42;
				}
				else if(text2.equals(s3))
				{
					b=43;
				}
				else if(text2.equals(s4))
				{
					b=44;
				}
				else if(text2.equals(s5))
				{
					b=45;
				}
				else if(text2.equals(s6))
				{
					b=46;
				}
				else if(text2.equals(s7))
				{
					b=47;
				}
				else if(text2.equals(s8))
				{
					b=48;
				}
				else if(text2.equals(s9))
				{
					b=49;
				}
				else if(text2.equals(s10))
				{
					b=410;
				}
				

				
				
				/* �½�һ��Intent���� */
		        Intent intent = new Intent();
		        intent.putExtra("number5",a);    
		        intent.putExtra("number6",b);
		        intent.putExtra("number7",mm);
		        intent.putExtra("number8",nn);
		        intent.putExtra("name3",4);    
		        /* ָ��intentҪ�������� */
		        intent.setClass(RoadActivity.this, SecondActivity.class);
		        /* ����һ���µ�Activity */
		        RoadActivity.this.startActivity(intent);
		        finish();
		        
				
			}
		});
	}
	

}
