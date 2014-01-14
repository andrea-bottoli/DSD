/* Tajiki (UTF-8) initialisation for the jQuery UI date picker plugin. */
/* Written by Abdurahmon Saidov (saidovab@gmail.com). */
jQuery(function($){
	$.datepicker.regional['tj'] = {
		closeText: 'Идома',
		prevText: '&#x3c;Қафо',
		nextText: 'Пеш&#x3e;',
		currentText: 'Имрӯз',
		monthNames: ['Январ','Феврал','Март','�?прел','Май','Июн',
		'Июл','�?вгу�?т','Сент�?бр','Окт�?бр','�?о�?бр','Декабр'],
		monthNamesShort: ['Янв','Фев','Мар','�?пр','Май','Июн',
		'Июл','�?вг','Сен','Окт','�?о�?','Дек'],
		dayNames: ['�?кшанбе','душанбе','�?ешанбе','чоршанбе','панҷшанбе','ҷумъа','шанбе'],
		dayNamesShort: ['�?кш','душ','�?еш','чор','пан','ҷум','шан'],
		dayNamesMin: ['Як','Дш','Сш','Чш','Пш','Ҷм','Шн'],
		weekHeader: 'Хф',
		dateFormat: 'dd.mm.yy',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: ''};
	$.datepicker.setDefaults($.datepicker.regional['tj']);
});
