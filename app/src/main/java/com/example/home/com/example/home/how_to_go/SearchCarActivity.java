package com.example.home.how_to_go;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

public class SearchCarActivity extends AppCompatActivity {
    String[][] tmp1;
    String[][] tmp2;
    ListViewFragment_search_car lf1;    //최소비용 리스트뷰
    ListViewFragment_search_car2 lf2;   //최단거리 리스트뷰

    DBPathList pathList = new DBPathList(SearchCarActivity.this, "SavedPath.db", null, 1);
    SQLiteDatabase dbPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(" 경로 검색 결과 ");
        setContentView(R.layout.activity_search_car2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_car, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        dbPath = pathList.getWritableDatabase();
        pathList.onCreate(dbPath);
        ContentValues values = new ContentValues();

        Cursor c = dbPath.query("SavedPath", null, null, null, null, null, null);

        int id = item.getItemId();

        boolean[] k1 = lf1.getCheck();    //lf1 : 최소비용
        boolean[] k2 = lf2.getCheck();    //lf2 : 최단시간

        //얘네부터 println 까지는 걍 확인해볼라고 한거니까 돌려봐도 된당ㅇ
        int cnt1 = 0;   //최소비용 checkbox 카운트
        int cnt2 = 0;   //최단시간 checkbox 카운트

        if (id == R.id.home) {
            finish();
            return true;
        }
        else if (id == R.id.save) {
            for(int i = 0; i < k1.length; i++){
                if(k1[i]==true){
                    cnt1++;
                }
            }
            System.out.println("car cnt1 : " + cnt1);   //지워도 됨
            for(int i = 0; i < k2.length; i++){
                if(k2[i]==true){
                    cnt2++;
                }
            }
            System.out.println("car cnt2 : " + cnt2);   //지워도 됨
            if(cnt1 != 0 || cnt2 != 0) {
                //여기에 데베넣고 저장하면 된디
                //중복 처리 해줘야돼...
                String tmpName="";
                String tmpCost="";

                for (int i = 0; i < k1.length ; i++) {
                    LogManager.printLog(i+ "번째 k1 길이 " + k1.length);
                    if (k1[i] == true) {
                        LogManager.printLog("DB --------- check box true " + i);

                        c.moveToFirst();
                        String overlap="no";

                        //데베 내용 없으면
                        if(c.getCount() == 0) {
                            pathList.insert("CAR", tmp1[i][0], tmp1[i][1]);
                            String result = pathList.getResult();
                            LogManager.printLog("******db내용물 \n" + result);
                        }

                        else {
                            c.moveToFirst();
                            tmpName = c.getString(c.getColumnIndex("path"));
                            tmpCost = c.getString(c.getColumnIndex("timeNcost"));
                            LogManager.printLog("0 db 저장된 이름 " + tmpName);
                            LogManager.printLog("입력 이름    : " + tmp1[i][0]);

                            if((tmpName.equals(tmp1[i][0]) && tmpCost.equals(tmp1[i][1]))) {
                                overlap = "yes";
                                LogManager.printLog("첫번째꺼 overlap check : " + overlap + "---------------------");
                            }
                            else {
                                overlap = "no";
                            }

                            if(overlap.equals("no")) {
                                while(c.moveToNext()) {
                                    tmpName = c.getString(c.getColumnIndex("path"));
                                    tmpCost = c.getString(c.getColumnIndex("timeNcost"));
                                    LogManager.printLog("db 저장된 이름 " + tmpName + " " + tmpCost);
                                    LogManager.printLog("입력 이름    : " + tmp1[i][0] + " " + tmp1[i][1]);

                                    if((tmpName.equals(tmp1[i][0]) && tmpCost.equals(tmp1[i][1]))) {
                                        overlap = "yes";
                                        LogManager.printLog("overlap check : " + overlap + "---------------------");
                                        break;
                                    }
                                    else {
                                        overlap = "no";
                                    }
                                }
                            }

                            if(overlap.equals("no")) {
                                LogManager.printLog("---------------중복없음-----------데이터입력");
                                pathList.insert("CAR", tmp1[i][0], tmp1[i][1]);
                                String result = pathList.getResult();
                                LogManager.printLog("******db내용물 \n" + result);
                            }
                        }
                    }
                }

                for (int i = 0; i < k2.length ; i++) {
                    LogManager.printLog(i + "번째 k2 길이 " + k2.length);
                    if (k2[i] == true) {
                        LogManager.printLog("DB --------- check box true " + i);

                        c.moveToFirst();
                        String overlap = "no";

                        //데베 내용 없으면
                        if (c.getCount() == 0) {
                            pathList.insert("CAR", tmp2[i][0], tmp2[i][1]);
                            String result = pathList.getResult();
                            LogManager.printLog("******db내용물 \n" + result);
                        } else {
                            c.moveToFirst();
                            tmpName = c.getString(c.getColumnIndex("path"));
                            tmpCost = c.getString(c.getColumnIndex("timeNcost"));
                            LogManager.printLog("0 db 저장된 이름 " + tmpName + " " + tmpCost);
                            LogManager.printLog("입력 이름    : " + tmp2[i][0] + " " + tmp2[i][1]);

                            if ( (tmpName.equals(tmp2[i][0]) && tmpCost.equals(tmp2[i][1]))) {
                                overlap = "yes";
                                LogManager.printLog("overlap check : " + overlap + "---------------------");

                            } else {
                                overlap = "no";
                            }

                            if(overlap.equals("no")) {
                                while (c.moveToNext()) {
                                    tmpName = c.getString(c.getColumnIndex("path"));
                                    tmpCost = c.getString(c.getColumnIndex("timeNcost"));
                                    LogManager.printLog("db 저장된 이름 " + tmpName + " " + tmpCost);
                                    LogManager.printLog("입력 이름    : " + tmp2[i][0] + " " + tmp2[i][1]);

                                    if (tmpName.equals(tmp2[i][0]) && tmpCost.equals(tmp2[i][1])) {
                                        overlap = "yes";
                                        LogManager.printLog("overlap check : " + overlap + "---------------------");
                                        break;
                                    } else {
                                        overlap = "no";
                                    }
                                }
                            }

                            if (overlap.equals("no")) {
                                LogManager.printLog("---------------중복없음-----------데이터입력");
                                pathList.insert("CAR", tmp2[i][0], tmp2[i][1]);
                                String result = pathList.getResult();
                                LogManager.printLog("******db내용물 \n" + result);
                            }
                        }
                    }
                }

                lf1.removeCheck();
                lf2.removeCheck();

                LogManager.printLog("한번끝남------------------------------------");
                Toast.makeText(SearchCarActivity.this, "저장 되었습니다", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(SearchCarActivity.this, "저장할 경로가 없습니다", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Setting View Pager
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        lf1 = new ListViewFragment_search_car();
        lf2 = new ListViewFragment_search_car2();
        tmp1 = (String[][])getIntent().getSerializableExtra("pathCC");
        tmp2 = (String[][])getIntent().getSerializableExtra("pathCT");
        lf1.setPath(tmp1);
        lf2.setPath(tmp2);
        adapter.addFrag(lf1, "최소 비용");
        adapter.addFrag(lf2, "최단 시간");
        viewPager.setAdapter(adapter);
    }
}