# UNDERTOW-2200 Reproducer

[UNDERTOW-2200](https://issues.redhat.com/browse/UNDERTOW-2200) Encoding % in RelativePathAttribute causes wrong rewrites

Tested with JDK 11 and Maven 3.8.6

## Run tests

```bash
mvn verify
```

This will result in the following failure:
```
[ERROR] Failures: 
[ERROR]   PathCheckServletIT.withRedirect:62 expected: </bar/hello%2Fworld> but was: </bar/hello%252Fworld>
```
