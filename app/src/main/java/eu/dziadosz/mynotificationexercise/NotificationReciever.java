package eu.dziadosz.mynotificationexercise;

import android.app.Notification;
import android.app.NotificationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.app.Activity;
import android.app.RemoteInput;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class NotificationReciever extends Activity {

    public int notificationId=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(this,getMessageText(getIntent()),Toast.LENGTH_SHORT).show();

        Notification repliedNotification =
                new Notification.Builder(this)
                        .setSmallIcon(R.drawable.ic_bug)
                        .setContentText(getString(R.string.success))
                        .build();

// Issue the new notification.
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, repliedNotification);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.google.com/search?btnG=1&pws=0&q="+getMessageText(getIntent())));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "No activity to handle intent.", Toast.LENGTH_LONG).show();
        }
    }

    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(MainActivity.KEY_TEXT_REPLY);
        }
        Toast.makeText(this,"Remoteinput is null",Toast.LENGTH_SHORT).show();

        return null;
    }
}