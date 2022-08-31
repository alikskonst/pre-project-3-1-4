$(async function () {
    await allUsersTable();
    await addNewUserForm();
    await aboutMeTable();
    await getDefaultModal();
    await addNewUser();
})

const roleJson = [];

fetch('/api/users/roles').then(function (response) {
    if (response.ok) {
        response.json().then(roleSet => {
            roleSet.forEach(role => {
                roleJson.push(role);
            });
        });
    } else {
        console.error('Network request for roles.json failed with response ' + response.status + ': ' + response.statusText);
    }
})


const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },

    findMe: async () => await fetch('api/users/me'),
    findOneUser: async (id) => await fetch('api/users/one?id=' + id),
    findAllUsers: async () => await fetch('api/users/all'),
    saveUser: async (user) => await fetch('api/users/save', {
        method: 'POST',
        headers: userFetchService.head,
        body: JSON.stringify(user)
    }),
    removeUser: async (id) => await fetch('api/users?id=' + id, {method: 'DELETE', headers: userFetchService.head})
}

async function aboutMeTable() {
    const table = $('#aboutMeTable tbody');
    table.empty();

    await userFetchService.findMe()
        .then(res => res.json())
        .then(user => {
            const tableFilling = `$(
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.login}</td>
                            <td>${user.name}</td>
                            <td>${user.lastName}</td>
                            <td>${user.middleName}</td>
                            <td>${user.roles.sort(role => role.name).map(role => {
                return role.name.toLowerCase();
            })}</td>
                        </tr>
                )`;
            table.append(tableFilling);
        })
}

async function allUsersTable() {
    const table = $('#usersTable tbody');
    table.empty();

    await userFetchService.findAllUsers()
        .then(res => res.json())
        .then(userList => {
            userList.forEach(user => {
                const tableFilling = `$(
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.login}</td>
                            <td>${user.name}</td>
                            <td>${user.lastName}</td>
                            <td>${user.middleName}</td>
                            <td>${user.roles.sort(role => role.name).map(role => {
                    return role.name.toLowerCase();
                })}</td>
                            <td>
                                <button type="button" data-userId="${user.id}" data-action="edit" class="btn btn-info" 
                                data-toggle="modal" data-target="#modalFrame">Edit</button>
                            </td>
                            <td>
                                <button type="button" data-userId="${user.id}" data-action="delete" class="btn btn-danger" 
                                data-toggle="modal" data-target="#modalFrame">Delete</button>
                            </td>
                        </tr>
                )`;
                table.append(tableFilling);
            })
        })

    $("#usersTable").find('button').on('click', (event) => {
        const modalFrame = $('#modalFrame');
        const targetButton = $(event.target);
        const buttonUserId = targetButton.attr('data-userId');
        const buttonAction = targetButton.attr('data-action');
        modalFrame.attr('data-userId', buttonUserId);
        modalFrame.attr('data-action', buttonAction);
        modalFrame.modal('show');
    })
}

async function addNewUser() {
    $('#addUserButton').on('click', async () => {
        const addUserForm = $('#addUserForm')
        const login = addUserForm.find('#userLogin').val().trim();
        const name = addUserForm.find('#userName').val().trim();
        const lastName = addUserForm.find('#userLastName').val().trim();
        const middleName = addUserForm.find('#userMiddleName').val().trim();
        const password = addUserForm.find('#userPassword').val().trim();
        const rolesArray = addUserForm.find('#userRoles').val();
        let roles = [];
        for (let i = 0; i < rolesArray.length; i++) {
            roles.push(roleJson.filter(item => item.id === Number(rolesArray[i])))
        }
        roles = [].concat.apply([], roles);

        const user = {
            login: login,
            password: password,
            name: name,
            lastName: lastName,
            middleName: middleName,
            roles: roles
        }

        const response = await userFetchService.saveUser(user);
        if (response.ok) {
            await allUsersTable();
            addUserForm.find('#userLogin').val('');
            addUserForm.find('#userPassword').val('');
            addUserForm.find('#userName').val('');
            addUserForm.find('#userLastName').val('');
            addUserForm.find('#userMiddleName').val('');
        } else {
            const body = await response.json();
            const alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="messageError">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            addUserForm.prepend(alert)
        }
    })
}

async function addNewUserForm() {
    const button = $(`#SliderNewUserForm`);
    const form = $(`#addUserForm`)
    button.on('click', () => {
        if (form.attr("data-hidden") === "true") {
            form.attr('data-hidden', 'false');
            form.show();
            button.text('Hide panel');
        } else {
            form.attr('data-hidden', 'true');
            form.hide();
            button.text('Show panel');
        }
    });

    fetch('/api/users/roles').then(function (response) {
        if (response.ok) {
            form.find('#userRoles').empty();
            response.json().then(roleSet => {
                roleSet
                    .sort(role => role.name)
                    .forEach(role => {
                        form.find('#userRoles').append($('<option>').val(role.id).text(role.name));
                    });
            });
        } else {
            console.error('Network request for roles.json failed with response ' + response.status + ': ' + response.statusText);
        }
    });
}

async function getDefaultModal() {
    $('#modalFrame').modal({
        keyboard: true,
        backdrop: "static",
        show: false
    }).on("show.bs.modal", (event) => {
        const thisModal = $(event.target);
        const userId = thisModal.attr('data-userId');
        const action = thisModal.attr('data-action');
        switch (action) {
            case 'edit':
                editUser(thisModal, userId);
                break;
            case 'delete':
                deleteUser(thisModal, userId);
                break;
        }
    }).on("hidden.bs.modal", (e) => {
        const thisModal = $(e.target);
        thisModal.find('.modal-title').html('');
        thisModal.find('.modal-body').html('');
        thisModal.find('.modal-footer').html('');
    })
}

