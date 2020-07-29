This is a Spring-boot aplicattion with a React redux-saga front end.

Instructions:

    in eclipse, add the project as maven project, make a maven clean package, and run with spring boot run the PortalApplicattion.java class.
    in cmd, java -jar Portal-0.0.1-SNAPSHOT (which is in the target directory).

The first time it will take a little long time to make the clean package, because it will install node, npm, and will make an npm install to configure the front-end (you need to do nothing, just let the maven work).

To execute the front-end tests go to the front directory and (src/main/front) and execute "npm run test" it will run the jest tests.

I would have liked to integrate the Jest tests with the maven clean package but i had not more time so i am going to do it in the future.