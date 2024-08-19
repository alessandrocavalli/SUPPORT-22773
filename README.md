1. helm install my-release oci://registry-1.docker.io/bitnamicharts/kafka -f values.yaml
2. start your camunda platform environment... I use helm in same namespace...
3. Add 127.0.0.1 my-release-kafka-controller-0.my-release-kafka-controller-headless.default.svc.cluster.local to /etc/hosts
4. kubectl port-forward service/my-release-kafka 9092:9092
5. kubectl port-forward service/my-release-kafka-controller-0-external 9094:9094
6. Run kafaka-producer-application
7. Trigger http://localhost:8089/kafka/publishmultitopic/topicname where {topicname} is a string, and the topic name.
8. 5000 messages will be created for that topic
9. Deploy kafka-start-event.bpmn multiple times (6-8 times), changing every time the groupid and the definition id in order to unleash the RESOURCE_EXHAUSTED exception.
10. Wait a bit... until all of the processes reach 5000 instances.
11. Deploy another batch of processes (.9) and check Operate, you will see the first batch changing from 5000 to 10000 
