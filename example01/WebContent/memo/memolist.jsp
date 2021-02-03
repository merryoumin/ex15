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
<span id="span_pageNumber">${pageNumber}</span>
	<form name ="lform">	
	
		<table border = "1" width="80%" align="center" style="text-align:center">
			<tr>
				<td colspan="20" align="center">
				<h2>메모목록</h2>
				</td>			
			</tr>
			<tr>
				<th>
				id
				</th>
				<th>
				이름
				</th>
				<th>
				메모
				</th>
				<th>
				작성일
				</th>
			</tr>
			<c:if test="${list.size() == 0 }">
				<tr>
					<td colspan="20" align="center" height="200">
					<h2>등록된 메모가 없습니다.</h2>
					</td>			
				</tr>
			</c:if>
			<c:forEach var="row" items="${list}">
				<tr>
					<td>
					${row.id}
					</td>
					<td>
					${row.name}
					</td>
					<td>
					${row.memo}
					</td>
					<td>
					${row.regiDate}
					</td>
				</tr>
			</c:forEach>	
				<c:if test="${totalRecord > 0}">
				<tr>
					 <td colspan="7">
					 	<a href="#" onclick="suntaek_page('1');">[첫페이지]</a>&nbsp;&nbsp;
					 	<c:if test="${startPage > blockSize }">
							<a href="#" onclick="suntaek_page('${startPage - blockSize}');">[이전 10개]</a>&nbsp;&nbsp;
						</c:if>
						<c:if test="${startPage <= blockSize }">
							[이전 10개]
						</c:if>
						&nbsp;&nbsp;
						<c:forEach var="i" begin="${startPage}" end="${lastPage}" step="1">
							<c:if test="${i == pageNumber}">
								[${i}]&nbsp;&nbsp;
							</c:if>
							<c:if test="${i != pageNumber}">
								<a href="#" onclick="suntaek_page('${i}');">${i}</a>&nbsp;&nbsp;
							</c:if>	
						</c:forEach>
						<c:if test="${lastPage < totalPage }">
							<a href="#" onclick="suntaek_page('${startPage + blockSize}');">[다음 10개]</a>&nbsp;&nbsp;
						</c:if>
						<c:if test="${lastPage <= totalPage }">
							[다음 10개]            
						</c:if>&nbsp;&nbsp;
						<a href="#" onclick="suntaek_page('${totalPage}');">[끝페이지]</a>&nbsp;&nbsp;
			   
        			 </td>
				</tr>	
			</c:if>	
		</table>
		
	</form>
<script>

//    function GoPage(value1, value2, value3){
// 	      if (value1 == 'memo_list') {
// 	        location.href= '${path}/memo_servlet/memolist.do?pageNumber=' + value2;
// 	      } 
// 	   }

// 	function suntaek_page(value1){   
// 		   $("#pageNumber").text(value1);
// 		   list();
// 	 }
</script>
</body>
</html>