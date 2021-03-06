<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<%@ page import="org.hibernate.Session"%>

<%@ 
	page
	import="org.cnio.appform.util.HibernateUtil,
			org.cnio.appform.util.AppUserCtrl, org.cnio.appform.util.Singleton,
			org.cnio.appform.entity.AppuserRole, org.cnio.appform.entity.Role,
			org.cnio.appform.entity.AppUser, org.cnio.appform.entity.AppGroup, 
			org.cnio.appform.entity.GroupType, org.cnio.appform.entity.Project"%>
			
<%@ page import="java.util.List"%>

<%
	// Checking the users permissions
	String user = request.getUserPrincipal().getName();
System.out.println("Principal's name: "+user);

	List<Role> roleList = null;
	AppUserCtrl userCtrl = null;
	Session hibSes = null;
	
	hibSes = HibernateUtil.getSessionFactory().openSession();

	userCtrl = new AppUserCtrl(hibSes);
	roleList = userCtrl.getAllRoles();
/*	
	List<String> activeUsrs = Singleton.getInstance().getLoggedUsers();
	activeUsrs.remove(user);
	pageContext.setAttribute("activeUsrs", activeUsrs);
	pageContext.setAttribute ("numUsrs", activeUsrs.size());
*/	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">

  <!-- Le styles -->
  <link href="../css/bootstrap.min.css" rel="stylesheet">
  <link href="../css/bootstrap-responsive.min.css" rel="stylesheet">
  <style type="text/css">
  /*
    body {
      padding-top: 160px;
      padding-bottom: 40px;
    }
*/
    /* Sticky footer styles
      -------------------------------------------------- */

      html,
      body {
        height: 100%;
        /* The html and body elements cannot have any padding or margin. */
      }

      /* Wrapper for page content to push down footer */
      #wrap {
        min-height: 100%;
        height: auto !important;
        height: 100%;
        /* Negative indent footer by it's height */
        margin: 0 auto -60px;
      }

      /* Set the fixed height of the footer here */
      #push,
      #footer {
        height: 60px;
      }
      #footer {
        background-color: #f5f5f5;
      }

      /* Lastly, apply responsive CSS fixes as necessary */
      @media (max-width: 767px) {
        #footer {
          margin-left: -20px;
          margin-right: -20px;
          padding-left: 20px;
          padding-right: 20px;
        }
      }

      /* Custom page CSS
      -------------------------------------------------- */
      /* Not required for template or sticky footer method. */

      #wrap > .container {
        padding-top: 100px;
      }

      #wrap > .container-fluid  {
        padding-top: 5%;
      }

      .container-fluid > hr {
        margin: 60px 0;
      }

      .container-fluid .intro-description {
        padding: 1% 3%;
        font-size: 24px;
        line-height: 1.25;
      }

      .container-fluid .adminrow {
        /* padding-left: 5%; */
      }

      .page-title {
        padding-left: 2%;
        text-align: center;
      }

      .page-title h1 {
        font-size: 42px
      }

      
  </style>
  
  <title>EPIQUEST Admin</title>
</head>
<body>
<div id="wrap">

<%-- 
<div class="navbar navbar-inverse navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container-fluid">
      <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
        <span class="icon-bar">x</span>
        <span class="icon-bar">y</span>
        <span class="icon-bar">z</span>
      </button>
      <a class="brand" href="#">EPIQUEST Admin</a>
      <div class="nav-collapse collapse">
        
        <p class="navbar-text pull-right">
          Logged in as <a href="#" class="navbar-link">username (administrator)</a>
        </p>
      
        <ul class="nav nav-pills">
          <li><a href="users.jsp">Users</a></li>
          <li><a href="cloning.jsp">Cloning</a></li>
          <!-- <li><a href="#contact">Contact</a></li> -->
          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">DB Management
              <b class="caret"></b>
            </a>
            <ul class="dropdown-menu">
              <li><a href="delpatients.jsp">Delete subjects</a></li>
              <li><a href="delintrvs.jsp">Delete interviews</a></li>
              <li><a href="changecodes.jsp">Change subjects code</a></li>
              <li class="divider"></li>
              <li style="background-color: #DEDEDE"><a href="dbdumps.jsp">Database dumps</a></li>
            </ul>
          </li>
        </ul>
      </div> !--/.nav-collapse --
    </div>
  </div>
