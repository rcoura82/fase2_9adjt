from django.test import TestCase, Client
from django.contrib.auth.models import User
from django.urls import reverse
from .models import Restaurant, MenuItem


class RestaurantModelTest(TestCase):
    def setUp(self):
        self.user = User.objects.create_user(username='testuser', password='testpass123')
        self.restaurant = Restaurant.objects.create(
            nome='Test Restaurant',
            endereco='Test Address, 123',
            tipo_cozinha='Italiana',
            horario_funcionamento='10h-22h',
            dono=self.user
        )

    def test_restaurant_creation(self):
        """Test restaurant creation"""
        self.assertEqual(self.restaurant.nome, 'Test Restaurant')
        self.assertEqual(self.restaurant.dono, self.user)
        self.assertEqual(str(self.restaurant), 'Test Restaurant')

    def test_restaurant_fields(self):
        """Test restaurant has all required fields"""
        self.assertIsNotNone(self.restaurant.nome)
        self.assertIsNotNone(self.restaurant.endereco)
        self.assertIsNotNone(self.restaurant.tipo_cozinha)
        self.assertIsNotNone(self.restaurant.horario_funcionamento)
        self.assertIsNotNone(self.restaurant.dono)


class MenuItemModelTest(TestCase):
    def setUp(self):
        self.user = User.objects.create_user(username='testuser', password='testpass123')
        self.restaurant = Restaurant.objects.create(
            nome='Test Restaurant',
            endereco='Test Address',
            tipo_cozinha='Italiana',
            horario_funcionamento='10h-22h',
            dono=self.user
        )
        self.menu_item = MenuItem.objects.create(
            restaurante=self.restaurant,
            nome='Pizza Margherita',
            descricao='Traditional Italian pizza',
            preco=25.90,
            disponivel_apenas_restaurante=False,
            foto_caminho='/media/pizzas/margherita.jpg'
        )

    def test_menuitem_creation(self):
        """Test menu item creation"""
        self.assertEqual(self.menu_item.nome, 'Pizza Margherita')
        self.assertEqual(self.menu_item.restaurante, self.restaurant)
        self.assertEqual(self.menu_item.preco, 25.90)

    def test_menuitem_str_method(self):
        """Test menu item string representation"""
        expected = 'Pizza Margherita - Test Restaurant'
        self.assertEqual(str(self.menu_item), expected)


class RestaurantViewsTest(TestCase):
    def setUp(self):
        self.client = Client()
        self.user = User.objects.create_user(username='testuser', password='testpass123')
        self.restaurant = Restaurant.objects.create(
            nome='Test Restaurant',
            endereco='Test Address',
            tipo_cozinha='Italiana',
            horario_funcionamento='10h-22h',
            dono=self.user
        )

    def test_home_view(self):
        """Test home page loads"""
        response = self.client.get(reverse('home'))
        self.assertEqual(response.status_code, 200)
        self.assertContains(response, 'Sistema de Restaurantes')

    def test_restaurant_list_view(self):
        """Test restaurant list view"""
        response = self.client.get(reverse('restaurant_list'))
        self.assertEqual(response.status_code, 200)
        self.assertContains(response, 'Test Restaurant')

    def test_restaurant_detail_view(self):
        """Test restaurant detail view"""
        response = self.client.get(reverse('restaurant_detail', args=[self.restaurant.pk]))
        self.assertEqual(response.status_code, 200)
        self.assertContains(response, 'Test Restaurant')
        self.assertContains(response, 'Italiana')

    def test_restaurant_create_requires_login(self):
        """Test that creating a restaurant requires login"""
        response = self.client.get(reverse('restaurant_create'))
        self.assertEqual(response.status_code, 302)  # Redirect to login

    def test_restaurant_create_authenticated(self):
        """Test restaurant creation when authenticated"""
        self.client.login(username='testuser', password='testpass123')
        response = self.client.get(reverse('restaurant_create'))
        self.assertEqual(response.status_code, 200)


class MenuItemViewsTest(TestCase):
    def setUp(self):
        self.client = Client()
        self.user = User.objects.create_user(username='testuser', password='testpass123')
        self.restaurant = Restaurant.objects.create(
            nome='Test Restaurant',
            endereco='Test Address',
            tipo_cozinha='Italiana',
            horario_funcionamento='10h-22h',
            dono=self.user
        )
        self.menu_item = MenuItem.objects.create(
            restaurante=self.restaurant,
            nome='Pizza',
            descricao='Delicious pizza',
            preco=25.90,
            disponivel_apenas_restaurante=False,
            foto_caminho='/media/pizza.jpg'
        )

    def test_menuitem_detail_view(self):
        """Test menu item detail view"""
        response = self.client.get(reverse('menuitem_detail', args=[self.menu_item.pk]))
        self.assertEqual(response.status_code, 200)
        self.assertContains(response, 'Pizza')
        self.assertContains(response, '25,90')  # Price formatted with Brazilian locale

    def test_menuitem_create_requires_login(self):
        """Test that creating a menu item requires login"""
        response = self.client.get(reverse('menuitem_create', args=[self.restaurant.pk]))
        self.assertEqual(response.status_code, 302)  # Redirect to login

    def test_menuitem_create_authenticated(self):
        """Test menu item creation when authenticated"""
        self.client.login(username='testuser', password='testpass123')
        response = self.client.get(reverse('menuitem_create', args=[self.restaurant.pk]))
        self.assertEqual(response.status_code, 200)
