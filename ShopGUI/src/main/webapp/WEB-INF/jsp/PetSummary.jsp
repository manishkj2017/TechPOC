<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Pets Shop</title>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<!-- <script type="text/javascript" src="jquery-3.2.1.js"></script> -->
<script type="text/javascript">
	var lastHtml = '';
	$(document).ready(function() {
		//alert('inside jquery');
		var originalHtml = $('#saleSummary').html();
		lastHtml = originalHtml;
		var date = Date();
		var refreshTime = 1000;
		var isRefresh = true;
		var html = '';
		$('#welcome').append('<h4>'+date+'</h4>');
		window.setInterval(function() {
			//alert(isRefresh);
			//alert(lastHtml);
			if(isRefresh){
				$.getJSON('http://localhost:8080/ShopGUI/shopclosed.json', function(data){
					if(data == true){
						//alert('setting refresh false');
						isRefresh = false;
					}
				});
			}
			
			if(!isRefresh){
				//alert('no more refresh');
				$('#saleSummary').html(lastHtml);
				$('.container p').html('<p>Shop is closed..</p>').css('color', 'red').css('font-size', '18px');
				setDataColors();
			}else{

				$.getJSON('http://localhost:8080/ShopGUI/salesumm.json', function(data){
					
					html = '';
					html = createDataTable(data, html);
					$('#saleSummary').html(originalHtml);
					$('#saleSummary').append(html);
					lastHtml = $('#saleSummary').html();
					setDataColors();
					$('.container p').html('');
				
				});
			}
			
		}, refreshTime);
		
	});
	
	function setDataColors(){
		$('tr:even').css('background-color', '#208B8B');
		$('tr:odd').css('background-color', '#3CB371');
		$('#header').css('background-color', '#006400').css('color', 'lightgray');
		
		//mark loss as red color
		$('td[id*="loss"]').each(function() {
			$(this).css('background-color', '#FF4500')
		});
	}

	function createDataTable(data,html){
		var len = data.length;
		var html = '';
		for(var i=0; i< len; i++){
			html += '<tr align="center" font-family="Verdana">';
			html += '<td>' + data[i].petType + '</td>';
			html += '<td>' + data[i].orderSource + '</td>';
			html += '<td>' + data[i].totalSold + '</td>';
			html += '<td>' + data[i].available + '</td>';
			html += '<td>' + data[i].revenue + '</td>';
			html += '<td>' + data[i].buyCost + '</td>';
			html += '<td>' + data[i].rejectionPenalty + '</td>';
			
			var pl = data[i].profitLoss;
			if(pl < 0)
				html += '<td id="loss"'+i+'>' + data[i].profitLoss + '</td>';
			else
				html += '<td>' + data[i].profitLoss + '</td>';
			
			html += '<td>' + data[i].noOfRejectedOrders + '</td>';
			html += '</tr>';
		}
		
		return html;
	}
</script>

<!-- css file link for all styles - css resource mapping is defined in servlet config xml file -->
<link href="resources/css/site.css" rel="stylesheet" type="text/css" />

</head>

<body>
	<div class="container">
		<div class="image">
			<h1><img src="resources/images/petstore.jpg" width="150" height="100"/></h1>
		</div>
		<div id="welcome">
			<h2>&copy;Welcome to Pet Shop</h2>
		</div>
		<h3 class="summtitle bordered">Pets Sale Summary</h3>
		<form:form commandName="petsalesummary">
			
	
				<table id="saleSummary" border="0.75" bordercolor="red" cellpadding="10" >
					<tr id="header">
						<th>Pet Type</th>
						<th>Order Source</th>
						<th>Total Sold</th>
						<th>Available</th>
						<th>Revenue</th>
						<th>BuyCost</th>
						<th>RejectionPenalty</th>
						<th>P/L</th>
						<th>Rejected Orders</th>
					</tr>
				</table>
		
		</form:form>
		<p></p>
	</div>
</body>

</html>