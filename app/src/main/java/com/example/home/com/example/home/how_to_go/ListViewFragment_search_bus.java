package com.example.home.how_to_go;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by YUUUUU on 2017-06-19.
 */

public class ListViewFragment_search_bus extends Fragment {
    SeekActivity seek;
    private Context context;
    private GridListAdapter_bus adapter;
    private String[][] path;
    SparseBooleanArray tmp;
    boolean[] tmp2;

    public ListViewFragment_search_bus(){

    }

    public void setPath(String[][] s){
        path = s;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_view_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //selectButton = (Button) view.findViewById(R.id.select_button);
        loadListView(view);
        //onClickEvent(view);
    }

    private void loadListView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.list_view);

        adapter = new GridListAdapter_bus(context, path, true);
        listView.setAdapter(adapter);

        tmp = adapter.getSelectedIds();
        tmp2 = adapter.getCntCheck();
    }

    public SparseBooleanArray getSelect(){
        return tmp;
    }
    public boolean[] getCheck(){
        return tmp2;
    }

    public void removeCheck(){
        adapter.removeSelection();
    }
}
