<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ page import="com.mastery.java.task.model.Gender" %>


<form id="employeeData">
    <div class="d-flex flex-column align-items-stretch">
        <div class="d-flex ">
            <div class="flex-grow-1 d-flex flex-column align-items-start justify-content-start">
                <div class="input-group input-group-sm mt-3" style="width: 16em;">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon1" style="width: 8em;">ID</span>
                    </div>
                    <input id="id" type="text" readonly="true" style="text-align:center;  font-weight: bold;"
                           autocomplete="off" name="id" class="form-control"
                           aria-describedby="basic-addon1"/>
                </div>
                <div class="input-group input-group-sm mt-3" style="width: 20em;">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon2" style="width: 8em;">Last Name</span>
                    </div>
                    <input id="lastName" type="text" autocomplete="off" name="lastName" required="true"
                           class="form-control" aria-describedby="basic-addon2"/>
                </div>
                <div class="input-group input-group-sm mt-3" style="width: 20em;">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon3" style="width: 8em;">First Name</span>
                    </div>
                    <input id="firstName" type="text" autocomplete="off" name="firstName" required="true"
                           class="form-control" aria-describedby="basic-addon3"/>
                </div>
            </div>

            <div class="flex-grow-1 d-flex flex-column align-items-start justify-content-start">

                <div class="input-group input-group-sm mt-3" style="width: 16em;">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon4" style="width: 8em;">Dept. ID</span>
                    </div>
                    <input id="departmentId" type="number" min="0" step="1" autocomplete="off" name="departmentId"
                           required="true"
                           class="form-control" aria-describedby="basic-addon4"/>
                </div>

                <div class="input-group input-group-sm mt-3" style="width: 16em;">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon5" style="width: 8em;">Gender</span>
                    </div>

                    <select id="gender" class="form-control" id="mode" name="gender" aria-describedby="basic-addon5">
                        <option value=${Gender.MALE}>Male</option>
                        <option value=${Gender.FEMALE}>Female</option>
                    </select>

                </div>

                <div class="input-group input-group-sm mt-3" style="width: 16em;">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon6" style="width: 8em;">Date of Birth</span>
                    </div>
                    <input id="dateOfBirth" type="date" class="form-control" name="dateOfBirth" required="true"
                           aria-describedby="basic-addon6"/>
                </div>

            </div>
        </div>
        <div class="input-group input-group-sm mt-3">
            <div class="input-group-prepend">
                <span class="input-group-text" id="basic-addon7" style="width: 8em;">Job Title</span>
            </div>
            <input id="jobTitle" class="form-control" type="text" autocomplete="off" name="jobTitle" required="true"
                   aria-describedby="basic-addon7"/>
        </div>

    </div>
</form>

<div class="d-flex justify-content-between align-items-center mb-5 mt-5">
    <button class="btn btn-outline-primary" onclick="window.location.href='/employees'">Go to the List of Employees
    </button>
    <button id="<tiles:insertAttribute name="saveButtonName"/>" class="btn btn-outline-secondary"
            style="width: 15em;">
        <tiles:insertAttribute name="saveButtonName"/></button>
    <button id="delete" class="btn btn-outline-danger">Delete</button>
</div>

<script type="text/javascript">
    $.ajax({
        url: '/api/employees/' + ${id},
        dataType: 'json',
        success: function (data, textStatus, xhr) {
            $("#id").val(data.id);
            $("#firstName").val(data.firstName);
            $("#lastName").val(data.lastName);
            $("#departmentId").val(data.departmentId);
            $("#jobTitle").val(data.jobTitle);
            $("#gender").val(data.gender);
            $("#dateOfBirth").val(data.dateOfBirth);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(JSON.parse(xhr.responseText)["message"]);
        }
    });
</script>


<script>
    $(document).ready(function () {
        $("#Update").on('click', function () {
            $.ajax({
                url: '/api/employees/' + ${id},
                type: 'put',
                contentType: 'application/json',
                data: getFormAsJson(),
                success: function (data, textStatus, xhr) {
                    window.location.href = '/employees';
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    alert(JSON.parse(xhr.responseText)["message"]);
                }
            })
        });
    });
</script>

<script>
    $(document).ready(function () {
        $("#Create").on('click', function () {
            $.ajax({
                url: '/api/employees',
                type: 'post',
                contentType: 'application/json',
                data: getFormAsJson(),
                success: function (data, textStatus, xhr) {
                    window.location.href = "/employees";
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    alert(JSON.parse(xhr.responseText)["message"]);
                }
            })
        });
    });
</script>

<script>
    $(document).ready(function () {
        $("#delete").on('click', function () {
            $.ajax({
                url: '/api/employees/' + ${id},
                type: 'delete',
                success: function (data, textStatus, xhr) {
                    window.location.href = '/employees';
                },
            })
        });
    });
</script>

<script>
    function getFormAsJson() {
        var employee = {
            id: $("#id").val(),
            firstName: $("#firstName").val(),
            lastName: $("#lastName").val(),
            departmentId: $("#departmentId").val(),
            jobTitle: $("#jobTitle").val(),
            gender: $("#gender").val(),
            dateOfBirth: $("#dateOfBirth").val()
        };
        return JSON.stringify(employee);
    }
</script>