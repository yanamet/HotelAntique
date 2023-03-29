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
                resultRoles = resultRoles + role + " ";

            }
            currentRoles.textContent = resultRoles;

            let rolesCol = document.createElement('td');
            let changeRolesBtn = document.createElement('a')
            changeRolesBtn.classList.add("btn");
            changeRolesBtn.classList.add("btn-secondary");
            changeRolesBtn.innerText = "Change Roles";
            changeRolesBtn.href = `/roles/change/${user.id}`;

            rolesCol.appendChild(changeRolesBtn);

            row.appendChild(idCol);
            row.appendChild(usernameCol);
            row.appendChild(currentRoles);
            row.appendChild(rolesCol);

            usersTable.appendChild(row);
        }))

}


let  loadReservationsBtn = document.getElementById("loadReservations");

loadReservationsBtn.addEventListener('click', reloadReservations);

function reloadReservations(event){


    let reservationTable = document.getElementById('reservationsTableBody');
    reservationTable.innerHTML = '';


    fetch("http://localhost:8080/pages/admin/reservations")
        .then(response => response.json())
        .then(json => json.forEach(reservation => {
            let row = document.createElement('tr');

            let idCol = document.createElement('th');
            idCol.textContent = reservation.id;

            let usernameCol = document.createElement('td')
            usernameCol.textContent = reservation.guestUsername + ' - ' + reservation.guestFullName;

            let checkInInfoCol = document.createElement('td')
            checkInInfoCol.textContent = reservation.checkInAndCheckOut;

            let isActiveCol = document.createElement('td')
            let activeBool = "active";
            if(reservation.isActive <= 0){
                activeBool = "anulated";
            }
            //ВСЕКИ ПЪТ СА АКТИВ ЗАРАДИ ЗАЯКАТА МИ, ТРЯБВА ДА РЕША ДАЛИ ИСКАМ И АНУЛИРАНИТЕ
            isActiveCol.textContent = activeBool;

            row.appendChild(idCol);
            row.appendChild(usernameCol);
            row.appendChild(checkInInfoCol);
            row.appendChild(isActiveCol);

            reservationTable.appendChild(row);

        }))


}