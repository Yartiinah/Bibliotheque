<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Ajouter un livre</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/navbar.jsp" />
<h2>Ajouter un livre</h2>
<form:form method="post" modelAttribute="livre">
    <label>Titre : <form:input path="titre" required="true"/></label><br>
    <label>Auteur : <form:input path="auteur" required="true"/></label><br>
    <label>Catégorie :
        <form:select path="categorie.id">
            <form:options items="${categories}" itemValue="id" itemLabel="nom"/>
        </form:select>
    </label><br>
    <label>ISBN : <form:input path="isbn"/></label><br>
    <label>Restriction : <form:input path="restriction"/></label><br>
    <button type="submit">Ajouter</button>
</form:form>
<c:if test="${not empty message}">
    <p style="color:blue;">${message}</p>
</c:if>
</body>
</html>
