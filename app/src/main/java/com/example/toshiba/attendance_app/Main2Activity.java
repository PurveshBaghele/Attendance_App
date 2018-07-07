package com.example.toshiba.attendance_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    TextView tv[]=new TextView[500]  ;
   // TextView cv ;
    LinearLayout linear ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        linear=(LinearLayout)findViewById(R.id.linear) ;
       // cv=(TextView)findViewById(R.id.show) ;
        String add=getIntent().getStringExtra("data") ;
        String parts[]=add.split("X") ;
        //Toast.makeText(getApplicationContext(),""+parts.length,Toast.LENGTH_SHORT).show();
        //cv.setText(add);
        int i =0 ;
        for(i=0;i<parts.length;i++)
        {
            final TextView cv=new TextView(this) ;
            cv.setText(parts[i]);
            //tv[i].setText(parts[i])  ;
            linear.addView(cv) ;
        }
        //tv.setText(getIntent().getStringExtra("data").replace("\n","<br>");


       //tv.setText(add.replaceAll("\\n","\n"));
    }
}
