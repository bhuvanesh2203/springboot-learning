// Shared script for login, register, and todos pages
const SERVER_URL = "http://localhost:8080";
const token = localStorage.getItem("token");

function parseResponse(response) {
    const contentType = response.headers.get("content-type") || "";
    const contentLength = response.headers.get("content-length");

    if (response.status === 204 || contentLength === "0") {
        return Promise.resolve(null);
    }

    if (contentType.includes("application/json")) {
        return response.text().then(text => text ? JSON.parse(text) : null);
    }

    return response.text();
}

function extractErrorMessage(data, fallbackMessage) {
    if (data && typeof data === "object" && data.message) {
        return data.message;
    }

    if (typeof data === "string" && data.trim()) {
        return data;
    }

    return fallbackMessage;
}

// Login page logic
function login() {
    const email=document.getElementById("email").value;
    const password=document.getElementById("password").value;
    fetch(`${SERVER_URL}/auth/login`,{
        method:"POST",
        headers:{"Content-Type":"application/json"},
        body: JSON.stringify({email,password})

}) .then(response =>{
    if(!response.ok){
        return parseResponse(response).then(data => {
            throw new Error(extractErrorMessage(data, "login is failed"));
        });
    }
    return parseResponse(response);
})
.then(data =>{
    const authToken =
        typeof data === "string"
            ? data
            : data.token || data.accessToken || "";

    if (!authToken) {
        throw new Error("Token not found in login response");
    }

    localStorage.setItem("token",authToken);
    window.location.href="todos.html";

})   .catch(error =>{
    alert(error.message);
})

}

// Register page logic
function register() {
    const email=document.getElementById("email").value;
    const password=document.getElementById("password").value;
    fetch(`${SERVER_URL}/auth/register`,{

        method:"POST",
        headers:{"Content-Type":"application/json"},
        body: JSON.stringify({email,password})

    })
    .then(response =>{
        if(response.ok){
            alert("Registration is success , please login");
            window.location.href="login.html";
        }else{
            return parseResponse(response).then(data => {
                throw new Error(extractErrorMessage(data, "Registration is failed"));
            });
        }
    })
    .catch(error =>{
        alert(error.message);
    })

}

// Users page logic
function createUserCard(user) {
    const card = document.createElement("div");
    card.className = "todo-card";

    const details = document.createElement("div");
    details.className = "user-details";

    const name = document.createElement("strong");
    name.textContent = user.name || "Unnamed user";

    const email = document.createElement("span");
    email.textContent = user.email || "No email";

    const description = document.createElement("small");
    description.textContent = user.description || "No description";

    details.appendChild(name);
    details.appendChild(email);
    details.appendChild(description);
    card.appendChild(details);

    if (user.id != null) {
        const deleteButton = document.createElement("button");
        deleteButton.textContent = "Delete";
        deleteButton.onclick = function () {
            deleteUser(user.id);
        };
        card.appendChild(deleteButton);
    }

    return card;
}

function loadUsers() {

  if(!token){
    alert("please login first");
    window.location.href="login.html";
    return;

  }

fetch(`${SERVER_URL}/api/users`,{
        method:"GET",
        headers:{"Content-Type":"application/json",
            Authorization: `Bearer ${token}`,
            
        },
 
}) .then(response =>{
    if(!response.ok){
        return parseResponse(response).then(data => {
            throw new Error(extractErrorMessage(data, "failed to get users"));
        });
    }
    return parseResponse(response);
})
.then((users)=>{
   const userList=document.getElementById("user-list");
   userList.innerHTML="";
   if(!users || users.length === 0){
    userList.innerHTML=`<p id="empty-message">No users found.</p>`;

   }    else{
    users.forEach(user=> {
        userList.appendChild(createUserCard(user));
    });
   }


})
   .catch(error =>{
    alert(error.message);
    document.getElementById("user-list").innerHTML=`<p id="empty-message">Failed to load users.</p>`
})



}

function addUser() {
    const nameInput = document.getElementById("name");
    const emailInput = document.getElementById("user-email");
    const descriptionInput = document.getElementById("description");

    const name = nameInput.value.trim();
    const email = emailInput.value.trim();
    const description = descriptionInput.value.trim();

    fetch(`${SERVER_URL}/api/users/createServicelevelEntry`,{
        method:"POST",
        headers:{"Content-Type":"application/json",
            Authorization: `Bearer ${token}`,
            
        },
        body: JSON.stringify({name, email, description})
 
}) .then(response =>{
    if(!response.ok){
        return parseResponse(response).then(data => {
            throw new Error(extractErrorMessage(data, "failed to add user"));
        });
    }
    return parseResponse(response);
})
.then(()=>{
    nameInput.value="";
    emailInput.value="";
    descriptionInput.value="";
    loadUsers()})
   .catch(error =>{
    alert(error.message);
})

}

function deleteUser(id) {
    fetch(`${SERVER_URL}/api/users/${id}`,{
        method:"DELETE",
        headers:{"Content-Type":"application/json",
            Authorization: `Bearer ${token}`
        }

}) .then(response =>{
    if(!response.ok){
        return parseResponse(response).then(data => {
            throw new Error(extractErrorMessage(data, "failed to delete user"));
        });
    }
    return parseResponse(response);
})
.then(()=>{
    loadUsers();
})
   .catch(error =>{
    alert(error.message);
})
}

// Page-specific initializations
document.addEventListener("DOMContentLoaded", function () {
    if (document.getElementById("user-list")) {
        loadUsers();
    }
});
