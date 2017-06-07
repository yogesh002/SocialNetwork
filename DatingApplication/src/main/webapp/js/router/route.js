(function() {
	angular.module("photoAlbum").config(PhotoAlbumConfiguration);
	PhotoAlbumConfiguration.$inject = [ "$stateProvider", "$urlRouterProvider" ]
	function PhotoAlbumConfiguration($stateProvider, $urlRouterProvider) {
		$urlRouterProvider.otherwise("/");
		$stateProvider.state("createAlbum", {
			url : "/createAlbum",
			templateUrl : "html/album/create.html",
			controller : "photoAlbumController as controller",
		}).
		state("home", {
			url:"/album",
		}).
		state("viewAllAlbumsOfUser", {
			url : "/allAlbums",
			templateUrl : "html/album/view.html",
			controller : "viewAllAlbumsController as controller",
		}).
		state("viewPhotosInAlbum",{
			url : "/viewAlbum/{album_id}",
			templateUrl : "html/album/photos.html",
			controller : "viewPhotosController as controller",
			resolve : {
				photos : ["$stateParams", "photoAlbumService",function($stateParams, photoAlbumService){
					var details = photoAlbumService.viewPhotosInTheAlbum("getPhotosFromAlbum", $stateParams.album_id);
					return details.then(function(response){
						var imageData = response.data;
						return imageData;
					})
				}]
			}
		})
	}
})();