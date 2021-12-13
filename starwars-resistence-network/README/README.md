# Starwars Resistence Network 
**Desenvovedor:** Anupam Gogoi


## Descrição do problema
O império continua sua luta incessante de dominar a galáxia, tentando ao máximo expandir seu território e eliminar os rebeldes.
Você, como um soldado da resistência, foi designado para desenvolver um sistema para compartilhar recursos entre os rebeldes.


## Requisitos
Você irá desenvolver uma API REST (sim, nós levamos a arquitetura da aplicação a sério mesmo no meio de uma guerra), ao qual irá armazenar informação sobre os rebeldes, bem como os recursos que eles possuem.

## Configurações
| Item      | Description |
| ----------- | ----------- |
| Database      | H2 In memory      |
| Port   | 8081        |

## Executar usadno Docker
```
docker pull anupamgogoi/starwars-app:latest
docker run --name starwars -p 8081:8081 -d starwars-app 
docker logs -f starwars
```

## Postman Collection
[Postman Collection](./STARWARS.postman_collection.json)

## Endpoints

### Adicionar Rebelde
Seus pertences devem ser declarados quando eles são registrados no sistema. Após isso eles só poderão
mudar seu inventário através de negociação com os outros rebeldes.Rebeldes não podem Adicionar/Remover itens do seu inventário

POST http://localhost:8081/rebelde/
```
{
    "nome": "Rebelde Z",
    "idade": 28,
    "genero": "F",
    "traidor": false,
    "localizacao": {
        "latitude": "-7.1185908",
        "longitude": "-30.8406991",
        "nome": "Via Láctea"
    },
    "inventario": {
        "itens": [
            {
                "nome": "Arma",
                "quantidade": 1
            },
            {
                "nome": "Munição",
                "quantidade": 0
            },
            {
                "nome": "Água",
                "quantidade": 5
            },
            {
                "nome": "Comida",
                "quantidade": 3
            }
        ]
    }
}
```
### Listar todos os Rebeldes
GET http://localhost:8081/rebelde/

### Reportar Localização
PATCH http://localhost:8081/rebelde/{id}/reportar/local

**id** = id de **Rebelde**
```
{
    "latitude": "1.0",
    "longitude": "2.0",
    "nome": "Demo"
}
```

### Reportar Traidor
Um rebelde é marcado como traidor quando, ao menos, três outros rebeldes reportarem a traição.
POST http://localhost:8081/rebelde/reportar/traidor

**idReporter** : Quem rporta 
**idTraidor** : Qual Rebelde será Traidor
```
{
    "idReporter":3,
    "idTraidor": 1
}
```


### Trade
Os rebeldes poderão negociar itens entre eles.
Para isso, eles devem respeitar a tabela de preços abaixo, onde o valor do item é descrito em termo de pontos.
Ambos os lados deverão oferecer a mesma quantidade de pontos. Por exemplo, 1 arma e 1 água (1 x 4 + 1 x 2) valem 6 comidas (6 x 1) ou 2 munições (2 x 3).
A negociação em si não será armazenada, mas os itens deverão ser transferidos de um rebelde a outro.

POST http://localhost:8081/rebelde/trade

```
[
    {
        "id": 1,
        "itens": [
            {
                "nome": "Arma",
                "quantidade": 1
            }
        ]
    },
    {
        "id": 2,
        "itens": [
            {
                "nome": "Água",
                "quantidade": 2
            }
        ]
    }
]
```

