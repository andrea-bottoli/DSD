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
(function( $ ) {

module( "tooltip: events" );

test( "programmatic triggers", function() {
	expect( 4 );
	var tooltip,
		element = $( "#tooltipped1" ).tooltip();

	element.one( "tooltipopen", function( event, ui ) {
		tooltip = ui.tooltip;
		ok( !( "originalEvent" in event ), "open" );
		strictEqual( ui.tooltip[0],
			$( "#" + element.data( "ui-tooltip-id" ) )[0], "ui.tooltip" );
	});
	element.tooltip( "open" );

	element.one( "tooltipclose", function( event, ui ) {
		ok( !( "originalEvent" in event ), "close" );
		strictEqual( ui.tooltip[0], tooltip[0], "ui.tooltip" );
	});
	element.tooltip( "close" );
});

test( "mouse events", function() {
	expect( 2 );
	var element = $( "#tooltipped1" ).tooltip();

	element.bind( "tooltipopen", function( event ) {
		deepEqual( event.originalEvent.type, "mouseover" );
	});
	element.trigger( "mouseover" );

	element.bind( "tooltipclose", function( event ) {
		deepEqual( event.originalEvent.type, "mouseleave" );
	});
	element.trigger( "focusout" );
	element.trigger( "mouseleave" );
});

test( "focus events", function() {
	expect( 2 );
	var element = $( "#tooltipped1" ).tooltip();

	element.bind( "tooltipopen", function( event ) {
		deepEqual( event.originalEvent.type, "focusin" );
	});
	element.trigger( "focusin" );

	element.bind( "tooltipclose", function( event ) {
		deepEqual( event.originalEvent.type, "focusout" );
	});
	element.trigger( "mouseleave" );
	element.trigger( "focusout" );
});

}( jQuery ) );
