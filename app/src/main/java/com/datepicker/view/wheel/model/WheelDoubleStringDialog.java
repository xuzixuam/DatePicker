package com.datepicker.view.wheel.model;

import com.datepicker.ui.wheel.OnWheelChangedListener;
import com.datepicker.ui.wheel.WheelView;
import com.datepicker.ui.wheel.adapter.ArrayWheelAdapter;
import com.example.datepicker.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class WheelDoubleStringDialog extends Dialog {

	private Context context;
	
	private WheelView firstWheel,secondWheel;
	private ArrayWheelAdapter<String> firstAdapter;
	private ArrayWheelAdapter<String> secondAdatper;
	private OnDoubleSelectedListner selectedListner;
	
	private String[] firstStrings,selectedSecondStrings;//数据集，String[]类型
	private String[][] secondStrings;
	private int firstSelection = 0;//选中位置
	private int secondSelection = 0;//选中位置
	private int visibleItems = 5;//显示条数
	private int defTxtSize = 18;//默认字号
	private int sltTxtSize = 18;//选中字号
	private int defTxtColor = R.color.text_color_hint;//默认文字颜色
	private int sltTxtColor = R.color.txt_black;//选中文字颜色
	private int background = R.drawable.wheel_bg_def;//控件背景
	private int foreground = R.drawable.wheel_val_def;//选中项背景
	private boolean isLinkage = false;
	
	public WheelDoubleStringDialog(Context mContext) {
		super(mContext, R.style.WheelDialog);
		this.context = mContext;
	}
	
	public WheelDoubleStringDialog(Context mContext, int theme) {
		super(mContext, theme);
		this.context = mContext;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_wheel_double_string_dialog);
		
		findViewById(R.id.confirm).setOnClickListener(clickListener);
		findViewById(R.id.cancel).setOnClickListener(clickListener);
		
		firstAdapter = new ArrayWheelAdapter<String>(context, firstStrings);
		firstAdapter.setTextSize(defTxtSize);
		firstAdapter.setCurTextSize(sltTxtSize);
		firstAdapter.setTextColor(context.getResources().getColor(defTxtColor));
		firstAdapter.setCurTextColor(context.getResources().getColor(sltTxtColor));
		
		secondAdatper = new ArrayWheelAdapter<String>(context, selectedSecondStrings);
		secondAdatper.setTextSize(defTxtSize);
		secondAdatper.setCurTextSize(sltTxtSize);
		secondAdatper.setTextColor(context.getResources().getColor(defTxtColor));
		secondAdatper.setCurTextColor(context.getResources().getColor(sltTxtColor));
		
		firstWheel = (WheelView) findViewById(R.id.wv_firstwheelview);
		firstWheel.setWheelBackground(background);
		firstWheel.setWheelForeground(foreground);
		firstWheel.addChangingListener(firstWheelChangedListener);
		firstWheel.setVisibleItems(visibleItems);
		firstWheel.setViewAdapter(firstAdapter);
		firstWheel.setCurrentItem(firstSelection);
		
		secondWheel = (WheelView) findViewById(R.id.wv_secondwheelview);
		secondWheel.setWheelBackground(background);
		secondWheel.setWheelForeground(foreground);
		secondWheel.addChangingListener(secondWheelChangedListener);
		secondWheel.setVisibleItems(visibleItems);
		secondWheel.setViewAdapter(secondAdatper);
		secondWheel.setCurrentItem(secondSelection);
		
		
		
	}
	
	private View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.confirm:
				if(null != selectedListner){
					selectedListner.onSelect(firstWheel.getCurrentItem(),secondWheel.getCurrentItem());
				}
				dismiss();
				break;
			case R.id.cancel:
				dismiss();
				break;
			}
		}
	};
	
	private OnWheelChangedListener firstWheelChangedListener = new OnWheelChangedListener() {
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			firstAdapter.setCurIndex(newValue);
			if(isLinkage){
				secondAdatper.setCurIndex(0);
				if(secondWheel != null){
					selectedSecondStrings = secondStrings[wheel.getCurrentItem()];
					secondAdatper = new ArrayWheelAdapter<String>(context, selectedSecondStrings);
					secondAdatper.setTextSize(defTxtSize);
					secondAdatper.setCurTextSize(sltTxtSize);
					secondAdatper.setTextColor(context.getResources().getColor(defTxtColor));
					secondAdatper.setCurTextColor(context.getResources().getColor(sltTxtColor));
					secondWheel.setViewAdapter(secondAdatper);
					secondWheel.setCurrentItem(0);
				}
			}
		}
	};
	
	private OnWheelChangedListener secondWheelChangedListener = new OnWheelChangedListener() {
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			secondAdatper.setCurIndex(newValue);
		}
	};
	
	public interface OnDoubleSelectedListner {
		public void onSelect(int firstPosition,int secondPosition);
	}
	
	public void setSelectedListner(OnDoubleSelectedListner selectedListner) {
		this.selectedListner = selectedListner;
	}

	public void setData(String[] mFirstStrings,String[] selectedSecondStrings,String[][] mSecondStrings){
		this.firstStrings = mFirstStrings;
		this.selectedSecondStrings = selectedSecondStrings;
		this.secondStrings = mSecondStrings;
		if(isLinkage){
			convertData();
		}
	}

	public void setFirstSelection(int firstSelection) {
		this.firstSelection = firstSelection;
	}
	
	public void setSecondSelection(int secondSelection) {
		this.secondSelection = secondSelection;
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
	
	public void setLinkage(boolean isLinkage) {
		this.isLinkage = isLinkage;
	}
	
	private void convertData(){
		if(secondStrings != null && secondStrings[0].length >=firstSelection-1){
			this.selectedSecondStrings = secondStrings[firstSelection];
		}
	}
}
