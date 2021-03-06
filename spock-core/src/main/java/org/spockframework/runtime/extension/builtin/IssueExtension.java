/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.spockframework.runtime.extension.builtin;

import org.spockframework.report.log.ReportLogConfiguration;
import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension;
import org.spockframework.runtime.model.*;

import spock.lang.Issue;

public class IssueExtension extends AbstractAnnotationDrivenExtension<Issue> {
  private ReportLogConfiguration configuration;

  @Override
  public void visitSpecAnnotation(Issue issue, SpecInfo spec) {
    addTag(issue, spec);
  }

  @Override
  public void visitFeatureAnnotation(Issue issue, FeatureInfo feature) {
    addTag(issue, feature);
  }

  private void addTag(Issue issue, SpecElementInfo specElement) {
    String value = issue.value()[0];

    if (value.startsWith("http://")) {
      int index = value.lastIndexOf('/');
      String name = value.substring(index + 1);
      specElement.addTag(new Tag(configuration.issueNamePrefix + name, "issue", name, value));
      return;
    }

    specElement.addTag(new Tag(configuration.issueNamePrefix + value,
        "issue", value, configuration.issueUrlPrefix + value));
  }
}
