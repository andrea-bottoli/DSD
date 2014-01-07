/* Georgian (UTF-8) initialisation for the jQuery UI date picker plugin. */
/* Written by Lado Lomidze (lado.lomidze@gmail.com). */
jQuery(function($){
	$.datepicker.regional['ka'] = {
		closeText: 'დ�?ხურვ�?',
		prevText: '&#x3c; წინ�?',
		nextText: 'შემდეგი &#x3e;',
		currentText: 'დღეს',
		monthNames: ['ი�?ნვ�?რი','თებერვ�?ლი','მ�?რტი','�?პრილი','მ�?ისი','ივნისი', 'ივლისი','�?გვისტ�?','სექტემბერი','�?ქტ�?მბერი','ნ�?ემბერი','დეკემბერი'],
		monthNamesShort: ['ი�?ნ','თებ','მ�?რ','�?პრ','მ�?ი','ივნ', 'ივლ','�?გვ','სექ','�?ქტ','ნ�?ე','დეკ'],
		dayNames: ['კვირ�?','�?რშ�?ბ�?თი','ს�?მშ�?ბ�?თი','�?თხშ�?ბ�?თი','ხუთშ�?ბ�?თი','პ�?რ�?სკევი','შ�?ბ�?თი'],
		dayNamesShort: ['კვ','�?რშ','ს�?მ','�?თხ','ხუთ','პ�?რ','შ�?ბ'],
		dayNamesMin: ['კვ','�?რშ','ს�?მ','�?თხ','ხუთ','პ�?რ','შ�?ბ'],
		weekHeader: 'კვირ�?',
		dateFormat: 'dd-mm-yy',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: ''};
	$.datepicker.setDefaults($.datepicker.regional['ka']);
});
