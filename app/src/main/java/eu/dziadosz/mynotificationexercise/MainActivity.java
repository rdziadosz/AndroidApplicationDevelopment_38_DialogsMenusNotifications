package eu.dziadosz.mynotificationexercise;

import android.app.DialogFragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements HelloDialogFragment.HelloDialogListener,PopupMenu.OnMenuItemClickListener {

    public int notificationId = 1;
    public static final String KEY_TEXT_REPLY = "key_text_reply";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_quit:
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    // this method is called from Activity Layout (Button)
    // open example project and look activity_main.xml
    public void openPopUp(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_a:
                Toast.makeText(getApplicationContext(), "A", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_b:
                Toast.makeText(getApplicationContext(), "B", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_c:
                Toast.makeText(getApplicationContext(), "C", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_d:
                Toast.makeText(getApplicationContext(), "D", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    public void launchNotification1(View viev) {
        //create implicit intent
        Intent actionIntent = new Intent(Intent.ACTION_VIEW);
        actionIntent.setData(Uri.parse("http://jamk.fi"));

        //create pending intent
        PendingIntent actionPendingIntent = PendingIntent.getActivity(this, 0, actionIntent, 0);

        //create notification
        Notification notification = new Notification.Builder(this)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentTitle("Open JAMK website")
                .setContentText("http://jamk.fi")
                .setSmallIcon(R.drawable.ic_bug)
                .setAutoCancel(true)
                .setContentIntent(actionPendingIntent)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .build();
        //connect notification manager
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        //make a notifications
        notificationManager.notify(notificationId, notification);
    }


    public void launchNotification2(View viev) {

        Intent actionIntent = new Intent(this, NotificationReciever.class);

        PendingIntent actionPendingIntent = PendingIntent.getActivity(this, 0, actionIntent, 0);

        String replyLabel = getResources().getString(R.string.reply_label);
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(replyLabel)
                .build();


        Notification.Action action =
                new Notification.Action.Builder(R.drawable.ic_bug,
                        getString(R.string.label), actionPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();


        Notification newMessageNotification =
                new Notification.Builder(this)
                        .setSmallIcon(R.drawable.ic_bug)
                        .setContentTitle(getString(R.string.title))
                        .setContentText(getString(R.string.content))
                        .setAutoCancel(true)
                        .addAction(action)
                        .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, newMessageNotification);
    }

    public void launchCustomDialog(View view) {
        HelloDialogFragment eDialog = new HelloDialogFragment();
        eDialog.show(getFragmentManager(), "Hello!");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String username) {
        Toast.makeText(getApplicationContext(), "Hello " + username+"!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        //do nothing
    }

    public void launchExitDialog(View view) {
        ExitDialogFragment eDialog = new ExitDialogFragment();
        eDialog.show(getFragmentManager(), "exit");
    }

}
