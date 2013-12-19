#-------------------------------------------------------------------------------
# Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Br?i?, Dzana Kujan, Nikola Radisavljevic, Jφrn Tillmanns, Miraldi Fifo
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
/* Khmer initialisation for the jQuery calendar extension. */
/* Written by Chandara Om (chandara.teacher@gmail.com). */
jQuery(function($){
	$.datepicker.regional['km'] = {
		closeText: 'ααααΎβαα½α',
		prevText: 'αα»α',
		nextText: 'αααααΆαα',
		currentText: 'α?αααβαα?α',
		monthNames: ['ααααΆ','αα»αααα','ααΈααΆ','αα?ααΆ','α§αααΆ','αα·α?α»ααΆ',
		'ααααααΆ','ααΈα αΆ','αααααΆ','α?α»ααΆ','αα·αααα·ααΆ','ααααΌ'],
		monthNamesShort: ['ααααΆ','αα»αααα','ααΈααΆ','αα?ααΆ','α§αααΆ','αα·α?α»ααΆ',
		'ααααααΆ','ααΈα αΆ','αααααΆ','α?α»ααΆ','αα·αααα·ααΆ','ααααΌ'],
		dayNames: ['α’αΆαα·α?αα', 'αααα', 'α’ααααΆα', 'αα»α', 'αααα αααα?α·α?', 'αα»ααα', 'αααα?'],
		dayNamesShort: ['α’αΆ', 'α', 'α’', 'αα»', 'αααα ', 'αα»', 'αα'],
		dayNamesMin: ['α’αΆ', 'α', 'α’', 'αα»', 'αααα ', 'αα»', 'αα'],
		weekHeader: 'αααααΆα α?',
		dateFormat: 'dd-mm-yy',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: ''};
	$.datepicker.setDefaults($.datepicker.regional['km']);
});
