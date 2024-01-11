FROM sapmachine:21-jre-headless-ubuntu-22.04

LABEL description="Image for demo application"
LABEL maintainer=latusikl@gmail.com

ARG USER="latusikl"
ARG APP_DIR="/opt/demo-app"
ARG JAR_NAME="demo-app.jar"

#Create system group called $USER then add new user that do not have password (cannot login),
#has empty GECOS field (name, surname etc.), has no home direcotry, has no login shell and is assigned to created group.
#Creat directory for application
RUN     addgroup --system $USER \
        && adduser --disabled-password  \
                --system \
                --gecos "" \
                --no-create-home  \
                --shell /usr/sbin/nologin  \
                --ingroup $USER $USER \
                && mkdir $APP_DIR
USER $USER
WORKDIR $APP_DIR
COPY build/libs/$JAR_NAME $APP_DIR

CMD ["java","-jar","/opt/demo-app/demo-app.jar"]