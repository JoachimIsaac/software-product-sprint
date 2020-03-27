// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    // Holds all the possible time ranges for the query.
    ArrayList<TimeRange> availableTimes = new ArrayList();

    ArrayList<Event> eventsCopy = copyEventsToArrayList(events);

    eventsCopy.sort(Comparator.comparing(Event::getWhen, TimeRange.ORDER_BY_START));

    // If the duration of the request is longer than a day there is no aviable times.
    // returns an empty array.
    if (request.getDuration() > TimeRange.WHOLE_DAY.duration()) {
      return availableTimes;
    }

    // If there are no attends on the request then the entire day is free interms of aviability.
    if (request.getAttendees().size() == 0) {
      availableTimes.add(TimeRange.WHOLE_DAY);
      return availableTimes;
    }

    int previous_endTime = TimeRange.START_OF_DAY;

    for (Event eV : eventsCopy) {
      // Does this event contain an attendee?
      if (!Collections.disjoint(eV.getAttendees(), request.getAttendees())) {
        // If previous end time is less than the current start time
        // create a timeRange from the current start to the end of day and add it to our aviable
        // items.
        if (previous_endTime < eV.getWhen().start()) {
          TimeRange tempTr = TimeRange.fromStartEnd(previous_endTime, eV.getWhen().start(), false);
          availableTimes.add(tempTr);
        }

        // Set the most recent end time to into previous_Endtime
        if (previous_endTime < eV.getWhen().end()) {
          previous_endTime = eV.getWhen().end();
        }
      }
    }

    // If the difference of the current previous end time and the full day is more than or equal to
    // the duration of
    // of the request get the time after the previous end time.
    if (TimeRange.END_OF_DAY - previous_endTime >= request.getDuration()) {
      availableTimes.add(getTimeAfter(previous_endTime));
    }

    return availableTimes;
  }

  public static ArrayList<Event> copyEventsToArrayList(Collection<Event> events) {
    ArrayList<Event> result = new ArrayList<Event>();
    for (Event ev : events) {
      result.add(ev);
    }
    return result;
  }

  public static TimeRange getTimeAfter(int start) {
    return TimeRange.fromStartEnd(start, TimeRange.END_OF_DAY, true);
  }
}
