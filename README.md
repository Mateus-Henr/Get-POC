# Projeto-Desenvolvimento-POO

## Autores:

Miguel Antônio Ribeiro e Silva - 4680

Alan Gabriel Martins Silva - 4663

Mateus Henrique Vieira Figueiredo - 4707

João Victor Graciano Belfort de Andrade - 4694

## Objetivos:

O projeto de desenvolvimento desta disciplina visa desenvolver, em linguagem Java, um sistema para gerenciamento de Trabalhos de Conclusão de Curso.
Ao longo do período, várias classes e módulos serão criados visando utilizar ao máximo todas as especifidades e  peculiaridades da Programação Orientada a Objetos.
Bancos de Dados e bibliotecas gráficas nos auxiliarão no desenvolvimento do trabalho e pretendemos com isso, criar um programa simples, prático, minimalista e  interativo com o usuário, possibilitando seu uso, nos contextos reais das Universidades.

## Detalhamento, primeira entrega:

Foi definido que cada integrante do grupo começaria diferentes partes do trabalho, como design, banco de dados e formas de implementação. Foi realizado a modelagem do Banco de Dados que usaremos, em modelo relacional, por meio do MySQL Workbench, e iniciamos sua junção com o projeto. A interatividade com o usuário foi desenhada (Canva) e começamos a implementá-la pela biblioteca gráfica Java FX. As classes e o modelo MVC estão sendo implementadas em JAVA, mas ainda não foram testadas por completo, visto que preferimos realizar os testes unitários, conforme implementamos o restante do projeto.

## Detalhamento, segunda entrega:

O controle está bem encaminhado, com heranças, alguns testes (não unitários) e com tratamento de exceção simples, sua finalização e testes por completo depende apenas da parte da visualização, que ainda está em desenvolvimento, pois se trata de algo que nunca havíamos usado antes, CSS e HTML. A parte de banco de dados, está praticamente completa. O Database, com os testes, está na pasta models/db juntamente com todos os scripts relacionados a ele. As funções referentes ao banco, está na pasta db, na main. Como havia dito, não fizemos testes unitários ainda, por dependência de outras partes do projeto, mas todas as funções criadas, até agora, foram testadas.

## Detalhamento, terceira entrega:

Projeto finalizado. O controle foi feito como especificado, alguns testes unitários foram feitos e tratamento de exceções mais elaborados. A parte da visualização está completa, juntamente com a parte de Banco de Dados.

## Relatório Final

O projeto, implementado ao longo do período foi um desafio para o grupo. Tivemos que aprender várias tecnologias que nunca haviam usados antes, além de projetar um programa complexo, com muitas funcionalidades. Detalho a seguir, alguns detalhes das nossas implementações.

### Armazenamento persistente

A parte de armazenamento do projeto foi projetada utilizando o banco de dados MySQL. O modelo Entidade-Relacionamento foi feito usando a ferramento MySQL Workbench, buscamos deixar as relações simples, mas completas, além de criarmos algumas que não foram pedidas na especificação. Após a criação do modelo, uma engenharia reversa foi feita e o esquema foi importado para o projeto GetPOC. A conexão com JAVA foi criada na pasta package com.ufv.project.db, juntamente com todos os querys necessários. Foi criado uma classe para cada tabela, e os querys, implementados dentro das mesmas.

### View - Interface gráfica

A interação com o usuário, ou seja, a parte de View do programa, foi feita por meio de uma interface gráfica, usando JavaFX e FXML para os elementos gráficos do programa em sí, e CSS para os estilos da GUI. Todos os recursos usados no View (arquivos .fxml, .css, arquivos de imagens e arquivos de fontes) foram organizados na pasta src/main//resources/com/ufv/project, sendo que há uma grande integração entre esses recursos. Os arquivos FXML também estão fortemente interligaods com os seus respectivos controles, que por sua vez estão no pacote src/main/java/com/ufv/project/controller/fx. Foi um grade desafio usar essa tecnologia para a realização da visão do projeto, já que nenhum dos integrantes do grupo tiveram expreriência prévia com essas ferramentas, o que também fez com que fosse uma ótima oportunidade de aprendizado.

### Controller

Controles foram criados a fim de seguir o modelo MVC, de forma a ter controles para páginas específicas na aplicação. Suas funções são lidar com operações que o usuário solicita como recuperar dados do banco de dados, checagem de dados informado, etc. Como optamos por usar JavaFX os controladores funcionam a partir de "Event handlers" associados a elementos gráficos do próprio JavaFX. Vale notar que por usar essa interface gráfica, muitas formas de modularização se tornam impossível por causa da necessidade de ter que atrelar o controlador diretamente com JavaFX, o que foi mostrado através de várias pesquisas.