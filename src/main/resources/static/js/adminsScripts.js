$(document).ready(function () {

    $.ajax({
        url: 'http://localhost:8080/api/auth/get_current_user',
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            let pOfCurrentUserInNavBar = $('<p>').css({
                'margin': '5px',
                'font-size': '21px'
            }).text(data.email + ' with roles: ' + data.roles.join('; '));
            $('#navbarBrandCurrentUser').append(pOfCurrentUserInNavBar);

            /////////////////////////////////////////////////////////////////////////////////

            let rowOfCurrentUser = $('<tr>').append(
                $('<td>').text(data.id),
                $('<td>').text(data.name),
                $('<td>').text(data.gender),
                $('<td>').text(data.age),
                $('<td>').text(data.email),
                $('<td>').text(data.roles.join('; ')),
            );
            $('#tableOfCurrentUser').append(rowOfCurrentUser);

            /////////////////////////////////////////////////////////////////////////////////

            if (!data.roles.includes('ROLE_ADMIN')) {
                $('#v-pills-home-tab').hide();
                $('#v-pills-home').hide();
            }
        },
        error: function (xhr, status, error) {
            console.error('Ошибка при получении данных: ' + error);
        }
    });

    $.ajax({
        url: 'http://localhost:8080/api/admin/get_all_roles',
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            let divOfRolesInEditModal = $('#divOfRolesInEditModal');
            let divOfRolesOfNewUser = $('#divOfRolesOfNewUser');

            for (const role of data) {
                let labelOfRolesInEditModal = $('<label>').addClass('form-check-label d-block my-2');
                let spanOfRolesInEditModal = $('<span>').text(role.roleName);

                let inputOfRolesInEditModal = $('<input>').addClass('form-check-input')
                    .attr({
                        'type': 'checkbox',
                        'id': 'checkboxEditModal' + role.roleName,
                        'value': role.id,
                        'name': 'checkBoxRoleEditModal'
                    });

                labelOfRolesInEditModal.append(inputOfRolesInEditModal)
                    .append(spanOfRolesInEditModal);
                divOfRolesInEditModal.append(labelOfRolesInEditModal);

                /////////////////////////////////////////////////////////////////////////////////

                let labelOfRolesOfNewUser = $('<label>').addClass('form-check-label d-block my-2');
                let spanOfRolesOfNewUser = $('<span>').text(role.roleName);

                let inputOfRolesOfNewUser = $('<input>').addClass('form-check-input')
                    .attr({
                        'type': 'checkbox',
                        'id': 'checkboxNewUser' + role.roleName,
                        'value': role.id,
                        'name': 'checkBoxRoleNewUser'
                    });

                labelOfRolesOfNewUser.append(inputOfRolesOfNewUser)
                    .append(spanOfRolesOfNewUser);
                divOfRolesOfNewUser.append(labelOfRolesOfNewUser);
            }
        },
        error: function (xhr, status, error) {
            console.error('Ошибка при получении данных: ' + error);
        }
    });

    $.ajax({
        url: 'http://localhost:8080/api/admin/get_all_users',
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            for (const user of data) {
                let idOfUser = user.id;
                let buttonShowEditModal = $('<button>').attr({
                    'type': 'button',
                    'class': 'btn btn-outline-warning bi-pencil-square',
                    'data-user-id': idOfUser
                }).on('click', function () {
                    showEditModal(idOfUser);
                })

                let buttonShowDeleteModal = $('<button>').attr({
                    'type': 'button',
                    'class': 'btn btn-outline-danger bi-trash3',
                    'data-user-id': idOfUser
                }).on('click', function () {
                    showDeleteModal(idOfUser)
                })

                let newRowOfUser = $('<tr>').append(
                    $('<td>').text(idOfUser),
                    $('<td>').text(user.name),
                    $('<td>').text(user.gender),
                    $('<td>').text(user.age),
                    $('<td>').text(user.email),
                    $('<td>').text(user.roles.join('; ')),
                    $('<td>').append(buttonShowEditModal),
                    $('<td>').append(buttonShowDeleteModal)
                );
                $('#tableOfUsers').append(newRowOfUser);
            }
        },
        error: function (xhr, status, error) {
            console.error('Ошибка при получении данных: ' + error);
        }
    });
});

function showEditModal(userId) {
    $.ajax({
        url: 'http://localhost:8080/api/admin/edit_user/' + userId,
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            $('#inputIdEditModal').val(userId);
            $('#inputNameEditModal').val(data.name);
            if (data.gender === 'Мужской') {
                $('#radioMaleEditModal').prop('checked', true);
            } else if (data.gender === 'Женский') {
                $('#radioFemaleEditModal').prop('checked', true);
            }
            $('#inputAgeEditModal').val(data.age);
            $('#inputEmailEditModal').val(data.email);

            $('[id^="checkboxEditModal"]').prop('checked', false);
            for (const roleName of data.roles) {
                $('#checkboxEditModal' + roleName).prop('checked', true);
            }
            new bootstrap.Modal(document.getElementById('editModal')).show();
        },
        error: function (xhr, status, error) {
            console.error('Ошибка при получении данных: ' + error);
        }
    });
}

