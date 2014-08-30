<?php
 
class CollectionsController extends BaseController {
 
 
    /**
     * Display the specified collection.
     */
    public function show($idCollection) {
        // GET <URLbase>/collections/{idCollection}
        return Collection::find($idCollection);
    }
	
 
}