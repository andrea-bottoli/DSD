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
 * droppable_options.js
 */
(function($) {

module("droppable: options");

/*
test("{ accept '*' }, default ", function() {
	ok(false, 'missing test - untested code is broken code');
});

test("{ accept: Selector }", function() {
	ok(false, 'missing test - untested code is broken code');
});

test("{ accept: function(draggable) }", function() {
	ok(false, 'missing test - untested code is broken code');
});

test("activeClass", function() {
	ok(false, 'missing test - untested code is broken code');
});
*/
test("{ addClasses: true }, default", function() {
	expect( 1 );
	var el = $("<div></div>").droppable({ addClasses: true });
	ok(el.is(".ui-droppable"), "'ui-droppable' class added");
	el.droppable("destroy");
});

test("{ addClasses: false }", function() {
	expect( 1 );
	var el = $("<div></div>").droppable({ addClasses: false });
	ok(!el.is(".ui-droppable"), "'ui-droppable' class not added");
	el.droppable("destroy");
});
/*
test("greedy", function() {
	ok(false, 'missing test - untested code is broken code');
});

test("hoverClass", function() {
	ok(false, 'missing test - untested code is broken code');
});

test("scope", function() {
	ok(false, 'missing test - untested code is broken code');
});

test("tolerance, fit", function() {
	ok(false, 'missing test - untested code is broken code');
});

test("tolerance, intersect", function() {
	ok(false, 'missing test - untested code is broken code');
});

test("tolerance, pointer", function() {
	ok(false, 'missing test - untested code is broken code');
});

test("tolerance, touch", function() {
	ok(false, 'missing test - untested code is broken code');
});
*/
})(jQuery);
