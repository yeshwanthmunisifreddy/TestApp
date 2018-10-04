package technology.nine.test;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import technology.nine.test.data.DBHelper;
import technology.nine.test.model.Items;

public class BarcodeHistoryActivity extends AppCompatActivity {

    DBHelper helper;
    RecyclerView recyclerView;
    HistoryRecyclerAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    List<Items> items = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        fetch(getApplicationContext());
    }

    private void fetch(Context applicationContext) {
        helper = new DBHelper(applicationContext);
        items = helper.getAllBarcodes();
        linearLayoutManager = new LinearLayoutManager(applicationContext);
        adapter = new HistoryRecyclerAdapter(applicationContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.addAll(items);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
