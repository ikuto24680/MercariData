package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.Category;
import com.example.demo.domain.Item;
import com.example.demo.form.ItemAddForm;
import com.example.demo.form.ItemSearchForm;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CategoryService categoryService;
	
	int pageCount = 0;

	@GetMapping("/showList")
	public String showList(ItemSearchForm form,Integer minusPage,Integer plusPage,Model model) {
		if(minusPage!=null) {
			pageCount++;
		}else if(plusPage!=null) {
			pageCount--;
			if(pageCount<0) {
				pageCount=0;
			}
		}
		List<Item> itemList = itemService.showList(form, pageCount);
		model.addAttribute("itemList",itemList);
		return "list";
	}
	
	@GetMapping("/toAdd")
	public String toAdd(Model model) {
		
		//全体のcategoryList
		List<Category> categoryList = categoryService.showAllCategory();
		model.addAttribute("categoryList",categoryList);
		//大カテゴリのみcategory
		List<Category> bigCategoryList = categoryService.showBigCategory();
		model.addAttribute("bigCategoryList",bigCategoryList);
		List<Category> middleCategoryList = categoryService.showMiddleCategory();
		model.addAttribute("middleCategoryList",middleCategoryList);
		List<Category> smallCategoryList = categoryService.showSmallCategory();
		model.addAttribute("smallCategoryList",smallCategoryList);

		return "add";
	}
	
	@PostMapping("/add")
	public String add(ItemAddForm form) {
		System.out.println("/addに移動しました");
		System.out.println("form = "+form);
		//CategoryのINSERT→id取得
		Item item = new Item();
		Integer categoryId = 0;
		if((form.getBigcategory()==null&&form.getMiddlecategory()==null)&&form.getSmallcategory()==null) {
			
		}else if(form.getBigcategory()!=null&&form.getMiddlecategory()==null&&form.getSmallcategory()==null) {
			categoryId = categoryService.findBigId(form.getBigcategory());
		}else if((form.getBigcategory()!=null&&form.getMiddlecategory()!=null)&&form.getSmallcategory()==null){
			categoryId = categoryService.findMiddleId(form.getMiddlecategory());
		}else if((form.getBigcategory()!=null&&form.getMiddlecategory()!=null)&&form.getSmallcategory()!=null) {
			categoryId = categoryService.findSmallId(form.getSmallcategory());
		}
		BeanUtils.copyProperties(form, item);
		System.out.println("categoryId = "+categoryId);
		item.setCategory(categoryId);
		item.setCondition(Integer.parseInt(form.getCondition()));
		item.setPrice(Double.parseDouble(form.getPrice()));
		item.setShipping(Integer.parseInt(form.getShipping()));
		itemService.addItem(item);
		//Categoryのidを使ってItemをINSERT
		return "redirect:/item/showList";
	}
	
	@ResponseBody
	@PostMapping("/middle")
	public List<Category> middle(String bigCategory){
		List<Category> middleList = new ArrayList<>();
		Integer bigCategoryId = categoryService.findId(bigCategory);
		List<Category> middleCategoryList = categoryService.showMiddleCategory();
		for(Category middle : middleCategoryList) {
			if(middle.getParent()==bigCategoryId) {
			middleList.add(middle);
		}
		}
		
		return middleList;
	}
	
	@ResponseBody
	@PostMapping("/small")
	public List<Category> small(String middleCategory){
		List<Category> smallList = new ArrayList<>();
		Integer MC = categoryService.findId(middleCategory);
		List<Category> smallCategoryList = categoryService.showSmallCategory();
		for(Category small : smallCategoryList) {
			if(small.getParent()==MC) {
			smallList.add(small);
		}
		}
		return smallList;
	}
}
