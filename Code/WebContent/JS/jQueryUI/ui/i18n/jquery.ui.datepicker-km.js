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
/* Khmer initialisation for the jQuery calendar extension. */
/* Written by Chandara Om (chandara.teacher@gmail.com). */
jQuery(function($){
	$.datepicker.regional['km'] = {
		closeText: 'ធ្វើ​រួច',
		prevText: 'មុន',
		nextText: 'បន្ទាប់',
		currentText: '�?្ងៃ​ន�?ះ',
		monthNames: ['មករា','កុម្ភៈ','មីនា','ម�?សា','ឧសភា','មិ�?ុនា',
		'កក្កដា','សីហា','កញ្ញា','�?ុលា','វិច្ឆិកា','ធ្នូ'],
		monthNamesShort: ['មករា','កុម្ភៈ','មីនា','ម�?សា','ឧសភា','មិ�?ុនា',
		'កក្កដា','សីហា','កញ្ញា','�?ុលា','វិច្ឆិកា','ធ្នូ'],
		dayNames: ['អាទិ�?្យ', 'ចន្ទ', 'អង្គារ', 'ពុធ', 'ព្រហស្ប�?ិ�?', 'សុក្រ', 'សៅរ�?'],
		dayNamesShort: ['អា', 'ច', 'អ', 'ពុ', 'ព្រហ', 'សុ', 'សៅ'],
		dayNamesMin: ['អា', 'ច', 'អ', 'ពុ', 'ព្រហ', 'សុ', 'សៅ'],
		weekHeader: 'សប្ដាហ�?',
		dateFormat: 'dd-mm-yy',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: ''};
	$.datepicker.setDefaults($.datepicker.regional['km']);
});
