package com.drmmx.devmax.exchangerates.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.drmmx.devmax.exchangerates.R;
import com.drmmx.devmax.exchangerates.adapter.ExchangeRatesAdapter;
import com.drmmx.devmax.exchangerates.model.ExchangeRate;
import com.drmmx.devmax.exchangerates.model.ExchangeRateData;
import com.drmmx.devmax.exchangerates.retrofit.PbAPI;
import com.drmmx.devmax.exchangerates.retrofit.RetrofitClient;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView pbDateTextView, nbuDateTextView, eurPurchaseTextView, eurSaleTextView, usdPurchaseTextView,
            usdSaleTextView, rurPurchaseTextView, rurSaleTextView;
    private ImageView pbDateImageView, nbuDateImageView;
    private ConstraintLayout eurLayout, usdLayout, rurLayout;
    private ProgressBar progressBar;

    private PbAPI pbAPI;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ExchangeRatesAdapter adapter;

    private String pbExchangeRateDate;
    private String nbuExchangeRateDate;

    //RxJava
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        pbExchangeRateDate = getCurrentDate();
        nbuExchangeRateDate = getCurrentDate();*/
        pbExchangeRateDate = "15.12.2018";
        nbuExchangeRateDate = "15.12.2018";

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        pbDateTextView = findViewById(R.id.pbDateTextView);
        nbuDateTextView = findViewById(R.id.nbuDateTextView);
        eurPurchaseTextView = findViewById(R.id.eurPurchaseTextView);
        eurSaleTextView = findViewById(R.id.eurSaleTextView);
        usdPurchaseTextView = findViewById(R.id.usdPurchaseTextView);
        usdSaleTextView = findViewById(R.id.usdSaleTextView);
        rurPurchaseTextView = findViewById(R.id.rurPurchaseTextView);
        rurSaleTextView = findViewById(R.id.rurSaleTextView);
        nbuDateImageView = findViewById(R.id.nbuDateImageView);
        pbDateImageView = findViewById(R.id.pbDateImageView);
        eurLayout = findViewById(R.id.eurLayout);
        usdLayout = findViewById(R.id.usdLayout);
        rurLayout = findViewById(R.id.rurLayout);

        //Retrofit init
        Retrofit retrofit = RetrofitClient.getInstance();
        pbAPI = retrofit.create(PbAPI.class);

        //RecyclerView init
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        pbDateTextView.setPaintFlags(pbDateTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        pbDateTextView.setText(pbExchangeRateDate);
        nbuDateTextView.setPaintFlags(nbuDateTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        nbuDateTextView.setText(nbuExchangeRateDate);

        //Load data
        fetchNbuData(nbuExchangeRateDate);
    }

    private void fetchNbuData(String nbuExRateDate) {
        compositeDisposable.add(pbAPI.getExchangeRate("", nbuExRateDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ExchangeRateData>() {
                    @Override
                    public void accept(ExchangeRateData exchangeRateData) {
                        exchangeRateData.getExchangeRate().remove(0);
                        adapter = new ExchangeRatesAdapter(MainActivity.this, exchangeRateData.getExchangeRate());
                        recyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.d(TAG, "error: " + throwable.getMessage());
                    }
                }));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.graph_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.graphItem:
                startActivity(new Intent(this, GraphActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
