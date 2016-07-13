#!/bin/bash

NO_ARTIFACTS=true

usage() {

echo " "
echo "usage is "
echo " <script> -s <maven target path> [-n]"
echo " -n when this options is used, artefacts links will not be added to release notes. use this when artefacts are not at standard location."
exit
}

while getopts "s:hn" flag; do
case "$flag" in
s) MAVEN_TARGET_DIR="$OPTARG" ;;
n) NO_ARTIFACTS=false ;;
h) usage ;;
  esac
done

if test -z $MAVEN_TARGET_DIR
then
  echo "maven target location path is empty."
  usage
fi


VERSION_NO=`cat $MAVEN_TARGET_DIR/target/maven-archiver/pom.properties | grep "version" | cut -d= -f2`
artifactid=`cat $MAVEN_TARGET_DIR/target/maven-archiver/pom.properties | grep "artifactId" | cut -d= -f2`
GITURL=${GIT_URL}

echo "Version NO is: $VERSION_NO"
git checkout $VERSION_NO

#RELEASEURL=${GITURL/://}
#RELEASEURL=${RELEASEURL/git@/https://}
#RELEASEURL=${RELEASEURL/.git//releases/tag/$VERSION_NO}

echo "version found: $VERSION_NO"
echo "APP_REVISON=${VERSION_NO}" > version.properties

#echo "APP_REVISON=${VERSION_NO}" > project_sharing.properties
#echo "RELEASEURL=${RELEASEURL}" >> project_sharing.properties

echo "CHANGES:" > release_notes.txt
echo "=======" >> release_notes.txt

git log $GIT_PREVIOUS_SUCCESSFUL_COMMIT..$GIT_COMMIT --pretty=format:'%s' | grep -v "\[maven-release-plugin\]" | grep -v "Merge pull request" >> release_notes.txt
echo " " >> release_notes.txt
echo "........................." >> release_notes.txt

if $NO_ARTIFACTS
then
echo "Artifacts can be found at: " >> release_notes.txt
echo "........................." >> release_notes.txt

echo "https://bintray.com/csc/opensource/agility.platform-sdk/${VERSION_NO}#files" >> release_notes.txt
#echo "http://dl.bintray.com/asurendrababu/maven/com/cheetahtools/${artifactid}/${VERSION_NO}/${artifactid}-${VERSION_NO}-sources.jar" >> release_notes.txt
#echo "http://dl.bintray.com/asurendrababu/maven/com/cheetahtools/${artifactid}/${VERSION_NO}/${artifactid}-${VERSION_NO}-javadoc.jar" >> release_notes.txt
fi

git tag -f $VERSION_NO -F release_notes.txt
git push -f origin $VERSION_NO
