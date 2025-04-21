package com.example.lv1_a;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lv1_a.model.CalculatorResponse;
import com.example.lv1_a.model.Person;
import com.example.lv1_a.model.PersonAdapter;
import com.example.lv1_a.model.PersonServiceResponse;
import com.example.lv1_a.service.ICalculatorService;
import com.example.lv1_a.service.IPersonService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Activity zeigt beispielhaft die Verwendung des calculator webService
 * GUI ist nur rudimentaer umgesetzt und muss erweitert werden
 */
public class ActivityCalc extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    TextView txt; //nur definieren, Wertzuweisung in oncreate nach setContentView

    Retrofit retrofitCalculator = new Retrofit.Builder()
            .baseUrl("https://mycalculator-fdfz.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ICalculatorService calculatorService = retrofitCalculator.create(ICalculatorService.class);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_calc);
        txt = (TextView) findViewById(R.id.calc_textResult);

        Log.i(TAG, "onCreated");

        Button myBt = (Button) findViewById(R.id.calc_bt1);
        myBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doAdd();
            }
        });

        /*
        Button myBt2 = (Button) findViewById(R.id.bt2);
        myBt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                txt.setText("ok");
            }
        });

        Button myBt3 = (Button) findViewById(R.id.b3);
        myBt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txt = (TextView) findViewById(R.id.textView);
                //doWebserviceCallNew();
                txt.setText(R.string.clicked);
            }
        });
*/
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void doAdd() {
        txt.setText("Start request");

        //toDo: Zahlen aus der GUI lesen
        int zahl1=10;
        int zahl2=5;
        Call<CalculatorResponse> call = calculatorService.add(zahl1, zahl2);
        call.enqueue(new Callback<CalculatorResponse>() {
            @Override
            public void onResponse(Call<CalculatorResponse> call, Response<CalculatorResponse> response) {
                if (response.isSuccessful()) {
                    CalculatorResponse calculator = response.body();
                    txt.setText(calculator.getResult() + "");
                    // Ergebnis verarbeiten
                    Log.d("MainActivity", "Result: " + calculator.getResult());
                }
            }

            @Override
            public void onFailure(Call<CalculatorResponse> call, Throwable t) {
                // Fehlerbehandlung
                txt.setText(t.getMessage());
                Log.e("MainActivity", "Error: " + t.getMessage());
            }
        });

    }


}