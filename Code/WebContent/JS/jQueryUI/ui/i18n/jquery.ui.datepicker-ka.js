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
/* Georgian (UTF-8) initialisation for the jQuery UI date picker plugin. */
/* Written by Lado Lomidze (lado.lomidze@gmail.com). */
jQuery(function($){
	$.datepicker.regional['ka'] = {
		closeText: 'დ�?ხურვ�?',
		prevText: '&#x3c; წინ�?',
		nextText: 'შემდეგი &#x3e;',
		currentText: 'დღეს',
		monthNames: ['ი�?ნვ�?რი','თებერვ�?ლი','მ�?რტი','�?პრილი','მ�?ისი','ივნისი', 'ივლისი','�?გვისტ�?','სექტემბერი','�?ქტ�?მბერი','ნ�?ემბერი','დეკემბერი'],
		monthNamesShort: ['ი�?ნ','თებ','მ�?რ','�?პრ','მ�?ი','ივნ', 'ივლ','�?გვ','სექ','�?ქტ','ნ�?ე','დეკ'],
		dayNames: ['კვირ�?','�?რშ�?ბ�?თი','ს�?მშ�?ბ�?თი','�?თხშ�?ბ�?თი','ხუთშ�?ბ�?თი','პ�?რ�?სკევი','შ�?ბ�?თი'],
		dayNamesShort: ['კვ','�?რშ','ს�?მ','�?თხ','ხუთ','პ�?რ','შ�?ბ'],
		dayNamesMin: ['კვ','�?რშ','ს�?მ','�?თხ','ხუთ','პ�?რ','შ�?ბ'],
		weekHeader: 'კვირ�?',
		dateFormat: 'dd-mm-yy',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: ''};
	$.datepicker.setDefaults($.datepicker.regional['ka']);
});
