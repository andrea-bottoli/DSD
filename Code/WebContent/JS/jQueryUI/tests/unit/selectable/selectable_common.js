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
TestHelpers.commonWidgetTests("selectable", {
	defaults: {
		appendTo: "body",
		autoRefresh: true,
		cancel: "input,textarea,button,select,option",
		delay: 0,
		disabled: false,
		distance: 0,
		filter: "*",
		tolerance: "touch",

		// callbacks
		create: null,
		selected: null,
		selecting: null,
		start: null,
		stop: null,
		unselected: null,
		unselecting: null
	}
});
