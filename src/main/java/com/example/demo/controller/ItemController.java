package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.Category;
import com.example.demo.domain.Item;
import com.example.demo.domain.ItemCategory;
import com.example.demo.enums.ItemConstant;
import com.example.demo.enums.ItemConstant.Condition;
import com.example.demo.enums.PageCountConstant.PageCount;
import com.example.demo.enums.SearchItemFotmConstant.SearchItem;
import com.example.demo.form.ItemAddForm;
import com.example.demo.form.ItemEditForm;
import com.example.demo.form.ItemSearchForm;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ItemService;

/**
 * ItemやCategoryにかんするコントローラ
 * 
 * @author pengi
 */
@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@Autowired
	private CategoryService categoryService;

	/**
	 * 検索フォームとページングに基づいて該当のListを返し、listに遷移する.
	 * 
	 * @param form
	 * @param model
	 * @param page
	 * @return
	 */
	@GetMapping("/search")
	public String showList(ItemSearchForm form, Model model, Integer page) {

		PageCount.setPageCount(0);

		SearchItem searchItemInstance = SearchItem.getSearchItemForm();
		SearchItem.resetItemSearchForm();

		List<ItemCategory> itemList = itemService.findBySearchForm(form, page);
		model.addAttribute("itemList", itemList);

		searchItemInstance.setName(form.getName());
		searchItemInstance.setBigCategory(form.getBigCategory());
		searchItemInstance.setMiddleCategory(form.getMiddleCategory());
		searchItemInstance.setSmallCategory(form.getSmallCategory());
		searchItemInstance.setBrand(form.getBrand());
		searchItemInstance.setSearch(true);

		List<Category> bigCategoryList = categoryService.showBigCategory();
		model.addAttribute("bigCategoryList", bigCategoryList);
		return "list";
	}

	/**
	 * デフォルトのItemlist表示に遷移する.
	 * 
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model, Integer page) {
		List<ItemCategory> itemList;
		SearchItem searchItemInstance = SearchItem.getSearchItemForm();

		if (page != null) {
			if (page == 1) {
				PageCount.setPageCountPlusOne();
			} else {
				PageCount.setPageCountMinusOne();
			}
			if (searchItemInstance.isSearch()) {
				ItemSearchForm form = new ItemSearchForm();
				form.setName(searchItemInstance.getName());
				form.setBrand(searchItemInstance.getBrand());
				form.setBigCategory(searchItemInstance.getBigCategory());
				form.setMiddleCategory(searchItemInstance.getMiddleCategory());
				form.setSmallCategory(searchItemInstance.getSmallCategory());
				itemList = itemService.findBySearchForm(form, PageCount.getPageCount());

			} else {
				itemList = itemService.findList(PageCount.getPageCount());
			}
		} else {
			SearchItem.resetItemSearchForm();
			itemList = itemService.findList(PageCount.getPageCount());
		}
		model.addAttribute("itemList", itemList);

		List<Category> bigCategoryList = categoryService.showBigCategory();
		model.addAttribute("bigCategoryList", bigCategoryList);

		return "list";
	}

	/**
	 * 指定されたItemの詳細画面に遷移する.
	 * 
	 * @param itemId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/detail")
	public String detail(Integer itemId, Model model) throws Exception {
		ItemCategory detail = itemService.searchDetail(itemId);
		model.addAttribute("detail", detail);
		Condition condition = ItemConstant.ConditionSwitch(detail.getCondition());
		model.addAttribute("condition", condition);
		return "detail";
	}

	/**
	 * Item追加画面に遷移する.
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/toAdd")
	public String toAdd(Model model, ItemAddForm form) {

		List<Category> bigCategoryList = categoryService.showBigCategory();
		model.addAttribute("bigCategoryList", bigCategoryList);

		return "add";
	}

	/**
	 * Itemを登録する.
	 * 
	 * @param form
	 * @return
	 */
	@PostMapping("/add")
	public String add(@Validated ItemAddForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("form", form);
			List<Category> bigCategoryList = categoryService.showBigCategory();
			model.addAttribute("bigCategoryList", bigCategoryList);
			return "add";
		}
		System.out.println("result.hasError()を通過した。");
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
		System.out.println("リダイレクトの直前");
		return "redirect:/item/list";
	}

	/**
	 * 大カテゴリをプルダウンで選択時に中カテゴリを取得するAjax通信内でのメソッド.
	 * 
	 * @param bigCategory
	 * @return
	 */
	@ResponseBody
	@PostMapping("/middle")
	public List<Category> middle(String bigCategory) {
		System.out.println("/middleに来ました。");
		List<Category> middleList = new ArrayList<>();
		Integer bigCategoryId = categoryService.findId(bigCategory);
		List<Category> middleCategoryList = categoryService.showMiddleCategory();
//		for (Category middle : middleCategoryList) {
//			if (middle.getParent() == bigCategoryId) {
//				middleList.add(middle);
//			}
//		}
//		middleCategoryList.forEach(middle -> {
//			if (middle.getParent() == bigCategoryId)
//				middleList.add(middle);
//		});
		
		middleCategoryList.stream().filter(middle -> middle.getParent() == bigCategoryId).forEach(middle ->middleList.add(middle));
		return middleList;
	}

	/**
	 * 中カテゴリをプルダウンで選択時に小カテゴリを取得するAjax通信内でのメソッド.
	 * 
	 * @param middleCategory
	 * @return
	 */
	@ResponseBody
	@PostMapping("/small")
	public List<Category> small(String middleCategory) {
		List<Category> smallList = new ArrayList<>();
		Integer MC = categoryService.findId(middleCategory);
		List<Category> smallCategoryList = categoryService.showSmallCategory();
//		for (Category small : smallCategoryList) {
//			if (small.getParent() == MC) {
//				smallList.add(small);
//			}
//		}

//		smallCategoryList.forEach(small -> {
//			if (small.getParent() == MC)
//				smallList.add(small);
//		});
		
		smallCategoryList.stream().filter(small -> small.getParent() == MC).forEach(small -> smallList.add(small));
		return smallList;
	}

	/**
	 * Itemの編集画面へ遷移する.
	 * 
	 * @param itemId
	 * @param model
	 * @return
	 */
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

	/**
	 * 入力されたItem情報に更新する.
	 * 
	 * @param form
	 * @param model
	 * @return
	 */
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

}
