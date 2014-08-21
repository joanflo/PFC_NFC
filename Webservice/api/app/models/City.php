<?php

class City extends Eloquent {


	protected $table = 'City';
	
	protected $primaryKey = 'cityName';
	
	public $timestamps = false;

}