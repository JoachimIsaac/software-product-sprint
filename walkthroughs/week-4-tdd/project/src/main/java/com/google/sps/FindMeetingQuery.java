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

import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Set;
import java.util.Comparator;


public final class FindMeetingQuery {




  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
        
        ArrayList<TimeRange> availableTimes = new ArrayList();
        
        ArrayList<Event> eventsCopy = new ArrayList<Event>();

        eventsCopy = loadEvents(events);
        
        eventsCopy.sort(Comparator.comparing(Event::getWhen, TimeRange.ORDER_BY_START));


        TimeRange tempT = new TimeRange(0, 24 * 60);

    /// if our duration is more than a day 
        if(request.getDuration() > tempT.duration() ){
            return availableTimes; // return empty array 
        }
        else if(request.getAttendees().size() == 0){
                 availableTimes.add(tempT);
                 return availableTimes;
        }
        
        int tempConflict = 0;
        boolean conflict = false;
  
        ArrayList<String> currentAttendees = new ArrayList<String>();

        for(Event eV : eventsCopy){
            for(String attendee: eV.getAttendees()){
                if(request.getAttendees().contains(attendee)){

                        if(currentAttendees.contains(attendee)){
                            conflict = true;
                            continue;
                            
                        }

                        availableTimes.add(getTimeBefore(eV));
                        tempConflict = eV.getWhen().end();
                        currentAttendees.add(attendee);
                }
                
            }
        }

      

        availableTimes.add(getTimeAfter(tempConflict));

 
    
  
       
    
    
    
    
    return availableTimes;
    
    }


    public static ArrayList<Event> loadEvents(Collection<Event> events){
         ArrayList<Event> result = new ArrayList<Event>();

        for(Event ev : events){
            result.add(ev);
        }
        return result;
    }


   public static TimeRange getTimeAfter(int start){
       
        TimeRange tempT = new TimeRange(start, 24 * 60);
       tempT = tempT.fromStartEnd(start,(24 * 60) - 1,true);
       return tempT;
   }

    public static TimeRange getTimeBefore(Event ev){
       
        TimeRange tempT = new TimeRange(0, ev.getWhen().start());
        
       return tempT;
   }


    // public static ArrayList<TimeRange> checkIfTimeRangesClash(ArrayList<TimeRange> resquestRanges,Collection<Event> events){
    //     .
    //     ArrayList<TimeRange> results = new ArrayList<TimeRange>();
        
    //     for(TimeRange rr : resquestRanges){
            
    //         int counter = 0; 

       
    //         for(Event event : events){
    //             if(event.getWhen().contains(rr)){
    //                 counter += 1;
    //                 continue;
    //             }
    //         }
    //     if(counter  == 0 && pt.duration() < pt.END_OF_DAY){
    //         results.add(rr);
    //         }
    //     }
        
    //     return results;

    // }


  }   
    
    
    
    
    
    
    
    
    
