$(document).ready(function () {
    $('#newUserLink').click(function () {
        clearNewUser();
    });
    $('#create_user').click(function () {
        addUser();
    });
    $('#clear_user').click(function () {
        clearNewUser();
    });
    refreshUsersTable()
});

function clearNewUser() {
    $('#nameN').val("");
    $('#lastNameN').val("");
    $('#loginN').val("");
    $('#passwordN').val("");
    $('#passwordConfirmN').val("");
    $('#roleSetN').val([]);
    $('#roleSetN option:selected').attr('selected', false);
}

function addUser() {
    let roleArray = [];
    $('#roleSetN option:selected').each(function () {
        roleArray.push(JSON.parse($(this).val()));
    });
    let user = {
        "name": $('#nameN').val(),
        "lastName": $('#lastNameN').val(),
        "login": $('#loginN').val(),
        "password": $('#passwordN').val(),
        "passwordConfirm": $('#passwordConfirmN').val(),
        "roleSet": roleArray

    };
    $.ajax({
        url: 'http://localhost:8080/api/admin/save',
        type: 'post',
        data: JSON.stringify(user),
        headers: {
            'x-auth-token': localStorage.accessToken,
            "Content-Type": "application/json"
        },
        dataType: 'json',
        success: function () {
            refreshUsersTable();
            $('#userTablePage').tab('show');
        },
        error: (data) => showError(data)
    })
}

function openDeleteUserModal(userId) {
    $.get(`http://localhost:8080/api/admin/${userId}`)
        .done((user) => {
            $('#idD').val(user.id);
            $('#nameD').val(user.name);
            $('#lastNameD').val(user.lastName);
            $('#loginD').val(user.login);
            $('#roleSetD option:selected').attr('selected', false);
            for (let i = 0; i < user.roleSet.length; i++) {
                $('#roleSetD option:contains("' + user.roleSet[i].role + '")').attr('selected', true);
            }
            $('#modalDelete').modal('show');
            $('#delete_button').off("click");
            $('#delete_button').click(function () {
                deleteUser(userId);
            })
        });
}

function openEditUserModal(userId) {
    console.log("openEditUserModal " + userId);
    $.ajax({
        url: `http://localhost:8080/api/admin/${userId}`,
        type: 'get',
        headers: {
            'x-auth-token': localStorage.accessToken,
        },
        success: (user) => {
            sendEditRequest(user);
        },
        error: (data) => showError(data)
    })
}

function sendEditRequest(user) {
    const userId = user.id;
    console.log("sendEditRequest " + userId);
    $('#idE').val(user.id);
    $('#nameE').val(user.name);
    $('#lastNameE').val(user.lastName);
    $('#loginE').val(user.login);
    $('#roleSetE option:selected').attr('selected', false);
    for (let i = 0; i < user.roleSet.length; i++) {
        $('#roleSetE option:contains("' + user.roleSet[i].role + '")').attr('selected', true);
    }
    $('#modalEdit').modal('show');
    $('#edit_button').off("click");
    $('#edit_button').click(() => {
        console.log("edit_button click " + userId);
        let roleArray = [];
        $('#roleSetE option:selected').each(function () {
            roleArray.push(JSON.parse($(this).val()));
        });
        let user = {
            "id": $('#idE').val(),
            "name": $('#nameE').val(),
            "lastName": $('#lastNameE').val(),
            "login": $('#loginE').val(),
            "roleSet": roleArray
        };
        $.ajax({
            url: `http://localhost:8080/api/admin/${userId}`,
            type: 'post',
            data: JSON.stringify(user),
            headers: {
                'x-auth-token': localStorage.accessToken,
                "Content-Type": "application/json"
            },
            dataType: 'json',
            success: refreshUsersTable,
            error: (data) => showError(data)
        })
    });
}

function refreshUsersTable() {
    $.get(`http://localhost:8080/api/users/me`)
        .done(() => {
            fillInUsersTable(true);
        })
}

function fillInUsersTable(isAdmin) {
    $.get('http://localhost:8080/api/users?t=' + (((new Date()).getTime() * 10000) + 621355968000000000))
        .done((userList) => {
            $('#modalEdit').modal('hide');
            $('#modalDelete').modal('hide');
            $(document).ready(() => $("#userTablePage").click());
            $(document).ready(() => $("#usersTableBody").empty());
            $(document).ready(() => {
                for (let i = 0; i < userList.length; i++) {
                    const user = userList[i];
                    if (isAdmin === true) {
                        $("#usersTableBody")
                            .append($('<tr>')
                                .append($('<td>').text(user.id))
                                .append($('<td>').text(user.name))
                                .append($('<td>').text(user.lastName))
                                .append($('<td>').text(user.login))
                                .append($('<td>').text(user.roleSet))
                                .append($('<td>').html("<input onclick=\"openEditUserModal(" + user.id + ")\" type=\"button\" class=\"btn btn-primary\"\n" +
                                    "                                           value=\"Edit\">"))
                                .append($('<td>').html("<input onclick=\"openDeleteUserModal(" + user.id + ")\" type=\"button\" class=\"btn btn-danger\"\n" +
                                    "                                           value=\"Delete\">"))
                            );
                    } else {
                        $("#usersTableBody")
                            .append($('<tr>')
                                .append($('<td>').text(user.id))
                                .append($('<td>').text(user.name))
                                .append($('<td>').text(user.lastName))
                                .append($('<td>').text(user.login))
                                .append($('<td>').text(user.roleSet))
                            );
                    }
                }
            });
        });
}


function deleteUser(userId) {
    $.ajax({
        url: 'http://localhost:8080/api/admin/remove?id=' + userId,
        type: 'delete',
        headers: {
            'x-auth-token': localStorage.accessToken
        },
        success: refreshUsersTable,
        error: (data) => showError(data)
    })
}

function showError(message) {
    alert(message.responseText);
}