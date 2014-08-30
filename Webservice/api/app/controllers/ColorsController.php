<?php
 
class ColorsController extends BaseController {
 
 
    /**
     * Display the specified color.
     */
    public function show($colorCode)
    {
        // GET <URLbase>/colors/{colorCode}
        return Color::find($colorCode);
    }
	
 
}