package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.domain.Item;
import com.example.demo.form.ItemSearchForm;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemrepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public List<Item> showList(ItemSearchForm form, int pageCount) {
		String name = form.getName();
		String bigCategory = form.getBigCategory();
		String middleCategory = form.getMiddleCategory();
		String smallCategory = form.getSmallCategory();
		String brand = form.getBrand();
		String sql ="SELECT id,name,condition,category,brand,price,shipping,description FROM items WHERE ";

		List<Item> itemList = null;
		

		return itemList;
	}
	
	public void addItem(Item item) {
		itemrepository.insert(item);
	}
}
