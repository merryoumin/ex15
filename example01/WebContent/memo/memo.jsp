<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ include file="../include/inc_header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<form name ="form">
<div style="display:none;">
</div>	
		<table border="1" width="80%" align="center" style="text-align:center">
			<tr>
				<td colspan="2" align="center">
				<h2>MEMO ${list.size()}</h2>
				</td>			
			</tr>	
			<tr>
				<td width="150">
				이름
				</td>
				<td>
				<input type="text" id="name" name="name" value=""> 
				</td>
			</tr>
			<tr>
				<td>
				메모 
				</td>
				<td>
				<input type="text" id="memo" name="memo" value="" style="width:450px; "> <button type="button" id="btnSave">SAVE</button>
				</td>
			</tr>		
		
			<tr>
				<td colspan="20">	
					<div id="result" style="border:1px solid red;"></div>	
			    </td>
			</tr>   
		</table>	 
	</form>
<script>
	$(document).ready(function(){
		list('');
		$("#btnSave").click(function(){
			insert();
		});
		
	});

	function insert() {
		
		var name = $("#name").val();
		var memo = $("#memo").val();
	
		
		var param = "name="+name +"&memo="+memo;
		
		$.ajax({
			type: "post",
			data : $('form').serialize(),
			url: "${path}/memo_servlet/memoProc.do",
			success: function(){
				list('1');
				$("#name").val("");
				$("#memo").val("");
			}

		});
	}
	function list(value1) {
		var param = "";
		if(value1 == '1') {
			param = {
					"pageNumber" : '1'
				}
		} else {
			param = {
					"pageNumber" : $("#span_pageNumber").text()
				}
		}

			$.ajax({
		  		type: "post",
	            data: param,
	            url: "${path}/memo_servlet/memolist.do",
	            success: function(result){
// 	            	alert(result);
	            	$("#result").html(result);

	           }
				
			})
	}
	   function suntaek_page(value1){   
		   $("#span_pageNumber").text(value1);
		   list();
	   }



	
	
</script>
</body>
</html>