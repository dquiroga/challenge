FROM centos

ARG ENV
ENV JAVA_VERSION 8u31
ENV BUILD_VERSION b13

# Upgrading system
RUN yum -y upgrade
RUN yum -y install wget
#RUN yum -y install htop
RUN yum -y install mc

# Downloading & Config Java 8
RUN wget "https://s3.amazonaws.com/amplify-general/docker/jre-8u131-linux-x64.rpm" -O /tmp/jdk-8-linux-x64.rpm
RUN yum -y install /tmp/jdk-8-linux-x64.rpm
RUN alternatives --install /usr/bin/java jar /usr/java/latest/bin/java 200000
RUN alternatives --install /usr/bin/javaws javaws /usr/java/latest/bin/javaws 200000
RUN alternatives --install /usr/bin/javac javac /usr/java/latest/bin/javac 200000

EXPOSE 8080

VOLUME /tmp
ADD reviewsApi.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]