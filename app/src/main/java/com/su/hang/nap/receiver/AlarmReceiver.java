package com.su.hang.nap.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.su.hang.nap.business.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);

        Toast.makeText(context,".onReceive.........",Toast.LENGTH_LONG).show();
    }
}
