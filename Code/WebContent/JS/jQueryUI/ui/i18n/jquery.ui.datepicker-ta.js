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
/* Tamil (UTF-8) initialisation for the jQuery UI date picker plugin. */
/* Written by S A Sureshkumar (saskumar@live.com). */
jQuery(function($){
	$.datepicker.regional['ta'] = {
		closeText: 'மூட�?',
		prevText: 'ம�?ன�?னையத�?',
		nextText: 'அட�?த�?தத�?',
		currentText: 'இன�?ற�?',
		monthNames: ['தை','மாசி','பங�?க�?னி','சித�?திரை','வைகாசி','ஆனி',
		'ஆடி','ஆவணி','ப�?ரட�?டாசி','�?ப�?பசி','கார�?த�?திகை','மார�?கழி'],
		monthNamesShort: ['தை','மாசி','பங�?','சித�?','வைகா','ஆனி',
		'ஆடி','ஆவ','ப�?ர','�?ப�?','கார�?','மார�?'],
		dayNames: ['ஞாயிற�?ற�?க�?கிழமை','திங�?கட�?கிழமை','செவ�?வாய�?க�?கிழமை','ப�?தன�?கிழமை','வியாழக�?கிழமை','வெள�?ளிக�?கிழமை','சனிக�?கிழமை'],
		dayNamesShort: ['ஞாயிற�?','திங�?கள�?','செவ�?வாய�?','ப�?தன�?','வியாழன�?','வெள�?ளி','சனி'],
		dayNamesMin: ['ஞா','தி','செ','ப�?','வி','வெ','ச'],
		weekHeader: '�?е',
		dateFormat: 'dd/mm/yy',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: ''};
	$.datepicker.setDefaults($.datepicker.regional['ta']);
});
