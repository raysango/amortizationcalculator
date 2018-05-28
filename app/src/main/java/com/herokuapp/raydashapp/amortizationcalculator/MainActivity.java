package com.herokuapp.raydashapp.amortizationcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void calculateMonthly(View view) {
        String error = getString(R.string.error);
        EditText loanAmountView = findViewById(R.id.loan_amount);
        EditText interestRateView = findViewById(R.id.rate);
        EditText termView = findViewById(R.id.term);
        TextView errorMessageView= findViewById(R.id.error_message);
        if (loanAmountView.getText().toString().matches("") || interestRateView.getText().toString().matches("") || termView.getText().toString().matches("")) {
            errorMessageView.setText(error);
        } else {
            errorMessageView.setText("");
            int loanAmount = Integer.parseInt(loanAmountView.getText().toString());
            float interestRate = Float.parseFloat(interestRateView.getText().toString());
            int term = Integer.parseInt(termView.getText().toString()) * 12;
            if (loanAmount <= 0 || interestRate <= 0 || term <= 0) {
                errorMessageView.setText(error);
                return;
            }
            float monthlyRate = interestRate / 1200;
            double numerator = monthlyRate * Math.pow((1 + monthlyRate), term);
            double denominator = Math.pow((1 + monthlyRate), term) - 1;
            int monthlyPayment = (int) Math.round(loanAmount * (numerator / denominator));
            int totalInterest = (monthlyPayment * term) - loanAmount;
            int totalPaid = monthlyPayment * term;
            displayNumbers(monthlyPayment, totalInterest, totalPaid);
        }

    };

    public void displayNumbers(int monthlyPayment, int totalInterest, int totalPaid) {
        TextView monthlyPaymentView = findViewById(R.id.monthly_payment);
        monthlyPaymentView.setText(getString(R.string.monthly_payment, NumberFormat.getCurrencyInstance(Locale.US).format(monthlyPayment)));
        TextView totalInterestView = findViewById(R.id.total_interest);
        totalInterestView.setText(getString(R.string.total_interest, NumberFormat.getCurrencyInstance(Locale.US).format(totalInterest)));
        TextView totalPaidView = findViewById(R.id.total_paid);
        totalPaidView.setText(getString(R.string.total_paid, NumberFormat.getCurrencyInstance(Locale.US).format(totalPaid)));
    }
}
