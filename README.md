# Tomcat Vault Utility -- JAR only

Using Tomcat IntrospectionUtils to inject hashed password from HashiCrop Vault

## Installation:

Add into ${TOMCAT_HOME}/conf/catalina.properties:
```
org.apache.tomcat.util.digester.PROPERTY_SOURCE=com.ztalk.tomcatvault.TomcatPropertyDecoder
```

Add into ${TOMCAT_HOME}/conf/tomcat-users.xml:

```
<user username="${-U res1=admin|-P res1=secret/user}" password="${-U res1=admin1|-P res1=secret/pass}" roles="manager-gui" />
```

If the ROOT is set in the ${TOMCAT_HOME}/webapps, http://{host_ip}/tomcat-vault/hello is used to test password responded back from your Vault server.

Add vault-java-driver-3.1.0.jar to ${TOMCAT_HOME}/lib

Add commons-cli-1.4.jar to ${TOMCAT_HOME}/lib


## TODO:

Supporting local encrypied password "without" Hashicorp Vault?

~~Supporting multiple resources with different passwords?~~

Add release tag with compiled jar files

~~Add loggings~~

~~Test cases~~

