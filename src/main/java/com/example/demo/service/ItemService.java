package com.example.demo.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.example.demo.domain.Item;
import com.example.demo.domain.ItemCategory;
import com.example.demo.form.ItemEditForm;
import com.example.demo.form.ItemSearchForm;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;

/**
 * ItemテーブルのServiceクラス.
 * 
 * @author pengi
 */
@Service
public class ItemService {

	@Autowired
	private ItemRepository itemrepository;

	@Autowired
	private CategoryRepository categoryRepository;

	/**
	 * Itemを１件登録する.
	 * 
	 * @param item
	 */
	public void addItem(Item item) {
		itemrepository.insert(item);
	}

	/**
	 * 指定されたページのリストを返す.
	 * 
	 * @param pageCount
	 * @return
	 */
	public List<ItemCategory> findList(Integer pageCount) {
		return itemrepository.findList(pageCount);
	}

	/**
	 * 指定されたidのItemを返す
	 * 
	 * @param itemId
	 * @return
	 */
	public ItemCategory searchDetail(Integer itemId) {
		return itemrepository.searchDetail(itemId);
	}

	/**
	 * 検索されたリストのページング機能.
	 * 
	 * @param form
	 * @param page
	 * @return
	 */
	public List<ItemCategory> findBySearchForm(ItemSearchForm form, Integer page) {

		String name = form.getName();
		String brand = form.getBrand();
		Integer categoryId = categoryRepository.findSmallId(selectedCategory(form));

		return itemrepository.findBySearchForm(name, brand, categoryId, page);
	}

	/**
	 * 入力された箇所含めItemゴト更新する.
	 * 
	 * @param item
	 */
	@Transactional
	public void edit(ItemEditForm form,Model model) {
		
		Item item = new Item();
		BeanUtils.copyProperties(form, item);
		item.setCategory((categoryRepository.findCategoryByNameAll(
				form.getBigCategory() + "/" + form.getMiddleCategory() + "/" + form.getSmallCategory())).getId());
		itemrepository.update(item);
		ItemCategory detail = itemrepository.searchDetail(form.getId());
		if((form.getVersion() +1) != detail.getVersion()) {
			throw new IllegalStateException("Data is Stale. Please Retry");
		}
		model.addAttribute("detail", detail);
		
	}

	/**
	 * ItemSearchFormから
	 * @param form
	 * @return
	 */
	public static String selectedCategory(ItemSearchForm form) {
		String categoryName = null;
		if ((form.getBigCategory() == null && form.getMiddleCategory() == null) && form.getSmallCategory() == null) {

		} else if (form.getBigCategory() != null && form.getMiddleCategory() == null
				&& form.getSmallCategory() == null) {
			categoryName = form.getBigCategory();
		} else if ((form.getBigCategory() != null && form.getMiddleCategory() != null)
				&& form.getSmallCategory() == null) {
			categoryName = form.getMiddleCategory();
		} else if ((form.getBigCategory() != null && form.getMiddleCategory() != null)
				&& form.getSmallCategory() != null) {
			categoryName = form.getSmallCategory();
		}
		return categoryName;
	}
}
