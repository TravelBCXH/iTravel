package com.example.travelguide;
import com.example.travelguide.until.*;


import java.util.ArrayList;
import java.util.List;

import android.media.MediaPlayer;
import android.net.ConnectivityManager;

import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnMapClickListener;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.NaviPara;
import com.amap.api.maps.overlay.PoiOverlay;
import com.amap.api.maps.LocationSource;





import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.example.travelguide.R;
import com.example.travelguide.SecondActivity;
import com.example.travelguide.until.AMapUtil;
import com.example.travelguide.until.Nameclass;
import com.example.travelguide.until.ToastUtil;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Tip;
import com.amap.api.services.help.Inputtips.InputtipsListener;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends Activity implements  LocationSource, AMapLocationListener, OnMapClickListener, OnMarkerClickListener, 
OnMapLoadedListener, OnClickListener, TextWatcher, InfoWindowAdapter, OnPoiSearchListener,AMapNaviListener {

	
	private int area=0;
	private int store=0;
	private int LocationFuda=0;
	
	
	private AMapNavi mAMapNavi;
    private int sendnum1=0;
    private int sendnum2=0;
    private int sendnum3=0;
    private int sendnum4=0;
	// ����յ�����
	private double mm=0,nn=0;
	private double instant_lat=0,instant_lng=0;
	private NaviLatLng mNaviStart = new NaviLatLng(26.051000,119.192000);
	private NaviLatLng mNaviEnd = new NaviLatLng(26.051212,119.192369);
	// ����յ��б�
	private ArrayList<NaviLatLng> mStartPoints = new ArrayList<NaviLatLng>();
	private ArrayList<NaviLatLng> mEndPoints = new ArrayList<NaviLatLng>();
	// �滮��·
	private RouteOverLay mRouteOverLay;
	
	
	private AMap aMap;
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private LocationManagerProxy mLocationManagerProxy;
    private Marker mGPSMarker;
    private PendingIntent mPendingIntent;
    private Circle mCircle;
    private LatLng mar1=new LatLng(26.0537,119.1875);
    private TextView markerText;
    private LatLng mar2=new LatLng(26.05393,119.18717);
    
   
    //
    private AutoCompleteTextView searchText;// ���������ؼ���
	private String keyWord = "";// Ҫ�����poi�����ؼ���
	private ProgressDialog progDialog = null;// ����ʱ������
	private PoiResult poiResult; // poi���صĽ��
	private PoiSearch.Query query;// Poi��ѯ������
	private PoiSearch poiSearch;// POI����

    
    public static final String GEOFENCE_BROADCAST_ACTION = "com.example.travelguide";
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_second);

		mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
	    init(savedInstanceState);
	    
	    
	    mAMapNavi = AMapNavi.getInstance(this);

		mAMapNavi.setAMapNaviListener(this);

		
		mRouteOverLay = new RouteOverLay(aMap, null);
		
		Intent intent_num1 = getIntent();
		Bundle bundle_num1 = intent_num1.getExtras();  
		sendnum1=bundle_num1.getInt("name1");
		if(sendnum1==3 || sendnum1==4) area=sendnum1;
		
		
		Intent intent_num2 = getIntent();
		Bundle bundle_num2 = intent_num2.getExtras();  
		sendnum2=bundle_num2.getInt("name2");
		if(sendnum2==3 || sendnum2==4) area=sendnum2;
		
		Intent intent_num3 = getIntent();
		Bundle bundle_num3 = intent_num3.getExtras();  
		sendnum3=bundle_num3.getInt("name3");
		if(sendnum3==3 || sendnum3==4) area=sendnum3;
		
		
		
		Intent intent_accept = getIntent();           //����һ��������ͼ
        Bundle bundle = intent_accept.getExtras();    //����Bundle�������ڽ���Intent����
