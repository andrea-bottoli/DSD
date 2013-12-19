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
 * selectable_events.js
 */
(function( $ ) {

module("selectable: events");

test( "start", function() {
	expect( 2 );
	var el = $("#selectable1");
	el.selectable({
		start: function() {
			ok( true, "drag fired start callback" );
			equal( this, el[0], "context of callback" );
		}
	});
	el.simulate( "drag", {
		dx: 20,
		dy: 20
	});
});

test( "stop", function() {
	expect( 2 );
	var el = $("#selectable1");
	el.selectable({
		start: function() {
			ok( true, "drag fired stop callback" );
			equal( this, el[0], "context of callback" );
		}
	});
	el.simulate( "drag", {
		dx: 20,
		dy: 20
	});
});

test( "mousedown: initial position of helper", function() {
	expect( 2 );

	var helperOffset,
		element = $( "#selectable1" ).selectable(),
		contentToForceScroll = $( "<div>" ).css({
			height: "10000px",
			width: "10000px"
		});

	contentToForceScroll.appendTo( "body" );
	$( window ).scrollTop( 100 ).scrollLeft( 100 );

	element.simulate( "mousedown", {
		clientX: 10,
		clientY: 10
	});

	// we do a GTE comparison here because IE7 erroneously subtracts
	// 2 pixels from a simulated mousedown for clientX/Y
	// Support: IE7
	helperOffset = $( ".ui-selectable-helper" ).offset();
	ok( helperOffset.top >= 99, "Scroll top should be accounted for." );
	ok( helperOffset.left >= 99, "Scroll left should be accounted for." );

	// Cleanup
	element.simulate( "mouseup" );
	contentToForceScroll.remove();
	$( window ).scrollTop( 0 ).scrollLeft( 0 );
});

})( jQuery );
