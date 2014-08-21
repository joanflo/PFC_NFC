<?php

class Country extends Eloquent {


	protected $table = 'Country';
	
	protected $primaryKey = 'countryName';
	
	public $timestamps = false;

}