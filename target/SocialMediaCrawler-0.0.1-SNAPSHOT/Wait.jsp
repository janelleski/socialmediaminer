<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>   

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="refresh" content="2;url=<s:url includeParams="all" />"/>
    <title>Social Media Miner</title>

    
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-1.12.3.js"></script>
    <script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 
    <script src="https://cdn.datatables.net/buttons/1.2.2/js/dataTables.buttons.min.js"></script> 
	<script src="https://cdn.datatables.net/buttons/1.2.2/js/buttons.flash.min.js"></script> 
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js"></script> 
	<script src="https://cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/pdfmake.min.js"></script> 
	<script src="https://cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/vfs_fonts.js"></script> 
	<script src="https://cdn.datatables.net/buttons/1.2.2/js/buttons.html5.min.js"></script> 
	<script src="https://cdn.datatables.net/buttons/1.2.2/js/buttons.print.min.js"></script> 
    

<!--        Bootstrap Core CSS -->

<!--     <link rel="stylesheet"  href="https://cdn.datatables.net/1.10.12/css/dataTables.bootstrap.min.css"> -->
    <link rel="stylesheet"  href="https://cdn.datatables.net/buttons/1.2.2/css/buttons.dataTables.min.css">

	<script src="https://cdn.datatables.net/1.10.12/js/dataTables.bootstrap.min.js"></script>
     
       <!-- Bootstrap Core CSS -->
    <link rel="stylesheet"  href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!--     <link rel="stylesheet"  href="https://cdn.datatables.net/1.10.12/css/dataTables.bootstrap.min.css"> -->
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs-3.3.6/jq-2.2.3/dt-1.10.12/datatables.min.css"/>

    <link href="css/bootstrap.min.css" rel="stylesheet">

 	<link href="css/portfolio-item.css" rel="stylesheet"> 

   	<script>
   	$(document).ready(function() {
   	    $('#example').DataTable( {
   	        dom: 'Bfrtip',
   	        buttons: [
   	            'copy', 'csv', 'excel', 'pdf', 'print'
   	        ]
   	    } );
   	} );
   	
    function submitForm(){
     	  document.forms["searchForm"].submit();
     	}
   	</script>
</head>


<body>

    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="Home.jsp">Social Media Miner</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="#">Keywords</a>
                    </li>
                    <li>
                        <a href="#">Reports</a>
                    </li>
                    <li>
                        <a href="#">Help</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>

    <!-- Page Content -->
    <div class="container">

          <!-- Portfolio Item Heading -->
        <div class="row">
            <div class="col-lg-12">
             <s:form action="searchKeyword" name="searchForm">
             <div class="col-lg-12">
                <h1 class="page-header">Search posts for # <input type="text" id="search" name="keywordString" placeholder="example: cheesecake"style="max-width:100%"> </h1>
            </div>

            <div class="col-sm-3 col-xs-6">
               Choose a Source:
            </div>

            <div class="col-sm-3 col-xs-6">
            
              <select class="form-control" name="keywordOption" style="width:150px">
                                                <option>Facebook</option>
                                                <option>Twitter</option>
                                                <option>Google+</option>
                                                <option>YouTube</option>
                                                <option>All</option>
                                            </select>
            </div>
           
           
            <input type="submit" class="btn btn-info" value="Search" style="width:125px">
		</s:form>

            </div>
        </div>
        <!-- /end item heading -->

        <!-- Portfolio Item Row -->
        <div class="row">
        
        
         <div class="col-sm-3 col-xs-6"> -->
                <a href="#">
                   
                </a>
            </div>

            <div class="col-sm-3 col-xs-6">
                <a href="#">
                    <img class="img-responsive portfolio-item" src="spinner-blue.gif" alt=""><br/>
                </a>
            </div>

            <div class="col-sm-3 col-xs-6">
                <a href="#">
                    
                </a>
            </div>

<%--                <s:if test="allRecords.empty"> --%>
<!--                <h1>No results found :( (That's odd)</h1> -->
<%--                 </s:if> --%>
<%--                 <s:else> --%>
<!--                 <h1>Results:</h1> -->
                
<!--                 <table id="example" class="display" cellspacing="0" width="100%"> -->
<!--  				 <thead> -->
<!--             		<tr> -->
<!-- 		                <th>Date Created</th> -->
<!-- 		                <th>Content</th> -->
<!-- 		                <th>Author</th> -->
<!-- 		                <th>Social Media</th> -->
<!-- 		                <th>Keyword</th> -->
<!--             		</tr> -->
<!--         		</thead> -->
<!--         		<tfoot> -->
<!--             		<tr> -->
<!-- 		              	<th>Date Created</th> -->
<!-- 		                <th>Content</th> -->
<!-- 		                <th>Author</th> -->
<!-- 		                <th>Social Media</th> -->
<!-- 		                <th>Keyword</th> -->
<!--             	</tr> -->
<!--         		</tfoot> -->
<!--         		<tbody> -->
         
<%--   					<s:iterator value="allRecords" var="postRecord"> --%>
<!--                   	 <tr> -->
<%--                     <td width="75px"><s:property value="#postRecord.value.dateCreated" /></td> --%>
<%--                     <td width="200px"><s:property value="#postRecord.value.text" /></td> --%>
<%--                     <td width="100px"><s:property value="#postRecord.value.creator" /></td> --%>
<%--                     <td width="75px"><s:property value="#postRecord.value.type" /></td> --%>
<%--                     <td width="75px"><s:property value="#postRecord.value.hashtag" /></td> --%>
<!--                     </tr> -->
<%-- 				</s:iterator> --%>
		
<!-- 				</tbody>  -->
<!--   				</table> -->

              
<%--                 </s:else> --%>
	
				</div> 
        <!-- /.row -->

        <!-- Related Projects Row -->
 

</body>


</html>
