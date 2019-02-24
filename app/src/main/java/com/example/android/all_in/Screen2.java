package com.example.android.all_in;

import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android.all_in.BroadcastReceiver.Receiver;
import com.example.android.all_in.NotificationUtil.Notification;
import com.example.android.all_in.Service.ScheduleService;

public class Screen2 extends AppCompatActivity {

    Button notification, taskScheduler;
    Receiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        notification = findViewById(R.id.screen2_notification);
        taskScheduler = findViewById(R.id.screen2_jobScheduler);


        // Broadcast Receiver - Notification
        receiver = new Receiver();
        registerReceiver(receiver, new IntentFilter(com.example.android.all_in.NotificationUtil.Notification.ACTION_CANCEL_NOTIFICATION));
        registerReceiver(receiver, new IntentFilter(com.example.android.all_in.NotificationUtil.Notification.ACTION_UPDATE_NOTIFICATION));

    }// end of onCreate()

    /**
     * this method is called when notification button is clicked
     *
     * @param view
     */
    public void showNotification(View view) {
        Notification.notifyUser(this);
        final android.support.design.widget.Snackbar snackbar = android.support.design.widget.Snackbar.make(view, "This is example of Snackbar",
                android.support.design.widget.Snackbar.LENGTH_LONG);
        snackbar.show();
        snackbar.setAction("dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
                Intent intent = new Intent(Screen2.this, Screen3.class);
                startActivity(intent);
            }
        });
    }

    /**
     * this method is called when taskscheduler button is clicked
     *
     * @param view
     */
    public void setTaskScheduler(View view) {
        ComponentName componentName = new ComponentName(this, ScheduleService.class);
        JobInfo jobInfo = new JobInfo.Builder(123, componentName)
                .setPeriodic(15 * 60 * 1000)
                .setPersisted(true)
                .build();
        android.app.job.JobScheduler scheduler = (android.app.job.JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.schedule(jobInfo);
    }
}
