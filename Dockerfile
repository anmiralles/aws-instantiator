FROM payara/server-full:5.191

ENV JVM_ARGS="-Xmx512m -Xms256m"

COPY target/aws-instantiator.war $DEPLOY_DIR
