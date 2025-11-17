from django.urls import path
from . import views

urlpatterns = [
    # Home
    path('', views.home, name='home'),
    
    # Restaurant URLs
    path('restaurants/', views.restaurant_list, name='restaurant_list'),
    path('restaurants/<int:pk>/', views.restaurant_detail, name='restaurant_detail'),
    path('restaurants/create/', views.restaurant_create, name='restaurant_create'),
    path('restaurants/<int:pk>/update/', views.restaurant_update, name='restaurant_update'),
    path('restaurants/<int:pk>/delete/', views.restaurant_delete, name='restaurant_delete'),
    
    # MenuItem URLs
    path('restaurants/<int:restaurant_pk>/menu/', views.menuitem_list, name='menuitem_list'),
    path('menu-items/<int:pk>/', views.menuitem_detail, name='menuitem_detail'),
    path('restaurants/<int:restaurant_pk>/menu/create/', views.menuitem_create, name='menuitem_create'),
    path('menu-items/<int:pk>/update/', views.menuitem_update, name='menuitem_update'),
    path('menu-items/<int:pk>/delete/', views.menuitem_delete, name='menuitem_delete'),
]
