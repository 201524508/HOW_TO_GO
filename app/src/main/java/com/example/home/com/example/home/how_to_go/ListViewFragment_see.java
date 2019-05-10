package com.example.home.how_to_go;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatCallback;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

/**
 * Created by sonu on 08/02/17.
 * DB edit by s0woo 20170619
 */

//extends Fragment
public class ListViewFragment_see extends Fragment {
    SeekActivity seek;
    private Context context;
    private GridListAdapter_see adapter;
    private String[][] path;
    private Button selectButton;
    boolean[] tmp2; //얘가 체크박스 체크 됐는지 안 됐는지 확인 돼있는 배열

    public ListViewFragment_see() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.see_list_view_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectButton = (Button) view.findViewById(R.id.select_button);

        loadListView(view);
        onClickEvent(view);
    }

    private void loadListView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.list_view_see);
        //arrayList = new ArrayList<String[]>();

        DBPathList pathList = new DBPathList(ListViewFragment_see.this.getActivity(), "SavedPath.db", null, 1);
        SQLiteDatabase dbPath;
        dbPath = pathList.getWritableDatabase();
        pathList.onCreate(dbPath);
        Cursor c = dbPath.query("SavedPath", null, null, null, null, null, null);

        int t = 0;
        String[][] tmp;

        int cnt=0;

        //LogManager.printLog("cnt : " + cnt);
        if(c.getCount() == 0) {
            tmp = new String[1][3];
            tmp[cnt][0] = "저장된 값이 없습니다";
            tmp[cnt][1] = "저장된 값이 없습니다";
            tmp[cnt][2] = "NODATA";
            c.close();
        }
        else{
            c.moveToFirst();
            t = t + 1;
            while(c.moveToNext()!=false){
                t = t + 1;
            }
            tmp = new String[t][3];

            c.moveToFirst();

            LogManager.printLog("cnt : " + cnt);
            tmp[cnt][0] = c.getString(c.getColumnIndex("path"));
            tmp[cnt][1] = c.getString(c.getColumnIndex("timeNcost"));
            tmp[cnt][2] = c.getString(c.getColumnIndex("_index"));
            //System.out.println("확인용 : " + tmp[cnt][0] + " / " + tmp[cnt][1] + " / " + tmp[cnt][2]);
            cnt++;

            while(c.moveToNext() != false) {
                LogManager.printLog("cnt : " + cnt);
                tmp[cnt][0] = c.getString(c.getColumnIndex("path"));
                tmp[cnt][1] = c.getString(c.getColumnIndex("timeNcost"));
                tmp[cnt][2] = c.getString(c.getColumnIndex("_index"));
                //System.out.println("확인용 : " + tmp[cnt][0] + " / " + tmp[cnt][1] + " / " + tmp[cnt][2]);
                cnt++;
                if(cnt > t) {
                    break;
                }
            }
        }

        path = tmp;

        adapter = new GridListAdapter_see(context, path, true);
        listView.setAdapter(adapter);
        //tmp2 = adapter.getCntCheck();
    }

    public boolean[] getCheck(){
        return tmp2;
    }

    private void onClickEvent(View view) {
        view.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {

            DBPathList pathList = new DBPathList(ListViewFragment_see.this.getActivity(), "SavedPath.db", null, 1);
            SQLiteDatabase dbPath;

            @Override
            public void onClick(View view) {
                //SparseBooleanArray selectedRows = adapter.getSelectedIds();//Get the selected ids from adapter

                tmp2 = adapter.getCntCheck();
                /*System.out.print("체크 확인을 할 거란다 ");
                for(int i = 0; i < tmp2.length; i++){
                    System.out.print(" - " + tmp2[i]);
                }

                System.out.println();
                */
                String[][] tmp3 = new String[path.length][3];
                for(int i = 0; i < path.length; i++){
                    tmp3[i][0] = path[i][0];
                    tmp3[i][1] = path[i][1];
                    tmp3[i][2] = path[i][2];
                }

                dbPath = pathList.getWritableDatabase();
                pathList.onCreate(dbPath);

                int t = 0;
                for(int i = 0; i < tmp2.length; i++){
                    if(tmp2[i]==true){
                        t = t + 1;
                    }
                }

                if(t != tmp2.length){
                    for(int i = tmp2.length - 1; i >= 0; i--) {
                        if(tmp2[i] == true) {
                            //System.out.println(i + " 번째를 삭제할 것이니라!!!!");
                            pathList.delete(tmp3[i][2], tmp3[i][0], tmp3[i][1]);

                            for(int j = i; j < tmp2.length - 1; j++){
                                for(int k = j + 1; j < tmp2.length; j++){
                                    boolean h = tmp2[j];
                                    String[] str = tmp3[j];
                                    tmp2[j] = tmp2[k];
                                    tmp2[k] = h;
                                    tmp3[j] = tmp3[k];
                                    tmp3[k] = str;
                                }
                            }
                            int n = tmp2.length-1;
                            boolean[] n1 = new boolean[n];
                            for(int j = 0; j < n; j++){
                                n1[j] = tmp2[j];
                            }
                            String[][] n2 = new String[n][3];
                            for(int j = 0; j < n; j++){
                                n2[j][0] = tmp3[j][0];
                                n2[j][1] = tmp3[j][1];
                                n2[j][2] = tmp3[j][2];
                            }
                            tmp2 = n1;
                            tmp3 = n2;
                        }
                    }

                    path = tmp3;
                    /*
                    System.out.println("DB 확인 전 path 확인");

                    for(int i = 0; i < path.length; i++){
                        System.out.println(path[i][0] + " / " + path[i][1] + " / " + path[i][2]);
                    }
                    */

                    adapter.setArrayList(path);
                    adapter.removeSelection();
                    adapter.notifyDataSetChanged();

                    //String string = pathList.getResult();
                    //System.out.println("DB확인좀\n" + string);
                    adapter.removeSelection();
                }
                else if(t == tmp2.length){
                    //System.out.println("DB가 비었습니다");
                    pathList.deleteAll();

                    String[][] tmp = new String[1][3];
                    tmp[0][0] = "저장된 값이 없습니다";
                    tmp[0][1] = "저장된 값이 없습니다";
                    tmp[0][2] = "NODATA";

                    adapter.setArrayList(tmp);
                    adapter.removeSelection();
                    adapter.notifyDataSetChanged();
                }


                /*
                //Check if item is selected or not via size
                if (selectedRows.size() > 0) {
                    //Loop to all the selected rows array
                    for (int i = (selectedRows.size() - 1); i >= 0; i--) {

                        //Check if selected rows have value i.e. checked item
                        if (selectedRows.valueAt(i)) {

                            String tmp = Integer.toString(i);
                            pathList.delete(tmp);
                            String result = pathList.getResult();
                            LogManager.printLog("******db내용물 \n" + result);

                            //remove the checked item
                            //arrayList.remove(selectedRows.keyAt(i));
                            if(tmp2[i] == true) {
                                int k = i;
                                System.out.println(k + "번째 " + "삭제를 할 것이니라!");
                                pathList.delete(path[k][2], path[k][0], path[k][1]);
                            }
                        }
                }

                    //notify the adapter and remove all checked selection
                    adapter.removeSelection();
                }
                */

            }
        });
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check the current text of Select Button
                if (selectButton.getText().toString().equals(getResources().getString(R.string.select_all))) {
                    System.out.println("다 선택한단다!");
                    //If Text is Select All then loop to all array List items and check all of them
                    for (int i = 0; i < path.length; i++)
                        adapter.checkCheckBox(i, true);

                    adapter.notifyDataSetChanged();
                    adapter.setCntCheckAll();

                    //After checking all items change button text
                    selectButton.setText(getResources().getString(R.string.deselect_all));
                } else {
                    //If button text is Deselect All remove check from all items
                    System.out.println("다 선택 안 한단다!");
                    adapter.removeSelection();

                    adapter.notifyDataSetChanged();

                    //After checking all items change button text
                    selectButton.setText(getResources().getString(R.string.select_all));
                }

            }
        });
    }
}
