<?php
 
class BrandsController extends BaseController {
	
 
    /**
     * Display the specified brand.
     */
    public function show($brandName) {
        // GET <URLbase>/brands/{brandName}
        return Brand::find($brandName);
    }
	
 
}