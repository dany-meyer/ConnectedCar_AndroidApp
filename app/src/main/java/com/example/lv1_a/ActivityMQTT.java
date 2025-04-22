package com.example.lv1_a;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3Client;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;

public class ActivityMQTT extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private Mqtt3AsyncClient mqClient;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mqtt);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        txt = (TextView) findViewById(R.id.mqtt_textView);

        Button bt_snd = (Button) findViewById(R.id.mqtt_bt_send);
        bt_snd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doPublish("connectedCarTx", "Testnachricht von Android");
                txt.setText("gesendet");
            }
        });

        Log.d(TAG, "Create and connect MQTT client");
        mqClient = Mqtt3Client.builder()
                .identifier("connectedCarExampleApp_dany") //bitte eindeutig machen
                //.serverHost("broker.hivemq.com")
                .serverHost("mqtt.eclipseprojects.io")
                .serverPort(1883)
                .buildAsync();
        doMqConnect();

    }

    private void doMqConnect() {
        Log.i(TAG, "start connect mqClient");

        mqClient.connectWith()
                .send()
                .whenComplete(
                (connAck, throwable) -> {
                    if (throwable != null) {
                        Log.e(TAG, "Connection failed: " + throwable.getMessage());
                    } else {
                        Log.i(TAG, "Connected successfully!");
                        doSubscribe("connectedCarRx");
                        doSubscribe("HuHuDany");
                    }
                });
    }

    public void doPublish(String topic, String message) {
        mqClient.publishWith()
                .topic(topic)
                .payload(message.getBytes())
                .send()
                .whenComplete((publish, throwable) -> {
                    if (throwable != null) {
                        Log.e(TAG, "Publish failed: " + throwable.getMessage());
                    } else {
                        Log.i(TAG, "Message published successfully!");
                    }
                });
    }


    public void doSubscribe(String topic) {
        mqClient.subscribeWith()
                .topicFilter(topic)
                .callback(this::handleMessage)
                .send()
                .whenComplete((subAck, throwable) -> {
                    if (throwable != null) {
                        Log.e(TAG, "Subscription failed: " + throwable.getMessage());
                    } else {
                        Log.i(TAG, "Subscribed successfully!");
                    }
                });
    }


    private void handleMessage(Mqtt3Publish publish) {
        String topic = publish.getTopic().toString();
        String message = new String(publish.getPayloadAsBytes());
        txt.setText("Received message from " + topic + ": " + message);
    }



}