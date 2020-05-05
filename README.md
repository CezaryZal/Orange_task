# Meeting Calendar

### Description

The application returns possible appointment dates based on the calendar sent and 
the length of the meeting time.


#### Usage

>Upload calendars along with the length of future meeting.

Use endpoint `**/orange-task/meeting/calculate/{inputDuration}`

Sample calendars in Json format:

```
[
       {
       "working_hours":{
           "start":"10:00",
           "end":"18:30"
           },
           "planned_meeting":[
               {
                   "start":"10:00",
                   "end":"11:30"
               },
               {
                   "start":"12:30",
                   "end":"14:30"
               },
               {
                   "start":"14:30",
                   "end":"15:00"
               },
               {
                   "start":"16:00",
                   "end":"17:00"
               }
             ]
           },
       {
       "working_hours":{
           "start":"09:00",
           "end":"20:00"
           },
           "planned_meeting":[
               {
                   "start":"09:00",
                   "end":"10:30"
               },
               {
                   "start":"12:00",
                   "end":"13:00"
               },
               {
                   "start":"16:00",
                   "end":"18:30"
               }
             ]
       }
   ]
```


## Applied technologies

##### Backend:
- Java - version 1.8.0
- Maven - version 3.5.2
- Spring Boot - version 2.2.0
- REST API
- application container Tomcat - version 9.0.27
- JUnit - version 5.6.2
- assertJ - version 3.14.0
- Swagger UI - version 2.7.0

## Setup

To clone and run this application, you'll need [Git](https://git-scm.com), [Maven](http://maven.apache.org/) and 
[Java SE](https://www.oracle.com/java/technologies/javase-downloads.html)
installed on your computer. From your command line:

#### Running the application

```bash
# Clone this repository
$ git clone https://github.com/CezaryZal/Orange_task

# Go into the repository
$ cd Orange_task

# Run the app
$ mvn spring-boot:run
```

> open the helpful UI Swagger use the path: `**/orange-task/swagger-ui.html`

#### Running the test

To run the *test* via Maven:

```shell
$ mvn test
```

## Authors

* **Cezary Zalewski** - *Backend* - [GitHub profile](https://github.com/CezaryZal)
