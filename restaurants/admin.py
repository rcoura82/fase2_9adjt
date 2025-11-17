from django.contrib import admin
from .models import Restaurant, MenuItem


@admin.register(Restaurant)
class RestaurantAdmin(admin.ModelAdmin):
    list_display = ['nome', 'tipo_cozinha', 'dono', 'horario_funcionamento', 'criado_em']
    list_filter = ['tipo_cozinha', 'criado_em']
    search_fields = ['nome', 'endereco', 'tipo_cozinha']
    ordering = ['-criado_em']


@admin.register(MenuItem)
class MenuItemAdmin(admin.ModelAdmin):
    list_display = ['nome', 'restaurante', 'preco', 'disponivel_apenas_restaurante', 'criado_em']
    list_filter = ['restaurante', 'disponivel_apenas_restaurante', 'criado_em']
    search_fields = ['nome', 'descricao']
    ordering = ['-criado_em']
