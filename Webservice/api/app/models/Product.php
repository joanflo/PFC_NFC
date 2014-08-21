<?php

class Product extends Eloquent {


	protected $table = 'Product';
	
	protected $primaryKey = 'idProduct';
	
	public $timestamps = false;

}