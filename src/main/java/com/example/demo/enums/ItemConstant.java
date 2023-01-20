package com.example.demo.enums;

public class ItemConstant {
	
	public enum Condition {
		販売中,
		特価販売中,
		販売NG,
	}

	public static Condition ConditionSwitch(Integer conditionNum) throws Exception {
		switch(conditionNum) {
		case 1:
			return Condition.販売中;
		case 2:
			return Condition.特価販売中;
		case 3:
			return Condition.販売NG;
		}
		return null;
	}
}
