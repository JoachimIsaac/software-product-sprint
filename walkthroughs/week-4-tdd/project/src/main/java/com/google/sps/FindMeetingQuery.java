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
        int previous_endTime = 0; 
        boolean conflict = false;
  
        ArrayList<String> currentAttendees = new ArrayList<String>();
        ArrayList<TimeRange> availableGaps = new ArrayList<TimeRange>();
        availableGaps = getGaps(eventsCopy);
        




        for(Event eV : eventsCopy){


            for(String attendee: eV.getAttendees()){
                if(request.getAttendees().contains(attendee)){


                        

                        if(currentAttendees.contains(attendee)){
                            conflict = true;
                            continue;
                            
                        }

                            
                        

                        if(previous_endTime < eV.getWhen().start()){
                            
                                TimeRange tempTr = new TimeRange(0, 24 * 60);
                                tempTr = tempTr.fromStartEnd(previous_endTime,eV.getWhen().start() - 1,true);
                                availableTimes.add(tempTr);
                        }



                        if(previous_endTime <  eV.getWhen().end()){
                            previous_endTime = eV.getWhen().end();
                        }
                        
                        

                        if(overlaps(availableTimes,getTimeBefore(eV))){
                       
                        availableTimes.add(getTimeBefore(eV));
                        }




                        tempConflict = eV.getWhen().end();
                        
                        currentAttendees.add(attendee);

                }
                
            }
        }

      
        
        availableTimes.add(getTimeAfter(previous_endTime));
    
 
    return availableTimes;
    // return results;
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

   



    public boolean overlaps(ArrayList<TimeRange> possibleTimes, TimeRange ev){
        for(TimeRange t : possibleTimes){
            if(ev.start() == t.start()){
                return false;
            }

        }
        return true;
    }



public ArrayList<TimeRange> getGaps(ArrayList<Event> eventCopy){
    // find possibles gaps
    ArrayList<TimeRange> result = new ArrayList<TimeRange>();

    for(int i = 1; i < eventCopy.size(); i++){
    
      TimeRange tempT = new TimeRange(0, 24 * 60);
        tempT = tempT.fromStartEnd(eventCopy.get(i - 1).getWhen().end(),eventCopy.get(i).getWhen().start() - 1,true);
    
    
  
    result.add(tempT);
    }

    return result;
  

}

    

  }   
    
    
    
    
    
    
    
    
    
