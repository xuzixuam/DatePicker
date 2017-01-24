package com.datepicker.view.wheel.model;


import com.datepicker.ui.wheel.OnWheelChangedListener;
import com.datepicker.ui.wheel.WheelView;
import com.datepicker.ui.wheel.adapter.ArrayWheelAdapter;
import com.example.datepicker.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;


public class WheelSingleStringDialog extends Dialog {

	private Context context;
	
	private WheelView wheel;
	private ArrayWheelAdapter<String> adapter;
	private OnSelectedListner selectedListner;
	
	private String[] strings;//数据集，String[]类型 
	private int selection = 0;//选中位置
	private int visibleItems = 5;//显示条数
	private int defTxtSize = 18;//默认字号
	private int sltTxtSize = 18;//选中字号
	private int defTxtColor = R.color.text_color_hint;//默认文字颜色
	private int sltTxtColor = R.color.txt_black;//选中文字颜色
	private int background = R.drawable.wheel_bg_def;//控件背景
	private int foreground = R.drawable.wheel_val_def;//选中项背�?
	
	
	public WheelSingleStringDialog(Context mContext) {
		super(mContext, R.style.WheelDialog);
		this.context = mContext;
	}
	
	public WheelSingleStringDialog(Context mContext, int theme) {
		super(mContext, theme);
		this.context = mContext;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_wheel_single_string_dialog);
		
		findViewById(R.id.confirm).setOnClickListener(clickListener);
		findViewById(R.id.cancel).setOnClickListener(clickListener);
		
		adapter = new ArrayWheelAdapter<String>(context, strings);
		adapter.setTextSize(defTxtSize);
		adapter.setCurTextSize(sltTxtSize);
		adapter.setTextColor(context.getResources().getColor(defTxtColor));
		adapter.setCurTextColor(context.getResources().getColor(sltTxtColor));
		
		wheel = (WheelView) findViewById(R.id.wheel);
		wheel.setWheelBackground(background);
		wheel.setWheelForeground(foreground);
		wheel.addChangingListener(changedListener);
		wheel.setVisibleItems(visibleItems);
		wheel.setViewAdapter(adapter);
		wheel.setCurrentItem(selection);
		
	}
	
	private View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.confirm:
				if(null != selectedListner){
					selectedListner.onSelect(wheel.getCurrentItem());
				}
				dismiss();
				break;
			case R.id.cancel:
				dismiss();
				break;
			}
		}
	};
	
	private OnWheelChangedListener changedListener = new OnWheelChangedListener() {
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			adapter.setCurIndex(newValue);
		}
	};
	
	public interface OnSelectedListner {
		public void onSelect(int index);
	}
	
	public void setSelectedListner(OnSelectedListner selectedListner) {
		this.selectedListner = selectedListner;
	}

	public void setData(String[] mStrings){
		this.strings = mStrings;
	}

	public void setSelection(int selection) {
		this.selection = selection;
	}

	public void setVisibleItems(int visibleItems) {
		this.visibleItems = visibleItems;
	}

	public void setDefTxtSize(int defTxtSize) {
		this.defTxtSize = defTxtSize;
	}

	public void setSltTxtSize(int sltTxtSize) {
		this.sltTxtSize = sltTxtSize;
	}

	public void setDefTxtColor(int defTxtColor) {
		this.defTxtColor = defTxtColor;
	}

	public void setSltTxtColor(int sltTxtColor) {
		this.sltTxtColor = sltTxtColor;
	}
	
}
