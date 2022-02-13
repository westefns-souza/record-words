package com.westefns.recordswords.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.westefns.recordswords.R;
import com.westefns.recordswords.dao.RecordWordDao;


public class AlertService extends Service {
    private RecordWordDao recordWordDao = new RecordWordDao(getApplicationContext());

    private IBinder mBinder = new MyBinder();
    //private Handler mHandler;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean haveWordToday = recordWordDao.getHaveWordToday();

        if(!haveWordToday) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "teste")
                    .setContentTitle("Lembrete")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText("Lembre-se de anotar uma nova palavra hoje")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
            notificationManagerCompat.notify(1, builder.build());
        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder {
        AlertService getService() {
            return AlertService.this;
        }
    }
}

