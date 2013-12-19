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

module( "autocomplete: methods" );

test( "destroy", function() {
	expect( 1 );
	domEqual( "#autocomplete", function() {
		$( "#autocomplete" ).autocomplete().autocomplete( "destroy" );
	});
});

test( "search, close", function() {
	expect( 6 );
	var data = [ "c++", "java", "php", "coldfusion", "javascript", "asp", "ruby", "python", "c", "scala", "groovy", "haskell", "perl" ],
		element = $( "#autocomplete" ).autocomplete({
			source: data,
			minLength: 0
		}),
		menu = element.autocomplete( "widget" );

	ok( menu.is( ":hidden" ), "menu is hidden on init" );

	element.autocomplete( "search" );
	ok( menu.is( ":visible" ), "menu is visible after search" );
	equal( menu.find( ".ui-menu-item" ).length, data.length, "all items for a blank search" );

	element.val( "has" ).autocomplete( "search" );
	equal( menu.find( ".ui-menu-item" ).text(), "haskell", "only one item for set input value" );

	element.autocomplete( "search", "ja" );
	equal( menu.find( ".ui-menu-item" ).length, 2, "only java and javascript for 'ja'" );

	element.autocomplete( "close" );
	ok( menu.is( ":hidden" ), "menu is hidden after close" );
});

test( "widget", function() {
	expect( 2 );
	var element = $( "#autocomplete" ).autocomplete(),
		widgetElement = element.autocomplete( "widget" );
	equal( widgetElement.length, 1, "one element" );
	ok( widgetElement.is( ".ui-menu" ), "menu element" );
});

}( jQuery ) );