//        String name = bundle.getString("name");       //��ȡIntent������name
       
        int numb1=0;
        int numb2=0;
        int ss=0;
        if(area==3) numb1 = bundle.getInt("number1");               //��ȡIntent������age
        
        if(area==3) numb2 = bundle.getInt("number2");               //��ȡIntent������age
        
        if(area==4) numb1 = bundle.getInt("number5");               //��ȡIntent������age
        
        if(area==4) numb2 = bundle.getInt("number6");               //��ȡIntent������age
       
		
		if(numb1!=0 &&numb2!=0)
		{
			
			double m1=0,n1=0,m2=0,n2=0;

			
//			����     26.05502  119.18683
//			Բͨ    26.05500   119.186838
//			�����У    26.05502    119.18705
//			�岷��԰    26.05383    119.18704	
//			addMarker(26.051246,119.19283,"����֮��","1.png");//
//			addMarker(26.050908,119.19187,"ǧ������","1.png");//
//			addMarker(26.050932,119.191784,"��߲��԰","1.png");//
//			addMarker(26.051824,119.191677,"�����껪","1.png");//
//			addMarker(26.052002,119.191704,"Բͨ��ݵ�","1.png");//
//			addMarker(26.052103,119.191725,"�����У","1.png");//
//			addMarker(26.052171,119.191816,"֪����","1.png");//
//			addMarker(26.052171,119.191913,"С������","1.png");//
//			addMarker(26.052195,119.192122,"�������ɲ�","1.png");//	
//			��� 26.051067��119.192433
//			������� 26.051241��119.191629

			
//			String s1 = "����";
//			String s2 = "Բͨ";
//			String s3 = "�����У";
//			String s4 = "�岷��԰";
//			String s5 = "����֮��";
//			String s6 = "ǧ������";
//			String s7 = "�����껪";
//			String s8 = "֪����";
//			String s9 = "С������";
//			String s10 = "�������ɲ�";
//			String s11= "���";
//			String s12= "�������";
//			String s13= "Բͨ��ݵ�";

			//��ʼ�ص�ȷ��
			if(numb1==1)  
			{
				m1=26.051906;
				m2=119.191682;
			}
			else if(numb1==2)  
			{
				m1=26.052002;
				m2=119.191704;
			}
			else if(numb1==3)  
			{
				m1= 26.052103;
				m2=119.191725;
			}
			else if(numb1==4)  
			{
				m1=26.050932;
				m2=119.191784;
			}
			else if(numb1==5)  
			{
				m1=26.051246;
				m2=119.19283;
			}
			else if(numb1==6)  
			{
				m1=26.050908;
				m2=119.19187;
			}
			else if(numb1==7)  
			{
				m1=26.051824;
				m2=119.191677;
			}
			else if(numb1==8)  
			{
				m1=26.052171;
				m2=119.191816;
			}
			else if(numb1==9)  
			{
				m1=26.052171;
				m2=119.191913;
			}
			else if(numb1==10)  
			{
				m1=26.052195;
				m2=119.192122;
			}
			else if(numb1==11)  
			{
				m1=26.051067;
				m2=119.192433;
			}
			else if(numb1==12)  
			{
				m1=26.051241;
				m2=119.191629;
			}
			else if(numb1==41)  
			{
				m1=26.051058;
				m2=119.190722;
			}
			else if(numb1==42)  
			{
				m1=26.050947;
				m2=119.190829;
			}
			else if(numb1==43)  
			{
				m1= 26.05085;
				m2=119.190862;
			}
			else if(numb1==44)  
			{
				m1=26.050754;
				m2=119.190878;
			}
			else if(numb1==45)  
			{
				m1=26.050677;
				m2=119.190894;
			}
			else if(numb1==46)  
			{
				m1=26.050571;
				m2=119.190915;
			}
			else if(numb1==47)  
			{
				m1=26.050474;
				m2=119.190937;
			}
			else if(numb1==48)  
			{
				m1=26.050392;
				m2=119.190926;
			}
			else if(numb1==49)  
			{
				m1=26.050296;
				m2=119.191028;
			}
			else if(numb1==410)  
			{
				m1=26.050132;
				m2=119.191028;
			}
			
			
			else if(numb1==100)  
			{
				if(area==3) mm = bundle.getDouble("number3");               //��ȡIntent������age
				if(area==3) nn = bundle.getDouble("number4");               //��ȡIntent������age
				if(area==4) mm = bundle.getDouble("number7");               //��ȡIntent������age
				if(area==4) nn = bundle.getDouble("number8");               //��ȡIntent������age
				m1=mm;
				m2=nn;
				
			}
			
			
			
			//��ֹ�ص�ȷ��
			if(numb2==1)  
			{
				n1=26.051906;
				n2=119.191682;
			}
			else if(numb2==2)  
			{
				n1=26.052002;
				n2=119.191704;
			}
			else if(numb2==3)  
			{
				n1=26.052103;
				n2=119.191725;
			}
			else if(numb2==4)  
			{
				n1=26.050932;
				n2=119.191784;
			}
			else if(numb2==5)  
			{
				n1=26.051246;
				n2=119.19283;
			}
			else if(numb2==6)  
			{
				n1=26.050908;
				n2=119.19187;
			}
			else if(numb2==7)  
			{
				n1=26.051824;
				n2=119.191677;
			}
			else if(numb2==8)  
			{
				n1=26.052171;
				n2=119.191816;
			}
			else if(numb2==9)  
			{
				n1=26.052171;
				n2=119.191913;
			}
			else if(numb2==10)  
			{
				n1=26.052195;
				n2=119.192122;
			}
			else if(numb2==11)  
			{
				n1=26.051067;
				n2=119.192433;
			}
			else if(numb2==12)  
			{
				n1=26.051241;
				n2=119.191629;
			}
			else if(numb2==41)  
			{
				n1=26.051058;
				n2=119.190722;
			}
			else if(numb2==42)  
			{
				n1=26.050947;
				n2=119.190829;
			}
			else if(numb2==43)  
			{
				n1= 26.05085;
				n2=119.190862;
			}
			else if(numb2==44)  
			{
				n1=26.050754;
				n2=119.190878;
			}
			else if(numb2==45)  
			{
				n1=26.050677;
				n2=119.190894;
			}
			else if(numb2==46)  
			{
				n1=26.050571;
				n2=119.190915;
			}
			else if(numb2==47)  
			{
				n1=26.050474;
				n2=119.190937;
			}
			else if(numb2==48)  
			{
				n1=26.050392;
				n2=119.190926;
			}
			else if(numb2==49)  
			{
				n1=26.050296;
				n2=119.191028;
			}
			else if(numb2==410)  
			{
				n1=26.050132;
				n2=119.191028;
			}
			
			
			mNaviStart = new NaviLatLng(m1,m2);

			mNaviEnd = new NaviLatLng(n1,n2);
			
			
			mStartPoints.clear();
			mEndPoints.clear();
			mStartPoints.add(mNaviStart);
			mEndPoints.add(mNaviEnd);
			
			
			calculateFootRoute();
		}

		
	    
	    
	    Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			
			if (area==3) {
				
				Intent intent = new Intent();
		           intent.putExtra("dangqian1",mm);    
	               intent.putExtra("dangqian2",nn);
				   intent.setClass(SecondActivity.this,ThirdActivity.class);
				  // intent.setClass(SecondActivity.this,ThirdActivity.class);
				   startActivity(intent);
				   finish();
				   
			}
			          if(area==4) {
			        	 
			        	   Intent intent = new Intent();
				           intent.putExtra("dangqian1",mm);    
			               intent.putExtra("dangqian2",nn);
						   intent.setClass(SecondActivity.this,RoadActivity.class);
						  // intent.setClass(SecondActivity.this,ThirdActivity.class);
						   startActivity(intent);
						   finish();
					}
				}
		});
		
		//��λ��ť�Զ���
		 Button btn2 = (Button)findViewById(R.id.LocationButton);
			btn2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
						
				mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork,  -1, 3, SecondActivity.this);
				
