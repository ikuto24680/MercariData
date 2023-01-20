package com.example.demo.form;

public class ItemSearchForm {

	/** name */
	private String name;
	
	/** カテゴリの一部 */
	private String bigCategory;
	
	/** カテゴリの一部 */
	private String middleCategory;
	
	/** カテゴリの一部 */
	private String smallCategory;
	
	/** ブランド　*/
	private String brand;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return "ItemSearchForm [name=" + name + ", bigCategory=" + bigCategory + ", middleCategory=" + middleCategory
				+ ", smallCategory=" + smallCategory + ", brand=" + brand + "]";
	}
}
