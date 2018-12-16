package com.drmmx.devmax.exchangerates.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drmmx.devmax.exchangerates.R;
import com.drmmx.devmax.exchangerates.model.ExchangeRate;

import java.util.List;

import static com.drmmx.devmax.exchangerates.util.Utils.getCurrencyName;
import static com.drmmx.devmax.exchangerates.util.Utils.roundDouble;

public class ExchangeRatesAdapter extends RecyclerView.Adapter<ExchangeRatesAdapter.ViewHolder> {

    private Context context;
    private List<ExchangeRate> exchangeRates;

    public ExchangeRatesAdapter(Context context, List<ExchangeRate> exchangeRates) {
        this.context = context;
        this.exchangeRates = exchangeRates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.nbu_exchange_rates_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (exchangeRates.get(position).getCurrency() != null) {
            if (position % 2 == 1) {
                holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryLight));
            } else {
                holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.backgroundColor));
            }
            holder.currencyName.setText(getCurrencyName().get(exchangeRates.get(position).getCurrency()));
            holder.uahValue.setText(new StringBuilder(roundDouble(exchangeRates.get(position).getSaleRateNB())).append("UAH"));
            holder.currencyValue.setText(new StringBuilder("1").append(String.valueOf(exchangeRates.get(position).getCurrency())));
        }
    }

    @Override
    public int getItemCount() {
        return exchangeRates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView currencyName;
        TextView uahValue;
        TextView currencyValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            currencyName = itemView.findViewById(R.id.currencyName);
            uahValue = itemView.findViewById(R.id.uahValue);
            currencyValue = itemView.findViewById(R.id.currencyValue);
        }
    }
}
