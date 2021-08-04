# RabbitMQTutorial
 rabbitMQ messaging study


Necessário estar rodando a imagem do rabbitMq no docker :
docker run -d --hostname my-rabbit --name rabbit13 -p 8082:15672 -p 5672:5672 -p 25676:25676 rabbitmq:3-management
61bdfb5639c1422767f3237c82c45a4a0bf4a717908b6b19cd037597e1fba84f

em http://localhost:8082/ utilizar em usuário e senha guest para ter acesso a visualização do envio e leitura de mensagens

Em SendService está disponível um método POST para o envio da mensagem desejada (http://localhost:8080/send?message=MensagemDesejada)

No RecieveService existe um método que simula uma tarefa conforme a quantidade de pontos que existem na mensagem, se tiver MensagemDesejada... levará três segundos para que a mensagem Done seja exibida.

Além disso foram criados dois métodos (readMessageFirst,readMessageSecond)  que iniciam assim que construída a aplicação, eles são os workers responsáveis por receber e exibir as mensagens.
A princípio como é utilizado o método default do RabbitMQ as mensagens são alteradas entre os dois workers, este método de distribuição é chamado de round-robin