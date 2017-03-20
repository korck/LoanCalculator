<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="loan" method="get">
		<span id="amount">
			<label>
				Kwota: <input type="text" id="amount" name="amount" size=3/>
			</label>				
		</span>
		<span id="noi">
			<label>
				 / Ilość rat: <input type="text" id="noi" name="noi" size=1/>
			</label>
		</span>
		<br>
		<span id="interest">
			<label>
				Oprocentowanie: <input type="text" id="interest" name="interest" size=1/> %
			</label>
		</span>
		<br>
		<span id="fixedfee">
			<label>
				Opłata stała: <input type="text" id="fixedfee" name="fixedfee" size=2/>
			</label>
		</span>
		<span id="type">
			<br>Rodzaj rat:<br>
				Malejąca: <input type="radio" name="type" value="dec" checked><br>
				Rosnąca: <input type="radio" name="type" value="inc" checked><br>
		</span>
		<br>
		<input type="submit" name="oblicz" value="Oblicz"/> <input type="submit" name="genpdf" value="Wygeneruj PDF"/> 
	</form>
</body>
</html>