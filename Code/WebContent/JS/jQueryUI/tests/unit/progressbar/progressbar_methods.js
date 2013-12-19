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
module( "progressbar: methods" );

test( "destroy", function() {
	expect( 1 );
	domEqual( "#progressbar", function() {
		$( "#progressbar" ).progressbar().progressbar( "destroy" );
	});
});

test( "value", function() {
	expect( 3 );

	var element = $( "<div>" ).progressbar({ value: 20 });
	equal( element.progressbar( "value" ), 20, "correct value as getter" );
	strictEqual( element.progressbar( "value", 30 ), element, "chainable as setter" );
	equal( element.progressbar( "option", "value" ), 30, "correct value after setter" );
});

test( "widget", function() {
	expect( 2 );
	var element = $( "#progressbar" ).progressbar(),
		widgetElement = element.progressbar( "widget" );
	equal( widgetElement.length, 1, "one element" );
	strictEqual( widgetElement[ 0 ], element[ 0 ], "same element" );
});
