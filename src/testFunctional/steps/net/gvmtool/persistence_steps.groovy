import static cucumber.api.groovy.EN.And
import static db.MongoHelper.insertBroadcastInDb

And(~'^the message "([^"]*)"$') { String message ->
    insertBroadcastInDb(db, message)
}

And(~'^the message "([^"]*)" on the date "([^"]*)"$') { String message, Date date ->
    insertBroadcastInDb(db, message, date)
}

And(~'^the message "([^"]*)" on the date "([^"]*)" with id "([^"]*)"$') { String message, Date date, int id ->
    insertBroadcastInDb(db, message, date, id)
}