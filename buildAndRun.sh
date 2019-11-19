#!/bin/bash
mvn clean package && docker build -t anmiralles/aws-instantiator .
docker rm -f aws-instantiator || true && docker run -d -p 8080:8080 -p 4848:4848 --name aws-instantiator anmiralles/aws-instantiator
