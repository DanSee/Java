package com.example.cosc195_assign2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Db extends SQLiteOpenHelper
{
    public static final String DB_NAME = "MilkSales";
    public static final String TABLE_NAME = "MilkSalesTable" ;
    public static final String ID = "_id";
    public static final String ON_HAND = "MilkOnHand";
    public static final String ON_ORDER = "MilkOnOrder";
    public static final int DB_VERSION = 1;

    public SQLiteDatabase db;

    public Db(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        // TODO Auto-generated constructor stub
    }


    //C reate   
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Executes when the database is created for the first time
        String sCreate = "create table " +
                TABLE_NAME + " (" +
                ID + " integer primary key autoincrement, " +
                ON_HAND + " int not null, " +
                ON_ORDER + " int not null);";

        //Execute the sql statement / create
        db.execSQL(sCreate);
        ContentValues cv = new ContentValues();

        //set initial values
        cv.put(ON_HAND, 50);
        cv.put(ON_ORDER, 10);

        db.insert(TABLE_NAME, null, cv);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        //The following code will drop the existing table and create an new empty one.
        //This is being done for testing purposes.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //Recreate the table
        onCreate(db);
    }




    //R etrieve
    public Cursor getMilkNumbers()
    {

        String[] fields = new String[]{ON_HAND, ON_ORDER};

        return  db.query(TABLE_NAME, fields,  null, null, null, null, null);
    }


    //     SellMilk(int milkSold)

    //     getMilkOnOrder()
    //      setMilkOnOrder()
    //  

    //U pdate
    public boolean changeMilkNumbers(int milk_ON_HAND, int milk_TO_ORDER)
    {

        //Wrap all fields in a ContentValues object
        ContentValues cvs =  new ContentValues();
        //Put your data into the ContentValues object
        cvs.put(ON_HAND, milk_ON_HAND);
        cvs.put(ON_ORDER, milk_TO_ORDER);

        //Call update
        return db.update(TABLE_NAME, cvs, ID + " = 1", null) > 0;
    }


    //D elete
    public boolean sellMilk(int milkSold)
    {

        //Wrap all fields in a ContentValues object
        ContentValues cvs =  new ContentValues();
        //Put your data into the ContentValues object
        
        
        
        cvs.put(ON_HAND, milkSold);
        // cvs.put(ON_ORDER, milkToOrder);


        //Call update
        return db.update(TABLE_NAME, cvs, null, null) > 0;
    }





    //Open the database or create one if it doesn't exist
    public void open() throws SQLException
    {
        try
        {
            db = this.getWritableDatabase();
        }
        catch (SQLException ex)
        {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>SQLEXCEPTION<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        }
    }

    public void close()
    {
        db.close();
    }
}



