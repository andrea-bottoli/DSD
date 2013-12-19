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
module( "progressbar: events" );

test( "create", function() {
	expect( 1 );
	$( "#progressbar" ).progressbar({
		value: 5,
		create: function() {
			equal( $( this ).progressbar( "value" ), 5, "Correct value at create" );
		},
		change: function() {
			ok( false, "create has triggered change()" );
		}
	});
});

test( "change", function() {
	expect( 2 );
	var element = $( "#progressbar" ).progressbar();

	element.one( "progressbarchange", function() {
		equal( element.progressbar( "value" ), 5, "change triggered for middle value" );
	});
	element.progressbar( "value", 5 );
	element.one( "progressbarchange", function() {
		equal( element.progressbar( "value" ), 100, "change triggered for final value" );
	});
	element.progressbar( "value", 100 );
});

test( "complete", function() {
	expect( 5 );
	var value,
		changes = 0,
		element = $( "#progressbar" ).progressbar({
			change: function() {
				changes++;
				equal( element.progressbar( "value" ), value, "change at " + value );
			},
			complete: function() {
				equal( changes, 3, "complete triggered after change and not on indeterminate" );
				equal( element.progressbar( "value" ), 100, "value is 100" );
			}
		});

	value = 5;
	element.progressbar( "value", value );
	value = false;
	element.progressbar( "value", value );
	value = 100;
	element.progressbar( "value", value );
});
