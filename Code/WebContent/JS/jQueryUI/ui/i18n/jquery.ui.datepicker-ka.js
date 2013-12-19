#-------------------------------------------------------------------------------
# Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Br?i?, Dzana Kujan, Nikola Radisavljevic, J๖rn Tillmanns, Miraldi Fifo
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
/* Georgian (UTF-8) initialisation for the jQuery UI date picker plugin. */
/* Written by Lado Lomidze (lado.lomidze@gmail.com). */
jQuery(function($){
	$.datepicker.regional['ka'] = {
		closeText: 'แแ?แฎแฃแ แแ?',
		prevText: '&#x3c; แฌแแแ?',
		nextText: 'แจแแแแแแ &#x3e;',
		currentText: 'แแฆแแก',
		monthNames: ['แแ?แแแ?แ แ','แแแแแ แแ?แแ','แแ?แ แขแ','แ?แแ แแแ','แแ?แแกแ','แแแแแกแ', 'แแแแแกแ','แ?แแแแกแขแ?','แกแแฅแขแแแแแ แ','แ?แฅแขแ?แแแแ แ','แแ?แแแแแ แ','แแแแแแแแ แ'],
		monthNamesShort: ['แแ?แ','แแแ','แแ?แ ','แ?แแ ','แแ?แ','แแแ', 'แแแ','แ?แแ','แกแแฅ','แ?แฅแข','แแ?แ','แแแ'],
		dayNames: ['แแแแ แ?','แ?แ แจแ?แแ?แแ','แกแ?แแจแ?แแ?แแ','แ?แแฎแจแ?แแ?แแ','แฎแฃแแจแ?แแ?แแ','แแ?แ แ?แกแแแแ','แจแ?แแ?แแ'],
		dayNamesShort: ['แแ','แ?แ แจ','แกแ?แ','แ?แแฎ','แฎแฃแ','แแ?แ ','แจแ?แ'],
		dayNamesMin: ['แแ','แ?แ แจ','แกแ?แ','แ?แแฎ','แฎแฃแ','แแ?แ ','แจแ?แ'],
		weekHeader: 'แแแแ แ?',
		dateFormat: 'dd-mm-yy',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: ''};
	$.datepicker.setDefaults($.datepicker.regional['ka']);
});
