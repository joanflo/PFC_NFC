<?php

class Language extends Eloquent {


	protected $table = 'Language';
	
	protected $primaryKey = 'languageName';
	
	public $timestamps = false;

}