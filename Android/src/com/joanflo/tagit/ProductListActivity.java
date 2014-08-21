package com.joanflo.tagit;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import com.joanflo.adapters.ProductListAdapter;
import com.joanflo.adapters.ProductListItem;
import com.joanflo.adapters.SpinnerNavAdapter;
import com.joanflo.adapters.SpinnerNavItem;
import com.joanflo.models.Brand;
import com.joanflo.models.Category;
import com.joanflo.models.City;
import com.joanflo.models.Country;
import com.joanflo.models.Language;
import com.joanflo.models.Product;
import com.joanflo.models.ProductImage;
import com.joanflo.models.Region;
import com.joanflo.models.Review;
import com.joanflo.models.Tax;
import com.joanflo.models.User;
import com.joanflo.utils.SearchUtils;
import android.app.SearchManager;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;


public class ProductListActivity extends BaseActivity implements ActionBar.OnNavigationListener {

	
	private List<Product> products;
	private User user;
	private List<ProductListItem> productItems;
	private int currentPosition = 0;
	
    private ArrayList<SpinnerNavItem> navSpinner;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_productlist);
		

	    // Get the intent and verify the action
	    Intent intent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	    	// Search intent
	    	String query = intent.getStringExtra(SearchManager.QUERY);
	      	searchProducts(query);
	    } else {
	    	// we come from category list
	    	Bundle bundle = getIntent().getExtras();
	    	int idCategory = bundle.getInt("idCategory");
	    	prepareList(idCategory);
	    }
	    
	    prepareActionBar();
	}
	
	
	
	private void prepareActionBar() {
		ActionBar actionBar = getActionBar();
		
		// Hide the action bar title
        actionBar.setDisplayShowTitleEnabled(false);
        
        // Enabling Spinner dropdown navigation
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        
        // Spinner title navigation data
        navSpinner = new ArrayList<SpinnerNavItem>();
        String[] navSpinnerTitles = getResources().getStringArray(R.array.nav_spinner_items);
        TypedArray navSpinnerIcons = getResources().obtainTypedArray(R.array.nav_spinner_icons);
        for (int i = 0; i < navSpinnerTitles.length; i++) {
        	navSpinner.add(new SpinnerNavItem(navSpinnerTitles[i], navSpinnerIcons.getResourceId(i, -1)));
        }
        navSpinnerIcons.recycle();
        
        // assigning the spinner navigation
        SpinnerNavAdapter adapter = new SpinnerNavAdapter(getApplicationContext(), navSpinner);
        actionBar.setListNavigationCallbacks(adapter, this);
	}
	
	

	private void searchProducts(String query) {
		
	}



	@Override
	protected void onResume() {
		super.onResume();
		
		// uncheck elements
        ListView productList = (ListView) findViewById(R.id.listView_product);
		productList.setItemChecked(currentPosition, false);
	}
	
	
	
	private void prepareList(int idCategory) {
		List<Category> categories = new ArrayList<Category>();
		loadCategories(categories);

		// get products list
		Category currentCategory = SearchUtils.searchCategoryById(idCategory, categories);
		products = currentCategory.getProducts();
	
		// creating product list items
		Iterator<Product> it = products.iterator();
		productItems = new ArrayList<ProductListItem>();
        while(it.hasNext()) {
        	Product product = (Product) it.next();
        	
        	ProductImage front = SearchUtils.searchFrontImage(product);
        	URL url = null;
        	CharSequence desc = "";
        	if (front != null) { // image not found
        		url = front.getUrl();
        		desc = front.getDescription();
        	}
        	CharSequence pName = product.getName();
        	CharSequence bName = product.getBrand().getBrandName();
        	CharSequence cName = currentCategory.getName();
        	Tax tax = product.searchTax(user.getCity().getRegion().getCountry());
        	DecimalFormat df = new DecimalFormat("0.00");
        	CharSequence price = df.format(product.calculatePrice(tax));
        	CharSequence coin = String.valueOf(tax.getCountry().getCoin());
        	double averageRate = product.calculateAverageRating();
        	CharSequence rate;
        	if (averageRate == -1) {
        		rate = "-";
        	} else {
        		df = new DecimalFormat("0.0");
        		rate = df.format(averageRate);
        	}
        	
        	ProductListItem pItem = new ProductListItem(this, url, desc, pName, bName, cName, price, coin, rate);
        	productItems.add(pItem);
        }
        
        // setting the product list listener
        ListView productList = (ListView) findViewById(R.id.listView_product);
        productList.setOnItemClickListener(new ProductClickListener());
	}



	private void loadCategories(List<Category> categories) {
		Country espanya = new Country("Espanya", null, 34, Country.EURO);
		Region balears = new Region("Balears", null, espanya);
		City palma = new City("Palma", null, balears);
		Language catala = new Language("Català");
		user = new User("joan@uib.cat", palma, catala, "Joan_flo", "Joan", "Florit Gomila", 23, "password", "686922414", "Sant Vicenç Ferrer 117");
		
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
		

		ProductImage img1;
		try {
			img1 = new ProductImage("", p1, ProductImage.TYPE_FRONT, "description...");
			p1.addImage(img1);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img14;
		try {
			img14 = new ProductImage("", p1, ProductImage.TYPE_REGULAR, "description...");
			p1.addImage(img14);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img2;
		try {
			img2 = new ProductImage("", p2, ProductImage.TYPE_FRONT, "description...");
			p2.addImage(img2);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img3;
		try {
			img3 = new ProductImage("", p3, ProductImage.TYPE_FRONT, "description...");
			p3.addImage(img3);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img4;
		try {
			img4 = new ProductImage("", p4, ProductImage.TYPE_FRONT, "description...");
			p4.addImage(img4);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img5;
		try {
			img5 = new ProductImage("", p5, ProductImage.TYPE_FRONT, "description...");
			p5.addImage(img5);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img6;
		try {
			img6 = new ProductImage("", p6, ProductImage.TYPE_FRONT, "description...");
			p6.addImage(img6);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img7;
		try {
			img7 = new ProductImage("", p7, ProductImage.TYPE_FRONT, "description...");
			p7.addImage(img7);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img8;
		try {
			img8 = new ProductImage("", p8, ProductImage.TYPE_FRONT, "description...");
			p8.addImage(img8);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img9;
		try {
			img9 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p9, ProductImage.TYPE_FRONT, "description...");
			p9.addImage(img9);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img10;
		try {
			img10 = new ProductImage("http://shop.camisetasfrikis.es/163-1195-large/camiseta-i-love-mates.jpg", p10, ProductImage.TYPE_FRONT, "description...");
			p10.addImage(img10);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img11;
		try {
			img11 = new ProductImage("http://shop.camisetasfrikis.es/73-544-large/camiseta-space-invader-game-over.jpg", p11, ProductImage.TYPE_FRONT, "description...");
			p11.addImage(img11);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img12;
		try {
			img12 = new ProductImage("", p12, ProductImage.TYPE_FRONT, "description...");
			p12.addImage(img12);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img13;
		try {
			img13 = new ProductImage("", p13, ProductImage.TYPE_FRONT, "description...");
			p13.addImage(img13);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		
		
		Tax t1 = new Tax(p1, espanya, 50, 21, 49, Tax.DISCOUNT_MONEY);
		p1.addTax(t1);
		Tax t2 = new Tax(p2, espanya, 50, 21, 50, Tax.DISCOUNT_PERCENT);
		p2.addTax(t2);
		Tax t3 = new Tax(p3, espanya, 50, 21, 0, Tax.DISCOUNT_MONEY);
		p3.addTax(t3);
		Tax t4 = new Tax(p4, espanya, 50, 21, 0, Tax.DISCOUNT_MONEY);
		p4.addTax(t4);
		Tax t5 = new Tax(p5, espanya, 49.99, 21, 0, Tax.DISCOUNT_MONEY);
		p5.addTax(t5);
		Tax t6 = new Tax(p6, espanya, 49.95, 21, 0, Tax.DISCOUNT_MONEY);
		p6.addTax(t6);
		Tax t7 = new Tax(p7, espanya, 49.678952, 21, 0, Tax.DISCOUNT_MONEY);
		p7.addTax(t7);
		Tax t8 = new Tax(p8, espanya, 50, 21, 0, Tax.DISCOUNT_MONEY);
		p8.addTax(t8);
		Tax t9 = new Tax(p9, espanya, 50, 21, 0, Tax.DISCOUNT_MONEY);
		p9.addTax(t9);
		Tax t10 = new Tax(p10, espanya, 50, 21, 0, Tax.DISCOUNT_MONEY);
		p10.addTax(t10);
		Tax t11 = new Tax(p11, espanya, 50, 21, 0, Tax.DISCOUNT_MONEY);
		p11.addTax(t11);
		Tax t12 = new Tax(p12, espanya, 50, 21, 0, Tax.DISCOUNT_MONEY);
		p12.addTax(t12);
		Tax t13 = new Tax(p13, espanya, 50, 21, 0, Tax.DISCOUNT_MONEY);
		p13.addTax(t13);
		
		
		Review r1 = new Review(0, p1, user, 5, "Lorem ipsum...");
		user.addReview(r1);
		p1.addReview(r1);
		Review r2 = new Review(0, p1, user, (float) 3.5, "Lorem ipsum...");
		user.addReview(r2);
		p1.addReview(r2);
		Review r3 = new Review(0, p1, user, 5, "Lorem ipsum...");
		user.addReview(r3);
		p1.addReview(r3);
		Review r4 = new Review(0, p2, user, 5, "Lorem ipsum...");
		user.addReview(r4);
		p2.addReview(r4);
		Review r5 = new Review(0, p3, user, 5, "Lorem ipsum...");
		user.addReview(r5);
		p3.addReview(r5);
		Review r6 = new Review(0, p4, user, 5, "Lorem ipsum...");
		user.addReview(r6);
		p4.addReview(r6);
		Review r7 = new Review(0, p5, user, 5, "Lorem ipsum...");
		user.addReview(r7);
		p5.addReview(r7);
		Review r8 = new Review(0, p6, user, 5, "Lorem ipsum...");
		user.addReview(r8);
		p6.addReview(r8);
	}
	
	
	
	public void onClickButton(View v) {
		super.onClickButton(v);
    }
	
	
	
	private class ProductClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// go to product
			Intent i;
			currentPosition = position;
			Product product = products.get(position);
			i = new Intent(getBaseContext(), ProductActivity.class);
			i.putExtra("idProduct", product.getIdProduct());
			startActivity(i);
		}
		
	}



	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		ProductListItem.setSortCriteria(itemPosition);
		Collections.sort(productItems);
		ProductListAdapter adapter = new ProductListAdapter(getApplicationContext(), productItems);
        ListView productList = (ListView) findViewById(R.id.listView_product);
        productList.setAdapter(adapter);
		return false;
	}
	
	
	
	public synchronized void requestFinished(JSONObject jResponses) {
		// TODO
	}
	
	
}
