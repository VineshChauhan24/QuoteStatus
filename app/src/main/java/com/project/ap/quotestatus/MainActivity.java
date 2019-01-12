package com.project.ap.quotestatus;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    mySingleTon mySingleTon;

    String myurl = "http://quotesondesign.com/wp-json/posts?filter[orderby]=rand&filter[posts_per_page]=43";

    ArrayList<String> quoteauther;
    ArrayList<String> quotecontent;
    ArrayList<String> imagesUrls;

    ArrayList<Bitmap> downloadBitmaps;
    ArrayList<Bitmap> editedBitmaps;


    JsonArrayRequest jsonArrayRequest;

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;



//    SwipeStack mSwipeStack;
//    SwipeStackAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quoteauther = new ArrayList<>();
        quotecontent = new ArrayList<>();
        imagesUrls = new ArrayList<>();
        downloadBitmaps = new ArrayList<>();
        editedBitmaps = new ArrayList<>();


        // Initialize a new JsonArrayRequest instance
        jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, myurl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        // Loop through the array elements
                        for (int i = 0; i < response.length(); i++) {
                            // Get current json object
                            JSONObject student = null;

                            try {

                                student = response.getJSONObject(i);
                                quoteauther.add(student.getString("title"));

                                quotecontent.add(student.getString("content"));

                                if (i == response.length()-1){

                                    loadBitmapWork();
                                }
                            } catch (Exception e){

                            }

                        }

                        setChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred

                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);

        mySingleTon.getInstance(getApplicationContext()).addToRequestque(jsonArrayRequest);






        recyclerView = findViewById(R.id.recyclerview_xml);

        adapter = new RecyclerViewAdapter(editedBitmaps , getApplicationContext());

//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }


    public void loadBitmapWork(){

        imagesUrls.add("http://funkyimg.com/i/2PsK1.png");
        imagesUrls.add("http://funkyimg.com/i/2PsYu.png");

        Log.i("Size", "ImagesUrls.size() = --- " + imagesUrls.size());


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    for (int i = 0; i < imagesUrls.size(); i++) {
                        downloadBitmaps.add(getBitmapFromURL(imagesUrls.get(i)));
                    }


                    for(int j = 0; j < quotecontent.size(); j++){
                        editedBitmaps.add(drawTextToBitmap(getApplicationContext(), downloadBitmaps.get(new Random().nextInt(downloadBitmaps.size())), String.valueOf(Html.fromHtml(quotecontent.get(j)))));

                    }

                    setChanged();

                } catch (Exception e){

                }
            }
        }).start();

    }

    public void setChanged(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }


