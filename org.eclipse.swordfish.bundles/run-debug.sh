#!/bin/bash
cd ./target/bundles
java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000 -jar org.eclipse.osgi_3.5.0.v20090311-1300.jar -console -clean

