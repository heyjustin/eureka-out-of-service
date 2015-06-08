Example code to reproduce the issue [reported here](https://github.com/spring-cloud/spring-cloud-netflix/issues/377)

To get this example running

- Start a eureka server on localhost:8761 (i.e. out of the box configuration)
- Build the app: `mvn clean install`
- Run the application:  `java -jar eureka-out-of-service-1.0-SNAPSHOT.jar`
- Pause the application: `curl -X POST http://localhost:8080/pause`
- Check the Health endpoint: [http://localhost:8080/health](http://localhost:8080/health).  It should report the instance as `OUT_OF_SERVICE`

When the eureka clients re-registers, a health check is run.  The `ApplicationContextStateHealthCheckHandler` will consult the 
`ApplicationContextStateHealthIndicator` and report the status.  You should  

```
2015-06-08 11:20:32.591 DEBUG 8944 --- [scoveryClient-1] pplicationContextStateHealthCheckHandler : Mapped status from: Status.OUT_OF_SERVICE to: InstanceStatus.OUT_OF_SERVICE
```

To Resume the application: `curl -X POST http://localhost:8080/resume`

