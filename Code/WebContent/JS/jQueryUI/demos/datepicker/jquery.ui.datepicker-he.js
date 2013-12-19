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
/* Hebrew initialisation for the UI Datepicker extension. */
/* Written by Amir Hardon (ahardon at gmail dot com). */
jQuery(function($){
	$.datepicker.regional['he'] = {
		closeText: '×¡×××¨',
		prevText: '&#x3C;××§×××?',
		nextText: '×××?&#x3E;',
		currentText: '××××?',
		monthNames: ['×× ××?×¨','×¤××¨××?×¨','××¨×¥','×?×¤×¨××','××?×','××× ×',
		'××××','×?××××¡×','×¡×¤××××¨','×?××§××××¨','× ×××××¨','××¦×××¨'],
		monthNamesShort: ['×× ×','×¤××¨','××¨×¥','×?×¤×¨','××?×','××× ×',
		'××××','×?××','×¡×¤×','×?××§','× ××','××¦×'],
		dayNames: ['×¨×?×©××','×©× ×','×©×××©×','×¨×××¢×','××××©×','×©××©×','×©××ª'],
		dayNamesShort: ['×?\'','×\'','×\'','×\'','×\'','×\'','×©××ª'],
		dayNamesMin: ['×?\'','×\'','×\'','×\'','×\'','×\'','×©××ª'],
		weekHeader: 'Wk',
		dateFormat: 'dd/mm/yy',
		firstDay: 0,
		isRTL: true,
		showMonthAfterYear: false,
		yearSuffix: ''};
	$.datepicker.setDefaults($.datepicker.regional['he']);
});
