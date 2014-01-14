/* Czech initialisation for the jQuery UI date picker plugin. */
/* Written by Tomas Muller (tomas@tomas-muller.net). */
jQuery(function($){
	$.datepicker.regional['cs'] = {
		closeText: 'Zavřít',
		prevText: '&#x3C;Dříve',
		nextText: 'Později&#x3E;',
		currentText: 'Nyní',
		monthNames: ['leden','únor','březen','duben','květen','�?erven',
		'�?ervenec','srpen','září','říjen','listopad','prosinec'],
		monthNamesShort: ['led','úno','bře','dub','kvě','�?er',
		'�?vc','srp','zář','říj','lis','pro'],
		dayNames: ['neděle', 'pondělí', 'úterý', 'středa', '�?tvrtek', 'pátek', 'sobota'],
		dayNamesShort: ['ne', 'po', 'út', 'st', '�?t', 'pá', 'so'],
		dayNamesMin: ['ne','po','út','st','�?t','pá','so'],
		weekHeader: 'Týd',
		dateFormat: 'dd.mm.yy',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: ''};
	$.datepicker.setDefaults($.datepicker.regional['cs']);
});
