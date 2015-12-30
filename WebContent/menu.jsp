<%@ page contentType="text/html;charset=UTF-8" %>

<%@page import="com.toshko.photoalbum.dto.Category"%>
<%@page import="com.toshko.photoalbum.db.CategoryDB"%>

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
</head>

<script type="text/javascript">

function deleteUser(href)
{
	// Ask the user if he really wants to delete his profile
	if (confirm("Сигурни ли сте, че искате да изтриете вашия профил?") == true) {
		window.location.href=href;
	}
}

function deleteCategory(href)
{
	// Ask the user if he really wants to delete his profile
	if (confirm("Сигурни ли сте, че искате да изтриете текущатата категория?") == true) {
		window.location.href=href;
	}
}

/* $(".list-group-item").fancybox({ 
	autoScale: false,
	type: 'iframe',
	padding: 0,
	closeClick: false
}); */

</script>
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
	
	String userId = (String)request.getParameter("userId");
	if (userId == null) {
		userId = String.valueOf(request.getAttribute("userId"));	
	}

	String deleteUserHref="/PhotoAlbum/deleteUser.do?userId=" + userId; 
	String deleteCategoryHref="/PhotoAlbum/deleteCategory.do?userId=" + userId + "&categoryId=" + categoryId;
	
	Object showCategoryMenuObj = request.getParameter("showCategoryMenu");
	boolean showCategoryMenu = true;
	if (showCategoryMenuObj != null && !Boolean.parseBoolean(showCategoryMenuObj.toString())) {
		showCategoryMenu = false;
	}

	Object activeMenuItem = request.getParameter("activeMenuItem");

	String homeClass = activeMenuItem == null? "list-group-item active" : "list-group-item";
	String addPictureClass = "addPicture".equals(activeMenuItem)? "list-group-item active" : "list-group-item";
	String addCategoryClass = "addCategory".equals(activeMenuItem)? "list-group-item active" : "list-group-item";
	String editCategoryClass = "editCategory".equals(activeMenuItem)? "list-group-item active" : "list-group-item";
	String deleteCategoryClass = "deleteCategory".equals(activeMenuItem)? "list-group-item active" : "list-group-item";
	String editProfileClass = "editProfile".equals(activeMenuItem)? "list-group-item active" : "list-group-item";
	String changePasswordClass = "changePassword".equals(activeMenuItem)? "list-group-item active" : "list-group-item";
%>
<div class="list-group">
	<a href = "/PhotoAlbum/showCategories.do" class="<%= homeClass%>">Начало</a>
	<% if (showCategoryMenu) { %>
		<a href = "/PhotoAlbum/AddPicture.jsp?userId=<%=userId%>&categoryId=<%= categoryId%>" class="<%= addPictureClass%>">Добави нова снимка</a>
		<a href = "/PhotoAlbum/AddCategory.jsp?userId=<%=userId %>&categoryId=<%= categoryId%>" class="<%= addCategoryClass%>">Добави нова категория</a>
		<a href="EditCategory.jsp?userId=<%=userId %>&childCategoryId=<%=categoryId%>&categoryId=<%=categoryId%>&nameOfCategory=<%=category.getName()%>&descriptionOfCategory=<%=category.getDescription()%>" class="<%= editCategoryClass%>">Промяна на текущата категория</a>
		<a id="deleteCategoryOptions" class="<%= deleteCategoryClass%>" onclick="deleteCategory('<%=deleteCategoryHref%>')">Изтриване на текущата категория</a>
	<% } %>
	<form method="GET" action="editProfile" class="editProfileForm">		
		<a class="<%= editProfileClass%>" id="editProfile" href="EditProfile.jsp?userId=<%=userId%>&categoryId=<%=categoryId%>">Промяна на профил</a>
	</form>
	<a href="/PhotoAlbum/ChangePassword.jsp?userId=<%=userId%>&categoryId=<%=categoryId%>" class="<%= changePasswordClass%>">Промяна на парола</a>
	<a id="deleteUserProfileOption" class="list-group-item" onclick="deleteUser('<%=deleteUserHref%>')">Изтриване на потребител</a>
	<form method="GET" action="logout">
		<a href="/PhotoAlbum/logout" class="list-group-item">Изход</a>
	</form>
</div>