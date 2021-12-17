package it.mirea.fourpr;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION_READ_CONTACTS = 1;
    private Button buttonNotify;
    private NotificationManager nm;
    public static final String CAHNNEL_ID = "default_channel_id";
    private final int Notification_Id = 1; // Уникальный id



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonNotify = findViewById(R.id.buttonNotify);
        nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        actionBar();

        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            readContacts();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_CONTACTS},
                    REQUEST_CODE_PERMISSION_READ_CONTACTS);
        }
    }

    private void readContacts() {
    }

    public void makeText(View view) {
        buttonNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // Увидеть текст
                show();
                Toast.makeText(getApplicationContext(), "Уведомление отправилось", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void actionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public void show() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), CAHNNEL_ID)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_launcher_background) // Иконка уведомления
                        .setTicker("+Новое уведомление \uD83E\uDD7A")
                        .setContentTitle("ИКБО-27-20") // Заголовок у уведомления
                        .setContentText("Мирошниченко Максим Павлович") // Текст внутри оболочки уведомления
                        .setWhen(System.currentTimeMillis()) // Время уведомления
                        .setAutoCancel(true); // Автоматическое закрытие

        Notification notification = notificationBuilder.build(); // Сборка проектов
        createChannelIfNeeded(nm);
        nm.notify(Notification_Id, notification);


    }

    public static void createChannelIfNeeded(NotificationManager manager) { // мин версия 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CAHNNEL_ID, CAHNNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }


    public void viewCamera(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
        startActivity(intent1);

    }

}
