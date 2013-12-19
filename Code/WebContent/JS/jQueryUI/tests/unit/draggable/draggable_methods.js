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
 * draggable_methods.js
 */
(function( $ ) {

var element;

module( "draggable: methods", {
	setup: function() {
		element = $("<div style='background: green; width: 200px; height: 100px; position: absolute; top: 10px; left: 10px;'><span>Absolute</span></div>").appendTo("#qunit-fixture");
	},
	teardown: function() {
		element.remove();
	}
});

test( "init", function() {
	expect( 5 );

	element.draggable();
	ok( true, ".draggable() called on element" );

	$([]).draggable();
	ok( true, ".draggable() called on empty collection" );

	$("<div></div>").draggable();
	ok( true, ".draggable() called on disconnected DOMElement" );

	element.draggable( "option", "foo" );
	ok( true, "arbitrary option getter after init" );

	element.draggable( "option", "foo", "bar" );
	ok( true, "arbitrary option setter after init" );
});

test( "destroy", function() {
	expect( 4 );

	element.draggable().draggable("destroy");
	ok( true, ".draggable('destroy') called on element" );

	$([]).draggable().draggable("destroy");
	ok( true, ".draggable('destroy') called on empty collection" );

	element.draggable().draggable("destroy");
	ok( true, ".draggable('destroy') called on disconnected DOMElement" );

	var expected = element.draggable(),
		actual = expected.draggable("destroy");
	equal( actual, expected, "destroy is chainable" );
});

test( "enable", function() {
	expect( 7 );

	element.draggable({ disabled: true });
	TestHelpers.draggable.shouldNotMove( element, ".draggable({ disabled: true })" );

	element.draggable("enable");
	TestHelpers.draggable.shouldMove( element, ".draggable('enable')" );
	equal( element.draggable( "option", "disabled" ), false, "disabled option getter" );

	element.draggable("destroy");
	element.draggable({ disabled: true });
	TestHelpers.draggable.shouldNotMove( element, ".draggable({ disabled: true })" );

	element.draggable( "option", "disabled", false );
	equal(element.draggable( "option", "disabled" ), false, "disabled option setter" );
	TestHelpers.draggable.shouldMove( element, ".draggable('option', 'disabled', false)" );

	var expected = element.draggable(),
		actual = expected.draggable("enable");
	equal( actual, expected, "enable is chainable" );
});

test( "disable", function() {
	expect( 7 );

	element = $("#draggable2").draggable({ disabled: false });
	TestHelpers.draggable.shouldMove( element, ".draggable({ disabled: false })" );

	element.draggable("disable");
	TestHelpers.draggable.shouldNotMove( element, ".draggable('disable')" );
	equal( element.draggable( "option", "disabled" ), true, "disabled option getter" );

	element.draggable("destroy");
	element.draggable({ disabled: false });
	TestHelpers.draggable.shouldMove( element, ".draggable({ disabled: false })" );

	element.draggable( "option", "disabled", true );
	equal( element.draggable( "option", "disabled" ), true, "disabled option setter" );
	TestHelpers.draggable.shouldNotMove( element, ".draggable('option', 'disabled', true)" );

	var expected = element.draggable(),
		actual = expected.draggable("disable");
	equal( actual, expected, "disable is chainable" );
});

})( jQuery );
