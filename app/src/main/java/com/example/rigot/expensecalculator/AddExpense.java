package com.example.rigot.expensecalculator;
/*
    Name: Rodrigo Trejo
    Name: Anal Shah
    HW2
 */
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddExpense extends AppCompatActivity {
    static String ADD = "Add";
    static String KEY = "key";
    private static final int SELECT_PHOTO = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        this.setTitle("Add Expense");

        final EditText expense = (EditText) findViewById(R.id.expenseName);
        final EditText expenseAmount = (EditText) findViewById(R.id.expenseAmount);
        final Calendar cal = Calendar.getInstance();    //create calendar instance with current date
        final TextView dateText = (TextView) findViewById(R.id.dateView);
        final Spinner categorySpinner = (Spinner) findViewById(R.id.categoryDropDown);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView_Add);

        //set arrayadapter for choices
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //set dropdown layout
        categorySpinner.setAdapter(adapter);    //set spinner adapter

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

        //when calendar image is clicked
        findViewById(R.id.editDateCalendar).setOnClickListener(new View.OnClickListener() {
            //@TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                //
                new DatePickerDialog(AddExpense.this, date, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //make sure user enters all necessary information
                if (expense.length() == 0) {
                    Toast.makeText(AddExpense.this, "Please enter an expense name!", Toast.LENGTH_SHORT).show();
                } else if (expenseAmount.length() == 0) {
                    Toast.makeText(AddExpense.this, "Please enter an amount!", Toast.LENGTH_SHORT).show();
                } else if (dateText.length() == 0) {
                    Toast.makeText(AddExpense.this, "Please enter a valid date!", Toast.LENGTH_SHORT).show();
                } else {
                    //create object from user input

                    Expense newExpense = null;
                    if (imageView.getTag() != null) {
                        newExpense = new Expense(String.valueOf(expense.getText()), categorySpinner.getSelectedItem().toString(),
                                Double.parseDouble(expenseAmount.getText().toString()), dateText.getText().toString(), imageView.getTag().toString());
                        //Log.d("Demo", newExpense.toString());
                    } else {
                        newExpense = new Expense(String.valueOf(expense.getText()), categorySpinner.getSelectedItem().toString(),
                                Double.parseDouble(expenseAmount.getText().toString()), dateText.getText().toString());
                        //Log.d("Demo", newExpense.toString());
                    }
                    Intent addIntent = new Intent(AddExpense.this, MainActivity.class);
                    addIntent.putExtra(ADD, newExpense);
                    setResult(AddExpense.RESULT_OK, addIntent);
                    finish();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();

                    final ImageView imageView = (ImageView) findViewById(R.id.imageView_Add);
                    imageView.setImageURI(selectedImage);
                    imageView.setTag(selectedImage);

                }
        }
    }
}
