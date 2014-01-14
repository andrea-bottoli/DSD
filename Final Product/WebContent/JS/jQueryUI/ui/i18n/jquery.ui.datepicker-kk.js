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
