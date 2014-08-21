<?php

class User extends Eloquent {


	protected $table = 'User';
	
	protected $primaryKey = 'userEmail';
	
	protected $timestamps = false;

}
