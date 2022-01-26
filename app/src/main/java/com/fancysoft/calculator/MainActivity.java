package com.fancysoft.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fancysoft.calculator.model.Calculator;
import com.fancysoft.calculator.model.Screen;
import com.fancysoft.calculator.service.CalculatorService;
import com.fancysoft.calculator.service.OperationService;
import com.fancysoft.calculator.service.RPNService;
import com.fancysoft.calculator.service.impl.CalculatorServiceImpl;
import com.fancysoft.calculator.service.impl.OperationServiceImpl;
import com.fancysoft.calculator.service.impl.RPNServiceImpl;

public class MainActivity extends AppCompatActivity {

    private Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        run();
    }

    private void run() {
        Screen screen = new Screen(findViewById(R.id.screen));

        OperationService opService = new OperationServiceImpl();
        CalculatorService calcService = new CalculatorServiceImpl();
        RPNService service = new RPNServiceImpl(opService, calcService);

        calculator = new Calculator(screen, service);
        calculator.clear();
    }

    public void clear(View view) {
        calculator.clear();
    }

    public void backspace(View view) {
        calculator.backspace();
    }

    public void input(View view) {
        Button button = (Button) view;
        String command = button.getText().toString();
        calculator.display(command);
    }

    public void execute(View view) {
        calculator.execute();
    }
}