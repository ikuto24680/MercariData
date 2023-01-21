package com.example.demo.form;

import javax.validation.constraints.NotBlank;

public class ItemAddForm {

	/** 名前　*/
	@NotBlank(message="名前は必須です")
	private String name;
	/** 値段*/
	@NotBlank(message="値段は必須です")
	private String price;
	/** カテゴリの一部*/
	@NotBlank(message="カテゴリは必須です")
	private String bigCategory;
	/** カテゴリの一部*/
	@NotBlank(message="カテゴリは必須です")
	private String middleCategory;
	/** カテゴリの一部*/
	@NotBlank(message="カテゴリは必須です")
	private String smallCategory;
	/** ブランド*/
	@NotBlank(message="ブランド名は必須です")
	private String brand;
	/** コンディション*/
	@NotBlank(message="コンディションは必須です")
	private String condition;
	/** 発送単位*/
	@NotBlank(message="発送単位は必須です")
	private String shipping;
	/** 商品説明*/
	@NotBlank(message="商品説明は必須です")
	private String description;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getBigCategory() {
		return bigCategory;
	}
	public void setBigCategory(String bigCategory) {
		this.bigCategory = bigCategory;
	}
	public String getMiddleCategory() {
		return middleCategory;
	}
	public void setMiddleCategory(String middleCategory) {
		this.middleCategory = middleCategory;
	}
	public String getSmallCategory() {
		return smallCategory;
	}
	public void setSmallCategory(String smallCategory) {
		this.smallCategory = smallCategory;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getShipping() {
		return shipping;
	}
	public void setShipping(String shipping) {
		this.shipping = shipping;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "ItemAddForm [name=" + name + ", price=" + price + ", bigCategory=" + bigCategory + ", middleCategory="
				+ middleCategory + ", smallCategory=" + smallCategory + ", brand=" + brand + ", condition=" + condition
				+ ", shipping=" + shipping + ", description=" + description + "]";
	}
}
