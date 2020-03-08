package com.google.sps.comments;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;

public class CommentService {
  private CommentService() {}

  public static List<Comment> fetchAsList(Query query) {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    List<Comment> comments = new ArrayList<Comment>();
    for (Entity entity : datastore.prepare(query).asIterable()) {
      comments.add(Comment.fromEntity(entity));
    }
    return comments;
  }

  public static Query createQueryForAll() {
    return new Query(Comment.ENTITY);
  }

  public static void add(Comment comment) {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(comment.toEntity());
  }

  public static String toJson(List<Comment> comments) {
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    return gson.toJson(comments);
  }
} 