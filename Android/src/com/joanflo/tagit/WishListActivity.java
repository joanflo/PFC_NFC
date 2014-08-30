package com.joanflo.tagit;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import com.joanflo.adapters.WishListAdapter;
import com.joanflo.adapters.WishListItem;
import com.joanflo.models.Batch;
import com.joanflo.models.Brand;
import com.joanflo.models.Category;
import com.joanflo.models.City;
import com.joanflo.models.Collection;
import com.joanflo.models.Color;
import com.joanflo.models.Country;
import com.joanflo.models.Language;
import com.joanflo.models.Product;
import com.joanflo.models.ProductImage;
import com.joanflo.models.Purchase;
import com.joanflo.models.PurchaseDetail;
import com.joanflo.models.Region;
import com.joanflo.models.Review;
import com.joanflo.models.Shop;
import com.joanflo.models.Size;
import com.joanflo.models.Tax;
import com.joanflo.models.User;
import com.joanflo.models.Wish;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;


public class WishListActivity extends BaseActivity implements Button.OnClickListener {

	
	private User user;
	private List<WishListItem> wishItems;
	private int currentPosition;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setFrameContainerView(R.layout.activity_wishlist);
		
        
        // update selected item and title, then close the drawer
        Bundle bundle = getIntent().getExtras();
        int pos = bundle.getInt("drawerPosition");
        super.updateSelected(pos);
        
        
        loadData();
        
