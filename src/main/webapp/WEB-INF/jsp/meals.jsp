<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <h3><fmt:message key="meals.title"/></h3>
    </div>
    <div class="container">
        <form class="form-horizontal">
            <div class="form-group-sm">
                <div class="row">
                    <div class="col-lg-3 col-md-3 col-sm-4">
                        <label class="control-label" for="startDate"><fmt:message key="meals.startDate"/></label>
                        <input class="form-control" type="date" id="startDate">
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-4">
                        <label class="control-label" for="endDate"><fmt:message key="meals.endDate"/></label>
                        <input class="form-control" type="date" id="endDate">
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-3 col-md-3 col-sm-4">
                        <label class="control-label" for="startTime"><fmt:message key="meals.startTime"/></label>
                        <input class="form-control" type="time" id="startTime">
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-4">
                        <label class="control-label" for="endTime"><fmt:message key="meals.endTime"/></label>
                        <input class="form-control" type="time" id="endTime">
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-lg-offset-3 col-md-offset-3 col-sm-offset-4 col-lg-3 col-md-3 col-sm-4">
                        <button class="btn btn-default pull-right filter" type="button"><fmt:message key="meals.filter"/></button>
                    </div>
                </div>
            </div>
        </form>
        <button type="button" class="btn btn-sm btn-info new-meal"><fmt:message key="meals.add"/></button>
    </div>
</div>
<div class="container">

    <table class="table table-striped" id="meals-table">
        <thead>
        <tr>
            <th class="col-lg-3 col-md-3 col-sm-2"><fmt:message key="meals.dateTime"/></th>
            <th class="col-lg-2 col-md-2 col-sm-2"><fmt:message key="meals.description"/></th>
            <th class="col-lg-2 col-md-2 col-sm-2"><fmt:message key="meals.calories"/></th>
            <th class="col-lg-3 col-md-3 col-sm-3"></th>
        </tr>
        </thead>
        <tbody>
            <tr><td>No data yet</td></tr>
        </tbody>
    </table>
</div>

<!-- Modal -->
<div id="editor-modal" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title"><fmt:message key="meals.add"/></h3>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="mealForm">
                    <input type="text" hidden="hidden" id="meal-id" name="id" value="">

                    <div class="form-group">
                        <label for="dateTime" class="control-label col-xs-3"><fmt:message key="meals.dateTime"/></label>

                        <div class="col-xs-9">
                            <input type="datetime-local" class="form-control" id="dateTime" name="dateTime">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3"><fmt:message key="meals.description"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="description" name="description">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="calories" class="control-label col-xs-3"><fmt:message key="meals.calories"/></label>

                        <div class="col-xs-9">
                            <input type="number" class="form-control" id="calories" name="calories">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary"><fmt:message key="common.save"/></button>
                            <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="common.cancel"/></button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
<script type="text/javascript" src="webjars/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
    var ajax_url = "ajax/meals/";
    var mealDataTable;
    var isFiltered = false;

    $(document).ready(function(){
        mealDataTable = $('#meals-table').DataTable({
            ajax : function (data, callback, settings) {
                if (!isFiltered) {
                    $.get(ajax_url, function (tableData) {
                        callback({data: tableData});
                    });
                } else {
                    $.get(ajax_url.concat('filter'), {
                        startDate: $('#startDate').val(),
                        endDate: $('#endDate').val(),
                        startTime: $('#startTime').val(),
                        endTime: $('#endTime').val()
                    }, function (tableData) {
                        callback({data: tableData});
                    });
                }
            },
            info: false,
            paging: false,
            columns: [
                { data: "dateTime" },
                { data: "description" },
                { data: "calories" },
                { defaultContent: '<button class="btn btn-warning btn-xs meal-update"><fmt:message key="common.update"/></button> <button class="btn btn-danger btn-xs meal-delete"><fmt:message key="common.delete"/></button>',
                    sortable: false,
                    searchable: false
                }
            ],
            order: [0, 'desc'],
            createdRow: function (row, data, index) {
                if (data.exceed == true) {
                    $(row).addClass("exceeded");
                } else {
                    $(row).addClass("normal");
                }
                $(row).attr("meal-id", data.id);
            },
            language: {
                search: "_INPUT_",
                searchPlaceholder: "<fmt:message key="common.search"/>"
            },
            dom: '<"pull-right"f>lipt',
            drawCallback: function (settings) {
                makeEditable();
            }
        });
        $('#meals-table_filter').find('input').addClass("form-control");
    });

    $('.new-meal').on('click', function () {
        $('#meal-id').val(null);
        $('#dateTime').val(null);
        $('#description').val(null);
        $('#calories').val("");
        $('#editor-modal').modal();
    });

    $('#mealForm').on('submit', function (event) {
        var meal = {
            id : $('#meal-id').val(),
            dateTime : $('#dateTime').val(),
            description : $('#description').val(),
            calories : $('#calories').val()
        };
        var method = meal.id === "" ? "POST" : "PUT";
        var currentURL = method === "PUT" ? ajax_url + meal.id : ajax_url;
        $.ajax({
            type: method,
            contentType: "application/json",
            url: currentURL,
            data: JSON.stringify(meal),
            success: function () {
                $('#editor-modal').modal('hide');
                updateTable();
            }
        });
        return false;
    });

    $('.filter').on('click', function (event) {
        isFiltered = true;
        updateTable();
    });

    function updateTable() {
        mealDataTable.ajax.reload();
        mealDataTable.draw();
    }

    function makeEditable() {
        $('.meal-update').on('click', function () {
            $('#meal-id').val($(this).closest('tr').attr("meal-id"));
            $('#calories').val($(this).parent().prev().text());
            $('#description').val($(this).parent().prev().prev().text());
            $('#dateTime').val($(this).parent().prev().prev().prev().text());
            $('#editor-modal').modal();
        });

        $('.meal-delete').on('click', function () {
            deleteMealRow($(this).closest('tr').attr("meal-id"));
        });
    }

    function deleteMealRow(id) {
        $.ajax({
            type: "DELETE",
            url: ajax_url + id,
            success: function () {
                updateTable();
            }
        });
    }
    </script>
</html>
