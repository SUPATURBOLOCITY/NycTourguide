package com.timemasta.nyctourguide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tab4 extends Fragment {
    private RecyclerView recyclerView4;
    private ModelAdapter mAdapter;
    String url = "https://www.newyorkpass.com/new-york-attractions/";
    String imageUrl = "https://www.newyorkpass.com";
    ProgressDialog mProgressDialog;
    private String titleOfPlace, summ;
    private List<Model> modelList = new ArrayList<>();
    private int CONST = 76;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        final View view =inflater.inflate(R.layout.tab4, container, false);
        recyclerView4 = (RecyclerView)view.findViewById(R.id.recycler_view_4);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView4.setLayoutManager(mLayoutManager);
        mAdapter = new ModelAdapter(modelList, getContext(), new ModelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("POSITION", "" + (CONST + position));
                startActivity(intent);
            }
        });
        new Fetch().execute();

        return view;
    }

    private class Fetch extends AsyncTask<Void, Void, Void> {
        String title, sub;
        Document document;
        Elements elements, elements2, elements3, span, atag, atag2, img;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setTitle("Tour Guide");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override

        protected Void doInBackground(Void... params) {
            try {
                document = Jsoup.connect(url).get();
                elements = document.body().select(getResources().getString(R.string.att_trip));
                elements2 = document.body().select(getResources().getString(R.string.att_trip_li));
                elements3 = document.body().select(getResources().getString(R.string.att_trip_article));
                span = document.body().select(getResources().getString(R.string.att_trip_li_h3));
                atag = document.body().select(getResources().getString(R.string.att_trip_li_a));
                atag2 = document.body().select(getResources().getString(R.string.att_trip_li_ahref));
                img = document.body().select(getResources().getString(R.string.att_trip_img));

                String url = document.select(getResources().getString(R.string.a)).first().attr(getResources().getString(R.string.newurl));

                for (int i = 76; i < elements.size(); i++) {
                    for (Element elem : elements2) {
                        titleOfPlace = elem.ownText();
                    }

                    summ = elements3.get(i).text();
                    Element cv = atag2.get(i);
                    String iimage = elements.get(i).select(getResources().getString(R.string.att_strip_img)).text();

                    Element featureImage = elements.get(i).select(getResources().getString(R.string.div_att_strip_img)).first();
                    String temp = featureImage.getElementsByAttribute(getResources().getString(R.string.style_)).toString();
                    String imageStrg = temp.substring(temp.indexOf("(") + 1, temp.indexOf(")"));

                    modelList.add(new Model(elements2.get(i).text(), elements3.get(i).text(),
                            elements.get(i).select(getResources().getString(R.string.h3)).text(),
                            "" + url + imageStrg));
                }

                Elements select = document.select(getResources().getString(R.string.filtered_item));
                for (int i = 0; i < select.size(); i++) {
                    String li = select.get(i).className();
                    String lii = select.get(i).cssSelector();
                    String li2 = select.get(i).id();
                    String li3 = select.get(i).wholeText();
                    String x = select.get(50).cssSelector();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            recyclerView4.setItemAnimator(new DefaultItemAnimator());
            recyclerView4.setAdapter(mAdapter);
            mProgressDialog.dismiss();
        }
    }

}
