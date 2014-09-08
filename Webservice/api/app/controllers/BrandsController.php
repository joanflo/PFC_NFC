<?php
 
class BrandsController extends BaseController {
	
	
	/**
     * Display a listing of brands.
     */
    public function index() {
        // GET <URLbase>/brands
        return Brand::all();
    }
	
	
 
    /**
     * Display the specified brand.
     */
    public function show($brandName) {
        // GET <URLbase>/brands/{brandName}
        return Brand::find($brandName);
    }
	
 
}