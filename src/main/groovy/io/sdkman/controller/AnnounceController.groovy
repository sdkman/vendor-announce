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
import io.sdkman.response.Announcement
import io.sdkman.service.TwitterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import static org.springframework.http.HttpStatus.OK
import static org.springframework.web.bind.annotation.RequestMethod.POST

@Controller
class AnnounceController {

    @Autowired
    BroadcastRepository repository

    @Autowired
    TwitterService twitterService

    @RequestMapping(value = "/announce/struct", method = POST)
    @ResponseBody
    ResponseEntity<Announcement> structured(@RequestBody StructuredAnnounceRequest request) {
        def message = "${request.candidate} ${request.version} available on SDKMAN!"
        def urlMessage = request.url ? "$message ${request.url}" : message
        twitterService.update(urlMessage)
        def broadcast = repository.save(new Broadcast(text: urlMessage, date: new Date()))
        new ResponseEntity(new Announcement(status: OK.value(), id: broadcast.id, message: broadcast.text), OK)
    }
}
