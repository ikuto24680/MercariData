package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Item;

@Repository
public class ItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	@Autowired
	private static final RowMapper<Item> ITEM_LIST_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setCondition(rs.getInt("condition"));
		item.setCategory(rs.getInt("category"));
		item.setBrand(rs.getString("brand"));
		item.setShipping(rs.getInt("shipping"));
		item.setDescription(rs.getString("description"));
		return item;
	};

	public List<Item> findAll() {

		String sql = "SELECT id,name,condition,category,brand,price,shipping,description FROM items ;";
		
		return template.query(sql, ITEM_LIST_ROW_MAPPER);
	}

	public void insert(Item item) {

		String sql = "INSERT INTO items (name,condition,category,brand,price,shipping,description) VALUES (:name,:condition,:category,:brand,:price,:shipping,:description);";

		SqlParameterSource param = new BeanPropertySqlParameterSource(item);

		template.update(sql, param);
		System.out.println("itemのiNSERTが完了");
	}
	
	/**
	 * 
	 * @param allCategory
	 * @param brand
	 * @return
	 */
//	public List<Item> findItemListByBrandCategoryPages(Integer allCategory, String brand,Integer startPage,Integer endPage){
//		
//		String sql = "SELECT id,name,condition,category,brand,price,shipping,description FROM items WHERE category = :allCategory AND brand = :brand AND ORDER BY name;";
//		
//		
//	}
	
	/**
	 * @param itemName
	 * @param allCategory
	 * @param brand
	 * @param startPage
	 * @param endPage
	 * @return
	 */
	public List<Item> findAllSelection(String itemName,Integer allCategory, String brand,Integer startPage,Integer endPage) {

		String sql = "SELECT id,name,condition,category,brand,price,shipping,description FROM items WHERE name = :itemName AND category = :allCategory AND brand = :brand AND ORDER BY name;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("itemName",itemName).addValue("allCategory", allCategory).addValue("brand", brand).addValue("startPage", startPage).addValue("endPage", endPage);
		
		return template.query(sql, param, ITEM_LIST_ROW_MAPPER);
	}
}
