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

module( "droppable: events" );

test( "droppable destruction/recreation on drop event", function() {
	expect( 1 );

	var config = {
			activeClass: "active",
			drop: function() {
				var element = $( this ),
					newDroppable = $( "<div>" )
						.css({ width: 100, height: 100 })
						.text( "Droppable" );
				element.after( newDroppable );
				element.remove();
				newDroppable.droppable( config );
			}
		},

		draggable = $( "#draggable1" ).draggable(),
		droppable1 = $( "#droppable1" ).droppable( config ),
		droppable2 = $( "#droppable2" ).droppable( config ),

		droppableOffset = droppable1.offset(),
		draggableOffset = draggable.offset(),
		dx = droppableOffset.left - draggableOffset.left,
		dy = droppableOffset.top - draggableOffset.top;

	draggable.simulate( "drag", {
		dx: dx,
		dy: dy
	});

	ok( !droppable2.hasClass( "active" ), "subsequent droppable no longer active" );
});



// todo: comment the following in when ready to actually test
/*
test("activate", function() {
	ok(false, 'missing test - untested code is broken code');
});

test("deactivate", function() {
	ok(false, 'missing test - untested code is broken code');
});

test("over", function() {
	ok(false, 'missing test - untested code is broken code');
});

test("out", function() {
	ok(false, 'missing test - untested code is broken code');
});

test("drop", function() {
	ok(false, 'missing test - untested code is broken code');
});
*/

})( jQuery );
