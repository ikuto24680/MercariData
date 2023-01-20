package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Category;
import com.example.demo.interfaces.CategoryRepositoryInterface;

@Repository
public class CategoryRepository implements CategoryRepositoryInterface {

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

	@Override
	public void insert(Category category) {

		String sql = "INSERT INTO category (parent,name,name_all) VALUES (:parent, :name, :nameAll);";

		SqlParameterSource param = new BeanPropertySqlParameterSource(category);

		template.update(sql, param);
	}

	@Override
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

	public void showResult() {
		System.out.println("trueCount = " + this.trueCount);
		System.out.println("falseCount = " + this.falseCount);
	}

	@Override
	public List<Category> showCategory() {
		String sql = "SELECT id,parent,name,name_all FROM category ;";

		List<Category> categoryList = template.query(sql, CATEGORY_ROW_MAPPER);

		return categoryList;
	}

	@Override
	public List<Category> showBigCategory() {
		String sql = "SELECT id,parent,name,name_all FROM category WHERE parent IS NULL AND name_all IS NULL;";

		List<Category> categoryList = template.query(sql, CATEGORY_ROW_MAPPER);

		return categoryList;
	}

	@Override
	public List<Category> showMiddleCategory() {
		String sql = "SELECT id,parent,name,name_all FROM category WHERE parent IS NOT NULL AND name_all IS NULL;";

		List<Category> categoryList = template.query(sql, CATEGORY_ROW_MAPPER);

		return categoryList;
	}

	@Override
	public List<Category> showSmallCategory() {
		String sql = "SELECT id,parent,name,name_all FROM category WHERE parent IS NOT NULL AND name_all IS NOT NULL;";

		List<Category> categoryList = template.query(sql, CATEGORY_ROW_MAPPER);

		return categoryList;
	}

	@Override
	public Category findCategory(String middleCategoryId) {
		String sql = "SELECT id,name FROM category WHERE parent IS NOT NULL AND name_all IS NULL AND id = :middleCategoryId;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("middleCategoryId", middleCategoryId);

		List<Category> categoryList = template.query(sql, param, FINDAID_ROWMAPPER);

		try {
			categoryList.get(0);
			return categoryList.get(0);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Integer findMiddleId(String middleCategoryName) {
		String sql = "SELECT id,name FROM category WHERE parent IS NOT NULL AND name_all IS NULL AND name = :middleCategoryName;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("middleCategoryName", middleCategoryName);

		List<Category> categoryList = template.query(sql, param, FINDAID_ROWMAPPER);

		try {
			categoryList.get(0);
			return categoryList.get(0).getId();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Integer findBigId(String bigCategoryName) {
		String sql = "SELECT id,name FROM category WHERE parent IS NULL AND name_all IS NULL AND name = :bigCategoryName;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("bigCategoryName", bigCategoryName);

		List<Category> categoryList = template.query(sql, param, FINDAID_ROWMAPPER);

		try {
			categoryList.get(0);
			return categoryList.get(0).getId();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Integer findSmallId(String smallCategoryName) {
		String sql = "SELECT id,name FROM category WHERE parent IS NOT NULL AND name_all IS NOT NULL AND name = :smallCategoryName;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("smallCategoryName", smallCategoryName);

		List<Category> categoryList = template.query(sql, param, FINDAID_ROWMAPPER);

		try {
			categoryList.get(0);
			return categoryList.get(0).getId();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Category findCategory(Integer categoryId) {

		String sql = "SELECT id,parent,name,name_all FROM category WHERE id = :categoryId;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("categoryId", categoryId);

		List<Category> categoryList = template.query(sql, param, FINDAID_ROWMAPPER);
		try {
			categoryList.get(0);
			return categoryList.get(0);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Category findCategoryByNameAll(String nameAll) {

		String sql = "SELECT id,name_all FROM category WHERE name_all = :nameAll;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("nameAll", nameAll);

		List<Category> categoryList = template.query(sql, param, FIND_ALLNAME_ROWMAPPER);
		try {
			categoryList.get(0);
			return categoryList.get(0);
		} catch (Exception e) {
			return null;
		}
	}
}
