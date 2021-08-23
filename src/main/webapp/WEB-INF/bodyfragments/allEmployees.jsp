<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>

<div class="d-flex justify-content-between align-items-center mt-3 mb-3">
    <div class="d-inline-flex col-sm-5 col-lg-3">
        <input id="search" type="text" class="name form-control ml-5 " style="width: 12em" autocomplete="off"
               placeholder="search">
        <button class="btn btn-outline-dark ml-2" onclick="clearSearch()">X</button>
    </div>
    <form class="form-inline m-0 " style="height: 2em; float: right;" action="/employees/new/">
        <button class="btn btn-outline-info m-0"> Create new Employee</button>
    </form>
</div>

<div class="tableFixHead mt-2 bg-white flex-grow-1">
    <table class="table table-hover" id="tableOfEmployees">
        <thead class="thead-dark">
        <tr align="center">
            <th style="width:4em">ID</th>
            <th style="width:10em">Last Name</th>
            <th style="width:10em">First Name</th>
            <th style="width:5em">Dept.ID</th>
            <th style="width:10em">Position</th>
            <th style="width:8em">Gender</th>
            <th style="width:10em">D.O.B.</th>
            <th style="width:8em"></th>
        </tr>
        </thead>
        <tbody id="tableBody">
        <script type="text/javascript">
            $.ajax({
                url: '/api/employees',
                dataType: 'json',
                success: function (data) {
                    for (var i = 0; i < data.length; i++) {
                        var row = $('<tr align="center"><td>' + data[i].id + '</td><td>' + data[i].lastName + '</td><td>' + data[i].firstName + '</td><td>' +
                            data[i].departmentId + '</td><td>' + data[i].jobTitle + '</td><td>' + data[i].gender + '</td><td>' +
                            data[i].dateOfBirth + '</td><td>' + '<button class="btn btn-outline-primary btn-sm" onclick="window.location.href=\'/employees/' + data[i].id + '\'">edit</button>' + '</td></tr>');
                        $('#tableOfEmployees').append(row);
                    }
                }
            });
        </script>
        </tbody>
    </table>
</div>

<script src="<c:url value="/resources/js/allEmployeeTable.js"/>"></script>