async function editUser(modal, id) {
    const response = await userFetchService.findOneUser(id);
    const user = response.json();
    const modalForm = $(`#modalFrame`)
    modal.find('.modal-title').html('Edit user');
    const editButton = `<button  class="btn btn-outline-success" id="editButton">Edit</button>`;
    const closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`
    modal.find('.modal-footer').append(editButton);
    modal.find('.modal-footer').append(closeButton);
    user.then(user => {
        const bodyForm = `
            <form class="form-group" id="editUser">
                <input type="text" class="form-control" id="id" name="id" value="${user.id}" disabled>
                <label th:for="login" class="col-form-label">Login</label>
                <input class="form-control" type="text" id="login" value="${user.login}">
                <label th:for="password" class="col-form-label">Password</label>
                <input class="form-control" type="password" id="password" value="${user.password}">
                <label th:for="name" class="col-form-label">Name</label>
                <input class="form-control" type="text" id="name" value="${user.name}">
                <label th:for="lastName" class="col-form-label">Last name</label>
                <input class="form-control" type="text" id="lastName" value="${user.lastName}">
                <label th:for="middleName" class="col-form-label">Middle name</label>
                <input class="form-control" id="middleName" type="text" value="${user.middleName}">
                <label th:for="rolesSelected" class="col-form-label">Roles</label>
                <select class="form-select w-100" name="rolesSelected" id="updatedRoles" multiple></select>
            </form>
        `;
        modal.find('.modal-body').append(bodyForm);
    });

    fetch('/api/users/roles').then(function (response) {
        if (response.ok) {
            modalForm.find('#updatedRoles').empty();
            response.json().then(roleSet => {
                roleSet
                    .sort(role => role.name)
                    .forEach(role => {
                        modalForm.find('#updatedRoles').append($('<option>').val(role.id).text(role.name));
                    });
            });
        } else {
            console.error('Network request for roles.json failed with response ' + response.status + ': ' + response.statusText);
        }
    });

    $("#editButton").on('click', async () => {
        const id = modal.find("#id").val().trim();
        const login = modal.find("#login").val().trim();
        const password = modal.find("#password").val().trim();
        const name = modal.find('#name').val().trim();
        const lastName = modal.find('#lastName').val().trim();
        const middleName = modal.find("#middleName").val().trim();
        const rolesArray = modal.find('#updatedRoles').val();
        let roles = [];
        for (let i = 0; i < rolesArray.length; i++) {
            roles.push(roleJson.filter(item => item.id === Number(rolesArray[i])))
        }
        roles = [].concat.apply([], roles);

        const data = {
            id: id,
            login: login,
            password: password,
            name: name,
            lastName: lastName,
            middleName: middleName,
            roles: roles
        }
        const response = await userFetchService.saveUser(data, id);

        if (response.ok) {
            await allUsersTable();
            modal.modal('hide');
        } else {
            const body = await response.json();
            const alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="messageError">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            modal.find('.modal-body').prepend(alert);
        }
    })
}

async function deleteUser(modal, id) {
    const response = await userFetchService.findOneUser(id);
    const user = response.json();
    const modalForm = $(`#modalFrame`)
    modal.find('.modal-title').html('Delete user');
    const deleteButton = `<button  class="btn btn-danger" id="deleteButton">Delete</button>`;
    const closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`
    modal.find('.modal-footer').append(deleteButton);
    modal.find('.modal-footer').append(closeButton);

    user.then(user => {
        const bodyForm = `
            <form class="form-group" id="deleteUser">
                <input type="text" class="form-control" id="id" name="id" value="${user.id}" disabled>
                <label th:for="login" class="col-form-label">Login</label>
                <input readonly class="form-control" type="text" id="login" value="${user.login}">
                <label th:for="password" class="col-form-label">Password</label>
                <input readonly class="form-control" type="password" id="password" value="${user.password}">
                <label th:for="name" class="col-form-label">Name</label>
                <input readonly class="form-control" type="name" id="name" value="${user.name}">
                <label th:for="lastName" class="col-form-label">Last name</label>
                <input readonly class="form-control" type="lastName" id="lastName" value="${user.lastName}">
                <label th:for="middleName" class="col-form-label">Middle name</label>
                <input readonly class="form-control" id="middleName" type="text" value="${user.middleName}">
                <label th:for="rolesSelected" class="col-form-label">Roles</label>
                <select class="form-select w-100" name="rolesSelected" id="deletedRoles" multiple disabled="true"></select>
            </form>
        `;
        modal.find('.modal-body').append(bodyForm);
    });

    fetch('/api/users/roles').then(function (response) {
        if (response.ok) {
            modalForm.find('#deletedRoles').empty();
            response.json().then(roleSet => {
                roleSet
                    .sort(role => role.name)
                    .forEach(role => {
                        modalForm.find('#deletedRoles').append($('<option>').val(role.id).text(role.name));
                    });
            });
        } else {
            console.error('Network request for roles.json failed with response ' + response.status + ': ' + response.statusText);
        }
    });

    $("#deleteButton").on('click', async () => {
        const response = await userFetchService.removeUser(id);
        if (response.ok) {
            await allUsersTable();
            modal.modal('hide');
        } else {
            const body = await response.json();
            const alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="messageError">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            modal.find('.modal-body').prepend(alert);
        }
    })
}

