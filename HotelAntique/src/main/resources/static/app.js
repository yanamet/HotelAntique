let loadUsersBtn = document.getElementById("loadUsers");

loadUsersBtn.addEventListener('click', reloadUsers);

function reloadUsers(event){

    let usersTable = document.getElementById('userTableBody');
    usersTable.innerHTML = '';

    fetch("http://localhost:8080/pages/admin/users")
        .then(response => response.json())
        .then(json => json.forEach(user => {

            let row = document.createElement('tr');

            let idCol = document.createElement('th');
            idCol.textContent = user.id;

            let usernameCol = document.createElement('td')
            usernameCol.textContent = user.username;

            let resultRoles = '';
            let currentRoles = document.createElement('td');
            for (let role in user.roles) {
                resultRoles = resultRoles + role.toString() + " ";
                console.log("i am in");
            }
            currentRoles.textContent = resultRoles;

            let rolesCol = document.createElement('td');
            let changeRolesBtn = document.createElement('button')
            changeRolesBtn.classList.add("btn");
            changeRolesBtn.classList.add("btn-secondary");
            changeRolesBtn.innerText = "Change Roles";

            rolesCol.appendChild(changeRolesBtn);

            row.appendChild(idCol);
            row.appendChild(usernameCol);
            row.appendChild(currentRoles);
            row.appendChild(rolesCol);

            usersTable.appendChild(row);
        }))

}