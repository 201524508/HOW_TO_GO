package com.example.home.how_to_go;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapData.FindAllPOIListenerCallback;
import com.skp.Tmap.TMapView;
import com.skp.Tmap.TMapPoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.String;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import algo.Pathway;

/**
 * Created by HOME on 2017-04-05.
 */

public class SeekActivity extends AppCompatActivity {
    public SeekActivity(){
    }

    //TMap api 실행을 위한 api key
    private TMapView mMapView = null;
    public static String mApiKey = "73a7a315-a395-350e-9bff-14b10cd0f738";

    private void configureMapView() {
        mMapView.setSKPMapApiKey(mApiKey);
    }

    public ProgressDialog progressDialog;
    public ImageButton btn_bus;
    public ImageButton btn_car;
    public ImageButton add;
    public int cntInput;

    public EditText search_startpoint;
    public ImageButton btn_search_start;

    public EditText search_finishpoint;
    public ImageButton btn_search_finish;

    public EditText search_first;
    public ImageButton btn_search_1st;

    public EditText search_second;
    public ImageButton btn_search_2nd;

    public ImageView num3;
    public EditText search_third;
    public ImageButton btn_search_3th;
    public ImageButton btn_remove_3th;

    public ImageView num4;
    public EditText search_fourth;
    public ImageButton btn_search_4th;
    public ImageButton btn_remove_4th;

    final static int ACT_EDIT = 0;

    final String StartName[] = new String[7];
    final String StartAddress[] = new String[7];
    final double StartLatitude[] = new double[7];
    final double StartLongitude[] = new double[7];

    final String FinishName[] = new String[7];
    final String FinishAddress[] = new String[7];
    final double FinishLatitude[] = new double[7];
    final double FinishLongitude[] = new double[7];

    final String Stop1Name[] = new String[7];
    final String Stop1Address[] = new String[7];
    final double Stop1Latitude[] = new double[7];
    final double Stop1Longitude[] = new double[7];

    final String Stop2Name[] = new String[7];
    final String Stop2Address[] = new String[7];
    final double Stop2Latitude[] = new double[7];
    final double Stop2Longitude[] = new double[7];

    final String Stop3Name[] = new String[7];
    final String Stop3Address[] = new String[7];
    final double Stop3Latitude[] = new double[7];
    final double Stop3Longitude[] = new double[7];

    final String Stop4Name[] = new String[7];
    final String Stop4Address[] = new String[7];
    final double Stop4Latitude[] = new double[7];
    final double Stop4Longitude[] = new double[7];

    final String decidedStartName[] = new String[1];
    final double decidedStartLatitude[] = new double[1];
    final double decidedStartLongitude[] = new double[1];

    final String decidedFinishName[] = new String[1];
    final double decidedFinishLatitude[] = new double[1];
    final double decidedFinishLongitude[] = new double[1];

    final String decidedStopName[] = new String[4];
    final double decidedStopLatitude[] = new double[4];
    final double decidedStopLongitude[] = new double[4];

    int cnt = 0;

    //public String resultPath="";

    public String urlCarF = "";
    public String urlCarS = "";
    public String urlBusS = "";

