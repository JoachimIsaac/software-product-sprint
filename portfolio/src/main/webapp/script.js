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



// doget may be the issue
async function loadAllComments() {
  clearCommentContainer();
  showCommentsLoadingMessage();
  const response = await fetch('/comment');
  const comments = await response.json();
  hideCommentsLoadingMessage();
  addCommentsToContainer(comments);
}

function clearCommentContainer() {
  const commentContainer = document.getElementById('comments');
  while (commentContainer.firstChild) {
    commentContainer.removeChild(commentContainer.firstChild);
  }
}

function showCommentsLoadingMessage() {
  document.getElementById('comments-loading').style.display = 'block';
}

function hideCommentsLoadingMessage() {
  document.getElementById('comments-loading').style.display = 'none';
}

function addCommentsToContainer(comments) {
  const commentContainer = document.getElementById('comments');
  console.log(comments);
  if (comments.length == 0) {
    commentContainer.innerText = 'No comments have been added!'
    return;
  }

  for (const comment of comments) {
    commentContainer.append(createCommentElement(comment));
  }
}

function createCommentElement(comment) {
  const commentElement = document.createElement('p');
  commentElement.innerText = `${comment.author}: ${comment.comment}`;
  return commentElement;
}

function addComment() {
  fetch('/comment', {
    method: 'post',
    body: new URLSearchParams(
        new FormData(document.getElementById('comment-form')))
  }).then(loadAllComments);
}

document.addEventListener('DOMContentLoaded', () => {
  // Fetch and display all of the comments once the DOM is ready.
  loadAllComments();

  // Add an event listener to the comment form submission button.
  document.getElementById('comment-form-submit')
      .addEventListener('click', addComment);
});
