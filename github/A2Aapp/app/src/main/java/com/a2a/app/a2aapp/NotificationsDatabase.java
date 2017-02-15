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
 * Created by Heerok Banerjee on 6/7/2016.
 */
public class NotificationsDatabase {
    public NotificationsDatabase(Context c){
        mcontext=c;
    }
    public NotificationsDatabase open()throws SQLException {
        ourhelper=new Dbhelper(mcontext);
        mdatabase=ourhelper.getWritableDatabase();
        return this;
    }
    public void close(){
        ourhelper.close();
    }

    //Table details
    public static final String KEY_NOTEID="NOTE_ID";
    public static final String KEY_ORDERID="ORDER_ID";
    public static final String KEY_RETID="RET_ID";
    public static final String KEY_NAME="product_name";
    public static final String KEY_PRICE="PRICE";
    public static final String PRODUCT_ID="product_ID";
    public static final String QTY="QUANTITY";
    public static final String TOTAL="TOTAL";
    public static final String DEL_TYPE="delivery_TYPE";
    public static final String CONTACT_NUM="CONTACT_NUM";
    public static final String ADDRESS="ADDRESS";

    //DB details

    public static final String DB_NAME = "NotificationsDB.db";
    public static final String DB_TABLE = "notifications";
    public static final int DB_VER = 1;
    private Dbhelper ourhelper;
    private Context mcontext=null;
    private static SQLiteDatabase mdatabase;
    String[] columns=new String[] {KEY_NOTEID,KEY_ORDERID,KEY_RETID,KEY_NAME,KEY_PRICE,PRODUCT_ID,QTY,TOTAL,DEL_TYPE,CONTACT_NUM,ADDRESS};

    public void createentry(String noteid,int orderid,String retid,String productname,int price,int Qty,long total,int pdtid,String del_type,String cnt,String addr) {

        //    public boolean check(String productname){

        //   Cursor c = ourdatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE+" WHERE " + KEY_NAME + "= ", new String[]{productname});
        //   if(c==null) {
        // return true;
        //  }
        //  else{return false;}
        //  }
        ContentValues cv = new ContentValues();
        cv.put(KEY_NOTEID,noteid);
        cv.put(KEY_ORDERID,orderid);
        cv.put(KEY_RETID,retid);
        cv.put(KEY_NAME, productname);
        cv.put(KEY_PRICE, price);
        cv.put(PRODUCT_ID, pdtid);
        cv.put(QTY,Qty);
        cv.put(TOTAL,total);
        cv.put(DEL_TYPE,del_type);
        cv.put(CONTACT_NUM,cnt);
        cv.put(ADDRESS,addr);
        // cv.put(QTY,1);
        mdatabase.insert(DB_TABLE, null, cv);
        Log.e("MyActivity","Product inserted");
    }
    //   }
    public void deleteEntry(String l) throws SQLException {
        mdatabase.delete(DB_TABLE, PRODUCT_ID + "=" + l, null);
        // reset();
    }

    public static boolean checkentry(String noteid ) {
        String Query = "Select * from " + DB_TABLE + " where " + KEY_NOTEID + " = " + noteid;
       Cursor cursor = mdatabase.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    public List<notification> getData() {
        Cursor c=mdatabase.query(DB_TABLE,columns,null,null,null,null,null);
        int inote=c.getColumnIndex(KEY_NOTEID);
        int iorder=c.getColumnIndex(KEY_ORDERID);
        int iret_id=c.getColumnIndex(KEY_RETID);
        int iname=c.getColumnIndex(KEY_NAME);
        int iprice=c.getColumnIndex(KEY_PRICE);
        int iproductid=c.getColumnIndex(PRODUCT_ID);
        int iqty=c.getColumnIndex(QTY);
        int itotal=c.getColumnIndex(TOTAL);
        int ideltype=c.getColumnIndex(DEL_TYPE);
        int icnt=c.getColumnIndex(CONTACT_NUM);
        int iaddr=c.getColumnIndex(ADDRESS);
        // int iquantity=c.getColumnIndex(QTY);
        List<notification> list=new ArrayList<>();
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            notification note=new notification();
            //result=result+c.getString(iname)+"\n";
            note.setnoteId(c.getString(inote));
            note.setOrder_id(c.getInt(iorder));
            note.setretid(c.getString(iret_id));
            note.setProduct_name(c.getString(iname));
            note.setPrice(c.getInt(iprice));
            note.setProduct_id(c.getInt(iproductid));
            note.setQuantity(c.getInt(iqty));
            note.setTotal(c.getLong(itotal));
            note.setDel_type(c.getString(ideltype));
            note.setContactnum(c.getString(icnt));
            note.setAddress(c.getString(iaddr));
            //  bean.setQuantity(c.getInt(iquantity));
            list.add(note);
        }
        return list;
    }







    private static class Dbhelper extends SQLiteOpenHelper {
        public Dbhelper(Context context) {
            super(context, DB_NAME, null, DB_VER);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DB_TABLE + " (" + KEY_NOTEID + " INTEGER PRIMARY KEY, "+KEY_ORDERID+" INTEGER NOT NULL, "+ KEY_RETID +" INTEGER , " + KEY_NAME + " TEXT NOT NULL, " + KEY_PRICE + "  INTEGER NOT NULL, " + PRODUCT_ID + " INTEGER NOT NULL, " + QTY + " INTEGER, "+TOTAL+" INTEGER, "+DEL_TYPE+" TEXT, "+ CONTACT_NUM +" TEXT NOT NULL, "+ ADDRESS + " TEXT NOT NULL );");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +DB_TABLE );
            onCreate(db);
        }
    }
}