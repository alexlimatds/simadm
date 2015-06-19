# Arquitetura #

A linguagem/plataforma Java foi escolhida para o desenvolvimento do SIMADM 2. Essa decisão justifica-se principalmente pela familiaridade dos desenvolvedores com a tecnologia.

Para a camada de web, atualmente em desenvolvimento, optou-se pelo framework JSF em conjunto com a biblioteca Primefaces. O núcleo de simulação, o qual aborda os conceitos de fluxos e estoques, foi implementado em Java puro apoiado pela API Jep para a avaliação de expressões matemáticas. A camada de persistência será desenvolvida com Hibernate, mas seu desenvolvimento ainda não foi iniciado. Ainda não foi decidido qual o banco de dados a ser utilizado, mas a preferência será por um SGBDR open source e disponível em Linux e Windows como Postgres e MySQL.