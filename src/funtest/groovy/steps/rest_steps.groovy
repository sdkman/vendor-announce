/*
 * Copyright 2014 Marco Vermeulen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package steps

import wslite.rest.RESTClientException
import static cucumber.api.groovy.EN.And
import static utils.HttpHelper.*

And(~'^an "([^"]*)" status is returned$') { String status ->
    assert status == statusCodes[statusCode]
}

And(~'^the structured message is announced$') { ->
    def params = [candidate: candidate, version: version]
    def allParams = url ? params << [url: url] : params
    http{
        post(path: "/announce/struct") {
            type "application/json"
            json allParams
        }
    }
}

And(~'^the content type is "([^"]*)"$') { String contentType ->
    assert headers['Content-Type'].contains(contentType)
}

And(~'^a valid Broadcast Identifier is returned$') { ->
    broadcastId = json(response).id
}

And(~/^the application should report a name of "(.*)"$/) { String name ->
    assert name == json(response).app.name
}

And(~/^an HTTP GET on the "([^"]*)" endpoint$/) { String endpoint ->
    http { get(path: endpoint) }
}

And(~/^an HTTP HEAD on the "([^"]*)" endpoint$/) { String endpoint ->
    http { head(path: endpoint) }
}

private http(restAction) {
    try {
        httpResponse = restAction()
        response = httpResponse.contentAsString
        statusCode = httpResponse.statusCode
        headers = httpResponse.headers

    } catch (RESTClientException re) {
        httpResponse = re.response
        response = httpResponse.statusMessage
        statusCode = httpResponse.statusCode
    }
}

private json(response) {
    slurper.parseText(response)
}