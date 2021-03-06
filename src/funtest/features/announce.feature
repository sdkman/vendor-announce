#
#  Copyright 2014 Marco Vermeulen
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
#

Feature: Announce

  Scenario: Announce a new Structured Message without URL
    Given a new message to be announced for "groovy" version "2.3.0"
    When the structured message is announced
    Then an "OK" status is returned
    And the content type is "application/json"
    And a valid Broadcast Identifier is returned
    And the message "groovy 2.3.0 available on SDKMAN!" is available

  Scenario: Announce a new Structured Message with URL
    Given a new message to be announced for "groovy" version "2.3.0" url "https://t.co/qwerty"
    When the structured message is announced
    Then an "OK" status is returned
    And the content type is "application/json"
    And a valid Broadcast Identifier is returned
	And the message "groovy 2.3.0 available on SDKMAN! https://t.co/qwerty" is available
