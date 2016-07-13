function finish {
  taskkill /F /IM "ssh-agent.exe"
}

taskkill /F /IM "ssh-agent.exe"
/cygdrive/c/scripts/startsshagent.cmd
subst Z: C:\\Shared

#cd /cygdrive/c/Shared/Git/agility
rm -rf /cygdrive/c/Shared/Git/agility/*.*

cd  /cygdrive/c/Shared/Git/techpubs
git reset --hard HEAD^
git clean -f -d
git pull
git checkout $TECHDOCS_BRANCH
git pull

for proj in $(echo $PROJECT_LIST | sed 's/,/ /g') ; do
  echo $proj
  if [[ -n $(git log -n 1 --format=oneline --since="$SINCE_X_DAYS_AGO days ago" AgilityAPI) ]]; then
    echo "Recent changes found.  Initiating madbuild"
    madbuild -project Z:/Git/techpubs/$proj/$proj.flprj -batch AutoBuilds -log true
  else
    echo "Project Skipped, no changes were commited within $SINCE_X_DAYS_AGO days"
  fi
done

ant clean ivy-publish 

trap finish EXIT
echo "Doc Automation Complete"
