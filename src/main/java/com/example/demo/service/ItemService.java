package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Item;
import com.example.demo.domain.ItemCategory;
import com.example.demo.form.ItemSearchForm;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemrepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public List<Item> showList(ItemSearchForm form) {
		String name = form.getName();
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT id,name,condition,category,brand,price,shipping,description FROM items WHERE ");
		if (name != null) {
			sql.append(" name LIKE '%:name%' ");
		}

		List<Item> itemList = null;

		return itemList;
	}

	public void addItem(Item item) {
		itemrepository.insert(item);
	}

	public List<ItemCategory> findList(Integer pageCount) {
		return itemrepository.findList(pageCount);
	}

	public ItemCategory searchDetail(Integer itemId) {
		return itemrepository.searchDetail(itemId);
	}

	public List<ItemCategory> findBySearchForm(ItemSearchForm form, Integer page) {

		String name = form.getName();
		String brand = form.getBrand();
		Integer categoryId = 0;
		if ((form.getBigCategory() == null && form.getMiddleCategory() == null) && form.getSmallCategory() == null) {

		} else if (form.getBigCategory() != null && form.getMiddleCategory() == null
				&& form.getSmallCategory() == null) {
			categoryId = categoryRepository.findBigId(form.getBigCategory());
		} else if ((form.getBigCategory() != null && form.getMiddleCategory() != null)
				&& form.getSmallCategory() == null) {
			categoryId = categoryRepository.findMiddleId(form.getMiddleCategory());
		} else if ((form.getBigCategory() != null && form.getMiddleCategory() != null)
				&& form.getSmallCategory() != null) {
			categoryId = categoryRepository.findSmallId(form.getSmallCategory());
		}

		return itemrepository.findBySearchForm(name, brand, categoryId, page);
	}

	public void edit(Item item) {
		itemrepository.update(item);
	}

}
