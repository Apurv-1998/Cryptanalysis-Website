<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Enter Id:-</title>
<script src = "https://www.google.com/recaptcha/api.js"></script>
</head>
<body>
	<form action = "./ReadFile" method = "post">
		Enter The File-Id to Continue Reading:
		 	<input type = "text" name = "fileId"><br>
		 	<div class="g-recaptcha"
		 		data-sitekey = "6Lc566kZAAAAAJjWv1mf9HPHVARFfD2EXRxH8Bcg"
		 	></div><br>
		 	<input type = "submit" value = "GO">
	
	</form>
</body>
</html>