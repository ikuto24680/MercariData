package com.example.demo.form;

public class ItemAddForm {

	private String name;
	private String price;
	private String bigcategory;
	private String middlecategory;
	private String smallcategory;
	private String brand;
	private String condition;
	private String shipping;
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
	public String getBigcategory() {
		return bigcategory;
	}
	public void setBigcategory(String bigcategory) {
		this.bigcategory = bigcategory;
	}
	public String getMiddlecategory() {
		return middlecategory;
	}
	public void setMiddlecategory(String middlecategory) {
		this.middlecategory = middlecategory;
	}
	public String getSmallcategory() {
		return smallcategory;
	}
	public void setSmallcategory(String smallcategory) {
		this.smallcategory = smallcategory;
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
		return "ItemAddForm [name=" + name + ", price=" + price + ", bigcategory=" + bigcategory + ", middlecategory="
				+ middlecategory + ", smallcategory=" + smallcategory + ", brand=" + brand + ", condition=" + condition
				+ ", shipping=" + shipping + ", description=" + description + "]";
	}
}
