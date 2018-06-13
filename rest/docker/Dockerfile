FROM openjdk:8

COPY ./bin/* /opt/tablebase/bin/
COPY ./config/* /opt/tablebase/config/
COPY ./libs/*  /opt/tablebase/libs/

RUN ln --symbol /usr/bin/java /usr/local/java1.8 \
    && mkdir -p /opt/tablebase/logs \
    && chmod 744 /opt/tablebase/bin/*

ENTRYPOINT ["/opt/tablebase/bin/startup"]
CMD ["", "", ""]
