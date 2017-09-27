# Socio Torcedor

Expor um serviço de Sócio Torcedor seguindo a especificação abaixo  

## Premissas

Eu, como usuário, quero me cadastrar através de uma API que me permite participar de algumas campanhas. Para tanto, os critérios de aceite dessa história são:

*	Dado um E-mail que já existe, informar que o cadastro já foi efetuado, porém, caso o cliente não tenha nenhuma campanha associada, o serviço deverá enviar as novas campanhas como resposta;
*	O Cadastro deve ser composto de:
```
	Nome Completo;
	E-mail;
	Data de Nascimento;
	Meu Time do Coração;
```
*	O Cliente não pode ter mais de um cadastro ativo;
*	Ao efetuar o cadastro do usuário, utilize os serviços criados anteriormente para efetuar as operações e as associações necessárias:
```
	O Cadastramento do cliente ocorre antes da associação com as campanhas, ou seja, o processo de cadastro e associação ocorre em dois momentos separados;
	O Usuários pode ter N Campanhas associadas a ele; Lembrando que as campanhas são associadas ao Time do Coração;
	A associação do usuário as campanhas podem ocorrer em dois momentos:
  	Se for usuário novo: Após o cadastramento do usuário, o sistema deverá solicitar as campanhas ativas para aquele time do coração e efetuar a associação;
  	Se for um usuário já cadastrado: Deverá ser feita a associação das campanhas novas, ou seja, as que o usuário daquele time do coração não tem relacionamento até o momento.
	O Consumo das listas das campanhas deve ser feita via Serviço exposto conforme descrito no exercício anterior;
	O Cadastramento das campanhas deverá ser feito via Serviço (API, conforme descrito no exercício anterior)
	O Cadastramento não pode ser influenciado pelo serviço das campanhas, caso o serviço das campanhas não esteja “UP”
```

## Métodos

### cadastrarCliente(Cliente cliente)

Cadastra um cliente no banco, caso o e-mail já esteja cadastrado retorna uma mensagem avisando que o mesmo já estava cadastrado, caso o cliente possua campanhas disponiveis a serem associadas, retorna a lista das campanhas disponiveis.

* URL: /SocioTorcedor/cadastrarCliente
* Método: Post
* Parametros de Dados: Cliente
Exemplo
```
{
    "email": "joao@teste.com",
    "nome": "Joal da Silva Teste",
    "idTime": 10,
    "dataNascimento": "1989-04-26"
}
```

Exemplo chamada
```
curl -X POST \
  http://localhost:8080/SocioTorcedor/cadastrarCliente \
  -H 'content-type: application/json' \
  -d '{
    "email": "joao@teste.com",
    "nome": "Joal da Silva Teste",
    "idTime": 10,
    "dataNascimento": "1989-04-26"
}'
```

Exemplo Retorno Sucesso
```
Cadastro Realizado com Sucesso!
```

Exemplo Retorno E-mail Cadastrado sem Campanhas Disponiveis para associar
```
"E-mail já cadastrado"
```

Exemplo Retorno E-mail Cadastrado com Campanhas Disponiveis para associar
```
[
    {
        "id": 814,
        "nome": "Campanha 28",
        "idTime": 19,
        "dataInicioVigencia": "2017-09-30",
        "dataFimVigencia": "2017-11-26"
    },
    {
        "id": 841,
        "nome": "Campanha 55",
        "idTime": 19,
        "dataInicioVigencia": "2017-09-30",
        "dataFimVigencia": "2017-12-20"
    },
    {
        "id": 870,
        "nome": "Campanha 84",
        "idTime": 19,
        "dataInicioVigencia": "2017-09-30",
        "dataFimVigencia": "2018-01-17"
    }
]
```

### associarCampanhas(Integer idCliente, List<Campanha> campanhas)

Associa as campanhas desejadas ao cadastro do Cliente.

* URL: /SocioTorcedor/associarCampanhas/{idCliente}
* Método: Post
* Parametros de URL: idCliente. Ex.: 1
* Parametros de Dados: campanhas
Exemplo
```
[
    {
        "id": 632,
        "nome": "Campanha 46",
        "idTime": 19,
        "dataInicioVigencia": "2017-09-30",
        "dataFimVigencia": "2017-11-29"
    },
    {
        "id": 642,
        "nome": "Campanha 56",
        "idTime": 19,
        "dataInicioVigencia": "2017-09-30",
        "dataFimVigencia": "2017-10-24"
    },
    {
        "id": 648,
        "nome": "Campanha 62",
        "idTime": 19,
        "dataInicioVigencia": "2017-09-30",
        "dataFimVigencia": "2017-12-31"
    }
]
```

Exemplo chamada
```
curl -X POST \
  http://localhost:8080/SocioTorcedor/associarCampanhas/1 \
  -H 'content-type: application/json' \
  -d '[
    {
        "id": 632,
        "nome": "Campanha 46",
        "idTime": 19,
        "dataInicioVigencia": "2017-09-30",
        "dataFimVigencia": "2017-11-29"
    },
    {
        "id": 642,
        "nome": "Campanha 56",
        "idTime": 19,
        "dataInicioVigencia": "2017-09-30",
        "dataFimVigencia": "2017-10-24"
    },
    {
        "id": 648,
        "nome": "Campanha 62",
        "idTime": 19,
        "dataInicioVigencia": "2017-09-30",
        "dataFimVigencia": "2017-12-31"
    }
]'
```

Exemplo Retorno
```
Campanhas associadas com sucesso!
```
