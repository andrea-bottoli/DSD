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
(function() {

var lastItem,
	log = [];

TestHelpers.menu = {
	log: function( message, clear ) {
		if ( clear ) {
			log.length = 0;
		}
		if ( message === undefined ) {
			message = lastItem;
		}
		log.push( $.trim( message ) );
	},

	logOutput: function() {
		return log.join( "," );
	},

	clearLog: function() {
		log.length = 0;
	},

	click: function( menu, item ) {
		lastItem = item;
		menu.children( ":eq(" + item + ")" ).find( "a:first" ).trigger( "click" );
	}
};

})();
