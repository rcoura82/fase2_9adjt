from django import forms
from .models import Restaurant, MenuItem


class RestaurantForm(forms.ModelForm):
    class Meta:
        model = Restaurant
        fields = ['nome', 'endereco', 'tipo_cozinha', 'horario_funcionamento']
        widgets = {
            'nome': forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Nome do restaurante'}),
            'endereco': forms.Textarea(attrs={'class': 'form-control', 'rows': 3, 'placeholder': 'Endereço completo'}),
            'tipo_cozinha': forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Ex: Italiana, Japonesa, Brasileira'}),
            'horario_funcionamento': forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Ex: Seg-Sex 10h-22h, Sáb-Dom 11h-23h'}),
        }


class MenuItemForm(forms.ModelForm):
    class Meta:
        model = MenuItem
        fields = ['nome', 'descricao', 'preco', 'disponivel_apenas_restaurante', 'foto_caminho']
        widgets = {
            'nome': forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Nome do prato'}),
            'descricao': forms.Textarea(attrs={'class': 'form-control', 'rows': 3, 'placeholder': 'Descrição do prato'}),
            'preco': forms.NumberInput(attrs={'class': 'form-control', 'step': '0.01', 'placeholder': '0.00'}),
            'disponivel_apenas_restaurante': forms.CheckboxInput(attrs={'class': 'form-check-input'}),
            'foto_caminho': forms.TextInput(attrs={'class': 'form-control', 'placeholder': '/media/fotos/prato.jpg'}),
        }
