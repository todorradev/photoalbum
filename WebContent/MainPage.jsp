<!DOCTYPE>
<%@page import="com.toshko.photoalbum.dto.Picture"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="com.toshko.photoalbum.dto.Category"%>
<%@page import="java.util.Collection"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

	<!-- Add jQuery library -->
	<script type="text/javascript" src="/PhotoAlbum/fancybox/lib/jquery-1.10.1.min.js"></script>

	<!-- Add mousewheel plugin (this is optional) -->
	<script type="text/javascript" src="/PhotoAlbum/fancybox/lib/jquery.mousewheel-3.0.6.pack.js"></script>

	<!-- Add fancyBox main JS and CSS files -->
	<script type="text/javascript" src="/PhotoAlbum/fancybox/source/jquery.fancybox.js?v=2.1.5"></script>
	<link rel="stylesheet" type="text/css" href="/PhotoAlbum/fancybox/source/jquery.fancybox.css?v=2.1.5" media="screen" />

	<!-- Add Button helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="/PhotoAlbum/fancybox/source/helpers/jquery.fancybox-buttons.css?v=1.0.5" />
	<script type="text/javascript" src="/PhotoAlbum/fancybox/source/helpers/jquery.fancybox-buttons.js?v=1.0.5"></script>

	<!-- Add Thumbnail helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="/PhotoAlbum/fancybox/source/helpers/jquery.fancybox-thumbs.css?v=1.0.7" />
	<script type="text/javascript" src="/PhotoAlbum/fancybox/source/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script>

	<!-- Add Media helper (this is optional) -->
	<script type="text/javascript" src="/PhotoAlbum/fancybox/source/helpers/jquery.fancybox-media.js?v=1.0.6"></script>
	
<script type="text/javascript">
$(document).ready(function() {
 $(".fancyGroup").fancybox();
 });
 
function showNavigationIcons(id)
{
	var deleteImg = document.getElementById("deleteIcon" + id);
	deleteImg.style.display="inline";
	var editImg = document.getElementById("editIcon" + id);
	editImg.style.display="inline";
	var rotateImg = document.getElementById("rotateIcon" + id);
	rotateImg.style.display="inline";
	var faceDetectionImg = document.getElementById("faceDetectionIcon" + id);
	faceDetectionImg.style.display="inline";
}

function hideNavigationIcons(id)
{
	var deleteImg = document.getElementById("deleteIcon" + id);
	deleteImg.style.display="none";
	var editImg = document.getElementById("editIcon" + id);
	editImg.style.display="none";
	var rotateImg = document.getElementById("rotateIcon" + id);
	rotateImg.style.display="none";
	var faceDetectionImg = document.getElementById("faceDetectionIcon" + id);
	faceDetectionImg.style.display="none";
}

function deleteImage(href)
{
	// Ask the user if he really need to remove the image
    if (confirm("Наистина ли искате да изтриете тази снимка?") == true) {
    	window.location.href=href;
    }
}

function editImage(href)
{
	window.location.href=href;
}

function rotateImage(href)
{
	window.location.href=href;
}

function faceDetectionImage(href)
{
	window.location.href=href;
}

</script>
	<title>Photoalbum</title>
	<link rel="stylesheet" href="/PhotoAlbum/MainPage.css" type="text/css">
</head>
<body>
	<% 
		String categoryId = (String)request.getParameter("categoryId");
		if (categoryId == null) {
			categoryId = String.valueOf(request.getAttribute("categoryId"));
		}
		
	%>

	<jsp:include page="header.jsp"></jsp:include>
		
	<div class="container">
		<div class="row row-offcanvas row-offcanvas-right">
			<div class="col-xs-12 col-sm-9">
				<jsp:include page="showCurrentPath.jsp"/>

				<div class="row">
					<%
						Collection<Category> categories = (Collection<Category>)request.getAttribute("childCategories");
						if(categories != null) {
							for (Category tmpCategory : categories) {
						%>
							<div class="col-6 col-sm-6 col-lg-4">
								<h2><%= tmpCategory.getName() %></h2>
								<a href="/PhotoAlbum/showCategories.do?categoryId=<%= tmpCategory.getId()%>">
									<img id="directory" src="/PhotoAlbum/img/folder.jpg">
								</a>
							
							</div>
						<% } %>
					<% } %>
					<%
					
						Collection<Picture> pictures = (Collection<Picture>)request.getAttribute("pictures");
						for (Picture picture : pictures) {
							String deleteImgHref = "/PhotoAlbum/deletePicture.do?categoryId="+ categoryId+"&pictureId="+picture.getId();
							String editImgHref="EditPicture.jsp?userId="+request.getAttribute("userId")+"&categoryId="+categoryId+"&pictureId="+picture.getId();
							String downloadImgHref = "/PhotoAlbum/downloadImage/"+picture.getName()+".jpg?userId=" + request.getAttribute("userId") + "&categoryId=" + categoryId + "&pictureId=" + picture.getId();
							String showImgHref ="/PhotoAlbum/showPictures.do?userId=" + request.getAttribute("userId") + "&categoryId=" + categoryId + "&pictureId=" + picture.getId();
							String rotateImg = "/PhotoAlbum/rotatePicture.do?userId=" + request.getAttribute("userId") + "&categoryId=" + categoryId + "&pictureId=" + picture.getId();
							boolean test = true;
							String faceDetectionImg = "/PhotoAlbum/showCategories.do?userId=" + request.getAttribute("userId") + "&categoryId=" + categoryId + "&pictureId=" + picture.getId() + "&faceDetection=" + test;
					%>
							<div class="col-6 col-sm-6 col-lg-4" onmouseover="showNavigationIcons(<%=picture.getId()%>)" onmouseout="hideNavigationIcons(<%=picture.getId()%>)">
								<h2><%= picture.getName()%></h2>
								<a class="fancyGroup" rel="group" href="<%=downloadImgHref%>" title="<%=picture.getDescription() %>">
									<img class="normalPicture"  src="<%=downloadImgHref%>" alt="<%= picture.getName()%>" >
								</a>
								<img class="deleteIcon" id="deleteIcon<%=picture.getId()%>" src="/PhotoAlbum/img/delete.jpg" onclick="deleteImage('<%=deleteImgHref%>')">
								<img class="editIcon" id="editIcon<%=picture.getId()%>" src="/PhotoAlbum/img/edit.jpg" onclick="editImage('<%=editImgHref%>')">
								<img class="rotateIcon" id="rotateIcon<%=picture.getId()%>" src="/PhotoAlbum/img/rotate.png" onclick="rotateImage('<%=rotateImg%>')">
								<img class="faceDetectionIcon" id="faceDetectionIcon<%=picture.getId()%>" src="/PhotoAlbum/img/faceDetection.png" onclick="rotateImage('<%=faceDetectionImg%>')">
							</div>
					<% } %>
				</div><!--/row-->
			</div><!--/span-->

			<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar" role="navigation">
				<jsp:include page="menu.jsp">
					<jsp:param name="showCategoryMenu" value="True"/>
				</jsp:include>
			</div><!--/span-->
			
		</div><!--/row-->

	    <jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>