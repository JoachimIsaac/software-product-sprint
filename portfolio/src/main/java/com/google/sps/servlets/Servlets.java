package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/** A collection of utility functions for servlets. */
class Servlets {
  private Servlets() {}

  static void sendJsonResponse(HttpServletResponse response, String json) throws IOException {
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }
} 