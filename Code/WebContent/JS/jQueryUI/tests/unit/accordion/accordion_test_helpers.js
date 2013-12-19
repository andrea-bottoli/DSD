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
TestHelpers.accordion = {
	equalHeight: function( accordion, height ) {
		accordion.find( ".ui-accordion-content" ).each(function() {
			equal( $( this ).outerHeight(), height );
		});
	},

	setupTeardown: function() {
		var animate = $.ui.accordion.prototype.options.animate;
		return {
			setup: function() {
				$.ui.accordion.prototype.options.animate = false;
			},
			teardown: function() {
				$.ui.accordion.prototype.options.animate = animate;
			}
		};
	},

	state: function( accordion ) {
		var expected = $.makeArray( arguments ).slice( 1 ),
			actual = accordion.find( ".ui-accordion-content" ).map(function() {
			return $( this ).css( "display" ) === "none" ? 0 : 1;
		}).get();
		QUnit.push( QUnit.equiv(actual, expected), actual, expected );
	}
};
