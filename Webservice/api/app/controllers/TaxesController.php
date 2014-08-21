<?php
 
class TaxesController extends BaseController {
 
 
    /**
     * Display the specified resource.
     */
    public function show() {
        // GET <URLbase>/taxes?idProduct={idProduct}&countryName={countryName}
        $idProduct = Input::get('idProduct');
        $countryName = Input::get('countryName');
		return Tax::where('idProduct', '=', $idProduct)->where('countryName', '=', $countryName)->get()[0];
    }
	
 
}