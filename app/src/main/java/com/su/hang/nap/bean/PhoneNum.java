package com.su.hang.nap.bean;

public class PhoneNum {
	public String name;// 名字
	public String number;// 手机号

	@Override
	public String toString() {
		return "{\"name\":\"" + name + "\",\"number\":\"" + number + "\"}";
	}
}
