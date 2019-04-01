package com.laxman.newsheadline.DataBaseHelperClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.laxman.newsheadline.Model.TopHeadlineModelClass;

import java.util.ArrayList;
import java.util.List;

public class OfflineStrgeHelperClass extends SQLiteOpenHelper {
    public OfflineStrgeHelperClass(Context context) {
        super(context, "TopNews.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table  NewsHeadlineTable(id text  Primary Key,name text,author text,title text,description text,url text,urlToImage text,publishedAt text,content text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if EXISTS NewsHeadlineTable");
        this.onCreate(db);

    }

    public boolean save(TopHeadlineModelClass topHeadlineModelClass) {


        SQLiteDatabase sqliteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("id", topHeadlineModelClass.getId());
        contentValues.put("name", topHeadlineModelClass.getName());
        contentValues.put("author", topHeadlineModelClass.getAuthor());
        contentValues.put("title", topHeadlineModelClass.getTitle());
        contentValues.put("description", topHeadlineModelClass.getDescription());
        contentValues.put("url", topHeadlineModelClass.getUrl());
        contentValues.put("urlToImage", topHeadlineModelClass.getUrlToImage());
        contentValues.put("publishedAt", topHeadlineModelClass.getPublishedAt());
        contentValues.put("content", topHeadlineModelClass.getContent());
        try {
            sqliteDatabase.insert("NewsHeadlineTable", null, contentValues);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<TopHeadlineModelClass> getAll() {
        List<TopHeadlineModelClass> topHeadlineModelClasses = new ArrayList<>();

        // String selectQuery = "Select * from dmhasset";
        // String selectQuery = "SELECT DISTINCT id,title,description,url,urlToImage,publishedAt FROM NewsHeadlineTable";

        String selectQuery = "SELECT DISTINCT * from NewsHeadlineTable ORDER BY id DESC LIMIT 50";

/*
        String selectQuery="SELECT DISTINCT * FROM NewsHeadlineTable WHERE id = (SELECT MAX(id) FROM NewsHeadlineTable)";
*/

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            {
                if (cursor.moveToNext()) {
                    while (!cursor.isAfterLast()) {
                        TopHeadlineModelClass topHeadlineModelClass = new TopHeadlineModelClass();
                        topHeadlineModelClass.setId(cursor.getString(0));
                        topHeadlineModelClass.setName(cursor.getString(1));
                        topHeadlineModelClass.setAuthor(cursor.getString(2));
                        topHeadlineModelClass.setTitle(cursor.getString(3));
                        topHeadlineModelClass.setDescription(cursor.getString(4));
                        topHeadlineModelClass.setUrl(cursor.getString(5));
                        topHeadlineModelClass.setUrlToImage(cursor.getString(6));
                        topHeadlineModelClass.setPublishedAt(cursor.getString(7));
                        topHeadlineModelClass.setContent(cursor.getString(8));
                        topHeadlineModelClasses.add(topHeadlineModelClass);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return topHeadlineModelClasses;
    }
}
