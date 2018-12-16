package com.drmmx.devmax.exchangerates.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.drmmx.devmax.exchangerates.R;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        GraphView graph = findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 28.2),
                new DataPoint(2, 28.0),
                new DataPoint(3, 28.1),
                new DataPoint(4, 28.1),
                new DataPoint(5, 28.2),
                new DataPoint(6, 28.0),
                new DataPoint(7, 28.1)
        });
        graph.addSeries(series);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX);
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX) + "UAH";
                }
            }
        });
    }
}
