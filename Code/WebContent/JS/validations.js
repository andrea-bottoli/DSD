function validateRange()
{
	var errorFlag = false;
	var alertString = "Please insert the following parameters:";

	var startDate = document.forms["dateRange"]["from"].value;
	if (startDate == "") {
		alertString += "\n*Start date";
		errorFlag = true;
	}
	var endDate  = document.forms["dateRange"]["to"].value;
	if (endDate == "") {
		alertString += "\n*End date";
		errorFlag = true;
	}
	if (errorFlag == true) {
		alert(alertString);
		return false;
	}
}
