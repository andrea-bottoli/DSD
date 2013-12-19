#-------------------------------------------------------------------------------
# Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Br?i?, Dzana Kujan, Nikola Radisavljevic, Jörn Tillmanns, Miraldi Fifo
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
/* Written by Cloudream (cloudream@gmail.com). */
jQuery(function($){
	$.datepicker.regional['zh-CN'] = {
		closeText: 'å³é­',
		prevText: '&#x3C;ä¸æ',
		nextText: 'ä¸æ&#x3E;',
		currentText: 'ä»å¤©',
		monthNames: ['ä¸æ','äºæ','ä¸æ','åæ','äºæ','å­æ',
		'ä¸æ','å«æ','ä¹?æ','å??æ','å??ä¸æ','å??äºæ'],
		monthNamesShort: ['ä¸æ','äºæ','ä¸æ','åæ','äºæ','å­æ',
		'ä¸æ','å«æ','ä¹?æ','å??æ','å??ä¸æ','å??äºæ'],
		dayNames: ['æææ¥','ææä¸','ææäº','ææä¸','ææå','ææäº','ææå­'],
		dayNamesShort: ['å¨æ¥','å¨ä¸','å¨äº','å¨ä¸','å¨å','å¨äº','å¨å­'],
		dayNamesMin: ['æ¥','ä¸','äº','ä¸','å','äº','å­'],
		weekHeader: 'å¨',
		dateFormat: 'yy-mm-dd',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: true,
		yearSuffix: 'å¹´'};
	$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
});
