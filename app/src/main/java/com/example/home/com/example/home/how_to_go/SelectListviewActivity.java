package com.example.home.how_to_go;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HOME on 2017-06-06.
 */

public class SelectListviewActivity extends AppCompatActivity {
    private SimpleAdapter adapter;

    ArrayList<HashMap<String,String>> mDatas = new ArrayList<HashMap<String,String>>();

    ListView listview1; //ListView 참조변수

    final Intent outintent = new Intent(SelectListviewActivity.this, SeekActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        //넘긴 값 string으로 받아온 뒤 리스트에 추가
        Intent intent = getIntent();

        String location = " @@ ";
        location = getIntent().getStringExtra("str");
        setTitle("\" "+ location+"\" 검색 결과 ");

        //actionbar 뒤로가기 버튼 만들기
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        setContentView(R.layout.select_list_view);
        listview1 = (ListView)findViewById(R.id.listview);
        String name0;
        String name1;
        String name2;
        String name3;
        String name4;
        String name5;
        String name6;

        name0 = intent.getStringExtra("Name0");
        name1 = intent.getStringExtra("Name1");
        name2 = intent.getStringExtra("Name2");
        name3 = intent.getStringExtra("Name3");
        name4 = intent.getStringExtra("Name4");
        name5 = intent.getStringExtra("Name5");
        name6 = intent.getStringExtra("Name6");

        String address0;
        String address1;
        String address2;
        String address3;
        String address4;
        String address5;
        String address6;

        address0 = intent.getStringExtra("Address0");
        address1 = intent.getStringExtra("Address1");
        address2 = intent.getStringExtra("Address2");
        address3 = intent.getStringExtra("Address3");
        address4 = intent.getStringExtra("Address4");
        address5 = intent.getStringExtra("Address5");
        address6 = intent.getStringExtra("Address6");

        //문자열 데이터 ArrayList에 추가
        HashMap<String,String> item = new HashMap<String,String>();
        item.put("place",name0);
        item.put("location",address0);
        mDatas.add(item);

        item= new HashMap<String,String>();
        item.put("place",name1);
        item.put("location",address1);
        mDatas.add(item);

        item = new HashMap<String,String>();
        item.put("place",name2);
        item.put("location",address2);
        mDatas.add(item);

        item = new HashMap<String,String>();
        item.put("place",name3);
        item.put("location",address3);
        mDatas.add(item);

        item = new HashMap<String,String>();
        item.put("place",name4);
        item.put("location",address4);
        mDatas.add(item);

        item = new HashMap<String,String>();
        item.put("place",name5);
        item.put("location",address5);
        mDatas.add(item);

        item = new HashMap<String,String>();
        item.put("place",name6);
        item.put("location",address6);
        mDatas.add(item);

        //ListView가 보여줄 뷰를 만들어내는 Adapter 객체 생성
        //ArrayAdapter : 문자열 데이터들을 적절한 iew로 1:1로 만들어서 List형태로 ListView에 제공하는 객체
        //첫번째 파라미터 : Context객체 ->MainActivity가 Context를 상속했기 때문에 this로 제공 가능
        //두번째 파라미터 : 문자열 데이터를 보여줄 뷰. ListView에 나열되는 하나의 아이템 단위의 뷰 모양
        //세번째 파라미터 : adapter가 뷰로 만들어줄 대량의 데이터들
        //본 예제에서는 문자열만 하나씩 보여주면 되기 때문에 두번째 파라미터의 뷰 모먕은 Android 시스템에서 제공하는
        //기본 Layout xml 파일을 사용함.
        adapter = new SimpleAdapter(this, mDatas, android.R.layout.simple_list_item_2,
                new String[] { "place","location" }, new int[] { android.R.id.text1, android.R.id.text2 } ) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text2.setTextColor(Color.GRAY);
                return view;
            };


        };
        //listview= (ListView)findViewById(R.id.listview);

        //listview.setAdapter(adapter); //위에 만들어진 Adapter를 ListView에 설정 : xml에서 'entries'속성
        listview1.setAdapter(adapter);
        //ListView의 아이템 하나가 클릭되는 것을 감지하는 Listener객체 설정 (Button의 OnClickListener와 같은 역할)
        listview1.setOnItemClickListener(listener);
    }

    //ListView의 아이템 하나가 클릭되는 것을 감지하는 Listener객체 생성 (Button의 OnClickListener와 같은 역할)
    AdapterView.OnItemClickListener listener= new AdapterView.OnItemClickListener() {
        //ListView의 아이템 중 하나가 클릭될 때 호출되는 메소드
        //첫번째 파라미터 : 클릭된 아이템을 보여주고 있는 AdapterView 객체(여기서는 ListView객체)
        //두번째 파라미터 : 클릭된 아이템 뷰
        //세번째 파라미터 : 클릭된 아이템의 위치(ListView이 첫번째 아이템(가장위쪽)부터 차례대로 0,1,2,3.....)
        //네번재 파리미터 : 클릭된 아이템의 아이디(특별한 설정이 없다면 세번째 파라이터인 position과 같은 값)

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // TODO Auto-generated method stub
            Intent outIntent = getIntent();
            outIntent.putExtra("OutName",mDatas.get(position).toString());

            //Toast.makeText(SelectListviewActivity.this, mDatas.get(position), Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK, outIntent);
            finish();
        }
    };

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

