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
/* Greek (el) initialisation for the jQuery UI date picker plugin. */
/* Written by Alex Cicovic (http://www.alexcicovic.com) */
jQuery(function($){
	$.datepicker.regional['el'] = {
		closeText: 'Κλείσιμο',
		prevText: 'Π�?οηγο�?μενος',
		nextText: 'Επόμενος',
		currentText: 'Τ�?έχων Μήνας',
		monthNames: ['Ιανουά�?ιος','Φεβ�?ουά�?ιος','Μά�?τιος','Απ�?ίλιος','Μάιος','Ιο�?νιος',
		'Ιο�?λιος','Α�?γουστος','Σεπτέμβ�?ιος','Οκτώβ�?ιος','�?οέμβ�?ιος','Δεκέμβ�?ιος'],
		monthNamesShort: ['Ιαν','Φεβ','Μα�?','Απ�?','Μαι','Ιουν',
		'Ιουλ','Αυγ','Σεπ','Οκτ','�?οε','Δεκ'],
		dayNames: ['Κυ�?ιακή','Δευτέ�?α','Τ�?ίτη','Τετά�?τη','Πέμπτη','Πα�?ασκευή','Σάββατο'],
		dayNamesShort: ['Κυ�?','Δευ','Τ�?ι','Τετ','Πεμ','Πα�?','Σαβ'],
		dayNamesMin: ['Κυ','Δε','Τ�?','Τε','Πε','Πα','Σα'],
		weekHeader: 'Εβδ',
		dateFormat: 'dd/mm/yy',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: ''};
	$.datepicker.setDefaults($.datepicker.regional['el']);
});
