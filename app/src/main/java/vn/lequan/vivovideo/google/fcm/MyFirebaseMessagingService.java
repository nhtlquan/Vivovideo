package vn.lequan.vivovideo.google.fcm;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.libsmilestudio.utils.AndroidUtils;
import com.smile.studio.network.model.API;

import java.util.Map;

import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.activity.SplashActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            if (remoteMessage.getData().size() > 0) {
                Map<String, String> maps = remoteMessage.getData();
                Debug.e("Message Notification data payload: " + maps);
                FCMNotificationOption option = new FCMNotificationOption();
                for (Map.Entry<String, String> m : maps.entrySet()) {
                    if (m.getKey().equals("action")) {
                        option.setAction(m.getValue());
                    } else if (m.getKey().equals("title")) {
                        option.setTitle(m.getValue());
                    } else if (m.getKey().equals("packagename")) {
                        option.setPackagename(m.getValue());
                    } else if (m.getKey().equals("message")) {
                        option.setMessage(m.getValue());
                    } else if (m.getKey().equals("id")) {
                        try {
                            option.setID(Integer.parseInt(m.getValue()));
                        } catch (Exception e) {
                            option.setID(0);
                        }
                    }
                }
                sendNotificationOptions(option);
            } else if (remoteMessage.getNotification() != null) {
                String response = remoteMessage.getNotification().getBody();
                Debug.e("Message Notification Body: " + response);
                sendNotification(response);
            }
        } catch (Exception e) {
            Debug.e("Không phải FCM");
        }
    }

    /**
     * Create and showInterstitialAd a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        Debug.e("Notif Không chui vào đây");
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(messageBody)
                .setAutoCancel(false)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = notificationBuilder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify((int) System.currentTimeMillis(), notification);
    }

    private void sendNotificationOptions(FCMNotificationOption data) {
        data.trace();
        Intent intent = null;
        PendingIntent pendingIntent = null;
        if (data.getAction().equals("install") || data.getAction().equals("update") || data.getAction().equals("rate")) {
            Intent install_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(AndroidUtils.gotoMaketStore(data.getPackagename())));
            pendingIntent = PendingIntent.getActivity(this, 0, install_intent, PendingIntent.FLAG_ONE_SHOT);
        } else if (data.getAction().equals("play")) {
            data.trace();
            intent = new Intent(this, SplashActivity.class);
//            intent.putExtra(API.ID, data.getID());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        }
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(data.getTitle())
                .setContentText(data.getMessage())
                .setAutoCancel(false)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = notificationBuilder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify((int) System.currentTimeMillis(), notification);
    }

}