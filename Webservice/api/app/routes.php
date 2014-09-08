<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the Closure to execute when that URI is requested.
|
*/

Route::get('/', function()
{
	return View::make('hello');
});


Route::get('/authtest', array('before' => 'auth.basic', function()
{
    return View::make('hello');
}));



/* ================================================================================ *
 * ===================================== BADGES =================================== *
 * ================================================================================ */

Route::get('badges', 'BadgesController@index');

Route::get('badges/{badgeName}', 'BadgesController@show');



/* ================================================================================ *
 * ===================================== SHOPS ==================================== *
 * ================================================================================ */

Route::get('shops', 'ShopsController@index');
 
Route::get('shops/{idShop}', 'ShopsController@show');



/* ================================================================================ *
 * ===================================== CATEGORIES =============================== *
 * ================================================================================ */

Route::get('categories', 'CategoriesController@indexCategories');
 
Route::get('categories/{idCategory}/subcategories', 'CategoriesController@indexSubcategories');



/* ================================================================================ *
 * ===================================== COUNTRIES ================================ *
 * ================================================================================ */

Route::get('countries', 'CountriesController@indexCountries');

Route::get('countries/{countryName}/regions', 'CountriesController@indexRegions');

Route::get('countries/{countryName}/regions/{regionName}/cities', 'CountriesController@indexCities');



/* ================================================================================ *
 * ===================================== LANGUAGES ================================ *
 * ================================================================================ */
 
Route::get('languages', 'LanguagesController@index');



/* ================================================================================ *
 * ===================================== BATCHES ================================== *
 * ================================================================================ */

Route::get('batches', 'BatchesController@index');

Route::get('batches/{idBatch}', 'BatchesController@show');

Route::put('batches/{idBatch}', 'BatchesController@update');



/* ================================================================================ *
 * ===================================== BRANDS =================================== *
 * ================================================================================ */

Route::get('brands', 'BrandsController@index');
 
Route::get('brands/{brandName}', 'BrandsController@show');
 


/* ================================================================================ *
 * ===================================== SIZES ==================================== *
 * ================================================================================ */

Route::get('sizes/{idSize}', 'SizesController@show');
 


/* ================================================================================ *
 * ===================================== USERS ==================================== *
 * ================================================================================ */
 
Route::get('users/{userEmail}', 'UsersController@show');

Route::put('users/{userEmail}', 'UsersController@update');
 
Route::get('users/{userEmail}/reviews', 'UsersController@indexReviews');
 
Route::post('users/{userEmail}/reviews', 'UsersController@createReview');
 
Route::get('users/{userEmail}/reviews/{idComment}', 'UsersController@showReview');

Route::get('users/{userEmail}/wishes', 'UsersController@indexWishes');
 
Route::post('users/{userEmail}/wishes', 'UsersController@createWish');

Route::get('users/{userEmail}/wishes/{idWish}', 'UsersController@showWish');

Route::delete('users/{userEmail}/wishes/{idWish}', 'UsersController@destroyWish');

Route::get('users/{userEmail}/purchases', 'UsersController@indexPurchases');

Route::get('users/{userEmail}/purchases/{idPurchase}', 'UsersController@showPurchase');

Route::get('users/{userEmail}/purchases/{idPurchase}/purchase_details', 'UsersController@indexPurchaseDetails');

Route::post('users/{userEmail}/purchases/{idPurchase}/purchase_details', 'UsersController@createPurchaseDetail');

Route::get('users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}', 'UsersController@showPurchaseDetail');

Route::put('users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}', 'UsersController@updatePurchaseDetail');

Route::delete('users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}', 'UsersController@destroyPurchaseDetail');

Route::get('users/{userEmail}/friendships', 'UsersController@indexFriendships');

Route::post('users/{userEmailFollowing}/friendships', 'UsersController@createFriendship');

Route::delete('users/{userEmailFollowing}/friendships', 'UsersController@destroyFriendship');

Route::get('users/{userEmail}/achievements', 'UsersController@indexAchievements');

Route::post('users/{userEmail}/achievements', 'UsersController@createAchievement');

Route::get('users/{userEmail}/achievements/{idAchievement}', 'UsersController@showAchievement');



/* ================================================================================ *
 * ===================================== TAXES ==================================== *
 * ================================================================================ */
 
Route::get('taxes', 'TaxesController@show');
 


/* ================================================================================ *
 * ===================================== COLLECCIONS ============================== *
 * ================================================================================ */
 
Route::get('collections/{idCollection}', 'CollectionsController@show');
 


/* ================================================================================ *
 * ===================================== COLORS =================================== *
 * ================================================================================ */
 
Route::get('colors/{colorCode}', 'ColorsController@show');
 


/* ================================================================================ *
 * ===================================== PRODUCTS ================================= *
 * ================================================================================ */
 
Route::get('products', 'ProductsController@index');

Route::get('products/{idProduct}', 'ProductsController@showProduct');

Route::get('products/{idProduct}/reviews', 'ProductsController@indexReviews');

Route::get('products/{idProduct}/related_products', 'ProductsController@indexRelatedProducts');

Route::get('products/{idProduct}/product_images', 'ProductsController@indexProductImages');

