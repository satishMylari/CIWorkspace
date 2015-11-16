<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
 <%@ page import="java.util.Calendar"%>
 <%@ page import="com.pa.dao.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<LINK REL="stylesheet" HREF="${pageContext.request.contextPath}/css/custom-styles.css" TYPE="text/css">
 <script src="${pageContext.request.contextPath}/jquery/jquery-1.10.2.js"></script>
 <script src="${pageContext.request.contextPath}/js/datetimepicker_css.js"></script> 
 
<script type="text/javascript">
function test(){
	
	 var text=document.getElementById("date").value;
	// alert(text);
	 if (text =="")
		 { 
		  alert("Please select Date");
		 }else
			 {
			 
       date=$("#date").val();
     
    	$.ajax({type:"GET",url:"${pageContext.request.contextPath}/report?date="+date,success:function(result){
    	     object=JSON.parse(result);
              $("#image").attr("src","${pageContext.request.contextPath}/"+object.img);
              document.getElementById("image").style.visibility = "visible";
       
        }	
});
    	
			 }
	 }
</script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body class="bodyclass">
<div  style="text-align:left;">
<h>
<a href="${pageContext.request.contextPath}/pages/home.jsp">Home Page</a>
<h>
</div>
<% 
Date date = new Date();
out.print("<h2 align=\"center\">"+"CI Report :" +date.toString()+"</h2>");
%>
<br> </br>
<br> </br>

<div style="text-align:center; margin:150px auto 100px auto;">
     <label for="date">Date:</label>
     <input type="Text" id="date" maxlength="25" size="25"/ disabled="disabled"	>
     <img src="${pageContext.request.contextPath}/images/cal.gif" onclick="javascript:NewCssCal('date')" style="cursor:pointer"/>

<%--<label for="select">Project List :</label>
 <select multiple="multiple" name="select" id="slectboxid">
<%
ReportDao reportDao=new ReportDao();
List model=reportDao.projectlist;
for(int i=0;i<model.size();i++){
	String s=model.get(i).toString();
   out.print("<option value='"+s+"'>"+s+"</option>");
}
 %>
 </select> --%>
 
<br> </br>
<div>

<input  type="submit" Value="View Report"  id="submit" onclick="test()"/>
</div>
<div>
<!-- <a id="img">Image</a> -->

<img id="image" style="visibility:hidden" width="100%" height="100%"/>
</div>




</body>
</html>