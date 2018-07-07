package com.example.toshiba.attendance_app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.view.View.OnFocusChangeListener ;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream ;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Spinner sp1 ;
    static EditText et1,et2,et3,et4 ;
    Button save,show ;
    String data,present ;
    RadioButton absent ;
    static int select,flag ;
    int check=0 ;
    OutputStreamWriter outputStreamWriter ;
    BufferedWriter bufferedWriter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnSpinnerItemSelection() ;
        save=(Button)findViewById(R.id.button) ;
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Toast.makeText(getApplicationContext(), ""+sp1.getSelectedItem(), Toast.LENGTH_SHORT).show() ;

            }
        }) ;
        flag=0 ;


        et1=(EditText)findViewById(R.id.editText) ;
        //et1.setFocusable(true);
        //et1.setFocusableInTouchMode(true);
        et1.setOnFocusChangeListener(new OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                showDatePickerDialog(et1);
            }

        });
        et2=(EditText)findViewById(R.id.editText2) ;
        et2.setOnFocusChangeListener(new OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                    showTimePickerDialog(et2) ;

            }

        });
        et3=(EditText)findViewById(R.id.editText3) ;
        et3.setOnFocusChangeListener(new OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                    showTimePickerDialog(et3) ;

            }

        });
        et4=(EditText)findViewById(R.id.editText4) ;
        absent=(RadioButton)findViewById(R.id.radioButton) ;
        save=(Button)findViewById(R.id.button) ;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(absent.isChecked())
                    present="ABSENT" ;
                else
                    present="PRESENT" ;
                if(flag==0) {
                    try {
                       // OutputStream outStream = openFileOutput("attendance.txt");
                        //OutputStreamWriter outputStreamReader = new OutputStreamWriter(outStream);
                        bufferedWriter= new BufferedWriter(new OutputStreamWriter(openFileOutput("attendance.txt", Context.MODE_APPEND)));
                        flag=1 ;
                    } catch (IOException e) {

                    }
                }
                data="Subject: "+sp1.getSelectedItem()+" X Date: "+et1.getText().toString()+" X start time: "+et2.getText().toString()+" X end time: "+et3.getText().toString()+" X status= "+present+" X Roll No. : "+et4.getText().toString()+" X----------------------------X\n" ;
                try {
                    //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("attendance.txt", Context.MODE_PRIVATE));
                    //outputStreamWriter.write(data);
                    bufferedWriter.append(data) ;
                    bufferedWriter.newLine();
                    //outputStreamWriter.close();
                }
                catch (IOException e) {
                    //Log.e("Exception", "File write failed: " + e.toString());
                }
                Toast.makeText(getApplicationContext(),"Information Saved!",Toast.LENGTH_SHORT).show() ;
            }
        });
        show=(Button)findViewById(R.id.button2) ;
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(flag==1)
                    {
                        bufferedWriter.close();
                        flag=0 ;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                String ret = "";

                try {
                    InputStream inputStream = openFileInput("attendance.txt");

                    if ( inputStream != null ) {
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String receiveString = "\n";
                        StringBuilder stringBuilder = new StringBuilder();

                        while ( (receiveString = bufferedReader.readLine()) != null ) {
                            stringBuilder.append(receiveString);
                        }

                        inputStream.close();
                        ret = stringBuilder.toString();
                    }
                }
                catch (FileNotFoundException e) {
                    Log.e("login activity", "File not found: " + e.toString());
                } catch (IOException e) {
                    Log.e("login activity", "Can not read file: " + e.toString());
                }
                Intent i=new Intent(getApplicationContext(),Main2Activity.class) ;
                i.putExtra("data",ret) ;
                startActivity(i) ;

            }
        });
        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check==0)
                    check=1 ;
                else if(check==1)
                {
                    absent.setChecked(false) ;
                    check=0 ;
                }
            }
        });
    }

    public void addListenerOnSpinnerItemSelection() {
        sp1 = (Spinner) findViewById(R.id.spinner);
        //sp1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        static String date ;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
            //tv.setText(""+day+"/"+month+"/"+year) ;
            et1.setText(""+day+"/"+month+"/"+year) ;

        }
    }

    public void showDatePickerDialog(EditText et) {
        //tv=(TextView)findViewById(R.id.textView2) ;
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
        //et.setText(DatePickerFragment.date);
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if(select==2)
                et2.setText(""+hourOfDay+":"+minute) ;
            else
                et3.setText(""+hourOfDay+":"+minute) ;
            //time=""+hourOfDay+":"+minute ;
        }
    }

    public void showTimePickerDialog(View v) {
        if(v.getId()==R.id.editText2)
            select=2 ;
        else
            select=3 ;
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
}



/*
import java.util.Calendar;

        import android.os.Bundle;
        import android.app.Activity;
        import android.app.DatePickerDialog;
        import android.app.TimePickerDialog;
        import android.app.Dialog;
        import android.support.v4.app.DialogFragment;
        import android.support.v4.app.FragmentActivity;
        import android.text.format.DateFormat;
        import android.view.View.OnClickListener ;
        import android.view.Menu;
        import android.view.View;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.TimePicker;
        import android.widget.Toast;
        import android.widget.AdapterView.OnItemSelectedListener ;

public class MainActivity extends FragmentActivity {

    static TextView tv,tv1,tv2 ;
    static String time ;
    Button save ;
    Spinner sp1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnSpinnerItemSelection();
        save=(Button)findViewById(R.id.button2) ;
        save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Toast.makeText(getApplicationContext(), ""+sp1.getSelectedItem(),Toast.LENGTH_SHORT).show() ;

            }
        }) ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            tv.setText(""+day+"/"+month+"/"+year) ;

        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            time=""+hourOfDay+":"+minute ;
        }
    }


    public void showDatePickerDialog(View v) {
        tv=(TextView)findViewById(R.id.textView2) ;
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
        if(v.getId()==R.id.button3)
        {

        }
        else
        {

        }
    }
    public void addListenerOnSpinnerItemSelection() {
        sp1 = (Spinner) findViewById(R.id.spinner1);
        //sp1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }


}
*/