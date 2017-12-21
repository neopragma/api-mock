# API Mock

This is a simple mocking facility for testing RESTful APIs. It performs a key-value lookup to find the data to return for RESTful invocations over HTTP(S). The "key" comprises the request method concatenated with pipe concatenated with the request URI. For example:

```shell
request method: GET
request URI: /help/me/rhonda
```

results in a key of:

```shell
GET|/help/me/rhonda
```

## Want to use it, don't want to build it 

Download the contents of the ```dist``` directory.

## Specifying mock data values

The mock data values are specified in a yaml file formatted as illustrated in the following example:

```shell
!com.neopragma.apimock.RequestResponseData
requestHeaders: 
- 'Content-Type: application/json'
- 'Accept-Charset: utf-8'
requestMethod: GET
requestURIPath: /echo/text+to+echo
responseCode: 200
responseBody: text to echo
```

## Running the server

Start the server from a command line:

```shell
java -jar apimock.jar [options]
```

To get usage help, use:

```shell
java -jar apimock.jar --help
```

Usage help as of this version is:

```shell
Usage: java -jar apimock.jar
    -h|help - this usage help
    -d|--data filename
    -l|--loglevel SEVERE|WARNING|INFO|CONFIG|FINE|FINER|FINEST
    -p|--port - server listens on this port
```

Use the ```data``` option to specify the name of a yaml file that contains the mock data you want to use. Default is "./test.yml".

Use the ```loglevel``` option to set the logging level for the server. It uses the standard Java logging facility (```java.util.Logging```). Default is "ALL".

Use the ```port``` option to tell the server which port to listen on. Default is 8000.

## Example

Given the following yaml file:

```shell
!com.neopragma.apimock.RequestResponseData
requestHeaders: 
- 'Content-Type: application/json'
- 'Accept-Charset: utf-8'
requestMethod: GET
requestURIPath: /echo/text+to+echo
responseCode: 200
responseBody: { "result" : { "something": "123", "else": "ABC" }
```

when a client invokes ```GET http://localhost:8000/echo/text+to+echo``` the server will return response code 200 and response body of ```{ "result" : { "something": "123", "else": "ABC" }```.


## Development

#### General requirements

Please provide appropriate microtest cases for any new behaviors you implement. 

#### Building the executable jar

The assembly configuration is declared inside an execution element (by intent). Therefore, when building the executable jar, use:

```shell
mvn package
```

instead of 

```shell
mvn assembly:single
```

#### Warning messages

The classifier is omitted from the artifact final name to simplify usage of the executable jar. Due to the way Maven handles naming, the messages

```shell  
[WARNING] Configuration options: 'appendAssemblyId' is set to false, and 'classifier' is missing.  
[WARNING] Replacing pre-existing project main-artifact file: ...
```

are emitted during the build. As workarounds would not provide any particular value, we have decided to live with the warning message. Warnings can turn into real problems at some point, so we are documenting this decision. See [this explanation](https://issues.apache.org/jira/browse/MASSEMBLY-824) for more information about the naming conflict.






