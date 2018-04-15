FROM openjdk:8-jre
MAINTAINER Jeff Risberg <j.s.risberg@gmail.com>

RUN mkdir -p /opt/jersey03/bin \
             /opt/jersey03/lib \
             /opt/jersey03/www \
             /var/log/

ENV PATH /opt/jersey03/bin:$PATH

WORKDIR /opt/jersey03/bin

COPY build/libs/jersey03-shadow-0.1.0.jar /opt/jersey03/lib/
COPY src/main/resources /opt/jersey03/conf/
COPY bin/start-server /opt/jersey03/bin/

RUN chmod a+x /opt/jersey03/bin/start-server

EXPOSE 8080
ENTRYPOINT start-server