//				showToast("mm="+mm+"  nn="+nn);
				}
			});
	}

    
    private void calculateFootRoute() {
		boolean isSuccess = mAMapNavi.calculateWalkRoute(mNaviStart, mNaviEnd);
		if (!isSuccess) {
			showToast("·�߼���ʧ��,���������");
		}
	}
	private void showToast(String message) {
		ToastUtil.show(SecondActivity.this, message);
	}
    
    
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}



	private void init(Bundle savedInstanceState) {
		markerText = (TextView) findViewById(R.id.mark_listenter_text);
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
		aMap = mapView.getMap();
//		mapView = (MapView) findViewById(R.id.map);
//        mapView.onCreate(savedInstanceState);
//        aMap = mapView.getMap();
        
		//geoFence
        aMap.setOnMapClickListener(this);
		IntentFilter fliter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		fliter.addAction(GEOFENCE_BROADCAST_ACTION);
		registerReceiver(mGeoFenceReceiver, fliter);
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		
		Intent intent = new Intent(GEOFENCE_BROADCAST_ACTION);
		mPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0,
				intent, 0);

		// �˷���Ϊÿ���̶�ʱ��ᷢ��һ�ζ�λ����Ϊ�˼��ٵ������Ļ������������ģ�
		// ע�����ú��ʵĶ�λʱ��ļ������С���֧��Ϊ2000ms���������ں���ʱ�����removeUpdates()������ȡ����λ����
		// �ڶ�λ�������ں��ʵ��������ڵ���destroy()����
		// ����������ʱ��Ϊ-1����λֻ��һ��
		//�ڵ��ζ�λ����£���λ���۳ɹ���񣬶��������removeUpdates()�����Ƴ����󣬶�λsdk�ڲ����Ƴ�



		mLocationManagerProxy.requestLocationData(
				LocationProviderProxy.AMapNetwork		 
				, 2000, 15, this);


		MarkerOptions markOptions = new MarkerOptions();
		markOptions.icon(
				BitmapDescriptorFactory.fromBitmap(BitmapFactory
						.decodeResource(getResources(),
								R.drawable.location_marker)))
				.anchor(0.5f, 0.5f);
		mGPSMarker = aMap.addMarker(markOptions);
		aMap.setOnMapClickListener(this);
		addMarkersToMap();
    }
	
	
	
	
	private BroadcastReceiver mGeoFenceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // ���ܹ㲥
            if (intent.getAction().equals(GEOFENCE_BROADCAST_ACTION)) {
                Bundle bundle = intent.getExtras();
                // ���ݹ㲥��status��ȷ�����������ڻ�����������
                int status = bundle.getInt("status");
//                showToast("���Գɹ�");
                if (status == 0) {
                	LocationFuda=0;
                	showToast("�����ڸ�����");
                } else {
                	LocationFuda=1;
               	showToast("���ѽ��������");
/*                	AlertDialog.Builder dialog=new AlertDialog.Builder(SecondActivity.this);
                	dialog.setTitle("��ʾ��Ϣ");
                	dialog.setMessage("�Ƿ�������Ƶ����");
                	dialog.setCancelable(false);
                	dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent1=new Intent();
							intent1.putExtra("num",store);
							intent1.setClass(SecondActivity.this, MusicActivity.class);
							startActivity(intent1);
						}
					});
                	dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
                	dialog.show();*/
                }
 
            }
