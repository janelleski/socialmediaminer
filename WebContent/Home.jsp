<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

 	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">


    <title>Social Media Web Crawler</title>
 	<!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/portfolio-item.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    
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
    
   

</head>
<script type="text/javascript">


$(document).ready(function(){
	   $('[data-toggle="offcanvas"]').click(function(){
	       $("#navigation").toggleClass("hidden-xs");
	   });
	   
	   $('#example').DataTable( {
		   "columns": [
	  	                { "width": "20%" },
	  	              { "width": "30%" },
	  	            { "width": "20%" },
	  	          { "width": "10%" },
	  	        { "width": "20%" }
	  	              ],
	   
  	        dom: 'Bfrtip',
  	        buttons: [
  	            'copy', 'csv', 'excel', 'pdf', 'print'
  	        ],
  	   
  	    } );
	   
	   $("#loading").dialog({
		    hide: 'slide',
			show: 'slide',
			autoOpen: false
		});
	   
	});

function submitForm(){
	
		var e = document.getElementById("keywordOption");
		var keyword = document.getElementById("search").value;
		var strUser = e.options[e.selectedIndex].value;
		if(strUser=="Wordpress")
		{	
			
			
			var service = 'https://public-api.wordpress.com/rest/v1.1/read/tags/'+keyword+'/posts?number=40';
			//alert("Not redirecting for:" + service);
			  jQuery.support.cors = true;

			    $.ajax(
			    {
			        type: "GET",
			        url: service,
			        data: "{}",
			        contentType: "application/json; charset=utf-8",
			        dataType: "json",
			        cache: false,
			        beforeSend: alert("Processing request..."),
			        success: function (data) {
			        	
			        	//alert(data.posts[0].author.first_name);
			       
			        var t = $('#example').DataTable();
			                
			        $.each(data.posts, function (i, item) {
			        	t.row.add( [
			        	            data.posts[i].date,
			        	           '<a href="'+data.posts[i].short_URL+'">'+ data.posts[i].title+'</a>',
			        	            data.posts[i].excerpt,
			        	            data.posts[i].author.first_name,
			        	            'wordpress'
			        	            
			        	            
			        	        ] ).draw( false );
			        	
			        });
			        
			        },

			        error: function (msg) {
			            
			            alert(msg.responseText);
			        }
			    });
			    
			return false;
		}else 
		{
			document.forms["searchForm"].submit();
			return true;
		}
		
	
	}
</script>


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
             <s:form action="searchKeyword" name="searchForm" onsubmit="submitForm()">
             <div class="col-lg-12">
                <h1 class="page-header">Search posts for # <input type="text" id="search" name="keywordString" placeholder="example: cheesecake"style="max-width:100%"> </h1>
            </div>

            <div class="col-sm-3 col-xs-6">
               Choose a Source:
            </div>

            <div class="col-sm-3 col-xs-6">
            
              <select class="form-control" id="keywordOption" name="keywordOption" style="width:150px">
                                                <option>Facebook</option>
                                                <option>Twitter</option>
                                                <option>Google+</option>
                                                <option>YouTube</option>
                                                <option>Wordpress</option>
                                                <option>All</option>
                                            </select>
            </div>
           <s:token />
           
            <input type="button" class="btn btn-info" value="Search" style="width:125px"  onclick="submitForm()">
		</s:form>

            </div>
        </div>
        <!-- /end item heading -->

        <!-- Portfolio Item Row -->
        <div class="row">
        <div class="col-lg-12">
        <br/><br/>
         <table id="example" class="display" cellspacing="0" width="100%">
 				 <thead>
            		<tr>
		                <th>Date Created</th>
		                <th>Post Title</th>
		                <th>Content</th>
		                <th>Author</th>
		                <th>Social Media</th>
		              
            		</tr>
        		</thead>
        
        		<tbody>
         
<%--   					<s:iterator value="allRecords" var="postRecord"> --%>
<!--                   	 <tr> -->
<%--                     <td width="75px"><s:property value="#postRecord.value.dateCreated" /></td> --%>
<%--                     <td width="200px"><s:property value="#postRecord.value.text" /></td> --%>
<%--                     <td width="100px"><s:property value="#postRecord.value.creator" /></td> --%>
<%--                     <td width="75px"><s:property value="#postRecord.value.type" /></td> --%>
<%--                     <td width="75px"><s:property value="#postRecord.value.hashtag" /></td> --%>
<!--                     </tr> -->
<%-- 				</s:iterator> --%>
		
				</tbody> 
  				</table>
        </div>

<!--           <table id="location" border='1'> -->
<!--     <tr> -->
<!--         <th>Status</th> -->
<!--          <th>Firstname</th> -->
<!--     </tr> -->
<!-- </table> -->

        </div>
        <!-- /.row -->

<!--         Related Projects Row -->
<!--         <div class="row"> -->

<!--             <div class="col-lg-12"> -->
<!--                 <h3 class="page-header">Related Projects</h3> -->
<!--             </div> -->

<!--             <div class="col-sm-3 col-xs-6"> -->
<!--                 <a href="#"> -->
<!--                     <img class="img-responsive portfolio-item" src="http://placehold.it/500x300" alt=""> -->
<!--                 </a> -->
<!--             </div> -->

<!--             <div class="col-sm-3 col-xs-6"> -->
<!--                 <a href="#"> -->
<!--                     <img class="img-responsive portfolio-item" src="http://placehold.it/500x300" alt=""> -->
<!--                 </a> -->
<!--             </div> -->

<!--             <div class="col-sm-3 col-xs-6"> -->
<!--                 <a href="#"> -->
<!--                     <img class="img-responsive portfolio-item" src="http://placehold.it/500x300" alt=""> -->
<!--                 </a> -->
<!--             </div> -->

<!--             <div class="col-sm-3 col-xs-6"> -->
<!--                 <a href="#"> -->
<!--                     <img class="img-responsive portfolio-item" src="http://placehold.it/500x300" alt=""> -->
<!--                 </a> -->
<!--             </div> -->

<!--         </div> -->
<!--         /.row -->

        <hr>

        <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>Copyright &copy; Social Media Miner 2016</p>
                </div>
            </div>
            <!-- /.row -->
        </footer>

    </div>
    <!-- /.container -->




</body>
</html>