    public String resultT[][];
    public String resultCC[][];
    public String resultCT[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("갈 곳을 입력해주세요");
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        //actionbar 뒤로가기 버튼 만들기
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        setContentView(R.layout.activity_seek);

        add = (ImageButton) findViewById(R.id.add);

        search_startpoint = (EditText) findViewById(R.id.search_startpoint);
        btn_search_start = (ImageButton) findViewById(R.id.btn_search_start);

        search_finishpoint = (EditText) findViewById(R.id.search_finishpoint);
        btn_search_finish = (ImageButton) findViewById(R.id.btn_search_finish);

        search_first = (EditText) findViewById(R.id.search_first);
        btn_search_1st = (ImageButton) findViewById(R.id.btn_search_1st);

        search_second = (EditText) findViewById(R.id.search_second);
        btn_search_2nd = (ImageButton) findViewById(R.id.btn_search_2nd);

        num3 = (ImageView) findViewById(R.id.num3);
        search_third = (EditText) findViewById(R.id.search_third);
        btn_search_3th = (ImageButton) findViewById(R.id.btn_search_3th);
        btn_remove_3th = (ImageButton) findViewById(R.id.btn_remove_3th);

        num4 = (ImageView) findViewById(R.id.num4);
        search_fourth = (EditText) findViewById(R.id.search_fourth);
        btn_search_4th = (ImageButton) findViewById(R.id.btn_search_4th);
        btn_remove_4th = (ImageButton) findViewById(R.id.btn_remove_4th);

        btn_bus = (ImageButton) findViewById(R.id.btn_bus);
        btn_car = (ImageButton) findViewById(R.id.btn_car);

        mMapView = new TMapView(this);
        configureMapView();

        //출발지
        btn_search_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = search_startpoint.getText().toString();

                //final Intent intent = new Intent(SeekActivity.this, LocationList.class);
                final Intent intent = new Intent(SeekActivity.this, SelectListviewActivity.class);
                LogManager.printLog(str);

                if (str == null || str.length() == 0) {
                    Toast.makeText(getApplicationContext(), "출발지를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    progressBarDialog2();
                    //Toast.makeText(getApplicationContext(), "입력성공 : " + str, Toast.LENGTH_SHORT).show();

                    //레이아웃을 위함... actionbar title 하려고..
                    intent.putExtra("str",str);

                    //cntInput++;
                    TMapData tmapdata = new TMapData();

                    tmapdata.findAllPOI(str, new FindAllPOIListenerCallback() {
                        @Override
                        public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {

                            if(poiItem.size() < 7) {
                                for (int i = 0; i < poiItem.size(); i++) {

                                    TMapPOIItem item = poiItem.get(i);

                                    LogManager.printLog(" POI Name: " + item.getPOIName().toString() + ", " +
                                            "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                            "Point: " + item.getPOIPoint().toString());

                                    TMapPoint point = item.getPOIPoint();
                                    double Latitude = point.getLatitude();
                                    double Longitude = point.getLongitude();

                                    StartName[i] = item.getPOIName().toString();
                                    StartAddress[i] = item.getPOIAddress().replace("null", "");
                                    StartLatitude[i] = Latitude;
                                    StartLongitude[i] = Longitude;
                                }
                            }
                            else {
                                for (int i = 0; i < 7; i++) {

                                    TMapPOIItem item = poiItem.get(i);

                                    LogManager.printLog(" POI Name: " + item.getPOIName().toString() + ", " +
                                            "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                            "Point: " + item.getPOIPoint().toString());

                                    TMapPoint point = item.getPOIPoint();
                                    double Latitude = point.getLatitude();
                                    double Longitude = point.getLongitude();

                                    StartName[i] = item.getPOIName().toString();
                                    StartAddress[i] = item.getPOIAddress().replace("null", "");
                                    StartLatitude[i] = Latitude;
                                    StartLongitude[i] = Longitude;
                                }
                            }

                            intent.putExtra("Name0", StartName[0]);
                            intent.putExtra("Name1", StartName[1]);
                            intent.putExtra("Name2", StartName[2]);
                            intent.putExtra("Name3", StartName[3]);
                            intent.putExtra("Name4", StartName[4]);
                            intent.putExtra("Name5", StartName[5]);
                            intent.putExtra("Name6", StartName[6]);

                            intent.putExtra("Address0", StartAddress[0]);
                            intent.putExtra("Address1", StartAddress[1]);
                            intent.putExtra("Address2", StartAddress[2]);
                            intent.putExtra("Address3", StartAddress[3]);
                            intent.putExtra("Address4", StartAddress[4]);
                            intent.putExtra("Address5", StartAddress[5]);
                            intent.putExtra("Address6", StartAddress[6]);
                            startActivityForResult(intent, ACT_EDIT);
                        }
                    });
                }


            }
        });

        btn_search_finish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = search_finishpoint.getText().toString();

                final Intent intent = new Intent(SeekActivity.this, SelectListviewActivity.class);

                if (str == null || str.length() == 0) {
                    Toast.makeText(getApplicationContext(), "도착지를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    progressBarDialog2();
                    //Toast.makeText(getApplicationContext(), "입력성공 : " + str, Toast.LENGTH_SHORT).show();

                    //레이아웃을 위함... actionbar title 하려고..
                    intent.putExtra("str",str);

                    TMapData tmapdata = new TMapData();

                    tmapdata.findAllPOI(str, new FindAllPOIListenerCallback() {
                        @Override
                        public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {

                            if(poiItem.size() < 7) {
                                for (int i = 0; i < poiItem.size(); i++) {
                                    TMapPOIItem item = poiItem.get(i);

                                    LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                            "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                            "Point: " + item.getPOIPoint().toString());

                                    TMapPoint point = item.getPOIPoint();
                                    double Latitude = point.getLatitude();
                                    double Longitude = point.getLongitude();

                                    FinishName[i] = item.getPOIName().toString();
                                    FinishAddress[i] = item.getPOIAddress().replace("null", "");
                                    FinishLatitude[i] = Latitude;
                                    FinishLongitude[i] = Longitude;
                                }
                            }
                            else {
                                for (int i = 0; i < 7; i++) {
                                    TMapPOIItem item = poiItem.get(i);

                                    LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                            "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                            "Point: " + item.getPOIPoint().toString());

                                    TMapPoint point = item.getPOIPoint();
                                    double Latitude = point.getLatitude();
                                    double Longitude = point.getLongitude();

                                    FinishName[i] = item.getPOIName().toString();
                                    FinishAddress[i] = item.getPOIAddress().replace("null", "");
                                    FinishLatitude[i] = Latitude;
                                    FinishLongitude[i] = Longitude;
                                }
                            }

                            intent.putExtra("Name0", FinishName[0]);
                            intent.putExtra("Name1", FinishName[1]);
                            intent.putExtra("Name2", FinishName[2]);
                            intent.putExtra("Name3", FinishName[3]);
                            intent.putExtra("Name4", FinishName[4]);
                            intent.putExtra("Name5", FinishName[5]);
                            intent.putExtra("Name6", FinishName[6]);

                            intent.putExtra("Address0", FinishAddress[0]);
                            intent.putExtra("Address1", FinishAddress[1]);
                            intent.putExtra("Address2", FinishAddress[2]);
                            intent.putExtra("Address3", FinishAddress[3]);
                            intent.putExtra("Address4", FinishAddress[4]);
                            intent.putExtra("Address5", FinishAddress[5]);
                            intent.putExtra("Address6", FinishAddress[6]);

                            startActivityForResult(intent, ACT_EDIT);
                        }
                    });
                }
            }
        });

        btn_search_1st.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = search_first.getText().toString();

                final Intent intent = new Intent(SeekActivity.this, SelectListviewActivity.class);

                //레이아웃을 위함... actionbar title 하려고..


                if (str == null || str.length() == 0) {
                    Toast.makeText(getApplicationContext(), "경유지를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    progressBarDialog2();
                    //Toast.makeText(getApplicationContext(), "입력성공 : " + str, Toast.LENGTH_SHORT).show();

                    intent.putExtra("str",str);

                    TMapData tmapdata = new TMapData();



                    tmapdata.findAllPOI(str, new FindAllPOIListenerCallback() {
                        @Override
                        public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {

                            if(poiItem.size()<7) {
                                for (int i = 0; i < poiItem.size(); i++) {
                                    TMapPOIItem item = poiItem.get(i);

                                    LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                            "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                            "Point: " + item.getPOIPoint().toString());

                                    TMapPoint point = item.getPOIPoint();
                                    double Latitude = point.getLatitude();
                                    double Longitude = point.getLongitude();

                                    Stop1Name[i] = item.getPOIName().toString();
                                    Stop1Address[i] = item.getPOIAddress().replace("null", "");
                                    Stop1Latitude[i] = Latitude;
                                    Stop1Longitude[i] = Longitude;
                                }
                            }
                            else {
                                for (int i = 0; i < 7; i++) {
                                    TMapPOIItem item = poiItem.get(i);

                                    LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                            "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                            "Point: " + item.getPOIPoint().toString());

                                    TMapPoint point = item.getPOIPoint();
                                    double Latitude = point.getLatitude();
                                    double Longitude = point.getLongitude();

                                    Stop1Name[i] = item.getPOIName().toString();
                                    Stop1Address[i] = item.getPOIAddress().replace("null", "");
                                    Stop1Latitude[i] = Latitude;
                                    Stop1Longitude[i] = Longitude;
                                }
                            }

                            intent.putExtra("Name0", Stop1Name[0]);
                            intent.putExtra("Name1", Stop1Name[1]);
                            intent.putExtra("Name2", Stop1Name[2]);
                            intent.putExtra("Name3", Stop1Name[3]);
                            intent.putExtra("Name4", Stop1Name[4]);
                            intent.putExtra("Name5", Stop1Name[5]);
                            intent.putExtra("Name6", Stop1Name[6]);

                            intent.putExtra("Address0", Stop1Address[0]);
                            intent.putExtra("Address1", Stop1Address[1]);
                            intent.putExtra("Address2", Stop1Address[2]);
                            intent.putExtra("Address3", Stop1Address[3]);
                            intent.putExtra("Address4", Stop1Address[4]);
                            intent.putExtra("Address5", Stop1Address[5]);
                            intent.putExtra("Address6", Stop1Address[6]);

                            startActivityForResult(intent, ACT_EDIT);
                        }
                    });
                }
            }
        });

        btn_search_2nd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = search_second.getText().toString();

                final Intent intent = new Intent(SeekActivity.this, SelectListviewActivity.class);



                if (str == null || str.length() == 0) {
                    Toast.makeText(getApplicationContext(), "경유지를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    progressBarDialog2();
                    //Toast.makeText(getApplicationContext(), "입력성공 : " + str, Toast.LENGTH_SHORT).show();

                    //레이아웃을 위함... actionbar title 하려고..
                    intent.putExtra("str",str);

                    TMapData tmapdata = new TMapData();



                    tmapdata.findAllPOI(str, new FindAllPOIListenerCallback() {
                        @Override
                        public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {

                            if(poiItem.size()<7) {
                                for (int i = 0; i < poiItem.size(); i++) {
                                    TMapPOIItem item = poiItem.get(i);

                                    LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                            "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                            "Point: " + item.getPOIPoint().toString());

                                    TMapPoint point = item.getPOIPoint();
                                    double Latitude = point.getLatitude();
                                    double Longitude = point.getLongitude();

                                    Stop2Name[i] = item.getPOIName().toString();
                                    Stop2Address[i] = item.getPOIAddress().replace("null", "");
                                    Stop2Latitude[i] = Latitude;
                                    Stop2Longitude[i] = Longitude;
                                }
                            }
                            else {
                                for (int i = 0; i < 7; i++) {
                                    TMapPOIItem item = poiItem.get(i);

                                    LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                            "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                            "Point: " + item.getPOIPoint().toString());

                                    TMapPoint point = item.getPOIPoint();
                                    double Latitude = point.getLatitude();
                                    double Longitude = point.getLongitude();

                                    Stop2Name[i] = item.getPOIName().toString();
                                    Stop2Address[i] = item.getPOIAddress().replace("null", "");
                                    Stop2Latitude[i] = Latitude;
                                    Stop2Longitude[i] = Longitude;
                                }
                            }

                            intent.putExtra("Name0", Stop2Name[0]);
                            intent.putExtra("Name1", Stop2Name[1]);
                            intent.putExtra("Name2", Stop2Name[2]);
                            intent.putExtra("Name3", Stop2Name[3]);
                            intent.putExtra("Name4", Stop2Name[4]);
                            intent.putExtra("Name5", Stop2Name[5]);
                            intent.putExtra("Name6", Stop2Name[6]);

                            intent.putExtra("Address0", Stop2Address[0]);
                            intent.putExtra("Address1", Stop2Address[1]);
                            intent.putExtra("Address2", Stop2Address[2]);
                            intent.putExtra("Address3", Stop2Address[3]);
                            intent.putExtra("Address4", Stop2Address[4]);
                            intent.putExtra("Address5", Stop2Address[5]);
                            intent.putExtra("Address6", Stop2Address[6]);

                            startActivityForResult(intent, ACT_EDIT);
                        }
                    });
                }
            }
        });

        btn_search_3th.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = search_third.getText().toString();

                final Intent intent = new Intent(SeekActivity.this, SelectListviewActivity.class);

                if (str == null || str.length() == 0) {
                    Toast.makeText(getApplicationContext(), "경유지를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    progressBarDialog2();
                    //Toast.makeText(getApplicationContext(), "입력성공 : " + str, Toast.LENGTH_SHORT).show();

                    //레이아웃을 위함... actionbar title 하려고..
                    intent.putExtra("str",str);
                    TMapData tmapdata = new TMapData();



                    tmapdata.findAllPOI(str, new FindAllPOIListenerCallback() {
                        @Override
                        public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {

                            if(poiItem.size()<7) {
                                for (int i = 0; i < poiItem.size(); i++) {
                                    TMapPOIItem item = poiItem.get(i);

                                    LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                            "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                            "Point: " + item.getPOIPoint().toString());

                                    TMapPoint point = item.getPOIPoint();
                                    double Latitude = point.getLatitude();
                                    double Longitude = point.getLongitude();

                                    Stop3Name[i] = item.getPOIName().toString();
                                    Stop3Address[i] = item.getPOIAddress().replace("null", "");
                                    Stop3Latitude[i] = Latitude;
                                    Stop3Longitude[i] = Longitude;
                                }
                            }
                            else {
                                for (int i = 0; i < 7; i++) {
                                    TMapPOIItem item = poiItem.get(i);

                                    LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                            "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                            "Point: " + item.getPOIPoint().toString());

                                    TMapPoint point = item.getPOIPoint();
                                    double Latitude = point.getLatitude();
                                    double Longitude = point.getLongitude();

                                    Stop3Name[i] = item.getPOIName().toString();
                                    Stop3Address[i] = item.getPOIAddress().replace("null", "");
                                    Stop3Latitude[i] = Latitude;
                                    Stop3Longitude[i] = Longitude;
                                }
                            }

                            intent.putExtra("Name0", Stop3Name[0]);
                            intent.putExtra("Name1", Stop3Name[1]);
                            intent.putExtra("Name2", Stop3Name[2]);
                            intent.putExtra("Name3", Stop3Name[3]);
                            intent.putExtra("Name4", Stop3Name[4]);
                            intent.putExtra("Name5", Stop3Name[5]);
                            intent.putExtra("Name6", Stop3Name[6]);

                            intent.putExtra("Address0", Stop3Address[0]);
                            intent.putExtra("Address1", Stop3Address[1]);
                            intent.putExtra("Address2", Stop3Address[2]);
                            intent.putExtra("Address3", Stop3Address[3]);
                            intent.putExtra("Address4", Stop3Address[4]);
                            intent.putExtra("Address5", Stop3Address[5]);
                            intent.putExtra("Address6", Stop3Address[6]);

                            startActivityForResult(intent, ACT_EDIT);
                        }
                    });
                }
            }
        });

        btn_search_4th.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = search_fourth.getText().toString();

                final Intent intent = new Intent(SeekActivity.this, SelectListviewActivity.class);

                if (str == null || str.length() == 0) {
                    Toast.makeText(getApplicationContext(), "경유지를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    progressBarDialog2();
                    //Toast.makeText(getApplicationContext(), "입력성공 : " + str, Toast.LENGTH_SHORT).show();

                    //레이아웃을 위함... actionbar title 하려고..
                    intent.putExtra("str",str);

                    TMapData tmapdata = new TMapData();

                    tmapdata.findAllPOI(str, new FindAllPOIListenerCallback() {
                        @Override
                        public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {

                            if(poiItem.size()<7) {
                                for (int i = 0; i < poiItem.size(); i++) {
                                    TMapPOIItem item = poiItem.get(i);

                                    LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                            "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                            "Point: " + item.getPOIPoint().toString());

                                    TMapPoint point = item.getPOIPoint();
                                    double Latitude = point.getLatitude();
                                    double Longitude = point.getLongitude();

                                    Stop4Name[i] = item.getPOIName().toString();
                                    Stop4Address[i] = item.getPOIAddress().replace("null", "");
                                    Stop4Latitude[i] = Latitude;
                                    Stop4Longitude[i] = Longitude;
                                }
                            }
                            else {
                                for (int i = 0; i < 7; i++) {
                                    TMapPOIItem item = poiItem.get(i);

                                    LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                            "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                            "Point: " + item.getPOIPoint().toString());

                                    TMapPoint point = item.getPOIPoint();
                                    double Latitude = point.getLatitude();
                                    double Longitude = point.getLongitude();

                                    Stop4Name[i] = item.getPOIName().toString();
                                    Stop4Address[i] = item.getPOIAddress().replace("null", "");
                                    Stop4Latitude[i] = Latitude;
                                    Stop4Longitude[i] = Longitude;
                                }
                            }

                            intent.putExtra("Name0", Stop4Name[0]);
                            intent.putExtra("Name1", Stop4Name[1]);
                            intent.putExtra("Name2", Stop4Name[2]);
                            intent.putExtra("Name3", Stop4Name[3]);
                            intent.putExtra("Name4", Stop4Name[4]);
                            intent.putExtra("Name5", Stop4Name[5]);
                            intent.putExtra("Name6", Stop4Name[6]);

                            intent.putExtra("Address0", Stop4Address[0]);
                            intent.putExtra("Address1", Stop4Address[1]);
                            intent.putExtra("Address2", Stop4Address[2]);
                            intent.putExtra("Address3", Stop4Address[3]);
                            intent.putExtra("Address4", Stop4Address[4]);
                            intent.putExtra("Address5", Stop4Address[5]);
                            intent.putExtra("Address6", Stop4Address[6]);

                            startActivityForResult(intent, ACT_EDIT);
                        }
                    });
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cnt++;
                cnt = addPath(cnt);
                Toast.makeText(getApplicationContext(), "경유지 3, 4 삭제는 \'-\'를 눌러주세요.", Toast.LENGTH_SHORT).show();
            }
        });

        btn_remove_3th.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(decidedStopName[3] != null && decidedStopLatitude[3] != 0 && decidedStopLongitude[3] != 0 ) {
                    //3번째 OutName == 4번째 OutName;
                    decidedStopName[2] = decidedStopName[3];
                    decidedStopLatitude[2] = decidedStopLatitude[3];
                    decidedStopLongitude[2] = decidedStopLongitude[3];

                    LogManager.printLog(decidedStopName[2]);

                    decidedStopName[3] = null;
                    decidedStopLatitude[3] = 0.0;
                    decidedStopLongitude[3] = 0.0;

                    search_third.setText(decidedStopName[2]);

                    search_fourth.setText("");
                    search_fourth.setText(null);
                }
                else {
                    search_third.setText("");
                    search_third.setText(null);

                    decidedStopName[2] = null;
                    decidedStopLatitude[2] = 0.0;
                    decidedStopLongitude[2] = 0.0;
                }

                removePath(cnt);
                cnt--;
            }
        });

        btn_remove_4th.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_fourth.setText("");
                search_fourth.setText(null);

                decidedStopName[3] = null;
                decidedStopLatitude[3] = 0.0;
                decidedStopLongitude[3] = 0.0;

                removePath(cnt);
                cnt--;
            }
        });

        //google api
        btn_bus.setOnClickListener(new View.OnClickListener() {
            Pathway pathTrans = new Pathway();
            @Override
            public void onClick(View v) {

                pathTrans = new Pathway();
                if(search_finishpoint.getText().toString() != null){
                    cntInput = 1;
                }
                else{
                    decidedStartLatitude[0] = 0;
                    decidedStartLongitude[0] = 0;
                    decidedStartName[0] = null;

                    cntInput = 0;
                }

                String[] tmp = new String[4];
                if(search_first.getText().toString() != null){
                    tmp[0] = search_first.getText().toString();
                }
                else{
                    decidedStopLatitude[0] = 0;
                    decidedStopLongitude[0] = 0;
                    decidedStopName[0] = null;
                }

                if(search_second.getText().toString() != null){
                    tmp[1] = search_second.getText().toString();
                }
                else{
                    decidedStopLatitude[1] = 0;
                    decidedStopLongitude[1] = 0;
                    decidedStopName[1] = null;
                }

                if(search_third.getText().toString() != null){
                    tmp[2] = search_third.getText().toString();
                }
                else{
                    decidedStopLatitude[2] = 0;
                    decidedStopLongitude[2] = 0;
                    decidedStopName[2] = null;
                }

                if(search_fourth.getText().toString() != null){
                    tmp[3] = search_fourth.getText().toString();
                }
                else{
                    decidedStopLatitude[3] = 0;
                    decidedStopLongitude[3] = 0;
                    decidedStopName[3] = null;
                }

                for(int i = 0; i < 4; i++){
                    if(decidedStopName[i] != null && decidedStopLongitude[i] != 0.0 && decidedStopLatitude[i] != 0.0 && tmp[i] != null){
                        cntInput++;
                        //System.out.println("cntInput : " + cntInput);
                    }
                }

                //경유지 없는경우
                if (cntInput <= 2) {
                    //LogManager.printLog("경유지X -> 결과 1개");
                    Toast.makeText(getApplicationContext(), "도착지와 경유지를 최소 2군데 이상 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(search_startpoint.getText().toString().equals(search_first.getText().toString())
                            && search_startpoint.getText().toString().equals(search_second.getText().toString())
                            && search_startpoint.getText().toString().equals(search_finishpoint.getText().toString())){
                        Toast.makeText(getApplicationContext(), "출발지 또는 도착지와 경유지는 다르게 설정해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if (search_startpoint.getText().toString() == null || search_startpoint.getText().toString().length() <= 0) {
                            Toast.makeText(getApplicationContext(), "출발지가 입력되지 않았습니다", Toast.LENGTH_SHORT).show();
                        }
                        else if (search_finishpoint.getText().toString() == null || search_finishpoint.getText().toString().length() <= 0) {
                            Toast.makeText(getApplicationContext(), "목적지가 입력되지 않았습니다", Toast.LENGTH_SHORT).show();
                        }
                        else if (search_first.getText().toString() == null || search_first.getText().toString().length() <= 0) {
                            Toast.makeText(getApplicationContext(), "경유지1가 입력되지 않았습니다", Toast.LENGTH_SHORT).show();
                        }
                        else if (search_second.getText().toString() == null || search_second.getText().toString().length() <= 0) {
                            Toast.makeText(getApplicationContext(), "경유지2가 입력되지 않았습니다", Toast.LENGTH_SHORT).show();
                        }
                        else if (search_startpoint.getText().toString() != null && search_finishpoint.getText().toString() != null &&
                                search_first.getText().toString() != null && search_first.getText().toString() != null && search_second.getText().toString() != null ){
                            progressBarDialog();
                            pathTrans.setPathway(cntInput + 1);

                            for (int i = 0; i < cntInput - 1; i++) {
                                if (decidedStopName[i] != null) {
                                    //LogManager.printLog("start : " + decidedStartName[0] + decidedStartLatitude[0]+ " " +decidedStartLongitude[0] +
                                    //        " stop" +i + " " + decidedStopName[i] + decidedStopLatitude[i]+ " " + decidedStopLongitude[i]);

                                    urlBusS = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial"
                                            + "&origins=" + decidedStartLatitude[0] + "," + decidedStartLongitude[0]
                                            + "&destinations=" + decidedStopLatitude[i] + "," + decidedStopLongitude[i]
                                            + "&key=AIzaSyBN7CGEqgSPoED5753LZG3DQJJ3umuGSrk" + "&language=ko" + "&mode=transit&transit_mode=subway";

                                    //System.out.println(urlBusS);

                                    pathTrans.findBusShort(decidedStartName[0], 0, decidedStopName[i], (i + 1), urlBusS);
                                } else {
                                    break;
                                }
                            }

                            //Stop to Stop
                            for (int i = 0; i < cntInput - 2; i++) {
                                for (int j = i + 1; j < cntInput - 1; j++) {
                                    if ((decidedStopName[i] != null && decidedStopName[j] != null) || (tmp[i] != null && tmp[j] != null)) {
                                        // LogManager.printLog("stop" + i + ": " + decidedStopName[i] + " " + decidedStopLatitude[i]+ " " +decidedStopLongitude[i]
                                        //       + " stop" + j + ": " + decidedStopName[j] + decidedStopLatitude[j]+ " " +decidedStopLongitude[j]);

                                        urlBusS = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial"
                                                + "&origins=" + decidedStopLatitude[i] + "," + decidedStopLongitude[i]
                                                + "&destinations=" + decidedStopLatitude[j] + "," + decidedStopLongitude[j]
                                                + "&key=AIzaSyBN7CGEqgSPoED5753LZG3DQJJ3umuGSrk" + "&language=ko" + "&mode=transit&transit_mode=subway";

                                        pathTrans.findBusShort(decidedStopName[i], (i + 1), decidedStopName[j], (j + 1), urlBusS);
                                    } else {
                                        break;
                                    }
                                }
                            }

                            //Stop to Finish
                            for (int i = 0; i < cntInput - 1; i++) {
                                if (decidedStopName[i] != null) {
                                    //LogManager.printLog("stop " + i + " : " + decidedStopName[i] + decidedStopLatitude[i]+ " " + decidedStopLongitude[i]
                                    //        + " finish: " + decidedFinishName[0] + decidedFinishLatitude[0] + " " + decidedFinishLongitude[0]);

                                    urlBusS = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial"
                                            + "&origins=" + decidedStopLatitude[i] + "," + decidedStopLongitude[i]
                                            + "&destinations=" + decidedFinishLatitude[0] + "," + decidedFinishLongitude[0]
                                            + "&key=AIzaSyBN7CGEqgSPoED5753LZG3DQJJ3umuGSrk" + "&language=ko" + "&mode=transit&transit_mode=subway";

                                    pathTrans.findBusShort(decidedStopName[i], (i + 1), decidedFinishName[0], cntInput, urlBusS);
                                } else {
                                    break;
                                }
                            }

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    pathTrans.setWay();
                                    resultT = pathTrans.shTimeT();

                                    Handler h = new Handler();
                                    h.postDelayed(new Runnable(){
                                        public void run(){
                                            Intent intent = new Intent(SeekActivity.this, SearchBusActivity.class);
                                            intent.putExtra("path", resultT);
                                            startActivity(intent);
                                        }
                                    }, 500);
                                }
                            }, 4500);
                        }
                    }


                }

            }
        });

        //tmap
        btn_car.setOnClickListener(new View.OnClickListener() {

            Pathway pathCarCost = new Pathway();
            Pathway pathCarTime = new Pathway();
            @Override
            public void onClick(View v) {
                pathCarCost = new Pathway();
                pathCarTime = new Pathway();
                if(search_finishpoint.getText().toString() != null){
                    cntInput = 1;
                }
                else{
                    decidedStartLatitude[0] = 0;
                    decidedStartLongitude[0] = 0;
                    decidedStartName[0] = null;

                    cntInput = 0;
                }

                String[] tmp = new String[4];
                if(search_first.getText().toString() != null){
                    tmp[0] = search_first.getText().toString();
                }
                else{
                    decidedStopLatitude[0] = 0;
                    decidedStopLongitude[0] = 0;
                    decidedStopName[0] = null;
                }

                if(search_second.getText().toString() != null){
                    tmp[1] = search_second.getText().toString();
                }
                else{
                    decidedStopLatitude[1] = 0;
                    decidedStopLongitude[1] = 0;
                    decidedStopName[1] = null;
                }

                if(search_third.getText().toString() != null){
                    tmp[2] = search_third.getText().toString();
                }
                else{
                    decidedStopLatitude[2] = 0;
                    decidedStopLongitude[2] = 0;
                    decidedStopName[2] = null;
                }

                if(search_fourth.getText().toString() != null){
                    tmp[3] = search_fourth.getText().toString();
                }
                else{
                    decidedStopLatitude[3] = 0;
                    decidedStopLongitude[3] = 0;
                    decidedStopName[3] = null;
                }

                for(int i = 0; i < 4; i++){
                    if(decidedStopName[i] != null && decidedStopLongitude[i] != 0.0 && decidedStopLatitude[i] != 0.0){
                        cntInput++;
                    }
                }

                System.out.println("cntInput : " + cntInput);


                //경유지 없는경우
                if (cntInput <= 2) {
                    Toast.makeText(getApplicationContext(), "도착지와 경유지를 최소 2군데 이상 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(search_startpoint.getText().toString().equals(search_first.getText().toString())
                            && search_startpoint.getText().toString().equals(search_second.getText().toString())
                            && search_startpoint.getText().toString().equals(search_finishpoint.getText().toString())){
                        Toast.makeText(getApplicationContext(), "출발지 또는 도착지와 경유지는 다르게 설정해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if (search_startpoint.getText().toString() == null || search_startpoint.getText().toString().length() <= 0) {
                            Toast.makeText(getApplicationContext(), "출발지가 입력되지 않았습니다", Toast.LENGTH_SHORT).show();
                        }
                        else if (search_finishpoint.getText().toString() == null || search_finishpoint.getText().toString().length() <= 0) {
                            Toast.makeText(getApplicationContext(), "목적지가 입력되지 않았습니다", Toast.LENGTH_SHORT).show();
                        }
                        else if (search_first.getText().toString() == null || search_first.getText().toString().length() <= 0) {
                            Toast.makeText(getApplicationContext(), "경유지1가 입력되지 않았습니다", Toast.LENGTH_SHORT).show();
                        }
                        else if (search_second.getText().toString() == null || search_second.getText().toString().length() <= 0) {
                            Toast.makeText(getApplicationContext(), "경유지2가 입력되지 않았습니다", Toast.LENGTH_SHORT).show();
                        }
                        else if(search_startpoint.getText().toString() != null && search_finishpoint.getText().toString() != null &&
                                search_first.getText().toString() != null && search_first.getText().toString() != null && search_second.getText().toString() != null ){
                            progressBarDialog();
                            pathCarCost.setPathway(cntInput+1);
                            pathCarTime.setPathway(cntInput+1);

                            //StartPoint to Stop
                            for (int i = 0; i < cntInput-1; i++) {
                                if (decidedStopName[i] != null || tmp[i] != null) {
                                    //LogManager.printLog("start : " + decidedStartName[0] + decidedStartLatitude[0]+ " " +decidedStartLongitude[0] +
                                    //        " stop" +i + " " + decidedStopName[i] + decidedStopLatitude[i]+ " " + decidedStopLongitude[i]);

                                    //X : longitude, Y:latitude
                                    //무료우선 -> 최소비용 : F
                                    urlCarF = "https://apis.skplanetx.com/tmap/routes?version=1&appKey=73a7a315-a395-350e-9bff-14b10cd0f738"
                                            + "&startX=" + decidedStartLongitude[0] + "&startY=" + decidedStartLatitude[0]
                                            + "&endX=" + decidedStopLongitude[i] + "&endY=" + decidedStopLatitude[i]
                                            + "&reqCoordType=WGS84GEO&resCoordType=WGS84GEO&tollgateFareOption=8&searchOption=1";
                                    //최소시간 -> 최단거리 : S
                                    urlCarS = "https://apis.skplanetx.com/tmap/routes?version=1&appKey=73a7a315-a395-350e-9bff-14b10cd0f738"
                                            + "&startX=" + decidedStartLongitude[0] + "&startY=" + decidedStartLatitude[0]
                                            + "&endX=" + decidedStopLongitude[i] + "&endY=" + decidedStopLatitude[i]
                                            + "&reqCoordType=WGS84GEO&resCoordType=WGS84GEO&searchOption=2";

                                    //LogManager.printLog("Start Label 0 to Stop" + (i+1));
                                    pathCarCost.findCarFree(decidedStartName[0], 0, decidedStopName[i], (i + 1), urlCarF);
                                    pathCarTime.findCarShort(decidedStartName[0], 0, decidedStopName[i], (i + 1), urlCarS);
                                } else {
                                    break;
                                }
                            }

                            //Stop to Stop
                            for (int i = 0; i < cntInput-2; i++) {
                                for (int j = i + 1; j < cntInput -1 ; j++) {
                                    if ((decidedStopName[i] != null && decidedStopName[j] != null) || (tmp[i] != null && tmp[j] != null)) {
                                        // LogManager.printLog("stop" + i + ": " + decidedStopName[i] + " " + decidedStopLatitude[i]+ " " +decidedStopLongitude[i]
                                        //       + " stop" + j + ": " + decidedStopName[j] + decidedStopLatitude[j]+ " " +decidedStopLongitude[j]);

                                        //X : longitude, Y:latitude
                                        //무료우선 -> 최소비용 : F
                                        urlCarF = "https://apis.skplanetx.com/tmap/routes?version=1&appKey=73a7a315-a395-350e-9bff-14b10cd0f738"
                                                + "&startX=" + decidedStopLongitude[i] + "&startY=" + decidedStopLatitude[i]
                                                + "&endX=" + decidedStopLongitude[j] + "&endY=" + decidedStopLatitude[j]
                                                + "&reqCoordType=WGS84GEO&resCoordType=WGS84GEO&tollgateFareOption=8&searchOption=1";
                                        //최소시간 -> 최단거리 : S
                                        urlCarS = "https://apis.skplanetx.com/tmap/routes?version=1&appKey=73a7a315-a395-350e-9bff-14b10cd0f738"
                                                + "&startX=" + decidedStopLongitude[i] + "&startY=" + decidedStopLatitude[i]
                                                + "&endX=" + decidedStopLongitude[j] + "&endY=" + decidedStopLatitude[j]
                                                + "&reqCoordType=WGS84GEO&resCoordType=WGS84GEO&searchOption=2";

                                        //LogManager.printLog("StopLabel" + i+1 + " to Stop" + j+1);
                                        pathCarCost.findCarFree(decidedStopName[i], (i + 1), decidedStopName[j], (j + 1), urlCarF);
                                        pathCarTime.findCarShort(decidedStopName[i], (i + 1), decidedStopName[j], (j + 1), urlCarS);
                                    } else {
                                        break;
                                    }
                                }
                            }

                            //Stop to Finish
                            for (int i = 0; i < cntInput - 1; i++) {
                                if (decidedStopName[i] != null || tmp[i] != null) {

                                    //무료우선 -> 최소비용 : F
                                    urlCarF = "https://apis.skplanetx.com/tmap/routes?version=1&appKey=73a7a315-a395-350e-9bff-14b10cd0f738"
                                            + "&startX=" + decidedStopLongitude[i] + "&startY=" + decidedStopLatitude[i]
                                            + "&endX=" + decidedFinishLongitude[0] + "&endY=" + decidedFinishLatitude[0]
                                            + "&reqCoordType=WGS84GEO&resCoordType=WGS84GEO&tollgateFareOption=8&searchOption=1";
                                    //최소시간 -> 최단거리 : S
                                    urlCarS = "https://apis.skplanetx.com/tmap/routes?version=1&appKey=73a7a315-a395-350e-9bff-14b10cd0f738"
                                            + "&startX=" + decidedStopLongitude[i] + "&startY=" + decidedStopLatitude[i]
                                            + "&endX=" + decidedFinishLongitude[0] + "&endY=" + decidedFinishLatitude[0]
                                            + "&reqCoordType=WGS84GEO&resCoordType=WGS84GEO&searchOption=2";

                                    //LogManager.printLog("StopLabel" + (i+1) + " to finish" + cntInput);
                                    pathCarCost.findCarFree(decidedStopName[i], i + 1, decidedFinishName[0], cntInput, urlCarF);
                                    pathCarTime.findCarShort(decidedStopName[i], i + 1, decidedFinishName[0], cntInput, urlCarS);
                                } else {
                                    break;
                                }
                            }

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable(){
                                public void run(){

                                    pathCarCost.setWay();
                                    pathCarTime.setWay();
                                    resultCC = pathCarCost.chCarCost();
                                    resultCT = pathCarTime.shTimeC();

                                    Handler h = new Handler();
                                    h.postDelayed(new Runnable(){
                                        public void run(){
                                            Intent intent = new Intent(SeekActivity.this, SearchCarActivity.class);
                                            intent.putExtra("pathCC", resultCC);
                                            intent.putExtra("pathCT", resultCT);
                                            startActivity(intent);

                                        }
                                    }, 500);
                                }
                            }, 5000);
                        }
                    }


                }

            }

        });

        search_startpoint.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        search_finishpoint.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        search_first.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        search_second.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        search_third.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        search_fourth.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        search_startpoint.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if(id == EditorInfo.IME_ACTION_SEARCH) {
                    String str = search_startpoint.getText().toString();

                    //final Intent intent = new Intent(SeekActivity.this, LocationList.class);
                    final Intent intent = new Intent(SeekActivity.this, SelectListviewActivity.class);
                    LogManager.printLog(str);

                    if (str == null || str.length() == 0) {
                        Toast.makeText(getApplicationContext(), "출발지를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        progressBarDialog2();
                        //Toast.makeText(getApplicationContext(), "입력성공 : " + str, Toast.LENGTH_SHORT).show();

                        //레이아웃을 위함... actionbar title 하려고..
                        intent.putExtra("str",str);

                        //cntInput++;
                        TMapData tmapdata = new TMapData();

                        tmapdata.findAllPOI(str, new FindAllPOIListenerCallback() {
                            @Override
                            public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {

                                if(poiItem.size() < 7) {
                                    for (int i = 0; i < poiItem.size(); i++) {

                                        TMapPOIItem item = poiItem.get(i);

                                        LogManager.printLog(" POI Name: " + item.getPOIName().toString() + ", " +
                                                "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                                "Point: " + item.getPOIPoint().toString());

                                        TMapPoint point = item.getPOIPoint();
                                        double Latitude = point.getLatitude();
                                        double Longitude = point.getLongitude();

                                        StartName[i] = item.getPOIName().toString();
                                        StartAddress[i] = item.getPOIAddress().replace("null", "");
                                        StartLatitude[i] = Latitude;
                                        StartLongitude[i] = Longitude;
                                    }
                                }
                                else {
                                    for (int i = 0; i < 7; i++) {

                                        TMapPOIItem item = poiItem.get(i);

                                        LogManager.printLog(" POI Name: " + item.getPOIName().toString() + ", " +
                                                "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                                "Point: " + item.getPOIPoint().toString());

                                        TMapPoint point = item.getPOIPoint();
                                        double Latitude = point.getLatitude();
                                        double Longitude = point.getLongitude();

                                        StartName[i] = item.getPOIName().toString();
                                        StartAddress[i] = item.getPOIAddress().replace("null", "");
                                        StartLatitude[i] = Latitude;
                                        StartLongitude[i] = Longitude;
                                    }
                                }

                                intent.putExtra("Name0", StartName[0]);
                                intent.putExtra("Name1", StartName[1]);
                                intent.putExtra("Name2", StartName[2]);
                                intent.putExtra("Name3", StartName[3]);
                                intent.putExtra("Name4", StartName[4]);
                                intent.putExtra("Name5", StartName[5]);
                                intent.putExtra("Name6", StartName[6]);

                                intent.putExtra("Address0", StartAddress[0]);
                                intent.putExtra("Address1", StartAddress[1]);
                                intent.putExtra("Address2", StartAddress[2]);
                                intent.putExtra("Address3", StartAddress[3]);
                                intent.putExtra("Address4", StartAddress[4]);
                                intent.putExtra("Address5", StartAddress[5]);
                                intent.putExtra("Address6", StartAddress[6]);
                                startActivityForResult(intent, ACT_EDIT);
                            }
                        });
                    }
                    return true;
                }
                return false;
            }
        });

        search_finishpoint.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if(id == EditorInfo.IME_ACTION_SEARCH) {
                    String str = search_finishpoint.getText().toString();

                    final Intent intent = new Intent(SeekActivity.this, SelectListviewActivity.class);

                    if (str == null || str.length() == 0) {
                        Toast.makeText(getApplicationContext(), "도착지를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        progressBarDialog2();
                        //Toast.makeText(getApplicationContext(), "입력성공 : " + str, Toast.LENGTH_SHORT).show();

                        //레이아웃을 위함... actionbar title 하려고..
                        intent.putExtra("str",str);

                        TMapData tmapdata = new TMapData();

                        tmapdata.findAllPOI(str, new FindAllPOIListenerCallback() {
                            @Override
                            public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {

                                if(poiItem.size() < 7) {
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);

                                        LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                                "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                                "Point: " + item.getPOIPoint().toString());

                                        TMapPoint point = item.getPOIPoint();
                                        double Latitude = point.getLatitude();
                                        double Longitude = point.getLongitude();

                                        FinishName[i] = item.getPOIName().toString();
                                        FinishAddress[i] = item.getPOIAddress().replace("null", "");
                                        FinishLatitude[i] = Latitude;
                                        FinishLongitude[i] = Longitude;
                                    }
                                }
                                else {
                                    for (int i = 0; i < 7; i++) {
                                        TMapPOIItem item = poiItem.get(i);

                                        LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                                "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                                "Point: " + item.getPOIPoint().toString());

                                        TMapPoint point = item.getPOIPoint();
                                        double Latitude = point.getLatitude();
                                        double Longitude = point.getLongitude();

                                        FinishName[i] = item.getPOIName().toString();
                                        FinishAddress[i] = item.getPOIAddress().replace("null", "");
                                        FinishLatitude[i] = Latitude;
                                        FinishLongitude[i] = Longitude;
                                    }
                                }

                                intent.putExtra("Name0", FinishName[0]);
                                intent.putExtra("Name1", FinishName[1]);
                                intent.putExtra("Name2", FinishName[2]);
                                intent.putExtra("Name3", FinishName[3]);
                                intent.putExtra("Name4", FinishName[4]);
                                intent.putExtra("Name5", FinishName[5]);
                                intent.putExtra("Name6", FinishName[6]);

                                intent.putExtra("Address0", FinishAddress[0]);
                                intent.putExtra("Address1", FinishAddress[1]);
                                intent.putExtra("Address2", FinishAddress[2]);
                                intent.putExtra("Address3", FinishAddress[3]);
                                intent.putExtra("Address4", FinishAddress[4]);
                                intent.putExtra("Address5", FinishAddress[5]);
                                intent.putExtra("Address6", FinishAddress[6]);

                                startActivityForResult(intent, ACT_EDIT);
                            }
                        });
                    }
                    return true;
                }
                return false;
            }
        });

        search_first.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if(id == EditorInfo.IME_ACTION_SEARCH) {
                    String str = search_first.getText().toString();

                    final Intent intent = new Intent(SeekActivity.this, SelectListviewActivity.class);

                    //레이아웃을 위함... actionbar title 하려고..


                    if (str == null || str.length() == 0) {
                        Toast.makeText(getApplicationContext(), "경유지를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        progressBarDialog2();
                        //Toast.makeText(getApplicationContext(), "입력성공 : " + str, Toast.LENGTH_SHORT).show();

                        intent.putExtra("str",str);

                        TMapData tmapdata = new TMapData();



                        tmapdata.findAllPOI(str, new FindAllPOIListenerCallback() {
                            @Override
                            public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {

                                if(poiItem.size()<7) {
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);

                                        LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                                "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                                "Point: " + item.getPOIPoint().toString());

                                        TMapPoint point = item.getPOIPoint();
                                        double Latitude = point.getLatitude();
                                        double Longitude = point.getLongitude();

                                        Stop1Name[i] = item.getPOIName().toString();
                                        Stop1Address[i] = item.getPOIAddress().replace("null", "");
                                        Stop1Latitude[i] = Latitude;
                                        Stop1Longitude[i] = Longitude;
                                    }
                                }
                                else {
                                    for (int i = 0; i < 7; i++) {
                                        TMapPOIItem item = poiItem.get(i);

                                        LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                                "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                                "Point: " + item.getPOIPoint().toString());

                                        TMapPoint point = item.getPOIPoint();
                                        double Latitude = point.getLatitude();
                                        double Longitude = point.getLongitude();

                                        Stop1Name[i] = item.getPOIName().toString();
                                        Stop1Address[i] = item.getPOIAddress().replace("null", "");
                                        Stop1Latitude[i] = Latitude;
                                        Stop1Longitude[i] = Longitude;
                                    }
                                }

                                intent.putExtra("Name0", Stop1Name[0]);
                                intent.putExtra("Name1", Stop1Name[1]);
                                intent.putExtra("Name2", Stop1Name[2]);
                                intent.putExtra("Name3", Stop1Name[3]);
                                intent.putExtra("Name4", Stop1Name[4]);
                                intent.putExtra("Name5", Stop1Name[5]);
                                intent.putExtra("Name6", Stop1Name[6]);

                                intent.putExtra("Address0", Stop1Address[0]);
                                intent.putExtra("Address1", Stop1Address[1]);
                                intent.putExtra("Address2", Stop1Address[2]);
                                intent.putExtra("Address3", Stop1Address[3]);
                                intent.putExtra("Address4", Stop1Address[4]);
                                intent.putExtra("Address5", Stop1Address[5]);
                                intent.putExtra("Address6", Stop1Address[6]);

                                startActivityForResult(intent, ACT_EDIT);
                            }
                        });
                    }
                    return true;
                }
                return false;
            }
        });

        search_second.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if(id == EditorInfo.IME_ACTION_SEARCH) {
                    String str = search_second.getText().toString();

                    final Intent intent = new Intent(SeekActivity.this, SelectListviewActivity.class);



                    if (str == null || str.length() == 0) {
                        Toast.makeText(getApplicationContext(), "경유지를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        progressBarDialog2();
                        //Toast.makeText(getApplicationContext(), "입력성공 : " + str, Toast.LENGTH_SHORT).show();

                        //레이아웃을 위함... actionbar title 하려고..
                        intent.putExtra("str",str);

                        TMapData tmapdata = new TMapData();



                        tmapdata.findAllPOI(str, new FindAllPOIListenerCallback() {
                            @Override
                            public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {

                                if(poiItem.size()<7) {
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);

                                        LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                                "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                                "Point: " + item.getPOIPoint().toString());

                                        TMapPoint point = item.getPOIPoint();
                                        double Latitude = point.getLatitude();
                                        double Longitude = point.getLongitude();

                                        Stop2Name[i] = item.getPOIName().toString();
                                        Stop2Address[i] = item.getPOIAddress().replace("null", "");
                                        Stop2Latitude[i] = Latitude;
                                        Stop2Longitude[i] = Longitude;
                                    }
                                }
                                else {
                                    for (int i = 0; i < 7; i++) {
                                        TMapPOIItem item = poiItem.get(i);

                                        LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                                "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                                "Point: " + item.getPOIPoint().toString());

                                        TMapPoint point = item.getPOIPoint();
                                        double Latitude = point.getLatitude();
                                        double Longitude = point.getLongitude();

                                        Stop2Name[i] = item.getPOIName().toString();
                                        Stop2Address[i] = item.getPOIAddress().replace("null", "");
                                        Stop2Latitude[i] = Latitude;
                                        Stop2Longitude[i] = Longitude;
                                    }
                                }

                                intent.putExtra("Name0", Stop2Name[0]);
                                intent.putExtra("Name1", Stop2Name[1]);
                                intent.putExtra("Name2", Stop2Name[2]);
                                intent.putExtra("Name3", Stop2Name[3]);
                                intent.putExtra("Name4", Stop2Name[4]);
                                intent.putExtra("Name5", Stop2Name[5]);
                                intent.putExtra("Name6", Stop2Name[6]);

                                intent.putExtra("Address0", Stop2Address[0]);
                                intent.putExtra("Address1", Stop2Address[1]);
                                intent.putExtra("Address2", Stop2Address[2]);
                                intent.putExtra("Address3", Stop2Address[3]);
                                intent.putExtra("Address4", Stop2Address[4]);
                                intent.putExtra("Address5", Stop2Address[5]);
                                intent.putExtra("Address6", Stop2Address[6]);

                                startActivityForResult(intent, ACT_EDIT);
                            }
                        });
                    }
                    return true;
                }
                return false;
            }
        });

        search_third.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if(id == EditorInfo.IME_ACTION_SEARCH) {
                    String str = search_third.getText().toString();

                    final Intent intent = new Intent(SeekActivity.this, SelectListviewActivity.class);

                    if (str == null || str.length() == 0) {
                        Toast.makeText(getApplicationContext(), "경유지를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        progressBarDialog2();
                        //Toast.makeText(getApplicationContext(), "입력성공 : " + str, Toast.LENGTH_SHORT).show();

                        //레이아웃을 위함... actionbar title 하려고..
                        intent.putExtra("str",str);
                        TMapData tmapdata = new TMapData();



                        tmapdata.findAllPOI(str, new FindAllPOIListenerCallback() {
                            @Override
                            public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {

                                if(poiItem.size()<7) {
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);

                                        LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                                "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                                "Point: " + item.getPOIPoint().toString());

                                        TMapPoint point = item.getPOIPoint();
                                        double Latitude = point.getLatitude();
                                        double Longitude = point.getLongitude();

                                        Stop3Name[i] = item.getPOIName().toString();
                                        Stop3Address[i] = item.getPOIAddress().replace("null", "");
                                        Stop3Latitude[i] = Latitude;
                                        Stop3Longitude[i] = Longitude;
                                    }
                                }
                                else {
                                    for (int i = 0; i < 7; i++) {
                                        TMapPOIItem item = poiItem.get(i);

                                        LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                                "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                                "Point: " + item.getPOIPoint().toString());

                                        TMapPoint point = item.getPOIPoint();
                                        double Latitude = point.getLatitude();
                                        double Longitude = point.getLongitude();

                                        Stop3Name[i] = item.getPOIName().toString();
                                        Stop3Address[i] = item.getPOIAddress().replace("null", "");
                                        Stop3Latitude[i] = Latitude;
                                        Stop3Longitude[i] = Longitude;
                                    }
                                }

                                intent.putExtra("Name0", Stop3Name[0]);
                                intent.putExtra("Name1", Stop3Name[1]);
                                intent.putExtra("Name2", Stop3Name[2]);
                                intent.putExtra("Name3", Stop3Name[3]);
                                intent.putExtra("Name4", Stop3Name[4]);
                                intent.putExtra("Name5", Stop3Name[5]);
                                intent.putExtra("Name6", Stop3Name[6]);

                                intent.putExtra("Address0", Stop3Address[0]);
                                intent.putExtra("Address1", Stop3Address[1]);
                                intent.putExtra("Address2", Stop3Address[2]);
                                intent.putExtra("Address3", Stop3Address[3]);
                                intent.putExtra("Address4", Stop3Address[4]);
                                intent.putExtra("Address5", Stop3Address[5]);
                                intent.putExtra("Address6", Stop3Address[6]);

                                startActivityForResult(intent, ACT_EDIT);
                            }
                        });
                    }
                    return true;
                }
                return false;
            }
        });

        search_fourth.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if(id == EditorInfo.IME_ACTION_SEARCH) {
                    String str = search_fourth.getText().toString();

                    final Intent intent = new Intent(SeekActivity.this, SelectListviewActivity.class);

                    if (str == null || str.length() == 0) {
                        Toast.makeText(getApplicationContext(), "경유지를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        progressBarDialog2();
                        //Toast.makeText(getApplicationContext(), "입력성공 : " + str, Toast.LENGTH_SHORT).show();

                        //레이아웃을 위함... actionbar title 하려고..
                        intent.putExtra("str",str);

                        TMapData tmapdata = new TMapData();



                        tmapdata.findAllPOI(str, new FindAllPOIListenerCallback() {
                            @Override
                            public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {

                                if(poiItem.size()<7) {
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);

                                        LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                                "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                                "Point: " + item.getPOIPoint().toString());

                                        TMapPoint point = item.getPOIPoint();
                                        double Latitude = point.getLatitude();
                                        double Longitude = point.getLongitude();

                                        Stop4Name[i] = item.getPOIName().toString();
                                        Stop4Address[i] = item.getPOIAddress().replace("null", "");
                                        Stop4Latitude[i] = Latitude;
                                        Stop4Longitude[i] = Longitude;
                                    }
                                }
                                else {
                                    for (int i = 0; i < 7; i++) {
                                        TMapPOIItem item = poiItem.get(i);

                                        LogManager.printLog( " POI Name: " + item.getPOIName().toString() + ", " +
                                                "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                                "Point: " + item.getPOIPoint().toString());

                                        TMapPoint point = item.getPOIPoint();
                                        double Latitude = point.getLatitude();
                                        double Longitude = point.getLongitude();

                                        Stop4Name[i] = item.getPOIName().toString();
                                        Stop4Address[i] = item.getPOIAddress().replace("null", "");
                                        Stop4Latitude[i] = Latitude;
                                        Stop4Longitude[i] = Longitude;
                                    }
                                }

                                intent.putExtra("Name0", Stop4Name[0]);
                                intent.putExtra("Name1", Stop4Name[1]);
                                intent.putExtra("Name2", Stop4Name[2]);
                                intent.putExtra("Name3", Stop4Name[3]);
                                intent.putExtra("Name4", Stop4Name[4]);
                                intent.putExtra("Name5", Stop4Name[5]);
                                intent.putExtra("Name6", Stop4Name[6]);

                                intent.putExtra("Address0", Stop4Address[0]);
                                intent.putExtra("Address1", Stop4Address[1]);
                                intent.putExtra("Address2", Stop4Address[2]);
                                intent.putExtra("Address3", Stop4Address[3]);
                                intent.putExtra("Address4", Stop4Address[4]);
                                intent.putExtra("Address5", Stop4Address[5]);
                                intent.putExtra("Address6", Stop4Address[6]);


                                startActivityForResult(intent, ACT_EDIT);
                            }
                        });
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public String[][] getResultT(){
        return resultT;
    }

    public String[][] getResultCC(){
        return resultCC;
    }

    public String[][] getResultCT(){
        return resultCT;
    }
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public int addPath(int cnt) {
        if (cnt == 1) {
            num3.setVisibility(View.VISIBLE);
            search_third.setVisibility(View.VISIBLE);
            btn_search_3th.setVisibility(View.VISIBLE);
            btn_remove_3th.setVisibility(View.VISIBLE);
        }
        else if (cnt == 2) {
            num4.setVisibility(View.VISIBLE);
            search_fourth.setVisibility(View.VISIBLE);
            btn_search_4th.setVisibility(View.VISIBLE);
            btn_remove_4th.setVisibility(View.VISIBLE);
        }
        else if (cnt >= 3){
            Toast.makeText(getApplicationContext(), "더이상 추가는 안됩니다", Toast.LENGTH_SHORT).show();
            cnt--;
        }
        return cnt;
    }

    public int removePath(int cnt) {
        if (cnt == 1) {
            num3.setVisibility(View.INVISIBLE);
            search_third.setVisibility(View.INVISIBLE);
            btn_search_3th.setVisibility(View.INVISIBLE);
            btn_remove_3th.setVisibility(View.INVISIBLE);
        }
        else if (cnt == 2) {
            num4.setVisibility(View.INVISIBLE);
            search_fourth.setVisibility(View.INVISIBLE);
            btn_search_4th.setVisibility(View.INVISIBLE);
            btn_remove_4th.setVisibility(View.INVISIBLE);
        }
        return cnt;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //dbLct = SelectListviewActivity.getWritableDatabase();
        //SelectListviewActivity.onCreate(dbLct);
        //ContentValues values = new ContentValues();

        if (requestCode == ACT_EDIT && resultCode == RESULT_OK) {
            LogManager.printLog("넘어온 값 : " + data.getStringExtra("OutName"));
            String outputName1 = data.getStringExtra("OutName");
            String tmpPlace ="place=";
            String tmpEnd = "}";
            String outputName = outputName1.substring((outputName1.indexOf(tmpPlace)+tmpPlace.length()),outputName1.indexOf(tmpEnd));
            LogManager.printLog(outputName);

            //start
            for (int i = 0; i < 7; i++) {
                if (outputName.equals(StartName[i])) {
                    LogManager.printLog("start : " + data.getStringExtra("OutName"));
                    decidedStartName[0] = StartName[i];
                    decidedStartLatitude[0] = StartLatitude[i];
                    decidedStartLongitude[0] = StartLongitude[i];

                    LogManager.printLog("start 입력된 name : " + StartName[i] + " : " + decidedStartName[0]
                            + "\nlatitude: " + StartLatitude[i] + " : " + decidedStartLatitude[0]
                            + "\nlongitude : " + StartLongitude[i] + " : " + decidedStartLongitude[0]);

                    if(decidedStartName[0] != null){
                        String tmp ;
                        tmp = decidedStartName[0];
                        if(tmp.length() >= 15 ) {
                            search_startpoint.setText(tmp);
                            search_startpoint.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        }
                        else {
                            search_startpoint.setText(tmp);
                        }
                    }
                }
            }

            //finish
            for (int i = 0; i < 7; i++) {
                if (outputName.equals(FinishName[i])) {
                    LogManager.printLog("finish : " + data.getStringExtra("OutName"));

                    decidedFinishName[0] = FinishName[i];
                    decidedFinishLatitude[0] = FinishLatitude[i];
                    decidedFinishLongitude[0] = FinishLongitude[i];

                    LogManager.printLog("finish 입력된 name : " + FinishName[i] + ":"  + decidedFinishName[0]
                            + " latitude: " + FinishLatitude[i] + ":" + decidedFinishLatitude[0]
                            + " longitude : " + FinishLongitude[i] + ":" + decidedFinishLongitude[0]);

                    if(decidedFinishName[0] != null){

                        String tmp ;
                        tmp = decidedFinishName[0];
                        if(tmp.length() >= 15 ) {
                            search_finishpoint.setText(tmp);
                            search_finishpoint.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        }
                        else {
                            search_finishpoint.setText(tmp);
                        }
                    }
                }
            }

            for (int i = 0; i < 7; i++) {
                if (outputName.equals(Stop1Name[i])) {
                    LogManager.printLog("stop1 : " + data.getStringExtra("OutName"));
                    decidedStopName[0] = Stop1Name[i];
                    decidedStopLatitude[0] = Stop1Latitude[i];
                    decidedStopLongitude[0] = Stop1Longitude[i];

                    LogManager.printLog("입력된 name : " + Stop1Name[i] + " latitude: " + Stop1Latitude[i]
                            + " longitude : " + Stop1Longitude[i]);

                    if(decidedStopName[0] != null){

                        String tmp ;
                        tmp = decidedStopName[0];
                        if(tmp.length() >= 15 ) {
                            search_first.setText(tmp);
                            search_first.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        }
                        else {
                            search_first.setText(tmp);
                        }
                    }
                }
            }

            for (int i = 0; i < 7; i++) {
                if (outputName.equals(Stop2Name[i])) {
                    LogManager.printLog("stop2 : " + data.getStringExtra("OutName"));
                    decidedStopName[1] = Stop2Name[i];
                    decidedStopLatitude[1] = Stop2Latitude[i];
                    decidedStopLongitude[1] = Stop2Longitude[i];

                    LogManager.printLog("입력된 name : " + Stop2Name[i] + " latitude: " + Stop2Latitude[i]
                            + " longitude : " + Stop2Longitude[i]);


                    if(decidedStopName[1] != null){

                        String tmp ;
                        tmp = decidedStopName[1];

                        if(tmp.length() >= 15 ) {
                            search_second.setText(tmp);
                            search_second.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        }
                        else {
                            search_second.setText(tmp);
                        }
                    }
                }
            }

            for (int i = 0; i < 7; i++) {
                if (outputName.equals(Stop3Name[i])) {
                    LogManager.printLog("stop3 : " + data.getStringExtra("OutName"));
                    decidedStopName[2] = Stop3Name[i];
                    decidedStopLatitude[2] = Stop3Latitude[i];
                    decidedStopLongitude[2] = Stop3Longitude[i];

                    LogManager.printLog("입력된 name : " + Stop3Name[i] + " latitude: " + Stop3Latitude[i]
                            + " longitude : " + Stop3Longitude[i]);
                    if(decidedStopName[2] != null){

                        String tmp ;
                        tmp = decidedStopName[2];
                        if(tmp.length() >= 15 ) {
                            search_third.setText(tmp);
                            search_third.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        }
                        else {
                            search_third.setText(tmp);
                        }
                    }

                }
            }

            for (int i = 0; i < 7; i++) {
                if (outputName.equals(Stop4Name[i])) {
                    LogManager.printLog("stop4 : " + data.getStringExtra("OutName"));
                    decidedStopName[3] = Stop4Name[i];
                    decidedStopLatitude[3] = Stop4Latitude[i];
                    decidedStopLongitude[3] = Stop4Longitude[i];

                    LogManager.printLog("입력된 name : " + Stop4Name[i] + " latitude: " + Stop4Latitude[i]
                            + " longitude : " + Stop4Longitude[i]);


                    if(decidedStopName[3] != null){

                        String tmp ;
                        tmp = decidedStopName[3];
                        if(tmp.length() >= 15 ) {
                            search_fourth.setText(tmp);
                            search_fourth.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        }
                        else {
                            search_fourth.setText(tmp);
                        }
                    }
                }
            }
        }
    }

    public Handler mhandler = new Handler();
    public void progressBarDialog() {
        progressDialog = new ProgressDialog(SeekActivity.this);

        progressDialog.setMessage("경로를 찾고 있습니다");
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    // some stuff to do the task...
                    while (progressDialog.getProgress() <= progressDialog.getMax()) {

                        Thread.sleep(60);

                        mhandler.post(new Runnable() {
                            public void run() {
                                progressDialog.incrementProgressBy(1);
                            }
                        });

                        if (progressDialog.getProgress() == progressDialog.getMax()) {
                            progressDialog.dismiss();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void progressBarDialog2() {
        progressDialog = new ProgressDialog(SeekActivity.this);

        progressDialog.setMessage("주소 찾는 중");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        //progressDialog.setProgress(0);
        progressDialog.show();

        mhandler.postDelayed( new Runnable() {
            @Override public void run() {
                try {
                    if (progressDialog!=null&&progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                catch ( Exception e ) {
                    e.printStackTrace();
                }
            }
        }, 1000);
    }
}

