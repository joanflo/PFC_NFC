<?php

class Product_Image extends Eloquent {


	protected $table = 'Product_Image';
	
	protected $primaryKey = 'url';
	
	public $timestamps = false;

}