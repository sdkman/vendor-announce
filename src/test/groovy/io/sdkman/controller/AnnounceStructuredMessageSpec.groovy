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
package io.sdkman.controller

import io.sdkman.domain.Broadcast
import io.sdkman.repo.BroadcastRepository
import io.sdkman.request.StructuredAnnounceRequest
import io.sdkman.service.TwitterService
import org.springframework.http.HttpStatus
import spock.lang.Specification

class AnnounceStructuredMessageSpec extends Specification {

    AnnounceController controller
    BroadcastRepository repository = Mock()
    TwitterService twitterService = Mock()

    void setup(){
        controller = new AnnounceController(
                repository: repository,
                twitterService: twitterService)
    }

    void "announce structured should save a structured broadcast message"() {
        given:
        def candidate = "groovy"
        def version = "2.3.0"
        def request = new StructuredAnnounceRequest(candidate: candidate, version: version)
        def structuredMessage = "groovy 2.3.0 available on SDKMAN!"

        when:
        controller.structured(request)

        then:
        1 * repository.save({it.text == structuredMessage}) >> new Broadcast(id: "1234")
    }

    void "announce structured should respond with a broadcast id after saving"() {
        given:
        def candidate = "groovy"
        def version = "2.3.0"
        def request = new StructuredAnnounceRequest(candidate: candidate, version: version)

        and:
        def broadcastId = "1234"
        repository.save(_ as Broadcast) >> new Broadcast(id: broadcastId, text: "some message")

        when:
        def response = controller.structured(request)

        then:
        response.statusCode == HttpStatus.OK
        response.body.id == broadcastId
    }

    void "announce structured should post a structured message to twitter without url"() {
        given:
        def request = new StructuredAnnounceRequest(candidate: "groovy", version: "2.4.0")
        def status = "groovy 2.4.0 available on SDKMAN!"
        def broadcast = new Broadcast(id: "1234", text: status, date: new Date())

        and:
        repository.save(_) >> broadcast

        when:
        controller.structured(request)

        then:
        1 * twitterService.update(status)
    }

    void "announce structured should post a structured message to twitter with url"() {
        given:
        def url = "t.co/qwerty"
        def request = new StructuredAnnounceRequest(candidate: "groovy", version: "2.4.0", url: url)
        def message = "groovy 2.4.0 available on SDKMAN!"
        def status = "$message $url"
        def broadcast = new Broadcast(id: "1234", text: message, date: new Date())

        and:
        repository.save(_) >> broadcast

        when:
        controller.structured(request)

        then:
        1 * twitterService.update(status)
    }
}
