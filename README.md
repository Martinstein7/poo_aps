# Sistema de Monitoramento Hídrico

Sistema desktop desenvolvido em Java para o monitoramento e gerenciamento de consumo hídrico residencial. A aplicação permite o cadastro de usuários, registro de consumo diário, definição de metas de economia e exibição de métricas detalhadas com interface gráfica moderna.

## Tecnologias Utilizadas

* **Linguagem:** Java (JDK 25)
* **Interface Gráfica:** JavaFX (SDK 26.0.1)
* **Visual Estético:** FlatLaf (Look and Feel para Java)
* **Banco de Dados:** SQLite (Driver JDBC 3.53.1.0)
* **Gerenciamento de Dependências:** Apache Maven
* **Distribuição:** Launch4j (Encapsulamento em executável nativo do Windows)

## Arquitetura do Projeto

O projeto adota o padrão arquitetural MVC (Model-View-Controller) aliado ao padrão DAO (Data Access Object) para a persistência de dados:

* `src/model/`: Classes de entidade que representam o domínio do sistema (ex: Usuario).
* `src/dao/`: Camada de persistência responsável por gerenciar as operações de CRUD no banco de dados SQLite.
* `src/view/`: Arquivos FXML contendo a estrutura visual das telas e folhas de estilo CSS.
* `src/view/controller/`: Controladores responsáveis pela lógica de apresentação e manipulação dos componentes gráficos.
* `src/launch/`: Ponto de entrada da aplicação (`Main.java`) configurado para contornar restrições de inicialização do Java modular (JPMS).

## Estrutura de Distribuição

Para a execução correta do sistema fora do ambiente de desenvolvimento (IDE), a pasta de distribuição deve manter estritamente a seguinte organização de arquivos:

```text
MonitoramentoHidrico/
├── MonitoramentoHidrico.exe    # Executável gerado pelo Launch4j
├── monitoramento.db           # Banco de dados relacional SQLite
└── lib/                        # Dependências externas obrigatórias
    ├── flatlaf-3.4.1.jar
    ├── sqlite-jdbc-3.53.1.0.jar
    ├── javafx.base.jar
    ├── javafx.controls.jar
    ├── javafx.fxml.jar
    ├── javafx.graphics.jar
    └── [Demais módulos JavaFX necessários]
