Portal Properties Prettier App
==========
[![Build Status](https://travis-ci.org/tmoreira2020/portal-properties-prettier-app.svg?branch=master)](https://travis-ci.org/tmoreira2020/portal-properties-prettier-app)
[![Coverage Status](https://coveralls.io/repos/tmoreira2020/portal-properties-prettier-app/badge.png?branch=master)](https://coveralls.io/r/tmoreira2020/portal-properties-prettier-app?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/br.com.thiagomoreira.liferay.plugins.portal-properties-prettier-app/portal-properties-prettier-app/badge.svg)](https://maven-badges.herokuapp.com/maven-central/br.com.thiagomoreira.liferay.plugins.portal-properties-prettier-app/portal-properties-prettier-app)

Turns a messy Liferay portal-\*.properties into a clean and well organised portal-\*.properties. Basically it does 4 things:

* Removes properties that has the same values as the original portal.properties located at portal-impl.jar
* Sort the properties in the same order as the original portal.properties
* Add the comments for each property as definied in the original portal.properties
* Group the custom properties in the beginning of the file

### Online version

You can test it on http://portalproperti.es
 
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
