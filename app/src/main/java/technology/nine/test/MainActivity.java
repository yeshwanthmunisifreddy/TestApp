package technology.nine.test;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import technology.nine.test.model.Items;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    int spanCount = 2;
    int spacing = 10 ;
    boolean includeEdge = true;
    GridLayoutManager gridLayoutManager;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new RecyclerAdapter(this, getData());
        gridLayoutManager = new GridLayoutManager(this, spanCount);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount,spacing,includeEdge));
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tab_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.history:
                startActivity(new Intent(this,BarcodeHistoryActivity.class));

        }
        return true;
    }

    public ArrayList<Items> getData() {
        ArrayList<Items> items = new ArrayList<>();
        items.add(new Items("Products"));
        items.add(new Items("Offers"));
        items.add(new Items("Scan"));
        items.add(new Items("About Us"));
        items.add(new Items("Contact Us"));
        Log.e("Size", items.size() + "");
        return items;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
              finish();
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }
}
