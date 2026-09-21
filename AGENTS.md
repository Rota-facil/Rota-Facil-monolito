contexto:
	rota-facil eh um sistme que tem o objetivo de gerenciar rotas, transports, alunos e motoristas no contexto escolar. Por
	exemplo: aluno pode acessar no seu celular o app, escolher quais sao as viagens do dia, verificar qual eh o motorista,
	se inscrever na viagem via qr code, escolhar os pontos de espera, qual instituicao ele vai ir... O mostorista eh responsavel
	por gerenciar as viagens no contexto de (iniciar uma viagem, cancelar, ver os alunos...). E o admin (das prefeituras) cadas
	tram os veiculos, rotas, pontos de embarque, viagens, instituicoes, motoristas... Consegue gerenciar o sistema como um todo
	e o gestor (dono do rota-facil), faz o gerenciamento das proprias prefeituras (sistema multi-tenant)


o que esta sendo feito:
	Hoje o rota-facil esta passando por uma refatoracao arquitetural: antes ele era um sistema de microservicos, mas que agora
	foi mudado sua arquitetura para ser um monolito multimodular, e esta acontecendo essa transicao do sistma distribuido antigo
	para o monolito multimodular. O sistema novo esta aqui nessa pasta, enquanto o sistema antigo esta na pasta
	/home/tagashi/rota-facil e comtempla os repo's: audit-service, file-service, auth-service, transport-service, gateway-servi
	ce, notification-service, eureka-service e places-service. Em algumas task's que vou passar para voce, voce pode usar esse
	projeto do rota-facil distribuido para pegar contexto e fazer a task para a nova arquitetura monolitica. 

o que significa os modulos de rota facil:
	os modulos do rota-facil estao dividos por dominio e tem definicoes muito claras: APPLICATION serve como porta de entrada do programa
	eh onde ele vai ser iniciado, FILES é o modulo de gerenciamento de arquivos dos usuarios, INTERACTIONS eh referente a
	parte de avaliacoes (aluno pode avaliar motorista e motorista pode avaliar aluno), NOTIFICATION eh a parte de envio de
	emails, PLACES eh responsavel por gerenciar pontos de embarque e instituicoes, PREFECTURES eh o gerenciamento de prefeituras
	que o gestor vai fazer, SECURITY eh a parte de configuracao de seguranca (autenticacao e autorizacao), USERS eh onde vai
	ter funcionalidades pessoais de alunos, motoristas, admin's e gestores (SUPERADMIN),  VEHICLES eh o gerenciamento de
	veiculos e JOURNEY eh uma das partes mais importantes do sistema que vai servir para gerenciamento de viagnes, rotas,
	processar o caminho do onibus e assim por diante


como deve ser a organizacao dentro dos modulos:
	o rota-facil segue uma organizacao em cada modulo da seguinte maneira: cada modulo tem o sua propria area de, http(
	onde vai ficar os controllers, dto's (response ou request)), persistence (onde vai ficar os 
	mappers de entidade para dominio, repositories, entidades...), domain (onde vai ter os enum's), exceptions e assim por
	diante. Tambem existe o modulo business onde vao ficar as classes de regra de negocios, e aqui eh muito importante
	prestar atencao em uma coisa: o rota-facil segue a logica de casos de uso + helpers: ao inves de ter um service
	que guarda toda a regra de negocio em um unico lugar, o rota-facil ao inves disso cria pequenas classes que
	sao service que executam casos de uso diferentes: entao listar informacoes do usuario ou criar conta sao casos de 
	uso diferentes, ou seja, eu vou ter duas classes diferentes que vao executar respectivamente esses casos de uso,
	alem disso, tambem tem a logica dos helpers que executam acoes pequenas que podem ser utilizadas por diferentes
	casos de uso: por exemplo: para atualizar usuario ou para listar, eu preciso verificar se a conta dele existe,
	e ai eu vou ter que buscar o usuario no banco e se ele nao existir, vou lancar uma exceptions: ai ao inves de eu
	executar a mesma busca com o repository e se nao tiver lancar excessao nas duas classes, crio um helper que faz
	exatamente isso, ai nos casos de uso eles so injetam a classe e usam o metodo de excutar dela. 
