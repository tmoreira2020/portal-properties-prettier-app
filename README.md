Portal Properties Prettier App
==========
[![Build Status](https://travis-ci.org/tmoreira2020/portal-properties-prettier-app.svg?branch=master)](https://travis-ci.org/tmoreira2020/portal-properties-prettier-app)
[![Coverage Status](https://coveralls.io/repos/tmoreira2020/portal-properties-prettier-app/badge.svg?branch=master)](https://coveralls.io/r/tmoreira2020/portal-properties-prettier-app?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/br.com.thiagomoreira.liferay.plugins.portal-properties-prettier-app/portal-properties-prettier-app/badge.svg)](https://maven-badges.herokuapp.com/maven-central/br.com.thiagomoreira.liferay.plugins.portal-properties-prettier-app/portal-properties-prettier-app)

Turns a messy Liferay portal-\*.properties into a clean and well organised portal-\*.properties. Basically it does 6 things:

* Identify, group and comment properties that has the same values as the original portal.properties located at portal-impl.jar
* Identify, group and comment properties that are obsolete for the given Liferay version
* Identify, group and recommend a fix for properties that has typos in its key's declaration
* Group custom properties in a section in the beginning of the file
* Sort the properties in the same order as the original portal.properties
* Add the original comments for each property as definied in the portal.properties file

### Online version

 
You can test it on [http://portalproperti.es](http://portalproperti.es)

### Command line version

You can also call the service from command line using [curl](http://curl.haxx.se/). If you don't provide a Liferay version it will use the default that is 6.2.3-ga4.

```shell
curl http://portalproperti.es/prettify -F portalPropertiesFile=@/path/to/portal-ext.properties
```

If you need to use a different Liferay version, for instance 6.1.0-ga1, you must use the following format

```shell
curl http://portalproperti.es/prettify/6.1.0-ga1 -F portalPropertiesFile=@/path/to/portal-ext.properties
```

Or if you need to print the original value of each customized property you must call the service this way

```shell
curl http://portalproperti.es/prettify/6.2.3-g4/true -F portalPropertiesFile=@/path/to/portal-ext.properties
```

### License

Portal Properties Prettier App is licensed under [Apache 2](http://www.apache.org/licenses/LICENSE-2.0) license.

### Maven/Gradle

Portal Properties Prettier App is available on Maven central, the artifact is as follows:

Maven:

```xml
<dependency>
    <groupId>br.com.thiagomoreira.liferay.plugins.portal-properties-prettier-app</groupId>
    <artifactId>portal-properties-prettier-app</artifactId>
    <version>1.3.0</version>
    <type>lpkg</type>
</dependency>
```
Gradle:

```groovy
dependencies {
    compile(group: "br.com.thiagomoreira.liferay.plugins.portal-properties-prettier-app", name: "portal-properties-prettier-app", version: "1.3.0", type: "lpkg");
}
```
### Support
Portal Properties Prettier App tracks [bugs and feature requests](https://github.com/tmoreira2020/portal-properties-prettier-app/issues) with Github's issue system. Feel free to open your [new ticket](https://github.com/tmoreira2020/portal-properties-prettier-app/issues/new)!

### Contributing

Portal Properties Prettier App is a project based on Maven to improve it you just need to fork the repository, clone it and from the command line invoke

```shell
mvn package
```
After complete your work you can send a pull request to incorporate the modifications.

Enjoy!
