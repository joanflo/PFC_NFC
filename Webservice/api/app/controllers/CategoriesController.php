<?php
 
class CategoriesController extends BaseController {
 
 
    /**
     * Display a listing of the resource.
     */
    public function indexCategories() {
        // GET <URLbase>/categories?level={level}
        $level = Input::get('level');
		return Category::where('level', '=', $level)->get();
    }
 
 
    /**
     * Display a listing of the nested resource.
     */
    public function indexSubcategories($idCategory) {
        // GET <URLbase>/categories/{idCategory}/subcategories
        $subcategories = Subcategory::where('idFather', '=', $idCategory)->select('idSon')->get();
		$categoriesIds = array();
		foreach ($subcategories as $subcategory) {
			array_push($categoriesIds, $subcategory->idSon);
		}
		return Category::whereIn('idCategory', $categoriesIds)->get();
		
		/* 
		 * equivalent:
		SELECT Category.idCategory, Category.level, Category.name_en, Category.name_ca
		FROM Category
		INNER JOIN Subcategory
		ON Subcategory.idFather = {idCategory}
		WHERE Subcategory.idSon = Category.idCategory
		*/
    }
	
 
}