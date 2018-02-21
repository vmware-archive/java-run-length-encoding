FROM projectriff/java-function-invoker:0.0.1
ARG FUNCTION_JAR=/functions/decode-1.0.0.jar
ARG FUNCTION_CLASS=functions.Decode
ADD target/decode-1.0.0.jar $FUNCTION_JAR
ENV FUNCTION_URI file://${FUNCTION_JAR}?handler=${FUNCTION_CLASS}
