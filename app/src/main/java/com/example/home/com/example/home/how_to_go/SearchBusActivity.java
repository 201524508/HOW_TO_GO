package com.example.home.how_to_go;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SearchBusActivity extends AppCompatActivity {
    String[][] tmp;
    ListViewFragment_search_bus lf;

    DBPathList pathList = new DBPathList(SearchBusActivity.this, "SavedPath.db", null, 1);
    SQLiteDatabase dbPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(" 경로 검색 결과 ");
        setContentView(R.layout.activity_search_bus2);

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
        getMenuInflater().inflate(R.menu.menu_search_bus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        boolean[] tmp1 = lf.getCheck();    //얜 대중교통이라서 걍 최단시간

        dbPath = pathList.getWritableDatabase();
        pathList.onCreate(dbPath);
        ContentValues values = new ContentValues();

        Cursor c = dbPath.query("SavedPath", null, null, null, null, null, null);

        //얘네부터 println 까지는 걍 확인해볼라고 한거니까 돌려봐도 된당ㅇ
        int cnt = 0;    //true인 갯수

        if (id == R.id.home) {
            finish();
            return true;
        }
        else if (id == R.id.save) {
            for(int i = 0; i < tmp.length; i++){
                if(tmp1[i]==true){  //check 돼있으면
                    cnt++;
                }
            }
            System.out.println("bus cnt : " + cnt); //확인차 해본거 지워도 됨

            if(cnt != 0){
                //여기에 데베넣고 저장하면 된디
                //중복 처리 해줘야됨
                String tmpName="";
                String tmpCost="";

                for (int i = 0; i < tmp.length ; i++) {
                    LogManager.printLog(i+ "번째 k1 길이 " + tmp.length);
                    if (tmp1[i] == true) {
                        LogManager.printLog("DB --------- check box true " + i);

                        c.moveToFirst();
                        String overlap="no";

                        //데베 내용 없으면
                        if(c.getCount() == 0) {
                            pathList.insert("BUS", tmp[i][0], tmp[i][1]);
                            String result = pathList.getResult();
                            LogManager.printLog("******db내용물 \n" + result);
                        }

                        else {
                            c.moveToFirst();
                            tmpName = c.getString(c.getColumnIndex("path"));
                            tmpCost = c.getString(c.getColumnIndex("timeNcost"));
                            LogManager.printLog("0 db 저장된 이름 " + tmpName);
                            LogManager.printLog("입력 이름    : " + tmp[i][0]);

                            if(tmpName.equals(tmp[i][0]) && tmpCost.equals(tmp[i][1])) {
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
                                    LogManager.printLog("입력 이름    : " + tmp[i][0] + " " + tmp[i][1]);

                                    if(tmpName.equals(tmp[i][0]) && tmpCost.equals(tmp[i][1])) {
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
                                pathList.insert("BUS", tmp[i][0], tmp[i][1]);
                                String result = pathList.getResult();
                                LogManager.printLog("******db내용물 \n" + result);
                            }
                        }
                    }
                }

                lf.removeCheck();

                LogManager.printLog("한번끝남------------------------------------");
                Toast.makeText(SearchBusActivity.this, "저장 되었습니다", Toast.LENGTH_SHORT).show();

            }
            else{
                Toast.makeText(SearchBusActivity.this, "저장할 경로가 없습니다", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Setting View Pager
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        lf = new ListViewFragment_search_bus();
        tmp = (String[][])getIntent().getSerializableExtra("path");
        lf.setPath(tmp);
        adapter.addFrag(lf, "최단 시간");
        viewPager.setAdapter(adapter);
    }
}