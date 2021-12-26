FROM java:8
EXPOSE 8080
ADD /target/employees-1.0.war employees-1.0.war
ENTRYPOINT ["java","-jar","employees-1.0.war"]