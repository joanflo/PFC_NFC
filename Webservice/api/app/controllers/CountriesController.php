<?php
 
class CountriesController extends BaseController {
 
 
    /**
     * Display a listing of the resource.
     */
    public function indexCountries()
    {
        // GET <URLbase>/countries
        // GET <URLbase>/countries?cityName={cityName}
        
    }
 
 
    /**
     * Display a listing of the nested resource.
     */
    public function indexRegions($countryName)
    {
        // GET <URLbase>/countries/{countryName}/regions
        
    }
	
	
	/**
     * Display a listing of the nested resource.
     */
    public function indexCities($countryName, $regionName)
    {
        // GET <URLbase>/countries/{countryName}/regions/{regionName}/cities
        
    }
	
 
}