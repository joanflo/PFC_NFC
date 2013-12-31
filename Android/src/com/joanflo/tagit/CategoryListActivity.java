package com.joanflo.tagit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.joanflo.adapters.CategoryListAdapter;
import com.joanflo.adapters.CategoryListItem;
import com.joanflo.models.Brand;
import com.joanflo.models.Category;
import com.joanflo.models.Product;
import com.joanflo.utils.SearchUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;


public class CategoryListActivity extends BaseActivity {

	
	private List<Category> categories;
	private List<CategoryListItem> categoryItems;
	private Category currentCategory;
	private int currentPosition = 0;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        // update the main content by replacing view
		LayoutInflater factory = LayoutInflater.from(this);
		View activityView = factory.inflate(R.layout.activity_categorylist, null);
		// inflate activity layout
        FrameLayout viewContainer = (FrameLayout) findViewById(R.id.frame_container);
        viewContainer.addView(activityView);
        
        // update selected item and title, then close the drawer
        Bundle bundle = getIntent().getExtras();
        int pos = bundle.getInt("drawerPosition");
        if (pos != 0) {
        	super.updateSelected(pos);
        }
        
        int idCategory = bundle.getInt("idCategory");
        prepareList(idCategory);
	}
	
	

	@Override
	protected void onResume() {
		super.onResume();
		
		// uncheck elements
		ListView categoryList = (ListView) findViewById(R.id.listView_category);
		categoryList.setItemChecked(currentPosition, false);
	}
	
	
	
	private void prepareList(int idCategory) {
		loadCategories();
		
		currentCategory = SearchUtils.searchCategoryById(idCategory, categories);
		
		int currentLevel;
		Iterator<?> it;
		categoryItems = new ArrayList<CategoryListItem>();
		if (currentCategory == null) {
			currentLevel = 0;
			it = categories.iterator();
		} else {
			setTitle(currentCategory.getName());
			currentLevel = currentCategory.getLevel() + 1;
			it = currentCategory.getCategories().iterator();
		}
		
		// creating category list items
        while(it.hasNext()) {
        	Category category = (Category) it.next();
        	if (category.getLevel() == currentLevel) {
	        	CategoryListItem categoryItem = new CategoryListItem(this, category.getIdCategory(), category.getName(), category.getItemsNumber());
	        	categoryItems.add(categoryItem);
        	}
        }
        
        // setting the category list adapter
        CategoryListAdapter adapter = new CategoryListAdapter(getApplicationContext(), categoryItems);
        ListView categoryList = (ListView) findViewById(R.id.listView_category);
        categoryList.setAdapter(adapter);
        
        // setting the category click listener
        categoryList.setOnItemClickListener(new CategoryClickListener());
	}



	private void loadCategories() {
		categories = new ArrayList<Category>();
		
		Category c1 = new Category(1, null, new ArrayList<Category>(), 0, "Male");
		categories.add(c1);
		Category c2 = new Category(2, null, new ArrayList<Category>(), 0, "Female");
		categories.add(c2);
		Category c3 = new Category(3, null, new ArrayList<Category>(), 0, "Kids");
		categories.add(c3);
		Category c4 = new Category(4, null, new ArrayList<Category>(), 1, "Trousers");
		categories.add(c4);
		Category c5 = new Category(5, new ArrayList<Product>(), null, 1, "Shoes");
		categories.add(c5);
		Category c6 = new Category(6, new ArrayList<Product>(), null, 1, "T-Shirts");
		categories.add(c6);
		Category c7 = new Category(7, new ArrayList<Product>(), null, 1, "Handbag");
		categories.add(c7);
		Category c8 = new Category(8, new ArrayList<Product>(), null, 1, "Skirts");
		categories.add(c8);
		Category c9 = new Category(9, new ArrayList<Product>(), null, 1, "Dress");
		categories.add(c9);
		Category c10 = new Category(10, new ArrayList<Product>(), null, 1, "Assets");
		categories.add(c10);
		Category c11 = new Category(11, new ArrayList<Product>(), null, 2, "Jeans");
		categories.add(c11);
		Category c12 = new Category(12, new ArrayList<Product>(), null, 2, "Fishers");
		categories.add(c12);
		
		List<Category> ac1 = c1.getCategories();
		ac1.add(c4);
		ac1.add(c5);
		ac1.add(c6);
		ac1.add(c10);
		
		List<Category> ac2 = c2.getCategories();
		ac2.add(c4);
		ac2.add(c5);
		ac2.add(c6);
		ac2.add(c7);
		ac2.add(c8);
		ac2.add(c9);
		ac2.add(c10);
		
		List<Category> ac3 = c3.getCategories();
		ac3.add(c4);
		ac3.add(c5);
		ac3.add(c6);
		ac3.add(c8);
		ac3.add(c9);
		ac3.add(c10);
		
		List<Category> ac4 = c4.getCategories();
		ac4.add(c11);
		ac4.add(c12);
		
		
		Brand brand = new Brand("Vans", "", "", "", 0, 0);
		Product p1 = new Product(0, brand, null, "Camiseta verda", "", "", "");
		Product p2 = new Product(1, brand, null, "Vestit blau", "", "", "");
		Product p3 = new Product(2, brand, null, "Deportives blanques", "", "", "");
		Product p4 = new Product(3, brand, null, "Vaqueros slim fit", "", "", "");
		Product p5 = new Product(4, brand, null, "Calçons pana", "", "", "");
		Product p6 = new Product(5, brand, null, "Minifalda", "", "", "");
		Product p7 = new Product(6, brand, null, "Bolso 1", "", "", "");
		Product p8 = new Product(7, brand, null, "Tacons", "", "", "");
		Product p9 = new Product(8, brand, null, "Camiseta blanca", "", "", "");
		Product p10 = new Product(9, brand, null, "Camiseta blava", "", "", "");
		Product p11 = new Product(10, brand, null, "Camiseta negra", "", "", "");
		Product p12 = new Product(11, brand, null, "Collar", "", "", "");
		Product p13 = new Product(12, brand, null, "Pulsera", "", "", "");
		
		List<Product> ac5 = c5.getProducts(); // shoes
		ac5.add(p3);
		ac5.add(p8);
		
		List<Product> ac6 = c6.getProducts(); // T-Shirts
		ac6.add(p1);
		ac6.add(p9);
		ac6.add(p10);
		ac6.add(p11);
		
		List<Product> ac7 = c7.getProducts(); //Handbag
		ac7.add(p7);
		
		List<Product> ac8 = c8.getProducts(); //Skirts
		ac8.add(p6);
		
		List<Product> ac9 = c9.getProducts(); //Dress
		ac9.add(p2);
		
		List<Product> ac10 = c10.getProducts(); //Assets
		ac10.add(p12);
		ac10.add(p13);
		
		List<Product> ac11 = c11.getProducts(); //Jeans
		ac11.add(p4);
		
		List<Product> ac12 = c12.getProducts(); // Fishers
		ac12.add(p5);
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
