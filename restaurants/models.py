from django.db import models
from django.contrib.auth.models import User


class Restaurant(models.Model):
    nome = models.CharField(max_length=200, verbose_name="Nome")
    endereco = models.TextField(verbose_name="Endereço")
    tipo_cozinha = models.CharField(max_length=100, verbose_name="Tipo de Cozinha")
    horario_funcionamento = models.CharField(max_length=200, verbose_name="Horário de Funcionamento")
    dono = models.ForeignKey(User, on_delete=models.CASCADE, related_name='restaurantes', verbose_name="Dono do Restaurante")
    criado_em = models.DateTimeField(auto_now_add=True)
    atualizado_em = models.DateTimeField(auto_now=True)

    class Meta:
        verbose_name = "Restaurante"
        verbose_name_plural = "Restaurantes"
        ordering = ['-criado_em']

    def __str__(self):
        return self.nome


class MenuItem(models.Model):
    restaurante = models.ForeignKey(Restaurant, on_delete=models.CASCADE, related_name='itens_cardapio', verbose_name="Restaurante")
    nome = models.CharField(max_length=200, verbose_name="Nome")
    descricao = models.TextField(verbose_name="Descrição")
    preco = models.DecimalField(max_digits=10, decimal_places=2, verbose_name="Preço")
    disponivel_apenas_restaurante = models.BooleanField(default=False, verbose_name="Disponível apenas no Restaurante")
    foto_caminho = models.CharField(max_length=500, blank=True, verbose_name="Caminho da Foto")
    criado_em = models.DateTimeField(auto_now_add=True)
    atualizado_em = models.DateTimeField(auto_now=True)

    class Meta:
        verbose_name = "Item do Cardápio"
        verbose_name_plural = "Itens do Cardápio"
        ordering = ['-criado_em']

    def __str__(self):
        return f"{self.nome} - {self.restaurante.nome}"
