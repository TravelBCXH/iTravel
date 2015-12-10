package com.example.travelguide;

import com.example.travelguide.R;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;


public class MusicService extends Service {

	
	IBinder musicBinder  = new MyBinder();
	protected MediaPlayer mediaPlayer;
	
	//��ȡ��activity��Handler������֪ͨ���½�����
	Handler mHandler; 
	
	//�������ֵ�ý����
//	MediaPlayer mediaPlayer;
	
	//���ظ�����·��
    String path ="/res/raw/music.mp3";
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
//			mediaPlayer.setDataSource(path);
			mediaPlayer=MediaPlayer.create(getApplicationContext(), R.raw.music);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);  
			
			// prepare ͨ���첽�ķ�ʽװ��ý����Դ
			mediaPlayer.prepareAsync();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }  
  
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
	    //���󶨺󣬷���һ��musicBinder
		return musicBinder;
	}
	
	class MyBinder extends Binder{
		
		public Service getService(){
			return MusicService.this;
		}
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