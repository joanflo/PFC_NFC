<?php
 
class ProductsController extends BaseController {
 
 
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        // GET <URLbase>/products?queryName={queryName}
        // GET <URLbase>/products?queryName={queryName}&priceFrom={priceFrom}&priceSince={priceSince}&coin={coin}&brandName={brandName}&idCategory={idCategory}&rating={rating}
        
    }
 
 
    /**
     * Display the specified resource.
     */
    public function showProduct($idProduct)
    {
        // GET <URLbase>/products/{idProduct}
        
    }
 
 
    /**
     * Display the specified resource.
     */
    public function showProduct($showReviews)
    {
        // GET <URLbase>/products/{idProduct}/reviews 
        
    }
 
 
    /**
     * Display the specified resource.
     */
    public function showProduct($showRelatedProducts)
    {
        // GET <URLbase>/products/{idProduct}/related_products
        
    }
 
 
    /**
     * Display the specified resource.
     */
    public function showProduct($showProductImages)
    {
        // GET <URLbase>/products/{idProduct}/product_images
        // GET <URLbase>/products/{idProduct}/product_images?type={ProductImage.TYPE_FRONT}
        
    }
	
 
}