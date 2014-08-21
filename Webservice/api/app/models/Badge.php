<?php

class Badge extends Eloquent {


	protected $table = 'Badge';
	
	protected $primaryKey = 'badgeName';
	
	public $timestamps = false;

}