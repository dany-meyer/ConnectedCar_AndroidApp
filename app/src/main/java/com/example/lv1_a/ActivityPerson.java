package com.example.lv1_a;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lv1_a.model.Person;
import com.example.lv1_a.model.PersonAdapter;
import com.example.lv1_a.model.PersonServiceResponse;
import com.example.lv1_a.service.IPersonService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Activity zeigt beispielhaft die Verwendung des person webService
 * GUI ist nur rudimentaer umgesetzt und muss erweitert werden
 */
public class ActivityPerson extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    TextView txt; //nur definieren, Wertzuweisung in oncreate nach setContentView

    Retrofit retrofitPerson = new Retrofit.Builder()
            .baseUrl("https://personmicro-itqh.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    IPersonService personService = retrofitPerson.create(IPersonService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_person);

        txt = (TextView) findViewById(R.id.person_textView);
        txt.setText("Hallo");

        Log.i(TAG, "onCreated");

        Button myBtLoad = (Button) findViewById(R.id.person_bt_loadAll);
        myBtLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt.setText("Start loading");
                doLoadAll();
                txt.setText("Loading completed");
                Log.i(TAG, "load geklick");
            }
        });

        Button myBtClear = (Button) findViewById(R.id.person_bt_clear);
        myBtClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doClearView();
                txt.setText("Clear completed");
                Log.i(TAG, "clear geklickt");
            }
        });

        Button myBtDelete = (Button) findViewById(R.id.person_bt_deleteLast);
        myBtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt.setText("Start delete");
                doClearView();
                doDelete();
                txt.setText("Delete completed");
                Log.i(TAG, "delete geklickt");
            }
        });

        Button myBtNew = (Button) findViewById(R.id.person_bt_addNew);
        myBtNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt.setText("Start create person");
                doClearView();
                doNewPerson();
                txt.setText("Create new person completed");
                Log.i(TAG, "new geklickt");
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



    private void doLoadAll() {
        Call<List<Person>> call = personService.getPersons();

        call.enqueue(new Callback<List<Person>>() { //asynchronen Aufruf mit call-back-Methoden starten
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (response.isSuccessful()) {
                    List<Person> persons = response.body();
                    // Verarbeite die Antwort
                    for (Person p : persons) {
                        Log.d(TAG, p.getIdent() + " " + p.getFirst_name() + p.getLast_name());
                    }
                    PersonAdapter adapter = new PersonAdapter(ActivityPerson.this, persons);
                    ListView listView = findViewById(R.id.person_listView);
                    listView.setAdapter(adapter);
                } else {
                    // Fehlerbehandlung für nicht erfolgreiche Antworten
                    Log.e("API_ERROR", "Response Code: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage());
            }
        });
    }

    private void doDelete() {
        Call<PersonServiceResponse> call = personService.deletePerson(12);
        call.enqueue(new Callback<PersonServiceResponse>() {
            @Override
            public void onResponse(Call<PersonServiceResponse> call, Response<PersonServiceResponse> response) {
                if (response.isSuccessful()) {
                    // Erfolgreiche Antwort
                    PersonServiceResponse status = response.body();
                } else {
                    // Fehlerhafte Antwort
                    Log.i(TAG, "Request failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<PersonServiceResponse> call, Throwable t) {
                // Fehler bei der Anfrage
                t.printStackTrace();
            }
        });
    }


    private void doNewPerson() {

        Person newPerson = new Person();
        newPerson.setFirst_name("John");
        newPerson.setLast_name("Doe");
        newPerson.setIdent(12);

        Call<PersonServiceResponse> call = personService.createPerson(newPerson);

        call.enqueue(new Callback<PersonServiceResponse>() {
            @Override
            public void onResponse(Call<PersonServiceResponse> call, Response<PersonServiceResponse> response) {
                if (response.isSuccessful()) {
                    // Erfolgreiche Antwort
                    PersonServiceResponse status = response.body();
                    Log.i(TAG, "Person created: " + status.getStatus());
                } else {
                    // Fehlerhafte Antwort
                    Log.i(TAG, "Request failed: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<PersonServiceResponse> call, Throwable t) {
                // Fehler bei der Anfrage
                t.printStackTrace();
            }
        });

    }

    private void doClearView() {
        PersonAdapter adapter = new PersonAdapter(ActivityPerson.this, new ArrayList<Person>());
        ListView listView = findViewById(R.id.person_listView);
        listView.setAdapter(adapter);
    }


}