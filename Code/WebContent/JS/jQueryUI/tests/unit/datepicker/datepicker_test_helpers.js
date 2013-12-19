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
TestHelpers.datepicker = {
	addMonths: function(date, offset) {
		var maxDay = 32 - new Date(date.getFullYear(), date.getMonth() + offset, 32).getDate();
		date.setDate(Math.min(date.getDate(), maxDay));
		date.setMonth(date.getMonth() + offset);
		return date;
	},
	equalsDate: function(d1, d2, message) {
		if (!d1 || !d2) {
			ok(false, message + " - missing date");
			return;
		}
		d1 = new Date(d1.getFullYear(), d1.getMonth(), d1.getDate());
		d2 = new Date(d2.getFullYear(), d2.getMonth(), d2.getDate());
		equal(d1.toString(), d2.toString(), message);
	},
	init: function(id, options) {
		$.datepicker.setDefaults($.datepicker.regional[""]);
		return $(id).datepicker($.extend({showAnim: ""}, options || {}));
	},
	onFocus: function( element, onFocus ) {
		var fn = function( event ){
			if( !event.originalEvent ) {
				return;
			}
			element.unbind( "focus", fn );
			onFocus();
		};

		element.bind( "focus", fn )[ 0 ].focus();
	},
	PROP_NAME: "datepicker"
};
