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
TestHelpers.commonWidgetTests( "draggable", {
	defaults: {
		appendTo: "parent",
		axis: false,
		cancel: "input,textarea,button,select,option",
		connectToSortable: false,
		containment: false,
		cursor: "auto",
		cursorAt: false,
		disabled: false,
		grid: false,
		handle: false,
		helper: "original",
		opacity: false,
		refreshPositions: false,
		revert: false,
		revertDuration: 500,
		scroll: true,
		scrollSensitivity: 20,
		scrollSpeed: 20,
		scope: "default",
		snap: false,
		snapMode: "both",
		snapTolerance: 20,
		stack: false,
		zIndex: false,

		//todo: remove the following option checks when interactions are rewritten:
		addClasses: true,
		delay: 0,
		distance: 1,
		iframeFix: false,

		// callbacks
		create: null,
		drag: null,
		start: null,
		stop: null
	}
});
