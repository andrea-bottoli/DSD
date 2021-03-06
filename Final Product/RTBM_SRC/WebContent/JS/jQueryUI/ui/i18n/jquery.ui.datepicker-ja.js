/* Japanese initialisation for the jQuery UI date picker plugin. */
/* Written by Kentaro SATO (kentaro@ranvis.com). */
jQuery(function($){
	$.datepicker.regional['ja'] = {
		closeText: '้ใ?ใ',
		prevText: '&#x3C;ๅ?',
		nextText: 'ๆฌก&#x3E;',
		currentText: 'ไปๆฅ',
		monthNames: ['1ๆ','2ๆ','3ๆ','4ๆ','5ๆ','6ๆ',
		'7ๆ','8ๆ','9ๆ','10ๆ','11ๆ','12ๆ'],
		monthNamesShort: ['1ๆ','2ๆ','3ๆ','4ๆ','5ๆ','6ๆ',
		'7ๆ','8ๆ','9ๆ','10ๆ','11ๆ','12ๆ'],
		dayNames: ['ๆฅๆๆฅ','ๆๆๆฅ','็?ซๆๆฅ','ๆฐดๆๆฅ','ๆจๆๆฅ','้ๆๆฅ','ๅๆๆฅ'],
		dayNamesShort: ['ๆฅ','ๆ','็?ซ','ๆฐด','ๆจ','้','ๅ'],
		dayNamesMin: ['ๆฅ','ๆ','็?ซ','ๆฐด','ๆจ','้','ๅ'],
		weekHeader: '้ฑ',
		dateFormat: 'yy/mm/dd',
		firstDay: 0,
		isRTL: false,
		showMonthAfterYear: true,
		yearSuffix: 'ๅนด'};
	$.datepicker.setDefaults($.datepicker.regional['ja']);
});
