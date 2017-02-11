package com.a2a.app.a2aapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2/5/16.
 */
public class CartDatabase {

    public static final String KEY_ROWID="_id";
    public static final String KEY_NAME="product_name";
    public static final String KEY_PRICE="PRICE";
    public static final String PRODUCT_ID="product_ID";
    public static final String QTY="QUANTITY";


  //  public static final String KEY_HOTNESS="persons_hotness";
    private static final String DATABASE_NAME="CartProduct";
    private static final String DATABASE_TABLE="producttable";
    private static final int DATABASE_VERSION=2;
    private Dbhelper ourhelper;
    private final Context ourcontext;
    private SQLiteDatabase ourdatabase;

    public void createentry(String productname,String price,String id) {

   //    public boolean check(String productname){

        //   Cursor c = ourdatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE+" WHERE " + KEY_NAME + "= ", new String[]{productname});
         //   if(c==null) {
                // return true;
                //  }
                //  else{return false;}
                //  }
                ContentValues cv = new ContentValues();
                cv.put(KEY_PRICE, price);
                cv.put(PRODUCT_ID, id);
                cv.put(KEY_NAME, productname);
                // cv.put(QTY,1);
                ourdatabase.insert(DATABASE_TABLE, null, cv);
            }
 //   }

    public List<Bean> getData() {
        String[] columns=new String[] {KEY_ROWID,KEY_NAME,KEY_PRICE,PRODUCT_ID};

        Cursor c=ourdatabase.query(DATABASE_TABLE,columns,null,null,null,null,null);
        String result="";
        int irow=c.getColumnIndex(KEY_ROWID);

        int iname=c.getColumnIndex(KEY_NAME);
        int iprice=c.getColumnIndex(KEY_PRICE);
        int iproductid=c.getColumnIndex(PRODUCT_ID);
       // int iquantity=c.getColumnIndex(QTY);
        List<Bean> list=new ArrayList<>();
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            Bean bean=new Bean();
            //result=result+c.getString(iname)+"\n";
            bean.setId(c.getString(iproductid));
            bean.setProduct_name(c.getString(iname));
            bean.setPrice(c.getString(iprice));
          //  bean.setQuantity(c.getInt(iquantity));
            list.add(bean);
        }
        return list;
    }

    public void updateEntry(long l, String name, String hotness) {
        ContentValues cv=new ContentValues();
        cv.put(KEY_NAME, name);
        Log.d("y", "y");
        ourdatabase.update(DATABASE_TABLE, cv, KEY_ROWID + "=" + l, null);
    }
    public boolean checkentry(String id) {
        Cursor c;
        c = ourdatabase.query( DATABASE_TABLE, new String[]{PRODUCT_ID, KEY_NAME}, PRODUCT_ID + "=" + id, null, null, null, null, null);
        if (c.getCount()==0) {
            return true;
        } else {
            return false;
        }
    }
    public void deleteEntry(String l) throws SQLException {
        ourdatabase.delete(DATABASE_TABLE, PRODUCT_ID + "=" + l, null);
       // reset();
    }

    private static class Dbhelper extends SQLiteOpenHelper {
        public Dbhelper(Context context) {
            super(context,DATABASE_NAME, null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT NOT NULL, " + KEY_PRICE + "  TEXT NOT NULL, " + PRODUCT_ID + " TEXT NOT NULL, " + QTY + " INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);
        }
    }

    public CartDatabase(Context c){
        ourcontext=c;
    }
    public CartDatabase open()throws SQLException {
       ourhelper=new Dbhelper(ourcontext);
        ourdatabase=ourhelper.getWritableDatabase();
        return this;
    }

    public void close(){
        ourhelper.close();
    }
}
