<?php
 
class CountriesController extends BaseController {
 
 
    /**
     * Display a listing of countries.
     */
    public function indexCountries() {
        // GET <URLbase>/countries
        // GET <URLbase>/countries?cityName={cityName}
        $cityName = Input::get('cityName');
		if ($cityName) {
			// search by city name
			
			return Country::join('Region', 'Country.countryName', '=', 'Region.countryName')
            			  ->join('City', 'Region.regionName', '=', 'City.regionName')
            			  ->select('Country.countryName', 'Country.code', 'Country.coin')
						  ->where('City.cityName', '=', $cityName)->get()[0];
			/*
			SELECT *
			FROM Country
			INNER JOIN (Region
				INNER JOIN City
				ON Region.regionName = City.regionName
			) ON Country.countryName = Region.countryName
			WHERE City.cityName = {cityName}
			*/
		} else {
			// return all countries
			return Country::All();
		}
    }
 
 
    /**
     * Display a listing of the nested resource.
     */
    public function indexRegions($countryName) {
        // GET <URLbase>/countries/{countryName}/regions
        return Region::where('countryName', '=', $countryName)->get();
    }
	
	
	/**
     * Display a listing of the nested resource.
     */
    public function indexCities($countryName, $regionName) {
        // GET <URLbase>/countries/{countryName}/regions/{regionName}/cities
        return City::where('regionName', '=', $regionName)->get();
    }
	
 
}