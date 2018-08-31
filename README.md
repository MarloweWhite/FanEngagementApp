# FanEngagementApp

To the next person that inherits this app there will be a few changes that will need to be made to achieve full functionality.

Firebase:
You will need to create project. This should be simple as all you will need to do is follow the instructions they provide you with.
You will however need to change the links and references in the code for example where it says  
"storageRef = storage.getReferenceFromUrl("gs://roar-29883.appspot.com/Display Pics" );"
you will need to put in place your own link for your project. If you also want to change the Childs in the project etc then you can 
do that by simply changing the strings in the project e.g in ActivityRegistration
"DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("RoarSports").child("users").child(user_id);" 
simply change roarsports and users to anything of your choice. The user_id can be changed to something of your choice as well. 
You will also need to enter sign in methods in the console and enable email sign in.

Facebook Login:
This is quite simple. Go to facebook developers, create a new app and under products choose facebook login. 
Then follow the instructions facebook gives you. After go to settings and get you app key and secret. 
Use these and replace them with the ones in the strings file in the ROAR app. After go to the Firebase console sign in 
methods and enable facebook sign in and put the secrets and app id there.

TwitterLogin:
Similair to facebook, search twitter apps for developers. 
(You may have to google how to do twitter login cause twitter aren't very helpful but there's good youtube tutorials).
Make a new app and set privacy policy and terms of service urls to "http://roarsports.net/" . 
Change the project keys and secrets in the roar app to the ones given to you then go to permissions and tick request emails
from users and set access to read and write. Then go to the Firebase console, enble twitter sign in  under sign in methods,
put in keys and secrets, copy the callback at the bottom, go back to twitter app console then paste it in callback urls under settings. 

Google Maps:
Go to google developers console, Create a project and follow the instructions.
PLace your api key in Maps activity line 230 and line 109 in manifest.
