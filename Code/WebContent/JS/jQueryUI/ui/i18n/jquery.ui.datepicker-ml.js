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
/* Malayalam (UTF-8) initialisation for the jQuery UI date picker plugin. */
/* Written by Saji Nediyanchath (saji89@gmail.com). */
jQuery(function($){
	$.datepicker.regional['ml'] = {
		closeText: 'ശരി',
		prevText: 'മ�?ന�?നത�?തെ',
		nextText: 'അട�?ത�?തത�? ',
		currentText: 'ഇന�?ന�?',
		monthNames: ['ജന�?വരി','ഫെബ�?ര�?വരി','മാര�?�?ച�?ച�?','�?പ�?രില�?�?','മേയ�?','ജൂണ�?�?',
		'ജൂലൈ','ആഗസ�?റ�?റ�?','സെപ�?റ�?റംബര�?�?','ഒക�?ടോബര�?�?','നവംബര�?�?','ഡിസംബര�?�?'],
		monthNamesShort: ['ജന�?', 'ഫെബ�?', 'മാര�?�?', '�?പ�?രി', 'മേയ�?', 'ജൂണ�?�?',
		'ജൂലാ', 'ആഗ', 'സെപ�?', 'ഒക�?ടോ', 'നവം', 'ഡിസ'],
		dayNames: ['ഞായര�?�?', 'തിങ�?കള�?�?', 'ചൊവ�?വ', 'ബ�?ധന�?�?', 'വ�?യാഴം', 'വെള�?ളി', 'ശനി'],
		dayNamesShort: ['ഞായ', 'തിങ�?ക', 'ചൊവ�?വ', 'ബ�?ധ', 'വ�?യാഴം', 'വെള�?ളി', 'ശനി'],
		dayNamesMin: ['ഞാ','തി','ചൊ','ബ�?','വ�?യാ','വെ','ശ'],
		weekHeader: 'ആ',
		dateFormat: 'dd/mm/yy',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: ''};
	$.datepicker.setDefaults($.datepicker.regional['ml']);
});
