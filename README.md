# Yeltsin
A java program that pairs with VLC media player to mute or skip unwanted content in a video.

## Introduction

  Yeltsin is the brain child of an interstellar team. Its inception originated from the distinguished Johnny - the head gizmo of the operation. He envisioned a program that
would free the world from having to throw trash into its humans' minds through the malicious content of movies - particularly profane language and repulsive visuals. 
Thus, the Yeltsin program was begun. Not yet complete but presently functional with the proper operational knowledge (currently held by its top developers), Yeltsin 
offers the opportunity, with the concurrent use of VLC media player, to view family unfriendly movies as movies that any child, gentleman, or lady would have no remorse watching due to any language or visuals held therein. Ladies and gentlemen: introducing the one and only Yeltsin project - the program that frees those who object to bad movie content.

   Specifically, Yeltin automatically (not quite automatically, in that it does not detect bad content before it comes; you must have a file that stores data about when
to mute or skip repugnant content) mutes and/or skips sound and video segments being played in the VLC video player with a little preparation on the part of the viewer.
A .csv file for every movie in your DVD library must be made that contains the times and action to take at those times (either mute or skip) in a specific format. Luckily
for the more ignorant and untrained user, the benevolent Andrewskev has added functions to the GUI that allow the user to easily create the file or edit the file if it 
already exists but has errors (see User Instructions).

## Software Requirements

This program uses Java Applet, which is now deprecated with the latest version of JDK. For applets to work, an older JDK Version is required. We know that JDK 8 supports Applet. To develop the code, we are using Eclipse IDE. One developer uses Eclipse Luna with success.

## User Instructions

### Preliminaries
  For the program to properly connect to VLC, the file address to VLC must be correct in the CommandPrompt class. Line 12 of CommandPrompt.java is 		
  
  "cd \"C:\\Program Files (x86)\\VideoLAN\\VLC\" && vlc -I qt --extraintf rc --rc-host localhost:50000");   
  
  The path name in line 12 must lead to where the VLC file is installed on your system. It may be in C:\Program Files\VideoLAN\, however this depends on where the user install VLC. Also, the "  \"   "s that surround the file address in line 12 may or may not be needed. Try what works for your system.
  
  
  
 An issue that we have had and need to work on is that Time Requests from VLC do not work well. If this problem is too frequent, then correct portions may not be muted and skipped.
 
 ### Operation
  To use this  program, simply run the program. The program will open the VLC media player by itself (perhaps after an unexplained delay). In the Yeltsin movie
menu, select the movie that you want to watch with this purging Yeltsin program. When the VLC player opens, open your DVD with the VLC player. If the movie file has been
prepared already, then simply sit back, eat your popcorn, and enjoy untill you fall asleep to a relevated movie. However, chances are that the movie file has not been
made unless you have already been generous enough to filter through your movie beforehand. Thanks to the diligent Andrewskev, creating the movie file is simple. Simply
touch the "Create New Movie File" button. When prompted, enter the name of the movie that you are adding and press "Enter". To add a skipping or muting action, click "Add
Action" in the new frame that has appeared. In the chart, fill in the correct areas with the time that you want to begin muting sound or skipping video, the time that you
want to end muting or skipping, and whether you want the entered time frame to be muted or skipped (by typing the word "mute" or "skip", respectively). You can use the 
Get Current Time button to play the movie and find the times of parts you want to begin and end skiping or muting. Note: the times are all in seconds, not hours:minutes:
seconds. When you are happy, you can save and exit the file. Newly created files will now appear at the top of the movie menu until you run Yeltsin the next time - then
they will be in alphabetical order. If you are unhappy with an existing movie file, you can press the "Edit Movie File" button on the main Yeltsin frame after selecting
the movie you want to edit in the movie menu. Then, edit it like you created it.

## Final Notes
I do not think that the intelligent Andrewskev made the Readme file do as it should. But it was a good shot!

Eventually, we will hopefully have a nicer Readme file. Come back then!
