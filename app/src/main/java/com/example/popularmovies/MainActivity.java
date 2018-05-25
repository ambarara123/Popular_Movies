package com.example.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView gridRecyclerView;
    private RecyclerAdapter recyclerAdapter;
    //test case
    int num = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2
        , LinearLayoutManager.VERTICAL,false);

        gridRecyclerView.setLayoutManager(gridLayoutManager);
        gridRecyclerView.setHasFixedSize(true);

        recyclerAdapter = new RecyclerAdapter(num);

        gridRecyclerView.setAdapter(recyclerAdapter);




    }


    //doing sort menu related stuff

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflating menu item
        getMenuInflater().inflate(R.menu.sort,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //getting id of menu items
        int id = item.getItemId();

        if (id == R.id.action_popular){
            //popular sort coding will done here
            return true;
        }else if (id== R.id.acrion_highestrated){
            //highest rated coding will be done here
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
