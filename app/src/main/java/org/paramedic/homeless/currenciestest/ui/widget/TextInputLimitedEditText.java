package org.paramedic.homeless.currenciestest.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;

import org.paramedic.homeless.currenciestest.R;

public class TextInputLimitedEditText extends TextInputEditText {
    protected int maxInputLength = -1;

    public TextInputLimitedEditText(Context context) {
        super(context);
    }

    public TextInputLimitedEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.editTextStyle);
    }

    public TextInputLimitedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextInputLimitedEditText, defStyleAttr, 0);

        maxInputLength = a.getInteger(R.styleable.TextInputLimitedEditText_rlc_max_input_length, -1);

        a.recycle();
    }

    // checks whether the limits are set and corrects them if not within limits
    @Override
    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        if (maxInputLength != -1 && isFocused()) {
            try {
                if (this.getText().toString().length() > maxInputLength) {
                    // change value and keep cursor position
                    int selection = this.getSelectionStart();
                    this.setText(this.getText().toString().substring(0,maxInputLength) );
                    if (selection >= this.getText().toString().length()) {
                        selection = this.getText().toString().length();
                    }
                    this.setSelection(selection);
                }
            } catch (NumberFormatException exception) {
                super.onTextChanged(text, start, before, after);
            }
        }
        super.onTextChanged(text, start, before, after);
    }

    // set the maximum integer value the user can enter.
    // if exeeded, input value will become equal to the set limit
    public void setMaxInputLengthValue(int value) {
        maxInputLength = value;
    }
}