function editUser() {
    let updatedUser = {
        name: $('#inputNameEditModal').val(),
        gender: $('input[name="gender"]:checked').val(),
        age: $('#inputAgeEditModal').val(),
        email: $('#inputEmailEditModal').val(),
        password: $('#inputPasswordEditModal').val()
    }

    let idOfEditUser = $('#inputIdEditModal').val();
    let rolesIds = [];
    let chosenRoles = [];
    $('input[name=checkBoxRoleEditModal]').each(function () {
        if ($(this).prop('checked')) {
            rolesIds.push($(this).val());
            chosenRoles.push($(this).next().text());
        }
    });


    let requestData = {
        updatedUser: updatedUser,
        rolesIds: rolesIds
    }

    $.ajax({
        url: 'http://localhost:8080/api/admin/edit_user/' + idOfEditUser,
        method: 'PATCH',
        contentType: 'application/json',
        data: JSON.stringify(requestData),
        success: function () {
            function updateUserInTable(userSelector, updatedUser, chosenRoles) {
                userSelector.find('td:eq(1)').text(updatedUser.name);
                userSelector.find('td:eq(2)').text(updatedUser.gender);
                userSelector.find('td:eq(3)').text(updatedUser.age);
                userSelector.find('td:eq(4)').text(updatedUser.email);
                userSelector.find('td:eq(5)').text(chosenRoles.join('; '));
            }

            $('#tableOfUsers').find('tr').each(function () {
                if ($(this).find('td:first').text() === idOfEditUser) {
                    updateUserInTable($(this), updatedUser, chosenRoles);
                }
            })
            let $rowOfCurrentUser = $('#tableOfCurrentUser').find('tr:first-child');
            if ($rowOfCurrentUser.find('td:first').text() === idOfEditUser) {
                updateUserInTable($rowOfCurrentUser, updatedUser, chosenRoles);
            }
            $('#editModal').modal('hide');
        },
        error: function (xhr, status, error) {
            console.error('Ошибка при получении данных: ' + error);
            alert('Введены неверные данные!');
        }
    })

}

function showDeleteModal(userId) {
    $.ajax({
        url: 'http://localhost:8080/api/admin/delete_user/' + userId,
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            $('#inputIdDeleteModal').val(userId);
            $('#spanNameDeleteModal').text(data.name);
            $('#spanGenderDeleteModal').text(data.gender);
            $('#spanAgeDeleteModal').text(data.age);
            $('#spanEmailDeleteModal').text(data.email);
            $('#spanRolesDeleteModal').text(data.roles.join('\n'));
            new bootstrap.Modal(document.getElementById('deleteModal')).show();
        },
        error: function (xhr, status, error) {
            console.error('Ошибка при получении данных: ' + error);
        }
    });
}

function deleteUser() {
    let idOfDeleteUser = $('#inputIdDeleteModal').val();
    $.ajax({
        url: 'http://localhost:8080/api/admin/delete_user/' + idOfDeleteUser,
        method: 'DELETE',
        success: function () {
            $('#tableOfUsers').find('tr').each(function () {
                if ($(this).find('td:first').text() === idOfDeleteUser) {
                    $(this).remove();
                    return false;
                }
            })
            $('#deleteModal').modal('hide');
        },
        error: function (xhr, status, error) {
            console.error('Ошибка при получении данных: ' + error);
        }
    })

}

function createUser() {

    let newUser = {
        name: $('#inputNameNewUser').val(),
        gender: $('input[name="gender"]:checked').val(),
        age: $('#inputAgeNewUser').val(),
        email: $('#inputEmailNewUser').val(),
        password: $('#inputPasswordNewUser').val()
    }

    let rolesIdsOfNewUser = [];
    let rolesOfNewUser = [];
    $('input[name="checkBoxRoleNewUser"]').each(function () {
        if ($(this).prop('checked')) {
            rolesIdsOfNewUser.push($(this).val());
            rolesOfNewUser.push($(this).next().text());
        }
    })

    let requestData = {
        newUser: newUser,
        rolesIds: rolesIdsOfNewUser
    }

    $.ajax({
        url: 'http://localhost:8080/api/admin/create_user',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(requestData),
        success: function (data) {
            let idOfNewUser = data;

            let buttonShowEditModal = $('<button>').attr({
                'type': 'button',
                'class': 'btn btn-outline-warning bi-pencil-square',
                'data-user-id': idOfNewUser
            }).on('click', function () {
                showEditModal(idOfNewUser);
            })

            let buttonShowDeleteModal = $('<button>').attr({
                'type': 'button',
                'class': 'btn btn-outline-danger bi-trash3',
                'data-user-id': idOfNewUser
            }).on('click', function () {
                showDeleteModal(idOfNewUser)
            })

            let newRowOfNewUser = $('<tr>').append(
                $('<td>').text(idOfNewUser),
                $('<td>').text(newUser.name),
                $('<td>').text(newUser.gender),
                $('<td>').text(newUser.age),
                $('<td>').text(newUser.email),
                $('<td>').text(rolesOfNewUser.join('; ')),
                $('<td>').append(buttonShowEditModal),
                $('<td>').append(buttonShowDeleteModal)
            );
            $('#tableOfUsers').append(newRowOfNewUser);

            $('#nav-home-tab').addClass('active');
            $('#nav-home').addClass('show active');

            $('#nav-profile-tab').removeClass('active');
            $('#nav-profile').removeClass('show active');

        },
        error: function (xhr, status, error) {
            console.error('Ошибка при получении данных: ' + error);
            alert('Введены неверные данные!');
        }
    })

}
