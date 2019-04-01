package com.laxman.newsheadline.Viw;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.laxman.newsheadline.Adapter.NewsHeadlineAdapter;
import com.laxman.newsheadline.DataBaseHelperClass.OfflineStrgeHelperClass;
import com.laxman.newsheadline.Model.TopHeadlineModelClass;
import com.laxman.newsheadline.Model.TopHeadlineResponse;
import com.laxman.newsheadline.MainViewInterface.MainViewInterface;
import com.laxman.newsheadline.NetworkPackage.NetworkInterface;
import com.laxman.newsheadline.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MainViewInterface {

    private List<TopHeadlineModelClass> list;
    NewsHeadlineAdapter recyclerAdapter;
    /*
        Context context;
    */
    static RecyclerView recyclerView;
    @BindView(R.id.simpleProgressBar)
    ProgressBar progressBar;

    OfflineStrgeHelperClass offlineStrgeHelperClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.lst_topHedline);
        progressBar = new ProgressBar(getApplicationContext());

        try {
            //for divider for recylerview
            recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                    DividerItemDecoration.VERTICAL));
            DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
            itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider));
        } catch (Exception e) {
            e.getMessage();
        }


        offlineStrgeHelperClass = new OfflineStrgeHelperClass(getApplicationContext());


        if (NetworkStatusClass.isInternetAvailable(getApplicationContext())) //returns true if internet available
        {

            GetHeadlineData();


        } else {
            list = new ArrayList<>();
            final List<TopHeadlineModelClass> topHeadlineModelClasses = offlineStrgeHelperClass.getAll();
            Log.d("response size", String.valueOf(topHeadlineModelClasses.size()));
             for (int i = 0; i < topHeadlineModelClasses.size(); i++) {

                TopHeadlineModelClass topHeadlineModelClass = new TopHeadlineModelClass();

                topHeadlineModelClass.setTitle(topHeadlineModelClasses.get(i).getTitle());
                topHeadlineModelClass.setPublishedAt(topHeadlineModelClasses.get(i).getPublishedAt());
                topHeadlineModelClass.setDescription(topHeadlineModelClasses.get(i).getDescription());
                topHeadlineModelClass.setAuthor(topHeadlineModelClasses.get(i).getAuthor());
                topHeadlineModelClass.setId(topHeadlineModelClasses.get(i).getId());
                topHeadlineModelClass.setUrl(topHeadlineModelClasses.get(i).getUrl());
                topHeadlineModelClass.setName(topHeadlineModelClasses.get(i).getName());
                topHeadlineModelClass.setContent(topHeadlineModelClasses.get(i).getContent());
                topHeadlineModelClass.setUrlToImage(topHeadlineModelClasses.get(i).getUrlToImage());

                list.add(topHeadlineModelClass);

                Set<TopHeadlineModelClass> set = new HashSet<>(list);
                list.clear();
                list.addAll(set);


                recyclerAdapter = new NewsHeadlineAdapter(MainActivity.this, list);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                 showToast("No internet is available");

            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        GetHeadlineData();

    }

    @Override
    public void showToast(String s) {
        Toast.makeText(MainActivity.this, s +"", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void displayError(String s) {

    }





    private void GetHeadlineData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        NetworkInterface networkInterface = retrofit.create(NetworkInterface.class);

        Observable<TopHeadlineResponse> observable = networkInterface.GetHeadline("us", "a4cf9c6322864d3f8b4d6ca75989b3fd").subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<TopHeadlineResponse>() {
            @Override
            public void onCompleted() {
                hideProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Log.d("error", e.toString());

            }

            @Override
            public void onNext(TopHeadlineResponse response) {
                list = new ArrayList<>();
                final List<TopHeadlineModelClass> topHeadlineModelClasses = response.getTopHeadlineModelClasses();
                Log.d("response size", String.valueOf(topHeadlineModelClasses.size()));
                for (int i = 0; i < topHeadlineModelClasses.size(); i++) {

                    TopHeadlineModelClass topHeadlineModelClass = new TopHeadlineModelClass();

                    topHeadlineModelClass.setTitle(topHeadlineModelClasses.get(i).getTitle());
                    topHeadlineModelClass.setPublishedAt(topHeadlineModelClasses.get(i).getPublishedAt());
                    topHeadlineModelClass.setDescription(topHeadlineModelClasses.get(i).getDescription());
                    topHeadlineModelClass.setAuthor(topHeadlineModelClasses.get(i).getAuthor());
                    topHeadlineModelClass.setId(topHeadlineModelClasses.get(i).getId());
                    topHeadlineModelClass.setUrl(topHeadlineModelClasses.get(i).getUrl());
                    topHeadlineModelClass.setName(topHeadlineModelClasses.get(i).getName());
                    topHeadlineModelClass.setContent(topHeadlineModelClasses.get(i).getContent());

                    topHeadlineModelClass.setUrlToImage(topHeadlineModelClasses.get(i).getUrlToImage());

                    list.add(topHeadlineModelClass);
                    offlineStrgeHelperClass = new OfflineStrgeHelperClass(getApplicationContext());
                    if (offlineStrgeHelperClass.save(topHeadlineModelClass)) {

                    }
                }

                recyclerView = (RecyclerView) findViewById(R.id.lst_topHedline);
                recyclerAdapter = new NewsHeadlineAdapter(MainActivity.this, list);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


                recyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                             /*   String NewsTitle = (topHeadlineModelClasses.get(position).getTitle());
                                String Descprn = (topHeadlineModelClasses.get(position).getDescription());*/
                                String Url = (topHeadlineModelClasses.get(position).getUrl());
                             /*   String UrlToImage = (topHeadlineModelClasses.get(position).getUrlToImage());
                                String PublishDate = (topHeadlineModelClasses.get(position).getPublishedAt());
                                String Author = (topHeadlineModelClasses.get(position).getAuthor());*/

                                // TODO Handle item click
                                Intent indt = new Intent(MainActivity.this, NewsHeadlineDetail.class);
                             /*   indt.putExtra("NewsTitle", NewsTitle);
                                indt.putExtra("Descprn", Descprn);*/
                                indt.putExtra("Url", Url);
                              /*  indt.putExtra("UrlToImage", UrlToImage);
                                indt.putExtra("PublishDate", PublishDate);
                                indt.putExtra("Author", Author);*/

                                startActivity(indt);
                            }
                        })
                );


            }
        });
    }

}
