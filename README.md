# b2-cdi

Copyright © 2012-, base2Service Pty Ptd. Licensed under ASL 2.0 License.

## About
A number of CDI extensions to make the life of any JEE developer easier.

## Developer Notes

### GitHub Setup
1. Fork Repo into your base2 github account
2. Run on your local machine:

		git clone git@github.com:<github-username>/b2-cdi.git
		cd b2-cdi
		git remote add upstream git@github.com:base2Services/b2-cdi.git

You can now pull from the upstream repository to rebase your code from main repo

	git pull upstream master
	
And push now push these changes to your forked repository

	git push
	
Also you should run the following

    git config --global user.name "Your Name"
    git config --global user.email <your_email>@yourdomain.com

### Building
This project using maven 3+ to build the lastest version execute:

	mvn clean install

To execute the integration test run:

	mvn clean install -Pintegration-tests

