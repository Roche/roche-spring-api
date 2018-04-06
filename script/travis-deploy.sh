#!/bin/sh
# vim: set ts=4:
set -e

cd "$(dirname "$0")/.."

if [ -z "$BINTRAY_KEY" ]; then
	echo '$BINTRAY_KEY is not set, skipping deploy.'; exit 0
fi

echo '==> Copying Maven deployment settings'
cp .maven-bintray.xml $HOME/.m2/settings.xml

echo '==> Deploying artifact to Bintray and OSS Maven repositories'
mvn deploy -Dgpg.skip=true -DskipTests=true