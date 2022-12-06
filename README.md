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

# Armazenamento persistente

A parte de armazenamento to projeto foi projetada utilizando o banco de dados MySQL. O modelo Entidade-Relacionamento foi feito usando a ferramento MySQL Workbench, buscamos deixar as relações simples, mas completas, além de criarmos algumas que não foram pedidas na especificação. Após a criação do modelo, uma engenharia reversa foi feita e o esquema foi importado para o projeto GetPOC. A conexão com JAVA foi criada na pasta package com.ufv.project.db, juntamente com todos os querys necessários. Foi criado uma classe para cada tabela, e os querys, implementados dentro das mesmas.



