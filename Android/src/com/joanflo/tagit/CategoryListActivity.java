package com.joanflo.tagit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.joanflo.adapters.CategoryListAdapter;
import com.joanflo.adapters.CategoryListItem;
import com.joanflo.controllers.CategoriesController;
import com.joanflo.models.Category;
import com.joanflo.utils.LocalStorage;
import com.joanflo.utils.SearchUtils;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class CategoryListActivity extends BaseActivity {

	
	private List<Category> categories;
	private List<CategoryListItem> categoryItems;
	private int currentPosition = 0;
	private int requestsNumber;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_categorylist);
		
        
        // update selected item and title, then close the drawer
        Bundle bundle = getIntent().getExtras();
        int pos = bundle.getInt("drawerPosition");
        if (pos != 0) {
        	super.updateSelected(pos);
        }
        
        super.showProgressBar(true);
        // call web service
        int idCategory = bundle.getInt("idCategory");
        CategoriesController controller = new CategoriesController(this);
        if (idCategory == 0) {
        	// search by level
        	controller.getCategories(0);
        } else {
        	// search by category id
        	controller.getSubategories(idCategory);
        }
	}
	
	

	@Override
	protected void onResume() {
		super.onResume();
		
		// uncheck elements
		ListView categoryList = (ListView) findViewById(R.id.listView_category);
		categoryList.setItemChecked(currentPosition, false);
	}
	
	
	
	public void categoriesReceived(List<Category> categories) {
		this.categories = categories;
		requestsNumber = categories.size();

		LocalStorage storage = LocalStorage.getInstance();
		Category currentCategory = storage.getCurrentCategory();
		if (currentCategory == null) {
			currentCategory = new Category(0, null, new ArrayList<Category>(), -1, "");
			storage.setCurrentCategory(currentCategory);
		}
		currentCategory.setCategories(categories);
        CategoriesController controller = new CategoriesController(this);
		// call web service
		for (Category category : categories) {
			controller.getSubategoriesCount(category.getIdCategory());
		}
	}
	
	
	
	public void countReceived(int idCategory, int count, boolean productCount) {
		Category currentCategory = LocalStorage.getInstance().getCurrentCategory();
		Category subcategory = SearchUtils.searchCategoryById(idCategory, currentCategory.getCategories());
		subcategory.setItemsNumber(count, productCount);
		
		// check if it's the last request
		checkLastRequest();
	}
	
	
	
	private synchronized void checkLastRequest() {
		requestsNumber--;
		if (requestsNumber == 0) {
			// last request
			Category currentCategory = LocalStorage.getInstance().getCurrentCategory();
			prepareList(currentCategory);
			super.showProgressBar(false);
		}
	}
	
	
	
	private void prepareList(Category currentCategory) {
		// get current level
		int currentLevel = currentCategory.getLevel();
		if (currentLevel != -1) {
			setTitle(currentCategory.getName());
			currentLevel++;
		}
		
		// creating category list items
		Iterator<Category> it = categories.iterator();
		categoryItems = new ArrayList<CategoryListItem>();
        while(it.hasNext()) {
        	Category category = (Category) it.next();
        	CategoryListItem categoryItem = new CategoryListItem(this, category.getIdCategory(), category.getName(), category.getItemsNumber());
        	categoryItems.add(categoryItem);
        }
        
        // setting the category list adapter
        CategoryListAdapter adapter = new CategoryListAdapter(getApplicationContext(), categoryItems);
        ListView categoryList = (ListView) findViewById(R.id.listView_category);
        categoryList.setAdapter(adapter);
        
        // setting the category click listener
        categoryList.setOnItemClickListener(new CategoryClickListener());
	}



	public void onClickButton(View v) {
		super.onClickButton(v);
    }
	
	
	
	private class CategoryClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// go to category or product list
			Intent i;
			currentPosition = position;
			CategoryListItem categoryItem = categoryItems.get(position);
			Category category = SearchUtils.searchCategoryById(categoryItem.getIdCategory(), categories);
			LocalStorage.getInstance().setCurrentCategory(category);
			if (!category.isLeafNode()) {
				i = new Intent(getBaseContext(), CategoryListActivity.class);
			} else {
				i = new Intent(getBaseContext(), ProductListActivity.class);
			}
			i.putExtra("idCategory", categoryItem.getIdCategory());
			startActivity(i);
		}
		
	}
	
	
}
