package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Item;
import com.example.demo.domain.ItemCategory;

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

	@Autowired
	private static final ResultSetExtractor<List<ItemCategory>> ITEM_RESULTSET_EXTRACTOR = (rs) -> {
		List<ItemCategory> itemList = new ArrayList<>();
		ItemCategory item = null;

		long beforeItemCategoryId = 0;

		while (rs.next()) {
			if (beforeItemCategoryId != rs.getInt("i_id")) {
				item = new ItemCategory();
				item.setId(rs.getInt("i_id"));
				item.setName(rs.getString("i_name"));
				item.setCondition(rs.getInt("i_condition"));
				item.setNameAll(rs.getString("c_name_all"));
				item.setBrand(rs.getString("i_brand"));
				item.setPrice(Double.parseDouble(rs.getString("i_price")));
				item.setShipping(rs.getInt("i_shipping"));
				item.setDescription(rs.getString("i_description"));
				beforeItemCategoryId = rs.getInt("i_id");
				itemList.add(item);
			}
		}
		return itemList;
	};

	public void insert(Item item) {

		String sql = "INSERT INTO items (name,condition,category,brand,price,shipping,description) VALUES (:name,:condition,:category,:brand,:price,:shipping,:description);";

		SqlParameterSource param = new BeanPropertySqlParameterSource(item);

		template.update(sql, param);
	}

	/**
	 * @param itemName
	 * @param allCategory
	 * @param brand
	 * @param startPage
	 * @param endPage
	 * @return
	 */
	public List<Item> findAllSelection(String itemName, Integer allCategory, String brand, Integer startPage,
			Integer endPage) {

		String sql = "SELECT id,name,condition,category,brand,price,shipping,description FROM items WHERE name = :itemName AND category = :allCategory AND brand = :brand AND ORDER BY name;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("itemName", itemName)
				.addValue("allCategory", allCategory).addValue("brand", brand).addValue("startPage", startPage)
				.addValue("endPage", endPage);

		return template.query(sql, param, ITEM_LIST_ROW_MAPPER);
	}

	public List<ItemCategory> findList(Integer pageCount) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.id i_id,i.name i_name,i.condition i_condition,c.name_all c_name_all,i.brand i_brand,i.price i_price,i.shipping i_shipping,i.description i_description FROM items i JOIN Category c ON i.category = c.id ORDER BY i.name ");

		if(pageCount==0) {
			sql.append(" LIMIT 30");
		}else {
			pageCount = pageCount*30;
			sql.append(" LIMIT 30 OFFSET "+pageCount);
		}
		sql.append(" ;");
		return template.query(sql.toString(), ITEM_RESULTSET_EXTRACTOR);
	}

	public ItemCategory searchDetail(Integer itemId) {
		String sql = "SELECT i.id i_id,i.name i_name,i.condition i_condition,c.name_all c_name_all,i.brand i_brand,i.price i_price,i.shipping i_shipping,i.description i_description FROM items i JOIN category c ON i.category = c.id WHERE i.id = :itemId;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("itemId", itemId);

		List<ItemCategory> itemList = template.query(sql, param, ITEM_RESULTSET_EXTRACTOR);

		return itemList.get(0);

	}

	public List<ItemCategory> findBySearchForm(String name, String brand, Integer categoryId,Integer page) {

		Integer andNum = 0;// 次ANDが必要なときは1にする
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT i.id i_id,i.name i_name,i.condition i_condition,c.name_all c_name_all,i.brand i_brand,i.price i_price,i.shipping i_shipping,i.description i_description FROM items i JOIN category c ON i.category = c.id ");

		if((!name.equals("")||!brand.equals(""))||categoryId!=null) {
			sql.append(" WHERE ");
		}
		
		if (!name.equals("")) {
			sql.append(" i.name LIKE '%" + name + "%' ");
//			sql.append(" name LIKE 're:named' ");
			andNum = 1;
		}

		if (categoryId != null) {
			if (andNum == 1) {
				sql.append(" AND ");
				andNum = 1;
			}
			sql.append(" i.category = " + categoryId + " ");
		}

		if (!brand.equals("")) {
			if (andNum == 1) {
				sql.append(" AND ");
				andNum = 1;
			}
			sql.append(" i.brand LIKE '%" + brand + "%' ");
		}
		
		sql.append(" ORDER BY i.name ");
		
		if(page==0) {
			sql.append(" LIMIT 30");
		}else {
			page = page*30;
			sql.append(" LIMIT 30 OFFSET "+page);
		}

		sql.append(" ;");

		List<ItemCategory> itemList = template.query(sql.toString(), ITEM_RESULTSET_EXTRACTOR);
		return itemList;
	}
	
	public void update(Item item) {
		System.out.println("item = "+ item);
		System.out.println("update文の前のitemのid　="+item.getId());
		
		String sql = "UPDATE items SET name = :name,condition = :condition,category = :category,brand = :brand,price = :price,shipping = :shipping,description =:description WHERE id = :id;";
		
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		template.update(sql, param);
		System.out.println("update完了したよん");
	}
}
	// 検索フォームから検索する用のメソッドもつくるorResultSetに置き換える。
