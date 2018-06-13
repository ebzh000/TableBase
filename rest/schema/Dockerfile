FROM mysql

MAINTAINER ErikZ

ENV MYSQL_USER=root \
    MYSQL_DATABASE=table_base \
    MYSQL_ROOT_PASSWORD=password

ADD ./tablebase.dump.sql /docker-entrypoint-initdb.d

EXPOSE 3306

CMD mysql -u $MYSQL_USER -p $MYSQL_PASSWORD $MYSQL_DATABASE