        prepareList();
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		// uncheck elements
        ListView wishList = (ListView) findViewById(R.id.listView_wish);
        wishList.setItemChecked(currentPosition, false);
	}
	
	
	
	private void prepareList() {
		// creating wish list items
		wishItems = new ArrayList<WishListItem>();
		Iterator<Wish> it = user.getWishes().iterator();
        while(it.hasNext()) {
        	Wish wish = it.next();
        	Product product = wish.getProduct();
        	ProductImage front = product.getFrontImage();
        	WishListItem wishItem = new WishListItem(wish.getDate(), product.getName(), product.getIdProduct(), front.getUrl(), front.getDescription());
        	wishItems.add(wishItem);
        }
        
        // setting the wish list adapter
        WishListAdapter adapter = new WishListAdapter(this, getApplicationContext(), wishItems);
        ListView wishList = (ListView) findViewById(R.id.listView_wish);
        wishList.setAdapter(adapter);
        
        // setting the wish click listener
        wishList.setOnItemClickListener(new WishClickListener());
	}



	public void onClickButton(View v) {
		int position;
    	
		switch (v.getId()) {
		case R.id.button_wishlist_remove:
			position = (Integer) v.getTag();
			user.getWishes().remove(position);
			removeItem(position);
			break;
			
		case R.id.button_wishlist_addtocart:
			position = (Integer) v.getTag();
			Purchase cart = user.getPurchaseCart();
			List<PurchaseDetail> cartItems = cart.getPurchaseDetails();
			PurchaseDetail cartItem = new PurchaseDetail(cart, null, 1);
			cartItems.add(cartItem);
			removeItem(position);
			break;
		
		default:
			super.onClickButton(v);
			break;
		}
    }



	private void removeItem(int position) {
		// remove from array list
		wishItems.remove(position);
		// remove from list view
		ListView wishList = (ListView) findViewById(R.id.listView_wish);
		WishListAdapter badapter = (WishListAdapter) wishList.getAdapter();
		badapter.notifyDataSetChanged();
	}



	private void loadData() {

		Country espanya = new Country("Espanya", null, 34, Country.EURO);
		Region balears = new Region("Balears", null, espanya);
		City palma = new City("Palma", null, balears);
		Language catala = new Language("Català");
		user = new User("joan@uib.cat", palma, catala, "Joan_flo", "Joan", "Florit Gomila", 23, "password", "686922414", "Sant Vicenç Ferrer 117");
		
		Category c1 = new Category(1, null, new ArrayList<Category>(), 0, "Male");
		List<Category> categories = new ArrayList<Category>();
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
		Collection col = new Collection(0, "primavera", 2014);
		Product p1 = new Product(0, brand, col, "Camiseta verda", "Una camiseta verda", "896-25/3", "100% cotton");
		Product p2 = new Product(1, brand, col, "Vestit blau", "", "", "");
		Product p3 = new Product(2, brand, col, "Deportives blanques", "", "", "");
		Product p4 = new Product(3, brand, col, "Vaqueros slim fit", "", "", "");
		Product p5 = new Product(4, brand, col, "Calçons pana", "", "", "");
		Product p6 = new Product(5, brand, col, "Minifalda", "", "", "");
		Product p7 = new Product(6, brand, col, "Bolso 1", "", "", "");
		Product p8 = new Product(7, brand, col, "Tacons", "", "", "");
		Product p9 = new Product(8, brand, col, "Camiseta blanca", "", "", "");
		Product p10 = new Product(9, brand, col, "Camiseta blava", "", "", "");
		Product p11 = new Product(10, brand, col, "Camiseta negra", "", "", "");
		Product p12 = new Product(11, brand, col, "Collar", "", "", "");
		Product p13 = new Product(12, brand, col, "Pulsera", "", "", "");
		
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
			img1 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p1, ProductImage.TYPE_FRONT, "description...");
			p1.addImage(img1);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img14;
		try {
			img14 = new ProductImage("http://shop.camisetasfrikis.es/163-1195-large/camiseta-i-love-mates.jpg", p1, ProductImage.TYPE_REGULAR, "description...");
			p1.addImage(img14);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img15;
		try {
			img15 = new ProductImage("http://shop.camisetasfrikis.es/73-544-large/camiseta-space-invader-game-over.jpg", p1, ProductImage.TYPE_REGULAR, "description...");
			p1.addImage(img15);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img16;
		try {
			img16 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p1, ProductImage.TYPE_REGULAR, "description...");
			p1.addImage(img16);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img17;
		try {
			img17 = new ProductImage("http://1.bp.blogspot.com/-BviKauMn6uY/TicGTGbONII/AAAAAAAAAX0/_QEHg5FADEI/s1600/productimage-picture-hal-9000-60.jpg", p1, ProductImage.TYPE_REGULAR, "description...");
			p1.addImage(img17);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img2;
		try {
			img2 = new ProductImage("http://rlv.zcache.es/mensaje_pionero_camiseta-r4a0ac2d55dc845dca282ff71edc3348c_va6lr_512.jpg?max_dim=400", p2, ProductImage.TYPE_FRONT, "description...");
			p2.addImage(img2);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img3;
		try {
			img3 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p3, ProductImage.TYPE_FRONT, "description...");
			p3.addImage(img3);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img4;
		try {
			img4 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p4, ProductImage.TYPE_FRONT, "description...");
			p4.addImage(img4);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img5;
		try {
			img5 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p5, ProductImage.TYPE_FRONT, "description...");
			p5.addImage(img5);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img6;
		try {
			img6 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p6, ProductImage.TYPE_FRONT, "description...");
			p6.addImage(img6);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img7;
		try {
			img7 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p7, ProductImage.TYPE_FRONT, "description...");
			p7.addImage(img7);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img8;
		try {
			img8 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p8, ProductImage.TYPE_FRONT, "description...");
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
			img10 = new ProductImage("http://rlv.zcache.es/carl_sagan_las_demandas_requieren_pruebas_camiseta-r23d8d4b20ae04eeab221a535d6bea717_8naiq_512.jpg?max_dim=400", p10, ProductImage.TYPE_FRONT, "description...");
			p10.addImage(img10);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img11;
		try {
			img11 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p11, ProductImage.TYPE_FRONT, "description...");
			p11.addImage(img11);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img12;
		try {
			img12 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p12, ProductImage.TYPE_FRONT, "description...");
			p12.addImage(img12);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ProductImage img13;
		try {
			img13 = new ProductImage("http://rlv.zcache.es/sagan_camiseta-ref5cd14f703542ea837a4fa112262c5e_804gs_512.jpg", p13, ProductImage.TYPE_FRONT, "description...");
			p13.addImage(img13);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		
		Tax t1 = new Tax(p1, espanya, 50, 21, 10, Tax.DISCOUNT_PERCENT);
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
		
		Shop shop = new Shop(0, palma, "Sant Vicenç Ferrer 117", "L-V 9.00h a 19.00h\nS 9.00h a 13.00h", "686922414", "info@tenda.cat", 39.57993, 2.666000);
		
		p1.addRelatedProduct(p2);
		p1.addRelatedProduct(p3);
		p1.addRelatedProduct(p4);
		p1.addRelatedProduct(p10);
		
		Size s1 = new Size(0, 38, Size.GENRE_FEMALE, Size.TYPE_CLOTHES);
		Color co1 = new Color("#ff0000", "red");
		Batch b1 = new Batch(0, p1, s1, co1, shop, 1);
		p1.addBatch(b1);

		Size s2 = new Size(1, 38, Size.GENRE_MALE, Size.TYPE_CLOTHES);
		Color co2 = new Color("#ff0000", "red");
		Batch b2 = new Batch(1, p1, s2, co2, shop, 1);
		p1.addBatch(b2);
		
		Size s3 = new Size(2, 40, Size.GENRE_MALE, Size.TYPE_CLOTHES);
		Color co3 = new Color("#00ff00", "green");
		Batch b3 = new Batch(2, p1, s3, co3, shop, 1);
		p1.addBatch(b3);
		
		
		Wish w1 = new Wish(p1, user);
		user.addWish(w1);
		Wish w2 = new Wish(p2, user);
		user.addWish(w2);
		Wish w3 = new Wish(p3, user);
		user.addWish(w3);
		Wish w4 = new Wish(p4, user);
		user.addWish(w4);
		
		
		Purchase pur1 = new Purchase(0, user, Purchase.STATUS_FINISHED);
		
		Batch bat1 = new Batch(0, p1, s1, co1, shop, 8);
		PurchaseDetail purDet1 = new PurchaseDetail(pur1, bat1, 2);
		pur1.addPurchaseDetail(purDet1);
		
		Batch bat2 = new Batch(1, p1, s1, co1, shop, 4);
		PurchaseDetail purDet2 = new PurchaseDetail(pur1, bat2, 1);
		pur1.addPurchaseDetail(purDet2);
		
		user.addPurchase(pur1);
		
		
		Purchase pur2 = new Purchase(1, user, Purchase.STATUS_FINISHED);
		
		Batch bat3 = new Batch(2, p1, s1, co1, shop, 4);
		PurchaseDetail purDet3 = new PurchaseDetail(pur2, bat3, 1);
		pur2.addPurchaseDetail(purDet3);
		
		Batch bat4 = new Batch(3, p1, s1, co1, shop, 6);
		PurchaseDetail purDet4 = new PurchaseDetail(pur2, bat4, 1);
		pur2.addPurchaseDetail(purDet4);
		
		user.addPurchase(pur2);
		
		
		Purchase pur3 = new Purchase(2, user, Purchase.STATUS_PENDING);
		
		Batch bat5 = new Batch(4, p1, s1, co1, shop, 4);
		PurchaseDetail purDet5 = new PurchaseDetail(pur3, bat5, 1);
		pur3.addPurchaseDetail(purDet5);
		
		Batch bat6 = new Batch(5, p1, s1, co1, shop, 3);
		PurchaseDetail purDet6 = new PurchaseDetail(pur3, bat6, 2);
		pur3.addPurchaseDetail(purDet6);
		
		user.addPurchase(pur3);
	}



	@Override
	public void onClick(View v) {
		onClickButton(v);
	}
	
	
	
	private class WishClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// go to user profile
			currentPosition = position;
			int productId = wishItems.get(position).getProductId();
			Intent i = new Intent(getBaseContext(), ProductActivity.class);
			i.putExtra("productId", productId);
			startActivity(i);
		}
		
	}
	
	
}