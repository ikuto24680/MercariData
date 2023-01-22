package com.example.demo.domain;

public class ItemCategory {
	/**  ID*/
	private Integer id;
	/**  名前*/
	private String name;
	/**  コンディション*/
	private Integer condition;
	/**  name_all*/
	private String nameAll;
	/**  ブランド*/
	private String brand;
	/**  値段*/
	private Double price;
	/**  発送単位*/
	private Integer shipping;
	/**  商品説明*/
	private String description;
	/** バージョン　*/
	private Integer version;
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
	public Integer getCondition() {
		return condition;
	}
	public void setCondition(Integer condition) {
		this.condition = condition;
	}
	public String getNameAll() {
		return nameAll;
	}
	public void setNameAll(String nameAll) {
		this.nameAll = nameAll;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
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
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "ItemCategory [id=" + id + ", name=" + name + ", condition=" + condition + ", nameAll=" + nameAll
				+ ", brand=" + brand + ", price=" + price + ", shipping=" + shipping + ", description=" + description
				+ ", version=" + version + "]";
	}
	
}
