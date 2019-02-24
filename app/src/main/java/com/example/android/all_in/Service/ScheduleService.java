package com.example.android.all_in.Service;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.example.android.all_in.NotificationUtil.Notification;

public class ScheduleService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Notification.notifyUser(ScheduleService.this);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
