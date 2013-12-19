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
/* Macedonian i18n for the jQuery UI date picker plugin. */
/* Written by Stojce Slavkovski. */
jQuery(function($){
	$.datepicker.regional['mk'] = {
		closeText: 'Затвори',
		prevText: '&#x3C;',
		nextText: '&#x3E;',
		currentText: 'Дене�?',
		monthNames: ['Јануари','Февруари','Март','�?прил','Мај','Јуни',
		'Јули','�?вгу�?т','Септември','Октомври','�?оември','Декември'],
		monthNamesShort: ['Јан','Фев','Мар','�?пр','Мај','Јун',
		'Јул','�?вг','Сеп','Окт','�?ое','Дек'],
		dayNames: ['�?едела','Понеделник','Вторник','Среда','Четврток','Петок','Сабота'],
		dayNamesShort: ['�?ед','Пон','Вто','Сре','Чет','Пет','Саб'],
		dayNamesMin: ['�?е','По','Вт','Ср','Че','Пе','Са'],
		weekHeader: 'Сед',
		dateFormat: 'dd.mm.yy',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: ''};
	$.datepicker.setDefaults($.datepicker.regional['mk']);
});
