<?php
 
class UsersController extends BaseController {
 
 
    /**
     * Display the specified resource.
     */
    public function show($userEmail) {
        // GET <URLbase>/users/{userEmail}
        // GET <URLbase>/users/{userEmail}?fields=nick,points
        // GET <URLbase>/users/{userEmail}?fields=password
        $fields = Input::get('fields');
		if ($fields) {
			$fields = explode(',', $fields);
			return User::where('userEmail', '=', $userEmail)->select($fields)->get()[0];
		} else {
			return User::where('userEmail', '=', $userEmail)->select('userEmail', 'cityName', 'languageName', 'nick', 'name', 'surname', 'age', 'phone', 'direction', 'registration', 'points')->get()[0];
		}
    }
 
 
    /**
     * Update the specified resource in storage.
     */
    public function update($userEmail) {
        // PUT <URLbase>/users/{userEmail}?password={password}
        // PUT <URLbase>/users/{userEmail}?cityName={cityName}&languageName={languageName}&nick={nick}&name={name}&surname={surname}&age={age}&phone={phone}&direction={direction}
        $password = Input::get('password');
		if ($password) {
			// peta
		} else {
			// peta
			$user = User::where('userEmail', '=', $userEmail)->get()[0];
			$user->cityName = Input::get('cityName');
			$user->languageName = Input::get('languageName');
			$user->nick = Input::get('nick');
			$user->name = Input::get('name');
			$user->surname = Input::get('surname');
			$user->age = Input::get('age');
			$user->phone = Input::get('phone');
			$user->direction = Input::get('direction');
			$user->save();
			return $user;
		}
    }
	
	
	/**
     * Display a listing of the resource.
     */
    public function indexReviews($userEmail) {
        // GET <URLbase>/users/{userEmail}/reviews
        return Review::where('userEmail', '=', $userEmail)->get();
    }
	
	
	/**
     * Show the form for creating a new resource.
     */
    public function createReview($userEmail) {
        // POST <URLbase>/users/{userEmail}/reviews?idProduct={idProduct}&rating={rating}&comment={comment}&date={date}
        $review = new Review();
		$review->idProduct = Input::get('idProduct');
		$review->userEmail = $userEmail;
		$review->rating = Input::get('rating');
		$review->comment = Input::get('comment');
		$review->date = Input::get('date');
		$review->save();
		return $review;
    }
	
	
	/**
     * Display the specified resource.
     */
    public function showReview($userEmail, $idComment) {
        // GET <URLbase>/users/{userEmail}/reviews/{idComment}
        return Review::where('idComment', '=', $idComment)
					 ->where('userEmail', '=', $userEmail)
					 ->get()[0];
    }
	
	
	/**
     * Display a listing of the resource.
     */
    public function indexWishes($userEmail) {
        // GET <URLbase>/users/{userEmail}/wishes
        return Wish::where('userEmail', '=', $userEmail)->get();
    }
	
	
	/**
     * Show the form for creating a new resource.
     */
    public function createWish($userEmail) {
        // POST <URLbase>/users/{userEmail}/wishes?idProduct={idProduct}&date={date}
        $wish = new Wish();
		$wish->idProduct = Input::get('idProduct');
		$wish->userEmail = $userEmail;
		$wish->date = Input::get('date');
		$wish->save();
		return $wish;
    }
	
	
	/**
     * Display the specified resource.
     */
    public function showWish($userEmail, $idWish) {
        // GET <URLbase>/users/{userEmail}/wishes/{idWish}
        return Wish::where('idWish', '=', $idWish)
				   ->where('userEmail', '=', $userEmail)
				   ->get()[0];
    }
	
 
    /**
     * Remove the specified resource from storage.
     */
    public function destroyWish($userEmail, $idWish) {
        // DELETE <URLbase>/users/{userEmail}/wishes/{idWish}
        return Wish::where('idWish', '=', $idWish)
				   ->where('userEmail', '=', $userEmail)
				   ->delete();
    }
	
	
	/**
     * Display a listing of the resource.
     */
    public function indexPurchases($userEmail) {
        // GET <URLbase>/users/{userEmail}/purchases
        // GET <URLbase>/users/{userEmail}/purchases?status={Purchase.STATUS_PENDING}
        $query = Purchase::where('userEmail', '=', $userEmail);
		$status = Input::get('status');
		if ($status) {
			// Purchase.STATUS_PENDING = 'p' | Purchase.STATUS_FINISHED = 'f'
			$query->where('status', '=', $status);
		}
		return $query->get();
    }
	
	
	/**
     * Display the specified resource.
     */
    public function showPurchase($userEmail, $idPurchase) {
        // GET <URLbase>/users/{userEmail}/purchases/{idPurchase}
        return Purchase::where('idPurchase', '=', $idPurchase)
					   ->where('userEmail', '=', $userEmail)
					   ->get()[0];
        
    }
	
	
	/**
     * Display a listing of the resource.
     */
    public function indexPurchaseDetails($userEmail, $idPurchase) {
        // GET <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details
		return DB::table('Purchase_Detail')->join('Purchase', 'Purchase_Detail.idPurchase', '=', 'Purchase.idPurchase')
            							   ->select('Purchase_Detail.idPurchaseDetail', 'Purchase_Detail.idPurchase', 'Purchase_Detail.idBatch', 'Purchase_Detail.units')
										   ->where('Purchase.idPurchase', '=', $idPurchase)
										   ->where('Purchase.userEmail', '=', $userEmail)
										   ->get();
    }
	
	
	/**
     * Display the specified resource.
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
     * Show the form for creating a new resource.
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
		return DB::table('Purchase_Detail')->where('idPurchaseDetail', '=', $idPurchaseDetail)->get();
    }
 
 
    /**
     * Update the specified resource in storage.
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
     * Remove the specified resource from storage.
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
    }
	
	
	/**
     * Display a listing of the resource.
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
			return User::whereIn('userEmail', $emailsUsers)
				   ->select('userEmail', 'nick', 'points')
				   ->get();
		}
    }
 
 
    /**
     * Show the form for creating a new resource.
     */
    public function createFriendship($userEmailFollowing) {
        // POST <URLbase>/users/{userEmailFollowing}/friendships?userEmailFollowed={userEmailFollowed}
        $userEmailFollowed = Input::get('userEmailFollowed');
		$idFriendship = Friendship::insertGetId(
			array('emailFollower' => $userEmailFollowing, 'emailFollowed' => $userEmailFollowed, 'date' => \Carbon\Carbon::now()->toDateTimeString())
		);
		return Friendship::find($idFriendship);
    }
	
 
    /**
     * Remove the specified resource from storage.
     */
    public function destroyFriendship($userEmailFollowing) {
        // DELETE <URLbase>/users/{userEmailFollowing}/friendships?userEmailFollowed={userEmailFollowed}
        $userEmailFollowed = Input::get('userEmailFollowed');
        return Friendship::where('emailFollower', '=', $userEmailFollowing)
        				 ->where('emailFollowed', '=', $userEmailFollowed)
        				 ->delete();
    }
	
	
	/**
     * Display a listing of the resource.
     */
    public function indexAchievements($userEmail) {
        // GET <URLbase>/users/{userEmail}/achievements
        return Achievement::where('userEmail', '=', $userEmail)->get();
    }
 
 
    /**
     * Show the form for creating a new resource.
     */
    public function createAchievement($userEmail) {
        // POST <URLbase>/users/{userEmail}/achievements?badgeName={badgeName}
        $badgeName = Input::get('badgeName');
		$idAchievement = Achievement::insertGetId(
			array('userEmail' => $userEmail, 'badgeName' => $badgeName, 'date' => \Carbon\Carbon::now()->toDateTimeString())
		);
		return Achievement::find($idAchievement);
    }
 
 
    /**
     * Display the specified resource.
     */
    public function showAchievement($userEmail, $idAchievement) {
        // GET <URLbase>/users/{userEmail}/achievements/{idAchievement}
        return Achievement::where('userEmail', '=', $userEmail)
						  ->where('idAchievement', '=', $idAchievement)
						  ->get()[0];
    }
	
 
}