# brolog

Provide a simple slf4j backend. 

Defaults to JSON output to stdout.

brolog is intended for containers. Logging to 
files or other fancy stuff is not supported. 


## Test
There is a simple human-readable logger that is only intended for unit tests.
Using json output when running tests in the IDE is just not ideal.

Just set the system property `brolog.simple.mode` or the environment variable `BROLOG_SIMPLE_MODE` to true.


## Gradle
```kotlin
implementation("sh.brocode:brolog:$version")
```

## Configuration
Use the environment variable`BROLOG_ROOT_LEVEL` to
configure the root log level.

For logger levels define environment variables with
the prefix `BROLOG_LEVEL_`. 
