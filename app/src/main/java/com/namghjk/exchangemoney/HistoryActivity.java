package com.namghjk.exchangemoney;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import Adapter.HistoryAdapter;
import Model.History;

public class HistoryActivity extends AppCompatActivity {

    private ListView lsvHistory;
    private HistoryAdapter adapterHistory;
    ArrayList<History> historyArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        lsvHistory = findViewById(R.id.lsvHistory);
        historyArrayList = new ArrayList<>();
        adapterHistory = new HistoryAdapter(this,R.layout.item,historyArrayList);
        lsvHistory.setAdapter(adapterHistory);

        History history = (History) getIntent().getSerializableExtra("history");
        historyArrayList.add(history);
        Log.e("history",historyArrayList.toString());




    }
}