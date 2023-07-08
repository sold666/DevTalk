# DevTalk <img src="https://github.com/sold666/DevTalk/assets/61206345/0d47c7fc-63d3-4450-9c17-8afdb8f2443b" alt="DevTalk" width="80" height="40">

> A messenger for developers. <br> The project developed on the "Mobile Development" program in VK
> Technopolis <img src="https://github.com/sold666/sold666/assets/61206345/6a238107-fecf-44a2-a956-6bc5ac824d0e" alt="VK" height="30" width="30">

- [Main idea](#Main-Idea)
- [Project requirements](#Project-Requirements)
- [Realization](#Realization)
    - [Localization](#Localization)
    - [Error Handling](#Error-Handling)
    - [Asynchronous Interaction](#Asynchronous-Interaction)
    - [Networking](#Networking)
    - [Storage](#Storage)
- [Technologies](#Technologies)

## Main Idea

**DevTalk** is a messenger for developers that doesn't waste their time. **The main feature** of our
messenger is that users can define their interests using tags in order to see only the chats that
correspond to them in the recommendations, as well as create chats with a specific tag.

## Project Requirements

The project had the following requirements:

- Main development language - Kotlin
- Presence of at least two localizations
- Availability of asynchronous work
- Availability of networking
- Availability of non-volatile storage
- Correct handling of errors and exceptions (including in conditions of network loss)
- Participation in the development of each team member
- Do not use third-party graphics engines (Unity, Unreal, etc.)
- You need to use the standard UI Kit.
- Correct handling of configuration change
- Correct handling of the situation when the application is forced out by the system
- Minimum version (minSdkVersion) API Level 24
- Target version API Level(minSdkVersion) 33
- Compliance with the rules of Material Design

## Realization

### Localization

Our application supports two languages - `Russian and English`.

![Localization screens](https://github.com/sold666/DevTalk/assets/61206345/cc38fa87-3ecb-435e-b6f4-d78535944cce)

### Error Handling

When registering and authenticating a user, the fields are checked for validity. The uniqueness of
the mail is also
checked using a request in Firebase.

![Errors Handling screens](https://github.com/sold666/DevTalk/assets/61206345/90c287b9-5fbc-4b21-b292-394c66116e8d)

### Asynchronous Interaction

Asynchronous interaction in the DevTalk developer chat application is implemented using
the `Observer pattern`. In our
application, Observer monitors the update of the chat list. When a new chat is created, or a user
joins the recommended
one, the observer object notifies all subscribers that changes have occurred.

`SharedPreferences` is used to save the state of selected professions between different app launches
or fragments. This
allows you to restore the previous state of the selected professions if the user restarts the
application, theme, or
returns to this fragment.

![Asynchronous Interaction screens](https://github.com/sold666/DevTalk/assets/61206345/db08f744-f7ee-4cec-8197-e6f87f47ea1f)

### Networking

Network interaction in the application is carried out using `Firebase` and `Retrofit`. Firebase
stores user and chat
data,
and retrofit is used for requests to third-party services (for example: Github and Gitlab) when
adding links to them in
the profile.

![Networking screens](https://github.com/sold666/DevTalk/assets/61206345/60c5385c-9840-401d-abe9-ffa45f73cff4)
![Chat window screens](https://github.com/sold666/DevTalk/assets/61206345/e74e2d89-1d37-410b-8b60-d8123dc11eaf)
![Chats screens](https://github.com/sold666/DevTalk/assets/61206345/93d73895-fb79-4cbf-8394-5b44114115f7)
![Checking for lack of Internet screen](https://github.com/sold666/DevTalk/assets/61206345/fb09ecfa-0157-464e-9d22-44c5863fcd1d)

### Storage

Our application uses non-volatile storage to contain tag icons to display them in profiles, chats,
and so on.

![Icons screens](https://github.com/sold666/DevTalk/assets/61206345/d8d307a3-e80d-4cca-b33b-a95e95834b25)
![Profile screens](https://github.com/sold666/DevTalk/assets/61206345/2c3e1eee-3ca1-4115-bef5-aab0b52636b6)

## Technologies

<p style="text-align:center;">
 <img src="https://github.com/sold666/DevTalk/assets/61206345/27394ae1-fbdb-4282-9766-7c2d81934645" alt="Firebase" width="100">
 <img src="https://github.com/sold666/DevTalk/assets/61206345/d93507e9-6d4f-44d1-b264-bc9ba617af1d" alt="Retrofit" width="70">
 <img src="https://github.com/sold666/DevTalk/assets/61206345/bf603572-6827-4d91-9230-97276293869b" alt="Material design" width="70">
 <img src="https://github.com/sold666/DevTalk/assets/61206345/23715fc9-3ac8-4596-aa82-7d2bde98bd08" alt="Picasso" width="70">
 <img src="https://github.com/sold666/DevTalk/assets/61206345/5308aba9-8173-4bb8-a784-ea8f511fea66" alt="Figma" width="100">
 <img src="https://github.com/sold666/DevTalk/assets/61206345/bbcd876e-cd51-486c-a497-589b20d9c860" alt="Coroutines" width="100">
</p>

- Kotlin
- Material design
- Firebase
- Figma
- Coroutines
- Retrofit
- Android Jetpack's Navigation
- Picasso
