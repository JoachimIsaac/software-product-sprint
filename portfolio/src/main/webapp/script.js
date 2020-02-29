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

/**
 * Adds a random greeting to the page.
 */


async function getContentUsingAsyncAwait(){
    console.log("It's starting to fetch!");
    
    const response = await fetch('/data');

    const content = await response.text();

    document.getElementById('info_container').innerText = content;
}

function getServerStats() {
  fetch('/data').then(response => response.json()).then((data) => {
    // stats is an object, not a string, so we have to
    // reference its fields to create HTML content
    const divElement = document.getElementById('info_container');
    console.log(data);
    divElement.innerHTML = '';
    divElement.appendChild(
        createListElement('Contestant1: ' + data[0]));
    divElement.appendChild(
        createListElement('Contestant2: ' + data[1]));
    divElement.appendChild(
        createListElement('Contestant3: ' + data[2]));
  });
}


function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}