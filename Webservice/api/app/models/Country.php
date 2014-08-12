<?php

class Country extends Eloquent {


	protected $table = 'Country';
	
	protected $primaryKey = 'countryName';
	
	protected $timestamps = false;

}