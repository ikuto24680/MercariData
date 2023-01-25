package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Item;
import com.example.demo.domain.ItemCategory;

/**
 * itemテーブルのリポジトリ.
 * @author pengi
 */
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
		item.setVersion(rs.getInt("version"));
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
				item.setCondition(rs.getString("co_condition"));
				item.setNameAll(rs.getString("ca_name_all"));
				item.setBrand(rs.getString("i_brand"));
				item.setPrice(Double.parseDouble(rs.getString("i_price")));
				item.setShipping(rs.getInt("i_shipping"));
				item.setDescription(rs.getString("d_description"));
				item.setVersion(rs.getInt("i_version"));
				beforeItemCategoryId = rs.getInt("i_id");
				itemList.add(item);
			}
		}
		return itemList;
	};

	/**
	 * Itemを１件登録する.
	 * @param item
	 */
	@Lock(LockMode.PESSIMISTIC_READ)
	public void insert(Item item) {

		String sql = "INSERT INTO items (name,condition,category,brand,price,shipping,description,version) VALUES (:name,:condition,:category,:brand,:price,:shipping,:description,:version);";

		SqlParameterSource param = new BeanPropertySqlParameterSource(item);

		template.update(sql, param);
	}

	/**
	 * 検索Formに入力されたものから検索し、該当のリストを返すメソッド.
	 * @param itemName　商品名
	 * @param allCategory　カテゴリ
	 * @param brand　ブランド
	 * @param startPage　最初のページ
	 * @param endPage　終わりのページ
	 * @return
	 */
	public List<Item> findAllSelection(String itemName, Integer allCategory, String brand, Integer startPage,
			Integer endPage) {

		String sql = "SELECT id,name,condition,category,brand,price,shipping,description,version FROM items WHERE name = :itemName AND category = :allCategory AND brand = :brand AND ORDER BY name;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("itemName", itemName)
				.addValue("allCategory", allCategory).addValue("brand", brand).addValue("startPage", startPage)
				.addValue("endPage", endPage);

		return template.query(sql, param, ITEM_LIST_ROW_MAPPER);
	}

	/**
	 * 選択されたページのリストを返すメソッド.
	 * @param pageCount
	 * @return
	 */
	public List<ItemCategory> findList(Integer pageCount) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.id i_id,i.name i_name,co.condition co_condition,ca.name_all ca_name_all,i.brand i_brand,i.price i_price,i.shipping i_shipping,d.description d_description,i.version i_version FROM items i JOIN Category ca ON i.category = ca.id JOIN Condition co ON i.condition = co.id JOIN description d ON i.description = d.id ORDER BY i.name ");

		if(pageCount==0) {
			sql.append(" LIMIT 30");
		}else {
			pageCount = pageCount*30;
			sql.append(" LIMIT 30 OFFSET "+pageCount);
		}
		sql.append(" ;");
		return template.query(sql.toString(), ITEM_RESULTSET_EXTRACTOR);
	}

	/**
	 * 選択された商品のIdからItemを１件返すメソッド.
	 * @param itemId
	 * @return
	 */
	public ItemCategory searchDetail(Integer itemId) {
		String sql = "SELECT i.id i_id,i.name i_name,co.condition co_condition,ca.name_all ca_name_all,i.brand i_brand,i.price i_price,i.shipping i_shipping,d.description d_description,i.version i_version FROM items i JOIN category ca ON i.category = ca.id JOIN Condition co ON i.condition = co.id JOIN description d ON i.description = d.id WHERE i.id = :itemId;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("itemId", itemId);

		List<ItemCategory> itemList = template.query(sql, param, ITEM_RESULTSET_EXTRACTOR);

		return itemList.get(0);

	}

	/**
	 * 検索Formに入力されたものから検索し、該当のリストを返すメソッド.
	 * @param name
	 * @param brand
	 * @param categoryId
	 * @param page
	 * @return
	 */
	public List<ItemCategory> findBySearchForm(String name, String brand, Integer categoryId,Integer page) {

		Integer andNum = 0;// 次ANDが必要なときは1にする
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT i.id i_id,i.name i_name,co.condition co_condition,ca.name_all ca_name_all,i.brand i_brand,i.price i_price,i.shipping i_shipping,d.description d_description,i.version i_version FROM items i JOIN category ca ON i.category = ca.id JOIN Condition co ON i.condition = co.id JOIN description d ON i.description = d.id ");

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
			sql.append(" ca.category = " + categoryId + " ");
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
	
	/**
	 * itemを更新するメソッド.
	 * @param item
	 */
	public void update(Item item) {
		String sql = "UPDATE items SET name = :name,condition = :condition,category = :category,brand = :brand,price = :price,shipping = :shipping,description =:description ,version = (:version + 1) WHERE id = :id AND version = :version;";
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		template.update(sql, param);
	}
}