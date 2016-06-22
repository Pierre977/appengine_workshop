<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<html>
<body>

<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    String userName;
    String linkText;
    String link;
    if (user != null) {
    	userName = user.getNickname();
    	linkText = "sign out";
    	link = userService.createLogoutURL(request.getRequestURI());
    } else {
    	userName = "Anonimous";
    	linkText = "sign in";
    	link = userService.createLoginURL(request.getRequestURI());
    }
%>

<p>Welcome <%=userName%></p>
<p>You can <a href="<%=link%></p>"><%=linkText%></a> </p>
</body>
</html>