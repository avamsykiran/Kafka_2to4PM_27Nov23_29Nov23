Kafka
----------------------------------------------------------------

    Agenda
    -----------------

        Main 1: Apache Kafka Baics                                         
                    What is Kafka? 
                    Kafka Features
                    Kafka Concepts
                    Kafka Architecture                                      
                    Kafka Components 
                    Kafka Installation
                    Kafka Cluster 
                    Types of Kafka Clusters
                    Configuring Single Node Single Broker Cluster  
        Main 2: Kafka Producer                                              
                    Constructing a Kafka Producer 
                    Sending a Message to Kafka 
                    Producing Keyed and Non-Keyed Messages  
                    Sending a Message Synchronously & Asynchronously 
                    Configuring Producers 
                    Serializers 
                    Partitions 
        Main 3: Kafka Consumer                                              
                    Consumers and Consumer Groups 
                    Standalone Consumer 
                    Consumer Groups and Partition Rebalance 
                    Creating a Kafka Consumer 
                    Subscribing to Topics 
                    Deserializers 
        
    Lab SetUp
    -----------------------
        STS/Eclipse IDE
        Java 8
        Kafka
        Zookeeper

    What and Why ?

        + Distrubuted Event Progression or Message Broadcasting System.
        + It means that Kafka can facilitate two completely isolated non-similar
            application to communicate withe one another.

        Apps need to talk to one another
            In context of microservices
            In context of a IOT device talking to a Web App/Mobile App
            In context to bigdata
            and so on.....

        + Kafka can be configured on a single clustur or a multiple - clustur
        + Kafka can be used in any scale of utility
    
    Message Driven System

        Point-to-Point

            producer ----------->> MSG --------->> consumer
                            (half-duplex)

        Broadcasting

            producer1
            producer2   -------->> MSG -- MSG-POOL ------->> consumer1, consumer2, consumer3...
            producer3

        Kafka supports both types of Messaging systems.

        Producer        is one which supplies a message

        Consumer        is one which receives a message

        Message         can be 
                                a request
                                an event
                                an instruction
                                or a record or any arbitary piece of information.

                in a chatting application, a message is 'some text'
                in CQRS , a message is 'an event' or 'a command'

        Topic           is a logical identifier for a group of messages.

        Publish     the process of sending message by a producer is called publishing.
                    a producer PUBLISHES messages to a TOPIC

        Subscribe   the process of receiving messages by a consumer is called subscribing.
                    a consumer SUBSCRIBES a topic and receives the messages.

    Apcche Kafka ---------> Pub-Sub Messaging System
    ---------------------------------------------------------------------------------

            Installation
            ------------------------------------------------------------------------
                dependency:     Java 8
                download: https://kafka.apache.org/downloads  Scala - 2.13 (tgz)

                Extract it to drive:/kafka

                create folder drive:/kafka/data/zookeeper       //state maintanence
                create folder drive:/kafka/data/kafka           //kafka server logs

                open drive:/kafka/config/zookeeper.properties
                set the below prop:
                        dataDir=drive:/kafka/data/zookeeper

                open drive:/kafka/config/server.properties
                set the below prop:
                       log.dirs=drive:/kafka/data/kafka

                INSTALLATION IS DONE

            Start Up
            ------------------------------------------------------------------------

                Start ZooKeeper
                    drive:\kafka\bin\windows>zookeeper-server-start.bat ../../config/zookeeper.properties

                Start Kafka
                    drive:\kafka\bin\windows>kafka-server-start.bat ../../config/server.properties

            Shut Down
            -------------------------------------------------------------------------

                shutdown kafka
                shutdown zookeeper

            Archetecture
            -------------------------------------------------------------------------
                                            Kafka Eco System
                                              Clustur1
                                                Broker1
                Producer ---message----→            TopicA              ----message---► Consumer Group
                                                        Partition1                              Consumer1
                                                        Partition2                              Consumer2
                                                    TopicB
                                                        Partition1
                                                        Partition2

                                                Broker2
                                                    TopicA
                                                        Partition1
                                                        Partition2
                                                    TopicB
                                                        Partition1
                                                        Partition2

                   Producer ↔---broker Id----          Zookeeper            ----offset---► Consumer Group


                Clustur?
                        is a group of brokers..

                Topic?
                        1. is a logical channel of messages
                        2. a topic is resposnible to recive or to send
                            message of homoginous context.
                        3. each topic is identified by a unique name.
                        4. message when sent must be assosiated with the topic name from producer
                        5. a consumer can subscribe to a single topic through the topic name.
                        6. message in a topic irrespective of thir model (string/object/event) are
                            are modeled as an array of bytes (binary) in kafka.

                Broker?
                        1. is a execution unit that maintains the messages of a topic.
                        2. a single broker can manage one or more topics.
                        3. a broker is a stateless unit of process, that the broker
                                will not rememebr anything related to the communcation
                                like, who produced or who consumed or hom much is consumed.....
                        4. a broker is the reason behind the scalability and avialability of
                            messaging service on kafka.
                                as each broker can attend one consumer at a time, the more the 
                                number of broker the more the number of consumers that can
                                be served.

                Partition?
                        1. a topic can be split into any number of partiions.
                        2. each partition can hold any number of messages.
                        3. there is limit on number of partitions.
                        4. the partition is selected to hold a message randomly, as long as the 
                           message has no assosiate key from the producer.
                        5. If the producer assosiates a message with a key, the partition
                            related to the key is selected/created and the message is pushed in it.
                        6. Each broker will have a copy of the partition and those copies
                           are called replacas.
                        7. Every broker need not have every partition or its replicas.
                        8. A partion is mastrly managed by one of these brokers and is called
                                the leader and other broker having the replicas are called followers.
                        9. The availability is ensured, as if the leader falls, the next follower will
                                becoem the leader automatically.

                            assuming replica-factor = 2

                Broker1         Broker2         Broker3     Broker4
                    TopicA          TopicA          TopicA      TopicA
                        P1              P2              P3          P4
                        P2              P3              P4          P1

                TopicA P1   has Broker1 as leader and Broker4 as follower

                if Broekr 1 falls .....

                        assuming replica = 2

                Broker1  (falled) Broker2         Broker3     Broker4
                    TopicA          TopicA          TopicA      TopicA
                        P1              P2              P3          P4
                        P2              P3              P4          P1
                                        P1              P2
            
                 TopicA P1   has Broker4 as leader and Broker2 as follower
                        
            offset?

                is a serial number maintained by the zookeeper for 
                messages and consuemrs, to remeber, what is the 
                last message consumed by a consumer in  a consumer group.

    Kafka API
    =================================================================================

        Producer API        api for a producer to interact with Kafka
        Consumer API        api for a consumer to interact with Kafka
        Stream API          api allows the processing of the vents received on kafka,
        Connector API       api can interact with an underlying perssitant api
                            to act like an automatic producer or consumer.

    Kafka CLI
    =================================================================================

        bunch of .bat/.sh files are available as kafka cli tools,

        kafka-topics -zookeeper localhost:2181 -topic SAVE_TRAN -create -partitions 3 -replication-factor 1

        kafka-topics -zookeeper localhost:2181 -list
            
        kafka-topics -zookeeper localhost:2181 -describe --topic topicName

        Kafka-console-producer  -broker-list localhost:9092 -topic topicName

        Kafka-console-producer  -broker-list localhost:9092 -topic topicName -property parse.key=true -property key.separator=:

        Kafka-console-consumer  -bootstrap-server localhost:9092 -topic topicName

        Kafka-console-consumer  -bootstrap-server localhost:9092 -topic topicName --from-beginning

        Kafka-console-consumer  -bootstrap-server localhost:9092 -topic topicName --from-beginning -property print.key=true -property key.separator=:

    Working with Kafka on Java
    ===========================================================================

        <dependency>
			<groupId>org.apache.kafka</groupId>
			<artifactId>kafka-clients</artifactId>
			<version>2.3.0</version>
		</dependency>
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-simple</artifactId>
			<version>1.7.30</version>
			<scope>test</scope>
		</dependency>

        refer to ./WS foldwer

    Spring Kafka
    ============================================================================
        refer to ./WS foldwer

    Message Stream Processing
    ============================================================================

        Stream  ---> a flow (of messages)

            Intermideate Streams

                recevie streams of messages
                        ------ process it --------------
                                send a new stream of modified message

            Terminal Streams

                recevie streams of messages --------- and consumed.!

            Stream Operations
                map             converting one mesg into another
                reduce          reduces the entire stream into one message
                filter          apply a predicate on the src stream
                                and only those mesgs that pass for the predicate
                                are allowed in the dest stream.
                foreach         action executed on each msg
                flatMap         a container of containers into a flat container.

    Spring Kafka Streams
    ============================================================================

        Producer        java.util.function.Supplier
        Consumer        java.util.function.Consumer
        StreamProcessor java.util.function.Functional

        Spring web flux:
                    Flux        a stream of one or more values
                    Mono        a stream of a single value.

        Producer            publishes to a topic (say topicP)
      
        StreamProcessor     subscribes to topicP
                                process the stream  
                                    publishes to topicC

        Consumer            subscribes to a topic (say topicC)

        refer to ./WS foldwer

        Producer  --> topicLayer1   --->  doublerStream  --> topicLayer2 ---> filterStream   --> topicLayer3 -> Consumer
            produce a random                  n -> n*2                          pick only                      print
            series of numbers                                                    5 multiples