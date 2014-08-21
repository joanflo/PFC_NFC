<?php
 
class BadgesController extends BaseController {
 
 
    /**
     * Display a listing of the resource.
     */
    public function index() {
        // GET <URLbase>/badges
        return Badge::all();
    }
	
	
    /**
     * Display the specified resource.
     */
    public function show($badgeName) {
        // GET <URLbase>/badges/{badgeName}
        return Badge::find($badgeName);
    }
	
 
}