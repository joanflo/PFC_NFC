<?php
 
class UsersController extends BaseController {
 
 
    /**
     * Display the specified resource.
     */
    public function show($userEmail)
    {
        // GET <URLbase>/users/{userEmail}
        // GET <URLbase>/users/{userEmail}?fields=nick,points
        // GET <URLbase>/users/{userEmail}?fields=password
        
    }
 
 
    /**
     * Update the specified resource in storage.
     */
    public function update($userEmail)
    {
        // PUT <URLbase>/users/{userEmail}?password={password}
        // PUT <URLbase>/users/{userEmail}?userEmail={userEmail}&cityName={cityName}&languageName={languageName}&nick={nick}&name={name}&surname={surname}&age={age}&password={password}&phone={phone}&direction={direction}
        
    }
	
	
	/**
     * Display a listing of the resource.
     */
    public function indexReviews($userEmail)
    {
        // GET <URLbase>/users/{userEmail}/reviews
        
    }
	
	
	/**
     * Show the form for creating a new resource.
     */
    public function createReview($userEmail)
    {
        // POST <URLbase>/users/{userEmail}/reviews?idProduct={idProduct}&rating={rating}&comment={comment}&date={date}
        
    }
	
	
	/**
     * Display the specified resource.
     */
    public function showReview($userEmail, $idComment)
    {
        // GET <URLbase>/users/{userEmail}/reviews/{idComment}
        
    }
	
	
	/**
     * Display a listing of the resource.
     */
    public function indexWishes($userEmail)
    {
        // GET <URLbase>/users/{userEmail}/wishes
        
    }
	
	
	/**
     * Show the form for creating a new resource.
     */
    public function createWish($userEmail)
    {
        // POST <URLbase>/users/{userEmail}/wishes?idProduct={idProduct}&date={date}
        
    }
	
	
	/**
     * Display the specified resource.
     */
    public function showWish($userEmail, $idWish)
    {
        // GET <URLbase>/users/{userEmail}/wishes/{idWish}
        
    }
	
 
    /**
     * Remove the specified resource from storage.
     */
    public function destroyWish($userEmail, $idWish)
    {
        // DELETE <URLbase>/users/{userEmail}/wishes/{idWish}
        
    }
	
	
	/**
     * Display a listing of the resource.
     */
    public function indexPurchases($userEmail)
    {
        // GET <URLbase>/users/{userEmail}/purchases
        // GET <URLbase>/users/{userEmail}/purchases?status={Purchase.STATUS_PENDING}
        
    }
	
	
	/**
     * Display the specified resource.
     */
    public function showPurchase($userEmail, $idPurchase)
    {
        // GET <URLbase>/users/{userEmail}/purchases/{idPurchase}
        
    }
	
	
	/**
     * Display a listing of the resource.
     */
    public function indexPurchaseDetails($userEmail, $idPurchase)
    {
        // GET <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details
        
    }
	
	
	/**
     * Display the specified resource.
     */
    public function showPurchaseDetail($userEmail, $idPurchase, $idPurchaseDetail)
    {
        // GET <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}
        
    }
 
 
    /**
     * Show the form for creating a new resource.
     */
    public function createPurchaseDetail($userEmail, $idPurchase)
    {
        // POST <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details?idBatch={idBatch}&units={units}
        
    }
 
 
    /**
     * Update the specified resource in storage.
     */
    public function updatePurchaseDetail($userEmail, $idPurchase, $idPurchaseDetail)
    {
        // PUT <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}?idBatch={idBatch}&units={units}
        
    }
	
 
    /**
     * Remove the specified resource from storage.
     */
    public function destroyPurchaseDetail($userEmail, $idPurchase, $idPurchaseDetail)
    {
        // DELETE <URLbase>/users/{userEmail}/purchases/{idPurchase}/purchase_details/{idPurchaseDetail}
        
    }
	
	
	/**
     * Display a listing of the resource.
     */
    public function indexFriendships($userEmail)
    {
        // GET <URLbase>/users/{userEmail}/friendships?following=false
        // GET <URLbase>/users/{userEmail}/friendships?following=true
        
    }
 
 
    /**
     * Show the form for creating a new resource.
     */
    public function createFriendship($userEmailFollowing)
    {
        // POST <URLbase>/users/{userEmailFollowing}/friendships?userEmailFollowed={userEmailFollowed}
        
    }
	
 
    /**
     * Remove the specified resource from storage.
     */
    public function destroyFriendship($userEmailFollowing)
    {
        // DELETE <URLbase>/users/{userEmailFollowing}/friendships?userEmailFollowed={userEmailFollowed}
        
    }
	
	
	/**
     * Display a listing of the resource.
     */
    public function indexAchievements($userEmail)
    {
        // GET <URLbase>/users/{userEmail}/achievements
        
    }
 
 
    /**
     * Show the form for creating a new resource.
     */
    public function createAchievement($userEmail)
    {
        // POST <URLbase>/users/{userEmail}/achievements?badgeName={badgeName}&date={date}
        
    }
 
 
    /**
     * Display the specified resource.
     */
    public function showAchievement($userEmail, $idAchievement)
    {
        // GET <URLbase>/users/{userEmail}/achievements/{idAchievement}
        
    }
	
	
	
	
	
	
	
	
 
 
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        //
    }
 
 
    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        //
    }
	
 
    /**
     * Store a newly created resource in storage.
     */
    public function store()
    {
        //
    }
 
 
    /**
     * Display the specified resource.
     */
    public function show($id)
    {
        //
    }
 
 
    /**
     * Show the form for editing the specified resource.
     */
    public function edit($id)
    {
        //
    }
 
 
    /**
     * Update the specified resource in storage.
     */
    public function update($id)
    {
        //
    }
	
 
    /**
     * Remove the specified resource from storage.
     */
    public function destroy($id)
    {
        //
    }
	
 
}