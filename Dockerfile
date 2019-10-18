FROM java:8
 
ADD ./target/geocode-0.1.jar /geocode/geocode-0.1.jar
CMD ["java", "-Xmx200m", "-jar", "/geocode/geocode-0.1.jar"]

EXPOSE 8090