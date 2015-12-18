package com.example.travelguide;

import com.example.travelguide.MusicService.MyBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.travelguide.until.ToastUtil;

import android.widget.Toast;



public class MusicActivity extends Activity {

	Boolean mBound = false;
	
	MusicService mService;
	
	SeekBar seekBar;
	
	//���̣߳���̨����UI
	Thread myThread;
	
	//���ƺ�̨�߳��˳�
	boolean playStatus = true;
	
	
	//�������������
	Handler mHandler = new Handler(){
		@Override  
        public void handleMessage(Message msg){  
			switch (msg.what){
				case 0:
					//��bundle�л�ȡ���ȣ���double���ͣ����ŵİٷֱ�
					double progress = msg.getData().getDouble("progress");
					
					//���ݲ��Űٷֱȣ�����seekbar��ʵ��λ�� 
		            int max = seekBar.getMax();  
		            int position = (int) (max*progress);
		            
		            //����seekbar��ʵ��λ��
		            seekBar.setProgress(position);  
		            break;
		        default:
		        	break;
			}
            
        }  
	};
	private void showToast(String message) {
		ToastUtil.show(MusicActivity.this, message);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music);
		
		//����һ�����̣߳�����������Ϣ��֪ͨ����UI
		myThread = new Thread(new MyThread());
		
		
		//��service;
		Intent serviceIntent = new Intent(this , MusicService.class);
		
		//���δ�󶨣�����а�
		if(!mBound){
			bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
		}	
		
		Intent intent_audio_num = getIntent();
		Bundle bundle_audio_num = intent_audio_num.getExtras();  
		int audio_num=bundle_audio_num.getInt("num");
		if(audio_num==1){
			Intent intentTest=new Intent();
			intentTest.putExtra("num", 1);
		}

//		Intent service_num=new Intent();
//		

//		for(int i=1;i<26;i++){
//			if(audio_num==i){
//				Bundle mbundle = new Bundle();
//				mbundle.putInt("musicNum", i);
//				serviceIntent.putExtras(mbundle);
//			}

//		}	
//		}		

		//��ʼ�����Ű�ť
		Button playButton = (Button)findViewById(R.id.playButton);
		playButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub	
				if(mBound){
					mService.setId(2);
					mService.play();
				}			
			}
			
		});
		
		//��ʼ����ͣ��ť
		Button pauseButton = (Button)findViewById(R.id.pauseButton);	
		pauseButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//������Ҫ�ж������
				if(mBound){
					mService.pause();
				}
			}
		});
		
	    seekBar = (SeekBar)findViewById(R.id.seekbar);
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			//�ֶ����ڽ���
				// TODO Auto-generated method stub
				//seekbar���϶�λ��
				int dest = seekBar.getProgress();
				//seekbar�����ֵ
				int max = seekBar.getMax();
				//����service���ڲ��Ž���
				mService.setProgress(max, dest);
			}

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});		
				
	}
	
	//ʵ��runnable�ӿڣ����߳�ʵʱ���½�����
	public class MyThread implements Runnable{
		
	
		//֪ͨUI���µ���Ϣ
		
		
		//������UI�̴߳��ݽ��ȵ�ֵ
		Bundle data = new Bundle();

		//����UI���ʱ��
		int milliseconds = 100;
		double progress;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			//������	ʶ�Ƿ��ڲ���״̬�����������߳��˳�
			while(playStatus){  
				
                try {  
                	//�󶨳ɹ����ܿ�ʼ����UI
                    if(mBound){
                    	
                    	//������Ϣ��Ҫ�����UI
                    	
                    	Message msg = new Message();
                    	data.clear();
             	
                    	progress = mService.getProgress();
            			msg.what = 0;
            			
            			data.putDouble("progress", progress);
            			msg.setData(data);
            			mHandler.sendMessage(msg);
                    }
                    Thread.sleep(milliseconds);  
        			//Thread.currentThread().sleep(milliseconds);  
					//ÿ��100ms����һ��UI
        			
                } catch (InterruptedException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
			
			}
		}
		
	}
	
	 /** Defines callbacks for service binding, passed to bindService() */  
    private ServiceConnection mConnection = new ServiceConnection() {  
  
        @Override  
        public void onServiceConnected(ComponentName className,  
                IBinder binder) {  
            // We've bound to LocalService, cast the IBinder and get LocalService instance  
        	MyBinder myBinder = (MyBinder) binder;
            
        	//��ȡservice
        	mService = (MusicService) myBinder.getService();  
            
            //�󶨳ɹ�
            mBound = true;  
            
            //�����̣߳�����UI
            myThread.start();
        }  
  
        @Override  
        public void onServiceDisconnected(ComponentName arg0) {  
            mBound = false;  
        }  
    };  
	   
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onDestroy(){
		//����activityʱ��Ҫ�ǵ������߳�
		playStatus = false;
		super.onDestroy();
	}

}