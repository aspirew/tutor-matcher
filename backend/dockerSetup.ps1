Get-ChildItem -recurse | 
where {$_.name -eq "gradlew"} | 
foreach { cd $_.DirectoryName; ./gradlew dockerBuild; cd .. }