//    @Override
//    public void onViewSwipedToLeft ( int position){
//
//    }
//
//    @Override
//    public void onViewSwipedToRight ( int position){
//
//    }
//
//    @Override
//    public void onStackEmpty () {
//
//        dataload();
//
//    }

    private ColorDrawable[] vibrantLightColorList =
            {
                    new ColorDrawable(Color.parseColor("#e9e2d0")),
                    new ColorDrawable(Color.parseColor("#F5BCBA")),
                    new ColorDrawable(Color.parseColor("#EA7773")),
                    new ColorDrawable(Color.parseColor("#1287A5")),
                    new ColorDrawable(Color.parseColor("#00CCCD")),
                    new ColorDrawable(Color.parseColor("#EAF0F1")),
                    new ColorDrawable(Color.parseColor("#E74292")),
                    new ColorDrawable(Color.parseColor("#01CBC6")),
                    new ColorDrawable(Color.parseColor("#BB2CD9")),
                    new ColorDrawable(Color.parseColor("#8B78E6")),
                    new ColorDrawable(Color.parseColor("#DAE0E2")),
                    new ColorDrawable(Color.parseColor("#FFF222")),
                    new ColorDrawable(Color.parseColor("#E1DA00")),
                    new ColorDrawable(Color.parseColor("#F9DDA4")),
                    new ColorDrawable(Color.parseColor("#E5B143")),
                    new ColorDrawable(Color.parseColor("#FBD28B")),
                    new ColorDrawable(Color.parseColor("#EEC213")),
                    new ColorDrawable(Color.parseColor("#F4C724")),
                    new ColorDrawable(Color.parseColor("#45CE30")),
                    new ColorDrawable(Color.parseColor("#7CEC9F")),
                    new ColorDrawable(Color.parseColor("#A3CB37")),
                    new ColorDrawable(Color.parseColor("#badc57")),
                    new ColorDrawable(Color.parseColor("#2ecc72")),
                    new ColorDrawable(Color.parseColor("#25CCF7"))
            };


    public ColorDrawable getRandomDrawbleColor() {
        int idx = new Random().nextInt(vibrantLightColorList.length);
        return vibrantLightColorList[idx];
    }

    public int[] yoRandomPosition;

    public int getrandomImagesPosition(){
        int random = new Random().nextInt(downloadBitmaps.size());
        Log.i("Size", "random = --- " + random );

        return yoRandomPosition[random];
    }

    public Bitmap getBitmapFromURL(String imgUrl) {
        try {
            URL url = new URL(imgUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }


    public Bitmap drawTextToBitmap(Context gContext, Bitmap bm, String gText) {

        // prepare canvas
        Resources resources = gContext.getResources();
        float scale = (resources.getDisplayMetrics().density)/2;


//        Bitmap bitmap = BitmapFactory.decodeResource(resources, gResId);


        Bitmap bitmap = bm;

        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
        // set default bitmap config if none
        if(bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);

        // new antialiased Paint
        TextPaint paint=new TextPaint(Paint.ANTI_ALIAS_FLAG);


        // text color - #3D3D3D
        paint.setColor(Color.parseColor("#FFFFFF"));
        // text size in pixels
//        paint.setTextSize((int) (30 * scale));
//            // text shadow
//        paint.setShadowLayer(1f, 0f, 2f, Color.parseColor("#000000"));

        // set text width to canvas width minus 16dp padding
        int textWidth = canvas.getWidth() - (int) (16 * scale);





//                paint.setTextSize(determineMaxTextSize(gText, textWidth, textLayout.getHeight() ));





        // init StaticLayout for text
        StaticLayout textLayout = new StaticLayout(
                gText, paint, textWidth, Layout.Alignment.ALIGN_CENTER , 1.0f, 0.0f, false);

        // get height of multiline text
        int textHeight = textLayout.getHeight();

        // get position of text's top left corner
        float x = (bitmap.getWidth() - textWidth)/2;
        float y = (bitmap.getHeight() - textHeight)/2;

        // draw text to the Canvas center
        canvas.save();
        canvas.translate(x, y);
        textLayout.draw(canvas);
        canvas.restore();

        return bitmap;
    }

    /**
     * Retrieve the maximum text size to fit in a given width.
     * @param str (String): Text to check for size.
     * @param maxWidth (float): Maximum allowed width.
     * @return (int): The desired text size.
     */
    private int determineMaxTextSize(String str, float maxWidth, float maxHeight)
    {
        int size = 0;
        Paint paint = new Paint();

        if (maxWidth > maxHeight){
            do {
                paint.setTextSize(++ size);
            } while(paint.measureText(str) < maxWidth);
        }else {

            do {
                paint.setTextSize(++size);
            } while (paint.measureText(str) < maxHeight);

        }
        return size;
    } //End getMaxTextSize()




//    class SwipeStackAdapter extends BaseAdapter {
//
//        private ArrayList<String> tit;
//        private ArrayList<String> cont;
//
//
//
//        public SwipeStackAdapter(ArrayList<String> tit, ArrayList<String> conn) {
//            this.tit = tit;
//            this.cont = conn;
//        }
//
////        public void addAll(ArrayList<String> result){
////            this.tit = result;
////            notifyDataSetChanged();
////        }
//
//        @Override
//        public int getCount() {
//            return tit.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return tit.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//            View view = getLayoutInflater().inflate(R.layout.swipe_custom ,parent,false);
////            TextView quote = view.findViewById(R.id.quote_text_xml);
////
////            quote.setText(Html.fromHtml(quotecontent.get(position)) + "\n\t"+ quoteauther.get(position));
////            quote.setBackground(getRandomDrawbleColor());
//            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.partner);
//            final ImageView imageView = view.findViewById(R.id.yp);
//
////            findViewById(R.id.card).setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    startActivity(new Intent(getApplicationContext(), EditActivity.class));
////                }
////            });
////
//
//            imageView.setImageBitmap(drawMultilineTextToBitmap(getApplicationContext(), R.drawable.partner , String.valueOf(Html.fromHtml(quotecontent.get(position)))));
//
//            return view;
//
//        }
//
//
//
//    }


}




