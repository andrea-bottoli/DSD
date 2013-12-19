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
module( "effect.scale: Scale" );

function run( position, v, h, vo, ho ) {
	var desc = "End Position Correct: " + position + " (" + v + "," + h + ") - origin: (" + vo + "," + ho + ")";
	asyncTest( desc, function() {
		expect( 2 );
		function complete() {
			equal( parseInt( test.css( h ), 10 ), target[ h ], "Horizontal Position Correct " + desc );
			equal( parseInt( test.css( v ), 10 ), target[ v ], "Vertical Position Correct " + desc );
			start();
		}
		var test = $( ".testScale" ),
			css = {
				position: position
			},
			effect = {
				effect: "scale",
				mode: "effect",
				percent: 200,
				origin: [ vo, ho ],
				complete: complete,
				duration: 1
			},
			target = {},
			relative = position === "relative";

		css[ h ] = 33;
		css[ v ] = 33;
		target[ h ] = h === ho ? css[ h ] : ho === "center" ? css[ h ] - 35 : css[ h ] - 70;
		target[ v ] = v === vo ? css[ v ] : vo === "middle" ? css[ v ] - 35 : css[ v ] - 70;
		if ( relative && h === "right" ) {
			target[ h ] += 70;
		}
		if ( relative && v === "bottom" ) {
			target[ v ] += 70;
		}
		test.css( css );
		test.effect( effect );
	});
}

function suite( position ) {
	run( position, "top", "left", "top", "left" );
	run( position, "top", "left", "middle", "center" );
	run( position, "top", "left", "bottom", "right" );
	/* Firefox is currently not capable of supporting detection of bottom and right....
	run( position, "bottom", "right", "top", "left" );
	run( position, "bottom", "right", "middle", "center" );
	run( position, "bottom", "right", "bottom", "right" );
	*/
}

$(function() {
	suite( "absolute" );
	suite( "relative" );
	var fixedElem = $( "<div>" )
		.css({
			position: "fixed",
			top: 10
		})
		.appendTo( "body" );
	if ( fixedElem.offset().top === 10 ) {
		suite( "fixed" );
	}
});

})( jQuery );
