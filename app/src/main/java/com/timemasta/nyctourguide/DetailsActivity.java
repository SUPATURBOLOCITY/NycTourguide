package com.timemasta.nyctourguide;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DetailsActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    String url = Constant.BASE_URL;
    int position;
    String newUrl;
    public ImageView imagevater;
    public TextView title1, overview1, openingtimes1, gettingthere1, gettingthere2, attractionsNearby, doyouknow;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activiy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        initUi();


        String sessionId = getIntent().getStringExtra(getResources().getString(R.string.position));
        position = Integer.parseInt(sessionId);
        new Fetch().execute();

    }


    private class Fetch extends AsyncTask<Void, Void, Void> {
        String title, sub;
        Document document;
        Elements elements, elements2, elements3, elements4, elements5, elements6, elements7;
        String cv, cv2, cv3, cv4, cv5, cv6, cv7, cv8;
        Element e1;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(DetailsActivity.this);
            mProgressDialog.setTitle(R.string.app_name);
            mProgressDialog.setMessage(getResources().getString(R.string.loading));
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                document = Jsoup.connect(url).get();
                Elements atag2 = document.body().select(getResources().getString(R.string.atag));

                newUrl = atag2.get(position).attr(getResources().getString(R.string.newurl));
                document = Jsoup.connect(newUrl).get();

                elements = document.body().select(getResources().getString(R.string.detail_element));
                elements2 = document.body().select(getResources().getString(R.string.detail_element2));
                elements3 = document.body().select(getResources().getString(R.string.detail_element3));
                elements4 = document.body().select(getResources().getString(R.string.detail_element4));
                elements5 = document.body().select(getResources().getString(R.string.detail_element5));
                elements6 = document.body().select(getResources().getString(R.string.detail_element6));
                elements7 = document.body().select(getResources().getString(R.string.detail_element7));

                Element image = elements5.select(getResources().getString(R.string.image)).first();
                String imageurl = image.absUrl(getResources().getString(R.string.src));

                cv = elements.select(getResources().getString(R.string.h1span)).text();
                cv2 = elements.select(getResources().getString(R.string.p)).text();
                cv3 = elements2.select(getResources().getString(R.string.p)).text();
                cv4 = elements3.select(getResources().getString(R.string.p)).text();
                url = elements5.attr(getResources().getString(R.string.absrc));


                for (int i = 0; i < elements4.size(); i++) {
                    sb3.append(+i + 1 + ". ").append(elements4.get(i).text()).append("\n\n");

                }
                for (int i = 0; i < elements6.size(); i++) {
                    sb.append(+i + 1 + ". ").append(elements6.get(i).text()).append("\n\n");
                }
                for (int i = 0; i < elements7.size(); i++) {
                    sb2.append(i + 1 + ". ").append(elements7.get(i).text()).append("\n\n");

                }

                cv6 = imageurl;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            title1.setText("" + cv);
            overview1.setText("" + cv2);
            openingtimes1.setText("" + cv3);
            gettingthere1.setText(cv4);
            gettingthere2.setText(sb3);


            doyouknow.setText(sb);
            attractionsNearby.setText(sb2);

            Picasso.get().load(cv6).resize(R.dimen.dimen_300, R.dimen.dimen_300).into(imagevater);
            mProgressDialog.dismiss();
        }
    }

    private void initUi() {
        title1 = (TextView) findViewById(R.id.title);
        overview1 = (TextView) findViewById(R.id.overview);
        openingtimes1 = (TextView) findViewById(R.id.opening_times);
        gettingthere1 = (TextView) findViewById(R.id.getting_there);
        gettingthere2 = (TextView) findViewById(R.id.getting_there2);
        doyouknow = (TextView) findViewById(R.id.do_you_know);
        attractionsNearby = (TextView) findViewById(R.id.attractions_nearby);
        imagevater = (ImageView) findViewById(R.id.imagevater);
        imagevater = (ImageView) findViewById(R.id.imagevater);
    }

}
