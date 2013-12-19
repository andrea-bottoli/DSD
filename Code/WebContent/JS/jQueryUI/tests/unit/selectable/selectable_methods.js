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
 * selectable_methods.js
 */
(function($) {

module("selectable: methods");

test("init", function() {
	expect( 5 );

	$("<div></div>").appendTo("body").selectable().remove();
	ok(true, ".selectable() called on element");

	$([]).selectable().remove();
	ok(true, ".selectable() called on empty collection");

	$("<div></div>").selectable().remove();
	ok(true, ".selectable() called on disconnected DOMElement");

	var el = $("<div></div>").selectable();
	el.selectable("option", "foo");
	el.remove();
	ok(true, "arbitrary option getter after init");

	$("<div></div>").selectable().selectable("option", "foo", "bar").remove();
	ok(true, "arbitrary option setter after init");
});

test("destroy", function() {
	expect( 4 );

	$("<div></div>").appendTo("body").selectable().selectable("destroy").remove();
	ok(true, ".selectable('destroy') called on element");

	$([]).selectable().selectable("destroy").remove();
	ok(true, ".selectable('destroy') called on empty collection");

	$("<div></div>").selectable().selectable("destroy").remove();
	ok(true, ".selectable('destroy') called on disconnected DOMElement");

	var expected = $("<div></div>").selectable(),
		actual = expected.selectable("destroy");
	equal(actual, expected, "destroy is chainable");
});

test("enable", function() {
	expect(3);
	var expected, actual,
		fired = false,
		el = $("#selectable1");

	el.selectable({
		disabled: true,
		start: function() { fired = true; }
	});
	el.simulate( "drag", {
		dx: 20,
		dy: 20
	});
	equal(fired, false, "start fired");
	el.selectable("enable");
	el.simulate( "drag", {
		dx: 20,
		dy: 20
	});
	equal(fired, true, "start fired");
	el.selectable("destroy");

	expected = $("<div></div>").selectable();
	actual = expected.selectable("enable");
	equal(actual, expected, "enable is chainable");
});

test("disable", function() {
	expect(3);
	var expected, actual,
		fired = false,
		el = $("#selectable1");

	el.selectable({
		disabled: false,
		start: function() { fired = true; }
	});
	el.simulate( "drag", {
		dx: 20,
		dy: 20
	});
	equal(fired, true, "start fired");
	el.selectable("disable");
	fired = false;

	el.simulate( "drag", {
		dx: 20,
		dy: 20
	});
	equal(fired, false, "start fired");
	el.selectable("destroy");

	expected = $("<div></div>").selectable();
	actual = expected.selectable("disable");
	equal(actual, expected, "disable is chainable");
});

})(jQuery);
