(function() {
	angular.module("userDetailsModule", [ "ui.router" ]).directive(
			"focusSelect", FocusSelectFunction);

	function FocusSelectFunction($timeout) {
		var ddo = {
			restrict : 'A',
			link : function(scope, element, attributes) {
				element.on('click', function() {
					this.select();
				})
			}
		}
		return ddo;
	}
})();