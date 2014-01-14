/* Bulgarian initialisation for the jQuery UI date picker plugin. */
/* Written by Stoyan Kyosev (http://svest.org). */
jQuery(function($){
	$.datepicker.regional['bg'] = {
		closeText: 'затвори',
		prevText: '&#x3C;назад',
		nextText: 'напред&#x3E;',
		nextBigText: '&#x3E;&#x3E;',
		currentText: 'дне�?',
		monthNames: ['Януари','Февруари','Март','�?прил','Май','Юни',
		'Юли','�?вгу�?т','Септември','Октомври','�?оември','Декември'],
		monthNamesShort: ['Яну','Фев','Мар','�?пр','Май','Юни',
		'Юли','�?вг','Сеп','Окт','�?ов','Дек'],
		dayNames: ['�?едел�?','Понеделник','Вторник','Ср�?да','Четвъртък','Петък','Събота'],
		dayNamesShort: ['�?ед','Пон','Вто','Ср�?','Чет','Пет','Съб'],
		dayNamesMin: ['�?е','По','Вт','Ср','Че','Пе','Съ'],
		weekHeader: 'Wk',
		dateFormat: 'dd.mm.yy',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: ''};
	$.datepicker.setDefaults($.datepicker.regional['bg']);
});
