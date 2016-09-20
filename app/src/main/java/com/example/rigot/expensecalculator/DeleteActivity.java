package com.example.rigot.expensecalculator;
/*
    Name: Rodrigo Trejo
    Name: Anal Shah
    HW2
 */
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DeleteActivity extends AppCompatActivity {

    public static String DELETE = "Delete";
    private int temp = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        findViewById(R.id.delete).setEnabled(false);
        final TextView expenseName = (TextView) findViewById(R.id.expenseName);
        final TextView expenseAmount = (TextView) findViewById(R.id.expenseAmount);
        final TextView dateText = (TextView) findViewById(R.id.dateText);
        final Calendar cal = Calendar.getInstance();    //create calendar instance with current date
        final Spinner categorySpinner = (Spinner)findViewById(R.id.categoryDropDown);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView_Delete);
        //set arrayadapter for choices
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //set dropdown layout
        categorySpinner.setAdapter(adapter);    //set spinner adapter
        categorySpinner.setEnabled(false);
        categorySpinner.setVisibility(View.INVISIBLE);

        //create datepickerdialog and set date from calendar
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, day);
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");   //set a date format
                dateText.setText(format.format(cal.getTime())); //display date when it is selected from datepicker
            }
        };

        ArrayList<Expense> expenses = new ArrayList<>();

        if(getIntent().getExtras()!=null){
            expenses =  getIntent().getParcelableArrayListExtra(DELETE);  //receive array list of expense
        }

        //create CharSequence array and store expense names
        int size = expenses.size();
        final CharSequence[] items = new CharSequence[size];
        for(int j=0; j< expenses.size(); j++){
            items[j] = expenses.get(j).name;
        }

        final ArrayList<Expense> finalExpenses = expenses;

        //create AlertDialog object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //display values of expense to be deleted
        builder.setTitle("Pick an Expense").setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int breaker = -1;
                while(breaker != finalExpenses.size()){
                    breaker ++;
                    if(items[i].toString().equals(finalExpenses.get(breaker).name)){
                        expenseName.setText(finalExpenses.get(breaker).name);
                        expenseAmount.setText(String.valueOf(finalExpenses.get(breaker).amount));
                        categorySpinner.setSelection(adapter.getPosition(finalExpenses.get(breaker).category));
                        categorySpinner.setVisibility(View.VISIBLE);
                        dateText.setText(finalExpenses.get(breaker).date);
                        if (finalExpenses.get(breaker).imageUri != null)
                            imageView.setImageURI(Uri.parse(finalExpenses.get(breaker).imageUri));
                        else
                            imageView.setImageResource(0);
                        temp = breaker;
                        breaker = finalExpenses.size();
                        findViewById(R.id.delete).setEnabled(true);
                    }
                }
            }
        });

        final AlertDialog alert = builder.create();

        findViewById(R.id.selectExpenseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.show();
            }
        });

        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deleteIntent = new Intent();
                deleteIntent.putExtra("DELETE_POSITION", temp);
                setResult(RESULT_OK, deleteIntent);
                finish();
            }
        });

        findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
