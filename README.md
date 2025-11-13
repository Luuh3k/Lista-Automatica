# 📱 Sistema de Controle para Assistência Técnica de Celulares

![Java](https://img.shields.io/badge/Java-11+-orange?style=for-the-badge&logo=java)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Em%20Produção-green?style=for-the-badge)

[![GitHub](https://img.shields.io/badge/GitHub-Repositório-black?style=flat-square&logo=github)](https://github.com/seu-usuario/seu-repositorio)
[![Contribuições](https://img.shields.io/badge/Contribuições-Bem--vindas-brightgreen?style=flat-square)](CONTRIBUTING.md)

Sistema completo para controle de estoque, vendas e pedidos de peças para celular.

## 📖 Índice

- [📖 Sobre o Projeto](#-sobre-o-projeto)
- [✈️ Funcionalidades](#️-funcionalidades)
- [🖥️ Demonstração](#️-demonstração)
- [⚙️ Tecnologias](#️-tecnologias)
- [🔗 Instalação](#-instalação)
- [📘 ComoUsar](#-como-usar)
- [🏗️ Arquitetura](#️-arquitetura)
- [📖 Exemplos](#-exemplos)
- [🤝 Contribuindo](#-contribuindo)
- [📄 Licença](#-licença)

## 📖 Sobre o Projeto

Sistema desenvolvido para gerenciar assistências técnicas de celulares, oferecendo controle completo de estoque, vendas e pedidos de peças.

## ✈️ Funcionalidades

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

## 🖥️ Demonstração

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

## ⚙️ Tecnologias

- **Java 11+** - Linguagem principal
- **Spring Boot** - Framework backend
- **MySQL/PostgreSQL** - Banco de dados
- **React/Angular** - Frontend (se aplicável)
- **Maven/Gradle** - Gerenciamento de dependências

## 🔗 Instalação

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/seu-repositorio.git

# Entre no diretório
cd seu-repositorio

# Instale as dependências
mvn install

# Execute a aplicação
mvn spring-boot:run

### 🏗️ Arquitetura

Lista-Automatica/
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


👨‍💻 Autor
Luuh3k - lucas.digital18@gmail.com
GitHub: @Luuh3k
LinkedIn: https://www.linkedin.com/in/costalucas96/
