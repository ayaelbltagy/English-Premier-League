#1. English premier league

English premier league is a task required for skills approval.

#2. API

- As mentioned in #6 description, the API allowed to be used : {{url}}/v4/competitions/2021/matches?matchday=
- API can retreive all matches in season 2022-2023 but filter can filter only on the week's level matchday=[1-36]
If we send matchday=1, we receive 10 matches across all days within competition of the first week of league.
- Task required to be filtered on daily basis like today or tomorrow. This is not possible using the API filteration.

#3. Architecture

In previous section, the challenge of matches filteration defined the architicture to be used:
- Depending on sqlLite database[Room] to save matches on application start to update matches results
and save match time to be used later as filteration aspect and favourites view.
About Design pattern :
MVVM -> to separates views from business logic.
Kotlin coroutines -> to simplify code that executes asynchronously.
Retrofit library -> to fetch data from server then convert to json.
Room -> sqlite database to save filter and “favorite” matches.
glide and coil-kt -> for images.

#4. UI

Recycler view and Recycler adapter -> to view each match in one item.
Calendar component -> take start and end date from season return in api.

#5. unit tests

-view model testing
-database testing

#6. Task description

Android Technical Assesment 
The Problem
The goal is to create a sample app that displays information about The English Premier League. The app should have the following screens:
1. Fixtures List:
This screen should display a list of matches retrieved from this JSON endpoint.
Each item should have the teams’ name and the game result or the time of
the game in hh:mm instead (if it’s not played yet).
The list should be sectioned by day.
The first visible section of the list has to be the current day or the next if the
the current day has no fixtures.
Provide a control toggle between showing:
The entire list.
Mark a fixture as “favorite”
iii. A subset of the list, containing only those favorite fixtures only.
You have to generate an Api-key before you start. Check the documentation in case you need more info. The list of favorites should persist across app launches.
This is an open ended project. Choose wisely where you spend your energy. 
Rules:
●  Git repository
Must be a public repository so we can review it 
●  Write production quality code
We don’t want a prototype. Pretend you have to ship this to production, and that you might have to maintain your code in the future.
●  Write tests
Focus on unit tests, particularly those parts of your app that make up the rules and logic.

●  Document it
 Please provide a README file outlining the choices you have made: which architecture and visualization did you choose and why, and any caveats we should be aware of.
●  Keep it simple
Make sure you don’t get hung up on details. Simplicity is key! If it takes you more than one week to build this, you’re probably overdoing it. Moreover, we don’t want to spend hours prying into your code to understand what’s going on.


