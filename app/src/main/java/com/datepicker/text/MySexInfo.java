package com.datepicker.text;

import java.util.ArrayList;

import com.example.datepicker.R;

import android.content.Context;

public class MySexInfo {
	private	ArrayList<MySexInfo> listSexDec = new ArrayList<MySexInfo>();
	
	private String sexDec;
	private Context context;
	
	public String getSexDec() {
		return sexDec;
	}

	public void setSexDec(String sexDec) {
		this.sexDec = sexDec;
	}

	public MySexInfo(Context context) {
		super();
		this.context = context;
	}


	public ArrayList<MySexInfo> getList(){
		MySexInfo sexDec = new MySexInfo(context);
		sexDec.setSexDec(context.getResources().getString(R.string.sex_male));
		listSexDec.add(sexDec);
		MySexInfo sexDec1 = new MySexInfo(context);
		sexDec1.setSexDec(context.getResources().getString(R.string.sex_female));
		listSexDec.add(sexDec1);
		MySexInfo sexDec2 = new MySexInfo(context);
		sexDec2.setSexDec(context.getResources().getString(R.string.sex_secrecy));
		listSexDec.add(sexDec2);
		return listSexDec;
	}
}
