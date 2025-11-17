from django.shortcuts import render, redirect, get_object_or_404
from django.contrib.auth.decorators import login_required
from django.contrib import messages
from .models import Restaurant, MenuItem
from .forms import RestaurantForm, MenuItemForm


def home(request):
    """Home page view"""
    return render(request, 'restaurants/home.html')


# Restaurant CRUD Views
def restaurant_list(request):
    """List all restaurants"""
    restaurants = Restaurant.objects.all()
    return render(request, 'restaurants/restaurant_list.html', {'restaurants': restaurants})


def restaurant_detail(request, pk):
    """View restaurant details"""
    restaurant = get_object_or_404(Restaurant, pk=pk)
    menu_items = restaurant.itens_cardapio.all()
    return render(request, 'restaurants/restaurant_detail.html', {
        'restaurant': restaurant,
        'menu_items': menu_items
    })


@login_required
def restaurant_create(request):
    """Create a new restaurant"""
    if request.method == 'POST':
        form = RestaurantForm(request.POST)
        if form.is_valid():
            restaurant = form.save(commit=False)
            restaurant.dono = request.user
            restaurant.save()
            messages.success(request, 'Restaurante criado com sucesso!')
            return redirect('restaurant_detail', pk=restaurant.pk)
    else:
        form = RestaurantForm()
    return render(request, 'restaurants/restaurant_form.html', {'form': form, 'action': 'Criar'})


@login_required
def restaurant_update(request, pk):
    """Update an existing restaurant"""
    restaurant = get_object_or_404(Restaurant, pk=pk)
    if restaurant.dono != request.user:
        messages.error(request, 'Você não tem permissão para editar este restaurante.')
        return redirect('restaurant_detail', pk=pk)
    
    if request.method == 'POST':
        form = RestaurantForm(request.POST, instance=restaurant)
        if form.is_valid():
            form.save()
            messages.success(request, 'Restaurante atualizado com sucesso!')
            return redirect('restaurant_detail', pk=restaurant.pk)
    else:
        form = RestaurantForm(instance=restaurant)
    return render(request, 'restaurants/restaurant_form.html', {'form': form, 'action': 'Atualizar'})


@login_required
def restaurant_delete(request, pk):
    """Delete a restaurant"""
    restaurant = get_object_or_404(Restaurant, pk=pk)
    if restaurant.dono != request.user:
        messages.error(request, 'Você não tem permissão para deletar este restaurante.')
        return redirect('restaurant_detail', pk=pk)
    
    if request.method == 'POST':
        restaurant.delete()
        messages.success(request, 'Restaurante deletado com sucesso!')
        return redirect('restaurant_list')
    return render(request, 'restaurants/restaurant_confirm_delete.html', {'restaurant': restaurant})


# MenuItem CRUD Views
def menuitem_list(request, restaurant_pk):
    """List all menu items for a restaurant"""
    restaurant = get_object_or_404(Restaurant, pk=restaurant_pk)
    menu_items = restaurant.itens_cardapio.all()
    return render(request, 'restaurants/menuitem_list.html', {
        'restaurant': restaurant,
        'menu_items': menu_items
    })


def menuitem_detail(request, pk):
    """View menu item details"""
    menu_item = get_object_or_404(MenuItem, pk=pk)
    return render(request, 'restaurants/menuitem_detail.html', {'menu_item': menu_item})


@login_required
def menuitem_create(request, restaurant_pk):
    """Create a new menu item"""
    restaurant = get_object_or_404(Restaurant, pk=restaurant_pk)
    if restaurant.dono != request.user:
        messages.error(request, 'Você não tem permissão para adicionar itens a este restaurante.')
        return redirect('restaurant_detail', pk=restaurant_pk)
    
    if request.method == 'POST':
        form = MenuItemForm(request.POST)
        if form.is_valid():
            menu_item = form.save(commit=False)
            menu_item.restaurante = restaurant
            menu_item.save()
            messages.success(request, 'Item do cardápio criado com sucesso!')
            return redirect('menuitem_detail', pk=menu_item.pk)
    else:
        form = MenuItemForm()
    return render(request, 'restaurants/menuitem_form.html', {
        'form': form,
        'restaurant': restaurant,
        'action': 'Criar'
    })


@login_required
def menuitem_update(request, pk):
    """Update an existing menu item"""
    menu_item = get_object_or_404(MenuItem, pk=pk)
    if menu_item.restaurante.dono != request.user:
        messages.error(request, 'Você não tem permissão para editar este item.')
        return redirect('menuitem_detail', pk=pk)
    
    if request.method == 'POST':
        form = MenuItemForm(request.POST, instance=menu_item)
        if form.is_valid():
            form.save()
            messages.success(request, 'Item do cardápio atualizado com sucesso!')
            return redirect('menuitem_detail', pk=menu_item.pk)
    else:
        form = MenuItemForm(instance=menu_item)
    return render(request, 'restaurants/menuitem_form.html', {
        'form': form,
        'restaurant': menu_item.restaurante,
        'action': 'Atualizar'
    })


@login_required
def menuitem_delete(request, pk):
    """Delete a menu item"""
    menu_item = get_object_or_404(MenuItem, pk=pk)
    restaurant = menu_item.restaurante
    if restaurant.dono != request.user:
        messages.error(request, 'Você não tem permissão para deletar este item.')
        return redirect('menuitem_detail', pk=pk)
    
    if request.method == 'POST':
        menu_item.delete()
        messages.success(request, 'Item do cardápio deletado com sucesso!')
        return redirect('restaurant_detail', pk=restaurant.pk)
    return render(request, 'restaurants/menuitem_confirm_delete.html', {'menu_item': menu_item})
