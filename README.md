# Pubber
**Find your favourite pubs in your nearby location, check ratings, drinks and more!!!**<br>
At this moment moment the app is in early stage of development. It's addressed for Android users who love beer and pubs.

## Visualisation

Watch the app in action: [Demo Video](docs/videos/demo.mp4)

See key app features in: [Screenshots](docs/Screenshots.md) and in the [GIFs](#gifs) section.
## Features
The app has multiple features, some of which are still in development
- Logging and creating user account
- Filtering pubs by drink, beer styles, prices, distance, opening hours
- A list of pubs, where for each one you can:
  - Get Google Maps Direction and distance from your place
  - Contact with place
  - Post comments and rate it
  - Observe ratings
  - **See the beers on offer**
  - View photos of a pub
  - Checks details about pub e.g. opening hours, address
- Light and dark theme
- Beers dictionary
- View map of pubs in nearby (planned for the future)
- Add pubs to favourites
- Support for two languages : Polish and English (Ukrainian in future)
## Architecture and UI
The Pubber app architecture follows [Android guidelines](https://developer.android.com/topic/architecture). The App was designed using [Material Design](https://m3.material.io/).
## Tools and technologies
- Programming Language: Java
- Reactive Programming: RxJava
- Networking: Retrofit
- Local Storage: Room, DataStore
- UI Framework: Jetpack Compose (planned for the future)
- Google Services: Firebase Authentication, Google Maps API
## Related services
In parallel to support network functionalities of the app services are developed. See [Pubber REST API](https://github.com/Sewery/pubber-rest-api) and [Pubber Services](https://github.com/Sewery/pubber-services) for more informations.
## About
### What is the idea's origin?
The idea behind Pubber is quite simple. My friend and I wanted to create a app that allows both you and us to quickly and easily choose a pub. Personally, I enjoy pub crawling once in while, but I always struggle to decide which pub to visit based on Google Maps. This inspired us to develop Pubber, and now here we are!
### Who are we?
The entire codebase, from A to Z, has been developed by two students—one from PW and another from AGH.
### What are the future plans for the app's development?
We will keep this section regularly updated, so make sure to check it out once in a while!\
<a name="gifs"></a>
## GIFs
**First open and sign in**

<img src="https://github.com/OverMighties/pubber/blob/develop%232/docs/first_open.gif" width="316" height="720" />


**Search and filtration**

<img src="https://github.com/OverMighties/pubber/blob/develop%232/docs/search_filtration.gif" width="316" height="720" />


**Pub details page**

<img src="https://github.com/OverMighties/pubber/blob/develop%232/docs/pub_details.gif" width="316" height="720" />


**Light and dark theme**

<img src="https://github.com/OverMighties/pubber/blob/develop%232/docs/light_to_dark_theme_change.gif" width="316" height="720" />


**Settings**

<img src="https://github.com/OverMighties/pubber/blob/develop%232/docs/options_changing.gif" width="316" height="720" />


## Contributors
+ [Sewery](https://github.com/Sewery)
+ [RudyKarpus](https://github.com/RudyKarpus)
## License
Pubber is distributed under the terms of the GNU General Public License v3.0. See the [license](LICENSE) for more information.