//            else{
//            	showToast("���ѽ��뾰��");
//            }
        }
    };

	private void setUpMap() {
        aMap.setLocationSource(this);// ���ö�λ����
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// ����Ĭ�϶�λ��ť�Ƿ���ʾ
        aMap.setMyLocationEnabled(true);// ����Ϊtrue��ʾ��ʾ��λ�㲢�ɴ�����λ��false��ʾ���ض�λ�㲢���ɴ�����λ��Ĭ����false
        aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
        aMap.setOnMapLoadedListener(this);
        aMap.setOnMarkerClickListener(this);
//        aMap.moveCamera(CameraUpdateFactory.zoomTo(20));
        
//        addMarkersToMap();
        //
    	Button searButton = (Button) findViewById(R.id.searchButton);
		searButton.setOnClickListener(this);
		
		searchText = (AutoCompleteTextView) findViewById(R.id.keyWord);
		searchText.addTextChangedListener(this);// ����ı����������¼�
		aMap.setOnMarkerClickListener(this);// ��ӵ��marker�����¼�
		aMap.setInfoWindowAdapter(this);// �����ʾinfowindow�����¼�
    	//
        
    }
	//
	public void searchButton() {
		keyWord = AMapUtil.checkEditText(searchText);
		if ("".equals(keyWord)) {
			ToastUtil.show(SecondActivity.this, "�����������ؼ���");
			return;
		} else {
			doSearchQuery();
		}
	}
	private void showProgressDialog() {
		if (progDialog == null)
			progDialog = new ProgressDialog(this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(false);
		progDialog.setMessage("��������:\n" + keyWord);
		progDialog.show();
	}

	/**
	 * ���ؽ��ȿ�
	 */
	private void dissmissProgressDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}

	/**
	 * ��ʼ����poi����
	 */
	protected void doSearchQuery() {
		showProgressDialog();// ��ʾ���ȿ�

		query = new PoiSearch.Query(keyWord, "", "����");// ��һ��������ʾ�����ַ������ڶ���������ʾpoi�������ͣ�������������ʾpoi�������򣨿��ַ�������ȫ����
		poiSearch = new PoiSearch(this, query);
		poiSearch.setOnPoiSearchListener(this);
		poiSearch.searchPOIAsyn();
	}

	
	

	
	public void startAMapNavi(Marker marker) {
		//���쵼������
		NaviPara naviPara=new NaviPara();
		//�����յ�λ��
		naviPara.setTargetPoint(marker.getPosition());
		//���õ������ԣ������Ǳ���ӵ��
		naviPara.setNaviStyle(AMapUtils.DRIVING_AVOID_CONGESTION);
		try {
			//����ߵµ�ͼ����
			AMapUtils.openAMapNavi(naviPara, getApplicationContext());
		} catch (com.amap.api.maps.AMapException e) {
			  //���û��װ������쳣����������ҳ��
			 AMapUtils.getLatestAMapApp(getApplicationContext());
		}
		
		
 

	}


	/**
	 * ��ȡ��ǰapp��Ӧ������
	 */
	public String getApplicationName() {
		PackageManager packageManager = null;
		ApplicationInfo applicationInfo = null;
		try {
			packageManager = getApplicationContext().getPackageManager();
			applicationInfo = packageManager.getApplicationInfo(
					getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			applicationInfo = null;
		}
		String applicationName = (String) packageManager
				.getApplicationLabel(applicationInfo);
		return applicationName;
	}

	
	

	

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		String newText = s.toString().trim();
		Inputtips inputTips = new Inputtips(SecondActivity.this,
				new InputtipsListener() {

					@Override
					public void onGetInputtips(List<Tip> tipList, int rCode) {
						if (rCode == 0) {// ��ȷ����
							List<String> listString = new ArrayList<String>();
							for (int i = 0; i < tipList.size(); i++) {
								listString.add(tipList.get(i).getName());
							}
							ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
									getApplicationContext(),
									R.layout.route_inputs, listString);
							searchText.setAdapter(aAdapter);
							aAdapter.notifyDataSetChanged();
						}
					}
				});
		try {
			inputTips.requestInputtips(newText,  "����");// ��һ��������ʾ��ʾ�ؼ��֣��ڶ�������Ĭ�ϴ���ȫ����Ҳ����Ϊ��������

		} catch (AMapException e) {
			e.printStackTrace();
		}
	}

	
	

	/**
	 * POI��Ϣ��ѯ�ص�����
	 */
	@Override
	public void onPoiSearched(PoiResult result, int rCode) {
		dissmissProgressDialog();// ���ضԻ���
		if (rCode == 0) {
			if (result != null && result.getQuery() != null) {// ����poi�Ľ��
				if (result.getQuery().equals(query)) {// �Ƿ���ͬһ��
					poiResult = result;
					// ȡ����������poiitems�ж���ҳ
					List<PoiItem> poiItems = poiResult.getPois();// ȡ�õ�һҳ��poiitem���ݣ�ҳ��������0��ʼ
					List<SuggestionCity> suggestionCities = poiResult
							.getSearchSuggestionCitys();// ����������poiitem����ʱ���᷵�غ��������ؼ��ֵĳ�����Ϣ

					if (poiItems != null && poiItems.size() > 0) {
						aMap.clear();// ����֮ǰ��ͼ��
						PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
						poiOverlay.removeFromMap();
						poiOverlay.addToMap();
						poiOverlay.zoomToSpan();
				
					} else {
						ToastUtil.show(SecondActivity.this,
								R.string.no_result);
					}
				}
			} else {
				ToastUtil.show(SecondActivity.this,
						R.string.no_result);
			}
		} else if (rCode == 27) {
			ToastUtil.show(SecondActivity.this,
					R.string.error_network);
		} else if (rCode == 32) {
			ToastUtil.show(SecondActivity.this, R.string.error_key);
		} else {
			ToastUtil.show(SecondActivity.this, getString(R.string.error_other) + rCode);
		}

	}

	/**
	 * Button����¼��ص�����
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		/**
		 * ���������ť
		 */
		case R.id.searchButton:
			searchButton();
			break;
		
		
		default:
			break;
		}
	}
	//
	
	private void addMarkersToMap() {
//   	 	LatLng latLng = new LatLng(26.05124,119.19283);//����֮��
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);	
//		CircleOptions circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 100))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
//		
//		latLng = new LatLng(26.050908,119.19187);
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);
//		circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
//		
//		latLng = new LatLng(26.050932,119.191784);
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);
//		circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
//		
//		latLng = new LatLng(26.05182,119.191677);
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);
//		circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
//		
//		latLng = new LatLng(26.052002,119.191704);
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);
//		circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
//		
//		latLng = new LatLng(26.052103,119.191725);
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);
//		circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
//		
//		latLng = new LatLng(26.052171,119.191816);
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);
//		circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
//				
//		latLng = new LatLng(26.052171,119.191913);
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);
//		circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
//		
//		latLng = new LatLng(26.052195,119.192122);
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);
//		circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
//		
//		latLng = new LatLng(26.051906,119.191682);
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);
//		circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
//		
//		latLng = new LatLng(26.051067,119.192433);
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);
//		circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
//		
//		latLng = new LatLng(26.051241,119.191629);
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);
//		circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
		
						/*����B��*/
   	 	
		LatLng latLng = new LatLng(26.051125,119.192069);
		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
				latLng.longitude, 170, 1000 * 60 * 30, mPendingIntent);
		CircleOptions circleOptions = new CircleOptions();
		circleOptions.center(latLng).radius(170)
				.fillColor(Color.argb(180, 224, 171, 10))
				.strokeColor(Color.RED);
		mCircle = aMap.addCircle(circleOptions);//��������
		Nameclass nc=new Nameclass();
		
		MarkerOptions option = new MarkerOptions();

		//���������
		addMarker(nc.����֮��,"����֮��","2.png",0.1f,0.5f);//
		addMarker(nc.ǧ������,"ǧ������","qianyi.png",0.5f,0.1f);//
		addMarker(nc.��߲��԰,"��߲��԰","qingbu.png",0.5f,0.1f);//
		addMarker(nc.�����껪,"�����껪","huayang.png",0.1f,0.5f);//
		addMarker(nc.Բͨ���,"Բͨ���","yuantong.png",0.1f,0.5f);//
		addMarker(nc.�����У,"�����У","yixiang.png",0.1f,0.5f);//
		addMarker(nc.֪����,"֪����","zhifazhe.png",0.5f,0.1f);//
		addMarker(nc.С������,"С������","xiaochun.png",0.5f,0.1f);//
		addMarker(nc.�������ɲ�,"�������ɲ�","fubo.png",0.5f,0.1f);//
		addMarker(nc.�й��ƶ�,"�й��ƶ�","1.png",0.5f,0.5f);//
		addMarker(nc.��γ���,"��γ���","1.png",0.5f,0.5f);//
		addMarker(nc.����ϴ�·�,"����ϴ�·�","1.png",0.5f,0.5f);//
		addMarker(nc.���������,"���������","1.png",0.5f,0.5f);//
		addMarker(nc.����۾�,"����۾�","1.png",0.5f,0.5f);//
		addMarker(nc.����,"����","1.png",0.5f,0.5f);//
		addMarker(nc.����������,"����������","1.png",0.5f,0.5f);//
		addMarker(nc.�������,"�������","1.png",0.5f,0.5f);//
		addMarker(nc.���ٳ���,"���ٳ���","1.png",0.5f,0.5f);//
		addMarker(nc.Բͨ�ٵ�,"Բͨ�ٵ�","yuantongsu.png",0.9f,0.5f);//
		addMarker(nc.����������,"����������","erhua.png",0.9f,0.5f);//
		addMarker(nc.������װ,"������װ","xizi.png",0.9f,0.5f);//
		addMarker(nc.������,"������","1.png",0.5f,0.5f);//
		addMarker(nc.�������,"�������","jindong.png",0.9f,0.5f);//
		addMarker(nc.����,"����","meige.png",0.9f,0.5f);//
		addMarker(nc.�ϴ���,"�ϴ���","yunda.png",0.9f,0.5f);//
		addMarker(nc.��γ���4,"��γ��У�4����","wj4.png",0.9f,0.5f);//
		addMarker(nc.ŵ��������װ,"ŵ��������װ","nuoman.png",0.9f,0.5f);//
		addMarker(nc.˳����,"˳����","shunfeng.png",0.9f,0.5f);//
		}
	public void addMarker(LatLng a,String title,String picture,float s1,float s2){
		aMap.addMarker(new MarkerOptions().anchor(s1, s2).icon(BitmapDescriptorFactory.fromAsset(picture))
				.position(a).title(title).setFlat(true)
				.draggable(true));
	}
	public void onMapLoaded() {
		// TODO Auto-generated method stub
		LatLng northeast = new LatLng(30,130 );
		LatLng southwest = new LatLng(20, 100);
		LatLngBounds bounds = new LatLngBounds.Builder().include(northeast).include(mar1)
		                                .include(southwest).build();
	}
	public boolean onMarkerClick(final Marker marker) {
		if(marker.getTitle().equals("����֮��"))
		markerText.setText("������" + marker.getTitle()+"��ӪһЩ�����йصĲ�Ʒ��������꣬�Լ�ά�޵��ԡ�");
		if(marker.getTitle().equals("ǧ������"))
			markerText.setText("ǧ������������������ʵ�ݡ�");
		if(marker.getTitle().equals("��߲��԰"))
			markerText.setText("��߲��԰��С�;ۻ�ĺ�ȥ�����ṩ����������ϷŶ");
		if(marker.getTitle().equals("�����껪"))
			markerText.setText("��ʲô�����ţ�");
		if(marker.getTitle().equals("Բͨ��ݵ�"))
			markerText.setText("���⿩��Բͨ��ݵ㡣");
		if(marker.getTitle().equals("�����У"))
			markerText.setText("�����ܱߵ�һ�Ҽ�У�ı����㡣");
		if(marker.getTitle().equals("֪����"))
			markerText.setText("��˵�е�ӵ����ꡣ");
		if(marker.getTitle().equals("С������"))
			markerText.setText("һ����Ҫ���۸���С����ɫ���Ե�С�ꡣ");
		if(marker.getTitle().equals("�й��ƶ�"))
			markerText.setText("����3�����ƶ��㡣");
		if(marker.getTitle().equals("��γ���"))
			markerText.setText("����3��������С�");
		if(marker.getTitle().equals("����ϴ�·�"))
			markerText.setText("����3����ϴ�·���ϴ���æɹ�ɡ�");
		if(marker.getTitle().equals("���������"))
			markerText.setText("����3���ܱ�һ�ұȽϺõ�����ꡣ");
		if(marker.getTitle().equals("����۾�"))
			markerText.setText("һ���۾��ꡣ");
		if(marker.getTitle().equals("����"))
			markerText.setText("һ����Ҫ�Գ��۵綯���ĵ��̣�Ҳ�ṩ�綯����ά�ޡ�");
		if(marker.getTitle().equals("����������"))
			markerText.setText("һ���ľߵꡣ");
		if(marker.getTitle().equals("�������"))
			markerText.setText("һ���·���:-)��");
		if(marker.getTitle().equals("�ϴ���"))
			markerText.setText("�ϴ�Ŀ�ݵ㿩��");
		if(marker.getTitle().equals("���ٳ���"))
			markerText.setText("һ�������꣬��������߲��԰��");
		if(marker.getTitle().equals("����"))
			markerText.setText("����˵��˵�ұ�ʲô�ĳ�檹�Ȼ�����������ġ�");
		if(marker.getTitle().equals("�������"))
			markerText.setText("������ݵ㣬���⿩��");
		if(marker.getTitle().equals("������"))
			markerText.setText("��..��...��(wo)��(bu)��(zhi)��(dao)��");
		if(marker.getTitle().equals("������װ"))
			markerText.setText("ûȥ���������Ծ�д�������� - - ");
		if(marker.getTitle().equals("����������"))
			markerText.setText("���������ң��Ƕ��ƺ����������Ƭ������");
		if(marker.getTitle().equals("Բͨ�ٵ�"))
			markerText.setText("Բͨ�Ŀ�ݵ㿩��");
		if(marker.getTitle().equals("˳����"))
			markerText.setText("˳��Ŀ�ݵ㿩��");
		if(marker.getTitle().equals("��γ��У�4����"))
			markerText.setText("��γ��е�4���ꡣ");
		if(marker.getTitle().equals("ŵ��������װ"))
			markerText.setText("ŵ�����Ρ�");
		return false;
	}
	private void updateLocation(double latitude, double longtitude) {
        if (mGPSMarker != null) {
            mGPSMarker.setPosition(new LatLng(latitude, longtitude));
        }
 
    }

    
     
    /**
     * ��λ�ɹ���ص�����
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation.getAMapException().getErrorCode() == 0) {
            	updateLocation(amapLocation.getLatitude(), amapLocation.getLongitude());
            	 LatLng latLng = new LatLng(amapLocation.getLatitude(),amapLocation.getLongitude());
            	 mm=amapLocation.getLatitude();
            	 nn=amapLocation.getLongitude();
            	 instant_lat=amapLocation.getLatitude();
            	 instant_lng=amapLocation.getLongitude();
            	 
            	 LatLng Fudathree=new LatLng(26.050908,119.19187);
            	 LatLng Fudafour=new LatLng(26.050677,119.190894);
//     			addMarker(26.050908,119.19187,"ǧ������","1.png");//
//            	 ����  119.190894,26.050677
            	 
            	 if(LocationFuda==0)
            	 {
            		 mListener.onLocationChanged(amapLocation);// ��ʾϵͳС����
            		 if(area==3)
            		 {
            		 aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Fudathree,18));
            		 }
            		 if(area==4)
            		 {
            		 aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Fudafour,18));
            		 }

            		 
            		 showToast("����뾰���ٽ��ж�λ");
            		
            	 }
            	 
            	 else{

            		 mListener.onLocationChanged(amapLocation);// ��ʾϵͳС����
                 // ��λ�ɹ���ѵ�ͼ�ƶ�����ǰ����������
            	 aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
            	 showToast("������Ŀ�꾰��");
            	 }
            }
        }
    }
    /**
     * ���λ
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mLocationManagerProxy == null) {
        	mLocationManagerProxy = LocationManagerProxy.getInstance(this);
            //�˷���Ϊÿ���̶�ʱ��ᷢ��һ�ζ�λ����Ϊ�˼��ٵ������Ļ������������ģ�
            //ע�����ú��ʵĶ�λʱ��ļ���������ں���ʱ�����removeUpdates()������ȡ����λ����
            //�ڶ�λ�������ں��ʵ��������ڵ���destroy()����     
            //����������ʱ��Ϊ-1����λֻ��һ��
        	mLocationManagerProxy.requestLocationData(
                    LocationProviderProxy.AMapNetwork, -1, 10, this);
        }
    }
    /**
     * ֹͣ��λ
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationManagerProxy != null) {
        	mLocationManagerProxy.removeUpdates(this);
        	mLocationManagerProxy.destroy();
        }
        mLocationManagerProxy = null;
    }
	
	
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mLocationManagerProxy.removeGeoFenceAlert(mPendingIntent);
		mLocationManagerProxy.removeUpdates(this);
		mLocationManagerProxy.destroy();
		unregisterReceiver(mGeoFenceReceiver);
        mapView.onPause();
        deactivate();
    }
    
	protected void onStart() {
		super.onStart();
	}
	
	protected void onStop() {
		super.onStop();
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        // ���ٶ�λ
       

        AMapNavi.getInstance(this).removeAMapNaviListener(this);

    }
	
	
	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}




	
	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}


	

	@Override
	public View getInfoContents(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public View getInfoWindow(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void onPoiItemDetailSearched(PoiItemDetail arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onArriveDestination() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onArrivedWayPoint(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onCalculateRouteFailure(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onCalculateRouteSuccess() {
		
		AMapNaviPath naviPath = mAMapNavi.getNaviPath();
		if (naviPath == null) {
			return;
		}
		// ��ȡ·���滮��·����ʾ����ͼ��
		mRouteOverLay.setRouteInfo(naviPath);
		mRouteOverLay.addToMap();
	}


	@Override
	public void onEndEmulatorNavi() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onGetNavigationText(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onGpsOpenStatus(boolean arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onInitNaviFailure() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onInitNaviSuccess() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onLocationChange(AMapNaviLocation arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onNaviInfoUpdate(NaviInfo arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onNaviInfoUpdated(AMapNaviInfo arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onReCalculateRouteForTrafficJam() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onReCalculateRouteForYaw() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStartNavi(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTrafficStatusUpdate() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onMapClick(LatLng latLng) {
//		mLocationManagerProxy.removeGeoFenceAlert(mPendingIntent);
//		if (mCircle != null) {
//			mCircle.remove();
//		}
//		//����Χ��ʹ��ʱ��Ҫ�붨λ���󷽷����ʹ��
//		// ���õ���Χ����λ�á��뾶����ʱʱ�䡢�����¼�
//		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
//				latLng.longitude, 1000,
//				 1000 * 60 * 30
//				 , mPendingIntent);
//		// ������Χ����ӵ���ͼ����ʾ
//		CircleOptions circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(1000)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);

	}
	

	
	
}


	



