package com.datepicker.text;

import java.util.ArrayList;

import com.datepicker.view.wheel.model.WheelDoubleStringDialog;
import com.datepicker.view.wheel.model.WheelDoubleStringDialog.OnDoubleSelectedListner;
import com.datepicker.view.wheel.model.WheelSingleStringDialog;
import com.datepicker.view.wheel.model.WheelSingleStringDialog.OnSelectedListner;
import com.datepicker.view.wheel.model.WheelThreeStringDialog;
import com.datepicker.view.wheel.model.WheelThreeStringDialog.OnThreeSelectedListner;
import com.example.datepicker.R;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
/**
 * 一个简单的滚轮控件，包含单个，二级，三级。只是单纯的控件，数据都是由外层处理。
 * @author 北京-許
 *
 */

public class MainActivity extends Activity implements OnClickListener {

	private TextView date, sex, address;
	private WheelThreeStringDialog dateDialog;
	private String data;
	private boolean isShowNow;
	
	
	private WheelSingleStringDialog sexDialog;
	private MySexInfo mySexInfo;
	private	ArrayList<MySexInfo> listSexDec = new ArrayList<MySexInfo>();
	private String sexTemp = "";

	
	private WheelDoubleStringDialog addressDialog;
	private String [] s1;
	private String[][] str;
	private int province;
	private int city;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		date = (TextView) findViewById(R.id.date);
		sex = (TextView) findViewById(R.id.sex);
		address = (TextView) findViewById(R.id.address);
		date.setOnClickListener(this);
		sex.setOnClickListener(this);
		address.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.date:
			showDateDialog();
			// isShowNow = true;
			break;
		case R.id.sex:
			showSexDialog();
			break;
		case R.id.address:
			showAddressDialog();
			break;

		}

	}
	
	//雙滾輪
	private void showAddressDialog(){
		s1 = new String[]{"河北","山西","内蒙古"};
		String [] s2 = {"石家庄","唐山","秦皇岛","邯郸","邢台","保定","张家口","承德"};
		String [] s3 = {"太原","大同","阳泉","长治","晋城","朔州","忻州","吕梁","晋中","临汾","运城"};
		String [] s4 = {"呼和浩特","包头","乌海","赤峰","呼伦贝尔","兴安盟","锡林郭勒盟","乌兰察布盟"};
		str = new String[][]{s2,s3,s4};
		addressDialog = new WheelDoubleStringDialog(this);
		addressDialog.setSelectedListner(addressSelectedListner);
		addressDialog.setLinkage(true);
		addressDialog.setFirstSelection(province);
		addressDialog.setSecondSelection(city);
		addressDialog.setData(s1, null, str);
		
		addressDialog.show();
	}

	OnDoubleSelectedListner addressSelectedListner = new OnDoubleSelectedListner() {
		
		@Override
		public void onSelect(int firstPosition, int secondPosition) {
			province = firstPosition;
			city = secondPosition;
			address.setText(s1[firstPosition] + "  " + str[firstPosition][secondPosition]);
			
		}
	};
	
	//單個滾輪
	private void showSexDialog(){
		mySexInfo = new MySexInfo(this);
		listSexDec = mySexInfo.getList();
		sexDialog = new WheelSingleStringDialog(this);
		sexDialog.setSelectedListner(selectedListner);
		sexDialog.setData(buildArrayValue());
		sexDialog.setSelection(checkDescSelected());
		sexDialog.show();
	}
	
	private String[] buildArrayValue(){
		String[] strings = new String[listSexDec.size()];
		for(int i=0;i<listSexDec.size();i++){
			strings[i] = listSexDec.get(i).getSexDec();
		}
		return strings;
	}
	
	private int checkDescSelected(){
		int index = 0;
		for(int i=0;i<listSexDec.size();i++){
			if(sexTemp.equals(listSexDec.get(i).getSexDec())){
				return i;
			}
		}
		return index;
	}

	private OnSelectedListner selectedListner = new OnSelectedListner() {
		@Override
		public void onSelect(int index) {
			if (index == 0) {
				sexTemp = getResources().getString(R.string.sex_male);
			}else if (index == 1) {
				sexTemp = getResources().getString(R.string.sex_female);
			}else if (index == 2) {
				sexTemp = getResources().getString(R.string.sex_secrecy);
			}
			sex.setText(sexTemp);
		}
	};

	//三級聯動滾輪
	private void showDateDialog() {

		long time1 = System.currentTimeMillis();
		String birthday1 = StringUtils.formatDateNew(time1);
		String[] temp1 = birthday1.split("-");
		String currentYear = temp1[0];
		int topYear = Integer.parseInt(currentYear) - 99;
		int yearNow = Integer.valueOf(temp1[0]);
		String yearAndMonthAndDay[][][] = new String[100][12][31];
		String year[] = new String[100];
		String yearAndMonth[][] = new String[100][];
		String month[] = null;
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = topYear; i <= Integer.parseInt(currentYear); i++) {
			year[i - topYear] = i + "";
			int position = i - topYear;
			String monthAndDay[][] = new String[12][31];
			month = new String[12];
			for (int j = 0; j < 12; j++) {
				if (yearNow == i && j >= Integer.valueOf(temp1[1]))
					continue;
				month[j] = j + 1 + "";
				String day[] = new String[31];
				for (int k = 0; k < getDay(i, j + 1); k++) {
					if (yearNow == i && j == Integer.valueOf(temp1[1]) - 1 && k >= Integer.valueOf(temp1[2]))
						continue;
					day[k] = k + 1 + "";
				}
				monthAndDay[j] = day;
			}
			yearAndMonth[i - topYear] = month;
			yearAndMonthAndDay[position] = monthAndDay;
		}

		dateDialog = new WheelThreeStringDialog(this);

		dateDialog.setSelectedThreeListner(onThreeSelectedListner);
		dateDialog.setLinkage(true);
		dateDialog.setNowDay(Integer.valueOf(temp1[2]));

		if (isShowNow) {
			dateDialog.setNowYear(true);
			dateDialog.setFirstSelection(99);
			dateDialog.setSecondSelection(Integer.valueOf(temp1[1]) - 1);
			dateDialog.setThreeSelection(Integer.valueOf(temp1[2]) - 1);
		} else {
			long time = 0;
			if (!TextUtils.isEmpty(data)) {
				time = StringUtils.formatDateNew(data, "");// 取得用户资料中的生日的long值
			} else {
//				time = Long.parseLong("555555555");
			}

			String[] temp2 = StringUtils.formatDateNew(time).split("-");
			if (Integer.valueOf(temp2[0]) == yearNow) {
				dateDialog.setNowYear(true);
			}
			dateDialog.setFirstSelection(Integer.valueOf(temp2[0]) - yearNow + 99);
			dateDialog.setSecondSelection(Integer.valueOf(temp2[1]) - 1);
			dateDialog.setThreeSelection(Integer.valueOf(temp2[2]) - 1);
		}
		dateDialog.setData(year, null, null, yearAndMonth, yearAndMonthAndDay);
		dateDialog.show();
	}

	OnThreeSelectedListner onThreeSelectedListner = new OnThreeSelectedListner() {

		@Override
		public void onSelect(int firstPosition, int secondPosition, int threePosition, String[] firstStrings,
				String[] secondStrings, String[] threeStrings) {
			data = firstStrings[firstPosition] + "-" + secondStrings[secondPosition] + "-"
					+ threeStrings[threePosition];
			date.setText(data);
			date.setTextColor(getResources().getColor(R.color.new_text_gray_1));

		}
	};

	private int getDay(int year, int month) {
		int day = 30;
		boolean flag = false;
		if (((year % 100 == 0) && (year % 400 == 0)) || ((year % 100 != 0) && (year % 4 == 0))) {
			flag = true;
		} else {
			flag = false;
		}

		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 2:
			day = flag ? 29 : 28;
			break;
		default:
			day = 30;
			break;
		}
		return day;
	}

}
