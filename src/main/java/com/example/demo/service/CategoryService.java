package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Category;
import com.example.demo.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> showAllCategory(){
		return categoryRepository.showCategory();
	}
	
	public List<Category> showBigCategory(){
		return categoryRepository.showBigCategory();
	}
	
	public List<Category> showMiddleCategory(){
		return categoryRepository.showMiddleCategory();
	}
	
	public List<Category> showSmallCategory(){
		return categoryRepository.showSmallCategory();
	}
	
	public Category findCategory(String middleCategoryId) {
		return categoryRepository.findCategory(middleCategoryId);
	}

	public Integer findId(String bigCategory) {
		return categoryRepository.findId(bigCategory);
	}
	
	public Integer findMiddleId(String middleCategoryName) {
		return categoryRepository.findMiddleId(middleCategoryName);
	}
	
	public Integer findBigId(String bigCategoryName) {
		return categoryRepository.findBigId(bigCategoryName);
	}
	
	public Integer findSmallId(String smallCategoryName) {
		return categoryRepository.findSmallId(smallCategoryName);
	}
}
