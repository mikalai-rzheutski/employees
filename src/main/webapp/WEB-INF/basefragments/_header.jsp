<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #080815;" id="navigation">
    <div class="container d-flex justify-content-between align-items-center">
        <ul class="m-0"><h2 class="page-title text-white p-0 m-2" href="#"><i style="white-space: nowrap;">Company</i>
        </h2></ul>
        <ul>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target=".navbar-collapse"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span
                    class="navbar-toggler-icon"></span>
            </button>
        </ul>
        <ul class="m-0">
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ">
                    <li class="nav-item">
                        <a class="nav-link text-warning mr-3" style="white-space: nowrap;"
                           href="<c:url value = "/employees"/>">Employees</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-warning mr-3" style="white-space: nowrap;"
                           href="<c:url value = "/loggers"/>">Loggers</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-info mr-3" style="white-space: nowrap;" target="_blank"
                           href="<c:url value = "/swagger-ui.html"/>">REST API / Swagger UI</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-warning" style="white-space: nowrap;" href="<c:url value = "/about"/>">About</a>
                    </li>
                </ul>
            </div>
        </ul>

    </div>
</nav>