#-------------------------------------------------------------------------------
# Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Br?i?, Dzana Kujan, Nikola Radisavljevic, J�rn Tillmanns, Miraldi Fifo
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

module( "tooltip: methods" );

test( "destroy", function() {
	expect( 3 );
	var element = $( "#tooltipped1" );

	domEqual( "#tooltipped1", function() {
		element.tooltip().tooltip( "destroy" );
	});

	// make sure that open tooltips are removed on destroy
	domEqual( "#tooltipped1", function() {
		element
			.tooltip()
			.tooltip( "open", $.Event( "mouseover", { target: element[0] }) )
			.tooltip( "destroy" );
	});
	equal( $( ".ui-tooltip" ).length, 0 );
});

test( "open/close", function() {
	expect( 3 );
	$.fx.off = true;
	var tooltip,
		element = $( "#tooltipped1" ).tooltip();
	equal( $( ".ui-tooltip" ).length, 0, "no tooltip on init" );

	element.tooltip( "open" );
	tooltip = $( "#" + element.data( "ui-tooltip-id" ) );
	ok( tooltip.is( ":visible" ) );

	element.tooltip( "close" );
	ok( tooltip.is( ":hidden" ) );
	$.fx.off = false;
});

// #8626 - Calling open() without an event
test( "open/close with tracking", function() {
	expect( 3 );
	$.fx.off = true;
	var tooltip,
		element = $( "#tooltipped1" ).tooltip({ track: true });
	equal( $( ".ui-tooltip" ).length, 0, "no tooltip on init" );

	element.tooltip( "open" );
	tooltip = $( "#" + element.data( "ui-tooltip-id" ) );
	ok( tooltip.is( ":visible" ) );

	element.tooltip( "close" );
	ok( tooltip.is( ":hidden" ) );
	$.fx.off = false;
});

test( "enable/disable", function() {
	expect( 7 );
	$.fx.off = true;
	var tooltip,
		element = $( "#tooltipped1" ).tooltip();
	equal( $( ".ui-tooltip" ).length, 0, "no tooltip on init" );

	element.tooltip( "open" );
	tooltip = $( "#" + element.data( "ui-tooltip-id" ) );
	ok( tooltip.is( ":visible" ) );

	element.tooltip( "disable" );
	equal( $( ".ui-tooltip" ).length, 0, "no tooltip when disabled" );
	// support: jQuery <1.6.2
	// support: IE <8
	// We should use strictEqual( ..., undefined ) when dropping jQuery 1.6.1 support (or IE6/7)
	ok( !tooltip.attr( "title" ), "title removed on disable" );

	element.tooltip( "open" );
	equal( $( ".ui-tooltip" ).length, 0, "open does nothing when disabled" );

	element.tooltip( "enable" );
	equal( element.attr( "title" ), "anchortitle", "title restored on enable" );

	element.tooltip( "open" );
	tooltip = $( "#" + element.data( "ui-tooltip-id" ) );
	ok( tooltip.is( ":visible" ) );
	$.fx.off = false;
});

test( "widget", function() {
	expect( 2 );
	var element = $( "#tooltipped1" ).tooltip(),
		widgetElement = element.tooltip( "widget" );
	equal( widgetElement.length, 1, "one element" );
	strictEqual( widgetElement[ 0 ], element[ 0 ], "same element" );
});

}( jQuery ) );
