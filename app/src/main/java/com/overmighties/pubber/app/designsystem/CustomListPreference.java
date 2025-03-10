package com.overmighties.pubber.app.designsystem;

import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.ListPreference;

import com.overmighties.pubber.R;

public class CustomListPreference extends ListPreference
{
    public CustomListPreference (Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(R.layout.preference_text_layout);
    }

    @Override
    protected void onClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.CustomRadioButtonDialog)
                .setSingleChoiceItems(getEntries(), getValueIndex(), (dialog, index) -> {
                    if (callChangeListener(getEntryValues()[index].toString())) {
                        setValueIndex(index);
                    }
                    dialog.dismiss();
                })
                .setNegativeButton(getNegativeButtonText(), (dialog, which) -> dialog.dismiss())
                .setTitle(getTitle());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private int getValueIndex() {
        CharSequence[] entryValues = getEntryValues();
        String value = getValue();
        for (int i = 0; i < entryValues.length; i++) {
            if (entryValues[i].toString().equals(value)) {
                return i;
            }
        }
        return -1;
    }

}