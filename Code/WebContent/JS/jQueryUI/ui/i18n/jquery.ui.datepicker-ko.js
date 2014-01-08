/* Korean initialisation for the jQuery calendar extension. */
/* Written by DaeKwon Kang (ncrash.dk@gmail.com), Edited by Genie. */
jQuery(function($){
	$.datepicker.regional['ko'] = {
		closeText: 'λ«κΈ°',
		prevText: 'μ?΄μ λ¬',
		nextText: 'λ€μ?λ¬',
		currentText: 'μ€λ',
		monthNames: ['1μ','2μ','3μ','4μ','5μ','6μ',
		'7μ','8μ','9μ','10μ','11μ','12μ'],
		monthNamesShort: ['1μ','2μ','3μ','4μ','5μ','6μ',
		'7μ','8μ','9μ','10μ','11μ','12μ'],
		dayNames: ['μ?Όμμ?Ό','μμμ?Ό','νμμ?Ό','μμμ?Ό','λͺ©μμ?Ό','κΈμμ?Ό','ν μμ?Ό'],
		dayNamesShort: ['μ?Ό','μ','ν','μ','λͺ©','κΈ','ν '],
		dayNamesMin: ['μ?Ό','μ','ν','μ','λͺ©','κΈ','ν '],
		weekHeader: 'Wk',
		dateFormat: 'yy-mm-dd',
		firstDay: 0,
		isRTL: false,
		showMonthAfterYear: true,
		yearSuffix: 'λ'};
	$.datepicker.setDefaults($.datepicker.regional['ko']);
});
