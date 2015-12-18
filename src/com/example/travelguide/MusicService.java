package com.example.travelguide;

import android.content.Context;
import android.R.integer;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.util.Log;
import android.content.ComponentName;
import android.content.ServiceConnection;


public class MusicService extends Service {

	public int flag=0;
	IBinder musicBinder  = new MyBinder();
	protected MediaPlayer mediaPlayer;
	
	//��ȡ��activity��Handler������֪ͨ���½�����
	Handler mHandler; 
	
	//�������ֵ�ý����
//	MediaPlayer mediaPlayer;
	
	//���ظ�����·��
	
	
    String path="/res/raw/music.mp3";
//    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.music);
    
    
    private String TAG = "MyService";
	
	@Override  	
    public void onCreate() {  
        super.onCreate();  
        Log.d(TAG, "onCreate() executed");  
        
        
//        init();
//        mediaPlayer = new MediaPlayer();
		try {
			//��ʼ��
//			mediaPlayer.setDataSource(path);//ɾ��
			
			
			mediaPlayer=MediaPlayer.create(getApplicationContext(), R.raw.music);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);  
			
			// prepare ͨ���첽�ķ�ʽװ��ý����Դ
			mediaPlayer.prepareAsync();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }  


	public void setId(int Id){

		if(Id==1) mediaPlayer=MediaPlayer.create(getApplicationContext(), R.raw.music);
//		else if(Id==2) mediaPlayer=MediaPlayer.create(getApplicationContext(), R.raw.music2);

		
			
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
	    //���󶨺󣬷���һ��musicBinder
//		Bundle bundle = intent.getExtras(); 
//	    int numVal = bundle.getInt("testnum2");  //���ڽ���int��������
//	    if(numVal==1) mediaPlayer=MediaPlayer.create(getApplicationContext(), R.raw.music);
		return musicBinder;
	}
	
	class MyBinder extends Binder{
		
		public Service getService(){
			return MusicService.this;
		}
//		public boolean onTransact (int code,Parcel data, Parcel reply,int flags){
//			return handleTransactions(code,data,reply,flags);
//		}
	}
	
	//��ʼ�����ֲ���
//	void init(){
//		//����Idle
//		mediaPlayer = new MediaPlayer();
//		try {
//			//��ʼ��
////			mediaPlayer.setDataSource(path);
//			mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.music);
//			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);  
//			
//			// prepare ͨ���첽�ķ�ʽװ��ý����Դ
//			mediaPlayer.prepareAsync();
//		
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	//���ص�ǰ�Ĳ��Ž��ȣ���double���ͣ������ŵİٷֱ�
	public double getProgress(){
		int position = mediaPlayer.getCurrentPosition();  
        
        int time = mediaPlayer.getDuration();  
        
        double progress = (double)position / (double)time;
        
        return progress;  
	}
	
	//ͨ��activity���ڲ��Ž���
	public void setProgress(int max , int dest){	
		int time = mediaPlayer.getDuration();
		mediaPlayer.seekTo(time*dest/max);
	}
	
	//���Բ�������
	public void play(){		
		if(mediaPlayer != null){
			mediaPlayer.start();
		}
			
	}
	//��ͣ����   
    public void pause() {  
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {  
            mediaPlayer.pause();  
        }  
    }  
    
  //service ����ʱ��ֹͣ�������֣��ͷ���Դ
  	@Override
  	public void onDestroy() {
	     // ��activity������ʱ�������Դ
	     if (mediaPlayer != null && mediaPlayer.isPlaying()) {
	         mediaPlayer.stop();
	         mediaPlayer.release();
	         mediaPlayer = null;
	     }
	     super.onDestroy();
     }
}