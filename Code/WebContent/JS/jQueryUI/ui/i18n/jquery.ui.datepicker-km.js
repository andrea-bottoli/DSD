/* Khmer initialisation for the jQuery calendar extension. */
/* Written by Chandara Om (chandara.teacher@gmail.com). */
jQuery(function($){
	$.datepicker.regional['km'] = {
		closeText: 'ធ្វើ​រួច',
		prevText: 'មុន',
		nextText: 'បន្ទាប់',
		currentText: '�?្ងៃ​ន�?ះ',
		monthNames: ['មករា','កុម្ភៈ','មីនា','ម�?សា','ឧសភា','មិ�?ុនា',
		'កក្កដា','សីហា','កញ្ញា','�?ុលា','វិច្ឆិកា','ធ្នូ'],
		monthNamesShort: ['មករា','កុម្ភៈ','មីនា','ម�?សា','ឧសភា','មិ�?ុនា',
		'កក្កដា','សីហា','កញ្ញា','�?ុលា','វិច្ឆិកា','ធ្នូ'],
		dayNames: ['អាទិ�?្យ', 'ចន្ទ', 'អង្គារ', 'ពុធ', 'ព្រហស្ប�?ិ�?', 'សុក្រ', 'សៅរ�?'],
		dayNamesShort: ['អា', 'ច', 'អ', 'ពុ', 'ព្រហ', 'សុ', 'សៅ'],
		dayNamesMin: ['អា', 'ច', 'អ', 'ពុ', 'ព្រហ', 'សុ', 'សៅ'],
		weekHeader: 'សប្ដាហ�?',
		dateFormat: 'dd-mm-yy',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: ''};
	$.datepicker.setDefaults($.datepicker.regional['km']);
});
