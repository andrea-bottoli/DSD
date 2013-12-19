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
/* Japanese initialisation for the jQuery UI date picker plugin. */
/* Written by Kentaro SATO (kentaro@ranvis.com). */
jQuery(function($){
	$.datepicker.regional['ja'] = {
		closeText: '้ใ?ใ',
		prevText: '&#x3C;ๅ?',
		nextText: 'ๆฌก&#x3E;',
		currentText: 'ไปๆฅ',
		monthNames: ['1ๆ','2ๆ','3ๆ','4ๆ','5ๆ','6ๆ',
		'7ๆ','8ๆ','9ๆ','10ๆ','11ๆ','12ๆ'],
		monthNamesShort: ['1ๆ','2ๆ','3ๆ','4ๆ','5ๆ','6ๆ',
		'7ๆ','8ๆ','9ๆ','10ๆ','11ๆ','12ๆ'],
		dayNames: ['ๆฅๆๆฅ','ๆๆๆฅ','็?ซๆๆฅ','ๆฐดๆๆฅ','ๆจๆๆฅ','้ๆๆฅ','ๅๆๆฅ'],
		dayNamesShort: ['ๆฅ','ๆ','็?ซ','ๆฐด','ๆจ','้','ๅ'],
		dayNamesMin: ['ๆฅ','ๆ','็?ซ','ๆฐด','ๆจ','้','ๅ'],
		weekHeader: '้ฑ',
		dateFormat: 'yy/mm/dd',
		firstDay: 0,
		isRTL: false,
		showMonthAfterYear: true,
		yearSuffix: 'ๅนด'};
	$.datepicker.setDefaults($.datepicker.regional['ja']);
});
