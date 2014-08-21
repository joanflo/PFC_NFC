<?php

class User extends Eloquent {


	protected $table = 'User';
	
	protected $primaryKey = 'userEmail';
	
	public $timestamps = false;

}
