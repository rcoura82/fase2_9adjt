# Sistema de Gerenciamento de Restaurantes

Sistema web completo para gerenciamento de restaurantes e cardápios desenvolvido em Django.

## Funcionalidades

### CRUD de Restaurantes
- **Criar** novos restaurantes com informações completas
- **Visualizar** lista de todos os restaurantes
- **Editar** informações de restaurantes (apenas o dono)
- **Deletar** restaurantes (apenas o dono)

**Campos do Restaurante:**
- Nome
- Endereço
- Tipo de Cozinha
- Horário de Funcionamento
- Dono do Restaurante (usuário responsável)

### CRUD de Itens do Cardápio
- **Criar** novos itens para o cardápio do restaurante
- **Visualizar** todos os itens do cardápio
- **Editar** itens do cardápio (apenas o dono do restaurante)
- **Deletar** itens do cardápio (apenas o dono do restaurante)

**Campos do Item do Cardápio:**
- Nome
- Descrição
- Preço
- Disponibilidade para pedir apenas no Restaurante
- Foto do prato (caminho do arquivo)
- Restaurante (vínculo com o restaurante)

## Requisitos

- Python 3.8+
- Django 5.2+
- Pillow (para suporte a imagens)

## Instalação

1. Clone o repositório:
```bash
git clone https://github.com/rcoura82/fase2_9adjt.git
cd fase2_9adjt
```

2. Instale as dependências:
```bash
pip install -r requirements.txt
```

3. Execute as migrações:
```bash
python manage.py migrate
```

4. Crie um superusuário:
```bash
python manage.py createsuperuser
```

5. Inicie o servidor de desenvolvimento:
```bash
python manage.py runserver
```

6. Acesse o sistema:
- Frontend: http://127.0.0.1:8000/
- Admin: http://127.0.0.1:8000/admin/

## Uso

1. **Login**: Acesse o sistema usando suas credenciais de usuário
2. **Criar Restaurante**: Clique em "Novo Restaurante" para cadastrar um restaurante
3. **Gerenciar Cardápio**: Após criar um restaurante, adicione itens ao cardápio
4. **Visualizar**: Todos os usuários podem visualizar restaurantes e cardápios
5. **Editar/Deletar**: Apenas o dono do restaurante pode editar ou deletar

## Estrutura do Projeto

```
fase2_9adjt/
├── manage.py
├── requirements.txt
├── restaurant_system/          # Configurações do projeto
│   ├── settings.py
│   ├── urls.py
│   └── ...
└── restaurants/                # App principal
    ├── models.py              # Modelos Restaurant e MenuItem
    ├── views.py               # Views CRUD
    ├── forms.py               # Formulários
    ├── urls.py                # URLs da aplicação
    ├── admin.py               # Configuração do admin
    └── templates/             # Templates HTML
        └── restaurants/
            ├── base.html
            ├── home.html
            ├── restaurant_*.html
            └── menuitem_*.html
```

## Características Técnicas

- **Framework**: Django 5.2
- **Banco de Dados**: SQLite (desenvolvimento)
- **Interface**: Bootstrap 5
- **Autenticação**: Sistema de autenticação do Django
- **Permissões**: Apenas o dono pode editar/deletar seus restaurantes e itens
- **Validação**: Formulários com validação integrada
- **Mensagens**: Sistema de feedback ao usuário
- **Responsivo**: Interface adaptável a diferentes tamanhos de tela

## Licença

Este projeto foi desenvolvido para fins educacionais.

