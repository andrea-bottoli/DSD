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
/* Chinese initialisation for the jQuery UI date picker plugin. */
/* Written by SCCY (samuelcychan@gmail.com). */
jQuery(function($){
	$.datepicker.regional['zh-HK'] = {
		closeText: 'ιι',
		prevText: '&#x3C;δΈζ',
		nextText: 'δΈζ&#x3E;',
		currentText: 'δ»ε€©',
		monthNames: ['δΈζ','δΊζ','δΈζ','εζ','δΊζ','ε­ζ',
		'δΈζ','ε«ζ','δΉ?ζ','ε??ζ','ε??δΈζ','ε??δΊζ'],
		monthNamesShort: ['δΈζ','δΊζ','δΈζ','εζ','δΊζ','ε­ζ',
		'δΈζ','ε«ζ','δΉ?ζ','ε??ζ','ε??δΈζ','ε??δΊζ'],
		dayNames: ['ζζζ₯','ζζδΈ','ζζδΊ','ζζδΈ','ζζε','ζζδΊ','ζζε­'],
		dayNamesShort: ['ε¨ζ₯','ε¨δΈ','ε¨δΊ','ε¨δΈ','ε¨ε','ε¨δΊ','ε¨ε­'],
		dayNamesMin: ['ζ₯','δΈ','δΊ','δΈ','ε','δΊ','ε­'],
		weekHeader: 'ε¨',
		dateFormat: 'dd-mm-yy',
		firstDay: 0,
		isRTL: false,
		showMonthAfterYear: true,
		yearSuffix: 'εΉ΄'};
	$.datepicker.setDefaults($.datepicker.regional['zh-HK']);
});
