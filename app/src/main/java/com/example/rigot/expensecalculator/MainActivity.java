package com.example.rigot.expensecalculator;

/*
    Name: Rodrigo Trejo
    Name: Anal Shah
    HW2
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Expense> expenses = new ArrayList<>();
    public static String ADD = "Add";
    public static String EDIT = "Edit";
    public static String DELETE = "Delete";
    public static String SHOW = "Show";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //begin add activity for result
        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, AddExpense.class);
                startActivityForResult(addIntent, 1);
            }
        });
        //begin edit activity for result
        findViewById(R.id.editButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(MainActivity.this, EditActivity.class);
                editIntent.putParcelableArrayListExtra(EDIT, expenses);
                startActivityForResult(editIntent, 2);
            }
        });
        //begin delete activity for result
        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deleteIntent = new Intent(MainActivity.this, DeleteActivity.class);
                deleteIntent.putParcelableArrayListExtra(DELETE, expenses);
                startActivityForResult(deleteIntent, 3);
            }
        });
        //begin show activity
        findViewById(R.id.showButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showIntent = new Intent(MainActivity.this, ShowExpense.class);
                showIntent.putParcelableArrayListExtra(SHOW, expenses);
                startActivity(showIntent);
            }
        });

        findViewById(R.id.finishButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    //here we handle the results sent from other activities
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int j;
        if(requestCode==1){ //if the result comes from AddExpense activity
            if(resultCode == AddExpense.RESULT_OK){ //make sure everything went OK
                Expense expense = data.getExtras().getParcelable(ADD);
                //add expense in alphabetical order here
                if(expenses.size()>0) {
                    j=0;
                    while(j<=expenses.size()){
                        if (expense.name.compareTo(expenses.get(j).name) < 0) {
                            expenses.add(j, expense);
                            break;
                        }else if(j+1 == expenses.size()){
                            expenses.add(expense);
                            break;}

                        j++;
                    }
                }else
                    expenses.add(expense);
            }
        }else if(requestCode == 2){
            if(resultCode==EditActivity.RESULT_OK){
                int pos = data.getExtras().getInt("EDIT_POSITION");
                expenses.remove(pos);
                Expense tempExpense = data.getExtras().getParcelable(EDIT);
                //add expense in alphabetical order here
                if(expenses.size()>0) {
                    j=0;
                    while(j<=expenses.size()){
                        if (tempExpense.name.compareTo(expenses.get(j).name) < 0) {
                            expenses.add(j, tempExpense);
                            break;
                        }else if(j+1 == expenses.size()){
                            expenses.add(tempExpense);
                            break;}

                        j++;
                    }
                }else
                    expenses.add(tempExpense);
            }
        }else if(requestCode == 3){
            if(resultCode==EditActivity.RESULT_OK){
                int pos = data.getExtras().getInt("DELETE_POSITION");
                expenses.remove(pos);
            }
        }
    }

}
