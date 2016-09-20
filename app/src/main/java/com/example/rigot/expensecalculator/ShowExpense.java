package com.example.rigot.expensecalculator;
/*
    Name: Rodrigo Trejo
    Name: Anal Shah
    HW2
 */
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ShowExpense extends AppCompatActivity {
    static int currentPos = 0;
    ArrayList<Expense> expenses = new ArrayList<>();
    private static String SHOW = "Show";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expense);

        final TextView name = (TextView) findViewById(R.id.name);
        final TextView category = (TextView) findViewById(R.id.category);
        final TextView amount = (TextView) findViewById(R.id.amount);
        final TextView date = (TextView) findViewById(R.id.date);
        final ImageView imageView = (ImageView) findViewById(R.id.receiptImage);
        int max = 0;

        if (getIntent() != null) {
            expenses = getIntent().getParcelableArrayListExtra(SHOW);
            if (expenses.size() != 0) {

                max = expenses.size();
                name.setText(expenses.get(0).name);
                category.setText(expenses.get(0).category);
                amount.setText(String.valueOf(expenses.get(0).amount));
                date.setText(expenses.get(0).date);
                if (expenses.get(0).imageUri != null)
                    imageView.setImageURI(Uri.parse(expenses.get(0).imageUri));
                else
                    imageView.setImageResource(0);
            }
        }

        findViewById(R.id.finishButt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final int finalMax = max;
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPos++;
                if (currentPos < finalMax) {
                    name.setText(expenses.get(currentPos).name);
                    category.setText(expenses.get(currentPos).category);
                    amount.setText(String.valueOf(expenses.get(currentPos).amount));
                    date.setText(expenses.get(currentPos).date);
                    if (expenses.get(currentPos).imageUri != null)
                        imageView.setImageURI(Uri.parse(expenses.get(currentPos).imageUri));
                    else
                        imageView.setImageResource(0);
                } else
                    currentPos--;
            }
        });

        findViewById(R.id.previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPos--;
                if (currentPos > -1) {
                    name.setText(expenses.get(currentPos).name);
                    category.setText(expenses.get(currentPos).category);
                    amount.setText(String.valueOf(expenses.get(currentPos).amount));
                    date.setText(expenses.get(currentPos).date);
                    if (expenses.get(currentPos).imageUri != null)
                        imageView.setImageURI(Uri.parse(expenses.get(currentPos).imageUri));
                    else
                        imageView.setImageResource(0);
                } else
                    currentPos++;
            }
        });

        findViewById(R.id.showfirst).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expenses.size() != 0) {
                    currentPos = 0;
                    name.setText(expenses.get(0).name);
                    category.setText(expenses.get(0).category);
                    amount.setText(String.valueOf(expenses.get(0).amount));
                    date.setText(expenses.get(0).date);
                    if (expenses.get(0).imageUri != null)
                        imageView.setImageURI(Uri.parse(expenses.get(0).imageUri));
                    else
                        imageView.setImageResource(0);
                }
            }
        });

        findViewById(R.id.showlast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expenses.size() != 0) {
                    currentPos = expenses.size() - 1;
                    name.setText(expenses.get(expenses.size() - 1).name);
                    category.setText(expenses.get(expenses.size() - 1).category);
                    amount.setText(String.valueOf(expenses.get(expenses.size() - 1).amount));
                    date.setText(expenses.get(expenses.size() - 1).date);
                    if (expenses.get(expenses.size() - 1).imageUri != null)
                        imageView.setImageURI(Uri.parse(expenses.get(expenses.size() - 1).imageUri));
                    else
                        imageView.setImageResource(0);
                }
            }
        });
    }

}
