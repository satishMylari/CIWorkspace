<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.pa.dao.*" %>
    <%@ page import="java.io.*,java.util.*,javax.servlet.*" %>
    <%@ page import="com.pa.util.*" %>
    <%@ page import="com.pa.servlet.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <script src="${pageContext.request.contextPath}/jquery/jquery-1.10.2.js"></script>
 <LINK REL="stylesheet" HREF="${pageContext.request.contextPath}/css/custom-styles.css" TYPE="text/css">
<title></title>

<script type="text/javascript">
function onchangedropdown(){
	 
       select=$("#slectboxid").val();
     // alert(select);
         
    	$.ajax({type:"GET",url:"${pageContext.request.contextPath}/projectdetails?select="+select,success:function(responseText){
    		 
    		object=JSON.parse(responseText);
    		if(object == null)
    		{
    		
			document.getElementById("project").value ="";
    		document.getElementById("pmdbcount").value ="";
    		document.getElementById("checkstylebcount").value ="";
    		document.getElementById("jspbcount").value ="";
    		document.getElementById("propertiesbcount").value ="";
    		document.getElementById("ncssbcount").value ="";
    		document.getElementById("jlcbcount").value ="";
    		document.getElementById("pmdexclusions").value ="";
    		document.getElementById("checkstyleexclusions").value ="";
    	 		}
    		else
    			{
    			
    			
        		document.getElementById("project").value =object.project;
        		document.getElementById("pmdbcount").value =object.pmdbcount;
        		document.getElementById("checkstylebcount").value =object.checkstylebcount;
        		document.getElementById("jspbcount").value =object.jspbcount;
        		document.getElementById("propertiesbcount").value =object.propertiesbcount;
        		document.getElementById("ncssbcount").value =object.ncssbcount;
        		document.getElementById("jlcbcount").value =object.jlcbcount;
        		document.getElementById("pmdexclusions").value =object.pmdexclusions;
        		document.getElementById("checkstyleexclusions").value =object.checkstyleexclusions;
        		    			
    			}     		}
});
    	
	 }
	 
function update(){
	
	//alert("inside update");
	 submit=$("#submit").val();
	 project=$("#project").val();
	 pmdbcount=$("#pmdbcount").val();
	 checkstylebcount=$("#checkstylebcount").val();
	 jspbcount=$("#jspbcount").val();
	 propertiesbcount=$("#propertiesbcount").val();
	 ncssbcount=$("#ncssbcount").val();
	 jlcbcount=$("#jlcbcount").val();
	 pmdexclusions=$("#pmdexclusions").val();
	 checkstyleexclusions=$("#checkstyleexclusions").val();
   
 	$.ajax({type:"GET",url:"${pageContext.request.contextPath}/projectdetails?submit="+submit+"&project="+project+"&pmdbcount="+pmdbcount
 			+"&checkstylebcount="+checkstylebcount+"&jspbcount="+jspbcount+"&propertiesbcount="+propertiesbcount+"&ncssbcount="+ncssbcount
 			+"&jlcbcount="+jlcbcount+"&pmdexclusions="+pmdexclusions+"&checkstyleexclusions="+checkstyleexclusions,success:function(result1){
 				object=JSON.parse(result1);
 		 $("#message").text(object.message);
      }	
 	}); 
	 }
	 
</script>
</head>
<body class="bodyclass">

<div  style="text-align:left;">
<h>
<a href="${pageContext.request.contextPath}/pages/home.jsp">Home Page</a>
<h>
</div>
<div>

<div style="margin:150px 25px 25px 500px;">
 <div id="message" style="color:#DC143C ;" ></div></br>
<label for="select">Project List :</label>
<select name="select" id="slectboxid" onchange="onchangedropdown()">
<%
ReportDao reportDao=new ReportDao();
List model=reportDao.getProjects();
for(int i=0;i<model.size();i++){
	String s=model.get(i).toString();
   out.print("<option value='"+s+"'>"+s+"</option>");
}
 %>
 </select>

</div>

<div align="center">
<table border="1" cellspacing="5" Width="300">
 <tr><td>
<label for="PROJECT" align="center">PROJECT:</label></td>
<td>
<input type="Text" id="project" maxlength="25" size="25"  disabled="disabled"/></td>
</tr>
<tr>
<td><label for="PMDBCOUNT" >PMDBCOUNT:</label></td>
<td><input type="Text" id="pmdbcount" maxlength="25" size="25"/></td>
</tr>
<tr>
<td><label for="CHECKSTYLEBCOUNT" >CHECKSTYLEBCOUNT:</label></td>
<td><input type="Text" id="checkstylebcount" maxlength="25" size="25"/></td>
</tr>
<tr>
<td><label for="JSPBCOUNT">JSPBCOUNT:</label></td>
<td><input type="Text" id="jspbcount" maxlength="25" size="25" /></td>
</tr>
<tr>
<td><label for="PROPERTIESBCOUNT">PROPERTIESBCOUNT:</label></td>
<td><input type="Text" id="propertiesbcount" maxlength="25" size="25"/></td>
</tr>
<tr>
<td><label for="NCSSBCOUNT">NCSSBCOUNT:</label></td>
<td><input type="Text" id="ncssbcount" maxlength="25" size="25"/></td>
</tr>
<tr>
<td><label for="JLCBCOUNT">JLCBCOUNT:</label></td>
<td><input type="Text" id="jlcbcount" maxlength="25" size="25"/></td></tr>
<tr><td>
<label for="PMDEXCLUSIONS">PMDEXCLUSIONS:</label></td>
<td>
<input type="Text" id="pmdexclusions" maxlength="25" size="25"/></td>
</tr>
<tr>
<td><label for="CHECKSTYLEEXCLUSIONS">CHECKSTYLEEXCLUSIONS:</label></td>
<td><input type="Text" id="checkstyleexclusions" maxlength="25" size="25"/></td>
</tr>
 </table>
 </div><br>
<div align="center">
 <input  type="submit" Value="update"  id="submit"  onclick="update()"/> 
</div>

</body>
</html>