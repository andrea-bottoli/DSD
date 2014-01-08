/* Serbian i18n for the jQuery UI date picker plugin. */
/* Written by Dejan Dimić. */
jQuery(function($){
	$.datepicker.regional['sr'] = {
		closeText: 'Затвори',
		prevText: '&#x3C;',
		nextText: '&#x3E;',
		currentText: 'Дана�?',
		monthNames: ['Јануар','Фебруар','Март','�?прил','Мај','Јун',
		'Јул','�?вгу�?т','Септембар','Октобар','�?овембар','Децембар'],
		monthNamesShort: ['Јан','Феб','Мар','�?пр','Мај','Јун',
		'Јул','�?вг','Сеп','Окт','�?ов','Дец'],
		dayNames: ['�?едеља','Понедељак','Уторак','Среда','Четвртак','Петак','Субота'],
		dayNamesShort: ['�?ед','Пон','Уто','Сре','Чет','Пет','Суб'],
		dayNamesMin: ['�?е','По','Ут','Ср','Че','Пе','Су'],
		weekHeader: 'Сед',
		dateFormat: 'dd.mm.yy',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: ''};
	$.datepicker.setDefaults($.datepicker.regional['sr']);
});
