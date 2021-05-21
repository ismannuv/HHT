package com.example.hht;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

abstract public class ScreenController  extends AppCompatActivity {

    protected int stateTimeout;
    protected boolean useWorkerThread;
    protected boolean useTimerThread;
    protected boolean exitThread;
    private Thread workerThread;
    private Timer timer;
    protected int threadSleepTime;

    public void setThread(boolean timerThread,boolean workerThread,int sleepTime) {
        this.useTimerThread=timerThread;
        this.useWorkerThread=workerThread;
        this.threadSleepTime=sleepTime;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initialize(savedInstanceState);
        stateTimeout=0;
    }
    @Override
    public void onStart()
    {
        super.onStart();
        beforeEntry();
    }
    @Override
    public void onStop()
    {
        super.onStop();
        Toast.makeText(this, "in on stop", Toast.LENGTH_SHORT).show();
        Log.d("onStop","in on stop");
        afterExit();
    }
    private final void beforeEntry()
    {
        if(useTimerThread)
        {
            timer=new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                  @Override
                  public void run() {
                      every1sec();
                  }
              },
            0, 1000);
        }
        if(this.useWorkerThread)
        {
            this.exitThread=false;
            this.workerThread=new Thread(new Runnable() {
                @Override
                public void run() {
                    while(!exitThread)
                    {
                        try {
                            workerThreadTask();
                            if(threadSleepTime<10)
                            {
                                threadSleepTime=10;
                            }
                            Thread.sleep(threadSleepTime);
                        } catch (Exception ex) {
                          //  Config.logger.warning(ex.toString());
                        }
                    }
                }
            });
            this.workerThread.start();
        }
    }
    private final void afterExit()
    {
        this.exitThread=true;
        this.timer.cancel();
        this.timer=null;
        this.workerThread=null;
    }
    private void workerThreadTask(){
        threadSleepTime=stateMachine();
    }
    abstract public void every1sec();
    abstract public void initialize(Bundle savedInstanceState);
    protected int stateMachine(){
        return 10;
    }
    public enum STATES{UNKNOWN};
    protected STATES state=STATES.UNKNOWN,prevState=STATES.UNKNOWN;
}
