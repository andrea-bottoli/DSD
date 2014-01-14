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
