package com.example.a2048;

import android.content.Context;
import android.widget.LinearLayout;

public class GrilleLinearLayout extends LinearLayout {
	public GrilleLinearLayout(Context context) {
		super(context);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		int squareLen = Math.min(width, height);
		
		super.onMeasure(
				MeasureSpec.makeMeasureSpec(squareLen, MeasureSpec.EXACTLY),
				MeasureSpec.makeMeasureSpec(squareLen, MeasureSpec.EXACTLY));
	}
}
