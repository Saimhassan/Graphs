package com.example.graphs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //Add Points Graphs series of Dot points type
    PointsGraphSeries<DataPoint> xySeries;

    private Button btnAddptn;
    private EditText mX,mY;

    GraphView mScatterPlot;

    //make xy value Array Global
    private ArrayList<XYValue> xyValueArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //declare variable in oncreate method
        btnAddptn = (Button)findViewById(R.id.btnAddPt1);
        mX = (EditText)findViewById(R.id.nuMx);
        mY = (EditText)findViewById(R.id.nuMy);
        mScatterPlot = (GraphView)findViewById(R.id.scatterPlot);
        xyValueArray = new ArrayList<>();
        init();
    }
    private void init(){
        xySeries = new PointsGraphSeries<>();
        btnAddptn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mX.getText().toString().equals("") && !mY.getText().toString().equals("")){
                    double x = Double.parseDouble(mX.getText().toString());
                    double y = Double.parseDouble(mY.getText().toString());
                    Log.d(TAG,"onClick: Adding a new Point. (x,y):("+ x + "," + y + ")" );
                    xyValueArray.add(new XYValue(x,y));
                    init();
                }else
                {
                    toastMessage("You must fill out both fields");
                }
            }
        });
        if (xyValueArray.size() != 0){
             createScatterPlot();
        }else
        {
            Log.d(TAG, "on Create: No data to Plot");
        }
    }

    private void createScatterPlot() {
        Log.d(TAG,"createScatterPlot : creating scatter plot.");

        xyValueArray = sortArray(xyValueArray);
        for (int i = 0; i<xyValueArray.size();i++){
         try {
             double x = xyValueArray.get(i).getX();
             double y = xyValueArray.get(i).getY();
             xySeries.appendData(new DataPoint(x,y),true,1000);
         }catch (IllegalArgumentException e){
             Log.e(TAG,"createScatterPlot: IllegalArgumentException: "+e.getMessage());
         }
        }

        //Set some properties
        xySeries.setShape(PointsGraphSeries.Shape.RECTANGLE);
        xySeries.setColor(Color.WHITE);
        xySeries.setSize(20f);

        //Set Scrollable and Scaleable
        mScatterPlot.getViewport().setScalable(true);
        mScatterPlot.getViewport().setScalableY(true);
        mScatterPlot.getViewport().setScalable(true);
        mScatterPlot.getViewport().setScalableY(true);

        //Set Manual x bounds
        mScatterPlot.getViewport().setYAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxY(150);
        mScatterPlot.getViewport().setMinY(-150);

        //set Manual y bounds
        mScatterPlot.getViewport().setXAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxX(150);
        mScatterPlot.getViewport().setMinX(-150);

        mScatterPlot.addSeries(xySeries);

    }

    private ArrayList<XYValue> sortArray(ArrayList<XYValue> array) {
        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(), 2))));
        int m = array.size() - 1;
        int count = 0;
        Log.d(TAG, "sortArray: Sorting the XYArray.");

        while (true) {
            m--;
            if (m <= 0) {
                m = array.size() - 1;
            }
            Log.d(TAG, "sortArray: m =" + m);
            try {
                double tempY = array.get(m - 1).getY();
                double tempX = array.get(m - 1).getX();
                if (tempX > array.get(m).getX()) {
                    array.get(m - 1).setY(array.get(m).getY());
                    array.get(m).setY(tempY);
                    array.get(m - 1).setX(array.get(m).getX());
                    array.get(m).setX(tempX);
                } else if (tempX == array.get(m).getX()) {
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                } else if (array.get(m).getX() > array.get(m - 1).getX()) {
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                }
                if (count == factor) {
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.e(TAG, "sortArray: ArrayIndexOutOfBoundException. Need More than 1 data point to create Plot."
                        + e.getMessage());
                break;
            }
        }
        return array;
    }


    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
