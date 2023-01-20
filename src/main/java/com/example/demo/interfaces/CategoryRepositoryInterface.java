package com.example.demo.interfaces;

import java.util.List;

import com.example.demo.domain.Category;

public interface CategoryRepositoryInterface {

	/**
	 * Categoryを１件登録するメソッド.
	 * @param category
	 */
	public void insert(Category category);
	
	/**
	 * Categoryテーブルのnameで検索し、該当するレコードのidを返すメソッド.
	 * @param name
	 * @return
	 */
	public Integer findId(String name);
	
	/**
	 * Categoryの全権検索.
	 * @return
	 */
	public List<Category> showCategory();
	
	/**
	 * parentとname_allがnull（大カテゴリ）のものを全権検索.
	 * @return
	 */
	public List<Category> showBigCategory();
	
	/**
	 * parentがNOT NULLでname_allがNULL（中カテゴリ）のものを全権検索.
	 * @return
	 */
	public List<Category> showMiddleCategory();
	
	/**
	 * CategoryのすべてNOT NULLのものを全権検索.
	 * @return
	 */
	public List<Category> showSmallCategory();
	
	/**
	 * 中カテゴリのIdでCategoryを１件検索するメソッド.
	 * @param middleCategoryId
	 * @return
	 */
	public Category findCategory(String middleCategoryId);
	
	/**
	 * 中カテゴリの名前で検索し、idを返すメソッド.
	 * @param middleCategoryName
	 * @return
	 */
	public Integer findMiddleId(String middleCategoryName);
	
	/**
	 * 大カテゴリを名前で検索し、idを取得するメソッド.
	 * @param bigCategoryName
	 * @return
	 */
	public Integer findBigId(String bigCategoryName);
	
	/**
	 * 小カテゴリを名前で検索し、idを取得するメソッド.
	 * @param smallCategoryName
	 * @return
	 */
	public Integer findSmallId(String smallCategoryName);
	
	/**
	 * カテゴリを名前で検索し、idを取得するメソッド.
	 * @param categoryId
	 * @return
	 */
	public Category findCategory(Integer categoryId);
	
	/**
	 * name_allで検索しCategory1件返すメソッド.
	 * @param nameAll
	 * @return
	 */
	public Category findCategoryByNameAll(String nameAll);
}
