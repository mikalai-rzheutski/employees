<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<br>
<div class="d-flex flex-column justify-content-center align-items-start">
    <select id="loggerSelector" class="form-select m-5 p-2 w-100">
    </select>

    <div class="d-flex flex-row justify-content-start align-items-center">
        <select id="logLevel" class="selectpicker m-5 p-2">
        </select>

        <button id="update" type="button" class="btn btn-primary">Change logging level</button>
    </div>

</div>

<script type="text/javascript">
    $.ajax({
        url: '/actuator/loggers',
        dataType: 'json',
        success: function (data) {
            refreshLoggersList(data);
            var levels = data.levels;
            var levelSelect = document.getElementById("logLevel");
            for (var i = 0; i < levels.length; i++) {
                var option = document.createElement("option");
                option.text = levels[i];
                option.value = levels[i];
                levelSelect.appendChild(option);
            }
        }
    });
</script>

<script>
    $(document).ready(function () {
        var loggerSelect = document.getElementById("loggerSelector");
        $("#update").on('click', function () {
            var selectedLogger = loggerSelect.value;
            $.ajax({
                url: '/actuator/loggers/' + selectedLogger,
                type: 'post',
                contentType: 'application/json',
                data: getLevel(),
                success: function (data, textStatus, xhr) {
                    $.getJSON('/actuator/loggers', function (data) {
                        refreshLoggersList(data);
                        loggerSelect.value = selectedLogger;
                    });
                }
            })
        });
    });
</script>

<script>
    function getLevel() {
        var level = {
            configuredLevel: document.getElementById("logLevel").value,
        };
        return JSON.stringify(level);
    }
</script>

<script>
    function refreshLoggersList(data) {
        var loggerSelector = document.getElementById("loggerSelector");
        var loggers = Object.entries(data.loggers);
        var loggerNodes = Object.keys(data.loggers);
        // clear old options
        for (i = loggerSelector.options.length - 1; i >= 0; i--) {
            loggerSelector.remove(i);
        }
        for (var i = 0; i < loggers.length; i++) {
            var option = document.createElement("option");
            option.text = JSON.stringify(loggers[i]);
            option.value = loggerNodes[i];
            loggerSelector.appendChild(option);
        }
    }
</script>

</body>
</html>