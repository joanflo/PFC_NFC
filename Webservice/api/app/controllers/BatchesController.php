<?php
 
class BatchesController extends BaseController {
 
 
    /**
     * Display a listing of the resource.
     */
    public function index() {
        // <URLbase>/batches?idProduct={idProduct}
        // <URLbase>/batches?idProduct={idProduct}&idShop={idShop}
        $idProduct = Input::get('idProduct');
		$idShop = Input::get('idShop');
		if ($idShop) {
			return Batch::where('idProduct', '=', $idProduct)
						->where('idShop', '=', $idShop)->get();
		} else {
			return Batch::where('idProduct', '=', $idProduct)->get();
		}
    }
 
 
    /**
     * Display the specified resource.
     */
    public function show($idBatch) {
        // GET <URLbase>/batches/{idBatch}
        return Batch::find($idBatch);
    }
 
 
    /**
     * Update the specified resource in storage.
     */
    public function update($idBatch) {
        // PUT <URLbase>/batches/{idBatch}?units={units}
        $units = Input::get('units');
        $batch = Batch::find($idBatch);
		$batch->units = $units;
		$batch->save();
		return $batch;
		/*
		 * equivalent:
		return Batch::where('idBatch', $idBatch)
            		->update(array('units' => $units));
		*/
    }
	
 
}