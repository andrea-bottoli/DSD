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
 * resizable_events.js
 */
(function($) {

module("resizable: events");

test("start", function() {

	expect(5);

	var count = 0,
		handle = ".ui-resizable-se";

	$("#resizable1").resizable({
		handles: "all",
		start: function(event, ui) {
			equal( ui.size.width, 100, "compare width" );
			equal( ui.size.height, 100, "compare height" );
			equal( ui.originalSize.width, 100, "compare original width" );
			equal( ui.originalSize.height, 100, "compare original height" );
			count++;
		}
	});

	TestHelpers.resizable.drag(handle, 50, 50);

	equal(count, 1, "start callback should happen exactly once");

});

test( "resize", function() {

	expect( 9 );

	var count = 0,
		handle = ".ui-resizable-se";

	$("#resizable1").resizable({
		handles: "all",
		resize: function( event, ui ) {
			if ( count === 0 ) {
				equal( ui.size.width, 125, "compare width" );
				equal( ui.size.height, 125, "compare height" );
				equal( ui.originalSize.width, 100, "compare original width" );
				equal( ui.originalSize.height, 100, "compare original height" );
			} else {
				equal( ui.size.width, 150, "compare width" );
				equal( ui.size.height, 150, "compare height" );
				equal( ui.originalSize.width, 100, "compare original width" );
				equal( ui.originalSize.height, 100, "compare original height" );
			}
			count++;
		}
	});

	TestHelpers.resizable.drag( handle, 50, 50 );

	equal( count, 2, "resize callback should happen exactly once per size adjustment" );

});

test( "resize (min/max dimensions)", function() {

	expect( 5 );

	var count = 0,
		handle = ".ui-resizable-se";

	$("#resizable1").resizable({
		handles: "all",
		minWidth: 60,
		minHeight: 60,
		maxWidth: 100,
		maxHeight: 100,
		resize: function( event, ui ) {
			equal( ui.size.width, 60, "compare width" );
			equal( ui.size.height, 60, "compare height" );
			equal( ui.originalSize.width, 100, "compare original width" );
			equal( ui.originalSize.height, 100, "compare original height" );
			count++;
		}
	});

	TestHelpers.resizable.drag( handle, -200, -200 );

	equal( count, 1, "resize callback should happen exactly once per size adjustment" );

});

test( "resize (containment)", function() {

	expect( 5 );

	var count = 0,
		handle = ".ui-resizable-se",
		container = $("#resizable1").wrap("<div>").parent().css({
			height: "100px",
			width: "100px"
		});

	$("#resizable1").resizable({
		handles: "all",
		containment: container,
		resize: function( event, ui ) {
			equal( ui.size.width, 10, "compare width" );
			equal( ui.size.height, 10, "compare height" );
			equal( ui.originalSize.width, 100, "compare original width" );
			equal( ui.originalSize.height, 100, "compare original height" );
			count++;
		}
	});

	// Prove you can't resize outside containment by dragging southeast corner southeast
	TestHelpers.resizable.drag( handle, 100, 100 );

	// Prove you can't resize outside containment by dragging southeast corner northwest
	TestHelpers.resizable.drag( handle, -200, -200 );

	equal( count, 1, "resize callback should happen exactly once per size adjustment" );

});

test("resize (grid)", function() {

	expect(5);

	var count = 0,
		handle = ".ui-resizable-se";

	$("#resizable1").resizable({
		handles: "all",
		grid: 50,
		resize: function(event, ui) {
			equal( ui.size.width, 150, "compare width" );
			equal( ui.size.height, 150, "compare height" );
			equal( ui.originalSize.width, 100, "compare original width" );
			equal( ui.originalSize.height, 100, "compare original height" );
			count++;
		}
	});

	TestHelpers.resizable.drag(handle, 50, 50);

	equal(count, 1, "resize callback should happen exactly once per grid-unit size adjustment");

});

test("stop", function() {

	expect(5);

	var count = 0,
		handle = ".ui-resizable-se";

	$("#resizable1").resizable({
		handles: "all",
		stop: function(event, ui) {
			equal( ui.size.width, 150, "compare width" );
			equal( ui.size.height, 150, "compare height" );
			equal( ui.originalSize.width, 100, "compare original width" );
			equal( ui.originalSize.height, 100, "compare original height" );
			count++;
		}
	});

	TestHelpers.resizable.drag(handle, 50, 50);

	equal(count, 1, "stop callback should happen exactly once");

});

})(jQuery);
