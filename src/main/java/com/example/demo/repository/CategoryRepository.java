package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Category;

@Repository
public class CategoryRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	int trueCount = 0;
	int falseCount = 0;
	List<String> falseList;

	@Autowired
	private static final RowMapper<Category> CATEGORY_ROW_MAPPER = (rs, i) -> {

		Category category = new Category();
		category.setId(rs.getInt("id"));
		category.setParent(rs.getInt("parent"));
		category.setName(rs.getString("name"));
		category.setNameAll(rs.getString("name_all"));

		return category;
	};

	@Autowired
	private static final RowMapper<Category> FINDBY_CATEGORYNAME_ROW_MAPPER = (rs, i) -> {
		Category category = new Category();
		category.setId(rs.getInt("id"));
		category.setName(rs.getString("name"));
		return category;
	};

	@Autowired
	private static final RowMapper<Category> CATEGORY_ROW_MAPPER2 = (rs, i) -> {

		Category category = new Category();
		category.setId(rs.getInt("id"));
		category.setParent(rs.getInt("parent"));
		category.setName(rs.getString("name"));

		return category;
	};

	@Autowired
	private static final RowMapper<Category> FINDALLCATEGORYNAME_ROWMAPPER = (rs, i) -> {

		Category category = new Category();
		category.setName(rs.getString("name"));

		return category;
	};
	
	@Autowired
	private static final RowMapper<Category> FINDCATEGORYID_ROWMAPPER = (rs, i) -> {

		Category category = new Category();
		category.setId(rs.getInt("id"));

		return category;
	};

	@Autowired
	private static final RowMapper<Category> FINDAID_ROWMAPPER = (rs, i) -> {

		Category category = new Category();
		category.setId(rs.getInt("id"));
		category.setName(rs.getString("name"));
		return category;
	};

	@Autowired
	private static final RowMapper<Category> FIND_ALLNAME_ROWMAPPER = (rs, i) -> {

		Category category = new Category();
		category.setId(rs.getInt("id"));
		category.setNameAll(rs.getString("name_all"));
		return category;
	};

	public boolean findByCategoryName(String name) {

		String sql = "SELECT id,name FROM category WHERE name = :name ;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);

		List<Category> list = template.query(sql, param, FINDBY_CATEGORYNAME_ROW_MAPPER);
		try {
			list.get(0);
			System.out.println("WWWWWWWWWWWWWWWWWWWWW");

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean findByCategoryName2(String name) {

		String sql = "SELECT id,name FROM category WHERE name = :name ;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);

		List<Category> list = template.query(sql, param, FINDBY_CATEGORYNAME_ROW_MAPPER);
		List<String> FBCN2 = null;
		try {
			list.get(0);
			System.out.println("WWWWWWWWWWWWWWWWWWWWW");
			return true;
		} catch (Exception e) {
			FBCN2.add(name);
			return false;
		}
	}

	public List<Category> findByCategoryName3(String name) {

		String sql = "SELECT id,name FROM category WHERE name = :name ;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);

		List<Category> list = template.query(sql, param, FINDBY_CATEGORYNAME_ROW_MAPPER);

		return list;
	}

	public void insert(Category category) {

		String sql = "INSERT INTO category (parent,name,name_all) VALUES (:parent, :name, :nameAll);";

		SqlParameterSource param = new BeanPropertySqlParameterSource(category);

		template.update(sql, param);
	}

	public Integer findParentId(String name) {

		String sql = "SELECT id,parent, name FROM category WHERE name = :name ;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);

//		try {
		List<Category> categoryList = template.query(sql, param, CATEGORY_ROW_MAPPER2);
		return categoryList.get(0).getId();
//		}catch(Exception e) {
//			return null;
//		}
	}

	public List<Category> findAll() {

		String sql = "SELECT id,parent,name,name_all FROM category ;";

		List<Category> categoryList = template.query(sql, CATEGORY_ROW_MAPPER);

		return categoryList;
	}

	// distinctが使えるかどうかチェックするためのメソッド→無理
	public List<Category> findAllCategoryName() {
		String sql = "select distinct on (name) name from category;";

		List<Category> categoryList = template.query(sql, FINDALLCATEGORYNAME_ROWMAPPER);

		return categoryList;
	}

	public Integer findId(String name) {

		String sql = "SELECT id, name FROM category WHERE name = :name ;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);

		List<Category> categoryList = template.query(sql, param, FINDAID_ROWMAPPER);
		try {
			return categoryList.get(0).getId();
		} catch (Exception e) {
			return null;
		}
	}

	public boolean trueFalse0(String name) {

		String sql = "SELECT id, name FROM category WHERE name = :name AND parent IS NULL AND name_all IS NULL ;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);

		List<Category> categoryList = template.query(sql, param, FINDAID_ROWMAPPER);
		try {
			categoryList.get(0).getId();
			trueCount += 1;
			return true;
		} catch (Exception e) {
			falseCount += 1;
			return false;
		}
	}

	public boolean trueFalse1(String name) {

		String sql = "SELECT id, name FROM category WHERE name = :name AND parent IS NOT NULL AND name_all IS NULL;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);

		List<Category> categoryList = template.query(sql, param, FINDAID_ROWMAPPER);
		try {
			categoryList.get(0).getId();
			trueCount += 1;
			return true;
		} catch (Exception e) {
			falseCount += 1;
			return false;
		}
	}

	public boolean trueFalse2(String name) {

		String sql = "SELECT id, name FROM category WHERE name = :name AND parent IS NOT NULL AND name_all IS NOT NULL;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);

		List<Category> categoryList = template.query(sql, param, FINDAID_ROWMAPPER);
		try {
			categoryList.get(0).getId();
			trueCount += 1;
			return true;
		} catch (Exception e) {
			falseCount += 1;
			return false;
		}
	}

	public Category findName(String categoryName) {
		if (categoryName.equals("")) {
			System.out.println("categoryNameは" + categoryName + "です");
		}
		int NameCount = 0;
		NameCount += 1;
		System.out.println(NameCount + "|" + categoryName + "|");

		falseList = new ArrayList<>();

		String sql = "SELECT id, name_all FROM category WHERE name_all = :categoryName ;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("categoryName", categoryName);

		List<Category> categoryList = template.query(sql, param, FIND_ALLNAME_ROWMAPPER);
		try {
			categoryList.get(0);
			System.out.println("CategoryのfindNameでtrue");
			return categoryList.get(0);
		} catch (Exception e) {
			System.out.println("CategoryのfindNameでfalseでcatch");
			falseList.add(categoryName);
			return null;
		}
	}

	public void showResult() {
		System.out.println("trueCount = " + this.trueCount);
		System.out.println("falseCount = " + this.falseCount);
	}

	public void showFalseCatch() {
		int SFCcount = 0;
		for (String one : falseList) {
			SFCcount += 1;
			System.out.println(SFCcount + " : " + one);
		}
	}

	public List<Category> showCategory() {
		String sql = "SELECT id,parent,name,name_all FROM category ;";

		List<Category> categoryList = template.query(sql, CATEGORY_ROW_MAPPER);

		return categoryList;
	}

	public List<Category> showBigCategory() {
		String sql = "SELECT id,parent,name,name_all FROM category WHERE parent IS NULL AND name_all IS NULL;";

		List<Category> categoryList = template.query(sql, CATEGORY_ROW_MAPPER);

		return categoryList;
	}
	
	public List<Category> showMiddleCategory() {
		String sql = "SELECT id,parent,name,name_all FROM category WHERE parent IS NOT NULL AND name_all IS NULL;";

		List<Category> categoryList = template.query(sql, CATEGORY_ROW_MAPPER);

		return categoryList;
	}
	
	public List<Category> showSmallCategory() {
		String sql = "SELECT id,parent,name,name_all FROM category WHERE parent IS NOT NULL AND name_all IS NOT NULL;";

		List<Category> categoryList = template.query(sql, CATEGORY_ROW_MAPPER);

		return categoryList;
	}
	
	public Category findCategory(String middleCategoryId) {
		String sql = "SELECT id,name FROM category WHERE parent IS NOT NULL AND name_all IS NULL AND id = :middleCategoryId;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("middleCategoryId", middleCategoryId);

		List<Category> categoryList = template.query(sql,param, FINDAID_ROWMAPPER);

		try {
			categoryList.get(0);
			return categoryList.get(0);
		}catch(Exception e) {
			return null;
		}
	}
	
	public Integer findMiddleId(String middleCategoryName) {
		String sql = "SELECT id,name FROM category WHERE parent IS NOT NULL AND name_all IS NULL AND name = :middleCategoryName;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("middleCategoryName", middleCategoryName);

		List<Category> categoryList = template.query(sql,param, FINDAID_ROWMAPPER);

		try {
			categoryList.get(0);
			return categoryList.get(0).getId();
		}catch(Exception e) {
			return null;
		}
	}
	
	public Integer findBigId(String bigCategoryName) {
		String sql = "SELECT id,name FROM category WHERE parent IS NULL AND name_all IS NULL AND name = :bigCategoryName;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("bigCategoryName", bigCategoryName);

		List<Category> categoryList = template.query(sql,param, FINDAID_ROWMAPPER);

		try {
			categoryList.get(0);
			return categoryList.get(0).getId();
		}catch(Exception e) {
			return null;
		}
	}

	public Integer findSmallId(String smallCategoryName) {
		String sql = "SELECT id,name FROM category WHERE parent IS NOT NULL AND name_all IS NOT NULL AND name = :smallCategoryName;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("smallCategoryName", smallCategoryName);

		List<Category> categoryList = template.query(sql,param, FINDAID_ROWMAPPER);

		try {
			categoryList.get(0);
			return categoryList.get(0).getId();
		}catch(Exception e) {
			return null;
		}
	}
}
