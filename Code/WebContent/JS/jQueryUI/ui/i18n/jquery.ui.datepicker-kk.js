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
/* Kazakh (UTF-8) initialisation for the jQuery UI date picker plugin. */
/* Written by Dmitriy Karasyov (dmitriy.karasyov@gmail.com). */
jQuery(function($){
	$.datepicker.regional['kk'] = {
		closeText: 'Жабу',
		prevText: '&#x3C;�?лдыңғы',
		nextText: 'Келе�?і&#x3E;',
		currentText: 'Бүгін',
		monthNames: ['Қаңтар','�?қпан','�?аурыз','Сәуір','Мамыр','Мау�?ым',
		'Шілде','Тамыз','Қыркүйек','Қазан','Қараша','Желтоқ�?ан'],
		monthNamesShort: ['Қаң','�?қп','�?ау','Сәу','Мам','Мау',
		'Шіл','Там','Қыр','Қаз','Қар','Жел'],
		dayNames: ['Жек�?енбі','Дүй�?енбі','Сей�?енбі','Сәр�?енбі','Бей�?енбі','Жұма','Сенбі'],
		dayNamesShort: ['жк�?','д�?н','�?�?н','�?р�?','б�?н','жма','�?нб'],
		dayNamesMin: ['Жк','Д�?','С�?','Ср','Б�?','Жм','Сн'],
		weekHeader: '�?е',
		dateFormat: 'dd.mm.yy',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: ''};
	$.datepicker.setDefaults($.datepicker.regional['kk']);
});
