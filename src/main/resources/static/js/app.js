// API Base URL
const API_BASE = '/api';

// Navigation
document.querySelectorAll('.nav-link').forEach(link => {
    link.addEventListener('click', (e) => {
        e.preventDefault();
        const section = e.target.dataset.section;
        showSection(section);
    });
});

function showSection(sectionId) {
    document.querySelectorAll('.section').forEach(s => s.classList.remove('active'));
    document.querySelectorAll('.nav-link').forEach(l => l.classList.remove('active'));
    
    document.getElementById(sectionId).classList.add('active');
    document.querySelector(`[data-section="${sectionId}"]`)?.classList.add('active');
    
    // Load data when section is shown
    switch(sectionId) {
        case 'home':
            loadStats();
            break;
        case 'users':
            loadUsers();
            break;
        case 'user-types':
            loadUserTypes();
            break;
        case 'restaurants':
            loadRestaurants();
            break;
        case 'menu-items':
            loadMenuItems();
            break;
    }
}

// Toast Notifications
function showToast(message, type = 'success') {
    const toast = document.getElementById('toast');
    toast.textContent = message;
    toast.className = `toast ${type} show`;
    setTimeout(() => toast.classList.remove('show'), 3000);
}

// API Helper Functions
async function apiRequest(url, options = {}) {
    try {
        const response = await fetch(url, {
            ...options,
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            }
        });
        
        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || 'Erro na requisição');
        }
        
        if (response.status === 204) return null;
        return await response.json();
    } catch (error) {
        showToast(error.message, 'error');
        throw error;
    }
}

// Load Statistics for Home
async function loadStats() {
    try {
        const [users, restaurants, menuItems, userTypes] = await Promise.all([
            apiRequest(`${API_BASE}/users`),
            apiRequest(`${API_BASE}/restaurants`),
            apiRequest(`${API_BASE}/menu-items`),
            apiRequest(`${API_BASE}/user-types`)
        ]);
        
        document.getElementById('total-users').textContent = users.length;
        document.getElementById('total-restaurants').textContent = restaurants.length;
        document.getElementById('total-menu-items').textContent = menuItems.length;
        document.getElementById('total-user-types').textContent = userTypes.length;
    } catch (error) {
        console.error('Error loading stats:', error);
    }
}

// ========== USERS ==========

async function loadUsers() {
    try {
        const users = await apiRequest(`${API_BASE}/users`);
        const container = document.getElementById('users-list');
        
        if (users.length === 0) {
            container.innerHTML = '<p class="loading">Nenhum usuário cadastrado.</p>';
            return;
        }
        
        container.innerHTML = users.map(user => `
            <div class="card">
                <h3>👤 ${user.username}</h3>
                <div class="card-info">
                    <p><strong>Email:</strong> ${user.email}</p>
                    <p><strong>ID:</strong> ${user.id}</p>
                </div>
                <div class="card-actions">
                    <button onclick="editUser(${user.id})" class="btn btn-sm btn-primary">Editar</button>
                    <button onclick="deleteUser(${user.id})" class="btn btn-sm btn-danger">Deletar</button>
                </div>
            </div>
        `).join('');
    } catch (error) {
        console.error('Error loading users:', error);
    }
}

function showUserForm() {
    document.getElementById('user-form').style.display = 'block';
    document.getElementById('user-form-title').textContent = 'Novo Usuário';
    document.getElementById('userForm').reset();
    document.getElementById('userId').value = '';
}

function cancelUserForm() {
    document.getElementById('user-form').style.display = 'none';
    document.getElementById('userForm').reset();
}

async function editUser(id) {
    try {
        const user = await apiRequest(`${API_BASE}/users/${id}`);
        document.getElementById('user-form').style.display = 'block';
        document.getElementById('user-form-title').textContent = 'Editar Usuário';
        document.getElementById('userId').value = user.id;
        document.getElementById('username').value = user.username;
        document.getElementById('email').value = user.email;
        document.getElementById('password').value = '';
        document.getElementById('password').removeAttribute('required');
    } catch (error) {
        console.error('Error editing user:', error);
    }
}

async function deleteUser(id) {
    if (!confirm('Tem certeza que deseja deletar este usuário?')) return;
    
    try {
        await apiRequest(`${API_BASE}/users/${id}`, { method: 'DELETE' });
        showToast('Usuário deletado com sucesso!');
        loadUsers();
    } catch (error) {
        console.error('Error deleting user:', error);
    }
}

