(function(){
	angular.module("photoAlbum")
	.service("photoAlbumService", PhotoAlbumService);
	
	PhotoAlbumService.$inject = ["$http"]
	function PhotoAlbumService($http){
		var service = this;
		service.createAlbum = function(name){
			var response = $http({
				method : "POST",
				url : "createAlbum",
				data : {
					albumName : name,
				}
			})
			return response;
		}
		
		service.viewAllAlbums = function(){
			var response = $http({
				method : "POST",
				url : "getAllAlbums",
			}).success(function(data, status, headers, config) {
				/*console.log(data);*/
			})
			.error(function(data, status, headers, config) {
				console.log(data, status, headers, config);
				service.errorMessage = "Error while retrieving albums."
			});
			return response;
		}
		
		service.viewPhotosInTheAlbum = function(url, album_id){
			var response = $http({
				method : "POST",
				url : url,
				data : {
					albumId : album_id
				}
			}).success(function(data, status, headers, config) {
				
			})
			.error(function(data, status, headers, config) {
				console.log(data, status, headers, config);
				service.errorMessage = "Error while retrieving photos from the database."
			});
			return response;
		}
		
	}
})();