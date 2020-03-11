package com.example.registrationlist;

import android.app.DatePickerDialog;
import android.content.Intent;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.util.Log;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateResidentsActivity extends AppCompatActivity {

    Button SubmitButton;
    Button BackButton;
    Button DatePickerButton;
    EditText NameEditText;
    TextView mDisplayDate;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    AwesomeValidation awesomeValidation;
    int dobMonths;
    int dobYears;
    int dobDays;
    private static final String TAG = "CreateResidentsActivity";
    boolean flag = false;
    // Declaring String variable ( In which we are storing firebase server URL ).
    public static final String Firebase_Server_URL = "https://application-demo-dbc5d.firebaseio.com/";

    // Declaring String variables to store name & phone number get from EditText.
    String NameHolder, DOBHolder;
    String finalAge;
    Firebase firebase;
    DatabaseReference databaseReference;

    // Root Database Name for Firebase Database.
    public static final String Database_Path = "Resident_Details_Database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_residents);

        Firebase.setAndroidContext(CreateResidentsActivity.this);
        firebase = new Firebase(Firebase_Server_URL);
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);
        SubmitButton = (Button)findViewById(R.id.submit);
        NameEditText = (EditText)findViewById(R.id.name);

        mDisplayDate = (EditText) findViewById(R.id.dob);
        mDisplayDate.setEnabled(false);

        BackButton = (Button)findViewById(R.id.back);
        DatePickerButton = (Button)findViewById(R.id.datepicker);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.name,"[a-zA-Z][a-zA-Z ]*",R.string.invalid_name);

        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(awesomeValidation.validate() && flag){
                    ResidentDetails residentDetails = new ResidentDetails();

                    GetDataFromEditText();

                    // Adding name into class function object.
                    residentDetails.setResidentName(NameHolder);

                    // Adding phone number into class function object.
                    residentDetails.setResidentDateOfBirth(DOBHolder);

                    // Getting the ID from firebase database.
                    String ResidentRecordIDFromServer = databaseReference.push().getKey();

                    // Adding the both name and number values using student details class object using ID.
                    databaseReference.child(ResidentRecordIDFromServer).setValue(residentDetails);

                    // Showing Toast message after successfully data submit.
                    Toast.makeText(CreateResidentsActivity.this,"Data Saved Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CreateResidentsActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Validation failed, Both fields should be correct",Toast.LENGTH_SHORT).show();
                }

            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateResidentsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        DatePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog= new DatePickerDialog(CreateResidentsActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date1 = simpleDateFormat1.parse(date);
                    String date2 = simpleDateFormat1.format(date1);
                    //age calculate
                    Calendar cal = Calendar.getInstance();
                    int currentYear = cal.get(Calendar.YEAR);
                    int currentMonth = cal.get(Calendar.MONTH) + 1;
                    int currentday = cal.get(Calendar.DAY_OF_MONTH);
                    Log.d(TAG,"....................."+currentYear+ currentMonth+ currentday + " " + year + month + day);
                    if((currentYear-year)==0 && (currentMonth-month)>0){
                        flag = true;
                        dobMonths = currentMonth - month;
                        finalAge = dobMonths + " Months";
                        Log.d(TAG,"1........."+finalAge);
                        mDisplayDate.setEnabled(true);
                        mDisplayDate.setText(date2);
                        mDisplayDate.setTextColor(Color.BLACK);
                        mDisplayDate.setEnabled(false);
                    }
                    else if((currentYear-year)==0 && (currentMonth-month)==0 && (currentday-day)>0 ){
                        flag = true;
                        dobDays = currentday - day;
                        finalAge = dobDays + " days";
                        Log.d(TAG,"2........."+finalAge);
                        mDisplayDate.setEnabled(true);
                        mDisplayDate.setText(date2);
                        mDisplayDate.setTextColor(Color.BLACK);
                        mDisplayDate.setEnabled(false);
                    }
                    else if((currentYear-year)>0){
                        flag = true;
                        dobYears = ((12-month) + 12*(currentYear-year-1) + (currentMonth))/12;
                        dobMonths = ((12-month) + (currentMonth))%12;
                        if(dobYears >0 && dobMonths >0){
                            finalAge =  dobYears + " years " + dobMonths + " Months";
                        }
                        else if(dobMonths >0 && dobYears ==0){
                            finalAge = dobMonths + " Months";
                        }
                        else{
                            finalAge = dobYears + " years";
                        }

                        Log.d(TAG,"3........."+finalAge);
                        mDisplayDate.setEnabled(true);
                        mDisplayDate.setText(date2);
                        mDisplayDate.setTextColor(Color.BLACK);
                        mDisplayDate.setEnabled(false);
                    }
                    else{
                        Toast.makeText(CreateResidentsActivity.this,"Please select correct dob", Toast.LENGTH_LONG).show();
                        mDisplayDate.setEnabled(true);
                        mDisplayDate.setText("DOB (DD/MM/YYYY)");
                        mDisplayDate.setTextColor(Color.GRAY);
                        mDisplayDate.setEnabled(false);
                        flag = false;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
               Log.d(TAG,"4...................."+finalAge);
            }
        };
    }

    public void GetDataFromEditText(){
        NameHolder = NameEditText.getText().toString().trim();
        DOBHolder = finalAge.trim();
        Log.d(TAG,"DOB holder...................."+DOBHolder);
    }
}
