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
/* Russian (UTF-8) initialisation for the jQuery UI date picker plugin. */
/* Written by Andrew Stromnov (stromnov@gmail.com). */
jQuery(function($){
	$.datepicker.regional['ru'] = {
		closeText: 'Закрыть',
		prevText: '&#x3C;Пред',
		nextText: 'След&#x3E;',
		currentText: 'Сегодн�?',
		monthNames: ['Январь','Февраль','Март','�?прель','Май','Июнь',
		'Июль','�?вгу�?т','Сент�?брь','Окт�?брь','�?о�?брь','Декабрь'],
		monthNamesShort: ['Янв','Фев','Мар','�?пр','Май','Июн',
		'Июл','�?вг','Сен','Окт','�?о�?','Дек'],
		dayNames: ['во�?кре�?енье','понедельник','вторник','�?реда','четверг','п�?тница','�?уббота'],
		dayNamesShort: ['в�?к','пнд','втр','�?рд','чтв','птн','�?бт'],
		dayNamesMin: ['В�?','Пн','Вт','Ср','Чт','Пт','Сб'],
		weekHeader: '�?ед',
		dateFormat: 'dd.mm.yy',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: ''};
	$.datepicker.setDefaults($.datepicker.regional['ru']);
});
