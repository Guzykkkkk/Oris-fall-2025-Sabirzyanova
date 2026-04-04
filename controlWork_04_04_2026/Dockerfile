FROM tomcat:9-jdk11-openjdk
RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY build/libs/f-mobile-site.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]