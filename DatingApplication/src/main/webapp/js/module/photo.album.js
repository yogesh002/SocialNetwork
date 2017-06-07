(function() {
	angular.module("photoAlbum", ["ui.router"])
	.directive("chooseFiles", ChooseFiles);
	
	ChooseFiles.$inject = ["$parse"]
	function ChooseFiles($parse){
		var ddo = {
			link : function(scope, element, attributes, controller){
				var onChange = $parse(attributes.chooseFiles);
				element.on('change', function (event) {
		            onChange(scope, { files: event.target.files });
		        });
			}
		}
		 return ddo;
	}
})();
