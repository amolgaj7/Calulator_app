package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView displayText;
    private TextView expressionText;

    private double firstOperand = 0;
    private String pendingOperator = null;
    private boolean isNewInput = true;
    private boolean lastActionWasEquals = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayText = findViewById(R.id.displayText);
        expressionText = findViewById(R.id.expressionText);

        setupNumberButtons();
        setupOperatorButtons();

        findViewById(R.id.btnClear).setOnClickListener(v -> clear());
        findViewById(R.id.btnDelete).setOnClickListener(v -> delete());
        findViewById(R.id.btnEquals).setOnClickListener(v -> calculateResult());
        findViewById(R.id.btnDecimal).setOnClickListener(v -> appendDecimal());
        findViewById(R.id.btnPlusMinus).setOnClickListener(v -> toggleSign());
        findViewById(R.id.btnPercent).setOnClickListener(v -> applyPercent());
    }

    private void setupNumberButtons() {
        int[] ids = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };
        for (int id : ids) {
            Button b = findViewById(id);
            b.setOnClickListener(v -> appendNumber(((Button) v).getText().toString()));
        }
    }

    private void setupOperatorButtons() {
        findViewById(R.id.btnAdd).setOnClickListener(v -> setOperator("+"));
        findViewById(R.id.btnSubtract).setOnClickListener(v -> setOperator("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> setOperator("×"));
        findViewById(R.id.btnDivide).setOnClickListener(v -> setOperator("÷"));
    }

    private void appendNumber(String number) {
        if (isNewInput || lastActionWasEquals) {
            displayText.setText(number);
            isNewInput = false;
            lastActionWasEquals = false;
        } else {
            if (displayText.getText().toString().equals("0")) {
                displayText.setText(number);
            } else {
                displayText.append(number);
            }
        }
    }

    private void appendDecimal() {
        if (isNewInput || lastActionWasEquals) {
            displayText.setText("0.");
            isNewInput = false;
            lastActionWasEquals = false;
            return;
        }
        String current = displayText.getText().toString();
        if (!current.contains(".")) {
            displayText.append(".");
        }
    }

    private void setOperator(String operator) {
        if (pendingOperator != null && !isNewInput) {
            calculateResult();
        }
        firstOperand = parseCurrentValue();
        pendingOperator = operator;
        expressionText.setText(trimNumber(firstOperand) + " " + operator);
        isNewInput = true;
        lastActionWasEquals = false;
    }

    private void calculateResult() {
        if (pendingOperator == null) {
            return;
        }
        double secondOperand = parseCurrentValue();
        double result;
        switch (pendingOperator) {
            case "+":
                result = firstOperand + secondOperand;
                break;
            case "-":
                result = firstOperand - secondOperand;
                break;
            case "×":
                result = firstOperand * secondOperand;
                break;
            case "÷":
                if (secondOperand == 0) {
                    displayText.setText("Error");
                    expressionText.setText("");
                    pendingOperator = null;
                    isNewInput = true;
                    return;
                }
                result = firstOperand / secondOperand;
                break;
            default:
                return;
        }
        expressionText.setText(trimNumber(firstOperand) + " " + pendingOperator + " " + trimNumber(secondOperand) + " =");
        displayText.setText(trimNumber(result));
        firstOperand = result;
        pendingOperator = null;
        isNewInput = true;
        lastActionWasEquals = true;
    }

    private void clear() {
        displayText.setText("0");
        expressionText.setText("");
        firstOperand = 0;
        pendingOperator = null;
        isNewInput = true;
        lastActionWasEquals = false;
    }

    private void delete() {
        String current = displayText.getText().toString();
        if (current.length() > 1) {
            displayText.setText(current.substring(0, current.length() - 1));
        } else {
            displayText.setText("0");
            isNewInput = true;
        }
    }

    private void toggleSign() {
        double value = parseCurrentValue();
        value = -value;
        displayText.setText(trimNumber(value));
    }

    private void applyPercent() {
        double value = parseCurrentValue();
        value = value / 100.0;
        displayText.setText(trimNumber(value));
    }

    private double parseCurrentValue() {
        try {
            return Double.parseDouble(displayText.getText().toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String trimNumber(double value) {
        if (value == Math.floor(value) && !Double.isInfinite(value)) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }
}
