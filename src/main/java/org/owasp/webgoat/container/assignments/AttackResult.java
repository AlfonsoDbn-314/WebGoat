/*
 * SPDX-FileCopyrightText: Copyright © 2014 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.container.assignments;

import java.util.Map;
import lombok.Getter;
import org.apache.commons.text.translate.AggregateTranslator;
import org.apache.commons.text.translate.CharSequenceTranslator;
import org.apache.commons.text.translate.EntityArrays;
import org.apache.commons.text.translate.LookupTranslator;
import org.owasp.webgoat.container.i18n.PluginMessages;

@Getter
public class AttackResult {

  private static final CharSequenceTranslator ESCAPE_KEEPING_ACCENTS =
      new AggregateTranslator(
          new LookupTranslator(Map.of("\"", "\\\"", "\\", "\\\\", "/", "\\/")),
          new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE));

  private final boolean lessonCompleted;
  private final String feedback;
  private Object[] feedbackArgs;
  private final String output;
  private Object[] outputArgs;
  private final String assignment;
  private final boolean attemptWasMade;

  private AttackResult(
      boolean lessonCompleted,
      String feedback,
      String output,
      String assignment,
      boolean attemptWasMade) {
    this.lessonCompleted = lessonCompleted;
    this.feedback = ESCAPE_KEEPING_ACCENTS.translate(feedback);
    this.output = ESCAPE_KEEPING_ACCENTS.translate(output);
    this.assignment = assignment;
    this.attemptWasMade = attemptWasMade;
  }

  public AttackResult(
      boolean lessonCompleted,
      String feedback,
      Object[] feedbackArgs,
      String output,
      Object[] outputArgs,
      String assignment,
      boolean attemptWasMade) {
    this.lessonCompleted = lessonCompleted;
    this.feedback = feedback;
    this.feedbackArgs = feedbackArgs;
    this.output = output;
    this.outputArgs = outputArgs;
    this.assignment = assignment;
    this.attemptWasMade = attemptWasMade;
  }

  public boolean assignmentSolved() {
    return lessonCompleted;
  }

  public AttackResult apply(PluginMessages pluginMessages) {
    return new AttackResult(
        lessonCompleted,
        pluginMessages.getMessage(feedback, feedback, feedbackArgs),
        pluginMessages.getMessage(output, output, outputArgs),
        assignment,
        attemptWasMade);
  }
}
