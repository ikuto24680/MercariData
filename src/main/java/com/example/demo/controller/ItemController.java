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
import com.example.demo.domain.ItemCategory;
import com.example.demo.form.ItemAddForm;
import com.example.demo.form.ItemEditForm;
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

	int ListPageCount = 0;
	int searchPageCount = 0;
	String searchName = "";
	String searchBrand = "";
	String searchBig = "";
	String searchMiddle = "";
	String searchSmall = "";
	boolean search = false;

	@GetMapping("/search")
	public String showList(ItemSearchForm form, Model model, Integer page) {
		ListPageCount = 0;
		searchPageCount = 0;

		resetSearch();

		List<ItemCategory> itemList = itemService.findBySearchForm(form, page);
		model.addAttribute("itemList", itemList);

		searchName = form.getName();
		searchBrand = form.getBrand();
		searchBig = form.getBigCategory();
		searchMiddle = form.getMiddleCategory();
		searchSmall = form.getSmallCategory();
		search = true;

		List<Category> bigCategoryList = categoryService.showBigCategory();
		model.addAttribute("bigCategoryList", bigCategoryList);
		List<Category> middleCategoryList = categoryService.showMiddleCategory();
		model.addAttribute("middleCategoryList", middleCategoryList);
		List<Category> smallCategoryList = categoryService.showSmallCategory();
		model.addAttribute("smallCategoryList", smallCategoryList);
		return "list";
	}

	@RequestMapping("/list")
	public String list(Model model, Integer page) {
		List<ItemCategory> itemList;

		if (page != null) {
			if (page == 1) {
				ListPageCount += 1;
			} else {
				ListPageCount -= 1;
				if (ListPageCount < 0) {
					ListPageCount = 0;
				}
			}
			if (search) {
				ItemSearchForm form = new ItemSearchForm();
				form.setName(searchName);
				form.setBrand(searchBrand);
				form.setBigCategory(searchBig);
				form.setMiddleCategory(searchMiddle);
				form.setSmallCategory(searchSmall);
				itemList = itemService.findBySearchForm(form, ListPageCount);

			} else {
				itemList = itemService.findList(ListPageCount);
			}
		} else {
			resetSearch();
			itemList = itemService.findList(ListPageCount);
		}
		model.addAttribute("itemList", itemList);

		List<Category> bigCategoryList = categoryService.showBigCategory();
		model.addAttribute("bigCategoryList", bigCategoryList);
		List<Category> middleCategoryList = categoryService.showMiddleCategory();
		model.addAttribute("middleCategoryList", middleCategoryList);
		List<Category> smallCategoryList = categoryService.showSmallCategory();
		model.addAttribute("smallCategoryList", smallCategoryList);

		return "list";
	}

	@GetMapping("/detail")
	public String detail(Integer itemId, Model model) {
		ItemCategory detail = itemService.searchDetail(itemId);
		model.addAttribute("detail", detail);
		return "detail";
	}

	@GetMapping("/toAdd")
	public String toAdd(Model model) {

		List<Category> bigCategoryList = categoryService.showBigCategory();
		model.addAttribute("bigCategoryList", bigCategoryList);
		List<Category> middleCategoryList = categoryService.showMiddleCategory();
		model.addAttribute("middleCategoryList", middleCategoryList);
		List<Category> smallCategoryList = categoryService.showSmallCategory();
		model.addAttribute("smallCategoryList", smallCategoryList);

		return "add";
	}

	@PostMapping("/add")
	public String add(ItemAddForm form) {
		Item item = new Item();
		Integer categoryId = 0;
		if ((form.getBigCategory() == null && form.getMiddleCategory() == null) && form.getSmallCategory() == null) {

		} else if (form.getBigCategory() != null && form.getMiddleCategory() == null
				&& form.getSmallCategory() == null) {
			categoryId = categoryService.findBigId(form.getBigCategory());
		} else if ((form.getBigCategory() != null && form.getMiddleCategory() != null)
				&& form.getSmallCategory() == null) {
			categoryId = categoryService.findMiddleId(form.getMiddleCategory());
		} else if ((form.getBigCategory() != null && form.getMiddleCategory() != null)
				&& form.getSmallCategory() != null) {
			categoryId = categoryService.findSmallId(form.getSmallCategory());
		}
		BeanUtils.copyProperties(form, item);
		item.setCategory(categoryId);
		item.setCondition(Integer.parseInt(form.getCondition()));
		item.setPrice(Double.parseDouble(form.getPrice()));
		item.setShipping(Integer.parseInt(form.getShipping()));
		itemService.addItem(item);
		return "redirect:/item/showList";
	}

	@ResponseBody
	@PostMapping("/middle")
	public List<Category> middle(String bigCategory) {
		System.out.println("/middleに来ました。");
		List<Category> middleList = new ArrayList<>();
		Integer bigCategoryId = categoryService.findId(bigCategory);
		List<Category> middleCategoryList = categoryService.showMiddleCategory();
		for (Category middle : middleCategoryList) {
			if (middle.getParent() == bigCategoryId) {
				middleList.add(middle);
			}
		}

		return middleList;
	}

	@ResponseBody
	@PostMapping("/small")
	public List<Category> small(String middleCategory) {
		List<Category> smallList = new ArrayList<>();
		Integer MC = categoryService.findId(middleCategory);
		List<Category> smallCategoryList = categoryService.showSmallCategory();
		for (Category small : smallCategoryList) {
			if (small.getParent() == MC) {
				smallList.add(small);
			}
		}
		return smallList;
	}

	@GetMapping("/toEdit")
	public String edit(Integer itemId, Model model) {
		ItemCategory detail = itemService.searchDetail(itemId);

		model.addAttribute("detail", detail);

		String[] category = detail.getNameAll().split("/");
		model.addAttribute("bigCategory", category[0]);
		model.addAttribute("middleCategory", category[1]);
		model.addAttribute("smallCategory", category[2]);

		List<Category> bigCategoryList = categoryService.showBigCategory();
		model.addAttribute("bigCategoryList", bigCategoryList);
		List<Category> middleCategoryList = categoryService.showMiddleCategory();
		model.addAttribute("middleCategoryList", middleCategoryList);
		List<Category> smallCategoryList = categoryService.showSmallCategory();
		model.addAttribute("smallCategoryList", smallCategoryList);

		return "edit";
	}

	@PostMapping("/edit")
	public String edit(ItemEditForm form, Model model) {
		Item item = new Item();
		BeanUtils.copyProperties(form, item);
		item.setCategory((categoryService.findCategoryByNameAll(
				form.getBigCategory() + "/" + form.getMiddleCategory() + "/" + form.getSmallCategory())).getId());
		itemService.edit(item);
		ItemCategory detail = itemService.searchDetail(form.getId());
		model.addAttribute("detail", detail);
		return "detail";
	}

	public void resetSearch() {
		searchName = "";
		searchBrand = "";
		searchBig = "";
		searchMiddle = "";
		searchSmall = "";
		search = false;
	}
}
