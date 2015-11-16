package com.zongyou.library.text;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * @author Altas
 * @email Altas.Tutu@gmail.com
 * @time 2015-1-22 下午2:14:18
 */
public class TextFilter implements TextWatcher {
	public static final int PRICE = 1;
	private int mType;
	private EditText mEditText;

	public TextFilter(EditText editText, int formatType) {
		mEditText = editText;
		mType = formatType;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		try {
			if (PRICE == mType) {
				String[] temp = s.toString().split("\\.");
				if (temp.length == 2) {
					if (temp[1].length() > 2) {
						s = s.subSequence(0, s.length() - 1);
						setText(s, s);
					}
				}
			}
		} catch (NumberFormatException e) {
			setText("0.00", "0.00");
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

	private void setText(CharSequence src, CharSequence newStr) {
		mEditText.setText(newStr);
		mEditText.setSelection(src.length() > newStr.length() ? newStr.length() : src.length());
	}

}
