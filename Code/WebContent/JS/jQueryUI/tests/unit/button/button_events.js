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
 * button_events.js
 */
(function($) {

module("button: events");

test("buttonset works with single-quote named elements (#7505)", function() {
	expect( 1 );
	$("#radio3").buttonset();
	$("#radio33").click( function(){
		ok( true, "button clicks work with single-quote named elements" );
	}).click();
});

asyncTest( "when button loses focus, ensure active state is removed (#8559)", function() {
	expect( 1 );

	var element = $( "#button" ).button();

	element.one( "keypress", function() {
		element.one( "blur", function() {
			ok( !element.is(".ui-state-active"), "button loses active state appropriately" );
			start();
		}).blur();
	});

	element.focus();
	setTimeout(function() {
		element
			.simulate( "keydown", { keyCode: $.ui.keyCode.ENTER } )
			.simulate( "keypress", { keyCode: $.ui.keyCode.ENTER } );
	});
});

})(jQuery);
