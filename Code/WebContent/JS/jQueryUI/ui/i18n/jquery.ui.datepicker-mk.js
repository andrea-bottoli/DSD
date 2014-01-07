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
