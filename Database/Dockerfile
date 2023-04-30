FROM postgres:latest

ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=qW*RK909
ENV POSTGRES_DB=flavio_db

COPY src/main/resources/sql/init.sql /docker-entrypoint-initdb.d/flavio_db

EXPOSE 5431