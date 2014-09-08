<?php
 
class CategoriesController extends BaseController {
 
 
    /**
     * Display a listing of categories.
     */
    public function indexCategories() {
        // GET <URLbase>/categories?level={level}
        // GET <URLbase>/categories?idProduct={idProduct}
		if (!Input::get('idProduct')) {
			$level = Input::get('level');
			return Category::where('level', '=', $level)->get();
			
		} else {
			$idProduct = Input::get('idProduct');
			$ProductBelongsCategory = DB::table('Product_Belongs_Category')->where('idProduct', '=', $idProduct)->get();
			$categoriesIds = array();
			foreach ($ProductBelongsCategory as $idCategory) {
				array_push($categoriesIds, $idCategory->idCategory);
			}
			$Category = Category::whereIn('idCategory', $categoriesIds)->get();
			
			return json_encode(array("Category" => $Category, "ProductBelongsCategory" => $ProductBelongsCategory));
		}
    }
 
 
    /**
     * Display a listing of the nested categories (subcategories).
     */
    public function indexSubcategories($idCategory) {
        // GET <URLbase>/categories/{idCategory}/subcategories
        // GET <URLbase>/categories/{idCategory}/subcategories?count=true
        
        if (Input::get('count')) {
        	$categoriesCount = $this->getCategoriesCount($idCategory);
			if ($categoriesCount == 0) {
				// products count
				$productsCount = $this->getProductsCount($idCategory);
				return json_encode(array('idCategory' => $idCategory, 'productsCount' => $productsCount));
			} else {
				// categories count
				return json_encode(array('idCategory' => $idCategory, 'categoriesCount' => $categoriesCount));
			}
		}
        
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
	
	
	
	private function getCategoriesCount($idCategory) {
        return Subcategory::where('idFather', '=', $idCategory)->count();
	}
	
	
	
	private function getProductsCount($idCategory) {
		return DB::table('Product_Belongs_Category')->where('idCategory', '=', $idCategory)->count();
	}
	
	
 
}