document.getElementById('userForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const id = document.getElementById('userId').value;
    const data = {
        username: document.getElementById('username').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value
    };
    
    try {
        if (id) {
            await apiRequest(`${API_BASE}/users/${id}`, {
                method: 'PUT',
                body: JSON.stringify(data)
            });
            showToast('Usuário atualizado com sucesso!');
        } else {
            await apiRequest(`${API_BASE}/users`, {
                method: 'POST',
                body: JSON.stringify(data)
            });
            showToast('Usuário criado com sucesso!');
        }
        cancelUserForm();
        loadUsers();
    } catch (error) {
        console.error('Error saving user:', error);
    }
});

// ========== USER TYPES ==========

async function loadUserTypes() {
    try {
        const userTypes = await apiRequest(`${API_BASE}/user-types`);
        const container = document.getElementById('user-types-list');
        
        if (userTypes.length === 0) {
            container.innerHTML = '<p class="loading">Nenhum tipo de usuário cadastrado.</p>';
            return;
        }
        
        container.innerHTML = userTypes.map(type => `
            <div class="card">
                <h3>🏷️ ${type.nome}</h3>
                <div class="card-info">
                    <p><strong>Descrição:</strong> ${type.descricao || 'N/A'}</p>
                    <p><strong>ID:</strong> ${type.id}</p>
                </div>
                <div class="card-actions">
                    <button onclick="editUserType(${type.id})" class="btn btn-sm btn-primary">Editar</button>
                    <button onclick="deleteUserType(${type.id})" class="btn btn-sm btn-danger">Deletar</button>
                </div>
            </div>
        `).join('');
    } catch (error) {
        console.error('Error loading user types:', error);
    }
}

function showUserTypeForm() {
    document.getElementById('user-type-form').style.display = 'block';
    document.getElementById('user-type-form-title').textContent = 'Novo Tipo de Usuário';
    document.getElementById('userTypeForm').reset();
    document.getElementById('userTypeId').value = '';
}

function cancelUserTypeForm() {
    document.getElementById('user-type-form').style.display = 'none';
    document.getElementById('userTypeForm').reset();
}

async function editUserType(id) {
    try {
        const userType = await apiRequest(`${API_BASE}/user-types/${id}`);
        document.getElementById('user-type-form').style.display = 'block';
        document.getElementById('user-type-form-title').textContent = 'Editar Tipo de Usuário';
        document.getElementById('userTypeId').value = userType.id;
        document.getElementById('userTypeName').value = userType.nome;
        document.getElementById('userTypeDescription').value = userType.descricao || '';
    } catch (error) {
        console.error('Error editing user type:', error);
    }
}

async function deleteUserType(id) {
    if (!confirm('Tem certeza que deseja deletar este tipo de usuário?')) return;
    
    try {
        await apiRequest(`${API_BASE}/user-types/${id}`, { method: 'DELETE' });
        showToast('Tipo de usuário deletado com sucesso!');
        loadUserTypes();
    } catch (error) {
        console.error('Error deleting user type:', error);
    }
}

document.getElementById('userTypeForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const id = document.getElementById('userTypeId').value;
    const data = {
        nome: document.getElementById('userTypeName').value,
        descricao: document.getElementById('userTypeDescription').value
    };
    
    try {
        if (id) {
            await apiRequest(`${API_BASE}/user-types/${id}`, {
                method: 'PUT',
                body: JSON.stringify(data)
            });
            showToast('Tipo de usuário atualizado com sucesso!');
        } else {
            await apiRequest(`${API_BASE}/user-types`, {
                method: 'POST',
                body: JSON.stringify(data)
            });
            showToast('Tipo de usuário criado com sucesso!');
        }
        cancelUserTypeForm();
        loadUserTypes();
    } catch (error) {
        console.error('Error saving user type:', error);
    }
});

// ========== RESTAURANTS ==========

