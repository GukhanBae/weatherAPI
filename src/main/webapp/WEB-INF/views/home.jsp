<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cpath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>공공데이터 날씨 정보 가져오기 (java)</h1>
	<hr>
	
	<div id ="root"></div>
	
	
	<script>
		const url = '${cpath}/weather'
		const opt = {
			method: 'GET'
		}
		fetch(url, opt).then(resp => resp.json())
		.then(json => {
			
			const ul = document.createElement('ul')
			const arr = json.response.body.items.item;
			arr.forEach(e => {
				const li = document.createElement('li')
				switch(e.category) {
				
				case 'SKY':
					let sky = '';
					switch(e.fcstValue) {
					case '1': sky = '맑음';	 break;
					case '2': sky = '구름많음'; break;
					case '3': sky = '흐림';	 break;
					}
					li.innerText = '하늘상태 : ' + sky
					ul.appendChild(li);
					break;
					
				case 'POP':
					li.innerText = '강수확률 : ' + e.fcstValue + '%';
					ul.appendChild(li);
					break;
					
				case 'PTY':
					let pty = '';
					switch(e.fcstValue) {
					case '0': pty = '없음';		break;
					case '1': pty = '비';		break;
					case '2': pty = '비/눈';		break;
					case '3': pty = '눈';		break;
					case '4': pty = '소나기';		break;
					case '5': pty = '빗방울'; 	break;
					case '6': pty = '빗방울/눈날림';break;
					case '7': pty = '눈날림';		break;
					
					}
					li.innerText = '강수형태 : ' + pty;
					ul.appendChild(li)
					break;
				}
			})
			document.getElementById('root').appendChild(ul);
			console.log(json.response.body.items.item)
		})
	
	
	</script>
	
	

</body>
</html>