package com.vhackclub.oliu;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.MessageDialog;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.vhackclub.oliu.base.Event;

import java.util.Calendar;


public class CreateEventActivity extends ActionBarActivity {

    public int year;
    public int month;
    public int day;
    public int hour;
    public int minute;
    public EditText textDate;
    public EditText textTime;

    CallbackManager callbackManager;
    MessageDialog messageDialog;
    public Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        textDate = (EditText) findViewById(R.id.textDate);
        textDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mCurrentTime = Calendar.getInstance();
                int mYear = mCurrentTime.get(Calendar.YEAR);
                int mMonth = mCurrentTime.get(Calendar.MONTH);
                int mDay = mCurrentTime.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(CreateEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth = selectedmonth + 1;
                        textDate.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                        year = selectedyear;
                        month = selectedmonth;
                        day = selectedday;
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });
        textTime = (EditText) findViewById(R.id.textTime);
        textTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCurrentTime = Calendar.getInstance();
                int getHour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int getMinute = mCurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        textTime.setText(selectedHour + ":" + selectedMinute);
                        hour = selectedHour;
                        minute = selectedMinute;
                    }
                }, getHour, getMinute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        Button btnAsk = (Button) findViewById(R.id.btnNext);
        btnAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(CreateEventActivity.this, "", "Making plan ...", true);
                createEvent();
            }
        });
    }

    private void fallback(String error) {
        progressDialog.dismiss();
        Toast.makeText(CreateEventActivity.this, error, Toast.LENGTH_SHORT).show();
    }

    public void createEvent() {
        final Event event = new Event();
        RadioGroup radioButtonGroup = (RadioGroup) findViewById(R.id.whatGroup);
        int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) radioButtonGroup.findViewById(radioButtonID);
        if (radioButton == null) {
            fallback("What is not selected!");
            return;
        }
        event.setType(Event.TYPE.valueOf(radioButton.getText().toString().toUpperCase()));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        event.setWhen(cal.getTime());
        // TODO : validate the time

        event.setHost(ParseUser.getCurrentUser());
        event.addMember(ParseUser.getCurrentUser());
        event.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                prepareShareDialog();
                if (e == null) {
                    shareOnFacebook(event);
                } else {
                    fallback("Fail to save event");
                }
            }
        });
    }

    public void prepareShareDialog() {
        callbackManager = CallbackManager.Factory.create();
        messageDialog = new MessageDialog(CreateEventActivity.this);
        messageDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Log.d("onSuccess", "result " + result.getClass());
                // TODO fix the case where user presses back from share
                progressDialog.dismiss();
                startActivity(new Intent(CreateEventActivity.this, EventPlannerActivity.class));
                finish();
            }

            @Override
            public void onCancel() {
                fallback("Share is canceled");
            }

            @Override
            public void onError(FacebookException e) {
                fallback("Share is failed");
            }
        });
    }

    public void shareOnFacebook(Event event) {
        if (MessageDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("Yo yo Oliu")
                    .setContentDescription(event.getGreeting())
                    .setContentUrl(Uri.parse("http://oliu.io/" + event.getObjectId()))
                    .build();

            messageDialog.show(CreateEventActivity.this, linkContent);
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
