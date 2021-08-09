# RabbitMQTutorial
## RabbitMQ messaging study

Este estudo foi realizado com base no tutorial Java do próprio RabbitMQ (https://www.rabbitmq.com/tutorials).

Para utilizar o RabbitMQ com o SpringBoot foi também utilizado o conteúdo encontrado no Dev Cave (https://medium.com/dev-cave/rabbit-mq-parte-i-c15e5f89d94) e o vídeo COMUNICAÇÃO ASSÍNCRONA ENTRE MICROSERVICES (https://www.youtube.com/watch?v=V-PqR0BxA8c&t=1270s)

Tanto os producers como os consumers estão implementados neste mesmo projeto.
****************************************************************************************
### Pré Requisito
Necessário estar rodando a imagem do rabbitMq no docker (ou colocar nas  configurações o caminho do que deseja utilizar) :
docker run -d --hostname my-rabbit --name rabbit13 -p 8082:15672 -p 5672:5672 -p 25676:25676 rabbitmq:3-management
61bdfb5639c1422767f3237c82c45a4a0bf4a717908b6b19cd037597e1fba84f

Em http://localhost:8082/ utilizar usuário e senha "guest" para ter acesso à visualização do envio e leitura de mensagens

****************************************************************************************
### Funcionalidades

    http://localhost:8080/send?message=String que se deseja enviar ...

Endpoint utilizado para se enviar uma String *diretamente pra fila  'task_queue' (usando o exchanges padrão "").

Possuí um método que simula a realização de um trabalho,
se ao final da frase tiver três pontos levará três segundos para que a mensagem "Work Done" seja exibida.

Além disso possuem dois consumers que são responsáveis por exibir as mensagens.


    http://localhost:8080/{exchange}/{routingKey}
Simula o envio de mensagens utilizando o Exchanges.

Os tipos de Exchanges utilizados são o FANOUT-EXCHANGE-BASIC e o DIRECT-EXCHANGE-BASIC.
As routingKey disposíveis são as TO-FIRST-QUEUE e TO-SECOND-QUEUE.

Neste endpoint é enviado um json da classe MessageTextMessageText{id, title, message, email}. Então assim que recebida a mensagem é exibida.
****************************************************************************************
### Um pouco de teoria/ estudo de comportamento

 Em http://localhost:8080/send?message=String existem dois consumers responsáveis por receber e exibir as mensagens.
A princípio como é utilizado o método default do RabbitMQ as mensagens são alteradas entre os dois workers, este método de distribuição é chamado de round-robin. 
 
A desvantagem é que se um worker for desativado e a mensagem ainda não tiver sido processada, você simplesmente a perderá.

Para contornar o problema anterior o RabbitMQ suporta "message acknowledgments".
Uma confirmação é enviada ao Rabbit por parte do consumer dizendo que a mensagem já foi recebida e processada,
podendo ser assim então excluída. Por default isto já é realizado.


* Message durability

Permite manter os dados mesmo se o rabbit parar, se for marcado como true. 

*OBS: não pode ser alterado depois que o canal foi criado. 

* Fair dispatch

Há um modo de balancear o envio de mensagens para um consumer que esteja mais livre, para isso podemos utilizar o basicQos com o valor de prefetchCount setado em 1.
Isso basicamente faz com que não seja enviado uma nova mensagem a um consumer que não tenha dado o retorno de que a mensagem foi realmente processada.

** Se deve tomar cuidado para que a fila não fique muito grande e exceda o limite,
para evitar isso é necessário ficar de olho e caso necessário aumentar por exemplo o número de consumers. Além disso
é necessário sinalizar que o conteúdo foi processado ao RabbitMQ.


No endpoint /send só era enviada uma mensagem para um canal específico, agora será tratado de quando existem multiples consumidores.

##### Publish/Subscribe
http://localhost:8080/{exchange}/{routingKey}
 * Exchanges

Pra quem o producer deve de fato enviar a mensagem, ele que irá ser responsável por enviar a mensagem para a(s) fila(s).
O que define se uma mensagem irá para uma fila, para várias ou para nenhuma é o tipo do exchange.
Os tipos podem ser:

* direct: Encaminha as mensagens procurando por um binding key igual ao routing key fornecido. Caso não encontre a mensagem é descartada.
* topic: Existe uma espécie de expressão regular aplicada sobre o routing key, é como se colocasse uma lista de filas para qual deve ser enviada.
* headers: usa os valores de header da mensagem e ignora os binding key
* fanout: encaminha para todas as filas com binding nele, desconsiderando routing key.

Anteriormente era feita o envio sem utilizar o Exchanges, isso acontecia porque era utilizado um valor default para ele, o "".

Quando é utilizado este valor vazio as mensagens são roteadas para a fila que possui a específica routinKey, que no exemplo seria o "task_queue".

Dar um nome as filas é importante para quando desejamos compartilha-las entre diferentes producers e consumers.

Exemplo de envio para utilizar no POSTMAN:

    http://localhost:8080/DIRECT-EXCHANGE-BASIC/TO-FIRST-QUEUE

    http://localhost:8080/FANOUT-EXCHANGE-BASIC/ANY


No body o conteúdo deve ser algo similar a:

    {
    "id":"0da9b42c-d2d5-4bda-8336-1c6cdecb327d",
    "title":"Titulo da mensagem",
    "message":"Essa é a mensagem enviada",
    "email":"cristian.jesus@db1.com.br"
    }