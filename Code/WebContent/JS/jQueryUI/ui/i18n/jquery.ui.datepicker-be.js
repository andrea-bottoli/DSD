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
/* Belarusian initialisation for the jQuery UI date picker plugin. */
/* Written by Pavel Selitskas <p.selitskas@gmail.com> */
jQuery(function($){
	$.datepicker.regional['be'] = {
		closeText: 'Зачыніць',
		prevText: '&larr;Пап�?р.',
		nextText: '�?а�?т.&rarr;',
		currentText: 'Сёньн�?',
		monthNames: ['Студзень','Люты','Сакавік','Кра�?авік','Травень','Ч�?рвень',
		'Ліпень','Жнівень','Вера�?ень','Ка�?трычнік','Лі�?тапад','Сьнежань'],
		monthNamesShort: ['Сту','Лют','Сак','Кра','Тра','Ч�?р',
		'Ліп','Жні','Вер','Ка�?','Лі�?','Сьн'],
		dayNames: ['н�?дзел�?','пан�?дзелак','аўторак','�?ерада','чацьвер','п�?тніца','�?убота'],
		dayNamesShort: ['ндз','пнд','аўт','�?рд','чцв','птн','�?бт'],
		dayNamesMin: ['�?д','Пн','�?ў','Ср','Чц','Пт','Сб'],
		weekHeader: 'Тд',
		dateFormat: 'dd.mm.yy',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: ''};
	$.datepicker.setDefaults($.datepicker.regional['be']);
});
