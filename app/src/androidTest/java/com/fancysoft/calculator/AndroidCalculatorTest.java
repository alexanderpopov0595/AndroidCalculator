package com.fancysoft.calculator;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import android.content.pm.ActivityInfo;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AndroidCalculatorTest {

    @Rule
    public ActivityTestRule<MainActivity> activityScenarioRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init() {
        activityScenarioRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Test
    public void shouldClearScreenWhenExpressionIsEntered() {
        onView(withText("1")).perform(click());
        onView(withText("2")).perform(click());
        onView(withText("3")).perform(click());
        onView(withId(R.id.screen)).check(matches(withText("123")));

        onView(withText("C")).perform(click());

        onView(withId(R.id.screen)).check(matches(withText("0")));
    }

    @Test
    public void shouldClearScreenWhenNoExpressionIsEntered() {
        onView(withText("C")).perform(click());

        onView(withId(R.id.screen)).check(matches(withText("0")));
    }

    @Test
    public void shouldBackspaceWhenExpressionIsEntered() {
        onView(withText("1")).perform(click());
        onView(withText("2")).perform(click());
        onView(withText("3")).perform(click());
        onView(withId(R.id.screen)).check(matches(withText("123")));

        onView(withText("⌫")).perform(click());

        onView(withId(R.id.screen)).check(matches(withText("12")));
    }

    @Test
    public void shouldBackspaceWhenNoExpressionIsEntered() {
        onView(withText("⌫")).perform(click());

        onView(withId(R.id.screen)).check(matches(withText("0")));
    }

    @Test
    public void shouldSaveExpressionWhenOrientationChanged() {
        onView(withText("1")).perform(click());
        onView(withText("+")).perform(click());
        onView(withText("2")).perform(click());

        activityScenarioRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        onView(withId(R.id.screen)).check(matches(withText("1+2")));
    }

    @Test
    public void shouldCalculateSimpleArithmeticOperation() {
        onView(withText("2")).perform(click());
        onView(withText("+")).perform(click());
        onView(withText("4")).perform(click());

        onView(withId(R.id.screen)).check(matches(withText("2+4")));

        onView(withText("=")).perform(click());

        onView(withId(R.id.screen)).check(matches(withText("6")));
    }

    @Test
    public void shouldCalculateComplexArithmeticOperation() {
        onView(withText("2")).perform(click());
        onView(withText("+")).perform(click());
        onView(withText("4")).perform(click());
        onView(withText("-")).perform(click());
        onView(withText("3")).perform(click());

        onView(withId(R.id.screen)).check(matches(withText("2+4-3")));

        onView(withText("=")).perform(click());

        onView(withId(R.id.screen)).check(matches(withText("3")));
    }

    @Test
    public void shouldCalculateSimpleAlgebraicOperation() {
        onView(withText("2")).perform(click());
        onView(withText("x")).perform(click());
        onView(withText("4")).perform(click());

        onView(withId(R.id.screen)).check(matches(withText("2x4")));

        onView(withText("=")).perform(click());

        onView(withId(R.id.screen)).check(matches(withText("8")));
    }

    @Test
    public void shouldCalculateComplexAlgebraicOperation() {
        onView(withText("2")).perform(click());
        onView(withText("x")).perform(click());
        onView(withText("4")).perform(click());
        onView(withText("÷")).perform(click());
        onView(withText("8")).perform(click());

        onView(withId(R.id.screen)).check(matches(withText("2x4÷8")));

        onView(withText("=")).perform(click());

        onView(withId(R.id.screen)).check(matches(withText("1")));
    }

    @Test
    public void shouldCalculateMixedArithmeticAndAlgebraicOperation() {
        onView(withText("2")).perform(click());
        onView(withText("+")).perform(click());
        onView(withText("4")).perform(click());
        onView(withText("x")).perform(click());
        onView(withText("3")).perform(click());

        onView(withId(R.id.screen)).check(matches(withText("2+4x3")));

        onView(withText("=")).perform(click());

        onView(withId(R.id.screen)).check(matches(withText("14")));
    }

    @Test
    public void shouldCalculateSimpleBracketExpression() {
        activityScenarioRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        onView(withText("2")).perform(click());
        onView(withText("+")).perform(click());
        onView(withText("4")).perform(click());
        onView(withText("x")).perform(click());
        onView(withText("(")).perform(click());
        onView(withText("4")).perform(click());
        onView(withText("-")).perform(click());
        onView(withText("1")).perform(click());
        onView(withText(")")).perform(click());

        onView(withId(R.id.screen)).check(matches(withText("2+4x(4-1)")));

        onView(withText("=")).perform(click());

        onView(withId(R.id.screen)).check(matches(withText("14")));
    }

    @Test
    public void shouldCalculateExponentExpression() {
        activityScenarioRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        onView(withText("2")).perform(click());
        onView(withText("x2")).perform(click());

        onView(withId(R.id.screen)).check(matches(withText("2^2")));

        onView(withText("=")).perform(click());

        onView(withId(R.id.screen)).check(matches(withText("4")));
    }

    @Test
    public void shouldCalculateExpressionWithAllOperations() {
        activityScenarioRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        onView(withText("5")).perform(click());
        onView(withText("C")).perform(click());
        onView(withId(R.id.screen)).check(matches(withText("0")));

        onView(withText("2")).perform(click());
        onView(withText("3")).perform(click());
        onView(withText("⌫")).perform(click());
        onView(withText(".")).perform(click());
        onView(withText("5")).perform(click());
        onView(withId(R.id.screen)).check(matches(withText("2.5")));

        onView(withText("x")).perform(click());
        onView(withText("2")).perform(click());

        onView(withText("+")).perform(click());

        onView(withText("sin")).perform(click());
        onView(withText("(")).perform(click());

        onView(withText("3")).perform(click());
        onView(withText("6")).perform(click());
        onView(withText("0")).perform(click());

        onView(withText("÷")).perform(click());

        onView(withText("2")).perform(click());

        onView(withText("-")).perform(click());

        onView(withText("9")).perform(click());
        onView(withText("0")).perform(click());

        onView(withText(")")).perform(click());

        onView(withText("+")).perform(click());

        onView(withText("5")).perform(click());
        onView(withText("0")).perform(click());
        onView(withText("0")).perform(click());

        onView(withText("%")).perform(click());

        onView(withText("+")).perform(click());

        onView(withText("3")).perform(click());
        onView(withText("x√y")).perform(click());

        onView(withText("(")).perform(click());
        onView(withText("2")).perform(click());
        onView(withText("xy")).perform(click());
        onView(withText("3")).perform(click());
        onView(withText(")")).perform(click());

        onView(withId(R.id.screen)).check(matches(withText("2.5x2+sin(360÷2-90)+500%+3√(2^3)")));

        onView(withText("=")).perform(click());

        onView(withId(R.id.screen)).check(matches(withText("13")));
    }

    @Test
    public void shouldDisplayToastWithInvalidExpressionException() {
        onView(withText("2")).perform(click());
        onView(withText("x")).perform(click());

        onView(withText("=")).perform(click());

        onView(withText("Invalid expression")).
                inRoot(withDecorView(not(is(activityScenarioRule.getActivity().getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @Test
    public void shouldDisplayToastWithZeroDivisionException() {
        onView(withText("2")).perform(click());
        onView(withText("÷")).perform(click());
        onView(withText("0")).perform(click());

        onView(withText("=")).perform(click());

        onView(withText("Zero division")).
                inRoot(withDecorView(not(is(activityScenarioRule.getActivity().getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }
}