# 📱 Sistema de Gestão para Loja de Peças de Celular

<div align="center">

![Java](https://img.shields.io/badge/Java-11+-orange?style=for-the-badge&logo=java)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Em%20Produção-green?style=for-the-badge)

**Sistema completo para controle de estoque, vendas e pedidos de peças para celular**

[![GitHub](https://img.shields.io/badge/GitHub-Repositório-black?style=flat-square&logo=github)](https://github.com/Luuh3k/Lista-Automatica)
[![Issues](https://img.shields.io/badge/Contribuições-Bem--vindas-brightgreen?style=flat-square)](https://github.com/Luuh3k/Lista-Automatica/issues)

</div>

## 📋 Índice
- [📖 Sobre o Projeto](#-sobre-o-projeto)
- [✨ Funcionalidades](#-funcionalidades)
- [🚀 Demonstração](#-demonstração)
- [🛠️ Tecnologias](#️-tecnologias)
- [📦 Instalação](#-instalação)
- [🎮 Como Usar](#-como-usar)
- [🏗️ Arquitetura](#️-arquitetura)
- [📊 Exemplos](#-exemplos)
- [🤝 Contribuindo](#-contribuindo)
- [📄 Licença](#-licença)

## 📖 Sobre o Projeto

Sistema desenvolvido em Java para gestão completa de lojas de peças para celular. Controla estoque, vendas, devoluções e gera relatórios automáticos para pedidos com fornecedores.

**🎯 Destaques:**
- ✅ **Período automático 16h-16h** para listas de pedidos
- ✅ **Sistema inteligente** de devoluções
- ✅ **CRUD completo** de peças
- ✅ **Interface console** intuitiva
- ✅ **Relatórios automáticos** por categoria

## ✨ Funcionalidades

### 🏪 Gestão de Estoque
| Funcionalidade | Descrição | Status |
|----------------|-----------|---------|
| **Cadastro de Peças** | Adicionar novas peças ao estoque | ✅ Implementado |
| **Atualização** | Modificar dados e quantidades | ✅ Implementado |
| **Exclusão** | Remover peças (com validações) | ✅ Implementado |
| **Busca Inteligente** | Encontrar peças por nome | ✅ Implementado |
| **Categorias Automáticas** | Organização por tipo | ✅ Implementado |

### 💰 Controle Comercial
| Funcionalidade | Descrição | Status |
|----------------|-----------|---------|
| **Registro de Vendas** | Com verificação de estoque | ✅ Implementado |
| **Sistema de Devoluções** | Estorno automático na lista | ✅ Implementado |
| **Pedidos Pendentes** | Lista de espera quando falta estoque | ✅ Implementado |
| **Histórico Completo** | Registro de todas as movimentações | ✅ Implementado |

### 📈 Relatórios & Analytics
| Funcionalidade | Descrição | Status |
|----------------|-----------|---------|
| **Lista 16h-16h** | Período automático para pedidos | ✅ Implementado |
| **Consolidação por Categoria** | Agrupamento inteligente | ✅ Implementado |
| **Exportação para TXT** | Salvamento em arquivo | ✅ Implementado |
| **Relatório de Devoluções** | Análise específica de estornos | ✅ Implementado |

## 🚀 Demonstração

### 🎮 Menu Principal

```bash
=== SISTEMA LOJA DE PEÇAS PARA CELULAR ===
📱 Baterias • Telas • Tampas 📱

1. 📋 Listar Peças por Categoria
2. ⚙️  Gerenciar Peças (CRUD)
3. 💰 Registrar Saída/Venda
4. 📥 Registrar Entrada/Compra  
5. 🔄 Registrar Devolução
6. 📊 Ver Histórico Completo
7. ⏱️  Últimas Movimentações
8. ✏️  Gerenciar Saídas
9. ⏳ Pedidos Pendentes
10. 📦 Lista Período 16h-16h
11. 🔄 Ver Devoluções do Período
0. 🚪 Sair

📊 Exemplo de Lista de Pedidos

=== 📋 LISTA DE PEDIDOS - PERÍODO 16h-16h ===
🎯 INCLUI APENAS: Baterias • Telas/Frontais • Tampas
Período: 15/12/2024 16:00 até 16/12/2024 16:00

🔋 BATERIAS:
   Bateria iPhone 11: 5 unidades
   Bateria Samsung S20: 3 unidades

📱 TELAS/FRONTAIS:
   Tela iPhone 12: 2 unidades
   Tela Samsung A50: 4 unidades

🎯 TAMPAS:
   Tampa iPhone 13: 1 unidades

=== 📊 RESUMO FINAL ===
Total de unidades: 15
Total de itens diferentes: 5

🛠️ Tecnologias

<div align="center">
Camada	Tecnologias
Linguagem	https://img.shields.io/badge/Java-11+-orange?logo=java
Armazenamento	https://img.shields.io/badge/Collections-Framework-blue
Data/Hora	https://img.shields.io/badge/Date%252FTime-API-green
Arquivos	https://img.shields.io/badge/File-I%252FO-yellow
Arquitetura	https://img.shields.io/badge/POO-Orientada%2520a%2520Objetos-purple
</div>

📦 Instalação

⚙️ Pré-requisitos
Java 11 ou superior

Git

Maven (opcional)


🚀 Execução Rápida

# 1. Clone o repositório
git clone https://github.com/seu-usuario/loja-pecas-celular.git
cd loja-pecas-celular

# 2. Compile o projeto
mvn compile

# 3. Execute a aplicação
mvn exec:java -Dexec.mainClass="com.empresa.pecas.Main"

🖥️ No IntelliJ IDEA
File → Open → Selecione a pasta do projeto

Configure o JDK 11+

Clique direito em Main.java → Run 'Main.main()'

Ou use o atalho: Ctrl+Shift+F10

### 🎮 Como Usar

📋 Fluxo de Trabalho Diário

# 1. 📊 VER ESTOQUE ATUAL
Opção 1 → Lista organizada por categorias

# 2. 💰 REGISTRAR VENDAS  
Opção 3 → Registro com validação de estoque

# 3. 🔄 PROCESSAR DEVOLUÇÕES
Opção 5 → Remove automaticamente da lista

# 4. 📦 GERAR LISTA PARA PEDIDOS
Opção 10 → Lista consolidada 16h-16h


🎯 Estoque Inicial Pré-Cadastrado


Categoria	Peça	Estoque	Localização
🔋 Baterias	iPhone 11	15	Prateleira A1
🔋 Baterias	Samsung S20	12	Prateleira A2
🔋 Baterias	Xiaomi Redmi	8	Prateleira A3
📱 Telas	iPhone 12	8	Prateleira B1
📱 Telas	Samsung A50	10	Prateleira B2
📱 Telas	Motorola G100	6	Prateleira B3
🎯 Tampas	iPhone 13	20	Gaveta C1
🎯 Tampas	Samsung S21	18	Gaveta C2
🎯 Tampas	Xiaomi	25	Gaveta C3


🏗️ Arquitetura

loja-pecas-celular/
├── 📁 src/
│   └── 📁 main/
│       └── 📁 java/
│           └── 📁 com/
│               └── 📁 empresa/
│                   └── 📁 pecas/
│                       ├── 📁 model/
│                       │   ├── 📄 Peca.java
│                       │   ├── 📄 Historico.java
│                       │   └── 📄 PedidoPendente.java
│                       ├── 📁 repository/
│                       │   ├── 📄 PecaRepository.java
│                       │   ├── 📄 HistoricoRepository.java
│                       │   └── 📄 PedidoPendenteRepository.java
│                       ├── 📁 service/
│                       │   └── 📄 PecaService.java
│                       └── 📄 Main.java
└── 📄 README.md


🔧 Padrão Arquitetural MRS

Model: Entidades de dados (Peca, Historico, PedidoPendente)

Repository: Camada de acesso a dados (in-memory)

Service: Lógica de negócio e regras

Main: Interface do usuário e coordenação

📊 Exemplos

💰 Registro de Venda

// Sistema automaticamente:
// 1. ✅ Verifica estoque disponível
// 2. ✅ Atualiza quantidade no estoque  
// 3. ✅ Registra no histórico
// 4. ✅ Cria pedido pendente se necessário

Entrada: Venda de 2 Telas iPhone 12

Saída: 
✅ Estoque atualizado: 8 → 6
✅ Histórico registrado
✅ Lista de pedidos: Tela iPhone 12: 2 unidades

🔄 Processamento de Devolução

// Devolução remove automaticamente da lista
Entrada: Devolução de 1 Tela iPhone 12
Saída:

🔄 Estoque: 6 → 7
🔄 Lista atualizada: Tela iPhone 12: 1 unidade (2 - 1)
🔄 Histórico de devolução registrado


⚠️ Estoque Insuficiente

// Quando não há estoque suficiente
Entrada: Venda de 10 Telas iPhone 12 (estoque: 6)
Saída:

⏳ PEDIDO PENDENTE: Estoque insuficiente
📋 Criado pedido na lista de espera (ID: 1)



🎯 Roadmap de Melhorias
Conexão com banco de dados SQL

Interface gráfica com JavaFX

Relatórios em PDF

Sistema de usuários e permissões

Backup automático dos dados

API REST para integração

📝 Padrões de Código
Siga o estilo de código existente

Adicione comentários Javadoc para novas funcionalidades

Mantenha a arquitetura MRS

Escreva testes unitários para novas features

👨‍💻 Autor
Lucas Costa - lucas.digital18@gmail.com

GitHub: [@seu-usuario](https://github.com/Luuh3k)

LinkedIn: [Seu Perfil](https://www.linkedin.com/in/costalucas96/)


🙏 Agradecimentos
-Inspirado nas necessidades reais de lojas de assistência técnica

