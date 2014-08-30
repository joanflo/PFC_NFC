<?php
 
class BadgesController extends BaseController {
 
 
    /**
     * Display a listing of badges.
     */
    public function index() {
        // GET <URLbase>/badges
        return Badge::all();
    }
	
	
    /**
     * Display the specified badge.
     */
    public function show($badgeName) {
        // GET <URLbase>/badges/{badgeName}
        return Badge::find($badgeName);
    }
	
 
}