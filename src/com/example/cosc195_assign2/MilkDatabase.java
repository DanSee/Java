package com.example.cosc195_assign2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MilkDatabase extends SQLiteOpenHelper {

    //Database name
    private static final String DB_NAME = "NAME_MILK";
    //Table name
    private static final String TABLE_NAME = "TABLE_MILK";
    //Store DB version
    private static final int DB_VERSION = 1;
    //Define field names
    public static final String ID = "_id";
    public static final String DELIVERY_AMOUNT = "DELIVERY_AMOUNT";
    public static final String CURRENT_INVENTORY = "CURRENT_INVENTORY";
    //Reference to the SQLite Database
    public SQLiteDatabase sqlDB;
    
    public MilkDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Executes when the database is created for the first time
        String sCreate = "create table " +
                        TABLE_NAME + " (" +
                        ID + " integer primary key autoincrement, " +
                        DELIVERY_AMOUNT + " integer not null, " +
                        CURRENT_INVENTORY + " integer not null" + ");";
        //Execute the sql statement
        db.execSQL(sCreate);
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

    
    
    //Open the database or create one if it doesn't exist
    public void open() throws SQLException
    {
        sqlDB = this.getWritableDatabase();
    }
    
    public void close()
    {
        sqlDB.close();
    }
    
    //"C" in CRUD
    //Create a new row in the table, returns the primary key generated (autoincrement value)
    public long createMilkPurchase(int inventory)
    {
        //Wrap all fields in a ContentValues object
        ContentValues cvs =  new ContentValues();
        //Put your data into the ContentValues object
        cvs.put(CURRENT_INVENTORY, inventory );
        //cvs.put(DELIVERY_AMOUNT, deliveryAmount);
            
        return sqlDB.insert(TABLE_NAME, null, cvs);
    }
    
    
    //"R" in CRUD
    //Read all rows in the table and return them
    public Cursor getAllMilkPurchases()
    {
        //The fields that we are retrieving
        String [] sFields = new String [] {ID, CURRENT_INVENTORY, DELIVERY_AMOUNT};
        return sqlDB.query(TABLE_NAME, sFields, null, null, null, null, null);
    }
    
    //"R" in CRUD
    //Read particular records based on the id passed in
    public Cursor getMilkPurchase(long id)
    {
        //The fields that we are retrieving
        String [] sFields = new String [] {ID, CURRENT_INVENTORY, DELIVERY_AMOUNT};
        Cursor fpCursor = sqlDB.query(true, TABLE_NAME, sFields, ID + " = " + id, 
                null, null, null, null, null);
        //If the cursor is not null (has results)
        if(fpCursor != null)
        {
            //Move the internal cursor pointer to the first row
            fpCursor.moveToFirst();
        }
        return fpCursor;
    }
    
    //"U" in CRUD
    //Update a row(s) in the table
    public boolean updateMilkPurchase(int id, int inventory, int deliveryAmount)
    {
        //Wrap all fields in a ContentValues object
        ContentValues cvs =  new ContentValues();
        //Put your data into the ContentValues object
        cvs.put(CURRENT_INVENTORY, inventory );
        cvs.put(DELIVERY_AMOUNT, deliveryAmount);
        
        //Call update
        return sqlDB.update(TABLE_NAME, cvs, ID + " = " + id, null) > 0;
    }
    
    //"D" in CRUD
    //Delete a record from the table
    public boolean deleteMilkPurchase(int id)
    {
        //Delete will return the number of rows affected
        return sqlDB.delete(TABLE_NAME, ID + " = " + id, null) > 0;
    }
    
    
    
    
}
