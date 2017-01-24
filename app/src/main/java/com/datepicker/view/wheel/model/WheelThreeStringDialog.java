package com.datepicker.view.wheel.model;

import java.util.ArrayList;

import com.datepicker.ui.wheel.OnWheelChangedListener;
import com.datepicker.ui.wheel.OnWheelScrollListener;
import com.datepicker.ui.wheel.WheelView;
import com.datepicker.ui.wheel.adapter.ArrayWheelAdapter;
import com.example.datepicker.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class WheelThreeStringDialog extends Dialog {
	private Context context;

	private WheelView firstWheel, secondWheel, threeWheel;
	private ArrayWheelAdapter<String> firstAdapter;
	private ArrayWheelAdapter<String> secondAdatper;
	private ArrayWheelAdapter<String> threeAdatper;
	private OnThreeSelectedListner selectedListner;

	private String[] firstStrings, threeStrings, secondStrings;// 数据集，String[]类型
	private String[] Temp;
	private String[][][] threeStringsArry;
	private String[][] selectedSecondStrings, selectedStrings;
	private int firstSelection = 0;// 选中位置
	private int secondSelection = 0;// 选中位置
	private int threeSelection = 0;// 选中位置
	private int visibleItems = 5;// 显示条数
	private int defTxtSize = 18;// 默认字号
	private int sltTxtSize = 18;// 选中字号
	private int defTxtColor = R.color.text_color_hint;// 默认文字颜色
	private int sltTxtColor = R.color.txt_black;// 选中文字颜色
	private int background = R.drawable.wheel_bg_def;// 控件背景
	private int foreground = R.drawable.wheel_val_def;// 选中项背�?
	private boolean isLinkage = false;
	private boolean isNowYear = false;
//	private boolean isThree = false;
	private int secondItem = 0;
	private int nowDay = 0;

	public WheelThreeStringDialog(Context mContext) {
		super(mContext, R.style.WheelDialog);
		this.context = mContext;
	}

	public WheelThreeStringDialog(Context mContext, int theme) {
		super(mContext, theme);
		this.context = mContext;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_wheel_three_string_dialog);

		findViewById(R.id.confirm).setOnClickListener(clickListener);
		findViewById(R.id.cancel).setOnClickListener(clickListener);

		firstAdapter = new ArrayWheelAdapter<String>(context, firstStrings);
		firstAdapter.setTextSize(defTxtSize);
		firstAdapter.setCurTextSize(sltTxtSize);
		firstAdapter.setTextColor(context.getResources().getColor(defTxtColor));

		secondAdatper = new ArrayWheelAdapter<String>(context, secondStrings);
		secondAdatper.setTextSize(defTxtSize);
		secondAdatper.setCurTextSize(sltTxtSize);
		secondAdatper.setTextColor(context.getResources().getColor(defTxtColor));

		threeAdatper = new ArrayWheelAdapter<String>(context, threeStrings);
		threeAdatper.setTextSize(defTxtSize);
		threeAdatper.setCurTextSize(sltTxtSize);
		threeAdatper.setTextColor(context.getResources().getColor(defTxtColor));

		firstWheel = (WheelView) findViewById(R.id.wv_firstwheelview);
//		firstWheel.setCyclic(true);
		firstWheel.setWheelBackground(background);
		firstWheel.setWheelForeground(foreground);
		firstWheel.addChangingListener(firstWheelChangedListener);
		firstWheel.addScrollingListener(firstWheelScrollingListener);
		firstWheel.setVisibleItems(visibleItems);
		firstWheel.setViewAdapter(firstAdapter);
		firstWheel.setCurrentItem(firstSelection);

		secondWheel = (WheelView) findViewById(R.id.wv_secondwheelview);
//		secondWheel.setCyclic(true);
		secondWheel.setWheelBackground(background);
		secondWheel.setWheelForeground(foreground);
		secondWheel.addChangingListener(secondWheelChangedListener);
		secondWheel.addScrollingListener(secondWheelScrollingListener);
		secondWheel.setVisibleItems(visibleItems);
		secondWheel.setViewAdapter(secondAdatper);
		secondWheel.setCurrentItem(secondSelection);

		threeWheel = (WheelView) findViewById(R.id.wv_threewheelview);
//		threeWheel.setCyclic(true);
		threeWheel.setWheelBackground(background);
		threeWheel.setWheelForeground(foreground);
		threeWheel.addChangingListener(threeWheelChangedListener);
		threeWheel.addScrollingListener(threeWheelScrollingListener);
		threeWheel.setVisibleItems(visibleItems);
		threeWheel.setViewAdapter(threeAdatper);
		threeWheel.setCurrentItem(threeSelection);

	}

	private View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.confirm:
				if (null != selectedListner) {
					selectedListner.onSelect(firstWheel.getCurrentItem(), secondWheel.getCurrentItem(),
							threeWheel.getCurrentItem(), firstStrings, secondStrings, threeStrings);
				}
				dismiss();
				break;
			case R.id.cancel:
				dismiss();
				break;
			}
		}
	};

	private OnWheelScrollListener firstWheelScrollingListener = new OnWheelScrollListener() {

		@Override
		public void onScrollingStarted(WheelView wheel) {}

		@Override
		public void onScrollingFinished(WheelView wheel) {
			firstSelection = wheel.getCurrentItem();
			if (Temp != null) {
			   if (Temp.length == nowDay || Temp.length == 28) {
				  threeWheel.setCurrentItem(Temp.length - 1);
				  threeAdatper.setCurIndex(Temp.length - 1);
			   }
			}
		}
	};
	private OnWheelScrollListener secondWheelScrollingListener = new OnWheelScrollListener() {

		@Override
		public void onScrollingStarted(WheelView wheel) {}

		@Override
		public void onScrollingFinished(WheelView wheel) {
			secondSelection = wheel.getCurrentItem();
			if (Temp != null) {
				if (Temp.length < nowDay || Temp.length <= 30 && threeSelection >=29) {
					threeWheel.setCurrentItem(Temp.length - 1);
				}	
			}
		}
	};
	private OnWheelScrollListener threeWheelScrollingListener = new OnWheelScrollListener() {

		@Override
		public void onScrollingStarted(WheelView wheel) {}

		@Override
		public void onScrollingFinished(WheelView wheel) {
			threeSelection = wheel.getCurrentItem();
		}
	};

	private OnWheelChangedListener firstWheelChangedListener = new OnWheelChangedListener() {
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			firstAdapter.setCurIndex(newValue);
			if (isLinkage) {

				if ((newValue + 1) == firstStrings.length) {
					
					if (secondWheel != null) {

						selectedSecondStrings = threeStringsArry[wheel.getCurrentItem()];
						secondStrings = selectedStrings[wheel.getCurrentItem()];
						ArrayList<String> temp = new ArrayList<String>();
						for (int i = 0; i < secondStrings.length; i++) {
							if (secondStrings[i] == null || secondStrings[i].equals("null")) {
								continue;
							}
							temp.add(secondStrings[i]);
						}
						String[] tempString = temp.toArray(new String[temp.size()]);
						secondAdatper = new ArrayWheelAdapter<String>(context, tempString);
						secondAdatper.setCurIndex(0);
						secondAdatper.setTextSize(defTxtSize);
						secondAdatper.setCurTextSize(sltTxtSize);
						secondAdatper.setTextColor(context.getResources().getColor(defTxtColor));
						secondAdatper.setCurTextColor(context.getResources().getColor(sltTxtColor));
						secondWheel.setViewAdapter(secondAdatper);
						secondWheel.setCurrentItem(secondSelection);
					}
					
					if (threeWheel != null) {
						secondWheel.setCurrentItem(0);
						threeStrings = selectedSecondStrings[secondItem];
						ArrayList<String> temp = new ArrayList<String>();
						for (int i = 0; i < threeStrings.length; i++) {
							if (threeStrings[i] == null || threeStrings[i].equals("null")) {
								continue;
							}
							temp.add(threeStrings[i]);
						}
						String[] tempString = temp.toArray(new String[temp.size()]);
						Temp = tempString;
						threeAdatper = new ArrayWheelAdapter<String>(context, tempString);
						threeAdatper.setCurIndex(threeSelection);
						threeAdatper.setTextSize(defTxtSize);
						threeAdatper.setCurTextSize(sltTxtSize);
						threeAdatper.setTextColor(context.getResources().getColor(defTxtColor));
						threeAdatper.setCurTextColor(context.getResources().getColor(sltTxtColor));
						threeWheel.setViewAdapter(threeAdatper);
						threeWheel.setCurrentItem(threeSelection);
					}

				} else {
					
					if (secondWheel != null) {

						selectedSecondStrings = threeStringsArry[wheel.getCurrentItem()];
						secondStrings = selectedStrings[wheel.getCurrentItem()];
						ArrayList<String> temp = new ArrayList<String>();
						for (int i = 0; i < secondStrings.length; i++) {
							if (secondStrings[i] == null || secondStrings[i].equals("null")) {
								continue;
							}
							temp.add(secondStrings[i]);
						}
						String[] tempString = temp.toArray(new String[temp.size()]);
						secondAdatper = new ArrayWheelAdapter<String>(context, tempString);
						secondAdatper.setCurIndex(secondSelection);
						secondAdatper.setTextSize(defTxtSize);
						secondAdatper.setCurTextSize(sltTxtSize);
						secondAdatper.setTextColor(context.getResources().getColor(defTxtColor));
						secondAdatper.setCurTextColor(context.getResources().getColor(sltTxtColor));
						secondWheel.setViewAdapter(secondAdatper);
						secondWheel.setCurrentItem(secondSelection);
					}
					
					if (threeWheel != null) {
//						secondWheel.setCurrentItem(0);
						threeStrings = selectedSecondStrings[secondItem];
						ArrayList<String> temp = new ArrayList<String>();
						for (int i = 0; i < threeStrings.length; i++) {
							if (threeStrings[i] == null || threeStrings[i].equals("null")) {
								continue;
							}
							temp.add(threeStrings[i]);
						}
						String[] tempString = temp.toArray(new String[temp.size()]);
						Temp = tempString;
						threeAdatper = new ArrayWheelAdapter<String>(context, tempString);
						threeAdatper.setCurIndex(threeSelection);
						threeAdatper.setTextSize(defTxtSize);
						threeAdatper.setCurTextSize(sltTxtSize);
						threeAdatper.setTextColor(context.getResources().getColor(defTxtColor));
						threeAdatper.setCurTextColor(context.getResources().getColor(sltTxtColor));
						threeWheel.setViewAdapter(threeAdatper);
						threeWheel.setCurrentItem(threeSelection);
					}
				}
			}
		}
	};

	private OnWheelChangedListener secondWheelChangedListener = new OnWheelChangedListener() {
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			secondAdatper.setCurIndex(newValue);
			if (isLinkage) {
				if (threeWheel != null) {
					secondItem = wheel.getCurrentItem();
					threeStrings = selectedSecondStrings[wheel.getCurrentItem()];
					ArrayList<String> temp = new ArrayList<String>();
					for (int i = 0; i < threeStrings.length; i++) {
						if (threeStrings[i] == null || threeStrings[i].equals("null")) {
							continue;
						}
						temp.add(threeStrings[i]);
					}
					String[] tempString = temp.toArray(new String[temp.size()]);
					Temp = tempString;
					threeAdatper = new ArrayWheelAdapter<String>(context, tempString);
					threeAdatper.setCurIndex(threeSelection);
					threeAdatper.setTextSize(defTxtSize);
					threeAdatper.setCurTextSize(sltTxtSize);
					threeAdatper.setTextColor(context.getResources().getColor(defTxtColor));
					threeAdatper.setCurTextColor(context.getResources().getColor(sltTxtColor));
					threeWheel.setViewAdapter(threeAdatper);
					threeWheel.setCurrentItem(threeSelection);
				}
			}
		}
	};

	private OnWheelChangedListener threeWheelChangedListener = new OnWheelChangedListener() {
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			threeAdatper.setCurIndex(newValue);
		}
	};

	public interface OnThreeSelectedListner {
		public void onSelect(int firstPosition, int secondPosition, int threePosition, String[] firstStrings,
				String[] secondStrings, String[] threeStrings);
	}

	public void setSelectedThreeListner(OnThreeSelectedListner selectedListner) {
		this.selectedListner = selectedListner;
	}

	public void setData(String[] mFirstStrings, String[] selectedSecondStrings, String[] selectedThreeStrings,
			String[][] selectedStrings, String[][][] mThreeStrings) {
		this.firstStrings = mFirstStrings;
		this.secondStrings = selectedSecondStrings;
		this.threeStrings = selectedThreeStrings;
		this.selectedStrings = selectedStrings;
		this.threeStringsArry = mThreeStrings;

		if (isLinkage) {
			convertData();
		}
	}

	public void setFirstSelection(int firstSelection) {
		this.firstSelection = firstSelection;
	}

	public void setSecondSelection(int secondSelection) {
		this.secondSelection = secondSelection;
	}

	public void setThreeSelection(int threeSelection) {
		this.threeSelection = threeSelection;
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

	public void setNowYear(boolean isNowYear) {
		this.isNowYear = isNowYear;
	}
	public void setNowDay(int nowDay) {
		this.nowDay = nowDay;
	}

	private void convertData() {
		if (threeStringsArry != null && threeStringsArry.length >= firstSelection - 1) {
			if (isNowYear) {
				selectedSecondStrings = threeStringsArry[99];
				
				String[] tempString = selectedStrings[99];
				ArrayList<String> temp = new ArrayList<String>();
				for (int i = 0; i < tempString.length; i++) {
					if (tempString[i] == null || tempString[i].equals("null")) {
						continue;
					}
					temp.add(tempString[i]);
				}
				secondStrings = temp.toArray(new String[temp.size()]);

				if (selectedSecondStrings != null && selectedSecondStrings.length >= secondSelection - 1) {
					String[] tempString1 = selectedSecondStrings[secondSelection];
					ArrayList<String> temp1 = new ArrayList<String>();
					for (int i = 0; i < tempString1.length; i++) {
						if (tempString1[i] == null || tempString1[i].equals("null")) {
							continue;
						}
						temp1.add(tempString1[i]);
					}
					threeStrings = temp1.toArray(new String[temp1.size()]);
				}
			}else {
				selectedSecondStrings = threeStringsArry[firstSelection];
				secondStrings = selectedStrings[firstSelection];

				if (selectedSecondStrings != null && selectedSecondStrings.length >= secondSelection - 1) {
					String[] tempString1 = selectedSecondStrings[secondSelection];
					ArrayList<String> temp1 = new ArrayList<String>();
					for (int i = 0; i < tempString1.length; i++) {
						if (tempString1[i] == null || tempString1[i].equals("null")) {
							continue;
						}
						temp1.add(tempString1[i]);
					}
					threeStrings = temp1.toArray(new String[temp1.size()]);
				}
			}
			
		}
	}
}