async function loadRestaurants() {
    try {
        const restaurants = await apiRequest(`${API_BASE}/restaurants`);
        const container = document.getElementById('restaurants-list');
        
        if (restaurants.length === 0) {
            container.innerHTML = '<p class="loading">Nenhum restaurante cadastrado.</p>';
            return;
        }
        
        container.innerHTML = restaurants.map(restaurant => `
            <div class="card">
                <h3>🍽️ ${restaurant.nome}</h3>
                <div class="card-info">
                    <p><strong>Endereço:</strong> ${restaurant.endereco}</p>
                    <p><strong>Tipo:</strong> ${restaurant.tipoCozinha}</p>
                    <p><strong>Horário:</strong> ${restaurant.horarioFuncionamento}</p>
                    <p><strong>Dono:</strong> ${restaurant.donoNome || `ID: ${restaurant.donoId}`}</p>
                </div>
                <div class="card-actions">
                    <button onclick="editRestaurant(${restaurant.id})" class="btn btn-sm btn-primary">Editar</button>
                    <button onclick="deleteRestaurant(${restaurant.id})" class="btn btn-sm btn-danger">Deletar</button>
                </div>
            </div>
        `).join('');
    } catch (error) {
        console.error('Error loading restaurants:', error);
    }
}

async function loadUsersForSelect() {
    try {
        const users = await apiRequest(`${API_BASE}/users`);
        const select = document.getElementById('restaurantOwner');
        select.innerHTML = '<option value="">Selecione um usuário</option>' +
            users.map(user => `<option value="${user.id}">${user.username}</option>`).join('');
    } catch (error) {
        console.error('Error loading users for select:', error);
    }
}

async function showRestaurantForm() {
    await loadUsersForSelect();
    document.getElementById('restaurant-form').style.display = 'block';
    document.getElementById('restaurant-form-title').textContent = 'Novo Restaurante';
    document.getElementById('restaurantForm').reset();
    document.getElementById('restaurantId').value = '';
}

function cancelRestaurantForm() {
    document.getElementById('restaurant-form').style.display = 'none';
    document.getElementById('restaurantForm').reset();
}

async function editRestaurant(id) {
    try {
        await loadUsersForSelect();
        const restaurant = await apiRequest(`${API_BASE}/restaurants/${id}`);
        document.getElementById('restaurant-form').style.display = 'block';
        document.getElementById('restaurant-form-title').textContent = 'Editar Restaurante';
        document.getElementById('restaurantId').value = restaurant.id;
        document.getElementById('restaurantName').value = restaurant.nome;
        document.getElementById('restaurantAddress').value = restaurant.endereco;
        document.getElementById('restaurantCuisine').value = restaurant.tipoCozinha;
        document.getElementById('restaurantHours').value = restaurant.horarioFuncionamento;
        document.getElementById('restaurantOwner').value = restaurant.donoId;
    } catch (error) {
        console.error('Error editing restaurant:', error);
    }
}

async function deleteRestaurant(id) {
    if (!confirm('Tem certeza que deseja deletar este restaurante?')) return;
    
    try {
        await apiRequest(`${API_BASE}/restaurants/${id}`, { method: 'DELETE' });
        showToast('Restaurante deletado com sucesso!');
        loadRestaurants();
    } catch (error) {
        console.error('Error deleting restaurant:', error);
    }
}

document.getElementById('restaurantForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const id = document.getElementById('restaurantId').value;
    const data = {
        nome: document.getElementById('restaurantName').value,
        endereco: document.getElementById('restaurantAddress').value,
        tipoCozinha: document.getElementById('restaurantCuisine').value,
        horarioFuncionamento: document.getElementById('restaurantHours').value,
        donoId: parseInt(document.getElementById('restaurantOwner').value)
    };
    
    try {
        if (id) {
            await apiRequest(`${API_BASE}/restaurants/${id}`, {
                method: 'PUT',
                body: JSON.stringify(data)
            });
            showToast('Restaurante atualizado com sucesso!');
        } else {
            await apiRequest(`${API_BASE}/restaurants`, {
                method: 'POST',
                body: JSON.stringify(data)
            });
            showToast('Restaurante criado com sucesso!');
        }
        cancelRestaurantForm();
        loadRestaurants();
    } catch (error) {
        console.error('Error saving restaurant:', error);
    }
});

// ========== MENU ITEMS ==========

