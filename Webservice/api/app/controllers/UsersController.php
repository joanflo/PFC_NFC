<?php
 
class UsersController extends BaseController {
 
 
    /**
     * Display the specified user.
     */
    public function show($userEmail) {
        // GET <URLbase>/users/{userEmail}
        // GET <URLbase>/users/{userEmail}?fields=userEmail,nick,points
        // GET <URLbase>/users/{userEmail}?fields=password
        $fields = Input::get('fields');
		if ($fields) {
			// only return some fields
			$fields = explode(',', $fields);
			$user = User::where('userEmail', '=', $userEmail)->select($fields)->get();
			if (count($user) == 0) {
				App::abort(404);
			} else {
				return $user[0];
			}
		} else {
			// return the whole user (except password)
			return User::where('userEmail', '=', $userEmail)->select('userEmail', 'cityName', 'languageName', 'nick', 'name', 'surname', 'age', 'phone', 'direction', 'registration', 'points')->get()[0];
		}
    }
 
 
    /**
     * Update the specified user in storage.
     */
    public function update($userEmail) {
        // PUT <URLbase>/users/{userEmail}?password={password}&oldPassword={oldPassword}
        // PUT <URLbase>/users/{userEmail}?cityName={cityName}&languageName={languageName}&nick={nick}&name={name}&surname={surname}&age={age}&phone={phone}&direction={direction}
        // PUT <URLbase>/users/{userEmail}?password={password}&cityName={cityName}&languageName={languageName}&nick={nick}&name={name}&surname={surname}&age={age}&phone={phone}&direction={direction}
        
        // check if user exists
        $user = User::where('userEmail', '=', $userEmail)->get();
		$password = Input::get('password');
		if (count($user) != 0) {
			$user = $user[0];
			// update user
			if ($password && !Input::get('nick')) {
				// update user's password
				$oldPassword = Input::get('oldPassword');
				if ($user->password == $oldPassword) {
					// update old password
					$user->password = $password;
				} else {
					App::abort(404);
				}
			} else if (!$password) {
				// update user's data
				$user->cityName = Input::get('cityName');
				$user->languageName = Input::get('languageName');
				$user->nick = Input::get('nick');
				$user->name = Input::get('name');
				$user->surname = Input::get('surname');
				$user->age = Input::get('age');
				$user->phone = Input::get('phone');
				$user->direction = Input::get('direction');
			} else {
				// error
				App::abort(404);
			}
			
		} else {
			// create user
			$user = new User();
			//$user->userEmail = $userEmail;
			$user->password = $password;
			$user->cityName = Input::get('cityName');
			$user->languageName = Input::get('languageName');
			$user->nick = Input::get('nick');
			$user->name = Input::get('name');
			$user->surname = Input::get('surname');
			$user->age = Input::get('age');
			if (Input::get('phone')) {
				$user->phone = Input::get('phone');
			}
			if (Input::get('direction')) {
				$user->direction = Input::get('direction');
			}
			$user->registration = \Carbon\Carbon::now()->toDateTimeString();
			$user->save();
			$user->userEmail = $userEmail;
		}
		
		// save changes
		$user->save();
		return $user;
    }
	
	
	/**
     * Display a listing of reviews
     */
    public function indexReviews($userEmail) {
        // GET <URLbase>/users/{userEmail}/reviews
        $reviews = Review::where('userEmail', '=', $userEmail)->get();
		if (count($reviews) == 0) {
			App::abort(404);
		} else {
			return $reviews;
		}
    }
	
	
	/**
     * Create a new review.
     */
    public function createReview($userEmail) {
        // POST <URLbase>/users/{userEmail}/reviews?idProduct={idProduct}&rating={rating}&comment={comment}
        $review = new Review();
		$review->idProduct = Input::get('idProduct');
		$review->userEmail = $userEmail;
		$review->rating = Input::get('rating');
		$review->comment = Input::get('comment');
		$review->date = \Carbon\Carbon::now()->toDateTimeString();
		$review->save();
		return Response::json($review, 201);
    }
	
	
	/**
     * Display the specified review.
     */
    public function showReview($userEmail, $idComment) {
        // GET <URLbase>/users/{userEmail}/reviews/{idComment}
        return Review::where('idComment', '=', $idComment)
					 ->where('userEmail', '=', $userEmail)
					 ->get()[0];
    }
	
	
	/**
     * Display a listing of wishes.
     */
    public function indexWishes($userEmail) {
        // GET <URLbase>/users/{userEmail}/wishes
        $wishes = Wish::join('Product', 'Product.idProduct', '=', 'Wish.idProduct')
					  ->join('Product_Image', 'Product_Image.idProduct', '=', 'Product.idProduct')
        			  ->where('Product_Image.type', '=', 'f')
        			  ->where('Wish.userEmail', '=', $userEmail)
					  ->select('Wish.idWish', 'Wish.idProduct', 'Wish.date', 'Product.name_en', 'Product.name_ca', 'Product_Image.url', 'Product_Image.description_en', 'Product_Image.description_ca')
        			  ->get();
		if (count($wishes) == 0) {
			App::abort(404);
		} else {
			return $wishes;
		}
    }
	
	
	/**
     * Create a new wish.
     */
    public function createWish($userEmail) {
        // POST <URLbase>/users/{userEmail}/wishes?idProduct={idProduct}
        $wish = new Wish();
		$wish->idProduct = Input::get('idProduct');
		$wish->userEmail = $userEmail;
		$wish->date = \Carbon\Carbon::now()->toDateTimeString();
		$wish->save();
		return Response::json($wish, 201);
    }
	
	
	/**
     * Display the specified wish.
     */
    public function showWish($userEmail, $idWish) {
        // GET <URLbase>/users/{userEmail}/wishes/{idWish}
        return Wish::where('idWish', '=', $idWish)
				   ->where('userEmail', '=', $userEmail)
				   ->get()[0];
    }
	
 
    /**
     * Remove the specified wish from storage.
     */
    public function destroyWish($userEmail, $idWish) {
        // DELETE <URLbase>/users/{userEmail}/wishes/{idWish}
        Wish::where('idWish', '=', $idWish)
			->where('userEmail', '=', $userEmail)
			->delete();
		return Response::json(null, 204);
    }
	
	
	/**
     * Display a listing of purchases.
     */
    public function indexPurchases($userEmail) {
        // GET <URLbase>/users/{userEmail}/purchases
        // GET <URLbase>/users/{userEmail}/purchases?status={Purchase.STATUS_PENDING}
        $query = Purchase::where('userEmail', '=', $userEmail);
		$status = Input::get('status');
		if ($status) {
			// Purchase.STATUS_PENDING = 'p'
			$query->where('status', '=', $status);
		} else {
			$query->where('status', '=', 'f');
		}
		$purchases = $query->get();
		
		if (count($purchases) == 0) {
			App::abort(404);
		} else {
			return $purchases;
		}
    }
	
	
	/**
     * Display the specified purchase.
     */
    public function showPurchase($userEmail, $idPurchase) {
        // GET <URLbase>/users/{userEmail}/purchases/{idPurchase}
        return Purchase::where('idPurchase', '=', $idPurchase)
					   ->where('userEmail', '=', $userEmail)
					   ->get()[0];
        
    }
	
	
	/**
     * Display a listing of purchase details.
     */
    public function indexPurchaseDetails($userEmail, $idPurchase) {
        // GET <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details
		$purchaseDetails = DB::table('Purchase_Detail')->join('Purchase', 'Purchase_Detail.idPurchase', '=', 'Purchase.idPurchase')
													   ->join('Batch', 'Purchase_Detail.idBatch', '=', 'Batch.idBatch')
            										   ->select('Purchase_Detail.idPurchaseDetail', 'Purchase_Detail.idPurchase', 'Purchase_Detail.idBatch', 
            										   			'Purchase_Detail.units', 'Batch.idProduct', 'Batch.idShop', 'Batch.idSize', 'Batch.colorCode')
													   ->where('Purchase.idPurchase', '=', $idPurchase)
													   ->where('Purchase.userEmail', '=', $userEmail)
													   ->get();
										   
		if (count($purchaseDetails) == 0) {
			App::abort(404);
		} else {
			return $purchaseDetails;
		}
    }
	
	
	/**
     * Display the specified purchase detail.
     */
    public function showPurchaseDetail($userEmail, $idPurchase, $idPurchaseDetail) {
        // GET <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}
		return DB::table('Purchase_Detail')->join('Purchase', 'Purchase_Detail.idPurchase', '=', 'Purchase.idPurchase')
            							   ->select('Purchase_Detail.idPurchaseDetail', 'Purchase_Detail.idPurchase', 'Purchase_Detail.idBatch', 'Purchase_Detail.units')
										   ->where('Purchase.idPurchase', '=', $idPurchase)
										   ->where('Purchase.userEmail', '=', $userEmail)
										   ->where('Purchase_Detail.idPurchaseDetail', '=', $idPurchaseDetail)
										   ->get();
    }
 
 
    /**
     * Create a new purchase detail.
     */
    public function createPurchaseDetail($userEmail, $idPurchase) {
        // POST <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details?idBatch={idBatch}&units={units}
        
        // check if exists a pending purchase for the user
        $pendingPurchaseExists = Purchase::where('userEmail', '=', $userEmail)
        								 ->where('status', '=', 'p')->count() != 0;
		
		// get pending purchase id
		$idPendingPurchase = $idPurchase;
		if (!$pendingPurchaseExists) {
			// create new pending purchase
			$idPendingPurchase = Purchase::insertGetId(
	    		array('userEmail' => $userEmail, 'status' => 'p', 'date' => \Carbon\Carbon::now()->toDateTimeString())
			);
		}

		// create purchase detail
		$idBatch = Input::get('idBatch');
		$units = Input::get('units');
		$idPurchaseDetail = DB::table('Purchase_Detail')->insertGetId(
    		array('idPurchase' => $idPendingPurchase, 'idBatch' => $idBatch, 'units' => $units)
		);
		
		// return purchase detail
		$purchaseDetail = DB::table('Purchase_Detail')->where('idPurchaseDetail', '=', $idPurchaseDetail)->get();
		return Response::json($purchaseDetail, 201);
    }
 
 
    /**
     * Update the specified purchase detail in storage.
     */
    public function updatePurchaseDetail($userEmail, $idPurchase, $idPurchaseDetail) {
        // PUT <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}?idBatch={idBatch}&units={units}
		$idBatch = Input::get('idBatch');
		$units = Input::get('units');
		DB::table('Purchase_Detail')->join('Purchase', 'Purchase_Detail.idPurchase', '=', 'Purchase.idPurchase')
            						->select('Purchase_Detail.idPurchaseDetail', 'Purchase_Detail.idPurchase', 'Purchase_Detail.idBatch', 'Purchase_Detail.units')
									->where('Purchase.idPurchase', '=', $idPurchase)
									->where('Purchase.userEmail', '=', $userEmail)
									->where('Purchase_Detail.idPurchaseDetail', '=', $idPurchaseDetail)
									->update(array('idBatch' => $idBatch, 'units' => $units));
		return $this->showPurchaseDetail($userEmail, $idPurchase, $idPurchaseDetail);
    }
	
 
    /**
     * Remove the specified purchase datail from storage.
     */
    public function destroyPurchaseDetail($userEmail, $idPurchase, $idPurchaseDetail) {
        // DELETE <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}
        
        // delete purchase detail
        $purchase = $this->showPurchase($userEmail, $idPurchase);
        if ($purchase->status == 'p') {
        	DB::table('Purchase_Detail')->where('idPurchaseDetail', '=', $idPurchaseDetail)->delete();
        }
		
		// delete purchase cart if it's empty
		$numItems = DB::table('Purchase_Detail')->where('idPurchase', '=', $idPurchase)->count();
		if ($numItems == 0) {
        	Purchase::where('idPurchase', '=', $idPurchase)->delete();
		}
		
		return Response::json(null, 204);
    }
	
	
	/**
     * Display a listing of friendships.
     */
    public function indexFriendships($userEmail) {
        // GET <URLbase>/users/{userEmail}/friendships?following=true
        // GET <URLbase>/users/{userEmail}/friendships?following=false
		$emailsUsers = array();
		if (Input::get('following') == "true") {
			// following
			$emailsFollowed = Friendship::where('emailFollower', '=', $userEmail)->select('emailFollowed')->get();
			foreach ($emailsFollowed as $emailFollowed) {
				array_push($emailsUsers, $emailFollowed->emailFollowed);
			}
		} else { // = "false"
			// followers
			$emailsFollowers = Friendship::where('emailFollowed', '=', $userEmail)->select('emailFollower')->get();
			foreach ($emailsFollowers as $emailFollower) {
				array_push($emailsUsers, $emailFollower->emailFollower);
			}
		}
		
		if (count($emailsUsers) != 0) {
			$result = User::whereIn('userEmail', $emailsUsers)
					->select('userEmail', 'nick', 'name', 'surname')
					->get();
			if (Input::get('following') == "true") {
				$result = json_encode(array('following' => $result));
			} else {
				$result = json_encode(array('followers' => $result));
			}
			return $result;
		} else {
			App::abort(404);
		}
    }
 
 
    /**
     * Create a new friendship.
     */
    public function createFriendship($userEmailFollowing) {
        // POST <URLbase>/users/{userEmailFollowing}/friendships?userEmailFollowed={userEmailFollowed}
        $userEmailFollowed = Input::get('userEmailFollowed');
		$idFriendship = Friendship::insertGetId(
			array('emailFollower' => $userEmailFollowing, 'emailFollowed' => $userEmailFollowed, 'date' => \Carbon\Carbon::now()->toDateTimeString())
		);
		$friendship = Friendship::find($idFriendship);
		return Response::json($friendship, 201);
	}
	
 
    /**
     * Remove the specified friendship from storage.
     */
    public function destroyFriendship($userEmailFollowing) {
        // DELETE <URLbase>/users/{userEmailFollowing}/friendships?userEmailFollowed={userEmailFollowed}
        $userEmailFollowed = Input::get('userEmailFollowed');
        Friendship::where('emailFollower', '=', $userEmailFollowing)
        		  ->where('emailFollowed', '=', $userEmailFollowed)
        		  ->delete();
		return Response::json(null, 204);
    }
	
	
	/**
     * Display a listing of achievements.
     */
    public function indexAchievements($userEmail) {
        // GET <URLbase>/users/{userEmail}/achievements
        return Achievement::where('userEmail', '=', $userEmail)->get();
    }
 
 
    /**
     * Create a new achievement.
     */
    public function createAchievement($userEmail) {
        // POST <URLbase>/users/{userEmail}/achievements?badgeName={badgeName}
        $badgeName = Input::get('badgeName');
		$idAchievement = Achievement::insertGetId(
			array('userEmail' => $userEmail, 'badgeName' => $badgeName, 'date' => \Carbon\Carbon::now()->toDateTimeString())
		);
		$achievement = Achievement::find($idAchievement);
		return Response::json($achievement, 201);
    }
 
 
    /**
     * Display the specified achievement.
     */
    public function showAchievement($userEmail, $idAchievement) {
        // GET <URLbase>/users/{userEmail}/achievements/{idAchievement}
        return Achievement::where('userEmail', '=', $userEmail)
						  ->where('idAchievement', '=', $idAchievement)
						  ->get()[0];
    }
	
 
}