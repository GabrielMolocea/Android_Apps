package com.gabriel.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    // Variable to hod the operations and type of calculations
    private Double operation1 = null;
    private String pendingOperation = "=";

    private static final String SATE_PENDING_OPERATION = "PendingOperation";
    private static final String SATE_OPERAND1 = "Operand1";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        newNumber =  findViewById(R.id.newNumber);
        displayOperation = findViewById(R.id.operation);

        // Number buttons
        Button button0 =  findViewById(R.id.button0);
        Button button1 =  findViewById(R.id.button1);
        Button button2 =  findViewById(R.id.button2);
        Button button3 =  findViewById(R.id.button3);
        Button button4 =  findViewById(R.id.button4);
        Button button5 =  findViewById(R.id.button5);
        Button button6 =  findViewById(R.id.button6);
        Button button7 =  findViewById(R.id.button7);
        Button button8 =  findViewById(R.id.button8);
        Button button9 =  findViewById(R.id.button9);
        Button buttonDot =  findViewById(R.id.buttonDot);

        // Sign buttons
        Button buttonEquals =  findViewById(R.id.buttonEquals);
        Button buttonPlus =  findViewById(R.id.buttonPlus);
        Button buttonMinus =  findViewById(R.id.buttonMinus);
        Button buttonDivide =  findViewById(R.id.buttonDivide);
        Button buttonMultiply =  findViewById(R.id.buttonMultiplay);

        View.OnClickListener listener = view -> {
            Button button = (Button) view;
            newNumber.append(button.getText().toString());
        };

        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);

        View.OnClickListener operationListener = view -> {
            Button b = (Button) view;
            String op = b.getText().toString();
            String value = newNumber.getText().toString();
           try {
               Double doubleValue = Double.valueOf(value);
               performOperation(doubleValue, op);
           } catch (NumberFormatException e) {
                newNumber.setText("");
           }
            pendingOperation = op;
            displayOperation.setText(pendingOperation);
        };

        buttonEquals.setOnClickListener(operationListener);
        buttonDivide.setOnClickListener(operationListener);
        buttonMinus.setOnClickListener(operationListener);
        buttonMultiply.setOnClickListener(operationListener);
        buttonPlus.setOnClickListener(operationListener);

        Button buttonNeg = findViewById(R.id.buttonNeg);

        buttonNeg.setOnClickListener(v -> {
            String value = newNumber.getText().toString();
            if (value.length() == 0) {
                newNumber.setText("-");
            } else {
                try {
                    Double doubleValue = Double.valueOf(value);
                    doubleValue += -1;
                    newNumber.setText(doubleValue.toString());
                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(SATE_PENDING_OPERATION, pendingOperation);
        if (operation1 != null) {
            outState.putDouble(SATE_OPERAND1, operation1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(SATE_PENDING_OPERATION);
        operation1 = savedInstanceState.getDouble(SATE_OPERAND1);
        displayOperation.setText(pendingOperation);
    }

    private void performOperation(Double value, String operation) {
            if (operation1 == null) {
                operation1 = value;
            } else {
                if (pendingOperation.equals("=")) {
                    pendingOperation = operation;
                }
                switch (pendingOperation) {
                    case "=":
                        operation1 = value;

                        break;
                    case "/":
                        if (value == 0) {
                            operation1 = 0.0;
                        } else {
                            operation1 /= value;
                        }
                        break;
                    case "*":
                        operation1 *= value;
                        break;
                    case "-":
                        operation1 -= value;
                        break;
                    case "+":
                        operation1 += value;
                        break;
                }
            }
            result.setText(operation1.toString());
            newNumber.setText("");
        }
    }