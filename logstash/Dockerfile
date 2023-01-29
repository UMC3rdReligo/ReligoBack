FROM logstash:7.17.8
USER root
RUN apt-get update \
    && apt-get install -y wget
RUN wget 'https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.18.tar.gz'
RUN tar xvf ./mysql-connector-java-8.0.18.tar.gz
RUN mv ./mysql-connector-java-8.0.18/mysql-connector-java-8.0.18.jar /mysql-connector-java.jar
RUN rm -rf ./mysql-connector-java-8.0.18*
RUN bin/logstash-plugin install logstash-integration-jdbc
COPY religo_church.conf /usr/share/logstash/pipeline/logstash.conf
COPY logstash.yml /usr/share/logstash/config/
