<?php

class Brand extends Eloquent {


	protected $table = 'Brand';
	
	protected $primaryKey = 'brandName';
	
	public $timestamps = false;

}