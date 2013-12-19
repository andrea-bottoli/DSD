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

var versions = [
		"1.6", "1.6.1", "1.6.2", "1.6.3", "1.6.4",
		"1.7", "1.7.1", "1.7.2",
		"1.8.0", "1.8.1", "1.8.2", "1.8.3",
		"1.9.0", "1.9.1",
		"git"
	],
	additionalTests = {
		// component: [ "other_test.html" ]
	};

window.testAllVersions = function( widget ) {
	QUnit.testSuites( $.map(
		[ widget + ".html" ].concat( additionalTests[ widget ] || [] ),
		function( test ) {
			return $.map( versions, function( version ) {
				return test + "?jquery=" + version;
			});
		}));
};

}());
