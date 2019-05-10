
package com.example.home.how_to_go;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by home on 17/06/20.
 * */


public class GridListAdapter_see extends BaseAdapter {
    private Context context;
    private String[][] arrayList;
    private LayoutInflater inflater;
    private boolean isListView;
    private SparseBooleanArray mSelectedItemsIds;
    private boolean[] cntCheck;

    public GridListAdapter_see(Context context, String[][] arrayList, boolean isListView) {
        this.context = context;
        this.arrayList = arrayList;
        this.isListView = isListView;
        inflater = LayoutInflater.from(context);
        mSelectedItemsIds = new SparseBooleanArray();
        cntCheck = new boolean[arrayList.length];
    }

    public void setArrayList(String[][] tmp){
        arrayList = tmp;
        cntCheck = new boolean[tmp.length];
    }

    @Override
    public int getCount() {
        return arrayList.length;
    }

    @Override
    public Object getItem(int i) {
        return arrayList[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();

            //inflate the layout on basis of boolean
            if (isListView)
                view = inflater.inflate(R.layout.see_list_custom_row_layout, viewGroup, false);
            else
                view = inflater.inflate(R.layout.see_list_custom_row_layout, viewGroup, false);

            viewHolder.label = (TextView) view.findViewById(R.id.label);
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            viewHolder.data = (TextView) view.findViewById(R.id.data);
            viewHolder.CarOrBus = (ImageView) view.findViewById(R.id.CarOrBus);

            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();


        viewHolder.label.setText(arrayList[i][0]);
        viewHolder.data.setText(arrayList[i][1]);
        viewHolder.checkBox.setChecked(mSelectedItemsIds.get(i));

        if(arrayList[i][1]!=null){
            if(arrayList[i][2].equalsIgnoreCase("CAR")) {
                //System.out.println("자동차란다");
                viewHolder.CarOrBus.setImageResource(R.drawable.car_icon);
            }
            else if(arrayList[i][2].equalsIgnoreCase("BUS")){
                //System.out.println("대중교통이란다");
                viewHolder.CarOrBus.setImageResource(R.drawable.bus_icon);
            }
            else if(arrayList[i][2].equalsIgnoreCase("NODATA")) {
                //System.out.println("데이터가 없단다");
                viewHolder.CarOrBus.setImageResource(R.drawable.nullllll);
            }
            else{
                System.out.println("에러란다!!!!!!!!!!!");
            }
        }


        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckBox(i, !mSelectedItemsIds.get(i));
                //System.out.println("checkNum : " + i + " - " + mSelectedItemsIds.get(i));
                cntCheck[i] = mSelectedItemsIds.get(i);
            }
        });

        viewHolder.label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckBox(i, !mSelectedItemsIds.get(i));
                //System.out.println("checkNum : " + i + " - " + mSelectedItemsIds.get(i));
                cntCheck[i] = mSelectedItemsIds.get(i);
            }
        });

        viewHolder.data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckBox(i, !mSelectedItemsIds.get(i));
               // System.out.println("checkNum : " + i + " - " + mSelectedItemsIds.get(i));
                cntCheck[i] = mSelectedItemsIds.get(i);
            }
        });

        viewHolder.CarOrBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckBox(i, !mSelectedItemsIds.get(i));
                System.out.println("checkNum : " + i + " - " + mSelectedItemsIds.get(i));
                cntCheck[i] = mSelectedItemsIds.get(i);
            }
        });

        return view;
    }

    private class ViewHolder {
        private TextView label;
        private CheckBox checkBox;
        private TextView data;
        private ImageView CarOrBus;
    }


    /**
     * Remove all checkbox Selection
     * */

    public void removeSelection() {
        for(int i = 0; i < arrayList.length; i++){
            checkCheckBox(i, false);
        }

    }

    /**
     * Check the Checkbox if not checked
     * */

    public void checkCheckBox(int position, boolean value) {
        if (value){
            //System.out.println(position + "에 체크가 되었단다");
            mSelectedItemsIds.put(position, true);
        }
        else{
            //System.out.println(position + "에 체크는 개뿔");
            mSelectedItemsIds.delete(position);
        }
        notifyDataSetChanged();
    }

    /**
     * Return the selected Checkbox IDs
     * */

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    public boolean[] getCntCheck(){
        return cntCheck;
    }
    public void setCntCheckAll(){
        for(int i = 0; i < cntCheck.length; i++){
            cntCheck[i] = true;
        }
    }
}
