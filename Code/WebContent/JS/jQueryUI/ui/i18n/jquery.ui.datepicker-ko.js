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
/* Korean initialisation for the jQuery calendar extension. */
/* Written by DaeKwon Kang (ncrash.dk@gmail.com), Edited by Genie. */
jQuery(function($){
	$.datepicker.regional['ko'] = {
		closeText: 'λ«κΈ°',
		prevText: 'μ?΄μ λ¬',
		nextText: 'λ€μ?λ¬',
		currentText: 'μ€λ',
		monthNames: ['1μ','2μ','3μ','4μ','5μ','6μ',
		'7μ','8μ','9μ','10μ','11μ','12μ'],
		monthNamesShort: ['1μ','2μ','3μ','4μ','5μ','6μ',
		'7μ','8μ','9μ','10μ','11μ','12μ'],
		dayNames: ['μ?Όμμ?Ό','μμμ?Ό','νμμ?Ό','μμμ?Ό','λͺ©μμ?Ό','κΈμμ?Ό','ν μμ?Ό'],
		dayNamesShort: ['μ?Ό','μ','ν','μ','λͺ©','κΈ','ν '],
		dayNamesMin: ['μ?Ό','μ','ν','μ','λͺ©','κΈ','ν '],
		weekHeader: 'Wk',
		dateFormat: 'yy-mm-dd',
		firstDay: 0,
		isRTL: false,
		showMonthAfterYear: true,
		yearSuffix: 'λ'};
	$.datepicker.setDefaults($.datepicker.regional['ko']);
});
