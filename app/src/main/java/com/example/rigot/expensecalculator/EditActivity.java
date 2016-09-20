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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity {

    private static String EDIT = "Edit";
    private int temp = 0;
    private static final int SELECT_PHOTO = 100;
    static boolean isImageChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle("Edit Expense"); //set title

        findViewById(R.id.save).setEnabled(false);
        final EditText expenseName = (EditText) findViewById(R.id.expenseName);
        final EditText expenseAmount = (EditText) findViewById(R.id.expenseAmount);
        final TextView dateText = (TextView) findViewById(R.id.dateText);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView_Edit);
        final Calendar cal = Calendar.getInstance();    //create calendar instance with current date
        final Spinner categorySpinner = (Spinner) findViewById(R.id.categoryDropDown);
        //set arrayadapter for choices
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array,
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
                new DatePickerDialog(EditActivity.this, date, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //array list of expense objects
        ArrayList<Expense> expenses = new ArrayList<>();

        if (getIntent().getExtras() != null) {
            expenses = getIntent().getParcelableArrayListExtra(EDIT);  //receive array list of expense
        }                                                               //objects from MainActivity
        //create CharSequence array and store expense names
        int size = expenses.size();
        final CharSequence[] items = new CharSequence[size];
        for (int j = 0; j < expenses.size(); j++) {
            items[j] = expenses.get(j).name;
        }

        final ArrayList<Expense> finalExpenses = expenses;

        //create AlertDialog object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //display values of expense to be edited
        builder.setTitle("Pick an Expense").setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int breaker = -1;
                while (breaker != finalExpenses.size()) {
                    breaker++;
                    if (items[i].toString().equals(finalExpenses.get(breaker).name)) {
                        expenseName.setText(finalExpenses.get(breaker).name);
                        expenseAmount.setText(String.valueOf(finalExpenses.get(breaker).amount));
                        categorySpinner.setSelection(adapter.getPosition(finalExpenses.get(breaker).category));
                        dateText.setText(finalExpenses.get(breaker).date);
                        if (finalExpenses.get(breaker).imageUri != null) {
                            imageView.setImageURI(null);
                            imageView.setImageURI(Uri.parse(finalExpenses.get(breaker).imageUri));
                        }
                        else
                            imageView.setImageResource(R.mipmap.browse_icon);
                        temp = breaker;
                        breaker = finalExpenses.size();
                        findViewById(R.id.save).setEnabled(true);
                        isImageChanged = false;
                    }
                }
            }
        });

        final AlertDialog alert = builder.create();

        //show alert dialog
        findViewById(R.id.selectExpenseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.show();
            }
        });

        //handle save button
        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expenseName.length() == 0)
                    Toast.makeText(EditActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                else if (expenseAmount.length() == 0)
                    Toast.makeText(EditActivity.this, "Please enter an amount", Toast.LENGTH_SHORT).show();
                else if (dateText.length() == 0)
                    Toast.makeText(EditActivity.this, "Please enter a date", Toast.LENGTH_SHORT).show();
                else {
                    Expense expenseToSend = null;
                    if (isImageChanged) {
                        expenseToSend = new Expense(expenseName.getText().toString(), categorySpinner.getSelectedItem().toString(),
                                Double.parseDouble(expenseAmount.getText().toString()), dateText.getText().toString(), imageView.getTag().toString());
                    } else {
                        expenseToSend = new Expense(expenseName.getText().toString(), categorySpinner.getSelectedItem().toString(),
                                Double.parseDouble(expenseAmount.getText().toString()), dateText.getText().toString());
                    }

                    Intent saveIntent = new Intent();
                    Bundle dataBundle = new Bundle();
                    dataBundle.putInt("EDIT_POSITION", temp);
                    dataBundle.putParcelable(MainActivity.EDIT, expenseToSend);
                    saveIntent.putExtras(dataBundle);
                    setResult(RESULT_OK, saveIntent);
                    finish();
                }
            }
        });

        findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

                    final ImageView imageView = (ImageView) findViewById(R.id.imageView_Edit);
                    imageView.setImageURI(selectedImage);
                    imageView.setTag(selectedImage);
                    isImageChanged = true;
                }
        }
    }
}
