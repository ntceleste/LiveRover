# LiveRover
A simple native Android app to pull photos from [NASA's Mars Rover Photo API](https://github.com/chrisccerami/mars-photo-api) and display them alongside photo details.
# How to use
The launch screen provides you inputs to select a Rover and select a date. Upon selecting either the app will load in a list of possible photos to look at. You can tap any id to be brought to a detail view of the photo alongside it's NASA metadata.

If you select a date/rover combo with no photos... You'll get a little surprise :) 

# Design Decisions

## Why the NASA API?

I wanted a simple reliable API for this project. The NASA API seemed like it would allow me to easily access photos and photo data. I wanted to focus less on getting the API to work, and more on building out LiveRover

## What 3rd Party Libraries did I use? Why?

 - Retrofit2
	 - I needed an easy way to make a GET request to the NASA API. I'd never used Retrofit before, but it seemed like a simple library, and it allowed me to place my controller logic in a separate class.
 - Glide
	 - I needed a way to load the images in from the URL's the NASA API provided. Again, I hadn't used Glide before, but it seemed like a popular option online. Made it very easy to load in the images from the NASA API. I did run into issues trying to load in a locally stored image (for my default/error cases). I ended up just setting the `ImageView` to the local file, and then reseting it with Glide when it loaded in an image, but this doesn't seem ideal.
 - Google-Secrets-Plugin
	 - I wanted an easy way to obscure the API key from my repo and from the app (until build time). Google reccommended their solution.

## Was I trying to achieve a certain design pattern/architecture? 

At the start, Yes. I wanted to keep my app close to an MVC pattern, so I split the Data Models into their own classes, and let my `RoverService` (my retrofit implementation) exist as it's own interface. However, as I kept working, I saw my Activity classes getting bloated and doing more than a view should be doing. As I was doing more reading, I think a MVP pattern definitely makes more sense for an app like this, but I didn't really know much about MVP. I'd love to chat more about better ways to structure this project into something that could be easily expanded and extended.

## Testing?

Yes! This was my first time doing Espresso tests, so that was definitely a learning curve. I tried to cover all the ideal flows of the app, but I would love to hear more about how I could write tests that might also expose flaws in the user flows. 

 - `Thread.Sleep()` 😱
	 - This appears in my `tappingIdLaunchesDetailView` test for the Main Activity. I wanted to test that when an item from the `RecyclerView` is clicked that it would launch my Detail Activity. This test worked sometimes, but other times it wouldn't. I realized that it was because the test was running faster than the new view could be loaded in. I did some research and it seems like maybe an idling resource would make sense here, but I wasn't in love with adding something to the actual activity classes to make the test work consistently. Does an idling resource make sense here? How would you approach making this view transition testable?
- Tests for the Model classes
	- You'll notice there are not tests for my Data Classes. These are all simple Data Classes that implement `@Parcelize`. Because they are final classes, mocking them seemed to be a tough ask, and it seemed like common practice is to not test simple models like this. Does that make sense? How would you approach testing these models?  

## Styling?

My styling is pretty minimum for this app, using a lot of the default material views. I haven't had much experience in designing an Android UI, and so I wanted to focus on making the rest of the app as strong as it could be. If I were to keep working on this App, I would definitely consult with others to see how I could make this a more user-friendly, fun to look at experience.

# Misc Questions for the team
- Activities vs Fragments
	- When deciding how to show the photo details, I was stuck between if it should be a fragment or a separate activity. I haven't really worked with fragments before, and so I stuck with what I knew. Would it make sense for the detail screen to be a fragment? I'd love to hear more about what that could look like.
- Spinner as a action?
	- Currently when you select a Rover name from the Spinner it acts as a trigger to load in photos. I don't think seems as clear as I would like it to be, and might be confusing for a user. Do spinners normally trigger actions? Or is this a pattern that should be avoided?
- Design Pattern?
	- I asked about this above, but wanted to bring it up again here. As I working I felt my activity classes getting bloated. What steps would you take to avoid this? How would you think about designing a simple 2-screen app like this?
	
# Next Steps?
If I had more time, what would I do?
## Style!
I think overall my App seems very basic Android app, I would love to spend some time giving it a more thought out feel. Also I would like to do things like add iconography etc to make it feel like a finished project.

## Refactor!
This is my 3rd time saying it, but I do feel like my activity classes got bloated. I would like to refactor some of the helper methods out into their own presenter classes.

## Make the list more interesting!
Right now the list of photos is pretty bland. It's just text that lists the photo's ID and the name of the camera that took the photo. I would like maybe add a smaller preview of the picture into the `RecyclerView` items, but that seemed out of scope for me giving the timeline of this project.