<!DOCTYPE html>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<head>
    <meta charset=utf-8>
    <link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico"/>" type="image/png">

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-social/4.10.1/bootstrap-social.css" rel="stylesheet">
    <link rel="stylesheet" media="screen" href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
    <link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.0.3/css/font-awesome.css'>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="<c:url value="/resources/css/font-awesome.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/main.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/table.css"/>">
    <script src="<c:url value="/resources/js/onLoad.js"/>"></script>
</head>

<body class="d-flex flex-column bg-light">
<tiles:insertAttribute name="header"/>
<div class="container d-flex flex-column flex-grow-1 bg-white" style="overflow-y: auto;" id="main">
    <div class="row col-sm-12 d-flex flex-column flex-grow-1">
        <article class="col-sm-12 d-flex flex-column flex-grow-1">
            <div class="d-flex flex-column flex-grow-1">
                <div>
                    <header class="page-header ">
                        <h4 class="page-title"><tiles:insertAttribute name="caption"/></h4>
                    </header>
                    <hr class="m-1">
                </div>
                <tiles:insertAttribute name="body"/>
            </div>
        </article>
    </div>
</div>
<tiles:insertAttribute name="footer"/>

</body>
</html>