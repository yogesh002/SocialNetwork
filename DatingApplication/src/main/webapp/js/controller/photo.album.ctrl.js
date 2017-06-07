(function() {
	angular.module("photoAlbum")
		.controller("photoAlbumController", PhotoAlbumController)
		.controller("viewPhotosController", ViewPhotosController)
		.controller("viewAllAlbumsController", ViewAllAlbumsController);

	ViewAllAlbumsController.$inject = ["photoAlbumService"]
	function ViewAllAlbumsController(photoAlbumService){
		var service = photoAlbumService;
		var controller = this;
		var albumsPromise = service.viewAllAlbums();
		albumsPromise.success(function(response){
			controller.albums = response;
			console.log(response)
		});
	}

	ViewPhotosController.$inject = ["photos"]
	function ViewPhotosController(photos){
		var controller = this;
		console.log(photos)
		controller.imageData = photos;
	}
	
	PhotoAlbumController.$inject = [ "photoAlbumService", "$http", "$scope" ]
	function PhotoAlbumController(photoAlbumService, $http, $scope) {
		var controller = this;
		controller.createstatus = 0;
		controller.album_name = "";
		controller.createAlbum = function(albumName) {
			var createAlbumPromise = photoAlbumService.createAlbum(albumName);
			createAlbumPromise.success(function(data) {
				if (data.response.toLowerCase() === 'success') {
					controller.albumCreated = true;
				}
			}) 
			.error(function (error) {
				console.log(error);
				controller.errorMessage = "Error while creating the album. Please try again later.";
            });;
		}
		controller.sendAlbumName = function(name){
			var response = $http({
				method : "POST",
				url : "getAlbumId",
				data : {
					albumName : name,
				}
			}).success(function(data, status, headers, config) {
				controller.confirmed = true;
				controller.successMessage = "Album "+name+" "+"successfully created !";
				console.log(data, status, headers, config);
			})
			.error(function(data, status, headers, config) {
				console.log(data, status, headers, config);
				controller.errorMessage = "Error while sending album name to the server."
			});
			return response;
		}
		  var image = new FormData();
		  $scope.chooseFiles = function (files) {
        	  console.log(files)
              angular.forEach(files, function (value, key) {
            	  console.log(key, value)
                  image.append(key, value);
              });
          };
		controller.uploadPicture = function(){
	          var response = $http({
                method: 'POST',
                url: 'uploadPhotosToAlbum',
                data : image,
                headers: {
              	  'Content-Type': undefined,
                }
			}).success(function(data, status, headers, config) {
				controller.fileuploadsuccess = true;
				controller.successMessage = "File Successfully uploaded."
				console.log("File successfully uploaded.", data, status, headers, config);
			})
			.error(function(data, status, headers, config) {
				console.log(data, status, headers, config);
				controller.errorMessage = "Error while uploading file to the server."
			});
			return response;
		}
		
	}
})();