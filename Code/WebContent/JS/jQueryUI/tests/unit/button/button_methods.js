#-------------------------------------------------------------------------------
# Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Br?i?, Dzana Kujan, Nikola Radisavljevic, Jörn Tillmanns, Miraldi Fifo
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#   http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#-------------------------------------------------------------------------------
/*
 * button_methods.js
 */
(function($) {


module("button: methods");

test("destroy", function() {
	expect( 1 );
	domEqual( "#button", function() {
		$( "#button" ).button().button( "destroy" );
	});
});

test( "refresh: Ensure disabled state is preserved correctly.", function() {
	expect( 8 );

	var element = $( "<a href='#'></a>" );
	element.button({ disabled: true }).button( "refresh" );
	ok( element.button( "option", "disabled" ), "Anchor button should remain disabled after refresh" ); //See #8237

	element = $( "<div></div>" );
	element.button({ disabled: true }).button( "refresh" );
	ok( element.button( "option", "disabled" ), "<div> buttons should remain disabled after refresh" );

	element = $( "<button></button>" );
	element.button( { disabled: true} ).button( "refresh" );
	ok( element.button( "option", "disabled" ), "<button> should remain disabled after refresh");

	element = $( "<input type='checkbox'>" );
	element.button( { disabled: true} ).button( "refresh" );
	ok( element.button( "option", "disabled" ), "Checkboxes should remain disabled after refresh");

	element = $( "<input type='radio'>" );
	element.button( { disabled: true} ).button( "refresh" );
	ok( element.button( "option", "disabled" ), "Radio buttons should remain disabled after refresh");

	element = $( "<button></button>" );
	element.button( { disabled: true} ).prop( "disabled", false ).button( "refresh" );
	ok( !element.button( "option", "disabled" ), "Changing a <button>'s disabled property should update the state after refresh."); //See #8828

	element = $( "<input type='checkbox'>" );
	element.button( { disabled: true} ).prop( "disabled", false ).button( "refresh" );
	ok( !element.button( "option", "disabled" ), "Changing a checkbox's disabled property should update the state after refresh.");

	element = $( "<input type='radio'>" );
	element.button( { disabled: true} ).prop( "disabled", false ).button( "refresh" );
	ok( !element.button( "option", "disabled" ), "Changing a radio button's disabled property should update the state after refresh.");
});

})(jQuery);
