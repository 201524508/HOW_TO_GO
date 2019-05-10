package com.example.home.how_to_go;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by s0woo on 2017-06-18.
 */

public class DBPathList extends SQLiteOpenHelper {

    public DBPathList(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists SavedPath("
                + "_index text, " // 자동차 CAR, 대중교통 BUS
                + "path text, "
                + "timeNcost text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists SavedPath";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insert(String _index, String path, String timeNcost) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put("id", i);
        values.put("_index", _index);
        values.put("path", path);
        values.put("timeNcost", timeNcost);
        db.insert("SavedPath", null, values);
        db.close();
    }

    public void delete(String cond, String path_, String data_) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제

        //System.out.println("delete 확인용 : " + path_ + " " + data_ + " " + cond);

        db.delete("SavedPath", "_index=? and path=? and timeNcost=?", new String[] {cond, path_, data_});
        //db.delete("SavedPath", "id" + " = " + i, null);
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();

        db.delete("SavedPath", null, null);
    }

    public String getResult() {
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM SavedPath", null);

        while (cursor.moveToNext()) {
            result += cursor.getString(0)
                    + " (자동차CAR, 대중교통BUS) | 경로 : "
                    + cursor.getString(1)
                    + " "
                    + cursor.getString(2)
                    + "\n";
        }

       return result;
    }

}