async function loadMenuItems() {
    try {
        const menuItems = await apiRequest(`${API_BASE}/menu-items`);
        const container = document.getElementById('menu-items-list');
        
        if (menuItems.length === 0) {
            container.innerHTML = '<p class="loading">Nenhum item de cardápio cadastrado.</p>';
            return;
        }
        
        container.innerHTML = menuItems.map(item => `
            <div class="card">
                <h3>🍔 ${item.nome}</h3>
                <div class="card-info">
                    <p><strong>Descrição:</strong> ${item.descricao}</p>
                    <p><strong>Preço:</strong> R$ ${parseFloat(item.preco).toFixed(2)}</p>
                    <p><strong>Restaurante ID:</strong> ${item.restauranteId}</p>
                    ${item.fotoCaminho ? `<p><strong>Foto:</strong> ${item.fotoCaminho}</p>` : ''}
                </div>
                ${item.disponivelApenasRestaurante ? '<span class="badge badge-info">Apenas no Restaurante</span>' : '<span class="badge badge-success">Delivery Disponível</span>'}
                <div class="card-actions">
                    <button onclick="editMenuItem(${item.id})" class="btn btn-sm btn-primary">Editar</button>
                    <button onclick="deleteMenuItem(${item.id})" class="btn btn-sm btn-danger">Deletar</button>
                </div>
            </div>
        `).join('');
    } catch (error) {
        console.error('Error loading menu items:', error);
    }
}

async function loadRestaurantsForSelect() {
    try {
        const restaurants = await apiRequest(`${API_BASE}/restaurants`);
        const select = document.getElementById('menuItemRestaurant');
        select.innerHTML = '<option value="">Selecione um restaurante</option>' +
            restaurants.map(r => `<option value="${r.id}">${r.nome}</option>`).join('');
    } catch (error) {
        console.error('Error loading restaurants for select:', error);
    }
}

async function showMenuItemForm() {
    await loadRestaurantsForSelect();
    document.getElementById('menu-item-form').style.display = 'block';
    document.getElementById('menu-item-form-title').textContent = 'Novo Item do Cardápio';
    document.getElementById('menuItemForm').reset();
    document.getElementById('menuItemId').value = '';
}

function cancelMenuItemForm() {
    document.getElementById('menu-item-form').style.display = 'none';
    document.getElementById('menuItemForm').reset();
}

async function editMenuItem(id) {
    try {
        await loadRestaurantsForSelect();
        const item = await apiRequest(`${API_BASE}/menu-items/${id}`);
        document.getElementById('menu-item-form').style.display = 'block';
        document.getElementById('menu-item-form-title').textContent = 'Editar Item do Cardápio';
        document.getElementById('menuItemId').value = item.id;
        document.getElementById('menuItemRestaurant').value = item.restauranteId;
        document.getElementById('menuItemName').value = item.nome;
        document.getElementById('menuItemDescription').value = item.descricao;
        document.getElementById('menuItemPrice').value = item.preco;
        document.getElementById('menuItemInRestaurantOnly').checked = item.disponivelApenasRestaurante;
        document.getElementById('menuItemPhoto').value = item.fotoCaminho || '';
    } catch (error) {
        console.error('Error editing menu item:', error);
    }
}

async function deleteMenuItem(id) {
    if (!confirm('Tem certeza que deseja deletar este item do cardápio?')) return;
    
    try {
        await apiRequest(`${API_BASE}/menu-items/${id}`, { method: 'DELETE' });
        showToast('Item do cardápio deletado com sucesso!');
        loadMenuItems();
    } catch (error) {
        console.error('Error deleting menu item:', error);
    }
}

document.getElementById('menuItemForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const id = document.getElementById('menuItemId').value;
    const data = {
        restauranteId: parseInt(document.getElementById('menuItemRestaurant').value),
        nome: document.getElementById('menuItemName').value,
        descricao: document.getElementById('menuItemDescription').value,
        preco: parseFloat(document.getElementById('menuItemPrice').value),
        disponivelApenasRestaurante: document.getElementById('menuItemInRestaurantOnly').checked,
        fotoCaminho: document.getElementById('menuItemPhoto').value
    };
    
    try {
        if (id) {
            await apiRequest(`${API_BASE}/menu-items/${id}`, {
                method: 'PUT',
                body: JSON.stringify(data)
            });
            showToast('Item do cardápio atualizado com sucesso!');
        } else {
            await apiRequest(`${API_BASE}/menu-items`, {
                method: 'POST',
                body: JSON.stringify(data)
            });
            showToast('Item do cardápio criado com sucesso!');
        }
        cancelMenuItemForm();
        loadMenuItems();
    } catch (error) {
        console.error('Error saving menu item:', error);
    }
});

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    loadStats();
});
