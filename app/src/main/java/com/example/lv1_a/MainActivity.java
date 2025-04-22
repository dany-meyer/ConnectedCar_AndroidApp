package com.example.lv1_a;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


/**
 * Activity zur Demonstration Button-Click-Events und Menus
 * toDo: Zurücksetzen-Button für counter
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreated");

        TextView txt = (TextView) findViewById(R.id.main_txtView);

        Button bt_add = (Button) findViewById(R.id.button);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter+=1;
                txt.setText(String.valueOf(counter));
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_person) {
            Log.i(TAG, "person menu geklickt");
            Intent intent = new Intent(MainActivity.this, ActivityPerson.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_item_calc) {
            Log.i(TAG, "calc menu geklickt");
            Intent intent = new Intent(MainActivity.this, ActivityCalc.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_item_mqtt) {
            Log.i(TAG, "mqtt menu geklickt");
            Intent intent = new Intent(MainActivity.this, ActivityMQTT.class);
            startActivity(intent);
        return true;
    }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}