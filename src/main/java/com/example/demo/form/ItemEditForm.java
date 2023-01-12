package com.example.demo.form;

public class ItemEditForm {
	
	private Integer id;

	private String name;
	
	private Double price;

	private String bigCategory;

	private String middleCategory;

	private String smallCategory;

	private String brand;
	
	private Integer condition;
	
	private Integer shipping;
	
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
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

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	public Integer getShipping() {
		return shipping;
	}

	public void setShipping(Integer shipping) {
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
		return "ItemEditForm [id=" + id + ", name=" + name + ", price=" + price + ", bigCategory=" + bigCategory
				+ ", middleCategory=" + middleCategory + ", smallCategory=" + smallCategory + ", brand=" + brand
				+ ", condition=" + condition + ", shipping=" + shipping + ", description=" + description + "]";
	}


}
