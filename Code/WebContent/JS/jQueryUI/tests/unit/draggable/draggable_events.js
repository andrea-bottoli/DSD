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
 * draggable_events.js
 */
(function( $ ) {

var element;

module( "draggable: events", {
	setup: function() {
		element = $("<div>").appendTo("#qunit-fixture");
	},
	teardown: function() {
		element.draggable("destroy");
	}
});

test( "callbacks occurrence count", function() {
	expect( 3 );

	var start = 0,
		stop = 0,
		dragc = 0;

	element.draggable({
		start: function() {
			start++;
		},
		drag: function() {
			dragc++;
		},
		stop: function() {
			stop++;
		}
	});

	element.simulate( "drag", {
		dx: 10,
		dy: 10
	});

	equal( start, 1, "start callback should happen exactly once" );
	equal( dragc, 3, "drag callback should happen exactly once per mousemove" );
	equal( stop, 1, "stop callback should happen exactly once" );
});

test( "stopping the start callback", function() {
	expect( 3 );

	var start = 0,
		stop = 0,
		dragc = 0;

	element.draggable({
		start: function() {
			start++;
			return false;
		},
		drag: function() {
			dragc++;
		},
		stop: function() {
			stop++;
		}
	});

	element.simulate( "drag", {
		dx: 10,
		dy: 10
	});

	equal( start, 1, "start callback should happen exactly once" );
	equal( dragc, 0, "drag callback should not happen at all" );
	equal( stop, 0, "stop callback should not happen if there wasnt even a start" );
});

test( "stopping the drag callback", function() {
	expect( 2 );

	var start = 0,
		stop = 0,
		dragc = 0;

	element.draggable({
		start: function() {
			start++;
		},
		drag: function() {
			dragc++;
			return false;
		},
		stop: function() {
			stop++;
		}
	});

	element.simulate( "drag", {
		dx: 10,
		dy: 10
	});

	equal( start, 1, "start callback should happen exactly once" );
	equal( stop, 1, "stop callback should happen, as we need to actively stop the drag" );
});

test( "stopping the stop callback", function() {
	expect( 1 );

	element.draggable({
		helper: "clone",
		stop: function() {
			return false;
		}
	});

	element.simulate( "drag", {
		dx: 10,
		dy: 10
	});

	ok( element.data("ui-draggable").helper, "the clone should not be deleted if the stop callback is stopped" );


});

})( jQuery );
