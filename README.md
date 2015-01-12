Telecoms III Assignment
=======================

This is the github repo for 'Three Friends Doing Telecoms'


Getting Started YOLO


===============
	
Note: https://windows.github.com/ - link to download Windows app. Alternative to below.

1) First install git.(http://git-scm.com/downloads)

2) Open your command prompt or Terminal and move to where you want to store the project.

3) Type `git init`

4) Type `git remote add origin git@github.com:paterson/Telecoms-III.git`

5) Type `git pull origin master`. This will pull the latest code down to your current directory

6) Type `git status` to view currently changed files that haven't been commited.

7) Type `git log` to view a list of commits.


Very Important
===============

* Do not push up the whole eclipse project!
* Indent properly!
* 4 spaces not tabs for indention. Be consistent!
* No whitespace at end of lines.
* Comment where needed.
* In general, follow this style guide: http://www.oracle.com/technetwork/java/codeconventions-150003.pdf
* Add new files, commit, then push.
* Always pull before starting work
* If you hit a 'merge conflict' stop and let me know.
* There are programs to manage git. Use them if you wish (I prefer the command versions)
* If you are unsure about anything at all, ask.
* Don't blindy google and try the first result for anything git related - git is powerful, but can lead to massive damage if you are not careful :) Best only using things on this page. Again, if you get stuck, just ask.


Adding a new file you just created
===================================

`git add folder/file_name.java`

Stop Tracking a file/Remove from github
=======================================

`git rm folder/file_name.java`

Commiting changed files
========================

`git commit -m "description of what you did here"`

Pushing to github
===================

`git push origin master`

Remove all local changes (i.e rollback to version on github)
=============================================================

`git checkout .`
 
