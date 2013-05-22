package com.example.cosc195_assign2;





import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener
{
    private Db milkDB;
    private ProgressBar progressBar;
    private ImageView milkJug; 


    private int milkOnHand; 
    private int milkTOOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        createDatabase();

        setContentView(R.layout.activity_main);

        milkJug = (ImageView)findViewById(R.id.milkJug);
        progressBar = (ProgressBar)findViewById(R.id.progressBar1);
        progressBar.setMax(100); 





        // milkDB.createMilkPurchase(10);

        //changeColor();


    }


    @SuppressLint("NewApi")
    private void createDatabase()
    {

        milkDB = new Db(this); 


        milkDB.open();

        milkDB.onUpgrade(milkDB.db, 0, 1);

        System.out.println(  milkDB.getDatabaseName());

        milkOnHand = 50;

        milkDB.changeMilkNumbers(50, 50);

        Cursor c = milkDB.getMilkNumbers();
        c.moveToFirst();

        int num = c.getInt(0);


        System.out.println(num);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 
     * @param v
     */
    public void adjustDeliveryAmount(View v)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Adjust Delivery Amount");
        builder.setMessage("Enter new milk delivery amount.");
        final EditText input = new EditText(this); 
        builder.setView(input);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which)
            {

                try
                {
                int value = Integer.parseInt(input.getText().toString());

                milkDB.changeMilkNumbers(value, value);


                changeColor();
                }
                catch(Exception e)
                {
                    Toast.makeText(MainActivity.this, "Cant enter blank", Toast.LENGTH_LONG);
                }



            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    private void sell(int sold)
    {

        milkOnHand -= sold;
        milkDB.changeMilkNumbers(milkOnHand, milkTOOrder);

        progressBar.setProgress(milkOnHand);


    }




    private void changeColor()
    {
        Cursor cursor =  milkDB.getMilkNumbers() ;
        cursor.moveToFirst();

        int quantity = cursor.getInt(0);

        //   Toast.makeText(this, "" + cursor.getInt(1), Toast.LENGTH_SHORT).show();

        //        Toast.makeText(this, "" + cursor.getString(1), Toast.LENGTH_SHORT).show();
        //        Toast.makeText(this, "" + quantity, Toast.LENGTH_LONG).show();

        if (quantity < 5 || quantity > 95)
        {
            milkJug.setBackgroundColor(Color.RED);
        }
        else if (quantity < 10 || quantity  > 90 )
        {
            milkJug.setBackgroundColor(Color.YELLOW);
        }
        else
        {
            milkJug.setBackgroundColor(Color.GREEN);
        }
    }

    ///////////////////////
    public class MyAlarmReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent i)
        {
            Toast.makeText(context, "alarm has sounded", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onClick(View v) {
        //The view passed in is a button, so cast it to that type
        Button b = (Button)v;

        switch(b.getId())
        {
            case R.id.button1:

                Log.w("onClick", "clicked 1");

                sell(1);

                break;
            case R.id.button2:

                sell(2);

                break;
            case R.id.button3:

                sell(3);

                break;
            case R.id.button4:

                sell(4);

            default:
                break;
        }


    }





}
