var ajaxUrl = "ajax/profile/meals/";
var mealDataTable;
var isFiltered = false;
var form = $('#detailsForm');

$(document).ready(function(){
    mealDataTable = $('#datatable').DataTable({
            ajax : function (data, callback, settings) {
                if (!isFiltered) {
                    $.get(ajaxUrl, {_ : new Date().getTime()}, function (tableData) {
                        callback({data: tableData});
                    });
                } else {
                    $.get(ajaxUrl.concat('filter'), {
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
                { defaultContent: '',
                    sortable: false,
                    searchable: false,
                    render: renderEditBtn
                },
                { defaultContent: '',
                    sortable: false,
                    searchable: false,
                    render: renderDeleteBtn
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
                searchPlaceholder: i18n['common.search']
        },
        dom: '<"pull-right"f>lipt',
        drawCallback: function (settings) {
        makeEditable();
    }
});
    $('#meals-table_filter').find('input').addClass("form-control");
});

function newMeal() {
    $('#id').val(null);
    $('#dateTime').val(null);
    $('#description').val(null);
    $('#calories').val("");
    $('#editRow').modal();
}


function filter() {
    isFiltered = true;
    updateTable();
}

function updateTable() {
    mealDataTable.ajax.reload();
    mealDataTable.draw();
}

function makeEditable() {
    $('.meal-update').on('click', function () {
        $('#id').val($(this).closest('tr').attr("meal-id"));
        $('#calories').val($(this).parent().prev().text());
        $('#description').val($(this).parent().prev().prev().text());
        $('#dateTime').val($(this).parent().prev().prev().prev().text());
        $('#editRow').modal();
    });

    $('.meal-delete').on('click', function () {
        deleteMealRow($(this).closest('tr').attr("meal-id"));
    });
}

function deleteMealRow(id) {
    $.ajax({
        type: "DELETE",
        url: ajaxUrl + id,
        success: function () {
            updateTable();
        }
    });
}

function renderEditBtn(data, type, row) {
    if (type == 'display') {
        return '<a class="btn btn-warning btn-xs meal-update">' + i18n['common.update'] +'</a>';
    }
}

function renderDeleteBtn(data, type, row) {
    if (type == 'display') {
        return '<a class="btn btn-danger btn-xs meal-delete">' + i18n['common.delete'] +'</a>';
    }
}