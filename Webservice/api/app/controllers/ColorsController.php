<?php
 
class ColorsController extends BaseController {
 
 
    /**
     * Display the specified resource.
     */
    public function show($colorCode)
    {
        // GET <URLbase>/colors/{colorCode}
        return Color::find($colorCode);
    }
	
 
}