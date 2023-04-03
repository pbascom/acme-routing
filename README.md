# acme-routing
## About:
Acme Routing is an interview project completed for Platform Science. It's a simple Android application using MVVM with Dependency Injection and a Repository pattern, as recommended by the standard Google [Guide to App Architecture](https://developer.android.com/topic/architecture).

Acme Routing is written in Kotlin, using Jetpack Compose for the UI. 

## Contents
The Application contains two Activities, representing a parent or Launcher Activity, and a Shipments Activity. The Activities are designed to mirror architectural boundaries between modules, which are themselves designed to mirror team boundaries in the organization.

It also contains a Repository interface with a single implementation, several Composable UI files, and a ``DriverMatchingUtility`` which holds the business logic described by the exercise prompt. Several unit tests for the Matching Utility are also included.

## Build Instructions
To build Acme Routing, start by [installing Android Studio](https://developer.android.com/studio/install) and [cloning the repository](https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository).

Once you've cloned the repository onto your development machine, open it in Android Studio. At this point, there are several ways you could build and test the application.

### With an Emulator
1. If you don't already have an Android Emulator installed on your machine, [create an AVD](https://developer.android.com/studio/run/managing-avds#createavd). Make sure you choose a release with an API Level of at least 24.
2. Once your emulator is installed, select it from the build target dropdown:
<img width="413" alt="Screen Shot 2023-04-03 at 3 52 35 PM" src="https://user-images.githubusercontent.com/8560089/229615540-8f877872-b486-43aa-a624-230fd075302d.png">

3. Make sure 'app' is selected from the Run/Debug dropdown. It should look like this:
<img width="273" alt="Screen Shot 2023-04-03 at 3 54 38 PM" src="https://user-images.githubusercontent.com/8560089/229615572-ae9e5e6c-3ac0-47ae-bfe7-a18a6eb093ee.png">

4. Finally, click the green "Run" arrow.
5. In a few moments, your emulator should start and the app should install and run.
6. Enjoy!

### With a Physical Device
1. Using a physical device is only slightly more complicated. First, you'll need to [enable Developer Options](https://developer.android.com/studio/debug/dev-options) on the device.
2. Next, you should connect your device to your development machine using a physical cable. A dialogue should pop up on your device asking you to enable USB debugging; click "accept."
3. In Android Studio, you should see your physical device in the build target dropdown:
<img width="413" alt="Screen Shot 2023-04-03 at 3 52 35 PM" src="https://user-images.githubusercontent.com/8560089/229615540-8f877872-b486-43aa-a624-230fd075302d.png">

4. Make sure your device is connected, and make sure that 'app' is selected from the Run/Debug dropdown. It should look like this:
<img width="273" alt="Screen Shot 2023-04-03 at 3 54 38 PM" src="https://user-images.githubusercontent.com/8560089/229615572-ae9e5e6c-3ac0-47ae-bfe7-a18a6eb093ee.png">

5. Click the green "Run" arrow.
6. In a few moments, the app should install and launch on your device. Enjoy!

## Notes on Style
I chose to write comments throughout the Acme Routing codebase, with an eye to explaining my decisions and thought process to interviewers. That means these specifically _aren't_ the kinds of comments I would leave in production code. While I do enjoy a good joke from time to time, the priority in production code is clarity of purpose and convenience of debugging.

I also tend to write the most self-documenting code I can; usually, my production code will have kdoc comments as necessary, and that's about it. Ironically, I think the explanatory comments in this application made the code itself slightly less readable. I hope the insight into my thought process makes up for that gap.

The architecture itself is highly opinionated; where I think a choice may be controversial, I explained why I made it through comments. I personally think I made good architectural decisions, but in a team environment I value cohesion over perfection. I'd rather have everyone on the same sub-optimal page than competing for their own different vision of perfection.

With that said, I had fun with this one. I also almost threw in a Composer pattern rather than using top-level ``@Composable`` functions, but decided not to get _too_ spicy.

I didn't have enough time to add all of the features to this application which I would have liked to. For example, I didn't set up Dagger; I'm using constructor injection in the few classes which need it, and I've annotated the future injection sites with TODOs. There's also a significant amount of code reuse in the Compose files; ideally, we would have a more robust theme with a ``dimens`` class to avoid hard-coding padding dimensions, as well as a library of shared components. Each of the three screen UIs in this app could easily be an instance of a single Composable function, with shared ``Scaffold`` and ``TopAppBar`` settings.

While there are unit tests in this application, additional tests for the Repository, Viewmodel, and Composables would be desirable.
