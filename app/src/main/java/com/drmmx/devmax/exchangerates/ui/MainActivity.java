package com.drmmx.devmax.exchangerates.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.drmmx.devmax.exchangerates.R;
import com.drmmx.devmax.exchangerates.adapter.ExchangeRatesAdapter;
import com.drmmx.devmax.exchangerates.model.ExchangeRate;
import com.drmmx.devmax.exchangerates.model.ExchangeRateData;
import com.drmmx.devmax.exchangerates.retrofit.PbAPI;
import com.drmmx.devmax.exchangerates.retrofit.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static com.drmmx.devmax.exchangerates.util.Utils.getCurrentDate;
import static com.drmmx.devmax.exchangerates.util.Utils.*;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private static final String TAG = "MainActivity";

    private TextView pbDateTextView, nbuDateTextView, eurPurchaseTextView, eurSaleTextView, usdPurchaseTextView,
            usdSaleTextView, rubPurchaseTextView, rubSaleTextView;
    private ConstraintLayout eurLayout, usdLayout, rubLayout;
    private ProgressBar progressBar;
    private ProgressBar progressBar2;
    private SwipeRefreshLayout swipeRefreshLayout;

    private PbAPI pbAPI;
    private RecyclerView recyclerView;
    private ExchangeRatesAdapter adapter;

    private String exchangeRateDate;

    //RxJava
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState != null) {
            exchangeRateDate = savedInstanceState.getString("exchangeRateDate");
        } else {
            exchangeRateDate = getCurrentDate();
        }

        progressBar = findViewById(R.id.progressBar);
        progressBar2 = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        progressBar2.setVisibility(View.VISIBLE);

        pbDateTextView = findViewById(R.id.pbDateTextView);
        nbuDateTextView = findViewById(R.id.nbuDateTextView);
        eurPurchaseTextView = findViewById(R.id.eurPurchaseTextView);
        eurSaleTextView = findViewById(R.id.eurSaleTextView);
        usdPurchaseTextView = findViewById(R.id.usdPurchaseTextView);
        usdSaleTextView = findViewById(R.id.usdSaleTextView);
        rubPurchaseTextView = findViewById(R.id.rubPurchaseTextView);
        rubSaleTextView = findViewById(R.id.rubSaleTextView);
        eurLayout = findViewById(R.id.eurLayout);
        usdLayout = findViewById(R.id.usdLayout);
        rubLayout = findViewById(R.id.rubLayout);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        //Retrofit init
        Retrofit retrofit = RetrofitClient.getInstance();
        pbAPI = retrofit.create(PbAPI.class);

        //RecyclerView init
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        pbDateTextView.setPaintFlags(pbDateTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        pbDateTextView.setText(exchangeRateDate);
        nbuDateTextView.setPaintFlags(nbuDateTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        nbuDateTextView.setText(exchangeRateDate);

        //Load data
        fetchAllData(exchangeRateDate);

        eurLayout.setOnClickListener(this);
        usdLayout.setOnClickListener(this);
        rubLayout.setOnClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onResume();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void fetchAllData(String exRateDate) {
        compositeDisposable.add(pbAPI.getExchangeRate("", exRateDate)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ExchangeRateData>() {
                    @Override
                    public void accept(ExchangeRateData exchangeRateData) {
                        List<ExchangeRate> exchangeRates = exchangeRateData.getExchangeRate();
                        exchangeRates.remove(0);
                        for (ExchangeRate exchangeRate : exchangeRates) {
                            switch (exchangeRate.getCurrency()) {
                                case "EUR":
                                    eurPurchaseTextView.setText(roundDouble(exchangeRate.getPurchaseRate()));
                                    eurSaleTextView.setText(roundDouble(exchangeRate.getSaleRate()));
                                    break;
                                case "USD":
                                    usdPurchaseTextView.setText(roundDouble(exchangeRate.getPurchaseRate()));
                                    usdSaleTextView.setText(roundDouble(exchangeRate.getSaleRate()));
                                    break;
                                case "RUB":
                                    rubPurchaseTextView.setText(roundDouble(exchangeRate.getPurchaseRate()));
                                    rubSaleTextView.setText(roundDouble(exchangeRate.getSaleRate()));
                                    break;
                            }
                        }
                        adapter = new ExchangeRatesAdapter(MainActivity.this, exchangeRateData.getExchangeRate());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        progressBar2.setVisibility(View.GONE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.d(TAG, "error: " + throwable.getMessage());
                    }
                }));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.eurLayout:
                eurLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                usdLayout.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
                rubLayout.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
                break;
            case R.id.usdLayout:
                usdLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                eurLayout.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
                rubLayout.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
                break;
            case R.id.rubLayout:
                rubLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                usdLayout.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
                eurLayout.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dayMonthFormat = new SimpleDateFormat("dd.MM.yyyy");
        exchangeRateDate = dayMonthFormat.format(calendar.getTime());
        pbDateTextView.setText(exchangeRateDate);
        nbuDateTextView.setText(exchangeRateDate);
        onResume();
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
            case R.id.exchangeRateDate:
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("exchangeRateDate", exchangeRateDate);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        progressBar2.setVisibility(View.VISIBLE);
        fetchAllData(exchangeRateDate);
    }
}
