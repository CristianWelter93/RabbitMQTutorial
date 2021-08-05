# RabbitMQTutorial
 rabbitMQ messaging study

O código gerado foi criado com base no tutorial Java do próprio RabbitMQ (https://www.rabbitmq.com/tutorials) e também foi utilizado o conteúdo encontrado no Dev Cave (https://medium.com/dev-cave/rabbit-mq-parte-i-c15e5f89d94)

Necessário estar rodando a imagem do rabbitMq no docker :
docker run -d --hostname my-rabbit --name rabbit13 -p 8082:15672 -p 5672:5672 -p 25676:25676 rabbitmq:3-management
61bdfb5639c1422767f3237c82c45a4a0bf4a717908b6b19cd037597e1fba84f

em http://localhost:8082/ utilizar em usuário e senha guest para ter acesso a visualização do envio e leitura de mensagens

Em SendService está disponível um método POST para o envio da mensagem desejada (http://localhost:8080/send?message=MensagemDesejada)

No ReceiveService existe um método que simula uma tarefa conforme a quantidade de pontos que existem na mensagem, se tiver MensagemDesejada... levará três segundos para que a mensagem Done seja exibida.

Além disso foram criados dois métodos (readMessageFirst,readMessageSecond)  que iniciam assim que construída a aplicação, eles são os workers responsáveis por receber e exibir as mensagens.
A princípio como é utilizado o método default do RabbitMQ as mensagens são alteradas entre os dois workers, este método de distribuição é chamado de round-robin. A desvatagem é que se um worker for desativado e a mensagem ainda não tiver sido processada, você simplismente a perderá.

Para contornar o problema anteior o RabbitMQ suporta "message acknowledgments". Uma confirmação é enviada ao Rabbit por parte do consumer dizendo que a mensagem já foi recebida e processada, podendo ser assim então excluída. Por default isto já é realizado, podendo ser desabilitado utilizado o autoAck=true :

channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { }); 


Message durability


channel.queueDeclare(QUEUE_NAME, false, false, false, null);

O segundo parametro é o da durabilidade. Permite manter os dados mesmo se o rabbit parar, se for marcado como true. * OBS: não pode ser alterado depois que o canal foi criado. Além disso deve ser aplicada a seguinte configuração no canal:

channel.basicPublish("", QUEUE_NAME,
            MessageProperties.PERSISTENT_TEXT_PLAIN,
            message.getBytes());

Fair dispatch

Há um modo de balancear o envio de mensagens para um consumer que esteja mais livre, para isso podemos utilizar o basicQos com o valor de prefetchCount setado em 1.
Isso basicamente faz com que não seja enviado uma nova mensagem a um consumer que não tenha dado o retorno de que a mensagem foi realmente processada.

** Se deve tomar cuidado para que a fila não fique muito grande e exceda o limite, para evitar isso é necessário ficar de olho e caso necessário aumentar por exemplo o número de consumers.


channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false); avisa que finalizou

****************************************************************************************
Nos passos anteriores uma mensagem só era enviada para um canal específico, agora será tratado de quando existem multiplos consumidores.

Publish/Subscribe

Exchanges

Pra quem o producer deve de fato enviar a mensagem, ele que irá ser responsável por enviar a mensagem para a(s) fila(s).
O que define se uma mensagem irá para uma fila, para várias ou para nenhuma é o tipo do exchange.
Os tipos podem ser:

direct: Encaminha as mensagens procurando por um binding key igual ao routing key fornecido. Caso não encontre a mensagem é descartada.
topic: Existe uma espécia de expressão regular aplicada sobre o routing key, é como se colocasse uma lista de filas para qual deve ser enviada.
headers: usa os valores de header da mensagem e ignora os binding key
fanout: encaminha para todas as filas com binding nele, desconsiderando routing key.

Anteriormente era feita o envio sem utilizar o Exchanges, isso acontecia porque era utilizado um valor default para ele, o "" :

channel.basicPublish("", "hello", null, message.getBytes());

Quando é utilizado este valor vazio as mensagens são rotiadas para a fila que possui a específica routinKey, que no exemplo seria o "hello".

Agora pode-se publicar utilizando apenas o uma exchange com nome:
channel.basicPublish( "nomeDaExchange", "", null, message.getBytes());

Dar um nome as filas é importante para quando desejamos compartilha-las entre diferentes producers e consumers.
