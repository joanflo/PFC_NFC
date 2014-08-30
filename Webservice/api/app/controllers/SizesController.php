<?php
 
class SizesController extends BaseController {
	
 
    /**
     * Display the specified size.
     */
    public function show($idSize)
    {
        // GET <URLbase>/sizes/{idSize}
        return Size::find($idSize);
    }
	
 
}