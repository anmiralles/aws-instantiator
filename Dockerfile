FROM airhacks/wildfly
COPY target/aws-instantiator.war ${DEPLOYMENT_DIR}
