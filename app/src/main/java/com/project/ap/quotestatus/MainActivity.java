package com.project.ap.quotestatus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<String> arrayList1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList1 = new ArrayList<>();

        arrayList1.add("first");
        arrayList1.add("first");
        arrayList1.add("first");
        arrayList1.add("first");
        arrayList1.add("first");
        arrayList1.add("first");
        arrayList1.add("first");

        // get our folding cell

        // attach click listener to fold btn

        RecyclerAdapter adapter = new RecyclerAdapter(arrayList1);

//       TextView textView = (TextView) findViewById(R.id.title_quote);
//
//       RecyclerAdapter.ViewHolder tt = adapter.new  ViewHolder(textView);
//
//       TextView bdb = tt.titelQuote;
//
//
//        // attach click listener to toast btn
//        bdb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fc.toggle(false);
//            }
//        });






        RecyclerView recyclerView = findViewById(R.id.recyclerview_xml);

        LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mlinearLayoutManager.setReverseLayout(true);
        mlinearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mlinearLayoutManager);

        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }
}
