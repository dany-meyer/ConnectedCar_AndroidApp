package com.example.lv1_a.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lv1_a.R;

import java.util.List;

public class PersonAdapter extends ArrayAdapter<Person> {
    public PersonAdapter(Context context, List<Person> persons) {
        super(context, 0, persons);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Person person = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.person_item, parent, false);
        }

        TextView fnTextView = convertView.findViewById(R.id.first_name);
        TextView lnTextView = convertView.findViewById(R.id.last_name);
        TextView idTextView = convertView.findViewById(R.id.id);

        idTextView.setText(person.getIdent() + "");
        fnTextView.setText(person.getFirst_name());
        lnTextView.setText(String.valueOf(person.getLast_name()));

        return convertView;
    }
}
