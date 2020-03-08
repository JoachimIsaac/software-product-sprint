package com.google.sps.comments;

import com.google.appengine.api.datastore.Entity;
import com.google.gson.annotations.Expose;
import javax.servlet.http.HttpServletRequest;

/** A representation of a comment left by users. */
public class Comment {
  public static final String ENTITY = "comment";
  private static final String PROPERTY_AUTHOR = "author";
  private static final String PROPERTY_COMMENT = "comment";

  @Expose public final String author;
  @Expose public final String comment;

  private Comment(String author, String comment) {
    this.author = author;
    this.comment = comment;
  }

  public static Comment fromEntity(Entity entity) {
    return new Comment((String) entity.getProperty(PROPERTY_AUTHOR),
        (String) entity.getProperty(PROPERTY_COMMENT));
  }

  public static Comment fromServletRequest(HttpServletRequest request) {
    return new Comment(
        request.getParameter(PROPERTY_AUTHOR), request.getParameter(PROPERTY_COMMENT));
  }

  public Entity toEntity() {
    Entity entity = new Entity(ENTITY);
    entity.setProperty(PROPERTY_AUTHOR, author);
    entity.setProperty(PROPERTY_COMMENT, comment);
    return entity;
  }
}