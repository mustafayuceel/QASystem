QASystem
========

Question Answer System. Ask Question, Answer, Vote, Point


### Displaying All db names : 

_**http://localhost:7070/QASystem/qa/getAllDbNames**_

_[ "admin", "local", "mydb", "scdb", "tags", "test"]_

***

### Displaying all collection names:

_**http://localhost:7070/QASystem/qa/getAllCollectionNames**_

_[ "people","system.indexes" ]_


***
### Asking Question:

**http://localhost:7070/QASystem/qa/addEvent?author_id=author1&m_type=Q&m_id=question1&event_name=add&message_text=this%20is%20question%20text&actor_id=author1**

{
        "_id" : "author1",
        "authorPoint" : 3,
        "question1" : {
                "messageDetail" : "this is question text",
                "messageEvents" : [
                        {
                                "eventId" : "0",
                                "eventName" : "add",
                                "actorId" : "author1",
                                "detail" : "",
                                "time" : NumberLong("1366376817027")
                        }
                ],
                "messagePoint" : 3,
                "messageType" : "Q"
        }
}

***
### Voting Question:

**detail=up**  or **detail=down**  or **detail=remove**  can be used

**http://localhost:7070/QASystem/qa/addEvent?author_id=author1&m_type=Q&m_id=question1&event_name=vote&detail=up&actor_id=voter1**

{
        "_id" : "author1",
        "authorPoint" : 8,
        "question1" : {
                "messageDetail" : "this is question text",
                "messageEvents" : [
                        {
                                "eventId" : "0",
                                "eventName" : "add",
                                "actorId" : "author1",
                                "detail" : "",
                                "time" : NumberLong("1366376817027")
                        },
                        {
                                "eventId" : "0",
                                "eventName" : "vote",
                                "actorId" : "voter1",
                                "detail" : "up",
                                "time" : NumberLong("1366377218784")
                        }
                ],
                "messagePoint" : 8,
                "messageType" : "Q"
        }
}

***
### Add / remove Question to/from Favorite List of User:

detail=remove can be used

**http://localhost:7070/QASystem/qa/addEvent?author_id=author1&m_type=Q&m_id=question1&event_name=favorit&actor_id=voter1**

{
        "_id" : "author1",
        "authorPoint" : 10,
        "question1" : {
                "messageDetail" : "this is question text",
                "messageEvents" : [
                        {
                                "eventId" : "0",
                                "eventName" : "add",
                                "actorId" : "author1",
                                "detail" : "",
                                "time" : NumberLong("1366376817027")
                        },
                        {
                                "eventId" : "0",
                                "eventName" : "vote",
                                "actorId" : "voter1",
                                "detail" : "up",
                                "time" : NumberLong("1366377218784")
                        },
                        {
                                "eventId" : "0",
                                "eventName" : "favorit",
                                "actorId" : "voter1",
                                "detail" : "",
                                "time" : NumberLong("1366377542001")
                        }
                ],
                "messagePoint" : 10,
                "messageType" : "Q"
        }
}

***
### Answering Question:

**http://localhost:7070/QASystem/qa/addEvent?author_id=answerer1&m_type=A&m_id=answer1&event_name=add&message_text=this%20is%20answer%20text&event_id=question1&actor_id=answerer1**   detail=accepted  can be used

**http://localhost:7070/QASystem/qa/addEvent?author_id=author1&m_type=Q&m_id=question1&event_name=answer&event_id=answer1&actor_id=answerer1**    detail=accepted , detail=remove  can be used...

Second Request is needed. So that we can access easily one questions' answer list without iterating over all answers... Parameters logic is easy, second requests event_id must refer to previous requests m_id

{
        "_id" : "answerer1",
        "answer1" : {
                "messageDetail" : "this is answer text",
                "messageEvents" : [
                        {
                                "eventId" : "question1",
                                "eventName" : "add",
                                "actorId" : "answerer1",
                                "detail" : "",
                                "time" : NumberLong("1366379382577")
                        }
                ],
                "messagePoint" : 3,
                "messageType" : "A"
        },
        "authorPoint" : 3
}

{
        "_id" : "author1",
        "authorPoint" : 14,
        "question1" : {
                "messageDetail" : "this is question text",
                "messageEvents" : [
                        {
                                "eventId" : "0",
                                "eventName" : "add",
                                "actorId" : "author1",
                                "detail" : "",
                                "time" : NumberLong("1366379023938")
                        },
                        {
                                "eventId" : "0",
                                "eventName" : "vote",
                                "actorId" : "voter1",
                                "detail" : "up",
                                "time" : NumberLong("1366379064792")
                        },
                        {
                                "eventId" : "0",
                                "eventName" : "favorit",
                                "actorId" : "voter1",
                                "detail" : "",
                                "time" : NumberLong("1366379106029")
                        },
                        {
                                "eventId" : "answer1",
                                "eventName" : "answer",
                                "actorId" : "answerer1",
                                "detail" : "",
                                "time" : NumberLong("1366379565005")
                        }
                ],
                "messagePoint" : 14,
                "messageType" : "Q"
        }
}


***
### Voting Answer:

**http://localhost:7070/QASystem/qa/addEvent?author_id=answerer1&m_type=A&m_id=answer1&event_name=vote&detail=up&actor_id=voter1**   detail=down  , detail=remove  can be used

{
        "_id" : "answerer1",
        "answer1" : {
                "messageDetail" : "this is answer text",
                "messageEvents" : [
                        {
                                "eventId" : "question1",
                                "eventName" : "add",
                                "actorId" : "answerer1",
                                "detail" : "",
                                "messageText" : "this is answer text",
                                "time" : NumberLong("1366379382577")
                        },
                        {
                                "eventId" : "0",
                                "eventName" : "vote",
                                "actorId" : "voter1",
                                "detail" : "up",
                                "time" : NumberLong("1366380643967")
                        }
                ],
                "messagePoint" : 9,
                "messageType" : "A"
        },
        "authorPoint" : 9
}

***
### Comment Processes:

**http://localhost:7070/QASystem/qa/addEvent?author_id=answerer1&m_type=C&m_id=comment1&event_name=add&&message_text=this%20is%20comment%20text&event_id=answer1&actor_id=commenter1**

event_name=favorit ,  event_name=favorit&detail=remove  can be used
