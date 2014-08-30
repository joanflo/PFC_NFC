<?php
 
class ShopsController extends BaseController {
	
	
    /**
     * Display a listing of shops.
     */
    public function index() {
        // GET <URLbase>/shops?cityName={cityName}
        // GET <URLbase>/shops?latitude={latitude}&longitude={longitude}
        $cityName = Input::get('cityName');
		if ($cityName) {
			// search by city name
			return Shop::where('cityName', '=', $cityName)->get();
		} else {
			// search by location
			$latitudeFrom = Input::get('latitude');
			$longitudeFrom = Input::get('longitude');
			$nearShops = array();
			// for each shop
			$shopsAux = Shop::All();
			foreach ($shopsAux as $shopAux) {
				$latitudeTo = $shopAux->latitude;
				$longitudeTo = $shopAux->longitude;
				// distance between user's current location and shop location must be less than 10 km
		        if ($this->haversineGreatCircleDistance($latitudeFrom, $longitudeFrom, $latitudeTo, $longitudeTo) < 10000) {
		        	array_push($nearShops, $shopAux);
		        }
			}
			return $nearShops;
		}
    }
 
 
    /**
     * Display the specified shop.
     */
    public function show($idShop) {
        // GET <URLbase>/shops/{idShop}
        return Shop::find($idShop);
    }
	
	
	/**
	 * Calculates the great-circle distance between two points, with the Haversine formula.
	 * Code from: https://stackoverflow.com/questions/10053358/measuring-the-distance-between-two-coordinates-in-php
	 * @param float $latitudeFrom Latitude of start point in [deg decimal]
	 * @param float $longitudeFrom Longitude of start point in [deg decimal]
	 * @param float $latitudeTo Latitude of target point in [deg decimal]
	 * @param float $longitudeTo Longitude of target point in [deg decimal]
	 * @param float $earthRadius Mean earth radius in [m]
	 * @return float Distance between points in [m] (same as earthRadius)
	 */
	private function haversineGreatCircleDistance($latitudeFrom, $longitudeFrom, $latitudeTo, $longitudeTo, $earthRadius = 6371000) {
		// convert from degrees to radians
		$latFrom = deg2rad($latitudeFrom);
		$lonFrom = deg2rad($longitudeFrom);
		$latTo = deg2rad($latitudeTo);
		$lonTo = deg2rad($longitudeTo);
		
		$latDelta = $latTo - $latFrom;
		$lonDelta = $lonTo - $lonFrom;
		
		$angle = 2 * asin(sqrt(pow(sin($latDelta / 2), 2) +
		cos($latFrom) * cos($latTo) * pow(sin($lonDelta / 2), 2)));
		
		return $angle * $earthRadius;
	}
	
 
}