</div>
--%>

<%@include file="inc/navbar.jsp" %>

<div class="container-fluid">
  <div class="page-header page-title">
    <h1>EPIQUEST admin tool - Data dumper</h1>
  </div>
  <!-- Intro -->
  <div class="row-fluid">
    <div class="span12">
    <p class="intro-description text-center">
      Choose the right project, group/hospital/department, questionnaire and one or more sections in order to get the data dump for the requested data.
    </p>
    </div>
  </div>
  <hr>

  <!-- Admins -->
  <form class="form-horizontal" id="frmDump" name="frmDump">
  <div class="row-fluid" style="padding-top: 3%;">
    <div class="span2 offset1" style="margin-left: 12%">
      <label>Project</label>
      <select class="input-large" id="frmProject" name="frmProject">
      	<option value="-1" selected="selected">Choose</option>
        <%
					List<Project> projectList = userCtrl.getAllProjects();
					for (Project project : projectList) {
						out.println("<option value='" + project.getProjectCode() + "'>"
								+ project.getName() + "</option>");
					}
				%>
      </select>
    </div>
  
    <div class="span2">  
      <label>Group/Country</label>
      <select class="input-large" id="frmGroup" name="frmGroup">
        <option value="-1" selected="selected">Choose</option>
        <%
				List<AppGroup> primaryGrps = userCtrl.getPrimaryGroups();
				for (AppGroup group : primaryGrps) {
					out.println("<option value=\"" + group.getId() + "\"" + 
							" onmouseover=\"Tip('"+ group.getName() + "');\" onmouseout=\"UnTip();\">" + 
							group.getName()
							+ "</option>");
				}
				%>
      </select>
    </div>
      
    <div class="span2">
      <label>Questionnaire</label>
      <select class="input-large" id="frmQuestionnaire" name="frmQuestionnaire">
        <option value="-1" selected="selected">Choose</option>
        <!-- 
        <option value="-1">QES Español</option>
        <option value="-1">IDC</option>
        -->
      </select>
    </div>
    <div class="span2">
      <label>Section</label>
      <select class="input-large" id="frmSection" name="frmSection">
        <option value="-1" selected="selected">Choose</option>
        <!-- 
        <option value="-1">Introducción</option>
        <option value="-1">Datos personales</option>
       	-->
      </select>
    </div>

    <div class="span2" style="padding-left:2%;">
      <button class="btn btn-small btn-primary" id="btnSend" type="button" style="margin-top:17%">Send</button>
      <button class="btn btn-small btn-primary" id="btnReset" type="reset" style="margin-top:17%">Reset</button>
    </div>
      
  </div> <!-- EO row-fluid -->
  </form>
</div> <!-- EO container -->

</div> <!-- EO wrap -->

<%-- this is to create the modal "dialog" to run the progress bar --%>
<div id="overlay" xstyle="min-height:100%; height:100%" style="visibility:hidden;">
	<div>
		<p style="font-family: Arial, Helvetica, sans-serif; Font-size: 12px;">
		Processing...</p>
		<p><img src="../img/ajax-loader-trans.gif" alt="Processing..." /></p>
	</div>
</div>

<div id="footer">
  <div class="container">
    <p class="muted credit">
      &copy; INB, CNIO - Epidemiology Group
    </p>
  </div>
</div>

<script type="text/javascript" src="../js/lib/jquery-1.9.1.js"></script>

<script type="text/javascript" src="../js/admin-cfg.js"></script>

<script type="text/javascript" src="../js/yahoo/yahoo-dom-event.js"></script>
<script type="text/javascript" src="../js/yahoo/connection-debug.js"></script>
<script type="text/javascript" src="../js/yahoo/json-debug.js"></script>

<script type="text/javascript" src="../js/overlay.js"></script>
<script type="text/javascript" src="../js/yahoo/ajaxreq.js"></script>

<script type="text/javascript" src="../js/dbdump-ajaxresp.js"></script>
<script type="text/javascript" src="../js/dbdumps.js"></script>

<script type="text/javascript" src="../js/wz_tooltip.js"></script>

<script type="text/javascript" src="../js/lib/bootstrap.js"></script>




</body>
</html>