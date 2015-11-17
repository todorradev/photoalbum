<%@ page contentType="text/html;charset=UTF-8" %>

<%@page import="com.toshko.photoalbum.db.CategoryDB"%>
<%@page import="com.toshko.photoalbum.data.UserUtils"%>
<%@page import="com.toshko.photoalbum.db.UserRegistry"%>
<%@page import="com.toshko.photoalbum.dto.User"%>
<%@page import="com.toshko.photoalbum.dto.Category"%>
<%@page import="java.util.Collection"%>

<%
	String categoryId = (String)request.getParameter("categoryId");
	if (categoryId == null) {
		categoryId = String.valueOf(request.getAttribute("categoryId"));
	}
	String searchCategoriesAndPictures = (String)request.getAttribute("searchCategoriesAndPictures");
	if (searchCategoriesAndPictures == null) {
		searchCategoriesAndPictures = "";
	}
%>
  

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<a href = "/PhotoAlbum/showCategories.do" class="navbar-brand" >Photoalbum</a>
		<form class="navbar-form navbar-right"  method="POST" action="/PhotoAlbum/showCategories.do">
			<input type="text" name="searchCategoriesAndPictures" placeholder="Търси" value="<%= searchCategoriesAndPictures%>" class="form-control"/>
			<input type="hidden" name="categoryId" value="<%= categoryId%>" />
			<!-- <input id="searchButton" type="submit" value="Търси" name="submit"/> -->
       	</form>
   	</div><!-- /.container-fluid -->
</div><!-- /.navbar -->
