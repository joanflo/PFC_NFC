<?php
 
class LanguagesController extends BaseController {
 	
 
    /**
     * Display a listing of languages.
     */
    public function index() {
        // GET <URLbase>/languages
        return Language::All();
    }
	
 
}