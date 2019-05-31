package com.example.acer.finalormaybe;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.google.firebase.iid.FirebaseInstanceId;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private float[] yData = {25.3f, 10.6f, 66.76f, 44.32f, 46.01f};
    private String[] xData = {"Extreme risks", "High risks", "Moderate risks", "Low risks", "No risks"};
    PieChart pieChart;
    TextView SVMsentence;
    ListView bastard;
    ArrayList <String> arrayList = new ArrayList<>();
    ArrayAdapter <String> adapter;
    ListView bastard1;
    ArrayList <String> arrayList1 = new ArrayList<>();
    ArrayAdapter <String> adapter1;
    private static String TAG = "MainActivity";
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mval1=mRootRef.child("Hedi").child("firstOne");
    DatabaseReference mval2=mRootRef.child("Hedi").child("secondOne");
    DatabaseReference mval3=mRootRef.child("Hedi").child("third");
    DatabaseReference mval4=mRootRef.child("Hedi").child("fourth");
    DatabaseReference mval5=mRootRef.child("Hedi").child("fifth");
    DatabaseReference sentence = mRootRef.child("SVM sentence");
    DatabaseReference city1 = mRootRef.child("cities");
    DatabaseReference word = mRootRef.child("words");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList1);
        setContentView(R.layout.activity_main);
        System.out.println("MainActivity.onCreate: " + FirebaseInstanceId.getInstance().getToken());
        pieChart = (PieChart) findViewById(R.id.idPieChart);
        SVMsentence = (TextView)findViewById(R.id.svmsentence);
        ArrayList<Entry>yValues = new ArrayList<>();
        ArrayList<ILineDataSet>dataSets=new ArrayList<>();
        SVMsentence.setTextColor(Color.BLACK);
        bastard = findViewById(R.id.list);
        bastard1 = findViewById(R.id.list1);
        bastard.setAdapter(adapter);
        bastard1.setAdapter(adapter1);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(50f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterTextSize(10);
        addDataSet();
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());
                int pos1 = e.toString().indexOf("sum(): ");
                String sales = e.toString().substring(pos1 + 17);
                for (int i = 0; i < yData.length; i++) {
                    if (yData[i] == Float.parseFloat(sales)) {
                        pos1 = i;
                        break;
                    }
                }
                String employee = xData[pos1];
                Toast.makeText(MainActivity.this,  employee + "\n" + "Probability" + sales + " %", Toast.LENGTH_LONG).show();
            }


            @Override
            public void onNothingSelected() {

            }
        });
    }

    //pieChart.setUsePercentValues(true);
    //pieChart.setHoleColor(Color.BLUE);
    //pieChart.setCenterTextColor(Color.BLACK);
    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , i));
        }

        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Danger");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(255,0,0));
        colors.add(Color.rgb(255,100,0));
        colors.add(Color.rgb(255,255,0));
        colors.add(Color.rgb(0,100,0));
        colors.add(Color.rgb(0,200,0));
        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

        @Override
    protected void onStart() {
        super.onStart();

       mval1.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               float text = dataSnapshot.getValue(float.class);
               yData[0]=text;
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

       mval2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    float text = dataSnapshot.getValue(float.class);
                    yData[1]=text;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
       mval3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    float text = dataSnapshot.getValue(float.class);
                    yData[2]=text;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
       mval4.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    float text = dataSnapshot.getValue(float.class);
                    yData[3]=text;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
       mval5.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    float text = dataSnapshot.getValue(float.class);
                    yData[4]=text;
                    addDataSet();
                    pieChart.notifyDataSetChanged();
                    pieChart.invalidate();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
       sentence.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String text = dataSnapshot.getValue(String.class);
               SVMsentence.setText(text);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
       city1.addValueEventListener(new ValueEventListener() {

           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               int i=0;
               for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   String user = snapshot.getKey();
                   int nbtweets = snapshot.getValue(int.class);
                   String nbtweets1 = String.valueOf(nbtweets);
                   String twoofthem = user + " "+nbtweets1;
                   adapter.insert(twoofthem,i);
                   i+=1;
               }
               adapter.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
       word.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               int i=0;
               for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   String user = snapshot.getKey();
                   int nbtweets = snapshot.getValue(int.class);
                   String nbtweets1 = String.valueOf(nbtweets);
                   String twoofthem = user + " "+nbtweets1;
                   adapter1.insert(twoofthem,i);
                   i+=1;
               }
               adapter1.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }
}
