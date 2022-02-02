package com.fancysoft.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fancysoft.calculator.exception.handler.ExceptionHandler;
import com.fancysoft.calculator.model.Calculator;
import com.fancysoft.calculator.model.Screen;
import com.fancysoft.calculator.model.ScreenState;
import com.fancysoft.calculator.service.CalculatorService;
import com.fancysoft.calculator.service.CommandTransformer;
import com.fancysoft.calculator.service.OperationService;
import com.fancysoft.calculator.service.RPNService;
import com.fancysoft.calculator.service.impl.CalculatorServiceImpl;
import com.fancysoft.calculator.service.impl.CommandTransformerImpl;
import com.fancysoft.calculator.service.impl.OperationServiceImpl;
import com.fancysoft.calculator.service.impl.RPNServiceImpl;
import com.fancysoft.calculator.utils.Constants;

import lombok.Getter;

/**
 * Main app's controller
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Calculator app's backend
     */
    @Getter
    private Calculator calculator;
    /**
     * Handles app's exceptions
     */
    private ExceptionHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        run();
    }

    /**
     * Saves calculator's screen state
     *
     * @param outState - transfer object
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constants.SCREEN_STATE, calculator.getScreen().getScreenState());
    }

    /**
     * Restores calc state
     *
     * @param savedInstanceState - transfer object with saved data
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        ScreenState state = (ScreenState) savedInstanceState.getSerializable(Constants.SCREEN_STATE);
        calculator.getScreen().setScreenState(state);
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * Inits all components
     */
    private void run() {
        Screen screen = new Screen(findViewById(R.id.screen));

        CommandTransformer transformer = new CommandTransformerImpl();

        OperationService opService = new OperationServiceImpl();
        CalculatorService calcService = new CalculatorServiceImpl();
        RPNService service = new RPNServiceImpl(opService, calcService);

        calculator = new Calculator(screen, transformer, service);
        handler = new ExceptionHandler(this);
    }

    /**
     * Calls calculator's clear method
     *
     * @param view - pressed button
     */
    public void clear(View view) {
        calculator.clear();
    }

    /**
     * Calls calculator's backspace method
     *
     * @param view - pressed button
     */
    public void backspace(View view) {
        calculator.backspace();
    }

    /**
     * Takes pressed button's name and passes it's value to calculator's display method
     *
     * @param view - pressed button
     */
    public void input(View view) {
        TextView txt = (TextView) view;
        String command = txt.getText().toString();
        calculator.display(command);
    }

    /**
     * Calls calculator's execute method
     * If some exception acquires, passes exception to handler's method
     *
     * @param view - pressed button
     */
    public void execute(View view) {
        try {
            calculator.execute();
        } catch (Throwable e) {
            handler.handleException(e);
        }
    }
}