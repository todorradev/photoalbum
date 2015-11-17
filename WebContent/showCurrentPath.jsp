<%@ page contentType="text/html;charset=UTF-8" %>

<%@page import="com.toshko.photoalbum.db.CategoryDB"%>
<%@page import="com.toshko.photoalbum.dto.Category"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.LinkedList"%>

<%
  String categoryId = (String)request.getParameter("categoryId");
  if (categoryId == null) {
	  categoryId = String.valueOf(request.getAttribute("categoryId"));
  }
  Category category = null;
  if (categoryId != null) {
	  CategoryDB categoryDB = new CategoryDB();
	  category = categoryDB.getCategory(Integer.parseInt(categoryId));
  }
  
  if (category == null) {
	  throw new RuntimeException("Stupid bastard category is null, categoryId=" + categoryId);
  }
  
  Collection<Category> parentCategories = (Collection<Category>)session.getAttribute("parentCategories");
%>

<div class="jumbotron">
	<p class="folderPath">
		<%
			for (Category tmpCategory : parentCategories) { 
		%>
				<a href="/PhotoAlbum/showCategories.do?categoryId=<%= tmpCategory.getId()%>"><%= tmpCategory.getName() %></a>&gt;
		<% } %>
		<a href="/PhotoAlbum/showCategories.do?categoryId=<%= category.getId()%>">
			<%= category.getName() %> 
	   	</a>
   	</p>
</div>