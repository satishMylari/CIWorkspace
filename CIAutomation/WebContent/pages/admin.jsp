<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
 <%@ page import="java.util.Calendar"%>
 <%@ page import="com.pa.dao.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<style>
table,
th, td {
border: 1px solid black;
border-collapse: collapse;
}
th,
td {
padding: 5px;
}
</style>


<head>

<LINK REL="stylesheet" HREF="${pageContext.request.contextPath}/css/custom-styles.css" TYPE="text/css">
 <script src="${pageContext.request.contextPath}/jquery/jquery-1.10.2.js"></script>
 <script src="${pageContext.request.contextPath}/js/datetimepicker_css.js"></script> 
 

<script type="text/javascript">
function test(){
	
	var identity=0;
	 
	 var checkboxes = document.getElementsByName('Checkbox');
	 var vals = "";
     var n=checkboxes.length;
   for (var i=0;i<n; i++) {
  if (checkboxes[i].checked) 
  {
	  identity=1;
	     vals +=checkboxes[i].value+ ",";
  }
  }
   
   if( identity==1)
	   {
     //  alert("report?text="+text+"&select="+select);
    	$.ajax({type:"GET",url:"${pageContext.request.contextPath}/report?projects="+vals,success:function(result){
    		
    	  object=JSON.parse(result);
       	  $("#message").text(object.message);
    	 
        }	
});
   
	   } else
		   {
		   
		     alert("Please Select Any Checkbox");
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

<%-- <select multiple="multiple" name="select" id="slectboxid">
<%
ReportDao reportDao=new ReportDao();
List model=reportDao.projectlist;
for(int i=0;i<model.size();i++){
	String s=model.get(i).toString();
   out.print("<option value='"+s+"'>"+s+"</option>");
}
 %>
 </select> --%>
<!--  <label for="select">Project List :</label> -->

<!-- <div id="multipleCheckbox" style="margin-left: 400px;"> -->
<div>
 <div id="message" style="color:#DC143C ;" ></div></br>
  <table style="width:100%;a">
<%--  <%
ReportDao reportDao=new ReportDao();
List model=reportDao.getProjectDetails();
for(int i=0;i<model.size();i++){
	
	  String s=model.get(i).toString(); %>
	 
	  <tr>   
	 <input type="checkbox" name= <%=s%> id="Checkbox" value=<%=s %> ></input>
	 <%out.print(s);%> 
	<br>
     </tr>
 <%
}
%>  --%>
 <%!int j=0; %>
<% 

ReportDao reportDao=new ReportDao();
List model=reportDao.getProjectDetails();
for(int i =0 ;i<model.size();i++) 
{ 
	String s=model.get(i).toString();
	//out.println(s);
%>
<% if(j==0){%>

<tr>
<td>


<input type="checkbox" name= <%=s%> id="Checkbox" value=<%=s %>></input>
<%out.print(s);%> 
  <%j=j+1;%> 
</td>
<%}else { %>
<td>
<%-- <%String s2=model1.get(i).toString();%> --%> 
<input type="checkbox" name= <%=s%> id="Checkbox" value=<%=s%> ></input>
<%out.print(s);%>
</td>
<%} %>
<%if(j==4){ %>
<%j=0; %>
</tr>
<%} %>
<%j=j+1; %>
<%}%>
</table>


</div>
<br> </br>
<div style="text-align:center;">

<input  type="submit" Value="Save"  id="submit" onclick="test()"/>
</div>

<%-- <%! String image=null;%>
<%image=request.getParameter("image");%>
<%if (request.getParameter("image")!= null) {   
  // image="c:/../Image/" + image;
   image=request.getParameter("image");%>
  <img src=<%=image%>  width="100%" height="100%"/>
<%}%>
</div> --%>

<div>

<img id="image" style="visibility:hidden" width="100%" height="100%"/>
</div>




</body>
</html>