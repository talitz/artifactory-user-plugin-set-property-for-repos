# Setting Property Values For AllRepositories

This plugin is used to set artifacts with a specific property with the specified value.

Execution
---------

curl -X POST -v -u admin:password "http://localhost:8081/artifactory/api/plugins/execute/deleteByPropertyValue?params=propertyName=test;propertyValue=2;repo=libs-release-local"


Parameters
----------

- `propertyName`: The property name which you want to search for
- `propertyValue`: The value of the property
- `repos`: The repository from which you want to set artifacts with this property
- `dryRun`: If set to *true* the artifacts to delete will be logged but not deleted. The parameter is optional. Default: *false*.

To ensure logging for this plugin, edit ${ARTIFACTORY_HOME}/etc/logback.xml to add:
```xml
    <logger name="deleteByPropertyValue">
        <level value="info"/>
    </logger>
```


Further Work
----------

- Complete the tests class.
