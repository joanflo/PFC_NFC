<?php
 
class ProductsController extends BaseController {
 
 
    /**
     * Display a listing of the resource.
     */
    public function index() {
        // GET <URLbase>/products?queryName={queryName}
        // GET <URLbase>/products?queryName={queryName}&priceFrom={priceFrom}&priceSince={priceSince}&coin={coin}&brandName={brandName}&idCategory={idCategory}&rating={rating}
        $query = DB::table('Product');
		
		// product name contains query text
        if ($queryName = Input::get('queryName')) {
			$query->where('name_en', 'LIKE', '%'.$queryName.'%')
				  ->orWhere('name_ca', 'LIKE', '%'.$queryName.'%');
		}
		
		// get current country
		$coin = Input::get('coin');
		if (!$coin) {
			$coin = '€'; // by default
		}
		$countryName;
		switch ($coin) {
			case '$':
				$countryName = 'United States';
				break;
			case '£':
				$countryName = 'England';
				break;
			case '€':
			default:
				$countryName = 'Espanya';
				break;
		}
		
		// get temporal products list
		$tempProducts = $query->get();
		$products = array();
		$average = array();
		
		// for each product, check constraints if they are indicated
		$brandName = Input::get('brandName');
		$idCategory = Input::get('idCategory');
		$priceFrom = Input::get('priceFrom');
		$priceSince = Input::get('priceSince');
		$rating = Input::get('rating');
		foreach ($tempProducts as $productAux) {
			$idProduct = $productAux->idProduct;
			
			// product made by brand
			$brandConstraint = true;
			if ($brandName) {
				if ($brandName != $productAux->brandName) {
					$brandConstraint = false;
				}
			}
			
			// product belongs to the given category?
			$categoryConstraint = true;
			if ($idCategory) {
				$idCategoryAux = DB::table('Product_Belongs_Category')->where('idProduct', '=', $idProduct)->pluck('idCategory');
				if ($idCategory != $idCategoryAux) {
					$categoryConstraint = false;
				}
			}
			
			// product price between minimum and maximum?
			$priceConstraint = true;
			if ($priceFrom || $priceSince) {
				$productPrice = $this->calculatePrice($idProduct, $countryName);
				if ($priceFrom && !$priceSince) {
					if ($productPrice < $priceFrom) {
						$priceConstraint = false;
					}
				} else if (!$priceFrom && $priceSince) {
					if ($productPrice > $priceSince) {
						$priceConstraint = false;
					}
				} else { // $priceFrom && $priceSince
					if ($productPrice < $priceFrom || $productPrice > $priceSince) {
						$priceConstraint = false;
					}
				}
			}
			
			// product rating higher to the given?
			$ratingConstraint = true;
			if ($rating) {
				$productRating = $this->calculateAverageRating($idProduct);
				array_push($average, $productRating);
				if ($productRating < $rating) {
					$ratingConstraint = false;
				}
			}
			
			// check constraints
			if ($brandConstraint && $categoryConstraint && $priceConstraint && $ratingConstraint) {
				array_push($products, $productAux);
			}
		}
		
		//return $average;
		return $products;
    }


	private function calculatePrice($idProduct, $countryName) {
		// get tax
		$tax = Tax::where('idProduct', '=', $idProduct)->where('countryName', '=', $countryName)->get()[0];
		// get fields
		$basePrice = $tax->basePrice;
		$iva = $tax->IVA;
		$discount = $tax->discount;
		$discountType = $tax->discountType;
		// calculate price
		$ivaMoney = $basePrice * ($iva / 100);
		$discountMoney = 0;
		if ($discountType == '§') {
			$discountMoney = $discount;
		} else if ($discountType == '%') {
			$discountMoney = ($basePrice + $ivaMoney) * ($discount / 100);
		}
		$finalPrice = $basePrice + $ivaMoney - $discountMoney;
		
		return $finalPrice;
	}


	private function calculateAverageRating($idProduct) {
		$average = Review::where('idProduct', '=', $idProduct)->avg('rating');
		if ($average == null) {
			return 5;
		} else {
			return $average;
		}
	}
 
 
    /**
     * Display the specified resource.
     */
    public function showProduct($idProduct) {
        // GET <URLbase>/products/{idProduct}
        return Product::find($idProduct);
    }
 
 
    /**
     * Display the specified resource.
     */
    public function showReviews($idProduct) {
        // GET <URLbase>/products/{idProduct}/reviews 
        return Review::where('idProduct', '=', $idProduct)->get();
    }
 
 
    /**
     * Display the specified resource.
     */
    public function showRelatedProducts($idProduct) {
        // GET <URLbase>/products/{idProduct}/related_products
        $idsRelatedProducts = DB::table('Related_Product')->where('idProductA', '=', $idProduct)->select('IdProductB')->get();
		$idsProducts = array();
		foreach ($idsRelatedProducts as $idRelatedProduct) {
			array_push($idsProducts, $idRelatedProduct->IdProductB);
		}
		if ($idsRelatedProducts) {
        	return Product::whereIn('IdProduct', $idsProducts)->get();
		}
    }
 
 
    /**
     * Display the specified resource.
     */
    public function showProductImages($idProduct) {
        // GET <URLbase>/products/{idProduct}/product_images
        // GET <URLbase>/products/{idProduct}/product_images?type={ProductImage.TYPE_FRONT}
        $query = DB::table('Product_Image')->where('idProduct', '=', $idProduct);
        $type = Input::get('type');
		if ($type) {
			$query->where('type', '=', $type);
		}
		return $query->get();
    }
	
 
}