package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Category;
import com.example.demo.repository.CategoryRepository;

/**
 * ItemControllerで使うCategoryテーブル系のServiceクラス.
 * @author pengi
 */
@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	/**
	 * 大カテゴリだけリストで返す.
	 * @return
	 */
	public List<Category> showBigCategory(){
		return categoryRepository.showBigCategory();
	}
	
	/**
	 * 中カテゴリだけリストで返す.
	 * @return
	 */
	public List<Category> showMiddleCategory(){
		return categoryRepository.showMiddleCategory();
	}
	
	/**
	 *小カテゴリだけリストで返す.
	 * @return
	 */
	public List<Category> showSmallCategory(){
		return categoryRepository.showSmallCategory();
	}
	
	/**
	 * idをもとに該当するCategoryを１件返す.
	 * @param middleCategoryId
	 * @return
	 */
	public Category findCategory(String middleCategoryId) {
		return categoryRepository.findCategory(middleCategoryId);
	}

	/**
	 * 大カテゴリの名前をもとに該当するIDを返す.
	 * @param bigCategory
	 * @return
	 */
	public Integer findId(String bigCategory) {
		return categoryRepository.findId(bigCategory);
	}
	
	/**
	 * 中カテゴリの名前をもとに該当するIDを返す
	 * @param middleCategoryName
	 * @return
	 */
	public Integer findMiddleId(String middleCategoryName) {
		return categoryRepository.findMiddleId(middleCategoryName);
	}
	
	/**
	 * 名前をもとに該当するCategoryのIDを返す.
	 * @param bigCategoryName
	 * @return
	 */
	public Integer findBigId(String bigCategoryName) {
		return categoryRepository.findBigId(bigCategoryName);
	}
	
	/**
	 * 名前をもとに該当する小カテゴリのIDを返す.
	 * @param smallCategoryName
	 * @return
	 */
	public Integer findSmallId(String smallCategoryName) {
		return categoryRepository.findSmallId(smallCategoryName);
	}
	
	/**
	 * カテゴリIDをもとに該当するカテゴリを返す.
	 * @param categoryId
	 * @return
	 */
	public Category findCategory(Integer categoryId) {
		return categoryRepository.findCategory(categoryId);
	}
	
	/**
	 * name_allをもとに該当するカテゴリを返す.
	 * @param nameAll
	 * @return
	 */
	public Category findCategoryByNameAll(String nameAll) {
		return categoryRepository.findCategoryByNameAll(nameAll);
	}
}
