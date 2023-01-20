package com.example.demo.enums;

import com.example.demo.form.ItemSearchForm;

public class SearchItemFotmConstant {

	public enum SearchItem {

		INSTANCE;

		private String name;
		private String bigCategory;
		private String middleCategory;
		private String smallCategory;
		private String brand;
		private boolean search;

		public static SearchItem getSearchItemForm() {
			return INSTANCE; // これで6項目全て返せているのか？？
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			INSTANCE.name = name;
		}

		public String getBigCategory() {
			return bigCategory;
		}

		public void setBigCategory(String bigcategory) {
			this.bigCategory = bigcategory;
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

		public boolean isSearch() {
			return search;
		}

		public void setSearch(boolean search) {
			this.search = search;
		}

		public static void setItemSearchForm(ItemSearchForm form) {
			INSTANCE.setName(form.getName());
			INSTANCE.setBigCategory(form.getBigCategory());
			INSTANCE.setMiddleCategory(form.getMiddleCategory());
			INSTANCE.setSmallCategory(form.getSmallCategory());
			INSTANCE.setBrand(form.getBrand());
			INSTANCE.setSearch(true);
		}

		public static void resetItemSearchForm() {
			INSTANCE.setName("");
			INSTANCE.setBigCategory("");
			INSTANCE.setMiddleCategory("");
			INSTANCE.setSmallCategory("");
			INSTANCE.setBrand("");
			INSTANCE.setSearch(false);
		}

	}
}
