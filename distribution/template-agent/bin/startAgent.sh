JAVA_PATH=""
#JAVA_PATH="/usr/sbin/jre1.8.0_77/bin/"

JAVA_OPTS=-Dlogback.configurationFile=logback.xml

${JAVA_PATH}java ${JAVA_OPTS} -cp "../lib/*" step.grid.agent.Agent -config="../conf/AgentConf.json" -gridhost=http://localhost:8081