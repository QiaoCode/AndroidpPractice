package com.example.timer;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static String TAG="TimerDemo";
    
    private TextView mTextView=null;
    private Button mButton_start=null;
    private Button mButton_pause=null;
    
    private Timer mTimer=null;
    private TimerTask mTimerTask=null;
    
    private Handler mHandler=null;
    
    private static int count=0;
    private boolean isPause=false;
    private boolean isStop=true;
    
    private static int delay=1000;//1s
    private static int period=1000;//1s
    
    private static final int UPDATE_TEXTVIEW=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mTextView=(TextView)findViewById(R.id.mytextview);
        mButton_start=(Button)findViewById(R.id.mybutton_start);
        mButton_pause=(Button)findViewById(R.id.mybutton_pause);
        
        mButton_start.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isStop){
					Log.i(TAG,"start");
				}else{
					Log.i(TAG,"stop");
				}
				
				isStop=!isStop;
				
				if(!isStop){
					startTimer();
				}else{
					stopTimer();
				}
				
				if(isStop){
					mButton_start.setText(R.string.start);
				}else{
					mButton_start.setText(R.string.stop);
				}
			}
        	
        });
        
        mButton_pause.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isPause){
					Log.i(TAG,"Resume");
				}else{
					Log.i(TAG, "Pause");
				}
				
				isPause=!isPause;
				
				if(isPause){
					mButton_pause.setText(R.string.resume);
				}else{
					mButton_pause.setText(R.string.pause);
				}
			}
        });
        
        mHandler=new Handler(){
        	public void handleMessage(Message msg){
        		switch (msg.what){
        		case UPDATE_TEXTVIEW:
        		    updateTextView();
        		    break;
        		default:
        			break;
        		}
        	}
        };
        
    }

    private void updateTextView(){  
        mTextView.setText(String.valueOf(count));  
    }  
  
    private void startTimer(){  
        if (mTimer == null) {  
            mTimer = new Timer();  
        }  
  
        if (mTimerTask == null) {  
            mTimerTask = new TimerTask() {  
                @Override  
                public void run() {  
                    Log.i(TAG, "count: "+String.valueOf(count));  
                    sendMessage(UPDATE_TEXTVIEW);  
                      
                    do {  
                        try {  
                            Log.i(TAG, "sleep(1000)...");  
                            Thread.sleep(1000);  
                        } catch (InterruptedException e) {  
                        }     
                    } while (isPause);  
                      
                    count ++;    
                }  
            };  
        }  
  
        if(mTimer != null && mTimerTask != null )  
            mTimer.schedule(mTimerTask, delay, period);  
  
    }  
  
    private void stopTimer(){  
          
        if (mTimer != null) {  
            mTimer.cancel();  
            mTimer = null;  
        }  
  
        if (mTimerTask != null) {  
            mTimerTask.cancel();  
            mTimerTask = null;  
        }     
  
        count = 0;  
  
    }  
      
    public void sendMessage(int id){  
        if (mHandler != null) {  
            Message message = Message.obtain(mHandler, id);     
            mHandler.sendMessage(message);   
        }  
    }  

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
