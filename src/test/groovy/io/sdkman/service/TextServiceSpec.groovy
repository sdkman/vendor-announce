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
package io.sdkman.service

import spock.lang.Specification

import static io.sdkman.service.TextService.*

class TextServiceSpec extends Specification {

    TextService service

    void setup() {
        service = new TextService()
    }

    void "should compose a structured release message line"() {
        given:
        def candidate = "groovy"
        def version = "2.3.0"
        def hashtag = "groovylang"

        when:
        String message = service.composeStructuredMessage(candidate, version, hashtag)

        then:
        message == "Groovy 2.3.0 released on SDKMAN! #groovylang"
    }

    void "should compose a structured release message ignoring a # in the hashtag"() {
        given:
        def hashtag = "#groovylang"

        when:
        String message = service.composeStructuredMessage("", "", hashtag)

        then:
        message.contains " #groovylang"
    }

    void "should compose a structured release message including a # in the hashtag"() {
        given:
        def hashtag = "groovylang"

        when:
        String message = service.composeStructuredMessage("", "", hashtag)

        then:
        message.contains " #groovylang"
    }

    void "should compose a structured release message with default hashtag if not provided"() {
        when:
        String message = service.composeStructuredMessage("groovy", "2.0.4")

        then:
        message.contains " #groovy"
    }
}
