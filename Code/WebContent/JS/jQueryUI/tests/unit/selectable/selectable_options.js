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
 * selectable_options.js
 */
(function($) {

module("selectable: options");

test("autoRefresh", function() {
	expect(3);

	var actual = 0,
		el = $("#selectable1"),
		sel = $("*", el),
		selected = function() { actual += 1; };

	el = $("#selectable1").selectable({ autoRefresh: false,	selected: selected });
	sel.hide();
	el.simulate( "drag", {
		dx: 1000,
		dy: 1000
	});
	equal(actual, sel.length);
	el.selectable("destroy");

	actual = 0;
	sel.show();
	el = $("#selectable1").selectable({ autoRefresh: true,	selected: selected });
	sel.hide();
	el.simulate( "drag", {
		dx: 1000,
		dy: 1000
	});
	equal(actual, 0);

	sel.show();
	$( sel[ 0 ] ).simulate( "drag", {
		dx: 1000,
		dy: 1000
	});
	equal(actual, sel.length);

	el.selectable("destroy");
	sel.show();
});

test("filter", function() {
	expect(2);

	var actual =0,
		el = $("#selectable1"),
		sel = $("*", el),
		selected = function() { actual += 1; };


	el = $("#selectable1").selectable({ filter: ".special", selected: selected });
	el.simulate( "drag", {
		dx: 1000,
		dy: 1000
	});
	ok(sel.length !== 1, "this test assumes more than 1 selectee");
	equal(actual, 1);
	el.selectable("destroy");
});

})(jQuery);
